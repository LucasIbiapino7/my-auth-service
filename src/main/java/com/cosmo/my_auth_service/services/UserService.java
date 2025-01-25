package com.cosmo.my_auth_service.services;

import com.cosmo.my_auth_service.dto.*;
import com.cosmo.my_auth_service.entities.PasswordRecover;
import com.cosmo.my_auth_service.entities.Role;
import com.cosmo.my_auth_service.entities.User;
import com.cosmo.my_auth_service.infra.security.TokenService;
import com.cosmo.my_auth_service.repositories.PasswordRecoverRepository;
import com.cosmo.my_auth_service.repositories.RoleRepository;
import com.cosmo.my_auth_service.repositories.UserRepository;
import com.cosmo.my_auth_service.services.exceptions.EmailExistsException;
import com.cosmo.my_auth_service.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String recoverUri;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailServiceClient emailServiceClient;

    @Autowired
    private PasswordRecoverRepository recoverRepository;

    @Transactional
    public AccessTokenDTO login(AuthenticationDTO dto) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmail().toLowerCase(), dto.getPassword());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) auth.getPrincipal());
        return new AccessTokenDTO(token);
    }

    @Transactional
    public void register(RegisterDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail().toLowerCase());
        if (user != null){
            throw new EmailExistsException("O email já existe");
        }
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        User newUser = new User();
        newUser.setEmail(dto.getEmail().toLowerCase());
        newUser.setPassword(hashPassword);

        Role role = roleRepository.getReferenceById(1L);

        newUser.addRole(role);

        userRepository.save(newUser);
    }

    @Transactional
    public void createRecoverToken(EmailRecoveryDTO dto) {
        User user = userRepository.findByEmail(dto.email());
        if (user == null) {
            throw new ResourceNotFoundException("Email Não Encontrado");
        }

        String token = UUID.randomUUID().toString();
        PasswordRecover entity = new PasswordRecover();
        entity.setEmail(dto.email());
        entity.setToken(token);
        entity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));
        entity = recoverRepository.save(entity);

        EmailMicroserviceRequestDTO request = new EmailMicroserviceRequestDTO();
        String body = "Acesse o Link para definir sua nova senha\n\n" + recoverUri + token;
        request.setBody(body);
        request.setTo(dto.email());
        request.setSubject("Recuperação de Senha");

        emailServiceClient.sendEmail(request);
    }


    @Transactional
    public void saveNewPassword(NewPasswordDto dto) {
        List<PasswordRecover> result = recoverRepository.searchValidTokens(dto.getToken(), Instant.now());
        if (result.isEmpty()){
            throw new ResourceNotFoundException("Token Inválido");
        }
        User user = userRepository.findByEmail(result.get(0).getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user = userRepository.save(user);
    }
}
