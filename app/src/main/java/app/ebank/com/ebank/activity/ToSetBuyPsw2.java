package app.ebank.com.ebank.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.ebank.com.ebank.R;
import app.ebank.com.ebank.model.UserMsg;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Haden on 2016/10/24.
 */
public class ToSetBuyPsw2 extends Activity {
    private EditText oldpassword;   //旧密码输入框
    private EditText set_buypsw;   //新密码输入框
    private EditText set_buypsw2;   //新密码确认输入框
    private Button submit_buypsw;      //确认按钮
    private Button back;
    private TextView forget;  //  忘记密码按钮
    private static String old;
    private static String psw1;
    private static String psw2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setbuypsw2);


        //      第一：默认初始化Bmob
        Bmob.initialize(this, "34bf3494f1223137a33e3c73d6be754b");

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //实例化控件
        oldpassword = (EditText) findViewById(R.id.oldpassword);
        set_buypsw = (EditText) findViewById(R.id.set_buypsw);
        set_buypsw2 = (EditText) findViewById(R.id.set_buypsw2);
        submit_buypsw = (Button) findViewById(R.id.sbuypsw);
        forget= (TextView) findViewById(R.id.forgotbpwtext);
        //忘记密码
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ToSetBuyPsw2.this,ForgetPsw_activity.class);
                startActivity(i);
                finish();
            }
        });

        submit_buypsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old = oldpassword.getText().toString().trim();
                psw1 = set_buypsw.getText().toString().trim();
                psw2 = set_buypsw2.getText().toString().trim();
                //查询用户并判断用户输入的旧密码是否正确
                if (old.length() == 6) {
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    BmobQuery<UserMsg> query = new BmobQuery<UserMsg>();
                    query.getObject(bmobUser.getObjectId(), new QueryListener<UserMsg>() {
                        @Override
                        public void done(UserMsg userMsg, BmobException e) {
                            if (e == null) {
                                //查询成功 判断用户输入的旧密码是否正确
                                if (userMsg.getBuyPsw().equals(old)) {
                                    //旧密码正确
                                    //判断两次密码输入是否相同
                                    if (psw1.equals(psw2)) {
                                        //判断密码长度是否为6位
                                        if (psw1.toString().length() == 6) {
                                            //输入的信息都正确 修改密码信息
                                            UserMsg user = new UserMsg();
                                            user.setBuyPsw(psw1);
                                            //更新信息
                                            user.update(userMsg.getObjectId(), new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if (e == null) {
                                                        Toast.makeText(ToSetBuyPsw2.this, "支付密码修改成功", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    } else {
                                                        Toast.makeText(ToSetBuyPsw2.this, "支付密码修改失败,请检查网络是否正常", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(ToSetBuyPsw2.this, "密码长度必须为6位数字", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(ToSetBuyPsw2.this, "两次输入的密码不相同,请重新输入", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(ToSetBuyPsw2.this, "支付密码错误", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                //密码错误
                                Toast.makeText(ToSetBuyPsw2.this, "获取信息失败,请检查网络是否正常", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(ToSetBuyPsw2.this, "请输入6位的支付密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
