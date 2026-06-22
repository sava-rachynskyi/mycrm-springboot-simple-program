package com.savarachynskyi.mycrm;

import com.savarachynskyi.mycrm.models.Client;
import com.savarachynskyi.mycrm.repository.ClientRepository;
import com.savarachynskyi.mycrm.services.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void shouldThrowExceptionWhenEmailIsInvalid() {
        Client client = new Client();
        client.setFirstName("Ivan");
        client.setLastName("Ivanov");
        client.setEmail("invalid-email-no-at.com");
        client.setPhone("+48123456789");

        assertThrows(IllegalArgumentException.class, () -> {
            clientService.createClient(client);
        });
    }

    @Test
    public void shouldSaveClientWhenDataIsValid() {
        Client client = new Client();
        client.setFirstName("Ivan");
        client.setLastName("Ivanov");
        client.setEmail("ivan@mail.com");
        client.setPhone("+48123456789");

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client savedClient = clientService.createClient(client);

        assertNotNull(savedClient);
        verify(clientRepository, times(1)).save(client);
    }
}