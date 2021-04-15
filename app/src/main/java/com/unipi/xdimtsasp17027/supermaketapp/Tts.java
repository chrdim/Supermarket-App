package com.unipi.xdimtsasp17027.supermaketapp;

import android.content.Context;
import android.speech.tts.TextToSpeech;

public class Tts {

    private TextToSpeech tts;
    private TextToSpeech.OnInitListener initListener=new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int i) {


        }
    };

    public Tts(Context context){
        tts=new TextToSpeech(context,initListener);
    }

    public void speak(String message){
        tts.speak(message, TextToSpeech.QUEUE_ADD,null,null);
    }
}
