package com.ico.ltd.spring5mvcrest.services;


import com.ico.ltd.spring5mvcrest.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getCustomers();

    CustomerDTO findById(Long id);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);
}
