package com.coverteam.pta.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.views.signature.SignatureActivity;

public class DialogSignature  extends DialogFragment {

    private Users user;

    public DialogSignature(Users user) {
        this.user = user;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Tanda Tangan Harus di buat")
                .setPositiveButton("Buat", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent goSignaturePad = new Intent(getActivity(), SignatureActivity.class);

                        goSignaturePad.putExtra("user",user);
                        startActivity(goSignaturePad);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finish();
                        System.exit(0);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
