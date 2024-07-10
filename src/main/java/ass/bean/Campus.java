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
public class Campus implements Serializable{
    private String campus_id;
    private String campus_name;
    private String campus_locate;

    public Campus() {
    }

    public String getCampus_id() {
        return campus_id;
    }

    public String getCampus_name() {
        return campus_name;
    }

    public String getCampus_locate() {
        return campus_locate;
    }

    public void setCampus_id(String campus_id) {
        this.campus_id = campus_id;
    }

    public void setCampus_name(String campus_name) {
        this.campus_name = campus_name;
    }

    public void setCampus_locate(String campus_locate) {
        this.campus_locate = campus_locate;
    }
    
    
    
    
}
