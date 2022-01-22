package de.freerider.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.freerider.datamodel.Customer;
import de.freerider.datamodel.Vehicle;
import de.freerider.repository.CustomerRepository;
import de.freerider.repository.VehicleRepository;
import de.freerider.restapi.dto.CustomerDTO;
import de.freerider.restapi.dto.VehiclesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.mappers.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class VehiclesDTOController implements VehiclesDTOApi {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ApplicationContext context;

    @Autowired
    public VehicleRepository repo;

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    /**
     * Constructor.
     *
     * @param objectMapper entry point to JSON tree for the Jackson library
     * @param request      HTTP request object
     */
    public VehiclesDTOController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<List<VehiclesDTO>> getVehicles() {
        ResponseEntity<List<VehiclesDTO>> response = null;
        System.err.println(request.getMethod() + " " + request.getRequestURI());
        Iterable<Vehicle> cList = repo.findAll();
        List<VehiclesDTO> dtoList = new ArrayList<VehiclesDTO>();
        cList.forEach(c -> {
            dtoList.add(new VehiclesDTO(c));
        });
        response = new ResponseEntity<List<VehiclesDTO>>(dtoList, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<VehiclesDTO> getVehicle(int id) {
        ResponseEntity<VehiclesDTO> response = null;
        System.err.println(request.getMethod() + " " + request.getRequestURI());
        Optional v = repo.findById(id);
        if(v.isPresent()) {
            Vehicle vehicle = (Vehicle) v.get();
            VehiclesDTO dto = new VehiclesDTO(vehicle);
            response = new ResponseEntity<VehiclesDTO>(dto, HttpStatus.OK);
            return response;
        }
        System.err.println(request.getMethod() + " " + request.getRequestURI());
        response = new ResponseEntity<VehiclesDTO>(HttpStatus.NOT_FOUND);
        return response;
    }
}
