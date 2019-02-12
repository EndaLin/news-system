package domain;

public class Users {
    private String status; // 地位
    private String account; // 帐户名
    private String sex; // 性别
    private String ischeck; // 账号状态
    private String path;

    public Users() {
    }

    public Users(String status, String account, String sex, String ischeck) {
        super();
        this.status = status;
        this.account = account;
        this.sex = sex;
        this.ischeck = ischeck;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }


}
