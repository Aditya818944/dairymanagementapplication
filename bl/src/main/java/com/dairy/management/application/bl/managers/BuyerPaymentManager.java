package 	com.dairy.management.application.bl.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.interfaces.managers.*;
import com.dairy.management.application.bl.exceptions.*;



import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.interfaces.dao.*;
import com.dairy.management.application.dl.dto.*;
import com.dairy.management.application.dl.dao.*;
import com.dairy.management.application.dl.exceptions.*;


import java.util.*;
import java.math.*;
import java.text.*;
public class BuyerPaymentManager implements BuyerPaymentManagerInterface 
{
public void add(BuyerPaymentInterface buyerPayment) throws BLException 
{
BLException blException=new BLException();
if(buyerPayment==null) {
blException.setGenericException("buyer payment cannot be null ");
throw blException;
}
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

int buyerPaymentId=buyerPayment.getBuyerPaymentId();
if(buyerPaymentId!=0)  blException.addException("buyerPaymentId","Buyer Payment id should be zero , it is auto generated ");

int buyerId=buyerPayment.getBuyerId();
if(buyerId==0) blException.addException("buyerId","Buyer id is required ");
if(buyerId<0) blException.addException("buyerId","Invalid buyer Id : "+buyerId);

BigDecimal amountPaid=buyerPayment.getAmountPaid();
if(amountPaid==null) blException.addException("amountPaid","Amount value cannot be null or zero ");

String remarks=buyerPayment.getRemarks();
if(remarks==null) blException.addException("remarks","Remarks required ");
remarks=remarks.trim();
if(remarks.length()==0) blException.addException("remarks","Remarks required ");

Date paymentDate=buyerPayment.getPaymentDate();
if(paymentDate==null) blException.addException("paymentDate","Payment date is required ");

if(blException.hasExceptions()) throw blException;

try
{
BuyerPaymentDAOInterface dao=new BuyerPaymentDAO();
BuyerPaymentDTOInterface dto=new BuyerPaymentDTO();

dto.setBuyerId(buyerId);
dto.setAmountPaid(amountPaid);
dto.setRemarks(remarks);
dto.setPaymentDate(paymentDate);

dao.add(dto);

buyerPaymentId=dto.getBuyerPaymentId();
buyerPayment.setBuyerPaymentId(buyerPaymentId);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}


}
public void update(BuyerPaymentInterface buyerPayment) throws BLException {throw new BLException("Not yet implemented ");}
public void delete(int buyerPaymentId) throws BLException {throw new BLException("Not yet implemented ");}
}