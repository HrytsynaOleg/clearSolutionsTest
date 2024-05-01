package com.clearsolutions.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DtoUser {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
