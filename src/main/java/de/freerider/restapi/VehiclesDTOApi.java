package de.freerider.restapi;

import de.freerider.restapi.dto.ReservationDTO;
import de.freerider.restapi.dto.VehiclesDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RequestMapping("/api/v1/vehicles")
public interface VehiclesDTOApi {

    /*
     * Swagger API doc annotations:
     */
    @Operation(
            summary = "Return all vehicles from repository.",        // appears as text in API short-list
            description = "Return all vehicles from repository.",    // appears as text inside API
            tags = {"vehicles-controller"}    // appears in swagger-ui URL: http://localhost:8080/swagger-ui/index.html#/vehicles-controller
    )
    @ApiResponses(value = {    // also auto-derived by Swagger
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse( responseCode = "404", description = "Not Found")
    })

    /*
     * Spring REST Controller annotation:
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "",    // relative to interface @RequestMapping
            produces = {"application/json"}
    )
    ResponseEntity<List<VehiclesDTO>> getVehicles();

    /**
     * GET /customers/{id}
     *
     * @return JSON Array with customers (compact).
     */

    /*
     * Swagger API doc annotations:
     */
    @Operation(
            summary = "Return vehicle with {id} from repository.",
            description = "Return vehicle with {id} from repository.",
            tags = {"vehicles-controller"}
    )

    /*
     * Spring REST Controller annotation:
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "{id}",    // relative to interface @RequestMapping
            produces = {"application/json"}
    )
    //
    ResponseEntity<VehiclesDTO> getVehicle(@PathVariable("id") int id);

}
