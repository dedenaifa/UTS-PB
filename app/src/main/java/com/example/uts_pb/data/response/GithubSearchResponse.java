package com.example.uts_pb.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GithubSearchResponse {
    @SerializedName("items")
    private List<GithubUser> users; // Mengubah tipe data menjadi List<GithubUser>

    public List<GithubUser> getUsers() {
        return users;
    }

    public void setUsers(List<GithubUser> users) {
        this.users = users;
    }
}
