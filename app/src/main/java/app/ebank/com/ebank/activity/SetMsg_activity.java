package app.ebank.com.ebank.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import app.ebank.com.ebank.R;
import app.ebank.com.ebank.model.BankCard;
import app.ebank.com.ebank.model.IdCard;
import app.ebank.com.ebank.model.User;
import app.ebank.com.ebank.model.UserMsg;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Haden on 2016/9/30.
 */
public class SetMsg_activity extends Activity {
    private Button back;
    private EditText setdizhi;
    private Button queren;

    private static String address = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setmsg);
        //      第一：默认初始化Bmob
        Bmob.initialize(this, "34bf3494f1223137a33e3c73d6be754b");
        back = (Button) findViewById(R.id.back);
        //顶栏返回键
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //实例化控件
        setdizhi = (EditText) findViewById(R.id.setdizhi);
        queren = (Button) findViewById(R.id.queren);
        //监听按钮
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = setdizhi.getText().toString().trim();
                BmobUser bmobUser = BmobUser.getCurrentUser();
                BmobQuery<BankCard> query = new BmobQuery<BankCard>();
                query.addWhereEqualTo("phoneNumber", bmobUser.getUsername());
                query.findObjects(new FindListener<BankCard>() {
                    @Override
                    public void done(List<BankCard> list, BmobException e) {
                        if (e == null) {
                            BankCard bankCard = list.get(0);
                            bankCard.setAddress(address);
                            bankCard.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        //更新成功
                                        Toast.makeText(SetMsg_activity.this, "更新信息成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(SetMsg_activity.this, "更新信息失败,请检查网络是否正常", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SetMsg_activity.this, "获取信息失败,请检查网络是否正常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}