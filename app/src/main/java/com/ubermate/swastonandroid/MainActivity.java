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
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends Activity {

    private TextView mTextView;
    private Context mContext;
    private Toast mToast;
    private ClipboardManager mClipboard;


    HorizontalScrollView mHorizontalScrollView;

    private void setClipboard(String text) {
        if (mClipboard != null) {
            ClipData clip = ClipData.newPlainText("Copied Text", text);
            mClipboard.setPrimaryClip(clip);
            if (mToast.getView().getWindowVisibility() != View.VISIBLE) {
                mToast.show();
            }
        }

    }

    private void textViewResetCurrentPosition() {
        mHorizontalScrollView.clearAnimation();
        mHorizontalScrollView.setRotation(0);
        mHorizontalScrollView.setRotationX(0);
        mHorizontalScrollView.setRotationY(0);
    }

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = getApplicationContext();
        mToast = Toast.makeText(mContext, "Copied!", Toast.LENGTH_SHORT);
        mClipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        mTextView = findViewById(R.id.output);
        mTextView.setTypeface(Typeface.MONOSPACE); // WON'T work inside xml for some old abis
        mHorizontalScrollView = findViewById(R.id.text_scroll_view);
        mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                textViewResetCurrentPosition();
                mHorizontalScrollView.animate()
                        .rotationBy(-75)
                        .setInterpolator(new DecelerateInterpolator(1))
                        .setDuration(1000).withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        mHorizontalScrollView.animate()
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
                mHorizontalScrollView.animate()
                        .rotationYBy(-360)
                        .rotationBy(360)
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(1000);
            }
        });


        EditText editText = findViewById(R.id.input);


        final FloatingActionButton copyButton = findViewById(R.id.copy);
        copyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String text = mTextView.getText().toString();
                if (text.isEmpty()) {
                    return;
                }

                setClipboard(text);
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
                mHorizontalScrollView.setAlpha(0);
                mHorizontalScrollView.setRotation(180);
                mHorizontalScrollView.animate()
                        .alpha(1)
                        .rotationBy(180)
                        .setDuration(450);

                boolean empty = s.toString().isEmpty();
                copyButton.setClickable(!empty);
                if (empty) {
                    if (mClipboard != null && copyButton.isOrWillBeShown()) copyButton.hide();
                } else {
                    if (mClipboard != null && copyButton.isOrWillBeHidden()) copyButton.show();
                }
                mTextView.setText(SwastonGenerator.FromString(s.toString()));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }
}
