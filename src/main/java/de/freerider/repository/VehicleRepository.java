package de.freerider.repository;

import de.freerider.datamodel.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends org.springframework.data.repository.CrudRepository<Vehicle, Integer> {
}
