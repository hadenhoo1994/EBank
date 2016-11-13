package app.ebank.com.ebank.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.ebank.com.ebank.R;
import app.ebank.com.ebank.model.BankCard;
import app.ebank.com.ebank.model.UserMsg;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Haden on 2016/9/10.
 */
public class Main_Activity extends Activity {
    private Button yue;//余额
    private TextView title;//标题
    private Button gerenxinxi;//个人信息
    private Button zhuanzhang;//转账
    private Button zhangdan;//账单
    private Button seting;  //设置

    boolean flag_msg;
    boolean flag_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);
        //实例化控件
        yue = (Button) findViewById(R.id.yue);
        gerenxinxi = (Button) findViewById(R.id.gerenxinxi);
        zhuanzhang = (Button) findViewById(R.id.zhuanzhang);
        zhangdan = (Button) findViewById(R.id.zhangdan);
        seting = (Button) findViewById(R.id.seting);
        title = (TextView) findViewById(R.id.title);


//        //显示用户名到标题
        final BmobUser user0 =BmobUser.getCurrentUser();
        BmobQuery<BankCard> query0 = new BmobQuery<BankCard>();
        query0.addWhereEqualTo("phoneNumber", user0.getUsername());
        query0.findObjects(new FindListener<BankCard>() {
            @Override
            public void done(List<BankCard> list, BmobException e) {
                if (e == null) {
                    title.setText(list.get(0).getUsername());
                } else {
                    title.setText(user0.getUsername());
                }
            }
        });

        yue.setOnClickListener(new ButtonListener());
        gerenxinxi.setOnClickListener(new ButtonListener());
        zhuanzhang.setOnClickListener(new ButtonListener());
        zhangdan.setOnClickListener(new ButtonListener());
        seting.setOnClickListener(new ButtonListener());
    }

    //监听点击了那个模块
    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.gerenxinxi:
                    Intent intent1 = new Intent(Main_Activity.this, Gerenxinxi_activity.class);
                    startActivity(intent1);
                    break;
                case R.id.zhangdan:
                    Intent intent2 = new Intent(Main_Activity.this, ZhangDan_activity.class);
                    startActivity(intent2);
                    break;
                case R.id.zhuanzhang:
                    Intent intent3 = new Intent(Main_Activity.this, ZhuanZhang_activity.class);
                    startActivity(intent3);
                    break;
                case R.id.yue:
                    Intent intent4 = new Intent(Main_Activity.this, YuE_activity.class);
                    startActivity(intent4);
                    break;

                //设置
                case R.id.seting:
                    showPopupMenu(seting);
                    break;
            }
        }


    }

    //右上角下拉列表
    private void showPopupMenu(final View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(Main_Activity.this, seting);

        // menu布局
            popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());

        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_seting:    //跳转到用户信息设置
                        Intent i1 = new Intent(Main_Activity.this, SetMsg_activity.class);
                        startActivity(i1);
                        break;
                    case R.id.action_buyPsw:    //跳转到支付密码设置
                        Intent i3 = new Intent(Main_Activity.this, ToSetBuyPsw2.class);
                        startActivity(i3);
                        break;
                    case R.id.action_logout:    //退出登录
                        BmobUser.logOut();   //清除缓存用户对象
                        BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了
                        Intent intent = new Intent(Main_Activity.this, Index_activity.class);
                        startActivity(intent);
                        finish();
                }
                return false;
            }
        });
        // PopupMenu关闭事件
//                popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
//                    @Override
//                    public void onDismiss(PopupMenu menu) {
//                        Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
//                    }
//                });

        popupMenu.show();
    }
}
