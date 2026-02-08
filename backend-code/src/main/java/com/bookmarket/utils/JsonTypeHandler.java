package com.bookmarket.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes({JdbcType.LONGVARCHAR, JdbcType.VARCHAR, JdbcType.CLOB})
@MappedTypes({Object.class, String[].class, java.util.List.class, java.util.Map.class})
public class JsonTypeHandler extends BaseTypeHandler<Object> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        try {
            String jsonValue = objectMapper.writeValueAsString(parameter);
            ps.setString(i, jsonValue);
        } catch (JsonProcessingException e) {
            throw new SQLException("Error converting object to JSON", e);
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getObjectFromJson(rs.getString(columnName));
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getObjectFromJson(rs.getString(columnIndex));
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getObjectFromJson(cs.getString(columnIndex));
    }

    private Object getObjectFromJson(String jsonString) throws SQLException {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            return null;
        }
        try {
            // 根据实际需要的类型进行反序列化
            return objectMapper.readValue(jsonString, Object.class);
        } catch (Exception e) {
            throw new SQLException("Error converting JSON to object", e);
        }
    }
}
