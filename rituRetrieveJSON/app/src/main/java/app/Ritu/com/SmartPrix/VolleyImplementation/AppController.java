package app.Ritu.com.SmartPrix.VolleyImplementation;

import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by ritu on 12/11/15.
 */
public class AppController extends MultiDexApplication {

    public static final String TAG = AppController.class   // tag app controller class ka naam
            .getSimpleName();
    /*static public LayerClient layerClient;
    static public Atlas.ParticipantProvider participantProvider;
    static public String AppID = "layer:///apps/staging/de7a9c90-19e2-11e5-9296-5fe97100320a";
*/
    private static AppController mInstance;
    private RequestQueue mRequestQueue;  
    private ImageLoader mImageLoader;

    /*public static String getUserID() {
        return ZPreferences.getUserID(getInstance());
    }
    public void onUserAuthenticated() {
        participantProvider=new ParticipantProvider();
        Log.d("Authenticated", "Success");
    }
*/
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //LayerClient.enableLogging(getApplicationContext());
        /*layerClient = LayerClient.newInstance(this, AppID);
        layerClient.registerAuthenticationListener(new MyAuthenticationListener(this));
        layerClient.connect();

        if (!layerClient.isAuthenticated()) {
            layerClient.authenticate();
        } else {
            onUserAuthenticated();
        }*/
    }

    public RequestQueue getRequestQueue() {        // jo upar request banaya tha wo null hai to ek nayi request banao , agar queue null hai to nayi request banao
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {     
        getRequestQueue();  //queue laye
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,  //agar null tha to naya banao
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {    ///T- template pass karo kyunki kisi bhi type ka request aa skti hai
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag); //TAG=category last line of mainactivity
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {  //kuch pass nhi kiya to phir ye default 
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {   //agar request cancel karna pada aur request null nhi thi
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
