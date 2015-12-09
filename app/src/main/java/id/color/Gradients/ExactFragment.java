package id.color.Gradients;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//Working with exact Fragment to check if the color matches exactly with any
  //of the color stored in database
public class ExactFragment extends Fragment {

    private Uri uri = TaskContract.CONTENT_URI;
    Cursor cursor;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.exact_fragment, container, false);
        TextView textexact =(TextView)rootView.findViewById(R.id.textexact);
        ImageView exact_imageview =(ImageView)rootView.findViewById(R.id.exact_imageview);
        int hue=0;
        if(( MainActivity.hsv[1]==0.0f && MainActivity.hsv[2]==0.0f)||( MainActivity.hsv[1]==100.0f &&MainActivity.hsv[2]==100.0f))
            hue= 0;
        else
            hue= (int) MainActivity.hsv[0];



       String searchQuery =  TaskContract.Columns.HUE+" = '" +hue
                + "' AND "+TaskContract.Columns.SATURATION+" = '" +(((int)( MainActivity.hsv[1]*100)))
                + "' AND "+TaskContract.Columns.VALUE+" = '" +(((int)( MainActivity.hsv[2]*100)))+"'";
       cursor = getActivity().getContentResolver().query(uri, null, searchQuery, null,null);
       if (cursor != null) {
            if(cursor.getCount()==0){
                textexact.setText("No match found for :\n"
                +TaskContract.Columns.HUE+" = " +(int) MainActivity.hsv[0]
                                + " \n "+TaskContract.Columns.SATURATION+" = " +(((int)( MainActivity.hsv[1]*100)))
                                + " \n "+TaskContract.Columns.VALUE+" = " +(((int)( MainActivity.hsv[2]*100)))   );
                exact_imageview.setBackgroundColor(Color.rgb(MainActivity.dominantColor[0], MainActivity.dominantColor[1], MainActivity.dominantColor[2]));
            }
            else{
                while (cursor.moveToNext()) {
                    textexact.setText("Match found :\n" + "Name :" + cursor.getString(1) + "\n Hue :" + cursor.getString(2)
                                    + "° \n Saturation :" + cursor.getString(3) + "%\n Value :" + cursor.getString(4) + "%"  );
                    exact_imageview.setBackgroundColor(IdentifyColor.domColor);
                }
            }
        }

        return rootView;
    }

}


