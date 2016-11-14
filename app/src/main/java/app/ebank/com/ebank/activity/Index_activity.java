package app.ebank.com.ebank.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.ebank.com.ebank.R;
import app.ebank.com.ebank.model.MD5;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Haden on 2016/7/6.
 */
public class Index_activity extends Activity {
    private Button signIn;
    private EditText editUser;
    private EditText edPassWord;
    private TextView signUp;
    private BmobUser bmobUser = null;
    private TextView forgetPsw;   //忘记密码按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.index);
        //      第一：默认初始化
        Bmob.initialize(this, "34bf3494f1223137a33e3c73d6be754b");
        //判断本地是否有用户信息
        bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            Intent intent = new Intent(Index_activity.this, Main_Activity.class);
            startActivity(intent);
            finish();
        } else {
//            Intent intent = new Intent(Index_activity.this, SignUp_activity.class);
//            startActivity(intent);
        }
        signIn = (Button) findViewById(R.id.signin);
        editUser = (EditText) findViewById(R.id.edituser);
        edPassWord = (EditText) findViewById(R.id.edpassword);
        forgetPsw = (TextView) findViewById(R.id.forgotpw);
        signUp = (TextView) findViewById(R.id.signup);
//        signIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Index_activity.this,Main_Activity.class);
//                startActivity(intent);
//            }
//        });


        /**
         * 登录按钮
         */
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eduser = editUser.getText().toString().trim();
                String edpsw = edPassWord.getText().toString().trim();
                BmobUser bu = new BmobUser();
                //添加md5加密 将输入的密码加密再进行匹配
                MD5 md5 = new MD5();
                String  encipheredpsw = md5.getMD5ofStr(edpsw);
                bu.setUsername(eduser);
                bu.setPassword(encipheredpsw);
                //判断邮箱和密码都不能为空
                if (edpsw != null && eduser != null) {
                    bu.login(new SaveListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if (e == null) {
                                Toast.makeText(Index_activity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Index_activity.this, Main_Activity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Index_activity.this, "用户名或密码有误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Index_activity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        注册

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Index_activity.this, CheckData.class);
                startActivity(intent);
            }
        });

//      忘记密码
        forgetPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Index_activity.this, ForgetPsw_activity.class);
                startActivity(intent);
            }
        });


    }


}
