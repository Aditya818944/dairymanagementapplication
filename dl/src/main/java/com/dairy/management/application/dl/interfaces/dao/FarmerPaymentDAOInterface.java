package 	com.dairy.management.application.dl.interfaces.dao;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.exceptions.*;
public interface FarmerPaymentDAOInterface
{
public void add(FarmerPaymentDTOInterface farmerPaymentDTO) throws DAOException;
public void update(FarmerPaymentDTOInterface farmerPaymentDTO) throws DAOException;
public void delete(int farmerPaymentId) throws DAOException;
}