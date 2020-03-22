package com.lucaxiang.models;


import org.bson.Document;
import org.bson.types.ObjectId;

public class User {
    String _id="";
    String username;
    String password;
    String name;
    String phone;
    boolean admin = false;
    public User()
    {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    public Document toDocument()
    {
        return new Document("username", this.getUsername())
                    .append("password", this.getPassword())
                    .append("name",     this.getName())
                    .append("phone",    this.getPhone())
                    .append("admin",    this.isAdmin());
    }

    public static User fromDocument(Document document)
    {
        User user = new User();
        user.set_id(document.getObjectId("_id").toString());
        user.setUsername(document.getString("username"));
        user.setPassword(document.getString("password"));
        user.setName(document.getString("name"));
        user.setPhone(document.getString("phone"));
        user.setAdmin(document.getBoolean("admin"));
        return  user;
    }

}
