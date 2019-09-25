package com.example.rohandsouza.indianrailways.feedback;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohandsouza.indianrailways.R;

import org.w3c.dom.Text;

/**
 * Created by Rohan Dsouza on 14-Mar-18.
 */

public class FeedbackDialog extends AppCompatDialogFragment {

    String department = Departments.department;
    EditText e2, e3, e4;
    TextView t;
    String pnr_no, coach_no, problem;
    private FeedbackDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_feedback, null);

        t = (TextView)view.findViewById(R.id.textView9);
        e2 = (EditText) view.findViewById(R.id.editText9);
        e3 = (EditText) view.findViewById(R.id.editText10);
        e4 = (EditText) view.findViewById(R.id.editText11);

        t.setText(department);

        builder.setView(view)
                .setTitle("Help")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pnr_no = e2.getText().toString();
                        coach_no = e3.getText().toString();
                        problem = e4.getText().toString();
                        listener.applyTexts(pnr_no, coach_no, problem);

                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (FeedbackDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement FeedbackDialogListener");
        }
    }

    public interface FeedbackDialogListener{
        void applyTexts(String pnrno, String coachno, String problem);
    }
}
