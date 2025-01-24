package com.cosmo.my_auth_service.services;

import com.cosmo.my_auth_service.dto.AccessTokenDTO;
import com.cosmo.my_auth_service.dto.AuthenticationDTO;
import com.cosmo.my_auth_service.dto.RegisterDTO;
import com.cosmo.my_auth_service.entities.Role;
import com.cosmo.my_auth_service.entities.User;
import com.cosmo.my_auth_service.infra.security.TokenService;
import com.cosmo.my_auth_service.repositories.RoleRepository;
import com.cosmo.my_auth_service.repositories.UserRepository;
import com.cosmo.my_auth_service.services.exceptions.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

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
}
