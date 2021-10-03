package helper;

import com.example.luizao.android.mytask.Models.Task;

import java.util.List;

public interface ITaskDAO {

    public boolean save(Task task);
    public boolean update(Task task);
    public boolean delete(Task task);
    public List<Task> listar();


}
