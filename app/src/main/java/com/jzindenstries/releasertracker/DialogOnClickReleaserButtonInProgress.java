package com.jzindenstries.releasertracker;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DialogOnClickReleaserButtonInProgress {
    TextView ReleaserTitle, id, Answer1, Answer2;
    Button exitButton, edit;
    ImageView releaserImage;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showDialogOnClickReleaserButtonInProgress(Activity activity, ArrayList<GoToProgress> dataProgress , int position, final DialogOnClickReleaserButtonInProgress.onAddOrRemove listener){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_releaser_buttom_in_progress); // layout display
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSlide; //  dialog animation

        // getting data from dataAction array
        String type = dataProgress.get(position).getType();
        int idNumber = dataProgress.get(position).getId();
        String actionName = dataProgress.get(position).getActionName();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String returnDate = dtf.format(now);

        // define items
        ReleaserTitle = dialog.findViewById(R.id.ReleaserTitleButton);
        ReleaserTitle.setPaintFlags(ReleaserTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        id = dialog.findViewById(R.id.id);
        id.setPaintFlags(id.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Answer1 = dialog.findViewById(R.id.Answer1);
        Answer2 = dialog.findViewById(R.id.Answer2);
        releaserImage= dialog.findViewById(R.id.releaserImage);

        if(type.contains("F")){
            releaserImage.setImageResource(R.drawable.good_fxc);
            releaserImage.getLayoutParams().height = 480;
            releaserImage.getLayoutParams().width = 480;
            releaserImage.requestLayout();
        } else if(type.contains("2")){
            releaserImage.setImageResource(R.drawable.good_m2);
            releaserImage.getLayoutParams().height = 850;
            releaserImage.getLayoutParams().width = 850;
            releaserImage.requestLayout();
        } else{
            releaserImage.setImageResource(R.drawable.good_m1);
            releaserImage.getLayoutParams().height = 730;
            releaserImage.getLayoutParams().width = 730;
            releaserImage.requestLayout();
        }

        // setting data from dataAction array on dialog
        ReleaserTitle.setText("מנתק:  " + type);
        id.setText("מספר סידורי:  " + idNumber );
        Answer1.setText(actionName);
        Answer2.setText(returnDate);

        // a button to execute releasers to progress
        exitButton = (Button) dialog.findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                DialogBeforeComingBack dialogBeforeComingBack = new DialogBeforeComingBack();
                dialogBeforeComingBack.showDialogBeforeComingBack(activity, dataProgress, position, new DialogBeforeComingBack.onAddOrRemove() {
                    @Override
                    public void onClicked() {
                        listener.onClicked();

                    }
                });

            }
        });

        edit = (Button) dialog.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                EditOnClickedProgress dialogEditOnClickedProgress = new EditOnClickedProgress();
                dialogEditOnClickedProgress.showEditOnClickedProgress(activity, dataProgress, position, new DialogBeforeComingBack.onAddOrRemove() {
                    @Override
                    public void onClicked() {
                        listener.onClicked();

                    }
                });

            }
        });


        dialog.show();
    }

    public interface onAddOrRemove{
        void onClicked();
    }
}