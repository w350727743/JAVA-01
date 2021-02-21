package com.luzaichun.demo;

import java.sql.*;

/**
 * @author:luzaichun
 * @Date:2021/2/21
 * @Time:8:02
 **/
public class Crud {
    public static final String URL = "jdbc:mysql://localhost:3306/test";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "123456";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.加载驱动(开发推荐的方式)
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();

        //查询
        String querySql = "select * from teacher";
        ResultSet resultSet = statement.executeQuery(querySql);
        while (resultSet.next()){
            System.out.println("id="+resultSet.getString(1)+"name="+resultSet.getString(2));
        }


        //增加
        String addSql = "insert into teacher(id,name)values(10,'lisi')";
        int i = statement.executeUpdate(addSql);
        System.out.println("新增影响行数"+i);

        //删除
        String delSql = "delete from teacher where id = 10";
        int i1 = statement.executeUpdate(delSql);
        System.out.println("删除影响行数"+i1);

        //修改
        String updateSql = "update teacher set name='xiaohong' where id =3";
        int i2 = statement.executeUpdate(updateSql);
        System.out.println("删除影响行数"+i2);

    }
}
