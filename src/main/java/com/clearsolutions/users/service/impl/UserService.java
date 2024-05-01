package com.clearsolutions.users.service.impl;

import com.clearsolutions.users.errors.UserNotFoundException;
import com.clearsolutions.users.model.DtoUser;
import com.clearsolutions.users.service.IUserService;
import com.clearsolutions.users.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Override
    public String createUser(User user) {
        return "";
    }

    @Override
    public void updateUser(User user, String email) {
    }

    @Override
    public void partialUpdateUser(DtoUser user, String email) throws UserNotFoundException {

    }

    @Override
    public void deleteUser(String email) throws UserNotFoundException {

    }

    @Override
    public List<User> getUsersByDateRange(LocalDate from, LocalDate to) {
        return List.of();
    }
}
