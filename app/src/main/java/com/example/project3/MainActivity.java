package com.example.project3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int TEXT_REQUEST = 1;
    public static final String EXTRA_MESSAGE = "com.example.project3.MESSAGE";
    public static final String EXTRA_HISTORY = "com.example.project3.HISTORY";

    private EditText editTextMessage;
    private LinearLayout layoutChatContainer;
    private ScrollView scrollViewChat;
    private ArrayList<String> chatHistory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMessage = findViewById(R.id.editTextMessage);
        layoutChatContainer = findViewById(R.id.layoutChatContainer);
        scrollViewChat = findViewById(R.id.scrollViewChat);
        Button buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(v -> {
            String message = editTextMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                chatHistory.add("Pengguna 1: " + message);
                addMessage("Pengguna 1: " + message, Gravity.END);
                editTextMessage.setText("");

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(EXTRA_MESSAGE, message);
                intent.putStringArrayListExtra(EXTRA_HISTORY, chatHistory);
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST && resultCode == RESULT_OK && data != null) {
            chatHistory = data.getStringArrayListExtra(EXTRA_HISTORY);
            refreshChat();
        }
    }

    private void refreshChat() {
        layoutChatContainer.removeAllViews();
        for (String msg : chatHistory) {
            int gravity = msg.startsWith("Pengguna 1") ? Gravity.END : Gravity.START;
            addMessage(msg, gravity);
        }
    }

    private void addMessage(String text, int gravity) {
        TextView messageView = new TextView(this);
        messageView.setText(text);
        messageView.setPadding(16, 8, 16, 8);
        messageView.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = gravity;
        params.setMargins(8, 8, 8, 8);

        messageView.setLayoutParams(params);
        layoutChatContainer.addView(messageView);
        scrollViewChat.post(() -> scrollViewChat.fullScroll(View.FOCUS_DOWN));
    }
}
