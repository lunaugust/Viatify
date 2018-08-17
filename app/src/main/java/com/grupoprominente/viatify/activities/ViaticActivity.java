package com.grupoprominente.viatify.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.support.v7.widget.Toolbar;
import com.grupoprominente.viatify.R;
import com.grupoprominente.viatify.adapters.ArrayRvAdapter;
import com.grupoprominente.viatify.model.Viatic;
import com.grupoprominente.viatify.sqlite.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ViaticActivity extends AppCompatActivity {
    private ArrayRvAdapter mAdapter;
    private List<Viatic> ViaticList = new ArrayList<>();
    private ImageView imgView;
    private EditText txtTitle;
    private EditText txtDescription;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viatic);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(this);

        imgView = (ImageView)findViewById(R.id.imgView);
        FloatingActionButton fabtnAdd = (FloatingActionButton) findViewById(R.id.fabtnAdd);
        fabtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });

        mAdapter = new ArrayRvAdapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }
        };
    }
    private void createViatic(String title, String description, String path) {
        // inserting note in db and getting
        // newly inserted note id
        long id = db.insertViatic(title, description, path);
        finish();
        /*// get the newly inserted note from db
        Viatic n = db.getViatic(id);

        if (n != null) {
            // adding new note to array list at 0 position
            ViaticList.add(0, n);

            // refreshing the list
            mAdapter.notifyDataSetChanged();

        }*/
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imageBitmap);
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

        //noinspection SimplifiableIfStatement
        switch (id) {
            /*case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;*/
            /*case R.id.btnAttach:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
                return true;*/
            case R.id.btnDone:
                txtTitle = (EditText) findViewById(R.id.input_title);
                txtDescription = (EditText) findViewById(R.id.input_desc);
                createViatic(txtTitle.getText().toString(), txtDescription.getText().toString(), "null");
        }

        return super.onOptionsItemSelected(item);
    }
}
