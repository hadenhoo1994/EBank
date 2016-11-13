package app.ebank.com.ebank.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import app.ebank.com.ebank.R;
import app.ebank.com.ebank.model.Bill;
import app.ebank.com.ebank.model.BillAdapter;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Haden on 2016/7/11.
 */
public class ZhangDan_activity extends Activity {
    private Button back;
    private Button showBill;
    private static List<HashMap<String, Object>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zhangdan);
        //      第一：默认初始化Bmob
        Bmob.initialize(this, "34bf3494f1223137a33e3c73d6be754b");
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showBill = (Button) findViewById(R.id.showBill);
        final ListView listView = (ListView) findViewById(R.id.zhangdan_list);
        initBill();
        showBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleAdapter adapter = new SimpleAdapter(ZhangDan_activity.this, data, R.layout.zhangdanlist_item,
                        new String[]{"zhangdan_time", "zhangdan_type", "zhangdan_sum"},
                        new int[]{R.id.zhangdan_time, R.id.zhangdan_type, R.id.zhangdan_sum});
                listView.setAdapter(adapter);
            }
        });

    }

    private void initBill() {
        //通过本地用户获取用户名来得到用户账单
        BmobUser user = BmobUser.getCurrentUser();
        BmobQuery<Bill> query = new BmobQuery<Bill>();
        query.addWhereEqualTo("username", user.getUsername());
        query.order("-updatedAt");
        query.findObjects(new FindListener<Bill>() {
            @Override
            public void done(List<Bill> list, BmobException e) {
                if (e == null) {
                    data = new ArrayList<HashMap<String, Object>>();
                    Iterator<Bill> i = list.iterator();
                    while (i.hasNext()) {
                        HashMap<String, Object> item = new HashMap<String, Object>();
                        Bill bill = i.next();
                        item.put("zhangdan_time", bill.getCreatedAt());
                        item.put("zhangdan_type", bill.getType());
                        item.put("zhangdan_sum", bill.getMoney());
                        data.add(item);

                    }

                    //Toast.makeText(ZhangDan_activity.this, "账单获取到了", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ZhangDan_activity.this, "账单获取失败,请检查网络情况", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
