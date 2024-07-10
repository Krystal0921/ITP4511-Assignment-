package ass.bean;

public class ItemBean {
    private String id, name, type, cItemId, campusId;
    private boolean isAva, inCampus;

    public ItemBean() {
    }

    // constructor
    public ItemBean(String id, String campus_item_id, boolean isAva) {
        this.id = id;
        this.cItemId = campus_item_id;
        this.isAva = isAva;
    }

    public ItemBean(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public ItemBean(String id, String name, String type, boolean isAva) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isAva = isAva;
    }

    public ItemBean(String id, String name, String type, boolean isAva, boolean inCampus) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isAva = isAva;
        this.inCampus = inCampus;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getCItemId() {
        return this.cItemId;
    }

    public boolean getIsAva() {
        return this.isAva;
    }
    public String getCampusId() {
        return this.campusId;
    }

    public boolean getInCampus() {
        return this.inCampus;
    }

    public void setInCampus(String[] detail) {
        this.campusId = detail[0];
        this.inCampus = detail[1].equals("true");
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCItemId(String cItemId) {
        this.cItemId = cItemId;
    }

    public void setIsAva(boolean isAva) {
        this.isAva = isAva;
        if (!isAva)
            this.cItemId = null;
    }

    @Override
    public String toString() {
        return "<td>" + this.name + "</td>" +
                "<td>" + this.type + "</td>";
    }
}
