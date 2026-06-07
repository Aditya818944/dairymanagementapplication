package com.dairy.management.application.dl.dao;
import com.dairy.management.application.dl.interfaces.dao.*;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.exceptions.*;
import com.dairy.management.application.dl.dto.*;
import java.io.*;
import java.sql.*;
import java.util.*;
public class BuyerDAO implements BuyerDAOInterface
{

public void add(BuyerDTOInterface buyer) throws DAOException
{
if(buyer==null)  throw new DAOException("BuyerDTO should not be null ");
int buyerId=buyer.getBuyerId();
if(buyerId!=0) throw new DAOException("Buyer id should be zero ");
String name=buyer.getName();
if(name==null) throw new DAOException("Buyer name should not be null ");
name=name.trim();
if(name.length()==0) throw new DAOException("Buyer name length should not be zero ");
String address=buyer.getAddress();
if(address==null) throw new DAOException("Buyer address should not be null ");
address=address.trim();
if(address.length()==0) throw new DAOException("Buyer address length should not be zero ");
String phoneNumber=buyer.getPhoneNumber();
if(phoneNumber==null) throw new DAOException("Buyer phone number should not be null ");
phoneNumber=phoneNumber.trim();
if(phoneNumber.length()==0) throw new DAOException("Buyer phone number length should not be zero ");
if(phoneNumber.length()>10) throw new DAOException("Buyer phone number should only contain 10 digits ");
String type=buyer.getType();
if(type==null) throw new DAOException("Buyer type is required (special/regular) ");
type=type.trim();
if(type.length()==0) throw new DAOException("Buyer type length should not be zero ");
if( !(type.equalsIgnoreCase("REGULAR") || type.equalsIgnoreCase("SPECIAL")) ) throw new DAOException("Type must be either 'REGULAR' or 'SPECIAL' ");
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;

preparedStatement=connection.prepareStatement("select  * from buyer where phone_number=?");
preparedStatement.setString(1,phoneNumber);

resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Give phone number : "+phoneNumber+" already exists ");
}



preparedStatement=connection.prepareStatement("insert into buyer(name,address,phone_number,type) values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setString(2,address);
preparedStatement.setString(3,phoneNumber);
preparedStatement.setString(4,type.toUpperCase());


preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
buyerId=resultSet.getInt(1);
buyer.setBuyerId(buyerId);



resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}


public void update(BuyerDTOInterface buyer) throws DAOException
{
if(buyer==null)  throw new DAOException("BuyerDTO should not be null ");
int buyerId=buyer.getBuyerId();
if(buyerIdExists(buyerId)==false) throw new DAOException("Invalid buyer Id "+buyerId);
if(buyerId==0) throw new DAOException("Buyer id should not  be zero ");
String name=buyer.getName();
if(name==null) throw new DAOException("Buyer name should not be null ");
name=name.trim();
if(name.length()==0) throw new DAOException("Buyer name length should not be zero ");
String address=buyer.getAddress();
if(address==null) throw new DAOException("Buyer address should not be null ");
address=address.trim();
if(address.length()==0) throw new DAOException("Buyer address length should not be zero ");
String phoneNumber=buyer.getPhoneNumber();
if(phoneNumber==null) throw new DAOException("Buyer phone number should not be null ");
phoneNumber=phoneNumber.trim();
if(phoneNumber.length()==0) throw new DAOException("Buyer phone number length should not be zero ");
if(phoneNumber.length()>10) throw new DAOException("Buyer phone number should only contain 10 digits ");
String type=buyer.getType();
if(type==null) throw new DAOException("Buyer type is required ");
type=type.trim();
if(type.length()==0) throw new DAOException("Buyer type length should not be zero ");
if( !(type.equalsIgnoreCase("REGULAR") || type.equalsIgnoreCase("SPECIAL")) ) throw new DAOException("Type must be either 'REGULAR' or 'SPECIAL' ");

try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");

PreparedStatement preparedStatement=connection.prepareStatement("select buyer_id from buyer where phone_number=?");
preparedStatement.setString(1,phoneNumber);

ResultSet resultSet=preparedStatement.executeQuery();
int dbBuyerId=0;
if(resultSet.next()==true){
dbBuyerId=resultSet.getInt(1);
if(dbBuyerId!=buyerId) 
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Given phone number alread exists : "+phoneNumber);
}
}

preparedStatement=connection.prepareStatement("update  buyer set name=?,address=?,phone_number=?,type=?  where buyer_id=?");



preparedStatement.setString(1,name);
preparedStatement.setString(2,address);
preparedStatement.setString(3,phoneNumber);
preparedStatement.setString(4,type.toUpperCase());
preparedStatement.setInt(5,buyerId);
preparedStatement.executeUpdate();



resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}







public void delete(int buyerId) throws DAOException
{
try
{
if(buyerIdExists(buyerId)==false) throw new DAOException("Invalid buyer id "+buyerId);
Class.forName("com.mysql.cj.jdbc.Driver");


Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("select buyer_id from buyer_sale where buyer_id=?");
preparedStatement.setInt(1,buyerId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Unable to delete because milk is sold to  this id ("+buyerId+")");
}
preparedStatement=connection.prepareStatement("delete from buyer where buyer_id=?");
preparedStatement.setInt(1,buyerId);
preparedStatement.executeUpdate();


resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}








public List<BuyerDTOInterface> getAllBuyers() throws DAOException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
List<BuyerDTOInterface> buyers=new ArrayList<>();
BuyerDTOInterface buyer;
PreparedStatement preparedStatement=connection.prepareStatement("select buyer_id , name , address , phone_number , type from buyer");
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("No buyer exist ");
}
buyer=new BuyerDTO();
buyer.setBuyerId(resultSet.getInt(1));
buyer.setName(resultSet.getString(2));
buyer.setAddress(resultSet.getString(3));
buyer.setPhoneNumber(resultSet.getString(4));
buyer.setType(resultSet.getString(5));
buyers.add(buyer);
while(resultSet.next())
{
buyer=new BuyerDTO();
buyer.setBuyerId(resultSet.getInt(1));
buyer.setName(resultSet.getString(2));
buyer.setAddress(resultSet.getString(3));
buyer.setPhoneNumber(resultSet.getString(4));
buyer.setType(resultSet.getString(5));
buyers.add(buyer);
}
resultSet.close();
preparedStatement.close();
connection.close();
return buyers;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}



public boolean buyerIdExists(int buyerId) throws DAOException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("select buyer_id from buyer where buyer_id=?");
preparedStatement.setInt(1,buyerId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
return false;
}
resultSet.close();
preparedStatement.close();
connection.close();
return true;
}catch(Exception exception)
{
return false;
}
}






public BuyerDTOInterface getByBuyerId(int buyerId) throws DAOException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("select buyer_id , name , address, phone_number , type from buyer where buyer_id=?");
preparedStatement.setInt(1,buyerId);
ResultSet resultSet=preparedStatement.executeQuery();
BuyerDTOInterface buyerDTO=new BuyerDTO();
if(resultSet.next()==false) throw new DAOException("Invalid buyer id : "+buyerId);
buyerDTO.setBuyerId(resultSet.getInt(1));
buyerDTO.setName(resultSet.getString(2));
buyerDTO.setAddress(resultSet.getString(3));
buyerDTO.setPhoneNumber(resultSet.getString(4));
buyerDTO.setType(resultSet.getString(5));
resultSet.close();
preparedStatement.close();
connection.close();
return buyerDTO;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
}