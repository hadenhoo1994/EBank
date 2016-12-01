package app.ebank.com.ebank.model;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * 账单类
 * Created by Haden on 2016/10/10.
 */
public class Bill extends BmobObject {
    private String type;    //账单种类     true为支出 false为收入
    private String money;   //金额
    private String balance; //余额
    private String username;  //账单所属用户
    private Date time;    //表的创建时间

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
