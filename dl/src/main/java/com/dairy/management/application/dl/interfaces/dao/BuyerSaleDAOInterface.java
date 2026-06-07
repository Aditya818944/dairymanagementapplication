package 	com.dairy.management.application.dl.interfaces.dao;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.exceptions.*;
import java.util.*;
import java.math.*;
public interface BuyerSaleDAOInterface 
{
public void add(BuyerSaleDTOInterface buyerSaleDTO) throws DAOException;
public void update(BuyerSaleDTOInterface buyerSaleDTO) throws DAOException;
public void delete(int buyerSaleId) throws DAOException;
public void reSale(BuyerSaleDTOInterface buyerSaleDTO) throws DAOException;
public List<BuyerSaleDTOInterface> getAll() throws DAOException;
}