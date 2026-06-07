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
import java.text.*;
import java.math.*;

public class FarmerPaymentManager implements FarmerPaymentManagerInterface
{
public void add(FarmerPaymentInterface farmerPayment) throws BLException 
{
BLException blException=new BLException();
if(farmerPayment==null) {
blException.setGenericException("buyer payment cannot be null ");
throw blException;
}
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

int farmerPaymentId=farmerPayment.getFarmerPaymentId();
if(farmerPaymentId!=0)  blException.addException("farmerPaymentId","Buyer Payment id should be zero , it is auto generated ");

int farmerId=farmerPayment.getFarmerId();
if(farmerId==0) blException.addException("farmerId","Buyer id is required ");
if(farmerId<0) blException.addException("farmerId","Invalid buyer Id : "+farmerId);

BigDecimal amountPaid=farmerPayment.getAmountPaid();
if(amountPaid==null) blException.addException("amountPaid","Amount value cannot be null or zero ");

String remarks=farmerPayment.getRemarks();
if(remarks==null) blException.addException("remarks","Remarks required ");
remarks=remarks.trim();
if(remarks.length()==0) blException.addException("remarks","Remarks required ");

Date paymentDate=farmerPayment.getPaymentDate();
if(paymentDate==null) blException.addException("paymentDate","Payment date is required ");

if(blException.hasExceptions()) throw blException;

try
{
FarmerPaymentDAOInterface dao=new FarmerPaymentDAO();
FarmerPaymentDTOInterface dto=new FarmerPaymentDTO();

dto.setFarmerId(farmerId);
dto.setAmountPaid(amountPaid);
dto.setRemarks(remarks);
dto.setPaymentDate(paymentDate);

dao.add(dto);

farmerPaymentId=dto.getFarmerPaymentId();
farmerPayment.setFarmerPaymentId(farmerPaymentId);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void update(FarmerPaymentInterface farmerPayment) throws BLException {throw new BLException("Not yet implemented ");}
public void delete(int farmerPaymentId) throws BLException {throw new BLException("Not yet implemented ");}
}