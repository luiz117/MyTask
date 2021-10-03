package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.luizao.android.mytask.Models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements  ITaskDAO{

    private SQLiteDatabase write;
    private SQLiteDatabase read;


    public TaskDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        write = dbHelper.getWritableDatabase();
        read = dbHelper.getReadableDatabase();


    }

    @Override
    public boolean save(Task task) {

        ContentValues cv = new ContentValues();
        cv.put("nome", task.getTaskName());

        try{
            write.insert(DbHelper.TASK_TABLE,null,cv);
            Log.i("INFO","Tarefa Salva com sucesso");

        }catch (Exception ex){
            Log.i("INFO","Erro ao Salvar Tarefa" + ex.getMessage());
            return false;

        }
        return true;
    }

    @Override
    public boolean update(Task task) {

        ContentValues cv = new ContentValues();
        cv.put("nome", task.getTaskName());

        try{
            String[] args = {task.getId().toString()};
            write.update(DbHelper.TASK_TABLE,cv,"id=?",args );

            Log.i("INFO","Tarefa atualizada com sucesso");

        }catch (Exception ex){
            Log.i("INFO","Erro ao atualizar Tarefa" + ex.getMessage());
            return false;

        }


        return true;
    }

    @Override
    public boolean delete(Task task) {



        try{
            String[] args = {task.getId().toString()};
            write.delete(DbHelper.TASK_TABLE,"id=?",args);

            Log.i("INFO","Tarefa removida com sucesso");

        }catch (Exception ex){
            Log.i("INFO","Erro ao remover Tarefa" + ex.getMessage());
            return false;

        }




        return true;
    }

    @Override
    public List<Task> listar() {
        List <Task> taskList = new ArrayList<>();
        String sql = "Select * from " + DbHelper.TASK_TABLE + " ;";

        Cursor c = read.rawQuery(sql , null);

        try{
            while ( c.moveToNext() ){

                Task task = new Task();

                int intId =  c.getColumnIndex("id");
                Long id = c.getLong(intId);

                int taskNameid = c.getColumnIndex("nome");
                String taskName = c.getString(taskNameid);
                task.setId(id);
                task.setTaskName(taskName);

                taskList.add(task);


            }
        }catch (Exception e){

        }

        return taskList;
    }

}
