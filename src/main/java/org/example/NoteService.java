package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface NoteService extends Remote {
    void addNote(String text) throws RemoteException;
    List<String> getAllNotes() throws RemoteException;
}
