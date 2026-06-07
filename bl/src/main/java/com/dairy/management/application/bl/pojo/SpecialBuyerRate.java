package com.dairy.management.application.bl.pojo;
import com.dairy.management.application.bl.interfaces.pojo.*;
import java.util.*;
import java.math.*;

public class SpecialBuyerRate implements SpecialBuyerRateInterface
{
private int rateId;
private int buyerId;
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
public void setBuyerId(int buyerId)
{
this.buyerId=buyerId;
}
public int getBuyerId()
{
return this.buyerId;
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
if(!(other instanceof SpecialBuyerRateInterface)) return false;
SpecialBuyerRateInterface buyerRate=(SpecialBuyerRateInterface)other;
return this.rateId==buyerRate.getRateId();
}
public int compareTo(SpecialBuyerRateInterface buyerRate)
{
return this.rateId-buyerRate.getRateId();
}

public int hashCode()
{
return Integer.hashCode(this.rateId);
}

}