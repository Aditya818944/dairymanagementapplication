package 	com.dairy.management.application.dl.interfaces.dao;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.exceptions.*;
public interface BuyerPaymentDAOInterface 
{
public void add(BuyerPaymentDTOInterface buyerPaymentDTO) throws DAOException;
public void update(BuyerPaymentDTOInterface buyerPaymentDTO) throws DAOException;
public void delete(int buyerPaymentId) throws DAOException;
}