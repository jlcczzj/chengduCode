/**
 * 生成程序 
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 *CR1 20120328 吕航 原因 参见TD5943
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.AWTEvent;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.util.CappWrapData;
import com.faw_qm.cappclients.capp.view.TechnicsRegulationsMainJFrame;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.clients.util.QMMultiList;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMExceptionHandler;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.ShortCutRouteInfo;
import com.faw_qm.technics.consroute.util.RouteCategoryType;
import com.faw_qm.users.model.UserIfc;

/**
 * <p>Title: </p> <p>快捷路线对话框: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: </p>
 * @author not attributable
 * @version 1.0 20120106 徐春英 add
 */
public class ShortCutRouteDialog extends JDialog
{
    /** 父窗口 */
    private JFrame parentFrame;

    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JLabel userNameLabel = new JLabel();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JTextField routeStrField1 = new JTextField();
    private JLabel keyLabel1 = new JLabel();
    private JTextField routeStrField2 = new JTextField();
    private JLabel keyLabel2 = new JLabel();
    private JTextField routeStrField3 = new JTextField();
    private JLabel keyLabel3 = new JLabel();
    private JTextField routeStrField4 = new JTextField();
    private JLabel keyLabel4 = new JLabel();
    private JTextField routeStrField5 = new JTextField();
    private JLabel keyLabel5 = new JLabel();
    private JLabel keyLabel6 = new JLabel();
    private JLabel keyLabel7 = new JLabel();
    private JLabel keyLabel8 = new JLabel();
    private JLabel keyLabel9 = new JLabel();
    private JTextField routeStrField6 = new JTextField();
    private JTextField routeStrField7 = new JTextField();
    private JTextField routeStrField8 = new JTextField();
    private JTextField routeStrField9 = new JTextField();
    private JButton cancelJButton = new JButton();
    private JButton okButton = new JButton();
    private JButton editButton = new JButton();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();

    private JLabel jLabel = new JLabel();
    //标识确定按钮如何执行
    private boolean flag = false;
    private HashMap shortMap = null;
    /** 用于标记资源文件路径 */
    protected static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /**
     * 当前用户
     */
    private UserIfc currentUser;

    public ShortCutRouteDialog(JFrame frame, HashMap map)
    {
        super(frame, "", true);
        // enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        // result = null;
        parentFrame = frame;
        shortMap = map;
        try
        {
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 初始化
     * @throws Exception
     */
    private void jbInit()
    {
        this.setTitle("定义快捷路线");
        currentUser = CappRouteListManageJFrame.currentUser;
        userNameLabel.setText("用户" + "<" + currentUser.getUsersDesc() + ">" + "的快捷路线");
        userNameLabel.setMinimumSize(new Dimension(200, 30));
        userNameLabel.setMaximumSize(new Dimension(200, 30));
        userNameLabel.setPreferredSize(new Dimension(200, 30));
        userNameLabel.setFont(new Font("Dialog", 0, 14));

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setLayout(gridBagLayout1);
        jPanel1.setLayout(gridBagLayout2);
        jPanel2.setLayout(gridBagLayout3);
        keyLabel1.setText("Ctrl+1");
        keyLabel2.setText("Ctrl+2");
        keyLabel3.setText("Ctrl+3");
        keyLabel4.setText("Ctrl+4");
        keyLabel5.setText("Ctrl+5");
        keyLabel6.setText("Ctrl+6");
        keyLabel7.setText("Ctrl+7");
        keyLabel8.setText("Ctrl+8");
        keyLabel9.setText("Ctrl+9");
        //CR1 begin
//        if(shortMap == null || shortMap.size() == 0)
//        {
//            this.enableComponent(true);
//            flag = true;
//        }else
//        {
            this.enableComponent(false);
            flag = false;
//        }
          //CR1 end
        //设置路线信息
        routeStrField1.setText((String)shortMap.get("Ctrl+1"));
        routeStrField2.setText((String)shortMap.get("Ctrl+2"));
        routeStrField3.setText((String)shortMap.get("Ctrl+3"));
        routeStrField4.setText((String)shortMap.get("Ctrl+4"));
        routeStrField5.setText((String)shortMap.get("Ctrl+5"));
        routeStrField6.setText((String)shortMap.get("Ctrl+6"));
        routeStrField7.setText((String)shortMap.get("Ctrl+7"));
        routeStrField8.setText((String)shortMap.get("Ctrl+8"));
        routeStrField9.setText((String)shortMap.get("Ctrl+9"));

        editButton.setMaximumSize(new Dimension(75, 23));
        editButton.setMinimumSize(new Dimension(75, 23));
        editButton.setPreferredSize(new Dimension(75, 23));
        editButton.setMnemonic('E');
        //okButton.setRequestFocusEnabled(true);
        editButton.setMnemonic('E');
        editButton.setText("编辑(E)");
        editButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                editButton_actionPerformed(e);
            }
        });
        editButton.setEnabled(true);
        okButton.setMaximumSize(new Dimension(75, 23));
        okButton.setMinimumSize(new Dimension(75, 23));
        okButton.setPreferredSize(new Dimension(75, 23));
        okButton.setMnemonic('Y');
        //okButton.setRequestFocusEnabled(true);
        okButton.setMnemonic('Y');
        okButton.setText("确定(Y)");
        okButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                okButton_actionPerformed(e);
            }
        });
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setMnemonic('C');
        //cancelJButton.setVerifyInputWhenFocusTarget(true);
        //cancelJButton.setMargin(new Insets(2, 14, 2, 14));
        cancelJButton.setText("取消(C)");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });

        routeStrField1.setMinimumSize(new Dimension(200, 22));
        routeStrField1.setPreferredSize(new Dimension(200, 22));
        routeStrField2.setMinimumSize(new Dimension(200, 22));
        routeStrField2.setPreferredSize(new Dimension(200, 22));
        routeStrField3.setMinimumSize(new Dimension(200, 22));
        routeStrField3.setPreferredSize(new Dimension(200, 22));
        routeStrField4.setMinimumSize(new Dimension(200, 22));
        routeStrField4.setPreferredSize(new Dimension(200, 22));
        routeStrField5.setMinimumSize(new Dimension(200, 22));
        routeStrField5.setPreferredSize(new Dimension(200, 22));

        this.getContentPane().add(userNameLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(6, 14, 0, 0), 0, 0));
        this.getContentPane().add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 8, 0), 0, 0));
        jPanel1.add(keyLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 0, 0), 0, 0));
        jPanel1.add(routeStrField1, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 16, 0, 12), 0, 0));

        jPanel1.add(keyLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(8, 12, 0, 0), 0, 0));
        jPanel1.add(routeStrField2, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 16, 0, 12), 0, 0));

        jPanel1.add(keyLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(8, 12, 0, 0), 0, 0));
        jPanel1.add(routeStrField3, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 16, 0, 12), 0, 0));

        jPanel1.add(keyLabel4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(8, 12, 0, 0), 0, 0));
        jPanel1.add(routeStrField4, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 16, 0, 12), 0, 0));

        jPanel1.add(keyLabel5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(8, 12, 0, 0), 0, 0));
        jPanel1.add(routeStrField5, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 16, 0, 12), 0, 0));
        jPanel1.add(keyLabel6, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(8, 12, 0, 0), 0, 0));
        jPanel1.add(routeStrField6, new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 16, 0, 12), 0, 0));
        jPanel1.add(keyLabel7, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(8, 12, 0, 0), 0, 0));
        jPanel1.add(routeStrField7, new GridBagConstraints(1, 6, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 16, 0, 12), 0, 0));
        jPanel1.add(keyLabel8, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(8, 12, 0, 0), 0, 0));
        jPanel1.add(routeStrField8, new GridBagConstraints(1, 7, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 16, 0, 12), 0, 0));
        jPanel1.add(keyLabel9, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(8, 12, 0, 0), 0, 0));
        jPanel1.add(routeStrField9, new GridBagConstraints(1, 8, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 16, 0, 12), 0, 0));

        add(jPanel2, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 8, 0), 0, 0));
        // jPanel6.add(addButton, null);
        okButton.setVisible(false);
        jPanel2.add(jLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 12, 0), 0, 0));
        jPanel2.add(editButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 8, 12), 0, 0));
        jPanel2.add(okButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 8, 12), 0, 0));
        jPanel2.add(cancelJButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 8, 12), 0, 0));

    }

    void editButton_actionPerformed(ActionEvent e)
    {
        okButton.setVisible(true);
        this.enableComponent(true);
        flag = true;
    }

    /**
     * 确定保存用户的快捷路线
     * @param e
     */
    void okButton_actionPerformed(ActionEvent e)
    {
        boolean isSaveSuccess = false;
        if(flag)
        {
            isSaveSuccess = saveShortCutRoute();
        }
        if(isSaveSuccess)
        this.dispose();
    }

    /**
     * 保存快捷路线
     */
    private boolean saveShortCutRoute()
    {
        List list = new ArrayList();
        ShortCutRouteInfo shortCutRoute = null;
        //构造该用户的快捷路线信息
        if(routeStrField1.getText() != null && !routeStrField1.getText().equals(""))
        {
            shortCutRoute = new ShortCutRouteInfo();
            shortCutRoute.setUserID(currentUser.getBsoID());
            shortCutRoute.setShortKey(keyLabel1.getText());
            shortCutRoute.setRouteStr(routeStrField1.getText());
            list.add(shortCutRoute);
        }
        if(routeStrField2.getText() != null && !routeStrField2.getText().equals(""))
        {
            shortCutRoute = new ShortCutRouteInfo();
            shortCutRoute.setUserID(currentUser.getBsoID());
            shortCutRoute.setShortKey(keyLabel2.getText());
            shortCutRoute.setRouteStr(routeStrField2.getText());
            list.add(shortCutRoute);
        }
        if(routeStrField3.getText() != null && !routeStrField3.getText().equals(""))
        {
            shortCutRoute = new ShortCutRouteInfo();
            shortCutRoute.setUserID(currentUser.getBsoID());
            shortCutRoute.setShortKey(keyLabel3.getText());
            shortCutRoute.setRouteStr(routeStrField3.getText());
            list.add(shortCutRoute);
        }
        if(routeStrField4.getText() != null && !routeStrField4.getText().equals(""))
        {
            shortCutRoute = new ShortCutRouteInfo();
            shortCutRoute.setUserID(currentUser.getBsoID());
            shortCutRoute.setShortKey(keyLabel4.getText());
            shortCutRoute.setRouteStr(routeStrField4.getText());
            list.add(shortCutRoute);
        }
        if(routeStrField5.getText() != null && !routeStrField5.getText().equals(""))
        {
            shortCutRoute = new ShortCutRouteInfo();
            shortCutRoute.setUserID(currentUser.getBsoID());
            shortCutRoute.setShortKey(keyLabel5.getText());
            shortCutRoute.setRouteStr(routeStrField5.getText());
            list.add(shortCutRoute);
        }
        if(routeStrField6.getText() != null && !routeStrField6.getText().equals(""))
        {
            shortCutRoute = new ShortCutRouteInfo();
            shortCutRoute.setUserID(currentUser.getBsoID());
            shortCutRoute.setShortKey(keyLabel6.getText());
            shortCutRoute.setRouteStr(routeStrField6.getText());
            list.add(shortCutRoute);
        }
        if(routeStrField7.getText() != null && !routeStrField7.getText().equals(""))
        {
            shortCutRoute = new ShortCutRouteInfo();
            shortCutRoute.setUserID(currentUser.getBsoID());
            shortCutRoute.setShortKey(keyLabel7.getText());
            shortCutRoute.setRouteStr(routeStrField7.getText());
            list.add(shortCutRoute);
        }
        if(routeStrField8.getText() != null && !routeStrField8.getText().equals(""))
        {
            shortCutRoute = new ShortCutRouteInfo();
            shortCutRoute.setUserID(currentUser.getBsoID());
            shortCutRoute.setShortKey(keyLabel8.getText());
            shortCutRoute.setRouteStr(routeStrField8.getText());
            list.add(shortCutRoute);
        }
        if(routeStrField9.getText() != null && !routeStrField9.getText().equals(""))
        {
            shortCutRoute = new ShortCutRouteInfo();
            shortCutRoute.setUserID(currentUser.getBsoID());
            shortCutRoute.setShortKey(keyLabel9.getText());
            shortCutRoute.setRouteStr(routeStrField9.getText());
            list.add(shortCutRoute);
        }
      //CR1 begin
//        //检查输入的单位信息
//        if(list.size() == 0)
//        {
//            DialogFactory.showWarningDialog(parentFrame, "请输入要定义的快捷路线信息");
//            return false;
//        }
      //CR1 end
        if(!checkDepartment(list))
        {
            return false;
        }
        //调用服务，保存快捷路线
        Class[] paraClass = {List.class, String.class};
        Object[] obj = {list, this.currentUser.getBsoID()};
        try
        {
            RequestHelper.request(//CR8
                    "consTechnicsRouteService", "saveShortCutRoute", paraClass, obj);

        }catch(Exception ex)
        {
            ex.printStackTrace();
            String message = QMExceptionHandler.handle(ex);
            DialogFactory.showWarningDialog(parentFrame, message);
            return false;
        }
        return true;
    }

    /**
     * 检查路线单位是否合法
     * @param list 路线信息集合
     * @return boolean 20120119 徐春英 add
     */
    private boolean checkDepartment(List list)
    {
        boolean flag = false;
        Vector vec = new Vector();
        for(int i = 0, j = list.size();i < j;i++)
        {
            ShortCutRouteInfo shortInfo = (ShortCutRouteInfo)list.get(i);
            //解析路线信息
            StringTokenizer routeStrToken = new StringTokenizer(shortInfo.getRouteStr(), ";");
            int size = routeStrToken.countTokens();
            String[] branches = new String[size];
            if(size > 0)
            {
                branches[0] = routeStrToken.nextToken();
                if(size > 1)
                {
                    for(int m = 1;m < size;m++)
                    {
                        branches[m] = routeStrToken.nextToken();
                    }
                }
            }
            for(int n = 0;n < size;n++)
            {
                String routeStr = branches[n];
                int length = routeStr.length();
                int position = routeStr.indexOf("=");
                String manuString = "";
                String assemString = "";
                // 获得制造路线和装配路线节点。
                if(position != -1)
                {
                    manuString = routeStr.substring(0, position);
                    //装配路线节点。
                    assemString = routeStr.substring(position + 1, length);
                }else
                {
                    manuString = routeStr;
                }
                Collection nodeString = new Vector();
                if(manuString != null && !manuString.equals(""))
                {
                    StringTokenizer token = new StringTokenizer(manuString, "-");
                    while(token.hasMoreTokens())
                    {
                        nodeString.add(token.nextToken());
                    }
                }
                if(assemString != null && !assemString.trim().equals(""))
                    nodeString.add(assemString);
                for(Iterator manuIterator = nodeString.iterator();manuIterator.hasNext();)
                {
                    String manu = (String)manuIterator.next();
                    String departID = RouteClientUtil.getDepartmentID(manu);
                    if(departID == null || departID.equals(""))
                    {
                        vec.add(manu);
                        flag = true;
                        continue;
                    }
                }
            }
            //将数据库中不存在的路线单位给出提示信息
            if(flag)
            {
                String departNames = "";
                if(vec.size() == 1)
                {
                    departNames = (String)vec.elementAt(0);
                }else
                {
                    for(int m = 0, n = vec.size();m < n;m++)
                    {
                        String mamu = (String)vec.elementAt(m);
                        departNames = departNames + mamu;
                        if(m != n - 1)
                            departNames = departNames + ";";
                    }
                }
                vec.clear();
                String aa = "{" + departNames + "}";
                Object[] obj2 = {aa};
                String s = QMMessage.getLocalizedMessage(RESOURCE, "60", obj2, null);
                DialogFactory.showFormattedWarningDialog(parentFrame, s);
                return false;
            }
        }
        return true;
    }

    /**
     * 取消 则关闭界面
     * @param e
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        this.dispose();
    }

    /**
     * 设置组件使能
     * @param flag
     */
    public void enableComponent(boolean flag)
    {
        routeStrField1.setEnabled(flag);
        routeStrField2.setEnabled(flag);
        routeStrField3.setEnabled(flag);
        routeStrField4.setEnabled(flag);
        routeStrField5.setEnabled(flag);
        routeStrField6.setEnabled(flag);
        routeStrField7.setEnabled(flag);
        routeStrField8.setEnabled(flag);
        routeStrField9.setEnabled(flag);
    }
}
