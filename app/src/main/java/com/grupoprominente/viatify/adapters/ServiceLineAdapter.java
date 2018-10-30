package com.grupoprominente.viatify.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Filter;
import com.grupoprominente.viatify.model.ServiceLine;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ServiceLineAdapter extends ArrayAdapter<ServiceLine> {

    private Context context;
    private List<ServiceLine> serviceLineList, tempItems, suggestions;

    public ServiceLineAdapter(Context context, int textViewResourceId,
                              List<ServiceLine> serviceLineList) {
        super(context, textViewResourceId, serviceLineList);
        this.context = context;
        this.serviceLineList = serviceLineList;
        tempItems = new ArrayList<>(serviceLineList);
        suggestions = new ArrayList<>();
        }

    @Override
    public int getCount(){
        return serviceLineList.size();
    }

    @Override
    public ServiceLine getItem(int position){
        return serviceLineList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public int getServiceLineAdapterId(int position){
        return serviceLineList.get(position).getId();
    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(serviceLineList.get(position).getTitle());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(serviceLineList.get(position).getTitle());

        return label;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return ServiceLineFilter;
    }

    private Filter ServiceLineFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            ServiceLine serviceLine = (ServiceLine) resultValue;
            return serviceLine.getTitle();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (ServiceLine serviceLine : tempItems) {
                    if (serviceLine.getTitle().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(serviceLine);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<ServiceLine> tempValues = (ArrayList<ServiceLine>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (ServiceLine serviceLine : tempValues) {
                    add(serviceLine);
                    notifyDataSetChanged();
                }
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
