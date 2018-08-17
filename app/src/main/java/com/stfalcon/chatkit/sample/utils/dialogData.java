package com.stfalcon.chatkit.sample.utils;

import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.sample.common.data.model.Dialog;

import java.io.Serializable;

public class dialogData implements Serializable {
    public transient DialogsListAdapter<Dialog>  dialogsAdapter;

    public dialogData(DialogsListAdapter<Dialog> dialogsAdapter) {
        this.dialogsAdapter = dialogsAdapter;
    }

}