package com.grupoprominente.viatify.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.text.TextUtils;

import android.support.v7.widget.Toolbar;

import com.grupoprominente.viatify.R;
import com.grupoprominente.viatify.adapters.TravelAdapter;
import com.grupoprominente.viatify.model.Travel;
import com.grupoprominente.viatify.sqlite.database.DatabaseHelper;
import com.grupoprominente.viatify.helpers.MoneyTextWatcher;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TravelActivity extends AppCompatActivity  {
    private List<Travel> travels = new ArrayList<>();
    private TravelAdapter tAdapter;
    private ImageView imgView;
    private EditText txtTitle;
    private EditText txtDescription;
    private int travelId;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;


    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(this);
        imgView = findViewById(R.id.imgView);
        txtTitle = findViewById(R.id.input_title);
        txtDescription = findViewById(R.id.input_desc);
        FloatingActionButton fabtnDone = (FloatingActionButton) findViewById(R.id.fabtnDone);
        fabtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean cancel = false;
                View focusView = null;
                cancel = true;

                if (TextUtils.isEmpty(txtTitle.getText().toString()) ) {
                    txtTitle.setError(getString(R.string.error_field_required));
                    focusView = txtTitle;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    if (travelId != 0)
                    {
                        updateTravel(travelId, txtTitle.getText().toString(), txtDescription.getText().toString(), mCurrentPhotoPath); //dAmount,
                    }
                    else
                    {
                        createTravel(txtTitle.getText().toString(), txtDescription.getText().toString(), mCurrentPhotoPath); //dAmount,
                    }

                }
            }
        });

        Intent mIntent = getIntent();
        travelId = mIntent.getIntExtra("travelId", 0);
        if (travelId != 0)
        {
            Travel travel = db.getTravel(travelId);
            txtTitle.setText(travel.getTitle());
            txtDescription.setText(travel.getDescription());
            mCurrentPhotoPath = travel.getImgpath();
            if (mCurrentPhotoPath != null) {
                Uri uriImg = Uri.parse(mCurrentPhotoPath);
                imgView.setImageURI(uriImg);
            }
        }

    }
    private void createTravel(String title, String description, String path) { // Double amount

        long id = db.insertTravel(title, description, path); //,amount
        finish();
    }

    private void updateTravel(int id, String title, String description, String path) { //Double amount,
        Travel travel = new Travel(id,title,description,path); //,amount,"0",
        db.updateTravel(travel);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viatic, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        OutputStream output;
        //noinspection SimplifiableIfStatement
        switch (id) {

            case R.id.btnAttach:
                dispatchTakePictureIntent();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    private void setPic() {
        // Get the dimensions of the View
        int targetW = imgView.getWidth();
        int targetH = imgView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imgView.setImageBitmap(bitmap);
    }





}
