/** 生成程序TechnicsReguRenameJPanel.java	1.1  2003/08/08
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.faw_qm.capp.model.QMTechnicsMasterIfc;
import com.faw_qm.capp.model.QMTechnicsMasterInfo;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.version.model.MasteredIfc;


/**
 * <p>Title: 用于更改工艺卡的名称和编号的面板</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明 薛静
 * @version 1.0
 */

public class TechnicsReguRenameJPanel extends JPanel
{
    private JLabel numJLabel = new JLabel();
    private CappTextField numJTextField;
    private JLabel nameJLabel = new JLabel();
    private CappTextField nameJTextField;
    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**资源文件路径*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.capp.util.CappLMRB";


    /**工艺卡主信息对象*/
    private QMTechnicsMasterInfo technicsObject;


    /**用于标记当前名称*/
    private String currentName;


    /**用于标记当前编号*/
    private String currentNumber;


    /**请求服务器*/
    private RequestServer server = RequestServerFactory.getRequestServer();


    /**代码测试变量*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    /**父窗口*/
    private Component parent;

    public TechnicsReguRenameJPanel(Component parent)
    {
        this.parent = parent;
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
     * 界面初始化
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        numJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        numJLabel.setText("*工艺编号");
        setLayout(gridBagLayout1);
        setSize(400, 100);
        nameJLabel.setMaximumSize(new Dimension(53, 22));
        nameJLabel.setMinimumSize(new Dimension(53, 22));
        nameJLabel.setPreferredSize(new Dimension(53, 22));
        nameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        nameJLabel.setText("*工艺名称");
        add(nameJLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.NORTHEAST,
                                               GridBagConstraints.NONE,
                                               new Insets(20, 34, 62, 0), 0, 0));
        add(numJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.EAST,
                                              GridBagConstraints.NONE,
                                              new Insets(57, 34, 0, 0), 0, 0));
        String technicsNameNamedisp = QMMessage.getLocalizedMessage(RESOURCE,
                "technicsName", null);
        nameJTextField = new CappTextField(parent, technicsNameNamedisp, 40, false);
        add(nameJTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(20, 8, 62, 40), 0, 0));
        String technicsNumdisp = QMMessage.getLocalizedMessage(RESOURCE,
                "technicsNumber", null);
//      CCBegin by leixiao 2009-6-18 原因：解放系统升级 ,编号长度20->50
        numJTextField = new CappTextField(parent, technicsNumdisp, 50, false);
//      CCEnd by leixiao 2009-6-18 原因：解放系统升级 ,编号长度20->40
        add(numJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                                  , GridBagConstraints.WEST,
                                                  GridBagConstraints.HORIZONTAL,
                                                  new Insets(57, 8, 0, 40), 0,
                                                  0));
        localize();
    }


    /**
     * 界面信息本地化
     */
    private void localize()
    {
        numJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "technicsNumberJLabel", null));
        nameJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "technicsNameJLabel", null));
    }


    /**
     * 设置工艺卡业务对象
     * @param masterInfo 工艺卡主信息
     */
    public void setTechnics(QMTechnicsMasterInfo masterInfo)
    {
        technicsObject = masterInfo;
        initializeProperties();
    }


    /**
     * 获得工艺卡业务对象
     * @return  工艺卡主信息
     */
    public QMTechnicsMasterInfo getTechnics()
    {
        return technicsObject;
    }


    /**
     * 初始化业务对象的属性（名称和编号）
     * @throws QMRemoteException
     */
    private void initializeProperties()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsReguRenameJPanel.initializeProperties() begin...");
        }
        if (technicsObject != null)
        {
            //获得当前对象的名称
            currentName = technicsObject.getTechnicsName();
            nameJTextField.setText(currentName);
            //获得当前对象的编号
            currentNumber = technicsObject.getTechnicsNumber();
            numJTextField.setText(currentNumber);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsReguRenameJPanel.initializeProperties() end...return is void");
        }
    }


    /**
     * 改变业务对象的唯一标识
     * @return 更改后的业务对象的唯一标识
     * @throws QMRemoteException
     * @see #setAttribute
     */
    public QMTechnicsMasterInfo changeIdentity()
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsReguRenameJPanel.changeIdentity() begin...");
        }
        boolean flag = false;
        //如果工艺的原名称或原编号发生了修改，则获得新输入的工艺名称和编号，
        //并存入数据库
        if (nameJTextField.isShowing() && numJTextField.isShowing())
        {
            if (numJTextField.getText() == null || numJTextField.getText().trim() == null)
            {
                throw new QMRemoteException(RESOURCE,
                                            CappLMRB.NO_TECHNICS_NUMBER_ENTERED, null);
            }
            if (nameJTextField.getText() == null ||
                nameJTextField.getText().trim() == null)
            {
                throw new QMRemoteException(RESOURCE,
                                            CappLMRB.NO_TECHNICS_NAME_ENTERED, null);
            }

            //如果编号和名称都没有作任何更改，则抛出异常
            if (currentName.equals(nameJTextField.getText().trim()) &&
                currentNumber.equals(numJTextField.getText().trim())
                )
            {

                //通过标识工厂获得与给定值对象对应的显示标识对象。
                DisplayIdentity displayidentity = IdentityFactory.
                                                  getDisplayIdentity(
                        technicsObject);
                String s = displayidentity.getLocalizedMessage(null);
                Object aobj[] =
                        {s};
                throw new QMRemoteException(RESOURCE, CappLMRB.NO_CHANGE_MADE,
                                            aobj);
            }
            if (currentName == null ||
                !currentName.equals(nameJTextField.getText().trim()))
            {
                flag = false;
            }
            if (currentNumber == null ||
                !currentNumber.equals(numJTextField.getText().trim()))
            {
                flag = true;
            }

            String oldname = technicsObject.getTechnicsName();
            String oldnum = technicsObject.getTechnicsNumber();
            //设置新输入的工艺名称
            technicsObject.setTechnicsName(nameJTextField.getText().trim());
            //设置新输入的零部件编号
            technicsObject.setTechnicsNumber(numJTextField.getText().trim());

            //调用服务方法，修改数据库中工艺的编号和名称
            ServiceRequestInfo info1 = new ServiceRequestInfo();
            info1.setServiceName("StandardCappService");
            info1.setMethodName("renameTechnicsMaster");
            Class[] paraClass1 =
                    {QMTechnicsMasterIfc.class, boolean.class};
            info1.setParaClasses(paraClass1);
            Object[] objs1 =
                    {technicsObject, new Boolean(flag)};
            info1.setParaValues(objs1);
            try
            {
                technicsObject = (QMTechnicsMasterInfo) server.request(info1);
            }
            catch (QMRemoteException ex)
            {
                technicsObject.setTechnicsName(oldname);
                technicsObject.setTechnicsNumber(oldnum);
                throw ex;
            }

            if (technicsObject instanceof MasteredIfc)
            {

                //调用服务VersionControlService的方法,给定master，得到这个对象的
                //所有的小版本（包括不同分枝），也就是master对应的所有小版本。
                ServiceRequestInfo info2 = new ServiceRequestInfo();
                info2.setServiceName("VersionControlService");
                info2.setMethodName("allIterationsOf");
                Class[] paraClass2 =
                        {MasteredIfc.class};
                info2.setParaClasses(paraClass2);
                Object[] objs2 =
                        {(MasteredIfc) technicsObject};
                info2.setParaValues(objs2);
                Collection result = null;
                try
                {
                    result = (Collection) server.request(info2);
                }
                catch (QMRemoteException ex)
                {
                    technicsObject.setTechnicsName(oldname);
                    technicsObject.setTechnicsNumber(oldnum);
                    throw ex;
                }

                //将各版本进行刷新
                if (result != null)
                {
                    Object obj;
                    for (Iterator i = result.iterator(); i.hasNext();
                                      dispatchRefresh(obj, RefreshEvent.UPDATE))
                    {
                        obj = i.next();
                    }
                }
            }

            //发布刷新事件，刷新业务对象的唯一标识
            dispatchRefresh(technicsObject, RefreshEvent.UPDATE);
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsReguRenameJPanel.changeIdentity() end...return: " +
                    technicsObject);
        }
        return technicsObject;
    }


    /**
     * 根据具体的事件动作标识和事件目标，发布刷新事件
     * @param obj target事件目标
     * @param i  具体的事件动作标识
     */
    private void dispatchRefresh(Object obj, int i)
    {
        //实例化刷新事件
        RefreshEvent refreshEvent = new RefreshEvent(getParent(), i, obj);
        //指派刷新事件
        RefreshService.getRefreshService().dispatchRefresh(refreshEvent);

    }


    /**
     * 刷新界面
     */
    public void refresh()
    {
        invalidate();
        doLayout();
        repaint();
    }


    /**
     * 检验必填区域是否已有有效值
     * @return  如果必填区域已有有效值，则返回为真
     */
    protected boolean checkRequiredFields()
            throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsReguRenameJPanel.checkRequiredFields() begin...");
        }
        boolean isOK = false;

        isOK = numJTextField.check();
        if (isOK)
        {
            isOK = nameJTextField.check();

        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsReguRenameJPanel.checkRequiredFields() end...return: " +
                               isOK);
        }
        return isOK;

    }

}
