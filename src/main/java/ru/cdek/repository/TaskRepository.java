package ru.cdek.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.cdek.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

     /**
      * Метод, возвращающий из базы данных список заданий {@link Task} с датой добавления между
      * двумя датами, переданными в параметры метода.
      */
     List<Task> findByDateBetween(LocalDateTime firstDate, LocalDateTime secondDate);

}

