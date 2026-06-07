package com.dairy.management.application.pl.ui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.toedter.calendar.*;
import java.util.*;
public class NavigationButtonsPanel extends JPanel
{
private enum Mode{VIEW,SALE,EDITSALE,PURCHASE,EDITPURCHASE,PAYMENTSUMMARY,DAILYREPORT,ADDMEMBER,EDITMEMBER,SETRATE};
private Mode mode;
private ApplicationUI app;
private JButton sellButton,editSaleButton,buyMilkButton,editPurchaseButton,paymentSummaryButton;
private JButton addMemberButton,editMemberButton,setRateButton;
public NavigationButtonsPanel(ApplicationUI app)
{
this.app=app;
initComponents();
setAppearance();
addListeners();
this.setViewMode();
}
public static int getHour()
{
return new Date().getHours();
}
public void showPanel(String str)
{
app.showPanel(str);
}
public void initComponents()
{
sellButton=new JButton("Sell Milk");
editSaleButton=new JButton("Edit Sale");
buyMilkButton=new JButton("Buy Milk");
editPurchaseButton=new JButton("Edit Purchase");
paymentSummaryButton=new JButton("Payment Summary");
addMemberButton=new JButton("Add Member");
editMemberButton=new JButton("Edit Member");
setRateButton=new JButton("Set Rate");
}
public void setAppearance()
{
setBackground(new Color(36, 66, 43));




Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int left,top;
left=10;
top=30;
setLayout(null);
setBounds(left+30,top+600,d.width-130,70);
setBorder(BorderFactory.createLineBorder(Color.black));

int pleft,ptop;
pleft=10;
ptop=20;

sellButton.setBounds(pleft,ptop,140,30);
sellButton.setBackground(new Color(67,130,53));
sellButton.setForeground(Color.WHITE);
add(sellButton);

editSaleButton.setBounds(pleft+150+10+20,ptop,140,30);
editSaleButton.setBackground(new Color(67,130,53));
editSaleButton.setForeground(Color.WHITE);

add(editSaleButton);

buyMilkButton.setBounds(pleft+300+10+10+40,ptop,140,30);
buyMilkButton.setBackground(new Color(67,130,53));
buyMilkButton.setForeground(Color.WHITE);
add(buyMilkButton);

editPurchaseButton.setBounds(pleft+450+10+10+10+60,ptop,140,30);
editPurchaseButton.setBackground(new Color(67,130,53));
editPurchaseButton.setForeground(Color.WHITE);
add(editPurchaseButton);

paymentSummaryButton.setBounds(pleft+600+10+10+10+10+20+60,ptop,140,30);
paymentSummaryButton.setBackground(new Color(67,130,53));
paymentSummaryButton.setForeground(Color.WHITE);
add(paymentSummaryButton);


addMemberButton.setBounds(pleft+890+10,ptop,140,30);
addMemberButton.setBackground(new Color(67,130,53));
addMemberButton.setForeground(Color.WHITE);
add(addMemberButton);

editMemberButton.setBounds(pleft+1030+20+10+10,ptop,140,30);
editMemberButton.setBackground(new Color(67,130,53));
editMemberButton.setForeground(Color.WHITE);
add(editMemberButton);

setRateButton.setBounds(pleft+1180+20+10+10+10+10,ptop,140,30);
setRateButton.setBackground(new Color(67,130,53));
setRateButton.setForeground(Color.WHITE);
add(setRateButton);
}



public void addListeners()
{

//this.cardLayout.show(mainPanel,"DEFAULT_PANEL");





sellButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){




String [] options={"Daily Entry","Monthly Entry","Cancel"};

int selectedOption=JOptionPane.showOptionDialog(app,"Do you want to add today’s milk record or enter data for the whole month?","Select Entry Type ",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
if(selectedOption==0)
{
showPanel("SELL_MILK");
disableButtonsMode();
}else if(selectedOption==1)
{
showPanel("SELL_MILK_MONTHLY");
disableButtonsMode();
}else
{
// don nothing
}
}
}); // sellButtonAddActionListener end's here 





editSaleButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
showPanel("UPDATE_SOLD_MILK");
disableButtonsMode();
}
}); 



buyMilkButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae){

String [] options={"Daily Entry","Monthly Entry","Cancel"};

int selectedOption=JOptionPane.showOptionDialog(app,"Do you want to add today’s milk record or enter data for the whole month?","Select Entry Type ",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
if(selectedOption==0)
{
showPanel("BUY_MILK");
disableButtonsMode();
}else if(selectedOption==1)
{
showPanel("BUY_MILK_MONTHLY");
disableButtonsMode();
}else
{
// don nothing
}
}
}); // buyMilkButton.addActionListener end's here 









editPurchaseButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae){
showPanel("UPDATE_PURCHASED_MILK");
disableButtonsMode();
}
});







paymentSummaryButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae){
showPanel("PAYMENT_SUMMARY");
disableButtonsMode();
}
});






addMemberButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
showPanel("ADD_MEMBER");
disableButtonsMode();
}
});








editMemberButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
showPanel("UPDATE_MEMBER");
disableButtonsMode();
}
});









setRateButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
int selectedOption=JOptionPane.showConfirmDialog(app,"Set special rate ?","Set special rate  ",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.YES_OPTION)
{
showPanel("SET_SPECIAL_RATE");
disableButtonsMode();
}else if(selectedOption==JOptionPane.NO_OPTION)
{
app.showPanel("SET_RATE");
disableButtonsMode();
}else
{
// do nothing
}
}
});
} // addListeners end's here 







public void setViewMode()
{
this.mode=Mode.VIEW;
sellButton.setVisible(true);
sellButton.setEnabled(true);

editSaleButton.setVisible(true);
editSaleButton.setEnabled(true);

buyMilkButton.setVisible(true);
buyMilkButton.setEnabled(true);

editPurchaseButton.setVisible(true);
editPurchaseButton.setEnabled(true);

paymentSummaryButton.setVisible(true);
paymentSummaryButton.setEnabled(true);


addMemberButton.setVisible(true);
addMemberButton.setEnabled(true);

editMemberButton.setVisible(true);
editMemberButton.setEnabled(true);

setRateButton.setVisible(true);
setRateButton.setEnabled(true);
}

public void disableButtonsMode()
{
this.mode=Mode.SALE;
sellButton.setVisible(true);
sellButton.setEnabled(false);

editSaleButton.setVisible(true);
editSaleButton.setEnabled(false);

buyMilkButton.setVisible(true);
buyMilkButton.setEnabled(false);

editPurchaseButton.setVisible(true);
editPurchaseButton.setEnabled(false);

paymentSummaryButton.setVisible(true);
paymentSummaryButton.setEnabled(false);

addMemberButton.setVisible(true);
addMemberButton.setEnabled(false);

editMemberButton.setVisible(true);
editMemberButton.setEnabled(false);

setRateButton.setVisible(true);
setRateButton.setEnabled(false);
}

}
