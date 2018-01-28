/**
 * ���ɳ��� RouteListPartLinkJPanel.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/5/31 ������   ԭ��:����������б��п�������ظ������,
 *                             ���ҵ�ɾ��һ��������������Ӵ˼�.
 *                        ����:ɾ���������Ѿ���ɾ�������.                                      
 * CR2  2009/06/08 ������  �μ�:������:v4r3FunctionTest;TD��2286
 * 
 * CR3  2011/07/15 ������  �μ���������:v4r3FunctionTest;TD��4733
 * CR4  2011/12/20 ����  ԭ��:������б������ӹ�����
 * CR5  2011/12/22 ���� ԭ���޸���������ʱ����б���ʾ
 * CR6  2012/01/04 ���� ԭ��ȥ��ԭ������������Ȱ�ť
 * CR7  2012/01/04 ���� ԭ�򣺸��ݲ�ͬģʽ��ʾ��ͬ�ı�ͷ�Ͳ�ͬ��������ʽ
 * CR8  2012/01/04 �촺Ӣ   ԭ�򣺻��Ҫ������·����Ϣ�����Լ�Ҫ���µ���׼�����������
 * CR9  2012/01/04 �촺Ӣ   ԭ�򣺲鿴�͸���ʱ������·����Ϣ
 * CR10 2012/01/04 �촺Ӣ  ԭ���޸�ɾ���������
 * CR11 2012/01/05 ����  ԭ���޸�ֻ���ڱ༭״̬���б���в��ܱ༭
 * CR12 2012/03/27 ���� ԭ�򣺲μ�TD5935
 * CR13 2012/03/29 ���� ԭ�򣺲μ�TD5959
 * CR14 2012/03/29 ����ԭ�򣺲μ�TD5980
 * CR15 2012/03/30 ����ԭ�򣺲μ�TD5988
 * CR16 2012/03/31 ����ԭ�򣺲μ�TD6001
 * CR17 2012/04/01 ����ԭ�򣺲μ�TD6010
 * CR18 2012/04/01 ����ԭ�򣺲μ�TD6007
 * CR19 2012/04/05 ����ԭ�򣺲μ�TD6009
 * CR20 2012/04/09 ����ԭ�򣺲μ�TD6019
 * CR21 2012/04/09 ����ԭ�򣺲μ�TD5996
 * 
 * SS1 �������汾����  guoxiaoliang  2013-03-04
 * SS2 2013-03-01 ������ ԭ��:�ձ�֪ͨ�鲻�ܶ��㲿���Ĺ���·�߽��и���
 * SS3 ��Ӽ����������� guoxiaoliang  2013-03-05
 * SS4 �����乤��·���ظ����ͬһ�����ͬ�汾 pante 2013-11-16
 * SS5 �༭����·�߽����㲿��������ʾ�İ汾ֵ�޸�Ϊ����Դ�汾  ����ͮ 2013-12-16
 * SS6 ���� ���ս����׼����㲿�� liunan 2013-12-20
 * SS7 �û�Ϊ�������ʱ��ʾ���ɹ���ʶ���ԡ� liuyang 2013-12-25
 * SS8 �ж������û����Ƿ�����������û��� liuyang 2013-12-25
 * SS9 ������������㲿���汾 liuyang 2014-2-24
 * SS10 �������ͬһ�汾�㲿�� liunan 2014-2-13
 * SS11 �޸� ���������㲿��ʱ�ᶪ�� �����⡣ liunan 2014-2-13
 * SS12 �����û����ܿ������ɹ���ʶ����ֻ����������ܹ��༭ 2014-4-11
 * SS13 �½�·���޷��鿴�㲿�� liunan 2014-5-13
 * SS14 ��������б�����ԭ���õġ���֯��������Ϊ����֯����-bsx����������༭·��ʱ��ʾ����֯����-bsx�� liunan 2014-6-17
 * SS15 ������鿴·��ʱ���汾ҲҪ��ʾ��������İ汾�� liunan 2014-9-12
 * SS16 ��ݹ���·����������·���޽������ pante 2014-09-04
 * SS17 �������鿴����·���п������ձ�·�� pante 2014-10-09
 * SS18 ·�߱����и��ı�Ǻ�ë��״̬��ʾ���� pante 2014-10-09
 * SS19 ����������ӱ༭��Ӧ�̰�ť��һ��·�� liuyang 2014-8-18
 * SS20 �����㲿�������ӹ�Ӧ���� liuyang 2014-8-18
 * SS21 ·���뱾����س��ض���·�����ӳ���һ������·�ߺ�װ��·���� liuyang 2014-8-26
 * SS22 �����㲿���б��ʼ�����ж� liuyang 2014-8-27
 * SS23 ���ع���·�ߵ�λ���ж� liuyang 2014-8-28
 * SS24 ����ȥ���������ҹ�Ӧ�̹���ť���˵�  ���� 2014-12-9
 * SS25 ����·�߶���Ӱ�������·�߱���  ���� 2014-12-10
 * SS26 ������������������ܲ���ʹ ���� 2014-12-16
 * SS27 ֻ�г��ز���Ҫ��ʾ��Ӧ������ liunan 2015-4-29
 * SS28 ����һ��·�ߣ���ʾ����汾������  ���� 2015-3-9
 * SS29 ��������㲿��������������״̬�ж� ����SHIZHI ������SHEJIWANCHENG ����MANUFACTURING ����׼��PREPARING �ѷ���RELEASED �ſ������ liuzhicheng 2015-07-14
 * SS30 A005-2014-2957 ����������������㲿�����������°�·�ߣ���ȥ����·�ߣ�û�еĻ�ȡ���һ��·�ߡ� liunan 2015-7-16
 * SS31 �ɶ�����·�����ϣ�����ѡ����ı�ǡ� liunan 2016-8-4
 * SS32 �ɶ�����·�����ϣ���ʾ���·�ߣ�����ʾ�ɹ���ʶ�� liunan 2016-8-4
 * SS33 ����װ��λ�ó����㲿�������쳣���µĶ��Ǽ�¼��QMPart���ϰ���QMPpartMaster�� liunan 2016-8-26
 * SS34 �ɶ�·�ߣ�ʹ�ô�������С� liunan 2016-8-26
 * SS35 A032-2016-0067 �ڳɶ�����·���в�Ӧ����ʾ���ص���Ϣ�� liunan 2016-9-1
 * SS36 A032-2016-0071 ����·�߸���ճ�����ܲ���ʹ liunan 2016-9-2
 * SS37 �ɶ�����·�ߺϲ��� liunan 2016-10-17
 * SS38 �ɶ�Ҫ��·��ֻ��������ɣ����ơ�����׼����������������״̬���㲿���� liunan 2016-10-24
 * SS39 �ɶ�Ҫ������㲿��ʱ����ʾ�ѷ�����·�ߡ� liunan 2016-10-24
 * SS40 �ɶ�������ɫ����ʶ���ԡ� liunan 2016-10-25
 * SS41 A004-2017-3476 ����һ������·�ߵĸ��ı��Ĭ�ϴӡ����á���Ϊ�������� liunan 2017-3-20
 * SS42 A004-2017-3485 ����������㲿�����汾��ȡ������Դ�汾������ȡiba���Եġ������汾����û����Ϊ�ա����ֶ��༭�� liunan 2017-4-11
 * SS43 Ϊ�ɶ�����㲿�����⴦���ʶ����ҪΪ��ʵ���ް汾�㲿����汾��ת�����㲿��������·��  ����� 2018-01-08
 * SS44 �ɶ�Ҫ��·��ֻ���ǰ׼״̬���㲿��������� 2018-01-09
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.Cursor;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.faw_qm.cappclients.conscapproute.graph.DefaultGraphNode;
import com.faw_qm.cappclients.conscapproute.graph.RouteGraphEditJDialog;
import com.faw_qm.cappclients.conscapproute.graph.RouteGraphViewJDialog;
import com.faw_qm.cappclients.conscapproute.graph.RouteItem;
import com.faw_qm.cappclients.conscapproute.util.ActionAdapter;
import com.faw_qm.cappclients.conscapproute.util.ButtonCtrlHotKey;
import com.faw_qm.cappclients.conscapproute.util.CappRouteRB;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.clients.beans.explorer.QMToolBar;
import com.faw_qm.clients.beans.query.QMQueryEvent;
import com.faw_qm.clients.beans.query.QMQueryListener;
import com.faw_qm.clients.beans.query.QmChooser;
import com.faw_qm.clients.beans.query.QmQuery;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshListener;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.codemanage.client.view.CodeManageTree;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.ModelRouteInfo;
import com.faw_qm.technics.consroute.model.RouteBranchNodeLinkIfc;
import com.faw_qm.technics.consroute.model.RouteBranchNodeLinkInfo;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.RouteNodeLinkIfc;
import com.faw_qm.technics.consroute.model.RouteNodeLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.util.RouteCategoryType;
import com.faw_qm.technics.consroute.util.RouteListLevelType;
import com.faw_qm.technics.consroute.util.RouteWrapData;
import com.faw_qm.util.QMCt;
import com.faw_qm.version.model.MasteredIfc;
//CCBegin SS29
import com.faw_qm.users.model.UserIfc;
//CCEnd SS29

/**
 * ����·�߱��Ӧ���㲿����ı༭���档 �ڸ��¹���·�߱�ʱ���༭Ҫ���ƹ���·�ߵ��㲿������������㲿����ɾ���㲿���� Copyright:
 * Copyright (c) 2004 Company: һ������
 * 
 * @author ����
 * @mender skybird
 * @version 1.0
 */
public class RouteListPartLinkJPanel extends RParentJPanel implements
		RefreshListener, ActionListener, KeyListener, MouseListener {
	/** ѡ������㲿���Ľ��� */
	public SelectPartJDialog selectPartDialog;
	// CR5 begin
	/** �㲿���б� */
	public ComponentMultiList qMMultiList = new ComponentMultiList();

	// CR5 end
	private JButton addStructJButton = new JButton();

	private JButton addJButton = new JButton();
	// CCBegin SS3
	private JButton countButton = new JButton();
	// CCEnd SS3

	// ��¼ѡ�е��б��к�
	private int theSelectedNum;

	/**
	 * ҵ�����:·�߱�
	 */
	public TechnicsRouteListIfc routeListInfo;

	/** ������Ա��� */
	private static boolean verbose = (RemoteProperty.getProperty(
			"com.faw_qm.cappclients.verbose", "true")).equals("true");

	// ������״̬partRelative��·���뱾����أ�productRelative��·�����Ʒ���,
	// parentRelative��·����ṹ��أ�productAndparentRelative��·�����Ʒ�ͽṹ���
	// CR5 begin
	private static final String routeMode = RemoteProperty.getProperty(
			"routeManagerMode", "partRelative");
	// CR5 end
	private static final String modelRoutedomain = RemoteProperty
			.getProperty("modelRoutedomain");
	/** ����:�����г��ֵ������㲿�� */
	private HashMap allParts = new HashMap();

	// ����·�߱�ԭ�����������㲿����ɾ������ʣ���㲿������Ҫ���ĸ�����ŵļ��ϲ���
	private Vector partsToChange = new Vector();

	/** �洢ɾ�����㲿������Ϣ */
	private HashMap deleteParts = new HashMap();

	/** �洢����ӵ��㲿������Ϣ */
	private HashMap addedParts = new HashMap();

	// private boolean isChangeRouteList = false;

	private GridBagLayout gridBagLayout1 = new GridBagLayout();

	private GridBagLayout gridBagLayout2 = new GridBagLayout();

	private GridBagLayout gridBagLayout3 = new GridBagLayout();

	private QMPartMasterIfc theProductifc = null;
	// CR4 begin
	/** ���ڱ����Դ�ļ�·�� */
	protected static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";
	/** ���ڱ����Դ */
	protected static ResourceBundle resource = null;
	/** ������ */
	private QMToolBar qmToolBar = new QMToolBar();
	// CR4 end
	// 20120106 xucy add
	/** �༭·��ͼ������� */
	private RouteGraphEditJDialog editDialog;

	/** �鿴·��ͼ������� */
	private RouteGraphViewJDialog viewDialog;

	// begin 20120106 XUCY ADD
	/** ������ʾģʽ������ģʽ����� */
	public final static int UPDATE_MODE = 0;

	/** ������ʾģʽ������ģʽ����� */
	public final static int CREATE_MODE = 1;

	/** ������ʾģʽ���鿴ģʽ����� */
	public final static int VIEW_MODE = 2;

	/** ����ģʽ--�鿴 */
	private int mode = VIEW_MODE;

	/** ·�ߵ�λ�����������Ĳ��֣� */
	private CodeManageTree departmentTree;
	/** ����:·�߷�֧ key=·�߷�ֵ֧����, value= ���ϣ��÷�֧�е�����·�߽ڵ㣩 */
	private Hashtable branchHashtable = new Hashtable();
	/** ����:·�߷�֧ key=·�߷�֧��BsoID, value=·�߷�ֵ֧���� */
	private HashMap branchMap = new HashMap();
	// end 20120106 xucy add
	// 20120113 xucy add
	private String routeLevel;
	private RouteListTaskJPanel listPanel;

	// 20120116 xucy add Ҫ�洢����Ϣ����
	private HashMap saveMap = new HashMap();

	// 20120118 xucy add �洢��ǰ�û��Ŀ��·����Ϣ
	// private HashMap shortCutRoute = null;
	// �洢���ı��
	private Vector marks = null;
	// �洢ͨ����ͼ�γɵ�����
	private HashMap multiPartPicMaps = new HashMap();
	/** ����:·�߷�֧��ڵ�Ĺ����ļ���(Ԫ��ΪRouteBranchNodeLinkInfo) */
	public Vector branchNodeLinkVector = new Vector();
	// 20120119 xucy
	private HashMap routeNodeItemMap = new HashMap();
	private HashMap routeLinkItemMap = new HashMap();

	private HashMap routeStrMap = new HashMap();
   //CCBegin SS7
    private Vector stockID=new Vector();
    //CCEnd SS7
    //CCBegin SS20
    private String supplier="";
    private String supplierBsoId="";
    //CCEnd SS20
    //CCBegin SS22
    private String ctroute1="";
    private String ctroute2="";
    //CCEnd SS22
    //CCBegin SS8
    private String comp="";
    //CCEnd SS8
  
  //CCBegin SS31
	private JButton multiChangeMarkButton = new JButton();
  //CCEnd SS31
  
	/**
	 * ���캯��
	 */
	public RouteListPartLinkJPanel() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		RefreshService.getRefreshService().addRefreshListener(this);
	}

	/**
	 * ���캯�� 20120113 xucy add
	 */
	public RouteListPartLinkJPanel(RouteListTaskJPanel listPanel) {
		this.listPanel = listPanel;
		try {
		 	//CCBegin SS8
        	RequestServer server = RequestServerFactory.getRequestServer();
        	StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
        	//CCBegin SS32
        	//info1.setClassName("com.faw_qm.cappclients.conscapproute.util.RouteClientUtil");
        	info1.setClassName("com.faw_qm.doc.util.DocServiceHelper");
        	//CCEnd SS32
            info1.setMethodName("getUserFromCompany");
            Class[] classes = {};
            info1.setParaClasses(classes);
            Object[] objs = {};
            info1.setParaValues(objs);
            comp=(String)server.request(info1);
            //System.out.println("comp init====="+comp);
            //CCEnd SS8
			jbInit();  
		} catch (Exception e) {
			e.printStackTrace();    
		}

		RefreshService.getRefreshService().addRefreshListener(this);
	}

	private Vector masterToPart(Vector vect) {
		Vector parts = new Vector();
		for (int i = 0; i < vect.size(); i++) {
			// CCBegin SS4
			// QMPartMasterIfc subMaster = (QMPartMasterIfc)vect.elementAt(i);
			QMPartIfc subMaster = (QMPartIfc) vect.elementAt(i);
			// parts.add(filterPart(subMaster.getBsoID()));
			parts.add(subMaster);
			// CCEnd SS4
		}
		return parts;
	}

	/**
	 * ���¶���
	 * 
	 * @param re
	 *            �����¼�
	 */
	public void refreshObject(RefreshEvent re) {
		if (re.getSource().equals("addSelectedParts")) {
			Object obj = re.getTarget();
			if (obj instanceof Vector) {
				Vector vect = (Vector) obj;
				Vector parts = new Vector();

				parts = masterToPart(vect);
				// CR5 begin
				// ������״̬partRelative��·���뱾����أ�productRelative��·�����Ʒ���,
				// parentRelative��·����ṹ��أ�productAndparentRelative��·�����Ʒ�ͽṹ���
				if (routeMode.equals("productRelative")) {
					try {
						productRelative(vect, parts);
					} catch (QMException e) {
						e.printStackTrace();
						return;
					}
				} else if (routeMode.equals("productAndparentRelative")) {
					try {
						productAndparentRelative(vect, parts);
					} catch (QMException e) {
						e.printStackTrace();
						return;
					}
				} else if (routeMode.equals("parentRelative")) {
					try {
						parentRelative(vect);
					} catch (QMException e) {
						e.printStackTrace();
						return;
					}
				} else if (routeMode.equals("partRelative")) {
					try {
						partRelative(vect);
					} catch (QMException e) {
						e.printStackTrace();
						return;
					}
				}
				// CR5 end
			}
		}else if (re.getSource().equals("addSelectedPartsForCD")) {
			Object obj = re.getTarget();
			if (obj instanceof HashMap){
				try{
				partRelativeForCD((HashMap)obj);
				}catch(QMException exc){
					exc.printStackTrace();
					return;
				}
			}
		}
	}

	// CR5 begin
	/**
	 * ·�����Ʒ�й�
	 * 
	 * @throws QMException
	 */
	private void productRelative(Vector vect, Vector parts) throws QMException {
		QMPartMasterIfc subMaster = null;
		String subID = null;
		HashMap map = null;
		ImageIcon image;
		try {
			// 20120116 xucy
			// if(this.theProductifc == null)
			// {
			// theProductifc =
			// (QMPartMasterIfc)refreshInfo(routeListInfo.getProductMasterID());
			// }
			map = this.calCountInProduct(parts, theProductifc);
		} catch (Exception ex1) {
			String message = ex1.getMessage();
			DialogFactory.showInformDialog(this.getParentJFrame(), message);
			return;
		}
		for (int i = 0; i < vect.size(); i++) {
			subMaster = (QMPartMasterIfc) vect.elementAt(i);
			subID = subMaster.getBsoID();
			boolean flag = this.getStatus(subID);
			if (!allParts.containsKey(subID + theProductifc.getPartNumber())) {
				String partNumber = subMaster.getPartNumber();
				String partName = subMaster.getPartName();
				int count = ((Integer) map.get(((QMPartIfc) parts.elementAt(i))
						.getBsoID())).intValue();
				String[] listInfo = { subID, partNumber, partName };
				// Vector marks = getMarks();
				int row = qMMultiList.getNumberOfRows();
				if (flag) {// �Ѵ��ڷ���·��
					image = new ImageIcon(getClass().getResource(
							"/images/route_partUsedPlace.gif"));
					qMMultiList.setComboBoxSelected(row, 1, marks.elementAt(1),
							marks);
				} else {// ��·��
					image = new ImageIcon(getClass().getResource(
							"/images/route_emptyRoute.gif"));
					qMMultiList.setComboBoxSelected(row, 1, marks.elementAt(0),
							marks);
				}
				qMMultiList.addTextCell(row, 0, listInfo[0]);

				qMMultiList.addCell(row, 2, listInfo[1], image.getImage());
				qMMultiList.addTextCell(row, 3, listInfo[2]);
				qMMultiList.addTextCell(row, 4, "");
				qMMultiList.addTextCell(row, 5, "");
				qMMultiList.addTextCell(row, 6, "");
				qMMultiList.addTextCell(row, 7, String.valueOf(count));
				qMMultiList.setCheckboxSelected(row, 8, true);
				qMMultiList.addTextCell(row, 9, "");
				qMMultiList.addTextCell(row, 10, "");
				// CCBegin SS1
				String pv = this.getLastPartVersionID(subMaster);
				qMMultiList.addTextCell(row, 11, pv);
				// CCEnd SS1
				        //CCBegin SS7
//                if(comp.equals("zczx")){
                	 qMMultiList.setComboBoxSelected(row, 12, stockID.elementAt(0),stockID);
//                }
                //CCEnd SS7
                //CCBegin SS20
                if(comp.equals("ct")){
              
                	 qMMultiList.addTextCell(row, 13, "");
                	 qMMultiList.addTextCell(row, 14, "");
                	
                }
                //CCEnd SS20
				// RouteWrapData routeData = new RouteWrapData();
				// //���ñ����Ͳ�Ʒ��Ϣ
				// routeData.setPartMasterID(subID);
				// routeData.setProduct(theProductifc);
				// routeData.setProductCount(count);
				// saveMap.put(subID + theProductifc.getPartNumber(),
				// routeData);
				// addedParts.put(subID + theProductifc.getPartNumber(), new
				// Object[]{subID, theProductifc, new Integer(count)});
				allParts.put(subID + theProductifc.getPartNumber(),
						new Object[] { subID, theProductifc });
			}
		}
	}

	/**
	 * ·�����Ʒ�ͽṹ�й�
	 * 
	 * @param obj
	 * @param vect
	 * @param parts
	 * @throws QMException
	 */
	private void productAndparentRelative(Vector vect, Vector parts)
			throws QMException {
		QMPartMasterIfc subMaster = null;
		String subID = null;
		HashMap map = null;
		ImageIcon image;
		try {
			map = this.calCountInProduct(parts, theProductifc);
		} catch (Exception ex1) {
			String message = ex1.getMessage();
			DialogFactory.showInformDialog(this.getParentJFrame(), message);
			return;
		}
		for (int i = 0; i < vect.size(); i++) {
			subMaster = (QMPartMasterIfc) vect.elementAt(i);
			subID = subMaster.getBsoID();
			boolean flag = this.getStatus(subID);
			if (!allParts.containsKey(subID + theProductifc.getPartNumber())) {
				String partNumber = subMaster.getPartNumber();
				String partName = subMaster.getPartName();
				int count = ((Integer) map.get(((QMPartIfc) parts.elementAt(i))
						.getBsoID())).intValue();
				String[] listInfo = { subID, partNumber, partName };
				// Vector marks = getMarks();
				int row = qMMultiList.getNumberOfRows();
				if (flag) {// �Ѵ��ڷ���·��
					image = new ImageIcon(getClass().getResource(
							"/images/route_partUsedPlace.gif"));
					qMMultiList.setComboBoxSelected(row, 1, marks.elementAt(1),
							marks);
				} else {// ��·��
					image = new ImageIcon(getClass().getResource(
							"/images/route_emptyRoute.gif"));
					qMMultiList.setComboBoxSelected(row, 1, marks.elementAt(0),
							marks);
				}
				qMMultiList.addTextCell(row, 0, listInfo[0]);
				qMMultiList.addCell(row, 2, listInfo[1], image.getImage());
				qMMultiList.addTextCell(row, 3, listInfo[2]);
				qMMultiList.addTextCell(row, 4, "");
				qMMultiList.addTextCell(row, 5, "");
				qMMultiList.addTextCell(row, 6, "");
				qMMultiList.addTextCell(row, 7, "");
				qMMultiList.addTextCell(row, 8, "");
				qMMultiList.addTextCell(row, 9, String.valueOf(count));
				qMMultiList.setCheckboxSelected(row, 10, true);
				qMMultiList.addTextCell(row, 11, "");
				qMMultiList.addTextCell(row, 12, "");
				// CCBegin SS1
				String pv = this.getLastPartVersionID(subMaster);
				qMMultiList.addTextCell(row, 13, pv);
				//CCBegin SS6
//                if(comp.equals("zczx")){
//                     	
                	 qMMultiList.setComboBoxSelected(row, 14, stockID.elementAt(0),stockID);
//                }
        //CCEnd SS7
                	 
				// CCEnd SS1
                //CCBegin SS20
                if(comp.equals("ct")){
                	qMMultiList.addTextCell(row, 15, "");
                	qMMultiList.addTextCell(row, 16, "");
                }
                //CCEnd SS20
				// 20120405
				// RouteWrapData routeData = new RouteWrapData();
				// //���ñ����͸�����Ϣ
				// routeData.setPartMasterID(subID);
				// routeData.setProduct(theProductifc);
				// routeData.setProductCount(count);
				// routeData.setParent(null);
				// saveMap.put(subID + theProductifc.getPartNumber(),
				// routeData);
				// addedParts.put(subID + theProductifc.getPartNumber(), new
				// Object[]{subID, theProductifc, new Integer(count), null});
				allParts.put(subID + theProductifc.getPartNumber(),
						new Object[] { subID, theProductifc, null });

			}
		}
	}

	/**
	 * ·����ṹ�й�
	 * 
	 * @param vect
	 * @throws QMException
	 */
	private void parentRelative(Vector vect) throws QMException {
		QMPartMasterIfc subMaster = null;
		String subID = null;
		ImageIcon image;
		for (int i = 0; i < vect.size(); i++) {
			subMaster = (QMPartMasterIfc) vect.elementAt(i);
			subID = subMaster.getBsoID();
			// ����㲿��·��״̬
			boolean flag = this.getStatus(subID);
			if (!allParts.containsKey(subID)) {
				String partNumber = subMaster.getPartNumber();
				String partName = subMaster.getPartName();
				String[] listInfo = { subID, partNumber, partName };
				// ��ø��ı��
				// Vector marks = getMarks();
				int row = qMMultiList.getNumberOfRows();
				if (flag) {// �Ѵ��ڷ���·��
					image = new ImageIcon(getClass().getResource(
							"/images/route_partUsedPlace.gif"));
					qMMultiList.setComboBoxSelected(row, 1, marks.elementAt(1),
							marks);
				} else {// ��·��
					image = new ImageIcon(getClass().getResource(
							"/images/route_emptyRoute.gif"));
					qMMultiList.setComboBoxSelected(row, 1, marks.elementAt(0),
							marks);
				}
				qMMultiList.addTextCell(row, 0, listInfo[0]);
				qMMultiList.addCell(row, 2, listInfo[1], image.getImage());
				qMMultiList.addTextCell(row, 3, listInfo[2]);
				qMMultiList.addTextCell(row, 4, "");
				qMMultiList.addTextCell(row, 5, "");
				qMMultiList.addTextCell(row, 6, "");
				qMMultiList.addTextCell(row, 7, "");
				qMMultiList.addTextCell(row, 8, "");
				qMMultiList.addTextCell(row, 9, "");
				qMMultiList.addTextCell(row, 10, "");
				qMMultiList.addTextCell(row, 11, "");
				
				// CCBegin SS1
				
				String pv = this.getLastPartVersionID(subMaster);
				qMMultiList.addTextCell(row, 12, pv);
				// CCEnd SS1
				      //CCBegin SS7
//                if(comp.equals("zczx")){

                	 qMMultiList.setComboBoxSelected(row, 13, stockID.elementAt(0),stockID);
//                }
                //CCEnd SS7
                //CCBegin 
                     if(comp.equals("ct")){
                     	qMMultiList.addTextCell(row, 14, "");
                     	qMMultiList.addTextCell(row, 15, "");
                     }
                     //CCEnd SS20
        
				// 20120405
				// RouteWrapData routeData = new RouteWrapData();
				// //���ñ����͸�����Ϣ
				// routeData.setPartMasterID(subID);
				// routeData.setParent(null);
				// saveMap.put(subID, routeData);
				allParts.put(subID, new Object[] { subID, null });
			}
		}
	}

	/**
	 * ·�߽��뱾���й�
	 * 
	 * @param vect
	 * @throws QMException
	 */
	private void partRelative(Vector vect) throws QMException {
		//System.out.println("partRelative!!!!  vect===="+vect+"and comp="+comp);
		// CCBegin SS4
		// QMPartMasterIfc subMaster = null;
		QMPartIfc subMaster = null;
		// CCEnd SS4
		String subID = null;
		ImageIcon image;
		for (int i = 0; i < vect.size(); i++) {
			// CCBegin SS4 SS29
			// subMaster = (QMPartMasterIfc)vect.elementAt(i);
			subMaster = (QMPartIfc) vect.elementAt(i);
			//��������㲿��������������״̬�ж� ����SHIZHI ������SHEJIWANCHENG ����MANUFACTURING ����׼��PREPARING �ѷ���RELEASED
		  UserIfc user = (UserIfc)RequestHelper.request("SessionService", "getCurUserInfo", null, null);
			if(user.getUsersName().startsWith("ct")&&comp.equals("ct"))
            {
            	if(!(subMaster.getLifeCycleState().getDisplay().equals("����׼��")
            			||subMaster.getLifeCycleState().getDisplay().equals("������")
            			||subMaster.getLifeCycleState().getDisplay().equals("����")  
            			||subMaster.getLifeCycleState().getDisplay().equals("����")
            			||subMaster.getLifeCycleState().getDisplay().equals("�ѷ���")))  
            	{
            		continue;
            	}
            }

			// CCEnd SS4 SS29 
			
			//CCBegin SS38
			if(comp.equals("cd"))
			{
				if(!(subMaster.getLifeCycleState().getDisplay().equals("������")
				   ||subMaster.getLifeCycleState().getDisplay().equals("����")
				   ||subMaster.getLifeCycleState().getDisplay().equals("����׼��")
				   ||subMaster.getLifeCycleState().getDisplay().equals("����")
//				   CCBegin SS44
				   ||subMaster.getLifeCycleState().getDisplay().equals("ǰ׼")))
//					CCEnd SS44
				{
					continue;
				}
			}
			//CCEnd SS38
			
			subID = subMaster.getBsoID();
			// ����㲿��·��״̬
			boolean flag = this.getStatus(subID);
			//CCBegin SS10
			//if (!allParts.containsKey(subID)) {
			{
			//CCEnd SS10
				String partNumber = subMaster.getPartNumber();
				String partName = subMaster.getPartName();
				String[] listInfo = { subID, partNumber, partName };
				// ��ø��ı��
				// Vector marks = getMarks();
				// System.out.println("111111111============="+marks.size());
				int row = qMMultiList.getNumberOfRows();
				// System.out.println("route-------------"+partNumber+"            "+partName);
				qMMultiList.addTextCell(row, 0, listInfo[0]);
				if (flag) {// �Ѵ��ڷ���·��
					image = new ImageIcon(getClass().getResource(
							"/images/route_partUsedPlace.gif"));
					qMMultiList.setComboBoxSelected(row, 1, marks.elementAt(1),
							marks);
				} else {// ��·��
					image = new ImageIcon(getClass().getResource(
							"/images/route_emptyRoute.gif"));
					// System.out.println("error---------"+row);
					// System.out.println("error---------"+marks.elementAt(0));
					// System.out.println("error---------"+marks);
					//CCBegin SS41
					//qMMultiList.setComboBoxSelected(row, 1, marks.elementAt(0),
							//marks);
					if(listPanel.levelJComboBox.getSelectedItem().equals("һ��·��")&&comp.equals("ct"))
					{
						qMMultiList.setComboBoxSelected(row, 1, marks.elementAt(1),
							marks);
					}
					else
					{
						qMMultiList.setComboBoxSelected(row, 1, marks.elementAt(0),
							marks);
					}
					//CCEnd SS41
				}
				qMMultiList.addCell(row, 2, listInfo[1], image.getImage());
				qMMultiList.addTextCell(row, 3, listInfo[2]);
				//CCBegin SS21
//				System.out.println("comp11="+comp);
				if(comp.equals("ct")){
					int n=listPanel.getViewMode();
					if(n==1){
						if(listPanel.levelJComboBox.getSelectedItem().equals("����·��")){
							Class[] c1 = {String.class};
				            Object[] obj1 = {subID};
				            String[] route = (String[])RequestHelper.request("consTechnicsRouteService", "findQMPartRouteLevelOne", c1, obj1);
				            qMMultiList.addTextCell(row, 4, "");
							qMMultiList.addTextCell(row, 5,"");
							qMMultiList.addTextCell(row, 6, "");
							qMMultiList.addTextCell(row, 7, "");
							qMMultiList.addTextCell(row, 8, "");
							qMMultiList.addTextCell(row, 9, route[0]);
							qMMultiList.addTextCell(row, 10, route[1]);
							qMMultiList
									.addTextCell(row, 11, subMaster.getVersionValue());
							qMMultiList.addTextCell(row, 12, "");
							qMMultiList.setComboBoxSelected(row, 13, stockID
									.elementAt(0), stockID);
				
						    qMMultiList.addTextCell(row, 14, "");
							qMMultiList.addTextCell(row, 15, "");

							allParts.put(subID, new Object[] { subID });
						}else{
							qMMultiList.addTextCell(row, 4, "");
							qMMultiList.addTextCell(row, 5, "");
							qMMultiList.addTextCell(row, 6, "");
							qMMultiList.addTextCell(row, 7, "");
							qMMultiList.addTextCell(row, 8, "");
							qMMultiList.addTextCell(row, 9,"");
							qMMultiList.addTextCell(row, 10, "");
							// CCBegin SS1
							qMMultiList
									.addTextCell(row, 11, subMaster.getVersionValue());
							// CCEnd SS1
							qMMultiList.addTextCell(row, 12, "");
							// CCBegin SS7
							// if(comp.equals("zczx")){
							qMMultiList.setComboBoxSelected(row, 13, stockID
									.elementAt(0), stockID);
							// }
							// CCEnd SS7


					
								qMMultiList.addTextCell(row, 14, "");
								qMMultiList.addTextCell(row, 15, "");
			
							// 20120405
							// //���ñ�����Ϣ
							// RouteWrapData routeDate = new RouteWrapData();
							// routeDate.setPartMasterID(subID);
							// saveMap.put(subID, routeDate);
							allParts.put(subID, new Object[] { subID });

						}
					}else{
						if(routeListInfo.getRouteListLevel().equals("����·��")){
							Class[] c1 = {String.class};
				            Object[] obj1 = {subID};
				            String[] route = (String[])RequestHelper.request("consTechnicsRouteService", "findQMPartRouteLevelOne", c1, obj1);
							qMMultiList.addTextCell(row, 4, route[0]);
							qMMultiList.addTextCell(row, 5, route[1]);
							qMMultiList.addTextCell(row, 6, "");
							qMMultiList.addTextCell(row, 7, "");
							qMMultiList.addTextCell(row, 8, "");
							qMMultiList
									.addTextCell(row, 11, subMaster.getVersionValue());
							qMMultiList.addTextCell(row, 12, "");
							qMMultiList.setComboBoxSelected(row, 13, stockID
									.elementAt(0), stockID);
				
						    qMMultiList.addTextCell(row, 14, "");
							qMMultiList.addTextCell(row, 15, "");

							allParts.put(subID, new Object[] { subID });
						}else{
							qMMultiList.addTextCell(row, 4, "");
							qMMultiList.addTextCell(row, 5, "");
							qMMultiList.addTextCell(row, 6, "");
							qMMultiList.addTextCell(row, 7, "");
							qMMultiList.addTextCell(row, 8, "");
							// CCBegin SS1
							qMMultiList
									.addTextCell(row, 11, subMaster.getVersionValue());
							// CCEnd SS1
							qMMultiList.addTextCell(row, 12, "");
							// CCBegin SS7
							// if(comp.equals("zczx")){
							qMMultiList.setComboBoxSelected(row, 13, stockID
									.elementAt(0), stockID);
							// }
							// CCEnd SS7

				

					
								qMMultiList.addTextCell(row, 14, "");
								qMMultiList.addTextCell(row, 15, "");
			
							// 20120405
							// //���ñ�����Ϣ
							// RouteWrapData routeDate = new RouteWrapData();
							// routeDate.setPartMasterID(subID);
							// saveMap.put(subID, routeDate);
							allParts.put(subID, new Object[] { subID });
						}
					}
	//CCBegin SS25
				}else{
					//CCBegin SS30
					if(comp.equals("bsx"))
					{
						System.out.println("����bsx");
						Class[] classes = {String.class };
						Object[] objs = {subID};
						String[] str = (String[])RequestHelper.request("consTechnicsRouteService", "getLaterRouteByPartID", classes, objs);
						//System.out.println("str=========="+str);
						qMMultiList.addTextCell(row, 4, str[0]);
						qMMultiList.addTextCell(row, 5, str[1]);
					}
					//CCBegin SS39
					else if(comp.equals("cd"))
					{
						Class[] c1 = {String.class};
						Object[] obj1 = {subID};
						Collection cols = (Collection)RequestHelper.request("consTechnicsRouteService", "findPartEffRoute", c1, obj1);
						if(cols != null && cols.size() > 0)
						{
							Iterator ii = cols.iterator();
							if(ii.hasNext())
							{
								Object[] objss = (Object[])ii.next();
								ListRoutePartLinkInfo info = (ListRoutePartLinkInfo)objss[0];
								//System.out.println("info==="+info);
								//System.out.println("main==="+info.getMainStr());
								//System.out.println("second==="+info.getSecondStr());
								if(info.getMainStr()!=null)
								{
									qMMultiList.addTextCell(row, 4, info.getMainStr());
								}
								else
								{
									qMMultiList.addTextCell(row, 4, "");
								}
								if(info.getSecondStr()!=null)
								{
									qMMultiList.addTextCell(row, 5, info.getSecondStr());
								}
								else
								{
									qMMultiList.addTextCell(row, 5, "");
								}
							}
						}
						else
						{
							qMMultiList.addTextCell(row, 4, "");
							qMMultiList.addTextCell(row, 5, "");
						}
					}
					//CCEnd SS39
					else
					{
						qMMultiList.addTextCell(row, 4, "");
						qMMultiList.addTextCell(row, 5, "");
					}
					//CCEnd SS30
					qMMultiList.addTextCell(row, 6, "");
					qMMultiList.addTextCell(row, 7, "");
					qMMultiList.addTextCell(row, 8, "");
					//CCBegin SS42
					//qMMultiList.addTextCell(row, 9, subMaster.getVersionValue());
					if(comp.equals("bsx"))
					{
						//System.out.println("����bsx");
						Class[] classes = {QMPartInfo.class };
						Object[] objs = {subMaster};
						String str = (String)RequestHelper.request("consTechnicsRouteService", "getSourceVersion", classes, objs);
						qMMultiList.addTextCell(row, 9, str);
					}
					else
					{
						qMMultiList.addTextCell(row, 9, subMaster.getVersionValue());
					}
					//CCEnd SS42
					qMMultiList.addTextCell(row, 10, "");
					//CCBegin SS32
					if(comp.equals("cd"))
					{
						qMMultiList.addTextCell(row, 11, getJFRouteStr(subMaster.getMasterBsoID()));
						//CCBegin SS40
						qMMultiList.setCheckboxSelected(row, 12, false);
						qMMultiList.setCheckboxEditable(true);
						//CCEnd SS40
						//CCBegin SS43
						qMMultiList.setCheckboxSelected(row, 13, false);
						qMMultiList.setCheckboxEditable(true);
						//						qMMultiList.set
						qMMultiList.addTextCell(row, 14, getSourceVersion((QMPartInfo)subMaster));
						//CCEnd SS43
					}
					else
						//CCEnd SS32
						qMMultiList.setComboBoxSelected(row, 11, stockID.elementAt(0),stockID);
					//	                }
					allParts.put(subID, new Object[] { subID });
					//CCEnd SS25
				}
			}
		}
	}
	
	
	/**
	 * Ϊ�ɶ��㲿���б�����㲿��
	 * @param vect
	 * @throws QMException
	 */
	private void partRelativeForCD(HashMap map) throws QMException {
//		System.out.println("Enter the method : partRelativeForCD!");
		QMPartInfo subInfo = null;
		String subID = null;
		ImageIcon image;
		Iterator iter = map.keySet().iterator();
//		for (int i = 0; i < map.size(); i++) 
		while(iter.hasNext())
		{
			HashMap velueMap = (HashMap)map.get(iter.next());
			subInfo = (QMPartInfo) velueMap.get("info");
			if(!(subInfo.getLifeCycleState().getDisplay().equals("������")
					||subInfo.getLifeCycleState().getDisplay().equals("����")
					||subInfo.getLifeCycleState().getDisplay().equals("����׼��")
					||subInfo.getLifeCycleState().getDisplay().equals("����")
					||subInfo.getLifeCycleState().getDisplay().equals("ǰ׼")))
			{
				continue;
			}
			subID = subInfo.getBsoID();
			// ����㲿��·��״̬
			boolean flag = this.getStatus(subID);
			String partNumber = subInfo.getPartNumber();
			String partName = subInfo.getPartName();
			String[] listInfo = { subID, partNumber, partName };
			// ��ø��ı��
			int row = qMMultiList.getNumberOfRows();
			qMMultiList.addTextCell(row, 0, listInfo[0]);
			if (flag) {// �Ѵ��ڷ���·��
				image = new ImageIcon(getClass().getResource(
						"/images/route_partUsedPlace.gif"));
				qMMultiList.setComboBoxSelected(row, 1, marks.elementAt(1),
						marks);
			} else {// ��·��
				image = new ImageIcon(getClass().getResource(
						"/images/route_emptyRoute.gif"));
				qMMultiList.setComboBoxSelected(row, 1, marks.elementAt(0),
						marks);
			}
			qMMultiList.addCell(row, 2, listInfo[1], image.getImage());
			qMMultiList.addTextCell(row, 3, listInfo[2]);
			Class[] c1 = {String.class};
			Object[] obj1 = {subID};
			Collection cols = (Collection)RequestHelper.request("consTechnicsRouteService", "findPartEffRoute", c1, obj1);
			if(cols != null && cols.size() > 0)
			{
				Iterator ii = cols.iterator();
				if(ii.hasNext())
				{
					Object[] objss = (Object[])ii.next();
					ListRoutePartLinkInfo info = (ListRoutePartLinkInfo)objss[0];
					if(info.getMainStr()!=null)
					{
						qMMultiList.addTextCell(row, 4, info.getMainStr());
					}
					else
					{
						qMMultiList.addTextCell(row, 4, "");
					}
					if(info.getSecondStr()!=null)
					{
						qMMultiList.addTextCell(row, 5, info.getSecondStr());
					}
					else
					{
						qMMultiList.addTextCell(row, 5, "");
					}
				}
			}
			else
			{
				qMMultiList.addTextCell(row, 4, "");
				qMMultiList.addTextCell(row, 5, "");
			}
			qMMultiList.addTextCell(row, 6, "");
			qMMultiList.addTextCell(row, 7, "");
			qMMultiList.addTextCell(row, 8, "");
			qMMultiList.addTextCell(row, 9, subInfo.getVersionValue());
			qMMultiList.addTextCell(row, 10, "");
			qMMultiList.addTextCell(row, 11, getJFRouteStr(subInfo.getMasterBsoID()));
			qMMultiList.setCheckboxSelected(row, 12, false);
			qMMultiList.setCheckboxEditable(true);
			if(velueMap.get("bs").equals("1"))
				qMMultiList.setCheckboxSelected(row, 13, true);
			else
				qMMultiList.setCheckboxSelected(row, 13, false);
			qMMultiList.setCheckboxEditable(true);
			qMMultiList.addTextCell(row, 14, getSourceVersion((QMPartInfo)subInfo));
			allParts.put(subID, new Object[] { subID });
		}
	}
	
	

	// begin 20120111 xucy add
	public void keyPressed(KeyEvent e) {
		// ���ѡ�����б��е����ݣ����ݽ��治ͬģʽ���ù�������ť�Ƿ�ʹ��
		int index = qMMultiList.getSelectedRow();
		if (index != -1) {
			if (mode == this.VIEW_MODE) {
				qmToolBar.setEnabled("part_view", true);
				qmToolBar.setEnabled("route_editGraph", true);
				qmToolBar.setEnabled("route_parentPart", true);
			} else {
				setCompamentEnable();
			}
		}
		// ���ctrl+��ݼ�������ֻ�ܵĲ���
		if (e.isControlDown()) {
			if (mode != this.VIEW_MODE) {
				if (e.getKeyCode() == KeyEvent.VK_C) {
					this.copy();
				} else if (e.getKeyCode() == KeyEvent.VK_V) {
					// System.out.println("ճ��---");
					this.paste();
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					this.upRow();
					qMMultiList.getTable().setColumnSelectionInterval(0, 0);

				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					this.downRow();
					qMMultiList.getTable().setColumnSelectionInterval(0, 0);
				}
				if (e.getKeyCode() == KeyEvent.VK_E) {
					this.deletePart();

				}
			}
			if (e.getKeyCode() == KeyEvent.VK_L) {
				this.parentPart();
			} else if (e.getKeyCode() == KeyEvent.VK_R) {
				this.processEditRouteGraph();
			}
		}
		// Delete��ݼ�����ɾ��
		if (mode != this.VIEW_MODE && e.getKeyCode() == KeyEvent.VK_DELETE) {
			this.deleteRoute();
		}
		if (e.getKeyCode() == KeyEvent.VK_TAB) {
			// System.out.println("TAB---"+KeyEvent.VK_TAB);
			// java.awt.Component c
			// =qMMultiList.getTable().getComponentAt(qMMultiList.getTable().getSelectedRow(),
			// qMMultiList.getTable().getSelectedColumn()+1);
			// qMMultiList.getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			// int selRow = qMMultiList.getTable().getSelectedRow() ;
			// int selCol = qMMultiList.getTable().getSelectedColumn() ;
			// qMMultiList.getTable().changeSelection(selRow,selCol+1,false,false);
			// qMMultiList.getTable().editCellAt(selRow,selCol+1);
			//            
			// if(qMMultiList.getTable().getEditorComponent()!=null)
			// qMMultiList.getTable().getEditorComponent().requestFocus();

			// qMMultiList.getTable().requestFocusInWindow();
			// qMMultiList.getTable().requestDefaultFocus();
			//    		
			// qMMultiList.getTable().getColumnModel().getColumn(qMMultiList.getTable().getSelectedColumn()+1).setCellEditor(
			// new DefaultCellEditor(c));

			// System.out.println("row : "+
			// qMMultiList.getTable().getSelectedRow());
			// System.out.println("col : " +
			// qMMultiList.getTable().getSelectedColumn());
			// qMMultiList.getTable().editCellAt(qMMultiList.getTable().getSelectedRow(),
			// qMMultiList.getTable().getSelectedColumn()+1);
			// qMMultiList.getTable().changeSelection(qMMultiList.getTable().getSelectedRow(),
			// qMMultiList.getTable().getSelectedColumn()+1, true, true);

			// this.downRow();
			// qMMultiList.getTable().setColumnSelectionInterval(0, 0);
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

	// end 20120111 xucy add

	/**
	 * ����㲿��·��״̬,�������ʾͼ��
	 * 
	 * @param subID
	 * @return
	 * @throws QMException
	 */
	private boolean getStatus(String subID) throws QMException {
		boolean flag = false;

		ServiceRequestInfo info = new ServiceRequestInfo();
		info.setServiceName("consTechnicsRouteService");
		info.setMethodName("getAdoptStatusByPart");
		Class[] paramClass = { String.class };
		info.setParaClasses(paramClass);
		Object[] paramValue = { subID };
		info.setParaValues(paramValue);
		flag = (Boolean) RequestHelper.request(info);
		return flag;
	}

	// CR5 end
	/**
	 * ��������
	 * 
	 * @param parts
	 *            �㲿��
	 * @param parent
	 *            ����
	 * @param product
	 *            ��Ʒ
	 * @return
	 * @throws QMRemoteException
	 */
	public HashMap calCountInProduct(Vector parts, QMPartIfc parent,
			QMPartIfc product) {
		if (verbose) {
			System.out.println("��������====" + "part==" + parts + "parent=="
					+ parent + "product==" + product);
		}
		if (product == null) {
			return new HashMap();
		}
		ServiceRequestInfo info = new ServiceRequestInfo();
		info.setServiceName("consTechnicsRouteService");
		info.setMethodName("calCountInProduct");
		Class[] paramClass = { Vector.class, QMPartIfc.class, QMPartIfc.class };
		info.setParaClasses(paramClass);
		Object[] paramValue = { parts, parent, product };
		info.setParaValues(paramValue);
		try {
			return (HashMap) RequestHelper.request(info);
		} catch (Exception ex) {
			ex.printStackTrace();
			String message = ex.getMessage();
			DialogFactory.showInformDialog(this, message);
			return new HashMap();// xucy 20111213
		}

	}

	/**
	 * ��������
	 * 
	 * @param parts
	 *            �㲿��
	 * @param parent
	 *            ����
	 * @param product
	 *            ��Ʒ
	 * @return
	 * @throws QMRemoteException
	 *             20120109 xucy add
	 */
	public HashMap calCountInProduct(Vector parts, QMPartMasterIfc product) {
		if (verbose) {
			System.out.println("��������====" + "part==" + parts + "parent=="
					+ "product==" + product);
		}
		if (product == null) {
			return new HashMap();
		}
		ServiceRequestInfo info = new ServiceRequestInfo();
		info.setServiceName("consTechnicsRouteService");
		info.setMethodName("calCountInProduct");
		Class[] paramClass = { Vector.class, QMPartMasterIfc.class };
		info.setParaClasses(paramClass);
		Object[] paramValue = { parts, product };
		info.setParaValues(paramValue);
		try {
			return (HashMap) RequestHelper.request(info);
		} catch (Exception ex) {
			ex.printStackTrace();
			String message = ex.getMessage();
			DialogFactory.showInformDialog(this, message);
			return new HashMap();// xucy 20111213
		}

	}

	/**
	 * ��������
	 * 
	 * @param parts
	 *            �㲿��
	 * @param parent
	 *            ����
	 * @param product
	 *            ��Ʒ
	 * @return
	 * @throws QMRemoteException
	 */
	public int calCountInProduct(QMPartIfc part, QMPartIfc parent,
			QMPartIfc product) {
		if (verbose) {
			System.out.println("��������====" + "part==" + part + "parent=="
					+ parent + "product==" + product);
		}
		RequestServer server = RequestServerFactory.getRequestServer();
		ServiceRequestInfo info = new ServiceRequestInfo();
		info.setServiceName("consTechnicsRouteService");
		info.setMethodName("calCountInProduct");
		Class[] paramClass = { QMPartIfc.class, QMPartIfc.class,
				QMPartIfc.class };
		info.setParaClasses(paramClass);
		Object[] paramValue = { part, parent, product };
		info.setParaValues(paramValue);
		try {
			return ((Integer) RequestHelper.request(info)).intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
			String message = ex.getMessage();
			DialogFactory.showInformDialog(this, message);
			return 0;// xucy 20111213
		}

	}

	/**
	 * ������� �����������ٵ����������������4�����ܲ����Ż���
	 * 
	 * @param vec
	 *            Collection
	 * @return Collection
	 */
	private static Collection filterPart(Collection vec) {
		ConfigSpecItem configSpecItem = SelectPartJDialog.getPartTreePanel()
				.getConfigSpecItem();
		if (configSpecItem == null) {
			System.out.println("�������ù淶û�г�ʼ��1");
			SelectPartJDialog.getPartTreePanel().setConfigSpecCommand();
			configSpecItem = SelectPartJDialog.getPartTreePanel()
					.getConfigSpecItem();
		}
		try {
			if (configSpecItem == null) {
				Class[] classes = { Collection.class };
				Object[] objs = { vec };
				return (Collection) RequestHelper.request(
						"consTechnicsRouteService",
						"filteredIterationsOfByDefault", classes, objs);
			} else {
				Class[] classes = { Collection.class, PartConfigSpecIfc.class };
				Object[] objs = { vec, configSpecItem.getConfigSpecInfo() };

				Collection col = (Collection) RequestHelper.request(
						"StandardPartService", "filteredIterationsOf", classes,
						objs);
				// System.out.println(" else " + col.size());
				return col;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getMessage();
			DialogFactory.showWarningDialog(null, message);
			return null;
		}

	}

	/**
	 * �㲿��������
	 * 
	 * @param master
	 * @return
	 */
	public static QMPartIfc filterPart(String master) {
		// System.out.println("zz ���� ���� filterPart ");
		ConfigSpecItem configSpecItem = SelectPartJDialog.getPartTreePanel()
				.getConfigSpecItem();
		if (configSpecItem == null) {
			System.out.println("�������ù淶û�г�ʼ��2");
			SelectPartJDialog.getPartTreePanel().setConfigSpecCommand();
			configSpecItem = SelectPartJDialog.getPartTreePanel()
					.getConfigSpecItem();
		}
		try {
			Class[] cla = { String.class };
			Object[] obj = { master };
			// return useServiceMethod("PersistService", "refreshInfo", cla,
			// obj);
			// CCBegin pant 20111115
			QMPartMasterInfo partmaster = (QMPartMasterInfo) RequestHelper
					.request("PersistService", "refreshInfo", cla, obj);
			// CCEnd pant 20111115
			if (configSpecItem == null) {
				Class[] classes = { QMPartMasterIfc.class };
				Object[] objs = { partmaster };
				// QMPartIfc qq = (QMPartIfc) CappRouteAction.useServiceMethod(
				// "TechnicsRouteService",
				// "filteredIterationsOfByDefault", classes, objs);
				// System.out.println(" filterPart ������configSpecItem == null "
				// + qq);
				return (QMPartIfc) RequestHelper.request(
						"TechnicsRouteService",
						"filteredIterationsOfByDefault", classes, objs);
			} else {
				Vector vec = new Vector();
				vec.add(partmaster);
				Class[] classes = { Collection.class, PartConfigSpecIfc.class };
				Object[] objs = { vec, configSpecItem.getConfigSpecInfo() };

				Collection col = (Collection) RequestHelper.request(
						"StandardPartService", "filteredIterationsOf", classes,
						objs);
				// System.out.println("������֮�� ��collection " + col.size());
				if (col != null && col.size() > 0) {
					Object[] parts = (Object[]) col.toArray()[0];
					// System.out.println("filterPart ������ else" + (QMPartIfc)
					// parts[0]);
					return (QMPartIfc) parts[0];

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getMessage();
			DialogFactory.showWarningDialog(null, message);
			return null;
		}
		return null;
	}

	/**
	 * ˢ�¶���
	 * 
	 * @param bsoID
	 * @return 20120105 xucy add
	 */
	public static BaseValueIfc refreshInfo(String bsoID) {
		try {
			Class[] cla = { String.class };
			Object[] obj = { bsoID };
			// return useServiceMethod("PersistService", "refreshInfo", cla,
			// obj);
			BaseValueIfc ifc = (BaseValueIfc) RequestHelper.request(
					"PersistService", "refreshInfo", cla, obj);
			return ifc;
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getMessage();
			DialogFactory.showWarningDialog(null, message);
			return null;
		}
		// return null;
	}

	/**
	 * private QMPartIfc filterPart(String master) { ConfigSpecItem
	 * configSpecItem = SelectPartJDialog.getPartTreePanel()
	 * .getConfigSpecItem(); if (configSpecItem == null) {
	 * System.out.println("�������ù淶û�г�ʼ��3");
	 * SelectPartJDialog.getPartTreePanel().setConfigSpecCommand();
	 * configSpecItem = SelectPartJDialog.getPartTreePanel()
	 * .getConfigSpecItem(); } Hashtable table = null; try { QMPartMasterInfo
	 * partmaster = (QMPartMasterInfo) CappTreeHelper .refreshInfo(master);
	 * QMPartMasterInfo[] partMasters = { partmaster }; //���PART���� table =
	 * PartHelper.getAllVersionsNow(partMasters, configSpecItem
	 * .getConfigSpecInfo()); } catch (QMRemoteException e) { String title =
	 * QMMessage.getLocalizedMessage(RESOURCE, "information", null);
	 * JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), title,
	 * JOptionPane.WARNING_MESSAGE); return null; } if (table != null) {
	 * //���part���� Vector part = (Vector) table.get("part"); //���master���� Vector
	 * partMaster = (Vector) table.get("partmaster"); //���û�в����ϵ�PartMaster if
	 * (partMaster.size() == 0) { return (QMPartInfo) part.elementAt(0); }
	 * //����в����ϵ�PartMaster else { //jMenuConfigCrit.setEnabled(true);
	 * PartShowMasterDialog dialog = new PartShowMasterDialog( partMaster);
	 * dialog.setSize(400, 300); Dimension screenSize =
	 * Toolkit.getDefaultToolkit() .getScreenSize(); Dimension frameSize =
	 * dialog.getSize(); if (frameSize.height > screenSize.height) {
	 * frameSize.height = screenSize.height; } if (frameSize.width >
	 * screenSize.width) { frameSize.width = screenSize.width; }
	 * dialog.setLocation((screenSize.width - frameSize.width) / 2,
	 * (screenSize.height - frameSize.height) / 2); dialog.show(); } } return
	 * null; }
	 */

	/**
	 * ��ʼ��
	 * 
	 * @throws Exception 
	 */
	private void jbInit() {
		this.setLayout(gridBagLayout2);
		// CR4 begin
		int i = 0;
		if (routeMode.equals("productRelative")
				|| routeMode.equals("productAndparentRelative")) {
			i = 2;
		} else {
			i = 1;
		}
		ResourceBundle rb = getPropertiesRB();
		// begin 20120105 �촺Ӣ
		// ��׼�Ӽ���ʽ
		String str1[] = this.getValueSet(rb, "addparttoolbar.icons" + i);
		String str2[] = this.getValueSet(rb, "addparttoolbar.text" + i);
		String str3[] = this.getValueSet(rb, "addparttoolbar.discribe" + i);
		this.setTools(str1, str2, str3);
		// ����㲿���Ĳ���
		String str7[] = this.getValueSet(rb, "parttoolbar.icons");
		String str8[] = this.getValueSet(rb, "parttoolbar.text");
		String str9[] = this.getValueSet(rb, "parttoolbar.descripe");
		this.setTools(str7, str8, str9);
		// ������·�ߵĲ���
		String str4[] = this.getValueSet(rb, "routetoolbar.icons");
		String str5[] = this.getValueSet(rb, "routetoolbar.text");
		String str6[] = this.getValueSet(rb, "routetoolbar.descripe");
		this.setTools(str4, str5, str6);
		// end 20120105 �촺Ӣ

		this.setMaximumSize(new Dimension(392, 285));
		this.setMinimumSize(new Dimension(392, 285));
		this.setPreferredSize(new Dimension(392, 285));
		qMMultiList.setDebugGraphicsOptions(0);
		qMMultiList.setMaximumSize(new Dimension(380, 240));
		qMMultiList.setInputVerifier(null);
		// ��Ź��������
		JPanel panel1 = new JPanel();
		// ��� qMMultiList ��2����ť��壨buttonPanel��jPanel1��
		JPanel panel2 = new JPanel();
		panel1.setLayout(new GridBagLayout());
		panel2.setLayout(new GridBagLayout());
		//CCBegin SS31
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		//CCEnd SS31
		this.add(panel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(2, 10, 0, 10), 0, 0));
		this.add(panel2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						2, 10, 0, 10), 0, 0));
		panel2.add(qMMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.SOUTHWEST, GridBagConstraints.BOTH, new Insets(8,
						5, 25, 0), 251, 240));
		panel1.add(qmToolBar, new GridBagConstraints(0, 0, 1, 1, 1.0, 0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(8, 5, 2, 0), 0, 0));
		// CR7
		this.setMultilistInforms();
		// ���Ӽ����¼� �촺Ӣ 20120118 add
		KeyStroke ks1 = KeyStroke.getKeyStroke(KeyEvent.VK_1,
				InputEvent.CTRL_MASK, false);
		KeyStroke ks2 = KeyStroke.getKeyStroke(KeyEvent.VK_2,
				InputEvent.CTRL_MASK, false);
		KeyStroke ks3 = KeyStroke.getKeyStroke(KeyEvent.VK_3,
				InputEvent.CTRL_MASK, false);
		KeyStroke ks4 = KeyStroke.getKeyStroke(KeyEvent.VK_4,
				InputEvent.CTRL_MASK, false);
		KeyStroke ks5 = KeyStroke.getKeyStroke(KeyEvent.VK_5,
				InputEvent.CTRL_MASK, false);
		KeyStroke ks6 = KeyStroke.getKeyStroke(KeyEvent.VK_6,
				InputEvent.CTRL_MASK, false);
		KeyStroke ks7 = KeyStroke.getKeyStroke(KeyEvent.VK_7,
				InputEvent.CTRL_MASK, false);
		KeyStroke ks8 = KeyStroke.getKeyStroke(KeyEvent.VK_8,
				InputEvent.CTRL_MASK, false);
		KeyStroke ks9 = KeyStroke.getKeyStroke(KeyEvent.VK_9,
				InputEvent.CTRL_MASK, false);
		InputMap map = qMMultiList
				.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		map.put(ks1, "key1");
		map.put(ks2, "key2");
		map.put(ks3, "key3");
		map.put(ks4, "key4");
		map.put(ks5, "key5");
		map.put(ks6, "key6");
		map.put(ks7, "key7");
		map.put(ks8, "key8");
		map.put(ks9, "key9");
		// ��Ӽ��̼���
		ActionAdapter a1 = new ActionAdapter(this);
		a1.setActionCommandOld("Ctrl_1");
		ActionAdapter a2 = new ActionAdapter(this);
		a2.setActionCommandOld("Ctrl_2");
		ActionAdapter a3 = new ActionAdapter(this);
		a3.setActionCommandOld("Ctrl_3");
		ActionAdapter a4 = new ActionAdapter(this);
		a4.setActionCommandOld("Ctrl_4");
		ActionAdapter a5 = new ActionAdapter(this);
		a5.setActionCommandOld("Ctrl_5");
		ActionAdapter a6 = new ActionAdapter(this);
		a6.setActionCommandOld("Ctrl_6");
		ActionAdapter a7 = new ActionAdapter(this);
		a7.setActionCommandOld("Ctrl_7");
		ActionAdapter a8 = new ActionAdapter(this);
		a8.setActionCommandOld("Ctrl_8");
		ActionAdapter a9 = new ActionAdapter(this);
		a9.setActionCommandOld("Ctrl_9");
		qMMultiList.getActionMap().put("key1", a1);
		qMMultiList.getActionMap().put("key2", a2);
		qMMultiList.getActionMap().put("key3", a3);
		qMMultiList.getActionMap().put("key4", a4);
		qMMultiList.getActionMap().put("key5", a5);
		qMMultiList.getActionMap().put("key6", a6);
		qMMultiList.getActionMap().put("key7", a7);
		qMMultiList.getActionMap().put("key8", a8);
		qMMultiList.getActionMap().put("key9", a9);
		// ���Ӽ����¼� �촺Ӣ 20120118 add
		// ��ȡ��ǰ�û��Ŀ��·����Ϣ
		// this.shortCutRoute = RouteClientUtil.getShortCutRoute();
		marks = RouteClientUtil.getMarks();
   //CCBegin SS7
   stockID=RouteClientUtil.getStockID();
   //CCEnd SS7
		// 20120111 xucy add
		qMMultiList.addKeyListener(this);
		// qMMultiList.addMouseListener(this);
		qMMultiList.addActionListener(this);

		// CCBegin SS3
		countButton.setText("��������");
		//CCBegin SS31
		//final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		//gridBagConstraints.gridy = 1;
		//gridBagConstraints.gridx = 0;
    //CCEnd SS31
		countButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				countJButton_actionPerformed(e);
			}
		});
		//CCBegin SS31
		//panel2.add(countButton, gridBagConstraints);
		panel2.add(buttonPanel,           new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(20, 0, 20, 0), 0, 0));
            
    buttonPanel.add(countButton,      new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
		// CCEnd SS3
    multiChangeMarkButton.setText("����ѡ����ı��");
		multiChangeMarkButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				multiChangeMarkButton_actionPerformed(e);
			}
		});
    buttonPanel.add(multiChangeMarkButton,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
    //CCEnd SS31
	}
	
	//CCBegin SS31
	private void multiChangeMarkButton_actionPerformed(ActionEvent e)
	{
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try
		{
			int index = qMMultiList.getSelectedRow();
			if (index < 0)
			{
				DialogFactory.showWarningDialog(getParentJFrame(), "��ѡ�����");
				setCursor(Cursor.getDefaultCursor());
				return;
			}
			if (index != -1)
			{
				int rowCount = qMMultiList.getRowCount();
				String firstMark = (String) qMMultiList.getComboBoxObject(index, 1);
				for (int i = 0; i < rowCount; i++)
				{
					qMMultiList.setComboBoxSelected(i, 1, firstMark, marks);
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			String message = ex.getMessage();
			DialogFactory.showInformDialog(this.getParentJFrame(), message);
			setCursor(Cursor.getDefaultCursor());
			return;
		}
		setCursor(Cursor.getDefaultCursor());
	}
	//CCEnd SS31

	// CCBegin SS3
	private void countJButton_actionPerformed(ActionEvent e) {

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		if (theProductifc != null) {
			Vector partIDVec = new Vector();
			HashMap countMap = null;
			int[] selectRow = this.qMMultiList.getSelectedRows();
			if (selectRow == null || selectRow.length == 0) {

				DialogFactory.showInformDialog(this.getParentJFrame(),
						"��ѡ��Ҫ�������������!");
				setCursor(Cursor.getDefaultCursor());
				return;
			}
			for (int i = 0; i < selectRow.length; i++) {

				String partID = this.qMMultiList.getCellText(i, 0);
				partIDVec.add(partID);

			}

			Class[] c = { Vector.class, QMPartMasterIfc.class };
			Object[] objs = { partIDVec, theProductifc };
			try {
				countMap = (HashMap) RequestHelper.request(
						"consTechnicsRouteService", "getPartsCount", c, objs);

				for (int k = 0; k < selectRow.length; k++) {

					String partMasterID = this.qMMultiList.getCellText(k, 0);

					String count = (String) countMap.get(partMasterID);
					int row = selectRow[k];
					//CCBegin SS26
					if(comp.equals("ct")){
						this.qMMultiList.addTextCell(row, 12, count);
					}else{
						this.qMMultiList.addTextCell(row, 10, count);
					}
					//CCEnd SS26
					

				}

			} catch (Exception ex) {
				ex.printStackTrace();
				String message = ex.getMessage();
				DialogFactory.showInformDialog(this.getParentJFrame(), message);
				setCursor(Cursor.getDefaultCursor());
				return;
			}

		} else {

			DialogFactory.showInformDialog(this, "���ڲ�ƷΪ�գ����ܼ�������!");
			setCursor(Cursor.getDefaultCursor());
			return;

		}
		setCursor(Cursor.getDefaultCursor());

	}

	// CCEnd SS3

	/**
	 * �����б���������п� 20120208 xucy add
	 */
	public void setMultilistInforms() {
		//CCBegin SS6
        // ·�����Ʒ���
		//CCBegin SS12
//    	if(comp.equals("zczx")){
		//CCEnd SS12
		//CCBegin SS20
		//CCBegin SS22
		if(comp.equals("ct")){
			 if(routeMode.equals("productRelative"))
 	        {
 	            qMMultiList.setHeadings(new String[]{"id", "���ı��", "���", "����", "��Ҫ·��", "��Ҫ·��", "·��˵��", "����", "��Ʒ��ʶ", "·��ID", "����ID","�汾","�ɹ���ʶ","��Ӧ��","��Ӧ��ID"});
 	            qMMultiList.setRelColWidth(new int[]{0, 1, 2, 2, 2, 3, 2, 1, 1, 0, 0,1,1,1,0});
 	        }
 	        // ·�����Ʒ�ͽṹ���
 	        else if(routeMode.equals("productAndparentRelative"))
 	        {
 	            qMMultiList.setHeadings(new String[]{"id", "���ı��", "���", "����", "�������", "��������", "��Ҫ·��", "��Ҫ·��", "·��˵��", "����", "��Ʒ��ʶ", "·��ID", "����ID","�汾","�ɹ���ʶ","��Ӧ��","��Ӧ��ID"});
 	            qMMultiList.setRelColWidth(new int[]{0, 1, 2, 2, 2, 2, 2, 3, 2, 1, 1, 0, 0,1,1,1,0});
 	        }
 	        // ·���뱾�����
			
 	        else if(routeMode.equals("partRelative"))
 	        {
 	           //CCBegin SS21

 	          if(routeListInfo!=null){  	
 	        	  if(routeListInfo.getRouteListLevel().equals("����·��")){
 	        		 qMMultiList.setHeadings(new String[]{"id", "���ı��", "���", "����", "��Ҫ·��", "��Ҫ·��", "·��˵��", "·��ID", "����ID","һ������·��","һ��װ��·��","�汾","����","�ɹ���ʶ","��Ӧ��","��Ӧ��ID"});
 	        		 qMMultiList.setRelColWidth(new int[]{0, 1, 1, 1,1,1, 1,  0, 0,1,1,1,1,1,1,0}); 
 	        		  qMMultiList.resetWidth();
 	 	       		   qMMultiList.setRelColWidth(new int[]{0, 1, 1, 1,1,1, 1,  0, 0,1,1,1,1,1,1,0}); 
 	        	 }else{
 	        		   qMMultiList.setHeadings(new String[]{"id", "���ı��", "���", "����", "��Ҫ·��", "��Ҫ·��", "·��˵��", "·��ID", "����ID","һ������·��","һ��װ��·��","�汾","����","�ɹ���ʶ","��Ӧ��","��Ӧ��ID"});      
 	                   qMMultiList.setRelColWidth(new int[]{0, 1, 1, 1, 1, 1, 1, 0, 0,0,0,1,1,1,1,0});
 	       		       qMMultiList.resetWidth();
 	       		       qMMultiList.setRelColWidth(new int[]{0, 1, 1, 1, 1, 1, 1, 0, 0,0,0,1,1,1,1,0});
 	        	 }
 	          }else{
 	          
 	       	   if(listPanel.flag){    		   
 	       		   qMMultiList.setHeadings(new String[]{"id", "���ı��", "���", "����","��Ҫ·��", "��Ҫ·��", "·��˵��", "·��ID", "����ID", "һ������·��","һ��װ��·��","�汾","����","�ɹ���ʶ","��Ӧ��","��Ӧ��ID"});
 	       	    	qMMultiList.setRelColWidth(new int[]{0, 1, 1, 1,1, 1, 1, 0, 0,1,1,1,1,1,1,0});
 	       		   qMMultiList.resetWidth();
 	       		   qMMultiList.setRelColWidth(new int[]{0, 1, 1, 1,1, 1, 1, 0, 0,1,1,1,1,1,1,0}); 
	 	       	
 	       		
 	       	   }else{
 	       		
 	    
 	       		qMMultiList.setHeadings(new String[]{"id", "���ı��", "���", "����","��Ҫ·��", "��Ҫ·��", "·��˵��", "·��ID", "����ID","һ������·��","һ��װ��·��", "�汾","����","�ɹ���ʶ","��Ӧ��","��Ӧ��ID"});      
              
 	       		qMMultiList.setRelColWidth(new int[]{0, 1, 1, 1,1, 1, 1, 0, 0,0,0,1,1,1,1,0});  
     		    qMMultiList.resetWidth();
 	       		qMMultiList.setRelColWidth(new int[]{0, 1, 1, 1, 1, 1, 1, 0, 0,0,0,1,1,1,1,0});  
 	       	   }
 	          }   
 	        }
			//CCEnd��  SS21
 	        // ·����ṹ���
 	        else if(routeMode.equals("parentRelative"))
 	        {
 	            qMMultiList.setHeadings(new String[]{"id", "���ı��", "���", "����", "�������", "��������", "��Ҫ·��", "��Ҫ·��", "·��˵��", "·��ID", "����ID", "����ID","�汾","�ɹ���ʶ","��Ӧ��","��Ӧ��ID"});
 	            qMMultiList.setRelColWidth(new int[]{0, 1, 2, 2, 2, 2, 2, 3, 2, 0, 0, 0,1,1,1,0});
 	        }
		}
		//CCBegin SS32
		else if(comp.equals("cd"))
		{
			if(routeMode.equals("partRelative"))
			{

				//CCBegin SS40
//				CCBegin SS43
//				qMMultiList.setHeadings(new String[]{"id", "���ı��", "���", "����", "��Ҫ·��", "��Ҫ·��", "·��˵��", "·��ID", "����ID","�汾","����","���·��","��ɫ����ʶ"});
//				qMMultiList.setRelColWidth(new int[]{0, 1, 2, 2, 2, 3, 2, 0, 0, 1, 1, 1, 1});
				qMMultiList.setHeadings(new String[]{"id", "���ı��", "���", "����", "��Ҫ·��", "��Ҫ·��", "·��˵��", "·��ID", "����ID","�汾","����","���·��","��ɫ����ʶ","�汾ת��","����Դ�汾"});
				qMMultiList.setRelColWidth(new int[]{0, 1, 2, 2, 2, 3, 2, 0, 0, 1,1, 1, 1, 1,1});
				
//		    	  CCEnd SS43
				
    	  //CCEnd SS40

    	}
		}
		//CCEnd SS32
		else{
		//CCend SS20
			//CCend SS22
    		 if(routeMode.equals("productRelative"))
    	        {
    	            qMMultiList.setHeadings(new String[]{"id", "���ı��", "���", "����", "��Ҫ·��", "��Ҫ·��", "·��˵��", "����", "��Ʒ��ʶ", "·��ID", "����ID","�汾","�ɹ���ʶ"});
    	            qMMultiList.setRelColWidth(new int[]{0, 1, 2, 2, 2, 3, 2, 1, 1, 0, 0,1,1});
    	        }
    	        // ·�����Ʒ�ͽṹ���
    	        else if(routeMode.equals("productAndparentRelative"))
    	        {
    	            qMMultiList.setHeadings(new String[]{"id", "���ı��", "���", "����", "�������", "��������", "��Ҫ·��", "��Ҫ·��", "·��˵��", "����", "��Ʒ��ʶ", "·��ID", "����ID","�汾","�ɹ���ʶ"});
    	            qMMultiList.setRelColWidth(new int[]{0, 1, 2, 2, 2, 2, 2, 3, 2, 1, 1, 0, 0,1,1});
    	        }
    	        // ·���뱾�����
    	        else if(routeMode.equals("partRelative"))
    	        {
    	            qMMultiList.setHeadings(new String[]{"id", "���ı��", "���", "����", "��Ҫ·��", "��Ҫ·��", "·��˵��", "·��ID", "����ID","�汾","����","�ɹ���ʶ"});
    	            qMMultiList.setRelColWidth(new int[]{0, 1, 2, 2, 2, 3, 2, 0, 0,1,1,1});
    	        }
    	        // ·����ṹ���
    	        else if(routeMode.equals("parentRelative"))
    	        {
    	            qMMultiList.setHeadings(new String[]{"id", "���ı��", "���", "����", "�������", "��������", "��Ҫ·��", "��Ҫ·��", "·��˵��", "·��ID", "����ID", "����ID","�汾","�ɹ���ʶ"});
    	            qMMultiList.setRelColWidth(new int[]{0, 1, 2, 2, 2, 2, 2, 3, 2, 0, 0, 0,1,1});
    	        }
		}
    		 //CCBegin SS12
//		} else {
//			// CCEnd SS6
//		// CCBegin SS1
//		// ·�����Ʒ���
//		if (routeMode.equals("productRelative")) {
//			qMMultiList
//					.setHeadings(new String[] { "id", "���ı��", "���", "����",
//							"��Ҫ·��", "��Ҫ·��", "·��˵��", "����", "��Ʒ��ʶ", "·��ID",
//							"����ID", "�汾" });
//			qMMultiList.setRelColWidth(new int[] { 0, 1, 2, 2, 2, 3, 2, 1, 1,
//					0, 0, 1 });
//		}
//		// ·�����Ʒ���
//		else if (routeMode.equals("productAndparentRelative")) {
//			qMMultiList.setHeadings(new String[] { "id", "���ı��", "���", "����",
//					"�������", "��������", "��Ҫ·��", "��Ҫ·��", "·��˵��", "����", "��Ʒ��ʶ",
//					"·��ID", "����ID", "�汾" });
//			qMMultiList.setRelColWidth(new int[] { 0, 1, 2, 2, 2, 2, 2, 3, 2,
//					1, 1, 0, 0, 1 });
//		}
//		// ·���뱾�����
//		else if (routeMode.equals("partRelative")) {
//			qMMultiList.setHeadings(new String[] { "id", "���ı��", "���", "����",
//					"��Ҫ·��", "��Ҫ·��", "·��˵��", "·��ID", "����ID", "�汾", "����" });
//			qMMultiList.setRelColWidth(new int[] { 0, 1, 2, 2, 2, 3, 2, 0, 0,
//					1, 1 });
//		}
//		// ·����ṹ���
//		else if (routeMode.equals("parentRelative")) {
//			qMMultiList.setHeadings(new String[] { "id", "���ı��", "���", "����",
//					"�������", "��������", "��Ҫ·��", "��Ҫ·��", "·��˵��", "·��ID", "����ID",
//					"����ID", "�汾" });
//			qMMultiList.setRelColWidth(new int[] { 0, 1, 2, 2, 2, 2, 2, 3, 2,
//					0, 0, 0, 1 });
//		}
//	}
		// CCEnd SS1
    		 //CCEnd SS12
		// CR7 end
		qMMultiList.setMultipleMode(true); // linshi
	
		qMMultiList.updateUI();
//		String[] heads=qMMultiList.getHeadings();
// 		   for(int i=0;i<heads.length;i++)
// 		   {
// 			   System.out.println("heads ["+i+"]==========="+heads[i]);
// 		   }
// 		 int[] wid=qMMultiList.getRelColWidth();
// 	 for(int i=0;i<wid.length;i++)
//	   {
//		   System.out.println("width ["+i+"]==========="+wid[i]);
//	   }
	}

	private Vector sortLinks(Vector v) {

		Vector indexs = routeListInfo.getPartIndex();
		if (verbose) {
			System.out.println("����ǰ v= " + v + " ����indexs = " + indexs);
		}
		if (indexs == null || indexs.size() == 0) {
			// System.out.println("���򼯺�Ϊ��");
			return v;
		}
		Vector result = new Vector();
		String partid;
		String partNum;
		String[] index;

		for (int i = 0; i < indexs.size(); i++) {
			index = (String[]) indexs.elementAt(i);
			partid = index[0];
			System.out.println("˳�����" + partid);
			partNum = index[1];
			ListRoutePartLinkIfc link;
			for (int j = 0; j < v.size(); j++) {
				link = (ListRoutePartLinkIfc) v.elementAt(j);
				System.out.println("�������" + link.getRightBsoID());
				if (link.getRightBsoID().equals(partid)) {
					// 20111231 �촺Ӣ add �д��޸�
					// if((partNum == null || partNum.trim().equals("")) ||
					// (link.getParentPartNum() == null ||
					// link.getParentPartNum().trim().equals("")))
					// {
					result.add(link);
					v.remove(link);
					break;
					// }
					// if(partNum != null && link.getParentPartNum() != null &&
					// partNum.equals(link.getParentPartNum()))
					// {
					// result.add(link);
					// v.remove(link);
					// break;
					// }
				}
			}
		}
		if (verbose) {
			System.out.println("����� result= " + result);
		}
		return result;
	}

	// CCBegin SS1
	/**
	 * ����������汾
	 * 
	 * @param partInfo
	 * @return
	 */
	private String getLastPartVersionID(QMPartMasterIfc partInfo) {

		String partVersionID = "";
		// CCBegin SS5
		String version = "";
		// CCEnd SS5
		Class[] c = { MasteredIfc.class };
		Object[] objs = { partInfo };
		try {
			Vector nv = (Vector) RequestHelper.request("VersionControlService",
					"allIterationsOf", c, objs);

			QMPartIfc part = (QMPartIfc) nv.get(0);
	        
			// CCBegin SS5	
//			partVersionID = part.getVersionValue();
			Class[] c1 = { QMPartInfo.class };    
			Object[] objs1 = { part };
			version = (String) RequestHelper.request("consTechnicsRouteService",
					"getSourceVersion", c1, objs1);	
			if(version == null || version.equals("")){
				version = part.getVersionValue();
			}
			
			// CCEnd SS5
		} catch (Exception ex) {
			String message = ex.getMessage();
			DialogFactory.showInformDialog(this, message);
		}
		// CCBegin SS5
//		return partVersionID;
		return version;
		// CCEnd SS5
	}

	// CCEnd SS1

	// CCBegin SS4
	/**
	 * ����������汾
	 * 
	 * @param partInfo
	 * @return
	 */
	private String getLastPartVersionID(QMPartIfc partInfo) {

		String partVersionID = "";

			Class[] c = { MasteredIfc.class };
			Object[] objs = { (MasteredIfc) partInfo };
			try {
				Vector nv = (Vector) RequestHelper.request(
						"VersionControlService", "allIterationsOf", c, objs);

				QMPartIfc part = (QMPartIfc) nv.get(0);
				partVersionID = part.getVersionValue();

			} catch (Exception ex) {
				String message = ex.getMessage();
				DialogFactory.showInformDialog(this, message);
			}
		
		return partVersionID;
	}

	// CCEnd SS4
	/**
	 * ����·�߱�ֵ��������乤��·�߱���㲿���б�
	 * 
	 * @param routelist
	 *            ·�߱�ֵ����
	 * @throws QMException 
	 * @roseuid 4031B9EC0039
	 */
	public void setTechnicsRouteList(TechnicsRouteListIfc routelist) throws QMException {
		qMMultiList.clear();
		// ��������λ��������
		// saveMap.clear();
		addedParts.clear();
		deleteParts.clear();
		routeListInfo = routelist;
		Vector vold = this.getRouteListPartLinks();
		Vector v = sortLinks(vold);
		// ��ǰ·�߱�������㲿������Ϣ������б�
		// �ѵ�ǰ·�߱�������㲿������Ϣ����
		if (v != null && v.size() > 0) {
			ImageIcon image = null;
			for (int i = 0; i < v.size(); i++) {
				ListRoutePartLinkIfc link = (ListRoutePartLinkIfc) v
						.elementAt(i);
				QMPartMasterInfo info = (QMPartMasterInfo) link
						.getPartMasterInfo();
				// begin CR9
				//CCBegin SS11
				//qMMultiList.addTextCell(i, 0, info.getBsoID());
				qMMultiList.addTextCell(i, 0, link.getRightBsoID());
				//CCEnd SS11
				// Collection result = this.getModifyIdenty();
				// Vector marks = this.getMarks();
				qMMultiList
						.addComboBoxCell(i, 1, link.getModifyIdenty(), marks);
				// begin 20120113 xucy add
				// �ѱ���·��
				if (link.getAdoptStatus().equals("�ѱ���")) {
					image = new ImageIcon(getClass().getResource(
							"/images/route.gif"));
				} else if (link.getAdoptStatus().equals("�Ѵ���")) {
					image = new ImageIcon(getClass().getResource(
							"/images/route_partUsedPlace.gif"));
				}

				else if (link.getAdoptStatus().equals("δ����")) { // ��·��
					image = new ImageIcon(getClass().getResource(
							"/images/route_emptyRoute.gif"));
				}
				// end 20120113 xucy add
				qMMultiList.addCell(i, 2, info.getPartNumber(), image
						.getImage());
				qMMultiList.addTextCell(i, 3, info.getPartName());
				// CCBegin SS1
				String pv = this.getLastPartVersionID(info);
				// CCEnd SS1
				// �鿴��׼ʱ��������Ϣ��·��ģʽ�й�
				if (routeMode.equals("productRelative")) {
					qMMultiList.addTextCell(i, 4, link.getMainStr());
					qMMultiList.addTextCell(i, 5, link.getSecondStr());
					qMMultiList.addTextCell(i, 6, link.getRouteDescription());
					qMMultiList.addTextCell(i, 7, String.valueOf(link
							.getProductCount()));
					qMMultiList.setCheckboxSelected(i, 8, link
							.getProductIdenty());
					qMMultiList.addTextCell(i, 9, link.getRouteID());
					qMMultiList.addTextCell(i, 10, link.getBsoID());
					//CCBegin SS9
				
					// CCBegin SS1
					qMMultiList.addTextCell(i, 11, pv);
					// CCEnd SS1
				
				
				  //CCBegin SS7
//           if(comp.equals("zczx")){
                qMMultiList.addComboBoxCell(i, 12, link.getStockID(),stockID);
//             }
           //CCEnd SS7
                //CCBegin SS20
                if(comp.equals("ct")){
                  qMMultiList.addTextCell(i, 13, link.getSupplier());
                  qMMultiList.addTextCell(i, 14, link.getSupplierBsoId());
                 }
              //CCEnd SS20
					// CR19 begin
					// if(link.getProductID()==null)
					// {
					// allParts.put(info.getBsoID(), new
					// Object[]{info.getBsoID()});
					// }else
					// {
					// QMPartMasterIfc product =
					// (QMPartMasterIfc)RouteClientUtil.refresh(link.getProductID());
					allParts.put(info.getBsoID()
							+ this.theProductifc.getPartNumber(), new Object[] {
							info.getBsoID(), theProductifc, null });
					// }
					// CR19 end
				} else if (routeMode.equals("parentRelative")) {
					qMMultiList.addTextCell(i, 4, link.getParentPartNum());
					qMMultiList.addTextCell(i, 5, link.getParentPartName());
					qMMultiList.addTextCell(i, 6, link.getMainStr());
					qMMultiList.addTextCell(i, 7, link.getSecondStr());
					qMMultiList.addTextCell(i, 8, link.getRouteDescription());
					qMMultiList.addTextCell(i, 9, link.getRouteID());
					qMMultiList.addTextCell(i, 10, link.getBsoID());
					qMMultiList.addTextCell(i, 11, link.getParentPartID());	
					// CCBegin SS1
					qMMultiList.addTextCell(i, 12, pv);
				
					// CCEnd SS1
					//CCBegin SS7
//          if(comp.equals("zczx")){
               qMMultiList.addComboBoxCell(i, 13, link.getStockID(),stockID);
//          }
          //CCEnd SS7
               //CCBegin SS20
               if(comp.equals("ct")){
                 qMMultiList.addTextCell(i, 14, link.getSupplier());
                 qMMultiList.addTextCell(i, 15, link.getSupplierBsoId());
                }
             //CCEnd SS20
					QMPartIfc parent = link.getParentPart();
					if (parent == null) {
						allParts.put(info.getBsoID(), new Object[] {
								info.getBsoID(), null });
					} else {
						allParts.put(info.getBsoID() + parent.getPartNumber(),
								new Object[] { info.getBsoID(), parent });
						saveMap.put(info.getBsoID() + parent.getPartNumber(),
								parent);
					}

				} else if (routeMode.equals("productAndparentRelative")) {
					qMMultiList.addTextCell(i, 4, link.getParentPartNum());
					qMMultiList.addTextCell(i, 5, link.getParentPartName());
					qMMultiList.addTextCell(i, 6, link.getMainStr());
					qMMultiList.addTextCell(i, 7, link.getSecondStr());
					qMMultiList.addTextCell(i, 8, link.getRouteDescription());
					qMMultiList.addTextCell(i, 9, String.valueOf(link
							.getProductCount()));
					qMMultiList.setCheckboxSelected(i, 10, link
							.getProductIdenty());
					qMMultiList.addTextCell(i, 11, link.getRouteID());   
					qMMultiList.addTextCell(i, 12, link.getBsoID());
		
					// CCBegin SS1
					qMMultiList.addTextCell(i, 13, pv);
					// CCEnd SS1
				
						//CCBegin SS7
//          if(comp.equals("zczx")){
               qMMultiList.addComboBoxCell(i, 14, link.getStockID(),stockID);
//          }
          //CCEnd SS7
               //CCBegin SS20
               if(comp.equals("ct")){
                 qMMultiList.addTextCell(i, 15, link.getSupplier());
                 qMMultiList.addTextCell(i, 16, link.getSupplierBsoId());
                }
             //CCEnd SS20
					// CR19 begin 20120405
					QMPartIfc parent = link.getParentPart();
					// if(link.getProductID()==null)
					// {
					// if(parent == null)
					// {
					// allParts.put(info.getBsoID() , new
					// Object[]{info.getBsoID(), null});
					// }else
					// {
					// allParts.put(info.getBsoID() + parent.getPartNumber(),
					// new Object[]{info.getBsoID(), parent});
					// this.saveMap.put(info.getBsoID() +
					// this.theProductifc.getPartNumber() +
					// parent.getPartNumber(), parent);
					// }
					// }else
					// {
					// QMPartMasterIfc product =
					// (QMPartMasterIfc)RouteClientUtil.refresh(link.getProductID());
					if (parent == null) {
						allParts.put(info.getBsoID()
								+ this.theProductifc.getPartNumber(),
								new Object[] { info.getBsoID(), theProductifc,
										null });
					} else {
						allParts.put(info.getBsoID()
								+ theProductifc.getPartNumber()
								+ parent.getPartNumber(), new Object[] {
								info.getBsoID(), theProductifc, parent });
						saveMap.put(info.getBsoID()
								+ theProductifc.getPartNumber()
								+ parent.getPartNumber(), parent);
					}
					// }
					// CR19 end
				} else if (routeMode.equals("partRelative")) {
					//CCBegin SS21
					if(comp.equals("ct")){
						if(routeListInfo.getRouteListLevel().equals("����·��")){
						Class[] c1 = {String.class};
			            Object[] obj1 = {link.getRightBsoID()};
			            String[] route = (String[])RequestHelper.request("consTechnicsRouteService", "findQMPartRouteLevelOne", c1, obj1);
					
						qMMultiList.addTextCell(i, 4, link.getMainStr());
						qMMultiList.addTextCell(i, 5, link.getSecondStr());
						qMMultiList.addTextCell(i, 6, link.getRouteDescription());
						qMMultiList.addTextCell(i, 7, link.getRouteID());
						qMMultiList.addTextCell(i, 8, link.getBsoID());
						qMMultiList.addTextCell(i, 9, route[0]);
						qMMultiList.addTextCell(i, 10, route[1]);
						qMMultiList.addTextCell(i, 11, pv);
						qMMultiList.addTextCell(i, 12, String.valueOf(link
								.getProductCount()));
	                    qMMultiList.addComboBoxCell(i, 13, link.getStockID(),stockID);
	        
	                    qMMultiList.addTextCell(i, 14, link.getSupplier());
	                    qMMultiList.addTextCell(i, 15, link.getSupplierBsoId());
						allParts.put(info.getBsoID(), new Object[] { info
								.getBsoID() }); 
						}else{
							
							qMMultiList.addTextCell(i, 4, link.getMainStr());
							qMMultiList.addTextCell(i, 5, link.getSecondStr());
							qMMultiList.addTextCell(i, 6, link.getRouteDescription());
							qMMultiList.addTextCell(i, 7, link.getRouteID());
							qMMultiList.addTextCell(i, 8, link.getBsoID());
							qMMultiList.addTextCell(i, 9, "");
							qMMultiList.addTextCell(i, 10, "");
							//CCBegin SS28
							qMMultiList.addTextCell(i, 11, link.getPartVersion());
							//CCEnd SS28
		
							// CCEnd SS1
							qMMultiList.addTextCell(i, 12, String.valueOf(link
									.getProductCount()));
						 //CCBegin SS7
//		          if(comp.equals("zczx")){
		                qMMultiList.addComboBoxCell(i, 13, link.getStockID(),stockID);
//		             }
		         //CCEnd SS7
		             //CCBegin SS20
		                  qMMultiList.addTextCell(i, 14, link.getSupplier());
		                  qMMultiList.addTextCell(i, 15, link.getSupplierBsoId());
		              
		    
		              //CCEnd SS20
							allParts.put(info.getBsoID(), new Object[] { info
									.getBsoID() });
						 }
				
					}else{
					//CCEnd SS21
					qMMultiList.addTextCell(i, 4, link.getMainStr());
					qMMultiList.addTextCell(i, 5, link.getSecondStr());
					qMMultiList.addTextCell(i, 6, link.getRouteDescription());
					qMMultiList.addTextCell(i, 7, link.getRouteID());
					qMMultiList.addTextCell(i, 8, link.getBsoID());
					// CCBegin SS1
					//CCBegin SS9
					if(comp.equals("zczx")){
						qMMultiList.addTextCell(i, 9, link.getPartVersion());
					}else{
				    //CCEnd SS9
					//CCBegin SS15
					//qMMultiList.addTextCell(i, 9, pv);
						qMMultiList.addTextCell(i, 9, link.getPartVersion());
					//CCEnd SS15
					}
					// CCEnd SS1
					qMMultiList.addTextCell(i, 10, String.valueOf(link
							.getProductCount()));
				 //CCBegin SS7
//          if(comp.equals("zczx")){
          //CCBegin SS32
          if(comp.equals("cd"))
          {
          	
          	qMMultiList.addTextCell(i, 11, getJFRouteStr(info.getBsoID()));
          	//CCBegin SS40
          	boolean flag=false;
          	if(link.getColorFlag()!=null&&!link.getColorFlag().equals(""))
          	{
          		if(link.getColorFlag().equals("1"))
          		{
          			flag=true;
          		}  
          	}
          	qMMultiList.addCheckboxCell(i, 12, flag);
          	//CCEnd SS40
          //CCBegin SS43
          	boolean specialFlag=false;
          	if(link.getSpecialFlag()!=null&&!link.getSpecialFlag().equals(""))
          	{
          		if(link.getSpecialFlag().equals("1"))
          		{
          			specialFlag=true;
          		}  
          	}
          	qMMultiList.addCheckboxCell(i, 13, specialFlag);
          	qMMultiList.addTextCell(i, 14, link.getSourceVersion());
          	//CCEnd SS43
          }
          else
          //CCEnd SS32
                qMMultiList.addComboBoxCell(i, 11, link.getStockID(),stockID);
//             }
         //CCEnd SS7
              
           //CCBegin SS27
                  //qMMultiList.addTextCell(i, 12, link.getSupplier());
                  //qMMultiList.addTextCell(i, 13, link.getSupplierBsoId());
           //CCEnd SS27

					allParts.put(info.getBsoID(), new Object[] { info
							.getBsoID() });
				 }
				}
				// end CR9
			}
		}
		// isChangeRouteList = true;
	}

	

	/**
	 * ���·�߱�ֵ����
	 * 
	 * @return ·�߱�ֵ����
	 */
	public TechnicsRouteListIfc getTechnicsRouteList() {
		return routeListInfo;
	}

	/**
	 * ���÷��񣬻�õ�ǰ·�߱�������㲿������
	 * 
	 * @return �㲿�������ļ���
	 * @roseuid 4033530D01BC
	 */
	private Vector getRouteListPartLinks() {
		Vector nv = null;
		if (routeListInfo != null) {
			Class[] c = { TechnicsRouteListIfc.class };
			Object[] objs = { routeListInfo };
			try {
				nv = (Vector) RequestHelper.request("consTechnicsRouteService",
						"getRouteListLinkParts1", c, objs);
			} catch (Exception ex) {
				String message = ex.getMessage();
				DialogFactory.showInformDialog(this, message);
			}
		}
		System.out.println("v============" + nv.size());
		return nv;
	}

	/**
	 * Ӧ���㲿����ͨ����������㲿��
	 * 
	 * @roseuid 4031BC2701B7
	 */
	private void addPart() {
		// ����������
		QmChooser qmChooser = new QmChooser("QMPartMaster", "�����㲿��", this
				.getParentJFrame());
		qmChooser.setChildQuery(false);
		// ���ո���������ִ������
		qmChooser.addListener(new QMQueryListener() {
			public void queryEvent(QMQueryEvent e) {
				qmChooser_queryEvent(e);
			}
		});
		qmChooser.setVisible(true);
	}

	/**
	 * �鿴װ��λ�ã�·���븸���й�ʱ��������㲿���ĸ�����Ϣ
	 * 
	 * @return 20120106 �촺Ӣ add
	 */
	private void parentPart() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		int index = qMMultiList.getSelectedRow();
		String thePartBsoid = null;
		String oldParentNum = null;
		String productNum = null;
		setSelectedNum(index);
		if (index != -1) {
			// ���ѡ�������ID
			thePartBsoid = qMMultiList.getCellAt(index, 0).toString();
			if (routeMode.equals("parentRelative")) {
				oldParentNum = qMMultiList.getCellAt(index, 4).toString();
			} else if (routeMode.equals("productAndparentRelative")) {
				oldParentNum = qMMultiList.getCellAt(index, 4).toString();
				if (this.theProductifc != null) {
					productNum = theProductifc.getPartNumber();
				}
			} else if (routeMode.equals("productRelative")
					&& this.theProductifc != null) {
				productNum = theProductifc.getPartNumber();
			}
		} else {
			DialogFactory.showWarningDialog(this.getParentJFrame(), "��ѡ�����");
			setCursor(Cursor.getDefaultCursor());
			return;
		}
		// CR17
		ViewParentJDialog vpDialog = new ViewParentJDialog(getParentJFrame(),
				productNum, "", true, this.getViewMode());
		vpDialog.setSize(900, 500);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		vpDialog.setLocation((int) (screenSize.getWidth() - 900) / 2,
				(int) (screenSize.getHeight() - 500) / 2);
		//CCBegin SS33
		//boolean flag = vpDialog.setPartMaster((QMPartMasterIfc) refreshInfo(thePartBsoid));
		String mid = thePartBsoid;
		if(thePartBsoid.indexOf("QMPart_")!=-1)
		{
			QMPartIfc temp = (QMPartIfc) refreshInfo(thePartBsoid);
			mid = temp.getMasterBsoID();
		}
		boolean flag = vpDialog.setPartMaster((QMPartMasterIfc) refreshInfo(mid));
		//CCEnd SS33
		if (flag) {
			vpDialog.setVisible(true);
		} else {
			setCursor(Cursor.getDefaultCursor());
			return;
		}
		// ����������� ȷ��ʱ�Ĳ���
		if (vpDialog.getIsSave() && this.getViewMode() != VIEW_MODE) {
			QMPartIfc theParentPart = vpDialog.getSelectedParentPart();
			if (theParentPart == null) {
				// CR16
				setCursor(Cursor.getDefaultCursor());
				return;
			}
			if (routeMode.equals("parentRelative")) {
				qMMultiList.addTextCell(theSelectedNum, 4, theParentPart
						.getPartNumber());
				qMMultiList.addTextCell(theSelectedNum, 5, theParentPart
						.getPartName());
				String partMasterID = qMMultiList.getCellAt(theSelectedNum, 0)
						.toString();
				// ��¼Ҫ���ĸ�����ŵ�ԭ�������Ľ��
				// changePartsToChange(theSelectedNum, theParentPart);
				// RouteWrapData wrap = null;
				if (oldParentNum != null && !oldParentNum.equals("")) {
					// this.deleteParts.put(thePartBsoid + oldParentNum,
					// this.allParts.get(thePartBsoid + oldParentNum));
					// wrap = (RouteWrapData)saveMap.get(thePartBsoid +
					// oldParentNum);
					this.saveMap.remove(thePartBsoid + oldParentNum);
					this.allParts.remove(thePartBsoid + oldParentNum);
				} else {
					// this.deleteParts.put(thePartBsoid,
					// this.allParts.get(thePartBsoid));
					// wrap = (RouteWrapData)saveMap.get(thePartBsoid);
					this.saveMap.remove(thePartBsoid);
					this.allParts.remove(thePartBsoid);
				}
				// 20120405
				// if(wrap == null)
				// wrap = new RouteWrapData();
				// wrap.setParent(theParentPart);
				// wrap.setParentNum(theParentPart.getPartNumber());
				// wrap.setParentName(theParentPart.getPartName());
				this.saveMap.put(thePartBsoid + theParentPart.getPartNumber(),
						theParentPart);
				this.allParts.put(thePartBsoid + theParentPart.getPartNumber(),
						new Object[] { thePartBsoid, theParentPart });
			} else if (routeMode.equals("productAndparentRelative")) {
				qMMultiList.addTextCell(theSelectedNum, 4, theParentPart
						.getPartNumber());
				qMMultiList.addTextCell(theSelectedNum, 5, theParentPart
						.getPartName());
				String partMasterID = qMMultiList.getCellAt(theSelectedNum, 0)
						.toString();
				// RouteWrapData wrap = null;
				if (oldParentNum != null && !oldParentNum.equals("")) {
					// this.deleteParts.put(thePartBsoid + productNum +
					// oldParentNum, this.allParts.get(thePartBsoid + productNum
					// + oldParentNum));
					// wrap = (RouteWrapData)saveMap.get(thePartBsoid +
					// productNum + oldParentNum);
					this.saveMap.remove(thePartBsoid + productNum
							+ oldParentNum);
					this.allParts.remove(thePartBsoid + productNum
							+ oldParentNum);
				} else {
					// this.deleteParts.put(thePartBsoid + productNum,
					// this.allParts.get(thePartBsoid + productNum));
					// wrap = (RouteWrapData)saveMap.get(thePartBsoid +
					// productNum);
					this.saveMap.remove(thePartBsoid + productNum);
					this.allParts.remove(thePartBsoid + productNum);
				}
				// if(wrap == null)
				// wrap = new RouteWrapData();
				// wrap.setParent(theParentPart);
				// wrap.setParentNum(theParentPart.getPartNumber());
				// wrap.setParentName(theParentPart.getPartName());
				this.saveMap.put(thePartBsoid + productNum
						+ theParentPart.getPartNumber(), theParentPart);
				this.allParts.put(thePartBsoid + productNum
						+ theParentPart.getPartNumber(), new Object[] {
						thePartBsoid, theProductifc, theParentPart });
			}
		}
		setCursor(Cursor.getDefaultCursor());
	}

	// CR21 begin
	/**
	 * ɾ��װ��λ��
	 */
	private void deleteParentPart() {

		if (routeMode.equals("parentRelative")
				|| routeMode.equals("productAndparentRelative")) {

			int index[] = qMMultiList.getSelectedRows();
			if (index.length != 0) {
				for (int i = 0; i < index.length; i++) {
					String thePartBsoid = qMMultiList.getCellText(index[i], 0);
					String parentPartNumber = qMMultiList.getCellText(index[i],
							4);
					if (routeMode.equals("parentRelative")) {
						this.saveMap.remove(thePartBsoid + parentPartNumber);
						qMMultiList.addTextCell(index[0], 4, "");
						qMMultiList.addTextCell(index[0], 5, "");
					} else if (routeMode.equals("productAndparentRelative")) {
						this.saveMap.remove(thePartBsoid
								+ theProductifc.getPartNumber()
								+ parentPartNumber);
						qMMultiList.addTextCell(index[0], 4, "");
						qMMultiList.addTextCell(index[0], 5, "");
					}
				}
			}

		} else {

			DialogFactory.showWarningDialog(this.getParentJFrame(),
					"��ǰģʽ�˹�����Ч����(·����ṹ���)��(·�����Ʒ�ͽṹ���)ģʽ����Ч��");

		}
	}

	// CR21 end
	/**
	 * ��ͨ������������ť�������㲿���������б����㲿���ĸ�����ŵ�ʱ�� �ı�addedParts������Ӧ������
	 * 
	 * @param e
	 */
	/*
	 * public void changeAddedParts(int theSelectedNum, QMPartIfc theParentPart)
	 * { String thePartBsoid = qMMultiList.getCellAt(theSelectedNum,
	 * 0).toString(); if (this.addedParts != null) { int loop =
	 * addedParts.size(); for (int i = 0; i < loop; i++) { Object[] tmp =
	 * (Object[]) addedParts.elementAt(i); if (tmp[0].equals(thePartBsoid)) {
	 * addedParts.remove(i); addedParts.add(new Object[] {thePartBsoid,
	 * theParentPart}); } } } }
	 */

	/**
	 * ������������޸ĵ�ԭ�㲿���ļ���
	 * 
	 * @param e
	 */
	/*
	 * private void changePartsToChange(int theSelectedNum, QMPartIfc
	 * theParentPart) { String thePartBsoid =
	 * qMMultiList.getCellAt(theSelectedNum, 0).toString(); if
	 * (this.containsPart(this.addedParts,
	 * thePartBsoid,theParentPart.getPartNumber())) { return; } else {
	 * partsToChange.addElement(new Object[] {thePartBsoid, theParentPart}); }
	 * whenDelete(this.partsToChange, this.deleteParts); }
	 */

	/**
	 * ��·�߱������㲿����ɾ��ʱ
	 * 
	 * @param arg1
	 *            Vector
	 * @param arg2
	 *            Vector
	 */
	/*
	 * private void whenDelete(Vector arg1, Vector arg2) { int loop1 =
	 * arg1.size(); int loop2 = arg2.size(); for (int i = 0; i < loop1; i++) {
	 * Object[] tmp = (Object[]) arg1.elementAt(i); String partBsoid = (String)
	 * tmp[0]; boolean flag1 = false; for (int j = 0; j < loop2; j++) { Object[]
	 * tmp1 = (Object[]) arg2.elementAt(j); String partBsoidToCompare = (String)
	 * tmp1[0]; if (partBsoid.equals(partBsoidToCompare)) { flag1 = true; } } if
	 * (flag1) { arg1.remove(i); } } }
	 */

	/**
	 * �Ƿ��ǲ�Ʒ���Ӽ� ������4�����ܲ��ԣ��Ż�
	 * 
	 * @param psVec
	 *            Vector
	 * @return Collection
	 */
	private Collection isSubInProduct(Vector psVec) {
		// System.out.println(" zz д��
		// isSubInProduct"+theProductifc.getPartNumber());
		if (theProductifc == null) {
			return null;
		}
		Class[] classes = { Vector.class, QMPartIfc.class };
		Object[] objs = { psVec, theProductifc };
		try {
			return (Collection) RequestHelper.request(
					"consTechnicsRouteService", "isParentPart", classes, objs);
		} catch (Exception ex) {
			String message = ex.getMessage();
			DialogFactory.showWarningDialog(this.getParentJFrame(), message);
			return null;
		}

	}

	/**
	 * �Ƿ��ǲ�Ʒ���Ӽ�
	 * 
	 * @param partMaster
	 * @return
	 */
	/*
	 * private boolean isSubInProduct(QMPartMasterIfc partMaster) {
	 * //System.out.println(" 844���� �Ƿ��ǲ�Ʒ���Ӽ�ԭ���� zz"); //String productID =
	 * this.getTechnicsRouteList().getProductMasterID(); //QMPartIfc
	 * theProductifc = this.filterPart(productID); if (theProductifc == null) {
	 * return false; } QMPartIfc part = filterPart(partMaster.getBsoID());
	 * Class[] classes = { QMPartIfc.class, QMPartIfc.class}; Object[] objs = {
	 * part, theProductifc}; try { return ( (Boolean)
	 * CappRouteAction.useServiceMethod( "StandardPartService", "isParentPart",
	 * classes, objs)) .booleanValue(); } catch (QMRemoteException ex) { String
	 * title = QMMessage.getLocalizedMessage(RESOURCE, "notice", null);
	 * JOptionPane.showMessageDialog(this.getParentJFrame(), ex
	 * .getClientMessage(), title, JOptionPane.WARNING_MESSAGE); return false; }
	 * }
	 */

	/**
	 * �����㲿�������¼�����
	 * 
	 * @param e
	 *            ���������¼�
	 */
	public void qmChooser_queryEvent(QMQueryEvent e) {
		if (verbose) {
			System.out
					.println("capproute.view.RouteListPartLinkJPanel:qmChooser_queryEvent(e) begin...");
		}
		if (e.getType().equals(QMQueryEvent.COMMAND)) {
			if (e.getCommand().equals(QmQuery.OkCMD)) {
				// ��������������������������㲿��
				QmChooser c = (QmChooser) e.getSource();
				BaseValueIfc[] bvi = c.getSelectedDetails();
				if (bvi != null) {
					// add by guoxl 20080218
					Vector moreVec = new Vector();
					Vector lessVec = new Vector();
					// add by guoxl end

					Vector v = new Vector();
					Vector ps = new Vector();
					Vector nonMasters = new Vector();
					Vector vec = new Vector(bvi.length);
					// add by guoxl on 20080218
					QMPartMasterIfc partMasterIfc = null;
					// add by guoxl end
					for (int j = 0; j < bvi.length; j++) {
						// add by guoxl on 20080218
						partMasterIfc = (QMPartMasterIfc) bvi[j];
						moreVec.addElement(partMasterIfc.getPartNumber());
						// add by guoxl end

						vec.add(bvi[j]);
					}
					// zz start 20061110����·�ָ��ݲ��Ź����㲿�� ,���������������������ù淶����
					// ������е�������������ֱ�ӷ���
					if (vec != null
							&& routeListInfo.getRouteListLevel().equals(
									RouteListLevelType.SENCONDROUTE
											.getDisplay())) {
						Collection viatheDepart = null;

						Class[] cla = { String.class, Vector.class };
						Object[] obj = {
								routeListInfo.getRouteListDepartment(), vec };
						try {
							viatheDepart = (Collection) RequestHelper.request(
									"consTechnicsRouteService",
									"getOptionPart", cla, obj);

						} catch (Exception ex) {
							String message = ex.getMessage();
							DialogFactory.showInformDialog(this
									.getParentJFrame(), message);
						}

						if (viatheDepart == null || viatheDepart.size() == 0) {

							// modify by guoxl on 20080218(����·��û�ҷ��ϵ��㲿����������ʾ��Ϣ)
							for (int i = 0; i < v.size(); i++) {
								Object[] array = (Object[]) v.elementAt(i);
								partMasterIfc = (QMPartMasterIfc) array[0];
								lessVec.addElement(partMasterIfc
										.getPartNumber());
							}

							if (moreVec.size() > lessVec.size()) {

								for (int i = 0; i < lessVec.size(); i++) {

									if (moreVec.contains(lessVec.elementAt(i))) {

										moreVec.remove(lessVec.elementAt(i));

									}

								}
								JOptionPane.showMessageDialog(this, ""
										+ moreVec.toString()
										+ "�㲿�����Ǹ�·�ߵ�λ��·�߼�!", "��ʾ��Ϣ",
										JOptionPane.INFORMATION_MESSAGE);
							}

							// modify by guoxl end
							return;

						} else
							vec = (Vector) viatheDepart;

					}

					// zz end 20061110����·�ָ��ݲ��Ź����㲿��
					Collection colection = filterPart(vec);
					// System.out.println(" ������filterPart������ collection====== "
					// + colection.size());
					if (colection.size() == 0) {
						JOptionPane.showMessageDialog(this, "û�з��ϱ�׼���㲿��",
								"��ʾ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
						return;

					}
					Vector vector = new Vector(colection);
					// System.out.println("vector========"+vector);
					for (int ii = 0, jj = vector.size(); ii < jj; ii++) {
						Object[] obj = (Object[]) vector.get(ii);
						if (obj[0] == null) { // û��master��part
							nonMasters.add((QMPartMasterIfc) bvi[ii]);
						} else { // ps
							// System.out.println("obj[0] obj[0] obj[0] " +
							// obj[0]);
							ps.add((QMPartIfc) obj[0]);
						}
					}

					HashMap map = null;
					try {
						map = this.calCountInProduct(ps, this.theProductifc);
					} catch (Exception ex1) {
						String message = ex1.getMessage();
						DialogFactory.showInformDialog(this.getParentJFrame(),
								message);
						return;
					}
					Collection isCollection = this.isSubInProduct(ps);
					// System.out.println("isSubInProduct �õ��� collection " +
					// isCollection);
					Vector isornot = null;
					// zz start
					if (isCollection == null) {
						for (int i = 0; i < ps.size(); i++) {
							QMPartIfc parent = null;
							String parentNum = null;
							Integer count = null;
							// ��ѡ���ĳһ�㲿������Ϣ
							QMPartIfc pi = (QMPartIfc) ps.elementAt(i);
							QMPartMasterIfc newPart = (QMPartMasterIfc) pi
									.getMaster();

							if (!allParts.containsKey(newPart.getBsoID()
									+ parentNum)) {

								// ����Ӧ�ü��Դ���
								v.add(new Object[] { newPart, parent });
								allParts.put(newPart.getBsoID() + parentNum,
										new Object[] { newPart.getBsoID(),
												parent });
								addedParts.put(newPart.getBsoID() + parentNum,
										new Object[] { newPart.getBsoID(),
												parent, count });
							}
						}
					}
					// zz end
					else {
						isornot = new Vector(isCollection);
						for (int i = 0; i < ps.size(); i++) {
							// 20120109 xucy add
							QMPartMasterIfc parent = null;
							String parentNum = null;
							Integer count = null;
							// ��ѡ���ĳһ�㲿������Ϣ
							QMPartIfc pi = (QMPartIfc) ps.elementAt(i);
							QMPartMasterIfc newPart = (QMPartMasterIfc) pi
									.getMaster();

							if (((Boolean) isornot.elementAt(i))
									.equals(new Boolean(true))) {
								parent = theProductifc;
								parentNum = theProductifc.getPartNumber();
								count = (Integer) map.get(pi.getBsoID());

							}

							if (((Boolean) isornot.elementAt(i))
									.equals(new Boolean(false))) {
								parent = null;

								count = null;

							}

							if (!allParts.containsKey(newPart.getBsoID()
									+ parentNum)) {

								// ����Ӧ�ü��Դ���
								v.add(new Object[] { newPart, parent });
								allParts.put(newPart.getBsoID() + parentNum,
										new Object[] { newPart.getBsoID(),
												parent });
								addedParts.put(newPart.getBsoID() + parentNum,
										new Object[] { newPart.getBsoID(),
												parent, count });
							}
						}
					}

					this.addPartsToList(v, map, ps);

					for (int i = 0; i < nonMasters.size(); i++) {
						QMPartMasterInfo newPart = (QMPartMasterInfo) nonMasters
								.elementAt(i);
						Object[] obj2 = { ((QMPartMasterInfo) newPart)
								.getPartNumber() };
						String s = QMMessage.getLocalizedMessage(RESOURCE,
								"55", obj2, null);
						String title = QMMessage.getLocalizedMessage(RESOURCE,
								"notice", null);
						JOptionPane.showMessageDialog(this.getParentJFrame(),
								s, title, JOptionPane.WARNING_MESSAGE);

					}
				}
			}
		}
		if (verbose) {
			System.out
					.println("capproute.view.RouteListPartLinkJPanel:CappChooser_queryEvent(e) end...return is void");
		}
	}

	/**
	 * �Ӳ�Ʒ�ṹ������㲿�������Ը��ݸ��㲿�������²�Ʒ�ṹ���һ���㲿���б���Ϊ��
	 * ��·�߱����㲿�����б�ѡ�㲿���������ǰ�༭�Ĺ���·�߱���һ������·�߱���ϵͳ Ӧ�г���Ʒ�ṹ�е������㲿����
	 * �����ǰ�༭�Ĺ���·�߱��Ƕ�������·�߱���ϵͳӦ����·�߱�ĵ�λ���ԣ��г���Ʒ �ṹ������һ��·���ж�Ӧ��λ���㲿������Ϊ��ѡ�㲿����
	 * 
	 * @roseuid 4031BC410074
	 */
	private void addConstructPart() {
		// if(this.isChangeRouteList)
		// {

		selectPartDialog = new SelectPartJDialog(this.getParentJFrame(),
				listPanel);
		SelectPartJDialog.partTreePanel.getchangeConfig().clear();// CR2
		selectPartDialog.setProductStruct(this.theProductifc);
		// this.isChangeRouteList = false;
		// }
		selectPartDialog.setVisible(true);
		/**
		 * if (selectPartDialog.isSave) { Vector v =
		 * selectPartDialog.getSelectedParts(); if (v != null && v.size() > 0) {
		 * Vector v2 = new Vector(); for (int i = 0; i < v.size(); i++) {
		 * QMPartMasterInfo partinfo = (QMPartMasterInfo) v.elementAt(i); if
		 * (!this.isContain(partinfo.getBsoID())) { //���� v2.add(partinfo);
		 * allParts.add(partinfo.getBsoID()); addedParts.add(new Object[]
		 * {partinfo.getBsoID(), getProductNum()}); } else { Object[] obj2 = {
		 * partinfo.getPartNumber()}; String s =
		 * QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.ADD_RECYCLE,
		 * obj2, null); String title = QMMessage.getLocalizedMessage(RESOURCE,
		 * "notice", null);
		 * JOptionPane.showMessageDialog(this.getParentJFrame(), s, title,
		 * JOptionPane.WARNING_MESSAGE); } } this.addPartsToList(v2);
		 * System.out.println("to addpartstolist"); } }
		 */

	}

	/**
	 * �����з����������㲿������Ϣ������б���
	 * 
	 * @param parts
	 *            �㲿������Ϣֵ���󼯺�
	 */
	private void addPartsToList(Vector parts, Map map, Vector ps) {
		System.out.println("addPartsToList!!!!  parts===="+parts);
		if (parts != null && parts.size() > 0) {
			Object[] objs = parts.toArray();
			// String as[] = new String[parts.size()];
			/*
			 * Vector ps = new Vector(); for (int i = 0; i < parts.size(); i++)
			 * { Object[] partAndParent = (Object[]) objs[i]; QMPartMasterIfc
			 * partMaster = (QMPartMasterIfc) partAndParent[0];
			 * ps.add(filterPart(partMaster.getBsoID())); } HashMap map = null;
			 * try { map = this.calCountInProduct(ps, this.theProductifc,
			 * this.theProductifc); } catch (QMRemoteException ex1) {
			 * JOptionPane.showMessageDialog(this, ex1 .getClientMessage(),
			 * "�쳣��Ϣ", JOptionPane.INFORMATION_MESSAGE); return; }
			 */
			for (int i = 0; i < parts.size(); i++) {
				int row = qMMultiList.getNumberOfRows();
				Object[] partAndParent = (Object[]) objs[i];
				QMPartMasterIfc partMaster = (QMPartMasterIfc) partAndParent[0];
				QMPartIfc parent = (QMPartIfc) partAndParent[1];
				String partNumber = partMaster.getPartNumber();
				String partName = partMaster.getPartName();
				String parentPartNum = "";
				String parentPartName = "";
				String count = "";
				if (parent != null) {
					parentPartNum = parent.getPartNumber();
					parentPartName = parent.getPartName();
					int co = ((Integer) map.get(((QMPartIfc) ps.elementAt(i))
							.getBsoID())).intValue();
					count = new Integer(co).toString();
				}
				String partRouteStatus = "��";
				String[] listInfo = { partMaster.getBsoID(), partNumber,
						partName, parentPartNum, parentPartName, count,
						partRouteStatus };
				qMMultiList.addTextCell(row, 0, listInfo[0]);
				qMMultiList.addTextCell(row, 1, listInfo[1]);
				qMMultiList.addTextCell(row, 2, listInfo[2]);
				qMMultiList.addTextCell(row, 3, listInfo[3]);
				qMMultiList.addTextCell(row, 4, listInfo[4]);
				qMMultiList.addTextCell(row, 5, listInfo[5]);
				qMMultiList.addTextCell(row, 6, listInfo[6]);
				if (verbose) {
					System.out.println("=1=" + listInfo[0] + "=2="
							+ listInfo[1] + "=3=" + listInfo[2] + "=4="
							+ listInfo[3]);
				}
			}
		}
	}

	/**
	 * ɾ���㲿��
	 */
	private void deletePart() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		int index[] = qMMultiList.getSelectedRows();
		ListRoutePartLinkIfc partlink = null;
		if (index.length != 0) {
			int result = JOptionPane.showConfirmDialog(this.getParentJFrame(),
					"ɾ���㲿����ɾ�����Ĺ���·�ߣ��Ƿ������", "��ʾ", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			switch (result) {
			case JOptionPane.NO_OPTION: {
				setCursor(Cursor.getDefaultCursor());
				return;
			}

				/*
				 * modify by guoxl on 20080218(ɾ���Ѿ����ڵ��㲿��,ϵͳ������ʾ:�Ƿ�ɾ����
				 * ִ����û��ȷ��ɾ��,�رնԻ��򣬵������ɾ��)
				 */
			case JOptionPane.CLOSED_OPTION: {
				setCursor(Cursor.getDefaultCursor());
				return;
			}
				// modify by guoxl end
			}

			for (int i = index.length - 1; i >= 0; i--) {
				// ����������Ϣ
				RouteWrapData routeData = this.getSelectedPartData(index[i]);
				String bsoid = routeData.getPartMasterID();
				// begin CR10
				if (routeMode.equals("productRelative")) {
					allParts.remove(bsoid + theProductifc.getPartNumber());// �������㲿���Ļ�����ɾ��ѡ�е�
					deleteParts.put(bsoid + theProductifc.getPartNumber(),
							new Object[] { bsoid, theProductifc, null });
				} else if (routeMode.equals("parentRelative")) {
					QMPartIfc parent = routeData.getParent();
					String parentNum = "";
					// String parentName = "";
					if (parent != null) {
						parentNum = parent.getPartNumber();
						allParts.remove(bsoid + parentNum);// �������㲿���Ļ�����ɾ��ѡ�е�
						if (saveMap.containsKey(bsoid + parentNum)) {
							saveMap.remove(bsoid + parentNum);
						}
						deleteParts.put(bsoid + parentNum, new Object[] {
								bsoid, parent });
					} else {
						allParts.remove(bsoid);// �������㲿���Ļ�����ɾ��ѡ�е�
						if (saveMap.containsKey(bsoid)) {
							saveMap.remove(bsoid);
						}
						deleteParts.put(bsoid, new Object[] { bsoid, parent });
					}
					// QMPartIfc product = this.theProductifc;
					// if(parentNum.equals(""))
					// { //Begin CR1
					// parentNum = null;
					// } //End CR1
					// QMPartIfc parent = null;
					// if(parentNum != null && !parentNum.trim().equals(""))
					// {
					// parent = (QMPartIfc)((Object[])this.allParts.get(bsoid +
					// parentNum))[1];
					// }

				} else if (routeMode.equals("productAndparentRelative")) {
					// CR18 begin
					QMPartIfc parent = routeData.getParent();
					String parentNum = "";
					if (parent != null) {
						parentNum = parent.getPartNumber();
						allParts.remove(bsoid + theProductifc.getPartNumber()
								+ parentNum);// �������㲿���Ļ�����ɾ��ѡ�е�
						if (saveMap.containsKey(bsoid
								+ theProductifc.getPartNumber() + parentNum)) {
							saveMap
									.remove(bsoid
											+ theProductifc.getPartNumber()
											+ parentNum);
						}
						deleteParts.put(bsoid + theProductifc.getPartNumber()
								+ parentNum, new Object[] { bsoid,
								theProductifc, parent });
					} else {
						allParts.remove(bsoid + theProductifc.getPartNumber());// �������㲿���Ļ�����ɾ��ѡ�е�
						if (saveMap.containsKey(bsoid
								+ theProductifc.getPartNumber())) {
							saveMap.remove(bsoid
									+ theProductifc.getPartNumber());
						}
						deleteParts.put(bsoid + theProductifc.getPartNumber(),
								new Object[] { bsoid, theProductifc, null });
					}
					// CR18 end
				} else if (routeMode.equals("partRelative")) {
					allParts.remove(bsoid);// �������㲿���Ļ�����ɾ��ѡ�е�
					if (saveMap.containsKey(bsoid)) {
						saveMap.remove(bsoid);
					} else {
						deleteParts.put(bsoid, new Object[] { bsoid });
					}

				}
				qMMultiList.removeRow(index[i]);
			}
		} else {
			DialogFactory.showWarningDialog(this.getParentJFrame(), "��ѡ�����");
			setCursor(Cursor.getDefaultCursor());
			return;
		}
		setCursor(Cursor.getDefaultCursor());
	}

	public void clearPartLinks() {
		// this.saveMap = new HashMap();
		this.deleteParts = new HashMap();
	}

	/**
	 * �������map����
	 */
	public void clearHashMap() {
		this.allParts = new HashMap();
		this.saveMap = new HashMap();
		this.deleteParts = new HashMap();
	}

	/**
	 * ������б���ӵ��㲿���������ύ����˱��档
	 * 
	 * @return java.util.Vector Ԫ��Ϊ�㲿����BsoID
	 */
	public HashMap getAddedPartLinks() {
		if (verbose) {
			System.out.println("���б���ӵ��㲿��:" + addedParts);
		}
		return addedParts;
	}

	/**
	 * ���Ҫ�޸ĸ�����ŵ��㲿���������ύ����˱��档 added by skybird 2005.3.4
	 * 
	 * @return
	 */
	public Vector getPartsToChange() {
		return this.partsToChange;
	}

	/**
	 * ������б�ɾ�����㲿���������ύ�����ɾ���� �����Ӧ�ж��㲿�������Ƿ��Ѵ��ڣ�ֻɾ���Ѿ����ڵĹ���
	 * 
	 * @return java.util.Vector Ԫ��Ϊ�㲿����BsoID
	 */
	public HashMap getDeletedPartLinks() {
		if (verbose) {
			System.out.println("���б�ɾ�����㲿��:" + deleteParts);
		}
		return deleteParts;
	}

	/**
	 * �õ��㲿��˳�򼯺�
	 * 
	 * @return Vector
	 */
	public Vector getPartIndex() {
		Vector vec = new Vector();
		int rows = qMMultiList.getNumberOfRows();
		System.out.println("rows==="+rows);
		for (int i = 0; i < rows; i++) {
			String[] ids = new String[3];
			ids[0] = qMMultiList.getCellText(i, 0);
			ids[2] = qMMultiList.getCellText(i, 10);
			// xucy 20111226 add
			// ids[1] = qMMultiList.getCellText(i, 3);
			// if(routeMode.equals("productRelative") ||
			// routeMode.equals("productAndparentRelative"))
			// {
			// if(this.theProductifc == null)
			// {
			// theProductifc =
			// (QMPartMasterIfc)refreshInfo(routeListInfo.getProductMasterID());
			//
			// }
			// ids[1] = theProductifc.getPartNumber();
			// }else if(routeMode.equals("parentRelative"))
			// {
			// ids[1] = qMMultiList.getCellText(i, 4);
			// }else if(routeMode.equals("partRelative"))
			// {
			//
			// }
			vec.add(ids);
		}

		return vec;
		// Vector vec = new Vector();
		// //dtm.fireTableDataChanged();
		// int rows = qMMultiList.getNumberOfRows();
		// for (int i = 0; i < rows; i++) {
		// String[] ids = new String[3];
		// ids[0] = qMMultiList.getCellText(i, 0); //���masterID
		// ids[1] = qMMultiList.getCellText(i, 3); //������� ʵ���ǲ�Ʒ���
		// //ids[2] = dtm.getValueAt(i, 7).toString(); //���� liuming 20070523 add
		// ids[2] = qMMultiList.getCellText(i, 7); //anan
		// String partNumber = qMMultiList.getCellText(i, 1);
		// System.out.println("RouteListPartLink.getPartIndex::::::::::  " +
		// qMMultiList.getCellText(i, 7));
		// System.out.println(" RouteListPartLinkPanel PartNumber = " +
		// partNumber +
		// "    Count = " + ids[2]);
		// vec.add(ids);
		// }
		// return vec;
	}

	/**
	 * ���ý���Ϊ�鿴״̬
	 */
	public void setViewModel() {
		// CR7 begin
		// CR12 begin
		if (routeMode.equals("productRelative")) {
			qmToolBar.setEnabled("part_copy", false);
	   //CCBegin SS7
//		    int col[] = {1, 2, 3, 4, 5, 6, 7, 8};
			//CCBegin SS20
		      //int col[] = {1, 2, 3, 4, 5, 6, 7, 8,12};
		      int col[] = {1, 2, 3, 4, 5, 6, 7, 8,12,13};
		      //CCEnd SS20
		      //CCEnd SS7
		      qMMultiList.setColsEnabled(col, false);	

			
		} else if (routeMode.equals("productAndparentRelative")) {
			qmToolBar.setEnabled("part_copy", false);
            //CCBegin SS7
//          int col[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			//CCBegin SS20
      //    int col[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,14};
			int col[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,14,15};
          //CCEnd SS20
          //CCEnd SS7
			qMMultiList.setColsEnabled(col, false);
		
		} else if (routeMode.equals("partRelative")) {
			qmToolBar.setEnabled("partM_startProductManager", false);
			qmToolBar.setEnabled("part_copy", false);
			qmToolBar.setEnabled("changeNotice", false);
			qmToolBar.setEnabled("part_openIcon", false);
            //CCBegin SS6
            qmToolBar.setEnabled("partM_aptProjectManager", false);
            //CCEnd SS6
            
			 //CCBegin SS7
//          int col[] = {1, 2, 3, 4, 5, 6,9,10};
            //CCBegin SS20
            //CCBegin SS21
            if (comp.equals("ct")) {
				int col[] = { 1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14 };
				qMMultiList.setColsEnabled(col, false);

				// CCEnd SS21
			}
          // CCEnd SS20
        // CCEnd SS7
			
			

		} else if (routeMode.equals("parentRelative")) {
			qmToolBar.setEnabled("partM_startProductManager", false);
			qmToolBar.setEnabled("part_copy", false);
			qmToolBar.setEnabled("changeNotice", false);
			qmToolBar.setEnabled("part_openIcon", false);
            //CCBegin SS6
            qmToolBar.setEnabled("partM_aptProjectManager", false);
            //CCEnd SS6
            //CCBegin SS7 
//          int col[] = {1, 2, 3, 4, 5, 6, 7, 8};
           //CCBegin SS20
            
          //int col[] = {1, 2, 3, 4, 5, 6, 7, 8,13};
          int col[] = {1, 2, 3, 4, 5, 6, 7, 8,13,14};
          //CCEnd SS20
          //CCEnd SS7
			qMMultiList.setColsEnabled(col, false);
			
		}
		// CR12 end
		// begin 20120105 xucy add
		qmToolBar.setEnabled("part_view", false);
		qmToolBar.setEnabled("route_editGraph", false);
		qmToolBar.setEnabled("route_parentPart", false);
		// CR21
		qmToolBar.setEnabled("routeCode_delete", false);
		qmToolBar.setEnabled("part_deletestr", false);
		qmToolBar.setEnabled("public_moveUp", false);
		qmToolBar.setEnabled("public_moveDown", false);

		qmToolBar.setEnabled("public_copy", false);
		qmToolBar.setEnabled("public_paste", false);
		qmToolBar.setEnabled("route_delete", false);
		qmToolBar.setEnabled("open", false);
		// qmToolBar.setEnabled("route_selectPart", false);
		qmToolBar.setEnabled("routeM", false);
		qmToolBar.setEnabled("part_applyAll", false);
		//CCBegin 15
        if(comp.equals("ct")){
            qmToolBar.setEnabled("supplier", false);
            qmToolBar.setEnabled("searthLevelOne", false);
          }
        //CCEnd SS19
		//CCBegin SS35
		if(comp.equals("cd"))
		{
			qmToolBar.setEnabled("searthLevelOne", false);
		}
		//CCEnd SS35
		// end 20120105 xucy add
		// CR7 end
		// 20120106 xucy add
		countButton.setVisible(false);
    //CCBegin SS31
    multiChangeMarkButton.setVisible(false);
    //CCEnd SS31
		mode = this.VIEW_MODE;
	}

	/**
	 * ���ý���Ϊ�༭״̬
	 */
	public void setEditModel() {

		// CR7 begin
		if (routeMode.equals("productRelative")) {
			// CR11 begin
			qmToolBar.setEnabled("part_copy", true);
			
	            //CCBegin SS7 
			if (comp.equals("zczx")) {
				int col[] = { 1, 2, 3, 4, 5, 6, 7, 8, 12 };
				qMMultiList.setColsEnabled(col, true);
			}//CCBegin SS20
			else if(comp.equals("ct")) {
				//CCBegin SS21
//			
	
	             int col[] = { 1, 2, 3, 4, 5, 6, 7, 8,9,10, 14 };
			    qMMultiList.setColsEnabled(col, true);
	  
				//CCENd SS21
		
			}
			
			//CCEnd SS20
			else {
				int col[] = { 1, 2, 3, 4, 5, 6, 7, 8 };
				qMMultiList.setColsEnabled(col, true);
			}	

            //CCEnd SS7

		} else if (routeMode.equals("productAndparentRelative")) {
			qmToolBar.setEnabled("part_copy", true);
            //CCBegin SS7
			if(comp.equals("zczx")){
			 int col[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,14};
			 qMMultiList.setColsEnabled(col, true);
			}//CCBegin SS20
			else if(comp.equals("ct")) {
				int col[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,14,15};
				qMMultiList.setColsEnabled(col, true);
			}
			//CCEnd SS20
			else{
            int col[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        	qMMultiList.setColsEnabled(col, true);
			}
            //CCEnd SS7
		} else if (routeMode.equals("partRelative")) {
			qmToolBar.setEnabled("partM_startProductManager", true);
			qmToolBar.setEnabled("part_copy", true);
			qmToolBar.setEnabled("changeNotice", true);
			qmToolBar.setEnabled("part_openIcon", true);
            //CCBegin SS6
            qmToolBar.setEnabled("partM_aptProjectManager", true);
            //CCEnd SS6
			 //CCBegin SS7
            if(comp.equals("zczx")){
            	int col[] = {1, 2, 3, 4, 5, 6,9,10,11};
            	qMMultiList.setColsEnabled(col, true);
            }//CCBegin SS20
			else if(comp.equals("ct")) {
				//CCBegin SS21
				int col[] = { 1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14 };
				qMMultiList.setColsEnabled(col, true);
	           
			}
          //CCENd SS21
			//CCBegin SS40
			else if(comp.equals("cd"))
			{
				//CCBegin SS43
//				int col[] = { 1, 2, 3, 4, 5, 6, 9, 10, 12,13};
				int col[] = { 1, 2, 3, 4, 5, 6, 9, 10, 12,13};
				//CCEnd SS43
				qMMultiList.setColsEnabled(col, true);
			}
			//CCEnd SS40
			//CCEnd SS20
            else{
               int col[] = {1, 2, 3, 4, 5, 6,9,10};
               qMMultiList.setColsEnabled(col, true);
            } 
          //CCEnd SS7
		} else if (routeMode.equals("parentRelative")) {
			qmToolBar.setEnabled("partM_startProductManager", true);
			qmToolBar.setEnabled("part_copy", true);
			qmToolBar.setEnabled("changeNotice", true);
			qmToolBar.setEnabled("part_openIcon", true);
            //CCBegin SS6
            qmToolBar.setEnabled("partM_aptProjectManager", true);
            //CCEnd SS6
            //CCBegin SS7
            if(comp.equals("zczx")){
              int col[] = {1, 2, 3, 4, 5, 6, 7, 8,13};
              qMMultiList.setColsEnabled(col, true);
           }//CCBegin SS20
			else if(comp.equals("ct")) {
				 int col[] = {1, 2, 3, 4, 5, 6, 7, 8,13,14};
				qMMultiList.setColsEnabled(col, true);
			}
			//CCEnd SS20
            else{
             int col[] = {1, 2, 3, 4, 5, 6, 7, 8};
             qMMultiList.setColsEnabled(col, true);
           }
            //CCEnd SS7
			// CR11 end
		}
		// CR7 end
		// begin 20120105 xucy add
         
		qmToolBar.setEnabled("part_view", false);
		qmToolBar.setEnabled("route_editGraph", false);
		qmToolBar.setEnabled("route_parentPart", false);
		// CR21
		qmToolBar.setEnabled("routeCode_delete", false);
		qmToolBar.setEnabled("part_deletestr", false);
		qmToolBar.setEnabled("public_moveUp", false);
		qmToolBar.setEnabled("public_moveDown", false);

		qmToolBar.setEnabled("public_copy", false);
		qmToolBar.setEnabled("public_paste", false);
		qmToolBar.setEnabled("route_delete", false);
		qmToolBar.setEnabled("open", true);
		// qmToolBar.setEnabled("route_selectPart", true);
		qmToolBar.setEnabled("routeM", false);
		qmToolBar.setEnabled("part_applyAll", false);
		//CCBegin SS19
        if(comp.equals("ct")){
            qmToolBar.setEnabled("supplier", true);
            int n=listPanel.getViewMode();
            if(routeListInfo!=null){   	
            	if(routeListInfo.getRouteListLevel().equals("һ��·��")){
            		 qmToolBar.setEnabled("searthLevelOne", false);
            	}else{
            		qmToolBar.setEnabled("searthLevelOne", true);
            	}
            }else{
            if(listPanel.levelJComboBox.getSelectedItem().equals("һ��·��")){
            	 qmToolBar.setEnabled("searthLevelOne", false);
           
            }else{
            	qmToolBar.setEnabled("searthLevelOne", true);
            }
          }
        }
        //CCEnd SS19
		
		//CCBegin SS35
		if(comp.equals("cd"))
		{
			qmToolBar.setEnabled("searthLevelOne", false);
		}
		//CCEnd SS35
		// 20120106 xucy add
		// mode = this.UPDATE_MODE;
		countButton.setVisible(true);
		
    //CCBegin SS31
    if(comp.equals("cd"))
    {
    	multiChangeMarkButton.setVisible(true);
    }
    //CCEnd SS31
	
	}

	/**
	 * ���ý��湤������ť����
	 */
	public void setCompamentEnable() {
		// CR7 begin
		if (routeMode.equals("productRelative")) {
			// CR11 begin
			qmToolBar.setEnabled("part_copy", true);
			// CCBegin SS2
			if (listPanel.stateJComboBox.getSelectedItem().toString().equals(
					"�ձ�")) {
				   //CCBegin SS7
				   if(comp.equals("zczx")){
			        int col[] = {1, 2, 3, 4, 5, 6, 7, 8,12};
			        qMMultiList.setColsEnabled(col, false);
				   }//CCBegin SS20
					else if(comp.equals("ct")) {
						int col[] = { 1, 2, 3, 4, 5, 6, 7, 8, 12,13 };
						qMMultiList.setColsEnabled(col, false);
					}
					//CCEnd SS20
				   else{
			        int col[] = {1, 2, 3, 4, 5, 6, 7, 8};
			        qMMultiList.setColsEnabled(col, false);
				   }	
			   
			      //CCEnd SS7
						
				// CCEnd SS3
			} else {
				// CCBegin SS3
				   //CCBegin SS7
				   if(comp.equals("zczx")){
			        int col[] = {1, 2, 3, 4, 5, 6, 7, 8,12};
			        qMMultiList.setColsEnabled(col, true);
				   }//CCBegin SS20
					else if(comp.equals("ct")) {
						int col[] = { 1, 2, 3, 4, 5, 6, 7, 8, 12,13 };
						qMMultiList.setColsEnabled(col, true);
					}
					//CCEnd SS20
				   else{
			        int col[] = {1, 2, 3, 4, 5, 6, 7, 8};
			        qMMultiList.setColsEnabled(col, true);
				   }	
			   
			      //CCEnd SS7
						
				// CCEnd SS3
		
			}

			// CCEnd SS2
		} else if (routeMode.equals("productAndparentRelative")) {
			qmToolBar.setEnabled("part_copy", true);
            //CCBegin SS7
			if(comp.equals("zczx")){
			 int col[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,14};
			 qMMultiList.setColsEnabled(col, true);
			}//CCBegin SS20
			else if(comp.equals("ct")){
				 int col[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,14,15};
				 qMMultiList.setColsEnabled(col, true);
			}
			//CCEnd SS20
			else{
            int col[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        	qMMultiList.setColsEnabled(col, true);
			}
            //CCEnd SS7
		} else if (routeMode.equals("partRelative")) {
			qmToolBar.setEnabled("partM_startProductManager", true);
			qmToolBar.setEnabled("part_copy", true);
			qmToolBar.setEnabled("changeNotice", true);
			qmToolBar.setEnabled("part_openIcon", true);
            //CCBegin SS6
            qmToolBar.setEnabled("partM_aptProjectManager", true);
            //CCEnd SS6
			// CCBegin SS2
			if (listPanel.stateJComboBox.getSelectedItem().toString().equals(
					"�ձ�")) {
				 //CCBegin SS7
	            if(comp.equals("zczx")){
	            	int col[] = {1, 2, 3, 4, 5, 6,9,10,11};
	            	qMMultiList.setColsEnabled(col, false);
	            }//CCBegin SS20
	            else if(comp.equals("ct")){
	            	//CCBegin SS21  
	         
	            	int col[] = { 1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14 };
					qMMultiList.setColsEnabled(col, false);
	                	
	                
	            	//CCEnd SS21
	            }
	            //CCEnd SS20
	            else{
	               int col[] = {1, 2, 3, 4, 5, 6,9,10};
	               qMMultiList.setColsEnabled(col, false);
	            } 
	          //CCEnd SS7
			} else {
				 //CCBegin SS7
	            if(comp.equals("zczx")){
	            	int col[] = {1, 2, 3, 4, 5, 6,9,10,11};
	            	qMMultiList.setColsEnabled(col, true);
	            }//CCBegin SS20
	            else if(comp.equals("ct")){
	            	//CCBegin SS21
	        
	            		int col[] = {1, 2, 3, 4, 5, 6,7,8,11,12,13,14};
		            	qMMultiList.setColsEnabled(col, true);
	            	
	            }
	            //CCEnd SS20
			//CCBegin SS40
			else if(comp.equals("cd"))
			{
				//CCBegin SS43
//				int col[] = { 1, 2, 3, 4, 5, 6, 9, 10, 12};
				int col[] = { 1, 2, 3, 4, 5, 6, 9, 10, 12,13};
				//CCEnd SS43
				qMMultiList.setColsEnabled(col, true);
			}
			//CCEnd SS40
	            else{
	               int col[] = {1, 2, 3, 4, 5, 6,9,10};
	               qMMultiList.setColsEnabled(col, true);
	            } 
	          //CCEnd SS7
			}
			// CCEnd SS2
		} else if (routeMode.equals("parentRelative")) {
			qmToolBar.setEnabled("partM_startProductManager", true);
			qmToolBar.setEnabled("part_copy", true);
			qmToolBar.setEnabled("changeNotice", true);
			qmToolBar.setEnabled("part_openIcon", true);
            //CCBegin SS6
            qmToolBar.setEnabled("partM_aptProjectManager", true);
            //CCEnd SS6
            //CCBegin SS7
            if(comp.equals("zczx")){
              int col[] = {1, 2, 3, 4, 5, 6, 7, 8,13};
              qMMultiList.setColsEnabled(col, true);
           }//CCBegin SS20
            else if(comp.equals("ct")){
            	int col[] = {1, 2, 3, 4, 5, 6, 7, 8,13,14};
            	qMMultiList.setColsEnabled(col, true);
            }
            //CCEnd SS20
            else{
             int col[] = {1, 2, 3, 4, 5, 6, 7, 8};
             qMMultiList.setColsEnabled(col, true);
           }
            //CCEnd SS7
			// CR11 end
		}
		// CR7 end
		// begin 20120105 xucy add

		qmToolBar.setEnabled("part_view", true);
		qmToolBar.setEnabled("route_editGraph", true);
		qmToolBar.setEnabled("route_parentPart", true);
		// CR21
		qmToolBar.setEnabled("routeCode_delete", true);
		qmToolBar.setEnabled("part_deletestr", true);
		qmToolBar.setEnabled("public_moveUp", true);
		qmToolBar.setEnabled("public_moveDown", true);

		qmToolBar.setEnabled("public_copy", true);
		qmToolBar.setEnabled("public_paste", true);
		qmToolBar.setEnabled("route_delete", true);
		qmToolBar.setEnabled("open", true);
		// qmToolBar.setEnabled("route_selectPart", true);
		qmToolBar.setEnabled("routeM", true);
		qmToolBar.setEnabled("part_applyAll", true);
		//CCBegin SS19 
        if(comp.equals("ct")){

            qmToolBar.setEnabled("supplier", true);
            int n=listPanel.getViewMode();
            if(routeListInfo!=null){
            	System.out.println("routeListInfo.getRouteListLevel()=="+routeListInfo.getRouteListLevel());
            	if(routeListInfo.getRouteListLevel().equals("һ��·��")){
            		 qmToolBar.setEnabled("searthLevelOne", false);
            	}else{
            		qmToolBar.setEnabled("searthLevelOne", true);
            	}
            }else{
            if(listPanel.levelJComboBox.getSelectedItem().equals("һ��·��")){
            	 qmToolBar.setEnabled("searthLevelOne", false);
           
            }else{
            	qmToolBar.setEnabled("searthLevelOne", true);
            }
          }
        
          }
        //CCEnd SS19
		
		//CCBegin SS35
		if(comp.equals("cd"))
		{
			qmToolBar.setEnabled("searthLevelOne", false);
		}
		//CCEnd SS35
		// 20120106 xucy add
	}

	/**
	 * ִ�дӲ�Ʒ�ṹ����Ӳ���
	 * 
	 * @param e
	 *            ActionEvent
	 */
	void addStructJButton_actionPerformed(ActionEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		addConstructPart();
		setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * ִ����ͨ�������
	 * 
	 * @param e
	 *            ActionEvent
	 */
	void addJButton_actionPerformed(ActionEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		addPart();
		setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * ִ�����������Ĺ���
	 * 
	 * @param e
	 *            ActionEvent �в���֮��
	 */
	void parentPartJButton_actionPerformed(ActionEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		parentPart();
		setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * ɾ����ѡ����㲿��
	 * 
	 * @param e
	 *            ActionEvent
	 */
	void removeJButton_actionPerformed(ActionEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		deletePart();
		setCursor(Cursor.getDefaultCursor());
	}

	/** ���ñ�ѡ�е��� */
	public void setSelectedNum(int arg) {
		theSelectedNum = arg;
	}

	/** ��ȡ��ѡ�е��� */
	public int getSelectedNum() {
		return theSelectedNum;
	}

	/**
	 * ִ�������Ʋ��� ʹ��MiltiList�ķ���ȡ��ѡ���� ����Ҫ�޸ĵ��� �ж�Ҫ�����Ƶ����Ƿ񵽴�����߽� Added by Ginger
	 * 05/05/08
	 */
	private void upRow() {
		int selectedRow = qMMultiList.getSelectedRow();
		int changedRow = selectedRow - 1;
		if (selectedRow < 0) {

			DialogFactory.showWarningDialog(getParentJFrame(), "��ѡ�����");
			setCursor(Cursor.getDefaultCursor());
			return;
		}
		if (selectedRow != 0) {
			// upJButton.enableInputMethods(false);
			// }else
			// {
			qMMultiList.changeRow(selectedRow, changedRow);
		}
		if (selectedRow-- <= 0) {
			selectedRow = 0;
		}
		qMMultiList.deselectAll();
		qMMultiList.selectRow(selectedRow);
	}

	private void downRow() {
		int selectedRow = qMMultiList.getSelectedRow();
		if (selectedRow < 0) {
			DialogFactory.showWarningDialog(getParentJFrame(), "��ѡ�����");
			setCursor(Cursor.getDefaultCursor());
			return;
		}
		int changedRow = selectedRow + 1;
		int totalRowNumber = qMMultiList.getNumberOfRows();
		if (selectedRow != (totalRowNumber - 1)) {
			// downJButton.enableInputMethods(false);
			// }else
			// {
			qMMultiList.changeRow(selectedRow, changedRow);
		}
		if (selectedRow++ >= (totalRowNumber - 1)) {
			selectedRow = totalRowNumber - 1;
		}
		qMMultiList.deselectAll();
		qMMultiList.selectRow(selectedRow);
	}

	// /**
	// * ִ�������¼�
	// * @param e ActionEvent
	// */
	// private void upJButton_actionPerformed(ActionEvent e)
	// {
	// setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	// upRow();
	// setCursor(Cursor.getDefaultCursor());
	// }
	//
	// /**
	// * ִ�������¼�
	// * @param e ActionEvent
	// */
	// private void downJButton_actionPerformed(ActionEvent e)
	// {
	// setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	// downRow();
	// setCursor(Cursor.getDefaultCursor());
	// }

	// CR3 Begin
	// public void setIschangFlag(boolean flag)
	// {
	//
	// this.isChangeRouteList = flag;
	//
	// }
	//
	public void cleanMutlist() {

		this.qMMultiList.clear();

	}

	// CR3 End
	// CR4 begin
	protected ResourceBundle getPropertiesRB() {
		if (resource == null) {
			initResources();
		}
		return resource;
	}

	/**
	 * ��ʼ����ʹ�õ���Դ����Ϣ��
	 */
	protected void initResources() {
		try {
			// skybird
			if (resource == null) {
				resource = ResourceBundle.getBundle(RESOURCE, QMCt.getContext()
						.getLocale());
			}
		} catch (MissingResourceException mre) {
			String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
					null);
			JOptionPane.showMessageDialog(null, CappRouteRB.MISSING_RESOURCER,
					title, JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	/**
	 * ��ResourceBundle��ȡ��key �������StringTokenizer ��󱣴��String[]
	 * 
	 * @param rb
	 * @param key
	 * @return String[]
	 */
	protected String[] getValueSet(ResourceBundle rb, String key) {
		String[] values = null;
		try {
			String value = rb.getString(key);
			// The string tokenizer class allows an application to break
			// a string into tokens
			StringTokenizer st = new StringTokenizer(value, ",");

			int count = st.countTokens();
			values = new String[count];

			for (int i = 0; i < count; i++) {
				values[i] = st.nextToken();
			}
		} catch (MissingResourceException mre) {
			mre.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}

	/**
	 * ��ð�ť��Ϣ��ʾ����,����������
	 * 
	 * @param as1
	 * @param as2
	 * @param as3
	 *            3����������
	 */
	private void setTools(String as1[], String as2[], String as3[]) {
		String myTools[] = as1;
		for (int i = 0; i < myTools.length; i++) {
			//CCBegin SS24
			//ֻ�г����û������ӹ�Ӧ�̰�ť
			if((myTools[i]!=null)&&(myTools[i].equals("supplier"))){
				if(comp.equals("ct")){
					qmToolBar.addButton(myTools[i], as2[i], as3[i], this, null);
				}
			}else{
				qmToolBar.addButton(myTools[i], as2[i], as3[i], this, null);
			}
			//CCEnd SS24
			
		}
		for (int ii = 0; ii < qmToolBar.getComponentCount(); ii++) {
			if (qmToolBar.getComponentAtIndex(ii) instanceof JButton) {
				JButton jb = (JButton) (qmToolBar.getComponentAtIndex(ii));
				jb.setBorder(BorderFactory.createEtchedBorder());
				// begin 20120111 XUCY ADD ��������ť��ctrl��ݼ�
				String name = jb.getActionCommand();
				if (name.equals("partM_startProductManager")) {
					ButtonCtrlHotKey.addHotKey(jb, KeyEvent.VK_P);
					// jb.setMnemonic('P');
				} else if (name.equals("part_copy")) {
					ButtonCtrlHotKey.addHotKey(jb, KeyEvent.VK_B);
					// jb.setMnemonic('B');
				} else if (name.equals("changeNotice")) {
					ButtonCtrlHotKey.addHotKey(jb, KeyEvent.VK_T);
					// jb.setMnemonic('N');
				} else if (name.equals("part_openIcon")) {
					ButtonCtrlHotKey.addHotKey(jb, KeyEvent.VK_J);
					// jb.setMnemonic('J');
                //CCBegin SS6
                }else if(name.equals("partM_aptProjectManager"))
                {
                    ButtonCtrlHotKey.addHotKey(jb, KeyEvent.VK_N);
                    //jb.setMnemonic('V');
                //CCEnd SS6
				} else if (name.equals("part_view")) {
					ButtonCtrlHotKey.addHotKey(jb, KeyEvent.VK_W);
					// jb.setMnemonic('V');
				} else if (name.equals("open")) {
					ButtonCtrlHotKey.addHotKey(jb, KeyEvent.VK_Z);
					// jb.setMnemonic('V');
				} else if (name.equals("routeM")) {
					ButtonCtrlHotKey.addHotKey(jb, KeyEvent.VK_M);
					// jb.setMnemonic('V');
				} else if (name.equals("part_applyAll")) {
					ButtonCtrlHotKey.addHotKey(jb, KeyEvent.VK_Y);
					// jb.setMnemonic('V');
				}
				
				//CCbegin SS19
				else if(name.equals("supplier")){
					ButtonCtrlHotKey.addHotKey(jb, KeyEvent.VK_G);
				}else if(name.equals("searthLevelOne")){
					ButtonCtrlHotKey.addHotKey(jb, KeyEvent.VK_0);
				}
				//CCEnd SS19
				
			}
		}
	}

	/**
	 * ʵ�ֹ�������ť�Ķ���
	 */  
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if (name.equals("partM_startProductManager")) {
			QQMultPartsSearchJDialog searchJDialog = new QQMultPartsSearchJDialog(
					this.getParentJFrame());
			searchJDialog.setVisible(true);
		} else if (name.equals("part_copy")) {
			if (this.theProductifc == null || theProductifc.equals("")) {
				DialogFactory.showInformDialog(this.getParentJFrame(),
						"����׼��û����Ӳ�Ʒ�����ܴӲ�Ʒ�ṹ����ӣ�");
				return;
			} else {
				String productID = theProductifc.getBsoID();
				addConstructPart();
			}
			setCursor(Cursor.getDefaultCursor());
		} else if (name.equals("changeNotice")) {
			QQAdoptChangePartSearchJDialog qqAdoptChange = new QQAdoptChangePartSearchJDialog(
					this.getParentJFrame());
			qqAdoptChange.setVisible(true);
		} else if (name.equals("part_openIcon")) {
				SearchStructurePartJDialog p = new SearchStructurePartJDialog(this
						.getParentJFrame());
				p.setVisible(true);
        //CCBegin SS6
        }else if(name.equals("partM_aptProjectManager"))
        {
        
            JFRouteListSearchJDialog p = new JFRouteListSearchJDialog();
            p.setVisible(true);
        //CCEnd SS6
		} else if (name.equals("part_view")) {
			this.viewPart();
		}
		//CCBegin SS19
		else if (name.equals("supplier")) {
			int index = qMMultiList.getSelectedRow();
			RouteWrapData routeData = this.getSelectedPartData(index);
			String partNum = routeData.getPartNum();
			EditSupplierJDialog p = new EditSupplierJDialog(this
					.getParentJFrame(), this, partNum);
	            p.setVisible(true);
		}
		else if(name.equals("searthLevelOne")){
			CTRouteLevelOnePartSearchJDialog p = new CTRouteLevelOnePartSearchJDialog(this
					.getParentJFrame(), this);
	            p.setVisible(true);
		}
		//CCEnd SS19
		// begin 20120106 xucy add
		else if (name.equals("route_editGraph")) {
			// �༭·��ͼ
			processEditRouteGraph();
		} else if (name.equals("route_parentPart")) {
			// �鿴װ��λ�ã�·���븸���й�ʱ��������㲿���ĸ�����Ϣ
			parentPart();
		} else if (name.equals("routeCode_delete")) {
			// CR21
			deleteParentPart();
		} else if (name.equals("part_deletestr")) {
			// �����Ƴ�����е������������׼ʱɾ����·��
			deletePart();
		} else if (name.equals("public_moveUp")) {
			upRow();
		} else if (name.equals("public_moveDown")) {
			this.downRow();
		} else if (name.equals("public_copy")) {
			copy();
		} else if (name.equals("public_paste")) {
			paste();
		} else if (name.equals("route_delete")) {
			// 20120113 xucy add ����ɾ�����·��
			deleteRoute();
		} else if (name.equals("open")) {
			int index = qMMultiList.getSelectedRow();
			String partNum = "";
			if (index != -1) {
				RouteWrapData routeData = this.getSelectedPartData(index);
				partNum = routeData.getPartNum();
			}
			RouteCopyFromJDialog copyfromdialog = new RouteCopyFromJDialog(this
					.getParentJFrame(), this, partNum);
			copyfromdialog.setVisible(true);
			// setCursor(Cursor.getDefaultCursor());

		} else if (name.equals("routeM")) {
			saveModelRoute();
		} else if (name.equals("part_applyAll")) {
			useModelRoute();
		}
		if (this.getViewMode() != VIEW_MODE) {
			// Ӧ�ö���Ŀ��·�� 20120118 xucy add
			if (name.equals("Ctrl_1")) {
				// ��ȡ�û��Ŀ��·����Ϣ
				HashMap shortCutRoute = RouteClientUtil.getShortCutRoute();
				String routeStr = (String) shortCutRoute.get("Ctrl+1");
				// Ӧ�ö���Ŀ��·��
				applyShortCutRoute("Ctrl+1", routeStr);
			} else if (name.equals("Ctrl_2")) {
				// ��ȡ�û��Ŀ��·����Ϣ
				HashMap shortCutRoute = RouteClientUtil.getShortCutRoute();
				String routeStr = (String) shortCutRoute.get("Ctrl+2");
				// Ӧ�ö���Ŀ��·��
				applyShortCutRoute("Ctrl+2", routeStr);
			} else if (name.equals("Ctrl_3")) {
				// ��ȡ�û��Ŀ��·����Ϣ
				HashMap shortCutRoute = RouteClientUtil.getShortCutRoute();
				String routeStr = (String) shortCutRoute.get("Ctrl+3");
				// Ӧ�ö���Ŀ��·��
				applyShortCutRoute("Ctrl+3", routeStr);
			} else if (name.equals("Ctrl_4")) {
				// ��ȡ�û��Ŀ��·����Ϣ
				HashMap shortCutRoute = RouteClientUtil.getShortCutRoute();
				String routeStr = (String) shortCutRoute.get("Ctrl+4");
				// Ӧ�ö���Ŀ��·��
				applyShortCutRoute("Ctrl+4", routeStr);
			} else if (name.equals("Ctrl_5")) {
				// ��ȡ�û��Ŀ��·����Ϣ
				HashMap shortCutRoute = RouteClientUtil.getShortCutRoute();
				String routeStr = (String) shortCutRoute.get("Ctrl+5");
				// Ӧ�ö���Ŀ��·��
				applyShortCutRoute("Ctrl+5", routeStr);
			} else if (name.equals("Ctrl_6")) {
				// ��ȡ�û��Ŀ��·����Ϣ
				HashMap shortCutRoute = RouteClientUtil.getShortCutRoute();
				String routeStr = (String) shortCutRoute.get("Ctrl+6");
				// Ӧ�ö���Ŀ��·��
				applyShortCutRoute("Ctrl+6", routeStr);
			} else if (name.equals("Ctrl_7")) {
				// ��ȡ�û��Ŀ��·����Ϣ
				HashMap shortCutRoute = RouteClientUtil.getShortCutRoute();
				String routeStr = (String) shortCutRoute.get("Ctrl+7");
				// Ӧ�ö���Ŀ��·��
				applyShortCutRoute("Ctrl+7", routeStr);
			} else if (name.equals("Ctrl_8")) {
				// ��ȡ�û��Ŀ��·����Ϣ
				HashMap shortCutRoute = RouteClientUtil.getShortCutRoute();
				String routeStr = (String) shortCutRoute.get("Ctrl+8");
				// Ӧ�ö���Ŀ��·��
				applyShortCutRoute("Ctrl+8", routeStr);
			} else if (name.equals("Ctrl_9")) {
				// ��ȡ�û��Ŀ��·����Ϣ
				HashMap shortCutRoute = RouteClientUtil.getShortCutRoute();
				String routeStr = (String) shortCutRoute.get("Ctrl+9");
				// Ӧ�ö���Ŀ��·��
				applyShortCutRoute("Ctrl+9", routeStr);
			}
		}
		// end 20120106 xucy add
	}

	// CR4 end

	/**
	 * �鿴�㲿��
	 */
	private void viewPart() {
		int index = qMMultiList.getSelectedRow();
		if (index != -1) {
			// ���ѡ���㲿����ID
			String masterID = qMMultiList.getCellAt(index, 0).toString();
			Class[] cla = { String.class };
			Object[] obj = { masterID };
			Object myObject = null;
			try {
				// ��ȡ���ϵ�ǰ���ù淶�����
				//CCBegin SS13
				if(masterID.indexOf("QMPartMaster")!=-1)
				//CCEnd SS13
				myObject = RequestHelper.request("consTechnicsRouteService",
						"filteredIterationsOf", cla, obj);
				//CCBegin SS13
				else
				myObject = (BaseValueIfc)refreshInfo(masterID);
				//CCEnd SS13
			} catch (Exception e) {
				String message = e.getMessage();
				DialogFactory.showInformDialog(this.getParentJFrame(), message);
			}
			if (myObject == null) {
				return;
			}
			if ((BaseValueIfc) myObject instanceof QMPartIfc) {
				String bsoID = ((BaseValueIfc) myObject).getBsoID();
				HashMap hashmap = new HashMap();
				hashmap.put("bsoID", bsoID);
				String hasPcfg = com.faw_qm.framework.remote.RemoteProperty
						.getProperty("com.faw_qm.hasPcfg", "true");
				hashmap.put("infoD", hasPcfg);
				// ת��ҳ��鿴�㲿�����ԡ�
				RichToThinUtil.toWebPage("Part-Other-PartLookOver-001-0A.do",
						hashmap);
				bsoID = null;
				hashmap = null;
			}
		} else {
			DialogFactory.showWarningDialog(this.getParentJFrame(), "��ѡ�����");
			setCursor(Cursor.getDefaultCursor());
			return;
		}
	}

	// begin CR8
	/**
	 * xucy 20120116 add ��ÿͻ��˵������Ϣ
	 * 
	 * @return ���·����Ϣ����
	 */
	public ArrayList getSaveRouteMap() {
		HashMap map = new HashMap();
		// ��������е����ݼ���
		ArrayList list = new ArrayList();
		QMPartIfc parent = null;
		try {
			int rowCount = qMMultiList.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				String partMasterID = qMMultiList.getCellText(i, 0);
				// ��ȡһ�е�����ģ��
				RouteWrapData routeWrapData = getOnePartSaveData(i);
				RouteWrapData routeWrapData1 = null;
				// 20120405
				// if(routeMode.equals("partRelative"))
				// {
				// routeWrapData1 = (RouteWrapData)saveMap.get(partMasterID);
				// }else if(routeMode.equals("productRelative"))
				// {
				// routeWrapData1 = (RouteWrapData)saveMap.get(partMasterID +
				// this.theProductifc.getPartNumber());
				// }else
				if (routeMode.equals("parentRelative")) {
					String parentNum = qMMultiList.getCellText(i, 4);
					// routeWrapData1 = (RouteWrapData)saveMap.get(partMasterID
					// + parentNum);
					parent = (QMPartIfc) saveMap.get(partMasterID + parentNum);

				} else if (routeMode.equals("productAndparentRelative")) {
					String parentNum = qMMultiList.getCellText(i, 4);
					parent = (QMPartIfc) saveMap.get(partMasterID
							+ this.theProductifc.getPartNumber() + parentNum);
				}
				// if(routeWrapData1 != null)
				// {
				// routeWrapData.setProduct(routeWrapData1.getProduct());
				// routeWrapData.setProductCount(routeWrapData1.getProductCount());
				routeWrapData.setParent(parent);
				// }
				list.add(routeWrapData);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			String message = ex.getMessage();
			DialogFactory.showInformDialog(this.getParentJFrame(), message);
			setCursor(Cursor.getDefaultCursor());
			// return new HashMap();
		}
		return list;
	}

	/**
	 * xucy 20120116 add ���ѡ�������������ݣ�������װ���·��map��Ϣ
	 * 
	 * @return ���·����Ϣ����
	 */
	/**
	 * xucy 20120116 add ���ѡ�������������ݣ�������װ���·��map��Ϣ
	 * 
	 * @return ���·����Ϣ����
	 */
	public RouteWrapData getOnePartSaveData(int row) {
		// ��װ�����·����Ϣ
		RouteWrapData routeWrapData = new RouteWrapData();
		try {
			boolean indenty = false;
			String parentNum = "";
			String parentName = "";
			String mainStr = "";
			String secondStr = "";
			String description = "";
			String countStr = "";
			String routeID = "";
			String linkID = "";
			String partMasterID = qMMultiList.getCellText(row, 0);
			String codeContent = (String) qMMultiList.getComboBoxObject(row, 1);
			String partNum = qMMultiList.getCellText(row, 2);
			String partName = qMMultiList.getCellText(row, 3);
			//CCBeginSS7
			String stockID="";
			//CCend SS7
			//CCBegin SS20
			String supplier="";
			String supplierBsoId="";
			//CCEnd SS20
			//CCBegin SS9
			String partVersion="";
			//CCEnd SS9
			routeWrapData.setPartMasterID(partMasterID);
			routeWrapData.setModifyIdenty(codeContent);
			routeWrapData.setPartNum(partNum);
			routeWrapData.setPartName(partName);
			if (routeMode.equals("partRelative")) {
				mainStr = qMMultiList.getCellText(row, 4);
				secondStr = qMMultiList.getCellText(row, 5);
				description = qMMultiList.getCellText(row, 6);
				routeID = qMMultiList.getCellText(row, 7);
				linkID = qMMultiList.getCellText(row, 8);
				//CCBegin SS21 
				if(comp.equals("ct")){
					countStr = qMMultiList.getCellText(row, 12);
					//CCBegin SS7
					stockID=(String)qMMultiList.getComboBoxObject(row, 13);
					partVersion=(String)qMMultiList.getCellText(row,11);
				}
				//CCBegin SS32
				else if(comp.equals("cd"))
				{
					countStr = qMMultiList.getCellText(row, 10);
					stockID="";
					partVersion=(String)qMMultiList.getCellText(row,9);
					
				}
				//CCEnd SS32
				else{
					countStr = qMMultiList.getCellText(row, 10);
					//CCBegin SS7
					stockID=(String)qMMultiList.getComboBoxObject(row, 11);
					partVersion=(String)qMMultiList.getCellText(row,9);
				}
				//CCend SS21
				routeWrapData.setStockID(stockID);
				//CCend SS7
				//CCBegin SS9
			
				routeWrapData.setPartVersion(partVersion);
				//CCEnd SS9
				if (countStr != null && !countStr.equals(""))
					routeWrapData.setProductCount(Integer.valueOf(countStr)
							.intValue());
				// routeWrapData.setProductIndenty(indenty);
				//CCBegin SS20 
            	if(comp.equals("ct")){
            		//CCBegin SS21	
	                supplier=(String)qMMultiList.getCellText(row, 14);
	       		    supplierBsoId=(String)qMMultiList.getCellText(row, 15);		
	                	
            	}
            	//CCEnd SS21
            	routeWrapData.setSupplier(supplier);
				routeWrapData.setSupplierBsoId(supplierBsoId);
				//CCEnd SS20
			} else if (routeMode.equals("productRelative")) {
				mainStr = qMMultiList.getCellText(row, 4);
				secondStr = qMMultiList.getCellText(row, 5);
				description = qMMultiList.getCellText(row, 6);
				countStr = qMMultiList.getCellText(row, 7);
				routeWrapData.setProductCount(Integer.valueOf(countStr)
						.intValue());
				indenty = ((Boolean) qMMultiList.getSelectedObject(row, 8))
						.booleanValue();
				routeID = qMMultiList.getCellText(row, 9);
				linkID = qMMultiList.getCellText(row, 10);
				//CCBegin SS7
				stockID=(String)qMMultiList.getComboBoxObject(row, 12);
				routeWrapData.setStockID(stockID);
				//CCend SS7
				//CCBegin SS20
				supplier=(String)qMMultiList.getCellText(row, 13);
				supplierBsoId=(String)qMMultiList.getCellText(row, 14);
				routeWrapData.setSupplier(supplier);
				routeWrapData.setSupplierBsoId(supplierBsoId);
				//CCEnd SS20
			} else if (routeMode.equals("parentRelative")) {
				parentNum = qMMultiList.getCellText(row, 4);
				parentName = qMMultiList.getCellText(row, 5);
				mainStr = qMMultiList.getCellText(row, 6);
				secondStr = qMMultiList.getCellText(row, 7);
				description = qMMultiList.getCellText(row, 8);
				routeID = qMMultiList.getCellText(row, 9);
				linkID = qMMultiList.getCellText(row, 10);
				//CCBegin SS7
				stockID=(String)qMMultiList.getComboBoxObject(row, 13);
				routeWrapData.setStockID(stockID);
		
				//CCend SS7
				//CCBegin SS20
				supplier=(String)qMMultiList.getCellText(row, 14);
				supplierBsoId=(String)qMMultiList.getCellText(row, 15);
				routeWrapData.setSupplier(supplier);
				routeWrapData.setSupplierBsoId(supplierBsoId);
				//CCEnd SS20

			} else if (routeMode.equals("productAndparentRelative")) {
				parentNum = qMMultiList.getCellText(row, 4);
				parentName = qMMultiList.getCellText(row, 5);
				mainStr = qMMultiList.getCellText(row, 6);
				secondStr = qMMultiList.getCellText(row, 7);
				description = qMMultiList.getCellText(row, 8);
				countStr = qMMultiList.getCellText(row, 9);
				routeWrapData.setProductCount(Integer.valueOf(countStr)
						.intValue());
				indenty = ((Boolean) qMMultiList.getSelectedObject(row, 10))
						.booleanValue();
				routeID = qMMultiList.getCellText(row, 11);
				linkID = qMMultiList.getCellText(row, 12);
				//CCBegin SS7
				stockID=(String)qMMultiList.getComboBoxObject(row, 14);
				routeWrapData.setStockID(stockID);
				//CCend SS7

				//CCBegin SS20
				supplier=(String)qMMultiList.getCellText(row, 15);
				supplierBsoId=(String)qMMultiList.getCellText(row, 16);
				routeWrapData.setSupplier(supplier);
				routeWrapData.setSupplierBsoId(supplierBsoId);
				//CCEnd SS20
			}
			//CCBegin SS17
			if(listPanel.stateJComboBox.getSelectedItem().toString().equals("�ձ�") && comp.equals("zczx")){
				Class[] c = {String.class};
				Object[] objs = {partMasterID};
				Collection col = (Collection)RequestHelper.request("consTechnicsRouteService", "selectPartRoute", c, objs);
				Iterator iter1 = col.iterator();
				if(iter1.hasNext())
				{
					ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkIfc)iter1.next();
					mainStr = listLinkInfo1.getMainStr();
					secondStr = listLinkInfo1.getSecondStr();
					//CCBegin SS18
					routeWrapData.setModifyIdenty(listLinkInfo1.getModifyIdenty());
					routeWrapData.setStockID(listLinkInfo1.getStockID());
					//CCEnd SS18
				}
			}
			//CCEnd SS17
			routeWrapData.setMainStr(mainStr);

			routeWrapData.setSecondStr(secondStr);
			routeWrapData.setDescription(description);
			routeWrapData.setProductIndenty(indenty);
			routeWrapData.setParentNum(parentNum);
			routeWrapData.setParentName(parentName);
			routeWrapData.setRouteID(routeID);
			routeWrapData.setLinkID(linkID);
			//CCBegin SS40
			if(comp.equals("cd"))
			{
				boolean colorFlag=(Boolean)qMMultiList.getSelectedObject(row, 12);
				if(colorFlag==true)
				{
					routeWrapData.setColorFlag("1");
				}else
				{
					routeWrapData.setColorFlag("0");
				}
//				CCBegin SS43
				boolean specialFlag=(Boolean)qMMultiList.getSelectedObject(row, 13);
				if(specialFlag==true)
				{
					routeWrapData.setSpecialFlag("1");
				}else
				{
					routeWrapData.setSpecialFlag("0");
				}
//				CCEnd SS43
			}
			//CCEnd SS40
			
			// CR20 begin
			if (indenty == true) {
				routeWrapData.setProduct(this.theProductifc);
			}
			// CR20 end
			// routeWrapData.setProductCount(productCount);
			// ��������·����ͨ��·��ͼ�����򻺴��·��ͼ������ȡֵ
			Object[] objA = (Object[]) this.multiPartPicMaps.get(partMasterID);
			if (objA != null && objA.length > 0) {
				String[] routeStr = (String[]) objA[0];
				boolean flag1 = RouteClientUtil.compareRouteStr(mainStr,
						routeStr[0]);
				boolean flag2 = RouteClientUtil.compareRouteStr(secondStr,
						routeStr[1]);
				// �����Ҫ·�ߴ��ʹ�Ҫ·�ߴ���һ���仯�ˣ���ô�Ͳ����ֹ��༭�ķ�ʽ�洢
				if (!flag1 || !flag2) {
					Object[] routeObj = this.getPartHandRouteMap(mainStr,
							secondStr);
					// �����Ҫ·����Ϣ���ڣ�����·�߷�֧����·�߽ڵ㡢��֧��ڵ�������ڵ�������󣬽���Щ��Ϣ�ֱ�ŵ�����
					HashMap routeMap = (HashMap) routeObj[1];
					routeWrapData.setDepartmentVec((Object[]) routeObj[0]);
					routeWrapData.setRouteMap(routeMap);
				} else {
					HashMap mapA = (HashMap) objA[1];
					routeWrapData.setRouteMap(mapA);
					routeWrapData.setDepartmentVec(null);
				}
			}
			// ���·�����ֹ���д�ģ������·�ʽ����·����Ϣ����
			else {
				// �����Ҫ·����Ϣ���ڣ�����·�߷�֧����·�߽ڵ㡢��֧��ڵ�������ڵ�������󣬽���Щ��Ϣ�ֱ�ŵ�����
				if (mainStr != null && !mainStr.equals("")) {
					// �ֹ��༭·��ʱ����ȡ���·����Ϣ
					Object[] routeObj = this.getPartHandRouteMap(mainStr,
							secondStr);
					HashMap routeMap = (HashMap) routeObj[1];
					routeWrapData.setDepartmentVec((Object[]) routeObj[0]);
					routeWrapData.setRouteMap(routeMap);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			String message = ex.getMessage();
			DialogFactory.showInformDialog(this.getParentJFrame(), message);
			setCursor(Cursor.getDefaultCursor());
			// return new HashMap();
		}
		setCursor(Cursor.getDefaultCursor());
		return routeWrapData;
	}

	/**
	 * �����ֹ��༭��·����Ϣ
	 * 
	 * @param mainStr
	 * @param secondStr
	 * @return
	 */
	private Object[] getPartHandRouteMap(String mainStr, String secondStr) {
		// ����Ƕ���·�ߣ���ȡ����·�ߵ�λ����������
		Collection col = new Vector();
		if (listPanel.getRouteLevel().equals(
				RouteListLevelType.SENCONDROUTE.getDisplay())) {
			Collection col1 = RouteClientUtil
					.getAllChildColByDepartID(listPanel.getDepartmentID());
			Iterator it = col1.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				if (obj instanceof CodingClassificationIfc) {
					col.add(((CodingClassificationIfc) obj).getClassSort());
				} else if (obj instanceof CodingIfc) {
					col.add(((CodingIfc) obj).getShorten());
				}
			}
		}
		Object[] obj = new Object[2];
		// 20120210 xucy add ������ݿ��в����ڵ�·�ߵ�λ
		Object[] messageStr = new Object[2];
		StringBuffer buffer = new StringBuffer();
		StringBuffer buffer1 = new StringBuffer();
		// ��ŵ�����ĵ��������·����Ϣ
		HashMap routeMap = new HashMap();

		TechnicsRouteIfc routeInfo = null;
		// ����Ҫ�����·�߶��󣬽���ŵ�����
		if (routeMap.get(TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME) == null) {
			routeInfo = new TechnicsRouteInfo();
			// 20120110 xucy ע�� �ڴ˲�����·����Ϣ�����������ڹ�����Ϣ
			/*
			 * routeInfo.setRouteDescription(description);
			 * routeInfo.setModifyIdenty(codeContent);
			 */
			RouteItem routeItem = setRouteItem(routeInfo);
			// routeMap = new HashMap();
			// ����·��ӳ���
			routeMap.put(TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME,
					routeItem);
		} else {
			RouteItem routeItem = (RouteItem) routeMap
					.get(TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME);
			routeInfo = (TechnicsRouteIfc) routeItem.getObject();
		}
		// �����Ҫ·����Ϣ���ڣ�����·�߷�֧����·�߽ڵ㡢��֧��ڵ�������ڵ�������󣬽���Щ��Ϣ�ֱ�ŵ�����
		if (mainStr != null && !mainStr.equals("")) {
			int size = 0;
			StringTokenizer routeStrToken = null;
			// �����Ҫ·����Ϣ���ڣ����������Ϣ������·����ص���Ϣ����
			if (secondStr != null) {
				routeStrToken = new StringTokenizer(secondStr, ";");
				size = routeStrToken.countTokens();
			}
			String[] branches = new String[size + 1];
			branches[0] = mainStr;
			for (int m = 1; m < size + 1; m++) {
				branches[m] = routeStrToken.nextToken();
			}
			int count = 0;
			for (int n = 0; n < size + 1; n++) {
				count = count + 1;
				// ����·�߷�֧����
				TechnicsRouteBranchIfc routeBranchInfo = new TechnicsRouteBranchInfo();
				if (branches[n].equalsIgnoreCase(mainStr)) {
					routeBranchInfo.setMainRoute(true);
				} else {
					routeBranchInfo.setMainRoute(false);

				}
				routeBranchInfo.setRouteInfo(routeInfo);
				// ��÷�֧����
				Collection branchs = (Collection) routeMap
						.get(TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME);
				// ���÷�֧
				if (branchs == null) {
					branchs = new Vector();
					routeMap
							.put(
									TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME,
									branchs);
				}
				// ���ýڵ�
				Collection nodes = (Collection) routeMap
						.get(TechnicsRouteServiceEJB.ROUTENODE_BSONAME);
				if (nodes == null) {
					nodes = new Vector();
					routeMap.put(TechnicsRouteServiceEJB.ROUTENODE_BSONAME,
							nodes);
				}
 
				Vector tempNodeVector = new Vector();
		 		// ����ڵ�����
				int y = 60 * count;
				int x = 0;
				int length = branches[n].length();
				int position = branches[n].indexOf("=");
				String manuString = "";
				String assemString = "";
				// ��������·�߽ڵ㡣
				if (position != -1) {
					manuString = branches[n].substring(0, position);
					// ����װ��·�߽ڵ㡣
					assemString = branches[n].substring(position + 1, length);
				} else {
					manuString = branches[n];
				}
				// �洢·�߷�֧��·�ߴ���Ϣ
				String routeBranchStr = "";

				if (manuString != null && !manuString.equals("")) {
					routeBranchStr = manuString;
					Collection nodeString = new Vector();
					StringTokenizer token = new StringTokenizer(manuString, "-");
					while (token.hasMoreTokens()) {
						nodeString.add(token.nextToken());
					}
					for (Iterator manuIterator = nodeString.iterator(); manuIterator
							.hasNext();) {
						String manu = (String) manuIterator.next();
						RouteNodeIfc nodeInfo = new RouteNodeInfo();
						nodeInfo
								.setRouteType(RouteCategoryType.MANUFACTUREROUTE
										.getDisplay());
						String department = RouteClientUtil
								.getDepartmentID(manu);
						if (department == null) {
							buffer.append("���쵥λ��" + manu + "\n");
							continue;
						} else if (col.size() > 0 && !col.contains(manu)) {
							buffer1.append("���쵥λ��" + manu + "\n");
							continue;
						}
						nodeInfo.setNodeDepartment(RouteClientUtil
								.getDepartmentID(manu));
						nodeInfo.setNodeDepartmentName(manu);

						nodeInfo.setRouteInfo(routeInfo);
						x = x + 150;
						nodeInfo.setX(new Long(x).longValue());
						nodeInfo.setY(new Long(y).longValue());
						RouteItem nodeItem = setRouteItem(nodeInfo);
						nodes.add(nodeItem);
						tempNodeVector.add(nodeItem);
					}
				} else {
					routeBranchStr = "";

				}
				// ����װ��·�߽ڵ㡣
				if (assemString != null && !assemString.equalsIgnoreCase("")) {
					routeBranchStr = routeBranchStr + "=" + assemString;
					// �ڵ����
					RouteNodeIfc nodeInfo = new RouteNodeInfo();
					nodeInfo.setRouteType(RouteCategoryType.ASSEMBLYROUTE
							.getDisplay());
					String department = RouteClientUtil
							.getDepartmentID(assemString);
					if (department == null) {
						buffer.append("װ�䵥λ��" + assemString + "\n");
						continue;
					}
					if (col.size() > 0 && !col.contains(assemString)) {
						buffer1.append("װ�䵥λ��" + assemString + "\n");
						continue;
					}
					nodeInfo.setNodeDepartment(department);
					nodeInfo.setNodeDepartmentName(assemString);
					nodeInfo.setRouteInfo(routeInfo);
					RouteItem nodeItem = setRouteItem(nodeInfo);
					x = x + 150;
					nodeInfo.setX(new Long(x).longValue());
					nodeInfo.setY(new Long(y).longValue());
					nodes.add(nodeItem);
					tempNodeVector.add(nodeItem);
				} else {
					routeBranchStr = routeBranchStr + "=";
				}
				// ����·�߷�֧����
				// ��ý������·�ߴ���Ϣ
				routeBranchStr = RouteClientUtil.getRouteBranchStr(
						routeBranchStr, "-");
				// ����֧�ӵ���֧������
				if (routeBranchStr != null && !routeBranchStr.equals("")
						&& !routeBranchStr.equals("=")) {
					routeBranchInfo.setRouteStr(routeBranchStr);
					RouteItem branchItem = setRouteItem(routeBranchInfo);
					branchs.add(branchItem);
				}
				// ���ýڵ����
				Collection nodeLinks = (Collection) routeMap
						.get(TechnicsRouteServiceEJB.ROUTENODE_LINKBSONAME);
				if (nodeLinks == null) {
					nodeLinks = new Vector();
					routeMap.put(TechnicsRouteServiceEJB.ROUTENODE_LINKBSONAME,
							nodeLinks);
				}
				RouteNodeIfc temp = null;
				for (Iterator nodeIter = tempNodeVector.iterator(); nodeIter
						.hasNext();) {
					RouteItem destinationItem = (RouteItem) nodeIter.next();
					RouteNodeIfc destinationInfo = (RouteNodeIfc) destinationItem
							.getObject();
					if (temp != null) {
						RouteNodeLinkIfc nodeLinkInfo1 = new RouteNodeLinkInfo();
						nodeLinkInfo1.setSourceNode(temp);
						nodeLinkInfo1.setDestinationNode(destinationInfo);
						nodeLinkInfo1.setRouteInfo(routeInfo);
						RouteItem nodelinkItem = setRouteItem(nodeLinkInfo1);
						nodeLinks.add(nodelinkItem);
					}
					temp = destinationInfo;
				}
				// ���÷�֧��ڵ����
				Collection branchNodeLinks = (Collection) routeMap
						.get(TechnicsRouteServiceEJB.BRANCHNODE_LINKBSONAME);
				if (branchNodeLinks == null) {
					branchNodeLinks = new Vector();
					routeMap.put(
							TechnicsRouteServiceEJB.BRANCHNODE_LINKBSONAME,
							branchNodeLinks);
				}
				for (Iterator nodeIter = tempNodeVector.iterator(); nodeIter
						.hasNext();) {
					RouteItem nodeInfo1Item = (RouteItem) nodeIter.next();
					RouteNodeIfc nodeInfo1 = (RouteNodeIfc) nodeInfo1Item
							.getObject();
					RouteBranchNodeLinkIfc branchNodeLinkInfo = new RouteBranchNodeLinkInfo();
					branchNodeLinkInfo.setRouteBranchInfo(routeBranchInfo);
					branchNodeLinkInfo.setRouteNodeInfo(nodeInfo1);
					RouteItem branchNodeLinkItem = setRouteItem(branchNodeLinkInfo);
					branchNodeLinks.add(branchNodeLinkItem);
				}
			}
		}
		if (buffer.toString().equals("") && buffer1.toString().equals(""))
			obj[0] = null;
		else {
			messageStr[0] = buffer.toString();
			messageStr[1] = buffer1.toString();
			obj[0] = messageStr;
		}
		obj[1] = routeMap;
		return obj;
	}

	/**
	 * xucy 20120116 add ���ѡ�������������ݣ���������װ���·��map��Ϣ
	 * 
	 * @return ���·����Ϣ����
	 */
	public RouteWrapData getSelectedPartData(int row) {
		// ��װ�����·����Ϣ
		RouteWrapData routeWrapData = new RouteWrapData();
		boolean indenty = false;
		String parentNum = "";
		String parentName = "";
		String routeID = "";
		String linkID = "";
		String partMasterID = qMMultiList.getCellText(row, 0);
		String codeContent = (String) qMMultiList.getComboBoxObject(row, 1);
		String partNum = qMMultiList.getCellText(row, 2);
		String partName = qMMultiList.getCellText(row, 3);
		String mainStr = "";
		String secondStr = "";
		String description = "";
		int count = 0;

		routeWrapData.setPartMasterID(partMasterID);
		routeWrapData.setModifyIdenty(codeContent);
		routeWrapData.setPartNum(partNum);
		routeWrapData.setPartName(partName);

		routeWrapData.setRowNum(row);
		if (routeMode.equals("partRelative")) {
			//CCBegin SS36
				/*mainStr = qMMultiList.getCellText(row, 6);
				secondStr = qMMultiList.getCellText(row, 7);
				description = qMMultiList.getCellText(row, 8);
				routeID = qMMultiList.getCellText(row, 9);
				linkID = qMMultiList.getCellText(row, 10);*/
				mainStr = qMMultiList.getCellText(row, 4);
				secondStr = qMMultiList.getCellText(row, 5);
				description = qMMultiList.getCellText(row, 6);
				routeID = qMMultiList.getCellText(row, 7);
				linkID = qMMultiList.getCellText(row, 8);
		  //CCEnd SS36

		} else if (routeMode.equals("productRelative")) {
			mainStr = qMMultiList.getCellText(row, 4);
			secondStr = qMMultiList.getCellText(row, 5);
			description = qMMultiList.getCellText(row, 6);
			count = Integer.valueOf(qMMultiList.getCellText(row, 7)).intValue();
			indenty = ((Boolean) qMMultiList.getSelectedObject(row, 8))
					.booleanValue();
			routeID = qMMultiList.getCellText(row, 9);
			linkID = qMMultiList.getCellText(row, 10);
		} else if (routeMode.equals("parentRelative")) {
			parentNum = qMMultiList.getCellText(row, 4);
			parentName = qMMultiList.getCellText(row, 5);
			mainStr = qMMultiList.getCellText(row, 6);
			secondStr = qMMultiList.getCellText(row, 7);
			description = qMMultiList.getCellText(row, 8);
			routeID = qMMultiList.getCellText(row, 9);
			linkID = qMMultiList.getCellText(row, 10);

		} else if (routeMode.equals("productAndparentRelative")) {
			parentNum = qMMultiList.getCellText(row, 4);
			parentName = qMMultiList.getCellText(row, 5);
			mainStr = qMMultiList.getCellText(row, 6);
			secondStr = qMMultiList.getCellText(row, 7);
			description = qMMultiList.getCellText(row, 8);
			count = Integer.valueOf(qMMultiList.getCellText(row, 9)).intValue();
			indenty = ((Boolean) qMMultiList.getSelectedObject(row, 10))
					.booleanValue();
			routeID = qMMultiList.getCellText(row, 11);
			linkID = qMMultiList.getCellText(row, 12);
		}
		routeWrapData.setMainStr(mainStr);
		routeWrapData.setSecondStr(secondStr);
		routeWrapData.setDescription(description);
		routeWrapData.setProductIndenty(indenty);
		routeWrapData.setParentNum(parentNum);
		routeWrapData.setParentName(parentName);
		routeWrapData.setProductCount(count);
		routeWrapData.setRouteID(routeID);
		routeWrapData.setLinkID(linkID);
		routeWrapData.setProduct(this.theProductifc);
		// 20120405
		QMPartIfc parent = null;
		if (routeMode.equals("parentRelative")) {
			parent = (QMPartIfc) saveMap.get(partMasterID + parentNum);
		} else if (routeMode.equals("productAndparentRelative")) {
			parent = (QMPartIfc) saveMap.get(partMasterID
					+ this.theProductifc.getPartNumber() + parentNum);
		}
		// ����Ӽ��Ļ�������������Ϣ�������÷�װ����Ĳ�Ʒ��Ϣ��������Ϣ
		if (parent != null) {
			// routeWrapData = (RouteWrapData)saveMap.get(partMasterID);
			// routeWrapData = getPartRouteData(i);
			// routeWrapData.setProduct(routeWrapData1.getProduct());
			// routeWrapData.setProductCount(routeWrapData1.getProductCount());
			routeWrapData.setParent(parent);
		}
		return routeWrapData;
	}

	/**
	 * ����RouteItem����
	 * 
	 * @param info
	 * @return
	 */
	private static RouteItem setRouteItem(BaseValueIfc info) {
		RouteItem item = new RouteItem();
		item.setObject(info);
		item.setState(RouteItem.CREATE);
		return item;
	}

	// end CR8

	/**
	 * ��ò�Ʒ���� 20120105 �촺Ӣ add
	 */
	public QMPartMasterIfc getProductIfc() {
		return this.theProductifc;
	}

	/**
	 * ���ò�Ʒ���󣬹����������¡��鿴��׼ʱ���� 20120105 �촺Ӣ add
	 */
	public void setProductIfc(QMPartMasterIfc product) {
		this.theProductifc = product;
	}

	// ����Ķ������ getNodeDepartmentName �������Ϊ������ͬһ������
	private boolean judgeNodeEquals(RouteNodeIfc a, RouteNodeIfc b) {
		if (a == null && b != null)
			return false;
		if (a != null && b == null)
			return false;
		if (a == null && b == null)
			return false;

		if (a.getNodeDepartmentName().equals(b.getNodeDepartmentName()))
			return true;
		return false;
	}

	private void compareRouteStr(RouteGraphEditJDialog editDialog,
			RouteWrapData routeData) {
		Vector nodeVectorNew = (Vector) routeData.getRouteMap().get(
				TechnicsRouteServiceEJB.ROUTENODE_BSONAME);
		Vector linkVectorNew = (Vector) routeData.getRouteMap().get(
				TechnicsRouteServiceEJB.ROUTENODE_LINKBSONAME);

		Vector nodeVectorOld = editDialog.routeNodeItemVector;
		Vector linkVectorOld = editDialog.routeLinkItemVector;

		if (linkVectorNew != null && linkVectorNew.size() > 0
				&& linkVectorOld != null && linkVectorOld.size() > 0) {
			RouteNodeIfc sourceNode, destinationNode;
			RouteNodeIfc sourceNodeO, destinationNodeO;
			for (int j = 0; j < linkVectorNew.size(); j++) {
				RouteItem linkitem = (RouteItem) linkVectorNew.elementAt(j);
				RouteNodeLinkInfo linkinfo = (RouteNodeLinkInfo) linkitem
						.getObject();
				linkitem.setState(RouteItem.UPDATE);

				sourceNode = linkinfo.getSourceNode();
				destinationNode = linkinfo.getDestinationNode();

				for (int i = linkVectorOld.size() - 1; i >= 0; i--) {
					RouteItem linkitemO = (RouteItem) linkVectorOld
							.elementAt(i);
					RouteNodeLinkInfo linkinfoO = (RouteNodeLinkInfo) linkitemO
							.getObject();
					sourceNodeO = linkinfoO.getSourceNode();
					destinationNodeO = linkinfoO.getDestinationNode();

					if (judgeNodeEquals(sourceNode, sourceNodeO)
							&& judgeNodeEquals(destinationNode,
									destinationNodeO)) {
						sourceNode.setX(sourceNodeO.getX());
						sourceNode.setY(sourceNodeO.getY());

						destinationNode.setX(destinationNodeO.getX());
						destinationNode.setY(destinationNodeO.getY());

						linkVectorOld.remove(i);
					}
				}
			}
		}

	}

	// begin xucy 20120106 add
	/**
	 * �༭��鿴·��ͼ
	 */
	private void processEditRouteGraph() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		int index = qMMultiList.getSelectedRow();
		if (index != -1) {
			// ����ǲ鿴ģʽ����ͨ������·�е�·�߶�������·��ͼ
			if (getViewMode() == VIEW_MODE) {
				// ��ȡһ�е����ݷ�װ��Ϣ
				RouteWrapData routeData = this.getSelectedPartData(index);
				// QMPartMasterIfc partMaster =
				// (QMPartMasterIfc)refreshInfo(routeData.getPartMasterID());
				TechnicsRouteIfc routeIfc = null;
				if (routeData.getRouteID() != null
						&& !routeData.getRouteID().equals(""))
					routeIfc = (TechnicsRouteIfc) refreshInfo(routeData
							.getRouteID());
				if (routeIfc != null) {
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					viewDialog = new RouteGraphViewJDialog(this
							.getParentJFrame());
					viewDialog.setTechnicsRoute(routeIfc);
					// isChangeRoute = false;
					setCursor(Cursor.getDefaultCursor());
					viewDialog.setVisible(true);
				} else {
					// DialogFactory.showWarningDialog(this.getParentJFrame(),
					// "��ѡ�����·��,���ܲ鿴·��ͼ");
					setCursor(Cursor.getDefaultCursor());
					return;
				}

			}
			// ����Ǵ��������ģʽ�����տͻ��˽����л�ȡ����Ϣ���߻�ͼʱ�����·����Ϣ����·��ͼ
			// ���·����Ϣ��ͨ��ͼ�η�ʽ�������ģ���Ҫ�ж�·����Ϣ�Ƿ��޸Ĺ���û�޸���ԭͼ�������Ϣ��ʾ·��ͼ
			// �������Լ�����Ľڵ�͹���������ʾ·��ͼ
			// ���·����Ϣ��ȫ���ֹ���д�ģ���һ��������·�߽ڵ�͹�������·��ͼ
			else {
				// ��ȡһ�е����ݷ�װ��Ϣ
				RouteWrapData routeData = getOnePartSaveData(index);
				Object[] depVec = routeData.getDepartmentVec();
				StringBuffer aa = new StringBuffer();
				if (depVec != null) {
					String str = (String) depVec[0];
					if (str != null && !str.trim().equals(""))
						aa.append(str + "������");
					if ((String) depVec[1] != null
							&& !((String) depVec[1]).trim().equals("")) {

						if (aa.length() > 0) {
							aa.append("\n");
						}
						aa.append(depVec[1] + "���Ƕ���·�ߵ�λ���ӵ�λ" + "\n");
					}
				}
				// Object[] obj1 = {aa.toString()};
				// String s = QMMessage.getLocalizedMessage(RESOURCE, "61",
				// obj1, null);
				// int i = DialogFactory.showYesNoDialog(this.getParentJFrame(),
				// s, "�Ƿ�༭·��ͼ");
				// //����񲻽���༭·��ͼ����
				// if(i == 1)
				// {
				// setCursor(Cursor.getDefaultCursor());
				// return;
				// }

				// QMPartMasterIfc partMaster =
				// (QMPartMasterIfc)refreshInfo(routeData.getPartMasterID());
				TechnicsRouteIfc routeIfc = null;
				System.out.println("routeData.getRouteID()==="
						+ routeData.getRouteID());
				if (routeData.getRouteID() != null
						&& !routeData.getRouteID().equals(""))
					routeIfc = (TechnicsRouteIfc) refreshInfo(routeData
							.getRouteID());
				// setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

				this.initTree();// CR1
				editDialog = new RouteGraphEditJDialog(this.getParentJFrame(),
						this.getTechnicsRouteList(), routeIfc, routeData);
				editDialog.addDepartmentTree(this.departmentTree);
				editDialog.setVisible(true);
				if (editDialog.isSave) {
					this.branchHashtable.clear();
					this.branchMap.clear();
					this.branchHashtable = editDialog.branchHashtable;
					this.branchMap = editDialog.branchMap;
					// this.routeNodeItemVector =
					// editDialog.routeNodeItemVector;
					// this.routeLinkItemVector =
					// editDialog.routeLinkItemVector;

					// ·�߽ڵ�͹�����Ϣ���Դ�map��ȡ
					// this.routeNodeItemMap.put(routeData.getPartMasterID(),
					// editDialog.routeNodeItemVector);
					// this.routeLinkItemMap.put(routeData.getPartMasterID(),
					// editDialog.routeLinkItemVector);
					// if(branchHashtable !=null && branchHashtable.size()>0)
					// ����Ϣ��ӵ���׼��������
					String[] routeArray = formBranchToMultiList(index,
							branchMap, branchHashtable, routeData
									.getPartMasterID());
					// ��ȡѡ�������·�������Ϣ
					if (routeArray != null) {
						HashMap map = getOnePartRoutePicMap();
						Object[] obj = new Object[] { routeArray, map };
						RouteItem routeItem = (RouteItem) map
								.get(TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME);
						Vector vec = (Vector) map
								.get(TechnicsRouteServiceEJB.TECHNICSROUTEBRANCH_BSONAME);
						// System.out.println("branch==" + vec.size());
						// ��ͨ��ͼ���ɵ�·����Ϣ����
						multiPartPicMaps.put(routeData.getPartMasterID(), obj);
					}
				}

			}
		} else {
			DialogFactory.showWarningDialog(this.getParentJFrame(), "��ѡ�����");
			setCursor(Cursor.getDefaultCursor());
			return;
		}
		setCursor(Cursor.getDefaultCursor());

	}

	/**
	 * ��ȡ���������·��ͼ��Ϣ
	 * 
	 * @return 20120119 �촺Ӣ add
	 */
	private HashMap getOnePartRoutePicMap() {
		HashMap saveMap = new HashMap();
		TechnicsRouteIfc routeInfo = null;
		if (saveMap.get(TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME) == null) {
			routeInfo = new TechnicsRouteInfo();
			// 20120110 xucy ע�� �ڴ˲�����·����Ϣ�����������ڹ�����Ϣ
			/*
			 * routeInfo.setRouteDescription(description);
			 * routeInfo.setModifyIdenty(codeContent);
			 */
			RouteItem routeItem = setRouteItem(routeInfo);
			// routeMap = new HashMap();
			// ����·��ӳ���
			saveMap.put(TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME,
					routeItem);
		} else {
			RouteItem routeItem = (RouteItem) saveMap
					.get(TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME);
			routeInfo = (TechnicsRouteIfc) routeItem.getObject();
		}

		// ������½�ģʽ��,�������·��ͼ�����˱仯,�����»�������ɵ�·�߷�֧���ύ����
		Vector v2 = (Vector) commitUpdatedBranches();
		if (v2 != null && v2.size() > 0) {
			if (verbose) {
				System.out.println(":::::::::::::::: ���� ��֧ �ĸ�����" + v2.size());
			}
			saveMap.put("consTechnicsRouteBranch", v2);
		}
		// �����������ܲ��� �޸� zz start
		v2 = null;
		// �����������ܲ��� �޸� zz end

		if (editDialog != null) {
			// ����֧�ڵ��������
			Vector v3 = new Vector();
			v3.addAll(this.branchNodeLinkVector);
			if (v3 != null && v3.size() > 0) {
				if (verbose) {
					System.out.println(":::::::::::::::: ���� ��֧���ڵ� �����ĸ�����"
							+ v3.size());
				}
				saveMap.put("consRouteBranchNodeLink", v3);
			}
			// �����������ܲ��� �޸� zz start
			v3 = null;
			// �����������ܲ��� �޸� zz end

			Vector v4 = editDialog.routeNodeItemVector;
			if (v4 != null && v4.size() > 0) {
				if (verbose) {
					System.out.println(":::::::::::::::: ���� �ڵ� �ĸ�����"
							+ v4.size());
				}
				saveMap.put("consRouteNode", v4);
			}
			// �����������ܲ��� �޸� zz start
			v4 = null;
			// �����������ܲ��� �޸� zz end

			Vector v5 = editDialog.routeLinkItemVector;
			if (v5 != null && v5.size() > 0) {
				if (verbose) {
					System.out.println(":::::::::::::::: ���� ���� �ĸ�����"
							+ v5.size());
				}
				saveMap.put("consRouteNodeLink", v5);
			}
			// �����������ܲ��� �޸� zz start
			v5 = null;
			// �����������ܲ��� �޸� zz end
		}

		return saveMap;

	}

	/**
	 * ��ȡ����Ķ�������·��ͼ��Ϣ
	 * 
	 * @return 20120119 xucy add
	 */
	public HashMap getMultiPartRouteMap() {
		return this.multiPartPicMaps;
	}

	/**
	 * �������µ�·�߷�֧
	 * 
	 * @return ���º��·�߷�֧����(Ԫ��ΪRouteItem) 20120119 xucy add
	 */
	private Vector commitUpdatedBranches() {
		Vector v = new Vector();
		branchNodeLinkVector.clear();
		if (branchMap.size() > 0) {
			for (int i = 0; i < branchMap.size(); i++) {
				// String bsoid = qMMultiList.getCellText(i, 0);
				// if(verbose)
				// {
				// System.out.println("�ύʱ ·�߷�֧ ID = " + bsoid);
				// }
				Object[] objs2 = (Object[]) branchMap.get(String.valueOf(i));
				TechnicsRouteBranchInfo branch = (TechnicsRouteBranchInfo) objs2[0];
				Vector curPathNodes = (Vector) objs2[1];
				// if(verbose)
				{
					System.out.println("�ύʱ ·�߷�֧" + i + "�Ľڵ������"
							+ curPathNodes.size());
				}
				if (i == 0) {
					branch.setMainRoute(true);
				} else {
					branch.setMainRoute(false);
				}
				setObjectNotPersist(branch); // ·�߷�֧��ԶΪ�½�
				for (int j = 0; j < curPathNodes.size(); j++) {
					RouteBranchNodeLinkInfo linkinfo = new RouteBranchNodeLinkInfo();
					linkinfo.setRouteBranchInfo(branch);
					DefaultGraphNode node = (DefaultGraphNode) curPathNodes
							.elementAt(j);
					linkinfo.setRouteNodeInfo((RouteNodeInfo) node
							.getRouteItem().getObject());
					RouteItem item = new RouteItem(linkinfo);
					item.setState(RouteItem.CREATE);
					branchNodeLinkVector.addElement(item);
				}
				RouteItem item = new RouteItem(branch);
				item.setState(RouteItem.CREATE);
				v.addElement(item);
			}
		}
		return v;
	}

	/**
	 * ��õ�ǰ����ģʽ
	 * 
	 * @return ��ǰ����ģʽ 20120110 xucy add
	 */
	public int getViewMode() {
		return this.mode;
	}

	/**
	 * ���õ�ǰ����ģʽ
	 * 
	 * @return ��ǰ����ģʽ 20120209 xucy add
	 */
	public void setViewMode(int mode) {
		this.mode = mode;
	}

	/**
	 * ��ʼ��·�ߵ�λ�� 20120113 xucy modify
	 */
	private void initTree() {
		try {
			// ��ʼ��·�ߵ�λ��
			CodingClassificationIfc cc = null;
			System.out.println("listPanel.getRouteLevel()==="
					+ listPanel.getRouteLevel());
System.out.println("��·��22 comp=="+comp);					
			if (listPanel.getRouteLevel().equals(
					RouteListLevelType.FIRSTROUTE.getDisplay())) {
				Class[] c = { String.class, String.class };
				//CCBegin SS14
				/*Object[] obj = { "��֯����", "�������" };
				cc = (CodingClassificationIfc) RequestHelper.request(
						"CodingManageService", "findClassificationByName", c,
						obj);// CR2*/
				if(comp.equals("zczx"))
				{
					Object[] obj = { "��֯����", "�������" };
				  cc = (CodingClassificationIfc) RequestHelper.request(
						"CodingManageService", "findClassificationByName", c,
						obj);// CR2
				}//CCBegin SS23
				else if(comp.equals("ct")){
					Object[] obj = { "���ع���·�ߵ�λ", "�������" };
					  cc = (CodingClassificationIfc) RequestHelper.request(
							"CodingManageService", "findClassificationByName", c,
							obj);
				}
				//CCBegin SS34
				else if(comp.equals("cd"))
				{
					Object[] obj = { "��֯����-cd", "�������" };
				  cc = (CodingClassificationIfc) RequestHelper.request(
						"CodingManageService", "findClassificationByName", c,
						obj);// CR2
						System.out.println("CC=="+cc);
				}
				//CCEnd SS34
				//CCEnd SS23
				else
				{
					Object[] obj = { "��֯����-bsx", "�������" };
				  cc = (CodingClassificationIfc) RequestHelper.request(
						"CodingManageService", "findClassificationByName", c,
						obj);// CR2
				}
				//CCEnd SS14
			} else {
				String departID = listPanel.getDepartmentID();
				if (!departID.equals(""))
					cc = (CodingClassificationIfc) refreshInfo(departID);
			}
			if (cc != null) {
				departmentTree = new CodeManageTree(cc);
				departmentTree.setShowsRootHandles(false);
			}
		} catch (Exception ex) {
			String message = ex.getMessage();
			DialogFactory.showInformDialog(getParentJFrame(), message);
		}

	}

	/**
	 * �����µ�·�ߴ�����ӵ��б���
	 * 
	 * @param pathHashtable
	 *            Hashtable 20120113 xucy add
	 */
	private String[] formBranchToMultiList(int index, HashMap hashMap,
			Hashtable pathHashtable, String partMasterID) {
		String[] routeArray = new String[2];
		String mainRouteStr = "";
		String secondRouteStr = "";
		// ���û�е��·��ͼ�е�������Ҫ·�߰�Ť
		if (hashMap == null || hashMap.size() == 0) {
			if (pathHashtable == null || pathHashtable.size() == 0) {
				this.multiPartPicMaps.remove(partMasterID);
				return null;
			} else {
				if (verbose) {
					System.out
							.println("cappclients.capproute.view.RouteListPartLinkJPanel.formBranchToMultiList() begin...");
				}
				// 20120113 xucy add
				branchMap.clear();
				if (pathHashtable == null || pathHashtable.size() == 0) {
					this.multiPartPicMaps.remove(partMasterID);
					return null;
				}
				// ���·�߷�֧,����ʾ��·�ߴ��б���
				Object[] keys = pathHashtable.keySet().toArray();
				TechnicsRouteBranchInfo branchinfo;
				ArrayList list = new ArrayList();
				// ����ÿ�ΰ�ͬ����˳����ʾ
				Map mapA = new TreeMap();
				long yLocation = -1;
				for (int i = 0; i < pathHashtable.size(); i++) {
					branchinfo = (TechnicsRouteBranchInfo) keys[i];
					Object[] objs = new Object[2];
					objs[0] = branchinfo;
					objs[1] = pathHashtable.get(branchinfo);
					branchMap.put(String.valueOf(i), objs);
					String makeStr = "";
					String assemStr = "";
					Vector nodeVector = (Vector) pathHashtable.get(branchinfo);
					if (verbose) {
						System.out.println("�ύǰ ��֧" + i + "�Ľڵ������"
								+ nodeVector.size());
					}
					DefaultGraphNode graphnode;
					RouteNodeInfo node;
					boolean isMain = false;
					for (int j = 0; j < nodeVector.size(); j++) {
						graphnode = (DefaultGraphNode) nodeVector.elementAt(j);
						node = (RouteNodeInfo) graphnode.getRouteItem()
								.getObject();
						// Yλ����С���������������Ҫ��·��
						if ((yLocation == -1) || (node.getY() < yLocation)) {
							isMain = true;
							yLocation = node.getY();
						}
						if (node.getRouteType()
								.equals(
										RouteCategoryType.MANUFACTUREROUTE
												.getDisplay())) {
							if (makeStr == "") {
								makeStr = makeStr
										+ graphnode.getDepartmentName();
							} else {
								makeStr = makeStr + "-"
										+ graphnode.getDepartmentName();
							}
						} else if (node.getRouteType().equals(
								RouteCategoryType.ASSEMBLYROUTE.getDisplay())) {
							if (assemStr == "") {
								assemStr = assemStr
										+ graphnode.getDepartmentName();
							} else {
								assemStr = assemStr + "-"
										+ graphnode.getDepartmentName();
							}
						}

					}
					String routeStr = "";
					if (makeStr == "" && !"".equals(assemStr)) {
						routeStr = assemStr;
					} else if (assemStr == "" && !"".equals(assemStr)) {
						routeStr = makeStr;
					} else {
						routeStr = makeStr + "=" + assemStr;
					}
					if (isMain)
						list.add(0, routeStr);
					else
						list.add(routeStr);
					branchinfo.setRouteStr(makeStr + "=" + assemStr);
				}
				if (list.size() > 0) {
					mainRouteStr = (String) list.get(0);
				}
				mainRouteStr = RouteClientUtil.getRouteBranchStr(mainRouteStr,
						"=");
				// ������Ҫ·��
				if (routeMode.equals("productRelative")
						|| routeMode.equals("partRelative")) {
				
					  qMMultiList.addTextCell(index, 4, mainRouteStr);
				
				} else {
					qMMultiList.addTextCell(index, 6, mainRouteStr);
				}
				if (list.size() == 2) {
					secondRouteStr = (String) list.get(1);
					secondRouteStr = RouteClientUtil.getRouteBranchStr(
							secondRouteStr, "=");
				} else if (list.size() > 2) {
					for (int m = 1, n = list.size(); m < n; m++) {
						String secondStr = (String) list.get(m);
						secondStr = RouteClientUtil.getRouteBranchStr(
								secondStr, "=");
						if (secondRouteStr != null)
							secondRouteStr = secondRouteStr + secondStr;
						else
							secondRouteStr = secondStr;
						if (m != n - 1)
							secondRouteStr = secondRouteStr + ";";
					}
				}
				// secondRouteStr =
				// RouteClientUtil.getRouteBranchStr(secondRouteStr, "=");
				// ���ô�Ҫ·��
				if (routeMode.equals("productRelative")
						|| routeMode.equals("partRelative")) {
			
			
					qMMultiList.addTextCell(index, 5, secondRouteStr);
				
				} else {
					qMMultiList.addTextCell(index, 7, secondRouteStr);
				}
				if (verbose) {
					System.out
							.println("cappclients.capproute.view.RouteTaskJPanel.formBranchToMultiList() end...return is void");
				}
				routeArray[0] = mainRouteStr;
				routeArray[1] = secondRouteStr;
				return routeArray;
			}
		}
		// �����·��ͼ�е����������Ҫ·�߰�ť���������·�ʽ���·�ߴ���Ϣ
		else {
			for (int i = 0; i < hashMap.size(); i++) {
				Object[] objs = (Object[]) hashMap.get(String.valueOf(i));
				TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo) objs[0];
				branchMap.put(String.valueOf(i), objs);
				String makeStr = "";
				String assemStr = "";
				if (branchinfo.getMainRoute()) {
					mainRouteStr = branchinfo.getRouteStr();
					mainRouteStr = RouteClientUtil.getRouteBranchStr(
							mainRouteStr, "=");
				} else {
					String secRouteStr = branchinfo.getRouteStr();
					secRouteStr = RouteClientUtil.getRouteBranchStr(
							secRouteStr, "=");
					if ("".equals(secondRouteStr)) {
						secondRouteStr = secRouteStr;
					} else {
						secondRouteStr = secondRouteStr + ";" + secRouteStr;
					}
				}
				if (verbose) {
					System.out
							.println("cappclients.capproute.view.RouteTaskJPanel.formBranchToMultiList() end...return is void");
				}

			}
			// secondRouteStr =
			// RouteClientUtil.getRouteBranchStr(secondRouteStr, ";");
			// ���ô�Ҫ·��
			if (routeMode.equals("productRelative")
					|| routeMode.equals("partRelative")) {
   
				qMMultiList.addTextCell(index, 4, mainRouteStr);
				qMMultiList.addTextCell(index, 5, secondRouteStr);
       
			} else {

				qMMultiList.addTextCell(index, 6, mainRouteStr);
				qMMultiList.addTextCell(index, 7, secondRouteStr);
			}
			routeArray[0] = mainRouteStr;
			routeArray[1] = secondRouteStr;
		}
		return routeArray;
	}

	/**
	 * ��ȡ����ķ�֧ 20120106 xucy add
	 * 
	 * @return
	 */
	public HashMap getBranchMap() {
		return branchMap;
	}

	/**
	 * ����
	 */
	public void copy() {
		int index = qMMultiList.getSelectedRow();
		if (index != -1) {
			wrapdata = this.getSelectedPartData(index);
			// CR15 begin
			if (wrapdata.getMainStr().equals("")) {
				Object[] obj = { wrapdata.getPartNum() + "_"
						+ wrapdata.getPartName() };
				String s = QMMessage.getLocalizedMessage(RESOURCE, "59", obj,
						null);
				DialogFactory.showInformDialog(this.getParentJFrame(), s);
				this.setRouteWrapData(null);
				setCursor(Cursor.getDefaultCursor());
				return;
			} else {
				this.setRouteWrapData(wrapdata);
			}
			// CR15 end
		} else {
			DialogFactory.showWarningDialog(this.getParentJFrame(), "��ѡ�����");
			setCursor(Cursor.getDefaultCursor());
			return;
		}
	}

	/**
	 * ճ�� 20120129 xucy modify
	 */
	public void paste() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		int index[] = qMMultiList.getSelectedRows();
		boolean flag = false;
		ListRoutePartLinkIfc partlink = null;
		// ����·�ߵĶ��󼯺�
		Vector dataVec = new Vector();
		// ������·�ߵ��м��� 20120119 �촺Ӣ add
		Vector rowVec = new Vector();
		// Vector marks = this.getMarks();
		String routeStr = "";
		if (wrapdata == null) {
			DialogFactory.showInformDialog(this.getParentJFrame(),
					"û��Դ���ݣ��븴�ƺ���ճ����");
			setCursor(Cursor.getDefaultCursor());
			return;
		}
		if (wrapdata.getSecondStr() != null
				&& !wrapdata.getSecondStr().equals("")) {
			routeStr = wrapdata.getMainStr() + ";" + wrapdata.getSecondStr();
		} else {
			routeStr = wrapdata.getMainStr();
		}
		if (index.length != 0) {
			for (int i = 0; i < index.length; i++) {
				RouteWrapData routeData = this.getSelectedPartData(index[i]);
				if (routeData.getMainStr() != null
						&& !routeData.getMainStr().equals("")) {
					// �����ڵ�·����Ϣ�ŵ�������
					dataVec.add(routeData);
					flag = true;
					continue;
				} else {
					// ��������·�ߵ��л���
					rowVec.add(index[i]);
				}
			}
			// ����д���·����Ϣ������򵯳���ʾ��
			if (flag) {
				RoutePasteToJDialog pastetodialog = new RoutePasteToJDialog(
						this.getParentJFrame(), dataVec, routeStr);
				pastetodialog.setVisible(true);
				if (pastetodialog.getIsSave()) {
					Vector vec = pastetodialog.getReplaceRows();
					for (int i = 0; i < vec.size(); i++) {
						// ���Ҫ�滻��������
						int k = ((Integer) vec.get(i)).intValue();
						qMMultiList.setComboBoxSelected(k, 1, wrapdata
								.getModifyIdenty(), marks);
						if (routeMode.equals("partRelative")
								|| routeMode.equals("productRelative")) {
							// �����滻���������
	
							qMMultiList
									.addTextCell(k, 4, wrapdata.getMainStr());
							qMMultiList.addTextCell(k, 5, wrapdata
									.getSecondStr());
							qMMultiList.addTextCell(k, 6, wrapdata
									.getDescription());
			
						} else if (routeMode.equals("parentRelative")
								|| routeMode.equals("productAndparentRelative")) {
							// �����滻���������
							qMMultiList
									.addTextCell(k, 6, wrapdata.getMainStr());
							qMMultiList.addTextCell(k, 7, wrapdata
									.getSecondStr());
							qMMultiList.addTextCell(k, 8, wrapdata
									.getDescription());
						}
					}
				}
			}
			// ����·����Ϣ������·�ߵ���
			for (int i = 0; i < rowVec.size(); i++) {
				// �����·�ߵ�������
				int k = ((Integer) rowVec.get(i)).intValue();
				qMMultiList.setComboBoxSelected(k, 1, wrapdata
						.getModifyIdenty(), marks);
				if (routeMode.equals("partRelative")
						|| routeMode.equals("productRelative")) {
					// �����滻���������
					qMMultiList.addTextCell(k, 4, wrapdata.getMainStr());
					qMMultiList.addTextCell(k, 5, wrapdata.getSecondStr());
					qMMultiList.addTextCell(k, 6, wrapdata.getDescription());
				} else if (routeMode.equals("parentRelative")
						|| routeMode.equals("productAndparentRelative")) {
					// �����滻���������
					qMMultiList.addTextCell(k, 6, wrapdata.getMainStr());
					qMMultiList.addTextCell(k, 7, wrapdata.getSecondStr());
					qMMultiList.addTextCell(k, 8, wrapdata.getDescription());
				}
			}
		} else {
			DialogFactory.showInformDialog(this.getParentJFrame(), "��ѡ�����");
			setCursor(Cursor.getDefaultCursor());
			return;
		}
		setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * ������͹���
	 */
	private void saveModelRoute() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		Vector dataVec = new Vector();
		Vector mrVec = new Vector();
		HashMap nowModelRotueMap = new HashMap();
		int index[] = qMMultiList.getSelectedRows();
		for (int i = 0; i < index.length; i++) {
			wrapdata = this.getOnePartSaveData(index[i]);
			//CCBegin SS16
			QMPartInfo part = null;
			if(wrapdata.getPartMasterID().contains("QMPart_")){
				Class[] c = {String.class};
				Object[] obj = {wrapdata.getPartMasterID().toString()};
				try {
					part = (QMPartInfo)RequestHelper.request("PersistService", "refreshInfo", c, obj);
				} catch (QMRemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//CCEnd SS16
			if (wrapdata.getMainStr().equals("")) {
				continue;
			} else {
				//CCBegin SS16
				if(wrapdata.getPartMasterID().contains("QMPart_")){
					if (qMMultiList.getCellText(index[i], 0).equals(part.getBsoID())) {
						// CR13 begin
						String routeStrInfo = wrapdata.getMainStr();
						if (!wrapdata.getSecondStr().equals("")) {
							routeStrInfo = routeStrInfo + ";"
									+ wrapdata.getSecondStr();
						}
						if (!checkDepartment(routeStrInfo)) {
							return;
						}
						// CR13 end
						Class[] param1 = { String.class };
						Object[] value1 = { part.getMasterBsoID() };
						ModelRouteInfo modelrouteinfo;
						try {
							modelrouteinfo = (ModelRouteInfo) RequestHelper
									.request("consTechnicsRouteService",
											"findModelRouteByPartID", param1,
											value1);
							if (modelrouteinfo != null) {
								mrVec.add(modelrouteinfo);
								// �б��ϼ���Ҫ����ĵ���·��
								nowModelRotueMap.put(modelrouteinfo.getLeftBsoID(),
										wrapdata);
							} else {
								dataVec.add(wrapdata);
							}
						} catch (QMException e) {
							e.printStackTrace();
						}
					}
				}
				else{
					//CCEnd SS16
					if (qMMultiList.getCellText(index[i], 0).equals(
							wrapdata.getPartMasterID())) {
						// CR13 begin
						String routeStrInfo = wrapdata.getMainStr();
						if (!wrapdata.getSecondStr().equals("")) {
							routeStrInfo = routeStrInfo + ";"
									+ wrapdata.getSecondStr();
						}
						if (!checkDepartment(routeStrInfo)) {
							return;
						}
						// CR13 end
						Class[] param1 = { String.class };
						Object[] value1 = { wrapdata.getPartMasterID() };
						ModelRouteInfo modelrouteinfo;
						try {
							modelrouteinfo = (ModelRouteInfo) RequestHelper
									.request("consTechnicsRouteService",
											"findModelRouteByPartID", param1,
											value1);

							if (modelrouteinfo != null) {
								mrVec.add(modelrouteinfo);
								// �б��ϼ���Ҫ����ĵ���·��
								nowModelRotueMap.put(modelrouteinfo.getLeftBsoID(),
										wrapdata);
							} else {
								dataVec.add(wrapdata);
							}
						} catch (QMException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		if (!mrVec.isEmpty()) {
			CoverModelRouteJDialog cmrdialog = new CoverModelRouteJDialog(this
					.getParentJFrame(), this, mrVec, nowModelRotueMap);
			cmrdialog.setVisible(true);
		}
		if (!dataVec.isEmpty()) {
			Class[] params = { Vector.class };
			Object[] values = { dataVec };
			HashMap modelroutemap;
			try {
				modelroutemap = (HashMap) RequestHelper.request(
						"consTechnicsRouteService", "saveAsRoute", params,
						values);

				for (int j = 0; j < dataVec.size(); j++) {
					RouteWrapData wrapdata = (RouteWrapData) dataVec.get(j);
					ModelRouteInfo modelroute = new ModelRouteInfo();
					//CCBegin SS37
					String partnum ="";
					//CCEnd SS37
					//CCBegin SS16
					if(wrapdata.getPartMasterID().toString().contains("QMPart_")){
						Class[] c = {String.class};
						Object[] obj = {wrapdata.getPartMasterID().toString()};
						QMPartInfo part = (QMPartInfo)RequestHelper.request("PersistService", "refreshInfo", c, obj);
						modelroute.setLeftBsoID(part.getMasterBsoID());
						//CCBegin SS37
						partnum = part.getPartNumber().trim();
						//CCEnd SS37
					}
					else{
						modelroute.setLeftBsoID(wrapdata.getPartMasterID());
						//CCBegin SS37
						Class[] c = {String.class};
						Object[] obj = {wrapdata.getPartMasterID().toString()};
						QMPartMasterInfo part = (QMPartMasterInfo)RequestHelper.request("PersistService", "refreshInfo", c, obj);
						partnum = part.getPartNumber().trim();
						//CCEnd SS37
					}
					//CCEnd SS16
					//CCBegin SS37
					//modelroute.setLeftBsoID(wrapdata.getPartMasterID());
					if(comp.equals("cd"))
					{
						if (partnum.length() >= 7)
						{
							partnum = partnum.substring(0, 7);
						}
						else
						{
							partnum = partnum;
						}
						modelroute.setLeftBsoID(partnum);
					}
					//CCEnd SS37
					modelroute.setRightBsoID(modelroutemap.get(
							wrapdata.getPartMasterID()).toString());
					modelroute.setDomain(modelRoutedomain);
					Class[] param = { ModelRouteInfo.class };
					Object[] value = { modelroute };
					try {
						RequestHelper.request("consTechnicsRouteService",
								"saveModelRoute", param, value);
					} catch (QMException e) {
						e.printStackTrace();
					}
				}
			} catch (QMException e1) {
				e1.printStackTrace();
			}
		}
		setCursor(Cursor.getDefaultCursor());
	}

	// CR13 begin
	/**
	 * ���·�ߵ�λ�Ƿ�Ϸ�
	 * 
	 * @param routeStrInfo
	 *            ·����Ϣ����
	 */
	private boolean checkDepartment(String routeStrInfo) {
		boolean flag = false;
		Vector vec = new Vector();
		// ����·����Ϣ
		StringTokenizer routeStrToken = new StringTokenizer(routeStrInfo, ";");
		int size = routeStrToken.countTokens();
		String[] branches = new String[size];
		if (size > 0) {
			branches[0] = routeStrToken.nextToken();
			if (size > 1) {
				for (int m = 1; m < size; m++) {
					branches[m] = routeStrToken.nextToken();
				}
			}
		}
		for (int n = 0; n < size; n++) {
			String routeStr = branches[n];
			int length = routeStr.length();
			int position = routeStr.indexOf("=");
			String manuString = "";
			String assemString = "";
			// �������·�ߺ�װ��·�߽ڵ㡣
			if (position != -1) {
				manuString = routeStr.substring(0, position);
				// װ��·�߽ڵ㡣
				assemString = routeStr.substring(position + 1, length);
			} else {
				manuString = routeStr;
			}
			Collection nodeString = new Vector();
			if (manuString != null && !manuString.equals("")) {
				StringTokenizer token = new StringTokenizer(manuString, "-");
				while (token.hasMoreTokens()) {
					nodeString.add(token.nextToken());
				}
			}
			if (assemString != null && !assemString.trim().equals(""))
				nodeString.add(assemString);
			for (Iterator manuIterator = nodeString.iterator(); manuIterator
					.hasNext();) {
				String manu = (String) manuIterator.next();
				String departID = RouteClientUtil.getDepartmentID(manu);
				if (departID == null || departID.equals("")) {
					vec.add(manu);
					flag = true;
					continue;
				}
			}
		}
		// �����ݿ��в����ڵ�·�ߵ�λ������ʾ��Ϣ
		if (flag) {
			String departNames = "";
			if (vec.size() == 1) {
				departNames = (String) vec.elementAt(0);
			} else {
				for (int m = 0, n = vec.size(); m < n; m++) {
					String mamu = (String) vec.elementAt(m);
					departNames = departNames + mamu;
					if (m != n - 1)
						departNames = departNames + ";";
				}
			}
			vec.clear();
			String aa = "{" + departNames + "}";
			Object[] obj2 = { aa };
			String s = QMMessage
					.getLocalizedMessage(RESOURCE, "60", obj2, null);
			DialogFactory.showFormattedWarningDialog(this.getParentJFrame(), s);
			return false;
		}

		return true;
	}

	// CR13 end
	/**
	 * Ӧ�õ��͹���
	 */
	private void useModelRoute() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		int index[] = qMMultiList.getSelectedRows();
		HashMap nowMap = new HashMap();
		RouteWrapData wrapdata1;
		boolean flag = false;
		Vector partVec = new Vector();
		ArrayList list = new ArrayList();
		for (int i = 0; i < index.length; i++) {
			list.add(qMMultiList.getCellText(index[i], 0));
		}
		Class[] param1 = { ArrayList.class };
		Object[] value1 = { list };
		HashMap wrapdataMap;
		try {
			wrapdataMap = (HashMap) RequestHelper.request(
					"consTechnicsRouteService", "ApplyModelRoute", param1,
					value1);

			for (int k = 0; k < index.length; k++) {
				wrapdata = this.getOnePartSaveData(index[k]);
				//CCBegin SS37
				wrapdata1 = (RouteWrapData) wrapdataMap.get(qMMultiList
						.getCellText(index[k], 0));
				if(comp.equals("cd"))
				{
					String partnum = qMMultiList.getCellText(index[k], 2);
					if (partnum.length() >= 7)
            	{
            		partnum = partnum.substring(0, 7);
            	}
            	else
            	{
            		partnum = partnum;
            	}
					wrapdata1 = (RouteWrapData) wrapdataMap.get(partnum);
				}
				else if(wrapdata1==null)
				{
					String partID = qMMultiList.getCellText(index[k], 0);
					QMPartIfc temp = (QMPartIfc) refreshInfo(partID);
					wrapdata1 = (RouteWrapData) wrapdataMap.get(temp.getMasterBsoID());
				}
				//CCEnd SS37
				// CR14 begin
				if (wrapdata1 != null) {
					if (routeMode.equals("partRelative")
							|| routeMode.equals("productRelative")) {
						// �б����Ѿ�����·��
						if (!qMMultiList.getCellText(index[k], 4).equals("")) {
							nowMap.put(qMMultiList.getCellText(index[k], 0),
									wrapdata);
							partVec.add(qMMultiList.getCellText(index[k], 0));
							flag = true;
						} else {
							// �����滻���������
							qMMultiList.addTextCell(index[k], 4, wrapdata1
									.getMainStr());
							qMMultiList.addTextCell(index[k], 5, wrapdata1
									.getSecondStr());
							qMMultiList.addTextCell(index[k], 6, wrapdata1
									.getDescription());
						}
					} else if (routeMode.equals("parentRelative")
							|| routeMode.equals("productAndparentRelative")) {
						// �б����Ѿ�����·��
						if (!qMMultiList.getCellText(index[k], 6).equals("")) {
							nowMap.put(qMMultiList.getCellText(index[k], 0),
									wrapdata);
							partVec.add(qMMultiList.getCellText(index[k], 0));
							flag = true;
						} else {
							// �����滻���������
							qMMultiList.addTextCell(index[k], 6, wrapdata1
									.getMainStr());
							qMMultiList.addTextCell(index[k], 7, wrapdata1
									.getSecondStr());
							qMMultiList.addTextCell(index[k], 8, wrapdata1
									.getDescription());
						}
					}
				}
				// CR14 end
			}

			// �������Ҫ���ǵ�·��
			if (flag) {
				ApplyModelRouteJDailog applydialog = new ApplyModelRouteJDailog(
						this.getParentJFrame(), nowMap, wrapdataMap, partVec);
				applydialog.setVisible(true);
				Vector vec = applydialog.getDataVec();
				if (vec != null) {
					for (int j = 0; j < vec.size(); j++) {
						RouteWrapData data = (RouteWrapData) vec.elementAt(j);
						for (int m = 0; m < index.length; m++) {
							if (qMMultiList.getCellText(index[m], 0).equals(
									data.getPartMasterID())) {
								if (routeMode.equals("partRelative")
										|| routeMode.equals("productRelative")) {
									// �����滻���������
									qMMultiList.addTextCell(index[m], 4, data
											.getMainStr());
									qMMultiList.addTextCell(index[m], 5, data
											.getSecondStr());
									qMMultiList.addTextCell(index[m], 6, data
											.getDescription());
								} else if (routeMode.equals("parentRelative")
										|| routeMode
												.equals("productAndparentRelative")) {
									// �����滻���������
									qMMultiList.addTextCell(index[m], 6, data
											.getMainStr());
									qMMultiList.addTextCell(index[m], 7, data
											.getSecondStr());
									qMMultiList.addTextCell(index[m], 8, data
											.getDescription());
								}
							}
						}

					}
				}
			}
		} catch (QMException e) {
			e.printStackTrace();
		}
		setCursor(Cursor.getDefaultCursor());
	}

	RouteWrapData wrapdata;

	public void setRouteWrapData(RouteWrapData wrapdata) {
		this.wrapdata = wrapdata;
	}

	public RouteWrapData getRouteWrapData() {
		return wrapdata;
	}

	// 20120113 xucy add
	public void setRouteLevel(String level) {
		this.routeLevel = level;
	}

	/**
	 * ɾ��·�� 20120113 �촺Ӣ add
	 */
	private void deleteRoute() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		int index[] = qMMultiList.getSelectedRows();
		ListRoutePartLinkIfc partlink = null;
		if (index.length != 0) {
			ArrayList list = new ArrayList();
			String title = QMMessage.getLocalizedMessage(RESOURCE,
					"information", null);
			String message = QMMessage.getLocalizedMessage(RESOURCE,
					CappRouteRB.CONFIRM_DELETE_OBJECT, null);
			int result = JOptionPane.showConfirmDialog(this, message, title,
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			switch (result) {
			case JOptionPane.YES_OPTION: {
				for (int i = 0; i < index.length; i++) {
					// RouteWrapData routeData =
					// this.getSelectedPartData(index[i]);
					// if(!routeData.getRouteID().equals("") &&
					// !routeData.getRouteID().equals(""))
					// {
					// list.add(routeData.getLinkID());
					// // ���÷���ɾ��ָ����·�ߣ�ͬʱ�������е�routeID�ÿ�
					// Class[] paraClass = {ArrayList.class};
					// Object[] obj = {list};
					// RequestHelper.request("TechnicsRouteService",
					// "deleteRoute", paraClass, obj);
					// }
					// ֻ����տͻ��˵����ݣ��ȱ���ʱ�ٳ��״����ݿ��а�·��ɾ��
					if (routeMode.equals("partRelative")) {
						qMMultiList.addTextCell(index[i], 4, "");
						qMMultiList.addTextCell(index[i], 5, "");
						qMMultiList.addTextCell(index[i], 6, "");
						qMMultiList.addTextCell(index[i], 7, "");
					} else if (routeMode.equals("productRelative")) {
						qMMultiList.addTextCell(index[i], 4, "");
						qMMultiList.addTextCell(index[i], 5, "");
						qMMultiList.addTextCell(index[i], 6, "");
						qMMultiList.addTextCell(index[i], 9, "");
					} else if (routeMode.equals("parentRelative")) {
						qMMultiList.addTextCell(index[i], 6, "");
						qMMultiList.addTextCell(index[i], 7, "");
						qMMultiList.addTextCell(index[i], 8, "");
						qMMultiList.addTextCell(index[i], 9, "");
					} else if (routeMode.equals("productAndparentRelative")) {
						qMMultiList.addTextCell(index[i], 6, "");
						qMMultiList.addTextCell(index[i], 7, "");
						qMMultiList.addTextCell(index[i], 8, "");
						qMMultiList.addTextCell(index[i], 11, "");
					}
				}
			}
			}
		} else {
			DialogFactory.showWarningDialog(this.getParentJFrame(), "��ѡ�����");
			setCursor(Cursor.getDefaultCursor());
			return;
		}
		setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * ��������Ϣ �Ƿ�Ϸ� 20120113 xucy add
	 */
	public boolean checkLinkAttrs() {
		// boolean flag = false;
		int rowCount = qMMultiList.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String mainStr = "";
			String secondStr = "";
			if (routeMode.equals("productRelative")
					|| routeMode.equals("partRelative")) {
				mainStr = qMMultiList.getCellText(i, 4);
				secondStr = qMMultiList.getCellText(i, 5);

			} else if (routeMode.equals("productAndparentRelative")
					|| routeMode.equals("parentRelative")) {
				mainStr = qMMultiList.getCellText(i, 6);
				secondStr = qMMultiList.getCellText(i, 7);
			}
			if (!secondStr.equals("") && mainStr.equals("")) {
				DialogFactory.showInformDialog(this.getParentJFrame(),
						"��Ҫ·�߲��ܵ������ڣ���������������Ҫ·����Ϣ");
				return false;
			}
		}
		return true;
	}

	/**
	 * �����괴���͸��µĶ�������ñ�����������Ϣ
	 * 
	 * @param 20120116 xucy add
	 */
	public void setMultiListAttrs(ArrayList list) {
		  System.out.println("����2====");
		for (int i = 0, j = list.size(); i < j; i++) {
			ImageIcon image = null;
			ListRoutePartLinkInfo link = (ListRoutePartLinkInfo) list.get(i);
			if (link.getRouteID() != null
					&& !link.getRouteID().trim().equals("")) {
				image = new ImageIcon(getClass().getResource(
						"/images/route.gif"));
				String partNum = qMMultiList.getCellText(i, 2);
				qMMultiList.addCell(i, 2, partNum, image.getImage());
			}
			// else
			// {
			// image = new
			// ImageIcon(getClass().getResource("/images/route_emptyRoute.gif"));
			// }
			if (routeMode.equals("partRelative")) {
				qMMultiList.addTextCell(i, 4, link.getMainStr());
				qMMultiList.addTextCell(i, 5, link.getSecondStr());
				qMMultiList.addTextCell(i, 7, link.getRouteID());
				qMMultiList.addTextCell(i, 8, link.getBsoID());

			} else if (routeMode.equals("productRelative")) {
				qMMultiList.addTextCell(i, 4, link.getMainStr());
				qMMultiList.addTextCell(i, 5, link.getSecondStr());
				qMMultiList.addTextCell(i, 9, link.getRouteID());
				qMMultiList.addTextCell(i, 10, link.getBsoID());
			} else if (routeMode.equals("parentRelative")) {
				qMMultiList.addTextCell(i, 6, link.getMainStr());
				qMMultiList.addTextCell(i, 7, link.getSecondStr());
				qMMultiList.addTextCell(i, 9, link.getRouteID());
				qMMultiList.addTextCell(i, 10, link.getBsoID());
			} else if (routeMode.equals("productAndparentRelative")) {
				qMMultiList.addTextCell(i, 6, link.getMainStr());
				qMMultiList.addTextCell(i, 7, link.getSecondStr());
				qMMultiList.addTextCell(i, 11, link.getRouteID());
				qMMultiList.addTextCell(i, 12, link.getBsoID());
			}
		}
	}

	/**
	 * Ӧ�ÿ��·�� 20120118 xucy add
	 */
	private void applyShortCutRoute(String shortKey, String routeStr) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		// ��Ŵ���·�ߵĶ���
		Vector dataVec = new Vector();
		// ������·�ߵ��м���
		Vector rowVec = new Vector();
		boolean flag = false;
		if (qMMultiList.getTable().isEditing()) {
			qMMultiList.getTable().getCellEditor().stopCellEditing();
		}
		int index[] = qMMultiList.getSelectedRows();
		if (index.length != 0) {
			// �����ݼ�û�ж�Ӧ��·�ߴ��������ʾ��Ϣ
			if (routeStr == null || routeStr.equals("")) {
				Object[] obj2 = { shortKey };
				String s = QMMessage.getLocalizedMessage(RESOURCE, "58", obj2,
						null);
				DialogFactory.showInformDialog(this.getParentJFrame(), s);
				setCursor(Cursor.getDefaultCursor());
				return;
			}
			String mainStr = "";
			String secondStr = "";
			if (routeStr.indexOf(";") == -1) {
				mainStr = routeStr;
			} else {
				mainStr = routeStr.substring(0, routeStr.indexOf(";"));
				secondStr = routeStr.substring(routeStr.indexOf(";") + 1);
			}
			// �������Ƿ���·��
			for (int i = 0; i < index.length; i++) {
				RouteWrapData routeData = this.getSelectedPartData(index[i]);
				if (routeData.getMainStr() != null
						&& !routeData.getMainStr().equals("")) {
					// ������·�ߵ����ݼӵ�������
					dataVec.add(routeData);
					flag = true;
					continue;
				} else {
					rowVec.add(index[i]);
				}
			}
			// �д���·�ߵĶ����򵯳��Ƿ񸲸ǶԻ���
			if (flag) {
				RoutePasteToJDialog pastetodialog = new RoutePasteToJDialog(
						this.getParentJFrame(), dataVec, routeStr);
				pastetodialog.setVisible(true);
				if (pastetodialog.getIsSave()) {
					Vector vec = pastetodialog.getReplaceRows();
					for (int i = 0; i < vec.size(); i++) {
						// ���Ҫ�滻����
						int k = ((Integer) vec.get(i)).intValue();
						// �����滻���������
						if (routeMode.equals("productRelative")
								|| routeMode.equals("partRelative")) {
							qMMultiList.addTextCell(k, 4, mainStr);
							qMMultiList.addTextCell(k, 5, secondStr);
						} else {
							qMMultiList.addTextCell(k, 6, mainStr);
							qMMultiList.addTextCell(k, 7, secondStr);
						}
					}
				}
			}
			// ����·����Ϣ������·�ߵ���
			for (int i = 0; i < rowVec.size(); i++) {
				// �����·�ߵ�������
				int k = ((Integer) rowVec.get(i)).intValue();
				if (routeMode.equals("productRelative")
						|| routeMode.equals("partRelative")) {
					qMMultiList.addTextCell(k, 4, mainStr);
					qMMultiList.addTextCell(k, 5, secondStr);
				} else {
					qMMultiList.addTextCell(k, 6, mainStr);
					qMMultiList.addTextCell(k, 7, secondStr);
				}
			}

		} else {
			DialogFactory.showWarningDialog(this.getParentJFrame(), "��ѡ�����");
			setCursor(Cursor.getDefaultCursor());
			return;
		}
		setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * ���˫��ѡ����У�����·��ͼ����
	 */
	public void mouseClicked(MouseEvent e) {
		// if(e == null)
		// processEditRouteGraph();
	}

	public void mousePressed(MouseEvent e) {
	}

	/**
	 * ѡ���б�������ʱ��������ť�ı仯��� 20120209 xucy
	 */
	public void mouseReleased(MouseEvent e) {
		int index = qMMultiList.getSelectedRow();
		if (index != -1) {
			if (mode == this.VIEW_MODE) {
				qmToolBar.setEnabled("part_view", true);
				qmToolBar.setEnabled("route_editGraph", true);
				qmToolBar.setEnabled("route_parentPart", true);

			} else {
				setCompamentEnable();
			}
		}
	}
	//CCBegin SS20
	 public void addSupplier(String code,String codeBsoid){
		 int index = qMMultiList.getSelectedRow();
		 if (routeMode.equals("partRelative")) {
				 qMMultiList.addTextCell(index, 14, code);
				 qMMultiList.addTextCell(index, 15, codeBsoid);

		 }
		 if (routeMode.equals("productRelative")) {
			   qMMultiList.addTextCell(index, 13, code);
			   qMMultiList.addTextCell(index, 14, codeBsoid);
			 }
		 if (routeMode.equals("parentRelative")) {
			   qMMultiList.addTextCell(index, 14, code);
			   qMMultiList.addTextCell(index, 15, codeBsoid);
			 }
		 if (routeMode.equals("productAndparentRelative")) {
			   qMMultiList.addTextCell(index, 15, code);
			   qMMultiList.addTextCell(index, 16, codeBsoid);  
			 }
		 
		  
	    }
	//CCEnd SS20

//	 CCBegin SS43
		/**
		 * ����㲿���ķ���Դ�汾���ɶ�ר��
		 * @param partID
		 * @return
		 * @throws QMException
		 */
		private String getSourceVersion(QMPartInfo part) throws QMException
		{
			ServiceRequestInfo info = new ServiceRequestInfo();
			info.setServiceName("consTechnicsRouteService");
			info.setMethodName("getSourceVersion");
			Class[] paramClass = { QMPartInfo.class };
			info.setParaClasses(paramClass);
			Object[] paramValue = { part };
			info.setParaValues(paramValue);
			String sourceVersion = (String) RequestHelper.request(info);
			return sourceVersion;
		}
//	 CCEnd SS43
	 
	//CCBegin SS32
	/**
	 * ����㲿���Ľ��·�ߣ��ɶ�ר��
	 * @param subID
	 * @return
	 * @throws QMException
	 */
	private String getJFRouteStr(String partMasterID) throws QMException
	{
		String str = "";

		ServiceRequestInfo info = new ServiceRequestInfo();
		info.setServiceName("TechnicsRouteService");
		info.setMethodName("getPartRouteStrs");
		Class[] paramClass = { String.class };
		info.setParaClasses(paramClass);
		Object[] paramValue = { partMasterID };
		info.setParaValues(paramValue);
		Vector vec = (Vector) RequestHelper.request(info);
		if(vec!=null)
		{
			if(vec.size()==1)
			{
				str = (String)vec.elementAt(0);
			}
			else if(vec.size()>1)
			{
				for(int i=0;i<vec.size();i++)
				{
					String temp = (String)vec.elementAt(i);
					if(temp.indexOf("��")!=-1)
					{
						str = temp;
					}
				}
			}
		}
		if(str==null)
		{
			str = "";
		}
		
		return str;
	}
	//CCEnd SS32
	
	/**
	 * Invoked when the mouse enters a component.
	 */
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
