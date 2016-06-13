package com.hardy.person.tts_test;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mTextToSpeak;
    private Button mSpeak;
    private Button mWriteToFile;
    private Button mSpeakFromFile;
    private TextToSpeech mTextToSpeech;
    private MediaPlayer mMediaPlayer;
    private final String filePath = "/mnt/sdcard/MyTTS";

    private void assignViews() {
        mTextToSpeak = (EditText) findViewById(R.id.textToSpeak);
        mSpeak = (Button) findViewById(R.id.speak);
        mWriteToFile = (Button) findViewById(R.id.writeToFile);
        mSpeakFromFile = (Button) findViewById(R.id.speakFromFile);
        mMediaPlayer = new MediaPlayer();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        mSpeak.setOnClickListener(this);
        mWriteToFile.setOnClickListener(this);
        mSpeakFromFile.setOnClickListener(this);
        mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //经测试，这一部分只是判断TTS里是否有朗读这种语言的能力，实际上朗读用什么语言取决于要读的文本是什么类型的！
                    int result = mTextToSpeech.setLanguage(Locale.CHINA);
                    if (result != TextToSpeech.LANG_AVAILABLE && result != TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                        Toast.makeText(MainActivity.this, "TTS暂时不支持这种语言！！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        String text = mTextToSpeak.getText().toString();
        switch (v.getId()) {
            case R.id.speak:
                if (!TextUtils.isEmpty(text)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mTextToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null,TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED);
                    }
                }
                break;
            case R.id.writeToFile:
                if (!TextUtils.isEmpty(text)) {
                    File file = new File(filePath);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mTextToSpeech.synthesizeToFile(text,null,file,TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED);
                    }
                }
                break;
            case R.id.speakFromFile:
                try {
                    mMediaPlayer.setDataSource(filePath);
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭自动朗读
        mTextToSpeech.shutdown();
    }
}
