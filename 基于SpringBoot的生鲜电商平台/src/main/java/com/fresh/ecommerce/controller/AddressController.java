package com.fresh.ecommerce.controller;

import com.fresh.ecommerce.dto.AddressDTO;
import com.fresh.ecommerce.entity.User;
import com.fresh.ecommerce.service.AddressService;
import com.fresh.ecommerce.service.UserService;
import com.fresh.ecommerce.vo.ResultVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;

    @GetMapping
    public ResultVO<List<AddressDTO.Response>> getAddresses(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResultVO.success(addressService.getUserAddresses(user.getId()));
    }

    @PostMapping
    public ResultVO<AddressDTO.Response> createAddress(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody AddressDTO.CreateRequest request) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResultVO.success(addressService.createAddress(user.getId(), request));
    }

    @PutMapping("/{id}")
    public ResultVO<AddressDTO.Response> updateAddress(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @Valid @RequestBody AddressDTO.UpdateRequest request) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResultVO.success(addressService.updateAddress(user.getId(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResultVO<Void> deleteAddress(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        addressService.deleteAddress(user.getId(), id);
        return ResultVO.success("删除成功");
    }
}
