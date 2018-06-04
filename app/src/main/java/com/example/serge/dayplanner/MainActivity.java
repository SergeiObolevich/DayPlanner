package com.example.serge.dayplanner;

import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.serge.dayplanner.adapter.TabAdapter;
import com.example.serge.dayplanner.alarm.AlarmHelper;
import com.example.serge.dayplanner.database.DBHelper;
import com.example.serge.dayplanner.dialog.AddingTaskDialogFragment;
import com.example.serge.dayplanner.dialog.EditTaskDialogFragment;
import com.example.serge.dayplanner.fragment.CurrentTaskFragment;
import com.example.serge.dayplanner.fragment.DoneTaskFragment;
import com.example.serge.dayplanner.fragment.SplashFragment;
import com.example.serge.dayplanner.fragment.TaskFragment;
import com.example.serge.dayplanner.model.ModelTask;

public class MainActivity extends AppCompatActivity
        implements AddingTaskDialogFragment.AddingTaskListener,
        CurrentTaskFragment.OnTaskDoneListener, DoneTaskFragment.OnTaskRestoreListener,
        EditTaskDialogFragment.EditingTaskListener {

    FragmentManager fragmentManager;
    PreferenceHelper preferenceHelper;
    TabAdapter tabAdapter;

    TaskFragment currentTaskFragment;
    TaskFragment doneTaskFragment;

    SearchView searchView;

    public DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceHelper.getInstance().init(getApplicationContext());
        preferenceHelper = PreferenceHelper.getInstance();

        AlarmHelper.getInstance().init(getApplicationContext());

        dbHelper = new DBHelper(getApplicationContext());

        fragmentManager = getFragmentManager();

        runSplash();

        setUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem splashItem = menu.findItem(R.id.action_splash);
        splashItem.setChecked(preferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_splash) {
            item.setChecked(!item.isChecked());
            preferenceHelper.putBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE, item.isChecked());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void runSplash(){
        if(!preferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE)) {
            SplashFragment splashFragment = new SplashFragment();

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, splashFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void setUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar);
        }

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.current_task));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.done_task));

        final ViewPager viewPager = findViewById(R.id.pager);
        tabAdapter = new TabAdapter(fragmentManager, 2);

        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        currentTaskFragment = (CurrentTaskFragment) tabAdapter.getItem(TabAdapter.CURRENT_TASK_FRAGMENT_POSITION);
        doneTaskFragment = (DoneTaskFragment) tabAdapter.getItem(TabAdapter.DONE_TASK_FRAGMENT_POSITION);

        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentTaskFragment.findTasks(newText);
                doneTaskFragment.findTasks(newText);
                return false;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment addingTaskDialogFragment = new AddingTaskDialogFragment();
                addingTaskDialogFragment.show(fragmentManager, "AddingTaskDialogFragment");

            }
        });
    }

    @Override
    public void onTaskAdded(ModelTask newTask) {
       currentTaskFragment.addTask(newTask, true);
    }

    @Override
    public void onTaskAddingCancel() {
        Toast.makeText(this, "Task adding cancel.", Toast.LENGTH_LONG).show();
    }

    public void onTaskDone(ModelTask task) {
        doneTaskFragment.addTask(task, false);
    }

    public void onTaskRestore(ModelTask task) {
        currentTaskFragment.addTask(task,false);
    }

    @Override
    public void onTaskEdited(ModelTask updateTask) {
        currentTaskFragment.updateTask(updateTask);
        dbHelper.update().task(updateTask);
    }
}