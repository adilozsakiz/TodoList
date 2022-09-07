package com.example.todolist;

import com.example.todolist.entity.TodoList;
import com.example.todolist.repository.TodoListRepository;
import com.example.todolist.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class TodoListApplication implements CommandLineRunner {

    @Autowired
    TodoListRepository todoListRepository;
    @Autowired
    TodoListService todoListService;

    public static void main(String[] args) {
        SpringApplication.run(TodoListApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (todoListService.getAll("").size() == 0) {
            TodoList todo1 = new TodoList(null, "friends wedding", LocalDate.of(2022, 9, 17), false);
            todoListRepository.save(todo1);
            TodoList todo2 = new TodoList(null, "gym", LocalDate.of(2022, 3, 12), true);
            todoListRepository.save(todo2);
            TodoList todo3 = new TodoList(null, "doctor", LocalDate.of(2022, 8, 1), true);
            todoListRepository.save(todo3);
            TodoList todo4 = new TodoList(null, "dentist", LocalDate.of(2022, 6, 17), true);
            todoListRepository.save(todo4);
            TodoList todo5 = new TodoList(null, "shopping", LocalDate.of(2022, 6, 22), true);
            todoListRepository.save(todo5);
            TodoList todo6 = new TodoList(null, "car mechanic", LocalDate.of(2022, 12, 10), false);
            todoListRepository.save(todo6);
            TodoList todo7 = new TodoList(null, "friends birthday", LocalDate.of(2023, 1, 31), false);
            todoListRepository.save(todo7);
            TodoList todo8 = new TodoList(null, "gym", LocalDate.of(2022, 10, 6), false);
            todoListRepository.save(todo8);
            TodoList todo9 = new TodoList(null, "business meeting", LocalDate.of(2022, 9, 7), false);
            todoListRepository.save(todo9);
            TodoList todo10 = new TodoList(null, "doctor", LocalDate.of(2022, 11, 18), false);
            todoListRepository.save(todo10);
            TodoList todo11 = new TodoList(null, "fix the garage door", LocalDate.of(2022, 3, 13), true);
            todoListRepository.save(todo11);
            TodoList todo12 = new TodoList(null, "shopping", LocalDate.of(2022, 8, 28), true);
            todoListRepository.save(todo12);
            TodoList todo13 = new TodoList(null, "business meeting", LocalDate.of(2022, 9, 13), false);
            todoListRepository.save(todo13);
            TodoList todo14 = new TodoList(null, "camping", LocalDate.of(2022, 10, 10), false);
            todoListRepository.save(todo14);
            TodoList todo15 = new TodoList(null, "champions league match", LocalDate.of(2022, 7, 27), true);
            todoListRepository.save(todo15);
            TodoList todo16 = new TodoList(null, "champions league match", LocalDate.of(2022, 8, 3), false);
            todoListRepository.save(todo16);
            TodoList todo17 = new TodoList(null, "champions league match", LocalDate.of(2022, 8, 10), false);
            todoListRepository.save(todo17);
            TodoList todo18 = new TodoList(null, "derby match", LocalDate.of(2023, 1, 8), false);
            todoListRepository.save(todo18);
            TodoList todo19 = new TodoList(null, "derby match", LocalDate.of(2022, 10, 2), false);
            todoListRepository.save(todo19);
            TodoList todo20 = new TodoList(null, "gym", LocalDate.of(2023, 1, 12), false);
            todoListRepository.save(todo20);
            TodoList todo21 = new TodoList(null, "shopping", LocalDate.of(2023, 1, 1), false);
            todoListRepository.save(todo21);
            TodoList todo22 = new TodoList(null, "fishing", LocalDate.of(2023, 1, 22), false);
            todoListRepository.save(todo22);
            TodoList todo23 = new TodoList(null, "friends birthday", LocalDate.of(2022, 8, 24), true);
            todoListRepository.save(todo23);
            TodoList todo24 = new TodoList(null, "vacation", LocalDate.of(2022, 7, 12), false);
            todoListRepository.save(todo24);
            TodoList todo25 = new TodoList(null, "doctor", LocalDate.of(2022, 5, 8), true);
            todoListRepository.save(todo25);
        }
    }
}
