package com.example.android.final_graduation_project.ui.home.Drawer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public class SpaceItem extends DrawerItem<SpaceItem.ViewHolder> {
    private int spaceDp;

    public SpaceItem(int spaceDp) {
        this.spaceDp = spaceDp;
    }

    @Override
    public ViewHolder createViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        View view = new View(context);
       int height = (int) (context.getResources().getDisplayMetrics().density*spaceDp);
       // int height = spaceDp;
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                height));
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {

    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    static class ViewHolder extends DrawerAdapter.ViewHolder{
     //   private ImageView icon;
     //   private TextView title;
        public ViewHolder(@NonNull View itemsView){
            super(itemsView);
       //     icon = itemsView.findViewById(R.id.icon);
         //   title = itemsView.findViewById(R.id.title);
        }
    }
}
