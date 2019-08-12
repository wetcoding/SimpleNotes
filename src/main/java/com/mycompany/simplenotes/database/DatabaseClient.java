/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simplenotes.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс для работы с базой данных
 * @author Mashakaev
 */
public class DatabaseClient {
    
    private static Connection connection = null;
    private static boolean isConnected=false;
    
    /**
     * Подключение к БД
     * @return true-если соединение установлено
     */
    public static boolean connect(){
       try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/notes_database"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&allowPublicKeyRetrieval=true"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
            connection = DriverManager.getConnection(url, "root", "root");
            isConnected=true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            isConnected=false;
        }
       return isConnected;
    }
    
    /**
     * Поиск в базе данных по заданному ключу
     * @param key-ключ поиска
     * @return массив из объектов Note
     */
    public static ArrayList<Note> getNotes(String key){
              
        ArrayList<Note> result=new ArrayList<>();
        
        if(!isConnected)
            return result;
       
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;
        try {            
            String query="SELECT * FROM notes WHERE title LIKE ? OR note LIKE ?;";
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1, "%"+key+"%");
            preparedStatement.setString(2, "%"+key+"%");
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result.add(new Note(rs.getInt("id"),rs.getString("title"),rs.getString("note")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            closeRS(rs);
            closePreparedStatement(preparedStatement);
            closeConnection();
        }
        
        return result;
    }
    
    /**
     * Добавляет в БД запись
     * @param title - поле заголовка
     * @param note - поле заметки
     * @return true - если запись добавлена
     */
    public static boolean addNote(String title,String note){
        boolean result=false;
        PreparedStatement preparedStatement=null;
        
        try {
            String query = "INSERT INTO notes (title, note) VALUES (?,?);";
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2,note);
            preparedStatement.executeUpdate();
            result=true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection();
        }
        return result;
    }
    
    /**
     * Удаляет запись из БД по id
     * @param id - id записи для удаления
     * @return true - если запись удалена
     */
    public static boolean deleteNote(int id){
        boolean result=false;
        PreparedStatement preparedStatement=null;
        try {
            String query = "DELETE FROM notes WHERE id=?;";
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            result=true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection();
        }
        return result;
    }
    
    
    /**
     * Закрывает preparedStatement
     * @param preparedStatement 
     */
    private static void closePreparedStatement(PreparedStatement preparedStatement){
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) { 
                System.out.println(e.getMessage());
            } 
        }
    }
    
    /**
     * Закрывает ResultSet
     * @param rs 
     */
    private static void closeRS(ResultSet rs){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } 
        }
    }
    
    /**
     * Закрывает соединение с БД
     */
    private static void closeConnection(){
        if(isConnected){
            try {
                isConnected=false;
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
