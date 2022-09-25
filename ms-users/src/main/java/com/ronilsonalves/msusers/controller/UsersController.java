package com.ronilsonalves.msusers.controller;

import com.ronilsonalves.msusers.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getAllUsers());
    }

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserById (@PathVariable String userId) throws UsernameNotFoundException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usersService.getUserById(userId));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/bills")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getBillsByUserId (@PathVariable String userId) throws UsernameNotFoundException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usersService.getBillsByUserId(userId));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/allBills")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> allBills() {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getAllBills());
    }

}
