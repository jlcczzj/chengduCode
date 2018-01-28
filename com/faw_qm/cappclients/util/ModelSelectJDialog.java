package com.faw_qm.cappclients.util;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.faw_qm.cappclients.util.JCheckListBox.CheckListBoxModel;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RequestHelper;


public class ModelSelectJDialog extends JDialog
{
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final int SCREEN_WIDTH = screenSize.width;
	private static final int SCREEN_HEIGHT = screenSize.height;
	
	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private JFrame frame;
	
	private JPanel parentpanel;
	
//    private JButton findButton = new JButton("查找");
    private JButton okButton = new JButton("确认");
	private JButton cancelButton = new JButton("取消");
//	 private static  Vector Allmodel =getAllmode();
	 private   Vector Allmodel;
	 private  final HashMap modelMap =getAllmodeMap();


    JCheckListBox list;

    final JLabel label = new JLabel("当前没有选择。");
    
    String oldmodel="";
    
    String currentModel="";//当前选择的车系
    
    
    
//    public static Vector getAllmode(){
//    	
//		Class[] paraClass = {};
//      Object[] paraObj = {};
//      Vector result = (Vector) RequestHelper.request( 
//	    		"FccBomService","getAllProdcut", paraClass, paraObj);
////    	Vector result=new Vector();
////    	result.add("X80");
////    	result.add("B50");
////    	result.add("B70");
////    	result.add("B80");
//    	return result;
//    }
    
//    public static Vector getAllmode(Vector mode){
//    	 Vector result=null;
//    	if(mode==null||mode.size()==0){
//		Class[] paraClass = {};
//      Object[] paraObj = {};
//      result = (Vector) RequestHelper.request( 
//	    		"FccBomService","getAllProdcut", paraClass, paraObj);
////    	Vector result=new Vector();
////    	result.add("X80");
////    	result.add("B50");
////    	result.add("B70");
////    	result.add("B80");
//    	}
//    	else{
//    		result=mode;
//    	}
//    	return result;
//    }
    
    public  HashMap getAllmodeMap(){
    	HashMap map=new HashMap();
    	if(Allmodel==null||Allmodel.size()==0){
    		Class[] paraClass = {};
    	      Object[] paraObj = {};
    	      try {
				this.Allmodel= (Vector) RequestHelper.request( 
				    		"FccBomService","getAllProdcut", paraClass, paraObj);
			} catch (QMRemoteException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}	
    	      System.out.println("-----Allmodel-"+Allmodel);
    	      
    	}
    	for(int i=0;i<Allmodel.size();i++){
    		map.put(Allmodel.get(i),i);
    	}   	
    	
    	return map;
    }
    
    
    
    

	
	public ModelSelectJDialog(JFrame frame,JPanel parentpanel)
	{
		super(frame,"车系",true);
		this.frame = frame;
		this.parentpanel=parentpanel;
//		this.setUndecorated(true);
		Jbinit();
	}
	
	public ModelSelectJDialog(JFrame frame,JPanel parentpanel,String oldmodel)
	{
		super(frame,"车系",true);
		this.frame = frame;
		this.parentpanel=parentpanel;
		this.oldmodel=oldmodel;
//		this.setUndecorated(true);
		Jbinit();
	}
	
	public ModelSelectJDialog(JFrame frame,JPanel parentpanel,String oldmodel,Vector othermode)
	{
		super(frame,"车系",true);
		this.frame = frame;
		this.parentpanel=parentpanel;
		this.oldmodel=oldmodel;
//		this.setUndecorated(true);
		Jbinit();
	}
	
	void Jbinit(){
		
		Container container = getContentPane(); 
		container.setLayout(gridBagLayout);
		  Font font = new Font("宋体", Font.PLAIN, 12);
		  System.out.println("---map"+modelMap);

//		  list  = new JCheckListBox(new Object[]{"B50","X80","B30","B70"});
		  list  = new JCheckListBox(Allmodel.toArray());
		  list.setFont(font);
		  System.out.println("----oldmodel1="+oldmodel);
			if(!oldmodel.equals("")){
				System.out.println("----oldmodel2="+oldmodel);
				String[] mode=oldmodel.split("/");	
				for(int i=0;i<mode.length;i++){
					if(modelMap.get((String)mode[i])!=null){
				int index=((Integer)modelMap.get((String)mode[i])).intValue();
				System.out.println("-----"+mode[i]+"----"+index);				
				list.setChecked(index);
					}
				}
		}
		  label.setFont(font);
		  
		  list.getModel().addListDataListener(new ListDataListener() {

		  public void intervalAdded(ListDataEvent e) {
		  }

		  public void intervalRemoved(ListDataEvent e) {
		  }

		  public void contentsChanged(ListDataEvent e) {
		  if (list.getCheckedCount() == 0) {
		  	label.setText("当前没有选择。");
		  } else {
		  	String text = "当前选择:";
		  int[] indices = list.getCheckedIndices();
		  for (int i = 0; i < indices.length; i++) {
		  	text += ((CheckListBoxModel) list.getModel()).getItem(indices[i]).toString() + "/";
		  }
		  label.setText(text);
		   }
		  }
		  });
	
		JPanel buttonPanel = new JPanel();
		okButton.setMaximumSize(new Dimension(80, 23));
		okButton.setMinimumSize(new Dimension(80, 23));
		okButton.setPreferredSize(new Dimension(80, 23));
		
		cancelButton.setMaximumSize(new Dimension(80, 23));
		cancelButton.setMinimumSize(new Dimension(80, 23));
		cancelButton.setPreferredSize(new Dimension(80, 23));
		buttonPanel.setLayout(gridBagLayout);	
		
		buttonPanel.add(new JLabel(),new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		buttonPanel.add(okButton,new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		buttonPanel.add(cancelButton,new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		container.add(new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		container.add(buttonPanel,new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		container.add(label,new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		
		addButtonListener();
//		
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//		setBounds((SCREEN_WIDTH-300)/2, (SCREEN_HEIGHT-400)/2, 300, 400);
//		setSize(200,300);
//		setVisible(true);

	}
	
	private void addButtonListener()
	{
		
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					okEvent();
				} catch (Exception e1) 
				{
					JOptionPane.showMessageDialog(frame,"添加车系时出现错误！","提示",JOptionPane.INFORMATION_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
			}
		});
	}
	

	
//	private void okEvent() throws Exception
//	{
//		int modelcol=7;
//		ComponentMultiList checkboxMultiList=null;
//		if(parentpanel instanceof StationEquimentLinkPanel){
//		StationEquimentLinkPanel p=	(StationEquimentLinkPanel)parentpanel;
//		checkboxMultiList=p.checkboxMultiList;
//		modelcol=p.getModelCol();
//		}
//		else if(parentpanel instanceof StationToolLinkPanel){
//			StationToolLinkPanel p=	(StationToolLinkPanel)parentpanel;
//			checkboxMultiList=p.checkboxMultiList;
//			modelcol=p.getModelCol();
//		}
//		else if(parentpanel instanceof StationCommonToolLinkPanel){
//			StationCommonToolLinkPanel p=	(StationCommonToolLinkPanel)parentpanel;
//			checkboxMultiList=p.checkboxMultiList;
//			modelcol=p.getModelCol();
//		}
//		else if(parentpanel instanceof StationMatLinkPanel){
//			StationMatLinkPanel p=	(StationMatLinkPanel)parentpanel;
//			checkboxMultiList=p.checkboxMultiList;
//			modelcol=p.getModelCol();
//		}
//		int row=checkboxMultiList.getSelectedRow();
//		int[] rows = list.getCheckedIndices();
//		if(rows.length==0)
//		{
//			JOptionPane.showMessageDialog(frame,"无可选择的车系","提示",JOptionPane.INFORMATION_MESSAGE);
//			return;
//		}
//        String modelstr="";
//		for(int i=0;i<rows.length;i++)
//		{
//			String amodel=((CheckListBoxModel) list.getModel()).getItem(rows[i]).toString();
//			if(modelstr.equals(""))
//			modelstr=amodel;
//			else
//		    modelstr=modelstr+"/"+amodel;                 
// 		
//		}
//        checkboxMultiList.addTextCell(row, modelcol, modelstr);
//		dispose();
//	}
	
	private void okEvent() throws Exception
	{
		int[] rows = list.getCheckedIndices();
		if(rows.length==0)
		{
			JOptionPane.showMessageDialog(frame,"无可选择的车系","提示",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
        String modelstr="";
		for(int i=0;i<rows.length;i++)
		{
			String amodel=((CheckListBoxModel) list.getModel()).getItem(rows[i]).toString();
			if(modelstr.equals(""))
			modelstr=amodel;
			else
		    modelstr=modelstr+"/"+amodel;                 
 		
		}
		this.currentModel=modelstr;
		dispose();
	}
	
    /**
     * 获得当前选择车系
     * @return String
     */
    public String getCurrentModel()
    {
        return currentModel;
    }
	

	
	public static void main(String args[])
	{
		new ModelSelectJDialog(null,null);
	}
}