package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Первый","Первый",(byte) 12);
        userService.saveUser("Второй","Второй",(byte) 15);
        userService.saveUser("Третий","Третий",(byte) 15);
        userService.saveUser("Четвертый","Четвертый",(byte) 15);
        userService.getAllUsers().forEach(System.out::println);
        userService.dropUsersTable();

    }
}
