package com.example.workswiper.Services;

import com.example.workswiper.Domains.Task;
import com.example.workswiper.Repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TaskService {

    private final TaskRepository taskRepository;


    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void save(Task task){
        taskRepository.save(task);
    }

    public void delete(Long id){
        taskRepository.deleteById(id);
    }

    public Task get(Long id){
        return taskRepository.findById(id).get();
    }

}
