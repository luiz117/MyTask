package com.example.luizao.android.mytask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.luizao.android.mytask.Models.Task;
import com.google.android.material.textfield.TextInputEditText;

import helper.TaskDAO;

public class AddTaskActivity extends AppCompatActivity {

    private TextInputEditText editTask;
    private Task currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        editTask = findViewById(R.id.textTask);
        currentTask = (Task) getIntent().getSerializableExtra("selectedTask");

        //Set current task in the textBox

        if(currentTask != null){
            editTask.setText(currentTask.getTaskName());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_task,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        TaskDAO taskDAO = new TaskDAO(getApplicationContext());

        if(currentTask!= null){ //EDIT IF TASK IS NOT NULL
            if(!editTask.getText().toString().isEmpty() || editTask.getText().toString()!=null){
                Task task = new Task();
                task.setTaskName(editTask.getText().toString());
                task.setId(currentTask.getId());

                //UPDATE IN THE BD SQLITE

                if(taskDAO.update(task)){
                    finish();
                    Toast.makeText(getApplicationContext(), "Tarefa Atualizada", Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(getApplicationContext(), "Erro ao atualizar Tarefa", Toast.LENGTH_SHORT).show();

                }

            }


        }else{// SAVING NEW TASK
            switch (item.getItemId()){
                case R.id.itemSave:                 //Executa ação
                    Task task = new Task();
                    if(!editTask.getText().toString().isEmpty() || editTask.getText().toString()!=null){
                        task.setTaskName(editTask.getText().toString());
                        if(taskDAO.save(task)) {
                            Toast.makeText(getApplicationContext(), "Tarefa Salva", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Erro ao Salvar Tarefa", Toast.LENGTH_SHORT).show();
                    }
                    //finish();
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}