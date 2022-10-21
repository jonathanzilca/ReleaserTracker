package com.jzindenstries.releasertracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    public static WeakReference<MainActivity> weakActivity;
    private final int ID_House = 1;
    private final int ID_Action = 2;
    private final int ID_Progress = 3;
    EditText theFilter;
    TextView workingProgress, in, out;
    Button btnCircle, info1, info2, info3;
    RecyclerView recyclerViewInHouse, recyclerViewInAction, recyclerViewInProgress;
    ReleaserButtonAdapterInHouse releaserButtonAdapterInHouse;
    ReleaserButtonAdapterInAction releaserButtonAdapterInAction;
    ReleaserButtonAdapterInProgress releaserButtonAdapterInProgress;
    DataBaseHelperInHouse dataBaseHelperInHouse;
    DataBaseHelperInAction dataBaseHelperInAction;
    DataBaseHelperInProgress dataBaseHelperInProgress;
    int images[]={R.drawable.good_fxc,R.drawable.good_m2,R.drawable.good_m1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ActionBar actionBar = getSupportActionBar(); // removing the action bar
//        actionBar.hide();
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000"))); // removing the support bar
        getWindow().setStatusBarColor(Color.parseColor("#000000"));
        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale("en"));
        weakActivity = new WeakReference<>(MainActivity.this);
        getData();



        workingProgress = findViewById(R.id.workingProgress);
        workingProgress.setPaintFlags(workingProgress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        in = findViewById(R.id.in);
        in.setPaintFlags(in.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        out = findViewById(R.id.out);
        out.setPaintFlags(out.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        btnCircle = (Button) findViewById(R.id.addReleaser);
        btnCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddingReleaser additionalReleaser = new DialogAddingReleaser();
                additionalReleaser.showDialog(MainActivity.this, new DialogAddingReleaser.onClicked() {
                    @Override
                    public void onClicked(int click){
                        getData();
                    }
                });
            }
        });


        info1 = (Button) findViewById(R.id.info1);
        info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseHelperInAction = new DataBaseHelperInAction(MainActivity.this);
                List<GoToAction> everyone = dataBaseHelperInAction.getEveryone();
                Dialog1 dialog1 = new Dialog1();
                dialog1.showDialog1(MainActivity.this, (ArrayList<GoToAction>) everyone);

            }
        });

        info2 = (Button) findViewById(R.id.info2);
        info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseHelperInHouse = new DataBaseHelperInHouse(MainActivity.this);
                List<GoToHouse> everyone = dataBaseHelperInHouse.getEveryone();
                Dialog2 dialog2 = new Dialog2();
                dialog2.showDialog2(MainActivity.this, (ArrayList<GoToHouse>) everyone);
            }
        });

        info3 = (Button) findViewById(R.id.info3);
        info3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseHelperInProgress = new DataBaseHelperInProgress(MainActivity.this);
                List<GoToProgress> everyone = dataBaseHelperInProgress.getEveryone();
                Dialog3 dialog3 = new Dialog3();
                dialog3.showDialog3(MainActivity.this, (ArrayList<GoToProgress>) everyone);
            }
        });

//        theFilter = findViewById(R.id.search);
//        theFilter.addTextChangedListener((new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                filter(editable.toString());
//
//            }
//        }));
//
//    }
//
//    private void filter(String text){
//        ArrayList<GoToHouse> filteredList = new ArrayList<>();
//        for (GoToHouse goToHouse : dataHouse){
//            if(goToHouse.getType().contains(text)){
//                filteredList.add(goToHouse);
//
//            }
//
//        }
//        releaserButtonAdapterInHouse.filterList(filteredList);
//
//
    }

    private void getData(){
        recyclerViewInHouse = findViewById(R.id.inHouse);
        dataBaseHelperInHouse = new DataBaseHelperInHouse(MainActivity.this);
        List<GoToHouse> everyone = dataBaseHelperInHouse.getEveryone();
        releaserButtonAdapterInHouse = new ReleaserButtonAdapterInHouse(MainActivity.this, (ArrayList<GoToHouse>) everyone);
        recyclerViewInHouse.setAdapter(releaserButtonAdapterInHouse);
        recyclerViewInHouse.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        recyclerViewInAction = findViewById(R.id.inAction);
        dataBaseHelperInAction = new DataBaseHelperInAction(MainActivity.this);
        List<GoToAction> everyone1 = dataBaseHelperInAction.getEveryone();
        releaserButtonAdapterInAction = new ReleaserButtonAdapterInAction(MainActivity.this, (ArrayList<GoToAction>) everyone1);
        recyclerViewInAction.setAdapter(releaserButtonAdapterInAction);
        recyclerViewInAction.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        recyclerViewInProgress = findViewById(R.id.inProgress);
        dataBaseHelperInProgress = new DataBaseHelperInProgress(MainActivity.this);
        List<GoToProgress> everyone2 = dataBaseHelperInProgress.getEveryone();
        releaserButtonAdapterInProgress = new ReleaserButtonAdapterInProgress(MainActivity.this, (ArrayList<GoToProgress>) everyone2);
        recyclerViewInProgress.setAdapter(releaserButtonAdapterInProgress);
        recyclerViewInProgress.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }


    public static MainActivity getmInstanceActivity() {
        return weakActivity.get();
    }


}