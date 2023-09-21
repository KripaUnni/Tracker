package avantech.tracker.model;

public class SchoolModel {
    String name;
    String schoolId;
    String email;
    String pass;
    String userType;
    public SchoolModel() {
    }

    public SchoolModel(String name, String schoolId, String email, String pass, String userType){
        this.name = name;
        this.schoolId = schoolId;
        this.email = email;
        this.pass = pass;
        this.userType = userType;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSchoolId() {
        return schoolId;
    }
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
}
