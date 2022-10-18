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

import com.jzindenstries.releasertracker.DataBaseHelperInHouse;
import com.jzindenstries.releasertracker.DataBaseHelperInProgress;
import com.jzindenstries.releasertracker.R;

import java.util.ArrayList;
import java.util.Calendar;

public class DialogBeforeComingBack {
    Button toAction;
    EditText inspectorNameAnswer, inspectorDateAnswer;
    TextView ReleaserTitleButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public void showDialogBeforeComingBack(Activity activity, ArrayList<GoToProgress> dataProgress, int position, final DialogBeforeComingBack.onAddOrRemove listener){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_before_coming_back);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSlide;

        inspectorNameAnswer = dialog.findViewById(R.id.inspectorNameAnswer);
        ReleaserTitleButton = dialog.findViewById(R.id.ReleaserTitleButton);
        ReleaserTitleButton.setPaintFlags(ReleaserTitleButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        inspectorDateAnswer = dialog.findViewById(R.id.takerDateAnswer);

        inspectorDateAnswer.setOnClickListener(new View.OnClickListener() {
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
                inspectorDateAnswer.setText(date);
            }
        };

        toAction = (Button) dialog.findViewById(R.id.toAction);
        toAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = inspectorNameAnswer.getText().toString();
                String Date = inspectorDateAnswer.getText().toString();
                String Type = dataProgress.get(position).type;
                int SerialNumber = dataProgress.get(position).id;
                if(Name.equals("") || Date.equals("")){
                    inspectorNameAnswer.setHintTextColor(Color.RED);
                    inspectorNameAnswer.setHint("שדה חובה");
                    inspectorDateAnswer.setHintTextColor(Color.RED);
                    inspectorDateAnswer.setHint("שדה חובה");
                    Toast.makeText(activity, "אנא מלא את כלל השדות", Toast.LENGTH_SHORT).show();

                }else {
                    try{
                        DataBaseHelperInHouse dataBaseHelperInHouse = new DataBaseHelperInHouse(activity);
                        GoToHouse releaserClass = new GoToHouse(Type, SerialNumber, Name, Date, true);
                        dataBaseHelperInHouse.addOne(releaserClass);
                        DataBaseHelperInProgress dataBaseHelperInProgress = new DataBaseHelperInProgress(activity);
                        dataBaseHelperInProgress.deleteTitle(String.valueOf(dataProgress.get(position).id));
                        listener.onClicked();
                        dataProgress.remove(position);
                        Toast.makeText(activity, "המנתק חזר מביצוע בהצלחה!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    catch(Exception e) {
                        Toast.makeText(activity, "נא הוסף מספר סידורי", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });


        dialog.show();
    }

    public interface onAddOrRemove{
        void onClicked();
    }

}