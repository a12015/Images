package com.tom.images;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

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
        grid.setOnItemClickListener(this);
//        ImageAdapter adapter = new ImageAdapter();
        String[] from = {MediaStore.Images.Media._ID};
        int[] to = {android.R.id.text1};
        ImageCursorAdapter adapter = new ImageCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, from , to);


        grid.setAdapter(adapter);



        Cursor c = getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(MediaStore.Images.Thumbnails._ID));
            String data = c.getString(c.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
            int imageId = c.getInt(c.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID));
            Log.d("TH", id+"/"+data+"/"+imageId);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("ITEM", position+"");
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("POS", position);
        startActivity(intent);
    }

    class ImageCursorAdapter extends SimpleCursorAdapter{
        Cursor c;
        Context context;
        public ImageCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
            super(context, layout, c, from, to);
            this.c = c;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                ImageView img = new ImageView(MainActivity.this);
                c.moveToPosition(position);
                int id = c.getInt(c.getColumnIndex(MediaStore.Images.Media._ID));
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
