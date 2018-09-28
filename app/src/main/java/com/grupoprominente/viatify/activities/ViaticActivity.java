package com.grupoprominente.viatify.activities;

import android.content.Context;
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
import com.grupoprominente.viatify.adapters.MessagesAdapter;
import com.grupoprominente.viatify.model.Viatic;
import com.grupoprominente.viatify.sqlite.database.DatabaseHelper;
import com.grupoprominente.viatify.helpers.MoneyTextWatcher;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViaticActivity extends AppCompatActivity  {
    private List<Viatic> viatics = new ArrayList<>();
    private MessagesAdapter mAdapter;
    private ImageView imgView;
    private EditText txtTitle;
    private EditText txtDescription;
    private EditText txtAmount;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;


    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viatic);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(this);
        imgView = (ImageView)findViewById(R.id.imgView);
        FloatingActionButton fabtnDone = (FloatingActionButton) findViewById(R.id.fabtnDone);
        fabtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean cancel = false;
                View focusView = null;
                txtTitle = (EditText) findViewById(R.id.input_title);
                txtDescription = (EditText) findViewById(R.id.input_desc);
                txtAmount = (EditText) findViewById(R.id.input_amount);
                if (TextUtils.isEmpty(txtAmount.getText().toString()) ) {
                    txtAmount.setError(getString(R.string.error_field_required));
                    focusView = txtAmount;
                    cancel = true;
                }
                if (TextUtils.isEmpty(txtTitle.getText().toString()) ) {
                    txtTitle.setError(getString(R.string.error_field_required));
                    focusView = txtTitle;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    String sAmount = txtAmount.getText().toString();
                    String cleanString = sAmount.replaceAll("[$,]", "");
                    Double dAmount = Double.parseDouble(cleanString);
                    createViatic(txtTitle.getText().toString(), txtDescription.getText().toString(), dAmount, mCurrentPhotoPath);
                }

            }
        });
        EditText txtAmount = (EditText) findViewById(R.id.input_amount);
        txtAmount.addTextChangedListener(new MoneyTextWatcher(txtAmount));
        //mAdapter = new MessagesAdapter(ViaticList);
    }
    private void createViatic(String title, String description, Double amount, String path) {
        // inserting note in db and getting
        // newly inserted note id
        long id = db.insertViatic(title, description,amount, path);
        finish();
        // get the newly inserted note from db
        /*Viatic n = db.getViatic(id);

        if (n != null) {
            // adding new note to array list at 0 position
            viatics.add(0, n);

            // refreshing the list
            mAdapter.notifyDataSetChanged();

        }*/

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
