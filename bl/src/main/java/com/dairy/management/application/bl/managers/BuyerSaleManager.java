package 	com.dairy.management.application.bl.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.interfaces.managers.*;
import com.dairy.management.application.bl.pojo.*;
import com.dairy.management.application.bl.managers.*;
import com.dairy.management.application.bl.exceptions.*;
import com.dairy.management.application.dl.interfaces.dao.*;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.dao.*;
import com.dairy.management.application.dl.dto.*;
import com.dairy.management.application.dl.exceptions.*;

import java.math.*;
import java.util.*;
import java.text.*;
public class BuyerSaleManager implements BuyerSaleManagerInterface 
{
private Map<Integer,BuyerSaleInterface> buyerSaleIdWiseSaleMap;
private Map<String,BuyerSaleInterface> buyerIdDateShiftWiseSaleMap;
private Set<BuyerSaleInterface> buyerSalesSet;
private BigDecimal totalMilkSold;
private static BuyerSaleManagerInterface manager;
private BuyerSaleManager() throws BLException
{
populateDataStructures();
}
public static BuyerSaleManagerInterface getBuyerSaleManager() throws BLException
{
if(manager==null)  manager=new BuyerSaleManager();
return manager;
}
public void populateDataStructures() throws BLException
{
this.totalMilkSold=BigDecimal.ZERO;
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
buyerSaleIdWiseSaleMap=new HashMap<>();
buyerIdDateShiftWiseSaleMap=new HashMap<>();
buyerSalesSet=new TreeSet<>();
try
{
BuyerSaleDAOInterface dao=new BuyerSaleDAO();
BuyerSaleInterface buyerSale;
for(BuyerSaleDTOInterface dto : dao.getAll())
{
buyerSale=new BuyerSale();
buyerSale.setBuyerSaleId(dto.getBuyerSaleId());
buyerSale.setBuyerId(dto.getBuyerId());
buyerSale.setBuyingDate(dto.getBuyingDate());
buyerSale.setShift(dto.getShift());
buyerSale.setAmount(dto.getAmount());
buyerSale.setFatValue(dto.getFatValue());
buyerSale.setMilkInLiters(dto.getMilkInLiters());

this.totalMilkSold=this.totalMilkSold.add(buyerSale.getMilkInLiters());

this.buyerSaleIdWiseSaleMap.put(buyerSale.getBuyerSaleId(),buyerSale);
this.buyerIdDateShiftWiseSaleMap.put(buyerSale.getBuyerId()+sdf.format(buyerSale.getBuyingDate())+buyerSale.getShift(),buyerSale);
this.buyerSalesSet.add(buyerSale);
}
}catch(DAOException daoException)
{
// do nothing 
}
}
public boolean buyerIdExists(int buyerId) throws BLException
{
for(BuyerSaleInterface b : this.buyerSalesSet ) if(b.getBuyerId()==buyerId) return true;
return false;
}

public void add(BuyerSaleInterface buyerSale) throws BLException 
{
BLException blException=new BLException();
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

if(buyerSale==null) {
blException.setGenericException("Buyer Sale cannot be null ");
throw blException;
}

int buyerSaleId=buyerSale.getBuyerSaleId();
if(buyerSaleId!=0) blException.addException("buyerSaleId","Buyer Sale id  is auto generated ");

int buyerId=buyerSale.getBuyerId();
if(buyerId<=0)  blException.addException("buyerId","Invalid buyer id : "+buyerId);

Date buyingDate=buyerSale.getBuyingDate();
if(buyingDate==null) blException.addException("buyingDate","Invalid date : "+sdf.format(buyingDate));

String shift=buyerSale.getShift();
if(shift==null) blException.addException("shift","Shift required am or pm : ");
shift=shift.trim();
if(shift.length()==0) blException.addException("shift","Shift required am or pm : ");


if(this.buyerIdDateShiftWiseSaleMap.containsKey(buyerId+sdf.format(buyingDate)+shift)) {
blException.setGenericException("Milk is already sold for given time to buyer id ,to re-sale choose  re-sale option");
throw blException;
}

BigDecimal amount=buyerSale.getAmount();
if(amount!=null) blException.addException("amount","Amount is auto generated ");

BigDecimal fatValue=buyerSale.getFatValue();
if(fatValue==null) blException.addException("fatValue","Fat value is required ");



BigDecimal milkInLiters=buyerSale.getMilkInLiters();
if(milkInLiters==null) blException.addException("milkInLiters","Milk in liters value is required ");


if(blException.hasExceptions()) throw blException;

try{
BuyerSaleDAOInterface dao=new BuyerSaleDAO();

BuyerSaleDTOInterface dto=new BuyerSaleDTO();

dto.setBuyerId(buyerId);
dto.setBuyingDate(buyingDate);
dto.setShift(shift);
dto.setFatValue(fatValue);
dto.setMilkInLiters(milkInLiters);

dao.add(dto);

buyerSaleId=dto.getBuyerSaleId();
amount=dto.getAmount().setScale(2,RoundingMode.HALF_UP);
buyerSale.setBuyerSaleId(buyerSaleId);
buyerSale.setAmount(amount);


BuyerSaleInterface buyerSaleDS=new BuyerSale();
buyerSaleDS.setBuyerSaleId(buyerSale.getBuyerSaleId());
buyerSaleDS.setAmount(buyerSale.getAmount());
buyerSaleDS.setBuyerId(buyerSale.getBuyerId());
buyerSaleDS.setBuyingDate(buyerSale.getBuyingDate());
buyerSaleDS.setShift(buyerSale.getShift());
buyerSaleDS.setFatValue(buyerSale.getFatValue());
buyerSaleDS.setMilkInLiters(buyerSale.getMilkInLiters());


this.totalMilkSold=this.totalMilkSold.add(buyerSaleDS.getMilkInLiters());
this.buyerSaleIdWiseSaleMap.put(buyerSaleDS.getBuyerSaleId(),buyerSaleDS);
this.buyerIdDateShiftWiseSaleMap.put(buyerSaleDS.getBuyerId()+sdf.format(buyerSaleDS.getBuyingDate())+buyerSaleDS.getShift(),buyerSaleDS);
this.buyerSalesSet.add(buyerSaleDS);


}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}


public void reSale(BuyerSaleInterface buyerSale) throws BLException
{
BLException blException=new BLException();
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

if(buyerSale==null) {
blException.setGenericException("Milk Collection cannot be null ");
throw blException;
}

int buyerSaleId=buyerSale.getBuyerSaleId();
if(buyerSaleId!=0) blException.addException("buyerSaleId","Collection id  should be zero ");

int buyerId=buyerSale.getBuyerId();
if(buyerId<=0)  blException.addException("buyerId","Invalid farmer id : "+buyerId);

Date buyingDate=buyerSale.getBuyingDate();
if(buyingDate==null) blException.addException("buyingDate","Collection date is required  : ");

String shift=buyerSale.getShift();
if(shift==null) blException.addException("shift","Shift required am or pm : ");
shift=shift.trim();
if(shift.length()==0) blException.addException("shift","Shift required am or pm : ");




BigDecimal amount=buyerSale.getAmount();
if(amount!=null) blException.addException("amount","Amount is auto generated ");

BigDecimal fatValue=buyerSale.getFatValue();
if(fatValue==null) blException.addException("fatValue","Fat value is required ");




BigDecimal milkInLiters=buyerSale.getMilkInLiters();
if(milkInLiters==null) blException.addException("milkInLiters","Milk in liters value is required ");


if(blException.hasExceptions()) throw blException;

try{
BuyerSaleDAOInterface dao=new BuyerSaleDAO();

BuyerSaleDTOInterface dto=new BuyerSaleDTO();

dto.setBuyerSaleId(buyerSaleId);
dto.setBuyerId(buyerId);
dto.setBuyingDate(buyingDate);
dto.setShift(shift);
dto.setFatValue(fatValue);
dto.setMilkInLiters(milkInLiters);

dao.reSale(dto);

buyerSaleId=dto.getBuyerSaleId();


amount=dto.getAmount();
buyerSale.setAmount(amount);
buyerSale.setBuyerSaleId(buyerSaleId);


// below code is for updating in all data structures
BuyerSaleInterface dsBuyerSale=this.buyerSaleIdWiseSaleMap.get(buyerSaleId);

this.totalMilkSold=this.totalMilkSold.subtract(dsBuyerSale.getMilkInLiters());


this.totalMilkSold=this.totalMilkSold.add(milkInLiters);


dsBuyerSale.setBuyerSaleId(buyerSaleId);
dsBuyerSale.setBuyerId(buyerId);
dsBuyerSale.setBuyingDate(buyingDate);
dsBuyerSale.setShift(shift);
dsBuyerSale.setFatValue(fatValue);
dsBuyerSale.setMilkInLiters(milkInLiters);
dsBuyerSale.setAmount(amount);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}




public Set<BuyerSaleInterface> getByBuyerIdAndDate(int buyerId,java.util.Date date) throws BLException
{
BLException blException=new BLException();

SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");


if(buyerId==0) blException.addException("buyerId","Farmer id  required  ");
if(buyerId<0) blException.addException("buyerId","Invalid buyer  id : "+buyerId);
if(date==null) blException.addException("collectionDate","Collection date is required ");



if(blException.hasExceptions()) throw blException;

String shift="";

if(!(this.buyerIdDateShiftWiseSaleMap.containsKey(buyerId+sdf.format(date)+"am") || this.buyerIdDateShiftWiseSaleMap.containsKey(buyerId+sdf.format(date)+"pm")))  {
blException.setGenericException("No record exists");
throw blException;
}



Set<BuyerSaleInterface> sales=new HashSet<>();


BuyerSaleInterface buyerSale=null;
buyerSale=this.buyerIdDateShiftWiseSaleMap.get(buyerId+sdf.format(date)+"am");




BuyerSaleInterface buyerSaleForDS;

if(buyerSale!=null){
buyerSaleForDS=new BuyerSale();
buyerSaleForDS.setBuyerSaleId(buyerSale.getBuyerSaleId());
buyerSaleForDS.setBuyerId(buyerSale.getBuyerId());
buyerSaleForDS.setBuyingDate(buyerSale.getBuyingDate());
buyerSaleForDS.setShift(buyerSale.getShift());
buyerSaleForDS.setAmount(buyerSale.getAmount());
buyerSaleForDS.setFatValue(buyerSale.getFatValue());
buyerSaleForDS.setMilkInLiters(buyerSale.getMilkInLiters());
sales.add(buyerSaleForDS);
buyerSale=null;
}


buyerSale=this.buyerIdDateShiftWiseSaleMap.get(buyerId+sdf.format(date)+"pm");


if(buyerSale!=null){
buyerSaleForDS=new BuyerSale();
buyerSaleForDS.setBuyerSaleId(buyerSale.getBuyerSaleId());
buyerSaleForDS.setBuyerId(buyerSale.getBuyerId());
buyerSaleForDS.setBuyingDate(buyerSale.getBuyingDate());
buyerSaleForDS.setShift(buyerSale.getShift());
buyerSaleForDS.setAmount(buyerSale.getAmount());
buyerSaleForDS.setFatValue(buyerSale.getFatValue());
buyerSaleForDS.setMilkInLiters(buyerSale.getMilkInLiters());
sales.add(buyerSaleForDS);
}

return sales;


}
public Set<BuyerSaleInterface> getAllByBuyerId(int buyerId) throws BLException
{
BuyerSaleInterface buyerSale;
Set<BuyerSaleInterface> sales=new TreeSet<>();
//System.out.printl(this.buyerSaleSet.size());
for(BuyerSaleInterface bs : this.buyerSalesSet)
{
if(bs.getBuyerId()==buyerId){
buyerSale=new BuyerSale();
buyerSale.setBuyerSaleId(bs.getBuyerSaleId());
buyerSale.setBuyerId(bs.getBuyerId());
buyerSale.setBuyingDate(bs.getBuyingDate());
buyerSale.setShift(bs.getShift());
buyerSale.setAmount(bs.getAmount());
buyerSale.setFatValue(bs.getFatValue());
buyerSale.setMilkInLiters(bs.getMilkInLiters());
sales.add(buyerSale);
}else{
continue;
}
}
return sales;
}

public BigDecimal getTotalMilkSoldInLiters(Date startDate,Date endDate) throws BLException
{
try{
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
BLException blException=new BLException();
BigDecimal milkInLiters=BigDecimal.ZERO;
startDate=sdf.parse(sdf.format(startDate));
endDate=sdf.parse(sdf.format(endDate));
for(BuyerSaleInterface bs : this.buyerSalesSet)
{
Date date=bs.getBuyingDate();
date=sdf.parse(sdf.format(date));
if((date.compareTo(startDate)>=0) &&  (date.compareTo(endDate)<=0)) milkInLiters=milkInLiters.add(bs.getMilkInLiters());
} 
return milkInLiters;
}catch(Exception exception)
{
throw new BLException(exception.getMessage());
}
}



public BigDecimal getAmountByDateAndBuyerId(int buyerId,java.util.Date startDate,java.util.Date endDate) throws BLException
{
try{
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
BLException blException=new BLException();
BigDecimal amount=BigDecimal.ZERO;
startDate=sdf.parse(sdf.format(startDate));
endDate=sdf.parse(sdf.format(endDate));
for(BuyerSaleInterface bs : this.buyerSalesSet)
{
Date date=bs.getBuyingDate();
date=sdf.parse(sdf.format(date));
if((date.compareTo(startDate)>=0) &&  (date.compareTo(endDate)<=0) && bs.getBuyerId()==buyerId)amount=amount.add(bs.getAmount());
} 
return amount;
}catch(Exception exception)
{
throw new BLException(exception.getMessage());
}
}

public BigDecimal getAmountByDate(java.util.Date startDate,java.util.Date endDate) throws BLException
{
try
{
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
BLException blException=new BLException();
BigDecimal amount=BigDecimal.ZERO;
startDate=sdf.parse(sdf.format(startDate));
endDate=sdf.parse(sdf.format(endDate));
for(BuyerSaleInterface bs : this.buyerSalesSet)
{
Date date=bs.getBuyingDate();
date=sdf.parse(sdf.format(date));
if((date.compareTo(startDate)>=0) &&  (date.compareTo(endDate)<=0))amount=amount.add(bs.getAmount());
} 
return amount;
}catch(Exception exception)
{
throw new BLException(exception.getMessage());
}
}


public void update(BuyerSaleInterface buyerSale) throws BLException 
{
throw new BLException("Not yet implemented ");
}
public void delete(int buyerSaleId) throws BLException 
{
throw new BLException("Not yet implemented ");
}
}