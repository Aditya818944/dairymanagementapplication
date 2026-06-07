package com.dairy.management.application.dl.interfaces.dto;
import java.math.*;
import java.util.*;
public interface BuyerPaymentDTOInterface 
{
public void setBuyerPaymentId(int payId);
public int getBuyerPaymentId();
public void setBuyerId(int buyerId);
public int getBuyerId();
public void setPaymentDate(Date date);
public Date getPaymentDate();
public void setAmountPaid(BigDecimal amountPaid);
public BigDecimal getAmountPaid();
public void setRemarks(String remark);
public String getRemarks();
}