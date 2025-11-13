package org.example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            NoteService noteService = (NoteService) registry.lookup("NotepadService");

            System.out.println("Клієнт: Успішно підключено до сервера нотатника.");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\nОберіть дію:");
                System.out.println("1. Додати нотатку");
                System.out.println("2. Переглянути всі нотатки");
                System.out.println("3. Вийти");
                System.out.print("Ваш вибір: ");

                String choice = scanner.nextLine();

                if ("1".equals(choice)) {
                    System.out.print("Введіть текст нотатки: ");
                    String text = scanner.nextLine();

                    noteService.addNote(text);

                    System.out.println("Клієнт: Нотатку успішно надіслано.");

                } else if ("2".equals(choice)) {
                    System.out.println("\n--- Ваші нотатки ---");

                    List<String> notes = noteService.getAllNotes();

                    if (notes.isEmpty()) {
                        System.out.println("(Нотаток ще немає)");
                    } else {
                        for (String note : notes) {
                            System.out.println(note);
                        }
                    }

                } else if ("3".equals(choice)) {
                    System.out.println("Клієнт: Завершення роботи...");
                    break;
                } else {
                    System.out.println("Невірний вибір. Спробуйте 1, 2 або 3.");
                }
            }
            scanner.close();

        } catch (Exception e) {
            System.err.println("Помилка клієнта: " + e.getMessage());
            System.err.println("Переконайтеся, що сервер запущено.");
            e.printStackTrace();
        }
    }
}
