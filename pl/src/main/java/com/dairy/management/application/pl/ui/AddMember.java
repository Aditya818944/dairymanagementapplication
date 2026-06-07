package com.dairy.management.application.pl.ui;
import com.dairy.management.application.bl.interfaces.pojo.*;
import com.dairy.management.application.bl.interfaces.managers.*;
import com.dairy.management.application.bl.pojo.*;
import com.dairy.management.application.bl.managers.*;
import com.dairy.management.application.bl.exceptions.*;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import com.toedter.calendar.*;
public class AddMember extends JPanel
{
private NavigationButtonsPanel buttonsPanel;
private JLabel nameLabel,addressLabel,phoneNumberLabel,typeLabel;
private JTextField nameTextField,addressTextField,phoneNumberTextField;
private JComboBox typeComboBox;
private JButton addButton,cancelButton;
private JRadioButton buyerRadioButton,farmerRadioButton;
private ButtonGroup radioButtonGroup;

public AddMember(NavigationButtonsPanel buttonsPanel)
{
this.buttonsPanel=buttonsPanel;
initComponents();
setAppearance();
addListeners();
}
public void initComponents()
{
nameLabel=new JLabel("Name");
addressLabel=new JLabel("Address");
phoneNumberLabel=new JLabel("Phone No.");
nameTextField=new JTextField();
addressTextField=new JTextField();
phoneNumberTextField=new JTextField();
typeLabel=new JLabel("Type ");
typeComboBox=new JComboBox();
typeComboBox.addItem("<select>");
typeComboBox.addItem("SPECIAL");
typeComboBox.addItem("REGULAR");
farmerRadioButton=new JRadioButton("Farmer");
buyerRadioButton=new JRadioButton("Buyer");
radioButtonGroup=new ButtonGroup();
radioButtonGroup.add(farmerRadioButton);
radioButtonGroup.add(buyerRadioButton);
addButton=new JButton("Add");
cancelButton=new JButton("Cancel");
}
public void setAppearance()
{
int left,top;
left=150;
top=20;
nameLabel.setBounds(left+50,top+70,100,50);
nameLabel.setFont(new Font("Arial",Font.BOLD,26));
nameTextField.setBounds(left+50+100+30+10+10,top+80,200,30);

addressLabel.setBounds(left+50,top+70+70,150,50);
addressLabel.setFont(new Font("Arial",Font.BOLD,26));
addressTextField.setBounds(left+50+130+10+10,top+70+70+10,200,30);


phoneNumberLabel.setBounds(left+50,top+70+70+70,200,50);
phoneNumberLabel.setFont(new Font("Arial",Font.BOLD,26));
phoneNumberTextField.setBounds(left+50+150,top+70+70+70+10,200,30);


typeLabel.setBounds(left+50,top+70+70+70+70,100,50);
typeLabel.setFont(new Font("Arial",Font.BOLD,26));
typeComboBox.setBounds(left+50+150,top+70+70+70+70+20,200,30);
typeComboBox.setBackground(new Color(67,130,53));
typeComboBox.setForeground(Color.WHITE);



buyerRadioButton.setBounds(left+50,top+70+70+70+70+70,150,50);
buyerRadioButton.setFont(new Font("Arial",Font.ITALIC,26));

farmerRadioButton.setBounds(left+50+170,top+70+70+70+70+70,150,50);
farmerRadioButton.setFont(new Font("Arial",Font.ITALIC,26));


addButton.setBounds(left+100,top+70+70+70+70+70+70,100,30);
addButton.setBackground(new Color(67,130,53));
addButton.setForeground(Color.WHITE);


cancelButton.setBounds(left+100+100+50,top+70+70+70+70+70+70,100,30);
cancelButton.setBackground(new Color(67,130,53));
cancelButton.setForeground(Color.WHITE);

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
add(addButton);
add(cancelButton);
setLayout(null);
}
public void addListeners()
{
this.addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent)
{
if(addMember()) {
buttonsPanel.showPanel("DEFAULT_PANEL");
buttonsPanel.setViewMode();
}
}
});
this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
clearFields();
buttonsPanel.showPanel("DEFAULT_PANEL");
buttonsPanel.setViewMode();
}
});
}

public void clearFields()
{
nameTextField.setText("");
addressTextField.setText("");
phoneNumberTextField.setText("");
typeComboBox.setSelectedItem("<select>");
radioButtonGroup.clearSelection();
}

public boolean addMember()
{
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
farmer.setName(name);
farmer.setAddress(address);
farmer.setPhoneNumber(phoneNumber);
farmer.setType(type);
FarmerManagerInterface farmerManager=FarmerManager.getFarmerManager();
farmerManager.add(farmer);
String message="Id : "+farmer.getFarmerId()+"\n\n"+"Name : "+name+"\n\n"+"Phone Number : "+phoneNumber+"\n\n"+"Address : "+address+"\n\n"+"Farmer is added , Do you want to add more ?";

int result=JOptionPane.showConfirmDialog(this,message,"ARPITA    MILK   DAIRY ",JOptionPane.YES_NO_OPTION);
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
buyer.setName(name);
buyer.setAddress(address);
buyer.setPhoneNumber(phoneNumber);
buyer.setType(type);
BuyerManagerInterface buyerManager=BuyerManager.getBuyerManager();
buyerManager.add(buyer);
String message="Id : "+buyer.getBuyerId()+"\n\n"+"Name : "+name+"\n\n"+"Phone Number : "+phoneNumber+"\n\n"+"Address : "+address+"\n\n"+"Buyer is added , Do you want to add more ?";


int result=JOptionPane.showConfirmDialog(this,message,"Do you want to add more  ? ",JOptionPane.YES_NO_OPTION);
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
} // addMember function end's here 


}
