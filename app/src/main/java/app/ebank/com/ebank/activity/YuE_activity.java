package app.ebank.com.ebank.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

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
 * Created by Haden on 2016/9/10.
 */
public class YuE_activity extends Activity {
    private TextView username; //用户名文本
    private TextView yuetext;   //余额显示文本
    private Button check_rmb;   //显示人民币约按钮
    private Button check_dollar;    //显示美元余额
    private static String yue;             //余额
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_yue);
        //      第一：默认初始化Bmob
        Bmob.initialize(this, "34bf3494f1223137a33e3c73d6be754b");
        getRMBBalance();
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //实例化控件
        yuetext = (TextView) findViewById(R.id.yuetext);
        username = (TextView) findViewById(R.id.username);
        check_rmb = (Button) findViewById(R.id.check_rmb);
        check_dollar = (Button) findViewById(R.id.check_dollar);
        check_dollar.setOnClickListener(new ButtonListener());
        check_rmb.setOnClickListener(new ButtonListener());
        getUserName();
        getRMBBalance();
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.check_rmb:
                    getRMBBalance();
                    getUserName();
                    break;
                case R.id.check_dollar:
                    getDollarBalance();
                    break;
            }
        }
    }

    /**
     * 查询美元余额
     */
    private void getDollarBalance() {
        Toast.makeText(YuE_activity.this, "功能尚未推出,敬请期待", Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示用户名
     */
    private void getUserName() {
        //获取本地的user
        final BmobUser user = BmobUser.getCurrentUser();
        BmobQuery<BankCard> query = new BmobQuery<BankCard>();
        query.addWhereEqualTo("phoneNumber", user.getUsername());
        query.findObjects(new FindListener<BankCard>() {
            @Override
            public void done(List<BankCard> list, BmobException e) {
                if (e == null) {
                    BankCard bankCard = list.get(0);
                    username.setText(bankCard.getUsername());
                } else {
                    Toast.makeText(YuE_activity.this, "获取余额失败,请检查网络是否通畅" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 查询人民币余额
     */
    private void getRMBBalance() {
        //获取本地的user
        BmobUser user = BmobUser.getCurrentUser();
        BmobQuery<BankCard> query = new BmobQuery<BankCard>();
        query.addWhereEqualTo("phoneNumber", user.getUsername());
        query.findObjects(new FindListener<BankCard>() {
            @Override
            public void done(List<BankCard> list, BmobException e) {
                if (e == null){
                    BankCard bankCard = list.get(0);
                    yuetext.setText(bankCard.getBalance().toString());
                }else{
                    Toast.makeText(YuE_activity.this, "获取余额失败,请检查网络是否通畅"+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
//        //通过本地的objectId来获取余额
//        BmobQuery<UserMsg> query = new BmobQuery<UserMsg>();
//        query.getObject(user.getObjectId(), new QueryListener<UserMsg>() {
//            @Override
//            public void done(UserMsg userMsg, BmobException e) {
//                if (e ==null){
//                    yuetext.setText(userMsg.getBalance().toString());
//                }else{
//                    Toast.makeText(YuE_activity.this, "获取余额失败,请检查网络是否通畅", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}
