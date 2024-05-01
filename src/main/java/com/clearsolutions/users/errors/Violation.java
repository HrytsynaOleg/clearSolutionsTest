package com.clearsolutions.users.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Violation {
    private final int status;
    private final String detail;
}
