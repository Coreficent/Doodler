package com.example.stephen.myapplication;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

/**
 * Created by Stephen on 3/21/2016.
 */
public class BrushOption extends Activity {
    Button toMain;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.brushoption);
        toMain = (Button) findViewById(R.id.toMain);
        toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoodleView.doodleView.setBrush();
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        GridLayout gridAlpha = (GridLayout) this.findViewById(R.id.gridAlpha);
        HandlerAlpha handlerAlpha = new HandlerAlpha();
        for(int i = 0, l =gridAlpha.getChildCount() ; i < l; i++) {
            View view = gridAlpha.getChildAt(i);
            if(view instanceof Button){
                view.setOnClickListener(handlerAlpha);
            }
        }

        GridLayout gridSize = (GridLayout) this.findViewById(R.id.gridSize);
        HandlerSize handlerSize = new HandlerSize();
        for(int i = 0, l =gridSize.getChildCount() ; i < l; i++) {
            View view = gridSize.getChildAt(i);
            if(view instanceof Button){
                view.setOnClickListener(handlerSize);
            }
        }

        GridLayout gridColor = (GridLayout) this.findViewById(R.id.gridColor);
        HandlerColor handlerColor = new HandlerColor();
        for(int i = 0, l =gridColor.getChildCount() ; i < l; i++) {
            View view = gridColor.getChildAt(i);
            if(view instanceof Button){
                view.setOnClickListener(handlerColor);
            }
        }
    }
    private class HandlerAlpha implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Button button = (Button) view;
            String text =  button.getText().toString();
            Toast.makeText(getApplicationContext(), "Opacity Percent Selected: " + text, Toast.LENGTH_SHORT).show();
            Tuning.alpha =(int) (Integer.parseInt(text)/100.0*255);
        }
    }

    private class HandlerSize implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Button button = (Button) view;

            String text =  button.getText().toString();
            Toast.makeText(getApplicationContext(), "Brush Size Selected: " + text, Toast.LENGTH_SHORT).show();
            Tuning.width =Integer.parseInt(text);
        }
    }

    private class HandlerColor implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Button button = (Button) view;
            ColorDrawable colorDrawable = (ColorDrawable)view.getBackground();
            int color = colorDrawable.getColor();
            Toast.makeText(getApplicationContext(), "New Color Selected", Toast.LENGTH_SHORT).show();
            Tuning.color = color;
        }
    }
}
