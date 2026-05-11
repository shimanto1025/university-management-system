package com.ums.notification_service.model;

import java.time.LocalDateTime;

public class Notification {
    private Long id;
    private Long studentId;
    private String message;
    private LocalDateTime timestamp;
    
    public Notification() {}
    
    public Notification(Long id, Long studentId, String message) {
        this.id = id;
        this.studentId = studentId;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}