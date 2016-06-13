package com.hardy.person.mybroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Hardy
 * @date {date}
 * @des
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class MyBootCompleted extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("###", "启动");
        Toast.makeText(context, "开机自启动了", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(context,MyService.class);
        context.startService(intent1);

    }
}
