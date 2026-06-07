package com.dairy.management.application.dl.interfaces.dto;
import java.util.*;
import java.math.*;
public interface FarmerRateDTOInterface
{
public void setRateId(int id);
public int getRateId();
public void setRatePerFat(BigDecimal ratePerFat);
public BigDecimal getRatePerFat();
public void setStartDate(Date startDate);
public Date getStartDate();
public void setEndDate(Date endDate);
public Date getEndDate();
}