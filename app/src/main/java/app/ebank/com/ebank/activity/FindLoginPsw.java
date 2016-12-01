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

import org.w3c.dom.Text;

import java.lang.annotation.Target;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.ebank.com.ebank.R;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Haden on 2016/12/1.
 */
public class FindLoginPsw extends Activity {
    private Button back;
    private Button check;
    private EditText setEmail;
    private TextView manage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forgetloginpsw);
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

        check = (Button) findViewById(R.id.check);
        setEmail = (EditText) findViewById(R.id.setEmail);
        manage = (TextView) findViewById(R.id.forgotbpwtext);


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String eamil = setEmail.getText().toString().trim();
                //判断邮箱格式是否正确
                if (isEmail(eamil)){
                    //邮箱格式正确
                    BmobUser.resetPasswordByEmail(eamil, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(FindLoginPsw.this, "请求通过,请到邮箱" + eamil + "中进行密码修改",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(FindLoginPsw.this, "网络异常",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(FindLoginPsw.this,"邮箱格式有误",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(FindLoginPsw.this,"你好",Toast.LENGTH_SHORT).show();
            }
        });

        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FindLoginPsw.this,ForgetPsw_activity.class);
                startActivity(i);
                finish();
            }
        });

    }

    public boolean isEmail(String email) {

        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        Pattern p = Pattern.compile(str);

        Matcher m = p.matcher(email);

        return m.matches();
    }
}
