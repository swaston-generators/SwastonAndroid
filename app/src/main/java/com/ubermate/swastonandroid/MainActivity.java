package com.ubermate.swastonandroid;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends Activity {

    private TextView textView;

    private Context context = this;

    private void setClipboard(Context context, String text) {

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show();
        }

    }

    private void resetPosition() {
        textView.setRotation(0);
        textView.setRotationX(0);
        textView.setRotationY(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = findViewById(R.id.output);
        textView.setTypeface(Typeface.MONOSPACE);

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                resetPosition();
                textView.animate().rotationBy(-75).setDuration(1000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        textView.animate().rotationBy((360 * 8) + 75).setDuration(1000);

                    }
                });
                return true;
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPosition();
                textView.animate().rotationYBy(-360).rotationBy(360).setDuration(1000);
            }
        });


        EditText editText = findViewById(R.id.input);


        FloatingActionButton copyButton = findViewById(R.id.copy);
        copyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setClipboard(context, textView.getText().toString());
            }
        });

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                resetPosition();
                textView.setAlpha(0);
                textView.setRotation(180);
                textView.animate().alpha(1).rotationBy(180).setDuration(450);

                textView.setText(SwastonGenerator.FromString(s.toString()));

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }
}
