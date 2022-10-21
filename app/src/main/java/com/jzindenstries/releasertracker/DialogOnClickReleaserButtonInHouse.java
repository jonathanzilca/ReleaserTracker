package com.jzindenstries.releasertracker;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Paint;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DialogOnClickReleaserButtonInHouse {
    TextView ReleaserTitle, Answer1, Answer2, id;
    Button exitButton, edit;
    ImageView releaserImage;
    public void showDialogReleaserButton(Activity activity, ArrayList<GoToHouse> dataHouse , int position, final DialogOnClickReleaserButtonInHouse.onAddOrRemove listener){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_releaser_button_in_house); // layout display
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSlide; //  dialog animation

        // getting data from dataHouse array
        String type = dataHouse.get(position).getType();
        int idNumber = dataHouse.get(position).getId();
        String date = dataHouse.get(position).getDate();
        String name = dataHouse.get(position).getName();


        // define items
        edit = dialog.findViewById(R.id.edit);
        Answer1 = dialog.findViewById(R.id.Answer1);
        Answer2 = dialog.findViewById(R.id.Answer2);
        ReleaserTitle = dialog.findViewById(R.id.ReleaserTitleButton);
        ReleaserTitle.setPaintFlags(ReleaserTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        id = dialog.findViewById(R.id.id);
        id.setPaintFlags(id.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

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

        // setting data from dataHouse array on dialog
        id.setText("מספר סידורי:  "+idNumber );
        ReleaserTitle.setText("מנתק:  "+type);
        Answer1.setText(date);
        Answer2.setText(name);

        // a button to execute releasers to action
        exitButton = (Button) dialog.findViewById(R.id.toAction);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                DialogBeforeToAction dialogBeforeToAction = new DialogBeforeToAction();
                dialogBeforeToAction.showDialogBeforeToAction(activity, dataHouse, position, new DialogBeforeToAction.onAddOrRemove() {
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
                EditOnClickedHouse dialogEditOnClickedHouse = new EditOnClickedHouse();
                dialogEditOnClickedHouse.showEditOnClickedHouse(activity, dataHouse, position, new DialogBeforeComingBack.onAddOrRemove() {
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