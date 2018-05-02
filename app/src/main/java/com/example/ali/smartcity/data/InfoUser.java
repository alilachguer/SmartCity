package com.example.ali.smartcity.data;

import android.os.Parcel;
import android.os.Parcelable;

public class InfoUser implements Parcelable{
    public String username, e_mail, password, city, gender, height, weight, date;

    public InfoUser() {}

    public InfoUser(String e_mail, String password, String username){
        this.e_mail = e_mail;
        this.password = password;
        this.username = username;
    }

    public InfoUser(Parcel parcel){
        this.username = parcel.readString();
        this.e_mail = parcel.readString();
        this.password = parcel.readString();
        this.city = parcel.readString();
        this.gender = parcel.readString();
        this.height = parcel.readString();
        this.weight = parcel.readString();
        this.date = parcel.readString();
    }

    public static final Parcelable.Creator<InfoUser> CREATOR = new Parcelable.Creator<InfoUser>() {
        @Override
        public InfoUser createFromParcel(Parcel source) {
            return new InfoUser(source);
        }

        @Override
        public InfoUser[] newArray(int size)
        {
            return new InfoUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(e_mail);
        parcel.writeString(password);
        parcel.writeString(city);
        parcel.writeString(gender);
        parcel.writeString(height);
        parcel.writeString(weight);
        parcel.writeString(date);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
