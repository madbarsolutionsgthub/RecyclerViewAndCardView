package org.bitm.pencilbox.userloginpb5;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mobile App on 5/22/2018.
 */

public class EmployeeRecyclerViewAdapter extends RecyclerView.Adapter<EmployeeRecyclerViewAdapter.EmployeeViewHolder> implements Filterable{

    private List<BaseSalariedEmployee>bses;
    private List<BaseSalariedEmployee>filteredList;

    public EmployeeRecyclerViewAdapter(List<BaseSalariedEmployee> bses) {
        this.bses = bses;
        this.filteredList = bses;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_row,parent,false);
        return new EmployeeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        holder.nameTV.setText(filteredList.get(position).getEmpName());
        holder.desgTV.setText(filteredList.get(position).getEmpDesg());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString();
                if(query.isEmpty()){
                    filteredList = bses;
                }
                List<BaseSalariedEmployee> temp = new ArrayList<>();
                for (BaseSalariedEmployee b : bses) {
                    if (b.getEmpName().toLowerCase().contains(query.toLowerCase())) {
                        temp.add(b);
                    }
                }
                filteredList = temp;
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (List<BaseSalariedEmployee>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder{
        TextView nameTV, desgTV;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.row_empName);
            desgTV = itemView.findViewById(R.id.row_empDesg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   int position = getAdapterPosition();
                    Toast.makeText(v.getContext(), filteredList.get(position).getEmpName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
