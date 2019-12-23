package com.thirteen.andriod_imitationzhihu.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.thirteen.andriod_imitationzhihu.MyHelper;
import com.thirteen.andriod_imitationzhihu.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class IndexNewFragment extends Fragment {
    MyHelper myHelper;
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private List<String> titles;
    private List<String> creators;
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
        recyclerView.setAdapter(new MyAdapter(titles, creators, gmtCreates));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(1000);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                initdata();
                recyclerView.setAdapter(new MyAdapter(titles, creators, gmtCreates));
            }
        });

        return view;
    }

    private void initdata(){
        titles = new ArrayList<String>();
        creators = new ArrayList<String>();
        gmtCreates = new ArrayList<String>();

        SQLiteDatabase db;
        db = myHelper.getReadableDatabase();
        Cursor cursor = db.query("question", null, null, null, null, null, "gmt_create desc");
        while (cursor.moveToNext()){
            titles.add(cursor.getString(1));
            creators.add("提问人：" + cursor.getString(3));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(Long.valueOf(cursor.getLong(4)));
            gmtCreates.add("发布时间：" + time);
        }

    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<String> titles;
        private List<String> creators;
        private List<String> gmtCreates;
        public MyAdapter(List<String> titles, List<String> creators, List<String> gmtCreates){
            this.titles = titles;
            this.creators = creators;
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
            ((MyViewHolder) holder).tvTitle.setText(titles.get(position));
            ((MyViewHolder) holder).tvName.setText(creators.get(position));
            ((MyViewHolder) holder).tvTime.setText(gmtCreates.get(position));
        }

        @Override
        public int getItemCount() {
            return titles.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvTime;
        TextView tvName;

        public MyViewHolder(View itemView){
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.item_title);
            tvName = (TextView) itemView.findViewById(R.id.item_name);
            tvTime = (TextView) itemView.findViewById(R.id.item_time);
        }
    }
}
