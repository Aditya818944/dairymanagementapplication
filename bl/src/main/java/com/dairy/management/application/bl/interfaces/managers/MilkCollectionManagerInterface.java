package 	com.dairy.management.application.bl.interfaces.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.exceptions.*;
import java.util.*;
import java.math.*;
public interface MilkCollectionManagerInterface
{
public void add(MilkCollectionInterface milkCollection) throws BLException;
public void update(MilkCollectionInterface milkCollection) throws BLException;
public void delete(int milkCollectionId) throws BLException;
public Set<MilkCollectionInterface> getByFarmerIdAndDate(int farmerId,java.util.Date date) throws BLException;
public Set<MilkCollectionInterface> getAllByFarmerId(int farmerId) throws BLException;
public BigDecimal getAmountByDateAndFarmerId(int farmerId,java.util.Date startDate,java.util.Date endDate) throws BLException;
public BigDecimal getAmountByDate(java.util.Date startDate,java.util.Date endDate) throws BLException;
public boolean farmerIdExists(int farmerId) throws BLException;
public BigDecimal getTotalMilkCollectedInLiters(Date from,Date to)throws BLException;
public void recollect(MilkCollectionInterface milkCollection) throws BLException;
}