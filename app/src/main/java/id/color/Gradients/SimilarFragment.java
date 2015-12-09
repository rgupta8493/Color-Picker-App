package id.color.Gradients;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

//If the exact color not found search for the similar color having hsv componenets near to it.
public class SimilarFragment  extends Fragment {
    private ListView basecolor_listview_2;

    private ArrayList<String> list_change_1 ;

    private Uri uri = TaskContract.CONTENT_URI;
    Cursor cursor;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        list_change_1 = new ArrayList<>();
        final View rootView = inflater.inflate(R.layout.sim, container, false);
        basecolor_listview_2 = (ListView) rootView.findViewById(R.id.simcolor_listview_1);

        String searchQuery="";
        int startHue,endHue =0;
        if(((int) MainActivity.hsv[0]-50)<0){
            startHue=360-Math.abs((int) MainActivity.hsv[0]-50);
        }
        else
            startHue=((int) MainActivity.hsv[0]-50);

        if(((int) MainActivity.hsv[0]+50)>360){
            endHue=((int) MainActivity.hsv[0]+50)-360;
        }
        else
            endHue=((int) MainActivity.hsv[0]+50);
        if(startHue>endHue){

            searchQuery =  TaskContract.Columns.HUE+" > '" + startHue
                    + "' AND "+TaskContract.Columns.HUE+" < '" + "360.0"+"'"
                    + " AND "+TaskContract.Columns.SATURATION+" > '" + (((((int)( MainActivity.hsv[1]*100))))-5)+"'"
                    + " AND "+TaskContract.Columns.SATURATION+" < '" + (((((int)( MainActivity.hsv[1]*100))))+5)+"'"
                    + " AND "+TaskContract.Columns.VALUE+" > '" + (((((int)( MainActivity.hsv[2]*100))))-5)+"'"
                    + " AND "+TaskContract.Columns.VALUE+" < '" + (((((int)( MainActivity.hsv[2]*100))))+5)+"'"
                    + " OR "+ TaskContract.Columns.HUE+" < '" + endHue
                    + "' AND "+TaskContract.Columns.HUE+" >'" + "0.0"+"'"
                    + " AND "+TaskContract.Columns.SATURATION+" > '" + (((((int)( MainActivity.hsv[1]*100))))-5)+"'"
                    + " AND "+TaskContract.Columns.SATURATION+" < '" + (((((int)( MainActivity.hsv[1]*100))))+5)+"'"
                    + " AND "+TaskContract.Columns.VALUE+" > '" + (((((int)( MainActivity.hsv[2]*100))))-5)+"'"
                    + " AND "+TaskContract.Columns.VALUE+" < '" + (((((int)( MainActivity.hsv[2]*100))))+5)+"'"
            ;
        }
        else{

            searchQuery =  TaskContract.Columns.HUE+" > '" + startHue
                    + "' AND "+TaskContract.Columns.HUE+" < '" + endHue+"'"
                    + " AND "+TaskContract.Columns.SATURATION+" > '" + (((((int)( MainActivity.hsv[1]*100))))-5)+"'"
                    + " AND "+TaskContract.Columns.SATURATION+" < '" + (((((int)( MainActivity.hsv[1]*100))))+5)+"'"
                    + " AND "+TaskContract.Columns.VALUE+" > '" + (((((int)( MainActivity.hsv[2]*100))))-5)+"'"
                    + " AND "+TaskContract.Columns.VALUE+" < '" + (((((int)( MainActivity.hsv[2]*100))))+5)+"'"
            ;
        }


        cursor = getActivity().getContentResolver().query(uri, null, searchQuery, null,null);
        UpLoadColor();

        basecolor_listview_2.setAdapter(new ColorNameAdapter(getActivity(),list_change_1));
        basecolor_listview_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                Toast toast= Toast.makeText(getActivity(),cursor.getString(1)+": "
                        +"Hue :"+cursor.getString(2)+"   Saturation: "+cursor.getString(3)+"    Value :"+cursor.getString(4)
                        ,Toast.LENGTH_LONG);
                float hsv[] =new float[3];
                hsv[0]= Float.parseFloat(cursor.getString(2));
                hsv[1]= Float.parseFloat(cursor.getString(3))/100;
                hsv[2]= Float.parseFloat(cursor.getString(4))/100;

                toast.getView().setBackgroundColor( Color.HSVToColor(hsv));
 toast.show();
            }
        });

        return rootView;
    }
    private final void UpLoadColor() {
        list_change_1 = new ArrayList<>();
        while (cursor.moveToNext()) {
           list_change_1.add( cursor.getString(1) );
           float [] hsv = new float[3];
        }
    }

}


