package com.labym.flood.iam.web.rest;

import com.labym.flood.iam.model.po.UserPO;
import com.labym.flood.iam.model.vm.LoginVM;
import com.labym.flood.iam.model.vm.TokenVO;
import com.labym.flood.iam.repository.UserRepository;
import com.labym.flood.iam.security.jwt.JWTConfigurer;
import com.labym.flood.iam.security.jwt.TokenProvider;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TokenEndpoint {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    public TokenEndpoint(TokenProvider tokenProvider, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<TokenVO> authorize(@Valid @RequestBody LoginVM loginVM) throws UsernameNotFoundException {


        Optional<UserPO> userOptional = userRepository.findUserByLogin(loginVM.getUsername());
        return userOptional.map(user ->
                authenticate(loginVM,user.getSalt())
        ).orElseThrow(()->new UsernameNotFoundException("can't find User by login("+loginVM.getUsername()+")"));

        //.orElse(ResponseEntity.notFound().build());




    }

    private ResponseEntity<TokenVO> authenticate(LoginVM loginVM, String salt) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword()+salt);

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new TokenVO(jwt), httpHeaders, HttpStatus.OK);
    }
}
