package com.db.operator.controller;

import com.db.operator.service.ExportService;
import com.db.operator.service.QueryService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
@CrossOrigin
public class QueryController {

    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @PostMapping(
            path     = "/run",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )   
    public ResponseEntity<?> runQuery(@RequestBody String sql) {
        try {
            List<Map<String, Object>> result = queryService.runCustomQuery(sql);
            return ResponseEntity.ok(result);
        } catch (DataAccessException dae) {
            // Ошибка SQL или доступа к БД
            Map<String, String> error = Map.of(
                    "error", "SQL Error: " + dae.getMostSpecificCause().getMessage()
            );
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(error);
        } catch (Exception ex) {
            Map<String, String> error = Map.of(
                    "error", "Unexpected error: " + ex.getMessage()
            );
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(error);
        }
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportQuery(@RequestParam String sql) {
        List<Map<String, Object>> rows = queryService.runCustomQuery(sql);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PrintWriter writer = new PrintWriter(out)) {
            if (!rows.isEmpty()) {
                writer.println(String.join(",", rows.get(0).keySet()));
                for (Map<String, Object> row : rows) {
                    writer.println(
                            row.values().stream()
                                    .map(v -> v == null ? "" : v.toString())
                                    .collect(Collectors.joining(","))
                    );
                }
            }
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=result.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(out.toByteArray());
    }
}

