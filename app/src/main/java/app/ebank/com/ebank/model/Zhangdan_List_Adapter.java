package app.ebank.com.ebank.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import app.ebank.com.ebank.R;

/**
 * Created by Haden on 2016/7/11.
 */
public class Zhangdan_List_Adapter extends ArrayAdapter<Zhangdan> {
    private int resourceId;

    public Zhangdan_List_Adapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zhangdan zhangdan = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView zhangdan_time = (TextView) view.findViewById(R.id.zhangdan_time);
        TextView zhangdan_type = (TextView) view.findViewById(R.id.zhangdan_type);
        TextView zhangdan_sum = (TextView) view.findViewById(R.id.zhangdan_sum);
        return view;

    }
}
