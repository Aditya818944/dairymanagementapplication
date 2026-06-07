package 	com.dairy.management.application.bl.interfaces.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.exceptions.*;
import java.util.*;
import java.math.*;
public interface BuyerRateManagerInterface
{
public void add(BuyerRateInterface buyerRate) throws BLException;
public void update(BuyerRateInterface buyerRate) throws BLException;
public void delete(int rateId) throws BLException;
public BuyerRateInterface getByRateId(int rateId) throws BLException;
public BigDecimal getRate(Date date) throws BLException;
public Set<BuyerRateInterface> getAll() throws BLException;
}