package com.clearsolutions.users.controller;

import com.clearsolutions.users.model.DtoResponse;
import com.clearsolutions.users.model.DtoUser;
import com.clearsolutions.users.service.IUserService;
import com.clearsolutions.users.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService service;

    @GetMapping
    private ResponseEntity<DtoResponse> findUsers(@RequestParam("dateFrom") LocalDate dateFrom,
                                                  @RequestParam("dateTo") LocalDate dateTo) {
        if (dateTo.isBefore(dateFrom)) {
            throw new IllegalArgumentException("Date to cannot be less than date from");
        }
        List<User> users = service.getUsersByDateRange(dateFrom, dateTo);
        return ResponseEntity.ok(new DtoResponse(users));
    }

    @PostMapping
    private ResponseEntity<Void> createUser(@RequestBody @Valid User user) {
        String userEmail = service.createUser(user);
        return ResponseEntity.created(URI.create("/users/" + userEmail)).build();
    }

    @PutMapping("/{email}")
    private ResponseEntity<Void> updateUser(@RequestBody @Valid User user, @PathVariable("email") @Email String email) {
        service.updateUser(user, email);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{email}")
    private ResponseEntity<Void> partialUpdateUser(@RequestBody DtoUser dtoUser,
                                                   @PathVariable("email") @Email String email) {
        service.partialUpdateUser(dtoUser, email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{email}")
    private ResponseEntity<Void> deleteUser(@PathVariable("email") @Email String email) {
        service.deleteUser(email);
        return ResponseEntity.ok().build();
    }
}
