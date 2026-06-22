package com.savarachynskyi.mycrm.services;

import com.savarachynskyi.mycrm.models.Client;
import com.savarachynskyi.mycrm.repository.ClientRepository;
import com.savarachynskyi.mycrm.repository.DealRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final DealRepository dealRepository;

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    private static final Pattern PHONE_REGEX = Pattern.compile("^\\+?[0-9]{9,13}$");

    public ClientService(ClientRepository clientRepository, DealRepository dealRepository) {
        this.clientRepository = clientRepository;
        this.dealRepository = dealRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client createClient(Client client) {
        validateClientData(client.getEmail(), client.getPhone());
        return clientRepository.save(client);
    }

    public Client updateClientPhone(Long id, String newPhone) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        validateClientData(client.getEmail(), newPhone);
        client.setPhone(newPhone);
        return clientRepository.save(client);
    }

    public Client updateClientEmail(Long id, String newEmail) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        validateClientData(newEmail, client.getPhone());
        client.setEmail(newEmail);
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        if (dealRepository.existsByClientId(id)) {
            throw new IllegalArgumentException("Cannot delete client with active deals");
        }
        clientRepository.deleteById(id);
    }

    private void validateClientData(String email, String phone) {
        if (email == null || !EMAIL_REGEX.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format! Example: user@mail.com");
        }
        if (phone == null || !PHONE_REGEX.matcher(phone).matches()) {
            throw new IllegalArgumentException("Invalid phone format! Must contain 9-13 digits. Example: +48xxxxxxxxx");
        }
    }
}