package com.stfalcon.chatkit.sample.features.demo.custom.holder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.sample.Controler.newChatActivity;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.common.data.model.Dialog;
import com.stfalcon.chatkit.sample.common.data.model.Message;
import com.stfalcon.chatkit.sample.common.data.model.User;
import com.stfalcon.chatkit.sample.common.data.model.simpleMessage;
import com.stfalcon.chatkit.sample.features.demo.DemoDialogsActivity;
import com.stfalcon.chatkit.sample.features.demo.custom.holder.holders.dialogs.CustomDialogViewHolder;
import com.stfalcon.chatkit.sample.prototype.dialogProto;
import com.stfalcon.chatkit.sample.staticData;
import com.stfalcon.chatkit.sample.utils.dialogData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomHolderDialogsActivity extends DemoDialogsActivity implements Serializable {

    public CustomHolderDialogsActivity() {
    }


    public DialogsListAdapter<Dialog> getDialog() {
        return super.dialogsAdapter;
    }

    private DialogsList dialogsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_holder_dialogs);

        dialogsList = (DialogsList) findViewById(R.id.dialogsList);
        getMassagesFromServer();
    }

    private void getMassagesFromServer() {
        connect();
    }

    private void connect() {
//        showProgress(true);

        dialogProto getDialogService = dialogProto.retrofit.create(dialogProto.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user = preferences.getString(staticData.currentUserTag, "null");
        Gson gson = new Gson();
        User currentUser = gson.fromJson(user, User.class);

        String time = preferences.getString(staticData.lastUpdateTimeTag, "0");
        if (currentUser.getId() == null)
            currentUser.setId("0");
        final Call<List<Dialog>> call = getDialogService.getDialogs(currentUser.getId(), time);

        call.enqueue(new Callback<List<Dialog>>() {
            @Override
            public void onResponse(Call<List<Dialog>> call, Response<List<Dialog>> response) {
                Log.i("retro", response.body() + "  " + response.message() + " " + response.raw());
                if (response.isSuccessful()) {
                    Toast.makeText(CustomHolderDialogsActivity.this,
                            "server returned data", Toast.LENGTH_SHORT).show();
                    if (response.body() != null) {
                        if (response.body().size() == 0) {
                            Toast.makeText(CustomHolderDialogsActivity.this, "something is wrong",
                                    Toast.LENGTH_SHORT).show();
                            initAdapter(new ArrayList<Dialog>());

                        } else
                            initAdapter((ArrayList<Dialog>) response.body());
                    }
//saving last update time
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CustomHolderDialogsActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(staticData.lastUpdateTimeTag, String.valueOf(System.currentTimeMillis()));
                    editor.apply();
                } else {
                    Toast.makeText(CustomHolderDialogsActivity.this,
                            "Server returned an error", Toast.LENGTH_SHORT).show();
                }
//                showProgress(false);
            }

            @Override
            public void onFailure(Call<List<Dialog>> call, Throwable t) {
                Toast.makeText(CustomHolderDialogsActivity.this,
                        "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
//                showProgress(false);

            }
        });
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

    @Override
    public void onDialogClick(Dialog dialog) {

        Intent intent = new Intent(this, CustomHolderMessagesActivity.class);
        intent.putExtra(staticData.chatId, dialog.getId());
        startActivityForResult(intent, staticData.updateDialog);

    }

    private void initAdapter(ArrayList<Dialog> dialogArrayList) {

        super.dialogsAdapter = new DialogsListAdapter<>(
                R.layout.item_custom_dialog_view_holder,
                CustomDialogViewHolder.class,
                super.imageLoader);

        super.dialogsAdapter.setItems(dialogArrayList);

        super.dialogsAdapter.setOnDialogClickListener(this);
        super.dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(super.dialogsAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == staticData.updateDialog) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                String dialogId = data.getStringExtra(staticData.dialogId);
                simpleMessage messageTagId = data.getParcelableExtra(staticData.messageTagId);


                dialogsAdapter.updateDialogWithMessage(dialogId, messageTagId, true);
                // get String data from Intent


            }
        }
    }

    public void newChat(View view) {
        Intent intent = new Intent(CustomHolderDialogsActivity.this, newChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("dialogClass", new dialogData(getDialog()));
        startActivity(intent);
    }
}
