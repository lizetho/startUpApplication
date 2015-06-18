package com.example.lizetho.startupmaterialdesign.common.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lizetho.startupmaterialdesign.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by lizetho on 10/04/15.
 */
public class NavigationActionItemAdapter extends RecyclerView.Adapter<NavigationActionItemAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    private List<NavigationActionItem> actionItemList = Collections.emptyList();
    private Context context;
    private int selectedPosition = 0;//selectedItem by default

    public NavigationActionItemAdapter(Context context, List<NavigationActionItem> data) {
        actionItemList = data;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.navigation_action_item, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        NavigationActionItem current = actionItemList.get(i);
        myViewHolder.title.setText(current.getTitle());
        myViewHolder.imageView.setImageResource(i == selectedPosition ? android.R.drawable.btn_star_big_on : current.getIconId());
        myViewHolder.title.setTextColor(context.getResources().getColor(i == selectedPosition ? R.color.accent : R.color.primary_text));
        myViewHolder.itemView.setBackgroundResource(i == selectedPosition ? R.color.background_floating_material_dark : android.R.color.transparent);
    }

    @Override
    public int getItemCount() {
        return actionItemList.size();
    }

    public void setSelectedItem(int position) {

    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.navigation_item_image);
            title = (TextView) itemView.findViewById(R.id.navigation_item_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "hola " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }


}
