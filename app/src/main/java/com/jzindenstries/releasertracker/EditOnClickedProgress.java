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

public class EditOnClickedProgress {
    Button toAction;
    EditText ActionNameAnswer, inspectorDateAnswer, releaserSerialNumber2, returnDateAnswer;
    TextView ReleaserTitleButton;
    Spinner releaserTypeSpinner;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public void showEditOnClickedProgress(Activity activity, ArrayList<GoToProgress> dataProgress, int position, final DialogBeforeComingBack.onAddOrRemove listener){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_edit_progress);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSlide;



        ActionNameAnswer = dialog.findViewById(R.id.inspectorNameAnswer);
        ReleaserTitleButton = dialog.findViewById(R.id.ReleaserTitleButton);
        ReleaserTitleButton.setPaintFlags(ReleaserTitleButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        releaserSerialNumber2 = dialog.findViewById(R.id.serialNumberAnswer);
        releaserTypeSpinner = (Spinner) dialog.findViewById(R.id.releaserTypeSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, R.array.releaser_type, R.layout.my_selected_is_showed);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.my_selected_item);
        // Apply the adapter to the spinner
        releaserTypeSpinner.setAdapter(adapter);

        String s = dataProgress.get(position).type;
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
        releaserSerialNumber2.setText(String.valueOf(dataProgress.get(position).id));
        ActionNameAnswer.setText(dataProgress.get(position).ActionName);

        toAction = (Button) dialog.findViewById(R.id.toAction);
        toAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ActionName = ActionNameAnswer.getText().toString();
                String returnDate = dataProgress.get(position).returnDate;
                String Type = releaserTypeSpinner.getSelectedItem().toString();
                String SerialNumber = releaserSerialNumber2.getText().toString();

                if(ActionName.equals("") || SerialNumber.equals("")){
                    ActionNameAnswer.setHintTextColor(Color.RED);
                    ActionNameAnswer.setHint("שדה חובה");
                    returnDateAnswer.setHintTextColor(Color.RED);
                    returnDateAnswer.setHint("שדה חובה");
                    releaserSerialNumber2.setHintTextColor(Color.RED);
                    releaserSerialNumber2.setHint("שדה חובה");
                    Toast.makeText(activity, "אנא מלא את כלל השדות", Toast.LENGTH_SHORT).show();

                }else {
                    try{
                        DataBaseHelperInProgress dataBaseHelperInProgress1 = new DataBaseHelperInProgress(activity);
                        dataBaseHelperInProgress1.deleteTitle(String.valueOf(dataProgress.get(position).id));
                        dataProgress.remove(position);
                        DataBaseHelperInProgress dataBaseHelperInProgress = new DataBaseHelperInProgress(activity);
                        GoToProgress releaserClass = new GoToProgress(Type, Integer.parseInt(SerialNumber), ActionName, returnDate);
                        dataBaseHelperInProgress.addOne(releaserClass); // ADDING
                        Toast.makeText(activity, "השינוי בוצע בהצלחה!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    catch(Exception e) {
                        Toast.makeText(activity, "נא הוסף מספר סידורי", Toast.LENGTH_SHORT).show();

                    }
                }



                MainActivity.getmInstanceActivity().dataBaseHelperInProgress = new DataBaseHelperInProgress(activity);
                List<GoToProgress> everyone2 = MainActivity.getmInstanceActivity().dataBaseHelperInProgress.getEveryone();
                MainActivity.getmInstanceActivity().releaserButtonAdapterInProgress  = new ReleaserButtonAdapterInProgress(activity, (ArrayList<GoToProgress>) everyone2);
                MainActivity.getmInstanceActivity().recyclerViewInProgress.setAdapter(MainActivity.getmInstanceActivity().releaserButtonAdapterInProgress);
                MainActivity.getmInstanceActivity().recyclerViewInProgress.setLayoutManager(new LinearLayoutManager(activity));


            }
        });


        dialog.show();
    }

    public interface onAddOrRemove{
        void onClicked();
    }
}