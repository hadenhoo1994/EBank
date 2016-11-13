package app.ebank.com.ebank.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import app.ebank.com.ebank.R;
import app.ebank.com.ebank.activity.ZhangDan_activity;

/**
 * Created by Haden on 2016/10/10.
 */
public class BillAdapter extends ArrayAdapter<Bill> {
    private int resourceId;

    public BillAdapter(Context context, int textViewResourceId, List<Bill> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bill bill = getItem(position);  //获取当前实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView zhangdan_time = (TextView) view.findViewById(R.id.zhangdan_time);
        TextView zhangdan_type = (TextView) view.findViewById(R.id.zhangdan_type);
        TextView zhangdan_sum = (TextView) view.findViewById(R.id.zhangdan_sum);
        zhangdan_time.setText(bill.getTime().toString());
        zhangdan_type.setText(bill.getType());
        zhangdan_sum.setText(bill.getMoney());
        return view;
    }
}
