package com.cosmo.my_auth_service.controllers;

import com.cosmo.my_auth_service.dto.AccessTokenDTO;
import com.cosmo.my_auth_service.dto.AuthenticationDTO;
import com.cosmo.my_auth_service.dto.RegisterDTO;
import com.cosmo.my_auth_service.services.AuthorizationService;
import com.cosmo.my_auth_service.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenDTO> login(@RequestBody @Valid AuthenticationDTO dto){
        AccessTokenDTO result = userService.login(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO dto){
        userService.register(dto);
        return ResponseEntity.ok().build();
    }

}
