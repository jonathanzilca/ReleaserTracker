package com.jzindenstries.releasertracker;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Dialog3 {
    Button exitButton;
    TextView ReleaserTitleButton, Amount;
    BarChart barChart;
    int[] counterType = new int[5];
    ArrayList<BarEntry> barEntries= new ArrayList<>();
    ArrayList<PieEntry> pieEntries= new ArrayList<>();
    ArrayList<PieClass> pieClass = new ArrayList<>();
    public void showDialog3(Activity activity, ArrayList<GoToProgress> dataProgress) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_info3);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSlide;

        Amount = dialog.findViewById(R.id.Amount);
        Amount.setText("כמות מנתקים כוללת:  "+dataProgress.size());
        ReleaserTitleButton = dialog.findViewById(R.id.ReleaserTitleButton);
        ReleaserTitleButton.setPaintFlags(ReleaserTitleButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        barChart = dialog.findViewById(R.id.bar_chart);

        for(GoToProgress goToProgress : dataProgress){
            if(goToProgress.getType().contains("FXC")){ counterType[0]++;}
            if(goToProgress.getType().contains("5000")){ counterType[1]++;}
            if(goToProgress.getType().contains("M1")){ counterType[2]++;}
            if(goToProgress.getType().contains("M2")){ counterType[3]++;}
            if(goToProgress.getType().contains("K35")){ counterType[4]++;}
        }
        pieArrayList();

        for(int i =0 ; i < pieClass.size() ; i++){
            BarEntry barEntry = new BarEntry(i,counterType[i]);
            barEntries.add(barEntry);
            PieEntry pieEntry;
            pieEntry = new PieEntry(i,counterType[i]);
            pieEntries.add(pieEntry);


        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(12);
        barDataSet.setDrawValues(true);
        barChart.setData(new BarData(barDataSet));
        barChart.animateY(2000);
        barChart.getDescription().setText("");
        barChart.getDescription().setTextColor(Color.WHITE);

        exitButton = (Button) dialog.findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void pieArrayList(){
        pieClass.add(new PieClass("FXC", counterType[0]));
        pieClass.add(new PieClass("5000", counterType[1]));
        pieClass.add(new PieClass("M1", counterType[2]));
        pieClass.add(new PieClass("M2", counterType[3]));
        pieClass.add(new PieClass("K35", counterType[4]));
    }
}