package com.example.serge.dayplanner.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.serge.dayplanner.R;

public class AddingTaskDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View container = inflater.inflate(R.layout.dialog_task, null);

        TextInputLayout tilTitle = container.findViewById(R.id.tilDialogTaskTitle);
        EditText etTitle = tilTitle.getEditText();

        TextInputLayout tilData = container.findViewById(R.id.tilDialogTaskDate);
        EditText etData = tilData.getEditText();

        TextInputLayout tilTime = container.findViewById(R.id.tilDialogTaskTime);
        EditText etTime = tilTime.getEditText();

        tilTitle.setHint(getResources().getString(R.string.task_title));
        tilData.setHint(getResources().getString(R.string.task_data));
        tilTime.setHint(getResources().getString(R.string.task_time));

        builder.setView(container);

        return super.onCreateDialog(savedInstanceState);
    }
}
