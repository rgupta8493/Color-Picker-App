package id.color.Gradients;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
    private Button colorExplorer , identifyColor ;
    private static final int CAMERA_REQUEST = 1888;
    public static int[] dominantColor;
    public static float[] hsv;
    ProgressDialog ringProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ringProgressDialog = ProgressDialog.show(this, "Please wait ...",	"Loading data ...", true);
        ringProgressDialog.setCancelable(true);

        Uri uri = new TaskContract().CONTENT_URI;
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.clear();

        Resources resources = getResources();
        String[] color_name = resources.getStringArray(R.array.color_name);
        String[] color_hue = resources.getStringArray(R.array.color_hue);
        String[] color_saturation = resources.getStringArray(R.array.color_saturation);
        String[] color_value = resources.getStringArray(R.array.color_value);
        String selection = TaskContract.Columns.HUE + ">?";
        Cursor cursor = getContentResolver().query(uri, null, selection, new String[]{"0"}, null);

        if(cursor!=null && cursor.getCount()>0){
        }
        else {
            for (int i = 0; i < resources.getStringArray(R.array.color_name).length; i++) {
                values.put(TaskContract.Columns.NAME, color_name[i]);
                values.put(TaskContract.Columns.HUE, color_hue[i]);
                values.put(TaskContract.Columns.SATURATION, color_saturation[i]);
                values.put(TaskContract.Columns.VALUE, color_value[i]);
                getApplicationContext().getContentResolver().insert(uri, values);
            }
        }
        ringProgressDialog.dismiss();
        colorExplorer = (Button) findViewById(R.id.colorExplorer);
        identifyColor = (Button) findViewById(R.id.identifyColor);

        colorExplorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ColorExplorer.class));
            }
        });

        identifyColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && resultCode != Activity.RESULT_CANCELED ) {
            if (requestCode == CAMERA_REQUEST ) {
                final Bitmap photo = (Bitmap) data.getExtras().get("data");
                ImageColor ic=  new ImageColor(photo);
                dominantColor =ic.returnColour();
                hsv = new float[3];
                Color.RGBToHSV(dominantColor[0], dominantColor[1], dominantColor[2], hsv);
                startActivity(new Intent(this,IdentifyColor.class));
            }
        }
        else {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

