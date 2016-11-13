package app.ebank.com.ebank.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;

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

/**
 * 注册前 判断用户是否存在及用户信息是否正确
 * <p/>
 * Created by Haden on 2016/10/22.
 */
public class CheckData extends Activity {
    private Button back;    //返回按钮
    private EditText phoneNumber;   //手机号码输入框
    private EditText userName;      //用户名
    private EditText idCard;        //用户身份证号码
    private EditText bankCardId;    //银行卡号码
    private Button next;        //下一步按钮

    private static String phoneNumberGet = null;    //获取输入框的手机号码
    private static String userNameGet = null;//获取输入框的用户名
    private static String idCardGet = null;//获取输入框的身份证号码
    private static String bankCardIdGet = null;//获取输入框的银行卡号码
    private boolean tureData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_checkdata);
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

    //实例化控件
    private void initialize() {
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        userName = (EditText) findViewById(R.id.username);
        idCard = (EditText) findViewById(R.id.idCard);
        bankCardId = (EditText) findViewById(R.id.bankCardId);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkdata();
            }
        });
    }

    /**
     * 判断输入的信息是否正确
     * 正确返回 true
     * 不正确返回 false
     */
    private void checkdata() {
        phoneNumberGet = phoneNumber.getText().toString().trim();   //获取输入的手机号
        userNameGet = userName.getText().toString().trim();     //获取输入的用户名
        idCardGet = idCard.getText().toString().trim();     //获取输入的身份证号码
        bankCardIdGet = bankCardId.getText().toString().trim(); //获取输入的银行卡号码

        //判断输入的手机号码是否为空
        if (phoneNumberGet == null || userNameGet == null || idCardGet == null || bankCardIdGet == null) {
            Toast.makeText(CheckData.this, "输入的信息不能为空", Toast.LENGTH_SHORT).show();
        } else {
            //信息不为空
            //创建一个query查询 通过手机号码查询到对应的user
            BmobQuery<BankCard> query = new BmobQuery<BankCard>();
            query.addWhereEqualTo("phoneNumber", phoneNumberGet);
            query.findObjects(new FindListener<BankCard>() {
                @Override
                public void done(List<BankCard> list, BmobException e) {
                    if (e == null) {
                        BankCard bankCard = list.get(0);
                        //对比输入的数据和表的数据是否相同
                        //判断用户名是否匹配
                        if (bankCard.getUsername().equals(userNameGet)) {
                            //判断身份证号码是否匹配
                            if (bankCard.getIdCard().equals(idCardGet)){
                                //判断银行卡信息是否正确
                                if (bankCard.getBankCard().equals(bankCardIdGet)){
                                    //所有信息正确 跳转到signUp界面设置密码和支付密码
                                                Intent intent = new Intent();
                                                Bundle bundle = new Bundle();
                                                bundle.putString("phoneNumber", phoneNumberGet);
                                                intent.setClass(CheckData.this, SignUp_activity.class);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                finish();
                                }else{
                                    Toast.makeText(CheckData.this, "银行卡信息有误,请确认信息", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(CheckData.this, "身份证信息有误,请确认信息", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CheckData.this, "用户名信息有误,请确认信息", Toast.LENGTH_SHORT).show();
                        }
//                        String s = "真实姓名:" + bankCard.getUsername() + "身份证号码" + bankCard.getIdCard();
//                        Toast.makeText(CheckData.this, s, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CheckData.this, "查无此用户,请确认信息", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }


}
