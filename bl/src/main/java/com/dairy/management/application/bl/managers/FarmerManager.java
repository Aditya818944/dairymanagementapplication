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

public class FarmerManager implements FarmerManagerInterface 
{
private Map<Integer,FarmerInterface> farmerIdWiseFarmersMap;
private Map<String,FarmerInterface> phoneNumberWiseFarmersMap;
private Set<FarmerInterface> farmersSet;
private static FarmerManagerInterface FarmerManager=null;
private FarmerManager() throws BLException
{
populateDataStructures();
}
public static FarmerManagerInterface getFarmerManager() throws BLException
{
if(FarmerManager==null) FarmerManager=new FarmerManager();
return FarmerManager;
}
public void populateDataStructures() throws BLException
{
FarmerDAOInterface dao=new FarmerDAO();
farmerIdWiseFarmersMap=new HashMap<>();
phoneNumberWiseFarmersMap=new HashMap<>();
farmersSet=new HashSet<>();
try
{
List<FarmerDTOInterface> farmers=dao.getAllFarmers();
FarmerInterface farmer;
for(FarmerDTOInterface dto : farmers)
{
farmer=new Farmer();
farmer.setFarmerId(dto.getFarmerId());
farmer.setName(dto.getName());
farmer.setPhoneNumber(dto.getPhoneNumber());
farmer.setAddress(dto.getAddress());
farmer.setType(dto.getType());
farmerIdWiseFarmersMap.put(farmer.getFarmerId(),farmer);
phoneNumberWiseFarmersMap.put(farmer.getPhoneNumber(),farmer);
farmersSet.add(farmer);
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
public void add(FarmerInterface farmer) throws BLException 
{
BLException blException=new BLException();
BuyerManagerInterface buyerManager=BuyerManager.getBuyerManager();

if(farmer==null){
blException.setGenericException("farmer value is null ");
throw blException;
}

int farmerId=farmer.getFarmerId();
if(farmerId!=0) blException.addException("farmerId","Farmer id should be zero (it is auto generated ) ");


String name=farmer.getName();
if(name==null)  blException.addException("name","name should not be null ");
name=name.trim();
if(name.length()==0) blException.addException("name","name required ");


String phoneNumber=farmer.getPhoneNumber();
if(phoneNumber==null)  blException.addException("phoneNumber","phone number is required");
phoneNumber=phoneNumber.trim();
if(phoneNumber.length()==0) blException.addException("phoneNumber","phone number is required ");

if(buyerManager.phoneNumberExists(phoneNumber)) blException.addException("phoneNumber","phone number is already exists "+phoneNumber);
else if(this.phoneNumberExists(phoneNumber)) blException.addException("phoneNumber","phone number is already exists "+phoneNumber);


for(int i=0;i<phoneNumber.length();i++) 
{
char c=phoneNumber.charAt(i);
if(!(c=='1' || c=='2' || c=='3' || c=='4' || c=='5' || c=='6' || c=='7' || c=='8' || c=='9' || c=='0')) {
blException.addException("phoneNumber","phone number should only contain digit ");
break;
}
}

String address=farmer.getAddress();
if(address==null) blException.addException("address","Address required ");
address=address.trim();
if(address.length()==0) blException.addException("address","Address required ");


String type=farmer.getType();
if(type==null) blException.addException("type","Type required ");
type=type.trim();
if(type.length()==0) blException.addException("type","Type required ");



if(blException.hasExceptions()) throw blException;

try
{
FarmerDTOInterface dto=new FarmerDTO();
dto.setName(name);
dto.setPhoneNumber(phoneNumber);
dto.setAddress(address);
dto.setType(type);
FarmerDAOInterface dao=new FarmerDAO();
dao.add(dto);
farmer.setFarmerId(dto.getFarmerId());

FarmerInterface dsFarmer=new Farmer();
dsFarmer.setFarmerId(farmer.getFarmerId());
dsFarmer.setName(farmer.getName());
dsFarmer.setPhoneNumber(farmer.getPhoneNumber());
dsFarmer.setAddress(farmer.getAddress());
dsFarmer.setType(farmer.getType());

farmerIdWiseFarmersMap.put(dsFarmer.getFarmerId(),dsFarmer);
phoneNumberWiseFarmersMap.put(dsFarmer.getPhoneNumber(),dsFarmer);
farmersSet.add(dsFarmer);

}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void update(FarmerInterface farmer) throws BLException
{
BLException blException=new BLException();
BuyerManagerInterface buyerManager=BuyerManager.getBuyerManager();

if(farmer==null){
blException.setGenericException("farmer value is null ");
throw blException;
}

int farmerId=farmer.getFarmerId();
if(farmerId==0) blException.addException("farmerId","Farmer id should not be zero ");

if(this.farmerIdWiseFarmersMap.containsKey(farmerId)==false) blException.addException("farmerId","Invalid farmer id : "+farmerId);


String name=farmer.getName();
if(name==null)  blException.addException("name","name should not be null ");
name=name.trim();
if(name.length()==0) blException.addException("name","name required ");


String phoneNumber=farmer.getPhoneNumber();


if(phoneNumber==null)  blException.addException("phoneNumber","phone number is required");
phoneNumber=phoneNumber.trim();
if(phoneNumber.length()==0) blException.addException("phoneNumber","phone number is required ");


if(buyerManager.phoneNumberExists(phoneNumber)) blException.addException("phoneNumber","phone number is already exists "+phoneNumber);


for(int i=0;i<phoneNumber.length();i++) 
{
char c=phoneNumber.charAt(i);
if(!(c=='1' || c=='2' || c=='3' || c=='4' || c=='5' || c=='6' || c=='7' || c=='8' || c=='9' || c=='0')) {
blException.addException("phoneNumber","phone number should only contain digit ");
break;
}
}


if(this.phoneNumberWiseFarmersMap.containsKey(phoneNumber)){
FarmerInterface b=this.phoneNumberWiseFarmersMap.get(phoneNumber);
if(b.getFarmerId()!=farmerId) blException.addException("phoneNumber", "Given phone number already exists : "+phoneNumber);
}


String address=farmer.getAddress();
if(address==null) blException.addException("address","Address required ");
address=address.trim();
if(address.length()==0) blException.addException("address","Address required ");


String type=farmer.getType();
if(type==null) blException.addException("type","Type required ");
type=type.trim();
if(type.length()==0) blException.addException("type","Type required ");



if(blException.hasExceptions()) throw blException;

try
{
FarmerDTOInterface dto=new FarmerDTO();
dto.setFarmerId(farmerId);
dto.setName(name);
dto.setPhoneNumber(phoneNumber);
dto.setAddress(address);
dto.setType(type);
FarmerDAOInterface dao=new FarmerDAO();
dao.update(dto);


// removing old one from map and set 
FarmerInterface br=this.farmerIdWiseFarmersMap.get(farmerId);
this.farmerIdWiseFarmersMap.remove(br.getFarmerId());
this.phoneNumberWiseFarmersMap.remove(br.getPhoneNumber());
this.farmersSet.remove(br);


FarmerInterface dsFarmer=new Farmer();
dsFarmer.setFarmerId(farmer.getFarmerId());
dsFarmer.setName(farmer.getName());
dsFarmer.setPhoneNumber(farmer.getPhoneNumber());
dsFarmer.setAddress(farmer.getAddress());
dsFarmer.setType(type);



this.farmerIdWiseFarmersMap.put(dsFarmer.getFarmerId(),dsFarmer);
this.phoneNumberWiseFarmersMap.put(dsFarmer.getPhoneNumber(),dsFarmer);
this.farmersSet.add(dsFarmer);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}
public void delete(int farmerId) throws BLException 
{
BLException blException=new BLException();



if(farmerId==0 ||  this.farmerIdWiseFarmersMap.containsKey(farmerId)==false){
 blException.setGenericException("Invalid farmer id "+farmerId);
throw blException;
}

FarmerInterface farmer=this.farmerIdWiseFarmersMap.get(farmerId);
String phoneNumber=farmer.getPhoneNumber();

if(blException.hasExceptions()) throw blException;

try
{
FarmerDAOInterface dao=new FarmerDAO();

//removing  from database 
dao.delete(farmerId);

// removing from map and set 
this.farmerIdWiseFarmersMap.remove(farmer.getFarmerId());
this.phoneNumberWiseFarmersMap.remove(farmer.getPhoneNumber());
this.farmersSet.remove(farmer);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}

public boolean farmerIdExists(int farmerId)
{
return this.farmerIdWiseFarmersMap.containsKey(farmerId);
}

public boolean phoneNumberExists(String phoneNumber)
{
return this.phoneNumberWiseFarmersMap.containsKey(phoneNumber);
}

public FarmerInterface getFarmerById(int farmerId) throws BLException
{
BLException blException=new BLException();
FarmerInterface farmerOfDs=null;
if(this.farmerIdWiseFarmersMap.containsKey(farmerId)==false) {
blException.setGenericException("No such farmer id exists : "+farmerId);
throw blException;
}
farmerOfDs=this.farmerIdWiseFarmersMap.get(farmerId);
FarmerInterface farmer=new Farmer();
farmer.setFarmerId(farmerOfDs.getFarmerId());
farmer.setName(farmerOfDs.getName());
farmer.setAddress(farmerOfDs.getAddress());
farmer.setPhoneNumber(farmerOfDs.getPhoneNumber());
farmer.setType(farmerOfDs.getType());
return farmer;
}

public Set<FarmerInterface> getAll() throws BLException
{
Set<FarmerInterface> farmers=new HashSet<>();
if(this.farmersSet.size()==0) return farmers;

FarmerInterface b;

for(FarmerInterface farmer : this.farmersSet)
{
b=new Farmer();
b.setFarmerId(farmer.getFarmerId());
b.setName(farmer.getName());
b.setAddress(farmer.getAddress());
b.setPhoneNumber(farmer.getPhoneNumber());
b.setType(farmer.getType());
farmers.add(b);
}
return farmers;
}



}