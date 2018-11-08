package com.grupoprominente.viatify.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.grupoprominente.viatify.R;
import com.grupoprominente.viatify.adapters.ViaticsAdapter;
import com.grupoprominente.viatify.constants.AppConstants;
import com.grupoprominente.viatify.helpers.DividerItemDecoration;
import com.grupoprominente.viatify.model.Viatic;
import com.grupoprominente.viatify.sqlite.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ViaticsAdapter.ViaticAdapterListener {
    private List<Viatic> viatics = new ArrayList<>();
    private RecyclerView recyclerView;
    private ViaticsAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViaticActivity.class));
            }
        });

        db = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        mAdapter = new ViaticsAdapter(this, viatics, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

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
            viatics.clear();
            List<Viatic> lstViatics = db.getAllViatics();

            if (lstViatics.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Lista de Viaticos Vacia ", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            } else {
                for (Viatic viatic : lstViatics) {
                    viatic.setColor(getRandomMaterialColor("400"));
                    viatics.add(viatic);
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
    public void onViaticRowClicked(int position) {
        if (mAdapter.getSelectedItemCount() > 0) {
            enableActionMode(position);
        } else {
            Viatic viatic = viatics.get(position);
            viatics.set(position, viatic);
            mAdapter.notifyDataSetChanged();

            Intent intent = new Intent(MainActivity.this, ViaticActivity.class);
            intent.putExtra(AppConstants.SELECTED_VIATIC, viatic.getId());
            startActivity(intent);
        }
    }

    @Override
    public void onRowLongClicked(int position) {
        enableActionMode(position);
    }

    private void enableActionMode(int position) {
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
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }


    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);

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
                case R.id.action_delete:
                    deleteViatics();
                    mode.finish();
                    return true;

                case R.id.action_send:
                    Intent intent = new Intent(MainActivity.this, TravelActivity.class);
                    intent.putExtra(AppConstants.SELECTED_VIATICS, getSelectedViaticsIds());
                    startActivity(intent);
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

    private void deleteViatics() {
        mAdapter.resetAnimationIndex();
        List<Integer> selectedItemPositions =
                mAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            Viatic viatic = viatics.get(selectedItemPositions.get(i));
            db.deleteViatic(viatic);
            mAdapter.removeData(selectedItemPositions.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }

    private int[] getSelectedViaticsIds() {
        List<Integer> selectedItemPositions =
                mAdapter.getSelectedItems();
        int[] seletedIds = new int[selectedItemPositions.size()];
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            Viatic viatic = viatics.get(selectedItemPositions.get(i));
            seletedIds[i] = viatic.getId();
        }
        return seletedIds;
    }
}
