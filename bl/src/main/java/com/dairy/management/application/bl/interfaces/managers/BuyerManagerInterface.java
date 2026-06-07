package 	com.dairy.management.application.bl.interfaces.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.exceptions.*;
import java.util.*;
public interface BuyerManagerInterface 
{
public void add(BuyerInterface buyer) throws BLException;
public void update(BuyerInterface buyer) throws BLException;
public void delete(int buyerId) throws BLException;
public BuyerInterface getBuyerById(int buyerId) throws BLException;
public Set<BuyerInterface>  getAll() throws BLException;
public boolean buyerIdExists(int buyerId);
public boolean phoneNumberExists(String phoneNumber) ;
}