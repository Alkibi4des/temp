package org.testnh.myapplication22;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Detail extends AppCompatActivity {

    ActionBar actionBar;

    TextView numText;//번호
    TextView subText;//제목
    TextView writerText;//작성자
    TextView dateText;//날짜
    TextView contentText;//내용
    Button detailCheckBtn;//확인버튼
    //ItemListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contents);


        actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.layout_actionbar);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        //adapter = new ItemListAdapter(Detail.this);
        Intent intent = getIntent();

        String str[] = intent.getExtras().getStringArray("Item");
        String number = str[0];
        String subject = str[1];
        String writer = str[2];
        String date = str[3];
        String content = str[4];

        //어느 변수에 무엇이 어떤값이 들어가야 되는지 각각 설정
        numText = (TextView) findViewById(R.id.detailNumTextViewSH);
        numText.setText(String.valueOf(number));
        subText = (TextView) findViewById(R.id.detailSubTextViewSH);
        subText.setText(subject);
        writerText = (TextView) findViewById(R.id.detailWriterTextViewSH);
        writerText.setText(writer);
        dateText = (TextView) findViewById(R.id.detailDateTextViewSH);
        dateText.setText(date);
        contentText = (TextView) findViewById(R.id.detailContentTextViewSH);
        contentText.setText(content);
        detailCheckBtn = (Button) findViewById(R.id.detailCheckBtnSH);

        detailCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}



