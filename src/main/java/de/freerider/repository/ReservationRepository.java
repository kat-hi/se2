package de.freerider.repository;

import de.freerider.datamodel.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends
        org.springframework.data.repository.CrudRepository<Reservation, Integer> {
}
