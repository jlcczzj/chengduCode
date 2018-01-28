package com.faw_qm.cappclients.conscapproute.view;

import java.awt.*;


import javax.swing.*;
import java.awt.event.*;
import java.util.*;

import com.faw_qm.clients.util.*;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.enterprise.util.PDMIcons;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.part.model.*;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.util.RouteListLevelType;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.cappclients.conscapproute.util.RouteListTreeObject;
import com.faw_qm.cappclients.util.*;
import com.faw_qm.cappclients.resource.view.SortingSelectedPanel;
import com.faw_qm.codemanage.model.CodingClassificationIfc;


/**
 * <p>Title:搜索长特一级路线用来获取关联零部件 </p>
 * @author 刘扬 2014-8-25
 * @version 1.0
 */

public class CTRouteLevelOnePartSearchJDialog extends JDialog
{
	private JPanel panel1 = new JPanel();

    private JTabbedPane jTabbedPane1 = new JTabbedPane();

    private JPanel jPanel1 = new JPanel();

    private JLabel jLabel1 = new JLabel();

    private JTextField numJTextField = new JTextField();

    private JCheckBox numJCheckBox = new JCheckBox();

    private JLabel jLabel2 = new JLabel();

    private JTextField nameJTextField = new JTextField();

    private JCheckBox nameJCheckBox = new JCheckBox();

    private JLabel jLabel3 = new JLabel();

    private JComboBox levelJComboBox = new JComboBox();

    private JLabel jLabel4 = new JLabel();

    private JTextField productJTextField = new JTextField();

    private JCheckBox productJCheckBox = new JCheckBox();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JPanel jPanel2 = new JPanel();

    private JButton searchJButton = new JButton();

    private JButton stopJButton = new JButton();

    private QMMultiList qMMultiList = new QMMultiList();

    private JPanel jPanel3 = new JPanel();

    private JButton okJButton = new JButton();

    private JButton cancelJButton = new JButton();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private JLabel qMStatus = new JLabel();

    private GridBagLayout gridBagLayout3 = new GridBagLayout();

    private GridBagLayout gridBagLayout4 = new GridBagLayout();

    private JLabel jLabel5 = new JLabel();

    private JTextField versionJTextField = new JTextField();

    private JCheckBox versionJCheckBox = new JCheckBox();

    /** 用于标记资源文件路径 */
    protected static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /** 测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /** 用于标记资源信息 */
    protected static ResourceBundle resource = null;

    /** 中止进程标志 */
    protected boolean cancelInProgress = false;

    /** 线程组 */
    private static ThreadGroup theThreadGroup = Thread.currentThread().getThreadGroup();

    /** 检索控制类 */
    private SearchThread searchThread;

    private HashMap resultVector = new HashMap();

    //    /** 标准图标 */
    //    private static Image startandImage;
    //
    //    /** 检出图标 */
    //    private static Image checkOutImage;
    //
    //    /** 原本图标 */
    //    private static Image workingImage;

    private CappRouteListManageJFrame view;
    private RouteListPartLinkJPanel panel ;
    private JCheckBox appendResultJCheckBox = new JCheckBox();

    private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.clients.util.PaginatePanel();
    // 查询忽略大小写
    JCheckBox ignorejCheckBox = new JCheckBox();

    /**
     * 构造函数
     * @param frame 父窗口
     */
    public CTRouteLevelOnePartSearchJDialog(JFrame frame,JPanel panel)
    {
    	 super(frame);
        view = (CappRouteListManageJFrame)frame;
        this.panel = (RouteListPartLinkJPanel)panel;
        try
        {
            jbInit();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * 组件初始化
     * @throws Exception
     */
    private void jbInit()
    {
        this.setTitle("搜索工艺路线表");
        this.setSize(650, 500);
        panel1.setLayout(gridBagLayout3);
        panel1.setBounds(new Rectangle(0, 0, 650, 500));
        jLabel1.setMaximumSize(new Dimension(24, 22));
        jLabel1.setMinimumSize(new Dimension(24, 22));
        jLabel1.setPreferredSize(new Dimension(24, 22));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText("编号");
        jPanel1.setLayout(gridBagLayout1);
        numJCheckBox.setMaximumSize(new Dimension(37, 22));
        numJCheckBox.setMinimumSize(new Dimension(37, 22));
        numJCheckBox.setPreferredSize(new Dimension(37, 22));
        numJCheckBox.setText("非");
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setText("名称");
        nameJCheckBox.setMaximumSize(new Dimension(37, 22));
        nameJCheckBox.setMinimumSize(new Dimension(37, 22));
        nameJCheckBox.setPreferredSize(new Dimension(37, 22));
        nameJCheckBox.setText("非");
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText("级别");
        jLabel4.setMaximumSize(new Dimension(52, 22));
        jLabel4.setMinimumSize(new Dimension(52, 22));
        jLabel4.setPreferredSize(new Dimension(52, 22));
        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel4.setText("相关产品");
        productJCheckBox.setMaximumSize(new Dimension(37, 22));
        productJCheckBox.setMinimumSize(new Dimension(37, 22));
        productJCheckBox.setPreferredSize(new Dimension(37, 22));
        productJCheckBox.setText("非");
        jPanel2.setMaximumSize(new Dimension(70, 70));
        jPanel2.setMinimumSize(new Dimension(70, 70));
        jPanel2.setPreferredSize(new Dimension(70, 70));
        jPanel2.setLayout(gridBagLayout4);
        searchJButton.setMaximumSize(new Dimension(75, 23));
        searchJButton.setMinimumSize(new Dimension(75, 23));
        searchJButton.setPreferredSize(new Dimension(75, 23));
        searchJButton.setToolTipText("Search");
        searchJButton.setText("搜索(F)");
        searchJButton.setMnemonic('F');
        searchJButton.addActionListener(new CTRouteLevelOnePartSearchJDialog_searchJButton_actionAdapter(this));
        stopJButton.setMaximumSize(new Dimension(75, 23));
        stopJButton.setMinimumSize(new Dimension(75, 23));
        stopJButton.setPreferredSize(new Dimension(75, 23));
        stopJButton.setToolTipText("Stop");
        stopJButton.setText("停止(S)");
        stopJButton.setMnemonic('S');
        stopJButton.addActionListener(new CTRouteLevelOnePartSearchJDialog_stopJButton_actionAdapter(this));
        jPanel3.setLayout(gridBagLayout2);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("确定(Y)");
        okJButton.setEnabled(false);
        okJButton.addActionListener(new CTRouteLevelOnePartSearchJDialog_okJButton_actionAdapter(this));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消(C)");
        cancelJButton.addActionListener(new CTRouteLevelOnePartSearchJDialog_cancelJButton_actionAdapter(this));
        qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        qMStatus.setMaximumSize(new Dimension(4, 22));
        qMStatus.setMinimumSize(new Dimension(4, 22));
        qMStatus.setPreferredSize(new Dimension(4, 22));
        jLabel5.setMaximumSize(new Dimension(41, 22));
        jLabel5.setMinimumSize(new Dimension(41, 22));
        jLabel5.setPreferredSize(new Dimension(41, 22));
        jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel5.setText("版本");
        versionJCheckBox.setMaximumSize(new Dimension(37, 22));
        versionJCheckBox.setMinimumSize(new Dimension(37, 22));
        versionJCheckBox.setPreferredSize(new Dimension(37, 22));
        versionJCheckBox.setText("非");
        appendResultJCheckBox.setText("附加结果");
        ignorejCheckBox.setText("忽略大小写");
        ignorejCheckBox.addActionListener(new CTRouteLevelOnePartSearchJDialog_jCheckBox1_actionAdapter(this));
        jPanel2.setMaximumSize(new Dimension(80, 75));
        jPanel2.setPreferredSize(new Dimension(80, 75));
        jPanel2.setMinimumSize(new Dimension(80, 75));
        jPanel2.add(searchJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(stopJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
        panel1.add(jPanel3, new GridBagConstraints(0, 4, 3, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 10), 0, 0));
        jPanel3.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 6, 0, 0), 0, 0));
        jPanel3.add(okJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        panel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 2, 2, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 0, 8), 0, 0));
        panel1.add(jPanel2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(22, 15, 0, 8), 0, 0));
        
        jTabbedPane1.add(jPanel1, "搜索条件");
        getContentPane().add(panel1, null);
        jPanel1.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(6, 0, 3, 0), 0, 0));
        jPanel1.add(numJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(6, 8, 3, 0), 0, 0));
        jPanel1.add(numJCheckBox, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(6, 8, 3, 10), 1, 0));
        jPanel1.add(nameJTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 3, 0), 0, 0));
        jPanel1.add(nameJCheckBox, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 10), 0, 0));
        jPanel1.add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel1.add(levelJComboBox, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 3, 0), 0, 0));
        jPanel1.add(jLabel4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        jPanel1.add(productJTextField, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 3, 0), 0, 0));
        jPanel1.add(productJCheckBox, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 10), 0, 0));
        jPanel1.add(jLabel5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 6, 0), 0, 0));
        jPanel1.add(versionJTextField, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 6, 0), 0, 0));
        jPanel1.add(versionJCheckBox, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 8, 6, 10), 0, 0));
        panel1.add(qMStatus, new GridBagConstraints(0, 5, 3, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(appendResultJCheckBox, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 6, 0, 0), 0, 0));
        panel1.add(qMMultiList, new GridBagConstraints(0, 3, 3, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 0, 0));
        panel1.add(ignorejCheckBox, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        localize();
      //  levelJComboBox.addItem("");
        levelJComboBox.addItem(RouteListLevelType.FIRSTROUTE.getDisplay());
       // levelJComboBox.addItem(RouteListLevelType.SENCONDROUTE.getDisplay());
        levelJComboBox.setSelectedItem(0);

        paginatePanel.addListener(new com.faw_qm.clients.util.PaginateListener()
        {
            public void paginateEvent(com.faw_qm.clients.util.PaginateEvent e)
            {
                paginatePanel_paginateEvent(e);
            }
        });
    }

    /**
     * 本地化
     */
    private void localize()
    {
        qMMultiList.setCellEditable(false);
        qMMultiList.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                setOkButtonEnabled();
            }
        });
        String[] headings = {"id", "编号", "名称", "级别", "相关产品编号", "版本"};
        qMMultiList.setHeadings(headings);
        qMMultiList.setRelColWidth(new int[]{0, 1, 1, 1, 1, 1});
        qMMultiList.setMultipleMode(true);
        qMMultiList.addActionListener(new CTRouteLevelOnePartSearchJDialog_qMMultiList_actionAdapter(this));
        qMMultiList.setEnabled(false);

    }

    /**
     * 设置界面的显示位置
     */
    private void setViewLocation()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if(frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if(frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    }

    /**
     * 重载父类方法，使界面显示在屏幕中央
     * @param flag
     */
    public void setVisible(boolean flag)
    {
        this.setViewLocation();
        super.setVisible(flag);
    }

    /**
     * 设置按钮的编辑状态，仅供内部使用
     * @param flag
     */
    private void setButtons(boolean flag)
    {
        searchJButton.setEnabled(flag);
        stopJButton.setEnabled(!flag);

        if(qMMultiList.getSelectedRows() == null)
        {
            okJButton.setEnabled(false);
        }
        cancelJButton.setEnabled(flag);
    }

    /**
     * 设置确定按钮的状态
     */
    private void setOkButtonEnabled()
    {
        if(qMMultiList.getSelectedRows() == null)
        {
            okJButton.setEnabled(false);
        }else
            okJButton.setEnabled(true);
    }

    /**
     * 设置状态条信息
     * @param text 状态条要显示的文本
     */
    private void setStatus(String text)
    {
        qMStatus.setText(text);
    }

    /**
     * 清空结果域
     * @param flag 如果flag = true,则清空，否则不清
     */
    private synchronized void clearResults(boolean flag)
    {
        if(flag)
        {
            qMMultiList.clear();
            okJButton.setEnabled(false);
        }
        setStatus(" ");
    }

    /**
     * 判断是否中止检索线程
     * @return 如果返回True，则中止线程。
     */
    private boolean isCancelInProgress()
    {
        return cancelInProgress;
    }

    /**
     * 设置是否中止线程
     * @param flag 如果flag=True，则中止线程。
     */
    private synchronized void setCancelInProgress(boolean flag)
    {
        cancelInProgress = flag;
    }

    /**
     * 中止检索进程
     */
    private synchronized void cancel()
    {
        if(searchThread != null && searchThread.isAlive())
        {
            setCancelInProgress(true);
            //searchThread.interrupt(); //将这句去掉，是因为它使界面退出了，这不合业务
            searchThread = null;
        }
    }

    /**
     * 获得用户录入的搜索条件
     * @return 搜索条件
     */
    private Object[][] getCondition()
    {
        // Object[][] objs = new Object[5][2];
        Object[][] objs = new Object[6][2];
        objs[0][0] = numJTextField.getText().trim();
        objs[0][1] = new Boolean(!numJCheckBox.isSelected());
        objs[1][0] = nameJTextField.getText().trim();
        objs[1][1] = new Boolean(!nameJCheckBox.isSelected());
        objs[2][0] = levelJComboBox.getSelectedItem();
        objs[2][1] = new Boolean(true);
        objs[3][0] = productJTextField.getText().trim();
        objs[3][1] = new Boolean(!productJCheckBox.isSelected());
        objs[4][0] = versionJTextField.getText().trim();
        objs[4][1] = new Boolean(!versionJCheckBox.isSelected());
        objs[5][0] = new Boolean(ignorejCheckBox.isSelected());

        return objs;
    }

    /**
     * 根据业务类的属性和属性值来构造查询条件，然后从持久化服务中查询出数据。
     * @param flag 若flag=true，则保留原结果，否则清除上次查询结果
     */
    private void processSearchThread(boolean flag)
    {
        if(verbose)
        {
            System.out.println("part.client.other.view.ExtendSearchJDialog: processSearchThread()...begin");
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //是否清除原来结果
        clearResults(!flag);
        //（问题一）清除分页bean的缓存 zz 20061106
        paginatePanel.clearResultCache();
        if(!flag)
            resultVector.clear();
        this.setStatus("正在搜索数据库...");
        //获得查询条件
        Object[][] condition = getCondition();
        //add by guoxl on 2008.11.13(通用搜索条件为空时弹出提示信息)
        String str = new String();
        for(int i = 0;i < 5;i++)
        {
            str += condition[i][0];

        }
        if(str.equals("") || str == null)
        {
            JOptionPane.showMessageDialog(this, "搜索条件不能为空，请输入搜索条件！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
            setCursor(Cursor.getDefaultCursor());
            return;

        }
        //add by guoxl end
        // try {
        //调用服务方法查询数据
        Class[] paraClass = {Object[][].class};
        Object[] paraObj = {condition};
 
        
        try
        {
            Vector totalResult = new Vector(1000);
            boolean bool = true;
            while(bool)//xucy 2011.12.19
            {
                
                Collection result = (Collection)RequestHelper.request("consTechnicsRouteService", "findRouteLists", paraClass, paraObj);
                
                if(result.equals("over"))
                {
                    break;
                }
                Iterator i = result.iterator();
                while(i.hasNext())
                {
                    TechnicsRouteListInfo list = (TechnicsRouteListInfo)i.next();
                    totalResult.add(list);
                }
                postHandleResult(totalResult);
                bool = false;
                //处理result逻辑
            }
        }catch(Exception e)
        {
        	e.printStackTrace();
            String message = e.getMessage();
            DialogFactory.showWarningDialog(this, message);
            setCursor(Cursor.getDefaultCursor());
            if(!isCancelInProgress())
            {
                return;
            }
        }

        //        catch(Exception e)
        //        {
        //            server.close(info1); //关闭连接方法
        //        }

        /*
         * Vector queryresult = (Vector) RParentJPanel.useServiceMethod( "TechnicsRouteService", "findRouteLists", paraClass, paraObj); this.postHandleResult(queryresult);
         */
        /*
         * } catch (QMRemoteException e) { String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null); JOptionPane.showMessageDialog(this, e.getClientMessage(), title,
         * JOptionPane.INFORMATION_MESSAGE); setCursor(Cursor.getDefaultCursor()); if (!isCancelInProgress()) { return; } }
         */
        setCursor(Cursor.getDefaultCursor());
        if(verbose)
        {
            System.out.println("part.client.other.view.ExtendSearchJDialog: processSearchThread() end...return is void ");
        }
    }

    /**
     * 结果集后处理,将搜索结果加入结果列表中
     * @param searchResult 搜索结果(工艺路线表值对象)
     * @throws QMRemoteException
     */
    private void postHandleResult(Vector searchResult)
    {
        if(verbose)
        {
            System.out.println("part.client.other.view.ExtendSearchJDialog: postHandleResult() begin...");
        }
        if(searchResult == null || searchResult.size() == 0)
        {
            setStatus("找到 0 个工艺路线表");
            return;
        }
        //结果列表中的路线表总数
        if(verbose)
            System.out.println("the result size is:::" + searchResult.size());
        int oldSize = resultVector.size();
        for(int i = 0;i < searchResult.size();i++)
        {
            if(isCancelInProgress())
            {
                break;
            }
            if(verbose)
                System.out.println("add the object to MultiList start......");
            TechnicsRouteListInfo routelist = (TechnicsRouteListInfo)searchResult.elementAt(i);

            //判断列表中是否已存在此纪录，若存在，则不用将搜索结果加入结果列表中
            boolean flag = false;
            if(resultVector.size() != 0)
            {
                Object[] ids = resultVector.keySet().toArray();
                for(int k = 0;k < resultVector.size();k++)
                {
                    if(routelist.getBsoID().equals(ids[k]))
                    {
                        flag = true;
                        break;
                    }
                }
            }
            if(flag == true)
                continue;

            resultVector.put(routelist.getBsoID(), routelist);

        }

        if(!appendResultJCheckBox.isSelected())
            setStatus("找到 " + resultVector.size() + " 个工艺路线表");
        else
        {
            setStatus("附加找到 " + (resultVector.size() - oldSize) + " 个工艺路线表");
        }

        if(verbose)
        {
            System.out.println("part.client.other.view.ExtendSearchJDialog: postHandleResult() end...return is void");
        }
    }

    private synchronized void addResultToMultiList()
    {
        Vector newV = new Vector();
        newV.addAll(resultVector.values());
        Vector firstPageVector = paginatePanel.paginate(newV, 10, true);
        addObjectToMultiList(firstPageVector);
    }

    private void addObjectToMultiList(Vector objs)
    {
        try
        {
            qMMultiList.clear();
            if(objs == null || objs.size() == 0)
                return;
            //将搜索结果加入结果列表中
            int p = objs.size();
            for(int i = 0;i < p;i++)
            {
                TechnicsRouteListInfo routelist = (TechnicsRouteListInfo)objs.elementAt(i);
                qMMultiList.addTextCell(i, 0, routelist.getBsoID());
            
                Image img = (PDMIcons.getClientBsoIcon(routelist)).getImage();
                //end CR2
                qMMultiList.addCell(i, 1, routelist.getRouteListNumber(), img);
                qMMultiList.addTextCell(i, 2, routelist.getRouteListName());
                qMMultiList.addTextCell(i, 3, routelist.getRouteListLevel());
                //20111223 xucy
                if(routelist.getProductMasterID() != null)
                    qMMultiList.addTextCell(i, 4, ((QMPartMasterInfo)RParentJPanel.refreshInfo(routelist.getProductMasterID())).getPartNumber());
                qMMultiList.addTextCell(i, 5, routelist.getVersionValue());
            }
        }catch(Exception e)
        {
            String message = e.getMessage();
            DialogFactory.showInformDialog(this, message);
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * 执行搜索操作
     * @param e ActionEvent
     */
    void searchJButton_actionPerformed(ActionEvent e)
    {
        setButtons(false);
        //启动搜索线程
        searchThread = new SearchThread(theThreadGroup, this, appendResultJCheckBox.isSelected());
        searchThread.start();

    }

    /**
     * 执行“中止”操作
     * @param e ActionEvent
     */
    void stopJButton_actionPerformed(ActionEvent e)
    {
        stopJButton.setEnabled(false);
        cancel();
    }

    /**
     * 执行“确定”操作
     * @param e ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        processOKCommond();
    }

    private void processOKCommond()
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.dispose();
        //获得选择的所有路线表
        BaseValueIfc[] objs = (BaseValueIfc[])this.getSelectedDetails();
        //把从最新版本的工艺路线表加入树中
        Vector v = new Vector();
        Vector queryresult = null;
        //Begin CR1
        String deleteRouteList = "";
        if(view.controller.getDeleteRouteLisVec().size() != 0)
        {
            deleteRouteList = (String)view.controller.getDeleteRouteLisVec().elementAt(0);
        }
        //End  CR1
        for(int i = 0;i < objs.length;i++)
        {
            TechnicsRouteListInfo info = (TechnicsRouteListInfo)objs[i];   
            if(deleteRouteList.equals(info.getBsoID()))
            {

                JOptionPane.showMessageDialog(this, "路线表" + "(" + info.getRouteListNumber() + ")" + "正在被删除！", "提示", JOptionPane.INFORMATION_MESSAGE);
                continue;
            }
            v.add(info);
        }
        if(v.size()>1){
        	 JOptionPane.showMessageDialog(this, "只能选择一条路线", "提示信息", JOptionPane.INFORMATION_MESSAGE);
             setCursor(Cursor.getDefaultCursor());
             return;	
        }
        if(v.size() != 0)
        {
        	  Class[] c1 = {Vector.class};
              Object[] obj1 = {v};
              try
              { 
                  queryresult = (Vector)RequestHelper.request("consTechnicsRouteService", "findQMPartByCTRouteList", c1, obj1);
                  Vector v1 = new Vector();
                  Iterator iter = queryresult.iterator();
                  while(iter.hasNext())
                  {
                      QMPartInfo info = (QMPartInfo)iter.next();
                      v1.add(info);
                  }
                  PartListJDialog p = new PartListJDialog(this,v1);
                  p.setVisible(true);
                  this.setVisible(false);

              }catch(Exception e1)
              {
                  e1.printStackTrace();
              } 
        }
        //End  CR1
        setCursor(Cursor.getDefaultCursor());
        }

    /**
     * 获得结果域选中的业务对象
     * @return 选中的业务对象
     */
    private BaseValueIfc[] getSelectedDetails()
    {
        int[] rows = qMMultiList.getSelectedRows();
        BaseValueIfc[] values = new BaseValueIfc[rows.length];
        for(int i = 0;i < rows.length;i++)
        {
            int xx = rows[i];
            String bsoid = qMMultiList.getCellText(xx, 0);
            values[i] = (TechnicsRouteListInfo)resultVector.get(bsoid);
        }
        return values;
    }

    /**
     * 执行“取消“操作
     * @param e ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        this.dispose();
    }

    /**
     * <p> Title: 内部工作线程 </p>
     */
    class SearchThread extends QMThread
    {
        /** 判断是否保留原来的搜索结果 */
        private boolean isLeave;

        private CTRouteLevelOnePartSearchJDialog myDialog;

        /**
         * 构造查询实例
         * @param threadgroup 线程组
         * @param dialog 按工装搜索界面对象
         * @param flag 判断是否保留原来的搜索结果
         */
        public SearchThread(ThreadGroup threadgroup, CTRouteLevelOnePartSearchJDialog dialog, boolean flag)
        {
            super(threadgroup);
            myDialog = dialog;
            isLeave = flag;
        }

        /**
         * 使线程中断，停止搜索进程
         */
        public synchronized void interrupt()
        {
            if(verbose)
            {
                System.out.println("Inside Interrupt......");
            }
            this.interrupt();
        }

        /**
         * 线程运行方法，执行搜索。
         */
        public synchronized void run()
        {
            if(verbose)
            {
                System.out.println("SearchThread: run() begin...");
            }
            try
            {
                //执行搜索
                myDialog.processSearchThread(isLeave);
            }catch(Throwable throwable)
            {
                if(!myDialog.isCancelInProgress())
                {
                    throwable.printStackTrace();
                }
            }finally
            {
                if(verbose)
                {
                    System.out.println("Inside run finally......");
                }
                //设置界面按钮的显示状态
                myDialog.setButtons(true);
                //设置没有处于中断状态
                myDialog.setCancelInProgress(false);
                myDialog.addResultToMultiList();
            }

            if(verbose)
            {
                System.out.println("SearchThread: run() end...return is void");
            }
        }
    }

    /**
     * qMMultiList的处理方法
     * @param e
     */
    void qMMultiList_actionPerformed(ActionEvent e)
    {
        processOKCommond();
    }

    /**
     * 往MultiList中增加对象
     * @param e com.faw_qm.clients.util.PaginateEvent
     */
    void paginatePanel_paginateEvent(com.faw_qm.clients.util.PaginateEvent e)
    {
        this.addObjectToMultiList(paginatePanel.getCurrentObjects());
    }

    void jCheckBox1_actionPerformed(ActionEvent e)
    {

    }

}

/**
 * <p>Title:搜索按钮执行适配器</p> <p>Description: </p> <p>Package:com.faw_qm.cappclients.conscapproute.view</p>
 */
class CTRouteLevelOnePartSearchJDialog_searchJButton_actionAdapter implements java.awt.event.ActionListener
{
    private CTRouteLevelOnePartSearchJDialog adaptee;

    CTRouteLevelOnePartSearchJDialog_searchJButton_actionAdapter(CTRouteLevelOnePartSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.searchJButton_actionPerformed(e);
    }
}

/**
 * <p>Title:停止按钮执行适配器</p> <p>Description: </p>
 */
class CTRouteLevelOnePartSearchJDialog_stopJButton_actionAdapter implements java.awt.event.ActionListener
{
    private CTRouteLevelOnePartSearchJDialog adaptee;

    CTRouteLevelOnePartSearchJDialog_stopJButton_actionAdapter(CTRouteLevelOnePartSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.stopJButton_actionPerformed(e);
    }
}

/**
 * <p>Title:确定按钮执行适配器</p> <p>Description: </p>
 */
class CTRouteLevelOnePartSearchJDialog_okJButton_actionAdapter implements java.awt.event.ActionListener
{
    private CTRouteLevelOnePartSearchJDialog adaptee;

    CTRouteLevelOnePartSearchJDialog_okJButton_actionAdapter(CTRouteLevelOnePartSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.okJButton_actionPerformed(e);
    }
}

/**
 * <p>Title:取消按钮执行适配器</p> <p>Description: </p>
 */
class CTRouteLevelOnePartSearchJDialog_cancelJButton_actionAdapter implements java.awt.event.ActionListener
{
    private CTRouteLevelOnePartSearchJDialog adaptee;

    CTRouteLevelOnePartSearchJDialog_cancelJButton_actionAdapter(CTRouteLevelOnePartSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.cancelJButton_actionPerformed(e);
    }
}

/**
 * <p>Title：qMMultiList执行适配器</p> <p>Description: </p>
 */
class CTRouteLevelOnePartSearchJDialog_qMMultiList_actionAdapter implements java.awt.event.ActionListener
{
    private CTRouteLevelOnePartSearchJDialog adaptee;

    CTRouteLevelOnePartSearchJDialog_qMMultiList_actionAdapter(CTRouteLevelOnePartSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.qMMultiList_actionPerformed(e);
    }
}

class CTRouteLevelOnePartSearchJDialog_jCheckBox1_actionAdapter implements java.awt.event.ActionListener
{
	CTRouteLevelOnePartSearchJDialog adaptee;

	CTRouteLevelOnePartSearchJDialog_jCheckBox1_actionAdapter(CTRouteLevelOnePartSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jCheckBox1_actionPerformed(e);
    }
}
