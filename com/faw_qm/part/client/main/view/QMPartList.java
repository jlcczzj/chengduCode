/** ���ɳ��� QMPartList.java    1.0    2009/05/22
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 20090623 ��ǿ �޸�ԭ��Ϊ�㲿����������Ӽ����ҷֱ��޸��Ӽ���ʹ���������ڱ������ʾ��ʹ����������
 *                   ��������������Ӽ���û����ȷ���ù�����ID����֤����ǰ��ȷ��ȡ��������Ӷ�����ʹ��������
 * CR2 20090623 ��ǿ �޸�ԭ�򣺸ı�ʹ�ýṹ������ʾ���ã����޸��Ӽ�ʹ���������ڱ���ʱ�����쳣��
 *                   ����취�������ñ��ı�ͷʱͬʱ���õ���¼��ͷ�������У��������ߵ�һ�¡� 
 * CR3 2011/10/09 ��� ԭ��:TD5180��ʹ�ýṹTABҳ�У��Բ��������ù淶���㲿��ִ�в鿴������ҳ���㲿��ͼ�겻��ȷ
 *                    ����:�޸��ڲ�Ʒ��Ϣ�������в鿴�㲿������Ϣʱ�Ľ��档ʹ��û��sidebar��part_version_iterationsViewMain-001.screen����ʾ��                 
 *SS1 ����BOM���������,�������鳤�� liuyang 2014-6-20
 *SS2 ���������汾 xianglx 2014-8-12
 *  SS3 �Ƚ��㲿�����ߵİ汾�������汾�Ƿ���ͬ����ͬ��ɫ��� xianglx 2014-10-12
 *  SS4 ��Ʒ��Ϣ�������ġ�ʹ�ýṹ�������һ�У���·�ߡ�����ʾ������·�ߡ��͡�װ��·�ߡ������� gaoys 2015-5-8
*/
package com.faw_qm.part.client.main.view;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import com.faw_qm.acl.ejb.entity.QMPermission;
import com.faw_qm.auth.RequestHelper;
import com.faw_qm.clients.beans.explorer.Explorable;
import com.faw_qm.clients.beans.explorer.QMExplorer;
import com.faw_qm.clients.beans.explorer.QMList;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.clients.vc.controller.CheckInOutTaskLogic;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.folder.model.FolderedIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.design.model.UsageItem;
import com.faw_qm.part.client.design.model.UsageMasterItem;
import com.faw_qm.part.client.design.view.PartTaskJPanel;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.util.QMCt;
import com.faw_qm.wip.model.WorkableIfc;
//CCBegin��SS4
import com.faw_qm.technics.route.model.TechnicsRouteBranchInfo;
//CCEnd SS4
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class QMPartList extends QMList implements MouseMotionListener
{
	/**���л�ID*/
	static final long serialVersionUID = 1L;

	JPopupMenu menu = new JPopupMenu();

	BaseValueIfc sonPart = null;

	QMPartInfo parentPart = null;

	//private String partNum;

	private QMExplorer explor;

	//private int select = 0;

	//private static RequestHelper helper = new RequestHelper();

	private PartTaskJPanel taskPanel = null;
	
	//add liyz	
	QMList myList = new QMList(10);
	//�б���
	public String headings[] = null;
	//�з���
	public String headingMethod[] = null;		
	
	protected static final String RESOURCE
    = "com.faw_qm.part.client.design.util.PartDesignViewRB";
	
	private static ResourceBundle resource = null;
		//CCBegin��SS3
	public HashMap partsByBaseLine = new HashMap();		
		//CCEnd��SS3
	public QMPartList(int lieNum)
	{
		super(lieNum);
		init();
	}
	
	//liyz add
	/**
     *���캯��,��ȡʹ�ýṹ�б���
     *@param ��ͷ���ݵ�������ʽ
     */
	public QMPartList(String[] heads)
	{
		//CCBegin��SS1
		//CCBegin��SS2
		//CCBegin��SS4
		//super(10, true);
		//super(12, true);
//		super(13, true);
		super(14, true);
		//CCEnd SS4
		//CCEnd SS2
		//CCEnd SS1
		headings=heads;
		int i=heads.length;
		try
		{
		//CCBegin��SS3
			RequestServer server = RequestServerFactory.getRequestServer();
			com.faw_qm.framework.remote.StaticMethodRequestInfo info = new com.faw_qm.framework.remote.StaticMethodRequestInfo();
			info.setClassName("com.faw_qm.part.util.PartServiceHelper");
			info.setMethodName("getPartsByBaseLine");

			partsByBaseLine = ((HashMap) server.request(info));
		//CCEnd��SS3
			init();
			setHeadings(headings);
			this.getTable().getColumnModel().getColumn(i).setMaxWidth(0);
	        this.getTable().getColumnModel().getColumn(i).setPreferredWidth(0);
	        this.getTable().getTableHeader().getColumnModel().getColumn(i).setMinWidth(0);
	        //CCBegin SS1
		//CCBegin��SS2
	      //CCBegin��SS4
	       // int[] in=new int[10];
	        //int[] in=new int[12];
//	        int[] in=new int[13];
	        int[] in=new int[14];
	      //CCEnd SS4                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		//CCEnd SS2
	        //CCEnd SS1
            for(int j=0;j<i;j++)
            {
            	in[j]=4;
            }
            //CCBegin SS1
		//CCBegin��SS2
          //CCBegin��SS4
            //for(int k=i;k<10;k++)
            //for(int k=i;k<12;k++)
//            for(int k=i;k<13;k++)
              for(int k=i;k<14;k++)
         //CCEnd SS4
		//CCEnd SS2
            //CCEnd SS1
            in[i]=0;
            setRelColWidth(in);		
		}
		catch(Exception e)
		{
			return;
		}
	}

	public void init()
	{	
		resource = ResourceBundle.getBundle(RESOURCE, QMCt.getContext()
			.getLocale());
		LookAction look = new LookAction(resource.getString("look"));
		MoveAction move = new MoveAction(this, resource.getString("move"));
		menu.add(look);
		menu.add(move);
	}

	public void mousePressed(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
		if(getManagerDrage())
			explor.getManager().setEnterList(true);
	}

	/**
	 * �շ���������ƶ�
	 * @param mouseevent MouseEvent
	 */
	public void mouseMoved(MouseEvent mouseevent)
	{
	}

	boolean isdrage = false;

	public void mouseDragged(MouseEvent e)
	{
		isdrage = true;
	}

	public void mouseReleased(MouseEvent e)
	{
		boolean fromcapp=explor.getManager().fromcapp;
		//add for capp ������2007.06.29
		if(fromcapp)
			return;
		if (e.isPopupTrigger()) {
			int x = this.getTable()
					.columnAtPoint(new Point(e.getX(), e.getY()));
			int y = this.getTable().rowAtPoint(new Point(e.getX(), e.getY()));
			//select = y;
			if(x>=0&&y>=0) {
			    this.getTable().setRowSelectionInterval(y, y);
			  //������ڵ��㲿�����ǿɸ���״̬���Ҽ���ť�����ƶ�ѡ��
	            QMPartInfo part = getTreePart();
	            PartHelper helper=new PartHelper();
	            //boolean fromcapp=explor.getManager().getFromCapp();
	            if(!helper.isAllowUpdate(part))
	                menu.getComponent(1).setEnabled(false);
	            else
	                menu.getComponent(1).setEnabled(true);
	            menu.show(e.getComponent(), e.getX(), e.getY());
	            //partNum=this.getTable().findComponentAt(0,y).getName();
	            //partNum = this.getCellText(y, 0);
	            //this.getObject(y);
	            //if(getDetail(y).getObject() instanceof QMPartIfc)
	            {
	                try
	                {
	                    sonPart = (BaseValueIfc) getDetail(y).getObject();
	                    //���Ͻڵ㱻���˼��
	                    if(CheckInOutTaskLogic
	                            .isCheckedOutByOther((WorkableIfc) getTreePart()))
	                    {
	                    }
	                    //���Ͻڵ�
	                }
	                catch (Exception exp)
	                {
	                    exp.printStackTrace();
	                }
	            }
			}
		}
	}

	public void mouseExited(MouseEvent e)
	{
	}

	class LookAction extends AbstractAction
	{
		/**���л�ID*/
		static final long serialVersionUID = 1L;

		public LookAction(String name)
		{
			super(name);
			//action = name;
		}

		/**
		 * �¼�����
		 * @param e ActionEvent
		 */
		public void actionPerformed(ActionEvent e)
		{
//			HashMap hashmap = new HashMap();
//			String bsoID = sonPart.getBsoID();
//			hashmap.put("bsoID", bsoID);
//			String hasPcfg = com.faw_qm.framework.remote.RemoteProperty
//					.getProperty("com.faw_qm.hasPcfg", "true");
//			hashmap.put("infoD", hasPcfg);
//			//ת��ҳ��鿴�㲿�����ԡ�
//			RichToThinUtil.toWebPage("Part-Other-PartLookOver-001-0A.do",
//					hashmap);
			
			 if(sonPart instanceof QMPartIfc)
		        {
		            String bsoID = sonPart.getBsoID();
		            HashMap hashmap = new HashMap();
		            hashmap.put("bsoID", bsoID);
		            String hasPcfg = com.faw_qm.framework.remote.RemoteProperty
		                    .getProperty("com.faw_qm.hasPcfg", "true");
		            hashmap.put("infoD", hasPcfg);
		            //ת��ҳ��鿴�㲿�����ԡ�
		            RichToThinUtil.toWebPage("Part-Other-PartLookOver-001-0A.do",
		                    hashmap);
		            bsoID = null;
		            hashmap = null;
		            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		        }
		        else if(sonPart instanceof QMPartMasterIfc)
		        {
		            QMPartMasterIfc master = (QMPartMasterIfc) sonPart;
		            String masterBsoID = master.getBsoID();//CR3 begin
		            ///////////////////////modify begin////////////////////////////
		            //modify by muyp 20080507
		            //�޸�ԭ�򣺶��㲿������Ϣ������в鿴��ҳ����ʾ����ȷ(TD1754)
//		            ServiceRequestInfo info = new ServiceRequestInfo();
//                    info.setServiceName("StandardPartService");
//                    info.setMethodName("findPart");
//                    Class[] theClass = {QMPartMasterIfc.class};
//                    info.setParaClasses(theClass);
//                    Object[] objs = {master};
//                    info.setParaValues(objs);
//                    RequestServer server = RequestServerFactory.getRequestServer();
//                    try {
//        				Collection links = (Collection) server.request(info);
//        				if(links != null)
//        				{
//                            Iterator iter = links.iterator();
//                            String partID = "";
//                            if (iter.hasNext())
//                            {
//                            	QMPartIfc link = (QMPartIfc) iter.next();
//                                partID = link.getBsoID();
//                            }
                            HashMap hashmap = new HashMap();
                            //modify by shf 2003/09/13
        		            hashmap.put("BsoID", masterBsoID);;
//                            hashmap.put("bsoID", partID);
                            //ת��ҳ��鿴�㲿������Ϣ�����ԡ�
        		            //modify by shf 2003/09/13
        		            RichToThinUtil.toWebPage("part_version_iterationsViewMain-001.screen",
        		                    hashmap);
        		            master = null;
        		            masterBsoID = null;
//                            partID = "";
                            hashmap = null;
//                        }
//        			} catch (QMRemoteException ey) {
//        				// TODO �Զ����� catch ��
//        				ey.printStackTrace();
//        			}
                    //////////////////////////modify end////////////////////////////////CR3 END
		        }
			
		}
	}

	class MoveAction extends AbstractAction
	{
		/**���л�ID*/
		static final long serialVersionUID = 1L;

		QMPartList list;

		public MoveAction(QMPartList list, String name)
		{
			super(name);
			this.list = list;
			//action = name;
		}

		/**
		 * �¼�����
		 * @param e ActionEvent
		 */
		public void actionPerformed(ActionEvent e)
		{
			PartTaskJPanel panel = getTaskPanel();
			if(panel != null)
				panel.removeUsage();
		}
	}

	public void setTaskPanel(PartTaskJPanel panel)
	{
		taskPanel = panel;
	}

	public PartTaskJPanel getTaskPanel()
	{
		return taskPanel;
	}

	public void setQMExplorer(QMExplorer explorer)
	{
		this.explor = explorer;
	}

	public QMExplorer getQMExplorer()
	{
		return explor;
	}

	public QMPartInfo getTreePart()
	{
		Object obj = explor.getTree().getSelected().getObj().getObject();
		if(obj instanceof QMPartIfc)
		{
			parentPart = (QMPartInfo) obj;
		}
		return parentPart;
	}

	private void dispatchRefresh(int i, Object obj)
	{
		if(obj != null)
		{
			//������ˢ���¼�
			RefreshEvent refreshevent = new RefreshEvent(this, i, obj);
			//��ˢ�·������ˢ���¼�
			RefreshService.getRefreshService().dispatchRefresh(refreshevent);
		}
	}

	protected ConfigSpecItem getConfigSpecItem()
	{
		try
		{
			PartConfigSpecIfc configSpecIfc = (PartConfigSpecIfc) PartServiceRequest
					.getCurrentConfigSpec();
			ConfigSpecItem configSpecItem = new ConfigSpecItem(configSpecIfc);
			return configSpecItem;
		}
		catch (QMRemoteException qmRemoteException)
		{
			//  showMessage(qmRemoteException.getClientMessage(), exceptionTitle);
			qmRemoteException.printStackTrace();
			return null;
		}
		catch (QMException qmException)
		{
			//showMessage(qmException.getClientMessage(), exceptionTitle);
			qmException.printStackTrace();
			return null;
		}
	}

	boolean flag = true;

	boolean isAccess = false;

	boolean isInP = false;

	boolean isCheckOut = false;

	

	private boolean isCheckAccess(QMPartInfo part)
	{
		boolean isAccess = false;
		Boolean acc;
		//��ǰ�û��Ƿ���㲿�����޸�Ȩ��
		Class[] paraClass2 = {Object.class, String.class};
		Object[] objs2 = {(Object) part, QMPermission.MODIFY};
		//Collection result = null;
		try
		{
			acc = (Boolean) IBAUtility.invokeServiceMethod(
					"AccessControlService", "checkAccess", paraClass2, objs2);
		}
		catch (QMRemoteException ex)
		{
			isAccess = false;
			String RESOURCES = "com.faw_qm.part.client.design.util.PartDesignViewRB";
			String title = QMMessage.getLocalizedMessage(RESOURCES,
					"exception", null);
			JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
					JOptionPane.INFORMATION_MESSAGE);
			ex.printStackTrace();
			return false;
		}
		isAccess = acc.booleanValue();
		return isAccess;
	}

	private boolean isInPerson(QMPartInfo part)
	{
		boolean flag1 = false;
		if(part instanceof FolderedIfc)
		{
			FolderEntryIfc folderedIfc = (FolderEntryIfc) part;
			Class[] class1 = {FolderEntryIfc.class};
			Object[] param1 = {folderedIfc};
			//�����ļ��з���ķ�����ö������ڵ��ļ��С�
			FolderIfc folder = null;
			try
			{
				folder = (FolderIfc) IBAUtility.invokeServiceMethod(
						"FolderService", "getFolder", class1, param1);
			}
			catch (QMRemoteException ex)
			{
				folder = null;
				ex.printStackTrace();
			}
			Class[] class2 = {FolderIfc.class};
			Object[] param2 = {folder};
			//�����ļ��з���ķ����ж�folder�Ƿ�Ϊ�����ļ��С�
			Boolean flag = null;
			try
			{
				flag = (Boolean) IBAUtility.invokeServiceMethod(
						"FolderService", "isPersonalFolder", class2, param2);
			}
			catch (QMRemoteException ex1)
			{
				flag = null;
				ex1.printStackTrace();
			}
			flag1 = flag.booleanValue();
		}
		return flag1;
	}

	//add by zhangqiang
	public void setListItems(Explorable aexplorable[], String as[][])
	{
		clear();
		int i = aexplorable.length;
		for (int j = aexplorable.length - 1; j >= 0; j--)
		{
			Explorable explorable = aexplorable[j];
			//�õ�ָ����Ψһ��ʶ:branchID
			String s = explorable.getUniqueIdentity();
			//���part
			explorable.getObject();
			myDetailHashTable.put(s, explorable);
			for (int l = 0; l < getNumberOfCols() && l < headingMethod.length; l++)
			{
				String s1 = "";
				s1 = as[j][l];
				if(l == 0)
				{
					Image image = explorable.getStandardIcon();
					if(image != null)
					{
						
						addUniqueImageCell(j, l, getAttrValue(explorable,
								headingMethod[l]), image, s);
					}
					else
					{
						
						addUniqueTextCell(j, l, getAttrValue(explorable,
								headingMethod[l]), s);
					}
				}
				else
				{
					addTextCell(j, l, s1);
				}
			}
		}
	}

	//CR1 Begin 20090623 zhangq �޸�ԭ��Ϊ�㲿����������Ӽ����ҷֱ��޸��Ӽ���ʹ���������ڱ������ʾ��ʹ����������
	public void addDetail(Explorable aexplorable[], String as[][], int begin,
			int size)
	{
		int i = size;
		int cols=getNumberOfCols() ;
		if(cols>headingMethod.length){
			cols=headingMethod.length;
		}
		for (int j = size - 1; j >= 0; j--)
		{
			Explorable explorable = aexplorable[j];
			//�õ�ָ����Ψһ��ʶ:branchID
			String s = explorable.getUniqueIdentity();
			//���part
			explorable.getObject();
			myDetailHashTable.put(s, explorable);
			for (int l = 0; l < cols; l++)
			{
				String s1 = "";
				s1 = as[j][l];
				if(l == 0)
				{
					Image image = explorable.getStandardIcon();
					if(image != null)
					{
						addUniqueImageCell(j + begin, l, getAttrValue(
								explorable, headingMethod[l]), image, s);
					}
					else
					{
						addUniqueTextCell(j + begin, l, getAttrValue(
								explorable, headingMethod[l]), s);
					}
				}
				else if(l==cols-1){
					addTextCell(j + begin, l, s1);
				}
				else
				{
					//addTextCell(j + begin, l, s1);					
					addTextCell(j + begin, l,
							getAttrValue(explorable, headingMethod[l]));
				}
			}
		}
	}
	//CR1 End 20090623 zhangq

	private boolean getManagerDrage()
	{
		return explor.getManager().getDrage();
	}

	private Explorable myexplorable[];
	public Explorable[] getMyExplorable()
	{
		return myexplorable;
	}
	
	/**
	 * ����ָ����ϸ�ڵ�List��
	 * @param aexplorable[]:ϸ�ڶ���
	 * @return void:void��
	 */
	public void addDetail(Explorable aexplorable[])
	{
		myexplorable=aexplorable;
		int i = myDetailHashTable.size();
		for (int j = aexplorable.length - 1; j >= 0; j--)
		{
			Explorable explorable = aexplorable[j];
			//�õ�ָ����Ψһ��ʶ:branchID
			String s = explorable.getUniqueIdentity();
			//���part
			explorable.getObject();
			myDetailHashTable.put(s, explorable);
			int k = i + j;			
			for (int l = 0; l < getNumberOfCols() && l < headingMethod.length; l++)
			{
				if(l == 0)
				{
					Image image = explorable.getStandardIcon();
					if(image != null)
					{
						addUniqueImageCell(k, l, getAttrValue(explorable,
								headingMethod[l]), image, s);
					}
					else
					{						
						addUniqueTextCell(k, l, getAttrValue(explorable,
								headingMethod[l]), s);
					}
				}
				else if(l == (headingMethod.length - 1))
				{					
					if(explorable instanceof UsageMasterItem)
					{
						addTextCell(k, l, ((UsageMasterItem) explorable)
								.getPartUsageLink().getBsoID());
					}
					else if(explorable instanceof UsageItem)
					{						
					addTextCell(k, l, ((UsageItem) explorable)
							.getPartUsageLink().getBsoID());
					}					
				}
				else
				{
					//CCBegin SS4
					if(headingMethod[l].equals("getMakeRoute"))
					{
			        	Class[] paraclass ={String.class};
				        Object[] paraobj ={((UsageItem) explorable).getPartUsageLink().getLeftBsoID()};
				        Collection coll = null;
				        String routeStr=null;
			        	    try
			        	       {
			        	    	coll = (Collection)IBAUtility.invokeServiceMethod(
									        "BomNoticeService", "getRouteColl", paraclass, paraobj);
			        	        if(coll!=null && coll.size()>0)
			        	        {
			        	    	  for(Iterator ite = coll.iterator();ite.hasNext();)
			        	    	  {
			        	    		  TechnicsRouteBranchInfo partifc = (TechnicsRouteBranchInfo)ite.next();
			        	    		  routeStr=partifc.getRouteStr();
		        					  addTextCell(k, l,routeStr);
			        	    	  }
			        	        }
			        	       }
			        	        catch (QMRemoteException ex)
			        	        {
			        	            ex.printStackTrace();
			        	            return;
			        	        }			
					}
					else
					{
					//CCEnd SS4
						addTextCell(k, l,
								getAttrValue(explorable, headingMethod[l]));
					//CCBegin SS4	
					}
					//CCEnd SS4
				}
			}
		}
	}
	
		//CCBegin��SS3
    public void addTextCell(int r, int c, String s)
    {    	
        
         adjustRowsAndCols(r, c);
        if (s == null)
        {
            s = new String("");
        }
        CustomTableCellRenderer cell = new CustomTableCellRenderer(s);
        addCellImpl(r, c, cell);
        if (getColumnSizes() != null)
        {
            setColumnSizes(getColumnSizes());
        }
 	      this.getTable().setDefaultRenderer(com.faw_qm.clients.util.TextAndImageCell.class,cell);
      
    }
	
	class CustomTableCellRenderer extends com.faw_qm.clients.util.TextAndImageCell implements javax.swing.table.TableCellRenderer{
        public CustomTableCellRenderer(String str)
        {
        	super(str);            
        }

		public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			javax.swing.JLabel label = new javax.swing.JLabel();
			if (value!=null && value.toString().trim().length()>0){
				if (getHeading(column).equals(com.faw_qm.part.util.PartUsageList.toPartUsageList("proVersion").getDisplay()))
				{
						String partVersion=(String)partsByBaseLine.get(table.getValueAt(row,0).toString());
						if (partVersion!=null && !partVersion.equals(value.toString()))
						{
							label.setForeground(java.awt.Color.red);
						}
				}
			}
			if (label!=null && value!=null)
				label.setText(value.toString());
			return label;
		}
	}
		//CCEnd��SS3
//	-------------------------liyz add begin--------------------------------
		

	
	/**
     * ��ö��е����еĵ��÷���
     * @return String[]:���ط��������飨getxxx֮��ķ�����
     */
    public String[] getHeadingMethods()
    {
        return headingMethod;
    }

    /**
     * �ø����������ʼ��HeadingMethods
     * @param as[]:������ʼ��������
     * @return void:void��
     */
    public void setHeadingMethods(String as[])
            throws PropertyVetoException
    {
    	try
    	{
    		String[] headingMethodUsage = PartServiceRequest.getListMethod("usage");
            headingMethod=new String[headingMethodUsage.length+1];        
        for(int i=0;i<headingMethodUsage.length;i++)
        {
        	headingMethod[i]=headingMethodUsage[i]; 
        }
        headingMethod[headingMethodUsage.length]="";        
    	}
    	catch(QMException e)
    	{
    		e.printStackTrace();
    	}
    }

    
    /**
	 * ��ȡʹ�ýṹ�б����
	 * @param list
	 */
	public void setHeadings(String[] list)
	{	
		//CR2 Begin 20090623 zhangq �޸�ԭ�򣺸ı�ʹ�ýṹ������ʾ���ã����޸��Ӽ�ʹ���������ڱ���ʱ�����쳣��
		headings=list;
		//CR2 End 20090623
		for(int i=0;i<list.length;i++)
		{
			try
			{
				setHeading(list[i],i);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
	}
	
	 /**
     *�õ���ͷ��������ʽ
     * @return ��ͷ���ݵ�������ʽ     
     */
	public String[] getHeadings()
	{
		return headings;
	}	
	
	 /**
     * �õ���i�еı�ͷ����
     * @param i ������
     * @return �õ���i�еı�ͷ
     */
	public String getHeading(int i)
	{
		return table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString();
	}	
	
	/**
     * ���õ�i�еı�ͷ����
     * @param head ��ͷ����
     * @param i ������
     */
	public void setHeading(String head,int i)
	{
		//CR2 Begin 20090623 zhangq �޸�ԭ�򣺸ı�ʹ�ýṹ������ʾ���ã����޸��Ӽ�ʹ���������ڱ���ʱ�����쳣��
		headings[i]=head;
		//CR2 End 20090623 zhangq
		table.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(head);
		int size=table.getTableHeader().getColumnModel().getColumn(0).getWidth();
		if(i!=0)
		{
			table.getTableHeader().getColumnModel().getColumn(i).setMinWidth(size);
			table.getTableHeader().getColumnModel().getColumn(i).setPreferredWidth(size);
			table.getTableHeader().getColumnModel().getColumn(i).setWidth(size);
		}
		this.repaint();

	}	
	
}
