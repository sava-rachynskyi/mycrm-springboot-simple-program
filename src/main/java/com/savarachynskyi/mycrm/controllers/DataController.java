package com.savarachynskyi.mycrm.controllers;

import com.savarachynskyi.mycrm.models.Client;
import com.savarachynskyi.mycrm.models.Deal;
import com.savarachynskyi.mycrm.services.ClientService;
import com.savarachynskyi.mycrm.services.DealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
@CrossOrigin
public class DataController {

    private final ClientService clientService;
    private final DealService dealService;

    public DataController(ClientService clientService, DealService dealService) {
        this.clientService = clientService;
        this.dealService = dealService;
    }

    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping("/clients")
    public ResponseEntity<?> createClient(@RequestBody Client client) {
        try {
            return ResponseEntity.ok(clientService.createClient(client));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<?> updateClientPhone(@PathVariable Long id, @RequestBody Client clientUpdate) {
        try {
            return ResponseEntity.ok(clientService.updateClientPhone(id, clientUpdate.getPhone()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/clients/{id}/email")
    public ResponseEntity<?> updateClientEmail(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String newEmail = body.get("email");
            return ResponseEntity.ok(clientService.updateClientEmail(id, newEmail));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteClient(id);
            return ResponseEntity.ok("Client deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/deals")
    public List<Deal> getDealsByClient(@RequestParam Long clientId,
                                       @RequestParam(defaultValue = "id") String sort) {
        return dealService.getDealsByClient(clientId, sort);
    }

    @PostMapping("/deals")
    public ResponseEntity<?> createDeal(@RequestBody Map<String, Object> body) {
        try {
            Deal deal = new Deal();
            deal.setTitle((String) body.get("title"));
            deal.setDescription((String) body.get("description"));
            deal.setCategory((String) body.get("category"));
            deal.setPrice(Double.parseDouble(body.get("price").toString()));
            Long clientId = Long.parseLong(body.get("clientId").toString());
            return ResponseEntity.ok(dealService.createDeal(deal, clientId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/deals/{id}")
    public ResponseEntity<?> updateDeal(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        try {
            double price = Double.parseDouble(body.get("price").toString());
            String status = (String) body.get("status");
            return ResponseEntity.ok(dealService.updateDeal(id, price, status));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deals/{id}")
    public ResponseEntity<String> deleteDeal(@PathVariable Long id) {
        try {
            dealService.deleteDeal(id);
            return ResponseEntity.ok("Deal deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}