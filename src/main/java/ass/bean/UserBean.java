package ass.bean;

public class UserBean {
    private String role;
    private String id;

    public UserBean(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return this.role;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserID : " + this.id + "\n"
                + "Role : " + this.role +"\n";
    }

}
