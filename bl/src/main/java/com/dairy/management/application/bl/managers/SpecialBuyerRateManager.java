package com.dairy.management.application.bl.managers;
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

public class SpecialBuyerRateManager implements  SpecialBuyerRateManagerInterface
{
private Set<SpecialBuyerRateInterface> rateSet;
private Map<Integer,SpecialBuyerRateInterface> rateIdWiseRateMap;
private static  SpecialBuyerRateManagerInterface manager;
private SpecialBuyerRateManager() throws BLException
{
populateDataStructures();
}
public static SpecialBuyerRateManagerInterface getSpecialBuyerRateManager() throws BLException
{
if(manager!=null) return manager;
manager=new SpecialBuyerRateManager();
return manager;
}
public void populateDataStructures() throws BLException
{
BLException blException=new BLException();
this.rateSet=new HashSet<>();
this.rateIdWiseRateMap=new HashMap<>();
try
{

SpecialBuyerRateDAOInterface  dao=new SpecialBuyerRateDAO();

SpecialBuyerRateInterface rate;
for(SpecialBuyerRateDTOInterface dto : dao.getAll())
{
rate=new SpecialBuyerRate();
rate.setRateId(dto.getRateId());
rate.setBuyerId(dto.getBuyerId());
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

public void add(SpecialBuyerRateInterface buyerRate) throws BLException 
{
BLException blException=new BLException();
if(buyerRate==null) {
blException.setGenericException("buyerRate cannot be null ");
throw blException;
}
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
int rateId=buyerRate.getRateId();
if(rateId!=0)  blException.addException("rateId","Rate id should be zero it auto generated ");

int buyerId=buyerRate.getBuyerId();
if(buyerId==0) blException.addException("buyerId","Buyer id required ");

BigDecimal ratePerFat=buyerRate.getRatePerFat();
if(ratePerFat==null) blException.addException("ratePerFat","Rate per fat required ");

Date startDate=buyerRate.getStartDate();
if(startDate==null) blException.addException("startDate","Start date is required ");

Date endDate=buyerRate.getEndDate();
if(endDate==null) blException.addException("endDate","End date is required ");


if(blException.hasExceptions()) throw blException;

try
{
SpecialBuyerRateDTOInterface dto=new SpecialBuyerRateDTO();

dto.setRatePerFat(ratePerFat);
dto.setBuyerId(buyerId);
dto.setStartDate(startDate);
dto.setEndDate(endDate);

SpecialBuyerRateDAOInterface dao=new SpecialBuyerRateDAO();
dao.add(dto);

rateId=dto.getRateId();
buyerRate.setRateId(rateId);

SpecialBuyerRateInterface rate=new SpecialBuyerRate();
rate=new SpecialBuyerRate();
rate.setRateId(buyerRate.getRateId());
rate.setBuyerId(buyerRate.getBuyerId());
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
public Set<SpecialBuyerRateInterface> getAll() throws BLException
{
Set<SpecialBuyerRateInterface> rateList=new HashSet<>();
SpecialBuyerRateInterface buyerRate;
for(SpecialBuyerRateInterface rate : this.rateSet)
{
buyerRate=new SpecialBuyerRate();
buyerRate.setRateId(rate.getRateId());
buyerRate.setBuyerId(rate.getBuyerId());
buyerRate.setRatePerFat(rate.getRatePerFat());
buyerRate.setStartDate(rate.getStartDate());
buyerRate.setEndDate(rate.getEndDate());
rateList.add(buyerRate);
}
return rateList;
}
public void update(SpecialBuyerRateInterface buyerRate) throws BLException {throw new BLException("Not yet implemented ");}
public void delete(int rateId) throws BLException {throw new BLException("Not yet implemented ");}

public SpecialBuyerRateInterface getByRateId(int rateId) throws BLException 
{
BLException blException=new BLException();
SpecialBuyerRateInterface buyer=new SpecialBuyerRate();
if(this.rateIdWiseRateMap.containsKey(rateId)==false) 
{
blException.setGenericException("No such rate id exists : "+rateId);
throw blException;
}
SpecialBuyerRateInterface buyerRate=this.rateIdWiseRateMap.get(rateId);
buyer.setRateId(buyerRate.getRateId());
buyer.setBuyerId(buyerRate.getBuyerId());
buyer.setRatePerFat(buyerRate.getRatePerFat());
buyer.setStartDate(buyerRate.getStartDate());
buyer.setEndDate(buyerRate.getEndDate());
return buyer;
}

public BigDecimal getRate(int buyerId,Date date) throws BLException
{
for(SpecialBuyerRateInterface b : this.rateSet) if((date.compareTo(b.getStartDate())>=0) &&  (date.compareTo(b.getEndDate())<=0) && b.getBuyerId()==buyerId) return b.getRatePerFat();
return null;
}



}