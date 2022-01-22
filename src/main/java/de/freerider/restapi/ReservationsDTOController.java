package de.freerider.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.freerider.repository.CustomerRepository;
import de.freerider.restapi.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.mappers.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ReservationsDTOController implements  ReservationsDTOApi {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ApplicationContext context;
    @Autowired
    CustomerRepository repo;
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    /**
     * Constructor.
     *
     * @param objectMapper entry point to JSON tree for the Jackson library
     * @param request      HTTP request object
     */
    public ReservationsDTOController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<List<ReservationDTO>> getReservations(long id) {
        return null;
    }
}
