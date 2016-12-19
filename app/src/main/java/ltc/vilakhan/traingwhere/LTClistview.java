package ltc.vilakhan.traingwhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

public class LTClistview extends AppCompatActivity {

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ltclistview);

        listView = (ListView) findViewById(R.id.livLTC);

        //Create Listview
        try {
            SynLTC synLTC = new SynLTC(LTClistview.this);
            synLTC.execute();
            String s = synLTC.get();
            Log.d("16decV3", "JSON ==> " + s);

            JSONArray jsonArray = new JSONArray(s);
            String[] nameStrings = new String[jsonArray.length()];
            String[] latStrings = new String[jsonArray.length()];
            String[] lngStrings = new String[jsonArray.length()];
            String[] iconStrings = new String[jsonArray.length()];

            for (int i = 0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nameStrings[i] = jsonObject.getString("NameLogin");
                latStrings[i] = jsonObject.getString("Lat");
                lngStrings[i] = jsonObject.getString("Lng");
                iconStrings[i] = jsonObject.getString("Image");

            } // For

            MyAdapter myAdapter = new MyAdapter(LTClistview.this, nameStrings, latStrings, lngStrings, iconStrings);

            listView.setAdapter(myAdapter);


        } catch (Exception e) {
            Log.d("16decV3", "Listview ==> " + e.toString());
            e.printStackTrace();
        }


    }// Main Method


} // Main class
