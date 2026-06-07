package com.dairy.management.application.dl.dao;
import com.dairy.management.application.dl.interfaces.dao.*;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.exceptions.*;
import com.dairy.management.application.dl.dto.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.math.*;
public class BuyerPaymentDAO implements BuyerPaymentDAOInterface
{
public void add(BuyerPaymentDTOInterface buyerPaymentDTO) throws DAOException
{
int buyerPaymentId=buyerPaymentDTO.getBuyerPaymentId();
if(buyerPaymentId!=0) throw new DAOException("Buyer payment id should be zero ");
int buyerId=buyerPaymentDTO.getBuyerId();
BuyerDAO dao=new BuyerDAO();
if(dao.buyerIdExists(buyerId)==false) throw new DAOException("Not such buyer id exists  ( "+buyerId+" )");
if(buyerId==0) throw new DAOException("Buyer id should not be zero ");
java.util.Date paymentDate=buyerPaymentDTO.getPaymentDate();
if(paymentDate==null) throw new DAOException("Payment date should not be null ");
BigDecimal amountPaid=buyerPaymentDTO.getAmountPaid();
if(amountPaid==null) throw new DAOException("Amount should not be null ");
String remark=buyerPaymentDTO.getRemarks();
if(remark==null) throw new DAOException("Remark should not be null or of zero length ");
if(remark.length()==0) throw new DAOException("Remark should not be of zero length ");
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tstdb","tstdbuser","tstdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("insert into buyer_payment(buyer_id,payment_date,amount_paid,remarks) values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);

preparedStatement.setInt(1,buyerId);
preparedStatement.setDate(2,new java.sql.Date(paymentDate.getTime()));
preparedStatement.setBigDecimal(3,amountPaid);
preparedStatement.setString(4,remark);

preparedStatement.executeUpdate();
ResultSet resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();

buyerPaymentDTO.setBuyerPaymentId(resultSet.getInt(1));

resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}



public void update(BuyerPaymentDTOInterface buyerPaymentDTO) throws DAOException
{
throw new DAOException("Not yet implemented ");
}

public void delete(int buyerPaymentId ) throws DAOException
{
throw new DAOException("Not yet implemented ");
}



}