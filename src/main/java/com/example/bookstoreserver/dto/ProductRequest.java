package com.example.bookstoreserver.dto;

import com.example.bookstoreserver.model.Category;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ProductRequest {
    private String name;
    private String description;
    private Double price;
    private String image;
    private Integer quantity;
    private Long categoryId;
    private Long publisherId;
    private Long authorId;


    public ProductRequest(String name, String description, Double price, String image, Integer quantity, Long categoryId, Long publisherId, Long authorId) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.publisherId= publisherId;
        this.authorId = authorId;

    }

    public ProductRequest() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
