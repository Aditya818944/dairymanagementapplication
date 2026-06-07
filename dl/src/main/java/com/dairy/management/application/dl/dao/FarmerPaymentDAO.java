package com.dairy.management.application.dl.dao;
import com.dairy.management.application.dl.interfaces.dao.*;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.exceptions.*;
import com.dairy.management.application.dl.dto.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.math.*;
public class FarmerPaymentDAO implements FarmerPaymentDAOInterface
{
public void add(FarmerPaymentDTOInterface farmerPaymentDTO) throws DAOException
{
int farmerPaymentId=farmerPaymentDTO.getFarmerPaymentId();
if(farmerPaymentId!=0) throw new DAOException("Farmer payment id should be zero ");
int farmerId=farmerPaymentDTO.getFarmerId();
FarmerDAO dao=new FarmerDAO();
if(dao.farmerIdExists(farmerId)==false) throw new DAOException("Not such farmer id exists  ( "+farmerId+" )");
if(farmerId==0) throw new DAOException("Farmer id should not be zero ");
java.util.Date paymentDate=farmerPaymentDTO.getPaymentDate();
if(paymentDate==null) throw new DAOException("Payment date should not be null ");
BigDecimal amountPaid=farmerPaymentDTO.getAmountPaid();
if(amountPaid==null) throw new DAOException("Amount should not be null ");
String remark=farmerPaymentDTO.getRemarks();
if(remark==null) throw new DAOException("Remark should not be null or of zero length ");
if(remark.length()==0) throw new DAOException("Remark should not be of zero length ");
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("insert into farmer_payment(farmer_id,payment_date,amount_paid,remarks) values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);

preparedStatement.setInt(1,farmerId);
preparedStatement.setDate(2,new java.sql.Date(paymentDate.getTime()));
preparedStatement.setBigDecimal(3,amountPaid);
preparedStatement.setString(4,remark);

preparedStatement.executeUpdate();
ResultSet resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();

farmerPaymentDTO.setFarmerPaymentId(resultSet.getInt(1));

resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public void update(FarmerPaymentDTOInterface farmerPaymentDTO) throws DAOException
{
throw new DAOException("Not yet implemented ");
}

public void delete(int farmerPaymentId ) throws DAOException
{
throw new DAOException("Not yet implemented ");
}

}