package com.jzindenstries.releasertracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jzindenstries.releasertracker.R;

import java.util.ArrayList;
import java.util.List;

public class ReleaserButtonAdapterInProgress extends RecyclerView.Adapter<ReleaserButtonAdapterInProgress.ReleaserButtonViewHolder>{

    String releaserType[], releaserTypeID[];
    Activity context;
    ArrayList<GoToProgress> dataProgress = new ArrayList<>();
    RecyclerView recyclerView;

    public ReleaserButtonAdapterInProgress(Activity ct, ArrayList<GoToProgress> dataProgress){
        this.context = ct;
        this.dataProgress = dataProgress;
    }

    @NonNull
    @Override
    public ReleaserButtonAdapterInProgress.ReleaserButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycleview_button,parent, false);
        return new ReleaserButtonAdapterInProgress.ReleaserButtonViewHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull ReleaserButtonAdapterInProgress.ReleaserButtonViewHolder holder, int position) {
        holder.releaserTypeButton.setText(dataProgress.get(position).getType() + "");
        holder.IDButton.setText(dataProgress.get(position).getId() + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                DialogOnClickReleaserButtonInProgress dialogOnClickReleaserButton = new DialogOnClickReleaserButtonInProgress();
                dialogOnClickReleaserButton.showDialogOnClickReleaserButtonInProgress(context, dataProgress, position, new DialogOnClickReleaserButtonInProgress.onAddOrRemove() {
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
                        DataBaseHelperInProgress dataBaseHelperInProgress = new DataBaseHelperInProgress(context);
                        dataBaseHelperInProgress.deleteTitle(String.valueOf(dataProgress.get(position).id));
                        dataProgress.remove(position);
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
        return dataProgress.size();
    }

    public static class ReleaserButtonViewHolder extends RecyclerView.ViewHolder{

        public TextView releaserTypeButton, IDButton, IDText, recyclerView;
        public ConstraintLayout mainLayout;


        public ReleaserButtonViewHolder(@NonNull View itemView) {
            super(itemView);

            releaserTypeButton = itemView.findViewById(R.id.releaserTypeButton);
            IDButton = itemView.findViewById(R.id.IDButton);
            IDText = itemView.findViewById(R.id.IDText);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }


    }
}