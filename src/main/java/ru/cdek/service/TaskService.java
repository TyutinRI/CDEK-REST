package ru.cdek.service;

import ru.cdek.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    void save(Task task);

    Task saveIsNotBeInTimeTask(Long number);

    List<Task> findAll();

    List<Task> findByDatePeriod(LocalDateTime firstDate, LocalDateTime secondDate);

    Task findById(Long id);

}
