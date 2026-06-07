package com.dairy.management.application.dl.interfaces.dto;
import java.util.*;
import java.math.*;
public interface MilkCollectionDTOInterface 
{
public void setCollectionId(int collectionId);
public int getCollectionId();

public void setFarmerId(int farmerId);
public int getFarmerId();

public void setCollectionDate(Date date);
public Date getCollectionDate();

public void setShift(String shift);
public String getShift();

public void setAmount(BigDecimal amount);
public BigDecimal getAmount();

public void setFatValue(BigDecimal fatValue);
public BigDecimal getFatValue();

public void setMilkInLiters(BigDecimal milkInLiters);
public BigDecimal getMilkInLiters();

}