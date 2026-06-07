package 	com.dairy.management.application.bl.interfaces.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.exceptions.*;
import java.util.*;
import java.math.*;
public interface SpecialBuyerRateManagerInterface
{
public void add(SpecialBuyerRateInterface buyerRate) throws BLException;
public void update(SpecialBuyerRateInterface buyerRate) throws BLException;
public void delete(int rateId) throws BLException;
public SpecialBuyerRateInterface getByRateId(int rateId) throws BLException;
public BigDecimal getRate(int buyerId,Date date) throws BLException;
public Set<SpecialBuyerRateInterface> getAll() throws BLException;
}