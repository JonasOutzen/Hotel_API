package app.entities;

import app.dtos.HotelDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="hotel_id", nullable = false)
    private int hotelId;

    @Column(name="number", nullable = false)
    private Integer number;

    @Column(name="price", nullable = false)
    private double price;

    // Relationer m:1
    @ManyToOne
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Hotel hotel;

    public Room(HotelDTO dto) {
        this.id = dto.getId();
        this.hotelId = hotel.getId();
        this.number = dto.
    }


}

