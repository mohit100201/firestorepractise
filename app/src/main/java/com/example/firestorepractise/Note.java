package com.example.firestorepractise;

public class Note {
    private String Title;
    private String Description;

    public Note() {
    }

    public Note(String title, String description) {
        this.Title = title;
       this.Description = description;
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
}
