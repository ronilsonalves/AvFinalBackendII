package com.msbills.service;

import com.msbills.exceptions.BillNotFoundException;
import com.msbills.models.Bill;
import com.msbills.repositories.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository repository;

    public List<Bill> getAllBill() {
        return repository.findAll();
    }

    public List<Bill> getAllBillsByCustomerId (String customerId) {
        return repository.findBillsByCustomerBill(customerId);
    }

    public Bill saveBill(Bill bill) {
        return repository.save(bill);
    }

    public Bill getBillById(String id) {
        if(repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new BillNotFoundException("A bill record with this ID "+id+" was not found!");
        }
    }

    public void deleteBillById(String id) {
        repository.deleteById(id);
    }

}
