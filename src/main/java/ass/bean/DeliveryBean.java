package ass.bean;

public class DeliveryBean {
    private String id, status, campus, itemName;

    public DeliveryBean() {
    }

    // setter
    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCampus(String desCampus) {
        this.campus = desCampus;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    // getter
    public String getId() {
        return this.id;
    }

    public String getStatus() {
        return this.status;
    }

    public String getCampus() {
        return this.campus;
    }

    public String getItemName() {
        return this.itemName;
    }

    

}
