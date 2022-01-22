package de.freerider.datamodel;

import javax.persistence.*;

/**
 * Class for entity type Reservation. Reservation is an state of a vehicle that belongs to a customer
 *
 * @since "0.1.0"
 * @version "0.1.0"
 * @author ksachs
 */
@Entity
@Table(name = "RESERVATION")
public class Reservation {
    /**
     * id attribute, {@code < 0} invalid, can be set only once.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private int id = -1;

}
