package com.example.stephen.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Stephen on 3/9/2016.
 */
public class DoodleView extends View{
    static DoodleView doodleView;
    final Paint paintOut = new Paint();
    ArrayList<Path> paths = new ArrayList<Path>();
    ArrayList<Paint> paints = new ArrayList<Paint>();
    ArrayList<Path> pathsDo = new ArrayList<Path>();
    ArrayList<Paint> paintsDo = new ArrayList<Paint>();

    public DoodleView(Context context){
        super(context);
        this.initialize();

        return;
    }
    public DoodleView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        this.initialize();
        return;
    }
    public DoodleView(Context context, AttributeSet attributeSet, int style){
        super(context, attributeSet, style);
        this.initialize();
        return;
    }
    private void initialize(){
        this.paints.add(this.getPaint());
        this.paths.add(new Path());
        this.setBrush();
        DoodleView.doodleView = this;
        this.paintOut.setColor(Tuning.color);
        this.paintOut.setAlpha(Tuning.alpha);
        this.paintOut.setStrokeWidth(Tuning.width);
        this.paintOut.setStyle(Paint.Style.STROKE);
        return;
    }
    void clear(){
        this.paints = new ArrayList<Paint>();
        this.paths = new ArrayList<Path>();
        this.paints.add(this.getPaint());
        this.paths.add(new Path());
        this.setBrush();
        this.invalidate();
        return;
    }

    private Paint getPaint(){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    void setBrush(){
        this.lastPaint().setColor(Tuning.color);
        this.lastPaint().setAlpha(Tuning.alpha);
        this.lastPaint().setStrokeWidth(Tuning.width);
        return;
    }
    private Path lastPath(){
        return this.paths.get(this.paths.size()-1);
    }

    private Paint lastPaint(){
        return this.paints.get(this.paints.size()-1);
    }
    private boolean purge = false;
    void undo(){
        if(this.paints.size()>1) {
            this.pathsDo.add(this.paths.remove(this.paths.size() - 2));
            this.paintsDo.add( this.paints.remove(this.paints.size() - 2));
            purge = false;
            this.invalidate();
        }
        return;
    }

    void redo(){
        if(this.pathsDo.size()>0){
            this.paths.add(this.paths.size() - 1, this.pathsDo.remove(this.pathsDo.size()-1));
            this.paints.add(this.paints.size() - 1,this.paintsDo.remove(this.paintsDo.size()-1));
            purge = false;
            this.invalidate();
        }
    }

    void invert(){
        for(int i = 0, l = this.paints.size()-1; i < l; i++) {
            Paint paint = this.paints.get(i);

            int argb = paint.getColor();
            int alpha = paint.getAlpha();

            int blue =0xFF - ((argb)&0xFF);
            int green = 0xFF -((argb>>8)&0xFF);
            int red = 0xFF -((argb>>16)&0xFF);

            paint.setARGB(alpha,red,green,blue);
        }

        this.invalidate();
    }


    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if(purge) {
            this.pathsDo.clear();
            this.paintsDo.clear();
        }
        canvas.drawRect(1, 1, this.getWidth()-1, this.getHeight()-1, this.paintOut);
        for(int i = 0, l = this.paints.size(); i < l; i++) {
            canvas.drawPath( this.paths.get(i),  this.paints.get(i));
        }
        return;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        int type = motionEvent.getAction();

        if(type == MotionEvent.ACTION_DOWN){
            lastPath().moveTo(x, y);
        }else if(type == MotionEvent.ACTION_MOVE){
            lastPath().lineTo(x,y);
        }else if(type == MotionEvent.ACTION_UP){
            this.paints.add(this.getPaint());
            this.paths.add(new Path());
            this.setBrush();
        }
        purge = true;
        this.invalidate();
        return true;
    }
}
