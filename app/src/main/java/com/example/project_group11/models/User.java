
// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.models;

import java.io.Serializable;

public class User {
    private String userEmail;
    private String password;
    private String nickname;
    private String phoneNumber;

    public User() {
    }

    public User(String userEmail, String password, String nickname, String phoneNumber) {
        this.userEmail = userEmail;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "userEmail='" + userEmail + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
