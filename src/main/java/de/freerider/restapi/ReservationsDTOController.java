package de.freerider.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.freerider.datamodel.Customer;
import de.freerider.datamodel.Reservation;
import de.freerider.repository.CustomerRepository;
import de.freerider.repository.ReservationRepository;
import de.freerider.restapi.dto.CustomerDTO;
import de.freerider.restapi.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.mappers.ModelMapper;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class ReservationsDTOController implements ReservationsDTOApi {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ApplicationContext context;
    @Autowired
    ReservationRepository reservationRepo;
    @Autowired
    CustomerRepository customerRepo;
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
    public ResponseEntity<ReservationDTO> getReservation(long id) {
        ResponseEntity<ReservationDTO> response = null;
        System.err.println(request.getMethod() + " " + request.getRequestURI());

        Optional c = customerRepo.findById(id);
        Iterable<Reservation> reservations = reservationRepo.findAll();
        if (c.isPresent()) {
            Customer customer = (Customer) c.get();
            for(Reservation r: reservations) {
                if(r.getOwner() == customer) {
                    ReservationDTO dto = new ReservationDTO(r);
                    response = new ResponseEntity<ReservationDTO>(dto, HttpStatus.OK);
                    return response;
                }
            }
        }
        System.err.println(request.getMethod() + " " + request.getRequestURI());
        response = new ResponseEntity<ReservationDTO>(HttpStatus.NOT_FOUND);
        System.err.println(response);
        return response;
    }
}
