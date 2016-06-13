package com.hardy.person.sqllite_words_book;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    //添加单词的按钮
    private Button mAddWord;
    //要添加的单词输入框
    private EditText mInputAdd;
    //查询单词按钮
    private Button mSelectWord;
    //要查询的单词或者关键字输入框
    private EditText mInputSelect;
    //自定义的SqlLiteOpenHelper类的对象，用来创建（若不存在）和打开指定名字的数据库
    private MySqlLiteOpenHelper mMySqlLiteOpenHelper;
    //指定数据库名字
    private final String dbName = "words.db";


    //实例化对象
    private void assignViews() {
        mAddWord = (Button) findViewById(R.id.addWord);
        mInputAdd = (EditText) findViewById(R.id.inputAdd);
        mSelectWord = (Button) findViewById(R.id.selectWord);
        mInputSelect = (EditText) findViewById(R.id.inputSelect);
        mMySqlLiteOpenHelper = new MySqlLiteOpenHelper(this, dbName, null, 2);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        mAddWord.setOnClickListener(this);
        mSelectWord.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //添加单词按钮事件处理
            case R.id.addWord:
                String inputWord = mInputAdd.getText().toString();
                //若输入框不为空才执行插入数据库操作
                if (!TextUtils.isEmpty(inputWord)) {
                    insert(mMySqlLiteOpenHelper.getReadableDatabase(), inputWord);
                    Toast.makeText(MainActivity.this, "Success to add!", Toast.LENGTH_SHORT).show();
                    break;
                }
                //为空则提示
                Toast.makeText(MainActivity.this, "Please input word be added!", Toast.LENGTH_SHORT).show();
                break;
            //查询单词按钮事件处理
            case R.id.selectWord:
                String searchWord = mInputSelect.getText().toString();
                if (!TextUtils.isEmpty(searchWord)) {
                    Intent intent = new Intent(MainActivity.this, SelectResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("result", select(mMySqlLiteOpenHelper.getReadableDatabase(), searchWord));
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                }
                //为空则提示
                Toast.makeText(MainActivity.this, "Please input word be selected!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    void insert(SQLiteDatabase db, String word) {
        String selectSql = "select word from words";
        Cursor cursor = db.rawQuery(selectSql, null);
        /*//或者
        cursor = db.query("words",new String[]{"word"},null,null,null,null,null);*/
        boolean isExist = false;
        while (cursor.moveToNext()) {
            if (word.equals(cursor.getString(0))) {
                isExist = true;
                break;
            }
        }
        if (!isExist)
            db.execSQL("insert into words values(null,?)", new String[]{word});
        else
            Toast.makeText(MainActivity.this, "The Word has already existed!", Toast.LENGTH_SHORT).show();
    }


    ArrayList<String> select(SQLiteDatabase db, String key) {
        String spl = "select word from words where word like ?";
        Cursor cursor = mMySqlLiteOpenHelper.getReadableDatabase().rawQuery(spl, new String[]{"%" + key + "%"});
        ArrayList<String> words = new ArrayList<>();
        while (cursor.moveToNext()) {
            words.add(cursor.getString(0));
        }
        return words;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMySqlLiteOpenHelper != null)
            mMySqlLiteOpenHelper.close();
    }
}
