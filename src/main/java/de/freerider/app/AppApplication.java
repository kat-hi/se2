package de.freerider.app;

import de.freerider.datamodel.*;
import de.freerider.repository.CustomerRepository;
import de.freerider.repository.ReservationRepository;
import de.freerider.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;

// tell Spring where to scan for @Repository's
@EnableJpaRepositories(basePackages = {"de.freerider.repository"})
// tell Spring where to scan for @Entity's
@EntityScan(basePackages = {"de.freerider.datamodel"})
// tell Spring where to scan for @Components, @Controllers, @Services
@SpringBootApplication
@ComponentScan(
        basePackages = {"de.freerider.restapi", "de.freerider.repository"}
)
public class AppApplication {
    @Autowired  // Spring auto‐wires reference to CustomerRepository instance
    public CustomerRepository customerRepository;

    @Autowired
    public ReservationRepository reservationRepository;

    @Autowired
    public VehicleRepository vehicleRepository;

    public static void main(String[] args) {
        System.out.println("Hello, freerider.de");
        SpringApplication.run(AppApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterSpringStartup() {       // runs when Spring is ready
        Customer eric = customerRepository.save(new Customer()
                .setId(1).setName("Eric", "Meyer").addContact("eric98@yahoo.com")
                .addContact("(030) 7000‐640000")    // updated phone number
        );
        Customer anne = customerRepository.save(new Customer()
                .setId(2).setName("Anne", "Bayer").addContact("anne24@yahoo.de")
                .addContact("(030) 3481‐23352")
        );
        Customer tim = customerRepository.save(new Customer()
                .setId(3).setName("Tim", "Schulz‐Mueller").addContact("tim2346@gmx.de")
        );
        Customer gustav = customerRepository.save(new Customer()
                .setId(4).setName("Gustav", "Meierhuber").addContact("gustav@gmx.de")
        );


        Vehicle v1 = new Vehicle(476562, "Tesla", "Model 3", "red")
                .setPower(Power.electric)
                .setPassengers(4);
        Vehicle v2 = new Vehicle(934773, "Tesla", "Model 3", "white")
                .setPower(Power.electric)
                .setPassengers(4);
        Vehicle v3 = new Vehicle(347456, "Tesla", "Model X", "silver")
                .setPower(Power.electric)
                .setPassengers(4);
        Vehicle v4 = new Vehicle(126467, "BMW", "320i", "blue");
        Vehicle v5 = new Vehicle(356714, "BMW", "320i", "silver");
        Vehicle v6 = new Vehicle(247245, "BMW", "320d", "red").setPower(Power.diesel);
        Vehicle v7 = new Vehicle(247245, "VW", "ID Buzz", "red").setPower(Power.electric).setPassengers(8);
        Vehicle v8 = new Vehicle(247245, "VW", "ID Buzz", "yellow").setPower(Power.electric).setPassengers(8);

		vehicleRepository.saveAll( Arrays.asList( v1, v2, v3, v4, v5, v6, v7, v8 ) );

        Reservation r1 = new Reservation(239862, eric, v1)
                .pickup(LocCode.BTH11, "Feb 18, 2022 10:00 UTC+0100")
                .dropoff(LocCode.BER, "Feb 22, 2022 20:00 UTC+0100");
        Reservation r2 = new Reservation(262510, eric, v2)
                .pickup(LocCode.MUC, "Feb 18, 2022 10:00 UTC+0100")
                .dropoff(LocCode.MUC, "Feb 22, 2022 20:00 UTC+0100");
        Reservation r3 = new Reservation(342514, tim, v7)
                .pickup(LocCode.BENT02, "Jun 12, 2022 08:00 UTC+0100")
                .dropoff(LocCode.BER, "Jun 30, 2022 22:30 UTC+0100");
        Reservation r4 = new Reservation(651230, anne, v8)
                .pickup(LocCode.HAM, "Apr 17, 2022 06:00 UTC+0100")
                .dropoff(LocCode.HAM, "Apr 18, 2022 23:00 UTC+0100");
        Reservation r5 = new Reservation(635621, anne, v4)
                .pickup(LocCode.BENT02, "Dec 20, 2022 10:00 UTC+0100")
                .dropoff(LocCode.BTH11, "Jan 02, 2023 20:00 UTC+0100");

        reservationRepository.saveAll( Arrays.asList( r1, r2, r3, r4, r5));
        long count = customerRepository.count();
        System.out.println("repository<Customer> with: " + count + " entries");
    }
}