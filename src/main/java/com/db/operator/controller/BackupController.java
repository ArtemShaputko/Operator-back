package com.db.operator.controller;

import com.db.operator.service.BackupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backup")
public class BackupController {

    private final BackupService backupService;

    public BackupController(BackupService backupService) {
        this.backupService = backupService;
    }

    // Создать новый бэкап
    @PostMapping
    public ResponseEntity<String> createBackup() {
        try {
            String fileName = backupService.backupDatabase();
            return ResponseEntity.ok("Бэкап создан: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ошибка: " + e.getMessage());
        }
    }

    // Восстановить из бэкапа
    @PostMapping("/restore")
    public ResponseEntity<String> restore(@RequestParam String file) {
        try {
            backupService.restoreDatabase(file);
            return ResponseEntity.ok("База восстановлена из: " + file);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ошибка: " + e.getMessage());
        }
    }

    // Список доступных бэкапов
    @GetMapping("/list")
    public ResponseEntity<List<String>> list() {
        return ResponseEntity.ok(backupService.listBackups());
    }
}
