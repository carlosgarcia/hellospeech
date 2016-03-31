package com.example.carlos.hellospeech;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final int REQUEST_CODE = 1234;
    private static final String CURRENT_UTTERANCE = "HELLO";

    private Button start;
    private Button speak;

    private TextView speech;
    private TextView odiText;
    private List<String> matchesText;

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start_reg);
        speak = (Button) findViewById(R.id.speak_btn);
        speech = (TextView) findViewById(R.id.speech);
        odiText = (TextView) findViewById(R.id.odi_text);
        textToSpeech = new TextToSpeech(this, this);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptResponse();
            }
        });

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayIntroText();
            }
        });
    }

    private void dialogue() {
        System.out.println("starting dialogue");
        sayIntroText();
    }

    private void promptResponse() {
        if (ActivityUtils.getInstance().isConnected(MainActivity.this)) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, AppContextUtils.getInstance().getLocale().toLanguageTag());
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Toast.makeText(getApplicationContext(), "Please connect to internet", Toast.LENGTH_LONG).show();
        }
    }

    private void sayIntroText() {
        odiSpeak("Hola, dónde quieres ir?");
    }

    private void odiSpeak(String text) {
        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
                System.out.println("utterance OnDone " + utteranceId);

                if (CURRENT_UTTERANCE.equals(utteranceId)) {
                    textToSpeech.stop();
                    promptResponse();
                }
            }

            @Override
            public void onError(String utteranceId) {

            }
        });
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, CURRENT_UTTERANCE);
        odiText.setText(text);
    }

    @Override
    public void onInit(int status) {
        System.out.println("TTS onInit");

        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(AppContextUtils.getInstance().getLocale());
            textToSpeech.setPitch(1);
        }

        dialogue();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            matchesText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matchesText.size() > 0) {
                speech.setText(matchesText.get(0));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
