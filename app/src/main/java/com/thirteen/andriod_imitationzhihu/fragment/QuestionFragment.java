package com.thirteen.andriod_imitationzhihu.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.thirteen.andriod_imitationzhihu.MyHelper;
import com.thirteen.andriod_imitationzhihu.R;

public class QuestionFragment extends Fragment {
    MyHelper myHelper;
    String name;
    String title;
    String description;
    private MaterialButton btnQuestion;
    private EditText etTitle;
    private EditText etDescription;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnQuestion = (MaterialButton) getActivity().findViewById(R.id.btn_question);
        etTitle = (EditText) getActivity().findViewById(R.id.et_title);
        etDescription = (EditText) getActivity().findViewById(R.id.et_description);

        myHelper = new MyHelper(getActivity());
        Bundle bundle = getArguments();
        if (bundle != null){
            name = bundle.getString("name");
        }

        btnQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = etTitle.getText().toString().trim();
                description = etDescription.getText().toString().trim();
                if (name == null || name.length() <= 0){
                    Toast.makeText(getActivity(), "用户未登录", Toast.LENGTH_SHORT).show();
                }else {
                    SQLiteDatabase db;
                    ContentValues values;
                    db = myHelper.getWritableDatabase();
                    Cursor cursor = db.query("user", null, "name = ?", new String[]{name}, null, null, null);
                    cursor.moveToFirst();
                    if(cursor.getInt(3) == 1){
                        if (title == null || title.length() <= 0){
                            Toast.makeText(getActivity(), "问题不能为空", Toast.LENGTH_SHORT).show();
                        }else {
                            values = new ContentValues();
                            values.put("title", title);
                            values.put("description", description);
                            values.put("creator", name);
                            values.put("gmt_create", System.currentTimeMillis());
                            db.insert("question", null, values);
                            Toast.makeText(getActivity(), "发布问题成功", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), "用户未登录", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                    db.close();
                }
            }
        });
    }
}
