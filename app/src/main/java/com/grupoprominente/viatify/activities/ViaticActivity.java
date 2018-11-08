package com.grupoprominente.viatify.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.text.TextUtils;

import android.support.v7.widget.Toolbar;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.grupoprominente.viatify.R;
import com.grupoprominente.viatify.adapters.ServiceLineAdapter;
import com.grupoprominente.viatify.constants.AppConstants;
import com.grupoprominente.viatify.data.ServiceLineSerializer;
import com.grupoprominente.viatify.helpers.BinarySearch;
import com.grupoprominente.viatify.model.ServiceLine;
import com.grupoprominente.viatify.model.Viatic;
import com.grupoprominente.viatify.sqlite.database.DatabaseHelper;
import com.grupoprominente.viatify.helpers.MoneyTextWatcher;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViaticActivity extends AppCompatActivity  {
    private ImageView imgView;
    private EditText txtTitle;
    private EditText txtDescription;
    private EditText txtAmount;
    private AutoCompleteTextView txtServiceLine;
    private ServiceLineAdapter serviceLineAdapter;
    private EditText txtDate;
    private EditText txtTime;
    private int selectedServiceLineId;
    private int viaticId;
    private Spinner spCurrency;
    private ArrayAdapter<String> currencyAdapter;
    private String[] currency = {"ARS", "USD"};
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private DatabaseHelper db;
    String mCurrentPhotoPath;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viatic);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DatabaseHelper(this);
        imgView = findViewById(R.id.imgView);
        txtTitle = findViewById(R.id.input_title);
        txtDescription =  findViewById(R.id.input_desc);
        txtAmount = findViewById(R.id.input_amount);
        txtAmount.addTextChangedListener(new MoneyTextWatcher(txtAmount));
        txtServiceLine = findViewById(R.id.input_service_line);
        List<ServiceLine> lstServiceLine = ServiceLineSerializer.getInstance().load(ViaticActivity.this);
        if(lstServiceLine != null){
            serviceLineAdapter = new ServiceLineAdapter(this,R.layout.list_item,lstServiceLine);
        }
        txtServiceLine.setAdapter(serviceLineAdapter);
        txtServiceLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  selectedServiceLineId = serviceLineAdapter.getServiceLineAdapterId(position);
              }
          });
        spCurrency = findViewById(R.id.spiner_currency);
        currencyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, currency);
        spCurrency.setAdapter(currencyAdapter);

        FloatingActionButton fabtnDone = findViewById(R.id.fabtnDone);
        fabtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean cancel = false;
                View focusView = null;
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
                if (TextUtils.isEmpty(txtServiceLine.getText().toString()) ) {
                    txtServiceLine.setError(getString(R.string.error_field_required));
                    focusView = txtServiceLine;
                    cancel = true;
                }
                if (selectedServiceLineId == 0){
                    txtServiceLine.setError(getString(R.string.error_field_required));
                    focusView = txtServiceLine;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    String sAmount = txtAmount.getText().toString();
                    String cleanString = sAmount.replaceAll("[$,]", "");
                    Double dAmount = Double.parseDouble(cleanString);
                    String sTimeStamp = txtDate.getText() + " " + txtTime.getText();
                    if (viaticId != 0)
                    {
                        updateViatic(viaticId, txtTitle.getText().toString(), txtDescription.getText().toString(), dAmount, spCurrency.getSelectedItem().toString(), mCurrentPhotoPath, selectedServiceLineId, sTimeStamp);
                    }
                    else
                    {
                        createViatic(txtTitle.getText().toString(), txtDescription.getText().toString(), dAmount, spCurrency.getSelectedItem().toString(),mCurrentPhotoPath, selectedServiceLineId, sTimeStamp);
                    }
                }
            }
        });

        calendar = Calendar.getInstance();

        txtDate = findViewById(R.id.input_date);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTextDate();
            }

        };

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(ViaticActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
            }
        });

        txtTime = findViewById(R.id.input_time);
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                updateTextTime();
            }
        };

        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tDialog = new TimePickerDialog(ViaticActivity.this,time,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
                tDialog.show();
            }
        });

        Intent mIntent = getIntent();
        viaticId = mIntent.getIntExtra(AppConstants.SELECTED_VIATIC, 0);
        if (viaticId != 0)
        {
            final Viatic viatic = db.getViatic(viaticId);
            txtTitle.setText(viatic.getTitle());
            txtDescription.setText(viatic.getDescription());
            txtAmount.setText(viatic.getAmount().toString());
            spCurrency.setSelection(currencyAdapter.getPosition(viatic.getCurrency()));
            int position = BinarySearch.serviceLinePosition(lstServiceLine, viatic.getServiceline());
            if (position != -1) {
                txtServiceLine.setListSelection(position);
                txtServiceLine.setText(lstServiceLine.get(position).getTitle());
                selectedServiceLineId = viatic.getServiceline();
            }
            mCurrentPhotoPath = viatic.getImgpath();
            if (mCurrentPhotoPath != null) {
                Uri uriImg = Uri.parse(mCurrentPhotoPath);
                imgView.setImageURI(uriImg);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT);
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(viatic.getTimestamp());
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            calendar.setTime(convertedDate);
        }

        updateTextDate();
        updateTextTime();
    }

    protected void onResume(){
        super.onResume();
    }

    private void updateTextDate() {
        String myFormat = AppConstants.DATE_FORMAT;
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        txtDate.setText(sdf.format(calendar.getTime()));
    }

    private void updateTextTime() {
        String myFormat = AppConstants.TIME_FORMAT;
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        txtTime.setText(sdf.format(calendar.getTime()));
    }

    private void createViatic(String title, String description, Double amount, String currency ,String path, int serviceLineId, String timeStamp) {

        long id = db.insertViatic(title, description,amount, currency, path, serviceLineId, timeStamp);
        finish();
    }

    private void updateViatic(int id, String title, String description, Double amount, String currency, String path, int serviceLineId, String timeStamp) {
        Viatic viatic = new Viatic(id,title,description,amount, currency,timeStamp,path,serviceLineId);
        db.updateViatic(viatic);
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
