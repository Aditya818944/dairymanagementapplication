package com.dairy.management.application.dl.interfaces.dto;
import java.util.*;
import java.math.*;
public interface SpecialBuyerRateDTOInterface
{
public void setRateId(int id);
public int getRateId();
public void setBuyerId(int buyerId);
public int getBuyerId();
public void setRatePerFat(BigDecimal ratePerFat);
public BigDecimal getRatePerFat();
public void setStartDate(Date startDate);
public Date getStartDate();
public void setEndDate(Date endDate);
public Date getEndDate();
}