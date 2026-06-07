package com.dairy.management.application.bl.pojo;
import com.dairy.management.application.bl.interfaces.pojo.*;
import java.util.*;
import java.math.*;

public class BuyerPayment   implements BuyerPaymentInterface 
{
private int buyerPaymentId;
private int buyerId;
private Date paymentDate;
private BigDecimal amountPaid;
private String remarks; 

public BuyerPayment()
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

public boolean equals(Object other)
{
if(!(other instanceof BuyerPaymentInterface)) return false;
BuyerPaymentInterface buyerPayment=(BuyerPaymentInterface)other;
return this.buyerPaymentId==buyerPayment.getBuyerPaymentId();
}
public int compareTo(BuyerPaymentInterface buyerPayment)
{
return this.buyerId-buyerPayment.getBuyerPaymentId();
}

public int hashCode()
{
return Integer.hashCode(this.buyerPaymentId);
}


}