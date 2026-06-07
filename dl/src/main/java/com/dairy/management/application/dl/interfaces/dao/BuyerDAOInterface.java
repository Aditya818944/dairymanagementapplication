package 	com.dairy.management.application.dl.interfaces.dao;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.exceptions.*;
import java.util.*;
public interface BuyerDAOInterface 
{
public void add(BuyerDTOInterface buyerDTO) throws DAOException;
public void update(BuyerDTOInterface buyerDTO) throws DAOException;
public void delete(int buyerId) throws DAOException;
public List<BuyerDTOInterface> getAllBuyers() throws DAOException;
public BuyerDTOInterface getByBuyerId(int buyerId) throws DAOException;
}