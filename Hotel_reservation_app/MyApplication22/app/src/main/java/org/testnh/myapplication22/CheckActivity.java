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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CheckActivity extends AppCompatActivity{
    ActionBar actionBar;
    View slideMenu;
    MenuItem menuItem;
    DrawerLayout drawerLayout;
    TextView TextViewGoingJoinFromCheck,TextViewGoingLoginFromCheck,TextViewGoingRoomFromCheck,TextViewGoingQuesFromCheck,
            TextViewGoingShowFromCheck;
    ImageView actionBarLogo;

    myDBHelper_JB myHelper_JB;
    SQLiteDatabase memberDB;
    TextView textviewLoginState1;

    boolean flag=false;
    // 예매 취소와 확인을 위한 버튼
    //Button buttonForDel, buttonForCheck;

    //추가
    ListView rListView;
    ReservationItemAdapter adapter;
    SQLiteDatabase reserveDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_check);

        //HB추가
        TextView idLabel = findViewById(R.id.tv_reservation_User_HB);
        textviewLoginState1 = (TextView)findViewById(R.id.textviewLoginState1);

        rListView =  findViewById(R.id.lv_reservation_checkList_HB);
        adapter = new ReservationItemAdapter();


        MyDBHelper helper_HB = new MyDBHelper(this);
        reserveDB = helper_HB.getReadableDatabase();
        reserveDB.execSQL("CREATE TABLE IF NOT EXISTS reservation ( orderNo INTEGER PRIMARY KEY AUTOINCREMENT, user_ID TEXT NOT NULL, roomType TEXT NOT NULL, roomNo INTEGER NOT NULL, checkInDay INTEGER NOT NULL, checkOutDay INTEGER NOT NULL, adultNo INTEGER NOT NULL, childrenNo INTEGER NOT NULL, orderDay INTEGER NOT NULL )");
        //idLabel.setText(getLoginID()+"님의 예약 내역");
        String tmpID = getLoginID();
        tmpID = tmpID==null? "로그인이 필요합니다." : tmpID+"님의 예약 내역";
        idLabel.setText(tmpID);
        //예약정보를 뽑아낸다
        String idToCheck = getLoginID();
        //String idToCheck = "tempID";
        try {
            Cursor cursor = reserveDB.rawQuery("SELECT * FROM reservation WHERE user_ID = '" + idToCheck + "';"
                    , null);

            while(cursor.moveToNext()){
                adapter.addItem(new ReservationItem(cursor.getInt(0),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getInt(8)
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        rListView.setAdapter(adapter);

        //HB추가



        menuItem=(MenuItem) findViewById(R.id.menuDraw);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_check);
        slideMenu = (View) findViewById(R.id.slideMenu_check);

        actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.layout_actionbar);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        //액션 바 안의 메뉴 아이템들 선언
        actionBarLogo=(ImageView) findViewById(R.id.actionBarLogo);
        TextViewGoingLoginFromCheck=(TextView)findViewById(R.id.TextViewGoingLoginFromCheckNh);
        TextViewGoingJoinFromCheck=(TextView)findViewById(R.id.TextViewGoingJoinFromCheckNh);
        TextViewGoingRoomFromCheck=(TextView) findViewById(R.id.TextViewGoingRoomFromCheckNh);
        TextViewGoingQuesFromCheck=(TextView) findViewById(R.id.TextViewGoingQuesFromCheckNh);
        TextViewGoingShowFromCheck=(TextView) findViewById(R.id.TextViewGoingShowFromCheckNh);

        //buttonForCheck=(Button) findViewById(R.id.buttonForCheckNh);


        //액션 바 안의 메뉴 아이템들 클릭 메소드들

        actionBarLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckActivity.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingLoginFromCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckActivity.this,LonInActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingJoinFromCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckActivity.this,JoinActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingRoomFromCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckActivity.this,RoomActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingQuesFromCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckActivity.this,listView.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingShowFromCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckActivity.this,ShowActivity.class);
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
//        buttonForCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(CheckActivity.this,MainActivity.class);
//                finish();
//                startActivity(intent);
//            }
//        });

    }//end of onCreate for CheckActivity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sample,menu);
        return true;
    }//end of onCreate for CheckActivity
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

    private String getLoginID(){
        try {
            Cursor cursor = reserveDB.rawQuery("SELECT id FROM memberTBL WHERE login = 1" + ";"
                    , null);
            cursor.moveToNext();

            return cursor.getString(0);
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }

    }

    private class MyDBHelper extends SQLiteOpenHelper {
        MyDBHelper(Context ctx){
            //super(ctx, "groupDB", null,1);
            super(ctx, "/mnt/sdcard/groupDB", null,1);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public class ReservationItemAdapter extends BaseAdapter {
        ArrayList<ReservationItem> rItems = new ArrayList<>();

        public void removeItem(int position){
            rItems.remove(position);
        }


        public void addItem(ReservationItem rItem){
            rItems.add(rItem);
        }

        @Override
        public Object getItem(int position) {
            return rItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return rItems.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int cPosition = position;
            ReservationItemView view = new ReservationItemView(getApplicationContext());
            final ReservationItem item = rItems.get(position);
            view.setOrderNo(item.getOrderNo());
            view.setOrderDay(item.getOrderDay());
            view.setRoomType(item.getRoomType());
            view.setCheckInDay(item.getCheckInDay());
            view.setCheckOutDay(item.getCheckOutDay());
            view.setRoomNo(item.getRoomNo());
            view.setAmountPrice(item.amountPrice);
            view.setCancelBtnListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reserveDB.execSQL("DELETE FROM reservation WHERE orderNo= " + item.getOrderNo()+";");
                    adapter.removeItem(cPosition);
                    adapter.notifyDataSetChanged();
                }
            });
            return view;
        }
    }
}
