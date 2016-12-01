package app.ebank.com.ebank.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import app.ebank.com.ebank.R;
import app.ebank.com.ebank.model.UserMsg;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Haden on 2016/9/10.
 */
public class ZhuanZhang_activity extends Activity {
    private Button transfer_account;    //跳转到转账页面按钮
    private Button recharge;            //跳转到充值页面按钮
    private Button back;
    private static String buyPsw;              //支付密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.actitity_zhuanzhang);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        transfer_account = (Button) findViewById(R.id.transfer_account);
        recharge = (Button) findViewById(R.id.recharge);

        transfer_account.setOnClickListener(new ButtonListener());
        recharge.setOnClickListener(new ButtonListener());
        recharge.setOnClickListener(new ButtonListener());
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.transfer_account:
                    //判断用户是否冻结
                    final BmobUser user =BmobUser.getCurrentUser();
                    BmobQuery<UserMsg> query = new BmobQuery<UserMsg>();
                    query.getObject(user.getObjectId(), new QueryListener<UserMsg>() {
                        @Override
                        public void done(UserMsg userMsg, BmobException e) {
                            if (e ==null){
                                //判断用户是否是冻结的状态
                                if (userMsg.getFrozen()){
                                    new AlertDialog.Builder(ZhuanZhang_activity.this)
                                            .setTitle("支付密码错误")
                                            .setMessage("该用户已被冻结,无法进行转账交易,请联系管理员解冻")
                                            .setPositiveButton("确定", null)
                                            .show();
                                }else{
                                    Intent intent1 = new Intent();
                                    intent1.setClass(ZhuanZhang_activity.this, Transfer_activity.class);
                                    startActivity(intent1);
                                    finish();
                                }
                            }else{
                                Toast.makeText(ZhuanZhang_activity.this, "网络有误", Toast.LENGTH_SHORT).show();
                            }
                        }


                    });


                    break;
                case R.id.recharge:
                    Toast.makeText(ZhuanZhang_activity.this, "功能尚未推出,敬请期待", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
