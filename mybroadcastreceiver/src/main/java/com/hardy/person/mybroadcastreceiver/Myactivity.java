package com.hardy.person.mybroadcastreceiver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Hardy
 * @date {date}
 * @des
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class Myactivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent intent = new Intent();
        intent.setAction("abc");
        sendBroadcast(intent);
    }
}
