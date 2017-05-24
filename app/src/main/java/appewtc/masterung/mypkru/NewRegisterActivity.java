package appewtc.masterung.mypkru;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import static android.R.attr.data;

public class NewRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    //Expliciot
    private EditText nameEditText, userEditText, passwordEditText;
    private ImageView backImageview, humenImageView, cameraImageView;
    private Button button;
    private Uri humanUri, cameraUri;
    private String pathImageSting, nameImageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register);

        //Initial View
        initialView();

        //Controller
        controller();
    } // Main Method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //For Humen
        if ((requestCode == 0) &&(resultCode == RESULT_OK)) {
            Log.d("24MayV1", "Humen OK");


            //Show Image
            cameraUri = data.getData();
            try {

                try {

                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(cameraUri));
                    humenImageView.setImageBitmap(bitmap);

                    findPathAnName(cameraUri);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Log.d("24MayV1", "e humanUri ==> " + e.toString());
            }

        }   // IF human
        // For Camera
        if ((requestCode == 1 )&&(resultCode == RESULT_OK)) {
            Log.d("24MayV1", "Camera Result OK");
            // show image
            cameraUri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(cameraUri));
                humenImageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.d("24MayV1", "e camera ==> " + e.toString());
            }

        }//if camera
    }     //on Activity

    private void findPathAnName(Uri uri) {

        String[] strings = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri,strings,null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            pathImageSting = cursor.getString(index);
        } else {
            pathImageSting = uri.getPath();
        }
        Log.d("24MayV1", "Path ==> " + pathImageSting);
    }

    private void controller() {
        backImageview.setOnClickListener(this);
        humenImageView.setOnClickListener(this);
        cameraImageView.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    private void initialView() {
        nameEditText = (EditText) findViewById(R.id.edtName);
        userEditText = (EditText) findViewById(R.id.edtUser);
        passwordEditText = (EditText) findViewById(R.id.edtPassword);
        backImageview = (ImageView) findViewById(R.id.btnBack);
        humenImageView = (ImageView) findViewById(R.id.imvHumen);
        cameraImageView = (ImageView) findViewById(R.id.imvCamera);
        button = (Button) findViewById(R.id.btnRegister);
    }

    @Override
    public void onClick(View v) {

        //For Back
        if (v == backImageview) {
            finish();
        }
        //For Humen
        if (v == humenImageView) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Please Choose App for Choose Image"),0);
        }
        //For camera

        if (v == cameraImageView) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);


        }

    }
} // Main Class
