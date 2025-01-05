package com.gamebox.dto;

import java.util.Date;

public class CartItemDTO {
    private int cartId;          // 장바구니 항목 ID
    private int gameId;          // 게임 ID
    private String gameTitle;    // 게임 제목
    private double gamePrice;    // 게임 가격
    private int quantity;        // 수량
    private Date addedDate;      // 추가된 날짜
    private String imagePath;    // 게임 이미지 경로

    // Getter와 Setter
    public int getCartId() {
        return cartId;
    }
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
    public int getGameId() {
        return gameId;
    }
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    public String getGameTitle() {
        return gameTitle;
    }
    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }
    public double getGamePrice() {
        return gamePrice;
    }
    public void setGamePrice(double gamePrice) {
        this.gamePrice = gamePrice;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Date getAddedDate() {
        return addedDate;
    }
    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
