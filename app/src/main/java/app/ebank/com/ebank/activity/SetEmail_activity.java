package app.ebank.com.ebank.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.ebank.com.ebank.R;
import app.ebank.com.ebank.model.UserMsg;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Haden on 2016/12/1.
 */
public class SetEmail_activity extends Activity {
    private EditText emailText;
    private Button check;
    private Button back;
    private static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setemail);

        //      第一：默认初始化Bmob
        Bmob.initialize(this, "34bf3494f1223137a33e3c73d6be754b");

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        emailText = (EditText) findViewById(R.id.setEmail);
        check = (Button) findViewById(R.id.check);

        //设置验证
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailText.getText().toString().trim();
                if (isEmail(email)) {
                    BmobUser user = BmobUser.getCurrentUser();
                    UserMsg userMsg = new UserMsg();
                    userMsg.setEmail(email);
                    userMsg.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //邮箱格式正确 设置验证
                                BmobUser.requestEmailVerify(email, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(SetEmail_activity.this, "请求通过,请到邮箱" + email + "中激活邮箱", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(SetEmail_activity.this, "网络异常" + e, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });


                } else {
                    Toast.makeText(SetEmail_activity.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                }

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
