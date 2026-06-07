package 	com.dairy.management.application.dl.interfaces.dao;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.exceptions.*;
import java.util.*;
import java.math.*;
public interface MilkCollectionDAOInterface  
{
public void add(MilkCollectionDTOInterface milkCollectionDTO) throws DAOException;
public void update(MilkCollectionDTOInterface milkCollectionDTO) throws DAOException;
public void delete(int milkCollectionId) throws DAOException;
public List<MilkCollectionDTOInterface> getAll() throws  DAOException;
public List<MilkCollectionDTOInterface> getByFarmerIdAndDate(int farmerId,java.util.Date date) throws DAOException;
public List<MilkCollectionDTOInterface> getAllByFarmerId(int farmerId) throws DAOException;
public BigDecimal getAmountByDateAndFarmerId(int farmerId,java.util.Date startDate,java.util.Date endDate) throws DAOException;
public void recollect(MilkCollectionDTOInterface milkCollectionDTO) throws DAOException;
}