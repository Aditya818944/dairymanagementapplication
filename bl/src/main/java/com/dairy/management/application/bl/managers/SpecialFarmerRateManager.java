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

public class SpecialFarmerRateManager implements  SpecialFarmerRateManagerInterface
{
private Set<SpecialFarmerRateInterface> rateSet;
private Map<Integer,SpecialFarmerRateInterface> rateIdWiseRateMap;
private static  SpecialFarmerRateManagerInterface manager;
private SpecialFarmerRateManager() throws BLException
{
populateDataStructures();
}
public static SpecialFarmerRateManagerInterface getSpecialFarmerRateManager() throws BLException
{
if(manager!=null) return manager;
manager=new SpecialFarmerRateManager();
return manager;
}
public void populateDataStructures() throws BLException
{
BLException blException=new BLException();
this.rateSet=new HashSet<>();
this.rateIdWiseRateMap=new HashMap<>();
try
{
SpecialFarmerRateDAOInterface  dao=new SpecialFarmerRateDAO();
SpecialFarmerRateInterface rate;
for(SpecialFarmerRateDTOInterface dto : dao.getAll())
{
rate=new SpecialFarmerRate();
rate.setRateId(dto.getRateId());
rate.setFarmerId(dto.getFarmerId());
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

public void add(SpecialFarmerRateInterface farmerRate) throws BLException 
{
BLException blException=new BLException();
if(farmerRate==null) {
blException.setGenericException("farmerRate cannot be null ");
throw blException;
}
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
int rateId=farmerRate.getRateId();
if(rateId!=0)  blException.addException("rateId","Rate id should be zero it auto generated ");

int farmerId=farmerRate.getFarmerId();
if(farmerId==0) blException.addException("farmerId","Farmer id required ");

BigDecimal ratePerFat=farmerRate.getRatePerFat();
if(ratePerFat==null) blException.addException("ratePerFat","Rate per fat required ");

Date startDate=farmerRate.getStartDate();
if(startDate==null) blException.addException("startDate","Start date is required ");

Date endDate=farmerRate.getEndDate();
if(endDate==null) blException.addException("endDate","End date is required ");


if(blException.hasExceptions()) throw blException;

try
{
SpecialFarmerRateDTOInterface dto=new SpecialFarmerRateDTO();

dto.setRatePerFat(ratePerFat);
dto.setFarmerId(farmerId);
dto.setStartDate(startDate);
dto.setEndDate(endDate);

SpecialFarmerRateDAOInterface dao=new SpecialFarmerRateDAO();
dao.add(dto);

rateId=dto.getRateId();
farmerRate.setRateId(rateId);

SpecialFarmerRateInterface  rate=new SpecialFarmerRate();
rate.setRateId(farmerRate.getRateId());
rate.setFarmerId(farmerRate.getFarmerId());
rate.setRatePerFat(farmerRate.getRatePerFat());
rate.setStartDate(farmerRate.getStartDate());
rate.setEndDate(farmerRate.getEndDate());
this.rateSet.add(rate);
this.rateIdWiseRateMap.put(rate.getRateId(),rate);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public Set<SpecialFarmerRateInterface> getAll() throws BLException
{
Set<SpecialFarmerRateInterface> rateList=new HashSet<>();
SpecialFarmerRateInterface farmerRate;
for(SpecialFarmerRateInterface rate : this.rateSet)
{
farmerRate=new SpecialFarmerRate();
farmerRate.setRateId(rate.getRateId());
farmerRate.setFarmerId(rate.getFarmerId());
farmerRate.setRatePerFat(rate.getRatePerFat());
farmerRate.setStartDate(rate.getStartDate());
farmerRate.setEndDate(rate.getEndDate());
rateList.add(farmerRate);
}
return rateList;
}
public void update(SpecialFarmerRateInterface farmerRate) throws BLException {throw new BLException("Not yet implemented ");}
public void delete(int rateId) throws BLException {throw new BLException("Not yet implemented ");}

public SpecialFarmerRateInterface getByRateId(int rateId) throws BLException 
{
BLException blException=new BLException();
SpecialFarmerRateInterface farmer=new SpecialFarmerRate();
if(this.rateIdWiseRateMap.containsKey(rateId)==false) 
{
blException.setGenericException("No such rate id exists : "+rateId);
throw blException;
}
SpecialFarmerRateInterface farmerRate=this.rateIdWiseRateMap.get(rateId);
farmer.setRateId(farmerRate.getRateId());
farmer.setFarmerId(farmerRate.getFarmerId());
farmer.setRatePerFat(farmerRate.getRatePerFat());
farmer.setStartDate(farmerRate.getStartDate());
farmer.setEndDate(farmerRate.getEndDate());
return farmer;
}


public BigDecimal getRate(int farmerId,Date date) throws BLException
{
for(SpecialFarmerRateInterface f : this.rateSet) if((date.compareTo(f.getStartDate())>=0) &&  (date.compareTo(f.getEndDate())<=0) && f.getFarmerId()==farmerId) return f.getRatePerFat();
return null;
}



}