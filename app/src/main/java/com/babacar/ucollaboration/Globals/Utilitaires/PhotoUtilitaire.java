package com.babacar.ucollaboration.Globals.Utilitaires;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class PhotoUtilitaire {

    public static byte[] convertBitmapToByte(Bitmap imageBitmap){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
        return stream.toByteArray();
    }

    public static Bitmap convertByteToBitmap(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
