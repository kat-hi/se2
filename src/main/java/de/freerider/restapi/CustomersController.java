package de.freerider.restapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.freerider.datamodel.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class CustomersController implements CustomersAPI {
    @Autowired
    private ApplicationContext context;
    @Autowired
//    CustomerRepository repo = new CustomerRepository();
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    /**
     * Constructor.
     *
     * @param objectMapper entry point to JSON tree for the Jackson library
     * @param request      HTTP request object
     */
    public CustomersController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<List<?>> getCustomers() {
        ResponseEntity<List<?>> response = null;
        System.err.println(request.getMethod() + " " + request.getRequestURI());
        try {
            ArrayNode arrayNode = peopleAsJSON();
            ObjectReader reader = objectMapper.readerFor(new TypeReference<List<ObjectNode>>() {
            });
            List<String> list = reader.readValue(arrayNode);
            response = new ResponseEntity<List<?>>(list, HttpStatus.OK);

        } catch (IOException e) {
            response = new ResponseEntity<List<?>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @Override
    public ResponseEntity<?> getCustomer(long id) {
        ResponseEntity<List<?>> response = null;
        System.err.println(request.getMethod() + " " + request.getRequestURI());
        try {
            ArrayNode arrayNode = customerAsJSON(id);
            ObjectReader reader = objectMapper.readerFor(new TypeReference<List<ObjectNode>>() {
            });
            List<String> list = reader.readValue(arrayNode);
            response = new ResponseEntity<List<?>>(list, HttpStatus.OK);
            if (list.size() == 0) {
                System.err.println(request.getMethod() + " " + request.getRequestURI());
                response = new ResponseEntity<List<?>>(HttpStatus.NOT_FOUND);
                System.err.println(response);
            }
        } catch (IOException e) {
            response = new ResponseEntity<List<?>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
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
                for(int i = 0; i < contactlist.length; i++) {
                    acc.addContact(contactlist[i]);
                }
            }
            return Optional.of(acc);
        }
        return Optional.empty();
    }

    @Override
    public ResponseEntity<List<?>> postCustomers(Map<String, Object>[] jsonMap) {
        System.err.println("POST /customers");

        if (jsonMap == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        for (Map<String, Object> kvpairs : jsonMap) {
            for (String key : kvpairs.keySet()) {
                Optional<Customer> c = accept(kvpairs);
                if(c.isPresent()) {
//                    repo.save(c.get());
                    return new ResponseEntity<>(null, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<List<?>> putCustomers(Map<String, Object>[] jsonMap) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteCustomer(long id) {
        System.err.println("DELETE /customers/" + id);
//        if (repo.existsById(id)) {
//            repo.deleteById(id);
//            System.out.println("customer " + id + " deleted.");
//            return new ResponseEntity<>(null, HttpStatus.ACCEPTED); // status 202
//        } else {
//            System.err.println("customer " + id + " not found.");
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // status 404
//        }
        return new ResponseEntity<>("200", HttpStatus.ACCEPTED);
    }


    private ArrayNode customerAsJSON(long id) {
        ArrayNode arrayNode = objectMapper.createArrayNode();
//        Optional<Customer> c = repo.findById(id);
//        c.ifPresent(customer -> toJson(customer, arrayNode));
        return arrayNode;
    }

    private ArrayNode peopleAsJSON() {
        ArrayNode arrayNode = objectMapper.createArrayNode();
//        Iterable<Customer> cList = repo.findAll();
//        cList.forEach(c -> {
//            toJson(c, arrayNode);
//        });
        return arrayNode;
    }

    private void toJson(Customer c, ArrayNode arrayNode) {
        StringBuffer sb = new StringBuffer();
        c.getContacts().forEach(contact -> sb.append(sb.length() == 0 ? "" : "; ").append(contact));
        arrayNode.add(
                objectMapper.createObjectNode()
                        .put("Id", c.getId())
                        .put("name", c.getLastName())
                        .put("first", c.getFirstName())
                        .put("contact", sb.toString())
        );
    }
}
