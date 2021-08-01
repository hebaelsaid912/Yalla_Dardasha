package com.example.android.final_graduation_project.ui.home.Drawer;

import android.view.ViewGroup;

public abstract class DrawerItem<T extends DrawerAdapter.ViewHolder> {
    protected boolean isClecked;
    public abstract T createViewHolder(ViewGroup parent);
    public abstract void bindViewHolder(T holder);
    public DrawerItem<T> setChecked(boolean isChecked){
        this.isClecked = isChecked;
        return this;
    }
    public boolean isClecked(){
        return isClecked;
    }
    public boolean isSelectable(){
        return true;
    }
}
