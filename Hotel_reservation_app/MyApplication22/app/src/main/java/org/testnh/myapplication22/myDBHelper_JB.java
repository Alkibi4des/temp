package org.testnh.myapplication22;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDBHelper_JB extends SQLiteOpenHelper {
    public myDBHelper_JB(Context context) {
        super(context, "/mnt/sdcard/groupDB", null, 1);
        //Toast.makeText(context.getApplicationContext(), "DB 연결됨",
        //        Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
       /* Context context=null;
        Toast.makeText(context.getApplicationContext(), "생성됨",
                Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        //db.execSQL("update memberTBL set ");
    }
}