package com.ronilsonalves.msusers.repository;

import com.ronilsonalves.msusers.model.Bill;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-bills")
public interface IBillsFeignRepository {

    @GetMapping("/bills/billsByUserId/{userId}")
    List<Bill> getBillsByUserId(@PathVariable String userId);

    @GetMapping("/bills/all")
    List<Bill> getAll();
}
