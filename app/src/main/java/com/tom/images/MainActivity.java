package com.tom.images;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class MainActivity extends Activity {

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        /*
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
            //String data = c.getString(c.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
            //int imageId = c.getInt(c.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID));
            //int thumbId = c.getInt(c.getColumnIndex(MediaStore.Images.Media.MINI_THUMB_MAGIC));
            String file = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            Log.d("IMG", id+"/"+file);
        }
        */
        GridView grid = (GridView) findViewById(R.id.grid);
        ImageAdapter adapter = new ImageAdapter();
        grid.setAdapter(adapter);



//        Cursor c = getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
//                null, null, null, null);
        /*while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(MediaStore.Images.Thumbnails._ID));
            String data = c.getString(c.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
            int imageId = c.getInt(c.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID));
            Log.d("TH", id+"/"+data+"/"+imageId);
        }*/
    }

    class ImageAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return cursor.getCount();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                ImageView img = new ImageView(MainActivity.this);
                cursor.moveToPosition(position);
//                String file = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                Uri uri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id+""
                );
                img.setImageURI(uri);
                convertView = img;
            }
            return convertView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
