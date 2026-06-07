package com.dairy.management.application.dl.dto;
import com.dairy.management.application.dl.interfaces.dto.*;
import java.util.*;
import java.math.*;
public class MilkCollectionDTO implements MilkCollectionDTOInterface
{
private int collectionId;
private int farmerId;
private Date collectionDate;
private String shift;
private BigDecimal amount;
private BigDecimal ratePerFat;
private BigDecimal fatValue;
private BigDecimal milkInLiters;
public void setCollectionId(int collectionId)
{
this.collectionId=collectionId;
}
public int getCollectionId()
{
return this.collectionId;
}
public void setFarmerId(int farmerId)
{
this.farmerId=farmerId;
}
public int getFarmerId()
{
return this.farmerId;
}
public void setCollectionDate(java.util.Date collectionDate)
{
this.collectionDate=collectionDate;
}
public java.util.Date getCollectionDate()
{
return this.collectionDate;
}
public void setShift(java.lang.String shift)
{
this.shift=shift;
}
public java.lang.String getShift()
{
return this.shift;
}
public void setAmount(java.math.BigDecimal amount)
{
this.amount=amount;
}
public java.math.BigDecimal getAmount()
{
return this.amount;
}
public void setRatePerFat(java.math.BigDecimal ratePerFat)
{
this.ratePerFat=ratePerFat;
}
public BigDecimal getRatePerFat()
{
return this.ratePerFat;
}


public void setFatValue(java.math.BigDecimal fatValue)
{
this.fatValue=fatValue;
}
public java.math.BigDecimal getFatValue()
{
return this.fatValue;
}
public void setMilkInLiters(java.math.BigDecimal milkInLiters)
{
this.milkInLiters=milkInLiters;
}
public java.math.BigDecimal getMilkInLiters()
{
return this.milkInLiters;
}

}