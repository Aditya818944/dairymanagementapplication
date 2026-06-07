package 	com.dairy.management.application.dl.interfaces.dao;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.exceptions.*;
import java.util.*;
import java.math.*;
public interface  SpecialFarmerRateDAOInterface  
{
public void add(SpecialFarmerRateDTOInterface farmerRateDTO) throws DAOException;
public List<SpecialFarmerRateDTOInterface> getAll() throws DAOException;
public SpecialFarmerRateDTOInterface getByRateId(int rateId) throws DAOException;
public void update(SpecialFarmerRateDTOInterface farmerRateDTO) throws DAOException;
public void delete(int rateId) throws DAOException;
public BigDecimal getRate(Date date,int buyerId) throws DAOException;
}