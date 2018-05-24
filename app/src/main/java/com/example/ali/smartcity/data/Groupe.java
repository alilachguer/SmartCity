package com.example.ali.smartcity.data;

import java.util.List;

public class Groupe {
    public String nom;

    public String id;

    public Posts posts;

    public Membres membres;

    public Membres getMembres() {
        return membres;
    }

    public Groupe() {
        this.posts = new Posts();
        this.membres = new Membres();
    }

    public void setMembres(Membres membres) {
        this.membres = membres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Posts getPosts() {

        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


}
