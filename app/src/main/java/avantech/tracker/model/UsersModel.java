package avantech.tracker.model;

public class UsersModel {

    String userType;
    String schoolId;
    String emailId;
    String pass;

    public UsersModel() {
    }

    public UsersModel(String emailId, String pass, String userType, String schoolId){
        this.emailId = emailId;
        this.pass = pass;
        this.userType = userType;
        this.schoolId = schoolId;
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

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
