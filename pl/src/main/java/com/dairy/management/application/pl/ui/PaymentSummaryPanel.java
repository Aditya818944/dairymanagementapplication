package com.dairy.management.application.pl.ui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.toedter.calendar.*;
public class PaymentSummaryPanel extends JPanel
{
private JButton farmerLedgerButton,buyerLedgerButton,allLedgerButton,checkProfitLossButton;
private JButton cancelButton;
private NavigationButtonsPanel buttonsPanel;
public PaymentSummaryPanel(NavigationButtonsPanel buttonsPanel)
{
this.buttonsPanel=buttonsPanel;
initComponents();
setAppearance();
addListeners();
}
public void initComponents()
{
farmerLedgerButton=new JButton("Farmer Ledger");
buyerLedgerButton=new JButton("Buyer Ledger");
allLedgerButton=new JButton("Combined Ledger");
checkProfitLossButton=new JButton("P&L Report");
cancelButton=new JButton("Cancel");
}
public void setAppearance()
{
int left=130;
int top=150;
setLayout(null);
farmerLedgerButton.setBounds(left+40,top,200,50);
farmerLedgerButton.setBackground(new Color(67,130,53));
farmerLedgerButton.setForeground(Color.WHITE);


buyerLedgerButton.setBounds(left+40+200+50,top,200,50);
buyerLedgerButton.setBackground(new Color(67,130,53));
buyerLedgerButton.setForeground(Color.WHITE);

allLedgerButton.setBounds(left+40,top+100,200,50);
allLedgerButton.setBackground(new Color(67,130,53));
allLedgerButton.setForeground(Color.WHITE);

checkProfitLossButton.setBounds(left+40+200+50,top+100,200,50);
checkProfitLossButton.setBackground(new Color(67,130,53));
checkProfitLossButton.setForeground(Color.WHITE);

cancelButton.setBounds(left+40+130,top+200,200,50);
cancelButton.setBackground(new Color(67,130,53));
cancelButton.setForeground(Color.WHITE);


add(farmerLedgerButton);
add(buyerLedgerButton);
add(allLedgerButton);
add(checkProfitLossButton);
add(cancelButton);
}




public void addListeners()
{
this.farmerLedgerButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
Window window = SwingUtilities.getWindowAncestor(PaymentSummaryPanel.this);
FarmerLedgerDialog dialog = new FarmerLedgerDialog(window);
dialog.setVisible(true); 
}});

this.buyerLedgerButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
Window window = SwingUtilities.getWindowAncestor(PaymentSummaryPanel.this);
BuyerLedgerDialog dialog = new BuyerLedgerDialog(window);
dialog.setVisible(true); 
}});

this.allLedgerButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
Window window = SwingUtilities.getWindowAncestor(PaymentSummaryPanel.this);
CombinedLedgerDialog dialog = new CombinedLedgerDialog(window);
dialog.setVisible(true); 
}});

this.checkProfitLossButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent){
Window window = SwingUtilities.getWindowAncestor(PaymentSummaryPanel.this);
ProfitAndLossReportDialog dialog = new ProfitAndLossReportDialog(window);
dialog.setVisible(true); 
}});

this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent)
{
System.out.println("cancel of PaymentSummaryPanel is clicked ");
buttonsPanel.showPanel("DEFAULT_PANEL");
buttonsPanel.setViewMode();

}
});
}
}