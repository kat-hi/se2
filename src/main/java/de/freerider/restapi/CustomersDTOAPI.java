package de.freerider.restapi;

import de.freerider.restapi.dto.CustomerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@RequestMapping("/api/v1/customers")
public interface CustomersDTOAPI {

    /*
     * Swagger API doc annotations:
     */
    @Operation(
            summary = "Return all customers from repository.",        // appears as text in API short-list
            description = "Return all customers from repository.",    // appears as text inside API
            tags = {"customers-controller"}    // appears in swagger-ui URL: http://localhost:8080/swagger-ui/index.html#/customers-controller
    )
    @ApiResponses(value = {    // also auto-derived by Swagger
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            // to remove "404" from docs, set SwaggerConfig::Docket.useDefaultResponseMessages(true) // ->false
//		@ApiResponse( responseCode = "404", description = "Not Found")
    })

    /*
     * Spring REST Controller annotation:
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "",    // relative to interface @RequestMapping
            produces = {"application/json"}
    )
    ResponseEntity<List<CustomerDTO>> getCustomers();


    /**
     * GET /customers/{id}
     *
     * @return JSON Array with customers (compact).
     */

    /*
     * Swagger API doc annotations:
     */
    @Operation(
            summary = "Return customer with {id} from repository.",
            description = "Return customer with {id} from repository.",
            tags = {"customers-controller"}
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
    ResponseEntity<CustomerDTO> getCustomer( @PathVariable("id") long id );


    /**
     * POST /customers
     * <p>
     * Add new customers from JSON data passed as array of JSON objects in the request body.
     * Multiple customers can be posted with multiple JSON objects from the same request.
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
            summary = "Add new customers to repository.",
            description = "Add new customers to repository.",
            tags = {"customers-controller"}
    )

    /*
     * Spring REST Controller annotation:
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = ""    // relative to interface @RequestMapping
    )
    //
    ResponseEntity<List<CustomerDTO>> postCustomers( @RequestBody List<CustomerDTO> dtos );

    /**
     * PUT /customers
     * <p>
     * Update existing customers from JSON data passed as array of JSON objects in the request body.
     * Multiple customers can be updated from multiple JSON objects from the same request.
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
            summary = "Update existing customers in repository.",
            description = "Update existing customers in repository.",
            tags = {"customers-controller"}
    )

    /*
     * Spring REST Controller annotation:
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = ""    // relative to interface @RequestMapping
    )
    //
    ResponseEntity<List<CustomerDTO>> putCustomers( @RequestBody List<CustomerDTO> dtos );

    /*
     * Swagger API doc annotations:
     */
    @Operation(
            summary = "Delete customer by its id from repository.",
            description = "Delete customer by its id from repository.",
            tags = {"customers-controller"}
    )

    /*
     * Spring REST Controller annotation:
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "{id}"    // relative to interface @RequestMapping
    )
    //
    ResponseEntity<?> deleteCustomer( @PathVariable("id") long id );
}
