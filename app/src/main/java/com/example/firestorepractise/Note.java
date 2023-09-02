package com.example.firestorepractise;

import com.google.firebase.firestore.Exclude;

public class Note {
    private String Title;
    private String Description;
    private int priority;

    private String documentID;

    public Note() {
    }

    public Note(String title, String description,int priority) {
        this.Title = title;
       this.Description = description;
       this.priority=priority;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Exclude
    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
