package org.example;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBHelper implements AutoCloseable {

    private Connection connection;
    private static final SimpleDateFormat DATE_FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String DB_URL = "jdbc:sqlite:D:/notepad_db/notes.db";

    public DBHelper() {
        try {

            connection = DriverManager.getConnection(DB_URL);
            if (connection != null) {
                System.out.println("Сервер: Підключено до БД " + DB_URL);
                initTable();
            }
        } catch (SQLException e) {
            System.err.println("Сервер: Помилка підключення до БД!");
            throw new RuntimeException(e);
        }
    }


    private void initTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS notes (" +
                "   id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "   text TEXT NOT NULL," +
                "   date DATETIME NOT NULL" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Сервер: Таблиця 'notes' готова.");
        }
    }


    public void addNote(String text) throws SQLException {
        String sql = "INSERT INTO notes(text, date) VALUES(?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, text);
            pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            System.out.println("Сервер: Нотатку збережено: \"" + text + "\"");
        }
    }


    public List<String> getAllNotes() throws SQLException {
        List<String> notesList = new ArrayList<>();
        String sql = "SELECT text, date FROM notes ORDER BY date DESC"; // Показуємо новіші спочатку

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String text = rs.getString("text");
                Timestamp ts = rs.getTimestamp("date");
                String date = DATE_FORMATTER.format(ts);
                notesList.add("[" + date + "] - " + text);
            }
        }
        System.out.println("Сервер: Відправлено " + notesList.size() + " нотаток.");
        return notesList;
    }


    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Сервер: З'єднання з БД закрито.");
            }
        } catch (SQLException e) {
            System.err.println("Сервер: Помилка при закритті з'єднання.");
        }
    }
}