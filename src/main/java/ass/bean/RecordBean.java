package ass.bean;

public class RecordBean {
    private String id, recordId,status, returnDate, pickUpDate, campusName;

    public RecordBean() {
    }

    public RecordBean(String id, String status, String returnDate) {
        this.id = id;
        this.status = status;
        this.returnDate = returnDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    

    public void setPickUpDate(String pickUpDate) {
        String[] dates = pickUpDate.split(" ");
        this.pickUpDate = dates[0];
    }

    public void setReturnDate(String returnDate) {
        String[] dates = returnDate.split(" ");
        this.returnDate = dates[0];
    }
    public void setRecordId(String recordId){
        this.recordId = recordId;
    }
    public void setStatusString(String status) {
        switch (status) {
            case "pending":
                this.status= "Pending";
                break;
            case "approved":
                this.status= "Approved";
                break;
            case"waiting":
                this.status= "Waiting for delivery";
                break;

            case "pickup":
                this.status= "Item(s) ready for pick up";
                break;
            case "return":
                this.status= "Item(s) waiting for return";
                break;
            case "complete":
                this.status= "Completed";
                break;
        
            default:
                break;
        } 
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setCampusName(String campusName){
        this.campusName= campusName;
    }

    public String getId() {
        return id;
    }
    public String getRecordId(){
        return recordId;
    }
    public String getCampusName() {
        return this.campusName;
    }

    public String getStatus() {
        return status;
    }

    public String getPickUpDate() {
        return this.pickUpDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "<td>" + this.id + "</td>" +
                "<td>" + this.status + "</td>" +
                "<td>" + this.pickUpDate + "</td>" +
                "<td>" + this.returnDate + "</td>";
    }
}