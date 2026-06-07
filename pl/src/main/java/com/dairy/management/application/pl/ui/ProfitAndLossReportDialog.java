/***********************
VVV IMP Note : Local sale rate is hard coded
***********************/


package com.dairy.management.application.pl.ui;
import com.dairy.management.application.bl.interfaces.managers.*;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.managers.*;
import com.dairy.management.application.bl.pojo.*;
import com.dairy.management.application.bl.exceptions.*;


import java.io.*;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.geom.*;

import com.itextpdf.kernel.colors.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.borders.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.kernel.pdf.extgstate.*;
import com.itextpdf.kernel.pdf.canvas.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.math.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;


public class ProfitAndLossReportDialog extends JDialog 
{
private SimpleDateFormat sdf;
private JLabel headingLabel,fromDateLabel,toDateLabel;
private JDateChooser fromDate, toDate;

public ProfitAndLossReportDialog(Window parent) {
super(parent, "Farmer Ledger", ModalityType.APPLICATION_MODAL);

setSize(400, 260);
setLayout(new BorderLayout());
initComponents();
setLocationRelativeTo(parent);
}
private void initComponents() 
{
sdf=new SimpleDateFormat("yyyy-MM-dd");
int left=20;
int top=30;


JPanel formPanel = new JPanel();
formPanel.setLayout(null);


fromDateLabel=new JLabel("From Date : ");
fromDateLabel.setBounds(left+50,top+10,100,50);
fromDate=new JDateChooser();
fromDate.setBounds(left+150,top+20,100,30);


toDateLabel=new JLabel("To Date : ");
toDateLabel.setBounds(left+50,top+60,100,50);
toDate=new JDateChooser();
toDate.setBounds(left+150,top+70,100,30);


formPanel.add(fromDateLabel);
formPanel.add(fromDate);
formPanel.add(toDateLabel);
formPanel.add(toDate);


JPanel buttonPanel = new JPanel();

JButton cancelBtn = new JButton("Go Back");
JButton pdfBtn = new JButton("Get PDF");

cancelBtn.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
dispose();
}});


pdfBtn.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
handlePDF();
}});

buttonPanel.add(pdfBtn);
buttonPanel.add(cancelBtn);

add(formPanel, BorderLayout.CENTER);
add(buttonPanel, BorderLayout.SOUTH);
}



private boolean validateInput(Date from, Date to)
{
if (from == null || to == null) {
JOptionPane.showMessageDialog(this, "Please fill all fields");
return false;
}
if (from.after(to)) {
JOptionPane.showMessageDialog(this, "From date must be before To date");
return false;
}
return true;
} //validate input ends here 




private void handlePDF() {
try
{
Date from = fromDate.getDate();

Date to = toDate.getDate();

if (!validateInput(from, to)) return;

from=sdf.parse(sdf.format(from));

to=sdf.parse(sdf.format(to));

int count=0;

//Id , Name ,Phone Number ,from date , to date , total amount
Map<Integer,String> farmersDetailMap=new HashMap<>();

MilkCollectionManagerInterface milkCollectionManager=MilkCollectionManager.getMilkCollectionManager();
BuyerSaleManagerInterface buyerSaleManager=BuyerSaleManager.getBuyerSaleManager();


int indicator=0;

BigDecimal totalMilkCollected=milkCollectionManager.getTotalMilkCollectedInLiters(from,to);
BigDecimal totalMilkSold=buyerSaleManager.getTotalMilkSoldInLiters(from,to);

BigDecimal milkSoldToLocalBuyers=totalMilkCollected.subtract(totalMilkSold);
BigDecimal localBuyerRatePerLiter=new BigDecimal("60");  


BigDecimal localBuyersAmount=milkSoldToLocalBuyers.multiply(localBuyerRatePerLiter);





BigDecimal buyersAmount=buyerSaleManager.getAmountByDate(from,to);


BigDecimal totalAmountToReceive=buyersAmount.add(localBuyersAmount);


BigDecimal farmersAmount=milkCollectionManager.getAmountByDate(from,to);


int result=totalAmountToReceive.compareTo(farmersAmount);

BigDecimal amount=null;

if(result>0)
{
//buyers amount is greater , profit
indicator=1;
amount=totalAmountToReceive.subtract(farmersAmount);
}else if(result<0)
{
//buyers amount is lesser ,loss
indicator=-1;
amount=farmersAmount.subtract(buyersAmount);
}else {
//equal , no loss not profit 
indicator=0;
amount=BigDecimal.ZERO;
}
generatePdf(indicator,amount,buyersAmount,farmersAmount,localBuyersAmount,from,to);
dispose();
}catch(BLException blException)
{
}catch(Exception exception)
{
System.out.println("HandlePDF Exception : "+exception.getMessage());
}

}



private void generatePdf(int indicator,BigDecimal amount,BigDecimal buyersAmount,BigDecimal farmersAmount,BigDecimal localBuyersAmount,Date from,Date to)
{
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
String pdfFileName="Profit & Loss Report From ( "+sdf.format(from)+" ) to ( "+sdf.format(to)+" ).pdf";
try{

// for coloring
DeviceRgb darkGreen,white,black,red,blue,gray,green;
darkGreen=new DeviceRgb(0,102,51);
white=new DeviceRgb(255,255,255);
black=new DeviceRgb(0,0,0);
red=new DeviceRgb(255,0,0);
blue=new DeviceRgb(0,0,255);
gray=new DeviceRgb(80,80,80);
green=new DeviceRgb(0,255,20);

String pdfLocation="C:"+File.separator+"dairymanagementapplication"+File.separator+"pdfs"+File.separator+pdfFileName;
PdfWriter pdfWriter = new PdfWriter(new File(pdfLocation));
PdfDocument pdfDocument = new PdfDocument(pdfWriter);
Document document = new Document(pdfDocument);
document.setMargins(15,15,15,15);

float columnWidths[]={2, 2, 2, 3, 2, 3};
Table table=new Table(UnitValue.createPercentArray(columnWidths));
table.setHorizontalAlignment(HorizontalAlignment.CENTER);
table.setWidth(UnitValue.createPercentValue(100));

PdfFont headingFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);

Cell headingCell=new Cell(1,6);
headingCell.setTextAlignment(TextAlignment.CENTER);

Paragraph headingTitle=new Paragraph("MILK ACCOUNT STATEMENT");
headingTitle.setFont(headingFont);
headingTitle.setFontSize(20);

headingCell.add(headingTitle);

table.addHeaderCell(headingCell);


Cell detailCell=new Cell(1,6).setBackgroundColor(white).setFontColor(darkGreen);
detailCell.setTextAlignment(TextAlignment.CENTER);
detailCell.setBorderBottom(new SolidBorder(1));
PdfFont detailFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLDITALIC);


Paragraph detailPara=new Paragraph();
detailPara.setFont(detailFont);
detailPara.setFontSize(10);
detailPara.add("Profit and Loss Summary       |    ");
detailPara.add("Period : "+sdf.format(from)+" to "+sdf.format(to));

detailCell.add(detailPara);


table.addHeaderCell(detailCell);
document.add(table);



Paragraph p1=new Paragraph("\n\nFinancial Details  \n");
p1.setFont(detailFont);
p1.setFontSize(20);
Cell c1=new Cell().setBackgroundColor(white).setFontColor(darkGreen);
c1.setTextAlignment(TextAlignment.CENTER);
c1.add(p1);
document.add(c1);




Paragraph p2=new Paragraph("    Total Payable to Farmers       :  Rs. "+farmersAmount.toString()+"\n");
p2.setFont(detailFont);
p2.setFontSize(15);
p2.setMargin(50);
Cell c2=new Cell().setBackgroundColor(white).setFontColor(black);
c2.setTextAlignment(TextAlignment.LEFT);
c2.add(p2);
document.add(c2);



Paragraph p3=new Paragraph("    Total Receivable  from Buyers       :  Rs. "+buyersAmount.toString()+"\n");
p3.setFont(detailFont);
p3.setFontSize(15);
p3.setMargin(50);
Cell c3=new Cell().setBackgroundColor(white).setFontColor(black);
c3.setTextAlignment(TextAlignment.LEFT);
c3.add(p3);
document.add(c3);



Paragraph pp=new Paragraph("    Total Receivable  from Local Buyers       :  Rs. "+localBuyersAmount.toString()+"\n");
pp.setFont(detailFont);
pp.setFontSize(15);
pp.setMargin(50);
Cell cc=new Cell().setBackgroundColor(white).setFontColor(black);
cc.setTextAlignment(TextAlignment.LEFT);
cc.add(pp);
document.add(cc);



if(indicator==-1)
{
//loss
Paragraph lossPara=new Paragraph("    Net loss       :  Rs. "+amount.toString()+" (approx.) \n");
lossPara.setFont(detailFont);
lossPara.setFontSize(15);
lossPara.setMargin(50);
Cell lossCell=new Cell().setBackgroundColor(white).setFontColor(red);
lossCell.setTextAlignment(TextAlignment.LEFT);
lossCell.add(lossPara);
document.add(lossCell);
}else if(indicator==1)
{
//profit
Paragraph profitPara=new Paragraph("    Net profit    :  Rs. "+amount.toString()+" (approx.) \n");
profitPara.setFont(detailFont);
profitPara.setFontSize(15);
profitPara.setMargin(50);
Cell profitCell=new Cell().setBackgroundColor(white).setFontColor(green);
profitCell.setTextAlignment(TextAlignment.LEFT);
profitCell.add(profitPara);
document.add(profitCell);
}else if(indicator==0)
{
// no loss no profit
Paragraph p4=new Paragraph("   Break-even (No Profit , No Loss) "+"\n");
p4.setFont(detailFont);
p4.setFontSize(15);
p4.setMargin(50);
Cell c4=new Cell().setBackgroundColor(white).setFontColor(gray);
c4.setTextAlignment(TextAlignment.LEFT);
c4.add(p4);
document.add(c4);
}



int numberOfPages=pdfDocument.getNumberOfPages();
for(int i=1;i<=numberOfPages;i++)
{

PdfPage page=pdfDocument.getPage(i);
PdfCanvas canvas=new PdfCanvas(page.newContentStreamAfter(),page.getResources(),pdfDocument);



// setting tranparency 
PdfExtGState gs=new PdfExtGState();
gs.setFillOpacity(0.4f);
canvas.setExtGState(gs);


com.itextpdf.layout.Canvas modelCanvas=new com.itextpdf.layout.Canvas(canvas,pdfDocument,page.getPageSize());

Paragraph watermarkPara=new Paragraph("ARPITA   MILK   DAIRY").setFontSize(50);
watermarkPara.setFontColor(darkGreen);
modelCanvas.showTextAligned(watermarkPara,page.getPageSize().getWidth()/2,page.getPageSize().getHeight()/2,i,TextAlignment.CENTER,VerticalAlignment.MIDDLE,(float)Math.toRadians(45));

canvas.beginText();
PdfFont font = PdfFontFactory.createFont();
canvas.setFontAndSize(font, 50);

// Position (center of page)
 com.itextpdf.kernel.geom.Rectangle pageSize = page.getPageSize();
float x = pageSize.getWidth() / 2;
float y = pageSize.getHeight() / 2;

// Move and rotate 
canvas.moveText(x, y);

canvas.endText();
}
String message="PDF LOCATION : "+pdfLocation+"  \n\n\n "+" Do you want to open ?";
int result=JOptionPane.showConfirmDialog(this,message,"ARPITA MILK DAIRY",JOptionPane.YES_NO_OPTION);
if(result==JOptionPane.YES_OPTION)
{
if(Desktop.isDesktopSupported())
{
File file=new File(pdfLocation);
Desktop desktop=Desktop.getDesktop();
desktop.open(file);
}else
{
System.out.println("Desktop is not supported ");
}
}
pdfDocument.close();
document.close();
}catch(Exception exception )
{
System.out.println("Exception in pdf creation functions : "+exception.getMessage());
}

/// pdf logic ends here 

}





public Date getFromDate() {
return fromDate.getDate();
}

public Date getToDate() {
return toDate.getDate();
}
}