package com.cosmo.my_auth_service.services;


import com.cosmo.my_auth_service.dto.EmailMicroserviceRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Email-service",
        url = "http://localhost:8090")
public interface EmailServiceClient {

    @PostMapping("/emails/recover-password")
    void sendEmail(@RequestBody EmailMicroserviceRequestDTO emailRequest);
}
