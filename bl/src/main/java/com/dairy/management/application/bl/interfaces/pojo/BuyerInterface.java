package com.dairy.management.application.bl.interfaces.pojo;
import java.util.*;
import java.math.*;
public interface BuyerInterface extends java.io.Serializable , java.lang.Comparable<BuyerInterface>
{
public void setBuyerId(int buyerId);
public int getBuyerId();
public void  setName(String name);
public String getName();
public void setAddress(String address);
public String getAddress();
public void setPhoneNumber(String phoneNumber);
public String getPhoneNumber();
public void setType(String type);
public String getType();
}