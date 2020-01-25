package com.ubermate.swastonandroid;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = findViewById(R.id.output);
        textView.setTypeface(Typeface.MONOSPACE);
        EditText editText = findViewById(R.id.input);

        FloatingActionButton copyButton = findViewById(R.id.copy);
        copyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setClipboard(context, textView.getText().toString());
            }
        });

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                textView.setAlpha(0f);
                textView.setRotation(180f);
                textView.animate().alpha(1).rotationBy(180f).setDuration(450);

                textView.setText(SwastonGenerator.FromString(s.toString()));

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }
}
