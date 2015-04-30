package com.tom.images;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;


public class DetailActivity extends Activity implements GestureDetector.OnGestureListener{

    private Cursor cursor;
    private int pos;
    private GestureDetector detector;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detector = new GestureDetector(this, this);

        pos = getIntent().getIntExtra("POS", -1);
        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        cursor.moveToPosition(pos);
        img = (ImageView) findViewById(R.id.img);
        int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
        Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id+"");
        img.setImageURI(uri);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("GEST", "onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("GEST", "onShowPress");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("GEST", "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("GEST", "onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("GEST", "onLongPress");

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2,
                           float velocityX, float velocityY) {
        Log.d("GEST", "onFling");
        float distance = e2.getX()-e1.getX();
        if (distance>100) { //to the RIGHT
            Log.d("ONFLING", "to the right");
            if (cursor.moveToPrevious()){
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id+"");
                img.setImageURI(uri);
            }
        }else if (distance<-100){  // to the LEFT
            Log.d("ONFLING", "to the left");
            if (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id+"");
                img.setImageURI(uri);
            }else{
                cursor.moveToFirst();
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id+"");
                img.setImageURI(uri);
            }
        }
        return false;
    }
}
