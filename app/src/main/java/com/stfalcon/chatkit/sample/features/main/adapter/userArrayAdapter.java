package com.stfalcon.chatkit.sample.features.main.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.common.data.model.Message;
import com.stfalcon.chatkit.sample.common.data.model.User;
import com.stfalcon.chatkit.sample.features.demo.DemoMessagesActivity;

import java.util.ArrayList;

public class userArrayAdapter extends ArrayAdapter<User> {
    public userArrayAdapter(@NonNull Context context, ArrayList<User> userArrayList) {
        super(context, R.layout.new_chat_item,userArrayList);

    }
    ArrayList<User> userList = new ArrayList<>();

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        ImageView Image;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.new_chat_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.dialogName);
            viewHolder.Image = (ImageView) convertView.findViewById(R.id.dialogAvatar);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name.setText(user.getName());
        Picasso.with(getContext()).load(Uri.parse(user.getAvatar())).into(viewHolder.Image);
        // Return the completed view to render on screen
        return convertView;
    }

}
