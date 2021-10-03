package com.example.luizao.android.mytask;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.luizao.android.mytask.Models.Task;
import com.example.luizao.android.mytask.adapter.Adapter;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luizao.android.mytask.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import helper.DbHelper;
import helper.RecyclerItemClickListener;
import helper.TaskDAO;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private RecyclerView recyclerTask;
    private Adapter adapter;
    private List<Task> taskList = new ArrayList<>();
    private Task selectedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // find the id of recycler view
        recyclerTask = findViewById(R.id.recyclerTask);


        //set the layout to be used in the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerTask.setLayoutManager(layoutManager);

        //Optimizing the recyclerview using fixed size
        recyclerTask.setHasFixedSize(true);

        //Adding divider
        recyclerTask.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        //SETTING THE ADAPTER
        adapter = new Adapter(taskList);
        recyclerTask.setAdapter(adapter);
        recyclerTask.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerTask,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                //recover task to edit
                                Task selectedTask = taskList.get(position);

                                //Use intent to go to the task
                                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                                intent.putExtra("selectedTask", selectedTask);

                                startActivity(intent);


                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                selectedTask = taskList.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                dialog.setTitle("Confirma a exclusão?");
                                dialog.setMessage("Deseja excluir a tarefa:  " + selectedTask.getTaskName() + " ?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        TaskDAO taskDAO = new TaskDAO(getApplicationContext());
                                        if(taskDAO.delete(selectedTask)){
                                            loadTasklist();
                                            Toast.makeText(getApplicationContext(),
                                                    "Tarefa Excluida",
                                                    Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(),
                                                    "Erro ao excluir tarefa",
                                                    Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });

                                 dialog.setNegativeButton("Não", null);

                                 // Exibir a dialog
                                dialog.create();
                                dialog.show();



                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddTaskActivity.class);
                startActivity(intent);

            }
        });

    }


    public void loadTasklist(){

        TaskDAO taskDAO = new TaskDAO(getApplicationContext());
        taskList = taskDAO.listar();

        //set the layout to be used in the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerTask.setLayoutManager(layoutManager);
        //Optimizing the recyclerview using fixed size
        recyclerTask.setHasFixedSize(true);

        //Adding divider
        recyclerTask.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        //SETTING THE ADAPTER
        adapter = new Adapter(taskList);
        recyclerTask.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        loadTasklist();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void testExamplecode(){
        DbHelper db = new DbHelper(getApplicationContext());


        ContentValues cv = new ContentValues();
        cv.put("nome", "Teste");

        db.getWritableDatabase().insert("tasks", null, cv);
    }






    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}