package 	com.dairy.management.application.bl.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.interfaces.managers.*;
import com.dairy.management.application.bl.managers.*;
import com.dairy.management.application.bl.pojo.*;
import com.dairy.management.application.bl.exceptions.*;
import com.dairy.management.application.dl.exceptions.*;
import com.dairy.management.application.dl.interfaces.dao.*;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.dao.*;
import com.dairy.management.application.dl.dto.*;
import java.text.*;
import java.util.*;
import java.math.*;
public class MilkCollectionManager implements  MilkCollectionManagerInterface  
{
private Map<Integer,MilkCollectionInterface> collectionIdWiseMilkCollectionMap;
private Map<String,MilkCollectionInterface> farmerIdDateShiftWiseCollectionMap;
private Set<MilkCollectionInterface> milkCollectionsSet;
private BigDecimal totalMilkCollected;

private static MilkCollectionManagerInterface manager;
private MilkCollectionManager() throws BLException
{
populateDataStructures();
}
public static MilkCollectionManagerInterface getMilkCollectionManager() throws BLException
{
if(manager==null)  manager=new MilkCollectionManager();
return manager;
}
public void populateDataStructures() throws BLException
{
this.totalMilkCollected=BigDecimal.ZERO;
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
collectionIdWiseMilkCollectionMap=new HashMap<>();
farmerIdDateShiftWiseCollectionMap=new HashMap<>();
milkCollectionsSet=new TreeSet<>();
try
{
MilkCollectionDAOInterface dao=new MilkCollectionDAO();
MilkCollectionInterface milkCollection;
for(MilkCollectionDTOInterface dto : dao.getAll())
{
milkCollection=new MilkCollection();
milkCollection.setCollectionId(dto.getCollectionId());
milkCollection.setFarmerId(dto.getFarmerId());
milkCollection.setCollectionDate(dto.getCollectionDate());
milkCollection.setShift(dto.getShift());
milkCollection.setAmount(dto.getAmount());
milkCollection.setFatValue(dto.getFatValue());
milkCollection.setMilkInLiters(dto.getMilkInLiters());

this.totalMilkCollected=this.totalMilkCollected.add(milkCollection.getMilkInLiters());


this.collectionIdWiseMilkCollectionMap.put(milkCollection.getCollectionId(),milkCollection);
this.farmerIdDateShiftWiseCollectionMap.put(milkCollection.getFarmerId()+sdf.format(milkCollection.getCollectionDate())+milkCollection.getShift(),milkCollection);
this.milkCollectionsSet.add(milkCollection);
}
}catch(DAOException daoException)
{
// do nothing 
}
}

public boolean farmerIdExists(int farmerId) throws BLException 
{
for(MilkCollectionInterface m : this.milkCollectionsSet ) if(m.getFarmerId()==farmerId) return true;
return false;
}
public void add(MilkCollectionInterface milkCollection) throws BLException
{
BLException blException=new BLException();
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

if(milkCollection==null) {
blException.setGenericException("Milk Collection cannot be null ");
throw blException;
}

int collectionId=milkCollection.getCollectionId();
if(collectionId!=0) blException.addException("collectionId","Collection id  is auto generated ");

int farmerId=milkCollection.getFarmerId();
if(farmerId<=0)  blException.addException("farmerId","Invalid farmer id : "+farmerId);

Date collectionDate=milkCollection.getCollectionDate();
if(collectionDate==null) blException.addException("collectionDate","Invalid date : "+sdf.format(collectionDate));

String shift=milkCollection.getShift();
if(shift==null) blException.addException("shift","Shift required am or pm : ");
shift=shift.trim();
if(shift.length()==0) blException.addException("shift","Shift required am or pm : ");


if(this.farmerIdDateShiftWiseCollectionMap.containsKey(farmerId+sdf.format(collectionDate)+shift)) {
blException.setGenericException("Milk is already collected for given time and farmer id ,to recollect choose  recollect option");
throw blException;
}

BigDecimal amount=milkCollection.getAmount();
if(amount!=null) blException.addException("amount","Amount is auto generated ");

BigDecimal fatValue=milkCollection.getFatValue();
if(fatValue==null) blException.addException("fatValue","Fat value is required ");



BigDecimal milkInLiters=milkCollection.getMilkInLiters();
if(milkInLiters==null) blException.addException("milkInLiters","Milk in liters value is  required ");


if(blException.hasExceptions()) throw blException;

try{
MilkCollectionDAOInterface dao=new MilkCollectionDAO();

MilkCollectionDTOInterface dto=new MilkCollectionDTO();

dto.setFarmerId(farmerId);
dto.setCollectionDate(collectionDate);
dto.setShift(shift);
dto.setFatValue(fatValue);
dto.setMilkInLiters(milkInLiters);

dao.add(dto);

collectionId=dto.getCollectionId();
amount=dto.getAmount().setScale(2,RoundingMode.HALF_UP);
milkCollection.setCollectionId(collectionId);
milkCollection.setAmount(amount);



this.totalMilkCollected=this.totalMilkCollected.add(milkCollection.getMilkInLiters());

this.collectionIdWiseMilkCollectionMap.put(milkCollection.getCollectionId(),milkCollection);
this.farmerIdDateShiftWiseCollectionMap.put(milkCollection.getFarmerId()+sdf.format(milkCollection.getCollectionDate())+milkCollection.getShift(),milkCollection);
this.milkCollectionsSet.add(milkCollection);




}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}


public void recollect(MilkCollectionInterface milkCollection) throws BLException
{
BLException blException=new BLException();
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

if(milkCollection==null) {
blException.setGenericException("Milk Collection cannot be null ");
throw blException;
}

int collectionId=milkCollection.getCollectionId();
if(collectionId!=0) blException.addException("collectionId","Collection id  should be zero ");

int farmerId=milkCollection.getFarmerId();
if(farmerId<=0)  blException.addException("farmerId","Invalid farmer id : "+farmerId);

Date collectionDate=milkCollection.getCollectionDate();
if(collectionDate==null) blException.addException("collectionDate","Collection date is required  : ");

String shift=milkCollection.getShift();
if(shift==null) blException.addException("shift","Shift required am or pm : ");
shift=shift.trim();
if(shift.length()==0) blException.addException("shift","Shift required am or pm : ");




BigDecimal amount=milkCollection.getAmount();
if(amount!=null) blException.addException("amount","Amount is auto generated ");

BigDecimal fatValue=milkCollection.getFatValue();
if(fatValue==null) blException.addException("fatValue","Fat value is required ");



BigDecimal milkInLiters=milkCollection.getMilkInLiters();
if(milkInLiters==null) blException.addException("milkInLiters","Milk in liters value is required ");



if(blException.hasExceptions()) throw blException;

try{
MilkCollectionDAOInterface dao=new MilkCollectionDAO();

MilkCollectionDTOInterface dto=new MilkCollectionDTO();

dto.setCollectionId(collectionId);
dto.setFarmerId(farmerId);
dto.setCollectionDate(collectionDate);
dto.setShift(shift);
dto.setFatValue(fatValue);
dto.setMilkInLiters(milkInLiters);

dao.recollect(dto);

collectionId=dto.getCollectionId();


amount=dto.getAmount();
milkCollection.setAmount(amount);
milkCollection.setCollectionId(collectionId);
// below code is for updating in all data structures
MilkCollectionInterface dsMilkCollection=this.collectionIdWiseMilkCollectionMap.get(collectionId);

this.totalMilkCollected=this.totalMilkCollected.subtract(dsMilkCollection.getMilkInLiters());
this.totalMilkCollected=this.totalMilkCollected.add(milkInLiters);


dsMilkCollection.setCollectionId(collectionId);
dsMilkCollection.setFarmerId(farmerId);
dsMilkCollection.setCollectionDate(collectionDate);
dsMilkCollection.setShift(shift);
dsMilkCollection.setFatValue(fatValue);
dsMilkCollection.setMilkInLiters(milkInLiters);
dsMilkCollection.setAmount(amount);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}




public Set<MilkCollectionInterface> getByFarmerIdAndDate(int farmerId,java.util.Date date) throws BLException
{
BLException blException=new BLException();

SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");


if(farmerId==0) blException.addException("farmerId","Farmer id  required  ");
if(farmerId<0) blException.addException("farmerId","Invalid farmer id : "+farmerId);
if(date==null) blException.addException("collectionDate","Collection date is required ");



if(blException.hasExceptions()) throw blException;

String shift="";

if(!(this.farmerIdDateShiftWiseCollectionMap.containsKey(farmerId+sdf.format(date)+"AM") || this.farmerIdDateShiftWiseCollectionMap.containsKey(farmerId+sdf.format(date)+"PM")))  {
blException.setGenericException("No record exists");
throw blException;
}



Set<MilkCollectionInterface> collections=new HashSet<>();


MilkCollectionInterface milkCollection=null;
milkCollection=this.farmerIdDateShiftWiseCollectionMap.get(farmerId+sdf.format(date)+"AM");




MilkCollectionInterface milkCollectionForDS;

if(milkCollection!=null){
milkCollectionForDS=new MilkCollection();
milkCollectionForDS.setCollectionId(milkCollection.getCollectionId());
milkCollectionForDS.setFarmerId(milkCollection.getFarmerId());
milkCollectionForDS.setCollectionDate(milkCollection.getCollectionDate());
milkCollectionForDS.setShift(milkCollection.getShift());
milkCollectionForDS.setAmount(milkCollection.getAmount());
milkCollectionForDS.setFatValue(milkCollection.getFatValue());
milkCollectionForDS.setMilkInLiters(milkCollection.getMilkInLiters());
collections.add(milkCollectionForDS);
milkCollection=null;
}


milkCollection=this.farmerIdDateShiftWiseCollectionMap.get(farmerId+sdf.format(date)+"PM");


if(milkCollection!=null){
milkCollectionForDS=new MilkCollection();
milkCollectionForDS.setCollectionId(milkCollection.getCollectionId());
milkCollectionForDS.setFarmerId(milkCollection.getFarmerId());
milkCollectionForDS.setCollectionDate(milkCollection.getCollectionDate());
milkCollectionForDS.setShift(milkCollection.getShift());
milkCollectionForDS.setAmount(milkCollection.getAmount());
milkCollectionForDS.setFatValue(milkCollection.getFatValue());
milkCollectionForDS.setMilkInLiters(milkCollection.getMilkInLiters());
collections.add(milkCollectionForDS);
}

return collections;
}

public Set<MilkCollectionInterface> getAllByFarmerId(int farmerId)throws BLException
{
MilkCollectionInterface milkCollection;
Set<MilkCollectionInterface> collections=new TreeSet<>();
for(MilkCollectionInterface m : this.milkCollectionsSet)
{
if(m.getFarmerId()==farmerId){
milkCollection=new MilkCollection();
milkCollection.setCollectionId(m.getCollectionId());
milkCollection.setFarmerId(m.getFarmerId());
milkCollection.setCollectionDate(m.getCollectionDate());
milkCollection.setShift(m.getShift());
milkCollection.setAmount(m.getAmount());
milkCollection.setFatValue(m.getFatValue());
milkCollection.setMilkInLiters(m.getMilkInLiters());
collections.add(milkCollection);
}else{
continue;
}
}
return collections;
}

public BigDecimal getTotalMilkCollectedInLiters(Date startDate,Date endDate) throws BLException
{
try{
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
BLException blException=new BLException();
BigDecimal milkInLiters=BigDecimal.ZERO;
startDate=sdf.parse(sdf.format(startDate));
endDate=sdf.parse(sdf.format(endDate));
for(MilkCollectionInterface m : this.milkCollectionsSet)
{
Date date=sdf.parse(sdf.format(m.getCollectionDate()));
if((date.compareTo(startDate)>=0) &&  (date.compareTo(endDate)<=0)){
milkInLiters=milkInLiters.add(m.getMilkInLiters());
}
} 
return milkInLiters;
}catch(Exception exception)
{
throw new BLException(exception.getMessage());
}
}


public BigDecimal getAmountByDateAndFarmerId(int farmerId,java.util.Date startDate,java.util.Date endDate) throws BLException
{
try{
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
BLException blException=new BLException();
BigDecimal amount=BigDecimal.ZERO;
startDate=sdf.parse(sdf.format(startDate));
endDate=sdf.parse(sdf.format(endDate));
for(MilkCollectionInterface m : this.milkCollectionsSet)
{
Date date=sdf.parse(sdf.format(m.getCollectionDate()));
if((date.compareTo(startDate)>=0) &&  (date.compareTo(endDate)<=0) && m.getFarmerId()==farmerId){
amount=amount.add(m.getAmount());
}
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
for(MilkCollectionInterface m : this.milkCollectionsSet)
{
Date date=sdf.parse(sdf.format(m.getCollectionDate()));
if((date.compareTo(startDate)>=0) &&  (date.compareTo(endDate)<=0)) amount=amount.add(m.getAmount());
} 
return amount;
}catch(Exception exception)
{
throw new BLException(exception.getMessage());
}
}


public void update(MilkCollectionInterface milkCollectionDTO) throws BLException 
{
throw new BLException("Not yet implemented ");
}

public void delete(int milkCollectionId) throws BLException
{
throw new BLException("Not yet implemented ");
}
}