package app.ebank.com.ebank.model;

import android.content.Intent;
import android.graphics.Point;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Haden on 2016/10/3.
 */
public class UserMsg extends BmobUser {
    private String trueName;    //用户真实姓名
    private String idCard;      //用户身份证号码
    private Boolean frozen;     //用户冻结状态
    private String buyPsw;      //支付密码
    private String bankCard;        //用户银行卡号码
    private String address;         //用户住址
    private Double balance;         //用户余额

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }


    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public String getBuyPsw() {
        return buyPsw;
    }

    public void setBuyPsw(String buyPsw) {
        this.buyPsw = buyPsw;
    }
}