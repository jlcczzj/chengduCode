/**
 * 生成程序SearchZXNoticeDialog.java    1.0    2014-6-4
 * 版权归启明信息技术股份有限公司所有
 * 本程序属本公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 工艺BOM管理器中添加解放设计变更单会带入相应单据的采用和不采用件 xianglx 2014-8-28
 */
package com.faw_qm.gybomNotice.client.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;


import com.faw_qm.bomNotice.model.BomAdoptNoticeIfc;

import com.faw_qm.bomNotice.model.BomChangeNoticeIfc;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.clients.util.QMThread;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.gybomNotice.util.GYNoticeHelper;


/**
 * 查询采用单。
 * @author 文柳
 * @version 1.0
 */
public class SearchJFNoticeDialog extends JDialog
{

    private static final long serialVersionUID = 1L;


    /**
     * 构造函数
     * @param JFrame frame父窗口
     * @param BomNoticeMainJFrame frame
     */
    public SearchJFNoticeDialog(GYBomNoticeMainJFrame frame )
    {
        this.view = frame;

        
        try
        {
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /** 中止进程标志 */
    protected boolean cancelInProgress = false;

    private GYBomNoticeMainJFrame view;
    
    /** 线程组 */
    private static ThreadGroup theThreadGroup = Thread.currentThread().getThreadGroup();

    /** 检索控制类 */
    private SearchThread searchThread;
    /** 分页控制类 */
    private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.clients.util.PaginatePanel();
     /** 查询结果集 */
    private Vector queryresult = new Vector();
    /** 查询结果集 */
    private HashMap queryresultMap = new HashMap();
    /** 多行表格变量 */
    public ComponentMultiList qMMultiList = new ComponentMultiList();
    private JPanel panel1 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JPanel panel4 = new JPanel();
    private JPanel panel5 = new JPanel();
    private JPanel panel6 = new JPanel();
    private JPanel panel7 = new JPanel();
    private JLabel qMStatus = new JLabel();//状态条
    private JLabel label1 = new JLabel();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JLabel routeListNumberLable = new JLabel();
    private JLabel routeListNameLable = new JLabel();

    private JLabel noticeType = new JLabel();
    private JComboBox noticeTypeBox = new JComboBox();
    private JTextField routeListNumberField = new JTextField();
    private JTextField routeListNameField = new JTextField();



    //按钮
    private JButton okbutton = new JButton();
    private JButton cancelbutton = new JButton();
    private JButton searchbutton = new JButton();
    private JButton stopbutton = new JButton();
    
    
    //表头列名对应的列
    int routeListBsoIDindex;
    int routeListNumberindex;
    int routeListNameindex;
    int noticeTypeindex;

    /**
     * 界面初始化
     * @throws Exception
     */
    void jbInit() throws Exception
    {
        this.setModal(true);
        this.setSize(750, 600);
        this.setTitle("解放设计变更单");
        setViewLocation();
        
        this.setLayout(new GridBagLayout());
        panel1.setLayout(new GridBagLayout());
        panel3.setLayout(new GridBagLayout());
        panel4.setLayout(new GridBagLayout());
        panel5.setLayout(new GridBagLayout());
        panel6.setLayout(new GridBagLayout());
        panel7.setLayout(new GridBagLayout());

        //添加固定表头
        String[] heads = new String[4];
        heads[0] = "noticeBsoID";
        heads[1] = "通知单编号";
        heads[2] = "通知单名称";
        heads[3] = "通知单类型";

        int[] relcolwidth = new int[4];
        relcolwidth[0] = 0;//设置bsoID不显示
        relcolwidth[1] = 1;
        relcolwidth[2] = 1;
        relcolwidth[3] = 1;


        ArrayList headslist = new ArrayList();
        for(int i = 0;i < heads.length;i++)
        {
            headslist.add(i, heads[i]);
        }
        ArrayList relcolwidthlist = new ArrayList();
        for(int m = 0;m < relcolwidth.length;m++)
        {
            relcolwidthlist.add(m, relcolwidth[m]);
        }
        qMMultiList.setHeadings(heads);
        qMMultiList.setRelColWidth(relcolwidth);

        //获得表头对应的列，以后设值取值用
        qMMultiList.setCellEditable(false);
        qMMultiList.setMultipleMode(false);
        qMMultiList.setAllowSorting(true);
       
        qMMultiList.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                setOkButtonEnabled();
            }
        });

        routeListBsoIDindex = headslist.indexOf("noticeBsoID");
        routeListNumberindex = headslist.indexOf("通知单编号");
        routeListNameindex = headslist.indexOf("通知单名称");
        noticeTypeindex = headslist.indexOf("通知单类型");
        okbutton.setMaximumSize(new Dimension(75, 23));
        okbutton.setMinimumSize(new Dimension(75, 23));
        okbutton.setPreferredSize(new Dimension(75, 23));
        okbutton.setText("确定(Y)");
        okbutton.setMnemonic('Y');
        okbutton.addActionListener(new AddPartByRouteListJDialog_okbutton_actionAdapter(this));
        cancelbutton.setMaximumSize(new Dimension(75, 23));
        cancelbutton.setMinimumSize(new Dimension(75, 23));
        cancelbutton.setPreferredSize(new Dimension(75, 23));
        cancelbutton.setText("取消(C)");
        cancelbutton.setMnemonic('C');
        cancelbutton.addActionListener(new AddPartByRouteListJDialog_cancelbutton_actionAdapter(this));
        searchbutton.setMaximumSize(new Dimension(75, 23));
        searchbutton.setMinimumSize(new Dimension(75, 23));
        searchbutton.setPreferredSize(new Dimension(75, 23));
        searchbutton.setText("搜索(F)");
        searchbutton.setMnemonic('F');
        searchbutton.addActionListener(new AddPartByRouteListJDialog_searchbutton_actionAdapter(this));
        stopbutton.setMaximumSize(new Dimension(75, 23));
        stopbutton.setMinimumSize(new Dimension(75, 23));
        stopbutton.setPreferredSize(new Dimension(75, 23));
        stopbutton.setText("停止(S)");
        stopbutton.setMnemonic('S');
        stopbutton.addActionListener(new AddPartByRouteListJDialog_stopbutton_actionAdapter(this));
        qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        qMStatus.setMaximumSize(new Dimension(4, 22));
        qMStatus.setMinimumSize(new Dimension(4, 22));
        qMStatus.setPreferredSize(new Dimension(4, 22));
        

      
        routeListNumberLable.setText("通知单编号");
        routeListNameLable.setText("通知单名称");

        noticeType.setText("通知单类型");
        noticeTypeBox.addItem("采用通知单");
        noticeTypeBox.addItem("变更通知单");
      
        jTabbedPane1.add(panel1, "搜索条件");
        panel1.add(noticeType, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel1.add(noticeTypeBox, new GridBagConstraints(1, 0, 4, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel1.add(routeListNumberLable, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel1.add(routeListNumberField, new GridBagConstraints(1, 1, 4, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel1.add(routeListNameLable, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel1.add(routeListNameField, new GridBagConstraints(1, 2, 4, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        
        
        
        panel3.add(qMMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 8, 2, 8), 0, 0));

        panel4.add(label1, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel4.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 8, 2, 8), 0, 0));
        panel4.add(okbutton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel4.add(cancelbutton, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel7.add(qMStatus, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panel5.add(searchbutton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel5.add(stopbutton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));

        panel6.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 0), 0, 0));
        panel6.add(panel5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(33, 2, 2, 0), 0, 0));
        this.add(panel6, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 8, 2, 8), 0, 0));
        this.add(panel3, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 8, 2, 8), 0, 0));
        this.add(panel4, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 8, 10, 8), 0, 0));
        this.add(panel7, new GridBagConstraints(0, 4, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    }
    /**
     * 设置页面初始化
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
     * 主函数
     */
    public static void main(String[] args)
    {
    	SearchJFNoticeDialog s = new SearchJFNoticeDialog(null);
        s.setVisible(true);
    }

    /**
     * 取消按钮动作监听
     */
    class AddPartByRouteListJDialog_cancelbutton_actionAdapter implements java.awt.event.ActionListener
    {
        private SearchJFNoticeDialog adaptee;

        AddPartByRouteListJDialog_cancelbutton_actionAdapter(SearchJFNoticeDialog adaptee)
        {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e)
        {
            adaptee.cancelbutton_actionPerformed(e);
        }
    }
    /**
     * 取消按钮动作
     */
    void cancelbutton_actionPerformed(ActionEvent e)
    {
        this.dispose();
    }
    /**
     * 确定按钮动作监听
     */
    class AddPartByRouteListJDialog_okbutton_actionAdapter implements java.awt.event.ActionListener
    {
        private SearchJFNoticeDialog adaptee;

        AddPartByRouteListJDialog_okbutton_actionAdapter(SearchJFNoticeDialog adaptee)
        {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e)
        {
            adaptee.okbutton_actionPerformed(e);
        }
    }
    /**
     * 确定按钮动作
     *@param ActionEvent e
     */
    void okbutton_actionPerformed(ActionEvent e)
    {

       
        int[] index = qMMultiList.getSelectedRows();
    
    	String noticeBsoID = (String)qMMultiList.getCellText(index[0], routeListBsoIDindex);//采用单bsoID
    	BaseValueIfc objss = (BaseValueIfc)queryresultMap.get(noticeBsoID);
//CCBegin SS1
		if (view.getTaskPanel() instanceof ZCBomAdoptNoticeViewPanel)
   		((ZCBomAdoptNoticeViewPanel)view.getTaskPanel()).setJfBomNoticeIfc(objss);
		if (view.getTaskPanel() instanceof CJAdoptNoticeViewPanel)
   		((CJAdoptNoticeViewPanel)view.getTaskPanel()).setJfNoticeIfc(objss);
//CCEnd SS1
    	
        this.setVisible(false);
    }
    /**
     * 查询按钮监听事件
     */
    class AddPartByRouteListJDialog_searchbutton_actionAdapter implements java.awt.event.ActionListener
    {
        private SearchJFNoticeDialog adaptee;

        AddPartByRouteListJDialog_searchbutton_actionAdapter(SearchJFNoticeDialog adaptee)
        {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e)
        {
            adaptee.searchbutton_actionPerformed(e);
        }
    }
    /**
     * 停止按钮监听事件
     */
    class AddPartByRouteListJDialog_stopbutton_actionAdapter implements java.awt.event.ActionListener
    {
        private SearchJFNoticeDialog adaptee;

        AddPartByRouteListJDialog_stopbutton_actionAdapter(SearchJFNoticeDialog adaptee)
        {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e)
        {
            adaptee.stopbutton_actionPerformed(e);
        }
    }
    /**
     * 停止按钮监听事件
     */
    void stopbutton_actionPerformed(ActionEvent e)
    {
        stopbutton.setEnabled(false);
        cancel();
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
     * 启动搜索线程
     */
    void searchbutton_actionPerformed(ActionEvent e)
    {

    	
            setButtons(false);
            //启动搜索线程
            searchThread = new SearchThread(theThreadGroup, this);
            searchThread.start();	
    	

    }
    
   
   /**
     * 搜索线程
     *@see QMThread
     */
    class SearchThread extends QMThread
    {
        //        /** 判断是否保留原来的搜索结果 */
        //        private boolean isLeave;

        private SearchJFNoticeDialog myDialog;

        /**
         * 构造查询实例
         * @param threadgroup 线程组
         * @param dialog 按工装搜索界面对象
         * @param flag 判断是否保留原来的搜索结果
         */
        public SearchThread(ThreadGroup threadgroup, SearchJFNoticeDialog dialog)
        {
            super(threadgroup);
            myDialog = dialog; 
            // isLeave = flag;
        }

        /**
         * 使线程中断，停止搜索进程
         */
        public synchronized void interrupt()
        {
            this.interrupt();
        }

        /**
         * 线程运行方法，执行搜索。
         */
        public synchronized void run()
        {
            try
            {
                //执行搜索
                myDialog.processSearchThread();
            }catch(Throwable throwable)
            {
                if(!myDialog.isCancelInProgress())
                {
                    throwable.printStackTrace();
                }
            }finally
            {
                //设置界面按钮的显示状态
                myDialog.setButtons(true);
                //设置没有处于中断状态
                myDialog.setCancelInProgress(false);
                myDialog.addResultToMultiList();
            }

        }
    }
   /**
     * 查询结果插入到多行表格中
     */
    private synchronized void addResultToMultiList()
    {
        Vector firstPageVector = paginatePanel.paginate(queryresult);
        addObjectToMultiList(firstPageVector);
    }
   /**
     * 对象插入到多行表格中
     */
    private void addObjectToMultiList(Vector objs)
    {

        qMMultiList.clear();
        if(objs == null || objs.size() == 0)
        {
            return;
        }
        for(int i = 0;i < objs.size();i++)
        {
            BaseValueIfc base = (BaseValueIfc)objs.get(i);
            if(base!=null){
            	if(base instanceof BomAdoptNoticeIfc){
                    //表头列名对应的列
                    qMMultiList.addTextCell(i, routeListBsoIDindex, ((BomAdoptNoticeIfc)base).getBsoID());
                    qMMultiList.addTextCell(i, routeListNumberindex, ((BomAdoptNoticeIfc)base).getAdoptnoticenumber());
                    qMMultiList.addTextCell(i, routeListNameindex, ((BomAdoptNoticeIfc)base).getAdoptnoticename());
                    qMMultiList.addTextCell(i, noticeTypeindex,((BomAdoptNoticeIfc)base).getPublishType());
            	}else if(base instanceof BomChangeNoticeIfc){
                    //表头列名对应的列
                    qMMultiList.addTextCell(i, routeListBsoIDindex, ((BomChangeNoticeIfc)base).getBsoID());
                    qMMultiList.addTextCell(i, routeListNumberindex, ((BomChangeNoticeIfc)base).getAdoptnoticenumber());
                    qMMultiList.addTextCell(i, routeListNameindex, ((BomChangeNoticeIfc)base).getAdoptnoticename());
                    qMMultiList.addTextCell(i, noticeTypeindex,((BomChangeNoticeIfc)base).getPublishType());
            	}

            }

        }

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
            okbutton.setEnabled(false);
            paginatePanel.clearResultCache();
        }
    }
   /**
     * 搜索进程
     */
    private void processSearchThread()
    {

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //是否清除原来结果
        clearResults(true);
        queryresult.clear();
        queryresultMap.clear();
        this.setStatus("正在搜索数据库...");
        try
        {
            //调用服务方法查询数据
            Class[] paraClass = {HashMap.class};
            Object[] paraObj = {getCondition()};
            queryresult = (Vector)GYNoticeHelper.requestServer("GYBomNoticeService", "searchBomAdoptChangeNotice", paraClass, paraObj);
            setStatus("找到 " + queryresult.size() + " 个 结果");
            if(queryresult!=null&&queryresult.size()>0){
            	for(Iterator ite = queryresult.iterator();ite.hasNext();){
            		BaseValueIfc qInfo = (BaseValueIfc)ite.next();
            		queryresultMap.put(qInfo.getBsoID(), qInfo);
            	}
            }
            
        }catch(QMException e)
        {
        	e.printStackTrace();
            String title = "异常";
            JOptionPane.showMessageDialog(this, e.getClientMessage(), title, JOptionPane.INFORMATION_MESSAGE);
            setCursor(Cursor.getDefaultCursor());
            if(!isCancelInProgress())
            {
                return;
            }
        }
        setCursor(Cursor.getDefaultCursor());
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
     * 设置按钮的编辑状态，仅供内部使用
     * @param flag
     */
    private void setButtons(boolean flag)
    {
        searchbutton.setEnabled(flag);
        stopbutton.setEnabled(!flag);

        if(qMMultiList.getSelectedRows() == null)
        {
            okbutton.setEnabled(false);
        }
        cancelbutton.setEnabled(flag);
    }

    /**
     * 获得用户录入的搜索条件
     * @return 搜索条件
     */
    private HashMap getCondition()
    {
        HashMap map = new HashMap();
        String routeListNumberStr = "";
        String routeListNameStr = "";
        String routeListStatStr = "";

        //采用单编号
        if(routeListNumberField.getText()!=null&&!routeListNumberField.getText().trim().equals("")){
        	routeListNumberStr = routeListNumberField.getText().trim();
        }
        //采用单名称
        if(routeListNameField.getText()!=null&&!routeListNameField.getText().trim().equals("")){
        	routeListNameStr = routeListNameField.getText().trim();
        }
        //生命周期状态
        if(noticeTypeBox.getSelectedItem()!=null&&!noticeTypeBox.getSelectedItem().equals("")){
        	routeListStatStr = (String)noticeTypeBox.getSelectedItem().toString();
        }

        
        map.put("routeListNumberStr", routeListNumberStr);
        map.put("routeListNameStr", routeListNameStr);
        map.put("noticeTypeStr", routeListStatStr);


        return map;
    }

    /**
     * 设置确定按钮的状态
     */
    private void setOkButtonEnabled()
    {
        if(qMMultiList.getSelectedRows() == null)
        {
            okbutton.setEnabled(false);
        }else
        {
            okbutton.setEnabled(true);
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

}
