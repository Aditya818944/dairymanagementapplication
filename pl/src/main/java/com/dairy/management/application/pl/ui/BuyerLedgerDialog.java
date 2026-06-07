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


public class BuyerLedgerDialog extends JDialog 
{
private JTextField idField;
private JDateChooser fromDate, toDate;

public BuyerLedgerDialog(Window parent) {
super(parent, "Farmer Ledger", ModalityType.APPLICATION_MODAL);

setSize(400, 260);
setLayout(new BorderLayout());
initComponents();
setLocationRelativeTo(parent);
}
private void generatePdf(java.util.List<BuyerSaleInterface> ds)
{
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
int buyerId=ds.get(0).getBuyerId();
Date from=ds.get(0).getBuyingDate();
Date to=ds.get(ds.size()-1).getBuyingDate();
/**
*
*
Pdf creation logic starts here
*
*/
BigDecimal totalAmount=null;
String pdfFileName="";
String nameToPrint,startDateString,endDateString,ratePerFatString;
BigDecimal ratePerFat=null;
try{

BuyerSaleManagerInterface buyerSaleManager=BuyerSaleManager.getBuyerSaleManager();
totalAmount=buyerSaleManager.getAmountByDateAndBuyerId(buyerId,from,to);

nameToPrint=BuyerManager.getBuyerManager().getBuyerById(buyerId).getName();

pdfFileName=nameToPrint+" Ledger From ("+sdf.format(from)+" ) to ("+sdf.format(to)+").pdf";

if(BuyerManager.getBuyerManager().getBuyerById(buyerId).getType().equalsIgnoreCase("SPECIAL"))
{
ratePerFat=SpecialBuyerRateManager.getSpecialBuyerRateManager().getRate(buyerId,from);
ratePerFatString=ratePerFat.toString();
}else
{
ratePerFat=BuyerRateManager.getBuyerRateManager().getRate(from);
ratePerFatString=ratePerFat.toString();
}


startDateString=sdf.format(from);
endDateString=sdf.format(to);



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
detailPara.add("Id : "+buyerId+"     |     ");
detailPara.add("Name : "+nameToPrint+"     |     ");
detailPara.add("Period : "+startDateString+" to "+endDateString+"     |     ");
detailPara.add("Fat rate : "+ratePerFatString+" Rs/Fat ");

detailCell.add(detailPara);


table.addHeaderCell(detailCell);



String[] titles={"Date","Shift","Fat","Milk (in Liter)","Rate (Rs./Ltr.)","Amount (in Rupees)"};
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
Paragraph datePara,shiftPara,fatPara,milkPara,ratePara,totalAmountPara;
Cell dateCell,shiftCell,fatCell,milkCell,rateCell,totalAmountCell;

//"Name","Date","Shift","Liter","Fat","Rate","Total amount"

for(BuyerSaleInterface m : ds) 
{

String dateString=sdf.format(m.getBuyingDate());
String shiftString="";
if(m.getShift().equalsIgnoreCase("AM")) shiftString="Morning";
else shiftString="Evening";
String fatValueString=m.getFatValue().setScale(2,RoundingMode.HALF_UP).toString();
String milkInLiterString=m.getMilkInLiters().toString();
String rateString=m.getFatValue().multiply(ratePerFat).setScale(2,RoundingMode.HALF_UP).toString();
String totalAmountString=m.getAmount().toString();

datePara=new Paragraph(dateString);
datePara.setFont(parametersFont);
datePara.setFontSize(parameterFontSize);
dateCell=new Cell();
dateCell.add(datePara);


shiftPara=new Paragraph();
shiftPara.add(shiftString);
shiftPara.setFont(parametersFont);
shiftPara.setFontSize(parameterFontSize);
shiftCell=new Cell();
shiftCell.add(shiftPara);




fatPara=new Paragraph(fatValueString);
fatPara.setFont(parametersFont);
fatPara.setFontSize(parameterFontSize);
fatCell=new Cell();
fatCell.add(fatPara);


milkPara=new Paragraph(milkInLiterString);
milkPara.setFont(parametersFont);
milkPara.setFontSize(parameterFontSize);
milkCell=new Cell();
milkCell.add(milkPara);

ratePara=new Paragraph(rateString);
ratePara.setFont(parametersFont);
ratePara.setFontSize(parameterFontSize);
rateCell=new Cell();
rateCell.add(ratePara);

String rupee="\u20B9";
totalAmountPara=new Paragraph(totalAmountString);
totalAmountPara.setFont(parametersFont);
totalAmountPara.setFontSize(parameterFontSize);
totalAmountCell=new Cell();
totalAmountCell.add(totalAmountPara);





table.addCell(dateCell);
table.addCell(shiftCell);
table.addCell(fatCell);
table.addCell(milkCell);
table.addCell(rateCell);
table.addCell(totalAmountCell);
}

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
/**
*
*
Pdf creation logic ends here
*
*/
}
private void initComponents() 
{

JPanel formPanel = new JPanel(new GridLayout(3, 2, 30, 30));
formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
idField = new JTextField();
fromDate = new JDateChooser();
toDate = new JDateChooser();

Date today = new Date();
fromDate.setDate(today);
toDate.setDate(today);

fromDate.getDateEditor().setEnabled(false);
toDate.getDateEditor().setEnabled(false);

formPanel.add(new JLabel("Buyer Id:"));
formPanel.add(idField);

formPanel.add(new JLabel("From Date:"));
formPanel.add(fromDate);

formPanel.add(new JLabel("To Date:"));
formPanel.add(toDate);

JPanel buttonPanel = new JPanel();

JButton totalBtn = new JButton("Get Total");
JButton pdfBtn = new JButton("Get PDF");

totalBtn.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
handleTotal();
}});


pdfBtn.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
handlePDF();
}});

buttonPanel.add(totalBtn);
buttonPanel.add(pdfBtn);

add(formPanel, BorderLayout.CENTER);
add(buttonPanel, BorderLayout.SOUTH);
}



private boolean validateInput(String id, Date from, Date to)
{
if (id.isEmpty() || from == null || to == null) {
JOptionPane.showMessageDialog(this, "Please fill all fields");
return false;
}
if (from.after(to)) {
JOptionPane.showMessageDialog(this, "From date must be before To date");
return false;
}
return true;
} //validate input ends here 


private void handleTotal() 
{
String id = idField.getText();
Date from = fromDate.getDate();
Date to = toDate.getDate();

if (!validateInput(id, from, to)) return;



SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
try
{
BuyerSaleManagerInterface buyerSaleManager=BuyerSaleManager.getBuyerSaleManager();
BigDecimal amount=buyerSaleManager.getAmountByDateAndBuyerId(Integer.parseInt(id),from,to);

String message="Buyer Id           :  "+id+"\n\n"+"From Date          :  "+sdf.format(from)+"\n\n"+"To Date              :  "+sdf.format(to)+"\n\n"+"Total amount    :   "+amount.toString()+"\n\n";
JOptionPane.showMessageDialog(this,message);
dispose();
}catch(BLException blException)
{
if(blException.hasGenericException()) {
JOptionPane.showMessageDialog(this,blException.getGenericException(),"Errors",JOptionPane.ERROR_MESSAGE);
}
if(blException.hasExceptions())
{
java.util.List<String> list=blException.getProperties(); // extracting properties corresponding to them there is exception 
StringBuilder messages=new StringBuilder();
for(String prop : list) if(blException.hasException(prop)) messages.append(blException.getException(prop)).append("\n"); // printing exceptions 
JOptionPane.showMessageDialog(this,messages.toString(),"Errors",JOptionPane.ERROR_MESSAGE);
}
}
}

private void handlePDF() {

String id = idField.getText();
Date from = fromDate.getDate();
Date to = toDate.getDate();

if (!validateInput(id, from, to)) return;

SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
try
{
java.util.List<BuyerSaleInterface> list=new ArrayList<>();
int buyerId=Integer.parseInt(id);
from=sdf.parse(sdf.format(from));
to=sdf.parse(sdf.format(to));
BuyerSaleManagerInterface buyerSaleManager=BuyerSaleManager.getBuyerSaleManager();
Set<BuyerSaleInterface> st=buyerSaleManager.getAllByBuyerId(buyerId);
System.out.println(st.size());
if(st.size()==0) {
JOptionPane.showMessageDialog(this,"No record exist for given buyer id" ,"Errors",JOptionPane.ERROR_MESSAGE);
return ;
}
for(BuyerSaleInterface m : st){
Date date=m.getBuyingDate();
date=sdf.parse(sdf.format(date));
if(date.compareTo(from)>=0 &&  date.compareTo(to)<=0)
{
BuyerSaleInterface buyerSale=new BuyerSale();
buyerSale.setBuyerSaleId(m.getBuyerSaleId());
buyerSale.setBuyingDate(m.getBuyingDate());
buyerSale.setAmount(m.getAmount());
buyerSale.setMilkInLiters(m.getMilkInLiters());
buyerSale.setFatValue(m.getFatValue());
buyerSale.setBuyerId(m.getBuyerId());
buyerSale.setShift(m.getShift());
list.add(buyerSale);
}
}
if(list.size()==0) {
JOptionPane.showMessageDialog(this,"No record for given dates " ,"Errors",JOptionPane.ERROR_MESSAGE);
return ;
}
generatePdf(list);
}catch(BLException blException)
{
System.out.println("Exception in generate pdf function : "+blException.getMessage());
}catch(Exception exception)
{
System.out.println("Exception in generate pdf function : "+exception.getMessage());
}
dispose();
}




public String getBuyerId() {
return idField.getText();
}

public Date getFromDate() {
return fromDate.getDate();
}

public Date getToDate() {
return toDate.getDate();
}
}