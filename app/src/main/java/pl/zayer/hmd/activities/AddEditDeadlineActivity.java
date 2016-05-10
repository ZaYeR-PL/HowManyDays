package pl.zayer.hmd.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import pl.zayer.hmd.R;
import pl.zayer.hmd.database.Deadline;
import pl.zayer.hmd.database.primitives.CreateCallback;
import pl.zayer.hmd.database.sqlite.DatabaseAccess;

public class AddEditDeadlineActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorCL;
    private EditText nameET;

    private Deadline initialDeadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_deadline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorCL = (CoordinatorLayout) findViewById(R.id.coordinator_cl);
        nameET = (EditText) findViewById(R.id.name_et);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_edit_deadline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            saveDeadline();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveDeadline() {
        if (initialDeadline == null) {
            Deadline deadline = new Deadline();
            deadline.name = nameET.getText().toString();
            DatabaseAccess da = new DatabaseAccess(getContentResolver());

            CreateCallback<Deadline> callback = new CreateCallback<Deadline>() {
                @Override
                public void onSuccess(Deadline object) {
                    AddEditDeadlineActivity.this.finish();
                }

                @Override
                public void onFailure(int errorCode) {
                    final Snackbar snackbar = Snackbar.make(coordinatorCL, R.string.activity_add_edit_deadline_error_saving_deadline, Snackbar.LENGTH_LONG);
                    snackbar.setAction(R.string.activity_add_edit_deadline_snackbar_retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveDeadline();
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();
                }
            };
            da.createEvent(deadline, callback);
        } else {

        }
    }
}
