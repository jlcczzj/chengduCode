/**
 * SS1 传递qmpart而不是master，因为零部件表需要的是qmpartifc liunan 2013-12-24
 */

package com.faw_qm.cappclients.conscapproute.view;

import java.awt.*;


import javax.swing.*;
import java.awt.event.*;
import java.util.*;

import com.faw_qm.consadoptnotice.model.AdoptNoticeInfo;
import com.faw_qm.clients.util.*;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.part.model.*;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.cappclients.util.*;

/**
 * <p>Title:搜索变更通知书用来获取关联零部件 </p> <p>Description: </p> <p>Copyright: Copyright (c) 2007</p> <p>Company: 一汽启明</p>
 * @author 唐树涛
 * @version 1.0
 * SS2 2013-03-01 刘家坤 原因:删除“通知书类型”条件选择框",“创建者”条件输入框
 */

public class QQAdoptChangePartSearchJDialog extends JDialog
{
    private static final long serialVersionUID = 1L;
    private CalendarSelectedPanel beginTime = new CalendarSelectedPanel();
    private CalendarSelectedPanel endTime = new CalendarSelectedPanel();
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
    //CCBegin SS2
//    private JLabel typeLabel = new JLabel();
//    private JComboBox typeJComboBox = new JComboBox();

//    private JTextField creatorJTextField = new JTextField();
//    private JCheckBox creatorJCheckBox = new JCheckBox();
    //CCEnd SS2
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

    /** 用于标记资源文件路径 */
    protected static String RESOURCE = "com.faw_qm.qq.util.OrderKindResource";

    /** 测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.qq.verbose", "true")).equals("true");

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
    private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.clients.util.PaginatePanel();
    JLabel jLabelBeginTime = new JLabel();
    JLabel jLabelEndTime = new JLabel();

    /**
     * 构造函数
     * @param frame 父窗口
     */
    public QQAdoptChangePartSearchJDialog(JFrame frame)
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
    public QQAdoptChangePartSearchJDialog()
    {
        this(null);
    }

    /**
     * 组件初始化
     * @throws Exception
     */
    private void jbInit()
    {
        //CCBegin by liuzhicheng 2011-08-30  原因：参见 TD4990
        this.setTitle("搜索采用变更通知书");
        //CCEnd by liuzhicheng 2011-08-30
        this.setSize(650, 500);
        panel1.setLayout(gridBagLayout3);
        panel1.setBounds(new Rectangle(0, 0, 650, 500));
        numLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numLabel.setText("通知书编号");
        jPanel1.setLayout(gridBagLayout1);
        numJCheckBox.setMaximumSize(new Dimension(37, 22));
        numJCheckBox.setMinimumSize(new Dimension(37, 22));
        numJCheckBox.setPreferredSize(new Dimension(37, 22));
        numJCheckBox.setText("非");
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setText("通知书名称");
        nameJCheckBox.setMaximumSize(new Dimension(37, 22));
        nameJCheckBox.setMinimumSize(new Dimension(37, 22));
        nameJCheckBox.setPreferredSize(new Dimension(37, 22));
        nameJCheckBox.setText("非");
      //CCBegin SS2
//        typeLabel.setText("通知书类型");
//        typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//        typeJComboBox.setMaximumSize(new Dimension(37, 22));
//        typeJComboBox.setMinimumSize(new Dimension(37, 22));
//        typeJComboBox.setPreferredSize(new Dimension(37, 22));
//        typeJComboBox.addItem("采用单");
//        typeJComboBox.addItem("变更单");
//        creatorLabel.setMaximumSize(new Dimension(52, 22));
//        creatorLabel.setMinimumSize(new Dimension(52, 22));
//        creatorLabel.setPreferredSize(new Dimension(52, 22));
//        creatorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//        creatorLabel.setText("创建者");
//        creatorJCheckBox.setMaximumSize(new Dimension(37, 22));
//        creatorJCheckBox.setMinimumSize(new Dimension(37, 22));
//        creatorJCheckBox.setPreferredSize(new Dimension(37, 22));
//        creatorJCheckBox.setText("非");
        //CCEnd SS2
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
        searchJButton.addActionListener(new QQAdoptChangePartSearchJDialog_searchJButton_actionAdapter(this));
        stopJButton.setMaximumSize(new Dimension(75, 23));
        stopJButton.setMinimumSize(new Dimension(75, 23));
        stopJButton.setPreferredSize(new Dimension(75, 23));
        stopJButton.setToolTipText("Stop");
        stopJButton.setMnemonic('S');
        stopJButton.setText("停止(S)");
        stopJButton.addActionListener(new QQAdoptChangePartSearchJDialog_stopJButton_actionAdapter(this));
        jPanel3.setLayout(gridBagLayout2);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("确定(Y)");
        okJButton.setEnabled(false);
        okJButton.addActionListener(new QQAdoptChangePartSearchJDialog_okJButton_actionAdapter(this));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消(C)");
        cancelJButton.addActionListener(new QQAdoptChangePartSearchJDialog_cancelJButton_actionAdapter(this));
        qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        qMStatus.setMaximumSize(new Dimension(4, 22));
        qMStatus.setMinimumSize(new Dimension(4, 22));
        qMStatus.setPreferredSize(new Dimension(4, 22));
        appendResultJCheckBox.setText("附加结果");
        jLabelBeginTime.setText("开始时间");
        jLabelEndTime.setText("结束时间");
        jPanel2.add(searchJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(stopJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
        panel1.add(qMMultiList, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 0, 0));
        panel1.add(jPanel3, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 10), 0, 0));
        jPanel3.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 6, 0, 0), 0, 0));
        jPanel3.add(okJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        panel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 0, 0), 0, 0));
        panel1.add(jPanel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(22, 20, 0, 10), 10, 0));
        jTabbedPane1.add(jPanel1, "搜索采用变更通知书");
        getContentPane().add(panel1, null);
        jPanel1.add(nameLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel1.add(numLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 10, 0));
        //CCBegin SS2
        //  jPanel1.add(typeLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 10, 0));
        //CCEnd SS2
        jPanel1.add(numJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(6, 8, 3, 0), 0, 0));
        jPanel1.add(numJCheckBox, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(6, 10, 3, 10), 4, 0));
        jPanel1.add(nameJTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 3, 0), 0, 0));
        jPanel1.add(nameJCheckBox, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 11, 0, 9), 6, 0));
        //CCBegin SS2
        //jPanel1.add(typeJComboBox, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(6, 8, 3, 0), 0, 0));
       // jPanel1.add(creatorLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        //jPanel1.add(creatorJTextField, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 3, 0), 0, 0));
        //jPanel1.add(creatorJCheckBox, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 10), 7, 0));
        //CCEnd SS2
        jPanel1.add(jLabelBeginTime, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 6, 0), 0, 0));
        jPanel1.add(beginTime, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 6, 0), 0, 0));
        jPanel1.add(jLabelEndTime, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 6, 0), 0, 0));
        jPanel1.add(endTime, new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 6, 0), 0, 0));
        panel1.add(qMStatus, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(appendResultJCheckBox, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
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
        //CCBegin SS2
        //String[] headings = {"id", "通知书编号", "通知书名称", "通知书类型", "创建者", "创建时间", "说明"};
        String[] headings = {"id", "通知书编号", "通知书名称", "创建时间", "说明"};
        qMMultiList.setHeadings(headings);
        qMMultiList.setRelColWidth(new int[]{0, 1, 1, 1, 1});
        //CCEnd SS2
        qMMultiList.setMultipleMode(true);
        qMMultiList.addActionListener(new QQAdoptChangePartSearchJDialog_qMMultiList_actionAdapter(this));
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
        Object[][] objs = new Object[5][2];
        objs[0][0] = numJTextField.getText().trim();
        objs[0][1] = new Boolean(!numJCheckBox.isSelected());
        objs[1][0] = nameJTextField.getText().trim();
        objs[1][1] = new Boolean(!nameJCheckBox.isSelected());
        //CCBegin SS1
//        objs[2][0] = creatorJTextField.getText().trim();
//        objs[2][1] = new Boolean(!creatorJCheckBox.isSelected());
        //CCBegin SS1
        objs[3][0] = beginTime.getDate();
        objs[4][0] = endTime.getDate();
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
    private void processSearchThread(boolean flag)
    {
        if(verbose)
        {
            System.out.println(" processSearchThread()...begin");
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //是否清除原来结果
        clearResults(!flag);

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
        	 //CCBegin SS2
//            if(typeJComboBox.getSelectedIndex() == 0)
//            {
//                //调用服务方法查询数据
                Class[] paraClass = {Object[][].class};
                Object[] paraObj = {condition};
                queryresult = (Vector)RequestHelper.request("consTechnicsRouteService", "findAdoptOrders", paraClass, paraObj);
//            }
//            //为搜索变更单
//            else
//            {
        	
//                //调用服务方法查询数据
//                Class[] paraClass = {Object[][].class};
//                Object[] paraObj = {condition};
//                queryresult = (Vector)RequestHelper.request("consTechnicsRouteService", "findChangeOrders", paraClass, paraObj);
//            
        	 //CCEnd SS2
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
            setStatus("找到 0 个变更通知书");
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
                System.out.println("add the object to MultiList start......");
            }
            BaseValueInfo orderlist = null;
            //CCBegin SS2
//            if(typeJComboBox.getSelectedIndex() == 0)
//            {
                orderlist = (AdoptNoticeInfo)searchResult.elementAt(i);
//            }
                //CCEnd SS2
            //CCBegin SS1
//            else
//            {
//                orderlist = (ModifyInfo)searchResult.elementAt(i);
//            }
            //CCEnd SS1
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
            setStatus("找到 " + resultVector.size() + " 个变更通知书");
        }else
        {
            setStatus("附加找到 " + (resultVector.size() - oldSize) + " 个变更通知书");
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
        Vector firstPageVector = paginatePanel.paginate(newV);
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
        	 //CCBegin SS2
//            //采用单
//            if(typeJComboBox.getSelectedIndex() == 0)
//            {
        	 //CCEnd SS2
            	AdoptNoticeInfo qqadoptorder = (AdoptNoticeInfo)objs.elementAt(i);
                qMMultiList.addTextCell(i, 0, qqadoptorder.getBsoID());
                qMMultiList.addTextCell(i, 1, qqadoptorder.getAdoptNumber());    
                qMMultiList.addTextCell(i, 2, qqadoptorder.getName());
               // qMMultiList.addTextCell(i, 3, "采用单");
             //   UserIfc user = (UserIfc)RouteClientUtil.refresh(qqadoptorder.getCreator());
              //  qMMultiList.addTextCell(i, 4, user.getUsersDesc());
                qMMultiList.addTextCell(i, 3, String.valueOf(qqadoptorder.getCreateTime()));
                qMMultiList.addTextCell(i, 4, qqadoptorder.getSynDescription()); 
            
            //CCBegin SS1
//            else
//            {
//                //变更单
//                ModifyInfo qqchangeorder = (ModifyInfo)objs.elementAt(i);
//                qMMultiList.addTextCell(i, 0, qqchangeorder.getBsoID());
//                qMMultiList.addTextCell(i, 1, qqchangeorder.getModifyNumber());
//                qMMultiList.addTextCell(i, 2, qqchangeorder.getModifyName());
//                qMMultiList.addTextCell(i, 3, "变更单");
//                UserIfc user = (UserIfc)RouteClientUtil.refresh(qqchangeorder.getCreator());
//                qMMultiList.addTextCell(i, 4, user.getUsersDesc());
//                qMMultiList.addTextCell(i, 5, String.valueOf(qqchangeorder.getCreateTime()));
//                qMMultiList.addTextCell(i, 6, qqchangeorder.getModifyDescription());
//            }
            //CCEnd SS1
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

    /**
     * 点击确定按钮后，将选中的通知书关联的零部件添加到列表中
     */
    private void processOKCommond()
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.dispose();
        BaseValueInfo[] objs = null;
        Vector queryresult = null;
        //CCBegin SS2
//        if(typeJComboBox.getSelectedIndex() == 0)
//        {
        //CCEnd SS2
            objs = (AdoptNoticeInfo[])this.getSelectedDetails();
      //  }
        //CCBegin SS1
//        else
//        {
//            objs = (ModifyInfo[])this.getSelectedDetails();
//        }
        //CCEnd SS1
        //将选中的变更通知书封装成一个Vector，传到服务端进行处理，返回QMPartMasterIfc[]
        Vector ordervector = new Vector();
        for(int i = 0;i < objs.length;i++)
        {
            ordervector.add(objs[i]);
        }
        Class[] c1 = {Vector.class};
        Object[] obj1 = {ordervector};
        System.out.println("ordervector="+ordervector);
        try
        {
        	//CCBegin SS2
//            if(typeJComboBox.getSelectedIndex() == 0)
//            {
        	//CCEnd SS2
                queryresult = (Vector)RequestHelper.request("consTechnicsRouteService", "findQMPartByAdoptOrder", c1, obj1);
              //CCBegin SS2
                //            }else
//            {
//                queryresult = (Vector)RequestHelper.request("consTechnicsRouteService", "findQMPartByChangeOrder", c1, obj1);
//            }
              //CCEnd SS2
            Vector v = new Vector();
            Iterator iter = queryresult.iterator();
            while(iter.hasNext())
            {
                QMPartInfo info = (QMPartInfo)iter.next();
                if(verbose)
                {
                    System.out.println(info);
                }
                //CCBegin SS3
                //QMPartMasterInfo masterinfo = (QMPartMasterInfo)info.getMaster();

                //v.add(masterinfo);
                v.add(info);
                //CCEnd SS3

                //        RefreshService.getRefreshService().dispatchRefresh("addSelectedParts",
                //            0,
                //            v);

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
      //CCBegin SS2
//        if(typeJComboBox.getSelectedIndex() == 0)
//        {
            values = new AdoptNoticeInfo[rows.length];
          //CCEnd SS2
        //CCBegin SS1
//        else
//        {
//            values = new ModifyInfo[rows.length];
//        }
        //CCEnd SS1
        for(int i = 0;i < rows.length;i++)
        {
            int xx = rows[i];
            String bsoid = qMMultiList.getCellText(xx, 0);
          //CCBegin SS2
//            if(typeJComboBox.getSelectedIndex() == 0)
//            {
          //CCEnd SS2
                values[i] = (AdoptNoticeInfo)resultVector.get(bsoid);
            
            //CCBegin SS1
//            else
//            {
//                values[i] = (ModifyInfo)resultVector.get(bsoid);
//            }
            //CCEnd SS1
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

        /** 搜索界面对象 */
        private QQAdoptChangePartSearchJDialog myDialog;

        /**
         * 构造查询实例
         * @param threadgroup 线程组
         * @param dialog 按工装搜索界面对象
         * @param flag 判断是否保留原来的搜索结果
         */
        public SearchThread(ThreadGroup threadgroup, QQAdoptChangePartSearchJDialog dialog, boolean flag)
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

    void qMMultiList_actionPerformed(ActionEvent e)
    {
        processOKCommond();
    }

    void paginatePanel_paginateEvent(com.faw_qm.clients.util.PaginateEvent e)
    {
        this.addObjectToMultiList(paginatePanel.getCurrentObjects());
    }

}

class QQAdoptChangePartSearchJDialog_searchJButton_actionAdapter implements java.awt.event.ActionListener
{
    private QQAdoptChangePartSearchJDialog adaptee;

    QQAdoptChangePartSearchJDialog_searchJButton_actionAdapter(QQAdoptChangePartSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.searchJButton_actionPerformed(e);
    }
}

class QQAdoptChangePartSearchJDialog_stopJButton_actionAdapter implements java.awt.event.ActionListener
{
    private QQAdoptChangePartSearchJDialog adaptee;

    QQAdoptChangePartSearchJDialog_stopJButton_actionAdapter(QQAdoptChangePartSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.stopJButton_actionPerformed(e);
    }
}

class QQAdoptChangePartSearchJDialog_okJButton_actionAdapter implements java.awt.event.ActionListener
{
    private QQAdoptChangePartSearchJDialog adaptee;

    QQAdoptChangePartSearchJDialog_okJButton_actionAdapter(QQAdoptChangePartSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.okJButton_actionPerformed(e);
    }
}

class QQAdoptChangePartSearchJDialog_cancelJButton_actionAdapter implements java.awt.event.ActionListener
{
    private QQAdoptChangePartSearchJDialog adaptee;

    QQAdoptChangePartSearchJDialog_cancelJButton_actionAdapter(QQAdoptChangePartSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.cancelJButton_actionPerformed(e);
    }
}

class QQAdoptChangePartSearchJDialog_qMMultiList_actionAdapter implements java.awt.event.ActionListener
{
    private QQAdoptChangePartSearchJDialog adaptee;

    QQAdoptChangePartSearchJDialog_qMMultiList_actionAdapter(QQAdoptChangePartSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.qMMultiList_actionPerformed(e);
    }
}
