
package de.freerider.restapi.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.freerider.datamodel.Customer;


/**
 * Class for Data-Transfer Objects (DTO) for REST-endpoint: /customers
 * through which DTO are exchanged as JSON data.
 * <p>
 * Class also represents the Resource Model for the /customers endpoint.
 * <p>
 * Renders JSON for a Customer object like:
 * {@code
 * {
 * "serialnumber": 1,
 * "uuid": 12734634,
 * "time-sent": 1639502608151,
 * "customer-id": "1",
 * "customer-name": "Meyer, Eric OK",
 * "customer-contacts": "eric98@yahoo.com; (030) 7000-640000"
 * }
 * }
 *
 * @author sgra64
 */

public class CustomerDTO {

    /*
     * internal counter to create unique number for each generated DTO.
     */
    private static long serialno = 0;


    /**
     * JsonProperty, serial number incremented for each DTO.
     */
    @JsonProperty("serialnumber")
    private long serial;

    /**
     * JsonProperty, unique identifier to track DTO.
     */
    @JsonProperty("uuid")
    private long uuid;

    /**
     * JsonProperty, timestamp when DTO was sent, requires custom setter to create Date object from long value.
     */
    @JsonProperty("time-sent")
    private Date timeSent;

    /**
     * JsonProperty, id externalized as String (not long that is internally used).
     */
    @JsonProperty("customer-id")
    private String id;

    /**
     * JsonProperty, full name as externalized by getName() method.
     */
    @JsonProperty("customer-name")
    private String name;

    /**
     * JsonProperty, contacts[] flattened to ';'-separated String.
     */
    @JsonProperty("customer-contacts")
    private String contacts;


    /**
     * Public constructor to create DTO from internal object.
     *
     * @param copy internal object for which DTO is created.
     */

    public CustomerDTO(Customer copy) {
        this.id = Long.toString(copy.getId());
        this.name = copy.getName();
        StringBuffer sb = new StringBuffer();
        copy.getContacts().forEach(contact -> sb.append(sb.length() == 0 ? "" : "; ").append(contact));
        this.contacts = sb.toString();
        this.serial = serialno++;
        this.uuid = ThreadLocalRandom.current().nextInt(10000000, 100000000);
        this.timeSent = new Date();
    }


    /**
     * Non-public default constructor needed by de-serialization to create new DTO.
     */
    CustomerDTO() {
//		System.out.println( "CustomerDTO: default constructor called" );
    }


    /**
     * Public factory method to create internal object from this DTO.
     *
     * @return Optional with created internal object (or empty).
     */
    public Optional<Customer> create() {
        return createValidated();
    }

    private Optional<Customer> createValidated() {


        boolean validated = true;
        String reason = null;
        try {
            if (Long.parseLong(this.id) < 0) {
                reason = "Id is smaller than 0.";
                validated = false;
            }
        } catch (Exception e) {
            reason = "Id could not be parsed.";
            validated = false;
        }
        if(this.serial < 0 ) {
            reason = "Serialnr is smaller than 0";
            validated = false;
        }
        if(this.uuid < 0) {
            reason = "UUID is smaller than 0";
            validated = false;
        }
        if(this.name == null) {
            reason = "Name is null.";
            validated = false;
        }
        if (validated) {
            return create_();
        } else {
            System.err.println("Error: invalid JSON object rejected. Reason: " + reason);
            return Optional.empty();
        }
    }

    private Optional<Customer> create_() {
        //
        // TODO: check validity of attributes before creating Customer object
        Customer customer = null;
        try {
            //
            long idL = Long.parseLong(this.id);
            customer = new Customer()
                    .setId(idL)
                    .setName(this.name);

            for (String contact : contacts.toString().split(";")) {
                String contactr = contact.trim();
                if (contactr.length() > 0) {
                    customer.addContact(contactr);
                }
            }
            return Optional.ofNullable(customer);
            //
        } catch (Exception e) {

            customer = null;
        }
        return Optional.ofNullable(customer);
    }


    /**
     * Custom setter to create Date object from long value.
     *
     */
    @JsonProperty("time-sent")
    public long getTimestamp() {
        return this.timeSent.getTime();
    }


    /**
     * Custom setter to create Date object from long value.
     *
     * @param timestamp found in JSON as long value.
     */
    @JsonProperty("time-sent")
    public void setTimestamp(long timestamp) {
        this.timeSent = new Date(timestamp);
    }


    /*
     * Convenience methods.
     */

    public void print() {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd, HH:mm:ss.SSS").format(timeSent);
        System.out.println("Customer-DTO: " +
                "serialnumber: " + serial + ", " +
                "uuid: " + uuid + ", " +
                "customer-id: \"" + id + "\", " +
                "customer-name: \"" + name + "\", " +
                "customer-contacts: \"" + contacts + "\", " +
                "time-sent: \"" + timeStamp + "\""
        );
    }

    public static void print(Optional<Customer> opt) {
        if (opt.isPresent()) {
            Customer customer = opt.get();
            StringBuffer sb = new StringBuffer();
            customer.getContacts().forEach(contact ->
                    sb.append(", contact: \"").append(contact).append("\"")
            );
            //
            System.out.println("Customer-OBJ: " +
                    "id: \"" + customer.getId() + "\", " +
                    "lastName: \"" + customer.getLastName() + "\", " +
                    "firstName: \"" + customer.getFirstName() + "\", " +
                    "contactsCount: " + customer.contactsCount() + sb.toString()
            );
        } else {
            System.out.println("Customer-OBJ: empty.");
        }
    }

}
