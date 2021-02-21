package com.luzaichun.demo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author:luzaichun
 * @Date:2021/2/21
 * @Time:11:14
 **/
public class Hikari {

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();

        //查询
        String querySql = "select * from teacher where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(querySql);
        preparedStatement.setInt(1,1);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            System.out.println("id="+resultSet.getInt(1)+" name="+resultSet.getString(2));
        }

        connection.close();
    }

    private static Connection getConnection() throws SQLException {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        ds.setUsername("root");
        ds.setPassword("123456");

        return ds.getConnection();
    }
}
