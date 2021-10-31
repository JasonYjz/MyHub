package com.yjz.app.flywaydemo.service;

import com.yjz.app.flywaydemo.model.User;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.jdbc.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

//    UserServiceImpl(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    @Override
    public int create(String name, Integer age) {
        try {
            jdbcTemplate.update("insert into USER(NAME, AGE) values(?, ?)", name, age);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public List<User> getByName(String name) {
        RowMapper<User> rowMapper = resultSet -> {
            User user = new User();
            user.setId(resultSet.getLong("ID"));
            user.setName(resultSet.getString("NAME"));
            user.setAge(resultSet.getInt("AGE"));
            return user;
        };
        List<User> users = null;
        try {
            users = jdbcTemplate.query("select * from USER where NAME = ?", rowMapper, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public int deleteByName(String name) {
        try {
            jdbcTemplate.update("delete from USER where NAME = ?", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }

    @Override
    public int getAllUsers() {
        try {
            jdbcTemplate.queryForInt("select count(1) from USER");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }

    @Override
    public int deleteAllUsers() {
        try {
            jdbcTemplate.update("delete from USER");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }

}
