package com.thirteen.andriod_imitationzhihu;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    MyHelper myHelper;
    private Toolbar toolbar;
    private EditText etUserName;
    private EditText etPassword;
    private MaterialButton btnLogin;
    private MaterialButton btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myHelper = new MyHelper(this);
        init();
        initView();

    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_back);
        etUserName = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (MaterialButton) findViewById(R.id.btn_login);
        btnRegister = (MaterialButton) findViewById(R.id.btn_register);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void initView() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        String name;
        String password;
        Boolean status = false;
        SQLiteDatabase db;
        ContentValues values;
        switch (view.getId()){
            case R.id.btn_register:
                name = etUserName.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                if ((name == null || name.length() <= 0) || (password == null || password.length() <= 0)){
                    Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    db = myHelper.getWritableDatabase();
                    Cursor cursor = db.query("user", null, null, null, null, null, null);
                    while (cursor.moveToNext()){
                        if (name.equals(cursor.getString(1))){
                            status = true;
                            break;
                        }
                    }
                    cursor.close();
                    if (status){
                        Toast.makeText(this, "该账号已被注册", Toast.LENGTH_SHORT).show();
                    }else {
                        values = new ContentValues();
                        values.put("name", name);
                        values.put("password", password);
                        values.put("be", false);
                        db.insert("user", null, values);
                        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }
                break;
            case R.id.btn_login:
                name = etUserName.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                if ((name == null || name.length() <= 0) || (password == null || password.length() <= 0)){
                    Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    db = myHelper.getWritableDatabase();
                    Cursor cursor = db.query("user", null, null, null, null, null, null);
                    while (cursor.moveToNext()){
                        if (name.equals(cursor.getString(1)) && password.equals(cursor.getString(2))){
                            status = true;
                            break;
                        }
                    }
                    cursor.close();
                    if (status){
                        values = new ContentValues();
                        values.put("be", true);
                        db.update("user", values, "name = ?",
                                new String[]{name});
                        Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }else {
                        Toast.makeText(this, "账号密码错误或未注册", Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }
                break;
        }
    }

}
