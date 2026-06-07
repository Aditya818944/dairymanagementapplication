package com.dairy.management.application.dl.interfaces.dto;
import java.util.*;
import java.math.*;
public interface BuyerSaleDTOInterface
{
public void setBuyerSaleId(int buyerSaleId);
public int getBuyerSaleId();

public void setBuyerId(int buyerId);
public int getBuyerId();


public void setBuyingDate(Date buyingDate);
public Date getBuyingDate();

public void setShift(String shift);
public String getShift();

public void setFatValue(BigDecimal fatValue);
public BigDecimal getFatValue();

public void setAmount(BigDecimal amount);
public BigDecimal getAmount();


public void setMilkInLiters(BigDecimal milkInLiters);
public BigDecimal getMilkInLiters();

}