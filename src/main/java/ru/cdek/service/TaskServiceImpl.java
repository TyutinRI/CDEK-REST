package ru.cdek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cdek.entity.Task;
import ru.cdek.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }


    /**
     * Метод для сохранения задачи для Call-центра, для сообщения клиенту о том, что курьер не приедет.
     * @param number - номер заказа
     * @return {@link Task}  если задача успешно сохранена
     *         {@code null} - если задача для заказа с таким номером уже существует
     */
    @Override
    public Task saveIsNotBeInTimeTask(Long number) {
        if(taskRepository.findById(number).isPresent()){
            return null;
        }
        Task task = new Task(number, LocalDateTime.now(), false);
        taskRepository.save(task);
        return task;
    }


    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findByDatePeriod(LocalDateTime firstDate, LocalDateTime secondDate) {
        int compareResult = firstDate.compareTo(secondDate);
        if(compareResult<=0) {
            return taskRepository.findByDateBetween(firstDate, secondDate.plusMinutes(1));
        }else {
            return taskRepository.findByDateBetween(secondDate, firstDate.plusMinutes(1));
        }
    }

    /**
     * Поиск задания по Id
     * @param id - идентификатор заказа
     * @return {@code null} если не найдено задание с таким номером
     *         {@link Task} если задание с таким номером найдено
     */
    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

}
