package com.example.elasticsearch.pojo;


/**
 * @author wangpeil
 */


public class User {
    private int age;
    private String gender;

    public User(int age, String gender) {
        this.age = age;
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return this.age;
    }

    public String getGender() {
        return this.gender;
    }
}
