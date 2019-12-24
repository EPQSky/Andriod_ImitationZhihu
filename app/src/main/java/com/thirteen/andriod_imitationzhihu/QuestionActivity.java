package com.thirteen.andriod_imitationzhihu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;

public class QuestionActivity extends AppCompatActivity {
    MyHelper myHelper;
    private Toolbar toolbar;
    private TextView tvTitle;
    private TextView tvName;
    private TextView tvTime;
    private TextView tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        myHelper = new MyHelper(QuestionActivity.this);

        init();
        initView();

    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvDescription = (TextView) findViewById(R.id.tv_description);
    }

    private void initView() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String id = getIntent().getStringExtra("id");
        SQLiteDatabase db;
        db = myHelper.getReadableDatabase();
        Cursor cursor = db.query("question", null, "_id = ?", new String[]{id}, null, null, null);
        cursor.moveToFirst();
        tvTitle.setText(cursor.getString(1));
        tvTitle.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvDescription.setText(cursor.getString(2));
        tvName.setText("提问人：" + cursor.getString(3));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(Long.valueOf(cursor.getLong(4)));
        tvTime.setText("发布时间：" + time);
        cursor.close();
        db.close();
    }
}
