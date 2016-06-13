package com.hardy.person.datastorage;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Hardy
 * @date {date}
 * @des
 * @updateAuthor
 * @updateDate
 * @updateDes
 */

public class FileActivity extends Activity implements View.OnClickListener{


    private EditText mInputData;
    private Button mWrite;
    private Button mRead;
    private TextView mShow;
    private String FILE_NAME = "temp_test_file";

    private void assignViews() {
        mInputData = (EditText) findViewById(R.id.inputData);
        mWrite = (Button) findViewById(R.id.write);
        mRead = (Button) findViewById(R.id.read);
        mShow = (TextView) findViewById(R.id.show);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_read_write_layout);
        assignViews();
        mWrite.setOnClickListener(this);
        mRead.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //调用openFileOutput方法会固定写在当前程序的data目录下
            case R.id.write:
                if (!TextUtils.isEmpty(mInputData.getText())){
                    try {
                        FileOutputStream fileOutputStream = openFileOutput(FILE_NAME,MODE_APPEND);
                        PrintStream printStream = new PrintStream(fileOutputStream);
                        byte[] bytes = mInputData.getText().toString().getBytes();
                        printStream.write(bytes);
                        printStream.close();
                        mInputData.setText("");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.read:
                try {
                    FileInputStream fileInputStream = openFileInput(FILE_NAME);
                    byte[] bytes = new byte[1024];
                    int hasRead = 0;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((hasRead = fileInputStream.read(bytes))>0){
                        stringBuilder.append(new String(bytes,0,hasRead));
                    }
                    mShow.setText(stringBuilder);
                    fileInputStream.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}
