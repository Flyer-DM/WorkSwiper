package com.example.workswiper.Services;

import com.example.workswiper.Domains.Task;
import com.example.workswiper.Repos.TaskRepository;
import com.example.workswiper.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public List<Task> findByUser_Id(User id){
        return taskRepository.findByUser_Id(id);
    }

}
