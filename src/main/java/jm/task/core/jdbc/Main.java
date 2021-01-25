package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 30);
        userService.saveUser("Elena", "Petrova", (byte) 31);
        userService.saveUser("John", "Dow", (byte) 32);
        userService.saveUser("Anna", "Vasilyeva", (byte) 33);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
