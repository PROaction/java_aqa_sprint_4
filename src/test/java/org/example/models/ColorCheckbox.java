package org.example.models;


public class ColorCheckbox {
    private String id;
    private String value;

    public ColorCheckbox(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}