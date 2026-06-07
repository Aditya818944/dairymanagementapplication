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


public class CombinedLedgerDialog extends JDialog 
{
private SimpleDateFormat sdf;
private JLabel headingLabel,fromDateLabel,toDateLabel;
private JDateChooser fromDate, toDate;

public CombinedLedgerDialog(Window parent) {
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

to=sdf.parse(sdf.format(to));
from=sdf.parse(sdf.format(from));


int count=0;

//Id , Name ,Phone Number ,from date , to date , total amount
Map<Integer,String []> farmersDetailMap=new HashMap<>();
String [] farmerProperties=null;
FarmerManagerInterface farmerManager=FarmerManager.getFarmerManager();
MilkCollectionManagerInterface milkCollectionManager=MilkCollectionManager.getMilkCollectionManager();
Set<FarmerInterface> farmers=farmerManager.getAll();
if(farmers!=null)
{
for(FarmerInterface f : farmers)
{
if(milkCollectionManager.farmerIdExists(f.getFarmerId()))
{
farmerProperties=new String[6];
farmerProperties[0]=f.getFarmerId()+"";
farmerProperties[1]=f.getName();
farmerProperties[2]=f.getPhoneNumber();
farmerProperties[3]=sdf.format(from);
farmerProperties[4]=sdf.format(to);
farmerProperties[5]=milkCollectionManager.getAmountByDateAndFarmerId(f.getFarmerId(),from,to).toString();
farmersDetailMap.put(count,farmerProperties);
count++;
}

}
}

count=0;
Map<Integer,String []> buyersDetailMap=new HashMap<>();
String [] buyerProperties=null;
BuyerManagerInterface buyerManager=BuyerManager.getBuyerManager();
BuyerSaleManagerInterface buyerSaleManager=BuyerSaleManager.getBuyerSaleManager();
Set<BuyerInterface> buyers=buyerManager.getAll();
if(buyers!=null)
{
for(BuyerInterface b : buyers){
if(buyerSaleManager.buyerIdExists(b.getBuyerId()))
{
buyerProperties=new String[6];
buyerProperties[0]=b.getBuyerId()+"";
buyerProperties[1]=b.getName();
buyerProperties[2]=b.getPhoneNumber();
buyerProperties[3]=sdf.format(from);
buyerProperties[4]=sdf.format(to);
buyerProperties[5]=buyerSaleManager.getAmountByDateAndBuyerId(b.getBuyerId(),from,to).toString();
buyersDetailMap.put(count,buyerProperties);
count++;
}
}
}
generatePdf(farmersDetailMap,buyersDetailMap);
dispose();
}catch(BLException blException)
{
}catch(Exception exception)
{
System.out.println("HndlePDF Exception : "+exception.getMessage());
}

}



private void generatePdf(Map<Integer,String []> farmersDetailMap,Map<Integer,String []> buyersDetailMap)
{
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

String startDate=farmersDetailMap.get(0)[3];
String endDate=farmersDetailMap.get(0)[4];


String pdfFileName="Transaction Summary From ( "+startDate+" ) to ( "+endDate+" ).pdf";
try{

// for coloring
DeviceRgb darkGreen,white;
darkGreen=new DeviceRgb(0,102,51);
white=new DeviceRgb(255,255,255);
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
PdfFont detailFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLDITALIC);


Paragraph detailPara=new Paragraph();
detailPara.setFont(detailFont);
detailPara.setFontSize(10);
detailPara.add("Farmers & Buyers Transaction Summary");

detailCell.add(detailPara);


table.addHeaderCell(detailCell);


//Id , Name ,Phone Number ,from date , to date , total amount

String[] titles={"Id","Name","Phone Number","From Date","To Date","Amount (in Rupees)"};
PdfFont titlesFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLDITALIC);
Paragraph titlePara;
Cell cell;
for(String title : titles)
{
titlePara=new Paragraph(title);
titlePara.setFont(titlesFont);
titlePara.setFontSize(12);
cell=new Cell().setBackgroundColor(darkGreen).setFontColor(white);
cell.add(titlePara);
table.addHeaderCell(cell);
}



PdfFont parametersFont=PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);
int parameterFontSize=10;
Paragraph idPara,namePara,phoneNumberPara,fromDatePara,toDatePara,totalAmountPara;
Cell idCell,nameCell,phoneNumberCell,fromDateCell,toDateCell,totalAmountCell;

//Id , Name ,Phone Number ,from date , to date , total amount

for(int i=0;i<farmersDetailMap.size();i++) 
{
String parts[]=farmersDetailMap.get(i);

String idString=parts[0];

String nameString=parts[1];
String phoneNumberString=parts[2];



String fromDateString=parts[3];

String toDateString=parts[4];

String totalAmountString=parts[5];


idPara=new Paragraph(idString);
idPara.setFont(parametersFont);
idPara.setFontSize(parameterFontSize);
idCell=new Cell();
idCell.add(idPara);


namePara=new Paragraph();
namePara.add(nameString);
namePara.setFont(parametersFont);
namePara.setFontSize(parameterFontSize);
nameCell=new Cell();
nameCell.add(namePara);




phoneNumberPara=new Paragraph(phoneNumberString);
phoneNumberPara.setFont(parametersFont);
phoneNumberPara.setFontSize(parameterFontSize);
phoneNumberCell=new Cell();
phoneNumberCell.add(phoneNumberPara);


fromDatePara=new Paragraph(fromDateString);
fromDatePara.setFont(parametersFont);
fromDatePara.setFontSize(parameterFontSize);
fromDateCell=new Cell();
fromDateCell.add(fromDatePara);

toDatePara=new Paragraph(toDateString);
toDatePara.setFont(parametersFont);
toDatePara.setFontSize(parameterFontSize);
toDateCell=new Cell();
toDateCell.add(toDatePara);

String rupee="\u20B9";
totalAmountPara=new Paragraph(totalAmountString);
totalAmountPara.setFont(parametersFont);
totalAmountPara.setFontSize(parameterFontSize);
totalAmountCell=new Cell();
totalAmountCell.add(totalAmountPara);





table.addCell(idCell);
table.addCell(nameCell);
table.addCell(phoneNumberCell);
table.addCell(fromDateCell);
table.addCell(toDateCell);
table.addCell(totalAmountCell);
}




for(int i=0;i<buyersDetailMap.size();i++) 
{
String parts[]=buyersDetailMap.get(i);

String idString=parts[0];

String nameString=parts[1];

String phoneNumberString=parts[2];



String fromDateString=parts[3];

String toDateString=parts[4];

String totalAmountString=parts[5];


idPara=new Paragraph(idString);
idPara.setFont(parametersFont);
idPara.setFontSize(parameterFontSize);
idCell=new Cell();
idCell.add(idPara);


namePara=new Paragraph();
namePara.add(nameString);
namePara.setFont(parametersFont);
namePara.setFontSize(parameterFontSize);
nameCell=new Cell();
nameCell.add(namePara);




phoneNumberPara=new Paragraph(phoneNumberString);
phoneNumberPara.setFont(parametersFont);
phoneNumberPara.setFontSize(parameterFontSize);
phoneNumberCell=new Cell();
phoneNumberCell.add(phoneNumberPara);


fromDatePara=new Paragraph(fromDateString);
fromDatePara.setFont(parametersFont);
fromDatePara.setFontSize(parameterFontSize);
fromDateCell=new Cell();
fromDateCell.add(fromDatePara);

toDatePara=new Paragraph(toDateString);
toDatePara.setFont(parametersFont);
toDatePara.setFontSize(parameterFontSize);
toDateCell=new Cell();
toDateCell.add(toDatePara);

String rupee="\u20B9";
totalAmountPara=new Paragraph(totalAmountString);
totalAmountPara.setFont(parametersFont);
totalAmountPara.setFontSize(parameterFontSize);
totalAmountCell=new Cell();
totalAmountCell.add(totalAmountPara);





table.addCell(idCell);
table.addCell(nameCell);
table.addCell(phoneNumberCell);
table.addCell(fromDateCell);
table.addCell(toDateCell);
table.addCell(totalAmountCell);
}




/*********
Cell blankCell=new Cell(2,4).setTextAlignment(TextAlignment.RIGHT);
blankCell.add(new Paragraph()).setHeight(15);


Cell totalCell=new Cell(2,1);
totalCell.setTextAlignment(TextAlignment.LEFT);
totalCell.setBackgroundColor(darkGreen);
totalCell.setFontColor(white);

Paragraph totalPara=new Paragraph("Total Amout : ");
totalPara.setFont(titlesFont);
totalPara.setFontSize(12);
totalCell.add(totalPara);


Cell totalValueCell=new Cell();
totalValueCell.setTextAlignment(TextAlignment.LEFT);
totalValueCell.setBackgroundColor(darkGreen);
totalValueCell.setFontColor(white);
Paragraph totalValuePara=new Paragraph("Rs. "+totalAmount.toString());
totalValuePara.setFont(titlesFont);
totalPara.setFontSize(12);
totalValueCell.add(totalValuePara);

table.addCell(blankCell);
table.addCell(totalCell);
table.addCell(totalValueCell);


********/
document.add(table);

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