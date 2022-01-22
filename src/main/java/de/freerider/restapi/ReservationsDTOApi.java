package de.freerider.restapi;

import de.freerider.restapi.dto.ReservationDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
            tags = {"reservations-controller"}
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

}
