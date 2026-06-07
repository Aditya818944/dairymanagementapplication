package 	com.dairy.management.application.dl.interfaces.dao;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.exceptions.*;
import java.util.*;
import java.math.*;
public interface  FarmerRateDAOInterface  
{
public void add(FarmerRateDTOInterface farmerRateDTO) throws DAOException;
public List<FarmerRateDTOInterface> getAll() throws DAOException;
public FarmerRateDTOInterface getByRateId(int rateId) throws DAOException;
public void update(FarmerRateDTOInterface farmerRateDTO) throws DAOException;
public void delete(int rateId) throws DAOException;
public BigDecimal getRate(Date date) throws DAOException;
}