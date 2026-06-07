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
public class BuyMilkPanel extends JPanel
{
private JDateChooser dateChooser;
private NavigationButtonsPanel buttonsPanel;
private JLabel shiftLabel,milkInLitersLabel,farmerIdLabel,fatValueLabel,dateChooserLabel;
private JRadioButton morningRadioButton,eveningRadioButton;
private JButton saveButton,cancelButton;
private ButtonGroup shiftButtonGroup;
private JTextField milkInLitersTextField,farmerIdTextField,fatValueTextField;
public BuyMilkPanel(NavigationButtonsPanel buttonsPanel)
{
this.buttonsPanel=buttonsPanel;
initComponent();
setAppearance();
addListeners();
}
public void initComponent()
{
this.dateChooserLabel=new JLabel("Date ");
this.dateChooser=new JDateChooser();
this.shiftLabel=new JLabel("Shift");
this.milkInLitersLabel=new JLabel("Milk in liters");
this.milkInLitersTextField=new JTextField();
this.farmerIdLabel=new JLabel("Farmer Id ");
this.farmerIdTextField=new JTextField();
this.fatValueLabel=new JLabel("Fat value ");
this.fatValueTextField=new JTextField();
this.morningRadioButton=new JRadioButton("AM");
this.eveningRadioButton=new JRadioButton("PM");
this.shiftButtonGroup=new ButtonGroup();
this.shiftButtonGroup.add(morningRadioButton);
this.shiftButtonGroup.add(eveningRadioButton);
this.saveButton=new JButton("Save");
this.cancelButton=new JButton("Cancel");
}
public void setAppearance()
{

Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int left,top;
left=150;
top=100;
setLayout(null);

dateChooserLabel.setBounds(left+50,top+20,200,30);
dateChooserLabel.setFont(new Font("Arial",Font.BOLD,26));
dateChooser.setBounds(left+220,top+20,150,30);
dateChooser.setDateFormatString("yyyy-MM-dd");
dateChooser.getDateEditor().getUiComponent().setEnabled(false);
dateChooser.setDate(new java.util.Date());

shiftLabel.setBounds(left+50,top+70,100,50);
shiftLabel.setFont(new Font("Arial",Font.BOLD,26));
morningRadioButton.setBounds(left+50+160,top+70,100,50);
morningRadioButton.setFont(new Font("Arial",Font.ITALIC,26));
eveningRadioButton.setBounds(left+50+150+10+100,top+70,100,50);
eveningRadioButton.setFont(new Font("Arial",Font.ITALIC,26));

morningRadioButton.setEnabled(false);
eveningRadioButton.setEnabled(false);

int hrs=NavigationButtonsPanel.getHour();
if(hrs<12)
{
morningRadioButton.setSelected(true);
}else 
{
eveningRadioButton.setSelected(true);
}

farmerIdLabel.setBounds(left+50,top+70+70,150,30);
farmerIdLabel.setFont(new Font("Arial",Font.BOLD,26));
farmerIdTextField.setBounds(left+50+170,top+70+70,150,30);
milkInLitersLabel.setBounds(left+50,top+70+70+70,200,30);
milkInLitersLabel.setFont(new Font("Arial",Font.BOLD,26));
milkInLitersTextField.setBounds(left+50+170,top+70+70+70,150,30);
fatValueLabel.setBounds(left+50,top+70+70+70+70,150,30);
fatValueLabel.setFont(new Font("Arial",Font.BOLD,26));
fatValueTextField.setBounds(left+50+170,top+70+70+70+70,150,30);

saveButton.setBounds(left+50,top+70+70+70+120,100,30);
saveButton.setBackground(new Color(67,130,53));
saveButton.setForeground(Color.WHITE);




cancelButton.setBounds(left+50+100+30+70,top+70+70+70+120,100,30);
cancelButton.setBackground(new Color(67,130,53));
cancelButton.setForeground(Color.WHITE);


add(dateChooserLabel);
add(dateChooser);
add(shiftLabel);
add(morningRadioButton);
add(eveningRadioButton);
add(farmerIdLabel);
add(farmerIdTextField);
add(milkInLitersLabel);
add(milkInLitersTextField);
add(fatValueLabel);
add(fatValueTextField);
add(saveButton);
add(cancelButton);
}
public void addListeners()
{
this.saveButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent)
{
if(buyMilk()){
buttonsPanel.showPanel("DEFAULT_PANEL");
buttonsPanel.setViewMode();
}
}
});
this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent)
{
clearFields();
buttonsPanel.showPanel("DEFAULT_PANEL");
buttonsPanel.setViewMode();
}
});
}

public void clearFields()
{
farmerIdTextField.setText("");
milkInLitersTextField.setText("");
fatValueTextField.setText("");
}

public boolean  buyMilk()
{
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

String milkInLiters=milkInLitersTextField.getText().trim();
if(milkInLiters.length()==0)
{
JOptionPane.showMessageDialog(this,"Milk in liters field  required","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}

for(int i=0;i<milkInLiters.length();i++) {
if(!(Character.isDigit(milkInLiters.charAt(i)) || milkInLiters.charAt(i)=='.')) {
JOptionPane.showMessageDialog(this,"Invalid milk value","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
}

String fatValue=fatValueTextField.getText().trim();
if(fatValue.length()==0) 
{
JOptionPane.showMessageDialog(this,"Fat value required ","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}

for(int i=0;i<fatValue.length();i++) {
if(!(Character.isDigit(fatValue.charAt(i)) || fatValue.charAt(i)=='.')) {
JOptionPane.showMessageDialog(this,"Invalid fat value","Errors",JOptionPane.ERROR_MESSAGE);
return false;
}
}

java.util.Date collectionDate=this.dateChooser.getDate();
String shift="";
if(morningRadioButton.isSelected()) shift="AM";
else shift="PM";
try
{
MilkCollectionManagerInterface milkCollectionManager=MilkCollectionManager.getMilkCollectionManager();
MilkCollectionInterface milkCollection=new MilkCollection();
milkCollection.setCollectionDate(collectionDate);
milkCollection.setShift(shift);
milkCollection.setFarmerId(Integer.parseInt(farmerId));
milkCollection.setMilkInLiters(new BigDecimal(milkInLiters));
milkCollection.setFatValue(new BigDecimal(fatValue));
milkCollectionManager.add(milkCollection);
String message="Id : "+farmerId+"\n\n"+"Fat value : "+fatValue+"\n\n"+"Milk in Liters : "+milkInLiters+"\n\n"+"Amount : "+milkCollection.getAmount().toString()+"\n\n"+"Milk is collected ,  Do you want to collect more ?";
int result=JOptionPane.showConfirmDialog(this,message,"ARPITA MILK DAIRY",JOptionPane.YES_NO_OPTION);
if(result==JOptionPane.YES_OPTION)
{
clearFields();
return false;
}
clearFields();
return true;
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
}// buyMilk ends here


}

