package de.freerider.restapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationDTO {
    /**
     * JsonProperty, unique identifier to track DTO.
     */
    @JsonProperty("id")
    private int id;

    /**
     * JsonProperty, foreign relationship to current owner
     */
    @JsonProperty("owner_id")
    private long owner_id;

    /**
     * JsonProperty, foreign relationship to vechicle
     */
    @JsonProperty("vechicle_id")
    private String vechicle_id;


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
    private String reservation_pickup;

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

}
