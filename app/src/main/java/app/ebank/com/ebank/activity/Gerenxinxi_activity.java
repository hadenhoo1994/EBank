package app.ebank.com.ebank.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.annotation.Target;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import app.ebank.com.ebank.R;
import app.ebank.com.ebank.model.BankCard;
import app.ebank.com.ebank.model.UserMsg;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Haden on 2016/7/11.
 */
public class Gerenxinxi_activity extends Activity {
    private TextView zhanghaotext;//用户账号
    private TextView trueNametext;  //真实姓名
    private TextView userTypetext;  //账号状态
    private TextView bankCardtext; //绑定的银行卡信息
    private TextView idCardtext;    //绑定的身份证卡号
    private TextView phoneNumbertext;   //手机号码
    private TextView addresstext;       //住址
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gerenxinxi);
        //      第一：默认初始化Bmob
        Bmob.initialize(this, "34bf3494f1223137a33e3c73d6be754b");
        //实例化控件
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        idCardtext = (TextView) findViewById(R.id.idCardtext);
        phoneNumbertext = (TextView) findViewById(R.id.phoneNumbertext);
        zhanghaotext = (TextView) findViewById(R.id.zhanghaotext);
        trueNametext = (TextView) findViewById(R.id.trueNametext);
        userTypetext = (TextView) findViewById(R.id.userTypetext);
        bankCardtext = (TextView) findViewById(R.id.bankCardtext);
        addresstext = (TextView) findViewById(R.id.addresstext);

        //显示信息
        //获取本地用户的objectId来获取user表内的信息
        final BmobUser user = BmobUser.getCurrentUser();
        BmobQuery<BankCard> query = new BmobQuery<BankCard>();
        query.addWhereEqualTo("phoneNumber",user.getUsername());
        query.findObjects(new FindListener<BankCard>() {
            @Override
            public void done(List<BankCard> list, BmobException e) {
                if (e ==null){
                    BankCard bankCard = list.get(0);
                    idCardtext.setText(bankCard.getIdCard());
                    phoneNumbertext.setText(bankCard.getPhoneNumber());
                    zhanghaotext.setText(bankCard.getPhoneNumber());
                    trueNametext.setText(bankCard.getUsername());
                    bankCardtext.setText(bankCard.getBankCard());
                    addresstext.setText(bankCard.getAddress());
                    //判断用户状态显示不同状态信息
                    BmobQuery<UserMsg> query1 = new BmobQuery<UserMsg>();
                    query1.addWhereEqualTo("username",bankCard.getPhoneNumber());
                    query1.findObjects(new FindListener<UserMsg>() {
                        @Override
                        public void done(List<UserMsg> list, BmobException e) {
                            if (e ==null){
                                UserMsg userMsg = list.get(0);
                                //判断用户状态显示不同状态信息
                                if (userMsg.getFrozen() == true) {
                                    userTypetext.setText("冻结状态");
                                } else {
                                    userTypetext.setText("正常");
                                }
                            }else{
                                Toast.makeText(Gerenxinxi_activity.this, "状态异常" + e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(Gerenxinxi_activity.this, "用户信息获取失败,请检查网络是否连接", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
