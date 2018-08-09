package com.stfalcon.chatkit.sample.features.demo.def;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.sample.R;

import com.stfalcon.chatkit.sample.common.data.model.Dialog;
import com.stfalcon.chatkit.sample.common.data.model.Message;
import com.stfalcon.chatkit.sample.common.data.model.User;
import com.stfalcon.chatkit.sample.features.demo.DemoDialogsActivity;
import com.stfalcon.chatkit.sample.prototype.dialogProto;

import com.stfalcon.chatkit.sample.staticData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DefaultDialogsActivity extends DemoDialogsActivity {


    public static void open(Context context) {
        context.startActivity(new Intent(context, DefaultDialogsActivity.class));
    }

    private DialogsList dialogsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_dialogs);

        dialogsList = (DialogsList) findViewById(R.id.dialogsList);
        mLoginFormView = findViewById(R.id.dialogsList);
        mProgressView = findViewById(R.id.login_progress);
        getMassagesFromServer();

//        showProgress(true);
    }

    @Override
    public void onDialogClick(Dialog dialog) {

        DefaultMessagesActivity.open(this);
    }

    private View mLoginFormView;
    private View mProgressView;

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });

    }

    private void getMassagesFromServer() {
        connect();
    }

    private void initAdapter(ArrayList<Dialog> dialogArrayList) {
        super.dialogsAdapter = new DialogsListAdapter<>(super.imageLoader);
        super.dialogsAdapter.setItems(dialogArrayList);

        super.dialogsAdapter.setOnDialogClickListener(this);
        super.dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(super.dialogsAdapter);
    }

    //for example
    private void onNewMessage(String dialogId, Message message) {
        boolean isUpdated = dialogsAdapter.updateDialogWithMessage(dialogId, message);
        if (!isUpdated) {
            //Dialog with this ID doesn't exist, so you can create new Dialog or update all dialogs list
        }
    }

    //for example
    private void onNewDialog(Dialog dialog) {
        dialogsAdapter.addItem(dialog);
    }


    private void connect() {
        showProgress(true);

        dialogProto getDialogService = dialogProto.retrofit.create(dialogProto.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user = preferences.getString(staticData.currentUserTag, "null");
        Gson gson = new Gson();
        User currentUser = gson.fromJson(user, User.class);


        final Call<List<Dialog>> call = getDialogService.getDialogs(currentUser.getId());

        call.enqueue(new Callback<List<Dialog>>() {
            @Override
            public void onResponse(Call<List<Dialog>> call, Response<List<Dialog>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DefaultDialogsActivity.this,
                            "server returned data", Toast.LENGTH_SHORT).show();
                    initAdapter((ArrayList<Dialog>) response.body());

                } else {
                    Toast.makeText(DefaultDialogsActivity.this,
                            "Server returned an error", Toast.LENGTH_SHORT).show();
                }
                showProgress(false);
            }

            @Override
            public void onFailure(Call<List<Dialog>> call, Throwable t) {
                Toast.makeText(DefaultDialogsActivity.this,
                        "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                showProgress(false);

            }
        });
    }
}
