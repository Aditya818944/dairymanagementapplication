package 	com.dairy.management.application.bl.interfaces.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.exceptions.*;
import java.util.*;
import java.math.*;
public interface  FarmerRateManagerInterface
{
public void add(FarmerRateInterface farmerRate) throws BLException;
public void update(FarmerRateInterface farmerRate) throws BLException;
public void delete(int rateId) throws BLException;
public FarmerRateInterface getByRateId(int rateId) throws BLException;
public BigDecimal getRate(Date date) throws BLException;
public Set<FarmerRateInterface> getAll() throws BLException;
}