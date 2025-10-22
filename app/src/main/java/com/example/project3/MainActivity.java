package com.example.project3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout.LayoutParams;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int TEXT_REQUEST = 1;
    public static final String EXTRA_MESSAGE = "com.example.project3.MESSAGE";
    public static final String EXTRA_HISTORY = "com.example.project3.HISTORY";

    private EditText editTextMessage;
    private LinearLayout chatLayout;
    private ArrayList<String> chatHistory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMessage = findViewById(R.id.editTextMessage);
        Button buttonSend = findViewById(R.id.buttonSend);
        chatLayout = findViewById(R.id.chatLayout);

        buttonSend.setOnClickListener(v -> {
            String message = editTextMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                addMessage(message, true);
                chatHistory.add("1:" + message);

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(EXTRA_MESSAGE, message);
                intent.putStringArrayListExtra(EXTRA_HISTORY, chatHistory);
                startActivityForResult(intent, TEXT_REQUEST);

                editTextMessage.setText("");
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

        chatLayout.addView(textView);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST && resultCode == RESULT_OK && data != null) {
            String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
            if (reply != null) {
                chatHistory = data.getStringArrayListExtra(EXTRA_HISTORY);
                addMessage(reply, false);
            }
        }
    }
}
