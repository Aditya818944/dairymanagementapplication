package 	com.dairy.management.application.bl.managers;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.interfaces.managers.*;
import com.dairy.management.application.bl.pojo.*;
import com.dairy.management.application.dl.interfaces.dto.*;
import com.dairy.management.application.dl.interfaces.dao.*;
import com.dairy.management.application.dl.dto.*;
import com.dairy.management.application.dl.dao.*;
import com.dairy.management.application.dl.exceptions.*;
import com.dairy.management.application.bl.exceptions.*;
import java.util.*;

public class BuyerManager implements BuyerManagerInterface 
{
private Map<Integer,BuyerInterface> buyerIdWiseBuyersMap;
private Map<String,BuyerInterface> phoneNumberWiseBuyersMap;
private Set<BuyerInterface> buyersSet;
private static BuyerManagerInterface buyerManager=null;
private BuyerManager() throws BLException
{
populateDataStructures();
}
public static BuyerManagerInterface getBuyerManager() throws BLException
{
if(buyerManager==null) buyerManager=new BuyerManager();
return buyerManager;
}
public void populateDataStructures() throws BLException
{
BuyerDAOInterface dao=new BuyerDAO();
buyerIdWiseBuyersMap=new HashMap<>();
phoneNumberWiseBuyersMap=new HashMap<>();
buyersSet=new HashSet<>();
try
{
List<BuyerDTOInterface> buyers=dao.getAllBuyers();
BuyerInterface buyer;
for(BuyerDTOInterface dto : buyers)
{
buyer=new Buyer();
buyer.setBuyerId(dto.getBuyerId());
buyer.setName(dto.getName());
buyer.setPhoneNumber(dto.getPhoneNumber());
buyer.setAddress(dto.getAddress());
buyer.setType(dto.getType());
buyerIdWiseBuyersMap.put(buyer.getBuyerId(),buyer);
phoneNumberWiseBuyersMap.put(buyer.getPhoneNumber(),buyer);
buyersSet.add(buyer);
}
}catch(DAOException daoException)
{
/**** we will think about it later *******
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
*********************************/
}
}
public void add(BuyerInterface buyer) throws BLException 
{
BLException blException=new BLException();

FarmerManagerInterface farmerManager=FarmerManager.getFarmerManager();

if(buyer==null){
blException.setGenericException("buyer value is null ");
throw blException;
}

int buyerId=buyer.getBuyerId();
if(buyerId!=0) blException.addException("buyerId","Buyer id should be zero (it is auto generated ) ");


String name=buyer.getName();
if(name==null)  blException.addException("name","name should not be null ");
name=name.trim();
if(name.length()==0) blException.addException("name","name required ");


String phoneNumber=buyer.getPhoneNumber();
if(phoneNumber==null)  blException.addException("phoneNumber","phone number is required");
phoneNumber=phoneNumber.trim();
if(phoneNumber.length()==0) blException.addException("phoneNumber","phone number is required ");

if(farmerManager.phoneNumberExists(phoneNumber)) blException.addException("phoneNumber","phone number is already exists "+phoneNumber);
else if(this.phoneNumberExists(phoneNumber)) blException.addException("phoneNumber","phone number is already exists "+phoneNumber);

String type=buyer.getType();
if(type==null)  blException.addException("type","type should not be null ");
type=type.trim();
if(type.length()==0) blException.addException("type","type required ");


for(int i=0;i<phoneNumber.length();i++) 
{
char c=phoneNumber.charAt(i);
if(!(c=='1' || c=='2' || c=='3' || c=='4' || c=='5' || c=='6' || c=='7' || c=='8' || c=='9' || c=='0')) {
blException.addException("phoneNumber","phone number should only contain digit ");
break;
}
}

String address=buyer.getAddress();
if(address==null) blException.addException("address","Address required ");
address=address.trim();
if(address.length()==0) blException.addException("address","Address required ");



if(blException.hasExceptions()) throw blException;

try
{
BuyerDTOInterface dto=new BuyerDTO();
dto.setName(name);
dto.setPhoneNumber(phoneNumber);
dto.setAddress(address);
dto.setType(type);
BuyerDAOInterface dao=new BuyerDAO();
dao.add(dto);
buyer.setBuyerId(dto.getBuyerId());

BuyerInterface dsBuyer=new Buyer();
dsBuyer.setBuyerId(buyer.getBuyerId());
dsBuyer.setName(buyer.getName());
dsBuyer.setPhoneNumber(buyer.getPhoneNumber());
dsBuyer.setAddress(buyer.getAddress());
dsBuyer.setType(buyer.getType());
buyerIdWiseBuyersMap.put(dsBuyer.getBuyerId(),dsBuyer);
phoneNumberWiseBuyersMap.put(dsBuyer.getPhoneNumber(),dsBuyer);
buyersSet.add(dsBuyer);

}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void update(BuyerInterface buyer) throws BLException
{
BLException blException=new BLException();
FarmerManagerInterface farmerManager=FarmerManager.getFarmerManager();

if(buyer==null){
blException.setGenericException("buyer value is null ");
throw blException;
}

int buyerId=buyer.getBuyerId();
if(buyerId==0) blException.addException("buyerId","Buyer id should not be zero ");

if(this.buyerIdWiseBuyersMap.containsKey(buyerId)==false) blException.addException("buyerId","Invalid buyer id : "+buyerId);


String name=buyer.getName();
if(name==null)  blException.addException("name","name should not be null ");
name=name.trim();
if(name.length()==0) blException.addException("name","name required ");


String phoneNumber=buyer.getPhoneNumber();


if(phoneNumber==null)  blException.addException("phoneNumber","phone number is required");
phoneNumber=phoneNumber.trim();
if(phoneNumber.length()==0) blException.addException("phoneNumber","phone number is required ");

if(farmerManager.phoneNumberExists(phoneNumber)) blException.addException("phoneNumber","phone number is already exists "+phoneNumber);



for(int i=0;i<phoneNumber.length();i++) 
{
char c=phoneNumber.charAt(i);
if(!(c=='1' || c=='2' || c=='3' || c=='4' || c=='5' || c=='6' || c=='7' || c=='8' || c=='9' || c=='0')) {
blException.addException("phoneNumber","phone number should only contain digit ");
break;
}
}


if(this.phoneNumberWiseBuyersMap.containsKey(phoneNumber)){
BuyerInterface b=this.phoneNumberWiseBuyersMap.get(phoneNumber);
if(b.getBuyerId()!=buyerId) blException.addException("phoneNumber", "Given phone number already exists : "+phoneNumber);
}


String address=buyer.getAddress();
if(address==null) blException.addException("address","Address required ");
address=address.trim();
if(address.length()==0) blException.addException("address","Address required ");


String type=buyer.getType();
if(type==null)  blException.addException("type","type should not be null ");
type=type.trim();
if(type.length()==0) blException.addException("type","type required ");



if(blException.hasExceptions()) throw blException;

try
{
BuyerDTOInterface dto=new BuyerDTO();
dto.setBuyerId(buyerId);
dto.setName(name);
dto.setPhoneNumber(phoneNumber);
dto.setAddress(address);
dto.setType(type);
BuyerDAOInterface dao=new BuyerDAO();
dao.update(dto);


// removing old one from map and set 
BuyerInterface br=this.buyerIdWiseBuyersMap.get(buyerId);
this.buyerIdWiseBuyersMap.remove(br.getBuyerId());
this.phoneNumberWiseBuyersMap.remove(br.getPhoneNumber());
this.buyersSet.remove(br);


BuyerInterface dsBuyer=new Buyer();
dsBuyer.setBuyerId(buyer.getBuyerId());
dsBuyer.setName(buyer.getName());
dsBuyer.setPhoneNumber(buyer.getPhoneNumber());
dsBuyer.setAddress(buyer.getAddress());
dsBuyer.setType(buyer.getType());



this.buyerIdWiseBuyersMap.put(dsBuyer.getBuyerId(),dsBuyer);
this.phoneNumberWiseBuyersMap.put(dsBuyer.getPhoneNumber(),dsBuyer);
this.buyersSet.add(dsBuyer);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}
public void delete(int buyerId) throws BLException 
{
BLException blException=new BLException();



if(buyerId==0 ||  this.buyerIdWiseBuyersMap.containsKey(buyerId)==false){
 blException.setGenericException("Invalid buyer id "+buyerId);
throw blException;
}

BuyerInterface buyer=this.buyerIdWiseBuyersMap.get(buyerId);
String phoneNumber=buyer.getPhoneNumber();

if(blException.hasExceptions()) throw blException;

try
{
BuyerDAOInterface dao=new BuyerDAO();

//removing  from database 
dao.delete(buyerId);

// removing from map and set 
this.buyerIdWiseBuyersMap.remove(buyer.getBuyerId());
this.phoneNumberWiseBuyersMap.remove(buyer.getPhoneNumber());
this.buyersSet.remove(buyer);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}

public boolean buyerIdExists(int buyerId)
{
return this.buyerIdWiseBuyersMap.containsKey(buyerId);
}
public boolean phoneNumberExists(String phoneNumber)
{
return this.phoneNumberWiseBuyersMap.containsKey(phoneNumber);
}
public BuyerInterface getBuyerById(int buyerId)
{
BuyerInterface buyerOfDs=null;
if(this.buyerIdWiseBuyersMap.containsKey(buyerId)==false) return buyerOfDs;
buyerOfDs=this.buyerIdWiseBuyersMap.get(buyerId);
BuyerInterface buyer=new Buyer();
buyer.setBuyerId(buyerOfDs.getBuyerId());
buyer.setName(buyerOfDs.getName());
buyer.setAddress(buyerOfDs.getAddress());
buyer.setPhoneNumber(buyerOfDs.getPhoneNumber());
buyer.setType(buyerOfDs.getType());
return buyer;
}

public Set<BuyerInterface> getAll() throws BLException
{
Set<BuyerInterface> buyers=new HashSet<>();
if(this.buyersSet.size()==0) return buyers;

BuyerInterface b;

for(BuyerInterface buyer : this.buyersSet)
{
b=new Buyer();
b.setBuyerId(buyer.getBuyerId());
b.setName(buyer.getName());
b.setAddress(buyer.getAddress());
b.setPhoneNumber(buyer.getPhoneNumber());
b.setType(buyer.getType());
buyers.add(b);
}
return buyers;
}



}