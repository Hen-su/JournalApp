package com.example.journalapp;

public class EntryItem {
        private String date;
        private String subject;
        private String entry;

        public EntryItem(String date, String subject, String entry) {
            this.date = date;
            this.subject = subject;
            this.entry = entry;
        }

        public String getDate() {
            return date;
        }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
            return subject;
        }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEntry() {
            return entry;
        }

    public void setEntry(String entry) {
        this.entry = entry;
    }
}
