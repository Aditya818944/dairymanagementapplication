package 	com.dairy.management.application.bl.interfaces.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.exceptions.*;
public interface BuyerPaymentManagerInterface 
{
public void add(BuyerPaymentInterface buyerPayment) throws BLException;
public void update(BuyerPaymentInterface buyerPayment) throws BLException;
public void delete(int buyerPaymentId) throws BLException;
}