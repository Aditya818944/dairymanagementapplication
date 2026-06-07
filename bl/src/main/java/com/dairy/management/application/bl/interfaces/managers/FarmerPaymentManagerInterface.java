package 	com.dairy.management.application.bl.interfaces.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.exceptions.*;
public interface FarmerPaymentManagerInterface
{
public void add(FarmerPaymentInterface farmerPayment) throws BLException;
public void update(FarmerPaymentInterface farmerPayment) throws BLException;
public void delete(int farmerPaymentId) throws BLException;
}