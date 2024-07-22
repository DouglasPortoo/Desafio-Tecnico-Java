package com.estagiojava.eclipsehotel.service;

import java.util.UUID;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estagiojava.eclipsehotel.model.Customer;
import com.estagiojava.eclipsehotel.repository.CustomerRepository;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  public List<Customer> findAll() {
    return customerRepository.findAll();
  }

  public Customer findById(UUID id) {
    return customerRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Customer not found"));
  }

  public Customer save(Customer customer) {
    return customerRepository.save(customer);
  }

  public Customer update(UUID id, Customer customer) {
    Customer customerToUpdate = findById(id);

    customerToUpdate.setName(customer.getName());
    customerToUpdate.setEmail(customer.getEmail());
    customerToUpdate.setPhone(customer.getPhone());

    return customerRepository.save(customerToUpdate);
  }

  public void deleteById(UUID id) {
    customerRepository.deleteById(id);
  }
}
