package com.dairy.management.application.pl.ui;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.interfaces.managers.*;
import com.dairy.management.application.bl.pojo.*;
import com.dairy.management.application.bl.managers.*;
import com.dairy.management.application.bl.exceptions.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.toedter.calendar.*;
class UpdateMember extends JPanel
{
private NavigationButtonsPanel buttonsPanel;
private JLabel memberIdLabel,nameLabel,addressLabel,phoneNumberLabel,typeLabel;
private JTextField memberIdTextField, nameTextField,addressTextField,phoneNumberTextField;
private JComboBox typeComboBox;
private JButton updateButton,cancelButton;
private JRadioButton buyerRadioButton,farmerRadioButton;
private ButtonGroup radioButtonGroup;


public UpdateMember(NavigationButtonsPanel buttonsPanel)
{
this.buttonsPanel=buttonsPanel;
initComponents();
setAppearance();
addListeners();
}
public void initComponents()
{
memberIdLabel=new JLabel("Member Id ");
memberIdTextField=new JTextField();
nameLabel=new JLabel("Name");
addressLabel=new JLabel("Address");
phoneNumberLabel=new JLabel("Phone No.");
nameTextField=new JTextField();
addressTextField=new JTextField();
phoneNumberTextField=new JTextField();
typeLabel=new JLabel("Type");
typeComboBox=new JComboBox();
typeComboBox.addItem("<select>");
typeComboBox.addItem("SPECIAL");
typeComboBox.addItem("REGULAR");
farmerRadioButton=new JRadioButton("Farmer");
buyerRadioButton=new JRadioButton("Buyer");
radioButtonGroup=new ButtonGroup();
radioButtonGroup.add(farmerRadioButton);
radioButtonGroup.add(buyerRadioButton);
updateButton=new JButton("Update");
cancelButton=new JButton("Cancel");
}
public void setAppearance()
{
int left,top;
left=150;
top=50;

memberIdLabel.setBounds(left+50,top,150,50);
memberIdLabel.setFont(new Font("Arial",Font.BOLD,26));
memberIdTextField.setBounds(left+50+150+10,top+10,200,30);

nameLabel.setBounds(left+50,top+60,100,50);
nameLabel.setFont(new Font("Arial",Font.BOLD,26));
nameTextField.setBounds(left+50+100+30+10+10+10,top+70,200,30);

addressLabel.setBounds(left+50,top+70+50,150,50);
addressLabel.setFont(new Font("Arial",Font.BOLD,26));
addressTextField.setBounds(left+50+130+10+10+10,top+70+50+10,200,30);






phoneNumberLabel.setBounds(left+50,top+70+70+50,200,50);
phoneNumberLabel.setFont(new Font("Arial",Font.BOLD,26));
phoneNumberTextField.setBounds(left+50+150+10,top+70+70+50+10,200,30);


typeLabel.setBounds(left+50,top+70+70+70+40,100,50);
typeLabel.setFont(new Font("Arial",Font.BOLD,26));
typeComboBox.setBounds(left+50+160,top+70+70+70+20+40,200,30);
typeComboBox.setBackground(new Color(67,130,53));
typeComboBox.setForeground(Color.WHITE);



buyerRadioButton.setBounds(left+50,top+70+70+70+70+30,150,50);
buyerRadioButton.setFont(new Font("Arial",Font.ITALIC,26));

farmerRadioButton.setBounds(left+50+170,top+70+70+70+70+30,150,50);
farmerRadioButton.setFont(new Font("Arial",Font.ITALIC,26));


updateButton.setBounds(left+100,top+70+70+70+70+70+20,100,30);
updateButton.setBackground(new Color(67,130,53));
updateButton.setForeground(Color.WHITE);

cancelButton.setBounds(left+100+100+50,top+70+70+70+70+70+20,100,30);
cancelButton.setBackground(new Color(67,130,53));
cancelButton.setForeground(Color.WHITE);


add(memberIdLabel);
add(memberIdTextField);
add(nameLabel);
add(nameTextField);
add(addressLabel);
add(addressTextField);
add(phoneNumberLabel);
add(phoneNumberTextField);
add(typeLabel);
add(typeComboBox);
add(buyerRadioButton);
add(farmerRadioButton);
add(updateButton);
add(cancelButton);
setLayout(null);
}

public void clearFields()
{
memberIdTextField.setText("");
nameTextField.setText("");
addressTextField.setText("");
phoneNumberTextField.setText("");
typeComboBox.setSelectedItem("<select>");
radioButtonGroup.clearSelection();
}


public void addListeners()
{
updateButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
if(updateMember()) {
buttonsPanel.showPanel("DEFAULT_PANEL");
buttonsPanel.setViewMode();
}
}
});

cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
System.out.println("Updation canceled ");
clearFields();
buttonsPanel.showPanel("DEFAULT_PANEL");
buttonsPanel.setViewMode();
}
});

}


public boolean updateMember()
{
String memberId=memberIdTextField.getText().trim();
if(memberId.length()==0)
{
JOptionPane.showMessageDialog(this,"Member id required ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}

String name=nameTextField.getText().trim();
if(name.length()==0) 
{
JOptionPane.showMessageDialog(this,"Name required ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
String address=addressTextField.getText().trim();
if(address.length()==0)
{
JOptionPane.showMessageDialog(this,"Address required","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
String phoneNumber=phoneNumberTextField.getText().trim();
if(phoneNumber.length()==0) 
{
JOptionPane.showMessageDialog(this,"Phone number required ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
String type=(String)typeComboBox.getSelectedItem();
if(type.equals("<select>"))
{
JOptionPane.showMessageDialog(this,"Please select type ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
try{
if(farmerRadioButton.isSelected()){
FarmerInterface farmer=new Farmer();
farmer.setFarmerId(Integer.parseInt(memberId));
farmer.setName(name);
farmer.setAddress(address);
farmer.setPhoneNumber(phoneNumber);
farmer.setType(type);
FarmerManagerInterface farmerManager=FarmerManager.getFarmerManager();
farmerManager.update(farmer);

String message="Id : "+farmer.getFarmerId()+"\n\n"+"Name : "+name+"\n\n"+"Phone Number : "+phoneNumber+"\n\n"+"Address : "+address+"\n\n"+"Type : "+type+"\n\n"+"Farmer detail is updated , Do you want to update more ?";

int result=JOptionPane.showConfirmDialog(this,message,"ARPITA   MILK   DAIRY  ",JOptionPane.YES_NO_OPTION);
if(result==JOptionPane.YES_OPTION)
{
clearFields();
return false;
}
clearFields();
return true;
}else if(buyerRadioButton.isSelected())
{
BuyerInterface buyer=new Buyer();
buyer.setBuyerId(Integer.parseInt(memberId));
buyer.setName(name);
buyer.setAddress(address);
buyer.setPhoneNumber(phoneNumber);
buyer.setType(type);
BuyerManagerInterface buyerManager=BuyerManager.getBuyerManager();
buyerManager.update(buyer);
String message="Id : "+buyer.getBuyerId()+"\n\n"+"Name : "+name+"\n\n"+"Phone Number : "+phoneNumber+"\n\n"+"Address : "+address+"\n\n"+"Type : "+type+"\n\n"+"Buyer detail is updated  , Do you want to update more ?";


int result=JOptionPane.showConfirmDialog(this,message,"Do you want to update more  ? ",JOptionPane.YES_NO_OPTION);
if(result==JOptionPane.YES_OPTION)
{
clearFields();
return false;
}
clearFields();
return true;
}
return false;
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
return false;
} // updateMember function end's here 
}
