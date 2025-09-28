package app.daos;

import app.dtos.HotelDTO;
import app.dtos.RoomDTO;
import app.entities.Room;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    private static RoomDAO instance;
    private static EntityManagerFactory emf;

    private RoomDAO() {}

    public static RoomDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            instance = new RoomDAO();
            emf = _emf;
        }
        return instance;
    }

    public List<RoomDTO> getAllRooms() {
        try (var em = emf.createEntityManager()) {
            TypedQuery<Room> query = em.createQuery("SELECT r FROM Room r", Room.class);
            return RoomDTO.toDTOList(query.getResultList());
        }
    }

    public RoomDTO getRoomById(int id) {
        try (var em = emf.createEntityManager()) {
            var entity = em.find(Room.class, id);
            return (entity != null) ? new RoomDTO(entity) : null;
        }
    }

    public RoomDTO createRoom(RoomDTO dto) {
        var entity = new Room();
        entity.setNumber(dto.getNumber());
        entity.setPrice(dto.getPrice());
        // hotel relation should be set separately (through HotelDAO.addRoom)
        try (var em = emf.createEntityManager()) {
            var tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
            return new RoomDTO(entity);
        }
    }

    public List<RoomDTO> createRoomsFromList(RoomDTO[] dtos) {
        List<RoomDTO> roomDTOS = new ArrayList<>();
        for (RoomDTO dto : dtos) {
            roomDTOS.add(createRoom(dto));
        }
        return roomDTOS;
    }

    public RoomDTO updateRoom(int id, RoomDTO dto) {
        try (var em = emf.createEntityManager()) {
            Room entity = em.find(Room.class, id);
            if (entity != null) {
                var tx = em.getTransaction();
                tx.begin();
                entity.setNumber(dto.getNumber());
                entity.setPrice(dto.getPrice());
                em.merge(entity);
                tx.commit();
                return new RoomDTO(entity);
            }
            return null;
        }
    }

    public void deleteRoom(int id) {
        try (var em = emf.createEntityManager()) {
            var tx = em.getTransaction();
            tx.begin();
            var entity = em.find(Room.class, id);
            if (entity != null) {
                em.remove(entity);
            }
            tx.commit();
        }
    }

    public void deleteAllRooms() {
        try (var em = emf.createEntityManager()) {
            var tx = em.getTransaction();
            tx.begin();
            em.createQuery("DELETE FROM Room").executeUpdate();
            tx.commit();
        }
    }
}
