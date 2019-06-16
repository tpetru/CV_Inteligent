package com.example.user.licenta2.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.licenta2.R;

import java.util.ArrayList;
import java.util.Locale;

/*
    Resources:
    - https://developer.android.com/reference/android/speech/SpeechRecognizer
    - https://www.techjini.com/blog/android-speech-to-text-tutorial-part1/
 */

public class SpeechToTextActivity extends AppCompatActivity {

    TextView fullSpokenText;
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;
    String newText = "";

    private Boolean started = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        checkPermission();

        fullSpokenText = findViewById(R.id.etText);

        // initializam mSpeechRecognizer
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.d("MyDebug", "# ReadyForSpeech #############################################################################\n");
            }

            @Override
            public void onBeginningOfSpeech() {
//                Log.d("MyDebug","BeginningOfSpeech");

            }

            @Override
            public void onRmsChanged(float rmsdB) {
//                Log.d("MyDebug", "RmsChanged: " + rmsdB);
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
//                Log.d("MyDebug", "BufferReceived: " + buffer.toString());

            }

            @Override
            public void onEndOfSpeech() {
//                et3.setText(et3.getText() + "#_");
                Log.d("MyDebug", "EndOfSpeech");
                if (started == true) startListening(mSpeechRecognizer, mSpeechRecognizerIntent);
            }

            @Override
            public void onError(int error) {
                newText = "";
                switch (error) {
                        case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                            Log.d("MyDebug", "ERROR_NETWORK_TIMEOUT - " + SpeechRecognizer.ERROR_NETWORK_TIMEOUT); // 1
                            break;

                        case SpeechRecognizer.ERROR_AUDIO:
                            Log.d("MyDebug", "ERROR_NETWORK - " + SpeechRecognizer.ERROR_NETWORK); // 2
                            break;

                        case SpeechRecognizer.ERROR_SERVER:
                            Log.d("MyDebug", "ERROR_SERVER - " + SpeechRecognizer.ERROR_SERVER); // 4
                            break;

                        case SpeechRecognizer.ERROR_CLIENT:
                            Log.d("MyDebug", "ERROR_CLIENT - " + SpeechRecognizer.ERROR_CLIENT); // 5
                            break;

                        case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                             Log.d("MyDebug", "ERROR_SPEECH_TIMEOUT - " + SpeechRecognizer.ERROR_SPEECH_TIMEOUT); // 6
                             break;

                        case SpeechRecognizer.ERROR_NO_MATCH:
                            Log.d("MyDebug", "ERROR_NO_MATCH - " + SpeechRecognizer.ERROR_NO_MATCH); // 7
                            break;

                        case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                            Log.d("MyDebug", "ERROR_RECOGNIZER_BUSY - " + SpeechRecognizer.ERROR_RECOGNIZER_BUSY); // 8
                            break;

                        case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                            Log.d("MyDebug", "ERROR_INSUFFICIENT_PERMISSIONS - " + SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS); // 9
                            break;

                        default:
                }
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                if (matches != null) {
                    Log.d("MyDebug", "Results:" + results.toString());
                    newText = matches.get(0);
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
//                Log.d("MyDebug", "PartialResults: " + partialResults);
            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });


        findViewById(R.id.btnSpeech).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:

                        if (started == false) {
                            startListening(mSpeechRecognizer, mSpeechRecognizerIntent);
                        }
                        else {
                            stopListening(mSpeechRecognizer);
                        }
                        break;

                }
                return false;
            }
        });

        findViewById(R.id.btnResetText).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fullSpokenText.setText("");

                return false;
            }
        });
    }

    private void checkPermission() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Aplicatia are nevoie de acces la microfon.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public void startListening(SpeechRecognizer mSpeechRecognizer, Intent mSpeechRecognizerIntent) {
        started = true;
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
    }

    private void stopListening(SpeechRecognizer mSpeechRecognizer) {
        started = false;
        mSpeechRecognizer.stopListening();

        if (newText.length() > 0) {
            String oldStr = fullSpokenText.getText() + ". ";
            fullSpokenText.setText(oldStr + newText);
            newText = "";
        }
    }
}
