package com.grupoprominente.viatify.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderItem> {

    ArrayList<String> listItems;

    public RecyclerViewAdapter(ArrayList<String> listItems) {
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {

        TextView viatico;

        public ViewHolderItem(View itemView) {
            super(itemView);
            viatico=viatico(R.id.)




        }
    }
}





