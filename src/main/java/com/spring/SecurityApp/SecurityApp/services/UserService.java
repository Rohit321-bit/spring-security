package com.spring.SecurityApp.SecurityApp.services;

import com.spring.SecurityApp.SecurityApp.dto.LoginDTO;
import com.spring.SecurityApp.SecurityApp.dto.SignUpDTO;
import com.spring.SecurityApp.SecurityApp.dto.UserDTO;
import com.spring.SecurityApp.SecurityApp.entities.User;
import com.spring.SecurityApp.SecurityApp.exceptions.ResourceNotFoundException;
import com.spring.SecurityApp.SecurityApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()-> new BadCredentialsException("User with this email is not present!"));
    }
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User with id "+id+" not exist!")
        );
    }
    public UserDTO signUp(SignUpDTO signUpDTO) {
        Optional<User> user=userRepository.findByEmail(signUpDTO.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User is already present with !"+signUpDTO.getEmail());
        }
        User toSaved=modelMapper.map(signUpDTO,User.class);
        toSaved.setPassword(passwordEncoder.encode(toSaved.getPassword()));
        User savedUser=userRepository.save(toSaved);
        return modelMapper.map(savedUser,UserDTO.class);
    }


}
