package com.ico.ltd.spring5mvcrest.services;

import com.ico.ltd.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.ico.ltd.spring5mvcrest.api.v1.model.CustomerDTO;
import com.ico.ltd.spring5mvcrest.controllers.v1.CustomerController;
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
                    customerDTO.setUrl(getCustomerUrl(customer.getId()));
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    private String getCustomerUrl(Long id) {
        return CustomerController.BASE_URL + "/" + id;
    }

    @Override
    public CustomerDTO findById(Long id) {
        return mapper.customerToCustomerDTO(customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        return saveAndReturn(mapper.customerDTOtoCustomer(customerDTO));
    }

    private CustomerDTO saveAndReturn(Customer customer) {
        Customer updatedCustomer = customerRepository.save(customer);
        CustomerDTO customerDTO = mapper.customerToCustomerDTO(updatedCustomer);
        customerDTO.setUrl(getCustomerUrl(updatedCustomer.getId()));
        return customerDTO;
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = mapper.customerDTOtoCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturn(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {

            if (customerDTO.getFirstName() != null) {
                customer.setFirstName(customerDTO.getFirstName());
            }

            if (customerDTO.getLastName() != null) {
                customer.setLastName(customerDTO.getLastName());
            }

            CustomerDTO returnedDTO = mapper.customerToCustomerDTO(customerRepository.save(customer));
            returnedDTO.setUrl(getCustomerUrl(id));

            return returnedDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

}
