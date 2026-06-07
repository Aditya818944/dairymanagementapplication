package 	com.dairy.management.application.bl.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.pojo.*;

import com.dairy.management.application.bl.interfaces.managers.*;
import com.dairy.management.application.bl.managers.*;

import com.dairy.management.application.bl.exceptions.*;

import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.interfaces.dao.*;
import com.dairy.management.application.dl.dto.*;
import com.dairy.management.application.dl.dao.*;
import com.dairy.management.application.dl.exceptions.*;

import java.util.*;
import java.text.*;
import java.math.*;

public class FarmerRateManager implements FarmerRateManagerInterface
{
private Set<FarmerRateInterface> rateSet;
private Map<Integer,FarmerRateInterface> rateIdWiseRateMap;
private static FarmerRateManagerInterface manager;

private FarmerRateManager() throws BLException
{
populateDataStructures();
}

public static FarmerRateManagerInterface getFarmerRateManager() throws BLException
{
if(manager!=null) return manager;
manager=new FarmerRateManager();
return manager;
}

private void populateDataStructures()
{
BLException blException=new BLException();
this.rateSet=new HashSet<>();
this.rateIdWiseRateMap=new HashMap<>();
try
{
FarmerRateDAOInterface  dao=new FarmerRateDAO();
FarmerRateInterface rate;
for(FarmerRateDTOInterface dto : dao.getAll())
{
rate=new FarmerRate();
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
public void add(FarmerRateInterface farmerRate) throws BLException 
{
BLException blException=new BLException();
if(farmerRate==null) {
blException.setGenericException("farmerRate cannot be null ");
throw blException;
}
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
int rateId=farmerRate.getRateId();
if(rateId!=0)  blException.addException("rateId","Rate id should be zero it auto generated ");

BigDecimal ratePerFat=farmerRate.getRatePerFat();
if(ratePerFat==null) blException.addException("ratePerFat","Rate per fat required ");

Date startDate=farmerRate.getStartDate();
if(startDate==null) blException.addException("startDate","Start date is required ");

Date endDate=farmerRate.getEndDate();
if(endDate==null) blException.addException("endDate","End date is required ");


if(blException.hasExceptions()) throw blException;

try
{
FarmerRateDTOInterface dto=new FarmerRateDTO();

dto.setRatePerFat(ratePerFat);
dto.setStartDate(startDate);
dto.setEndDate(endDate);

FarmerRateDAOInterface dao=new FarmerRateDAO();
dao.add(dto);

rateId=dto.getRateId();
farmerRate.setRateId(rateId); 

FarmerRateInterface rate=new FarmerRate();
rate=new FarmerRate();
rate.setRateId(farmerRate.getRateId());
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



public Set<FarmerRateInterface> getAll()throws BLException 
{
Set<FarmerRateInterface> rateList=new HashSet<>();
FarmerRateInterface buyerRate;
for(FarmerRateInterface rate : this.rateSet)
{
buyerRate=new FarmerRate();
buyerRate.setRateId(rate.getRateId());
buyerRate.setRatePerFat(rate.getRatePerFat());
buyerRate.setStartDate(rate.getStartDate());
buyerRate.setEndDate(rate.getEndDate());
rateList.add(buyerRate);
}
return rateList;
}



public FarmerRateInterface getByRateId(int rateId) throws BLException 
{
BLException blException=new BLException();
FarmerRateInterface farmer=new FarmerRate();
if(this.rateIdWiseRateMap.containsKey(rateId)==false) 
{
blException.setGenericException("No such rate id exists : "+rateId);
throw blException;
}
FarmerRateInterface farmerRate=this.rateIdWiseRateMap.get(rateId);
farmer.setRateId(farmerRate.getRateId());
farmer.setRatePerFat(farmerRate.getRatePerFat());
farmer.setStartDate(farmerRate.getStartDate());
farmer.setEndDate(farmerRate.getEndDate());
return farmer;
}

public BigDecimal getRate(Date date) throws BLException
{
for(FarmerRateInterface f : this.rateSet) if((date.compareTo(f.getStartDate())>=0) &&  (date.compareTo(f.getEndDate())<=0)) return f.getRatePerFat();
return null;
}



public void update(FarmerRateInterface farmerRate) throws BLException {throw new BLException("Not yet implemented ");}
public void delete(int rateId) throws BLException {throw new BLException("Not yet implemented ");}
}