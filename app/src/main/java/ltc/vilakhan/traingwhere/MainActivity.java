package ltc.vilakhan.traingwhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // Explicit
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private String[] loginStrings;
    private boolean aBoolean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstance();


    } // Main method

    private void initInstance() {
        // Bind widget
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);
    }

    public void clickSignIn(View view) {
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        if (userString.equals("") || passwordString.equals("")) {
            // Have space
            MyAlert myAlert = new MyAlert(MainActivity.this, getResources().getString(R.string.title_have_space),
                    getResources().getString(R.string.message_have_space), R.drawable.nobita48);
            myAlert.myDialog();
        } else {
            // No space
            try {

                SynUser synUser = new SynUser(MainActivity.this);
                synUser.execute();
                String s = synUser.get();
                Log.d("14decV2", "JSon ==> " + s);

                JSONArray jsonArray = new JSONArray(s);

                loginStrings = new String[4];

                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (userString.equals(jsonObject.getString("User"))) {
                        loginStrings[0] = jsonObject.getString("id");
                        loginStrings[1] = jsonObject.getString("Name");
                        loginStrings[2] = jsonObject.getString("User");
                        loginStrings[3] = jsonObject.getString("Password");
                        aBoolean = false;
                    }
                } // for

                if (aBoolean) {
                    MyAlert myAlert = new MyAlert(MainActivity.this,
                            getResources().getString(R.string.title_user_false),
                            getResources().getString(R.string.message_user_false),
                            R.drawable.rat48);
                    myAlert.myDialog();
                } else if (passwordString.equals(loginStrings[3])) {
                    //Password true
                    Toast.makeText(MainActivity.this, "Welcome " + loginStrings[1],
                            Toast.LENGTH_LONG).show();
                } else {
                    //Password false
                    MyAlert myAlert = new MyAlert(MainActivity.this,
                            getResources().getString(R.string.title_password_false),
                            getResources().getString(R.string.message_pass_false),
                            R.drawable.kon48);
                    myAlert.myDialog();
                }

            } catch (Exception e) {

                Log.d("14decV2", "e Main ==> " + e.toString());
            }

        }
    } // Click sign in

    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

} // Main class
