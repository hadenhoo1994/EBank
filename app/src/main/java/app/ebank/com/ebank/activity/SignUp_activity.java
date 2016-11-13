package app.ebank.com.ebank.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.ebank.com.ebank.R;
import app.ebank.com.ebank.model.User;
import app.ebank.com.ebank.model.UserMsg;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Haden on 2016/9/24.
 */
public class SignUp_activity extends Activity implements View.OnClickListener {
    private Button back;    //返回按钮
    private EditText setPassWord;   //输入密码
    private EditText setPassWord2;  //确认密码
    private Button submit_SignUp;   //提交按钮
    private static String name = null;
    private static String password = null;
    private static String password2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);
        //顶栏功能
        // 返回
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//      第一：默认初始化
        Bmob.initialize(this, "34bf3494f1223137a33e3c73d6be754b");
        initialize();
    }

    //初始化控件
    private void initialize() {
        setPassWord = (EditText) findViewById(R.id.password);
        setPassWord2 = (EditText) findViewById(R.id.password2);
        submit_SignUp = (Button) findViewById(R.id.next);
        submit_SignUp.setOnClickListener(this);

    }

    //SignUp按钮监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                password = setPassWord.getText().toString();
                password2 = setPassWord2.getText().toString();
//                Toast.makeText(SignUp_activity.this, name+","+password+"," +password2+","+Emial, Toast.LENGTH_SHORT).show()

                //判断密码是否为空
                if (password != null || password2 != null) {
                    //判断两次输入的密码是否相同
                    if (password.equals(password2)) {
                        //判断密码长度是否为大于8位
                        if (password.length() >= 8) {
                            //注册事件
                            signUpData();
                            //Toast.makeText(this, "注册成功.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "请输入一个8-16位的密码", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "两次密码不相同,请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }


                break;

        }
    }

    //   注册
    private void signUpData() {
        // Toast.makeText(SignUp_activity.this, name+","+password+"," +password2+",", Toast.LENGTH_SHORT).show();
        final Intent intent = getIntent();
        final String phoneNumber = intent.getExtras().getString("phoneNumber");
//        //在user表创一个新的用户行
//        BmobUser bmobUser = new BmobUser();
//        bmobUser.setUsername(phoneNumber);
//        bmobUser.setPassword(password);
//        bmobUser.setMobilePhoneNumber(phoneNumber);
//        bmobUser.signUp(new SaveListener<BmobUser>() {
//            @Override
//            public void done(BmobUser s, BmobException e) {
//                if(e==null){
//                    Intent intent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putString("phoneNumber", phoneNumber);
                                bundle.putString("password",password);
                                intent.setClass(SignUp_activity.this, ToSetBuyPsw.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
//                }else{
//                    Toast.makeText(SignUp_activity.this, "用户已存在,请登录"+e, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        BmobQuery<UserMsg> query = new BmobQuery<UserMsg>();
//        query.addWhereEqualTo("username", phoneNumber);
//        query.findObjects(new FindListener<UserMsg>() {
//            @Override
//            public void done(List<UserMsg> list, BmobException e) {
//                if (e == null) {
//                    //获取用户成功
//                    UserMsg user = list.get(0);
//                    //设置密码
//                    UserMsg userMsg = new UserMsg();
//                    userMsg.setPassword(password);
//                    //更新信息
//                    userMsg.update(user.getObjectId(), new UpdateListener() {
//                        @Override
//                        public void done(BmobException e) {
//                            if (e == null) {
//                                // 更新成功跳转
//                                Intent intent = new Intent();
//                                Bundle bundle = new Bundle();
//                                bundle.putString("phoneNumber", phoneNumber);
//                                intent.setClass(SignUp_activity.this, ToSetBuyPsw.class);
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                //更新失败
//                                Toast.makeText(SignUp_activity.this, "信息提交失败,请检查网络是否通畅"+e, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                } else {
//                    //获取失败
//                    Toast.makeText(SignUp_activity.this, "设置密码失败,请检查网络是否通畅", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


    }

}
