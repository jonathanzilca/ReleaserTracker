package com.jzindenstries.releasertracker;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.jzindenstries.releasertracker.DataBaseHelperInAction;
import com.jzindenstries.releasertracker.DataBaseHelperInProgress;
import com.jzindenstries.releasertracker.DialogBeforeComingBack;
import com.jzindenstries.releasertracker.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DialogOnClickReleaserButtonInAction {
    TextView ReleaserTitle, actionNameAnswer, takerNameAnswer, id, takerDateAnswer, inspectorNameAnswer, inspectorDateAnswer;
    Button exitButton, edit;
    public void showDialogReleaserButton(Activity activity, ArrayList<GoToAction> dataAction , int position, final DialogOnClickReleaserButtonInAction.onAddOrRemove listener){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_releaser_button_in_action); // layout display
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSlide; //  dialog animation

        // getting data from dataAction array
        String type = dataAction.get(position).getType();
        int idNumber = dataAction.get(position).getId();
        String actionName = dataAction.get(position).getActionName();
        String takerName = dataAction.get(position).getTakerName();
        String takerDate = dataAction.get(position).getTakerDate();
        String inspectorName = dataAction.get(position).getInspectorName();
        String inspectorDate = dataAction.get(position).getInspectorDate();

        // define items
        ReleaserTitle = dialog.findViewById(R.id.ReleaserTitleButton);
        ReleaserTitle.setPaintFlags(ReleaserTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        id = dialog.findViewById(R.id.id);
        id.setPaintFlags(id.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        actionNameAnswer = dialog.findViewById(R.id.ActionNameAnswer);
        takerNameAnswer = dialog.findViewById(R.id.takerNameAnswer);
        takerDateAnswer = dialog.findViewById(R.id.takerDateAnswer);
        inspectorNameAnswer = dialog.findViewById(R.id.inspectorNameAnswer);
        inspectorDateAnswer = dialog.findViewById(R.id.inspectorDateAnswer);
        edit = dialog.findViewById(R.id.edit);

        // setting data from dataAction array on dialog
        ReleaserTitle.setText("מנתק:  "+type);
        id.setText("מספר סידורי:  "+idNumber );
        actionNameAnswer.setText(actionName);
        takerNameAnswer.setText(takerName);
        takerDateAnswer.setText(takerDate);
        inspectorNameAnswer.setText(inspectorDate);
        inspectorDateAnswer.setText( inspectorName);

        // a button to execute releasers to progress
        exitButton = (Button) dialog.findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String ActionMame = actionNameAnswer.getText().toString();
                String Type = dataAction.get(position).type;
                int SerialNumber = dataAction.get(position).id;
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDateTime now = LocalDateTime.now();
                String returnDate = dtf.format(now);

                try{
                    DataBaseHelperInProgress dataBaseHelperInProgress = new DataBaseHelperInProgress(activity);
                    GoToProgress releaserClass = new GoToProgress(Type, SerialNumber, ActionMame, returnDate);
                    dataBaseHelperInProgress.addOne(releaserClass); // ADDING
                    DataBaseHelperInAction dataBaseHelperInAction = new DataBaseHelperInAction(activity);
                    dataBaseHelperInAction.deleteTitle(String.valueOf(dataAction.get(position).id));
                    dataAction.remove(position);
                    listener.onClicked();
                    dialog.dismiss();
                }
                catch(Exception e) {
                    Toast.makeText(activity, "נא השלם את כל השדות", Toast.LENGTH_SHORT).show();

                }
            }
        });

        edit = (Button) dialog.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                EditOnClickedAction dialogEditOnClickedAction = new EditOnClickedAction();
                dialogEditOnClickedAction.showEditOnClickedAction(activity, dataAction, position, new DialogBeforeComingBack.onAddOrRemove() {
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