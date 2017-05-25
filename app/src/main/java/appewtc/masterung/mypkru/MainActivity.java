package appewtc.masterung.mypkru;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Explicit ประกาศตัวแปร
    private EditText userEditText, passwordEditText;
    private TextView textView;
    private Button button;
    private String userString, passwordString;
    private String[] loginStrings;
    private String[] columnStrings = new String[]{"id","Name","User","Password","Image"};
    private boolean aBoolean = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initial View
        initialView();

        //Controller
        controller();


    }  //Main method

    private void controller() {
        textView.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    private void initialView() {
        userEditText = (EditText) findViewById(R.id.edtUser);
        passwordEditText = (EditText) findViewById(R.id.edtPassword);
        textView = (TextView) findViewById(R.id.txtNewRegister);
        button = (Button) findViewById(R.id.btnLogin);
    }

    @Override
    public void onClick(View v) {
        //For TextView

        if (v == textView) {
            //Intent to NewRegister
            Intent intent = new Intent(MainActivity.this,NewRegisterActivity.class);
            startActivity(intent);

        }

        //For Button

        if (v == button) {
            userString = userEditText.getText().toString().trim();
            passwordString = passwordEditText.getText().toString().trim();

            if (userString.equals(""))|| passwordString.equals("") {

                MyAlert myAlert = new MyAlert(this);
                myAlert.myDialog(getResources().getString(R.string.titleHaveSpace),
                        getResources().getString(R.string.messageHaveSpace));

            }else{

                //No Space

                checkUserAnPass();


                }
        }

    }

    private void checkUserAnPass() {
        String urlPHP = "http://swiftcodingthai.com/pkru/GetUserYew.php";

        try {

            GetAllData getAllData = new GetAllData(this);
            getAllData.execute(urlPHP);

            String strJSON = getAllData.get();
            Log.d("25MayV1", "JSON ==> " + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            loginStrings = new String[columnStrings.length];

            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (userString.equals(jsonObject.getString(columnStrings[2]))) {
                    aBoolean = false;

                    for (int i1 = 0;i1<columnStrings.length;i1++) {
                        loginStrings[i1] = jsonObject.getString(columnStrings[i1]);
                        Log.d("25MayV2", "loginString(" + i1 + ")==>" + loginStrings[i1]);



                    }//for2

                }//if

            }// for 1

            //Check User And Password
            if (aBoolean) {
                //User False
                MyAlert myAlert = new MyAlert(this);
                myAlert.myDialog(getResources().getString(R.string.titleUserFalse),
                        getResources().getString(R.string.messageUserFalse));
            } else if (passwordString.equals(loginStrings[3])) {
                //Password True
                Toast.makeText(MainActivity.this, "Welcome" + loginStrings[1],
                        Toast.LENGTH_SHORT).show();
            } else {
                //Password False
                MyAlert myAlert = new MyAlert(this);
                myAlert.myDialog(getResources().getString(R.string.titlePassword),
                        getResources().getString(R.string.messagePassword));

            }


        } catch (Exception e) {
            Log.d("25MayV1", "e checkUser ==>" + e.toString());
        }
    }
}   //Main Class คลาสหลัก
