package 	com.dairy.management.application.bl.interfaces.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.exceptions.*;
import java.util.*;
import java.math.*;
public interface  SpecialFarmerRateManagerInterface
{
public void add(SpecialFarmerRateInterface farmerRate) throws BLException;
public void update(SpecialFarmerRateInterface farmerRate) throws BLException;
public void delete(int rateId) throws BLException;
public SpecialFarmerRateInterface getByRateId(int rateId) throws BLException;
public BigDecimal getRate(int farmerId,Date date) throws BLException;
public Set<SpecialFarmerRateInterface> getAll() throws BLException;
}