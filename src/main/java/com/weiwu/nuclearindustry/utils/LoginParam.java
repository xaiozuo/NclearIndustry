package com.weiwu.nuclearindustry.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginParam implements Serializable {
    private String phoneNumber;
    private String password;
}
