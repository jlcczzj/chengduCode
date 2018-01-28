package com.faw_qm.cappclients.conscapproute.view;
//SS1 把“路线”修改成“路线单位” liuyang 2014-2-11
//SS2 把界面的搜索条件增加更新的”开始时间”和结束时间  liuyang 2014-2-24
//SS3 隐藏零部件Id列 liuyang 2013-2-25
//SS4 创建者为显示名 Liuyang 2013-2-25
/**
 * SS5 按解放艺准搜索支持模糊搜索 liunan 2014-2-17
 * SS6 结果大于100时分页显示有问题，并将每页大小改为10个 liunan 2014-2-17
 */
import java.awt.*;


import javax.swing.*;
import java.awt.event.*;
import java.util.*;

import com.faw_qm.clients.util.*;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.part.model.*;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.cappclients.util.*;
import com.faw_qm.cappclients.resource.view.SortingSelectedPanel;
import com.faw_qm.codemanage.model.CodingClassificationIfc;


/**
 * <p>Title:搜索解放艺准通知书用来获取关联零部件 </p>
 * @author 刘楠 2013-10-23
 * @version 1.0
 */

public class JFRouteListSearchJDialog extends JDialog
{
    private static final long serialVersionUID = 1L;
    private CalendarSelectedPanel beginTime = new CalendarSelectedPanel();
    private CalendarSelectedPanel endTime = new CalendarSelectedPanel();
    //CCBegin SS2
    private CalendarSelectedPanel updateBeginTime = new CalendarSelectedPanel();
    private CalendarSelectedPanel updateEndTime = new CalendarSelectedPanel();
    //CCEnd SS2
    private JPanel panel1 = new JPanel();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel jPanel1 = new JPanel();
    private JLabel numLabel = new JLabel();
    private JTextField numJTextField = new JTextField();
    private JCheckBox numJCheckBox = new JCheckBox();
    private JLabel nameLabel = new JLabel();
    private JTextField nameJTextField = new JTextField();
    private JCheckBox nameJCheckBox = new JCheckBox();
    private JLabel creatorLabel = new JLabel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JPanel jPanel2 = new JPanel();
    private JButton searchJButton = new JButton();
    private JButton stopJButton = new JButton();
    private QMMultiList qMMultiList = new QMMultiList();
    private JPanel jPanel3 = new JPanel();
    
    private JLabel routeLabel = new JLabel();
    private SortingSelectedPanel routeSortingSelectedPanel = null;
    private JPanel jPanel4 = new JPanel();
    
    private JButton okJButton = new JButton();
    private JButton cancelJButton = new JButton();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JLabel qMStatus = new JLabel();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private GridBagLayout gridBagLayout5 = new GridBagLayout();
    
    

    /** 用于标记资源文件路径 */
    protected static String RESOURCE = "com.faw_qm.qq.util.OrderKindResource";

    /** 测试变量 */
    private static boolean verbose = true;

    /** 用于标记资源信息 */
    protected static ResourceBundle resource = null;

    /** 中止进程标志 */
    protected boolean cancelInProgress = false;

    /** 线程组 */
    private static ThreadGroup theThreadGroup = Thread.currentThread().getThreadGroup();

    /** 检索控制类 */
    private SearchThread searchThread;

    private HashMap resultVector = new HashMap();

    private JCheckBox appendResultJCheckBox = new JCheckBox();
    private JCheckBox ignoreCaseCheckBox = new JCheckBox();
    private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.clients.util.PaginatePanel();
    JLabel jLabelBeginTime = new JLabel();
    JLabel jLabelEndTime = new JLabel();
    //CCBegin SS2
    JLabel jLabelUpdateBeginTime = new JLabel();
    JLabel jLabelUpdateEndTime = new JLabel();
    //CCEnd SS2

    /**
     * 构造函数
     * @param frame 父窗口
     */
    public JFRouteListSearchJDialog(JFrame frame)
    {
        super(frame);
        try
        {
            jbInit();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 缺省的构造函数
     */
    public JFRouteListSearchJDialog()
    {
        this(null);
    }

    /**
     * 组件初始化
     * @throws Exception
     */
    private void jbInit()
    {
        this.setTitle("搜索解放艺准通知书");
        this.setSize(800, 600);
        panel1.setLayout(gridBagLayout3);
        panel1.setBounds(new Rectangle(0, 0, 650, 500));
        numLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numLabel.setText("解放艺准编号");
        jPanel1.setLayout(gridBagLayout1);
        numJCheckBox.setMaximumSize(new Dimension(37, 22));
        numJCheckBox.setMinimumSize(new Dimension(37, 22));
        numJCheckBox.setPreferredSize(new Dimension(37, 22));
        numJCheckBox.setText("非");
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setText("解放艺准名称");
        nameJCheckBox.setMaximumSize(new Dimension(37, 22));
        nameJCheckBox.setMinimumSize(new Dimension(37, 22));
        nameJCheckBox.setPreferredSize(new Dimension(37, 22));
        nameJCheckBox.setText("非");
        jPanel2.setMaximumSize(new Dimension(70, 70));
        jPanel2.setMinimumSize(new Dimension(70, 70));
        jPanel2.setPreferredSize(new Dimension(70, 70));
        jPanel2.setLayout(gridBagLayout4);
        searchJButton.setMaximumSize(new Dimension(75, 23));
        searchJButton.setMinimumSize(new Dimension(75, 23));
        searchJButton.setPreferredSize(new Dimension(75, 23));
        searchJButton.setToolTipText("Search");
        searchJButton.setMnemonic('F');
        searchJButton.setText("搜索(F)");
        searchJButton.addActionListener(new JFRouteListSearchJDialog_searchJButton_actionAdapter(this));
        stopJButton.setMaximumSize(new Dimension(75, 23));
        stopJButton.setMinimumSize(new Dimension(75, 23));
        stopJButton.setPreferredSize(new Dimension(75, 23));
        stopJButton.setToolTipText("Stop");
        stopJButton.setMnemonic('S');
        stopJButton.setText("停止(S)");
        stopJButton.addActionListener(new JFRouteListSearchJDialog_stopJButton_actionAdapter(this));
        jPanel3.setLayout(gridBagLayout2);
        
        //CCBegin SS1
//        routeLabel.setText("路线"); 
        routeLabel.setText("路线单位"); 
        //CCEnd SS1
        routeSortingSelectedPanel = new SortingSelectedPanel("路线组织机构", "代码分类");
        routeSortingSelectedPanel.setButtonSize(89, 23);
        //CCBegin SS1
//        routeSortingSelectedPanel.setDialogTitle("路线选择");
        routeSortingSelectedPanel.setDialogTitle("路线单位选择");
        //CCEnd SS1
        routeSortingSelectedPanel.setIsSelectCC(true);
        routeSortingSelectedPanel.setIsShorten(true);
        jPanel4.setLayout(gridBagLayout5);
        
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("确定(Y)");
        okJButton.setEnabled(false);
        okJButton.addActionListener(new JFRouteListSearchJDialog_okJButton_actionAdapter(this));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消(C)");
        cancelJButton.addActionListener(new JFRouteListSearchJDialog_cancelJButton_actionAdapter(this));
        qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        qMStatus.setMaximumSize(new Dimension(4, 22));
        qMStatus.setMinimumSize(new Dimension(4, 22));
        qMStatus.setPreferredSize(new Dimension(4, 22));
        appendResultJCheckBox.setText("附加结果");
        ignoreCaseCheckBox.setText("忽略大小写");
        ignoreCaseCheckBox.setSelected(true);
        //CCBegin SS2
//        jLabelBeginTime.setText("开始时间");
//        jLabelEndTime.setText("结束时间");
        jLabelBeginTime.setText("创建开始时间");
        jLabelEndTime.setText("创建结束时间");
        jLabelUpdateBeginTime.setText("更新开始时间");
        jLabelUpdateEndTime.setText("更新结束时间");
        //CCEnd SS2
        jPanel2.add(searchJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(stopJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
        panel1.add(qMMultiList, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 0, 0));
        panel1.add(jPanel3, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 10), 0, 0));
        jPanel3.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 6, 0, 0), 0, 0));
        
        
        jPanel4.add(routeLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        jPanel4.add(routeSortingSelectedPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        jPanel3.add(jPanel4, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 20), 0, 0));
        
        jPanel3.add(okJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(cancelJButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        panel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 0, 0), 0, 0));
        panel1.add(jPanel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(22, 20, 0, 10), 10, 0));
        jTabbedPane1.add(jPanel1, "搜索解放艺准通知书");
        getContentPane().add(panel1, null);
        jPanel1.add(nameLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel1.add(numLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 10, 0));
        jPanel1.add(numJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(6, 8, 3, 0), 0, 0));
        jPanel1.add(numJCheckBox, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(6, 10, 3, 10), 4, 0));
        jPanel1.add(nameJTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 3, 0), 0, 0));
        jPanel1.add(nameJCheckBox, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 11, 0, 9), 6, 0));
        jPanel1.add(jLabelBeginTime, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 6, 0), 0, 0));
        jPanel1.add(beginTime, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 6, 0), 0, 0));
        jPanel1.add(jLabelEndTime, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 6, 0), 0, 0));
        jPanel1.add(endTime, new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 6, 0), 0, 0));
        //CCBegin SS2
        jPanel1.add(jLabelUpdateBeginTime, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 6, 0), 0, 0));
        jPanel1.add(updateBeginTime, new GridBagConstraints(1, 6, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 6, 0), 0, 0));
        jPanel1.add(jLabelUpdateEndTime, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 6, 0), 0, 0));
        jPanel1.add(updateEndTime, new GridBagConstraints(1, 7, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 6, 0), 0, 0));
        //CCEnd SS2
        panel1.add(qMStatus, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(appendResultJCheckBox, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        panel1.add(ignoreCaseCheckBox, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        localize();

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
        String[] headings = {"id", "艺准编号", "艺准名称", "创建者", "创建时间", "状态"};
        qMMultiList.setHeadings(headings);
        //CCBegin SS3
//        qMMultiList.setRelColWidth(new int[]{1, 1, 1, 1, 1, 1});
        qMMultiList.setRelColWidth(new int[]{0, 1, 1, 1, 1, 1});
        //CCEnd SS3
        qMMultiList.setMultipleMode(true);
        qMMultiList.addActionListener(new JFRouteListSearchJDialog_qMMultiList_actionAdapter(this));
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
        {
            okJButton.setEnabled(true);
        }
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
        //通知书类型不用获取 直接判断
    	//CCBegin SS2
//        Object[][] objs = new Object[5][2];
        Object[][] objs = new Object[7][2];
        //CCEnd SS2
        objs[0][0] = numJTextField.getText().trim();
        objs[0][1] = new Boolean(!numJCheckBox.isSelected());
        objs[1][0] = nameJTextField.getText().trim();
        objs[1][1] = new Boolean(!nameJCheckBox.isSelected());
        //CCBegin SS5
        objs[2][0] = new Boolean(ignoreCaseCheckBox.isSelected());
        //CCEnd SS5
        objs[3][0] = beginTime.getDate();
        objs[4][0] = endTime.getDate();
        //CCBegin SS2
        objs[5][0] = updateBeginTime.getDate();
        objs[6][0] = updateEndTime.getDate();
        //CCEnd SS2
        if(verbose)
        {
            System.out.println(objs[3][0].toString());
            System.out.println(objs[4][0].toString());
        }
        return objs;
    }

    /**
     * 根据业务类的属性和属性值来构造查询条件，然后从持久化服务中查询出数据。
     * @param flag 若flag=true，则保留原结果，否则清除上次查询结果
     */
    private void processSearchThread(boolean flag, boolean flag1)
    {
        if(verbose)
        {
            System.out.println(" processSearchThread()...begin");
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //是否清除原来结果
        clearResults(!flag);
        
				//CCBegin SS6
        paginatePanel.clearResultCache();
        //CCEnd SS6

        if(!flag)
        {
            resultVector.clear();
        }
        this.setStatus("正在搜索数据库...");
        //获得查询条件
        Object[][] condition = getCondition();
        Vector queryresult = null;
        try
        {
            //等于0时为采用单
//                //调用服务方法查询数据
                Class[] paraClass = {Object[][].class};
                Object[] paraObj = {condition};
                queryresult = (Vector)RequestHelper.request("consTechnicsRouteService", "getJFRouteList", paraClass, paraObj);
            this.postHandleResult(queryresult);
        }catch(Exception e)
        {
            e.printStackTrace();
            String message = e.getMessage();
            DialogFactory.showInformDialog(this, message);
            setCursor(Cursor.getDefaultCursor());
            if(!isCancelInProgress())
            {
                return;
            }
        }
        setCursor(Cursor.getDefaultCursor());
        if(verbose)
        {
            System.out.println("processSearchThread() end... ");
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
            System.out.println(" postHandleResult() begin...");
        }
        if(searchResult == null || searchResult.size() == 0)
        {
            setStatus("找到 0 个解放艺准通知书");
            return;
        }
        //结果列表中的路线表总数
        if(verbose)
        {
            System.out.println("the result size is:::" + searchResult.size());
        }
        int oldSize = resultVector.size();
        for(int i = 0;i < searchResult.size();i++)
        {
            if(isCancelInProgress())
            {
                break;
            }
            if(verbose)
            {
                //System.out.println("add the object to MultiList start......");
            }
            BaseValueInfo orderlist = null;
            orderlist = (com.faw_qm.technics.route.model.TechnicsRouteListInfo)searchResult.elementAt(i);
            //判断列表中是否已存在此纪录，若存在，则不用将搜索结果加入结果列表中
            boolean flag = false;
            if(resultVector.size() != 0)
            {
                Object[] ids = resultVector.keySet().toArray();
                for(int k = 0;k < resultVector.size();k++)
                {
                    if(orderlist.getBsoID().equals(ids[k]))
                    {
                        flag = true;
                        break;
                    }
                }
            }
            if(flag == true)
            {
                continue;
            }

            resultVector.put(orderlist.getBsoID(), orderlist);

        }

        if(!appendResultJCheckBox.isSelected())
        {
            setStatus("找到 " + resultVector.size() + " 个解放艺准通知书");
        }else
        {
            setStatus("附加找到 " + (resultVector.size() - oldSize) + " 个解放艺准通知书");
        }

        if(verbose)
        {
            System.out.println("postHandleResult() end");
        }
    }

    private synchronized void addResultToMultiList()
    {
        Vector newV = new Vector();
        newV.addAll(resultVector.values());
        //CCBegin SS6
        //Vector firstPageVector = paginatePanel.paginate(newV);
        Vector firstPageVector = paginatePanel.paginate(newV,10,true);
        //CCEnd SS6
        addObjectToMultiList(firstPageVector);
    }

    /**
     * 将结果添加到列表中
     * @param objs Vector
     */
    private void addObjectToMultiList(Vector objs)
    {

        qMMultiList.clear();
        if(objs == null || objs.size() == 0)
        {
            return;
        }
        //将搜索结果加入结果列表中
        int p = objs.size();
        for(int i = 0;i < p;i++)
        {
            	com.faw_qm.technics.route.model.TechnicsRouteListInfo qqadoptorder = (com.faw_qm.technics.route.model.TechnicsRouteListInfo)objs.elementAt(i);
                qMMultiList.addTextCell(i, 0, qqadoptorder.getBsoID());
                qMMultiList.addTextCell(i, 1, qqadoptorder.getRouteListNumber());    
                qMMultiList.addTextCell(i, 2, qqadoptorder.getRouteListName());
                //CCBegin SS4
//              qMMultiList.addTextCell(i, 3, qqadoptorder.getIterationCreator());
                Class[] c1 = {String.class};
                Object[] obj1 = {qqadoptorder.getIterationCreator()};
                UserInfo userInfo=null;
				try {
					userInfo = (UserInfo)RequestHelper.request("PersistService", "refreshInfo", c1, obj1);
				
				} catch (QMRemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				 String userName = userInfo.getUsersDesc();
			       if (userName == null || userName.trim().length() == 0) {
				      userName = userInfo.getUsersName();
			       }
                qMMultiList.addTextCell(i, 3, userName);
                //CCEnd SS4
                qMMultiList.addTextCell(i, 4, String.valueOf(qqadoptorder.getCreateTime()));
                qMMultiList.addTextCell(i, 5, qqadoptorder.getLifeCycleState().getDisplay());             
        }
        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 执行搜索操作
     * @param e ActionEvent
     */
    void searchJButton_actionPerformed(ActionEvent e)
    {
        setButtons(false);
        //启动搜索线程
        searchThread = new SearchThread(theThreadGroup, this, appendResultJCheckBox.isSelected(), ignoreCaseCheckBox.isSelected());
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

    /**
     * 点击确定按钮后，将选中的解放艺准通知书关联的零部件添加到列表中
     */
    private void processOKCommond()
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.dispose();
        BaseValueInfo[] objs = null;
        Vector queryresult = null;
        objs = (com.faw_qm.technics.route.model.TechnicsRouteListInfo[])this.getSelectedDetails();
        //将选中的解放艺准通知书封装成一个Vector，传到服务端进行处理，返回QMPartMasterIfc[]
        Vector ordervector = new Vector();
        for(int i = 0;i < objs.length;i++)
        {
            ordervector.add(objs[i]);
        }
        Class[] c1 = {Vector.class, String.class};
        Object[] obj1 = {ordervector, ((CodingClassificationIfc)(routeSortingSelectedPanel.getCoding())).getClassSort()};
        System.out.println("ordervector="+ordervector);
        try
        {
            queryresult = (Vector)RequestHelper.request("consTechnicsRouteService", "findQMPartByJFRouteList", c1, obj1);
            Vector v = new Vector();
            Iterator iter = queryresult.iterator();
            while(iter.hasNext())
            {
                QMPartInfo info = (QMPartInfo)iter.next();
                v.add(info);
            }
            System.out.println("v="+v);
            PartListJDialog p = new PartListJDialog(this,v);
            p.setVisible(true);
            this.setVisible(false);

        }catch(Exception e1)
        {
            e1.printStackTrace();
        }

        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 获得结果域选中的业务对象
     * @return 选中的业务对象
     */
    private BaseValueInfo[] getSelectedDetails()
    {
        int[] rows = qMMultiList.getSelectedRows();
        BaseValueInfo[] values = null;
            values = new com.faw_qm.technics.route.model.TechnicsRouteListInfo[rows.length];
        for(int i = 0;i < rows.length;i++)
        {
            int xx = rows[i];
            String bsoid = qMMultiList.getCellText(xx, 0);
            values[i] = (com.faw_qm.technics.route.model.TechnicsRouteListInfo)resultVector.get(bsoid);
            if(verbose)
            {
                System.out.println("选中的单子是：" + values[i]);
            }
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
     * <p>Title: 内部工作线程</p> <p>Description: </p> <p>Copyright: Copyright (c) 2007</p> <p>Company: 一汽启明</p>
     * @author
     * @version 1.0
     */
    class SearchThread extends QMThread
    {
        /** 判断是否保留原来的搜索结果 */
        private boolean isLeave;
        private boolean isIgnoreCase;

        /** 搜索界面对象 */
        private JFRouteListSearchJDialog myDialog;

        /**
         * 构造查询实例
         * @param threadgroup 线程组
         * @param dialog 按工装搜索界面对象
         * @param flag 判断是否保留原来的搜索结果
         */
        public SearchThread(ThreadGroup threadgroup, JFRouteListSearchJDialog dialog, boolean flag, boolean flag1)
        {
            super(threadgroup);
            myDialog = dialog;
            isLeave = flag;
            isIgnoreCase = flag1;
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
                myDialog.processSearchThread(isLeave, isIgnoreCase);
            }
            catch(Throwable throwable)
            {
                if(!myDialog.isCancelInProgress())
                {
                    throwable.printStackTrace();
                }
            }
            finally
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

    void qMMultiList_actionPerformed(ActionEvent e)
    {
        processOKCommond();
    }

    void paginatePanel_paginateEvent(com.faw_qm.clients.util.PaginateEvent e)
    {
        this.addObjectToMultiList(paginatePanel.getCurrentObjects());
    }

}

class JFRouteListSearchJDialog_searchJButton_actionAdapter implements java.awt.event.ActionListener
{
    private JFRouteListSearchJDialog adaptee;

    JFRouteListSearchJDialog_searchJButton_actionAdapter(JFRouteListSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.searchJButton_actionPerformed(e);
    }
}

class JFRouteListSearchJDialog_stopJButton_actionAdapter implements java.awt.event.ActionListener
{
    private JFRouteListSearchJDialog adaptee;

    JFRouteListSearchJDialog_stopJButton_actionAdapter(JFRouteListSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.stopJButton_actionPerformed(e);
    }
}

/*class JFRouteListSearchJDialog_routeJButton_actionAdapter implements java.awt.event.ActionListener
{
    private JFRouteListSearchJDialog adaptee;

    JFRouteListSearchJDialog_routeJButton_actionAdapter(JFRouteListSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.routeJButton_actionPerformed(e);
    }
}*/

class JFRouteListSearchJDialog_okJButton_actionAdapter implements java.awt.event.ActionListener
{
    private JFRouteListSearchJDialog adaptee;

    JFRouteListSearchJDialog_okJButton_actionAdapter(JFRouteListSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.okJButton_actionPerformed(e);
    }
}

class JFRouteListSearchJDialog_cancelJButton_actionAdapter implements java.awt.event.ActionListener
{
    private JFRouteListSearchJDialog adaptee;

    JFRouteListSearchJDialog_cancelJButton_actionAdapter(JFRouteListSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.cancelJButton_actionPerformed(e);
    }
}

class JFRouteListSearchJDialog_qMMultiList_actionAdapter implements java.awt.event.ActionListener
{
    private JFRouteListSearchJDialog adaptee;

    JFRouteListSearchJDialog_qMMultiList_actionAdapter(JFRouteListSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.qMMultiList_actionPerformed(e);
    }
}
