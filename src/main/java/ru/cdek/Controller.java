package ru.cdek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.cdek.entity.Task;
import ru.cdek.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "tasks")
public class Controller {
    private final TaskService taskService;

    @Autowired
    public Controller(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = "courier/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> createTaskFromCourier(@PathVariable("id") Long id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Task savedTask = taskService.saveIsNotBeInTimeTask(id);
        if(savedTask == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }



    @GetMapping(value = "callcenter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Task>> showAllTasks(){
        List<Task> taskList = taskService.findAll();
        if(taskList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    @GetMapping(value = "callcenter/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> showTaskById(@PathVariable("id") Long id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Task task = taskService.findById(id);
        if(task == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping(value = "callcenter/{firstDate}/{secondDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Task>> showTaskOverDatePeriod(
            @PathVariable("firstDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
                    LocalDateTime firstDate,
            @PathVariable("secondDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
                    LocalDateTime secondDate
    ){
        if(firstDate == null || secondDate == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Task> taskList = taskService.findByDatePeriod(firstDate, secondDate);
        if(taskList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleException(){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
