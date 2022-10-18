package com.jzindenstries.releasertracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Binder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jzindenstries.releasertracker.DataBaseHelperInAction;
import com.jzindenstries.releasertracker.DataBaseHelperInHouse;
import com.jzindenstries.releasertracker.DataBaseHelperInProgress;
import com.jzindenstries.releasertracker.DialogOnClickReleaserButtonInHouse;
import com.jzindenstries.releasertracker.MainActivity;
import com.jzindenstries.releasertracker.R;
import com.jzindenstries.releasertracker.ReleaserButtonAdapterInAction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ReleaserButtonAdapterInHouse extends RecyclerView.Adapter<ReleaserButtonAdapterInHouse.ReleaserButtonViewHolder>{

    String releaserType[], releaserTypeID[];
    Activity context;
    ArrayList<GoToHouse> dataHouse = new ArrayList<>();
    RecyclerView recyclerView;

    public ReleaserButtonAdapterInHouse(Activity ct, ArrayList<GoToHouse> dataHouse){
        this.context = ct;
        this.dataHouse = dataHouse;
    }

    @NonNull
    @Override
    public ReleaserButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycleview_button,parent, false);
        return new ReleaserButtonViewHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull ReleaserButtonViewHolder holder,  int position) {
        holder.releaserTypeButton.setText(dataHouse.get(position).getType() + "");
        holder.IDButton.setText(dataHouse.get(position).getId() + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogOnClickReleaserButtonInHouse dialogOnClickReleaserButton = new DialogOnClickReleaserButtonInHouse();
                dialogOnClickReleaserButton.showDialogReleaserButton(context, dataHouse, position, new DialogOnClickReleaserButtonInHouse.onAddOrRemove() {
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
                        DataBaseHelperInHouse dataBaseHelperInHouse = new DataBaseHelperInHouse(context);
                        dataBaseHelperInHouse.deleteTitle(String.valueOf(dataHouse.get(position).id));
                        dataHouse.remove(position);
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
        return dataHouse.size();
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

    public class Help extends Binder{

    }
}