package de.freerider.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class for entity type Reservation. Reservation is an state of a vehicle that belongs to a customer
 *
 * @since "0.1.0"
 * @version "0.1.0"
 * @author ksachs
 */
@Entity
@Table(name = "VEHICLE")
public class Vehicle {
    /**
     * id attribute, {@code < 0} invalid, can be set only once.
     */
    @Id
    @Column(name="ID")
    private long id = -1;
}
