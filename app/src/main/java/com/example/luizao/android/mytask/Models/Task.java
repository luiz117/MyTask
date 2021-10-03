package com.example.luizao.android.mytask.Models;

import java.io.Serializable;

public class Task  implements Serializable {

    private String taskName;
    private Long id;

    public Task() {
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
