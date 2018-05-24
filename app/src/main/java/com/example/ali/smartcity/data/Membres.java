package com.example.ali.smartcity.data;

import java.util.ArrayList;
import java.util.List;

public class Membres {
    public List<String> users;

    public Membres() {
        this.users = new ArrayList<String>();
    }

    public List<String> getUsers() {

        return users;
    }

    public void setUsers(List<String> email) {
        this.users = email;
    }
}
