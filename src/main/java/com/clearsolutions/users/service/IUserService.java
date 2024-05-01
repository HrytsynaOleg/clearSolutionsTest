package com.clearsolutions.users.service;

import com.clearsolutions.users.model.DtoUser;
import com.clearsolutions.users.model.User;

import java.time.LocalDate;
import java.util.List;

import java.util.NoSuchElementException;

public interface IUserService {
    String createUser(User user);

    void updateUser(User user, String email) throws NoSuchElementException;

    void partialUpdateUser(DtoUser user, String email) throws NoSuchElementException;

    void deleteUser(String email) throws NoSuchElementException;

    List<User> getUsersByDateRange(LocalDate from, LocalDate to);
}
