package com.dairy.management.application.bl.pojo;
import com.dairy.management.application.bl.interfaces.pojo.*;
import java.util.*;
import java.math.*;

public class BuyerRate implements BuyerRateInterface
{
private int rateId;
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
if(!(other instanceof BuyerRateInterface)) return false;
BuyerRateInterface buyerRate=(BuyerRateInterface)other;
return this.rateId==buyerRate.getRateId();
}
public int compareTo(BuyerRateInterface buyerRate)
{
return this.rateId-buyerRate.getRateId();
}
public int hashCode()
{
return Integer.hashCode(this.rateId);
}


}