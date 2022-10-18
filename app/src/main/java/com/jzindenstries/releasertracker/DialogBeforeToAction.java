package com.jzindenstries.releasertracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class DialogBeforeToAction {
    Button toAction;
    EditText takerDateAnswer, actionNameAnswer, takerNameAnswer;
    TextView ReleaserTitleButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public void showDialogBeforeToAction(Activity activity, ArrayList<GoToHouse> dataHouse, int position, final DialogBeforeToAction.onAddOrRemove listener){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_before_to_action);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSlide;

        actionNameAnswer = dialog.findViewById(R.id.inspectorNameAnswer);
        takerNameAnswer = dialog.findViewById(R.id.takerNameAnswer);
        ReleaserTitleButton = dialog.findViewById(R.id.ReleaserTitleButton);
        ReleaserTitleButton.setPaintFlags(ReleaserTitleButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        takerDateAnswer = dialog.findViewById(R.id.takerDateAnswer);

        takerDateAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        activity,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = day + "/" + month + "/" + year;
                takerDateAnswer.setText(date);
            }
        };

        toAction = (Button) dialog.findViewById(R.id.toAction);
        toAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ActionMame = actionNameAnswer.getText().toString();
                String takerName = takerNameAnswer.getText().toString();
                String takerDate = takerDateAnswer.getText().toString();
                String Type = dataHouse.get(position).type;
                int SerialNumber = dataHouse.get(position).id;
                String inspectorName = dataHouse.get(position).name;
                String inspectorDate = dataHouse.get(position).date;

                if(ActionMame.equals("") || takerName.equals("") || takerDate.equals("")){
                    actionNameAnswer.setHintTextColor(Color.RED);
                    actionNameAnswer.setHint("שדה חובה");
                    takerNameAnswer.setHintTextColor(Color.RED);
                    takerNameAnswer.setHint("שדה חובה");
                    takerDateAnswer.setHintTextColor(Color.RED);
                    takerDateAnswer.setHint("שדה חובה");
                    Toast.makeText(activity, "אנא מלא את כלל השדות", Toast.LENGTH_SHORT).show();

                }else {
                    try{
                        DataBaseHelperInAction dataBaseHelperInAction = new DataBaseHelperInAction(activity);
                        GoToAction releaserClass = new GoToAction(Type, SerialNumber, ActionMame, takerName, takerDate, inspectorName, inspectorDate);
                        dataBaseHelperInAction.addOne(releaserClass); // ADDING
                        DataBaseHelperInHouse dataBaseHelperInHouse = new DataBaseHelperInHouse(activity);
                        dataBaseHelperInHouse.deleteTitle(String.valueOf(dataHouse.get(position).id));
                        listener.onClicked();
                        dataHouse.remove(position);
                        Toast.makeText(activity, "המנתק יצא לביצוע!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    catch(Exception e) {
                        Toast.makeText(activity, "נא השלם את כל השדות", Toast.LENGTH_SHORT).show();

                    }}


            }
        });


        dialog.show();
    }

    public interface onAddOrRemove{
        void onClicked();
    }

}
