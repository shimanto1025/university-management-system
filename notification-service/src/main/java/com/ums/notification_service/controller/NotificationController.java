package com.ums.notification_service.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ums.notification_service.model.Notification;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    
    private final List<Notification> notifications = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);
    
    @PostMapping
    public ResponseEntity<Notification> sendNotification(@RequestBody Notification notification) {
        notification.setId(counter.getAndIncrement());
        notification.setTimestamp(java.time.LocalDateTime.now());
        notifications.add(notification);
        System.out.println("📧 NOTIFICATION: " + notification.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Notification>> getByStudent(@PathVariable Long studentId) {
        List<Notification> result = notifications.stream()
            .filter(n -> n.getStudentId().equals(studentId))
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
    
    @GetMapping
    public ResponseEntity<List<Notification>> getAll() {
        return ResponseEntity.ok(notifications);
    }
}