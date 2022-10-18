package com.jzindenstries.releasertracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jzindenstries.releasertracker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReleaserButtonAdapterInAction extends RecyclerView.Adapter<ReleaserButtonAdapterInAction.ReleaserButtonViewHolder> implements Filterable {

    Activity context;
    ArrayList<GoToAction> dataAction = new ArrayList<>();
    ArrayList<GoToAction> dataFilter;

    public ReleaserButtonAdapterInAction(ArrayList<GoToAction> dataFilter) {
        this.dataFilter = dataFilter;
        dataFilter = new ArrayList<>(dataAction);
    }

    public ReleaserButtonAdapterInAction(Activity ct, ArrayList<GoToAction> data){
        this.context = ct;
        this.dataAction = data;
    }

    @NonNull
    @Override
    public ReleaserButtonAdapterInAction.ReleaserButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycleview_button,parent, false);
        return new ReleaserButtonAdapterInAction.ReleaserButtonViewHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull ReleaserButtonAdapterInAction.ReleaserButtonViewHolder holder, int position) {
        holder.releaserTypeButton.setText(dataAction.get(position).getType() + "");
        holder.IDButton.setText(dataAction.get(position).getId() + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogOnClickReleaserButtonInAction dialogOnClickReleaserButton = new DialogOnClickReleaserButtonInAction();
                dialogOnClickReleaserButton.showDialogReleaserButton(context, dataAction, position, new DialogOnClickReleaserButtonInAction.onAddOrRemove() {
                    @Override
                    public void onClicked() {
                        MainActivity.getmInstanceActivity().dataBaseHelperInAction = new DataBaseHelperInAction(context);
                        List<GoToAction> everyone = MainActivity.getmInstanceActivity().dataBaseHelperInAction.getEveryone();
                        MainActivity.getmInstanceActivity().releaserButtonAdapterInAction  = new ReleaserButtonAdapterInAction(context, (ArrayList<GoToAction>) everyone);
                        MainActivity.getmInstanceActivity().recyclerViewInAction.setAdapter(MainActivity.getmInstanceActivity().releaserButtonAdapterInAction);
                        MainActivity.getmInstanceActivity().recyclerViewInAction.setLayoutManager(new LinearLayoutManager(context));

                        MainActivity.getmInstanceActivity().dataBaseHelperInHouse = new DataBaseHelperInHouse(context);
                        List<GoToHouse> everyone1 = MainActivity.getmInstanceActivity().dataBaseHelperInHouse.getEveryone();
                        MainActivity.getmInstanceActivity().releaserButtonAdapterInHouse  = new ReleaserButtonAdapterInHouse(context, (ArrayList<GoToHouse>) everyone1);
                        MainActivity.getmInstanceActivity().recyclerViewInHouse.setAdapter(MainActivity.getmInstanceActivity().releaserButtonAdapterInHouse);
                        MainActivity.getmInstanceActivity().recyclerViewInHouse.setLayoutManager(new LinearLayoutManager(context));

                        MainActivity.getmInstanceActivity().dataBaseHelperInProgress = new DataBaseHelperInProgress(context);
                        List<GoToProgress> everyone2 = MainActivity.getmInstanceActivity().dataBaseHelperInProgress.getEveryone();
                        MainActivity.getmInstanceActivity().releaserButtonAdapterInProgress  = new ReleaserButtonAdapterInProgress(context, (ArrayList<GoToProgress>) everyone2);
                        MainActivity.getmInstanceActivity().recyclerViewInProgress.setAdapter(MainActivity.getmInstanceActivity().releaserButtonAdapterInProgress);
                        MainActivity.getmInstanceActivity().recyclerViewInProgress.setLayoutManager(new LinearLayoutManager(context));
                    }
                });
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("האם ברצונך למחוק את המנתק?").setPositiveButton("מחק", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataBaseHelperInAction dataBaseHelperInAction = new DataBaseHelperInAction(context);
                        dataBaseHelperInAction.deleteTitle(String.valueOf(dataAction.get(position).id));
                        dataAction.remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "המנתק נמחק מהמערכת!", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("בטל", null);
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataAction.size();
    }

    public static class ReleaserButtonViewHolder extends RecyclerView.ViewHolder{

        public TextView releaserTypeButton, IDButton, IDText;
        public ConstraintLayout mainLayout;


        public ReleaserButtonViewHolder(@NonNull View itemView) {
            super(itemView);

            releaserTypeButton = itemView.findViewById(R.id.releaserTypeButton);
            IDButton = itemView.findViewById(R.id.IDButton);
            IDText = itemView.findViewById(R.id.IDText);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }


    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<GoToAction> filteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(dataFilter);
            }else {
                int F = Integer.valueOf(charSequence.toString().toLowerCase().trim());

                for(GoToAction item:dataFilter){
                   if(item.getId()==F){
                       filteredList.add(item);

                   }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            dataAction.clear();
            dataAction.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };
}