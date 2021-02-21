package com.luzaichun.demo;

import java.sql.*;

/**
 * @author:luzaichun
 * @Date:2021/2/21
 * @Time:8:02
 **/
public class CrudPrepareStatement {
    public static final String URL = "jdbc:mysql://localhost:3306/test";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "123456";

    public static void main(String[] args) {
        //1.加载驱动(开发推荐的方式)
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            //查询
            String querySql = "select * from teacher";
            PreparedStatement queryStatement = connection.prepareStatement(querySql);
            ResultSet resultSet = queryStatement.executeQuery(querySql);
            while (resultSet.next()) {
                System.out.println("id=" + resultSet.getString(1) + "name=" + resultSet.getString(2));
            }


            //增加
            String addSql = "insert into teacher(id,name)values(?,?)";
            PreparedStatement addStatement = connection.prepareStatement(addSql);
            addStatement.setInt(1, 100);
            addStatement.setString(2, "zhaoliu");
            int i = addStatement.executeUpdate();
            System.out.println("新增影响行数" + i);

            //修改
            String updateSql = "update teacher set name=? where id =?";
            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
            updateStatement.setString(1, "xiaozhang");
            updateStatement.setInt(2, 100);
            int i2 = updateStatement.executeUpdate();
            System.out.println("删除影响行数" + i2);


            //删除
            String delSql = "delete from teacher where id = ?";
            PreparedStatement delStatement = connection.prepareStatement(delSql);
            delStatement.setInt(1, 100);
            int i1 = delStatement.executeUpdate();
            System.out.println("删除影响行数" + i1);

            connection.commit();
        } catch (Exception e) {
            try {
                //发生异常了回滚
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
