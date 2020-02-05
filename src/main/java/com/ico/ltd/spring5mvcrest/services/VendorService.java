package com.ico.ltd.spring5mvcrest.services;


import com.ico.ltd.spring5mvcrest.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {

    List<VendorDTO> getVendors();

    VendorDTO findById(Long id);

    VendorDTO saveVendor(VendorDTO vendorDTO);

    VendorDTO updateVendor(Long id, VendorDTO vendorDTO);

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

    void deleteVendorById(Long id);
}
