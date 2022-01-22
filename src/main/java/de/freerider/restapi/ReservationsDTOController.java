package de.freerider.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.v2.runtime.reflect.Lister;
import de.freerider.datamodel.Customer;
import de.freerider.datamodel.Reservation;
import de.freerider.repository.CustomerRepository;
import de.freerider.repository.ReservationRepository;
import de.freerider.restapi.dto.CustomerDTO;
import de.freerider.restapi.dto.ReservationDTO;
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

    private List<ReservationDTO> findAllReservationsOfCustomerById(long id) {
        Iterable<Reservation> reservations = reservationRepo.findAll();
        List<ReservationDTO> list = new ArrayList<ReservationDTO>();
        Optional c = customerRepo.findById(id);
        if (c.isPresent()) {
            Customer customer = (Customer) c.get();
            for (Reservation r : reservations) {
                if (r.getOwner() == customer) {
                    ReservationDTO dto = new ReservationDTO(r);
                    list.add(dto);
                }
            }
        }
        return list;
    }

    @Override
    public ResponseEntity<List<ReservationDTO>> getReservations(long id) {
        ResponseEntity<List<ReservationDTO>> response = null;
        System.err.println(request.getMethod() + " " + request.getRequestURI());

        List<ReservationDTO> l = findAllReservationsOfCustomerById(id);

        if (l.size() > 0) {
            response = new ResponseEntity<List<ReservationDTO>>(l, HttpStatus.OK);
            return response;
        }
        System.err.println(request.getMethod() + " " + request.getRequestURI());
        response = new ResponseEntity<List<ReservationDTO>>(HttpStatus.NOT_FOUND);
        System.err.println(response);
        return response;
    }

    @Override
    public ResponseEntity<List<ReservationDTO>> postReservations(List<ReservationDTO> dtos) {
        System.err.println("POST /reservations");

        if (dtos == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        List<Reservation> cList = new ArrayList<Reservation>();
        for (ReservationDTO dto : dtos) {
            Optional<Reservation> r = dto.create();
            if (r.isPresent()) {
                Reservation reservation = r.get();
                cList.add(reservation);
            } else {
                System.err.println("Could not create Reservation.");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
        reservationRepo.saveAll(cList);
        System.err.println("Reservation saved");
        return new ResponseEntity<List<ReservationDTO>>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteReservation(int id) {
        System.err.println("DELETE /reservations/" + id);
        if (reservationRepo.existsById(id)) {
            reservationRepo.deleteById(id);
            System.out.println("reservation " + id + " deleted.");
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED); // status 202
        } else {
            System.err.println("reservation " + id + " not found.");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // status 404
        }
    }

    @Override
    public ResponseEntity<List<ReservationDTO>> putReservation(int id, List<ReservationDTO> dtos) {
        System.err.println("PUT /reservations");

        if (dtos == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        for (ReservationDTO dto : dtos) {
            Optional<Reservation> new_res = dto.create();
            if (new_res.isPresent()) {
                Reservation new_reservation = new_res.get();
                Optional<Reservation> current_reservation = reservationRepo.findById(id);
                if (current_reservation.isPresent()) {
                    Reservation reservation = current_reservation.get();
                    if (!reservation.getReservation_begin().equals(new_reservation.getReservation_begin())) {
                        reservation.setReservation_begin(new_reservation.getReservation_begin());
                    }
                    if (!reservation.getReservation_end().equals(new_reservation.getReservation_end())) {
                        reservation.setReservation_end(new_reservation.getReservation_end());
                    }
                    if (reservation.getReservation_pickup() != new_reservation.getReservation_pickup()) {
                        reservation.setReservation_pickup(new_reservation.getReservation_pickup());
                    }
                    reservationRepo.save(reservation);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
