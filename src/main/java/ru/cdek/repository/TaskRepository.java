package ru.cdek.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.cdek.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    <S extends Task> List<S> findByDateBetween(LocalDateTime firstDate, LocalDateTime secondDate);
}
