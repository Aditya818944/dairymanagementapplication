package com.dairy.management.application.dl.dto;
import com.dairy.management.application.dl.interfaces.dto.*;
import java.util.*;
import java.math.*;
public class FarmerPaymentDTO implements FarmerPaymentDTOInterface
{
private int farmerPaymentId;
private int farmerId;
private Date paymentDate;
private BigDecimal amountPaid;
private String remarks;


public void setFarmerPaymentId(int farmerPaymentId)
{
this.farmerPaymentId=farmerPaymentId;
}
public int getFarmerPaymentId()
{
return this.farmerPaymentId;
}
public void setFarmerId(int farmerId)
{
this.farmerId=farmerId;
}
public int getFarmerId()
{
return this.farmerId;
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