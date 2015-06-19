package com.example.lizetho.startupmaterialdesign.common.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lizetho.startupmaterialdesign.R;

/**
 * Created by lizetho on 10/04/15.
 */
public class NavigationMenuAdapter extends RecyclerView.Adapter<NavigationMenuAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    private NavigationMenuItemEnum[] navigationMenuItemEnums;
    private Context context;
    private NavigationMenuItemEnum selectedNavigationMenuItemEnum;//selectedItem

    public NavigationMenuAdapter(Context context, NavigationMenuItemEnum[] data) {
        navigationMenuItemEnums = data;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.fragment_navigation_menu_item, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        NavigationMenuItemEnum current = navigationMenuItemEnums[i];
        myViewHolder.title.setText(current.nameResource);
        myViewHolder.imageView.setImageResource(current.iconResource);
        myViewHolder.title.setTextColor(context.getResources().getColor(current == selectedNavigationMenuItemEnum ? R.color.accent : R.color.navigation_menu_item_text));
        myViewHolder.itemView.setBackgroundResource(current == selectedNavigationMenuItemEnum ? R.color.navigation_menu_selected_item_background : R.color.navigation_menu_item_background);
    }

    @Override
    public int getItemCount() {
        return navigationMenuItemEnums.length;
    }

    public void setSelectedItem(NavigationMenuItemEnum navigationMenuItemEnum) {
        selectedNavigationMenuItemEnum = navigationMenuItemEnum;
    }

    public NavigationMenuItemEnum getSelectedItem() {
        return selectedNavigationMenuItemEnum;
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
            ((AbstractActivity) context).onNavigationMenuItemClicked(navigationMenuItemEnums[getPosition()]);
        }
    }


}
