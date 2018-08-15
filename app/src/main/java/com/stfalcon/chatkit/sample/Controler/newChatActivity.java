package com.stfalcon.chatkit.sample.Controler;

import android.app.ListActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.common.data.model.User;
import com.stfalcon.chatkit.sample.features.main.adapter.userArrayAdapter;
import com.stfalcon.chatkit.sample.prototype.dialogProto;
import com.stfalcon.chatkit.sample.responseModel.LoginData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newChatActivity extends ListActivity {
    protected ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat_avtivity);
        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(newChatActivity.this).load(url).into(imageView);
            }
        };
        LoadData();

    }

    private void initAdapter(ArrayList<User> arrayOfUsers) {


        // Create the adapter to convert the array to views
        userArrayAdapter adapter = new userArrayAdapter(this, arrayOfUsers);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Toast.makeText(newChatActivity.this, "hi nima", Toast.LENGTH_SHORT).show();
            }

        });
        // Attach the adapter to a ListView
        setListAdapter(adapter);
    }

    public void LoadData() {
        dialogProto getDialogService = dialogProto.retrofit.create(dialogProto.class);

        Call<ArrayList<User>> call = getDialogService.getAllChats("nima");

        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                Log.i("retro", String.valueOf(response.raw()));
                if (response.isSuccessful()) {
                    Toast.makeText(newChatActivity.this,
                            "server returned data", Toast.LENGTH_SHORT).show();
                    initAdapter(response.body());

                } else {
                    Toast.makeText(newChatActivity.this,
                            "Server returned an error", Toast.LENGTH_SHORT).show();
                }
//                showProgress(false);
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(newChatActivity.this,
                        "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
//                showProgress(false);

            }
        });
    }
}
