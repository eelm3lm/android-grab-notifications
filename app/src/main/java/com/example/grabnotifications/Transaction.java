package com.example.grabnotifications;

public class Transaction {
    private String account;
    private String amount;
    private String body;
    private String card;
    private String category;
    private String date;
    private String payee;
    private String status;

    public Transaction (String account, String amount, String body, String card, String category, String date, String payee, String status) {
        this.account = account;
        this.amount = amount;
        this.body = body;
        this.card = card;
        this.category = category;
        this.date = date;
        this.payee = payee;
        this.status = status;
    }

    public Transaction (String date, String category, String payee) {
        this.date = date;
        this.category = category;
        this.payee = payee;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
