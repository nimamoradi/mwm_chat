package com.stfalcon.chatkit.sample.features.demo.def;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.common.data.fixtures.MessagesFixtures;
import com.stfalcon.chatkit.sample.common.data.model.Message;
import com.stfalcon.chatkit.sample.common.data.model.User;
import com.stfalcon.chatkit.sample.features.demo.DemoMessagesActivity;
import com.stfalcon.chatkit.sample.prototype.messageProto;
import com.stfalcon.chatkit.sample.staticData;
import com.stfalcon.chatkit.sample.utils.AppUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefaultMessagesActivity extends DemoMessagesActivity
        implements MessageInput.InputListener,
        MessageInput.AttachmentsListener,
        MessageInput.TypingListener {
    User currentUser;

    public static void open(Context context) {
        context.startActivity(new Intent(context, DefaultMessagesActivity.class));
    }

    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_messages);

        this.messagesList = (MessagesList) findViewById(R.id.messagesList);
        initAdapter();

        MessageInput input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(this);
        input.setTypingListener(this);
        input.setAttachmentsListener(this);
        connect();
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        //todo add message to server too
        messageProto getDialogService = messageProto.retrofit.create(messageProto.class);


        Message last = new Message(currentUser.getId(),
                currentUser, input.toString(), new Date(System.currentTimeMillis()));
        super.messagesAdapter.addToStart(last, true);
        final Call<Message> call = getDialogService.sendMessage(currentUser.getId(), last);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.i("retro", response.body() + " " + response.raw());
                if (response.isSuccessful()) {
                    Toast.makeText(DefaultMessagesActivity.this,
                            "message send", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(DefaultMessagesActivity.this,
                            "error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(DefaultMessagesActivity.this,
                        "no internet", Toast.LENGTH_SHORT).show();

            }

        });

        return true;
    }

    @Override
    public void onAddAttachments() {
        //todo add message to server too
//        sendMessage

        super.messagesAdapter.addToStart(
                MessagesFixtures.getImageMessage(), true);
    }

    private void initAdapter() {

        super.messagesAdapter = new MessagesListAdapter<>(super.senderId, super.imageLoader);
        super.messagesAdapter.enableSelectionMode(this);
        super.messagesAdapter.setLoadMoreListener(this);
        super.messagesAdapter.registerViewClickListener(R.id.messageUserAvatar,
                new MessagesListAdapter.OnMessageViewClickListener<Message>() {
                    @Override
                    public void onMessageViewClick(View view, Message message) {
                        AppUtils.showToast(DefaultMessagesActivity.this,
                                message.getUser().getName() + " avatar click",
                                false);
                    }
                });
        this.messagesList.setAdapter(super.messagesAdapter);
    }

    @Override
    public void onStartTyping() {
        Log.v("Typing listener", getString(R.string.start_typing_status));
    }


    private void AddMessage(ArrayList<Message> messages) {

        for (int i = 0; i < messages.size(); i++) {
            super.messagesAdapter.addToStart(
                    messages.get(i), true);
        }

    }

    private void connect() {
        messageProto getDialogService = messageProto.retrofit.create(messageProto.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user = preferences.getString(staticData.currentUserTag, "null");
        Gson gson = new Gson();
        currentUser = gson.fromJson(user, User.class);

        String time = preferences.getString(staticData.lastUpdateTimeTag, "null");


        final Call<ArrayList<Message>> call = getDialogService.getMessage(currentUser.getId(), time, 0, 10);

        call.enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                Log.i("retro", response.body() + " " + response.raw());
                if (response.isSuccessful()) {
                    Toast.makeText(DefaultMessagesActivity.this,
                            "server returned data", Toast.LENGTH_SHORT).show();
                    AddMessage(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {

            }

        });

    }

    @Override
    public void onStopTyping() {
        Log.v("Typing listener", getString(R.string.stop_typing_status));
    }
}
