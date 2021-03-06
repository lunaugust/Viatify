package com.grupoprominente.viatify.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.grupoprominente.viatify.R;
import com.grupoprominente.viatify.helpers.FlipAnimator;
import com.grupoprominente.viatify.model.Travel;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.MyViewHolder> {
    private Context mContext;
    private List<Travel> travels;
    private TravelAdapterListener listener;
    private SparseBooleanArray selectedItems;

    // array used to perform multiple animation at once
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;

    // index is used to animate only the selected row
    // dirty fix, find a better solution
    private static int currentSelectedIndex = -1;

    private boolean selected = false;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView title, description, iconText;
        public ImageView imgProfile;
        public LinearLayout travelContainer;
        public RelativeLayout iconContainer, iconBack, iconFront;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.t_title);
            iconText = view.findViewById(R.id.icon_text);
            iconBack = view.findViewById(R.id.icon_back);
            iconFront = view.findViewById(R.id.icon_front);
            imgProfile = view.findViewById(R.id.icon_profile);
            travelContainer = view.findViewById(R.id.travel_container);
            iconContainer = view.findViewById(R.id.icon_container);
        }
    }


    public TravelAdapter(Context mContext, List<Travel> travels, TravelAdapterListener listener) {
        this.mContext = mContext;
        this.travels = travels;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Travel travel = travels.get(position);

        // displaying text view data
        holder.title.setText(travel.getTitle());

        // displaying the first letter of From in icon text
        holder.iconText.setText(travel.getTitle().substring(0, 1));

        // change the row state to activated
        holder.itemView.setActivated(selectedItems.get(position, false));

        // handle icon animation
        applyIconAnimation(holder, position);

        // display profile image
        applyProfilePicture(holder, travel);

        // apply click events
        applyClickEvents(holder, position);
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
        holder.iconContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIconClicked(position);
            }
        });

        holder.travelContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTravelRowClicked(position);
            }
        });
    }

    private void applyProfilePicture(MyViewHolder holder, Travel travel) {
        holder.imgProfile.setImageResource(R.drawable.bg_circle);
        holder.imgProfile.setColorFilter(travel.getColor());
        holder.iconText.setVisibility(View.VISIBLE);
    }

    private void applyIconAnimation(MyViewHolder holder, int position) {
        if (selectedItems.get(position, false)) {
            holder.iconFront.setVisibility(View.GONE);
            resetIconYAxis(holder.iconBack);
            holder.iconBack.setVisibility(View.VISIBLE);
            holder.iconBack.setAlpha(1);
            if (currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, true);
                resetCurrentIndex();
            }
        } else {
            holder.iconBack.setVisibility(View.GONE);
            resetIconYAxis(holder.iconFront);
            holder.iconFront.setVisibility(View.VISIBLE);
            holder.iconFront.setAlpha(1);
            if ((reverseAllAnimations && animationItemsIndex.get(position, false)) || currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, false);
                resetCurrentIndex();
            }
        }
    }


    // As the views will be reused, sometimes the icon appears as
    // flipped because older view is reused. Reset the Y-axis to 0
    private void resetIconYAxis(View view) {
        if (view.getRotationY() != 0) {
            view.setRotationY(0);
        }
    }

    public void resetAnimationIndex() {
        reverseAllAnimations = false;
        animationItemsIndex.clear();
    }

    @Override
    public long getItemId(int position) {
        return travels.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return travels.size();
    }

    public void toggleSelection(int pos) {
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
            animationItemsIndex.delete(pos);
            selected = false;
        } else {
            if (!selected) {
                selectedItems.put(pos, true);
                animationItemsIndex.put(pos, true);
                selected = true;
            }
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        reverseAllAnimations = true;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {
        travels.remove(position);
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }

    public interface TravelAdapterListener {
        void onIconClicked(int position);

        void onTravelRowClicked(int position);
    }
}