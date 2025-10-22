package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout.LayoutParams;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.project3.REPLY";

    private EditText editTextReply;
    private LinearLayout chatLayout2;
    private ArrayList<String> chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editTextReply = findViewById(R.id.editTextReply);
        Button buttonReply = findViewById(R.id.buttonReply);
        chatLayout2 = findViewById(R.id.chatLayout2);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        chatHistory = intent.getStringArrayListExtra(MainActivity.EXTRA_HISTORY);

        if (chatHistory != null) {
            for (String chat : chatHistory) {
                boolean isUser1 = chat.startsWith("1:");
                addMessage(chat.substring(2), isUser1);
            }
        }

        buttonReply.setOnClickListener(v -> {
            String reply = editTextReply.getText().toString().trim();
            if (!reply.isEmpty()) {
                addMessage(reply, false);
                chatHistory.add("2:" + reply);

                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_REPLY, reply);
                replyIntent.putStringArrayListExtra(MainActivity.EXTRA_HISTORY, chatHistory);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }

    private void addMessage(String message, boolean isUser1) {
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(15f);
        textView.setPadding(20, 10, 20, 10);

        // Atur lebar maksimal (supaya tidak penuh)
        int maxWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.7);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 10, 10, 10);

        if (isUser1) {
            params.gravity = Gravity.END;
            textView.setBackgroundResource(R.drawable.bubble_right);
        } else {
            params.gravity = Gravity.START;
            textView.setBackgroundResource(R.drawable.bubble_left);
        }

        textView.setMaxWidth(maxWidth);
        textView.setLayoutParams(params);

        chatLayout2.addView(textView);
    }

}
