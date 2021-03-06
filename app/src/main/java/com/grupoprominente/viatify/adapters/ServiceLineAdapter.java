package com.grupoprominente.viatify.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Filter;

import com.grupoprominente.viatify.R;
import com.grupoprominente.viatify.model.ServiceLine;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

public class ServiceLineAdapter extends ArrayAdapter<ServiceLine> {

    private Context context;
    private List<ServiceLine> serviceLineList, tempItems, suggestions;
    private int textViewResourceId;

    public ServiceLineAdapter(Context context, int textViewResourceId,
                              List<ServiceLine> serviceLineList) {
        super(context, textViewResourceId, serviceLineList);
        this.context = context;
        this.serviceLineList = serviceLineList;
        this.textViewResourceId = textViewResourceId;
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

    public int getPosition(int id) { return serviceLineList.indexOf(new ServiceLine(id,null,null));}

    @Override
    public long getItemId(int position){
        return position;
    }

    public int getServiceLineAdapterId(int position){
        return serviceLineList.get(position).getId();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater;
        try{
            if (convertView == null) {
                inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(textViewResourceId, parent, false);
            }
            ServiceLine serviceLine = serviceLineList.get(position);
            TextView txtTitle = view.findViewById(R.id.txtTitle);
            TextView txtSubTitle = view.findViewById(R.id.txtSubTitle);
            txtTitle.setText(serviceLine.getTitle());
            txtSubTitle.setText(serviceLine.getSub_org().getTitle() + " - " + serviceLine.getSub_org().getOrg().getTitle());

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return view;
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
                    if (serviceLine.toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
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

                }
                notifyDataSetChanged();
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
