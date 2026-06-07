package 	com.dairy.management.application.bl.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.interfaces.managers.*;
import com.dairy.management.application.bl.exceptions.*;


import com.dairy.management.application.bl.pojo.*;

import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.interfaces.dao.*;

import com.dairy.management.application.dl.dto.*;
import com.dairy.management.application.dl.dao.*;

import com.dairy.management.application.dl.exceptions.*;

import java.util.*;
import java.text.*;
import java.math.*;

public class BuyerRateManager implements  BuyerRateManagerInterface
{
private Set<BuyerRateInterface> rateSet;
private Map<Integer,BuyerRateInterface> rateIdWiseRateMap;
private static  BuyerRateManagerInterface manager;
private BuyerRateManager() throws BLException
{
populateDataStructures();
}
public static BuyerRateManagerInterface getBuyerRateManager() throws BLException
{
if(manager!=null) return manager;
manager=new BuyerRateManager();
return manager;
}
public void populateDataStructures() throws BLException
{
BLException blException=new BLException();
this.rateSet=new HashSet<>();
this.rateIdWiseRateMap=new HashMap<>();
try
{
BuyerRateDAOInterface  dao=new BuyerRateDAO();
BuyerRateInterface rate;
for(BuyerRateDTOInterface dto : dao.getAll())
{
rate=new BuyerRate();
rate.setRateId(dto.getRateId());
rate.setRatePerFat(dto.getRatePerFat());
rate.setStartDate(dto.getStartDate());
rate.setEndDate(dto.getEndDate());
this.rateSet.add(rate);
this.rateIdWiseRateMap.put(rate.getRateId(),rate);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}

public void add(BuyerRateInterface buyerRate) throws BLException 
{
BLException blException=new BLException();
if(buyerRate==null) {
blException.setGenericException("buyerRate cannot be null ");
throw blException;
}
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
int rateId=buyerRate.getRateId();
if(rateId!=0)  blException.addException("rateId","Rate id should be zero it auto generated ");

BigDecimal ratePerFat=buyerRate.getRatePerFat();
if(ratePerFat==null) blException.addException("ratePerFat","Rate per fat required ");

Date startDate=buyerRate.getStartDate();
if(startDate==null) blException.addException("startDate","Start date is required ");

Date endDate=buyerRate.getEndDate();
if(endDate==null) blException.addException("endDate","End date is required ");


if(blException.hasExceptions()) throw blException;

try
{
BuyerRateDTOInterface dto=new BuyerRateDTO();

dto.setRatePerFat(ratePerFat);
dto.setStartDate(startDate);
dto.setEndDate(endDate);

BuyerRateDAOInterface dao=new BuyerRateDAO();
dao.add(dto);

rateId=dto.getRateId();
buyerRate.setRateId(rateId);


BuyerRateInterface rate=new BuyerRate();
rate=new BuyerRate();
rate.setRateId(buyerRate.getRateId());
rate.setRatePerFat(buyerRate.getRatePerFat());
rate.setStartDate(buyerRate.getStartDate());
rate.setEndDate(buyerRate.getEndDate());
this.rateSet.add(rate);
this.rateIdWiseRateMap.put(rate.getRateId(),rate);

}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public Set<BuyerRateInterface> getAll() throws BLException
{
Set<BuyerRateInterface> rateList=new HashSet<>();
BuyerRateInterface buyerRate;
for(BuyerRateInterface rate : this.rateSet)
{
buyerRate=new BuyerRate();
buyerRate.setRateId(rate.getRateId());
buyerRate.setRatePerFat(rate.getRatePerFat());
buyerRate.setStartDate(rate.getStartDate());
buyerRate.setEndDate(rate.getEndDate());
rateList.add(buyerRate);
}
return rateList;
}

public BuyerRateInterface getByRateId(int rateId) throws BLException 
{
BLException blException=new BLException();
BuyerRateInterface buyer=new BuyerRate();
if(this.rateIdWiseRateMap.containsKey(rateId)==false) 
{
blException.setGenericException("No such rate id exists : "+rateId);
throw blException;
}
BuyerRateInterface buyerRate=this.rateIdWiseRateMap.get(rateId);
buyer.setRateId(buyerRate.getRateId());
buyer.setRatePerFat(buyerRate.getRatePerFat());
buyer.setStartDate(buyerRate.getStartDate());
buyer.setEndDate(buyerRate.getEndDate());
return buyer;
}

public BigDecimal getRate(Date date) throws BLException
{
for(BuyerRateInterface b : this.rateSet)  if((date.compareTo(b.getStartDate())>=0) &&  (date.compareTo(b.getEndDate())<=0)) return b.getRatePerFat();
return null;
}


public void update(BuyerRateInterface buyerRate) throws BLException {throw new BLException("Not yet implemented ");}
public void delete(int rateId) throws BLException {throw new BLException("Not yet implemented ");}
}