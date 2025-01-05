package com.gamebox.dto;

import java.time.LocalDateTime;

public class PaymentDTO {
    private int paymentId;        // 결제 ID (PK)
    private int userId;           // 사용자 ID
    private double amount;        // 결제 금액
    private String status;        // 결제 상태 (PENDING, SUCCESS, FAILED 등)
    private LocalDateTime createdAt; // 결제 생성 시간

    // 기본 생성자
    public PaymentDTO() {}

    // 모든 필드를 사용하는 생성자
    public PaymentDTO(int paymentId, int userId, double amount, String status, LocalDateTime createdAt) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // toString 메서드 (디버깅용)
    @Override
    public String toString() {
        return "PaymentDTO{" +
                "paymentId=" + paymentId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
