package com.dairy.management.application.dl.dto;
import com.dairy.management.application.dl.interfaces.dto.*;
import java.util.*;
import java.math.*;
public class BuyerDTO implements BuyerDTOInterface
{
private int buyerId;
private String name;
private String address;
private String phoneNumber;
private String type;
public void setBuyerId(int buyerId)
{
this.buyerId=buyerId;
}
public int getBuyerId()
{
return this.buyerId;
}
public void  setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setAddress(String address)
{
this.address=address;
}
public String getAddress()
{
return this.address;
}
public void setPhoneNumber(String phoneNumber)
{
this.phoneNumber=phoneNumber;
}
public String getPhoneNumber()
{
return this.phoneNumber;
}
public void setType(String type)
{
this.type=type;
}
public String getType()
{
return this.type;
}
}