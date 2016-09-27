package org.forms;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * IP地址控件类
 * @author tcwg
 *
 */
public class IPText extends Composite implements VerifyListener {
  private Text text_1;
  private Text text_2;
  private Text text_3;
  private Text text_4;
  
  
  public IPText(Composite parent, int style) {
    super(parent, SWT.BORDER);
    
    this.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
	this.setBounds(40, 10, 270, 70);
	this.setVisible(true);

	text_1 = new Text(this, SWT.NONE);//设置文字子体位置居中
    text_1.setFont(SWTResourceManager.getFont("宋体", 12, SWT.BOLD));//设置文本子体大小
    text_1.setBackground(SWTResourceManager.getColor(204, 232, 207));
	text_1.setLocation(8, 0);
	text_1.setSize(32, 20);
	text_1.addVerifyListener(this);
	text_1.setTextLimit(3);

	Label label = new Label(this, SWT.NONE);
	label.setBackground(SWTResourceManager.getColor(204, 232, 207));//改变文本框颜色（曾为白色）
	label.setBounds(46, 0, 3, 23);
	label.setText(".");

	text_2 = new Text(this, SWT.CENTER);//设置文字子体位置居中
	text_2.setFont(SWTResourceManager.getFont("宋体", 12, SWT.BOLD));//设置文本子体大小
	text_2.setBackground(SWTResourceManager.getColor(204, 232, 207));
	text_2.setBounds(52, 0, 32, 20);
	text_2.addVerifyListener(this);
	text_2.setTextLimit(3);

	Label label_1 = new Label(this, SWT.NONE);
	label_1.setBackground(SWTResourceManager.getColor(204, 232, 207));//改变文本框颜色（曾为白色）
	label_1.setBounds(87, 0, 3, 20);
	label_1.setText(".");

	text_3 = new Text(this, SWT.CENTER);//设置文字子体位置居中
	text_3.setFont(SWTResourceManager.getFont("宋体", 12, SWT.BOLD));//设置文本子体大小
	text_3.setBackground(SWTResourceManager.getColor(204, 232, 207));
	text_3.setBounds(96, 0, 26, 20);
	text_3.addVerifyListener(this);
	text_3.setTextLimit(3);

	Label label_2 = new Label(this, SWT.NONE);
	label_2.setBackground(SWTResourceManager.getColor(204, 232, 207));//改变文本框颜色（曾为白色）
	label_2.setBounds(128, 0, 3, 23);
	label_2.setText(".");

	text_4 = new Text(this, SWT.CENTER);//设置文字子体位置居中
	text_4.setFont(SWTResourceManager.getFont("宋体", 12, SWT.BOLD));//设置文本子体大小
	text_4.setBackground(SWTResourceManager.getColor(204, 232, 207));
	text_4.setBounds(137, 0, 26, 20);
	text_4.addVerifyListener(this);
	text_4.setTextLimit(3);


  }
  

  /**
   * 监听验证事件
   */
  public void verifyText(VerifyEvent e) {
    Text text=(Text)e.getSource();
    if(".0123456789".indexOf(e.text)<0) {
        e.doit=false;
        return;
    } else if(e.text.equals(".")) {
        nextFocus(text);
        e.doit=false;
        return;
    }
    String s=text.getText();
    s=s.substring(0,text.getSelection().x)+s.substring(text.getSelection().y);
    int position=text.getCaretPosition();//得到光标所在位置
    //
    
    if(position >= s.length())
    	position = s.length();
    //
    if(text==text_1) {
        StringBuffer buffer=new StringBuffer(s);
        s=buffer.insert(position,e.text).toString();
        if( StringtoInt(s)>255)
          e.doit=false;
        else if(position==0)
          e.doit = !e.text.equals("0");
        else if(position!=0)
          e.doit=true;
    } else if(s.equals("0")&&position==0) {
        if(e.text.equals("0"))
          e.doit=false;
        else
          e.doit=true;
    } else if(s.equals("0")&&position!=0) {
        e.doit=false;
    } else {
        StringBuffer buffer=new StringBuffer(s);
        s=buffer.insert(position,e.text).toString();
        System.out.println(position);//=============
        if(StringtoInt(s)>255)
          e.doit=false;
        else
          e.doit=true;
    }
    if(text==text_1&& StringtoInt(s)>255
        ||text!=text_1&&StringtoInt(s)>255)
        return;
    if(s.length()>=text.getTextLimit()) {
        nextFocus(text);
    }
  }

  private void nextFocus(Text text) {
    if(text==text_1) {
        text_2.setFocus();
        text_2.selectAll();
    } else if(text==text_2) {
        text_3.setFocus();
        text_3.selectAll();
    } else if(text==text_3) {
        text_4.setFocus();
        text_4.selectAll();
    }
  }
  
  /**
   * 设置可用状态
   * @param text
   */
  public void setEnabled(boolean val){
	  Control[] ctrl = this.getChildren();
	  for(int i=0;i<ctrl.length;i++){
		  ctrl[i].setEnabled(val);
	  }
	  if(val==true)
		  this.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
	  else
		  this.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
  }
  /**
   * 设置内容
   * @param text
   */
  public void setText(String text) {
    if(!isValid(text)){
    	System.out.println("指定的IP地址无效");
    	return;    	
    }
        
    String[] ip=text.split("\\.");
    text_1.removeVerifyListener(this);
    text_2.removeVerifyListener(this);
    text_3.removeVerifyListener(this);
    text_4.removeVerifyListener(this);
    text_1.setText(ip[0]);
    text_2.setText(ip[1]);
    text_3.setText(ip[2]);
    text_4.setText(ip[3]);
    text_1.addVerifyListener(this);
    text_2.addVerifyListener(this);
    text_3.addVerifyListener(this);
    text_4.addVerifyListener(this);
  }
  
  /**
   * 获取内容
   * @param
   */
  public String getText() {
	  String s1 = text_1.getText();
	  String s2 = text_2.getText();
	  String s3 = text_3.getText();
	  String s4 = text_4.getText();
	  if(s1.equals("")||s2.equals("")||s3.equals("")||s4.equals(""))
		  return "";
	  else
		  return s1 +"."+ s2 +"."+ s3 +"."+ s4;
  }
  
  /**
   * 获取内容字符串
   * @param 
   */
  public String toString() {
    return getText();
  }
  
  
  /**
   * 数字验证
   * @param 
   */
  private boolean isValid(String source) {
    String reg="^(22[0-3]|2[0-1]\\d|1\\d{2}|[1-9]\\d|[1-9])"
          +"\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)"
          +"\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)"
          +"\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)$";
    Pattern p=Pattern.compile(reg);
    Matcher m=p.matcher(source);
    return m.find();
  }
  
  /**
   * 数值转换
   * @param 
   */
  private int StringtoInt(String s){
	  if(s == null || s.equals(""))
	  		return 0;
	  	else 
	  		return Integer.parseInt(s);	  
  }
}