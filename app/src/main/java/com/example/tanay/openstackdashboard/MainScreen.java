package com.example.tanay.openstackdashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import openstack_connection.PostRequest.ComputeRequest;


public class MainScreen extends ActionBarActivity implements OnLoadingCompleted {

    private TextView username;
    private Button compute;
    private ComputeRequest request;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        request = new ComputeRequest();
        username = (TextView) findViewById(R.id.user);
        Log.i("Username: ",UserData.username);
        username.setText(UserData.username);
        compute = (Button) findViewById(R.id.btnCompute);
        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeRequest();
            }
        });
    }

    private void computeRequest() {
        request.setComputeRequest();
        request.execute();
        loading = ProgressDialog.show(this, "Please wait", "Loading the data...", true);
        loading.setCancelable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadingCompleted(Boolean successful) {
        loading.dismiss();
        if (successful) {
            Intent intent = new Intent(this, Compute.class);
            this.startActivity(intent);
        }
    }
}
