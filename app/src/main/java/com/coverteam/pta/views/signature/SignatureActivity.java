package com.coverteam.pta.views.signature;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.coverteam.pta.BuildConfig;
import com.coverteam.pta.R;

import se.warting.signatureview.views.SignaturePad;
import se.warting.signatureview.views.SignedListener;

public class SignatureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        Button mSaveButton = findViewById(R.id.save_button);
        Button mClearButton = findViewById(R.id.clear_button);
        SignaturePad mSignaturePad = findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignedListener() {
            @Override
            public void onStartSigning() {
                Toast.makeText(SignatureActivity.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
            }
        });
       mSaveButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
               String signatureSvg = mSignaturePad.getSignatureSvg();
               Bitmap transparentSignatureBitmap = mSignaturePad.getTransparentSignatureBitmap(true);
               if (BuildConfig.DEBUG) {
                   Log.d("ViewActivity", "Bitmap size: " + signatureBitmap.getByteCount());
                   Log.d(
                           "ViewActivity",
                           "Bitmap trasparent size: " + transparentSignatureBitmap.getByteCount()
                   );
                   Log.d("ViewActivity", "Svg length: " + signatureSvg.length());
               }
           }
       });
    }
}