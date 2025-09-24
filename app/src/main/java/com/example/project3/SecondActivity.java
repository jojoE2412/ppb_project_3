package com.example.project3;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project3.R;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.explicitintentdemo.REPLY";

    private EditText editTextReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView textViewReceived = findViewById(R.id.textViewReceived);
        editTextReply = findViewById(R.id.editTextReply);
        Button buttonReply = findViewById(R.id.buttonReply);

        // Terima pesan dari MainActivity
        Intent intent = getIntent();
        String message = intent.getStringExtra(com.example.project3.MainActivity.EXTRA_MESSAGE);
        textViewReceived.setText("Pesan dari Activity 1: " + message);

        // Balas pesan
        buttonReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reply = editTextReply.getText().toString();
                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_REPLY, reply);
                setResult(RESULT_OK, replyIntent);
                finish(); // Tutup SecondActivity
            }
        });
    }
}
