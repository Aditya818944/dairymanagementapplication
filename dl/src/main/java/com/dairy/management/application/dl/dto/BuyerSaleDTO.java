package com.dairy.management.application.dl.dto;
import com.dairy.management.application.dl.interfaces.dto.*;

import java.math.*;
import java.util.*;
public class BuyerSaleDTO implements BuyerSaleDTOInterface
{
private int buyerSaleId;
private int buyerId;
private Date buyingDate;
private String shift;
private BigDecimal fatValue;
private BigDecimal milkInLiters;
private BigDecimal amount;

public void setBuyerSaleId(int buyerSaleId)
{
this.buyerSaleId=buyerSaleId;
}
public int getBuyerSaleId()
{
return this.buyerSaleId;
}
public void setBuyerId(int buyerId)
{
this.buyerId=buyerId;
}
public int getBuyerId()
{
return this.buyerId;
}
public void setBuyingDate(java.util.Date buyingDate)
{
this.buyingDate=buyingDate;
}
public java.util.Date getBuyingDate()
{
return this.buyingDate;
}
public void setShift(java.lang.String shift)
{
this.shift=shift;
}
public java.lang.String getShift()
{
return this.shift;
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
public void setAmount(java.math.BigDecimal amount)
{
this.amount=amount;
}
public java.math.BigDecimal getAmount()
{
return this.amount;
}



}