package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.project3.REPLY";

    private EditText editTextReply;
    private LinearLayout layoutChatContainer;
    private ScrollView scrollViewChat;
    private ArrayList<String> chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editTextReply = findViewById(R.id.editTextReply);
        layoutChatContainer = findViewById(R.id.layoutChatContainer);
        scrollViewChat = findViewById(R.id.scrollViewChat);
        Button buttonReply = findViewById(R.id.buttonReply);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        chatHistory = intent.getStringArrayListExtra(MainActivity.EXTRA_HISTORY);

        if (chatHistory == null) {
            chatHistory = new ArrayList<>();
        }

        // Tambahkan pesan terakhir dari pengguna 1 ke daftar chat
        if (message != null && !message.isEmpty()) {
            chatHistory.add("Pengguna 1: " + message);
        }

        // Tampilkan semua riwayat
        refreshChat();

        buttonReply.setOnClickListener(v -> {
            String reply = editTextReply.getText().toString().trim();
            if (!reply.isEmpty()) {
                chatHistory.add("Pengguna 2: " + reply);

                Intent replyIntent = new Intent();
                replyIntent.putStringArrayListExtra(MainActivity.EXTRA_HISTORY, chatHistory);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
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
