package app.ebank.com.ebank.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import app.ebank.com.ebank.R;
import app.ebank.com.ebank.model.BankCard;
import app.ebank.com.ebank.model.Bill;
import app.ebank.com.ebank.model.MD5;
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
 * Created by Haden on 2016/10/7.
 */
public class Transfer_activity extends Activity {
    private EditText transfer_user;         //收款方账号输入框
    private EditText transfer_accounts;     //转账金额输入框
    private Button submit_transfer;         //确认转账按钮
    private static String tf_user;          //收款方
    private static String tf_account;       //收款金额
    private boolean flag_user;              //用户是否存在标签
    private boolean flag_balance;           //余额是足够标签
    private static double a = 0;                //当前用户卡里余额
    private static double b = 0;                //输入的金额
    private static double d = 0;
    private Button back;
    private EditText buypswsub;     //支付密码输入框
    private static String getbuyPsw;   //输入的支付密码
    private static String psw;             //支付密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_transfer);
        //      第一：默认初始化Bmob
        Bmob.initialize(this, "34bf3494f1223137a33e3c73d6be754b");
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //获取支付密码
        final BmobUser bmobUser9 = BmobUser.getCurrentUser();
        BmobQuery<UserMsg> query9 = new BmobQuery<UserMsg>();
        query9.addWhereEqualTo("username", bmobUser9.getUsername());
        query9.findObjects(new FindListener<UserMsg>() {
            @Override
            public void done(List<UserMsg> list, BmobException e) {
                if (e == null) {
                    UserMsg userMsg9 = list.get(0);
                    psw = userMsg9.getBuyPsw();
                } else {
                    Toast.makeText(Transfer_activity.this, "信息获取失败,请检查网络是否正常" + e, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        //实例化控件
        transfer_user = (EditText) findViewById(R.id.transfer_user);
        transfer_accounts = (EditText) findViewById(R.id.transfer_accounts);
        submit_transfer = (Button) findViewById(R.id.submit_transfer);
        buypswsub = (EditText) findViewById(R.id.buypswsub);
        //提交
        submit_transfer.setOnClickListener(new ButtonListener());

    }

    //监听button的事件
    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            getbuyPsw = buypswsub.getText().toString().trim();
            //获取EditText中的数据
            tf_user = transfer_user.getText().toString().trim();
            tf_account = transfer_accounts.getText().toString().trim();

            //判断支付密码是否正确
            if (getbuyPsw.equals(psw)) {
                //判断输入要转账的用户是否存在
                if (isUser()) {
                    //判断余额是否足够支付
                    if (isEnoughBalance()) {
                        //Toast.makeText(Transfer_activity.this, "转账成功", Toast.LENGTH_SHORT).show();
                        transferTo();
                    } else {
                        Toast.makeText(Transfer_activity.this, "网络有误,请重试", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Transfer_activity.this, "网络有误,请重试", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Transfer_activity.this,i,Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Transfer_activity.this, "支付密码错误,请重新输入", Toast.LENGTH_SHORT).show();
            }


        }
    }

    /**
     * 转账功能
     */
    private void transferTo() {
        //修改当前用户余额
        BmobUser bmobUser = BmobUser.getCurrentUser();
        BmobQuery<BankCard> bmobQuery = new BmobQuery<BankCard>();
        bmobQuery.addWhereEqualTo("phoneNumber", bmobUser.getUsername());
        bmobQuery.findObjects(new FindListener<BankCard>() {
                                  @Override
                                  public void done(List<BankCard> list, BmobException e) {
                                      if (e == null) {
                                          double c = a - b;
                                          BankCard bankCard = list.get(0);
                                          bankCard.setBalance(c);
                                          //修改当前用户余额
                                          bankCard.update(new UpdateListener() {
                                              @Override
                                              public void done(BmobException e) {
                                                  if (e == null) {
                                                      //当前用户余额修改成功
                                                      BmobQuery<BankCard> query = new BmobQuery<BankCard>();
                                                      query.addWhereEqualTo("phoneNumber", tf_user);
                                                      query.findObjects(new FindListener<BankCard>() {
                                                          @Override
                                                          public void done(List<BankCard> list, BmobException e) {
                                                              if (e == null) {
                                                                  BankCard bankCard1 = list.get(0);
                                                                  d = bankCard1.getBalance() + b;
                                                                  bankCard1.setBalance(d);
                                                                  bankCard1.update(new UpdateListener() {
                                                                      @Override
                                                                      public void done(BmobException e) {
                                                                          if (e == null) {
                                                                              // Toast.makeText(Transfer_activity.this, "进来了", Toast.LENGTH_SHORT).show();
                                                                              addIncomeBill();
                                                                              addExpenditure();
                                                                              finish();
                                                                          } else {
                                                                              Toast.makeText(Transfer_activity.this, "信息更新失败,请检查网络是否正常" + e, Toast.LENGTH_SHORT).show();
                                                                          }
                                                                      }
                                                                  });
//
                                                              } else {
                                                                  Toast.makeText(Transfer_activity.this, "信息获取失败,请检查网络是否正常" + e, Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });
                                                  } else {
                                                      Toast.makeText(Transfer_activity.this, "信息查询失败,请检查网络是否正常" + e, Toast.LENGTH_SHORT).show();
                                                  }
                                              }
                                          });
                                      } else

                                      {
                                          Toast.makeText(Transfer_activity.this, "信息获取失败,请检查网络是否正常", Toast.LENGTH_SHORT).show();
                                      }
                                  }
                              }

        );
    }

    /**
     * 判断余额是否足够
     */
    private boolean isEnoughBalance() {

        //获取本地的user
        BmobUser user = BmobUser.getCurrentUser();
        //通过本地的objectId来获取余额
        BmobQuery<BankCard> query = new BmobQuery<BankCard>();
        query.addWhereEqualTo("phoneNumber", user.getUsername());
        query.findObjects(new FindListener<BankCard>() {
            @Override
            public void done(List<BankCard> list, BmobException e) {
                if (e == null) {
                    BankCard bc = list.get(0);
                    a = bc.getBalance();     //卡里余额
                    b = Integer.parseInt(tf_account);//输入的金额
                    if (a > b) {
                        flag_balance = true;
                    } else {
                        flag_balance = false;
                    }
                } else {
                    Toast.makeText(Transfer_activity.this, "余额获取失败,请检查网络是否正常", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return flag_balance;
    }

    /**
     * 判断用户是否存在
     *
     * @return
     */
    public boolean isUser() {
        final BmobQuery<BankCard> query = new BmobQuery<BankCard>();
        query.addWhereEqualTo("phoneNumber", tf_user);
        //查询
        query.findObjects(new FindListener<BankCard>() {
            @Override
            public void done(List<BankCard> list, final BmobException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        flag_user = false;
                    } else {
                        flag_user = true;
                    }
                } else {
                    flag_user = false;
                }
            }
        });


        return flag_user;
    }


    /**
     * 添加支出情况到账单
     */
    private void addExpenditure() {
        Bill bill = new Bill();
        bill.setMoney(tf_account);
        bill.setType("支出");
        bill.setUsername(BmobUser.getCurrentUser().getUsername());
        bill.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                } else {
                }
            }
        });
    }

    /**
     * 添加收入情况到账单
     */
    private void addIncomeBill() {
        Bill bill = new Bill();
        bill.setUsername(tf_user);
        bill.setMoney(tf_account);
        bill.setType("收入");
        bill.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(Transfer_activity.this, "转账成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Transfer_activity.this, "转账成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

