/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simplenotes;

import com.mycompany.simplenotes.database.DatabaseClient;
import com.mycompany.simplenotes.database.Note;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 *
 * @author Равиль
 */
@WebServlet (urlPatterns = "")
public class MainServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {        
        RequestDispatcher requestDispatcher=req.getRequestDispatcher("index.html");
        if(requestDispatcher!=null)
            requestDispatcher.forward(req, resp);
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder jb = new StringBuilder();
        String line = null;

        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        
        try {
            JSONObject jsonRequest = new JSONObject(jb.toString());//!

            
            int command = jsonRequest.getInt("command");//
            
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();//
            JSONObject jsonRespounce= new JSONObject();//
            
            if(DatabaseClient.connect()){
                //command - 0-getAll, 1-add, 2-delete
                switch (command){
                    case 0: 
                        String key=jsonRequest.getString("key");
                        ArrayList<Note> result=DatabaseClient.getNotes(key);                    
                        jsonRespounce.put("status","notes");

                        JSONArray jsonArray=new JSONArray();
                        for(int i=0;i<result.size();i++){
                            JSONObject obj = new JSONObject();
                            obj.put("id", result.get(i).getId());
                            obj.put("title", result.get(i).getTitle());
                            obj.put("text", result.get(i).getText());
                            jsonArray.put(obj);
                        }

                        jsonRespounce.put("notes", jsonArray);
                        break;
                    case 1:
                        String title=jsonRequest.getString("title");
                        String note=jsonRequest.getString("note");
                        if (DatabaseClient.addNote(title,note)){
                            jsonRespounce.put("status","success");
                            jsonRespounce.put("message","Success added");
                        }
                        else{
                            jsonRespounce.put("status","error");
                            jsonRespounce.put("message","Error while adding");
                        }
                        
                        break;
                    case 2:
                        int id=jsonRequest.getInt("id");
                        if(DatabaseClient.deleteNote(id)){
                            jsonRespounce.put("status","success");
                            jsonRespounce.put("message","Success deleted");
                        }
                        else
                        {
                            jsonRespounce.put("status","error");
                            jsonRespounce.put("message","Error while deleting");
                        }
                        
                        break;    
                    default:
                        jsonRespounce.put("status", "error");
                        jsonRespounce.put("message","Unknown command");
                        break; 
                    }
                    
                }
                else{
                    jsonRespounce.put("status","error");
                    jsonRespounce.put("message","Can not connect to database");
                }
            
                out.println(jsonRespounce.toString());
            }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    
}
