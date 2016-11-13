package app.ebank.com.ebank.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import app.ebank.com.ebank.R;
import app.ebank.com.ebank.model.Bill;
import app.ebank.com.ebank.model.UserMsg;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Haden on 2016/10/3.
 */
public class ToSetBuyPsw extends Activity {
    private EditText set_buypsw;    //支付密码输入框
    private EditText set_buypsw2;   //二次确认输入框
    private Button submit_buypsw;   //提交按钮
    private Button back;
    private static String psw1;
    private static String psw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setpsw);

        //      第一：默认初始化Bmob
        Bmob.initialize(this, "34bf3494f1223137a33e3c73d6be754b");

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        set_buypsw = (EditText) findViewById(R.id.set_buypsw);
        set_buypsw2 = (EditText) findViewById(R.id.set_buypsw2);
        submit_buypsw = (Button) findViewById(R.id.submit_buypsw);
        submit_buypsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psw1 = set_buypsw.getText().toString().trim();
                psw2 = set_buypsw2.getText().toString().trim();
                //判断两次密码输入是否相同
                if (psw1.equals(psw2)) {
                    //判断密码长度是否为6位
                    if (psw1.toString().length() == 6) {
                        setBuyPsw();
                    } else {
                        Toast.makeText(ToSetBuyPsw.this, "密码长度必须为6位数字", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ToSetBuyPsw.this, "两次输入的密码不相同,请重新输入", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //设置支付密码
    private void setBuyPsw() {
        final Intent intent = getIntent();
        String phoneNumber = intent.getExtras().getString("phoneNumber");
        String password = intent.getExtras().getString("password");
        UserMsg userMsg = new UserMsg();
        userMsg.setUsername(phoneNumber);
        userMsg.setPassword(password);
        userMsg.setFrozen(false);
        userMsg.setMobilePhoneNumber(phoneNumber);
        userMsg.setBuyPsw(psw1);
        userMsg.signUp(new SaveListener<UserMsg>() {
            @Override
            public void done(UserMsg s, BmobException e) {
                if (e == null) {
                    Intent intent1 = new Intent(ToSetBuyPsw.this, Index_activity.class);
                    startActivity(intent1);
                    Toast.makeText(ToSetBuyPsw.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ToSetBuyPsw.this, "用户已存在,请前往登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
