package com.dairy.management.application.dl.dao;
import com.dairy.management.application.dl.interfaces.dao.*;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.exceptions.*;
import com.dairy.management.application.dl.dto.*;
import java.io.*;
import java.sql.*;
import java.util.*;
public class FarmerDAO implements FarmerDAOInterface
{

public void add(FarmerDTOInterface farmer) throws DAOException
{
if(farmer==null)  throw new DAOException("FarmerDTO should not be null ");
int farmerId=farmer.getFarmerId();
if(farmerId!=0) throw new DAOException("Farmer id should be zero ");
String name=farmer.getName();
if(name==null) throw new DAOException("Farmer name should not be null ");
name=name.trim();
if(name.length()==0) throw new DAOException("Farmer name length should not be zero ");
String address=farmer.getAddress();
if(address==null) throw new DAOException("Farmer address should not be null ");
address=address.trim();
if(address.length()==0) throw new DAOException("Farmer address length should not be zero ");
String phoneNumber=farmer.getPhoneNumber();
if(phoneNumber==null) throw new DAOException("Farmer phone number should not be null ");
phoneNumber=phoneNumber.trim();
if(phoneNumber.length()==0) throw new DAOException("Farmer phone number length should not be zero ");
if(phoneNumber.length()>10) throw new DAOException("Farmer phone number should only contain 10 digits ");
String type=farmer.getType();
if(type==null) throw new DAOException("Farmer type is required (special/regular) ");
type=type.trim();
if(type.length()==0) throw new DAOException("Farmer type length should not be zero ");
if( !(type.equalsIgnoreCase("REGULAR") || type.equalsIgnoreCase("SPECIAL")) ) throw new DAOException("Type must be either 'REGULAR' or 'SPECIAL' ");
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;

preparedStatement=connection.prepareStatement("select  * from farmer where phone_number=?");
preparedStatement.setString(1,phoneNumber);

resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Give phone number : "+phoneNumber+" already exists ");
}



preparedStatement=connection.prepareStatement("insert into farmer(name,address,phone_number,type) values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setString(2,address);
preparedStatement.setString(3,phoneNumber);
preparedStatement.setString(4,type.toUpperCase());


preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
farmerId=resultSet.getInt(1);
farmer.setFarmerId(farmerId);



resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}



public void update(FarmerDTOInterface farmer) throws DAOException
{
if(farmer==null)  throw new DAOException("FarmerDTO should not be null ");
int farmerId=farmer.getFarmerId();
if(farmerIdExists(farmerId)==false) throw new DAOException("Invalid farmer id : "+farmerId);
if(farmerId==0) throw new DAOException("Farmer id should not  be zero ");
String name=farmer.getName();
if(name==null) throw new DAOException("Farmer name should not be null ");
name=name.trim();
if(name.length()==0) throw new DAOException("Farmer name length should not be zero ");
String address=farmer.getAddress();
if(address==null) throw new DAOException("Farmer address should not be null ");
address=address.trim();
if(address.length()==0) throw new DAOException("Farmer address length should not be zero ");
String phoneNumber=farmer.getPhoneNumber();
if(phoneNumber==null) throw new DAOException("Farmer phone number should not be null ");
phoneNumber=phoneNumber.trim();
if(phoneNumber.length()==0) throw new DAOException("Farmer phone number length should not be zero ");
if(phoneNumber.length()>10) throw new DAOException("Farmer phone number should only contain 10 digits ");
String type=farmer.getType();
if(type==null) throw new DAOException("Farmer type is required (special/regular) ");
type=type.trim();
if(type.length()==0) throw new DAOException("Farmer type length should not be zero ");
if( !(type.equalsIgnoreCase("REGULAR") || type.equalsIgnoreCase("SPECIAL")) ) throw new DAOException("Type must be either 'REGULAR' or 'SPECIAL' ");

try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");

PreparedStatement preparedStatement=connection.prepareStatement("select farmer_id from farmer where phone_number=?");
preparedStatement.setString(1,phoneNumber);

ResultSet resultSet=preparedStatement.executeQuery();
int dbFarmerId=0;
if(resultSet.next()==true){
 dbFarmerId=resultSet.getInt(1);
if(dbFarmerId!=farmerId) 
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Given phone number alread exists : "+phoneNumber);
}
}

preparedStatement=connection.prepareStatement("update  farmer set name=?,address=?,phone_number=?,type=? where farmer_id=?");



preparedStatement.setString(1,name);
preparedStatement.setString(2,address);
preparedStatement.setString(3,phoneNumber);
preparedStatement.setString(4,type.toUpperCase());
preparedStatement.setInt(5,farmerId);
preparedStatement.executeUpdate();



resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}






public void delete(int farmerId) throws DAOException
{
try
{
if(farmerIdExists(farmerId)==false) throw new DAOException("Invalid farmer id "+farmerId);
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("select farmer_id from milk_collection where farmer_id=?");
preparedStatement.setInt(1,farmerId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Unable to delete because milk is collected from this id ("+farmerId+")");
}
preparedStatement=connection.prepareStatement("delete from farmer where farmer_id=?");
preparedStatement.setInt(1,farmerId);
preparedStatement.executeUpdate();


resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}






public List<FarmerDTOInterface> getAllFarmers() throws DAOException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
List<FarmerDTOInterface> farmers=new ArrayList<>();
FarmerDTOInterface farmer;
PreparedStatement preparedStatement=connection.prepareStatement("select farmer_id , name , address , phone_number , type from farmer");
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("No farmer exist ");
}
farmer=new FarmerDTO();
farmer.setFarmerId(resultSet.getInt(1));
farmer.setName(resultSet.getString(2));
farmer.setAddress(resultSet.getString(3));
farmer.setPhoneNumber(resultSet.getString(4));
farmer.setType(resultSet.getString(5));
farmers.add(farmer);
while(resultSet.next())
{
farmer=new FarmerDTO();
farmer.setFarmerId(resultSet.getInt(1));
farmer.setName(resultSet.getString(2));
farmer.setAddress(resultSet.getString(3));
farmer.setPhoneNumber(resultSet.getString(4));
farmer.setType(resultSet.getString(5));
farmers.add(farmer);
}
resultSet.close();
preparedStatement.close();
connection.close();
return farmers;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}


public boolean farmerIdExists(int farmerId) throws DAOException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("select farmer_id from farmer where farmer_id=?");
preparedStatement.setInt(1,farmerId);
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





public FarmerDTOInterface getByFarmerId(int farmerId) throws DAOException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("select farmer_id , name , address, phone_number , type from farmer where farmer_id=?");
preparedStatement.setInt(1,farmerId);
ResultSet resultSet=preparedStatement.executeQuery();
FarmerDTOInterface farmerDTO=new FarmerDTO();
if(resultSet.next()==false) throw new DAOException("Invalid farmer id : "+farmerId);
farmerDTO.setFarmerId(resultSet.getInt(1));
farmerDTO.setName(resultSet.getString(2));
farmerDTO.setAddress(resultSet.getString(3));
farmerDTO.setPhoneNumber(resultSet.getString(4));
farmerDTO.setType(resultSet.getString(5));
resultSet.close();
preparedStatement.close();
connection.close();
return farmerDTO;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
}