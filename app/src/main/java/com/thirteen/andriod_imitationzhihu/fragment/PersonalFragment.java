package com.thirteen.andriod_imitationzhihu.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.thirteen.andriod_imitationzhihu.LoginActivity;
import com.thirteen.andriod_imitationzhihu.MyHelper;
import com.thirteen.andriod_imitationzhihu.R;

public class PersonalFragment extends Fragment {
    MyHelper myHelper;
    String name;
    private MaterialButton btnFrame;
    private MaterialButton btnExit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        btnFrame = (MaterialButton) view.findViewById(R.id.login_frame);
        btnFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnFrame = (MaterialButton) getActivity().findViewById(R.id.login_frame);
        btnExit = (MaterialButton) getActivity().findViewById(R.id.btn_exit);
        myHelper = new MyHelper(getActivity());

        Bundle bundle = getArguments();
        if (bundle != null){
            name = bundle.getString("name");
            btnFrame.setText(name);
            btnFrame.setClickable(false);
            btnExit.setVisibility(View.VISIBLE);
        }

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db;
                ContentValues values;
                db = myHelper.getWritableDatabase();
                Cursor cursor = db.query("user", null, "name = ?", new String[]{name}, null, null, null);
                cursor.moveToFirst();
                if(cursor.getInt(3) == 1){
                    values = new ContentValues();
                    values.put("be", false);
                    db.update("user", values, "name = ?",
                            new String[]{name});
                    Toast.makeText(getActivity(), "退出登陆成功", Toast.LENGTH_SHORT).show();
                    btnFrame.setText("请登陆/注册");
                    btnFrame.setClickable(true);
                    btnExit.setVisibility(View.GONE);
                }
                cursor.close();
                db.close();
            }
        });
    }
}
