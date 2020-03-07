package ru.cdek.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tasks")
public class Task implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    private Long id;
    @Column(name = "date/time")
    private LocalDateTime date;
    @Column(name = "task_is_performed")
    private boolean isPerformed;

    public Task(){}

    public Task(Long id, LocalDateTime date, boolean isPerformed){
        this.id = id;
        this.date = date;
        this.isPerformed = isPerformed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isPerformed() {
        return isPerformed;
    }

    public void setPerformed(boolean performed) {
        isPerformed = performed;
    }
}
