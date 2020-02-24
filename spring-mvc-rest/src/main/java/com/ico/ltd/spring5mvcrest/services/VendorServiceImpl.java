package com.ico.ltd.spring5mvcrest.services;

import com.ico.ltd.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.ico.ltd.spring5mvcrest.api.v1.model.VendorDTO;
import com.ico.ltd.spring5mvcrest.controllers.v1.VendorController;
import com.ico.ltd.spring5mvcrest.domain.Vendor;
import com.ico.ltd.spring5mvcrest.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    private final VendorMapper mapper;

    @Autowired
    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper mapper) {
        this.vendorRepository = vendorRepository;
        this.mapper = mapper;
    }

    @Override
    public List<VendorDTO> getVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = mapper.vendorToVendorDTO(vendor);
                    vendorDTO.setUrl(getVendorId(vendor.getId()));
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    private String getVendorId(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }

    @Override
    public VendorDTO findById(Long id) {
        VendorDTO vendorDTO = mapper.vendorToVendorDTO(vendorRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
        vendorDTO.setUrl(VendorController.BASE_URL + '/' + id);
        return vendorDTO;
    }

    @Override
    public VendorDTO saveVendor(VendorDTO vendorDTO) {
        return saveAndReturn(mapper.vendorDTOToVendor(vendorDTO));
    }

    private VendorDTO saveAndReturn(Vendor vendor) {
         Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO vendorDTO = mapper.vendorToVendorDTO(savedVendor);
        vendorDTO.setUrl(getVendorId(savedVendor.getId()));
        return vendorDTO;
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor = mapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);
        return saveAndReturn(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {

            if (vendorDTO.getName() != null) {
                vendor.setName(vendor.getName());
            }

            VendorDTO returned = mapper.vendorToVendorDTO(vendorRepository.save(vendor));
            returned.setUrl(getVendorId(vendor.getId()));
            return returned;

        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
