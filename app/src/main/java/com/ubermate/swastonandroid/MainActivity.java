package com.ubermate.swastonandroid;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends Activity {

    private TextView mTextView;
    private final Context mContext = this;
    private Toast mToast;

    private void setClipboard(Context context, String text) {

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            if (mToast.getView().getWindowVisibility() != View.VISIBLE) {
                mToast.show();
            }
        }

    }

    private void textViewResetCurrentPosition() {
        mTextView.setRotation(0);
        mTextView.setRotationX(0);
        mTextView.setRotationY(0);
    }

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mToast = Toast.makeText(mContext, "Copied!", Toast.LENGTH_SHORT);
        mTextView = findViewById(R.id.output);
        mTextView.setTypeface(Typeface.MONOSPACE); // WON'T work inside xml for some old abis

        mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                textViewResetCurrentPosition();
                view.animate()
                        .rotationBy(-75)
                        .setInterpolator(new DecelerateInterpolator(1))
                        .setDuration(1000).withEndAction(
                        new Runnable() {
                            @Override
                            public void run() {
                                view.animate()
                                        .rotationBy((360 * 8) + 75)
                                        .setInterpolator(new DecelerateInterpolator(2))
                                        .setDuration(1100);
                            }
                        });
                return true;
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewResetCurrentPosition();
                mTextView.animate()
                        .rotationYBy(-360)
                        .rotationBy(360)
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(1000);
            }
        });


        EditText editText = findViewById(R.id.input);


        FloatingActionButton copyButton = findViewById(R.id.copy);
        copyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String text = mTextView.getText().toString();
                if (text.isEmpty()) {
                    return;
                }

                setClipboard(mContext, text);
                view.setRotation(0);
                view.animate()
                        .rotationBy(360)
                        .setInterpolator(new DecelerateInterpolator(1))
                        .setDuration(500);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                textViewResetCurrentPosition();
                mTextView.setAlpha(0);
                mTextView.setRotation(180);
                mTextView.animate()
                        .alpha(1)
                        .rotationBy(180)
                        .setDuration(450);

                mTextView.setText(SwastonGenerator.FromString(s.toString()));

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }
}
