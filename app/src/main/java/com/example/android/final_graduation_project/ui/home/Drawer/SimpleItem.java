package com.example.android.final_graduation_project.ui.home.Drawer;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.android.final_graduation_project.R;

public class SimpleItem extends DrawerItem<SimpleItem.ViewHolder>{
    private int selectedItemIconTint;
    private int selectedItemTitleTint;

    private int normalItemIconTint;
    private int normalItemTitleTint;

    private Drawable icon;
    private String title;

    public SimpleItem(Drawable icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    @Override
    public ViewHolder createViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.items_options,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {
        holder.title.setText(title);
        holder.icon.setImageDrawable(icon);

        holder.title.setTextColor(isClecked ? selectedItemTitleTint : normalItemTitleTint);
        holder.icon.setColorFilter(isClecked ? selectedItemIconTint : normalItemIconTint);
    }
    public  SimpleItem withSelectedIconTint(int selectedItemIconTint){
        this.selectedItemIconTint = selectedItemIconTint;
        return this;
    }
    public SimpleItem withIconTint(int normalItemIconTint){
        this.normalItemIconTint = normalItemIconTint;
        return this;
    }
    public SimpleItem withTitleTint(int normalItemTitleTint){
        this.normalItemTitleTint = normalItemTitleTint;
        return this;
    }
    public  SimpleItem withSelectedTitleTint(int selectedItemTitleTint){
        this.selectedItemTitleTint = selectedItemTitleTint;
        return this;
    }
    static class ViewHolder extends DrawerAdapter.ViewHolder{
        private ImageView icon;
        private TextView title;
        public ViewHolder(@NonNull View itemsView){
            super(itemsView);
            icon = itemsView.findViewById(R.id.icon);
            title = itemsView.findViewById(R.id.title);
        }
    }
}
