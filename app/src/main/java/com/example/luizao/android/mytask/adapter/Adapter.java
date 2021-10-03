package com.example.luizao.android.mytask.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luizao.android.mytask.Models.Task;
import com.example.luizao.android.mytask.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    List<Task> taskList;

    public Adapter(List<Task> taskList){
        this.taskList = taskList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.task_list_adapter,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.txttask.setText(task.getTaskName());


    }

    @Override
    public int getItemCount() {
        return this.taskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txttask;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txttask = itemView.findViewById(R.id.txtTask);

        }
    }
}
