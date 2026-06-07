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
public class PurchaseMilkMonthlyPanel extends JPanel
{
private NavigationButtonsPanel buttonsPanel;
private JLabel eveningLabel,morningLabel;
private JDateChooser dateChooser,fromDateChooser,toDateChooser;
private JLabel fromDateLabel,toDateLabel,fatValueLabel,milkLabel,farmerIdLabel;
private JTextField fatValueTextField,milkTextField,farmerIdTextField,milkTextFieldEvening,fatValueTextFieldEvening;
private JButton saveButton,cancelButton;


public PurchaseMilkMonthlyPanel(NavigationButtonsPanel buttonsPanel)
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
this.farmerIdLabel=new JLabel("Id : ");
this.farmerIdTextField=new JTextField();
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




farmerIdLabel.setBounds(left+400,top+70,200,30);
farmerIdLabel.setFont(new Font("Arial",Font.BOLD,26));
farmerIdTextField.setBounds(left+95+90+20+10+10+5+220,top+70,100,30);

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
add(farmerIdLabel);
add(farmerIdTextField);
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
if(addMonthlyEntryFarmer())
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
farmerIdTextField.setText("");
farmerIdTextField.setEnabled(true);
fatValueTextField.setText("");
milkTextField.setText("");
fromDateChooser.setDate(null);
fromDateChooser.setEnabled(true);
toDateChooser.setDate(null);
toDateChooser.setEnabled(true);
}

public boolean addMonthlyEntryFarmer()
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

String farmerId=farmerIdTextField.getText().trim();
if(farmerId.length()==0) 
{
JOptionPane.showMessageDialog(this,"Farmer id  required ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}

for(int i=0;i<farmerId.length();i++) {
if(Character.isDigit(farmerId.charAt(i))==false) {
JOptionPane.showMessageDialog(this,"Invalid farmer id","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
}

farmerIdTextField.setEnabled(false);

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



MilkCollectionManagerInterface milkCollectionManager=MilkCollectionManager.getMilkCollectionManager();

MilkCollectionInterface milkCollection=new MilkCollection();
milkCollection.setCollectionDate(calendar.getTime());
milkCollection.setFarmerId(Integer.parseInt(farmerId));
milkCollection.setMilkInLiters(new BigDecimal(milkInLiters));
milkCollection.setFatValue(new BigDecimal(fatValue));
milkCollection.setShift("AM");
milkCollectionManager.add(milkCollection);


MilkCollectionInterface milkCollectionEvening=new MilkCollection();
milkCollectionEvening.setCollectionDate(calendar.getTime());
milkCollectionEvening.setFarmerId(Integer.parseInt(farmerId));
milkCollectionEvening.setMilkInLiters(new BigDecimal(milkInLitersEvening));
milkCollectionEvening.setFatValue(new BigDecimal(fatValueEvening));
milkCollectionEvening.setShift("PM");
milkCollectionManager.add(milkCollectionEvening);





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

String message="Farmer Id : "+farmerId+"\n\n"+"Milk in Liters : "+milkInLiters+"\n\n"+"Fat value : "+fatValue+"\n\n"+"Amount : "+milkCollection.getAmount().toString()+"\n\n"+" Added , do you want to add more ? ";
return false;
}else
{
return true;
}
}catch(BLException blException)
{

fromDateChooser.setEnabled(true);
toDateChooser.setEnabled(true);
farmerIdTextField.setEnabled(true);

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
}


//done done

return true;
}//addMonthlyEntry ends here 







}
