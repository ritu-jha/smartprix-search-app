package app.Ritu.com.SmartPrix.VolleyImplementation;

/**
 * Created by ritu 
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ritu on 12/11/15.
 * Initialize the class by passing a reference to ImageView and the full constructed url of the api
 * resource.
 * After initialization call loadImage() to load the image in the ImageView
 * If successful call to loadImage() returns true else it returns false
 */
public class ImageLoader {
    final String TAG = "ImageLoader";
    ImageView imageView;
    Bitmap bitmap;
    boolean status = false;
    String url;
    ImageLoaderTask task;

    public ImageLoader(ImageView imageView, String url) {
        this.imageView = imageView;
        this.url = url;
        this.task = new ImageLoaderTask();
    }

    public boolean loadImage() {
        if (!task.isCancelled()) task.cancel(true);
        task.execute(this.url);
        return status;
    }

    private class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(params[0]);
                url.openConnection();
            } catch (MalformedURLException e) {
                Log.d(TAG, e.getLocalizedMessage());

            } catch (IOException ioe) {
                Log.d(TAG, ioe.getLocalizedMessage());
            }

            try {
                HttpURLConnection httpImageResource = (HttpURLConnection) url.openConnection();
                bitmap = BitmapFactory.decodeStream(httpImageResource.getInputStream());
                status = true;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NegativeArraySizeException npe) {
                Log.d(TAG, "Probably the specified url was not decoded properly or the server was down");
                Log.e(TAG, npe.getLocalizedMessage());
            }
            return bitmap;

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null) return;
            else imageView.setImageBitmap(result);
        }

    }
}
