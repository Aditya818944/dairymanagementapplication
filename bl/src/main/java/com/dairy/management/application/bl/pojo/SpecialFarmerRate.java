package com.dairy.management.application.bl.pojo;
import com.dairy.management.application.bl.interfaces.pojo.*;
import java.util.*;
import java.math.*;

public class SpecialFarmerRate implements SpecialFarmerRateInterface
{
private int rateId;
private int farmerId;
private BigDecimal ratePerFat;
private Date startDate;
private Date endDate;
public void setRateId(int rateId)
{
this.rateId=rateId;
}
public int getRateId()
{
return this.rateId;
}
public void setFarmerId(int farmerId)
{
this.farmerId=farmerId;
}
public int getFarmerId()
{
return this.farmerId;
}
public void setRatePerFat(java.math.BigDecimal ratePerFat)
{
this.ratePerFat=ratePerFat;
}
public java.math.BigDecimal getRatePerFat()
{
return this.ratePerFat;
}
public void setStartDate(java.util.Date startDate)
{
this.startDate=startDate;
}
public java.util.Date getStartDate()
{
return this.startDate;
}
public void setEndDate(java.util.Date endDate)
{
this.endDate=endDate;
}
public java.util.Date getEndDate()
{
return this.endDate;
}

public boolean equals(Object other)
{
if(!(other instanceof SpecialFarmerRateInterface)) return false;
SpecialFarmerRateInterface farmerRate=(SpecialFarmerRateInterface)other;
return this.rateId==farmerRate.getRateId();
}
public int compareTo(SpecialFarmerRateInterface farmerRate)
{
return this.rateId-farmerRate.getRateId();
}

public int hashCode()
{
return Integer.hashCode(this.rateId);
}
}