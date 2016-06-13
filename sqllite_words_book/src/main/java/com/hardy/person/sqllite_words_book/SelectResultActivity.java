package com.hardy.person.sqllite_words_book;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * @author Hardy
 * @date {date}
 * @des
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class SelectResultActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<String> wordsResult = (ArrayList<String>) bundle.getSerializable("result");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.list_item_layout,wordsResult);
        setListAdapter(adapter);
    }
}
