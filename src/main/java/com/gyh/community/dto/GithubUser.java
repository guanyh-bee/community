package com.gyh.community.dto;

/**
 * @author gyh
 * @create 2020-07-06 16:26
 */
public class GithubUser {
    private Integer id;
    private String name;
    private String bio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
