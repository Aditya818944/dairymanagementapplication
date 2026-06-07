package com.dairy.management.application.bl.interfaces.pojo;
import java.util.*;
import java.math.*;
public interface FarmerInterface extends  java.io.Serializable , java.lang.Comparable<FarmerInterface>
{
public void setFarmerId(int farmerId);
public int getFarmerId();
public void  setName(String name);
public String getName();
public void setAddress(String address);
public String getAddress();
public void setPhoneNumber(String phoneNumber);
public String getPhoneNumber();
public void setType(String type);
public String getType();
}