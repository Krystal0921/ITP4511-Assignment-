/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ass.bean;

/**
 *
 * @author LYF00
 */
public class User {

    private String user_id;
    private String username;
    private String password;
    private String role;
    private String campus_id;
    private String campus_name;
    private String active;

    public User() {
    }

    public User(String user_id, String username, String password, String role, String campus_id, String campus_name, String active) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.campus_id = campus_id;
        this.campus_name = campus_name;
        this.active = active;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getCampus_id() {
        return campus_id;
    }

    public String getCampus_name() {
        return campus_name;
    }

    public String getActive() {
        return active;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCampus_id(String campus_id) {
        this.campus_id = campus_id;
    }

    public void setCampus_name(String campus_name) {
        this.campus_name = campus_name;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
