package baeldung.liquibase;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "car")
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "make", length = 255)
    private String make;

    @Column(name = "brand", length = 255)
    private String brand;

    @Column(name = "price")
    private Double price;
}