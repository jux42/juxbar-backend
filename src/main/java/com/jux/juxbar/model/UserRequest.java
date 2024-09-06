package com.jux.juxbar.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRequest implements Serializable {
    private String username;

}
