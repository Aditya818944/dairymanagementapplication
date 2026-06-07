package 	com.dairy.management.application.bl.interfaces.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.exceptions.*;
import java.util.*;
public interface FarmerManagerInterface
{
public void add(FarmerInterface farmer) throws BLException;
public void update(FarmerInterface farmer) throws BLException;
public void delete(int farmerId) throws BLException;
public FarmerInterface getFarmerById(int farmerId) throws BLException;
public Set<FarmerInterface>  getAll() throws BLException;
public boolean farmerIdExists(int farmerId);
public boolean phoneNumberExists(String phoneNumber);
}