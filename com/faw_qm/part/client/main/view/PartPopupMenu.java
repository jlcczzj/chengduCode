package com.faw_qm.part.client.main.view;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.faw_qm.clients.beans.explorer.QMNode;
import com.faw_qm.clients.vc.controller.CheckInOutTaskLogic;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.ownership.model.OwnableIfc;
import com.faw_qm.ownership.util.OwnershipHelper;
import com.faw_qm.part.client.design.view.PartTaskJPanel;
import com.faw_qm.part.client.main.controller.QMProductManager;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.wip.model.WorkableIfc;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
/* 
 * CR1 穆勇鹏 20090603  原因：当点击检出对象的原本进行右键更新时，“更新”按钮
 * 								不可用，不符合需求
 * 						方案：在上述状态时使“更新”按钮可用
 * 						备注：ＴＤ２０８５
 * SS1 2014-6-6   文柳 解放工艺BOM:产品信息管理器增加右键“查看发布BOM” 
 * SS2 查看BOM版本与逻辑不合栏、没子组、不显示保存成功、应支持按单级/多级导出 xianglx 2014-9-28
 * SS3 添加右键的零部件另存为菜单 xianglx 2014-10-20
 * SS4 产品信息浏览器增加定位搜索零部件右键菜单 pante 2014-01-14
 * 
 */
public class PartPopupMenu extends JPopupMenu
{
    /**序列化ID*/
    static final long serialVersionUID = 1L;

    ResourceBundle resourcebundle = ResourceBundle.getBundle(
            "com.faw_qm.part.client.main.util.QMProductManagerRB",
            RemoteProperty.getVersionLocale());

    private JMenu changeMenu;

    private JMenuItem jiegou;

    private JMenuItem jibenshux;

    private JMenuItem shiwutx;

    private JMenuItem cankaowd;

    private JMenuItem miaoshuwd;

    private JMenuItem cut;

    private JMenuItem copy;

    private JMenuItem bmove;

    private JMenuItem paste;

    private JMenuItem delete;

    private JMenuItem view;
  //CCBegin SS1
    private JMenuItem viewReleaseBom;
//CCEnd SS1
  //CCBegin SS2
    private JMenuItem viewReleaseBomDj;
//CCEnd SS2
  //CCBegin SS3
    private JMenuItem saveAs;
//CCEnd SS3
    
    //CCBegin SS4
    private JMenuItem DWSS;
    //CCEnd SS4
    private JMenuItem checkin;

    private JMenuItem checkout;

    private JMenuItem undocheckout;

    private JMenuItem movefw;

    private JMenuItem refresh;

    private QMProductManager manager = null;
    
    //liyz add
    PartTaskJPanel task = null;//end

    public PartPopupMenu()
    {
        jbInit();
    }
    public PartPopupMenu(QMProductManager manager)
    {
    	this.manager=manager;
    	//liyz add
    	task = manager.getExplorer().getPartTaskJPanel();//end
        jbInit();
        
    }

    public PartPopupMenu(String p0)
    {
        super(p0);
    }

    /*
     * 界面初始化。
     */
    private void jbInit()
    {
        changeMenu = new JMenu(resourcebundle.getString("update")); 
        jiegou = new JMenuItem();
        jibenshux = new JMenuItem();
        cankaowd = new JMenuItem();
        miaoshuwd = new JMenuItem();
        copy = new JMenuItem();
        cut = new JMenuItem();
        bmove = new JMenuItem();
        paste = new JMenuItem();
        delete = new JMenuItem();
        view = new JMenuItem();
        //CCBegin SS1
        viewReleaseBom = new JMenuItem();
        //CCEnd SS1
        //CCBegin SS2
        viewReleaseBomDj = new JMenuItem();
        //CCEnd SS2
        //CCBegin SS3
        saveAs = new JMenuItem();
        //CCEnd SS3
        //CCBegin SS4
        DWSS = new JMenuItem();
        //CCEnd SS4
        checkin = new JMenuItem();
        checkout = new JMenuItem();
        undocheckout = new JMenuItem();
        shiwutx = new JMenuItem();
        movefw = new JMenuItem();
        refresh = new JMenuItem();
        jiegou.setAction(new JiegouAction(resourcebundle.getString("jiegou")));
        jibenshux.setAction(new BaseAttrAction(resourcebundle
                .getString("jibenshuxing")));
        shiwutx
                .setAction(new IBAAction(resourcebundle
                        .getString("shiwutexing")));
        cankaowd.setAction(new CankaoAction(resourcebundle
                .getString("cankaowendang")));
        miaoshuwd.setAction(new MiaoshuAction(resourcebundle
                .getString("miaoshuwendang")));
        LookAction action = new LookAction(resourcebundle.getString("view"));
        view.setAction(action);
        //CCBegin SS1
        ViewReleaseBomAction viewBomAction = new ViewReleaseBomAction(resourcebundle.getString("VIEW_REALSE_BOM"));
        viewReleaseBom.setAction(viewBomAction);
        //CCEnd SS1
        //CCBegin SS2
        ViewReleaseBomAction viewBomDJAction = new ViewReleaseBomAction(resourcebundle.getString("VIEW_REALSE_BOM_DJ"));
        viewReleaseBomDj.setAction(viewBomDJAction);
        //CCEnd SS2
         //CCBegin SS3
    		ResourceBundle resourcebundleLs = ResourceBundle.getBundle(
            "com.faw_qm.part.client.design.util.PartDesignViewRB",
            RemoteProperty.getVersionLocale());      
        SaveAsAction saveAsAction = new SaveAsAction(resourcebundleLs.getString("saveAsPartTitle"));
        saveAs.setAction(saveAsAction);
        //CCEnd SS3
        
      //CCBegin SS4
        DWSS.setAction(new DWSSAction(resourcebundle.getString("DWSS")));
        //CCEnd SS4
        
       checkin
                .setAction(new CheckInAction(resourcebundle
                        .getString("checkin")));
        checkout.setAction(new CheckOutAction(resourcebundle
                .getString("checkout")));
        undocheckout.setAction(new UndoCheckOutAction(resourcebundle
                .getString("undocheckout")));
        copy.setAction(new CopyAction(resourcebundle.getString("copy")));
        cut.setAction(new CutAction(resourcebundle.getString("cut")));
        paste.setAction(new PasteAction(resourcebundle.getString("paste")));
        movefw.setAction(new MoveFromWindowAction(resourcebundle
                .getString("movefromwindow")));
        delete.setAction(new DeleteAction(resourcebundle.getString("delete")));
        refresh
                .setAction(new RefreshAction(resourcebundle
                        .getString("refresh")));
        bmove.setAction(new BMoveAction(resourcebundle.getString("bmove")));        
        add(changeMenu);
        add(copy);
        add(cut);
        add(bmove);
        addSeparator();
        add(paste);
          //CCBegin SS3
        add(saveAs);
        //CCEnd SS3
        add(delete);
        add(view);
        //CCBegin SS1
        add(viewReleaseBom);
        //CCEnd SS1
          //CCBegin SS2
        add(viewReleaseBomDj);
        //CCEnd SS2
      addSeparator();
        add(checkin);
        add(checkout);
        add(undocheckout);
        addSeparator();
        add(movefw);
        add(refresh);
      //CCBegin SS4
        add(DWSS);
        //CCEnd SS4
        //liyz add根据需求V2.3如果tab页选择性部署时，相应的右键快捷菜单功能也要可配置
        int i=0;
        while(true)
        {
//        	//测试用，部署环境注释begin
//            java.util.Properties   prop=new Properties();
//            FileInputStream fis;
//    		try
//    		{
//    			fis = new FileInputStream("F:/PDMV4/product/productfactory/phosphor/cpdm/classes/properties/part.properties");
//    			prop.load(fis);
//    		} catch (Exception e1)
//    		{
//    			e1.printStackTrace();
//    		}          
//           String s=prop.getProperty( "com.faw_qm.part.manager.JTab."+String.valueOf(i));
//           //end
           String s = RemoteProperty.getProperty( "com.faw_qm.part.manager.JTab." + String.valueOf(i));
           if (s == null)
           {
               break;
           }
           if(s.equals("usage"))
           changeMenu.add(jiegou);
           if(s.equals("base"))
           changeMenu.add(jibenshux);
           if(s.equals("iba"))
           changeMenu.add(shiwutx);
           if(s.equals("description"))
           changeMenu.add(miaoshuwd);
           if(s.equals("reference"))
           changeMenu.add(cankaowd);
           i++;
        }
    }
   //CCBegin SS3
    class SaveAsAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public SaveAsAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
        	  manager.processSaveAsCommand();
         }
    }
   //CCEnd SS3

   //CCBegin SS1
    class ViewReleaseBomAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

   //CCBegin SS2
   			private String jbbs="多级";
   //CCBegin SS2
        public ViewReleaseBomAction(String name)
        {
            super(name);
   //CCBegin SS2
   					if (name.equals(resourcebundle.getString("VIEW_REALSE_BOM_DJ")))
   					{
   						jbbs="单级";
   					}
   //CCBegin SS2
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
        	  BaseValueIfc part = (BaseValueIfc) manager.getExplorer().getTree().getSelected().getObj().getObject();
              if(part instanceof QMPartIfc)
              {
              String bsoID = part.getBsoID();
              HashMap map = new HashMap();
              map.put("bsoID", bsoID);
   //CCBegin SS2
              map.put("jbbs", jbbs);
   //CCBegin SS2
              RichToThinUtil.toWebPage("GYBomAdoptNotice_ViewReleaseBom.screen", map);
              }
        }
    }
   //CCEnd SS1
    //查看
    class LookAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public LookAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            HashMap hashmap = new HashMap();
            
            BaseValueIfc part = (BaseValueIfc) manager.getExplorer().getTree()
                    .getSelected().getObj().getObject();
            if(part instanceof QMPartIfc)
            {
            String bsoID = part.getBsoID();
            hashmap.put("bsoID", bsoID);
            String hasPcfg = com.faw_qm.framework.remote.RemoteProperty
                    .getProperty("com.faw_qm.hasPcfg", "true");
            hashmap.put("infoD", hasPcfg);
            //转入页面查看零部件属性。
            RichToThinUtil.toWebPage("Part-Other-PartLookOver-001-0A.do",
                    hashmap);
            hashmap=null;
            }
            else
            	if(part instanceof QMPartMasterIfc)
            	{
            		QMPartMasterIfc master = (QMPartMasterIfc) part;
		            String masterBsoID = master.getBsoID();
		            ///////////////////////modify begin////////////////////////////
		            //modify by muyp 20080507
		            //修改原因：对零部件主信息对象进行查看，页面显示不正确(TD1754)
		            ServiceRequestInfo info = new ServiceRequestInfo();
                    info.setServiceName("StandardPartService");
                    info.setMethodName("findPart");
                    Class[] theClass = {QMPartMasterIfc.class};
                    info.setParaClasses(theClass);
                    Object[] objs = {master};
                    info.setParaValues(objs);
                    RequestServer server = RequestServerFactory.getRequestServer();
                    try {
        				Collection links = (Collection) server.request(info);
        				if(links != null)
        				{
                            Iterator iter = links.iterator();
                            String partID = "";
                            if (iter.hasNext())
                            {
                            	QMPartIfc link = (QMPartIfc) iter.next();
                                partID = link.getBsoID();
                            }
                            //modify by shf 2003/09/13
        		            hashmap.put("BsoID", masterBsoID);
                            hashmap.put("bsoID", partID);
                            //转入页面查看零部件主信息的属性。
        		            //modify by shf 2003/09/13
        		            RichToThinUtil.toWebPage("part_version_iterationsViewMain.screen",
        		                    hashmap);
        		            master = null;
        		            masterBsoID = null;
                            partID = "";
                            hashmap = null;
                        }
        			} catch (QMRemoteException ex) {
        				ex.printStackTrace();
        			}
        			//////////////////////////modify end////////////////////////////////   
            	}
        }
    }

    class CutAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public CutAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            manager.cut();
            paste.setEnabled(true);
        }
    }

    class CopyAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public CopyAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            manager.copy();
            paste.setEnabled(true);
        }
    }

    class PasteAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public PasteAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            manager.paste();
        }
    }

    class CheckInAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public CheckInAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            QMPartInfo part = (QMPartInfo) manager.getExplorer().getTree()
                    .getSelected().getObj().getObject();
            manager.checkinSelectedObjects();
        }
    }

    class CheckOutAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public CheckOutAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            manager.checkoutSelectedObjects();
        }
    }

    class UndoCheckOutAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public UndoCheckOutAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            manager.undoCheckoutSelectedObjects();
        }
    }

    class DeleteAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public DeleteAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            manager.deleteSelectedObject();
        }
    }

    class BMoveAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public BMoveAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            manager.bMove();
        }
    }

    class MoveFromWindowAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public MoveFromWindowAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            manager.clear();
        }
    }

    class RefreshAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public RefreshAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            manager.refreshSelectedObject();
        }
    }

    class IBAAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public IBAAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            updateSelectedObject();
            updateIBA();
        }
    }

    class CankaoAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public CankaoAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            updateSelectedObject();
            updateRefe();
        }
    }

    class MiaoshuAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public MiaoshuAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            updateSelectedObject();
            updateDesc();
        }
    }

    class JiegouAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public JiegouAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            updateSelectedObject();
            updateUses();
        }
    }

    class BaseAttrAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public BaseAttrAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            updateSelectedObject();
            updateAttribute();
        }
    }
//    CCBegin SS4
    class DWSSAction extends AbstractAction
    {
        /**序列化ID*/
        static final long serialVersionUID = 1L;

        public DWSSAction(String name)
        {
            super(name);
        }

        /**
         * 事件监听
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            manager.processDWSSCommand();
        }
    }
//    CCEnd SS4

    //liyz 修改右键更新tab页。现在tab页是可以配置的，索引位置也不再固定
    public void updateUses()
    {
        //manager.getExplorer().updateTabbed(0);
    	int usageIndex = task.getUsageIndex();
    	manager.getExplorer().updateTabbed(usageIndex);    	
    }
    
    public void updateAttribute()
    {
    	int baseIndex = task.getBaseIndex();
        manager.getExplorer().updateTabbed(baseIndex);
    }

    public void updateRefe()
    {
    	int refIndex = task.getRefIndex();
        manager.getExplorer().updateTabbed(refIndex);
    }

    public void updateDesc()
    {
    	int deseIndex = task.getDescIndex();
        manager.getExplorer().updateTabbed(deseIndex);
    }

    public void updateIBA()
    {
    	int ibaIndex = task.getIbaIndex();
        manager.getExplorer().updateTabbed(ibaIndex);
    }
    //end

    /**
     * 右键的更新。
     */
    protected void updateSelectedObject()
    {
        manager.updateSelectedObject();
    }

    public static void main(String[] args)
    {
        PartPopupMenu partPopupMenu1 = new PartPopupMenu();
    }
    
    public void initMenuItem()
    {
		//CR Begin zhangq 20090523
    	   PartHelper helper = new PartHelper();
    	BaseValueIfc part = (BaseValueIfc) manager.getExplorer().getTree()
        .getSelected().getObj().getObject();
    	try
    	{
			cut.setEnabled(false);
			paste.setEnabled(false);
			bmove.setEnabled(false);
			movefw.setEnabled(false);
			changeMenu.setEnabled(false);
			checkin.setEnabled(false);
			checkout.setEnabled(false);
			undocheckout.setEnabled(false);
			delete.setEnabled(false);
			if (part instanceof QMPartIfc) {
				delete.setEnabled(true);
				// 是否是根节点
				if (isRootNode()) {
					movefw.setEnabled(true);
				} else {
					// 如果不是根节点，选择零部件的父节点是否可更新
					Object obj = manager.getExplorer().getTree().getSelected()
							.getP().getObj().getObject();
					QMPartIfc partifc = null;
					if (obj instanceof QMPartInfo) {
						partifc = (QMPartInfo) obj;
					} else if ((((QMNode) manager.getExplorer().getTree()
							.getSelected().getParent()).getP().getObj()
							.getObject()) instanceof QMPartIfc) {
						partifc = (QMPartInfo) ((QMNode) manager.getExplorer()
								.getTree().getSelected().getParent()).getP()
								.getObj().getObject();
					}
					if (helper.isAllowUpdate(partifc)) {
						cut.setEnabled(true);
						bmove.setEnabled(true);
					} 
				}

				// 是否被他人检出
				if (isCheckOutOther(part)) {
					undocheckout.setEnabled(true);
				} else
				// 没有被他人检出，被当前用户检出并且是工作副本
				if (isCheckOut(part)) {
					checkin.setEnabled(true);
					undocheckout.setEnabled(true);
//					if (isWorkCopy(part)) {//ＣＲ１ begin
						changeMenu.setEnabled(true);
//					}//ＣＲ１　end
//				}
//				else if (isInPersonal(part)) {
//					System.out.println(" isInPersonal ");
//					checkin.setEnabled(true);
//					checkout.setEnabled(false);
//					undocheckout.setEnabled(false);
				} else  if(OwnershipHelper.isOwned((OwnableIfc)part)){
					changeMenu.setEnabled(true);
					checkin.setEnabled(true);
				}
				else{
					changeMenu.setEnabled(true);
					checkout.setEnabled(true);
				}
				if (helper.isAllowUpdate((QMPartIfc) part)) {
					if (manager.isPaste()) {
						paste.setEnabled(true);
					}
				} 
			} else if (part instanceof QMPartMasterIfc) {
				// 是否是根节点
				if (isRootNode()) {
					movefw.setEnabled(true);
				} else {
					// 如果不是根节点，选择零部件的父节点是否可更新
					Object obj = manager.getExplorer().getTree().getSelected()
							.getP().getObj().getObject();
					QMPartIfc partifc = null;
					if (obj instanceof QMPartInfo) {
						partifc = (QMPartInfo) obj;
					} else if ((((QMNode) manager.getExplorer().getTree()
							.getSelected().getParent()).getP().getObj()
							.getObject()) instanceof QMPartIfc) {
						partifc = (QMPartInfo) ((QMNode) manager.getExplorer()
								.getTree().getSelected().getParent()).getP()
								.getObj().getObject();
					}
					if (helper.isAllowUpdate(partifc)) {
						cut.setEnabled(true);
						bmove.setEnabled(true);
					} 
				}
			}
		} catch (QMException e) {
			e.printStackTrace();
		}
		//CR End zhangq 20090523
	}
    /**
     * 是否在个人资料夹中
     * @param value
     * @return true 在个人资料夹中
     * @throws QMException
     *@see boolean
     */
    private boolean isInPersonal(BaseValueIfc value)
    throws QMException
    {
    	Object object = IBAUtility.invokeServiceMethod("SessionService",
            "getCurUserID", null, null);
    boolean flag = OwnershipHelper.isOwnedBy((OwnableIfc) value, (String) object);
    return flag;
    }
    private boolean isInPublic(BaseValueIfc value)
    throws QMException
    {
       return 	!CheckInOutTaskLogic.isInVault((WorkableIfc)value);
    }
    /**
     * 是否被别人检出
     */
    private boolean isCheckOutOther(BaseValueIfc value)
    throws QMException
    {
    	return CheckInOutTaskLogic.isCheckedOutByOther((WorkableIfc)value);
    }
    /**
     * 是否被当前的用户检出
     */
    private boolean isCheckOut(BaseValueIfc value)
    throws QMException
    {
    	return CheckInOutTaskLogic.isCheckedOutByUser((WorkableIfc)value);
    }
    /**
     * 被当前用户检出是否是工作副本
     * @param value
     * @return
     *@see boolean
     */
    private boolean isWorkCopy(BaseValueIfc value)
    throws QMException
    {
    	boolean flag=false;
    	if(isCheckOut(value))
    	{
    		flag=CheckInOutTaskLogic
            .isWorkingCopy((WorkableIfc) value);
    	}
    	return flag;
    }
    private boolean isRootNode()
    {
    	
    	return manager.getExplorer().getTree()
        .getSelected().getP()==manager.getExplorer().getRootNode();
    }
}
