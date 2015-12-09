package id.color.Gradients;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
//picking image color in Image color

public class ImageColor {
    static int[] domRGB;

    public ImageColor(Bitmap image)  {
        int height = image.getHeight();
        int width = image.getWidth();

        Map m = new HashMap();

        for (int i = 0; i < width; i++) {

            for (int j = 0; j < height; j++) {

                int rgb = image.getPixel(i, j);
                Integer counter = (Integer) m.get(rgb);
                if (counter == null)
                    counter = 0;
                counter++;
                m.put(rgb, counter);

            }
        }
        int[] colourHex = getMostCommonColour(m);
    }


    public static int[] getMostCommonColour(Map map) {

        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {

                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());

            }

        });

        Map.Entry me = (Map.Entry) list.get(list.size() - 1);
        domRGB = getRGBArr((Integer) me.getKey());
        Log.d("rgb0000", ""+domRGB[0]);
        Log.d("rgb1111", ""+domRGB[1]);
        Log.d("rgb2222222", ""+domRGB[2]);

        return domRGB;
    }


    public static int[] getRGBArr(int pixel) {

        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;

        return new int[]{red, green, blue};

    }


    public int[] returnColour() {
       return domRGB;
    }
}