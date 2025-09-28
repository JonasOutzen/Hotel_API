package app.dtos;

import app.entities.Hotel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelDTO {

    private Integer id;
    private String name;
    private String address;
    private Integer rooms;
    private Integer stars;

    public HotelDTO(Hotel hotel) {
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.address = hotel.getAddress();
        this.rooms = hotel.getRooms();
        this.stars = hotel.getStars();
    }

    public HotelDTO(Integer id, String name, String address, Integer rooms, Integer stars) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.rooms = rooms;
        this.stars = stars;
    }

    public static List<HotelDTO> toDTOList(List<Hotel> resultList) {
        return resultList.stream().map(HotelDTO::new).toList();
    }
}
