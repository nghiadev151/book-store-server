package com.example.bookstoreserver.dto.order;

public class OrderRequest {
    private String address;
    private String phone;
    private double totalPrice;
    private Long userId;

    public OrderRequest(String address, String phone, double totalPrice, Long userId) {
        this.address = address;
        this.phone = phone;
        this.totalPrice = totalPrice;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
