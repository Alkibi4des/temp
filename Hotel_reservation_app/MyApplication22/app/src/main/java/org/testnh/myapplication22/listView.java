package org.testnh.myapplication22;
//문의 사항 액티비티 입니다.
//레이아웃상세에서 QuesActivity 입니다.
//layout_ ques.xml 는 activity_list 입니다.

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class listView extends AppCompatActivity {
    //메뉴
    ActionBar actionBar;
    View slideMenu;
    MenuItem menuItem;
    DrawerLayout drawerLayout;
    TextView TextViewGoingJoinFromQues,TextViewGoingLoginFromQues,TextViewGoingRoomFromQues,TextViewGoingShowFromQues,
            TextViewGoingCheckFromQues;
    ImageView actionBarLogo;
    boolean flag=false;

    myDBHelper_JB myHelper_JB;
    SQLiteDatabase memberDB;
    TextView textviewLoginState1;

    //database관련 변수들
    SQLiteDatabase db;
    myDBHelper helper;
    ListView listView;
    ItemListAdapter adapter;
    String num;
    String sub;
    String name;
    String date;
    String content;
    Button writeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        /////////////////////////////////메뉴 관련/////////////////////////////////////////////
        menuItem=(MenuItem) findViewById(R.id.menuDraw);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_ques);//수정하기
        slideMenu = (View) findViewById(R.id.slideMenu_ques);//수정하기

        actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.layout_actionbar);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        //액션 바 안의 메뉴 아이템들 선언
        actionBarLogo=(ImageView) findViewById(R.id.actionBarLogo);
        TextViewGoingLoginFromQues=(TextView)findViewById(R.id.TextViewGoingLoginFromQuesNh);
        TextViewGoingJoinFromQues=(TextView)findViewById(R.id.TextViewGoingJoinFromQuesNh);
        TextViewGoingRoomFromQues=(TextView) findViewById(R.id.TextViewGoingRoomFromQuesNh);
        TextViewGoingShowFromQues=(TextView) findViewById(R.id.TextViewGoingShowFromQuesNh);
        TextViewGoingCheckFromQues=(TextView) findViewById(R.id.TextViewGoingCheckFromQuesNh);

        textviewLoginState1 = (TextView)findViewById(R.id.textviewLoginState1);

        //액션 바 안의 메뉴 아이템들 클릭 메소드들

        actionBarLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(listView.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingLoginFromQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(listView.this,LonInActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingJoinFromQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(listView.this,JoinActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingRoomFromQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(listView.this,RoomActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingShowFromQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(listView.this,ShowActivity.class);
                finish();
                startActivity(intent);
            }
        });
        TextViewGoingCheckFromQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(listView.this,CheckActivity.class);
                finish();
                startActivity(intent);
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////

        //list를 불러와야하기 때문에 db를 연결시켜준다
        helper = new myDBHelper(this);

        //그리고 listview와 adapter를 설정해준다
        listView = (ListView) findViewById(R.id.listViewSH);
        adapter = new ItemListAdapter(listView.this);
        String[] arr = new String[5];

        //쿼리문을 설정해주고
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from boardList;", null);

        //while문으로 원하는값을 변수에 담아준다
        //그리고 곧바로 뿌려준다
        while (c.moveToNext()){
            arr[0] = num = c.getString(c.getColumnIndex("number"));
            arr[1] = sub = c.getString(c.getColumnIndex("subject"));
            arr[2] = name = c.getString(c.getColumnIndex("writer"));
            arr[3] = date = c.getString(c.getColumnIndex("date"));
            arr[4] = content = c.getString(c.getColumnIndex("content"));
            adapter.additem(new Item(arr));
            arr = new String[5];
        }
        listView.setAdapter(adapter);
        //해당 listView를 클릭하게 되면 상세보기 화면으로 넘어가게끔 설정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), Detail.class);
                Item curItem = (Item) adapter.getItem(position);
                String[] curData = curItem.boarditemdate();
                intent.putExtra("Item", curData);
                startActivity(intent);
		        //finish();
            }
        });

        //글쓰기 버튼을 클릭하게 되면 글쓰기 화면으로 넘어게가끔 설정
        writeBtn = (Button)findViewById(R.id.writeBtnSH);

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "눌렸음",
//                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Write.class);
                startActivity(intent);
                finish();
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



    }//end of onCreate for QuesActivity
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

