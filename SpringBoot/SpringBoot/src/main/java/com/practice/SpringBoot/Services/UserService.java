package com.practice.SpringBoot.Services;

import com.practice.SpringBoot.Dto.securitydto.LoginDto;
import com.practice.SpringBoot.Dto.securitydto.SignUpDto;
import com.practice.SpringBoot.Dto.securitydto.UserDto;
import com.practice.SpringBoot.entity.User;
import com.practice.SpringBoot.exception.ResourceNotFoundException;
import com.practice.SpringBoot.reposistory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow( ()-> new ResourceNotFoundException("User not found"));
    }

    public UserDto signUp(SignUpDto signUpDto) {
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
        if (user.isPresent()) {
            throw new BadCredentialsException("User with email already exists" + signUpDto.getEmail());
        }

        User createUser = modelMapper.map(signUpDto, User.class);
        createUser.setPassword(passwordEncoder.encode(createUser.getPassword()));
        User savedUser = userRepository.save(createUser);
        return modelMapper.map(savedUser, UserDto.class);
    }


}
