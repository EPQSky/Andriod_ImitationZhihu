package com.thirteen.andriod_imitationzhihu.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.thirteen.andriod_imitationzhihu.MyHelper;
import com.thirteen.andriod_imitationzhihu.QuestionActivity;
import com.thirteen.andriod_imitationzhihu.R;
import com.thirteen.andriod_imitationzhihu.RecyclerViewClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class IndexNewFragment extends Fragment {
    MyHelper myHelper;
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private List<String> ids;
    private List<String> titles;
    private List<String> gmtCreates;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_new, container, false);

        myHelper = new MyHelper(getActivity());

        refreshLayout = (RefreshLayout) view.findViewById(R.id.smart_refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.question_new);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        initdata();
        MyAdapter myAdapter = new MyAdapter(ids, titles, gmtCreates);
        recyclerView.setAdapter(myAdapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(1000);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                initdata();
                MyAdapter myAdapter = new MyAdapter(ids, titles, gmtCreates);
                recyclerView.setAdapter(myAdapter);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(getActivity(), new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), QuestionActivity.class);
                intent.putExtra("id", ids.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Intent intent = new Intent(getContext(), QuestionActivity.class);
                intent.putExtra("id", ids.get(position));
                startActivity(intent);
            }
        }));

        return view;
    }

    private void initdata(){
        ids = new ArrayList<String>();
        titles = new ArrayList<String>();
        gmtCreates = new ArrayList<String>();

        SQLiteDatabase db;
        db = myHelper.getReadableDatabase();
        Cursor cursor = db.query("question", null, null, null, null, null, "gmt_create desc");
        while (cursor.moveToNext()){
            ids.add(cursor.getString(0));
            titles.add(cursor.getString(1));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(Long.valueOf(cursor.getLong(4)));
            gmtCreates.add(time);
        }

    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<String> ids;
        private List<String> titles;
        private List<String> gmtCreates;
        public MyAdapter(List<String> ids, List<String> titles, List<String> gmtCreates){
            this.ids = ids;
            this.titles = titles;
            this.gmtCreates = gmtCreates;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((MyViewHolder) holder).tvId.setText(ids.get(position));
            ((MyViewHolder) holder).tvTitle.setText(titles.get(position));
            ((MyViewHolder) holder).tvTime.setText(gmtCreates.get(position));
        }

        @Override
        public int getItemCount() {
            return ids.size();
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvId;
        TextView tvTitle;
        TextView tvTime;

        public MyViewHolder(View itemView){
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.item_id);
            tvTitle = (TextView) itemView.findViewById(R.id.item_title);
            tvTime = (TextView) itemView.findViewById(R.id.item_time);
        }

    }

}
