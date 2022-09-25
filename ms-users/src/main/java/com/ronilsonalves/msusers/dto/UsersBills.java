package com.ronilsonalves.msusers.dto;

import com.ronilsonalves.msusers.model.Bill;
import com.ronilsonalves.msusers.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class UsersBills {

    private User user;
    private List<Bill> bills;
}
