package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;


public class NoteServiceImpl extends UnicastRemoteObject implements NoteService {

    private DBHelper dbHelper;

    public NoteServiceImpl(DBHelper dbHelper) throws RemoteException {
        super();
        this.dbHelper = dbHelper;
    }

    @Override
    public void addNote(String text) throws RemoteException {
        try {
            dbHelper.addNote(text);
        } catch (SQLException e) {
            System.err.println("Сервер: Помилка при додаванні нотатки!");
            throw new RemoteException("Помилка на сервері при роботі з БД", e);
        }
    }

    @Override
    public List<String> getAllNotes() throws RemoteException {
        try {
            return dbHelper.getAllNotes();
        } catch (SQLException e) {
            System.err.println("Сервер: Помилка при отриманні нотаток!");
            throw new RemoteException("Помилка на сервері при роботі з БД", e);
        }
    }
}
