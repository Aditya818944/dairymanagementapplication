package 	com.dairy.management.application.bl.interfaces.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.exceptions.*;
import java.util.*;
import java.math.*;
public interface BuyerSaleManagerInterface 
{
public void add(BuyerSaleInterface buyerSale) throws BLException;
public void update(BuyerSaleInterface buyerSale) throws BLException;
public void delete(int buyerSaleId) throws BLException;
public Set<BuyerSaleInterface> getByBuyerIdAndDate(int buyerId,java.util.Date date) throws BLException;
public Set<BuyerSaleInterface> getAllByBuyerId(int buyerId) throws BLException;
public BigDecimal getAmountByDateAndBuyerId(int buyerId,java.util.Date startDate,java.util.Date endDate) throws BLException;
public BigDecimal getAmountByDate(java.util.Date startDate,java.util.Date endDate) throws BLException;
public BigDecimal getTotalMilkSoldInLiters(Date from,Date to) throws BLException;
public boolean buyerIdExists(int buyerId) throws BLException;
public void reSale(BuyerSaleInterface buyerSale) throws BLException;
}