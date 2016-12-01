package app.ebank.com.ebank.model;

/**
 * Created by Haden on 2016/7/11.
 */
public class Zhangdan {

    private String zhangdan_time;

    private String zhangdan_type;

    private String zhangdan_sum;

    private String zhangdan_balance;

    public Zhangdan(String zhagndan_time, String zhangdan_type, String zhangdan_sum,String zhangdan_balance){
        this.zhangdan_time = zhagndan_time;
        this.zhangdan_type = zhangdan_type;
        this.zhangdan_sum = zhangdan_sum;
        this.zhangdan_balance = zhangdan_balance;
    }

    public String getZhangdan_time(){
        return zhangdan_time;
    }

    public String getZhangdan_type(){
        return zhangdan_type;
    }

    public String getZhangdan_sum(){
        return zhangdan_sum;
    }

    public String getZhangdan_balance(){
        return zhangdan_balance;
    }
}
