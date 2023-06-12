package com.example.bookstoreserver.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderRequest {
    @NotBlank(message = "Address should´t be null or empty")
    private String address;
    @NotBlank(message = "Phone should´t be null or empty")
    private String phone;
    @NotNull(message = "Total price should´t be null or empty")
    private double totalPrice;


    public OrderRequest(String address, String phone, double totalPrice) {
        this.address = address;
        this.phone = phone;
        this.totalPrice = totalPrice;

    }

    public OrderRequest() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


}
