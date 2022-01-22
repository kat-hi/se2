package de.freerider.restapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.freerider.datamodel.Customer;
import de.freerider.datamodel.LocCode;
import de.freerider.datamodel.Reservation;
import de.freerider.datamodel.Vehicle;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class ReservationDTO {
    /**
     * JsonProperty, unique identifier to track DTO.
     */
    @JsonProperty("id")
    private int id;

    /**
     * JsonProperty, foreign relationship to current owner
     */
    @JsonProperty("owner")
    private CustomerDTO owner;

    /**
     * JsonProperty, foreign relationship to vechicle
     */
    @JsonProperty("vechicle")
    private VehiclesDTO vechicle;


    /**
     * JsonProperty, time of reservation begin
     */
    @JsonProperty("reservation_begin")
    private String reservation_begin;


    /**
     * JsonProperty, time of reservation end
     */
    @JsonProperty("reservation_end")
    private String reservation_end;

    /**
     * JsonProperty, location to pick up vehicle
     */
    @JsonProperty("reservation_pickup")
    private LocCode reservation_pickup;

    /**
     * JsonProperty, location drop off vehicle
     */
    @JsonProperty("reservation_drop")
    private String reservation_drop;


    /**
     * JsonProperty, showing reservation status
     */
    @JsonProperty("reservation_status")
    private String reservation_status;

    public ReservationDTO() {
    }

    public ReservationDTO(Reservation copy) {
        this.id = copy.getId();
        this.reservation_begin = copy.getReservation_begin();
        this.owner = new CustomerDTO(copy.getOwner());
        this.vechicle = new VehiclesDTO(copy.getVehicle());
        this.reservation_drop = copy.getReservation_drop();
        this.reservation_end = copy.getReservation_end();
        this.reservation_pickup = copy.getReservation_pickup();
        this.reservation_status = copy.getStatus();
    }

    /**
     * Public factory method to create internal object from this DTO.
     *
     * @return Optional with created internal object (or empty).
     */
    public Optional<Reservation> create() {
//        createValidated()
        return create_();
    }

    private Optional<Reservation> create_() {
        Reservation reservation = null;
        try {
            int random_id = (int) (Math.random() * (10000 - 1));
            Optional<Customer> c = this.owner.create();
            Optional<Vehicle> v = this.vechicle.create();

            if (c.isEmpty()) {
                System.err.println("Customer is empty");
                return Optional.empty();
            }
            if (v.isEmpty()) {
                System.err.println("Vehicle is empty");
                return Optional.empty();
            }

            reservation = new Reservation(random_id, c.get(), v.get());
            if(this.reservation_begin != null) {
                reservation.setReservation_begin(this.reservation_begin);
            }
            if(this.reservation_end != null) {
                reservation.setReservation_end(this.reservation_end);
            }
            if(this.reservation_pickup != null) {
                reservation.setReservation_pickup(this.reservation_pickup);
            }
            //
        } catch (Exception e) {
            reservation = null;
        }
        return Optional.ofNullable(reservation);
    }


}
