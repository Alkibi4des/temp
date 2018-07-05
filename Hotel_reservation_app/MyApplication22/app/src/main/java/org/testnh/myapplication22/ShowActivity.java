package org.testnh.myapplication22;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
    ActionBar actionBar;
    View slideMenu;
    MenuItem menuItem;
    DrawerLayout drawerLayout;
    TextView TextViewGoingJoinFromShow,TextViewGoingLoginFromShow,TextViewGoingRoomFromShow,TextViewGoingQuesFromShow,
            TextViewGoingCheckFromShow;
    ImageView actionBarLogo;
    boolean flag=false;

    myDBHelper_JB myHelper_JB;
    SQLiteDatabase memberDB;
    TextView textviewLoginState1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show);


        menuItem=(MenuItem) findViewById(R.id.menuDraw);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_show);
        slideMenu = (View) findViewById(R.id.slideMenu_show);

        actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.layout_actionbar);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        //액션 바 안의 메뉴 아이템들 선언
        actionBarLogo=(ImageView) findViewById(R.id.actionBarLogo);
        TextViewGoingLoginFromShow=(TextView)findViewById(R.id.TextViewGoingLoginFromShowNh);
        TextViewGoingJoinFromShow=(TextView)findViewById(R.id.TextViewGoingJoinFromShowNh);
        TextViewGoingRoomFromShow=(TextView) findViewById(R.id.TextViewGoingRoomFromShowNh);
        TextViewGoingQuesFromShow=(TextView) findViewById(R.id.TextViewGoingQuesFromShowNh);
        TextViewGoingCheckFromShow=(TextView) findViewById(R.id.TextViewGoingCheckFromShowNh);

        textviewLoginState1 = (TextView)findViewById(R.id.textviewLoginState1);


        //액션 바 안의 메뉴 아이템들 클릭 메소드들

        actionBarLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowActivity.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingLoginFromShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowActivity.this,LonInActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingJoinFromShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowActivity.this,JoinActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingRoomFromShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowActivity.this,RoomActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingQuesFromShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowActivity.this,listView.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingCheckFromShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowActivity.this,CheckActivity.class);
                finish();
                startActivity(intent);
            }
        });


        myHelper_JB = new myDBHelper_JB(this);

        final ArrayList<String> memberId = new ArrayList<>();
        final ArrayList<String> memberlogin = new ArrayList<>();

        memberDB = myHelper_JB.getWritableDatabase();
        memberDB = myHelper_JB.getReadableDatabase();
        Cursor cursor;
        cursor = memberDB.rawQuery("Select id,login from memberTBL;",null);

        String strId = "";
        String strlogin= "";

        while(cursor.moveToNext()){
            strId = cursor.getString(0);
            strlogin = cursor.getString(1);

            memberId.add(strId);
            memberlogin.add(strlogin);
        }

        for(int i=0; i<memberId.size();i++){
            if(memberlogin.get(i).toString().equals("1")){
                //Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                textviewLoginState1.setText(memberId.get(i).toString()+"님 안녕하세요");
                //Intent intent  = new Intent(getApplicationContext(),)
                break;
            }

        }
        //memberDB.execSQL("update memberTBL set login=0;");

        cursor.close();
        memberDB.close();

    }//end of onCreate for ShowActivity

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sample,menu);
        return true;
    }
    //menutoggle
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(flag){
           // item.setIcon(R.drawable.menu1);
            drawerLayout.closeDrawer(slideMenu);
            flag=false;
        }
        else{
          //  item.setIcon(R.drawable.menu2);
            drawerLayout.openDrawer(slideMenu);
            flag=true;
        }
        return super.onOptionsItemSelected(item);
    }
}
