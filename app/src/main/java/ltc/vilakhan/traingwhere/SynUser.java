package ltc.vilakhan.traingwhere;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by Boualy on 12/14/2016.
 */

public class SynUser extends AsyncTask<Void,Void,String>{

    //Explicit
    private Context context;
   // private static final String urlJSON = "http://swiftcodingthai.com/ltc/get_user_ka.php";
   private static final String urlJSON = "http://lao-hosting.com/ltc/get_user_master.php";

    public SynUser(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlJSON).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            Log.d("14decV2", "e doin ==>" + e.toString());
            return null;
        }


    }
} // Main class
