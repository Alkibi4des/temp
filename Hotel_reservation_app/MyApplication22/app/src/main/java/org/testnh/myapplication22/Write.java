package org.testnh.myapplication22;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//글쓰기페이지 역할

public class Write extends AppCompatActivity {
    ActionBar actionBar;

    SQLiteDatabase db;
    SQLiteDatabase db2;
    myDBHelper helper;
    myDBHelper_JB reserveDB;
    EditText sub;
    //EditText writer;
    EditText content;
    Calendar curDate = Calendar.getInstance();
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        sub = (EditText) findViewById(R.id.writeSubEditViewSH);
        //writer = (EditText) findViewById(R.id.writeWriterEditViewSH);
        content = (EditText) findViewById(R.id.writeContentEditViewSH);
        Button button = (Button) findViewById(R.id.writeInputBtnSH);

        //가져온 id를 변수에 넣기
        String idToCheck = getLoginID();

        actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.layout_actionbar);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(false);

        //사용할 db를 연동 시켜준다
        helper = new myDBHelper(this);//boardList db 연결
        reserveDB = new myDBHelper_JB(this);//groupDB db 연결
        db = helper.getWritableDatabase();//쓰기 기능 연결
        db2 = reserveDB.getWritableDatabase();//쓰기 기능 연결

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //글을 다 입력하고 클릭하는 동시에
//                int year = curDate.get(Calendar.YEAR);
//                int month = curDate.get(Calendar.MONTH);
//                int day = curDate.get(Calendar.DAY_OF_MONTH);
//                int hour = curDate.get(Calendar.HOUR);
//                int min = curDate.get(Calendar.MINUTE);
//                int sec = curDate.get(Calendar.SECOND);
//                //변수에 각각의 값을 담아주고
//                String now = year + "-" + month + "-" + day + "\n" + hour + ":" + min + ":" + sec; //글쓴시간을 저장

                // 현재시간을 msec 으로 구한다.
                long now = System.currentTimeMillis();
                // 현재시간을 date 변수에 저장한다.
                Date date = new Date(now);
                // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");//HH:mm:ss
                // nowDate 변수에 값을 저장한다.
                String formatDate = sdfNow.format(date);

                if (getLoginID() != null) {
                    //db에 insert시켜준다
                    db.execSQL("insert into boardList values(null,'" + sub.getText().toString() + "','" + getLoginID() + "','" + formatDate + "','" + content.getText().toString() + "');");

                    //그다음 값을 intent에 실어주고 intent를 실행시켜준후 finish로 페이지를 닫아준다
                    Intent intent = new Intent(Write.this, listView.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "로그인해야 글을 작성할수 있습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private String getLoginID() {
        try {
            Cursor cursor = db2.rawQuery("SELECT id FROM memberTBL WHERE login = 1" + ";"
                    , null);
            cursor.moveToNext();

            return cursor.getString(0);
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "로그인해야 글을 작성할수 있습니다.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}