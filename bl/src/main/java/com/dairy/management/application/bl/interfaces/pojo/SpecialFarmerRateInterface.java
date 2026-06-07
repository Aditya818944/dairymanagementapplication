package com.dairy.management.application.bl.interfaces.pojo;
import java.util.*;
import java.math.*;
public interface SpecialFarmerRateInterface extends java.io.Serializable , java.lang.Comparable<SpecialFarmerRateInterface>
{
public void setRateId(int id);
public int getRateId();
public void setFarmerId(int farmerId);
public int getFarmerId();
public void setRatePerFat(BigDecimal ratePerFat);
public BigDecimal getRatePerFat();
public void setStartDate(Date startDate);
public Date getStartDate();
public void setEndDate(Date endDate);
public Date getEndDate();
}