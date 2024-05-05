package com.burak.project.request;

import lombok.Builder;
import lombok.Data;

@Data
public class StudentRequest {
    private String username;
    private String password;
    private Double balance;
}
