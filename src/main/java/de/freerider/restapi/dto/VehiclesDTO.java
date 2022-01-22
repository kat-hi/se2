package de.freerider.restapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.freerider.datamodel.Power;
import de.freerider.datamodel.Vehicle;

public class VehiclesDTO {

    @JsonProperty("ID")
    private int id;

    @JsonProperty("MAKE")
    private String make;

    @JsonProperty("MODEL")
    private String model;

    @JsonProperty("PASSENGERS")
    private Integer passengers;

    @JsonProperty("COLOR")
    private String color;

    @JsonProperty("POWER")
    private Power power;

    public VehiclesDTO(Vehicle copy) {
        this.id = copy.getId();
        this.make = copy.getMake();
        this.model = copy.getModel();
        this.color = copy.getColor();
        this.passengers = copy.getPassengers();
        this.power = copy.getPower();

    }

}
