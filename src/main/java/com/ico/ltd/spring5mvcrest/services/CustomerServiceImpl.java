package com.ico.ltd.spring5mvcrest.services;

import com.ico.ltd.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.ico.ltd.spring5mvcrest.api.v1.model.CustomerDTO;
import com.ico.ltd.spring5mvcrest.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper mapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = mapper.convertToCustomerDTO(customer);
                    customerDTO.setUrl("/api/v1/customers/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findById(Long id) {
        return mapper.convertToCustomerDTO(customerRepository.findById(id).orElseThrow(RuntimeException::new));
    }
}
