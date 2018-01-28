/** 生成程序DescribedByJPanel.java	1.1  2003/02/28
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/11/26 王亮  原因：产品信息管理器所有Tab页信息一次全都初始化。
 *                     方案：关注哪个Tab页就初始化哪个Tab页。
 */
package com.faw_qm.part.client.design.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyVetoException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.faw_qm.clients.beans.AssociationsPanel;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.design.model.DescribedByAssociationsModel;
import com.faw_qm.part.client.design.model.PartItem;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.client.main.controller.QMProductManager;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.util.QMCt;


/**
 * <p>Title: 描述关联面板。</p>
 * <p>Description:零部件描述关联的可视化界面。在本面板对零部件的描述关联进行</p>
 * <p>创建、更新、删除等操作。</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 * @see DescribedByAssociationsModel,AssociationsPanel
 */

public class DescribedByJPanel extends JPanel
{
    /**序列化ID*/
    static final long serialVersionUID = 1L;

    /**编辑描述关系面板（JavaBean）*/
    private AssociationsPanel describedByAssociationsPanel;

    /**编辑描述关系面板的模型*/
    private DescribedByAssociationsModel model;

    /**用于标记界面模式*/
    public String mode;

    /**用于标记资源信息路径*/
    protected static final String RESOURCE =
            "com.faw_qm.part.client.design.util.PartDesignViewRB";

    /**线程组*/
    private ThreadGroup contextThreadGroup;

    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private boolean isReference=false;
    //表头
	private String[] deslistheading = null;

	//获得表头的方法
	private String[] headingMethod1;
	private String[] headingMethodEPM;//EPM文档的属性值获取的方法名
    /**是否是信息管理器调用的标志，是则为true*/
    private boolean isparttask = false;
    private boolean isShown;//此Tab页的显示标志//CR1
    
    /**
     * 构造函数。
     */
    public DescribedByJPanel()
    {
        super();
        try
        {
        	setHeadingMethods();
        	setDesListHeading();
        	setHeadingMethodsEPM();
        	
            jbInit();
            isShown = false;//CR1
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    //add by muyp 081118 begin
    /**
     * 构造函数。一个参数，flag用于判断是否带显示设置功能。
     * @param flag boolean true则带显示设置功能。
     */
    public DescribedByJPanel(boolean flag)
    {
        super();
        try
        {
        	isparttask = flag;
        	setHeadingMethods();
        	setDesListHeading();
        	setHeadingMethodsEPM();
        	
            jbInit();
            isShown = false;//CR1
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }//end


    /**
     * 初始化。
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        this.setSize(620, 206);
        this.setLayout(gridBagLayout1);

        //初始化描述关系面板
        try
        {
        	if(this.isparttask)
        	{
        		describedByAssociationsPanel = new AssociationsPanel(true,isparttask);
        	}
        	else
        		describedByAssociationsPanel = new AssociationsPanel(true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    PartDesignViewRB.ASSOPL_INIT_FAIL, null);
            //异常：初始化描述关系面板失败
            JOptionPane.showMessageDialog(this,
                                          message,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }
        //初始化描述关系面板模型
        model = new DescribedByAssociationsModel();
        //初始界面模式为“查看”
        mode = "View";
        //获得当前线程组
        contextThreadGroup = QMCt.getContext().getThreadGroup();
        //当用户点击Add按钮时，本方法用来设置可选的类列表,确定要选择的对象的类型（这里为Doc）
        describedByAssociationsPanel.setChooserOptions("2");
        //设置业务类在关联中的角色
        describedByAssociationsPanel.setRole("describes");
        //设置多列列表的列标题
        try
        {
//            String as[] = new String[3]; //穆勇鹏注释
//            as[0] = "translateNumber";
//            as[1] = "translateName";
//            as[2] = "translateVersion";
            describedByAssociationsPanel.setLabels(deslistheading);
        }
        catch (PropertyVetoException _ex)
        {
            _ex.printStackTrace();
        }
        //设置关联类名
        try
        {
            describedByAssociationsPanel.setLinkClassName(
                    "com.faw_qm.part.model.PartDescribeLinkInfo");
        }
        catch (QMRemoteException qre)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
            JOptionPane.showMessageDialog(this,
                                          qre.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        describedByAssociationsPanel.setHeadingMethods(headingMethod1);
//      设置EPM文档表头属性值获取方法名
        describedByAssociationsPanel.setHeadingMethodsEPM(headingMethodEPM);
        //设置业务类名
        try
        {
            describedByAssociationsPanel.setObjectClassName(
                    "com.faw_qm.part.model.QMPartInfo");
        }
        catch (ClassNotFoundException _ex)
        {
            _ex.printStackTrace();
        }

//        String as1[] = new String[3]; //穆勇鹏 注释
//        as1[0] = "docNum";
//        as1[1] = "docName";
//        as1[2] = "versionID";
        //设置将显示在多列列表中关联的业务对象的属性集
        describedByAssociationsPanel.setOtherSideAttributes(headingMethod1);
        //设置关联业务对象类名
        try
        {
            describedByAssociationsPanel.setOtherSideClassName(
                    "com.faw_qm.doc.model.DocInfo");
        }
        catch (ClassNotFoundException _ex)
        {
            _ex.printStackTrace();
        }
        //设置字体
        describedByAssociationsPanel.setFont(PartScreenParameter.getFont());
        //设置界面为编辑模式
        describedByAssociationsPanel.setMode("Edit");
        ((PartMultiList)(describedByAssociationsPanel.getMultiList())).setUpdate(true);
        ((PartMultiList)(describedByAssociationsPanel.getMultiList())).setParentPanel(describedByAssociationsPanel);
        
      
        //界面布局
        add(describedByAssociationsPanel,
            new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                   , GridBagConstraints.CENTER,
                                   GridBagConstraints.BOTH,
                                   new Insets(0, 0, 0, 0), 0, 0));
        //设置尺寸
        describedByAssociationsPanel.setBounds(15, 15, 590, 240);
        //设置是否只保存更改过的
        describedByAssociationsPanel.setSaveUpdatesOnly(true);
        //将AssociationsPanel下方的AttributeForm部分删除
        describedByAssociationsPanel.removeAttributeForm();
        //设置只获得文档的每个大版本的最新版本
        describedByAssociationsPanel.setLastIteration(true);
        //设置列表内容不可排序
        describedByAssociationsPanel.getMultiList().setAllowSorting(false);

        //资源信息本地化
//        localize();//穆勇鹏 注释

    }


    /**
     * 资源信息本地化。
     */
    protected void localize()
    {
        try
        {
            String as[] = new String[3];
            as[0] = QMMessage.getLocalizedMessage(RESOURCE, "numberHeading", null,
                                                  getContext().getLocale());
            as[1] = QMMessage.getLocalizedMessage(RESOURCE, "nameHeading", null,
                                                  getContext().getLocale());
            as[2] = QMMessage.getLocalizedMessage(RESOURCE, "versionHeading", null,
                                                  getContext().getLocale());
            describedByAssociationsPanel.setLabels(as);
            return;
        }
        catch (PropertyVetoException _ex)
        {
            _ex.printStackTrace();
            return;
        }
    }


    /**
     * 设置描述关联面板的模式。
     * @param s 显示模式。
     */
    public void setMode(String s)
    {
        PartDebug.debug("setMode(s) begin...", this, PartDebug.PART_CLIENT);
        if (describedByAssociationsPanel != null)
        {
            //设置界面模式
            describedByAssociationsPanel.setMode(s);
            PartMultiList multiList = (PartMultiList)describedByAssociationsPanel.getMultiList();
            if(s.equals("Edit"))
            {
                multiList.setUpdate(true);
                multiList.setParentPanel(describedByAssociationsPanel);
            }
            else
            {
                multiList.setUpdate(false);
                multiList.setParentPanel(describedByAssociationsPanel);
            }

        }
        PartDebug.debug("setMode(s) end...return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * 设置PartItem对象。
     * @param  partItem  PartItem对象。
     */
    public void setPartItem(PartItem partItem)
    {
        PartDebug.debug("setPartItem(partItem) begin...", this,
                        PartDebug.PART_CLIENT);
        //设置模型的业务对象
        model.setPart(partItem.getPart());

        try
        {
            //设置模型
        	describedByAssociationsPanel.setReference(getReference());
            describedByAssociationsPanel.setModel(model);
            //设置将要被查看或编辑的业务对象
            describedByAssociationsPanel.setObject(model.getPart());
            PartDebug.debug("setPartItem(partItem) end...return is void", this,
                            PartDebug.PART_CLIENT);
            return;
        }
        catch (PropertyVetoException _ex)
        {
            _ex.printStackTrace();
            return;
        }

    }


    /**
     * 保存描述关联。
     * @param partItem PartItem对象。
     * @return   保存成功则返回True,否则返回false。
     * @throws QMRemoteException
     */
    public boolean save(PartItem partItem)
            throws QMRemoteException
    {
        PartDebug.debug("save(partItem) begin ....", this,
                        PartDebug.PART_CLIENT);
        boolean flag = false;
        if (describedByAssociationsPanel != null)
        {
            //获得要保存的关联零部件
            QMPartIfc part = partItem.getPart();
            //保存关联
            QMPartIfc partIfc =
                    (QMPartIfc) describedByAssociationsPanel.save(part);
            //把保存的关联存入模型
            model.setPart(partIfc);

            flag = true;
        }
        PartDebug.debug("save(partItem) end ...return " + flag, this,
                        PartDebug.PART_CLIENT);
        return flag;
    }


    /**
     * 设置界面显示模式（编辑或查看）。
     * @param flag 如果flag = true ,则mode = Edit;否则mode = View。
     */
    public void setUpdateMode(boolean flag)
    {
        mode = flag ? "Edit" : "View";
        setMode(mode);
    }


    /**
     * 获得Context。
     * @return  qmcontext  QMCt
     */
    public QMCt getContext()
    {
        QMCt qmcontext = null;
        if (contextThreadGroup != null)
        {
            qmcontext = QMCt.getContext(contextThreadGroup);
        }
        if (qmcontext == null)
        {
            qmcontext = QMCt.getContext();
        }
        return qmcontext;
    }
    
    /**
     * 获得描述面板是否改变标志的值。
     * @return  boolean
     */
    public boolean isChanged(){
    	return describedByAssociationsPanel.isDirty();
    }
    
    /**
     * 设置描述面板是否改变标志的值。
     * @param flag boolean
     */
    public void setChanged(boolean flag){
    	describedByAssociationsPanel.setDirty(flag);
    }
    /**
     * set for capp 屏蔽调补充需求，2007.06.29 王海军
     * @param manage
     */
    public void setManager(QMProductManager manage)
    {
    	
    	 if( manage.fromcapp)
    	        ((PartMultiList)(describedByAssociationsPanel.getMultiList())).setFromCapp(true);
    }
    
    public void setReference(boolean flag)
    {
    	isReference=flag;
    }
    public boolean getReference()
    {
    	return isReference;
    }
    
    /////add by muyp 20080922
    /**
     * 设置列表表头
     */
    private void setDesListHeading()
    {
    	String s = "description";
    	try {
    		deslistheading = (String[])PartServiceRequest.getListHead(s);
		} catch (QMException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
    }
    
    /**
     * 初始化HeadingMethods
     * @return void:void。
     */
    private void setHeadingMethods()
            throws PropertyVetoException
    {
    	try
    	{
    		headingMethod1 = PartServiceRequest.getListMethod("description");
    	}
    	catch(QMException e)
    	{
    		e.printStackTrace();
    	}
    }
    /**
     * 获得get列表头的方法名的集合
     * @return
     */
    public String[] getHeadingMethods()
    {
    	return headingMethod1;
    }
    
    /**
     * 用给定的数组初始化HeadingMethodsEPM（EPM文档）
     * @param as[]:描述文档的列标题
     * @return void:void。
     */
    private void setHeadingMethodsEPM()
            throws PropertyVetoException
    {
    	headingMethodEPM = new String[headingMethod1.length];
         for(int i=0;i<headingMethod1.length;i++)
         {
        	 if(headingMethod1[i].equals("getDocNum"))
        	 {
        		 headingMethodEPM[i]="getDocNumber";
        	 }
        	 else if(headingMethod1[i].equals("getDocName")||headingMethod1[i].equals("getVersionID"))
        	 {
        		 headingMethodEPM[i]=headingMethod1[i];
        	 }
         }
            
    }
    
    /**
     * 获得显示设置按钮
     */
    public JButton getShowAttrSettedJButton()
    {
    	return describedByAssociationsPanel.getShowAttrSettedJButton();
    }
    
    public PartMultiList getDescDocList()
    {
    	return describedByAssociationsPanel.getMultiList();
    }
    
    public AssociationsPanel getDescPanel()
    {
    	return describedByAssociationsPanel;
    }
    //end
    /**
     * 设置Tab页显示标志
     */
    public void setIsShown(boolean isShown)//CR1
    {
        this.isShown = isShown;
    }
    /**
     * 获取Tab页显示标志
     * @return
     */
    public boolean getIsShown()
    {
        return isShown;
    }
}
