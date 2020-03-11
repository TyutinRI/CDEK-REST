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
     * @param number - номер(id) заказа
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

    /**
     * Метод, возвращающий из базы данных список заданий {@link Task} с датой добавления между
     * двумя датами, переданными в параметры метода.
     * Метод работает не зависимо какая из дат больше, и возвращает все задачи {@link Task},
     * даты которых лежат во временном диапазоне между мелньшей и большей датой. Даты задаются
     * с точьностью до минуты. В результирующую выборку включаются также и задачи {@link Task},
     * с датой и временем добавления, равными датам, переданным в параметры с точностью до минуты.
     * Если даты, переданные в параметры равны с точностью до минуты, то возвращается выборка
     * задач {@link Task}, датой и временем создания, соответствующим этой минуте.
     * @param firstDate дата в формате "yyyy-MM-dd HH:mm"
     * @param secondDate дата в формате "yyyy-MM-dd HH:mm"
     * @return список {@link Task}
     */
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
