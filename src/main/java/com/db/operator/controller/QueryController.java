package com.db.operator.controller;

import com.db.operator.service.ExportService;
import com.db.operator.service.QueryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/query")
public class QueryController {

    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @PostMapping("/run")
    public ResponseEntity<List<Map<String, Object>>> runQuery(@RequestBody String sql) {
        return ResponseEntity.ok(queryService.runCustomQuery(sql));
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportQuery(@RequestParam String sql) {
        List<Map<String, Object>> rows = queryService.runCustomQuery(sql);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PrintWriter writer = new PrintWriter(out)) {
            if (!rows.isEmpty()) {
                writer.println(String.join(",", rows.get(0).keySet()));
                for (Map<String, Object> row : rows) {
                    writer.println(row.values().stream()
                            .map(v -> v == null ? "" : v.toString())
                            .collect(Collectors.joining(",")));
                }
            }
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=result.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(out.toByteArray());
    }
}

