package de.freerider.datamodel;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

/**
 * Class for entity type Reservation. Reservation is an state of a vehicle that belongs to a customer
 *
 * @author ksachs
 * @version "0.1.0"
 * @since "0.1.0"
 */
@Entity
@Table(name = "RESERVATION")
public class Reservation {
    /**
     * id attribute, {@code < 0} invalid, can be set only once.
     */
    @Id
    @Column(name = "ID")
    private int id = -1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Customer owner;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Column
    private String reservation_begin;

    @Column
    private String reservation_end;

    @Column
    private LocCode reservation_pickup;

    @Column
    private String reservation_drop;

    @Column
    private String status;

    public Reservation(Integer id, Customer owner, Vehicle v) {
        this.id = id;
        this.owner = owner;
        this.vehicle = v;
    }
    public Reservation() {}

    public Customer getOwner() {
        return owner;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getId() {
        return id;
    }

    public String getReservation_begin() {
        return reservation_begin;
    }

    public String getReservation_end() {
        return reservation_end;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocCode getReservation_pickup() {
        return reservation_pickup;
    }

    public String getReservation_drop() {
        return reservation_drop;
    }

    public String getStatus() {
        return status;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public void setReservation_begin(String reservation_begin) {
        this.reservation_begin = reservation_begin;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setReservation_drop(String reservation_drop) {
        this.reservation_drop = reservation_drop;
    }

    public void setReservation_end(String reservation_end) {
        this.reservation_end = reservation_end;
    }

    public void setReservation_pickup(LocCode reservation_pickup) {
        this.reservation_pickup = reservation_pickup;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Reservation pickup(LocCode location, String datetime) {
        this.reservation_pickup = location;
        this.reservation_begin = datetime;
        return this;
    }

    public Reservation dropoff(LocCode location, String datetime) {
        this.reservation_pickup = location;
        this.reservation_end = datetime;
        return this;
    }

}
