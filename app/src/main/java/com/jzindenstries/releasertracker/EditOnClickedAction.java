package com.jzindenstries.releasertracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditOnClickedAction {
    Button toAction;
    EditText inspectorNameAnswer, inspectorDateAnswer, serialNumberAnswer, actionNameAnswer, takerNameAnswer, takerDateAnswer;
    TextView ReleaserTitleButton;
    Spinner releaserTypeSpinner;
    private DatePickerDialog.OnDateSetListener mDateSetListener, mDateSetListener1;
    public void showEditOnClickedAction(Activity activity, ArrayList<GoToAction> dataAction, int position, final DialogBeforeComingBack.onAddOrRemove listener){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_edit_action);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSlide;



        serialNumberAnswer = dialog.findViewById(R.id.serialNumberAnswer);
        actionNameAnswer = dialog.findViewById(R.id.actionNameAnswer);
        takerNameAnswer = dialog.findViewById(R.id.takerNameAnswer);
        takerDateAnswer= dialog.findViewById(R.id.takerDateAnswer);
        inspectorNameAnswer= dialog.findViewById(R.id.inspectorNameAnswer);
        inspectorDateAnswer= dialog.findViewById(R.id.inspectorDateAnswer);
        releaserTypeSpinner = (Spinner) dialog.findViewById(R.id.releaserTypeSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, R.array.releaser_type, R.layout.my_selected_is_showed);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.my_selected_item);
        // Apply the adapter to the spinner
        releaserTypeSpinner.setAdapter(adapter);

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
                        mDateSetListener1,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = day + "/" + month + "/" + year;
                takerDateAnswer.setText(date);
            }
        };



        String s = dataAction.get(position).type;
        int releaserPosition = 0;
        switch (s) {
            case "FXC":
                releaserPosition = 0;
                break;
            case "5000":
                releaserPosition = 1;
                break;
            case "M1":
                releaserPosition = 2;
                break;
            case "M2":
                releaserPosition = 3;
                break;
            case "K35":
                releaserPosition = 4;
                break;
        }
        releaserTypeSpinner.setSelection(releaserPosition);
        serialNumberAnswer.setText(String.valueOf(dataAction.get(position).id));
        actionNameAnswer.setText(dataAction.get(position).actionName);
        takerNameAnswer.setText(dataAction.get(position).takerName);
        takerDateAnswer.setText(dataAction.get(position).takerDate);
        inspectorNameAnswer.setText(dataAction.get(position).inspectorDate);
        inspectorDateAnswer.setText(dataAction.get(position).inspectorName);

        toAction = (Button) dialog.findViewById(R.id.toAction);
        toAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Type = releaserTypeSpinner.getSelectedItem().toString();
                String SerialNumber = serialNumberAnswer.getText().toString();
                String ActionName = actionNameAnswer.getText().toString();
                String TakerName = takerNameAnswer.getText().toString();
                String TakerDate = takerDateAnswer.getText().toString();
                String inspectorName = inspectorNameAnswer.getText().toString();
                String inspectorDate = inspectorDateAnswer.getText().toString();


                if(ActionName.equals("") || SerialNumber.equals("") ||
                        TakerName.equals("") || TakerDate.equals("") || inspectorName.equals("") || inspectorDate.equals("")){
                    serialNumberAnswer.setHintTextColor(Color.RED);
                    serialNumberAnswer.setHint("שדה חובה");
                    actionNameAnswer.setHintTextColor(Color.RED);
                    actionNameAnswer.setHint("שדה חובה");
                    takerNameAnswer.setHintTextColor(Color.RED);
                    takerNameAnswer.setHint("שדה חובה");
                    takerDateAnswer.setHintTextColor(Color.RED);
                    takerDateAnswer.setHint("שדה חובה");
                    inspectorNameAnswer.setHintTextColor(Color.RED);
                    inspectorNameAnswer.setHint("שדה חובה");
                    inspectorDateAnswer.setHintTextColor(Color.RED);
                    inspectorDateAnswer.setHint("שדה חובה");
                    Toast.makeText(activity, "אנא מלא את כלל השדות", Toast.LENGTH_SHORT).show();

                }else {
                    try{
                        DataBaseHelperInAction dataBaseHelperInAction1 = new DataBaseHelperInAction(activity);
                        dataBaseHelperInAction1.deleteTitle(String.valueOf(dataAction.get(position).id));
                        dataAction.remove(position);
                        DataBaseHelperInAction dataBaseHelperInAction = new DataBaseHelperInAction(activity);
                        GoToAction releaserClass = new GoToAction(Type, Integer.parseInt(SerialNumber), ActionName,
                                TakerName, TakerDate, inspectorDate, inspectorName);
                        dataBaseHelperInAction.addOne(releaserClass); // ADDING
                        Toast.makeText(activity, "השינוי בוצע בהצלחה!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    catch(Exception e) {
                        Toast.makeText(activity, "נא הוסף מספר סידורי", Toast.LENGTH_SHORT).show();

                    }
                }



                MainActivity.getmInstanceActivity().dataBaseHelperInAction = new DataBaseHelperInAction(activity);
                List<GoToAction> everyone2 = MainActivity.getmInstanceActivity().dataBaseHelperInAction.getEveryone();
                MainActivity.getmInstanceActivity().releaserButtonAdapterInAction  = new ReleaserButtonAdapterInAction(activity, (ArrayList<GoToAction>) everyone2);
                MainActivity.getmInstanceActivity().recyclerViewInAction.setAdapter(MainActivity.getmInstanceActivity().releaserButtonAdapterInAction);
                MainActivity.getmInstanceActivity().recyclerViewInAction.setLayoutManager(new LinearLayoutManager(activity));


            }
        });


        dialog.show();
    }

    public interface onAddOrRemove{
        void onClicked();
    }
}