/** 生成程序TechUsageMaterialLinkController.java	1.1  2004/08/08
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 由于材料定额升级，更改获取主材相关信息的方法 liuyang 2013-2-26
 */
package com.faw_qm.cappclients.capp.controller;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsPanel;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.view.TechUsageMaterialLinkJPanel;
import com.faw_qm.cappclients.resource.view.ResourceDialog;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.resource.support.model.QMMaterialInfo;


/**
 * <p>Title: 工艺使用材料关联面板的控制类</p>
 * <p>Description: 此类相当于默认的材料关联控制类,
 * 子类中个性化的地方可以覆盖此类中的方法
 * 覆盖的方法有: setRadionButtonSelected(), calculate(),
 * handelCappAssociationsPanel(String type),
 * public BaseValueIfc[] doFillter(BaseValueIfc[] vec)
 * </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author 薛静
 * @version 1.0
 */
public abstract class TechUsageMaterialLinkController //implements ObjectFilter
{
    /**
     * 工艺与材料的关联面板
     */
    public TechUsageMaterialLinkJPanel panel;


    /**
     * 关联bean
     */
    public CappAssociationsPanel cappAssociationsPanel;


    /**列表中选中得行*/
    public int row = -1;


    /**列表中选中得列*/
    public int col = -1;


    /**
     * 输入的材料编号
     */
    private String inputNumber = "";


    /**
     * 工艺种类
     */
    public String technicsType = null;


    /**代码测试变量*/
    protected static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**用于标记资源文件路径*/
    public static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";


    /**
     * 构造方法
     * @param type String 工艺种类
     */
    public TechUsageMaterialLinkController(String type)
    {
        technicsType = type;
    }


    /**
     * 设置材料关联面板,并设置管联bean
     * @param linkpanel TechUsageMaterialLinkJPanel 材料关联面板
     */
    public void setLinkPanel(TechUsageMaterialLinkJPanel linkpanel)
    {
        panel = linkpanel;
        cappAssociationsPanel = panel.getCappAssociationsPanel();
        handelCappAssociationsPanel();
    }


    /**
     * 执行手工录入材料操作\判断材料定额
     * @param e ActionEvent
     */
    public void cappAssociationsPanel_actionPerformed(ActionEvent e)
    {
        Object obj = e.getSource();
        String actionCommand = e.getActionCommand();
        int t = actionCommand.indexOf(";");
        if (t != -1)
        {
            //得到行
            String rowString = actionCommand.substring(0, t);
            int t1 = rowString.indexOf(":");
            row = Integer.parseInt(rowString.substring(t1 + 1, rowString.length()));
            //得到列
            String colString = actionCommand.substring(t + 1,
                    actionCommand.length());
            int t2 = colString.indexOf(":");
            col = Integer.parseInt(colString.substring(t2 + 1, colString.length()));

        }

        //如果选中的是第0列（材料编号列）
        if (col == 0 && obj instanceof JTextField)
        {
            cappAssociationsPanel.setEnabled(false);
            JTextField textField = (JTextField) obj;
            if (textField.getText().trim() == null ||
                textField.getText().trim().equals(""))
            {
                cappAssociationsPanel.setEnabled(true);
            }
            else
            {
                handworkAddMaterialNumber(textField);
            }

        }

        //列表中添加了第一行，则设置材料主要标识为选中
       if (e.getActionCommand().equals("FirstRow"))
        {
            BinaryLinkIfc link = (BinaryLinkIfc) e.getSource();
            if (link.getBsoID() == null)
            {

                actionPerformed_whenFirstRowAdded();
            }
        }
        //计算每车件数
        try
        {
            if (actionCommand.equals("calculate"))
            {
                calculate();
            }
//          CCBeginby leixiao 2009-7-8 原因：工艺与材料面板，增加获取材料定额
            if (actionCommand.equals("ration"))
            {            	
                getration();
            }
//          CCEndby leixiao 2009-7-8 原因：工艺与材料面板，增加获取材
        }
        catch (Exception ex1)
        {
            if(verbose)
            ex1.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(panel.getParentJFrame(),
                                          ex1.getLocalizedMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }


    /**
     * 手工添加材料编号到指定的文本框中
     * @param textField 填写材料编号的文本框
     */
    private void handworkAddMaterialNumber(JTextField textField)
    {
        String number = textField.getText().trim();
        inputNumber = number;
        //如果输入的材料编号在系统中存在，则系统把该材料添加入列表中
        QMMaterialInfo eq = checkMaterialIsExist(number);
        if (eq == null)
        {
            CreateResource t = new CreateResource();
            new Thread(t).start();
        }

        else
        {
            try
            {
                cappAssociationsPanel.addObjectToRow(eq, row);
                cappAssociationsPanel.setEnabled(true);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }

    }


    /**
     * 调用资源服务，通过指定的材料编号在数据库中查找材料对象
     * @param eqNumber 指定的材料编号
     * @return 查找到的材料对象
     */
    private QMMaterialInfo checkMaterialIsExist(String eqNumber)
    {
        Class[] c =
                {String.class};
        Object[] obj =
                {eqNumber};
        try
        {
            return (QMMaterialInfo) TechnicsAction.useServiceMethod(
                    "ResourceService", "findMaterialByNumber", c, obj);
        }
        catch (QMRemoteException ex)
        {
            //ex.printStackTrace();
            return null;
        }
    }

    class CreateResource extends Thread
    {
        public void run()
        {

            //如果系统中不存在列表中输入的材料编号，则返回对应的消息，询问是否新建
            //材料，如果是，显示材料新建界面，根据访问权限创建申请材料；如果否，返回原位置.
            String s = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.IS_CREATE_MATERIAL, null);
            if (confirmAction(s))
            {
                cappAssociationsPanel.setEnabled(true);
                panel.getParentJFrame().setCursor(Cursor.getPredefinedCursor(
                        Cursor.WAIT_CURSOR));
                //显示材料新建界面
                //显示材料新建界面
                ResourceDialog d = new ResourceDialog(panel.getParentJFrame(),
                        "material", "CREATE", inputNumber);
                d.addQuitListener(new ActionListener()
                          {
                              public void actionPerformed(ActionEvent e)
                              {
                                  cappAssociationsPanel.undoCell();
                              }

                          });

                d.setSize(650, 500);
                panel.setViewLocation(d);
                d.setTitle("新建材料");


                d.setVisible(true);
                panel.getParentJFrame().setCursor(Cursor.getDefaultCursor());
                try
                {
                    Object obj = d.getObject();
                    if (obj != null)
                    {
                        cappAssociationsPanel.addObjectToRow((BaseValueIfc) obj,
                                row);
                    }
                    else
                    {
                      //20061127薛静修改，当选择的对象是空时，将材料编号列置空
                        cappAssociationsPanel.undoCell();
                    }

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            else
            {
                cappAssociationsPanel.undoCell();
                cappAssociationsPanel.setEnabled(true);
            }

        }
    }


    /**
     * 显示确认对话框
     * @param s 在对话框中显示的信息
     * @return  如果用户选择了“确定”按钮，则返回true;否则返回false
     */
    private boolean confirmAction(String s)
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
        return JOptionPane.showConfirmDialog(panel.getParentJFrame(),
                                             s, title,
                                             JOptionPane.YES_NO_OPTION) ==
                JOptionPane.YES_OPTION;
    }


    /**
     * 计算,需要计算的子类覆盖此方法
     */
    public void calculate()
    {}

    
//  CCBeginby leixiao 2009-7-8 原因：工艺与材料面板，增加获取材料定额
    public void   getration(){

    }
    
    public Collection findMainRationByPart(String partid){
    	Collection collection=null;

    	try
    	{
    		Class[] paraClass ={String.class};
    		Object[] objs ={partid};

    		QMPartInfo part = (QMPartInfo)
    		TechnicsAction.useServiceMethod(
 	         "PersistService", "refreshInfo", paraClass, objs);
    		Class[] paraClass1 ={String.class};
    		Object[] objs1 ={part.getMasterBsoID()};
    		//CCBegin SS1
//    		collection = (Collection) TechnicsAction.useServiceMethod(
//    				"MaterialRationService", "findMainRationByPart", paraClass1, objs1);
    		//CCEnd SS1 
    		collection = (Collection) TechnicsAction.useServiceMethod(
    				"MaterialRationService", "getValidMainRationByPartMasterID", paraClass1, objs1);
    	}
    	catch (QMRemoteException ex)
    	{
    		ex.printStackTrace();

    	}
    	return collection;
}
//  CCEndby leixiao 2009-7-8 原因：工艺与材料面板，增加获取材料定额
    

    /**
     * 根据工艺种类得到关联面版的显示属性
     * @param type String 工艺种类
     * @return Vector 长度为4 第一个元素是材料基本属性数组,第二个元素是关联属性数组,第三个元素是标签名
     * 第4个元素是关联属性是否包含扩展属性
     */
    public ArrayList getAttrs()
    {
        //根据工艺种类从资源文件得到字符串
        String allProperties = RemoteProperty.getProperty(
                "QMTechnicsQMMaterialLink" + technicsType);

        if (allProperties == null)
        {
            return null;
        }
        ArrayList vec = new ArrayList();
        StringTokenizer stringToken = new StringTokenizer(allProperties, ";");
        //得到材料的属性
        String materialProperties = stringToken.nextToken();
        //得到关联属性
        String linkProperties = stringToken.nextToken();
        //得到材料属性的标签名
        String labelProperties = stringToken.nextToken();
        Boolean hasExtendAttr;
        if (stringToken.nextToken().trim().equals("true"))
        {
            hasExtendAttr = Boolean.TRUE;
        }
        else
        {
            hasExtendAttr = Boolean.FALSE;
        }

        //把材料属性放入materialPropertiesArray[]中
        StringTokenizer materialToken = new StringTokenizer(materialProperties,
                ",");
        final int mL = materialToken.countTokens();
        String[] materialPropertiesArray = new String[mL];
        for (int i = 0; i < mL; i++)
        {
            materialPropertiesArray[i] = materialToken.nextToken();
        }
        vec.add(materialPropertiesArray);

        //把关联属性放入LinkPropertiesArray[]中
        StringTokenizer linkToken = new StringTokenizer(linkProperties, ",");
        int lL = linkToken.countTokens();
        String[] LinkPropertiesArray = new String[lL];
        for (int i = 0; i < lL; i++)
        {
            LinkPropertiesArray[i] = linkToken.nextToken();
        }
        vec.add(LinkPropertiesArray);

        //把标签名放入labelPropertiesArray[]中
        StringTokenizer lableToken = new StringTokenizer(labelProperties, ",");
        int labelL = lableToken.countTokens();
        String[] labelPropertiesArray = new String[labelL];
        for (int i = 0; i < labelL; i++)
        {
            labelPropertiesArray[i] = lableToken.nextToken();
        }
        vec.add(labelPropertiesArray);
        vec.add(hasExtendAttr);

        return vec;
    }


    /**
     * 设置关联bean,子类在覆盖此方法时要先调用父类的此方法
     * @param type String工艺种类
     */
    public void handelCappAssociationsPanel()
    {
        try
        {
            //设置角色名
            cappAssociationsPanel.setRole("technics");
            //设置关联类名
            cappAssociationsPanel.setLinkClassName(
                    "com.faw_qm.capp.model.QMTechnicsQMMaterialLinkInfo");
            //设置业务类名
            cappAssociationsPanel.setObjectClassName(
                    "com.faw_qm.capp.model.QMFawTechnicsInfo");
            //设置关联业务对象类名
            cappAssociationsPanel.setOtherSideClassName(
                    "com.faw_qm.resource.support.model.QMMaterialInfo");
            //设置是否只保存更改过的
            cappAssociationsPanel.setSaveUpdatesOnly(true);
            //将AssociationsPanel下方的AttributeForm部分删除
            cappAssociationsPanel.removeAttributeForm();

            ArrayList attr = getAttrs();
            //是否包含扩展属性
            boolean flag = ((Boolean) attr.get(3)).booleanValue();
            if (flag)
            {
                cappAssociationsPanel.setSecondTypeValue(technicsType);

                //设置将显示在多列列表中关联的业务对象的属性集
            }
            String[] as1 = (String[]) attr.get(0);
            cappAssociationsPanel.setOtherSideAttributes(as1);

            //设置关联属性
            String[] args2 = (String[]) attr.get(1);
            cappAssociationsPanel.setMultiListLinkAttributes(args2);

            //设置标签
            String[] labels = (String[]) attr.get(2);
            cappAssociationsPanel.setLabels(labels);
            // cappAssociationsPanel.setObjectFilter(this);
            //助记符
            cappAssociationsPanel.setBrowseButtonMnemonic('F');
            cappAssociationsPanel.setRemoveButtonMnemonic('D');
            cappAssociationsPanel.setViewButtonMnemonic('V');
            cappAssociationsPanel.setCalculateButtonMnemonic('C');

        }
        catch (Exception ex1)
        {
            ex1.printStackTrace();
        }

    }


    /**
     * 过滤结果集
     * @param vec BaseValueIfc[]
     * @return BaseValueIfc[]
     */
    /* public BaseValueIfc[] doFillter(BaseValueIfc[] vec)
     {

         BaseValueIfc[] returnValues = new BaseValueIfc[1];
         if (cappAssociationsPanel.getLinks().size() == 0)
         {
             returnValues[0] = vec[0];
             return returnValues;
         }
         else
         {
             return null;
         }
     }
     */

    /**
     * 获得材料利用率,相应子类覆盖此方法
     * @throws Exception
     * @return double
     */
    public double getMRation()
            throws Exception
    {
        return 0;
    }


    /**
     * 关联面板中第一行添加时的触发方法
     */
    public void actionPerformed_whenFirstRowAdded()
    {
        cappAssociationsPanel.setRadionButtonValue(0, getMajorMCol(), true);
    }


    /**
     * 当零件关联面板中的主要零件标识选中时,材料关联面板的处理事件,在子类中覆盖
     * @param e ActionEvent
     */
    public void actionPerformd_whenAddedMajorPark(ActionEvent e)
    {
    }


    /**
     * 获得主要材料标识列,子类中覆盖此方法
     * @return int 主要材料标识列
     */
    public abstract int getMajorMCol();

}
