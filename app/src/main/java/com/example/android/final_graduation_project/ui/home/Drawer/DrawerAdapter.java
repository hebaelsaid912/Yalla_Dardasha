package com.example.android.final_graduation_project.ui.home.Drawer;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {
    private List<DrawerItem> items;
    private Map<Class<? extends DrawerItem>,Integer> viewTypes;
    private SparseArray<DrawerItem> holderFactories;
    onItemSelectedListener listener;

    public DrawerAdapter( List<DrawerItem> items){
        this.items = items;
        this.viewTypes = new HashMap<>();
        this.holderFactories = new SparseArray<>();
        processViewTypes();
    }

    private void processViewTypes(){
        int type = 0;
        for (DrawerItem item : items){
            if (!viewTypes.containsKey(item.getClass())){
                viewTypes.put(item.getClass(),type);
                holderFactories.put(type,item);
                type++;
            }
        }
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ViewHolder holder = holderFactories.get(viewType).createViewHolder(parent);
        holder.drawerAdapter = this;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        items.get(position).bindViewHolder(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewTypes.get(items.get(position).getClass());
    }
    public void setSelected(int position){
        DrawerItem newChecked = items.get(position);
        if(!newChecked.isSelectable()){
            return;
        }
        for (int i=0 ; i<items.size() ; i++){
            DrawerItem item = items.get(i);
            if(item.isClecked()){
                item.setChecked(false);
                notifyItemChanged(i);
                break;
            }
        }
        newChecked.setChecked(true);
        notifyItemChanged(position);
        if (listener != null){
            listener.onItemSelected(position);
        }
    }
    public void  setListener(onItemSelectedListener listener){
        this.listener = listener;
    }
    public interface onItemSelectedListener{
        void onItemSelected(int position);
    }
    static abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private DrawerAdapter drawerAdapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            drawerAdapter.setSelected(getAdapterPosition());
        }
    }
}
