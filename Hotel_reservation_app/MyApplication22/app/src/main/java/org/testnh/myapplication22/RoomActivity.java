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

public class RoomActivity extends AppCompatActivity{
    ActionBar actionBar;
    View slideMenu;
    MenuItem menuItem;
    DrawerLayout drawerLayout;
    TextView TextViewGoingJoinFromRoom,TextViewGoingLoginFromRoom,TextViewGoingShowFromRoom,TextViewGoingQuesFromRoom,
            TextViewGoingCheckFromRoom;

    ImageView actionBarLogo;
    boolean flag=false;

    myDBHelper_JB myHelper_JB;
    SQLiteDatabase memberDB;
    TextView textviewLoginState1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_room);

        menuItem=(MenuItem) findViewById(R.id.menuDraw);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_room);
        slideMenu = (View) findViewById(R.id.slideMenu_room);

        actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.layout_actionbar);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        //액션 바 안의 메뉴 아이템들 선언
        actionBarLogo=(ImageView) findViewById(R.id.actionBarLogo);
        TextViewGoingLoginFromRoom=(TextView)findViewById(R.id.TextViewGoingLoginFromRoomNh);
        TextViewGoingJoinFromRoom=(TextView)findViewById(R.id.TextViewGoingJoinFromRoomNh);
        TextViewGoingShowFromRoom=(TextView)findViewById(R.id.TextViewGoingShowFromRoomNh);
        TextViewGoingQuesFromRoom=(TextView) findViewById(R.id.TextViewGoingQuesFromRoomNh);
        TextViewGoingCheckFromRoom=(TextView) findViewById(R.id.TextViewGoingCheckFromRoomNh);

        textviewLoginState1 = (TextView)findViewById(R.id.textviewLoginState1);

        //액션 바 안의 메뉴 아이템들 클릭 메소드들

        actionBarLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RoomActivity.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingLoginFromRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RoomActivity.this,LonInActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingJoinFromRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RoomActivity.this,JoinActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingShowFromRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RoomActivity.this,ShowActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingQuesFromRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RoomActivity.this, listView.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingCheckFromRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RoomActivity.this, CheckActivity.class);
                finish();
                startActivity(intent);
            }
        });
        //메뉴의 끝

        android.widget.Gallery galleryRoom1_hj = (android.widget.Gallery) findViewById(R.id.galleryRoom1_hj);
        android.widget.Gallery galleryRoom2_hj = (android.widget.Gallery) findViewById(R.id.galleryRoom2_hj);
        android.widget.Gallery galleryRoom3_hj = (android.widget.Gallery) findViewById(R.id.galleryRoom3_hj);
        MyGalleryAdapter galleryRoomAdapter1_hj = new MyGalleryAdapter(this);
        MyGalleryAdapter2 galleryRoomAdapter2_hj = new MyGalleryAdapter2(this);
        MyGalleryAdapter3 galleryRoomAdapter3_hj = new MyGalleryAdapter3(this);
        galleryRoom1_hj.setAdapter(galleryRoomAdapter1_hj);
        galleryRoom2_hj.setAdapter(galleryRoomAdapter2_hj);
        galleryRoom3_hj.setAdapter(galleryRoomAdapter3_hj);


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
    }//end of onCreate for RoomActivity
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
    //갤러리

    public class MyGalleryAdapter extends android.widget.BaseAdapter {

        android.content.Context context;
        Integer[] posterID = { R.drawable.room01, R.drawable.room02,
                R.drawable.room03 };

        public MyGalleryAdapter(android.content.Context c) {
            context = c;
        }

        public int getCount() {
            return posterID.length;
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, android.view.ViewGroup parent) {
            ImageView imageview = new ImageView(context);
            imageview.setLayoutParams(new android.widget.Gallery.LayoutParams(300, 400));
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageview.setPadding(5, 5, 5, 5);
            imageview.setImageResource(posterID[position]);

            final int pos = position;
            imageview.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, android.view.MotionEvent event) {
                    ImageView ivPoster = (ImageView) findViewById(R.id.imageViewRoom1_hj);
                    ivPoster.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ivPoster.setImageResource(posterID[pos]);
                    return false;
                }
            });

            return imageview;
        }
    }

    public class MyGalleryAdapter2 extends android.widget.BaseAdapter {

        android.content.Context context;
        Integer[] posterID = { R.drawable.room04, R.drawable.room05,
                R.drawable.room06 };

        public MyGalleryAdapter2(android.content.Context c) {
            context = c;
        }

        public int getCount() {
            return posterID.length;
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, android.view.ViewGroup parent) {
            ImageView imageview = new ImageView(context);
            imageview.setLayoutParams(new android.widget.Gallery.LayoutParams(300, 400));
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageview.setPadding(5, 5, 5, 5);
            imageview.setImageResource(posterID[position]);

            final int pos = position;
            imageview.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, android.view.MotionEvent event) {
                    ImageView ivPoster = (ImageView) findViewById(R.id.imageViewRoom2_hj);
                    ivPoster.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ivPoster.setImageResource(posterID[pos]);
                    return false;
                }
            });

            return imageview;
        }
    }

    public class MyGalleryAdapter3 extends android.widget.BaseAdapter {

        android.content.Context context;
        Integer[] posterID = { R.drawable.room07, R.drawable.room08,
                R.drawable.room09 };

        public MyGalleryAdapter3(android.content.Context c) {
            context = c;
        }

        public int getCount() {
            return posterID.length;
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, android.view.ViewGroup parent) {
            ImageView imageview = new ImageView(context);
            imageview.setLayoutParams(new android.widget.Gallery.LayoutParams(300, 400));
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageview.setPadding(5, 5, 5, 5);
            imageview.setImageResource(posterID[position]);

            final int pos = position;
            imageview.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, android.view.MotionEvent event) {
                    ImageView ivPoster = (ImageView) findViewById(R.id.imageViewRoom3_hj);
                    ivPoster.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ivPoster.setImageResource(posterID[pos]);
                    return false;
                }
            });

            return imageview;
        }
    }
}
