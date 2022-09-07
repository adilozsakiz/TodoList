package com.example.todolist.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "todolist")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TodoList {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private boolean complete;

    public TodoList(String description, LocalDate date, boolean complete) {
        this.description = description;
        this.date = date;
        this.complete = complete;
    }
}
