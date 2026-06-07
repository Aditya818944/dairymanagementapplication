package com.dairy.management.application.dl.dto;
import com.dairy.management.application.dl.interfaces.dto.*;

import java.math.*;
import java.util.*;
public class BuyerPaymentDTO   implements BuyerPaymentDTOInterface 
{
private int buyerPaymentId;
private int buyerId;
private Date paymentDate;
private BigDecimal amountPaid;
private String remarks; 

public BuyerPaymentDTO()
{
}
public void setBuyerPaymentId(int buyerPaymentId)
{
this.buyerPaymentId=buyerPaymentId;
}
public int getBuyerPaymentId()
{
return this.buyerPaymentId;
}
public void setBuyerId(int buyerId)
{
this.buyerId=buyerId;
}
public int getBuyerId()
{
return this.buyerId;
}
public void setPaymentDate(java.util.Date paymentDate)
{
this.paymentDate=paymentDate;
}
public java.util.Date getPaymentDate()
{
return this.paymentDate;
}
public void setAmountPaid(java.math.BigDecimal amountPaid)
{
this.amountPaid=amountPaid;
}
public java.math.BigDecimal getAmountPaid()
{
return this.amountPaid;
}
public void setRemarks(java.lang.String remarks)
{
this.remarks=remarks;
}
public java.lang.String getRemarks()
{
return this.remarks;
}






}