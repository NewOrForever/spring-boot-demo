package com.example.boot;

/**
 * ClassName:UserBean
 * Package:com.example.boot
 * Description:
 *
 * @Date:2021/11/26 16:43
 * @Author:qs@1.com
 */
public class UserBean {

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
