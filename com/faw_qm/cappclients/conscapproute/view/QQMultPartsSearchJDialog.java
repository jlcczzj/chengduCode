/**
 * SS1 批量添加QMPartMasterIfc改为QMPartIfc。 liunan 2014-5-21
 * SS2 修改成都批量搜索为精确搜索 liuyuzhu 2016-10-11
 * SS3 批量搜索物料号  xudezheng 2018-01-16
 * SS4 批量搜索界面增加反馈信息 xudezheng 2018-01-18
 */
 
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.*;

import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.clients.util.*;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.*;
//CCBegin SS1
import com.faw_qm.version.model.MasteredIfc;
//CCEnd SS1
/**
 * <p>Title:搜索变更通知书用来获取关联零部件 </p> <p>Description: </p> <p>Copyright: Copyright (c) 2007</p> <p>Company: 一汽启明</p>
 * @author 郭晓亮
 * @version 1.0
 */

public class QQMultPartsSearchJDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
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
    JLabel jLabel1 = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea numjTextArea1 = new JTextArea();
//    CCBegin SS4
    JScrollPane jScrollPane2 = new JScrollPane();
    JTextArea numjTextArea2 = new JTextArea();
//    CCEnd SS4

//    CCBegin SS3
    String comp = null;
//    CCEnd SS3
    
    /**
     * 构造函数
     * @param frame 父窗口
     */
    public QQMultPartsSearchJDialog(JFrame frame)
    {
        super(frame);
        try
        {
//        	CCBegin SS3
        	comp = getUserFromCompany();
//			CCEnd SS3
            jbInit();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 缺省的构造函数
     */
    public QQMultPartsSearchJDialog()
    {
        this(null);
    }

    /**
     * 组件初始化
     * @throws Exception
     */
    private void jbInit()
    {
        this.setTitle("批量添加零部件");
        this.setSize(1024, 768);
        this.setModal(true);
        panel1.setLayout(gridBagLayout3);
        panel1.setBounds(new Rectangle(0, 0, 1024, 768));
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
        searchJButton.addActionListener(new QQMultPartsSearchJDialog_searchJButton_actionAdapter(this));
        stopJButton.setMaximumSize(new Dimension(75, 23));
        stopJButton.setMinimumSize(new Dimension(75, 23));
        stopJButton.setPreferredSize(new Dimension(75, 23));
        stopJButton.setToolTipText("Stop");
        stopJButton.setMnemonic('S');
        stopJButton.setText("停止(S)");
        stopJButton.addActionListener(new QQMultPartsSearchJDialog_stopJButton_actionAdapter(this));
        jPanel3.setLayout(gridBagLayout2);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("确定(Y)");
        okJButton.setEnabled(false);
        okJButton.addActionListener(new QQMultPartsSearchJDialog_okJButton_actionAdapter(this));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消(C)");
        cancelJButton.addActionListener(new QQMultPartsSearchJDialog_cancelJButton_actionAdapter(this));
        qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        qMStatus.setMaximumSize(new Dimension(4, 22));
        qMStatus.setMinimumSize(new Dimension(4, 22));
        qMStatus.setPreferredSize(new Dimension(4, 22));
        appendResultJCheckBox.setText("附加结果");
        jLabel1.setRequestFocusEnabled(true);
        jLabel1.setText("（一个零件号一行）");
        numjTextArea1.setEditable(true);
        jPanel2.add(searchJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(stopJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
        panel1.add(qMMultiList, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 0, 0));
        panel1.add(jPanel3, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 10), 0, 0));
        jPanel3.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 6, 0, 0), 0, 0));
        jPanel3.add(okJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        panel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 0, 0), 0, 28));
        panel1.add(jPanel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(22, 20, 0, 10), 10, 0));
        jTabbedPane1.add(jPanel1, "批量搜索零部件");
        getContentPane().add(panel1, null);
        jPanel1.add(numLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 7), 25, 22));
        jPanel1.add(jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 0, 10));
        jPanel1.add(jScrollPane1, new GridBagConstraints(1, 0, 5, 5, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.WEST, new Insets(1, 0, 6, 0),200, 256));
        jScrollPane1.getViewport().add(numjTextArea1, 0);
//        CCBegin SS4
        jPanel1.add(jScrollPane2, new GridBagConstraints(8, 0, 5, 5, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.EAST, new Insets(2, 1, 7, 1),500, 256));
//        jPanel1.add(jScrollPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 7), 25, 22));
        jScrollPane2.getViewport().add(numjTextArea2, 1);
        numjTextArea2.setEditable(false);
        numjTextArea2.setLineWrap(true);
        numjTextArea2.setText("查询结果日志………………");
//        CCEnd SS4
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
//        CCBegin SS3
        if(comp.equals("cd")){
        	String[] headings = {"id", "编号", "名称","版本","特殊处理"};
        	qMMultiList.setHeadings(headings);
        	qMMultiList.setRelColWidth(new int[]{0,1,1,1,1});
        	}else{
        		String[] headings = {"id", "编号", "名称"};
        		qMMultiList.setHeadings(headings);
        		qMMultiList.setRelColWidth(new int[]{0, 1, 1});
        	}
//        CCEnd SS3
        qMMultiList.setMultipleMode(true);
        qMMultiList.addActionListener(new QQMultPartsSearchJDialog_qMMultiList_actionAdapter(this));
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
    
//    CCBegin SS3
    private Vector<String> getConditionForCD()
    {
        int rows = numjTextArea1.getLineCount();
        Vector<String> objs = new Vector<String>();
        String text = numjTextArea1.getText().trim();
        text = text.replaceAll("\n", ";");
        StringTokenizer parts = new StringTokenizer(text, ";");
        int i = 0;
        while(i < rows && parts.hasMoreTokens())
        {
            objs.add(parts.nextToken().toString().trim());
            i++;
        }

        return objs;
    }
//    CCEnd SS3
    

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
            paginatePanel.clearResultCache();
            resultVector.clear();
        }
        this.setStatus("正在搜索数据库...");
        //获得查询条件
        Object[] condition = getCondition();
        try
        {
            //调用服务方法查询数据
            Class[] paraClass = {Object[].class};
            Object[] paraObj = {condition};
            //CCBegin SS2
//            Vector queryresult = (Vector)RequestHelper.request("consTechnicsRouteService", "findMultPartsByNumbers", paraClass, paraObj);
            Vector queryresult = null;
//            CCBegin SS3
//            String usercp = getUserFromCompany();
//            CCEnd SS3
            if(comp.equals("cd")){
            	//            	CCBegin SS3
            	Vector<String> conditionFromCD = getConditionForCD();

            	RequestServer server = RequestServerFactory.getRequestServer();
            	StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
            	info1.setClassName("com.faw_qm.gybom.util.GYBomTool");
    			info1.setMethodName("getPartByMtlVector");
            	Class[] cdParaClass = {Vector.class};
            	Object[] cdParaObj = {conditionFromCD};
            	info1.setParaClasses(cdParaClass);
            	info1.setParaValues(cdParaObj);
            	queryresult = (Vector)server.request(info1);
            	this.postHandleResultForCD(queryresult);
            	
            }else{
            	queryresult = (Vector)RequestHelper.request("consTechnicsRouteService", "findMultPartsByNumbers", paraClass, paraObj);
            //CCEnd SS2
            this.postHandleResult(queryresult);
            }//           CCEnd SS3
        }catch(Exception e)
        {
            e.printStackTrace();
            String message = e.getMessage();
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
    //CCBegin SS2
    /**
     * 判断用户所属公司
     * @return String 获得用户所属公司
     * @author wenl
     */
    public String getUserFromCompany() throws QMException
    {
    	RequestServer server = RequestServerFactory.getRequestServer();;
    	String returnStr = "";
         StaticMethodRequestInfo info = new StaticMethodRequestInfo();
         info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
         info.setMethodName("getUserFromCompany");
         Class[] paraClass ={};
         info.setParaClasses(paraClass);
         Object[] obj ={};
         info.setParaValues(obj);
         boolean flag = false;
         try
         {
        	 returnStr = ((String) server.request(info));
         }
         catch (QMRemoteException e)
         {
               throw new QMException(e);
         }
         return returnStr;
    }
    //CCEnd SS2

    /**
     * 结果集后处理,将搜索结果加入结果列表中
     * @param searchResult 搜索结果(零件)
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
            setStatus("找到 0 个零部件");
            return;
        }
        //结果列表中的零件总数
        /*if(verbose)
        {
            System.out.println("the result size is:::" + searchResult.size());
        }*/
        int oldSize = resultVector.size();
        for(int i = 0;i < searchResult.size();i++)
        {
            if(isCancelInProgress())
            {
                break;
            }
            /*if(verbose)
            {
                System.out.println("add the object to MultiList start......");
            }*/
            QMPartMasterInfo orderlist = (QMPartMasterInfo)searchResult.elementAt(i);

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
            setStatus("找到 " + resultVector.size() + " 个零件");
        }else
        {
            setStatus("附加找到 " + (resultVector.size() - oldSize) + " 个零件");
        }

        if(verbose)
        {
            System.out.println("postHandleResult() end");
        }
    }

//    CCBegin SS3
    private void postHandleResultForCD(Vector searchResult)
    {
    	if(verbose)
    	{
    		System.out.println(" postHandleResult() begin...");
    	}
    	String message = "找到 0 个零部件"; 
    	StringBuffer nopart = new StringBuffer();
    	StringBuffer noversion = new StringBuffer();
    	boolean flag = false;
    	for(int i =0 ;i<searchResult.size();i++){
    		HashMap map = (HashMap)searchResult.get(i);
    		
    		if(map.get("info")==null){
    			if(!map.get("nopart").equals("")){
    				nopart.append(map.get("nopart"));
    			}else if(!map.get("noversion").equals("")){
    				noversion.append(map.get("noversion"));
    			}
    			flag = true;
    			searchResult.remove(i);
    			i--;
    			continue;
    		}
    	}
    	if(flag){
    		if(nopart!=null)
    			message = nopart.toString()+","+noversion.toString();
    		else
    			message = noversion.toString();
    		setStatus("未找到以下零部件："+message);
    		numjTextArea2.setText("未找到以下零部件："+message);
    		numjTextArea2.setLineWrap(true);
    		numjTextArea2.setWrapStyleWord(true);
    	}else
    		setStatus(message);
    	if(searchResult.size()==0){
    		return;
    	}

    	//结果列表中的零件总数
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
    		/*if(verbose)
            {
                System.out.println("add the object to MultiList start......");
            }*/
    		HashMap result = (HashMap)searchResult.elementAt(i);
    		QMPartInfo partInfo = (QMPartInfo)result.get("info");
    		if(result.get("info")==null){continue;}
    		//判断列表中是否已存在此纪录，若存在，则不用将搜索结果加入结果列表中
    		boolean flag1 = false;
    		if(resultVector.size() != 0)
    		{
    			Object[] ids = resultVector.keySet().toArray();
    			for(int k = 0;k < resultVector.size();k++)
    			{
    				if(partInfo.getBsoID().equals(ids[k]))
    				{
    					flag1 = true;
    					break;
    				}
    			}
    		}
    		if(flag1 == true)
    		{
    			continue;
    		}

    		resultVector.put(partInfo.getBsoID(), result);

    	}

    	if(!appendResultJCheckBox.isSelected())
    	{
    		if(flag)
    			setStatus("找到 " + resultVector.size() + " 个零件其中"+message+"未找到！");
    		else
    			setStatus("找到 " + resultVector.size() + " 个零件");
    	}else
    	{
    		setStatus("附加找到 " + (resultVector.size() - oldSize) + " 个零件");
    	}

    	if(verbose)
    	{
    		System.out.println("postHandleResult() end");
    	}
    }
//    CCEnd SS3
    
    private synchronized void addResultToMultiList()
    {
        Vector newV = new Vector();
        newV.addAll(resultVector.values());
        //分页
        Vector firstPageVector = paginatePanel.paginate(newV);
        addObjectToMultiList(firstPageVector);
    }
    
//    CCBegin SS3
    private synchronized void addResultToMultiListForCD()
    {
        Vector newV = new Vector();
        newV.addAll(resultVector.values());
        //分页
        Vector firstPageVector = paginatePanel.paginate(newV);
        addObjectToMultiListForCD(firstPageVector);
    }
//    CCEnd SS3

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
            QMPartMasterInfo qmpart = (QMPartMasterInfo)objs.elementAt(i);
            Image image = this.getStandardIcon(qmpart);
            qMMultiList.addTextCell(i, 0, qmpart.getBsoID());
            qMMultiList.addCell(i, 1, qmpart.getPartNumber(), image);
            qMMultiList.addTextCell(i, 2, qmpart.getPartName());
        }
        setCursor(Cursor.getDefaultCursor());
    }
    
//    CCBegin SS3
    private void addObjectToMultiListForCD(Vector objs)
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
        	HashMap result = (HashMap)objs.elementAt(i);
        	QMPartInfo qmpart = (QMPartInfo)result.get("info");
            Image image = this.getStandardIcon(qmpart);
            qMMultiList.addTextCell(i, 0, qmpart.getBsoID());
            qMMultiList.addCell(i, 1, qmpart.getPartNumber(), image);
            qMMultiList.addTextCell(i, 2, qmpart.getPartName());
            qMMultiList.addTextCell(i, 3, (String)result.get("version"));
            qMMultiList.addTextCell(i, 4, (String)result.get("bs"));
        }
        setCursor(Cursor.getDefaultCursor());
    }
//    CCEnd SS3

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
     * 点击确定按钮后，将选中的零部件添加到列表中
     */
    private void processOKCommond()
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.dispose();
        //返回QMPartMasterIfc[]
//        CCBegin SS3
        if(comp.equals("cd")){
        	
        	HashMap<String,Object> map = new HashMap<String,Object>();
        	map = this.getSelectedDetailsForCD();
        	RefreshService.getRefreshService().dispatchRefresh("addSelectedPartsForCD", 0, map);
        }else{
        	QMPartMasterInfo[] objs = (QMPartMasterInfo[])this.getSelectedDetails();
        	for(int i = 0;i < objs.length;i++)
        	{
        		QMPartMasterInfo masterinfo = (QMPartMasterInfo)objs[i];
        		Vector v = new Vector();
        		//CCBegin SS1
        		//v.add(masterinfo);
        		Class[] c = { MasteredIfc.class };
        		Object[] o = { masterinfo };
        		try
        		{
        			Vector nv = (Vector) RequestHelper.request("VersionControlService", "allIterationsOf", c, o);
        			QMPartIfc part = (QMPartIfc) nv.get(0);
        			v.add(part);
        		}
        		catch (Exception ex)
        		{
        			String message = ex.getMessage();
        			DialogFactory.showInformDialog(this, message);
        		}
        		//CCEnd SS3
        		RefreshService.getRefreshService().dispatchRefresh("addSelectedParts", 0, v);
        	}
        }

        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 获得结果域选中的业务对象
     * @return 选中的业务对象
     */
    public QMPartMasterInfo[] getSelectedDetails()
    {
        int[] rows = qMMultiList.getSelectedRows();
        QMPartMasterInfo[] values = new QMPartMasterInfo[rows.length];
        for(int i = 0;i < rows.length;i++)
        {
            int xx = rows[i];
            String bsoid = qMMultiList.getCellText(xx, 0);
            values[i] = (QMPartMasterInfo)resultVector.get(bsoid);
            if(verbose)
            {
                System.out.println("选中的是：" + values[i]);
            }
        }
        return values;
    }
    
//    CCBegin SS3
    public HashMap<String,Object> getSelectedDetailsForCD()
    {
        /*int[] rows = qMMultiList.getSelectedRows();
        Vector<QMPartInfo> values = new Vector<QMPartInfo>();
        for(int i = 0;i < rows.length;i++)
        {
            int xx = rows[i];
            String bsoid = qMMultiList.getCellText(xx, 0);
            HashMap map = (HashMap)resultVector.get(bsoid);
        }*/
        return resultVector;
    }
//    CCEnd SS3

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
        private QQMultPartsSearchJDialog myDialog;

        /**
         * 构造查询实例
         * @param threadgroup 线程组
         * @param dialog 按工装搜索界面对象
         * @param flag 判断是否保留原来的搜索结果
         */
        public SearchThread(ThreadGroup threadgroup, QQMultPartsSearchJDialog dialog, boolean flag)
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
//            	CCBegin SS4
            	if(comp.equals("cd")) 
            		numjTextArea2.setText("查询结果日志………………");
//            	 CCEnd SS4
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
//                CCBegin SS3
                if(comp.equals("cd")) {
                	myDialog.addResultToMultiListForCD();
                }else {
                	//                CCEnd SS3
                	myDialog.addResultToMultiList();
                }
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

class QQMultPartsSearchJDialog_searchJButton_actionAdapter implements java.awt.event.ActionListener
{
    private QQMultPartsSearchJDialog adaptee;

    QQMultPartsSearchJDialog_searchJButton_actionAdapter(QQMultPartsSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.searchJButton_actionPerformed(e);
    }
}

class QQMultPartsSearchJDialog_stopJButton_actionAdapter implements java.awt.event.ActionListener
{
    private QQMultPartsSearchJDialog adaptee;

    QQMultPartsSearchJDialog_stopJButton_actionAdapter(QQMultPartsSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.stopJButton_actionPerformed(e);
    }
}

class QQMultPartsSearchJDialog_okJButton_actionAdapter implements java.awt.event.ActionListener
{
    private QQMultPartsSearchJDialog adaptee;

    QQMultPartsSearchJDialog_okJButton_actionAdapter(QQMultPartsSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.okJButton_actionPerformed(e);
    }
}

class QQMultPartsSearchJDialog_cancelJButton_actionAdapter implements java.awt.event.ActionListener
{
    private QQMultPartsSearchJDialog adaptee;

    QQMultPartsSearchJDialog_cancelJButton_actionAdapter(QQMultPartsSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.cancelJButton_actionPerformed(e);
    }
}

class QQMultPartsSearchJDialog_qMMultiList_actionAdapter implements java.awt.event.ActionListener
{
    private QQMultPartsSearchJDialog adaptee;

    QQMultPartsSearchJDialog_qMMultiList_actionAdapter(QQMultPartsSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.qMMultiList_actionPerformed(e);
    }
}
