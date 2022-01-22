package de.freerider.datamodel;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Class for entity type Reservation. Reservation is an state of a vehicle that belongs to a customer
 *
 * @author ksachs
 * @version "0.1.0"
 * @since "0.1.0"
 */
@Entity
@Table(name = "VEHICLE")
public class Vehicle {

    /**
     * id attribute, {@code < 0} invalid, can be set only once.
     */
    @Id
    @Column(name = "ID")
    private int id = -1;

    @Column(name = "MAKE")
    private String make;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "PASSENGERS")
    private Integer passengers;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "POWER")
    private Power power;

    /**
     * Default constructor
     */
    public Vehicle() {
    }

    public Vehicle(Integer id, String make, String model, String color) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getId() {
        return id;
    }

    public Integer getPassengers() {
        return passengers;
    }

    public Power getPower() {
        return power;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Vehicle setPassengers(Integer passengers) {
        this.passengers = passengers;
        return this;
    }

    public Vehicle setPower(Power power) {
        this.power = power;
        return this;
    }
}
