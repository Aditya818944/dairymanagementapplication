package com.dairy.management.application.bl.interfaces.pojo;
import java.math.*;
import java.util.*;
public interface BuyerPaymentInterface extends  java.io.Serializable , java.lang.Comparable<BuyerPaymentInterface>
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