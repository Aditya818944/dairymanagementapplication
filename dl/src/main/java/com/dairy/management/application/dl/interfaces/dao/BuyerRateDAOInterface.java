package 	com.dairy.management.application.dl.interfaces.dao;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.exceptions.*;
import java.math.*;
import java.util.*;
import java.text.*;
public interface BuyerRateDAOInterface
{
public void add(BuyerRateDTOInterface buyerRateDTO) throws DAOException;
public void update(BuyerRateDTOInterface buyerRateDTO) throws DAOException;
public List<BuyerRateDTOInterface> getAll() throws DAOException;
public BuyerRateDTOInterface getByRateId(int rateId) throws DAOException;
public void delete(int rateId) throws DAOException;
public BigDecimal getRate(Date date) throws DAOException;
}