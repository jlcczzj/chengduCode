package com.faw_qm.cappclients.conscapproute.view;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.clients.util.*;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMExceptionHandler;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.*;
import com.faw_qm.part.model.*;
import com.faw_qm.technics.consroute.util.RouteWrapData;
import com.faw_qm.cappclients.util.*;
import com.faw_qm.cappclients.conscapproute.controller.CappRouteAction;
//CCBegin SS1
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
//CCEnd SS1

/**
 * <p>Title:查看典型路线 </p> <p>Description: </p> <p>Copyright: Copyright (c) 2007</p> <p>Company: 一汽启明</p>
 * @author 
 * @version 1.0
 * SS1 A032-2016-0116 成都搜索零部件典型路线不好使。 liunan 2016-9-12
 */

public class ViewModelRouteJDialog extends JDialog
{
    private JPanel panel1 = new JPanel();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel jPanel1 = new JPanel();
    private JLabel numLabel = new JLabel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JPanel jPanel2 = new JPanel();
    private JButton searchJButton = new JButton();
    private JButton stopJButton = new JButton();
    private QMMultiList qMMultiList = new QMMultiList();
    private JPanel jPanel3 = new JPanel();
    //private JButton okJButton = new JButton();
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
   private Vector dataVec=new Vector();
    private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.clients.util.PaginatePanel();
    JLabel jLabel1 = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea numjTextArea1 = new JTextArea();

    /**
     * 构造函数
     * @param frame 父窗口
     */
    public ViewModelRouteJDialog(JFrame frame)
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
    public ViewModelRouteJDialog()
    {
        this(null);
    }

    /**
     * 组件初始化
     * @throws Exception
     */
    private void jbInit()
    {
        this.setTitle("搜索典型路线");
        this.setSize(650, 500);
        this.setModal(true);
        panel1.setLayout(gridBagLayout3);
        panel1.setBounds(new Rectangle(0, 0, 650, 500));
        numLabel.setMaximumSize(new Dimension(24, 22));
        numLabel.setMinimumSize(new Dimension(24, 22));
        numLabel.setPreferredSize(new Dimension(24, 22));
        numLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numLabel.setText("零件编号");
        jPanel1.setLayout(gridBagLayout1);
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
        searchJButton.addActionListener(new ViewModelRouteJDialog_searchJButton_actionAdapter(this));
        stopJButton.setMaximumSize(new Dimension(75, 23));
        stopJButton.setMinimumSize(new Dimension(75, 23));
        stopJButton.setPreferredSize(new Dimension(75, 23));
        stopJButton.setToolTipText("Stop");
        stopJButton.setMnemonic('S');
        stopJButton.setText("停止(S)");
        stopJButton.addActionListener(new ViewModelRouteJDialog_stopJButton_actionAdapter(this));
        jPanel3.setLayout(gridBagLayout2);
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("关闭(C)");
        cancelJButton.addActionListener(new ViewModelRouteJDialog_cancelJButton_actionAdapter(this));
        qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        qMStatus.setMaximumSize(new Dimension(4, 22));
        qMStatus.setMinimumSize(new Dimension(4, 22));
        qMStatus.setPreferredSize(new Dimension(4, 22));
        jLabel1.setRequestFocusEnabled(true);
        jLabel1.setText("（一个零件号一行）");
        numjTextArea1.setEditable(true);
        jPanel2.add(searchJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(stopJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
        panel1.add(qMMultiList, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 0, 0));
        panel1.add(jPanel3, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 10), 0, 0));
        jPanel3.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 6, 0, 0), 0, 0));
        jPanel3.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        panel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 0, 0), 0, 28));
        panel1.add(jPanel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(22, 20, 0, 10), 10, 0));
        jTabbedPane1.add(jPanel1, "搜索典型路线");
        getContentPane().add(panel1, null);
        jPanel1.add(numLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 7), 25, 22));
        jPanel1.add(jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 0, 10));
        jPanel1.add(jScrollPane1, new GridBagConstraints(1, 0, 5, 5, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(1, 0, 6, 0), 299, 119));
        jScrollPane1.getViewport().add(numjTextArea1, null);
        panel1.add(qMStatus, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
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
        //CCBegin SS1
        //String[] headings = {"id", "编号", "名称","主要路线","次要路线"};
        //qMMultiList.setHeadings(headings);
        //qMMultiList.setRelColWidth(new int[]{0, 1, 1,1,1});
        String comp = "";
        try
        {
        	RequestServer server = RequestServerFactory.getRequestServer();
        	StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
        	info1.setClassName("com.faw_qm.doc.util.DocServiceHelper");
        	info1.setMethodName("getUserFromCompany");
        	Class[] classes = {};
        	info1.setParaClasses(classes);
        	Object[] objs = {};
        	info1.setParaValues(objs);
        	comp=(String)server.request(info1);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        if(comp.equals("cd"))
        {
        	String[] headings = {"编号","主要路线","次要路线"};
        	qMMultiList.setHeadings(headings);
        	qMMultiList.setRelColWidth(new int[]{1,1,1});
        }
        else
        {
        	String[] headings = {"id", "编号", "名称","主要路线","次要路线"};
        	qMMultiList.setHeadings(headings);
        	qMMultiList.setRelColWidth(new int[]{0, 1, 1,1,1});
        }
        //CCEnd SS1
        qMMultiList.setMultipleMode(true);
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
        cancelJButton.setEnabled(flag);
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
     * 
     */
    private synchronized void clearResults()
    {

        qMMultiList.clear();
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
    private Object[] getCondition()
    {
        int rows = numjTextArea1.getLineCount();
        Object[] objs = new Object[rows];
        String text = numjTextArea1.getText().trim();
        text = text.replaceAll("\n", ";");
        StringTokenizer parts = new StringTokenizer(text, ";");
        int i = 0;
        while(i < rows && parts.hasMoreTokens())
        {
            objs[i] = parts.nextToken().toString().trim();
            i++;
        }

        return objs;
    }

    /**
     * 根据业务类的属性和属性值来构造查询条件，然后从持久化服务中查询出数据。
     * @param flag 若flag=true，则保留原结果，否则清除上次查询结果
     */
    private void processSearchThread()
    {
        if(verbose)
        {
            System.out.println(" processSearchThread()...begin");
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //是否清除原来结果
        clearResults();
         paginatePanel.clearResultCache();
         resultVector.clear();
        this.setStatus("正在搜索数据库...");
        //获得查询条件
        Object[] condition = getCondition();
        try
        {
            //调用服务方法查询数据
            Class[] paraClass = {Object[].class};
            Object[] paraObj = {condition};
            Vector queryresult = (Vector)RequestHelper.request("consTechnicsRouteService", "ViewModelRoute", paraClass, paraObj);
            this.postHandleResult(queryresult);
        }catch(Exception e)
        {
            e.printStackTrace();
            String message = QMExceptionHandler.handle(e);
            DialogFactory.showInformDialog(this, message);
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
     * @param searchResult 搜索结果(零件)
     * @throws QMRemoteException
     */
    private void postHandleResult(Vector searchResult)
    {
        if(searchResult == null || searchResult.size() == 0)
        {
            setStatus("找到 0 个零部件");
            return;
        }
        //结果列表中的零件总数
        int oldSize = resultVector.size();
        //获得查询条件
//        Object[] condition = getCondition();
//        if(condition != null && condition.length > 0)
//        {
//            for(int i = 0;i < condition.length;i++)
//            {
//                if(condition[i] != null && condition[i].toString().trim().length() > 0)
//                {
//                    Class[] paraClass = {String.class};
//                    Object[] paraObj = {condition[i].toString()};
//                    QMPartMasterInfo partinfo = (QMPartMasterInfo)RequestHelper.request("TechnicsRouteService", "findPart", paraClass, paraObj);
//                    RouteWrapData wrapdata=(RouteWrapData)searchResult.get(condition[i].toString());
//                    if(wrapdata!=null)
//                    {
//                        resultVector.put(condition[i], wrapdata);
//                    }
//                }
//            }
//        }
//        for(int i = 0;i < searchResult.size();i++)
//        {
//            if(isCancelInProgress())
//            {
//                break;
//            }
//            QMPartMasterInfo orderlist = (QMPartMasterInfo)searchResult.elementAt(i);
//
//            //判断列表中是否已存在此纪录，若存在，则不用将搜索结果加入结果列表中
//            boolean flag = false;
//            if(resultVector.size() != 0)
//            {
//                Object[] ids = resultVector.keySet().toArray();
//                for(int k = 0;k < resultVector.size();k++)
//                {
//                    if(orderlist.getBsoID().equals(ids[k]))
//                    {
//                        flag = true;
//                        break;
//                    }
//                }
//            }
//            if(flag == true)
//            {
//                continue;
//            }
//
//            resultVector.put(orderlist.getBsoID(), orderlist);
//
//        }
        this.dataVec=searchResult;
            setStatus("附加找到 " + (searchResult.size() - oldSize) + " 个零件");

        if(verbose)
        {
            System.out.println("postHandleResult() end");
        }
    }

    private synchronized void addResultToMultiList()
    {
        Vector newV = new Vector();
        newV.addAll(dataVec);
        //分页
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
            RouteWrapData wrapdata = (RouteWrapData)objs.elementAt(i);
            //CCBegin SS1
            if(wrapdata.getPartMasterID().indexOf("QMPartMaster_")!=-1)
            {
            //CCEnd SS1
            Class[] c = {String.class};
            Object[] obj = {wrapdata.getPartMasterID()};
            QMPartMasterInfo part=null;
			try {
				part = (QMPartMasterInfo)RequestHelper.request("PersistService", "refreshInfo", c, obj);
			} catch (QMRemoteException e) {
				e.printStackTrace();
			}
            Image image = this.getStandardIcon(part);
            qMMultiList.addTextCell(i, 0, part.getBsoID());
            qMMultiList.addCell(i, 1, part.getPartNumber(), image);
            qMMultiList.addTextCell(i, 2, part.getPartName());
            qMMultiList.addTextCell(i, 3, wrapdata.getMainStr());
            qMMultiList.addTextCell(i, 4, wrapdata.getSecondStr());
          //CCBegin SS1
          }
          else if(wrapdata.getPartMasterID().indexOf("QMPart_")!=-1)
          {
            Class[] c = {String.class};
            Object[] obj = {wrapdata.getPartMasterID()};
            QMPartInfo part=null;
			try {
				part = (QMPartInfo)RequestHelper.request("PersistService", "refreshInfo", c, obj);
			} catch (QMRemoteException e) {
				e.printStackTrace();
			}
            Image image = this.getStandardIcon(part);
            qMMultiList.addTextCell(i, 0, part.getBsoID());
            qMMultiList.addCell(i, 1, part.getPartNumber(), image);
            qMMultiList.addTextCell(i, 2, part.getPartName());
            qMMultiList.addTextCell(i, 3, wrapdata.getMainStr());
            qMMultiList.addTextCell(i, 4, wrapdata.getSecondStr());
          }
          else
          {
            qMMultiList.addTextCell(i, 0, wrapdata.getPartMasterID());
            qMMultiList.addTextCell(i, 1, wrapdata.getMainStr());
            qMMultiList.addTextCell(i, 2, wrapdata.getSecondStr());
          }
            //CCEnd SS1
        }
        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 获得图标
     * @param basevalueinfo
     * @return
     */
    public Image getStandardIcon(BaseValueIfc basevalueinfo)
    {
        Image image = null;
        if(basevalueinfo != null)
        {
            try
            {
                image = QM.getStandardIcon(basevalueinfo.getBsoName());
            }catch(Exception _ex)
            {
                image = null;
            }
        }
        return image;
    }

    /**
     * 执行搜索操作
     * @param e ActionEvent
     */
    void searchJButton_actionPerformed(ActionEvent e)
    {
        setButtons(false);
        //启动搜索线程
        searchThread = new SearchThread(theThreadGroup, this);
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

        /** 搜索界面对象 */
        private ViewModelRouteJDialog myDialog;

        /**
         * 构造查询实例
         * @param threadgroup 线程组
         * @param dialog 按工装搜索界面对象
         * @param flag 判断是否保留原来的搜索结果
         */
        public SearchThread(ThreadGroup threadgroup, ViewModelRouteJDialog dialog)
        {
            super(threadgroup);
            myDialog = dialog;
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
                myDialog.processSearchThread();
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

    void paginatePanel_paginateEvent(com.faw_qm.clients.util.PaginateEvent e)
    {
        this.addObjectToMultiList(paginatePanel.getCurrentObjects());
    }

}

class ViewModelRouteJDialog_searchJButton_actionAdapter implements java.awt.event.ActionListener
{
    private ViewModelRouteJDialog adaptee;

    ViewModelRouteJDialog_searchJButton_actionAdapter(ViewModelRouteJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.searchJButton_actionPerformed(e);
    }
}

class ViewModelRouteJDialog_stopJButton_actionAdapter implements java.awt.event.ActionListener
{
    private ViewModelRouteJDialog adaptee;

    ViewModelRouteJDialog_stopJButton_actionAdapter(ViewModelRouteJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.stopJButton_actionPerformed(e);
    }
}


class ViewModelRouteJDialog_cancelJButton_actionAdapter implements java.awt.event.ActionListener
{
    private ViewModelRouteJDialog adaptee;

    ViewModelRouteJDialog_cancelJButton_actionAdapter(ViewModelRouteJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.cancelJButton_actionPerformed(e);
    }
}

