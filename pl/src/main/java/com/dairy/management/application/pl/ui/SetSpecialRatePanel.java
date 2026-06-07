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
import java.math.*;
import java.util.*;
import java.text.*;
public class SetSpecialRatePanel extends JPanel
{
private NavigationButtonsPanel buttonsPanel;
private JDateChooser dateChooser,fromDateChooser,toDateChooser;
private JLabel fromDateLabel,toDateLabel,ratePerFatLabel,memberIdLabel;
private JTextField ratePerFatTextField,memberIdTextField;
private JButton saveButton,cancelButton;
private JRadioButton buyerRateRadioButton,farmerRateRadioButton;
private ButtonGroup rateButtonGroup;


public SetSpecialRatePanel(NavigationButtonsPanel buttonsPanel)
{
this.buttonsPanel=buttonsPanel;
initComponents();
setAppearance();
addListeners();
}
public void initComponents()
{
this.fromDateLabel=new JLabel("From date ");
this.fromDateChooser=new JDateChooser();
this.toDateLabel=new JLabel("To date ");
this.toDateChooser=new JDateChooser();
this.ratePerFatLabel=new JLabel("Rate per fat  ");
this.ratePerFatTextField=new JTextField();
this.memberIdLabel=new JLabel("Member Id");
this.memberIdTextField=new JTextField();
this.farmerRateRadioButton=new JRadioButton("Farmer");
this.buyerRateRadioButton=new JRadioButton("Buyer");
this.rateButtonGroup=new ButtonGroup();
this.rateButtonGroup.add(buyerRateRadioButton);
this.rateButtonGroup.add(farmerRateRadioButton);
this.saveButton=new JButton("save");
this.cancelButton=new JButton("cancel");
}
public void setAppearance()
{
int left=150;
int top=20;
setLayout(null);
fromDateLabel.setBounds(left+50,top+70,200,30);
fromDateLabel.setFont(new Font("Arial",Font.BOLD,26));
fromDateChooser.setBounds(left+95+90+20+10+10,top+70,150,30);
fromDateChooser.setDateFormatString("yyyy-MM-dd");
fromDateChooser.getDateEditor().getUiComponent().setEnabled(false);
fromDateChooser.getCalendarButton().setBackground(new Color(67,130,53));
fromDateChooser.getCalendarButton().setForeground(Color.WHITE);


toDateLabel.setBounds(left+50,top+70+70,200,30);
toDateLabel.setFont(new Font("Arial",Font.BOLD,26));
toDateChooser.setBounds(left+95+90+20+10+10,top+70+70,150,30);
toDateChooser.setDateFormatString("yyyy-MM-dd");
toDateChooser.getDateEditor().getUiComponent().setEnabled(false);
toDateChooser.getCalendarButton().setBackground(new Color(67,130,53));
toDateChooser.getCalendarButton().setForeground(Color.WHITE);



ratePerFatLabel.setBounds(left+50,top+70+70+70,200,30);
ratePerFatLabel.setFont(new Font("Arial",Font.BOLD,26));
ratePerFatTextField.setBounds(left+110+90+20,top+70+70+70,150,30);

memberIdLabel.setBounds(left+50,top+70+70+70+70,200,30);
memberIdLabel.setFont(new Font("Arial",Font.BOLD,26));
memberIdTextField.setBounds(left+110+90+20,top+70+70+70+70,150,30);

buyerRateRadioButton.setBounds(left+50,top+70+70+70+70+70,150,50);
buyerRateRadioButton.setFont(new Font("Arial",Font.ITALIC,26));

farmerRateRadioButton.setBounds(left+50+170,top+70+70+70+70+70,150,50);
farmerRateRadioButton.setFont(new Font("Arial",Font.ITALIC,26));

saveButton.setBounds(left+50,top+70+70+70+70+70+50+30,100,30);
saveButton.setBackground(new Color(67,130,53));
saveButton.setForeground(Color.WHITE);

cancelButton.setBounds(left+100+100+50,top+70+70+70+70+70+50+30,100,30);
cancelButton.setBackground(new Color(67,130,53));
cancelButton.setForeground(Color.WHITE);


add(fromDateLabel);
add(fromDateChooser);
add(toDateLabel);
add(toDateChooser);
add(ratePerFatLabel);
add(ratePerFatTextField);
add(memberIdLabel);
add(memberIdTextField);
add(buyerRateRadioButton);
add(farmerRateRadioButton);
add(saveButton);
add(cancelButton);
}

public void addListeners()
{
saveButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
if(setSpecialRate())
{
clearFields();
buttonsPanel.showPanel("DEFAULT_PANEL");
buttonsPanel.setViewMode();
}
}
});

cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
System.out.println("Rate not saved ");
clearFields();
buttonsPanel.showPanel("DEFAULT_PANEL");
buttonsPanel.setViewMode();
}
});
}


public void clearFields()
{
fromDateChooser.setDate(null);
toDateChooser.setDate(null);
ratePerFatTextField.setText("");
memberIdTextField.setText("");
rateButtonGroup.clearSelection();	
}

public boolean setSpecialRate()
{
java.util.Date fromDate=fromDateChooser.getDate();
java.util.Date toDate=toDateChooser.getDate();

if(fromDate==null && toDate==null)
{
JOptionPane.showMessageDialog(this,"Select date  ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}

if(fromDate==null){
JOptionPane.showMessageDialog(this,"Select starting  date ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}

if(toDate==null){
JOptionPane.showMessageDialog(this,"Select end date ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}


String ratePerFat=ratePerFatTextField.getText().trim();
if(ratePerFat.length()==0) {
JOptionPane.showMessageDialog(this,"Rate per fat required  ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}


for(int i=0;i<ratePerFat.length();i++) {
if(!(Character.isDigit(ratePerFat.charAt(i)) || ratePerFat.charAt(i)=='.')) {
JOptionPane.showMessageDialog(this,"Invalid rate per fat value ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
}

String memberId=memberIdTextField.getText().trim();
if(memberId.length()==0)
{
JOptionPane.showMessageDialog(this,"Member Id required ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}

for(int i=0;i<memberId.length();i++) {
if(!(Character.isDigit(memberId.charAt(i)) || memberId.charAt(i)=='.')) {
JOptionPane.showMessageDialog(this,"Invalid milk value","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
}



if(buyerRateRadioButton.isSelected()==false && farmerRateRadioButton.isSelected()==false){
JOptionPane.showMessageDialog(this,"Select member type (Farmer or Buyer) ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
try{

SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
if(buyerRateRadioButton.isSelected())
{
SpecialBuyerRateManagerInterface specialBuyerRateManager=SpecialBuyerRateManager.getSpecialBuyerRateManager();
SpecialBuyerRateInterface specialBuyerRate=new SpecialBuyerRate();
specialBuyerRate.setBuyerId(Integer.parseInt(memberId));
specialBuyerRate.setStartDate(fromDate);
specialBuyerRate.setEndDate(toDate);
specialBuyerRate.setRatePerFat(new BigDecimal(ratePerFat));
specialBuyerRateManager.add(specialBuyerRate);
String message="Member Id "+memberId+"\n\n"+"Start date : "+sdf.format(fromDate)+"\n\n"+"End date : "+sdf.format(toDate)+"\n\n"+"Rate per fat : "+ratePerFat+"\n\n"+"Set more rate ? ";
int result=JOptionPane.showConfirmDialog(this,message,"ARPITA MILK DAIRY",JOptionPane.YES_NO_OPTION);
if(result==JOptionPane.YES_OPTION)
{
clearFields();
return false;
}
clearFields();
return true;
}else
{
SpecialFarmerRateManagerInterface specialFarmerRateManager=SpecialFarmerRateManager.getSpecialFarmerRateManager();
SpecialFarmerRateInterface specialFarmerRate=new SpecialFarmerRate();
specialFarmerRate.setFarmerId(Integer.parseInt(memberId));
specialFarmerRate.setStartDate(fromDate);
specialFarmerRate.setEndDate(toDate);
specialFarmerRate.setRatePerFat(new BigDecimal(ratePerFat));
specialFarmerRateManager.add(specialFarmerRate);
String message="Start date : "+sdf.format(fromDate)+"\n\n"+"End date : "+sdf.format(toDate)+"\n\n"+"Rate per fat : "+ratePerFat+"\n\n"+"Set more rate ? ";
int result=JOptionPane.showConfirmDialog(this,message,"ARPITA MILK DAIRY",JOptionPane.YES_NO_OPTION);
if(result==JOptionPane.YES_OPTION)
{
clearFields();
return false;
}
clearFields();
return true;

}
}catch(BLException blException)
{
System.out.println("blException is occured ");
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
return true;
}//setSpecialRate ends here


}
