package de.freerider.restapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.freerider.datamodel.Customer;
import de.freerider.repository.CustomerRepository;
import de.freerider.restapi.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.schema.Model;
import springfox.documentation.swagger2.mappers.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class CustomersDTOController implements CustomersDTOAPI {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ApplicationContext context;
    @Autowired
    CustomerRepository repo = new CustomerRepository();
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    /**
     * Constructor.
     *
     * @param objectMapper entry point to JSON tree for the Jackson library
     * @param request      HTTP request object
     */
    public CustomersDTOController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        ResponseEntity<List<CustomerDTO>> response = null;
        System.err.println(request.getMethod() + " " + request.getRequestURI());
        Iterable<Customer> cList = repo.findAll();
        List<CustomerDTO> dtoList = new ArrayList<CustomerDTO>();
        cList.forEach(c -> {
            dtoList.add(new CustomerDTO(c));
        });
        response = new ResponseEntity<List<CustomerDTO>>(dtoList, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<CustomerDTO> getCustomer(long id) {
        ResponseEntity<CustomerDTO> response = null;
        System.err.println(request.getMethod() + " " + request.getRequestURI());
        Optional c = repo.findById(id);
        if (c.isPresent()) {
            Customer customer = (Customer) c.get();
            CustomerDTO dto = new CustomerDTO(customer);
            response = new ResponseEntity<CustomerDTO>(dto, HttpStatus.OK);
        } else {
            System.err.println(request.getMethod() + " " + request.getRequestURI());
            response = new ResponseEntity<CustomerDTO>(HttpStatus.NOT_FOUND);
            System.err.println(response);
        }
        return response;
    }

    @Override
    public ResponseEntity<List<CustomerDTO>> postCustomers(List<CustomerDTO> dtos) {
        System.err.println("POST /customers");

        if (dtos == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        List<Customer> cList = new ArrayList<Customer>();
        for (CustomerDTO dto : dtos) {
            Optional<Customer> c = dto.create();
            if (c.isPresent()) {
                Customer customer = c.get();
                cList.add(customer);
            }
        }
        repo.saveAll(cList);
        return new ResponseEntity<List<CustomerDTO>>(dtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CustomerDTO>> putCustomers(List<CustomerDTO> dtos) {
        if (dtos == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        List<Customer> cList = new ArrayList<Customer>();
        for (CustomerDTO dto : dtos) {
            Optional<Customer> c = dto.create();
            if (c.isPresent()) {
                Customer customer_new = c.get();
                Optional customer = repo.findById(customer_new.getId());
                if (customer.isPresent()) {
                    Customer current_customer = (Customer) customer.get();
                    current_customer.setId(customer_new.getId());
                    current_customer.setName(customer_new.getFirstName(), customer_new.getLastName());
                    repo.save(current_customer);
                }
            }
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteCustomer(long id) {
        System.err.println("DELETE /customers/" + id);
        if (repo.existsById(id)) {
            repo.deleteById(id);
            System.out.println("customer " + id + " deleted.");
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED); // status 202
        } else {
            System.err.println("customer " + id + " not found.");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // status 404
        }
    }


    private Optional<Customer> accept(Map<String, Object> kvpairs) {
        AtomicBoolean name = new AtomicBoolean(false);
        AtomicBoolean lastname = new AtomicBoolean(false);
        AtomicBoolean id = new AtomicBoolean(false);
        Customer acc = null;
        Set<String> keys = kvpairs.keySet();
        for (String key : kvpairs.keySet()) {
            if (key.equals("id")) {
                String idstring = kvpairs.get("id").toString();
                if (Long.parseLong(idstring) >= 0) {
                    id.set(true);
                }
            } else if (key.equals("first")) {
                name.set(true);
            } else if (key.equals("name")) {
                lastname.set(true);
            }
        }
        if (name.get() && lastname.get() && id.get()) {
            acc = new Customer();
            acc.setId(Long.parseLong(kvpairs.get("id").toString()));
            acc.setName(kvpairs.get("first").toString(), kvpairs.get("name").toString());
            if (kvpairs.containsKey("contacts")) {
                String contacts = kvpairs.get("contacts").toString();
                String[] contactlist = contacts.split(";");
                for (int i = 0; i < contactlist.length; i++) {
                    acc.addContact(contactlist[i]);
                }
            }
            return Optional.of(acc);
        }
        return Optional.empty();
    }

}
