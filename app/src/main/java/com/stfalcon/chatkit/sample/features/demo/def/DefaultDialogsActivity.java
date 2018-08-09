package com.stfalcon.chatkit.sample.features.demo.def;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.common.data.fixtures.DialogsFixtures;
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


public class DefaultDialogsActivity extends DemoDialogsActivity {

    private ArrayList<Dialog> dialogs;

    public static void open(Context context) {
        context.startActivity(new Intent(context, DefaultDialogsActivity.class));
    }

    private DialogsList dialogsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_dialogs);

        dialogsList = (DialogsList) findViewById(R.id.dialogsList);
        getMassagesFromServer();
    }

    @Override
    public void onDialogClick(Dialog dialog) {
        //todo give user
        DefaultMessagesActivity.open(this);
    }


    //todo get request from server
    private void getMassagesFromServer() {
        getDialogListFromServerTask task = new getDialogListFromServerTask(this);
        task.execute();
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

    class getDialogListFromServerTask extends AsyncTask<Void, Void, List<Dialog>> {
        public getDialogListFromServerTask(Context main) {
            context = main;
        }

        private Context context;

        @Override
        protected List<Dialog> doInBackground(Void... voids) {
            dialogProto getDialogService = dialogProto.retrofit.create(dialogProto.class);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String user = preferences.getString(staticData.currentUserTag, "null");
            Gson gson = new Gson();
            User currentUser = gson.fromJson(user, User.class);


            final Call<List<Dialog>> call = getDialogService.getDialogs(currentUser.getId());

            try {
                return call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Dialog> dialogs) {
            super.onPostExecute(dialogs);
            initAdapter((ArrayList<Dialog>) dialogs);

        }
    }
}
