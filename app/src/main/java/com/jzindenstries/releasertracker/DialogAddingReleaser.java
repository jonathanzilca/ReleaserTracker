package com.jzindenstries.releasertracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.jzindenstries.releasertracker.DataBaseHelperInHouse;
import com.jzindenstries.releasertracker.DataBaseHelperInProgress;
import com.jzindenstries.releasertracker.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DialogAddingReleaser extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText inspectorExpiration, inspectorName, releaserSerialNumber;
    TextView ReleaserTitleButton;
    Button anotherButton;
    Switch approved;
    Spinner releaserTypeSpinner;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public void showDialog(Activity activity, final onClicked listener){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.additional_releaser);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationFading;


        inspectorExpiration = dialog.findViewById(R.id.inspectorExpiration);
        inspectorName = dialog.findViewById(R.id.inspectorName);
        releaserSerialNumber = dialog.findViewById(R.id.releaserSerialNumber);
        releaserTypeSpinner = (Spinner) dialog.findViewById(R.id.releaserTypeSpinner);
        approved = dialog.findViewById(R.id.approved);
        anotherButton = (Button) dialog.findViewById(R.id.anotherButton);
        ReleaserTitleButton = dialog.findViewById(R.id.ReleaserTitleButton);
        ReleaserTitleButton.setPaintFlags(ReleaserTitleButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        anotherButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String Type = releaserTypeSpinner.getSelectedItem().toString();
                String SerialNumber = releaserSerialNumber.getText().toString();
                String Name = inspectorName.getText().toString();
                String Date = inspectorExpiration.getText().toString();
                Boolean checked = approved.isChecked();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDateTime now = LocalDateTime.now();
                String returnDate = dtf.format(now);

                if(checked){

                    if(Name.equals("") || Date.equals("")){
                        inspectorName.setHintTextColor(Color.RED);
                        inspectorName.setHint(" שדה חובה");
                        inspectorExpiration.setHintTextColor(Color.RED);
                        inspectorExpiration.setHint(" שדה חובה");
                        releaserSerialNumber.setHintTextColor(Color.RED);
                        releaserSerialNumber.setHint(" שדה חובה");
                        Toast.makeText(activity, "אנא מלא את כלל השדות", Toast.LENGTH_SHORT).show();

                    }else {
                        try{
                            DataBaseHelperInHouse dataBaseHelperInHouse = new DataBaseHelperInHouse(activity);
                            GoToHouse releaserClass = new GoToHouse(Type, Integer.parseInt(SerialNumber), Name, Date, true);
                            dataBaseHelperInHouse.addOne(releaserClass);
                            Toast.makeText(activity, "המנתק נוסף למערכת בהצלחה!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        catch(Exception e) {
                            releaserSerialNumber.setHintTextColor(Color.RED);
                            releaserSerialNumber.setHint(" שדה חובה");
                            Toast.makeText(activity, "נא הוסף מספר סידורי", Toast.LENGTH_SHORT).show();

                        }
                    }
                }else {
                    try{
                        DataBaseHelperInProgress dataBaseHelperInProgress = new DataBaseHelperInProgress(activity);
                        GoToProgress releaserClass = new GoToProgress(Type, Integer.parseInt(SerialNumber), "הופשר מהימח", returnDate);
                        dataBaseHelperInProgress.addOne(releaserClass);
                        Toast.makeText(activity, "המנתק נוסף למערכת בהצלחה!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    catch(Exception e) {
                        releaserSerialNumber.setHintTextColor(Color.RED);
                        releaserSerialNumber.setHint(" שדה חובה");
                        Toast.makeText(activity, "נא הוסף מספר סידורי", Toast.LENGTH_SHORT).show();

                    }
                }

                listener.onClicked(0);

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, R.array.releaser_type, R.layout.my_selected_is_showed);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.my_selected_item);
        // Apply the adapter to the spinner
        releaserTypeSpinner.setAdapter(adapter);

        // making the name and the date dependable on spinner switch
        inspectorName.setVisibility(View.GONE);
        inspectorExpiration.setVisibility(View.GONE);
        inspectorName.setVisibility(false ? View.VISIBLE : View.INVISIBLE);
        inspectorExpiration.setVisibility(false ? View.VISIBLE : View.INVISIBLE);
        approved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                inspectorName.setVisibility(true ? View.VISIBLE : View.INVISIBLE);
                inspectorExpiration.setVisibility(true ? View.VISIBLE : View.INVISIBLE);
            }
        });

        inspectorExpiration.setOnClickListener(new View.OnClickListener() {
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
                inspectorExpiration.setText(date);
            }
        };

        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?>adapterView, View view, int i, long l) {
        Spinner releaserTypeSpinner = (Spinner) findViewById(R.id.releaserTypeSpinner);
        releaserTypeSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface onClicked{
        void onClicked(int click);
    }
}