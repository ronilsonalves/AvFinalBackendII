package com.msbills.controller;

import com.msbills.exceptions.BillNotFoundException;
import com.msbills.models.Bill;
import com.msbills.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bills")
public class BillController {

    private final BillService service;

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Bill>> getAll() {
        return ResponseEntity.ok().body(service.getAllBill());
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getBillById(@PathVariable String id) throws BillNotFoundException {
        try {
            return ResponseEntity.ok().body(service.getBillById(id));
        } catch (BillNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/billsByUserId/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Bill>> getBillsByUserId(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllBillsByCustomerId(userId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_PROVIDER')")
    public ResponseEntity<?> save(@RequestBody Bill bill) {
        return ResponseEntity.ok().body(service.saveBill(bill));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_PROVIDER')")
    public ResponseEntity<?> delete(@PathVariable String id) throws BillNotFoundException {
        try {
            service.deleteBillById(id);
            return ResponseEntity.ok().body("Bill deleted successfully");
        } catch (BillNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
