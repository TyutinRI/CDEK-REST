package ru.cdek.service;

import ru.cdek.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    void save();

    List<Task> findAll();

    List<Task> findById(Long id);

    List<Task> findByDatePeriod(LocalDateTime firstDate, LocalDateTime secondDate);

}
