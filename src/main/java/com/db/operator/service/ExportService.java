package com.db.operator.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExportService {
    private final QueryService queryService;

    public ExportService(QueryService queryService) {
        this.queryService = queryService;
    }

    public void exportQueryToCsv(String sql, String filePath) throws IOException {
        List<Map<String, Object>> rows = queryService.runCustomQuery(sql);
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            if (!rows.isEmpty()) {
                // Заголовки
                writer.println(String.join(",", rows.get(0).keySet()));
                // Строки
                for (Map<String, Object> row : rows) {
                    writer.println(row.values().stream()
                            .map(v -> v == null ? "" : v.toString())
                            .collect(Collectors.joining(",")));
                }
            }
        }
    }
}
