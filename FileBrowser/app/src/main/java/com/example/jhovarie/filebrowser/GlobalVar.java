package com.example.jhovarie.filebrowser;

import android.graphics.Bitmap;

/**
 * Created by jhovarie on 14/01/2017.
 */

public class GlobalVar {
    private static Bitmap bmp;
    public static void setBitmap(Bitmap b) {
        bmp = b;
    }

    public static Bitmap getExtractedFrame(){
        return bmp;
    }

}

