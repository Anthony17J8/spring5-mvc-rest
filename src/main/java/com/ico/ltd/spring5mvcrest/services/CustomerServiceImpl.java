package com.ico.ltd.spring5mvcrest.services;

import com.ico.ltd.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.ico.ltd.spring5mvcrest.api.v1.model.CustomerDTO;
import com.ico.ltd.spring5mvcrest.domain.Customer;
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
                    CustomerDTO customerDTO = mapper.customerToCustomerDTO(customer);
                    customerDTO.setUrl("/api/v1/customers/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findById(Long id) {
        return mapper.customerToCustomerDTO(customerRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = mapper.customerDTOtoCustomer(customerDTO);

        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO resultDTO = mapper.customerToCustomerDTO(savedCustomer);

        resultDTO.setUrl("/api/v1/customers/" + savedCustomer.getId());
        return resultDTO;
    }
}
