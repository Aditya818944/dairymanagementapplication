package com.dairy.management.application.pl.ui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import com.toedter.calendar.*;
public class DefaultPanel extends JPanel
{
private NavigationButtonsPanel buttonsPanel;
private Image defaultImage;
public DefaultPanel(NavigationButtonsPanel buttonsPanel)
{
this.buttonsPanel=buttonsPanel;
initComponents();
setAppearance();
}
public void initComponents()
{
defaultImage=new ImageIcon("c:"+File.separator+"dairymanagementapplication"+File.separator+"Images"+File.separator+"defaultPanelImage.png").getImage();
}
protected void paintComponent(Graphics g)
{
super.paintComponent(g);
Graphics2D g2d = (Graphics2D) g;
g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
g2d.drawImage(defaultImage, 0, 0, getWidth(), getHeight(), this);
}
public void setAppearance()
{
setLayout(null);
}
}