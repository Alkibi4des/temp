package org.testnh.myapplication22;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LonInActivity extends AppCompatActivity{
    ActionBar actionBar;
    View slideMenu;
    MenuItem menuItem;
    DrawerLayout drawerLayout;
    TextView TextViewGoingJoinFromLogin,TextViewGoingRoomFromLogin,TextViewGoingShowFromLogin,
            TextViewGoingQuesFromLogin,TextViewGoingCheckFromLogin;
    Button ButtonGoingJoinFromLogin;
    ImageView actionBarLogo;
    boolean flag=false;


    myDBHelper_JB1 myHelper;
    EditText EditId_JB,EditPwd_JB;
    Button BtnLogin_JB, BtnJoin_JB;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        //슬라이드 메뉴
        menuItem=(MenuItem) findViewById(R.id.menuDraw);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_login);
        slideMenu = (View) findViewById(R.id.slideMenu_login);

        actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.layout_actionbar);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        //액션 바 안의 메뉴 아이템들 선언
        actionBarLogo=(ImageView) findViewById(R.id.actionBarLogo);
        TextViewGoingJoinFromLogin=(TextView) findViewById(R.id.TextViewGoingJoinFromLoginNh);
        ButtonGoingJoinFromLogin=(Button) findViewById(R.id.ButtonGoingJoinFromLoginNh);
        TextViewGoingRoomFromLogin=(TextView)findViewById(R.id.TextViewGoingRoomFromLoginNh);
        TextViewGoingShowFromLogin=(TextView) findViewById(R.id.TextViewGoingShowFromLoginNh);
        TextViewGoingQuesFromLogin=(TextView)findViewById(R.id.TextViewGoingQuesFromLoginNh) ;
        TextViewGoingCheckFromLogin=(TextView) findViewById(R.id.TextViewGoingCheckFromLoginNh);


        //액션 바 안의 메뉴 아이템들 클릭 메소드들
        actionBarLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LonInActivity.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingJoinFromLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LonInActivity.this,JoinActivity.class);
                finish();
                startActivity(intent);
            }
        });
        ButtonGoingJoinFromLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LonInActivity.this,JoinActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingRoomFromLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LonInActivity.this,RoomActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingShowFromLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LonInActivity.this,ShowActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingQuesFromLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LonInActivity.this,listView.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingCheckFromLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LonInActivity.this,CheckActivity.class);
                finish();
                startActivity(intent);
            }
        });



        ////////////////////////////////////////////////////////////////////////////////////////////


        EditId_JB = findViewById(R.id.EditId_JB);
        EditPwd_JB = findViewById(R.id.EditPwd_JB);
        BtnLogin_JB = findViewById(R.id.BtnLogin_JB);
        //BtnJoin_JB = findViewById(R.id.BtnJoin_JB);

        final ArrayList<String> memberId = new ArrayList<>();
        final ArrayList<String> memberPwd = new ArrayList<>();

        myHelper = new myDBHelper_JB1(LonInActivity.this);

        sqlDB = myHelper.getWritableDatabase();

        sqlDB.execSQL("update memberTBL set login=0;");
        BtnLogin_JB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB= myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("Select id,pwd from memberTBL;",null);

                String strId = "";
                String strPwd= "";

                while(cursor.moveToNext()){
                    strId = cursor.getString(0);
                    strPwd = cursor.getString(1);

                    memberId.add(strId);
                    memberPwd.add(strPwd);
                }

                for(int i=0; i<memberId.size();i++){
                    if(memberId.get(i).equals(EditId_JB.getText().toString())&& memberPwd.get(i).equals(EditPwd_JB.getText().toString())){
                        Toast.makeText(getApplicationContext(), "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        //sqlDB.execSQL("update memberTBL set login = 1 where id = "+"'+EditId_JB.getText().toString()+'"+";");
                        sqlDB.execSQL("update memberTBL set login = 1 where id = '"+EditId_JB.getText().toString()+"';");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    }
                    else if(i==memberId.size()-1 && (!memberId.get(i).equals(EditId_JB.getText().toString()) || !memberPwd.get(i).equals(EditPwd_JB.getText().toString()))){
                        Toast.makeText(getApplicationContext(), "로그인이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                cursor.close();
                sqlDB.close();
            }

        });

        /*BtnJoin_JB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Join2Activity.class);
                startActivity(intent);
            }
        });*/


    }//end of onCreate for LoginActivity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sample,menu);
        return true;
    }
    //menutoggle
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (flag) {
            //item.setIcon(R.drawable.menu1);
            drawerLayout.closeDrawer(slideMenu);
            flag = false;
        } else {
            //item.setIcon(R.drawable.menu2);
            drawerLayout.openDrawer(slideMenu);
            flag = true;
        }
        return super.onOptionsItemSelected(item);
    }
    public class myDBHelper_JB1 extends SQLiteOpenHelper {
        public myDBHelper_JB1(Context context){
            super(context, "/mnt/sdcard/groupDB",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //TODO Auto-generated method stub
            //db.execSQL("create table memberTBL (id char(10) primary key, pwd char(15), name char(14), phone char(13), login int(1));");
            //db.execSQL("CREATE TABLE `boardList` ( `number` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `subject` TEXT NOT NULL, `writer` TEXT NOT NULL, `date` TEXT NOT NULL, `content` TEXT NOT NULL );");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVerision){
            //TODO Auto-generate method stub
        }
    }

}
/*
class myDBLoginHelper_JB extends SQLiteOpenHelper {
    public myDBLoginHelper_JB(Context context){
        super(context, "groupDB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO Auto-generated method stub
        db.execSQL("create table memberTBL (id char(10) primary key, pwd char(15), " +
                "name char(14), phone char(13))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVerision){
        //TODO Auto-generate method stub
    }

}*/
