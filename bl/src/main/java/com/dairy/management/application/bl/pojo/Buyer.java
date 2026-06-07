package com.dairy.management.application.bl.pojo;
import com.dairy.management.application.bl.interfaces.pojo.*;
import java.util.*;
import java.math.*;
public class Buyer implements BuyerInterface
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
public boolean equals(Object other)
{
if(!(other instanceof BuyerInterface)) return false;
BuyerInterface buyer=(BuyerInterface)other;
return this.buyerId==buyer.getBuyerId();
}
public int compareTo(BuyerInterface buyer)
{
return this.buyerId-buyer.getBuyerId();
}
public int hashCode()
{
return Integer.hashCode(this.buyerId);
}
}