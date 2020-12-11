package com.example.indexing;

public class OwnerData {
    private String id;
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "OwnerData{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
