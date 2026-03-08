package com.fresh.ecommerce.service;

import com.fresh.ecommerce.dto.AddressDTO;
import com.fresh.ecommerce.entity.Address;
import com.fresh.ecommerce.entity.User;
import com.fresh.ecommerce.exception.ResourceNotFoundException;
import com.fresh.ecommerce.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;

    public List<AddressDTO.Response> getUserAddresses(Long userId) {
        return addressRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AddressDTO.Response createAddress(Long userId, AddressDTO.CreateRequest request) {
        User user = userService.getUserById(userId);
        
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            addressRepository.clearDefaultByUserId(userId);
        }
        
        Address address = Address.builder()
                .user(user)
                .receiverName(request.getReceiverName())
                .receiverPhone(request.getReceiverPhone())
                .province(request.getProvince())
                .city(request.getCity())
                .district(request.getDistrict())
                .detailAddress(request.getDetailAddress())
                .postalCode(request.getPostalCode())
                .isDefault(request.getIsDefault() != null ? request.getIsDefault() : false)
                .build();
        
        return toResponse(addressRepository.save(address));
    }

    @Transactional
    public AddressDTO.Response updateAddress(Long userId, Long addressId, AddressDTO.UpdateRequest request) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("地址不存在"));
        
        if (!address.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("地址不存在");
        }
        
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            addressRepository.clearDefaultByUserId(userId);
        }
        
        if (request.getReceiverName() != null) {
            address.setReceiverName(request.getReceiverName());
        }
        if (request.getReceiverPhone() != null) {
            address.setReceiverPhone(request.getReceiverPhone());
        }
        if (request.getProvince() != null) {
            address.setProvince(request.getProvince());
        }
        if (request.getCity() != null) {
            address.setCity(request.getCity());
        }
        if (request.getDistrict() != null) {
            address.setDistrict(request.getDistrict());
        }
        if (request.getDetailAddress() != null) {
            address.setDetailAddress(request.getDetailAddress());
        }
        if (request.getPostalCode() != null) {
            address.setPostalCode(request.getPostalCode());
        }
        if (request.getIsDefault() != null) {
            address.setIsDefault(request.getIsDefault());
        }
        
        return toResponse(addressRepository.save(address));
    }

    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("地址不存在"));
        
        if (!address.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("地址不存在");
        }
        
        addressRepository.delete(address);
    }

    public Address getAddressById(Long userId, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("地址不存在"));
        
        if (!address.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("地址不存在");
        }
        
        return address;
    }

    private AddressDTO.Response toResponse(Address address) {
        AddressDTO.Response response = new AddressDTO.Response();
        response.setId(address.getId());
        response.setReceiverName(address.getReceiverName());
        response.setReceiverPhone(address.getReceiverPhone());
        response.setProvince(address.getProvince());
        response.setCity(address.getCity());
        response.setDistrict(address.getDistrict());
        response.setDetailAddress(address.getDetailAddress());
        response.setPostalCode(address.getPostalCode());
        response.setIsDefault(address.getIsDefault());
        return response;
    }
}
