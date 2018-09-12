package com.stfalcon.chatkit.sample.features.demo.custom.holder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.common.data.fixtures.MessagesFixtures;
import com.stfalcon.chatkit.sample.common.data.model.Message;
import com.stfalcon.chatkit.sample.common.data.model.User;
import com.stfalcon.chatkit.sample.common.data.model.simpleMessage;
import com.stfalcon.chatkit.sample.features.demo.DemoMessagesActivity;
import com.stfalcon.chatkit.sample.features.demo.custom.holder.holders.messages.CustomIncomingImageMessageViewHolder;
import com.stfalcon.chatkit.sample.features.demo.custom.holder.holders.messages.CustomIncomingTextMessageViewHolder;
import com.stfalcon.chatkit.sample.features.demo.custom.holder.holders.messages.CustomOutcomingImageMessageViewHolder;
import com.stfalcon.chatkit.sample.features.demo.custom.holder.holders.messages.CustomOutcomingTextMessageViewHolder;
import com.stfalcon.chatkit.sample.prototype.messageProto;
import com.stfalcon.chatkit.sample.staticData;
import com.stfalcon.chatkit.sample.utils.AppUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomHolderMessagesActivity extends DemoMessagesActivity
        implements MessagesListAdapter.OnMessageLongClickListener<Message>,
        MessageInput.InputListener,
        MessageInput.AttachmentsListener {
    User currentUser;
    private static final int loadMoreCount = 5;
    private int loadedCount = 0;
    private String chatId;


    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_holder_messages);

        messagesList = (MessagesList) findViewById(R.id.messagesList);
        initAdapter();

        MessageInput input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(this);
        input.setAttachmentsListener(this);
        chatId = getIntent().getStringExtra(staticData.chatId);
        connect();
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra(staticData.dialogId, chatId);
        IMessage message = super.messagesAdapter.getLastMessage();
        if (message != null) {
            simpleMessage simpleMessage = new simpleMessage(message.getId(), message.getText(),
                    message.getCreatedAt(), message.getUser());

            intent.putExtra(staticData.messageTagId, simpleMessage

            );

            setResult(RESULT_OK, intent);
        }
        finish();
//        super.onBackPressed();
    }

    private void AddMessage(ArrayList<Message> messages) {

        for (int i = 0; i < messages.size(); i++) {
            super.messagesAdapter.addToStart(
                    messages.get(i), true);
        }

    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {
        //todo get more message
        Log.i("TAG", "onLoadMore: " + page + " " + totalItemsCount);
        loadedCount = page;
        load();

    }

    private void load() {
        messageProto getDialogService = messageProto.retrofit.create(messageProto.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String time = preferences.getString(staticData.lastUpdateTimeTag, "null");


        final Call<ArrayList<Message>> call = getDialogService.getMessage(chatId, currentUser.getId(), time, loadedCount, loadedCount + loadMoreCount);

        call.enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                Log.i("retro", response.body() + " " + response.raw());
                if (response.isSuccessful()) {
                    Toast.makeText(CustomHolderMessagesActivity.this,
                            "server returned data", Toast.LENGTH_SHORT).show();
                    messagesAdapter.addToEnd(response.body(), true);

                } else {
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                Toast.makeText(CustomHolderMessagesActivity.this,
                        "no internet", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void connect() {
        messageProto getDialogService = messageProto.retrofit.create(messageProto.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String time = preferences.getString(staticData.lastUpdateTimeTag, "null");


        final Call<ArrayList<Message>> call = getDialogService.getMessage(chatId, currentUser.getId(), time, loadedCount, loadedCount + loadMoreCount);

        call.enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                Log.i("retro", response.body() + " " + response.raw());
                if (response.isSuccessful()) {
                    Toast.makeText(CustomHolderMessagesActivity.this,
                            "server returned data", Toast.LENGTH_SHORT).show();
                    AddMessage(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                Toast.makeText(CustomHolderMessagesActivity.this,
                        "no internet", Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    public boolean onSubmit(CharSequence input) {

        messageProto getDialogService = messageProto.retrofit.create(messageProto.class);


        Message last = new Message(currentUser.getId(),
                currentUser, input.toString(), new Date(System.currentTimeMillis()));
        super.messagesAdapter.addToStart(last, true);
        final Call<Message> call = getDialogService.sendMessage(chatId, currentUser.getId(), last);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.i("retro", response.body() + " " + response.raw());
                if (response.isSuccessful()) {
                    Toast.makeText(CustomHolderMessagesActivity.this,
                            "message send", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CustomHolderMessagesActivity.this,
                            "error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(CustomHolderMessagesActivity.this,
                        "no internet", Toast.LENGTH_SHORT).show();

            }

        });

        return true;
    }

    @Override
    public void onAddAttachments() {
        messagesAdapter.addToStart(MessagesFixtures.getImageMessage(), true);
    }

    @Override
    public void onMessageLongClick(Message message) {
        AppUtils.showToast(this, R.string.on_log_click_message, false);
    }

    private User getCurrentFromStorage() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user = preferences.getString(staticData.currentUserTag, "null");
        Gson gson = new Gson();
        currentUser = gson.fromJson(user, User.class);
        return currentUser;
    }

    private void initAdapter() {
        getCurrentFromStorage();
        String id;
        if (currentUser == null)
            id = "0";
        else id = currentUser.getId();
        MessageHolders holdersConfig = new MessageHolders()
                .setIncomingTextConfig(
                        CustomIncomingTextMessageViewHolder.class,
                        R.layout.item_custom_incoming_text_message)
                .setOutcomingTextConfig(
                        CustomOutcomingTextMessageViewHolder.class,
                        R.layout.item_custom_outcoming_text_message)
                .setIncomingImageConfig(
                        CustomIncomingImageMessageViewHolder.class,
                        R.layout.item_custom_incoming_image_message)
                .setOutcomingImageConfig(
                        CustomOutcomingImageMessageViewHolder.class,
                        R.layout.item_custom_outcoming_image_message);

        super.messagesAdapter = new MessagesListAdapter<>(id, holdersConfig, super.imageLoader);
        super.messagesAdapter.setOnMessageLongClickListener(this);
        super.messagesAdapter.setLoadMoreListener(this);
        messagesList.setAdapter(super.messagesAdapter);
    }
}
