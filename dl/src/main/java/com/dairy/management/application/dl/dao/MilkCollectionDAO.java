package com.dairy.management.application.dl.dao;
import com.dairy.management.application.dl.interfaces.dao.*;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.exceptions.*;
import com.dairy.management.application.dl.dto.*;
import java.io.*;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.text.*;
public class MilkCollectionDAO implements MilkCollectionDAOInterface
{
public void add(MilkCollectionDTOInterface milkCollectionDTO) throws DAOException
{
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

if(milkCollectionDTO==null) throw new DAOException("MilkCollectionDTO should not be null ");

int collectionId=milkCollectionDTO.getCollectionId();
if(collectionId!=0) throw new DAOException("Collection id should be zero ");

int farmerId=milkCollectionDTO.getFarmerId();
if(farmerId==0) throw new DAOException("Farmer id should not be zero ");
java.util.Date collectionDate=milkCollectionDTO.getCollectionDate();



int index=sdf.format(collectionDate).indexOf('-');

//leap year checking is not done;

int year=Integer.parseInt(sdf.format(collectionDate).substring(0,index));
int month=Integer.parseInt(sdf.format(collectionDate).substring(index+1,index+3));
int day=Integer.parseInt(sdf.format(collectionDate).substring(index+4));
if(day>31) throw new DAOException("Invalid day in date format : "+sdf.format(collectionDate));
if(month>12) throw new DAOException("Invalid month in date format : "+sdf.format(collectionDate));




String shift=milkCollectionDTO.getShift();
if(shift==null) throw new DAOException("Shift should not be null ");
shift=shift.trim();
if(shift.length()==0) throw new DAOException("Shift length should not be zero ");



BigDecimal amount=milkCollectionDTO.getAmount();
if(amount!=null) throw new DAOException("Amount should  be null ");

BigDecimal fatValue=milkCollectionDTO.getFatValue();
if(fatValue==null) throw new DAOException("Fat value should not be null ");

BigDecimal milkInLiters=milkCollectionDTO.getMilkInLiters();
if(milkInLiters==null) throw new DAOException("Milk in liters should not be null ");

java.sql.Date sqlDate;
java.util.Date javaDate;

sqlDate=new java.sql.Date(collectionDate.getTime());
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;

preparedStatement=connection.prepareStatement("select type from farmer where farmer_id=?");
preparedStatement.setInt(1,farmerId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid  farmer id : "+farmerId);
}
String type=resultSet.getString(1);


FarmerRateDAOInterface  farmerRateDAO=new FarmerRateDAO();
SpecialFarmerRateDAOInterface specialFarmerRateDAO=new SpecialFarmerRateDAO();

BigDecimal ratePerFat=null;
if(type.equalsIgnoreCase("REGULAR")) ratePerFat=farmerRateDAO.getRate(collectionDate);
else ratePerFat=specialFarmerRateDAO.getRate(collectionDate,farmerId);



preparedStatement=connection.prepareStatement("select *  from milk_collection where collection_date=? and shift=? and farmer_id=?");
preparedStatement.setDate(1,sqlDate);
preparedStatement.setString(2,shift);
preparedStatement.setInt(3,farmerId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true){
resultSet.close();
preparedStatement.close();
connection.close(); 
throw new DAOException("You have already collected milk for this farmer for the selected date and time. If you want to collect again , please use re-collect features");
}
amount=new BigDecimal("0");
amount=fatValue.multiply(milkInLiters).multiply(ratePerFat);

milkCollectionDTO.setAmount(amount);

preparedStatement=connection.prepareStatement("insert into milk_collection(farmer_id,collection_date,shift,amount,fat_value,milk_in_liters) values(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setInt(1,farmerId);
preparedStatement.setDate(2,sqlDate);
preparedStatement.setString(3,shift.toUpperCase());
preparedStatement.setBigDecimal(4,amount);
preparedStatement.setBigDecimal(5,fatValue);
preparedStatement.setBigDecimal(6,milkInLiters);
preparedStatement.executeUpdate();

resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
collectionId=resultSet.getInt(1);
milkCollectionDTO.setCollectionId(collectionId);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}

public void recollect(MilkCollectionDTOInterface milkCollectionDTO) throws DAOException
{
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

if(milkCollectionDTO==null) throw new DAOException("MilkCollectionDTO should not be null ");

int collectionId=milkCollectionDTO.getCollectionId();
if(collectionId!=0) throw new DAOException("Collection id should  be zero ");

BigDecimal amount=milkCollectionDTO.getAmount();
if(amount!=null) throw new DAOException("Amount should  be null ");


int farmerId=milkCollectionDTO.getFarmerId();
if(farmerId==0) throw new DAOException("Farmer id should not be zero ");

java.util.Date collectionDate=milkCollectionDTO.getCollectionDate();

String shift=milkCollectionDTO.getShift();
if(shift==null) throw new DAOException("Shift should not be null ");
shift=shift.trim();
if(shift.length()==0) throw new DAOException("Shift length should not be zero ");


BigDecimal fatValue=milkCollectionDTO.getFatValue();
if(fatValue==null) throw new DAOException("Fat value should not be null ");

BigDecimal milkInLiters=milkCollectionDTO.getMilkInLiters();
if(milkInLiters==null) throw new DAOException("Milk in liters should not be null ");

java.sql.Date sqlDate;
java.util.Date javaDate;

sqlDate=new java.sql.Date(collectionDate.getTime());
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");

PreparedStatement preparedStatement=connection.prepareStatement("select collection_id  from milk_collection where collection_date=? and farmer_id=? and shift=?");
preparedStatement.setDate(1,sqlDate);
preparedStatement.setInt(2,farmerId);
preparedStatement.setString(3,shift);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid date or shift or farmer id ");
}
collectionId=resultSet.getInt(1);
milkCollectionDTO.setCollectionId(collectionId);

preparedStatement=connection.prepareStatement("select type from farmer where farmer_id=?");
preparedStatement.setInt(1,farmerId);
resultSet=preparedStatement.executeQuery();
resultSet.next();
String type=resultSet.getString(1);

FarmerRateDAOInterface  farmerRateDAO=new FarmerRateDAO();
SpecialFarmerRateDAOInterface specialFarmerRateDAO=new SpecialFarmerRateDAO();

BigDecimal ratePerFat=null;
if(type.equalsIgnoreCase("REGULAR")) ratePerFat=farmerRateDAO.getRate(collectionDate);
else ratePerFat=specialFarmerRateDAO.getRate(collectionDate,farmerId);

amount=new BigDecimal("0");
amount=fatValue.multiply(milkInLiters).multiply(ratePerFat);
milkCollectionDTO.setAmount(amount);




preparedStatement=connection.prepareStatement("update  milk_collection set farmer_id=? ,collection_date=? ,shift=? ,amount =? , fat_value=? ,milk_in_liters=? where collection_id=?");
preparedStatement.setInt(1,farmerId);
preparedStatement.setDate(2,sqlDate);
preparedStatement.setString(3,shift.toUpperCase());
preparedStatement.setBigDecimal(4,amount);
preparedStatement.setBigDecimal(5,fatValue);
preparedStatement.setBigDecimal(6,milkInLiters);
preparedStatement.setInt(7,collectionId);
preparedStatement.executeUpdate();

resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}


public BigDecimal getAmountByDateAndFarmerId(int farmerId,java.util.Date startDate,java.util.Date endDate) throws DAOException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("select sum(amount) from milk_collection where farmer_id=? and collection_date>=? and collection_date<=?");
preparedStatement.setInt(	1,farmerId);
preparedStatement.setDate(2,new java.sql.Date(startDate.getTime()));
preparedStatement.setDate(3,new java.sql.Date(endDate.getTime()));
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false) 
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid farmer_id or date ");
}
BigDecimal amount=resultSet.getBigDecimal(1);
resultSet.close();
preparedStatement.close();
connection.close();
return amount;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}



public List<MilkCollectionDTOInterface> getAllByFarmerId(int farmerId) throws DAOException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("select collection_id , farmer_id , collection_date,shift,amount,fat_value,milk_in_liters from milk_collection where farmer_id=?");
preparedStatement.setInt(1,farmerId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid farmer id "+farmerId);
}
MilkCollectionDTOInterface record=new MilkCollectionDTO();
List<MilkCollectionDTOInterface> records=new ArrayList<>();
record.setCollectionId(resultSet.getInt(1));
record.setFarmerId(resultSet.getInt(2));
record.setCollectionDate(resultSet.getDate(3));
record.setShift(resultSet.getString(4));
record.setAmount(resultSet.getBigDecimal(5));
record.setFatValue(resultSet.getBigDecimal(6));
record.setMilkInLiters(resultSet.getBigDecimal(7));
records.add(record);
while(resultSet.next())
{
record=new MilkCollectionDTO();
record.setCollectionId(resultSet.getInt(1));
record.setFarmerId(resultSet.getInt(2));
record.setCollectionDate(resultSet.getDate(3));
record.setShift(resultSet.getString(4));
record.setAmount(resultSet.getBigDecimal(5));
record.setFatValue(resultSet.getBigDecimal(6));
record.setMilkInLiters(resultSet.getBigDecimal(7));
records.add(record);
}

resultSet.close();
preparedStatement.close();
connection.close();
return records;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}





public List<MilkCollectionDTOInterface> getByFarmerIdAndDate(int farmerId,java.util.Date date) throws DAOException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("select collection_id , farmer_id , collection_date,shift,amount,fat_value,milk_in_liters from milk_collection where farmer_id=? and collection_date=?");
preparedStatement.setInt(1,farmerId);
preparedStatement.setDate(2,new java.sql.Date(date.getTime()));
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid farmer id or date ");
}
MilkCollectionDTOInterface record=new MilkCollectionDTO();
List<MilkCollectionDTOInterface> records=new ArrayList<>();
record.setCollectionId(resultSet.getInt(1));
record.setFarmerId(resultSet.getInt(2));
record.setCollectionDate(resultSet.getDate(3));
record.setShift(resultSet.getString(4));
record.setAmount(resultSet.getBigDecimal(5));
record.setFatValue(resultSet.getBigDecimal(6));
record.setMilkInLiters(resultSet.getBigDecimal(7));
records.add(record);
while(resultSet.next())
{
record=new MilkCollectionDTO();
record.setCollectionId(resultSet.getInt(1));
record.setFarmerId(resultSet.getInt(2));
record.setCollectionDate(resultSet.getDate(3));
record.setShift(resultSet.getString(4));
record.setAmount(resultSet.getBigDecimal(5));
record.setFatValue(resultSet.getBigDecimal(6));
record.setMilkInLiters(resultSet.getBigDecimal(7));
records.add(record);
}

resultSet.close();
preparedStatement.close();
connection.close();
return records;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}



public List<MilkCollectionDTOInterface> getAll()  throws DAOException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("select collection_id , farmer_id , collection_date,shift,amount,fat_value,milk_in_liters from milk_collection");
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("No records exists ");
}
MilkCollectionDTOInterface record=new MilkCollectionDTO();
List<MilkCollectionDTOInterface> records=new ArrayList<>();
record.setCollectionId(resultSet.getInt(1));
record.setFarmerId(resultSet.getInt(2));
record.setCollectionDate(resultSet.getDate(3));
record.setShift(resultSet.getString(4));
record.setAmount(resultSet.getBigDecimal(5));
record.setFatValue(resultSet.getBigDecimal(6));
record.setMilkInLiters(resultSet.getBigDecimal(7));
records.add(record);
while(resultSet.next())
{
record=new MilkCollectionDTO();
record.setCollectionId(resultSet.getInt(1));
record.setFarmerId(resultSet.getInt(2));
record.setCollectionDate(resultSet.getDate(3));
record.setShift(resultSet.getString(4));
record.setAmount(resultSet.getBigDecimal(5));
record.setFatValue(resultSet.getBigDecimal(6));
record.setMilkInLiters(resultSet.getBigDecimal(7));
records.add(record);
}

resultSet.close();
preparedStatement.close();
connection.close();
return records;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public void update(MilkCollectionDTOInterface milkCollectionDTO) throws DAOException
{
throw new DAOException("Not yet implemented ");
}
public void delete(int milkCollectionId) throws DAOException
{
throw new DAOException("Delete of MilkCollectionDAO is not yet implemented ");
}
}