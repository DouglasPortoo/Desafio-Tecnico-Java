package com.estagiojava.eclipsehotel.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estagiojava.eclipsehotel.model.Customer;
import com.estagiojava.eclipsehotel.service.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
  
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<Object> getAllCustomers() {
        logger.info("Fetching all customers");
        try {
            var result = customerService.findAll();
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            logger.error("Error fetching all customers: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable UUID id) {
        logger.info("Fetching customer with id: {}", id);
        try {
            var result = customerService.findById(id);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            logger.error("Error fetching customer with id {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
        logger.info("Creating new customer");
        try {
            var result = customerService.save(customer);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            logger.error("Error creating customer: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable UUID id, @RequestBody Customer customerDetails) {
        logger.info("Updating customer with id: {}", id);
        try {
            var result = customerService.update(id, customerDetails);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            logger.error("Error updating customer with id {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable UUID id) {
        logger.info("Deleting customer with id: {}", id);
        try {
            customerService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting customer with id {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
