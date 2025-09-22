package com.db.operator.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QueryService {
    private final JdbcTemplate jdbc;

    public QueryService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> runCustomQuery(String sql) {
        return jdbc.queryForList(sql);
    }
}
