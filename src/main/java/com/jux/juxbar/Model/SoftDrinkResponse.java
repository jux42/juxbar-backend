package com.jux.juxbar.Model;

import lombok.Data;

import java.util.List;

@Data
public class SoftDrinkResponse {
    private List<SoftDrink> drinks;
    private SoftDrink drink;
}
