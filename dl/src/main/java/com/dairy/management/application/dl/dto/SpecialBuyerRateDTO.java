package com.dairy.management.application.dl.dto;
import com.dairy.management.application.dl.interfaces.dto.*;
import java.util.*;
import java.math.*;
public class SpecialBuyerRateDTO implements SpecialBuyerRateDTOInterface
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



}