package org.testnh.myapplication22;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Join3Activity extends AppCompatActivity {
    ActionBar actionBar;
    View slideMenu;
    MenuItem menuItem;
    DrawerLayout drawerLayout;
    TextView TextViewGoingLoginFromJoin,TextViewGoingRoomFromJoin,TextViewGoingShowFromJoin,TextViewGoingQuesFromJoin,
            TextViewGoingCheckFromJoin;

    ImageView actionBarLogo;
    Button buttonJoin3Com;
    boolean flag=false;


    TextView TextViewCompleteNh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_join3);

        menuItem=(MenuItem) findViewById(R.id.menuDraw);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_join);
        slideMenu = (View) findViewById(R.id.slideMenu_join);

        actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.layout_actionbar);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        //액션 바 안의 메뉴 아이템들 선언
        actionBarLogo=(ImageView) findViewById(R.id.actionBarLogo);
        TextViewGoingLoginFromJoin=(TextView)findViewById(R.id.TextViewGoingLoginFromJoinNh);
        TextViewGoingRoomFromJoin=(TextView)findViewById(R.id.TextViewGoingRoomFromJoinNh);
        TextViewGoingShowFromJoin=(TextView) findViewById(R.id.TextViewGoingShowFromJoinNh);
        TextViewGoingQuesFromJoin=(TextView) findViewById(R.id.TextViewGoingQuesFromJoinNh);
        TextViewGoingCheckFromJoin=(TextView) findViewById(R.id.TextViewGoingCheckFromJoinNh);
        buttonJoin3Com=(Button) findViewById(R.id.buttonJoin3ComNh);



        //액션 바 안의 메뉴 아이템들 클릭 메소드들

        actionBarLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Join3Activity.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingLoginFromJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Join3Activity.this,LonInActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingRoomFromJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Join3Activity.this,RoomActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingShowFromJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Join3Activity.this,ShowActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingQuesFromJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Join3Activity.this,listView.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingCheckFromJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Join3Activity.this,CheckActivity.class);
                finish();
                startActivity(intent);
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        buttonJoin3Com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Join3Activity.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sample,menu);
        return true;
    }
    //menutoggle
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(flag){
            //item.setIcon(R.drawable.menu1);
            drawerLayout.closeDrawer(slideMenu);
            flag=false;
        }
        else{
            //item.setIcon(R.drawable.menu2);
            drawerLayout.openDrawer(slideMenu);
            flag=true;
        }
        return super.onOptionsItemSelected(item);
    }
}

