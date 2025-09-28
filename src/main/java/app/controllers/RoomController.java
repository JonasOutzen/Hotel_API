package app.controllers;

import app.config.HibernateConfig;
import app.daos.RoomDAO;
import app.dtos.RoomDTO;
import app.daos.HotelDAO;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class RoomController {

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    RoomDAO roomDAO = RoomDAO.getInstance(emf);

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private static final Logger debugLogger = LoggerFactory.getLogger("app");

    public void createRoom(Context ctx) {
        RoomDTO[] roomDTOS = ctx.bodyAsClass(RoomDTO[].class);

        List<RoomDTO> newRoomDTOs = roomDAO.createRoomsFromList(roomDTOS);
        ctx.status(HttpStatus.CREATED);
        ctx.json(newRoomDTOs);
    }

    public void getAllRooms(Context ctx) {
        List<RoomDTO> roomDTOS = roomDAO.getAllRooms();
        ctx.status(HttpStatus.OK);
        ctx.json(roomDTOS);
        logger.info("Fetched all rooms, count: "  + roomDTOS.size());
    }

    public void getRoomById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        RoomDTO roomDTO = roomDAO.getRoomById(id);
        if (roomDTO != null) {
            ctx.status(HttpStatus.OK);
            ctx.json(roomDTO);
            logger.info("Fetched room with id: " + id);
        } else {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json(Map.of("error", "room with id " + id + " not found"));
            logger.warn("room with id " + id + " not found");
    }
    }

    public void updateRoom(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        RoomDTO roomDTO = ctx.bodyAsClass(RoomDTO.class);
        roomDTO = roomDAO.updateRoom(id, roomDTO);
        ctx.status(HttpStatus.OK);
        ctx.json(roomDTO);
    }

    public void deleteRoom(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        roomDAO.deleteRoom(id);
        ctx.result("Room with id " + id + " deleted");
    }
}
