package com.dairy.management.application.pl.ui;
import com.dairy.management.application.bl.interfaces.managers.*;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.managers.*;
import com.dairy.management.application.bl.pojo.*;
import com.dairy.management.application.bl.exceptions.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.toedter.calendar.*;
import java.util.*;
import java.math.*;
public class SellMilkMonthlyPanel extends JPanel
{
private NavigationButtonsPanel buttonsPanel;
private JLabel eveningLabel,morningLabel;
private JDateChooser dateChooser,fromDateChooser,toDateChooser;
private JLabel fromDateLabel,toDateLabel,fatValueLabel,milkLabel,buyerIdLabel;
private JTextField fatValueTextField,milkTextField,buyerIdTextField,milkTextFieldEvening,fatValueTextFieldEvening;
private JButton saveButton,cancelButton;


public SellMilkMonthlyPanel(NavigationButtonsPanel buttonsPanel)
{
this.buttonsPanel=buttonsPanel;
initComponents();
setAppearance();
addListeners();
}
public void initComponents()
{

this.eveningLabel=new JLabel("Morning ");
this.morningLabel=new JLabel("Evening");

this.fromDateLabel=new JLabel("From : ");
this.fromDateChooser=new JDateChooser();
this.toDateLabel=new JLabel("To :  ");
this.toDateChooser=new JDateChooser();
this.buyerIdLabel=new JLabel("Id : ");
this.buyerIdTextField=new JTextField();
this.milkTextFieldEvening=new JTextField();
this.fatValueTextFieldEvening=new JTextField();

this.fatValueLabel=new JLabel("Fat Value  ");
this.fatValueTextField=new JTextField();
this.milkLabel=new JLabel("Milk in Liters ");
this.milkTextField=new JTextField();
this.saveButton=new JButton("add");
this.cancelButton=new JButton("cancel");
}
public void setAppearance()
{
int left=100;
int top=50;
setLayout(null);
fromDateLabel.setBounds(left+10,top+70,200,30);
fromDateLabel.setFont(new Font("Arial",Font.BOLD,26));
fromDateChooser.setBounds(left+100,top+70,100,30);
fromDateChooser.setDateFormatString("yyyy-MM-dd");
fromDateChooser.getDateEditor().getUiComponent().setEnabled(false);
fromDateChooser.getCalendarButton().setBackground(new Color(67,130,53));
fromDateChooser.getCalendarButton().setForeground(Color.WHITE);


toDateLabel.setBounds(left+150+50+10,top+70,200,30);
toDateLabel.setFont(new Font("Arial",Font.BOLD,26));
toDateChooser.setBounds(left+95+90+20+10+10+5+50,top+70,100,30);
toDateChooser.setDateFormatString("yyyy-MM-dd");
toDateChooser.getDateEditor().getUiComponent().setEnabled(false);
toDateChooser.getCalendarButton().setBackground(new Color(67,130,53));
toDateChooser.getCalendarButton().setForeground(Color.WHITE);


buyerIdLabel.setBounds(left+400,top+70,200,30);
buyerIdLabel.setFont(new Font("Arial",Font.BOLD,26));
buyerIdTextField.setBounds(left+95+90+20+10+10+5+220,top+70,100,30);

eveningLabel.setBounds(left+220,top+70+90,100,20);
morningLabel.setBounds(left+350,top+70+90,100,20);


fatValueLabel.setBounds(left+10,top+70+70+50,200,30);
fatValueLabel.setFont(new Font("Arial",Font.BOLD,26));
fatValueTextField.setBounds(left+200,top+70+70+50,100,30);
fatValueTextFieldEvening.setBounds(left+330,top+70+70+50,100,30);

milkLabel.setBounds(left+10,top+70+70+70+50,200,30);
milkLabel.setFont(new Font("Arial",Font.BOLD,26));
milkTextField.setBounds(left+200,top+70+70+70+50,100,30);
milkTextFieldEvening.setBounds(left+330,top+70+70+70+50,100,30);


saveButton.setBounds(left+150,top+70+70+70+70+30+30,100,30);
saveButton.setBackground(new Color(67,130,53));
saveButton.setForeground(Color.WHITE);

cancelButton.setBounds(left+100+100+100,top+70+70+70+70+30+30,100,30);
cancelButton.setBackground(new Color(67,130,53));
cancelButton.setForeground(Color.WHITE);


add(fromDateLabel);
add(fromDateChooser);
add(toDateLabel);
add(toDateChooser);
add(buyerIdLabel);
add(buyerIdTextField);
add(eveningLabel);
add(morningLabel);	
add(fatValueLabel);
add(fatValueTextField);
add(fatValueTextFieldEvening);
add(milkLabel);
add(milkTextField);
add(milkTextFieldEvening);
add(saveButton);
add(cancelButton);
}
public void addListeners()
{
saveButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
if(addMonthlyEntryBuyer())
{
clearFields();
buttonsPanel.showPanel("DEFAULT_PANEL");
buttonsPanel.setViewMode();
}
}
});

cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
System.out.println("Cancel purchasing  ");
clearFields();
buttonsPanel.showPanel("DEFAULT_PANEL");
buttonsPanel.setViewMode();
}
});
} // addListeners ends here 


void clearFields()
{
buyerIdTextField.setText("");
buyerIdTextField.setEnabled(true);
fatValueTextField.setText("");
milkTextField.setText("");
fromDateChooser.setDate(null);
fromDateChooser.setEnabled(true);
toDateChooser.setDate(null);
toDateChooser.setEnabled(true);
}

public boolean addMonthlyEntryBuyer()
{
Date fromDate=fromDateChooser.getDate();
Date toDate=toDateChooser.getDate();



if(fromDate==null && toDate==null) {
JOptionPane.showMessageDialog(this,"Please select start date and end date  ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}

if(fromDate==null) {
JOptionPane.showMessageDialog(this,"Please select start date  ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}

if(toDate==null) {
JOptionPane.showMessageDialog(this,"Please select end date  ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}





fromDateChooser.setEnabled(false);
toDateChooser.setEnabled(false);

String buyerId=buyerIdTextField.getText().trim();
if(buyerId.length()==0) 
{
JOptionPane.showMessageDialog(this,"Buyer id  required ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}

for(int i=0;i<buyerId.length();i++) {
if(Character.isDigit(buyerId.charAt(i))==false) {
JOptionPane.showMessageDialog(this,"Invalid farmer id","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
}

buyerIdTextField.setEnabled(false);

String fatValue=fatValueTextField.getText().trim();
if(fatValue.length()==0) 
{
JOptionPane.showMessageDialog(this,"Fat value of morning is required ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}

for(int i=0;i<fatValue.length();i++) {
if(!(Character.isDigit(fatValue.charAt(i)) || fatValue.charAt(i)=='.')) {
JOptionPane.showMessageDialog(this,"Invalid fat value","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
}






String milkInLiters=milkTextField.getText().trim();
if(milkInLiters.length()==0)
{
JOptionPane.showMessageDialog(this,"Milk in liters of morning    required","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}

for(int i=0;i<milkInLiters.length();i++) {
if(!(Character.isDigit(milkInLiters.charAt(i)) || milkInLiters.charAt(i)=='.')) {
JOptionPane.showMessageDialog(this,"Invalid milk value","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
}




String fatValueEvening=fatValueTextFieldEvening.getText().trim();
if(fatValueEvening.length()==0) 
{
JOptionPane.showMessageDialog(this,"Fat value of evening is required ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}

for(int i=0;i<fatValueEvening.length();i++) {
if(!(Character.isDigit(fatValueEvening.charAt(i)) || fatValueEvening.charAt(i)=='.')) {
JOptionPane.showMessageDialog(this,"Invalid fat value","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
}


String milkInLitersEvening=milkTextFieldEvening.getText().trim();
if(milkInLitersEvening.length()==0)
{
JOptionPane.showMessageDialog(this,"Milk in liters of eveing is  required","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}

for(int i=0;i<milkInLitersEvening.length();i++) {
if(!(Character.isDigit(milkInLitersEvening.charAt(i)) || milkInLitersEvening.charAt(i)=='.')) {
JOptionPane.showMessageDialog(this,"Invalid milk value","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
}

try
{
Calendar calendar=Calendar.getInstance();
calendar.setTime(fromDate);

Calendar endCalendar=Calendar.getInstance();
endCalendar.setTime(toDate);

if(!calendar.after(endCalendar))
{


BuyerSaleManagerInterface buyerSaleManager=BuyerSaleManager.getBuyerSaleManager();

BuyerSaleInterface buyerSale=new BuyerSale();
buyerSale.setBuyingDate(calendar.getTime());
buyerSale.setBuyerId(Integer.parseInt(buyerId));
buyerSale.setMilkInLiters(new BigDecimal(milkInLiters));
buyerSale.setFatValue(new BigDecimal(fatValue));
buyerSale.setShift("AM");
buyerSaleManager.add(buyerSale);



BuyerSaleInterface buyerSaleEvening=new BuyerSale();
buyerSaleEvening.setBuyingDate(calendar.getTime());
buyerSaleEvening.setBuyerId(Integer.parseInt(buyerId));
buyerSaleEvening.setMilkInLiters(new BigDecimal(milkInLitersEvening));
buyerSaleEvening.setFatValue(new BigDecimal(fatValueEvening));
buyerSaleEvening.setShift("PM");
buyerSaleManager.add(buyerSaleEvening);



milkTextField.setText("");
milkTextFieldEvening.setText("");
fatValueTextField.setText("");
fatValueTextFieldEvening.setText("");

calendar.add(Calendar.DAY_OF_MONTH,1);
fromDateChooser.setDate(calendar.getTime());
fromDate=fromDateChooser.getDate();


if(fromDate.after(toDate)){
System.out.println("Entry is done : ");
return true;
}

String message="Buyer Id : "+buyerId+"\n\n"+"Milk in Liters : "+milkInLiters+"\n\n"+"Fat value : "+fatValue+"\n\n"+"Amount : "+buyerSale.getAmount().toString()+"\n\n"+" Added , do you want to add more ? ";
return false;
}else
{
return true;
}
}catch(BLException blException)
{

fromDateChooser.setEnabled(true);
toDateChooser.setEnabled(true);
buyerIdTextField.setEnabled(true);

if(blException.hasGenericException()) {
JOptionPane.showMessageDialog(this,blException.getGenericException(),"Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
if(blException.hasExceptions())
{
java.util.List<String> list=blException.getProperties(); // extracting properties corresponding to them there is exception 
StringBuilder messages=new StringBuilder();
for(String prop : list) if(blException.hasException(prop)) messages.append(blException.getException(prop)).append("\n"); // printing exceptions 
JOptionPane.showMessageDialog(this,messages.toString(),"Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
}catch(Exception exception)
{
System.out.println("Exception : "+exception.getMessage());
}


//done done

return true;
}//addMonthlyEntry ends here 







}
