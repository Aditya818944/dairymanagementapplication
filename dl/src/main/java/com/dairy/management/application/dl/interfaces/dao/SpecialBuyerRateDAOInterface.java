package 	com.dairy.management.application.dl.interfaces.dao;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.exceptions.*;
import java.math.*;
import java.util.*;
import java.text.*;
public interface SpecialBuyerRateDAOInterface
{
public void add(SpecialBuyerRateDTOInterface buyerRateDTO) throws DAOException;
public void update(SpecialBuyerRateDTOInterface buyerRateDTO) throws DAOException;
public List<SpecialBuyerRateDTOInterface> getAll() throws DAOException;
public SpecialBuyerRateDTOInterface getByRateId(int rateId) throws DAOException;
public void delete(int rateId) throws DAOException;
public BigDecimal getRate(Date date,int buyerId) throws DAOException;
}