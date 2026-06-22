package com.savarachynskyi.mycrm;

import com.savarachynskyi.mycrm.models.Client;
import com.savarachynskyi.mycrm.models.Deal;
import com.savarachynskyi.mycrm.repository.ClientRepository;
import com.savarachynskyi.mycrm.repository.DealRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class MyCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyCrmApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(ClientRepository clientRepository, DealRepository dealRepository) {
        return args -> {
            if (clientRepository.count() == 0) {
                Client client1 = new Client();
                client1.setFirstName("John");
                client1.setLastName("Doe");
                client1.setEmail("john.doe@example.com");
                client1.setPhone("+48690144245");
                clientRepository.save(client1);

                Client client2 = new Client();
                client2.setFirstName("Alex");
                client2.setLastName("Smith");
                client2.setEmail("alex.smith@example.com");
                client2.setPhone("+48780678903");
                clientRepository.save(client2);

                Deal deal1 = new Deal();
                deal1.setTitle("Software Design");
                deal1.setDescription("Initial architecture layout");
                deal1.setCategory("IT Consulting");
                deal1.setPrice(1500.0);
                deal1.setStatus("IN_PROGRESS");
                deal1.setCreatedAt(LocalDate.now());
                deal1.setClient(client1);
                dealRepository.save(deal1);

                Deal deal2 = new Deal();
                deal2.setTitle("Legacy Website Support");
                deal2.setDescription("Old Java project maintenance");
                deal2.setCategory("Support");
                deal2.setPrice(450.0);
                deal2.setStatus("IN_PROGRESS");
                deal2.setCreatedAt(LocalDate.now().minusDays(40));
                deal2.setClient(client1);
                dealRepository.save(deal2);

                System.out.println("data successfully loaded into H2 database!");
            }
        };
    }
}
