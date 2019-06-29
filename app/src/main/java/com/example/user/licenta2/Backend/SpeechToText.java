package com.example.user.licenta2.Backend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.user.licenta2.Fragments.Fragment_Experience;
import com.example.user.licenta2.MyClasses.Education;
import com.example.user.licenta2.MyClasses.Experience;
import com.example.user.licenta2.MyClasses.Skill;

import java.util.ArrayList;
import java.util.Locale;


public class SpeechToText {

    private SpeechRecognizer mSpeechRecognizer;
    private Context currentContext;
    private Intent mSpeechRecognizerIntent;
    private boolean isSpeacking = false;
    private String myText = "";
    private ExperienceListAdapter experienceListAdapter;
    private SkillListAdapter skillListAdapter;


    public SpeechToText(Context context) {
        currentContext = context;
        createInstanceForSpeechRecognizer(context);
        this.experienceListAdapter = null;
        this.skillListAdapter = null;
    }

    public SpeechToText(Context context, ExperienceListAdapter _experienceAdapter) {
        currentContext = context;
        createInstanceForSpeechRecognizer(context);
        this.experienceListAdapter = _experienceAdapter;
        this.skillListAdapter = null;
    }


    public SpeechToText(Context context, SkillListAdapter _skillAdapter) {
        currentContext = context;
        createInstanceForSpeechRecognizer(context);
        this.experienceListAdapter = null;
        this.skillListAdapter = _skillAdapter;

    }


    private void createInstanceForSpeechRecognizer(Context context) {
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);

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
                Log.d("MyDebug", "EndOfSpeech | " + getText());

//                isSpeacking = false;
            }

            @Override
            public void onError(int error) {
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
                Log.d("MyDebug", "Results:" + results.toString());
                myText = matches.get(0);

                if(experienceListAdapter != null && skillListAdapter == null) {
                    Experience newExperience = new Experience(myText);
                    experienceListAdapter.add(newExperience);
                    experienceListAdapter.notifyDataSetChanged();
                }
                else if(experienceListAdapter == null && skillListAdapter != null) {
                    Skill newSkill = new Skill(myText);
                    skillListAdapter.add(newSkill);
                    skillListAdapter.notifyDataSetChanged();
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
    }

    public void startListening(SpeechRecognizer mSpeechRecognizer, Intent mSpeechRecognizerIntent) {
        isSpeacking = true;
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
    }

    public void stopListening(SpeechRecognizer mSpeechRecognizer) {
        isSpeacking = false;
        mSpeechRecognizer.stopListening();
    }

    public boolean getIsSpeacking() {
        return isSpeacking;
    }

    public SpeechRecognizer getMySpeechRecognizer() {
        return this.mSpeechRecognizer;
    }

    public Intent getmSpeechRecognizerIntent() {
        return this.mSpeechRecognizerIntent;
    }

    public String getText() {
        return myText.toString();
    }

    public void resetText() {
        this.myText = "";
    }

}
