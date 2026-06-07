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
public class BuyerSaleDAO implements BuyerSaleDAOInterface
{
public void add(BuyerSaleDTOInterface buyerSaleDTO) throws DAOException
{
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

if(buyerSaleDTO==null) throw new DAOException("BuyerSaleDTO should not be null ");

int buyerSaleId=buyerSaleDTO.getBuyerSaleId();
if(buyerSaleId!=0) throw new DAOException("Buyer sale id should be zero ");

int buyerId=buyerSaleDTO.getBuyerId();
if(buyerId==0) throw new DAOException("Buyer id should not be zero ");
java.util.Date buyingDate=buyerSaleDTO.getBuyingDate();



int index=sdf.format(buyingDate).indexOf('-');

//leap year checking is not done;

int year=Integer.parseInt(sdf.format(buyingDate).substring(0,index));
int month=Integer.parseInt(sdf.format(buyingDate).substring(index+1,index+3));
int day=Integer.parseInt(sdf.format(buyingDate).substring(index+4));
if(day>31) throw new DAOException("Invalid day in date format : "+sdf.format(buyingDate));
if(month>12) throw new DAOException("Invalid month in date format : "+sdf.format(buyingDate));




String shift=buyerSaleDTO.getShift();
if(shift==null) throw new DAOException("Shift should not be null ");
shift=shift.trim();
if(shift.length()==0) throw new DAOException("Shift length should not be zero ");

BigDecimal amount=buyerSaleDTO.getAmount();
if(amount!=null) throw new DAOException("Amount should  be null ");

BigDecimal fatValue=buyerSaleDTO.getFatValue();
if(fatValue==null) throw new DAOException("Fat value should not be null ");

BigDecimal milkInLiters=buyerSaleDTO.getMilkInLiters();
if(milkInLiters==null) throw new DAOException("Milk in liters should not be null ");

java.sql.Date sqlDate;
java.util.Date javaDate;

sqlDate=new java.sql.Date(buyingDate.getTime());
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;

preparedStatement=connection.prepareStatement("select type from buyer where buyer_id=?");
preparedStatement.setInt(1,buyerId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid  buyer id : "+buyerId);
}
String type=resultSet.getString(1);

BuyerRateDAOInterface buyerRateDAO=new BuyerRateDAO();
SpecialBuyerRateDAOInterface specialBuyerRateDAO=new SpecialBuyerRateDAO();

BigDecimal ratePerFat=null;
if(type.equalsIgnoreCase("REGULAR")) ratePerFat=buyerRateDAO.getRate(buyingDate);
else ratePerFat=specialBuyerRateDAO.getRate(buyingDate,buyerId);


preparedStatement=connection.prepareStatement("select *  from buyer_sale where buying_date=? and shift=? and buyer_id=?");
preparedStatement.setDate(1,sqlDate);
preparedStatement.setString(2,shift);
preparedStatement.setInt(3,buyerId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true){
resultSet.close();
preparedStatement.close();
connection.close(); 
throw new DAOException("You have already sold milk to  this buyer for the selected date and time. If you want to re-sale  , please use re-sale features");
}

amount=new BigDecimal("0");
amount=fatValue.multiply(milkInLiters).multiply(ratePerFat);

buyerSaleDTO.setAmount(amount);

preparedStatement=connection.prepareStatement("insert into buyer_sale(buyer_id,buying_date,shift,amount,fat_value,milk_in_liters) values(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setInt(1,buyerId);
preparedStatement.setDate(2,sqlDate);
preparedStatement.setString(3,shift.toUpperCase());
preparedStatement.setBigDecimal(4,amount);
preparedStatement.setBigDecimal(5,fatValue);
preparedStatement.setBigDecimal(6,milkInLiters);
preparedStatement.executeUpdate();

resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
buyerSaleId=resultSet.getInt(1);
buyerSaleDTO.setBuyerSaleId(buyerSaleId);

resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}


public void reSale(BuyerSaleDTOInterface buyerSaleDTO) throws DAOException
{
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

if(buyerSaleDTO==null) throw new DAOException("MilkCollectionDTO should not be null ");

int buyerSaleId=buyerSaleDTO.getBuyerSaleId();
if(buyerSaleId!=0) throw new DAOException("Collection id should  be zero ");

BigDecimal amount=buyerSaleDTO.getAmount();
if(amount!=null) throw new DAOException("Amount should  be null ");


int buyerId=buyerSaleDTO.getBuyerId();
if(buyerId==0) throw new DAOException("Farmer id should not be zero ");

java.util.Date buyingDate=buyerSaleDTO.getBuyingDate();

String shift=buyerSaleDTO.getShift();
if(shift==null) throw new DAOException("Shift should not be null ");
shift=shift.trim();
if(shift.length()==0) throw new DAOException("Shift length should not be zero ");


BigDecimal fatValue=buyerSaleDTO.getFatValue();
if(fatValue==null) throw new DAOException("Fat value should not be null ");

BigDecimal milkInLiters=buyerSaleDTO.getMilkInLiters();
if(milkInLiters==null) throw new DAOException("Milk in liters should not be null ");

java.sql.Date sqlDate;
java.util.Date javaDate;

sqlDate=new java.sql.Date(buyingDate.getTime());
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");

PreparedStatement preparedStatement=connection.prepareStatement("select buyer_sale_id  from buyer_sale where buying_date=? and buyer_id=? and shift=?");
preparedStatement.setDate(1,sqlDate);
preparedStatement.setInt(2,buyerId);
preparedStatement.setString(3,shift);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid date or shift or farmer id ");
}
buyerSaleId=resultSet.getInt(1);

buyerSaleDTO.setBuyerSaleId(buyerSaleId);

preparedStatement=connection.prepareStatement("select type from buyer where buyer_id=?");
preparedStatement.setInt(1,buyerId);
resultSet=preparedStatement.executeQuery();
resultSet.next();
String type=resultSet.getString(1);


BigDecimal ratePerFat=null;
BuyerRateDAOInterface buyerRateDAO=new BuyerRateDAO();
SpecialBuyerRateDAOInterface specialBuyerRateDAO=new SpecialBuyerRateDAO();

if(type.equalsIgnoreCase("REGULAR")) ratePerFat=buyerRateDAO.getRate(buyingDate);
else ratePerFat=specialBuyerRateDAO.getRate(buyingDate,buyerId);


amount=new BigDecimal("0");
amount=fatValue.multiply(milkInLiters).multiply(ratePerFat);

buyerSaleDTO.setAmount(amount);




preparedStatement=connection.prepareStatement("update  buyer_sale set buyer_id=? ,buying_date=? ,shift=? ,amount =? , fat_value=? ,milk_in_liters=? where buyer_sale_id=?");
preparedStatement.setInt(1,buyerId);
preparedStatement.setDate(2,sqlDate);
preparedStatement.setString(3,shift.toUpperCase());
preparedStatement.setBigDecimal(4,amount);
preparedStatement.setBigDecimal(5,fatValue);
preparedStatement.setBigDecimal(6,milkInLiters);
preparedStatement.setInt(7,buyerSaleId);
preparedStatement.executeUpdate();

resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}


public List<BuyerSaleDTOInterface> getAll()  throws DAOException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("select buyer_sale_id , buyer_id , buying_date,shift,amount,fat_value,milk_in_liters from buyer_sale");
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("No records exists ");
}
BuyerSaleDTOInterface record=new BuyerSaleDTO();
List<BuyerSaleDTOInterface> records=new ArrayList<>();
record.setBuyerSaleId(resultSet.getInt(1));
record.setBuyerId(resultSet.getInt(2));
record.setBuyingDate(resultSet.getDate(3));
record.setShift(resultSet.getString(4));
record.setAmount(resultSet.getBigDecimal(5));
record.setFatValue(resultSet.getBigDecimal(6));
record.setMilkInLiters(resultSet.getBigDecimal(7));
records.add(record);
while(resultSet.next())
{
record=new BuyerSaleDTO();
record.setBuyerSaleId(resultSet.getInt(1));
record.setBuyerId(resultSet.getInt(2));
record.setBuyingDate(resultSet.getDate(3));
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


public void update(BuyerSaleDTOInterface buyerSaleDTO) throws DAOException
{
throw new DAOException("Not yet implemented ");
}

public void delete(int buyerSaleId) throws DAOException
{
throw new DAOException("Not yet implemented ");
}
}