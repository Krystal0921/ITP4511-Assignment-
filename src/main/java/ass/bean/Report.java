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
public class Report implements Serializable{
    private String report_id;
    private String item_id;
    private String description;
    private String user_id;
    private String datetime;
    private String status;
    private String item_name;
  

    public Report() {
    }

    public Report(String report_id, String item_id, String description, String user_id, String datetime, String status, String item_name) {
        this.report_id = report_id;
        this.item_id = item_id;
        this.description = description;
        this.user_id = user_id;
        this.datetime = datetime;
        this.status = status;
        this.item_name = item_name;
        
        
    }

    
    public String getReport_id() {
        return report_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getDescription() {
        return description;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getStatus() {
        return status;
    }

    public String getItem_name() {
        return item_name;
    }
    
    
    
    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
    
    
}
