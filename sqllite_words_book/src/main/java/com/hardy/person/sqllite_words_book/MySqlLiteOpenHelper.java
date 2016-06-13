package com.hardy.person.sqllite_words_book;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * @author Hardy
 * @date {date}
 * @des
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class MySqlLiteOpenHelper extends SQLiteOpenHelper {


    //已经在update方法里新增了新列了为什么还要修改建表语句？
    //因为是为了使那些刚刚安装的app是最新版本的数据库
    static final String createTable_State = "create table words(" +
            "id integer primary key autoincrement," +
            "word," +
            "chinese" +
            ")";
    Context mContext;


    public MySqlLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable_State);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
//                db.execSQL("drop table if exists words");
                //若是不使用alter语句则会使原先保存的信息丢失
                db.execSQL("alter table words add column chinese");
            case 2:

            default:
        }
        Toast.makeText(mContext, "Version" + oldVersion + " has updated to Version" + newVersion, Toast.LENGTH_SHORT).show();
    }

}
