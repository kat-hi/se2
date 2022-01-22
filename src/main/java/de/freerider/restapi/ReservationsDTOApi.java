package de.freerider.restapi;

import de.freerider.restapi.dto.CustomerDTO;
import de.freerider.restapi.dto.VehiclesDTO;
import de.freerider.restapi.dto.ReservationDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RequestMapping("/api/v1/reservations")
public interface ReservationsDTOApi {
    /**
     * GET /reservations/owner_id
     *
     * @return JSON Array with reservations of customer.
     */

    /*
     * Swagger API doc annotations:
     */
    @Operation(
            summary = "Return the customer's reservations with {id} from repository.",
            description = "Return the customer's reservations with {id} from repository",
            tags = {"reservations-dto-controller"}
    )

    /*
     * Spring REST Controller annotation:
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "{id}",    // relative to interface @RequestMapping
            produces = {"application/json"}
    )
    ResponseEntity<List<ReservationDTO>> getReservations(@PathVariable("id") long id);

    /**
     * POST /reservations
     * <p>
     * Add new Reservations from JSON data passed as array of JSON objects in the request body.
     * Multiple Reservations can be posted with multiple JSON objects from the same request.
     * Id's are assigned, if id-attributes are missing or are empty in JSON data.
     * <p>
     * JSON data containing id of objects that are already present are rejected. Rejected
     * objects are returned in the response with error 409 (conflict).
     * <p>
     * Status 201 (created) is returned with empty array of conflicts when all objects were
     * accepted. Partial acceptance of objects from the request is possible, but error 409 is
     * returned with the array of rejected objects.
     *
     * @param dtos array of maps with raw JSON {@code <key,obj>}-data.
     * @return JSON array with the rejected JSON objects, empty array [] if all objects were accepted.
     */

    /*
     * Swagger API doc annotations:
     */
    @Operation(
            summary = "Add new reservations to repository.",
            description = "Add new reservations to repository.",
            tags = {"reservations-dto-controller"}
    )

    /*
     * Spring REST Controller annotation:
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = ""    // relative to interface @RequestMapping
    )
    ResponseEntity<List<ReservationDTO>> postReservations(@RequestBody List<ReservationDTO> dtos);

    /*
     * Swagger API doc annotations:
     */
    @Operation(
            summary = "Delete reservation by its id from repository.",
            description = "Delete reservation by its id from repository.",
            tags = {"reservations-dto-controller"}
    )

    /*
     * Spring REST Controller annotation:
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "{id}"    // relative to interface @RequestMapping
    )
    //
    ResponseEntity<?> deleteReservation( @PathVariable("id") int id );

    /**
     * PUT /Reservations
     * <p>
     * Update existing Reservations from JSON data passed as array of JSON objects in the request body.
     * Multiple Reservations can be updated from multiple JSON objects from the same request.
     * <p>
     * JSON data missing id or with id that are not found are rejected. Rejected JSON objects
     * are returned in the response with error 404 (not found).
     * <p>
     * Status 202 (accepted) is returned with empty array of conflicts when all updates could be
     * performed. Partial acceptance of updates is possible for entire objects only (not single
     * attributes). Error 409 (conflict) is returned for errors other than an object (id) was not
     * found along with the array of rejected objects.
     *
     * @param dtos array of maps with raw JSON {@code <key,obj>}-data.
     * @return JSON array with the rejected JSON objects, empty array [] if all updates were accepted.
     */

    /*
     * Swagger API doc annotations:
     */
    @Operation(
            summary = "Update existing Reservations in repository.",
            description = "Update existing Reservations in repository.",
            tags = {"reservations-dto-controller"}
    )

    /*
     * Spring REST Controller annotation:
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "{id}"    // relative to interface @RequestMapping
    )
    //
    ResponseEntity<List<ReservationDTO>> putReservation(@PathVariable("id") int id, @RequestBody List<ReservationDTO> dtos );

}
