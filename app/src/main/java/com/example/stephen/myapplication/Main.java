package com.example.stephen.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Stephen on 3/21/2016.
 */
public class Main extends Activity {
    Button toBrush;
    Button clear;
    Button undo;
    Button redo;
    Button invert;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.main);

        toBrush = (Button) findViewById(R.id.toBrush);
        toBrush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, BrushOption.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoodleView.doodleView.clear();
            }
        });

        undo = (Button) findViewById(R.id.undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoodleView.doodleView.undo();
            }
        });

        redo = (Button) findViewById(R.id.redo);
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoodleView.doodleView.redo();
            }
        });

        invert = (Button) findViewById(R.id.invert);
        invert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoodleView.doodleView.invert();
            }
        });
    }
}
