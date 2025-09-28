package app.controllers;

import app.config.HibernateConfig;
import app.daos.HotelDAO;
import app.dtos.HotelDTO;
import jakarta.persistence.EntityManagerFactory;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Map;

public class HotelController {

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    HotelDAO hotelDAO = HotelDAO.getInstance(emf);

    private static final Logger logger = LoggerFactory.getLogger(HotelController.class);
    private static final Logger debugLogger = LoggerFactory.getLogger("app");

    public void createSample(Context ctx) {
        HotelDTO[] hotelDTOS = ctx.bodyAsClass(HotelDTO[].class);

        List<HotelDTO> newHotelDTOS = hotelDAO.createFromList(hotelDTOS);
        ctx.status(HttpStatus.CREATED);
        ctx.json(newHotelDTOS);
    }

    public void getAllSamples(Context ctx) {
        List<HotelDTO> hotelDTOS = hotelDAO.getAllHotels();
        ctx.status(HttpStatus.OK);
        ctx.json(hotelDTOS);
        logger.info("Fetched all samples, count: "  + hotelDTOS.size());
    }

    public void getSampleById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        HotelDTO hotelDTO = hotelDAO.getById(id);
        if (hotelDTO != null) {
            ctx.status(HttpStatus.OK);
            ctx.json(hotelDTO);
            logger.info("Fetched sample with id: " + id);
        } else {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json(Map.of("error", "Hotel with id " + id + " not found"));
            logger.warn("Hotel with id " + id + " not found");
    }
    }

    public void updateSample(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        HotelDTO hotelDTO = ctx.bodyAsClass(HotelDTO.class);
        hotelDTO = hotelDAO.update(id, hotelDTO);
        ctx.status(HttpStatus.OK);
        ctx.json(hotelDTO);
    }

    public void deleteSample(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        hotelDAO.delete(id);
        ctx.result("Hotel with id " + id + " deleted");
    }
}
