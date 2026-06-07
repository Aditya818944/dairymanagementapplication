package com.dairy.management.application.dl.dto;
import com.dairy.management.application.dl.interfaces.dto.*;

import java.math.*;
import java.util.*;
public class FarmerDTO implements FarmerDTOInterface
{
private int farmerId;
private String name;
private String address;
private String phoneNumber;
private String type;
public void setFarmerId(int farmerId)
{
this.farmerId=farmerId;
}
public int getFarmerId()
{
return this.farmerId;
}
public void setName(java.lang.String name)
{
this.name=name;
}
public java.lang.String getName()
{
return this.name;
}
public void setAddress(java.lang.String address)
{
this.address=address;
}
public java.lang.String getAddress()
{
return this.address;
}
public void setPhoneNumber(java.lang.String phoneNumber)
{
this.phoneNumber=phoneNumber;
}
public java.lang.String getPhoneNumber()
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