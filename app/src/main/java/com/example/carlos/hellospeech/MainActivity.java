package com.example.carlos.hellospeech;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1234;
    private Button start;
    private TextView speech;
    // private Dialog matchTextDialog;
    // private ListView textList;
    private List<String> matchesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start_reg);
        speech = (TextView) findViewById(R.id.speech);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    Toast.makeText(getApplicationContext(), "Please connect to internet", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            matchTextDialog = new Dialog(MainActivity.this);
            matchTextDialog.setContentView(R.layout.dialog_matches_frag);
            matchTextDialog.setTitle("Select Matching Text");
            textList = (ListView) matchTextDialog.findViewById(R.id.list);
            matchesText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matchesText);
            textList.setAdapter(adapter);
            textList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    speech.setText("You said " + matchesText.get(position));
                    matchTextDialog.hide();
                }
            });
            matchTextDialog.show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            matchesText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matchesText.size() > 0) {
                speech.setText("You said " + matchesText.get(0));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}