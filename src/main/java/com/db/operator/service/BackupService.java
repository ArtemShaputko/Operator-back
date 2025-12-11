package com.db.operator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class BackupService {

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.url}")
    private String url; // jdbc:postgresql://host:port/dbname

    @Value("${app.backup.dir:backups/labs_db}")
    private String backupDir;

    public String backupDatabase() throws IOException, InterruptedException, URISyntaxException {
        String dbName = url.substring(url.lastIndexOf("/") + 1);
        String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        String fileName = "backup-" + timestamp + ".sql";
        File dir = new File(backupDir);
        if (!dir.exists()) dir.mkdirs();

        File backupFile = new File(dir, fileName);
        System.out.println("URL: " + url);
        System.out.println("File: " + backupFile.getAbsolutePath());

        String[] host_port = url.split("/")[2].split(":");

        String host = host_port[0];
        String port = host_port[1];

        ProcessBuilder pb = new ProcessBuilder(
                "pg_dump",
                "-h", host,
                "-p", port,
                "-U", username,
                "-d", dbName,
                "-f", backupFile.getAbsolutePath()
        );
        pb.environment().put("PGPASSWORD", password);

        Process process = pb.start();
        if (process.waitFor() != 0) {
            throw new RuntimeException("Backup failed");
        }

        return fileName;
    }

    public void restoreDatabase(String fileName) throws IOException, InterruptedException {
        String dbName = url.substring(url.lastIndexOf("/") + 1);
        File backupFile = new File(backupDir, fileName);
        if (!backupFile.exists()) {
            throw new RuntimeException("Backup file not found: " + fileName);
        }

        String[] host_port = url.split("/")[2].split(":");

        String host = host_port[0];
        String port = host_port[1];

        ProcessBuilder pb = new ProcessBuilder(
                "psql",
                "-h", host,
                "-p", port,
                "-U", username,
                "-d", dbName,
                "-f", backupFile.getAbsolutePath()
        );
        pb.environment().put("PGPASSWORD", password);

        Process process = pb.start();
        if (process.waitFor() != 0) {
            throw new RuntimeException("Restore failed");
        }
    }

    public List<String> listBackups() {
        File dir = new File(backupDir);
        if (!dir.exists()) return List.of();
        return Arrays.stream(dir.listFiles((d, name) -> name.endsWith(".sql")))
                .map(File::getName)
                .sorted()
                .toList();
    }
}
