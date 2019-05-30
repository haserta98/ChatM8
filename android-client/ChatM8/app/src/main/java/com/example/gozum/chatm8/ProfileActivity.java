package com.example.gozum.chatm8;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gozum.chatm8.controllers.ProfileController;
import com.example.gozum.chatm8.helpers.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ProfileActivity extends AppCompatActivity {
    ImageView profilePhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilePhoto = findViewById(R.id.profilePhoto);

    }

    public void EditProfileImage(View v)
    {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);
    }

    public void Back(View v)
    {
        Intent i = new Intent(this,HomePage.class);
        startActivity(i);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if(resultCode == RESULT_OK){
            Uri selectedImage = imageReturnedIntent.getData();


            TranslateToBase64(selectedImage);

        }
    }


    public void TranslateToBase64(Uri uri)
    {
        String image = "";
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bmp, 500, 500, false);
            image = ConvertBitmapToString(resizedBitmap);

            byte[] decodedString = Base64.decode(image,Base64.DEFAULT);
            profilePhoto.setImageBitmap(BitmapFactory.decodeByteArray(decodedString,0,decodedString.length));
            ProfileController.Instance().SaveImage(this, image, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    Log.d("kardeşim",response.toString());
                }

                @Override
                public void onSuccess(JSONArray array) {

                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        Log.d("kardeşim",String.valueOf(image.length()));
    }

    public static String ConvertBitmapToString(Bitmap bitmap){
        String encodedImage = "";
        byte[] byteFormat;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byteFormat = byteArrayOutputStream.toByteArray();
        encodedImage = Base64.encodeToString(byteFormat,Base64.DEFAULT);

        return encodedImage;
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public void Read()
    {
        ProfileController.Instance().ReadImage(this, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {

            }

            @Override
            public void onSuccess(JSONArray array) {
                byte[] bytes = array.toString().getBytes();
                byte[] decodedString = Base64.decode(bytes,Base64.DEFAULT);
                profilePhoto.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0,decodedString.length));
                Log.d("kardeş",String.valueOf(decodedString.length));
            }
        });
    }
}
