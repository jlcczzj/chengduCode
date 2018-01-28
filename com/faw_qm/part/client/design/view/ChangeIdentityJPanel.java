/** 生成程序ChangeIdentityJPanel.java	1.1  2003/02/28
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/12/21 王亮 修改原因：TD2664:零部件进行修订后，对A版本的零部件进行重命名操作的问题。
 *                              重命名之后没有对树上的其他版本的零部件进行刷新。
 *                     修改方案：对树上的其他版本零部件进行刷新。
 * SS1 另存为的名称最大由50改为200 xianglx 2014-12-17
 * SS2 另存为的编号最大由30改为50 xianglx 2014-12-17
 */
package com.faw_qm.part.client.design.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.util.QMCt;
import com.faw_qm.util.TextValidCheck;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.version.model.VersionedIfc;
/**
 * <p>Title:更改业务对象的唯一标识的面板。 </p>
 * <p>Description:用于更改业务对象的名称和编号。 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 * @see PartDesignRenameJDialog
 */

public class ChangeIdentityJPanel extends JPanel
{
    /**序列化ID*/
    static final long serialVersionUID = 1L;

    /**业务对象的编号属性标签*/
    private JLabel numberJLabel = new JLabel();

    /**业务对象的名称属性标签*/
    private JLabel nameJLabel = new JLabel();

    /**业务对象编号属性的显示文本框*/
    private JTextField numberJTextField = new JTextField();

    /**业务对象名称属性的显示文本框*/
    private JTextField nameJTextField = new JTextField();

    /**网格包布局器*/
    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    /**用于标记资源信息路径*/
    protected static final String RESOURCE =
            "com.faw_qm.part.client.design.util.PartDesignViewRB";

    /**用于标记资源信息*/
    protected static ResourceBundle resources = null;

    /**上级JApplet*/
    private JApplet parentApplet;

    /**业务对象的唯一标识*/
    private QMPartMasterIfc identifiedObject;

    /**用于标记当前名称*/
    private String currentName;

    /**用于标记当前编号*/
    private String currentNumber;

    /**线程组*/
    private ThreadGroup contextGroup;
MouseListener moulis=null;

    /**
     * 构造函数。
     */
    public ChangeIdentityJPanel()
    {
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
         * 构造函数。
         */
        public ChangeIdentityJPanel(MouseListener moulis)
        {
            try
            {
              this.moulis=moulis;
                jbInit();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    /**
     * 初始化。
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        //获得当前线程组
        contextGroup = QMCt.getContext().getThreadGroup();
        //JLabel
        nameJLabel.setText("*Name:");
        if(moulis!=null)
        {
          nameJLabel.setName(QMMessage.getLocalizedMessage(
                    RESOURCE,
                    "renamename",null));
          nameJLabel.addMouseListener(moulis);
        }
        nameJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        nameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        nameJLabel.setBounds(new Rectangle(28, 120, 44, 18));
        numberJLabel.setText("*Number:");
         if(moulis!=null)
         {
           numberJLabel.setName(QMMessage.getLocalizedMessage(
                    RESOURCE,
                    "renamenum",null)
);
           numberJLabel.addMouseListener(moulis);
         }
        numberJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        numberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numberJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        numberJLabel.setBounds(new Rectangle(28, 78, 44, 18));
        this.setLayout(gridBagLayout1);
        this.setSize(400, 193);
        this.add(numberJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(60, 8, 0, 44), 272, 0));
        this.add(numberJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(60, 28, 0, 0), 12, 0));
        this.add(nameJLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(20, 28, 54, 0), 12, 0));
    if(moulis!=null)
          {
            nameJTextField.setName(QMMessage.getLocalizedMessage(
                     RESOURCE,
                     "renamename",null)
 );
            nameJTextField.addMouseListener(moulis);
          }

          if(moulis!=null)
                  {
                    numberJTextField.setName(QMMessage.getLocalizedMessage(
                             RESOURCE,
                             "renamenum",null)
         );
                    numberJTextField.addMouseListener(moulis);
                  }

        this.add(nameJTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(20, 8, 54, 44), 272, 0));
        localize();
    }


    /**
     * 初始化资源信息。
     */
    private void initResources()
    {
        try
        {
            resources = ResourceBundle.getBundle(RESOURCE,
                                                 getContext().getLocale());
            return;
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
            DialogFactory.showErrorDialog(this, PartDesignViewRB.MISS_RESOURCER);
        }
    }


    /**
     * 资源信息本地化。
     */
    private void localize()
    {
        try
        {
            if (resources == null)
            {
                initResources();
            }
            nameJLabel.setText(QMMessage.getLocalizedMessage(
                    RESOURCE,
                    "nameJLabel",
                    null));
            numberJLabel.setText(QMMessage.getLocalizedMessage(
                    RESOURCE,
                    "numberJLabel",
                    null));
            return;
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
            DialogFactory.showErrorDialog(this, PartDesignViewRB.MISS_RESOURCER);
        }
    }


    /**
     * 设置上级JApplet。
     * @param applet  上级JApplet。
     */
    public void setParentJApplet(JApplet applet)
    {
        parentApplet = applet;
    }


    /**
     * 获得上级JApplet。
     * @return  上级JApplet。
     */
    public JApplet getParentJApplet()
    {
        return parentApplet;
    }

    /**
     * 设置业务对象的唯一标识。
     * @param identified  业务对象的唯一标识。
     * @throws QMRemoteException
     * @see #initializeProperties
     */
    public void setIdentifiedObject(QMPartMasterIfc identified)
            throws QMRemoteException
    {
        PartDebug.debug("setIdentifiedObject(identified) begin ....", this,
                        PartDebug.PART_CLIENT);
        identifiedObject = identified;
        //初始化业务对象的属性（名称和编号）
        initializeProperties();
        PartDebug.debug("setIdentifiedObject(identified) end ...return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * 获得业务对象的唯一标识。
     * @return 业务对象的唯一标识。
     */
    public QMPartMasterIfc getIdentifiedObject()
    {
        return identifiedObject;
    }

    public void replace()
    {
        identifiedObject.setPartName(currentName);
        identifiedObject.setPartNumber(currentNumber.toUpperCase());//liunan
    }


    /**
     * 改变业务对象的唯一标识。
     * @return 更改后的业务对象的唯一标识。
     * @throws QMRemoteException
     * @see #setAttribute
     */
    public QMPartMasterIfc changeIdentity()
            throws QMRemoteException,QMException/////////2005.8.12
    {
        PartDebug.debug("changeIdentity() begin ....", this,
                        PartDebug.PART_CLIENT);
        boolean flag = false;
        //如果零部件的原名称或原编号发生了修改，则获得新输入的零部件名称和编号，
        //并存入数据库
        if (nameJTextField.isShowing() && numberJTextField.isShowing()&&
            !(currentName.equals(nameJTextField.getText().trim()) &&
            currentNumber.equals(numberJTextField.getText().trim().toUpperCase())//liunan
            ))
        {
            if (currentName == null ||
                !currentName.equals(nameJTextField.getText().trim()))
            {
                flag = false;
            }
            if (currentNumber == null ||
                !currentNumber.equals(numberJTextField.getText().trim().toUpperCase()))//liunan
            {
                flag = true;
            }
            //设置新输入的零部件名称
            identifiedObject.setPartName(nameJTextField.getText().trim());
            //设置新输入的零部件编号
            identifiedObject.setPartNumber(numberJTextField.getText().trim().toUpperCase());
//liunan
            PartDebug.debug(">>>>>>>>>>>>> new Number = " +
                            identifiedObject.getPartNumber(),
                            PartDebug.PART_CLIENT);
            PartDebug.debug(">>>>>>>>>>>>> new Name = " +
                            identifiedObject.getPartName(),
                            PartDebug.PART_CLIENT);
            PartDebug.debug(">>>>>>>>>>>>> new flag = " + flag,
                            PartDebug.PART_CLIENT);
            PartDebug.debug("ChangeIdentityJPanel: renamePartMaster() begin...", this,
                            PartDebug.PART_CLIENT);
            //调用服务方法，修改数据库中零部件的编号和名称
            try
            {
                identifiedObject = PartServiceRequest.renamePartMaster(
                        identifiedObject, flag);
            }
            catch (QMException ex)
            {
                throw new QMException(ex);/////////2005.8.12
            }
            PartDebug.debug("renamePartMaster() end...return " +
                            identifiedObject, this, PartDebug.PART_CLIENT);
            boolean ref=true;
            if (identifiedObject instanceof MasteredIfc)
            {
                PartDebug.debug(
                        "VersionControlService.allIterationsOf() begin...", this,
                        PartDebug.PART_CLIENT);
              PartConfigSpecIfc config = PartServiceRequest.findPartConfigSpecIfc();
              QMPartIfc part=PartServiceRequest.getPartByConfigSpec(identifiedObject, config);
              if(part!=null)
              {
            	  dispatchRefresh(part, RefreshEvent.UPDATE);
            	  ref=false;
            	  //CCBegin by leixiao 2009-12-24 打补丁　v4r3_p003_20091223　TD2664
            	  Class[] classes = {VersionedIfc.class};//Begin CR1
                  Object[] objects = {part};
                  Collection col = (Collection)IBAUtility
                          .invokeServiceMethod("VersionControlService",
                                  "allVersionsOf", classes, objects);
                  if(col != null && col.size() != 0)
                  {
                      Iterator iterator = col.iterator();
                      while(iterator.hasNext())
                      {
                          QMPartIfc qmpart = (QMPartIfc)iterator.next();
                          dispatchRefresh(qmpart, RefreshEvent.UPDATE);
                      }
                  }//End CR1
                   //CCBegin by leixiao 2009-12-24 打补丁　v4r3_p003_20091223
              }
//                //调用服务VersionControlService的方法,给定master，得到这个对象的
//                //所有的小版本（包括不同分枝），也就是master对应的所有小版本。
//                Class[] paraClass2 = {MasteredIfc.class};
//                Object[] objs2 = {(MasteredIfc) identifiedObject};
//                Collection result = null;
//                try
//                {
//                    result = (Collection) IBAUtility.invokeServiceMethod(
//                            "VersionControlService", "allIterationsOf",
//                            paraClass2, objs2);
//                }
//                catch (QMRemoteException ex)
//                {
//                    ex.printStackTrace();
//                    DialogFactory.showErrorDialog(this, ex.getClientMessage());
//                }
//                PartDebug.debug(
//                        "VersionControlService.allIterationsOf() end ...return:result", this,
//                        PartDebug.PART_CLIENT);
//                //获得最新版本
//                if (result != null)
//                {
//                    Object obj;
//                    Iterator i = result.iterator();
//                    if(i.hasNext())
//                    {
//                        obj = i.next();
//                        dispatchRefresh(obj, RefreshEvent.UPDATE);
//                    }
////                    for (Iterator i = result.iterator(); i.hasNext();
////                                      dispatchRefresh(obj, RefreshEvent.UPDATE))
////                    {
////                        obj = i.next();
////                    }
//                }
            }
            //发布刷新事件，刷新业务对象的唯一标识
            if(ref)
            dispatchRefresh(identifiedObject, RefreshEvent.UPDATE);
            Class[] aa = {String.class};
            Object[] objs2 = { identifiedObject.getPartNumber()};
            Collection result = null;
            try
            {
                result = (Collection) IBAUtility.invokeServiceMethod(
                        "EPMDocumentService", "renameCADFile",
                        aa, objs2);
            }
            catch (QMRemoteException ex)
            {
                ex.printStackTrace();
                DialogFactory.showErrorDialog(this, ex.getClientMessage());
            }
        }
        //如果编号和名称都没有作任何更改，则抛出异常
        if (currentName.equals(nameJTextField.getText().trim()) &&
            currentNumber.equals(numberJTextField.getText().trim())
            )
        {
            //通过标识工厂获得与给定值对象对应的显示标识对象。
            DisplayIdentity displayidentity = IdentityFactory.
                                              getDisplayIdentity(
                    identifiedObject);
            String s = displayidentity.getLocalizedMessage(null);
            Object aobj[] = {s};
            throw new QMRemoteException(null,
                                        RESOURCE,
                                        PartDesignViewRB.NO_CHANGES_MADE,
                                        aobj);
        }
        else
        {
            PartDebug.debug("changeIdentity() end ...return " +
                            identifiedObject, this, PartDebug.PART_CLIENT);
            return identifiedObject;
        }

    }


    /**
     * 初始化业务对象的属性（名称和编号）。
     * @throws QMRemoteException
     */
    protected void initializeProperties()
            throws QMRemoteException
    {
        PartDebug.debug("initializeProperties() begin ....", this,
                        PartDebug.PART_CLIENT);
        if (identifiedObject != null)
        {
            //获得当前对象的名称
            currentName = identifiedObject.getPartName();
            nameJTextField.setText(currentName);
            //获得当前对象的编号
            currentNumber = identifiedObject.getPartNumber();
            numberJTextField.setText(currentNumber);
        }
        PartDebug.debug("initializeProperties() end ...return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * 获得Context。
     * @return QMCt
     */
    protected QMCt getContext()
    {
        PartDebug.debug("getContext() begin...", this, PartDebug.PART_CLIENT);
        QMCt qmcontext = null;
        if (contextGroup != null)
        {
            qmcontext = QMCt.getContext(contextGroup);
        }
        else if (parentApplet != null)
        {
            qmcontext = QMCt.getContext(parentApplet);
        }
        if (qmcontext == null)
        {
            qmcontext = QMCt.getContext();
        }
        PartDebug.debug("getContext() end...return qmContext", this,
                        PartDebug.PART_CLIENT);
        return qmcontext;
    }


    /**
     * 获得本地资源信息。
     * @param s 资源信息。
     * @param aobj 资源信息中的参数。
     * @return s1  本地化后的资源信息。
     */
    protected String getLocalizedMessage(String s, Object aobj[])
    {
        String s1 = "";
        if (resources == null)
        {
            initResources();
        }
        if (aobj != null)
        {
            s1 = MessageFormat.format(resources.getString(s), aobj);
        }
        else
        {
            s1 = resources.getString(s);
        }
        return s1;
    }


    /**
     * 根据具体的事件动作标识和事件目标，发布刷新事件。
     * @param obj target事件目标。
     * @param i  具体的事件动作标识。
     */
    protected void dispatchRefresh(Object obj, int i)
    {
        PartDebug.debug("dispatchRefresh() begin ....", this,
                        PartDebug.PART_CLIENT);
        //实例化刷新事件
        RefreshEvent refreshEvent = new RefreshEvent(getParent(), i, obj);
        //指派刷新事件
        RefreshService.getRefreshService().dispatchRefresh(refreshEvent);

        PartDebug.debug("dispatchRefresh() end ...return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * 决定面板是否激活的方法。
     * @param flag 布尔值。
     */
    public void enablePanel(boolean flag)
    {
        if (isAncestorOf(nameJTextField))
        {
            nameJTextField.setEnabled(flag);
        }
        if (isAncestorOf(numberJTextField))
        {
            numberJTextField.setEnabled(flag);
        }
    }


    /**
     * 刷新界面。
     */
    public void refresh()
    {
        invalidate();
        doLayout();
        repaint();
    }


    /**
     * 检验必填区域是否已有有效值。
     * @return  如果必填区域已有有效值，则返回为真。
     */
    protected boolean checkRequiredFields()
    {
        PartDebug.debug("checkRequiredFields() begin....", this,
                        PartDebug.PART_CLIENT);
        boolean isOK = true;
        String message = "fell through ";
        String title = "";
        //lily lin 2004.8.16
      //  Validate validate = new Validate();
        //!--lily lin 添加,判断"名称","编号"是否有通配符eg: %\/:*?"<>|


        try
        {
        	//2007.05.29 whj
        	 //检验编号是否为空，并且不含有通配符
        	TextValidCheck numbervalidate = new TextValidCheck(QMMessage.getLocalizedMessage(RESOURCE,
            		"numberLbl",
//CCBegin SS2
//                    null),1,30);
                    null),1,50);
//CCBEnd SS2
        	numbervalidate.check(numberJTextField.getText().trim(),true);
        	TextValidCheck namevalidate = new TextValidCheck(QMMessage.getLocalizedMessage(RESOURCE,
            		"nameLbl",
//CCBegin SS1
//                    null),1,50);
                    null),1,200);
//CCBEnd SS1
        	namevalidate.check(nameJTextField.getText().trim(),true);
        }
        catch(QMRemoteException re)
        {
        	message=re.getClientMessage();
        	 isOK = false;
        }
//        if (validate.validateString(numberJTextField.getText().trim()) == false)
//        {
//            message = QMMessage.getLocalizedMessage(RESOURCE,
//                    PartDesignViewRB.NUM_ENTERWRONG,
//                    null);
//            isOK = false;
//        }
//        //检验名称是否为空,并且不含有通配符
//        else if (validate.validateString(nameJTextField.getText().trim()) == false)
//        {
//            message = QMMessage.getLocalizedMessage(RESOURCE,
//                    PartDesignViewRB.NAME_ENTERWRONG,
//                    null);
//            isOK = false;
//        }
        //-->
//        else if (numberJTextField.getText().trim().length() > 30)
//        {
//            message = QMMessage.getLocalizedMessage(RESOURCE, PartDesignViewRB.NUM_LENGTHWRONG, null);
//            isOK = false;
//        }
//        else if (nameJTextField.getText().trim().length() > 50)
//        {
//            message = QMMessage.getLocalizedMessage(RESOURCE, PartDesignViewRB.NAME_LENGTHWRONG, null);
//            isOK = false;
//        }
//        else
//        {
//            isOK = true;
//        }
        if (!isOK)
        {
            DialogFactory.showWarningDialog(this, message);
        }
        PartDebug.debug("checkRequiredFields() end....return is " + isOK, this,
                        PartDebug.PART_CLIENT);
        return isOK;
    }
}
