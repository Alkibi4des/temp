package org.testnh.myapplication22;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity{

    ActionBar actionBar;

    TextView TextViewNextJoin2;
    CheckBox checkboxJoin1,checkboxJoin2,checkboxJoin3,checkboxJoinTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_join);

        TextViewNextJoin2=(TextView) findViewById(R.id.TextViewNextJoin2Nh);

        checkboxJoin1=(CheckBox) findViewById(R.id.checkboxJoin1Nh);
        checkboxJoin2=(CheckBox) findViewById(R.id.checkboxJoin2Nh);
        checkboxJoin3=(CheckBox) findViewById(R.id.checkboxJoin3Nh);
        checkboxJoinTotal=(CheckBox) findViewById(R.id.checkboxJoinTotalNh);

        actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.layout_actionbar);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);


        checkboxJoinTotal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkboxJoinTotal.isChecked()){//전체 동의에 체크 되었니
                    if(!checkboxJoin1.isChecked()){//체크 안되있으면 체크 하기
                        checkboxJoin1.setChecked(true);
                    }
                    if(!checkboxJoin2.isChecked()){//체크 안되있으면 체크 하기
                        checkboxJoin2.setChecked(true);
                    }
                    if(!checkboxJoin3.isChecked()){//체크 안되있으면 체크 하기
                        checkboxJoin3.setChecked(true);
                    }
                }
                else {//전체 동의 체크 안됫니
                    if (checkboxJoin1.isChecked()) {//체크 안되있으면 체크 하기
                        checkboxJoin1.setChecked(false);
                    }
                    if (checkboxJoin2.isChecked()) {//체크 안되있으면 체크 하기
                        checkboxJoin2.setChecked(false);
                    }
                    if (checkboxJoin3.isChecked()) {//체크 안되있으면 체크 하기
                        checkboxJoin3.setChecked(false);
                    }
                }
            }
        });

        TextViewNextJoin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxJoin1.isChecked() && checkboxJoin2.isChecked() && checkboxJoin3.isChecked()){
                    Intent intent=new Intent(JoinActivity.this,Join2Activity.class);
                    finish();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(JoinActivity.this, "전체 동의를 해야 가입이 가능합니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }//end of onCreate for JoinActivity

}
