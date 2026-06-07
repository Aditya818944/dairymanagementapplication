package com.dairy.management.application.dl.interfaces.dto;
import java.math.*;
import java.util.*;
public interface FarmerPaymentDTOInterface 
{
public void setFarmerPaymentId(int payId);
public int getFarmerPaymentId();

public void setFarmerId(int farmerId);
public int getFarmerId();

public void setPaymentDate(Date date);
public Date getPaymentDate();

public void setAmountPaid(BigDecimal amountPaid);
public BigDecimal getAmountPaid();

public void setRemarks(String remark);
public String getRemarks();
}