package app.daos;

import app.entities.Hotel;
import app.entities.Room;

import java.util.List;

public interface IHotelDAO {

    List<Hotel> getAllHotels();
    Hotel getHotelById(int id);
    Hotel createHotel(Hotel hotel);
    Hotel updateHotel(Hotel hotel);
    void deleteHotel(int id);

    // Room operations
    Hotel addRoom(Hotel hotel, Room room);
    Hotel removeRoom(Hotel hotel, Room room);
    List<Room> getRoomsForHotel(Hotel hotel);
}
