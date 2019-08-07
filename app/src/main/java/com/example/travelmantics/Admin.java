package com.example.travelmantics;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class Admin extends AppCompatActivity {
    private static final int REQUEST_GET_SINGLE_FILE = 111;
    StorageReference storageRef;
    String imgString;
    private DatabaseReference mDatabase;
    EditText location, price, resort_name;
    ImageView image;
    Uri selectedImage;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        image = findViewById(R.id.image);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        location = findViewById(R.id.location);
        price = findViewById(R.id.price);
        resort_name = findViewById(R.id.resort_name);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
    }

    public void Imager(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GET_SINGLE_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            if (resultCode == RESULT_OK) {
//                if (requestCode == REQUEST_GET_SINGLE_FILE) {
//                    Uri selectedImageUri = data.getData();
//                    // Get the path from the Uri
//                    final String path = getPathFromURI(this ,selectedImageUri);
//                    Log.e("sam" , "The selected uri path is " + selectedImageUri);
//
//                    if (path != null) {
//                        File f = new File(path);
//                        selectedImageUri = Uri.fromFile(f);
//                        Log.e("sam" , "The image path is " + path);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            Log.e("FileSelectorActivity", "File select error", e);
//        }
        if (resultCode == Activity.RESULT_OK)
            if (requestCode == REQUEST_GET_SINGLE_FILE) {//data.getData returns the content URI for the selected Image
                selectedImage = data.getData();
                image.setImageURI(selectedImage);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null,
                        null);
                assert cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgString = cursor.getString(columnIndex);
                Log.e("sam", "the image name is " + imgString);
                cursor.close();
            }
    }

    public void upload(View view) {
        Uri file = Uri.fromFile(new File(imgString));
        String last_path = file.getLastPathSegment();
        StorageReference riversRef = storageRef.child("images/" + last_path);
        UploadTask uploadTask = riversRef.putFile(selectedImage);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("sam", "Failed upload");
                Log.e("sam", exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("sam", "Successful upload");
            }
        });
        String travellocation = location.getText().toString();
        String travelname = resort_name.getText().toString();
        int travelprice = Integer.parseInt(price.getText().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int mSec = calendar.get(Calendar.MILLISECOND);
        Travel travel = new Travel(last_path, travellocation, travelname, travelprice);
        mDatabase.child("Destinations").child(uid).child(String.valueOf(mSec)).setValue(travel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(Admin.this, Home.class));
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("user", "Failed entry");
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}