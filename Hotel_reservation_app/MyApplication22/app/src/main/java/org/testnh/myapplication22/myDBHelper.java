package org.testnh.myapplication22;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class myDBHelper extends SQLiteOpenHelper {
    public myDBHelper(Context context) {
        super(context, "/mnt/sdcard/boardList.db", null, 1);
        //Toast.makeText(context.getApplicationContext(), "DB 연결됨",
        //        Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `boardList` ( `number` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `subject` TEXT NOT NULL, `writer` TEXT NOT NULL, `date` TEXT NOT NULL, `content` TEXT NOT NULL );");
       /* Context context=null;
        Toast.makeText(context.getApplicationContext(), "생성됨",
                Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS boardList.db");
        onCreate(db);
    }
}