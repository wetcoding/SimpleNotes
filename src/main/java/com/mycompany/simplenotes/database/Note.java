/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simplenotes.database;

/**
 *
 * @author Mashakaev
 */
public class Note {
    int id;
    String title;
    String note;
    
    public Note (int id,String title,String note){
        this.id=id;
        this.title=title;
        this.note=note;
    }

    public int getId(){
        return id;
    }
    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }
    
    
}
