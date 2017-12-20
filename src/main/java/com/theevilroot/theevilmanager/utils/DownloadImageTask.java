package com.theevilroot.theevilmanager.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.theevilroot.theevilmanager.R;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    Activity act;

    public DownloadImageTask(ImageView bmImage, Activity activity) {
        this.bmImage = bmImage;
        this.act = activity;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        if(result == null) {
            act.runOnUiThread(() -> bmImage.setImageDrawable(act.getDrawable(R.drawable.unknown)));
        }else {
            bmImage.setImageBitmap(result);
        }
    }
}
