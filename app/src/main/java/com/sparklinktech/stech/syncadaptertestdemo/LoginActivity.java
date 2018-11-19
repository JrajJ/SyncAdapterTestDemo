package com.sparklinktech.stech.syncadaptertestdemo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sparklinktech.stech.syncadaptertestdemo.ConnectToServer.ConnectToServer;
import com.sparklinktech.stech.syncadaptertestdemo.NetworkCheck.CheckNetwork;
import com.sparklinktech.stech.syncadaptertestdemo.sharedpref.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity
{

    SessionManager manager;
    EditText uid_, pass_;
    SharedPreferences prefs;
    Button b;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        uid_ = (EditText) findViewById(R.id.username);
        pass_ = (EditText) findViewById(R.id.password);
        manager = new SessionManager();

        prefs = getApplicationContext().getSharedPreferences("status", Context.MODE_PRIVATE);


        if (prefs.contains("userflag"))
        {
            String temp = manager.getPreferences(getApplicationContext(), "userflag");
            if (temp.equals("true"))
            {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }

        }


    }

    public void login(final View view)
    {
        if (CheckNetwork.isInternetAvailable(getApplicationContext()))
        {
            if ((uid_.getText().toString().trim().equals("")))
            {
                uid_.requestFocus();
                uid_.setError("Uid Required");
            }
            else
                {
                if ((pass_.getText().toString().trim().equals("")))
                {
                    pass_.requestFocus();
                    pass_.setError("Password Required");
                }
                else
                    {
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing Up...");
                    progressDialog.show();

                    String uid = uid_.getText().toString().trim();
                    String password = pass_.getText().toString().trim();
                    Log.e("UID     >>>>  ", "    " + uid);
                    Log.e("PASS    >>>>  ", "    " + password);


                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ConnectToServer.URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                    CategoryInterface service = retrofit.create(CategoryInterface.class);


                    Call<Login> call = service.getLoginDetails(uid);

                    call.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                            Login jsonResponse = response.body();
                            Log.e("RES   >>>>   ", "" + response.message());
                            progressDialog.dismiss();
                            assert jsonResponse != null;
                            if (jsonResponse.getLogin()) {
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                                manager.setPreferences(getApplicationContext(), "userflag", "true");
                            } else {
                                Log.e("RES   >>>>   ", "" + response.message());

                                Toast.makeText(LoginActivity.this, "INVALID UID/PASSWORD", Toast.LENGTH_SHORT).show();
                                uid_.setText("");
                                pass_.setText("");
                                uid_.setFocusable(true);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                            progressDialog.dismiss();
                            Log.e("Error  ***", t.getMessage());
                            Toast.makeText(LoginActivity.this, "RESPONSE ERROR!!! TRY AGAIN", Toast.LENGTH_SHORT).show();
                            uid_.setText("");
                            pass_.setText("");
                            uid_.setFocusable(true);
                        }
                    });
                }
            }
        }
        else
        {
            //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder ab = new AlertDialog.Builder(getApplicationContext());
            ab.setTitle("No Internet Connection");
            ab.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
            ab.setNeutralButton("Reload", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });

            ab.show();
        }
    }
}
