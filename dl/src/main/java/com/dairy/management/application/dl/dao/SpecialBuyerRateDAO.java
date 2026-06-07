package 	com.dairy.management.application.dl.dao;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.dto.*;
import com.dairy.management.application.dl.interfaces.dao.*;
import com.dairy.management.application.dl.exceptions.*;
import java.sql.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import java.math.*;
public class SpecialBuyerRateDAO implements SpecialBuyerRateDAOInterface
{
public void add(SpecialBuyerRateDTOInterface buyerRateDTO) throws DAOException
{
int rateId=buyerRateDTO.getRateId();
if(rateId!=0) throw new DAOException("Rate id should be zero ");
int buyerId=buyerRateDTO.getBuyerId();
if(new BuyerDAO().buyerIdExists(buyerId)==false) throw new DAOException("No such buyer id exists : "+buyerId);
if(buyerId==0) throw new DAOException("Buyer id should not be zero ");
if(new BuyerDAO().getByBuyerId(buyerId).getType().equalsIgnoreCase("SPECIAL")==false) throw new DAOException("This is not special buyer id : "+buyerId);
BigDecimal ratePerFat=buyerRateDTO.getRatePerFat();
if(ratePerFat==null) throw new DAOException("Rate per fate value should not be zero ");
java.util.Date startDate=buyerRateDTO.getStartDate();
if(startDate==null) throw new DAOException("Start date should not be null ");
java.util.Date endDate=buyerRateDTO.getEndDate();
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
if(endDate==null) throw new DAOException("End date should not be null ");
try
{

if(startDate.compareTo(endDate)>0)  throw new DAOException("Invalid date sequence , Start Date : "+sdf.format(startDate)+" , End Date : "+sdf.format(endDate));

int index=sdf.format(startDate).indexOf('-');


//leap year checking is not done;

int year=Integer.parseInt(sdf.format(startDate).substring(0,index));
int month=Integer.parseInt(sdf.format(startDate).substring(index+1,index+3));
int day=Integer.parseInt(sdf.format(startDate).substring(index+4));



if(day>31) throw new DAOException("Invalid day in date  : "+sdf.format(startDate));
if(month>12) throw new DAOException("Invalid month in date  : "+sdf.format(startDate));


year=Integer.parseInt(sdf.format(endDate).substring(0,index));
month=Integer.parseInt(sdf.format(endDate).substring(index+1,index+3));
day=Integer.parseInt(sdf.format(endDate).substring(index+4));

if(day>31) throw new DAOException("Invalid day in date  : "+sdf.format(endDate));
if(month>12) throw new DAOException("Invalid month in date  : "+sdf.format(endDate));



Class.forName("com.mysql.cj.jdbc.Driver");

Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");



PreparedStatement preparedStatement=connection.prepareStatement("select * from special_buyer_rate where buyer_id=? and start_date=? and end_date=?");

preparedStatement.setInt(1,buyerId);
preparedStatement.setDate(2,new java.sql.Date(startDate.getTime()));
preparedStatement.setDate(3,new java.sql.Date(endDate.getTime()));
ResultSet resultSet=preparedStatement.executeQuery();



if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Rate for buyer id : "+buyerId+ " from date "+sdf.format(startDate)+" to date  "+sdf.format(endDate)+" is already declared ");
}




preparedStatement=connection.prepareStatement("select start_date , end_date  from special_buyer_rate where buyer_id=?");
preparedStatement.setInt(1,buyerId);

resultSet=preparedStatement.executeQuery();
java.util.Date sDate;
java.util.Date eDate;
while(resultSet.next())
{
sDate=new java.util.Date(resultSet.getDate(1).getTime());
eDate=new java.util.Date(resultSet.getDate(2).getTime());
if(startDate.compareTo(sDate)>=0 && startDate.compareTo(eDate)<=0)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Rate is already defined for date : "+sdf.format(startDate));
}

if(endDate.compareTo(sDate)>=0 && endDate.compareTo(eDate)<=0)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Rate is already defined for date : "+sdf.format(endDate));
}

}



preparedStatement=connection.prepareStatement("insert into special_buyer_rate(buyer_id,rate_per_fat,start_date,end_date) values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setInt(1,buyerId);
preparedStatement.setBigDecimal(2,ratePerFat);
preparedStatement.setDate(3,new java.sql.Date(startDate.getTime()));
preparedStatement.setDate(4,new java.sql.Date(endDate.getTime()));

preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
buyerRateDTO.setRateId(resultSet.getInt(1));

resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}

public BigDecimal getRate(java.util.Date date,int buyerId) throws DAOException
{
try
{
BigDecimal ratePerFat=null;
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");

SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
java.sql.Date sqlDate=new java.sql.Date(date.getTime());

PreparedStatement preparedStatement=connection.prepareStatement("select  rate_per_fat from special_buyer_rate where start_date<=? and end_date>=? and buyer_id=?");
preparedStatement.setDate(1,sqlDate);
preparedStatement.setDate(2,sqlDate);
preparedStatement.setInt(3,buyerId);
ResultSet resultSet=preparedStatement.executeQuery();

if(resultSet.next()==false) {
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Rate of  buyer id : "+buyerId+" for date : "+sdf.format(date)+" is not declared ");
}


ratePerFat=resultSet.getBigDecimal("rate_per_fat");
resultSet.close();
preparedStatement.close();
connection.close();
return ratePerFat;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}

public List<SpecialBuyerRateDTOInterface> getAll() throws DAOException
{
List<SpecialBuyerRateDTOInterface> list=new ArrayList<>();
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("select  rate_id , buyer_id , rate_per_fat,start_date,end_date from special_buyer_rate;");
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Rate list is empty ");
}
SpecialBuyerRateDTOInterface buyerRate=new SpecialBuyerRateDTO();
buyerRate.setRateId(resultSet.getInt("rate_id")); 
buyerRate.setBuyerId(resultSet.getInt("buyer_id"));
buyerRate.setRatePerFat(resultSet.getBigDecimal("rate_per_fat"));
buyerRate.setStartDate(resultSet.getDate("start_date"));
buyerRate.setEndDate(resultSet.getDate("end_date"));
list.add(buyerRate);

while(resultSet.next())
{
buyerRate=new SpecialBuyerRateDTO();
buyerRate.setRateId(resultSet.getInt("rate_id")); 
buyerRate.setBuyerId(resultSet.getInt("buyer_id"));
buyerRate.setRatePerFat(resultSet.getBigDecimal("rate_per_fat"));
buyerRate.setStartDate(resultSet.getDate("start_date"));
buyerRate.setEndDate(resultSet.getDate("end_date"));
list.add(buyerRate);
}

resultSet.close();
preparedStatement.close();
connection.close();
return list;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}

public SpecialBuyerRateDTOInterface getByRateId(int rateId) throws DAOException 
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("select  rate_id , buyer_id , rate_per_fat,start_date,end_date from special_buyer_rate where rate_id=?");
preparedStatement.setInt(1,rateId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid rate id ");
}
SpecialBuyerRateDTOInterface buyerRate=new SpecialBuyerRateDTO();
buyerRate.setRateId(resultSet.getInt("rate_id")); 
buyerRate.setBuyerId(resultSet.getInt("buyer_id"));
buyerRate.setRatePerFat(resultSet.getBigDecimal("rate_per_fat"));
buyerRate.setStartDate(resultSet.getDate("start_date"));
buyerRate.setEndDate(resultSet.getDate("end_date"));

resultSet.close();
preparedStatement.close();
connection.close();
return buyerRate;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}

public void update(SpecialBuyerRateDTOInterface buyerRateDTO) throws DAOException
{
throw new DAOException("Not yet implemented ");
}
public void delete(int rateId) throws DAOException
{
throw new DAOException("Not yet implemented ");
}
}