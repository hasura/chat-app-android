package com.example.android.chatapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by amogh on 1/6/17.
 */

public class Profile extends AppCompatActivity {

    EditText name;
    EditText status;
    ImageView picture;
    Button button;

    private static final String DATABASE_NAME = "chatapp";
    private static final int DATABASE_VERSION = 2;

    private static final int CAMERA_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_information_activity);

        UserDetails details;
        final DataBaseHandler db = new DataBaseHandler(Profile.this,DATABASE_NAME,null,DATABASE_VERSION);

        name = (EditText) findViewById(R.id.profile_name);
        status = (EditText) findViewById(R.id.profile_status);
        picture = (ImageView) findViewById(R.id.profile_picture);
        button = (Button) findViewById(R.id.profile_button);

        details = db.getProfile();
        if(details.getId() == Global.user.getId()){
            name.setText(details.getName());
            status.setText(details.getStatus());
            picture.setImageBitmap(Picture.getImage(details.getPicture()));
        }
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable)picture.getDrawable()).getBitmap();
                byte[] image = Picture.getBytes(bitmap);
                UserDetails userDetails = new UserDetails();
                userDetails.setName(name.getText().toString().trim());
                userDetails.setStatus(status.getText().toString().trim());
                userDetails.setPicture(image);
                userDetails.setId(Global.user.getId());


                db.insertUserDetails(userDetails);
                db.close();

                Intent i = new Intent(Profile.this,ContactsActivity.class);
                startActivity(i);
                finish();

            }
        });


}
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        if(requestcode == CAMERA_REQUEST && resultcode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            picture.setImageBitmap(photo);
        }
    }


}
