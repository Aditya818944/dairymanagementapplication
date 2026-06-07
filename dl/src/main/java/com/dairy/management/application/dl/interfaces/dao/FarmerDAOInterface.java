package 	com.dairy.management.application.dl.interfaces.dao;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.exceptions.*;
import java.util.*;
public interface FarmerDAOInterface
{
public void add(FarmerDTOInterface farmerDTO) throws DAOException;
public void update(FarmerDTOInterface farmerDTO) throws DAOException;
public void delete(int farmerId) throws DAOException;
public List<FarmerDTOInterface> getAllFarmers() throws DAOException;
public FarmerDTOInterface getByFarmerId(int farmerId) throws DAOException;
}