package org.example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public static void main(String[] args) {
        try (DBHelper dbHelper = new DBHelper()) {
            NoteService noteService = new NoteServiceImpl(dbHelper);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("NotepadService", noteService);
            System.out.println("Сервер Нотатника запущено...");
            System.out.println("Очікування підключень клієнтів...");
            Thread.sleep(Long.MAX_VALUE);

        } catch (Exception e) {
            System.err.println("Помилка запуску сервера: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
