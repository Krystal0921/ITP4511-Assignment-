/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ass.bean;

import java.io.Serializable;

/**
 *
 * @author LYF00
 */
public class Item implements Serializable {
    private String item_id;
    private String item_name;
    private String item_type;
    private String userType;
    private String active;

    public Item() {
        this.item_id = "";
        this.item_name = "";
        this.item_type = "";
        this.userType = "";
        this.active = "";
    }

    public Item(String item_id, String item_name, String item_type, String userType, String active) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_type = item_type;
        this.userType = userType;
        this.active = active;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_type() {
        return item_type;
    }

    public String getUserType() {
        return userType;
    }

    public String getActive() {
        return active;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
