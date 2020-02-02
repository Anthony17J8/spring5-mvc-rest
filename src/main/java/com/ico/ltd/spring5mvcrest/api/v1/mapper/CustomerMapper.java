package com.ico.ltd.spring5mvcrest.api.v1.mapper;

import com.ico.ltd.spring5mvcrest.api.v1.model.CustomerDTO;
import com.ico.ltd.spring5mvcrest.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO convertToCustomerDTO(Customer customer);
}
