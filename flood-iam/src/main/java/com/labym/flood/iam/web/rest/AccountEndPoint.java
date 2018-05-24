package com.labym.flood.iam.web.rest;

import com.labym.flood.iam.model.vm.RegistrationVM;
import com.labym.flood.iam.repository.UserRepository;
import com.labym.flood.iam.service.UserService;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
public class AccountEndPoint {
    private final UserService userService;
    private final UserRepository userRepository;

    public AccountEndPoint(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody RegistrationVM vm) {
        userService.register(vm.getEmail(),vm.getPassword());
    }

    @GetMapping("/activate")
    @Timed
    public void activateAccount(@RequestParam(value = "key") String key) {

    }

    @GetMapping("/authenticate")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }



}
