package com.ronilsonalves.msusers.repository;

import com.ronilsonalves.msusers.model.User;

import java.util.List;

public interface IUserRepository {

    User findUserById(String id);

    List<User> getAllUsers();
}
