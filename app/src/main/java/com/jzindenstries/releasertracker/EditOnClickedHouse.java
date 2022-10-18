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

public class EditOnClickedHouse {
    Button toAction;
    EditText ActionNameAnswer, inspectorDateAnswer, releaserSerialNumber2, returnDateAnswer, inspectorNameAnswer;
    TextView ReleaserTitleButton;
    Spinner releaserTypeSpinner;
    private DatePickerDialog.OnDateSetListener mDateSetListener1;
    public void showEditOnClickedHouse(Activity activity, ArrayList<GoToHouse> dataHouse, int position, final DialogBeforeComingBack.onAddOrRemove listener){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_edit_house);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSlide;



        inspectorNameAnswer = dialog.findViewById(R.id.inspectorNameAnswer);
        inspectorDateAnswer = dialog.findViewById(R.id.inspectorDateAnswer);
        ReleaserTitleButton = dialog.findViewById(R.id.ReleaserTitleButton);
        ReleaserTitleButton.setPaintFlags(ReleaserTitleButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        releaserSerialNumber2 = dialog.findViewById(R.id.serialNumberAnswer);
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
                inspectorDateAnswer.setText(date);
            }
        };

        String s = dataHouse.get(position).type;
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
        releaserSerialNumber2.setText(String.valueOf(dataHouse.get(position).id));
        inspectorNameAnswer.setText(dataHouse.get(position).date);
        inspectorDateAnswer.setText(dataHouse.get(position).name);


        toAction = (Button) dialog.findViewById(R.id.toAction);
        toAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = inspectorNameAnswer.getText().toString();
                String Date = inspectorDateAnswer.getText().toString();
                String Type = releaserTypeSpinner.getSelectedItem().toString();
                String SerialNumber = releaserSerialNumber2.getText().toString();

                if(Name.equals("") || SerialNumber.equals("")){
                    inspectorNameAnswer.setHintTextColor(Color.RED);
                    inspectorNameAnswer.setHint("שדה חובה");
                    returnDateAnswer.setHintTextColor(Color.RED);
                    returnDateAnswer.setHint("שדה חובה");
                    releaserSerialNumber2.setHintTextColor(Color.RED);
                    releaserSerialNumber2.setHint("שדה חובה");
                    Toast.makeText(activity, "אנא מלא את כלל השדות", Toast.LENGTH_SHORT).show();

                }else {
                    try{
                        DataBaseHelperInHouse dataBaseHelperInHouse1 = new DataBaseHelperInHouse(activity);
                        dataBaseHelperInHouse1.deleteTitle(String.valueOf(dataHouse.get(position).id));
                        dataHouse.remove(position);
                        DataBaseHelperInHouse dataBaseHelperInHouse = new DataBaseHelperInHouse(activity);
                        GoToHouse releaserClass = new GoToHouse(Type, Integer.parseInt(SerialNumber), Name, Date);
                        dataBaseHelperInHouse.addOne(releaserClass); // ADDING
                        Toast.makeText(activity, "השינוי בוצע בהצלחה!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    catch(Exception e) {
                        Toast.makeText(activity, "נא הוסף מספר סידורי", Toast.LENGTH_SHORT).show();

                    }
                }



                MainActivity.getmInstanceActivity().dataBaseHelperInHouse = new DataBaseHelperInHouse(activity);
                List<GoToHouse> everyone2 = MainActivity.getmInstanceActivity().dataBaseHelperInHouse.getEveryone();
                MainActivity.getmInstanceActivity().releaserButtonAdapterInHouse  = new ReleaserButtonAdapterInHouse(activity, (ArrayList<GoToHouse>) everyone2);
                MainActivity.getmInstanceActivity().recyclerViewInHouse.setAdapter(MainActivity.getmInstanceActivity().releaserButtonAdapterInHouse);
                MainActivity.getmInstanceActivity().recyclerViewInHouse.setLayoutManager(new LinearLayoutManager(activity));


            }
        });


        dialog.show();
    }

    public interface onAddOrRemove{
        void onClicked();
    }

}