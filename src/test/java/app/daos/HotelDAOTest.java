package app.daos;

import app.config.HibernateConfig;
import app.dtos.HotelDTO;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelDAOTest {

    private static EntityManagerFactory emf;
    private static HotelDAO dao;

    @BeforeAll
    static void setup() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        dao = HotelDAO.getInstance(emf);
    }

    @BeforeEach
    void clearDb() {
        dao.deleteAll();
    }

    @Test
    void testCreateAndGetById() {
        HotelDTO dto = new HotelDTO(null, "Hotel California", "California st. 8840", 221, 5);
        HotelDTO saved = dao.create(dto);

        assertNotNull(saved.getId());
        assertEquals("Alice", saved.getName());

        HotelDTO fetched = dao.getById(saved.getId());
        assertEquals(saved.getName(), fetched.getName());
    }

    @Test
    void testGetAllHotels() {
        dao.create(new HotelDTO(null, "Hotel California", "California st. 8840", 221, 5));
        dao.create(new HotelDTO(null, "Hotel Balifornia", "Balifornia st. 8840", 221, 5));

        List<HotelDTO> list = dao.getAllHotels();
        assertEquals(2, list.size());
    }

    @Test
    void testUpdateSample() {
        HotelDTO saved = dao.create(new HotelDTO(null, "Hotel Aalifornia", "Aalifornia st. 8840", 221, 5));
        HotelDTO updated = dao.update(saved.getId(), new HotelDTO(null, "Botel", "California st. 8840", 33, 5));

        assertEquals("Botel", updated.getName());
        assertEquals(33, updated.getRooms());
    }

    @Test
    void testDeleteSample() {
        HotelDTO saved = dao.create(new HotelDTO(null, "Hotel Danglatere", "Et sted i københavn", 221, 5));
        dao.delete(saved.getId());

        assertNull(dao.getById(saved.getId()));
    }
}
