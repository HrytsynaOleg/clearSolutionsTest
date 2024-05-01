package com.clearsolutions.users.model;

import com.clearsolutions.users.validator.AgeConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @NotBlank(message = "Field must be not empty")
    private String firstName;

    @NotBlank(message = "Field must be not empty")
    private String lastName;

    @Email(message = "Email format is incorrect")
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth must be in past")
    @AgeConstraint
    private LocalDate birthDate;

    private String address;
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName)
                && Objects.equals(email, user.email) && Objects.equals(birthDate, user.birthDate)
                && Objects.equals(address, user.address) && Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, birthDate, address, phoneNumber);
    }
}
