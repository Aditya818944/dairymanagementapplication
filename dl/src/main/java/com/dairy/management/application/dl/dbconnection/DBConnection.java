package com.dairy.management.application.dl.dbconnection;
import java.sql.*;
import java.io.*;
public class DBConnection
{
public static Connection getConnection()
{
Connection connection=null;
try{
Class.forName("com.mysql.cj.jdbc.Driver");
connection=DriverManager.getConnection("jdbc:mysql//localhost:3306/tstdb","tstdbuser","tstdbuser");
}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
return connection;
}
}