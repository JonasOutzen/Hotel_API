package app.daos;

import app.dtos.HotelDTO;
import app.entities.Hotel;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

    private static HotelDAO instance;
    private static EntityManagerFactory emf;

    private HotelDAO() {}

    public static HotelDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new HotelDAO();
            HotelDAO.emf = emf;
        }
        return instance;
    }

    public List<HotelDTO> getAllHotels() {
        try (var em = emf.createEntityManager()) {
            TypedQuery<Hotel> query = em.createQuery("SELECT h FROM Hotel h", Hotel.class);
            return HotelDTO.toDTOList(query.getResultList());
        }
    }

    public HotelDTO create(HotelDTO dto) {
        var entity = new Hotel(dto);
        try (var em = emf.createEntityManager()) {
            var tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
            return new HotelDTO(entity);
        }
    }

    public List<HotelDTO> createFromList(HotelDTO[] dtos) {
        List<HotelDTO> createdList = new ArrayList<>();
        for (HotelDTO dto : dtos) {
            createdList.add(create(dto));
        }
        return createdList;
    }

    public HotelDTO getById(int id) {
        try (var em = emf.createEntityManager()) {
            var entity = em.find(Hotel.class, id);
            return (entity != null) ? new HotelDTO(entity) : null;
        }
    }

    // Note: returns null if entity not found
    public HotelDTO update(int id, HotelDTO dto) {
        try (var em = emf.createEntityManager()) {
            Hotel entity = em.find(Hotel.class, id);
            if (entity != null) {
                var tx = em.getTransaction();
                tx.begin();
                entity.setName(dto.getName());
                entity.setAddress(dto.getAddress());
                entity.setRooms(dto.getRooms());
                entity.setStars(dto.getStars());
                em.merge(entity);
                tx.commit();
                return new HotelDTO(entity);
            }
            return null;
        }
    }

    public void delete(int id) {
        try (var em = emf.createEntityManager()) {
            var tx = em.getTransaction();
            tx.begin();
            var entity = em.find(Hotel.class, id);
            if (entity != null) {
                em.remove(entity);
            }
            tx.commit();
        }
    }

    public void deleteAll() {
        try (var em = emf.createEntityManager()) {
            var tx = em.getTransaction();
            tx.begin();
            em.createQuery("DELETE FROM Hotel").executeUpdate();
            tx.commit();
        }
    }
}
