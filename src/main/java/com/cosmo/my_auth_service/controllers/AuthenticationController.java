package com.cosmo.my_auth_service.controllers;

import com.cosmo.my_auth_service.dto.*;
import com.cosmo.my_auth_service.services.AuthorizationService;
import com.cosmo.my_auth_service.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO dto){
        userService.register(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recover-token")
    public ResponseEntity<Void> createRecoverToken(@RequestBody @Valid EmailRecoveryDTO dto){
        userService.createRecoverToken(dto);
        return null;
    }

    @PutMapping(value = "/new-password")
    public ResponseEntity<Void> saveNewPassword(@RequestBody @Valid NewPasswordDto dto){
        userService.saveNewPassword(dto);
        return ResponseEntity.noContent().build();
    }



}
