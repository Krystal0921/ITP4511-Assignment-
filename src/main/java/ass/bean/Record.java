/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ass.bean;

/**
 *
 * @author LYF00
 */
public class Record {
    private String borrow_id;
    private String user_id;
    private String pick_up_date;
    private String campus_item_id;
    private String item_name;
    private String campus_id;
    private String status;
    private String campusName;

    public Record() {
       
    }

    public String getBorrow_id() {
        return borrow_id;
    }
    public String getCampusName(){
        return this.campusName;
    }
    public String getUser_id() {
        return user_id;
    }

    public String getPick_up_date() {
        return pick_up_date;
    }

    public String getCampus_item_id() {
        return campus_item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getCampus_id() {
        return campus_id;
        
    }

    public String getStatus() {
        return status;
    }
    
    
    public void setCampusName (String campusName){
        this.campusName = campusName;
    }
    public void setBorrow_id(String borrow_id) {
        this.borrow_id = borrow_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setPick_up_date(String pick_up_date) {
        this.pick_up_date = pick_up_date.split(" ")[0];
    }

    public void setCampus_item_id(String campus_item_id) {
        this.campus_item_id = campus_item_id;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setCampus_id(String campus_id) {
        this.campus_id = campus_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
