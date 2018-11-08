package com.grupoprominente.viatify.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.grupoprominente.viatify.R;
import com.grupoprominente.viatify.adapters.TravelAdapter;
import com.grupoprominente.viatify.constants.AppConstants;
import com.grupoprominente.viatify.helpers.DividerItemDecoration;
import com.grupoprominente.viatify.model.Travel;
import com.grupoprominente.viatify.sqlite.database.DatabaseHelper;

public class TravelActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, TravelAdapter.TravelAdapterListener {
    private List<Travel> travels = new ArrayList<>();
    private RecyclerView recyclerView;
    private TravelAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private DatabaseHelper db;
    private int[] selectedViaticsIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        Toolbar toolbar = findViewById(R.id.toolbar_bar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(this);

        recyclerView = findViewById(R   .id.recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        mAdapter = new TravelAdapter(this, travels, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        Intent mIntent = getIntent();
        selectedViaticsIds = mIntent.getIntArrayExtra(AppConstants.SELECTED_VIATICS);

        actionModeCallback = new ActionModeCallback();

        // show loader and fetch messages
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        getInbox();
                    }
                }
        );
    }
    @Override
    protected void onResume(){
        super.onResume();
        getInbox();
    }
    private void getInbox() {
        swipeRefreshLayout.setRefreshing(true);
        if (db != null) {
            travels.clear();
            List<Travel> lstViatics = db.getAllTravel();

            if (lstViatics.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Lista de Viajes Vacia ", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            } else {
                for (Travel travel : lstViatics) {
                    travel.setColor(getRandomMaterialColor("400"));
                    travels.add(travel);
                }
                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }

    }

    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = getResources().getIdentifier("mdcolor_" + typeColor, "array", getPackageName());

        if (arrayId != 0) {
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }

    @Override
    public void onRefresh() {
        getInbox();
    }

    @Override
    public void onIconClicked(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }


    @Override
    public void onTravelRowClicked(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } if (count ==1){
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }


    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode_travel, menu);

            swipeRefreshLayout.setEnabled(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_send:
                    deleteSelectedViatics();
                    deleteTravels();
                    finish();
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelections();
            swipeRefreshLayout.setEnabled(true);
            actionMode = null;
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.resetAnimationIndex();
                }
            });
        }
    }

    private void deleteTravels() {
        mAdapter.resetAnimationIndex();
        List<Integer> selectedItemPositions =
                mAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            Travel travel = travels.get(selectedItemPositions.get(i));
            db.deleteTravel(travel);
            mAdapter.removeData(selectedItemPositions.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }

    private void deleteSelectedViatics() {
        if ( selectedViaticsIds.length > 0 ){
            for (int i = selectedViaticsIds.length - 1; i >= 0; i--) {
                db.deleteViaticById(selectedViaticsIds[i]);
            }
        }
    }
}
