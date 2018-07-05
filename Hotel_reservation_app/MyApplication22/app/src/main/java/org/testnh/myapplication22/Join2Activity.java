package org.testnh.myapplication22;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Join2Activity extends AppCompatActivity{
    ActionBar actionBar;
    //Button BtnInput_JB;
    myDBHelper_JB myHelper;
    EditText EditId_JB,EditPwd_JB,EditName_JB,EditPhone_JB,id,pwd,name,phone;
    Button BtnInput_JB,BtnReset_JB,BtnSelect,BtnLogin_JB,BtnCheck_JB;
    SQLiteDatabase sqlDB;
    ImageView imageIcon_JB;
    boolean flag =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_join2);


        actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.layout_actionbar);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);


        EditId_JB = findViewById(R.id.EditId_JB);
        EditPwd_JB = findViewById(R.id.EditPwd_JB);
        EditName_JB = findViewById(R.id.EditName_JB);
        EditPhone_JB = findViewById(R.id.EditPhone_JB);

        imageIcon_JB = findViewById(R.id.ImageIcon_JB);

        BtnInput_JB = findViewById(R.id.BtnInput_JB);
        BtnLogin_JB = findViewById(R.id.BtnLogin_JB);
        BtnCheck_JB = findViewById(R.id.BtnCheck_JB);


        myHelper = new myDBHelper_JB(Join2Activity.this);
        sqlDB = myHelper.getWritableDatabase();

        //myHelper.onUpgrade(sqlDB,1,2);



        BtnCheck_JB.setOnClickListener((new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(overLap(EditId_JB.getText().toString())) {
                                showMessage("이미 중복된 아이디 입니다.");
                                imageIcon_JB.setImageResource(R.drawable.xx);
                            }else if(EditId_JB.getText().toString().replace(" ","") .equals("")){
                                showMessage("아이디를 입력하셔야 합니다.");
                                imageIcon_JB.setImageResource(R.drawable.xx);
                            }else{
                                showMessage("사용 가능한 아이디 입니다.");
                                imageIcon_JB.setImageResource(R.drawable.check);
                    flag = false;
                }
            }
        }));
        BtnInput_JB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkPwd = "!@$%^&*()_+-=|<>?,./";


                if((EditId_JB.getText().toString().replace(" ","") .equals("") ||
                        EditPwd_JB.getText().toString().replace(" ","") .equals("") ||
                        EditName_JB.getText().toString().replace(" ","") .equals("")) ||
                        EditPhone_JB.getText().toString().replace(" ","") .equals("")){
                    Toast.makeText(getApplicationContext(), "빈칸이 있으면 안됩니다.", Toast.LENGTH_SHORT).show();

                } else if (EditId_JB.getText().toString().length() <5 || EditId_JB.getText().toString().length()>10) {
                    Toast.makeText(getApplicationContext(), "아이디는 5글자 이상  10글자 이하여야 합니다.", Toast.LENGTH_SHORT).show();
                } else if(flag){
                    Toast.makeText(getApplicationContext(), "중복체크를 해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    flag=true;
                } else if(check(EditPwd_JB.getText().toString(),checkPwd)){
                    Toast.makeText(getApplicationContext(), "비밀번호는 영문과 특수문자의 조합이어야 합니다.", Toast.LENGTH_SHORT).show();
                } else if(EditPwd_JB.getText().toString().length()<5 || EditPwd_JB.getText().toString().length()>15){
                    Toast.makeText(getApplicationContext(), "비밀번호는 5글자 이상 15글자 이하여야 합니다.", Toast.LENGTH_SHORT).show();
                } else if(!check(EditPhone_JB.getText().toString(),checkPwd)) {
                    Toast.makeText(getApplicationContext(), " 전호번호는'-'를 빼고 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                } else {
                    sqlDB = myHelper.getWritableDatabase();
                    sqlDB.execSQL("Insert into memberTBL values ('" + EditId_JB.getText().toString() + "','"+ EditPwd_JB.getText().toString() + "','" + EditName_JB.getText().toString() + "','" + EditPhone_JB.getText().toString() + "',0);");
                    sqlDB.close();
                    Toast.makeText(getApplicationContext(), "가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), Join3Activity.class);
                    startActivity(intent);
                    //sqlDB.close();
                }
            }
        });


    }////
    public class myDBHelper_JB extends SQLiteOpenHelper {
        public myDBHelper_JB(Context context){
            super(context, "/mnt/sdcard/groupDB",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //TODO Auto-generated method stub
            //db.execSQL("create table memberTBL (id char(10) primary key, pwd char(15), name char(14), phone char(13), login int(1));");
            //db.execSQL("CREATE TABLE `memberTBL` ( `number` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `subject` TEXT NOT NULL, `writer` TEXT NOT NULL, `date` TEXT NOT NULL, `content` TEXT NOT NULL );");
            //db.execSQL("CREATE TABLE IF NOT EXISTS memberTBL ( orderNo INTEGER PRIMARY KEY AUTOINCREMENT, user_ID TEXT NOT NULL, roomType TEXT NOT NULL, roomNo INTEGER NOT NULL, checkInDay INTEGER NOT NULL, checkOutDay INTEGER NOT NULL, adultNo INTEGER NOT NULL, childrenNo INTEGER NOT NULL, orderDay INTEGER NOT NULL )");


        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVerision){
            //TODO Auto-generate method stub
        }
    }

    public boolean check(String in, String check) {// 특수문자 확인
        boolean flag = true;
        for (int i = 0; i < in.length(); i++) {
            for (int j = 0; j < check.length(); j++) {
                if (in.charAt(i) == check.charAt(j)) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }
    public boolean overLap(String in) {
        boolean flag = false;
        sqlDB= myHelper.getReadableDatabase();

        Cursor cursor;
        cursor = sqlDB.rawQuery("Select id from memberTBL;",null);
        ArrayList<String> idList = new ArrayList<>();


        String strId = "";

        while(cursor.moveToNext()){
            strId = cursor.getString(0);
            idList.add(strId);
        }
        for(int i=0; i<idList.size();i++) {
            if (in.equals(idList.get(i))){
                flag=true;
            }
        }
        return flag;
    }
    private void showMessage(String answer){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("중복체크");
        builder.setMessage(answer);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
