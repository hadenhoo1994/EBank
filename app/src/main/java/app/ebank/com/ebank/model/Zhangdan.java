package app.ebank.com.ebank.model;

/**
 * Created by Haden on 2016/7/11.
 */
public class Zhangdan {

    private String zhangdan_time;

    private String zhangdan_type;

    private String zhangdan_sum;

    public Zhangdan(String zhagndan_time, String zhangdan_type, String zhangdan_sum){
        this.zhangdan_time = zhagndan_time;
        this.zhangdan_type = zhangdan_type;
        this.zhangdan_sum = zhangdan_sum;
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
}
