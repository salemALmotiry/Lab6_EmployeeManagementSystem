package com.example.lab6_employeemanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

@Data
@AllArgsConstructor
public class Employee {

    @NotEmpty(message = "Enter your name")
    @Size(min = 2 , message = "ID must be longer than 2 characters")
    private String ID;

    @NotEmpty(message = "Enter your name")
    @Size(min = 4 , message = "Name length must be longer than 4 characters")
    @Pattern(regexp = "^[A-Za-z]+ [A-Za-z]+$", message = "Name must valid name first and last name is required. No numbers allow")
    private String name;

    @NotEmpty(message = "Enter your email")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotEmpty(message = "Phone number cannot be empty")
    @Size(min = 10 , max = 14 , message = "phone number must be 10 digit ")
    @Pattern(regexp = "^(009665|9665|\\+9665|05|5)(5|0|3|6|4|9|1|8|7)([0-9]{7})$", message = "Phone number must be:\n" +
            "1- Start with one of thees : 009665|9665|+9665|05|5)\n" +
            "2- Validate that the contry code is for Saudi Arabia: \n   0, 5, 3 : STC prefix\n   6, 4 : Mobily prefix\n   9, 8 : Zain prefix,\n   7 : MVNO prefix (Virgin and Lebara)\n   1 : Bravo prefix\n" +
            "e.g 0505330609")
    private String phoneNumber;

    @NotNull(message = "Enter your name")
    @Positive(message = "Positive only")
    @Range(min = 25,max = 75 , message = "age must be between 25 and 75 ")
    private int age;

    @NotEmpty(message = "position cannot be empty")
    @Pattern(regexp = "^(supervisor|coordinator)$", message = "Position must be either supervisor or coordinator only")
    private String position;


    @AssertFalse(message = "onLeave must be initially set to false")
    private boolean onLeave;

    @NotNull(message = "cannot be empty")
    @PastOrPresent(message = "hire date must be past or present date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date hireDate;

    @NotNull(message = "Annual leave cannot be empty")
    @Positive(message = "Positive only")
    private int annualLeave;

}
