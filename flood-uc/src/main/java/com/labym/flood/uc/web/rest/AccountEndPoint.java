package com.labym.flood.uc.web.rest;

import com.labym.flood.common.exception.FloodErrorUtils;
import com.labym.flood.common.exception.FloodException;
import com.labym.flood.uc.config.Constants;
import com.labym.flood.uc.model.dto.UserDTO;
import com.labym.flood.uc.model.vm.RegistrationVM;
import com.labym.flood.uc.repository.UserRepository;
import com.labym.flood.uc.service.UserService;
import com.labym.flood.uc.util.UserUtils;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

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
    public  ResponseEntity registerAccount(@Valid @RequestBody RegistrationVM vm) {
        if (StringUtils.isEmpty(vm.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        if (!UserUtils.checkPassword(vm.getPassword())) {
            return ResponseEntity.badRequest().build();
        }

        UserDTO userDTO = userService.register(vm.getEmail(), vm.getPassword());
        return ResponseEntity.created(URI.create(Constants.Api.USER_API+"/"+userDTO.getId())).build();
    }

    @GetMapping("/activate")
    @Timed
    public void activateAccount(@RequestParam(value = "key") String key) {
        if (!userService.activateRegistration(key).isPresent()) {
            throw new FloodException(FloodErrorUtils.notExists("not found activationKey(%s)",key));
        }
    }

    @GetMapping("/authenticate")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }



}
