package com.example.myapplication30;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    ActionBar actionBar;
    View slideMenu;
    MenuItem menuItem;
    DrawerLayout drawerLayout;
    TextView TextViewGoingJoin,TextViewGoingLoginFromMain,TextViewGoingRoomFromMain,TextViewGoingShowFromMain,TextViewGoingQuesFromMain,
            TextViewGoingCheckFromMain;
    ImageView actionBarLogo;
    boolean flag=false;

    myDBHelper_JB myHelper_JB;
    SQLiteDatabase memberDB;
    TextView textviewLoginState1;

    //HB추가 변수
    String userID;
    Button btn_confirm;
    String roomType;
    String adultNo;
    String childNo;
    myDBHelper_HB myHelper_HB;
    SQLiteDatabase reserveDB;
    int mCheckInDay;
    int mCheckOutDay;
    HashMap<Integer, Reservation> orderList = new HashMap<Integer, Reservation>();
    final int[] standardRoom =  {101, 102, 103, 104, 105, 106, 201, 202, 203, 204, 205, 206};
    final int[] deluxRoom    =  {301, 302, 303, 304, 401, 402, 403, 404, 501, 502, 503, 504};
    final int[] suiteRoom = {601, 602};
    //    //HB추가 변수 끝

    String[] roomItems={"Select Room","디럭스","스탠다드","스위트"};
    String[] numOfItems={"0","1","2","3","4","5"};


    private TextView textViewCheckOut,textViewCheckIn,textViewForInYear,textViewForOutYear;

    int mYear,mMonth,mDay,mYear2,mMonth2,mDay2;
    static final int DATE_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //api 26 이상에서의 퍼미션을 위해 추가함.
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        menuItem=(MenuItem) findViewById(R.id.menuDraw);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        slideMenu = (View) findViewById(R.id.slideMenu);

        actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.layout_actionbar);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        //액션 바 안의 메뉴 아이템들 선언
        TextViewGoingJoin=(TextView) findViewById(R.id.TextViewGoingJoinFromMainNh);
        TextViewGoingLoginFromMain=(TextView) findViewById(R.id.TextViewGoingLoginFromMainNh);
        TextViewGoingRoomFromMain=(TextView)findViewById(R.id.TextViewGoingRoomFromMainNh);
        TextViewGoingShowFromMain=(TextView)findViewById(R.id.TextViewGoingShowFromMainNh);
        TextViewGoingQuesFromMain=(TextView) findViewById(R.id.TextViewGoingQuesFromMainNh);
        TextViewGoingCheckFromMain=(TextView) findViewById(R.id.TextViewGoingCheckFromMainNh);

        textviewLoginState1 = (TextView)findViewById(R.id.textviewLoginState1);
        //액션 바 안의 메뉴 아이템들 클릭 메소드들
        //maing activity는 finish하지 말것!
        TextViewGoingJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,JoinActivity.class);
                startActivity(intent);
            }
        });
        TextViewGoingLoginFromMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LonInActivity.class);
                startActivity(intent);
            }
        });
        TextViewGoingRoomFromMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RoomActivity.class);
                startActivity(intent);
            }
        });
        TextViewGoingShowFromMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ShowActivity.class);
                startActivity(intent);
            }
        });
        TextViewGoingQuesFromMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,listView.class);
                startActivity(intent);
            }
        });
        TextViewGoingCheckFromMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CheckActivity.class);
                startActivity(intent);
            }
        });

        //Selecting Room
        Spinner spinnerForRoom=(Spinner) findViewById(R.id.spinnerForRoomNh);
        ArrayAdapter<String> adapterForRoom=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,roomItems);
        adapterForRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForRoom.setAdapter(adapterForRoom);//아이템이 선택되었을때 처리 p. 460

        //Selecting the number of people
        Spinner spinnerForAdult=(Spinner) findViewById(R.id.spinnerForAdultNh);
        ArrayAdapter<String> adapterForAdult=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,numOfItems);
        adapterForAdult.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForAdult.setAdapter(adapterForAdult);

        Spinner spinnerForChild=(Spinner) findViewById(R.id.spinnerForChildNh);
        ArrayAdapter<String> adapterForChild=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,numOfItems);
        adapterForChild.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForChild.setAdapter(adapterForChild);

        //HB추가
        spinnerForRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                roomType = roomItems[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerForAdult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adultNo = numOfItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerForChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                childNo = numOfItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //HB추가

        //날짜
        textViewCheckOut=(TextView)findViewById(R.id.textViewCheckOut_hj);
        textViewCheckIn=(TextView) findViewById(R.id.textViewCheckIn_hj);
        textViewForInYear=(TextView)findViewById(R.id.textViewForInYearNh);
        textViewForOutYear=(TextView)findViewById(R.id.textViewForOutYearNh);

        textViewCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        textViewCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID2);
            }
        });

        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);//
        mDay=c.get(Calendar.DAY_OF_MONTH);

        Calendar c2=Calendar.getInstance();
        mYear2=c2.get(Calendar.YEAR);
        mMonth2=c2.get(Calendar.MONTH);//
        mDay2=c2.get(Calendar.DAY_OF_MONTH);

        initButton();

        //예약확인 activity 테스트용
//        Button btn_goCheck = findViewById(R.id.btn_gotoCheck_HB);
//        btn_goCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, ReservationCheckActivity.class);
//                startActivity(i);
//            }
//        });
        //예약확인 activity 테스트용


        android.widget.Gallery galleryMain1 = (android.widget.Gallery) findViewById(R.id.galleryMain1);
        MyGalleryAdapter galleryMainAdapter1_hj = new MyGalleryAdapter(this);
        galleryMain1.setAdapter(galleryMainAdapter1_hj);


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



    }//end of onCreate

    public class MyGalleryAdapter extends android.widget.BaseAdapter {

        Context context;
        Integer[] posterID = { R.drawable.main01, R.drawable.main02,
                R.drawable.main03,R.drawable.main05,R.drawable.main06,R.drawable.main07 };

        public MyGalleryAdapter(Context c) {
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
            final ImageView imageview = new ImageView(context);
            imageview.setLayoutParams(new android.widget.Gallery.LayoutParams(200, 300));
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageview.setPadding(5, 5, 5, 5);
            imageview.setImageResource(posterID[position]);

            final int pos = position;

            imageview.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, android.view.MotionEvent event) {
                    ImageView ivPoster = (ImageView) findViewById(R.id.imageViewMain1_hj);
                    ivPoster.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ivPoster.setImageResource(posterID[pos]);
                    ivPoster.setScaleType(ImageView.ScaleType.FIT_XY);
                    return false;
                }
            });

            return imageview;
        }
    }
// 갤러리


    //checkIn checkOut
    private void updateDisplay() {
        textViewForInYear.setText(String.format("%d년",mYear));
        textViewCheckIn.setText(String.format("%d월 %d일",mMonth,mDay));
    }
    private void updateDisplay2() {
        textViewForOutYear.setText(String.format("%d년",mYear2));
        textViewCheckOut.setText(String.format("%d월 %d일",mMonth2,mDay2));
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear+1;
                    mDay = dayOfMonth;
                    //HB추가
                    mCheckInDay = mDay + mMonth * 100 + mYear * 10000;
                    //HB추가
                    updateDisplay();
                }
            };
    private DatePickerDialog.OnDateSetListener mDateSetListener2 =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear2 = year;
                    mMonth2 = monthOfYear+1;
                    mDay2 = dayOfMonth;
                    //HB추가
                    mCheckOutDay = mDay2 + mMonth2 * 100 + mYear2 * 10000;
                    //HB추가
                    updateDisplay2();
                }
            };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);
            case DATE_DIALOG_ID2:
                return new DatePickerDialog(this,mDateSetListener2,mYear2,mMonth2,mDay2);
        }
        return null;
    }
    //menu
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

    private String getLogedUserID(){
        if(myHelper_HB==null){
            myHelper_HB = new myDBHelper_HB(MainActivity.this);
        }
        if(reserveDB==null){
            reserveDB = myHelper_HB.getReadableDatabase();
            reserveDB.execSQL("CREATE TABLE IF NOT EXISTS reservation ( orderNo INTEGER PRIMARY KEY AUTOINCREMENT, user_ID TEXT NOT NULL, roomType TEXT NOT NULL, roomNo INTEGER NOT NULL, checkInDay INTEGER NOT NULL, checkOutDay INTEGER NOT NULL, adultNo INTEGER NOT NULL, childrenNo INTEGER NOT NULL, orderDay INTEGER NOT NULL )");
        }
        Cursor cursor = reserveDB.rawQuery("SELECT id FROM memberTBL WHERE login = 1" + ";"
                , null);
        if(cursor.moveToNext()) {
            return cursor.getString(0);
        }else return null;

    }

    private void initButton(){
        myHelper_HB = new myDBHelper_HB(MainActivity.this);
        reserveDB = myHelper_HB.getReadableDatabase();
        reserveDB.execSQL("CREATE TABLE IF NOT EXISTS reservation ( orderNo INTEGER PRIMARY KEY AUTOINCREMENT, user_ID TEXT NOT NULL, roomType TEXT NOT NULL, roomNo INTEGER NOT NULL, checkInDay INTEGER NOT NULL, checkOutDay INTEGER NOT NULL, adultNo INTEGER NOT NULL, childrenNo INTEGER NOT NULL, orderDay INTEGER NOT NULL )");

        btn_confirm = findViewById(R.id.btn_main_reservation_HB);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myHelper_HB==null){
                    myHelper_HB = new myDBHelper_HB(MainActivity.this);
                }
                if(reserveDB==null){
                    reserveDB = myHelper_HB.getReadableDatabase();
                    reserveDB.execSQL("CREATE TABLE IF NOT EXISTS reservation ( orderNo INTEGER PRIMARY KEY AUTOINCREMENT, user_ID TEXT NOT NULL, roomType TEXT NOT NULL, roomNo INTEGER NOT NULL, checkInDay INTEGER NOT NULL, checkOutDay INTEGER NOT NULL, adultNo INTEGER NOT NULL, childrenNo INTEGER NOT NULL, orderDay INTEGER NOT NULL )");
                }
                userID = getLogedUserID();
                if (userID==null) {
                    Toast.makeText(getApplicationContext(), "로그인 해주세요", Toast.LENGTH_LONG).show();
                }else if(!checkDateVaild()) {
                    Toast.makeText(getApplicationContext(), "숙박기간을 올바르게 선택해주세요", Toast.LENGTH_SHORT).show();
                }else if(!chechHeadNum()){
                    Toast.makeText(getApplicationContext(), "숙박인원을 올바르게 선택해주세요", Toast.LENGTH_SHORT).show();
                }else{
//                    myHelper = new myDBHelper(MainActivity.this);
//                    reserveDB = myHelper.getReadableDatabase();
                    //중복되는예약정보를뽑아낸다
                    try {
                        Cursor cursor = reserveDB.rawQuery("SELECT * FROM reservation WHERE roomType = '" + roomType
                                        + "' and ( checkInDay >=" + mCheckInDay + " and checkInDay <= " + mCheckOutDay
                                        + " or checkOutDay <= " + mCheckOutDay + " and checkOutDay >= " + mCheckInDay + ")"
                                , null);
                        int tmpInt;
                        orderList.clear();
                        while (cursor.moveToNext()) {
                            tmpInt = cursor.getInt(0);
                            orderList.put(tmpInt, new Reservation(tmpInt, cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getInt(3),
                                    cursor.getInt(4),
                                    cursor.getInt(5),
                                    cursor.getInt(6),
                                    cursor.getInt(7),
                                    cursor.getInt(8)
                            ));
                        }

                        cursor.close();
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
                    }
                    ArrayList<String> ableRoom = new ArrayList<>();
                    if(roomType.equalsIgnoreCase("스위트")){
                        for(int i : suiteRoom){
                            boolean flag = false;
                            for(int key : orderList.keySet()){
                                if(orderList.get(key).qRoomNum ==  i){
                                    flag = true;
                                    break;
                                }
                            }
                            if(!flag){ableRoom.add(i+"호");}
                        }
                    }else if(roomType.equalsIgnoreCase("디럭스")){
                        for(int i : deluxRoom){
                            boolean flag = false;
                            for(int key : orderList.keySet()){
                                if(orderList.get(key).qRoomNum ==  i){
                                    flag = true;
                                    break;
                                }
                            }
                            if(!flag){ableRoom.add(i+"호");}
                        }
                    }else if(roomType.equalsIgnoreCase("스탠다드")){
                        for(int i : standardRoom){
                            boolean flag = false;
                            for(int key : orderList.keySet()){
                                if(orderList.get(key).qRoomNum ==  i){
                                    flag = true;
                                    break;
                                }
                            }
                            if(!flag){ableRoom.add(i+"호");}
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"방 종류를 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    String[] roomList = new String[ableRoom.size()];
                    Iterator it = ableRoom.iterator();
                    for(int i=0; i<roomList.length;i++){
                        roomList[i] = (String)it.next();
                    }

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
                            MainActivity.this);
                    //alertBuilder.setIcon(R.drawable.ic_launcher_background);
                    alertBuilder.setTitle("예약을 원하시는 방을 선택하세요.");

                    final ArrayAdapter<String> roomListAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, roomList);

                    alertBuilder.setAdapter(roomListAdapter,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    // AlertDialog 안에 있는 AlertDialog
                                    String strName = roomListAdapter.getItem(id);
                                    final int tmpRoomNo = Integer.valueOf(strName.substring(0, strName.indexOf("호")));
                                    AlertDialog.Builder innBuilder = new AlertDialog.Builder(
                                            MainActivity.this);
                                    innBuilder.setMessage(strName);
                                    innBuilder.setTitle("예약을 완료하시려면 확인을 누르세요");
                                    innBuilder
                                            .setPositiveButton(
                                                    "확인",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(
                                                                DialogInterface dialog,
                                                                int which) {
                                                            Calendar cal = Calendar.getInstance();
                                                            try {
                                                                int mToday = cal.get(cal.YEAR) * 10000 + (cal.get(cal.MONTH) + 1) * 100 + cal.get(cal.DATE);
                                                                String test = "INSERT INTO reservation VALUES (user_ID ='" + userID + "', roomType = '" + roomType + "', roomNo = " + tmpRoomNo
                                                                        + ", checkInDay = " + mCheckInDay + ", checkOutDay = " + mCheckOutDay + ", adultNo = " + adultNo + ", childrenNo = " + childNo + ", orderDay" + mToday;
                                                                reserveDB.execSQL("INSERT INTO reservation (user_ID, roomType, roomNo, checkInDay, checkOutDay, adultNo, childrenNo, orderDay)  VALUES ('" + userID + "', '" + roomType + "', " + tmpRoomNo
                                                                        + "," + mCheckInDay + ", " + mCheckOutDay + "," + adultNo + "," + childNo + ", " + mToday+");");
                                                                dialog.dismiss();
                                                            }catch (Exception e){
                                                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    });
                                    innBuilder.show();
                                }
                            });
                    alertBuilder.show();
                }
            }
        });
    }
    private boolean checkDateVaild(){
        if(mYear>mYear2)return false;
        else if(mMonth>mMonth2)return false;
        else if(mDay>mDay2)return  false;
        else return true;
    }
    private boolean chechHeadNum(){
        return !(adultNo.equals("0")&&childNo.equals("0"));
    }

    public class myDBHelper_HB extends SQLiteOpenHelper {
        public myDBHelper_HB(Context ctx){
            //super(ctx, "groupDB", null, 1);
            super(ctx, "/mnt/sdcard/groupDB", null, 1);

        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS reservation ( orderNo INTEGER PRIMARY KEY AUTOINCREMENT, user_ID TEXT NOT NULL, roomType TEXT NOT NULL, roomNo INTEGER NOT NULL, checkInDay INTEGER NOT NULL, checkOutDay INTEGER NOT NULL, adultNo INTEGER NOT NULL, childrenNo INTEGER NOT NULL, orderDay INTEGER NOT NULL )");
            db.execSQL("create table memberTBL (id char(10) primary key, pwd char(15), name char(14), phone char(13), login int(1) default 0);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public class Reservation{
        int qOrderNum;
        String qUser_id;
        String qRoomType;
        int qRoomNum;
        int qCheckInDay;
        int qCheckOutDay;
        int qAdultNo;
        int qChildNo;
        int qOrderDay;

        public Reservation(int arg1, String arg2, String arg3, int arg4, int arg7, int arg10, int arg11, int arg12, int arg13){
            this.qOrderNum = arg1;
            this.qUser_id = arg2;
            this.qRoomType = arg3;
            this.qRoomNum = arg4;
            this.qCheckInDay = arg7;
            this.qCheckOutDay = arg10;
            this.qAdultNo = arg11;
            this.qChildNo = arg12;
            this.qOrderDay = arg13;
        }
    }

}
