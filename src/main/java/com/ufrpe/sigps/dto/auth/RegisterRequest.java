// src/main/java/com/ufrpe/sigps/dto/auth/RegisterRequest.java
package com.ufrpe.sigps.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private LocalDate dateBirth;
    private String cpf;
    private String nationality;
    private String address;

    private String course;
    private String department;
}

//eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhbHZhcm9AdGVzdC5jb20iLCJpYXQiOjE3NTIzNTU3MTksImV4cCI6MTc1MjQ0MjExOX0.0ZEx4oZmgKLCj0jNdqj7AbmU5vyJ6ldx7yftpSeKi2ooNbV_HhRWRPCeWKYBs6l3