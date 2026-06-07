package com.dairy.management.application.bl.interfaces.pojo;
import java.util.*;
import java.math.*;
public interface MilkCollectionInterface   extends java.io.Serializable , java.lang.Comparable<MilkCollectionInterface>
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