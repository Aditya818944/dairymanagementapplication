package com.dairy.management.application.pl.ui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.toedter.calendar.*;
public class ApplicationUI extends JFrame
{
//daily report view is left
private Container container;
private CardLayout cardLayout;
private JPanel mainPanel;
private NavigationButtonsPanel buttonsPanel;
private DefaultPanel defaultPanel;
private SellMilkPanel sellMilkPanel;
private SellMilkMonthlyPanel sellMilkMonthlyPanel;
private BuyMilkPanel buyMilkPanel;
private PurchaseMilkMonthlyPanel purchaseMilkMonthlyPanel;
private UpdatePurchasedMilkPanel updatePurchasedMilkPanel;
private UpdateSoldMilkPanel updateSoldMilkPanel;
private AddMember addMember;
private UpdateMember updateMember;
private SetSpecialRatePanel setSpecialRatePanel;
private SetRatePanel setRatePanel;
private PaymentSummaryPanel paymentSummaryPanel;
public ApplicationUI()
{
initComponents();
setAppearance();
}
public void showPanel(String panelName)
{
cardLayout.show(mainPanel,panelName);
}
public void initComponents()
{
cardLayout=new CardLayout();
mainPanel=new JPanel(cardLayout);
buttonsPanel=new NavigationButtonsPanel(this);
defaultPanel=new DefaultPanel(buttonsPanel);
sellMilkPanel=new SellMilkPanel(buttonsPanel);
sellMilkMonthlyPanel=new SellMilkMonthlyPanel(buttonsPanel);
buyMilkPanel=new BuyMilkPanel(buttonsPanel);
purchaseMilkMonthlyPanel=new PurchaseMilkMonthlyPanel(buttonsPanel);
updatePurchasedMilkPanel=new UpdatePurchasedMilkPanel(buttonsPanel);
updateSoldMilkPanel=new UpdateSoldMilkPanel(buttonsPanel);
addMember=new AddMember(buttonsPanel);
updateMember=new UpdateMember(buttonsPanel);
setSpecialRatePanel=new SetSpecialRatePanel(buttonsPanel);
setRatePanel=new SetRatePanel(buttonsPanel);
paymentSummaryPanel=new PaymentSummaryPanel(buttonsPanel);
this.container=getContentPane();
this.container.setLayout(null);
}
public void setAppearance()
{
 try
{
UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
UIManager.put("OptionPane.background", new Color(245,248,243));
UIManager.put("Panel.background", new Color(245,248,243));
UIManager.put("Button.background", new Color(67,130,53));
UIManager.put("Button.foreground", new Color(245,245,245));
UIManager.put("OptionPane.messageForeground",new Color(50,50,50));
UIManager.put("Button.font",new Font("Segoe UI", Font.BOLD, 14));
UIManager.put("OptionPane.messageFont",new Font("Segoe UI", Font.PLAIN, 15));
}
catch(Exception e){
// do nothing
}

int left,top;
left=50;
top=70;

this.mainPanel.setBounds(left+300,top,800,500);
this.mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
this.mainPanel.setBackground(new Color(245, 245, 245));



this.mainPanel.add(defaultPanel,"DEFAULT_PANEL");
this.mainPanel.add(sellMilkPanel,"SELL_MILK");
this.mainPanel.add(sellMilkMonthlyPanel,"SELL_MILK_MONTHLY");
this.mainPanel.add(buyMilkPanel,"BUY_MILK");
this.mainPanel.add(purchaseMilkMonthlyPanel,"BUY_MILK_MONTHLY");
this.mainPanel.add(updatePurchasedMilkPanel,"UPDATE_PURCHASED_MILK");
this.mainPanel.add(updateSoldMilkPanel,"UPDATE_SOLD_MILK");
this.mainPanel.add(addMember,"ADD_MEMBER");
this.mainPanel.add(updateMember,"UPDATE_MEMBER");
this.mainPanel.add(setSpecialRatePanel,"SET_SPECIAL_RATE");
this.mainPanel.add(setRatePanel,"SET_RATE");
this.mainPanel.add(paymentSummaryPanel,"PAYMENT_SUMMARY");

this.container.add(buttonsPanel);
this.container.add(mainPanel);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation(0,0);
setSize(d.width,d.height);
}
}