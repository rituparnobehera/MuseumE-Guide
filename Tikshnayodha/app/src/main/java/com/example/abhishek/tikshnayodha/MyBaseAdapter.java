package com.example.abhishek.tikshnayodha;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ABHISHEK on 7/19/2017.
 */
public class MyBaseAdapter extends BaseAdapter {

    ArrayList myList = new ArrayList();
    LayoutInflater inflater;
    Context context;


    public MyBaseAdapter(Context context, ArrayList myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public ListData getItem(int position) {
        return (ListData) myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.detail_list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        ListData currentListData = getItem(position);

        mViewHolder.tvTitle.setText(currentListData.getTitle());
        //mViewHolder.tvDesc.setText(currentListData.getDescription());
        mViewHolder.ivIcon.setImageBitmap(currentListData.getImgResId());
        return convertView;
    }

    private class MyViewHolder {
        TextView tvTitle, tvDesc;
        ImageView ivIcon;

        public MyViewHolder(View item) {
            tvTitle = (TextView) item.findViewById(R.id.aname);
            //tvDesc = (TextView) item.findViewById(R.id.txtDetail);
            ivIcon = (ImageView) item.findViewById(R.id.head);
        }
    }
}

