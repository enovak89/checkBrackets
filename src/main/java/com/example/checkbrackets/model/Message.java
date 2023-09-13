package com.example.checkbrackets.model;

public class Message {
    private String text;

    private Boolean isCorrect = null;

    public void setText(String text) {
        this.text = text;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }
}
