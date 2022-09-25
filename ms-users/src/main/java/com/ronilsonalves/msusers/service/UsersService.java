package com.ronilsonalves.msusers.service;

import com.ronilsonalves.msusers.dto.UsersBills;
import com.ronilsonalves.msusers.model.Bill;
import com.ronilsonalves.msusers.model.User;
import com.ronilsonalves.msusers.repository.IBillsFeignRepository;
import com.ronilsonalves.msusers.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final IUserRepository userRepository;

    private final IBillsFeignRepository billsFeignRepository;

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserById(String userId) {
        if(userRepository.findUserById(userId) != null) {
            return userRepository.findUserById(userId);
        } else throw new UsernameNotFoundException("An user with this ID: "+userId+" was not found!");
    }

    public UsersBills getBillsByUserId(String userId) {
        User userToSave = userRepository.findUserById(userId);
        List<Bill> bills = this.billsFeignRepository.getBillsByUserId(userId);

        if(userToSave != null) {
            return new UsersBills(userToSave,bills);
        } else throw new UsernameNotFoundException("Was not possible to find bills from an user with this ID: "+userId+", please" +
                " check the userId and try again!");
    }

    public List<Bill> getAllBills() {
        return billsFeignRepository.getAll();
    }
}
