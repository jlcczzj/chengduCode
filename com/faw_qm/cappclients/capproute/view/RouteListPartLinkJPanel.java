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
 *                        
 *                        
 * CR2  2009/06/08 ������  �μ�:������:v4r3FunctionTest;TD��2286
 * 
 * SS1 ���·���Ƿ��Զ���ȡ����·�ߵĸ�ѡ���ʶ��Ĭ��ѡ�У����Զ��������·�ߣ�Ȼ����·�߱༭�������û������Ƿ��޸ġ� liunan 2013-4-17
 * SS2 �ڱ༭·�߱����tabҳ�У�����������ӹ���   guoxiaoliang 2011-8-3
 * SS3 ���ݽ��Ҫ����Ҫ��ӡ�����������׼����״̬�㲿���� liunan 2014-3-19
 * SS4 ǰ׼����׼�����ơ���׼4������֪ͨ��ȥ����׼�㲿���б���ԭ���İ�ť������֪ͨ������ӡ��͡����ý����ӡ���������ť������֪ͨ�顱�͡�����֪ͨ�顱2014-6-4
 * SS5 ������ɫ����ʶ liuyang 2014-6-6
 * SS6 ������Ϊ��ѡ�� liuyang 2014-6-7
 * SS7 ���ð�ť������֪ͨ�顱�͡�����֪ͨ�顱����ʾ�����͡����Ϊ·�ߡ��Ŀɼ��� liuyang 2014-6-18
 * SS8 ȥ��setCheckModel�����ã�������ѡ��һ���޸���������ɫ�������޷������޸���һ����������ɫ������������ѡ�������У��ٻ������������޸ġ� liunan 2014-12-9
 * SS9 A004-2015-3109��׼�Զ��޸�˵�����ݡ� liunan 2015-5-6
 * SS10 A004-2015-3112���պϼ���·�ߣ����뵥�� liunan 2015-5-26
 * SS11 A004-2015-3112����������ӱ�����ġ����������ơ�����������������״̬�㲿���� liunan 2015-6-11
 * SS12 A004-2015-3112����������ӡ����ơ�״̬�㲿���� liunan 2015-6-26  (2016-8-18������ƼҪ��ȥ��)
 * SS13 A004-2015-3161 ·��״̬��ͬ������ͬ���޷���ȷ��Ӧ���ͻ����뱨��һ�¡� liunan 2015-7-8
 * SS14 SS13�����޸ģ�����㲿���Ǵ�������·�ߣ���״̬���ݶ�Ӧ���ϣ�partindex���ǡ��ޡ�����·�������״̬����Ϊ����ʱ��ȡһ������·�ߵ�״̬�������һ�¡� liunan 2015-8-5
 * SS15 A004-2016-3293 ����״̬�����Ҳ��Ҫ�ܱ��շ�֪ͨ�� liunan 2016-1-25
 * SS16 ����������ͬ�㲿��������ʾ�����ĸ���ͬ�� liunan 2016-3-23
 * SS17 ��ӡ�������ֵ1����ť����������0���������ĳ�1�� liunan 2016-5-16
 * SS18 A004-2016-3390 1�������ظ���ͳһһ���Ի�����ʾ��������һ��һ����ʾ��2�����һ��ɾ���ظ����Ĺ��� liunan 2016-6-29
 * SS19 A004-2016-3436 �����ѷ���״̬��֧�� liunan 2016-12-21
 * SS20 ����㲿����֧�ּ�������֪ͨ���ѵ�����׼�㲿���� liunan 2017-5-2
 * SS21 �������񵥷�������������׼��·���������״̬�㲿����������ǰ��������׼��������Ϊ���� liunan 2017-6-28 �Ļ����� 2017-9-6

 */
package com.faw_qm.cappclients.capproute.view;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.faw_qm.cappclients.capproute.controller.*;
import com.faw_qm.cappclients.capproute.view.*;
import com.faw_qm.cappclients.util.*;
import com.faw_qm.clients.util.*;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.part.client.main.model.*;
import com.faw_qm.part.model.*;
import com.faw_qm.technics.route.model.*;
import com.faw_qm.clients.beans.query.*;
import com.faw_qm.technics.route.util.RouteListLevelType;
import com.faw_qm.cappclients.capp.util.ProgressService;

/**
 * ����·�߱��Ӧ���㲿����ı༭���档 �ڸ��¹���·�߱�ʱ���༭Ҫ���ƹ���·�ߵ��㲿������������㲿����ɾ���㲿����
 * Copyright: Copyright (c) 2004
 * Company: һ������
 * @author ����
 * @mender skybird
 * @version 1.0
 */
public class RouteListPartLinkJPanel
    extends RParentJPanel
    implements RefreshListener {
  /** ѡ������㲿���Ľ��� */
  public SelectPartJDialog selectPartDialog;

  /** �㲿���б� */
  //  private QMMultiList qMMultiList = new QMMultiList();
//CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��
  private ComponentMultiList qMMultiList = new ComponentMultiList();
//CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·�� 

  private JPanel buttonPanel = new JPanel();

  private JButton addStructJButton = new JButton();

  private JButton addJButton = new JButton();
  
//CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,����"����֪ͨ�����"��ť ,"��·�����"��ť,"����"��ť 
//2009-3-18 ԭ�򣺽����������·��,����"��׼֪ͨ�����"��ť 
 JButton routelistButton = new JButton();//��׼��Ӱ�ť
 JButton adoptNoticeButton = new JButton();//����֪ͨ����Ӱ�ť
 JButton newButton = new JButton();//��·����Ӱ�ť
 JButton jsButton = new JButton(); //����������ť
//CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��,����"����֪ͨ�����"��ť  
//CCBegin by leixiao 2010-11-30 ԭ������"������������"��ť 
 JButton publishButton = new JButton();//����֪ͨ����Ӱ�ť
//CCEnd by leixiao 2010-11-30 ԭ������"������������"��ť 
//CCBegin by leixiao 2011-1-12 ԭ������"���ṹ��Ӱ�ť"��ť 
 JButton structButton = new JButton();//�ṹ��Ӱ�ť
//CCEnd by leixiao 2011-1-12 ԭ������"���ṹ��Ӱ�ť"��ť 
 private ArrayList updateLinksList = new ArrayList();//20070530 liuming add �洢�����µ��������Ϣ Ӧ�洢���º�Ĺ��� 
 
 
//CCBegin SS2
  JButton addMultPartsJButton1 = new JButton();
//CCEnd SS2

//CCBegin SS10
  JButton addAssemblyListPartsJButton = new JButton();
//CCEnd SS10

  //CCBegin SS17
  JButton setCountButton = new JButton(); //������ֵ1
  //CCEnd SS17
  
  //CCBegin SS18
  JButton deleteYbSamePartButton = new JButton(); //�����ձ��ظ���
  //CCEnd SS18

  //��¼ѡ�е��б��к�
  private int theSelectedNum;

  /**
   * ҵ�����:·�߱�
   */
  private TechnicsRouteListIfc routeListInfo;

  /** ������Ա��� */
  private static boolean verbose = (RemoteProperty.getProperty(
      "com.faw_qm.cappclients.verbose", "true")).equals("true");

  /** ����:�����г��ֵ������㲿�� */
  private HashMap allParts = new HashMap();

  //����·�߱�ԭ�����������㲿����ɾ������ʣ���㲿������Ҫ���ĸ�����ŵļ��ϲ���
  private Vector partsToChange = new Vector();

  /** �洢ɾ�����㲿������Ϣ */
  private HashMap deleteParts = new HashMap();

  /** �洢����ӵ��㲿������Ϣ */
  private HashMap addedParts = new HashMap();

  private boolean isChangeRouteList = false;

  private JButton parentPartJButton = new JButton();

  private GridBagLayout gridBagLayout1 = new GridBagLayout();

  private GridBagLayout gridBagLayout2 = new GridBagLayout();

  private JPanel jPanel1 = new JPanel();

  private JButton removeJButton = new JButton();

  private GridBagLayout gridBagLayout3 = new GridBagLayout();  

  private JButton upJButton = new JButton();

  private JButton downJButton = new JButton();

  private QMPartIfc theProductifc = null;
  
  //CCBegin by liunan 2012-05-21 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
  JCheckBox manufacturingCheckBox = new JCheckBox();
  //CCEnd by liunan 2012-05-21 
  
  //CCBegin SS1
  public JCheckBox addLastRouteCheckBox = new JCheckBox();
  //CCEnd SS1
  //CCBegin SS6
  public JCheckBox saveAs =new JCheckBox();
  //CCEnd SS6
  
  //CCBegin SS9
  public RouteListTaskJPanel rltJPanel = null;
  //CCEnd SS9
  
  //CCBegin SS18
  public static String samepartstr = "";
  //CCEnd SS18

  /**
   * ���캯��
   */
  public RouteListPartLinkJPanel() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    RefreshService.getRefreshService().addRefreshListener(this);
  }

  private Vector masterToPart(Vector vect) throws QMRemoteException {

    Vector parts = new Vector();
    for (int i = 0; i < vect.size(); i++) {
      QMPartMasterIfc subMaster = (QMPartMasterIfc) vect.elementAt(i);
      parts.add(filterPart(subMaster.getBsoID()));
    }
    return parts;
  }
  
  //CCBegin SS9
  public void setRLTJPanel(RouteListTaskJPanel jp)
  {
  	rltJPanel = jp;
  }
  //CCEnd SS9

  /**
   * ���¶���
   * @param re �����¼�
   */
//CCBegin by leixiao 2008-7-31
  public void refreshObject(RefreshEvent re) {
	   if (re.getSource().equals("addSelectedParts")) {
		
	     Object obj = re.getTarget();
	     if (obj instanceof Vector) {
	       Vector vect = (Vector) obj;
	      //CCBegin SS9 
	      if(vect.elementAt(0) instanceof String)
	      {
	       	 String str = (String)vect.elementAt(0);
	       	 String[] strs = str.split(",");
	       	 vect.remove(0);
	       	 //System.out.println("str===="+str);
	       	 String s = rltJPanel.descriJTextArea.getText().trim();
	       	 //�״����ʱ
	       	 if(s.indexOf("��������PDM��������˵����      ")>0)
	       	 {
	       	 	 s = s.replaceAll("��������PDM��������˵����      ",str.replaceAll(",",""));
	       	 }
	       	 else if(s.indexOf("��������PDM��������˵����/��������֪ͨ��/��Ʒ���ü�����֪ͨ��      ")>0)
	       	 {
	       	 	 s = s.replaceAll("��������PDM��������˵����/��������֪ͨ��/��Ʒ���ü�����֪ͨ��      ",str.replaceAll(",",""));
	       	 }
	       	 else if(s.indexOf("�������Ĳ���֪ͨ�飨PDM��������˵������       ����׼     ")>0)
	       	 {
	       	 	 s = s.replaceAll("�������Ĳ���֪ͨ�飨PDM��������˵������       ����׼     ",str.replaceAll(",",""));
	       	 }
	       	 else if(s.indexOf("��������֪ͨ��������֪ͨ�飩    ")>0)
	       	 {
	       	 	 s = s.replaceAll("��������֪ͨ��������֪ͨ�飩    ",str.replaceAll(",",""));
	       	 }
	       	 else if(s.indexOf("��������PDM��������˵����")>0&&strs[0].equals("��������PDM��������˵����")&&s.indexOf(strs[1])==-1)
	       	 {
	       	 	 int i = s.indexOf("������׼��������׼��");
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("����ǰ׼��������׼��");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("������������׼�����,��Ͷ������");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("������������");
	       	   }
	       	   if(i==-1)
	       	   {
	       	   	 String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
	       	   	 JOptionPane.showMessageDialog(null, "��׼˵�����ݲ������Զ���ӹ���", title, JOptionPane.WARNING_MESSAGE);
             }
	       	 	 s = s.substring(0,i) + "��" + strs[1] + s.substring(i);
	       	 }
	       	 else if(s.indexOf("��Ź�˾����֪ͨ��")>0&&strs[0].equals("��Ź�˾����֪ͨ��")&&s.indexOf(strs[1])==-1)
	       	 {
	       	 	 int i = s.indexOf("������׼��������׼��");
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("����ǰ׼��������׼��");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("������������׼�����,��Ͷ������");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("������������");
	       	   }
	       	   if(i==-1)
	       	   {
	       	   	 String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
	       	   	 JOptionPane.showMessageDialog(null, "��׼˵�����ݲ������Զ���ӹ���", title, JOptionPane.WARNING_MESSAGE);
             }
	       	 	 s = s.substring(0,i) + "��" + strs[1] + s.substring(i);
	       	 }
	       	 else if(s.indexOf("�������Ĳ���֪ͨ��")>0&&strs[0].equals("�������Ĳ���֪ͨ��")&&s.indexOf(strs[1])==-1)
	       	 {
	       	 	 int i = s.indexOf("������׼��������׼��");
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("����ǰ׼��������׼��");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("������������׼�����,��Ͷ������");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("������������");
	       	   }
	       	   if(i==-1)
	       	   {
	       	   	 String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
	       	   	 JOptionPane.showMessageDialog(null, "��׼˵�����ݲ������Զ���ӹ���", title, JOptionPane.WARNING_MESSAGE);
             }
	       	 	 s = s.substring(0,i) + "��" + strs[1] + s.substring(i);
	       	 }
	       	 else if(s.indexOf("��Ź�˾����֪ͨ��")>0&&strs[0].equals("��Ź�˾����֪ͨ��")&&s.indexOf(strs[1])==-1)
	       	 {
	       	 	 int i = s.indexOf("������׼��������׼��");
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("����ǰ׼��������׼��");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("������������׼�����,��Ͷ������");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("������������");
	       	   }
	       	   if(i==-1)
	       	   {
	       	   	 String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
	       	   	 JOptionPane.showMessageDialog(null, "��׼˵�����ݲ������Զ���ӹ���", title, JOptionPane.WARNING_MESSAGE);
             }
	       	 	 s = s.substring(0,i) + "��" + strs[1] + s.substring(i);
	       	 }
	       	 //CCBegin SS10
	       	 else if(s.indexOf("���պϼ���·�ߣ����뵥")>0&&strs[0].equals("���պϼ���·�ߣ����뵥")&&s.indexOf(strs[1])==-1)
	       	 {
	       	 	 int i = s.indexOf("������׼��������׼��");
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("����ǰ׼��������׼��");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("������������׼�����,��Ͷ������");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("������������");
	       	   }
	       	   if(i==-1)
	       	   {
	       	   	 String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
	       	   	 JOptionPane.showMessageDialog(null, "��׼˵�����ݲ������Զ���ӹ���", title, JOptionPane.WARNING_MESSAGE);
             }
	       	 	 s = s.substring(0,i) + "��" + strs[1] + s.substring(i);
	       	 }
	       	 //CCEnd SS10
	       	 
	       	 rltJPanel.descriJTextArea.setText(s);
	      }
	      //CCEnd SS9
	       addPartsList(vect);
	     }
	   }
  }
//CCEnd by leixiao 2008-7-31
  /**
   * ��������
   * @param parts �㲿��
   * @param parent ����
   * @param product ��Ʒ
   * @return
   * @throws QMRemoteException
   */
  public HashMap calCountInProduct(Vector parts, QMPartIfc parent,
                                   QMPartIfc product) throws QMRemoteException {
    if (verbose) {
      System.out.println("��������====" + "part==" + parts + "parent=="
                         + parent + "product==" + product);
    }
    if (product == null) {
      return new HashMap();
    }
    RequestServer server = RequestServerFactory.getRequestServer();
    ServiceRequestInfo info = new ServiceRequestInfo();
    info.setServiceName("TechnicsRouteService");
    info.setMethodName("calCountInProduct");
    Class[] paramClass = {
        Vector.class, QMPartIfc.class,
        QMPartIfc.class};
    info.setParaClasses(paramClass);
    Object[] paramValue = {
        parts, parent, product};
    info.setParaValues(paramValue);
    try {
      return (HashMap) server.request(info);
    }
    catch (QMRemoteException ex) {
      ex.printStackTrace();
      throw ex;
    }

  }

  /**
   * ��������
   * @param parts �㲿��
   * @param parent ����
   * @param product ��Ʒ
   * @return
   * @throws QMRemoteException
   */
  public int calCountInProduct(QMPartIfc part, QMPartIfc parent,
                               QMPartIfc product) throws QMRemoteException {
    if (verbose) {
      System.out.println("��������====" + "part==" + part + "parent=="
                         + parent + "product==" + product);
    }
    RequestServer server = RequestServerFactory.getRequestServer();
    ServiceRequestInfo info = new ServiceRequestInfo();
    info.setServiceName("TechnicsRouteService");
    info.setMethodName("calCountInProduct");
    Class[] paramClass = {
        QMPartIfc.class, QMPartIfc.class,
        QMPartIfc.class};
    info.setParaClasses(paramClass);
    Object[] paramValue = {
        part, parent, product};
    info.setParaValues(paramValue);
    try {
      return ( (Integer) server.request(info)).intValue();
    }
    catch (QMRemoteException ex) {
      ex.printStackTrace();
      throw ex;
    }

  }

  /**
   * ������� �����������ٵ����������������4�����ܲ����Ż���
   * @param vec Collection
   * @return Collection
   */
  private static Collection filterPart(Collection vec) {
    ConfigSpecItem configSpecItem = SelectPartJDialog.getPartTreePanel()
        .getConfigSpecItem();
    if (configSpecItem == null) {
      System.out.println("�������ù淶û�г�ʼ��");
      SelectPartJDialog.getPartTreePanel().setConfigSpecCommand();
      configSpecItem = SelectPartJDialog.getPartTreePanel()
          .getConfigSpecItem();
    }
    try {
      if (configSpecItem == null) {
        Class[] classes = {
            Collection.class};
        Object[] objs = {
            vec};
        return (Collection) CappRouteAction.useServiceMethod(
            "TechnicsRouteService",
            "filteredIterationsOfByDefault", classes, objs);
      }
      else {
        Class[] classes = {
            Collection.class, PartConfigSpecIfc.class};
        Object[] objs = {
            vec, configSpecItem.getConfigSpecInfo()};

        Collection col = (Collection) CappRouteAction.useServiceMethod(
            "StandardPartService", "filteredIterationsOf", classes,
            objs);
        // System.out.println(" else " + col.size());
        return col;
      }
    }
    catch (QMRemoteException e) {
      e.printStackTrace();
      String title = QMMessage.getLocalizedMessage(RESOURCE,
          "information", null);
      JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), title,
                                    JOptionPane.WARNING_MESSAGE);
      return null;
    }

  }

  /**
   * �㲿��������
   * @param master
   * @return
   */
  public static QMPartIfc filterPart(String master) {
    // System.out.println("zz ���� ���� filterPart ");
    ConfigSpecItem configSpecItem = SelectPartJDialog.getPartTreePanel()
        .getConfigSpecItem();
    if (configSpecItem == null) {
      System.out.println("�������ù淶û�г�ʼ��");
      SelectPartJDialog.getPartTreePanel().setConfigSpecCommand();
      configSpecItem = SelectPartJDialog.getPartTreePanel()
          .getConfigSpecItem();
    }
    try {
      QMPartMasterInfo partmaster = (QMPartMasterInfo) CappTreeHelper
          .refreshInfo(master);
      if (configSpecItem == null) {
        Class[] classes = {
            QMPartMasterIfc.class};
        Object[] objs = {
            partmaster};
        //QMPartIfc qq = (QMPartIfc) CappRouteAction.useServiceMethod(
            //"TechnicsRouteService",
            //"filteredIterationsOfByDefault", classes, objs);
        // System.out.println(" filterPart ������configSpecItem == null  " + qq);
        return (QMPartIfc) CappRouteAction.useServiceMethod(
            "TechnicsRouteService",
            "filteredIterationsOfByDefault", classes, objs);
      }
      else {
        Vector vec = new Vector();
        vec.add(partmaster);
        Class[] classes = {
            Collection.class, PartConfigSpecIfc.class};
        Object[] objs = {
            vec, configSpecItem.getConfigSpecInfo()};

        Collection col = (Collection) CappRouteAction.useServiceMethod(
            "StandardPartService", "filteredIterationsOf", classes,
            objs);
        // System.out.println("������֮�� ��collection  " + col.size());
        if (col != null && col.size() > 0) {
          Object[] parts = (Object[]) col.toArray()[0];
          //  System.out.println("filterPart ������ else" + (QMPartIfc) parts[0]);
          return (QMPartIfc) parts[0];

        }
      }
    }
    catch (QMRemoteException e) {
      e.printStackTrace();
      String title = QMMessage.getLocalizedMessage(RESOURCE,
          "information", null);
      JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), title,
                                    JOptionPane.WARNING_MESSAGE);
      return null;
    }
    return null;
  }

  /**
   * private QMPartIfc filterPart(String master) {
   *
   * ConfigSpecItem configSpecItem = SelectPartJDialog.getPartTreePanel()
   * .getConfigSpecItem(); if (configSpecItem == null) {
   * System.out.println("�������ù淶û�г�ʼ��");
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
  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout2);
    buttonPanel.setLayout(gridBagLayout1);
    addStructJButton.setMaximumSize(new Dimension(125, 23));
    addStructJButton.setMinimumSize(new Dimension(125, 23));
    addStructJButton.setPreferredSize(new Dimension(125, 23));
    addStructJButton.setActionCommand("�Ӳ�Ʒ�ṹ�����(P)");
    addStructJButton.setText("�Ӳ�Ʒ�ṹ���(P)");
    addStructJButton.setMnemonic('P');
    addStructJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addStructJButton_actionPerformed(e);
      }
    });
    addJButton.setMaximumSize(new Dimension(75, 23));
    addJButton.setMinimumSize(new Dimension(75, 23));
    addJButton.setPreferredSize(new Dimension(75, 23));
    addJButton.setText("���(A)");
    addJButton.setMnemonic('A');
    addJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addJButton_actionPerformed(e);
      }
    });
//  CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,�ֶ��������� 
//  qMMultiList.addActionListener(new java.awt.event.ActionListener() {
//    public void actionPerformed(ActionEvent e) {
//      qMMultiList_actionPerformed(e);
//    }
//  });
//CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��, 
  
//CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,����"����֪ͨ�����"��ť 
  adoptNoticeButton.setMaximumSize(new Dimension(130, 23));
  adoptNoticeButton.setMinimumSize(new Dimension(130, 23));
  adoptNoticeButton.setPreferredSize(new Dimension(130, 23));
  adoptNoticeButton.setActionCommand("adoptnoticeButton");
  //CCBegin by liunan 2011-04-07 ������������������������ڣ�һ���ǡ�����֪ͨ��š���һ���ǡ����ñ��-��š�
  //adoptNoticeButton.setText("���ø���֪ͨ���");
  adoptNoticeButton.setText("����֪ͨ������");
  //CCEnd by liunan 2011-04-07
  adoptNoticeButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
      adoptNoticeButton_actionPerformed(e);
    }
  });
//CCEnd by leixiao 2008-7-31  ԭ�򣺽����������·��,����"����֪ͨ�����"��ť    
  
//CCBegin by leixiao 2010-11-30 ԭ������"������������"��ť 
  publishButton.setMaximumSize(new Dimension(110, 23));
  publishButton.setMinimumSize(new Dimension(110, 23));
  publishButton.setPreferredSize(new Dimension(110, 23));
  publishButton.setActionCommand("publishButton");
  //CCBegin by liunan 2011-04-07 ������������������������ڣ�һ���ǡ�����֪ͨ��š���һ���ǡ����ñ��-��š�
  //publishButton.setText("����������");

 publishButton.setText("���ý�����");

  //CCEnd by liunan 2011-04-07
  publishButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
    	publishButton_actionPerformed(e);
    }
  });
//CCEnd by leixiao 2010-11-30 ԭ������"������������"��ť   

//CCBegin by leixiao 2011-1-12 ԭ������"���ܳɽṹ���"��ť 
  structButton.setMaximumSize(new Dimension(110, 23));
  structButton.setMinimumSize(new Dimension(110, 23));
  structButton.setPreferredSize(new Dimension(110, 23));
  structButton.setActionCommand("publishButton");
  structButton.setText("�ܳɽṹ���");
  structButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
    	structButton_actionPerformed(e);
    }
  });
//CCEnd by leixiao 2011-1-12 ԭ������"���ܳɽṹ���"��ť   
 
//CCBegin by leixiao 2009-3-18 ԭ�򣺽����������·��,����"��׼֪ͨ�����"��ť 
  routelistButton.setMaximumSize(new Dimension(100, 23));
  routelistButton.setMinimumSize(new Dimension(100, 23));
  routelistButton.setPreferredSize(new Dimension(100, 23));
  routelistButton.setActionCommand("adoptnoticeButton");
  routelistButton.setText("��׼֪ͨ�����");
  routelistButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
  	  addroutelistJButton_actionPerformed(e);
    }
  });
//CCEnd by leixiao 2009-3-18 ԭ�򣺽����������·��,����"��׼֪ͨ�����"��ť    

//CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,����"��·�����"��ť 
  newButton.setMaximumSize(new Dimension(100, 23));
  newButton.setMinimumSize(new Dimension(100, 23));
  newButton.setPreferredSize(new Dimension(100, 23));
  newButton.setText("��·�����");
  newButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
      newButton_actionPerformed(e);
    }
  });
//CCBEnd by leixiao 2008-7-31 ԭ�򣺽����������·��,����"��·�����"��ť  


    //CCBegin SS2
    addMultPartsJButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          addMultPartsJButton1_actionPerformed(e);
        }
      });
      addMultPartsJButton1.setText("�������");
      addMultPartsJButton1.setActionCommand("����������");
      addMultPartsJButton1.setPreferredSize(new Dimension(100, 23));
      addMultPartsJButton1.setMinimumSize(new Dimension(100, 23));
      addMultPartsJButton1.setMaximumSize(new Dimension(100, 23));
    //CCEnd SS2

    //CCBegin SS10
    addAssemblyListPartsJButton.addActionListener(new java.awt.event.ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		addAssemblyListPartsJButton_actionPerformed(e);
    	}
    });
    addAssemblyListPartsJButton.setText("·������");
    addAssemblyListPartsJButton.setActionCommand("�����պϼ���·�ߣ����뵥������");
    addAssemblyListPartsJButton.setPreferredSize(new Dimension(100, 23));
    addAssemblyListPartsJButton.setMinimumSize(new Dimension(100, 23));
    addAssemblyListPartsJButton.setMaximumSize(new Dimension(100, 23));
    //CCEnd SS10
    
//CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,����"��������"��ť 
  jsButton.setMaximumSize(new Dimension(87, 23));
  jsButton.setMinimumSize(new Dimension(87, 23));
  jsButton.setOpaque(true);
  jsButton.setPreferredSize(new Dimension(87, 23));
  jsButton.setText("��������");
  jsButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
      jsButton_actionPerformed(e);
    }
  });
//CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��,����"��������"��ť 

    //CCBegin SS17
    setCountButton.setMaximumSize(new Dimension(87, 23));
    setCountButton.setMinimumSize(new Dimension(87, 23));
    setCountButton.setOpaque(true);
    setCountButton.setPreferredSize(new Dimension(87, 23));
    setCountButton.setText("������ֵ1");
    setCountButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
        setCountButton_actionPerformed(e);
      }
    });
    //CCEnd SS17
    
    //CCBegin SS18
    deleteYbSamePartButton.setMaximumSize(new Dimension(87, 23));
    deleteYbSamePartButton.setMinimumSize(new Dimension(87, 23));
    deleteYbSamePartButton.setOpaque(true);
    deleteYbSamePartButton.setPreferredSize(new Dimension(87, 23));
    deleteYbSamePartButton.setText("�����ձ��ظ���");
    deleteYbSamePartButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
        deleteYbSamePartButton_actionPerformed(e);
      }
    });
    //CCEnd SS18
    
    parentPartJButton.setMaximumSize(new Dimension(99, 23));
    parentPartJButton.setMinimumSize(new Dimension(99, 23));
    parentPartJButton.setPreferredSize(new Dimension(99, 23));
    parentPartJButton.setHorizontalAlignment(SwingConstants.CENTER);
    parentPartJButton.setHorizontalTextPosition(SwingConstants.TRAILING);
    parentPartJButton.setText("��������(R)");
    parentPartJButton.setEnabled(false);
    parentPartJButton.setMnemonic('R');
    parentPartJButton
        .addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        parentPartJButton_actionPerformed(e);
      }
    });

    buttonPanel.setDebugGraphicsOptions(0);
    buttonPanel.setMinimumSize(new Dimension(392, 30));
    buttonPanel.setPreferredSize(new Dimension(397, 30));
    buttonPanel.setInputVerifier(null);
    this.setMaximumSize(new Dimension(392, 285));
    this.setMinimumSize(new Dimension(392, 285));
    this.setPreferredSize(new Dimension(392, 285));
    qMMultiList.setDebugGraphicsOptions(0);
    qMMultiList.setMaximumSize(new Dimension(380, 240));
    qMMultiList.setInputVerifier(null);

//  CCBegin by leixiao 2008-11-12 ԭ�򣺽����������·��,�����ֶ���������   
    qMMultiList.addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        qMMultiList_componentResized(e);
      }
    });

    qMMultiList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        qMMultiList_actionPerformed(e);
      }
    });
//  CCEnd by leixiao 2008-11-12 ԭ�򣺽����������·��,�����ֶ��������� 
    removeJButton.setMaximumSize(new Dimension(75, 23));
    removeJButton.setMinimumSize(new Dimension(75, 23));
    removeJButton.setPreferredSize(new Dimension(75, 23));
    removeJButton.setText("ɾ��(O)");
    removeJButton.setMnemonic('O');
    removeJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        removeJButton_actionPerformed(e);
      }
    });
    jPanel1.setLayout(gridBagLayout3);
    upJButton.setMaximumSize(new Dimension(75, 23));
    upJButton.setMinimumSize(new Dimension(75, 23));
    upJButton.setPreferredSize(new Dimension(75, 23));
    upJButton.setMnemonic('U');
    upJButton.setText("����(U)");
    upJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        upJButton_actionPerformed(e);
      }
    });

    downJButton.setMaximumSize(new Dimension(75, 23));
    downJButton.setMinimumSize(new Dimension(75, 23));
    downJButton.setPreferredSize(new Dimension(75, 23));
    downJButton.setMnemonic('D');
    downJButton.setText("����(D)");
    downJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        downJButton_actionPerformed(e);
      }
    });
//  CCBegin by leixiao 2008-11-12 ԭ�򣺽����������·��,�������µ���
  /*  this.add(buttonPanel, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0,
                                                 GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(
        2, 10, 0, 10), 0, 0));
    buttonPanel.add(parentPartJButton, new GridBagConstraints(0, 0, 1, 1,
        0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(16, 15, 12, 0), 0, 0));
    buttonPanel.add(addStructJButton, new GridBagConstraints(1, 0, 1, 1,
        0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(16, 8, 12, 0), 0, 0));
    buttonPanel.add(addJButton, new GridBagConstraints(2, 0, 1, 1, 0.0,
        0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(16, 8, 12, 0), 0, 0));
    this.add(qMMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                                                 GridBagConstraints.WEST,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(8,
        5, 2, 0), 251, 240));
    this.add(jPanel1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                             GridBagConstraints.CENTER,
                                             GridBagConstraints.NONE,
                                             new Insets(
        0, 0, 0, 0), 16, 148));
    jPanel1.add(removeJButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
        8, 0, 8, 0), 0, 0));
    jPanel1.add(upJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                                  GridBagConstraints.CENTER,
                                                  GridBagConstraints.NONE,
                                                  new Insets(
        8, 0, 8, 0), 0, 0));
    jPanel1.add(downJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
        8, 0, 8, 0), 0, 0));
        */

    //CCBegin by liunan 2012-05-21 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
    manufacturingCheckBox.setFont(new java.awt.Font("SansSerif", 0, 12));
    manufacturingCheckBox.setText("�ձ����������");
    manufacturingCheckBox.setSelected(false);
    manufacturingCheckBox.setVisible(false);
    //CCEnd by liunan 2012-05-21
    
    //CCBegin SS1
    addLastRouteCheckBox.setFont(new java.awt.Font("SansSerif", 0, 12));
    addLastRouteCheckBox.setText("�������·��");
    addLastRouteCheckBox.setSelected(true);
    addLastRouteCheckBox.setVisible(true);
    //CCEnd SS1
    //CCBegin Ss6
	saveAs.setFont(new java.awt.Font("SansSerif", 0, 12));
	saveAs.setText("���Ϊ·��");
	saveAs.setSelected(false);
	saveAs.setVisible(false);

    //CCEnd SS6   
    this.add(buttonPanel,           new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(20, 0, 20, 0), 0, 0));
    buttonPanel.add(addStructJButton,
                     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
    buttonPanel.add(addJButton,   new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
  //CCBegin by leixiao 2011-1-12 ԭ������"���ṹ��Ӱ�ť"��ť 
    buttonPanel.add(structButton,   new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
  //CCEnd by leixiao 2011-1-12 ԭ������"���ṹ��Ӱ�ť"��ť 
    buttonPanel.add(adoptNoticeButton,
                     new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
    //CCBegin by leixiao 2010-11-30 ԭ������"������������"��ť 
    buttonPanel.add(publishButton,
            new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
   ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
  //CCEnd by leixiao 2010-11-30 ԭ������"������������"��ť 
    buttonPanel.add(newButton,   new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    buttonPanel.add(jsButton,   new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
    buttonPanel.add(routelistButton,   new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
    //CCBegin SS2
    buttonPanel.add(addMultPartsJButton1,   new GridBagConstraints(8, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
    //CCEnd SS2
    //CCBegin SS10
    buttonPanel.add(addAssemblyListPartsJButton,   new GridBagConstraints(9, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
    //CCEnd SS10
    this.add(qMMultiList,  new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(8, 5, 2, 0), 0, 0));
    this.add(jPanel1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                             GridBagConstraints.CENTER,
                                             GridBagConstraints.NONE,
                                             new Insets(
        0, 0, 0, 0), 16, 148));
    jPanel1.add(removeJButton,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));
    jPanel1.add(upJButton,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));
    jPanel1.add(downJButton,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));
            
    //CCBegin by liunan 2012-05-21 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
    jPanel1.add(manufacturingCheckBox,   new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));
    //CCEnd by liunan 2012-05-21
    
    //CCBegin SS1
    jPanel1.add(addLastRouteCheckBox,   new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));    
    //CCEnd SS1
//CCBegin SS6
    jPanel1.add(saveAs,   new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));   
    //CCEnd SS6
    
    //CCBegin SS17
    jPanel1.add(setCountButton,   new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));    
    //CCEnd SS17
    //CCBegin SS18
    jPanel1.add(deleteYbSamePartButton,   new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));    
    //CCEnd SS18
    
//  CCEnd by leixiao 2008-11-12 ԭ�򣺽����������·��,�������µ���
//  CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
    //CCBegin SS5
//    qMMultiList.setHeadings(new String[] {"id", "���", "����", "�������", "·��״̬",
 //                           "��ֵ", "routeID", "����", "linkID", "partid", "�汾", "״̬"}); //"��ֵ"quxg add for:·�߱�����Ӷ����ͬ�㲿����<��3��,���ڱ�ʶ��aaa��aaa1��aaa2
    qMMultiList.setHeadings(new String[] {"id", "���", "����", "�������", "·��״̬",
            "��ֵ", "routeID", "����", "linkID", "partid", "�汾", "״̬","��ɫ����ʶ"});

  //  qMMultiList.setRelColWidth(new int[] {0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1});
    qMMultiList.setRelColWidth(new int[] {0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1,1});

    //CCBegin SS8
    //qMMultiList.setCheckModel(true);
    //CCEnd SS8
 
//  qMMultiList.setCellEditable(false);
    //qMMultiList.setColsEnabled(new int[]{7},true); 
    qMMultiList.setColsEnabled(new int[]{7,12},true); 
//  CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
    //CCEnd SS5
    qMMultiList.setMultipleMode(false); //linshi
    qMMultiList.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        setParentPartJButtonEnabled();
      }
    });    
  }

  /**
   * ��������������ť��״̬
   */
  private void setParentPartJButtonEnabled() {
    if (qMMultiList.getSelectedRows() == null) {
      parentPartJButton.setEnabled(false);
    }
    else {
      parentPartJButton.setEnabled(true);
    }
  }

  private Vector sortLinks(Vector v) {

    Vector indexs = routeListInfo.getPartIndex();
    if (verbose) {
      System.out.println("����ǰ v= " + v + " ����indexs = " + indexs);
      System.out.println("����ǰ v= " + v.size() + " ����indexs = " + indexs.size());
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
      partNum = index[1];
      //System.out.println(partid+"=="+partNum);
      ListRoutePartLinkIfc link;
      for (int j = 0; j < v.size(); j++) {
        link = (ListRoutePartLinkIfc) v.elementAt(j);
        //System.out.println(""+link.getRightBsoID()+"      "+link.getParentPartNum());
        if (link.getRightBsoID().equals(partid)) {
          if ( (partNum == null || partNum.trim().equals("")) &&
              (link.getParentPartNum() == null ||
               link.getParentPartNum().trim().equals(""))) {
            //System.out.println("111"+link.getRightBsoID());
            result.add(link);
            v.remove(link);
            break;
          }
          if (partNum != null && link.getParentPartNum() != null
              && partNum.equals(link.getParentPartNum())) {
              	//System.out.println("222"+link.getRightBsoID());
            result.add(link);
            v.remove(link);
            break;
          }
        }
      }
    }
    if (verbose) {
      System.out.println("����� result= " + result);
    }
    return result;
  }

  /**
   * ����·�߱�ֵ��������乤��·�߱���㲿���б�
   *
   * @param routelist
   *            ·�߱�ֵ����
   * @roseuid 4031B9EC0039
   */
  public void setTechnicsRouteList(TechnicsRouteListIfc routelist) {
     //System.out.println("setTechnicsRouteList 614 routelist " + routelist);
    qMMultiList.clear();
    //�����һ�λ��������
    allParts.clear();
    addedParts.clear();
    deleteParts.clear();
    routeListInfo = routelist;
    theProductifc = filterPart(routelist.getProductMasterID());
    // System.out.println("theProductifc ����filterPart���� �õ�ֵ " + theProductifc);
    //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��,��Ϊ��ʷ���򲻿�����ȥ��
    HashMap countMap = new HashMap();
    Vector indexVector = routelist.getPartIndex();
    //CCBegin SS13
    boolean routestateflag = false;
    //CCEnd SS13
    //System.out.println(">>>> routelist = "+routelist.getBsoID());
    //System.out.println("indexVector===="+indexVector.size());
    if(indexVector!=null && indexVector.size()>0)
    {
      int size2= indexVector.size();
      for(int k=0;k<size2;k++)
      {
        String[] ids = (String[])indexVector.elementAt(k);
        String key = "";
        //CCBegin SS13
        /*if(countMap.containsKey(ids[0]))
        {
             key = ids[0]+"K"+k;
        }
        else
        {
             key = ids[0];
        }*/
        String routestate = "";
        if(ids.length>5&&ids[5]!=null)
        {
        	routestate = ids[5];
        	if(!routestateflag)
        	routestateflag = true;
        }
        if(countMap.containsKey(ids[0]+routestate))
        {
             key = ids[0]+routestate+"K"+k;
        }
        else
        {
             key = ids[0]+routestate;
        }
        //System.out.println(key+"=======save partindex========"+ids[2]);
        //CCEnd SS13
        
    //    System.out.println("key = "+key+".........count = "+ids.length+" 1="+ids[0]+" 2="+ids[1]);
//      CCBegin by leixiao 2008-11-5 ԭ�򣺽����������·�� ��������ʷ��û��ids[2]���������´򲻿��˼�  
       if(ids.length>2)
//    	 CCEnd by leixiao 2008-11-5 ԭ�򣺽����������·��
        countMap.put(key,ids[2]);   //����������ظ��Ŀ��ܣ���Ҫ��������
      }
    }
    //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
    Vector vold = this.getRouteListPartLinks();
    System.out.println(routeListInfo.getRouteListNumber()+":indexVector===="+indexVector.size()+"   and PartLinks===="+vold.size());
    Vector v = sortLinks(vold);
    //System.out.println("v===="+v.size());
    //��ǰ·�߱�������㲿������Ϣ������б�
    //�ѵ�ǰ·�߱�������㲿������Ϣ����
    //CCBegin SS18
    samepartstr = "";
    //CCEnd SS18
    if (v != null && v.size() > 0) {
      for (int i = 0; i < v.size(); i++) {
        ListRoutePartLinkIfc link = (ListRoutePartLinkIfc) v
            .elementAt(i);
        QMPartMasterInfo info = (QMPartMasterInfo) link
            .getPartMasterInfo();
//      CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
        
        // String  partid = (String)link.getPartBranchID();
         QMPartIfc part=link.getPartBranchInfo();
         String partid=null;
         String version="";
         String partstate="";
         //CCBegin SS5   
         boolean flag=false;
         if(link.getColorFlag()!=null&&!link.getColorFlag().equals("")){
        	 if(link.getColorFlag().equals("1")){
        		 flag=true;
        	 }     	 
         }     
         //CCEnd SS5
         if(part!=null){
          partid=part.getBsoID();
        version=part.getVersionValue();
        partstate=part.getLifeCycleState().getDisplay();
       //  System.out.println(""+part+" "+partid+"    "+version);
         }
//       CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��

         String coding;
         if (link.getRouteID() == null || link.getRouteID().length() < 1) {
           coding = "��";
         }
         else {
           coding = getCodingDesc(link.getRouteID());
         }
         //CCBegin SS18
         //String finalKey = addPartTohashmap(info,partid,"",allParts);
         String finalKey = addPartTohashmap111(info,partid,"",allParts);
         //CCEnd SS18
         //System.out.println("QMPartMaster = "+info.getBsoID()+" COUNT = "+link.getCount());
         //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
        qMMultiList.addTextCell(i, 0, info.getBsoID());
        qMMultiList.addTextCell(i, 1, info.getPartNumber());
        qMMultiList.addTextCell(i, 2, info.getPartName());
        //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
        qMMultiList.addTextCell(i, 3, theProductifc.getPartNumber());
        qMMultiList.addTextCell(i, 4, coding);
        qMMultiList.addTextCell(i, 5, finalKey);
        qMMultiList.addTextCell(i, 6, link.getRouteID());
        //qMMultiList.addTextCell(i, 7, Integer.toString(link.getCount())); //liuming 20070523 add
        qMMultiList.addTextCell(i, 8, link.getBsoID()); //liuming 20070523 add
//      CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
        qMMultiList.addTextCell(i, 9, partid);
        qMMultiList.addTextCell(i, 10, version);
        qMMultiList.addTextCell(i, 11, partstate);
//      CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
        //CCbegin SS5
        qMMultiList.addCheckboxCell(i, 12, flag);
        //CCEnd SS5
//////////////////////////////////////////////////////////////////////////////////
//      CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·��
        String countInProduct = "";
        String masterID = info.getBsoID();
        //CCBegin SS13
        if(routestateflag)
        {
        	masterID = masterID + coding;
        }
        //System.out.println("masterID========="+masterID);
        //CCEnd SS13
        if(countMap.containsKey(masterID))
        {
          countInProduct = countMap.get(masterID).toString();
          countMap.remove(masterID);
        }
        else
        {
            for(Iterator jt= countMap.keySet().iterator();jt.hasNext();)
            {
              String keya = jt.next().toString();
              if(keya.startsWith(masterID))
              {
                masterID = keya;
                countInProduct = countMap.get(masterID).toString();
                countMap.remove(masterID);
                break;
              }
            }
        }
        qMMultiList.addTextCell(i, 7, countInProduct);
      }
//    CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ�
    }
    //CCBegin SS18
    if (!samepartstr.equals(""))
    {
    	JOptionPane.showMessageDialog(null, "���������ͬ���㲿����"+samepartstr, "����", JOptionPane.WARNING_MESSAGE);
    }
    samepartstr = "";
    //CCEnd SS18
    
    isChangeRouteList = true;
  }
  //CCBegin SS7
 /**
  * ��ʼ��
  */
  public void setPartLinkJPanel(){
	  adoptNoticeButton.setText("����֪ͨ��");
	  publishButton.setText("����֪ͨ��");
	  saveAs.setVisible(true);
  }
  //CCEnd SS7
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
      Class[] c = {
          TechnicsRouteListIfc.class};
      Object[] objs = {
          routeListInfo};
      try {
        nv = (Vector) useServiceMethod("TechnicsRouteService",
                                       "getRouteListLinkParts", c, objs);
      }
      catch (QMRemoteException ex) {
        JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                      QMMessage.getLocalizedMessage(RESOURCE,
            "information",
            null), JOptionPane.INFORMATION_MESSAGE);
      }
    }
    return nv;
  }

  /**
   * Ӧ���㲿����ͨ����������㲿��
   *
   * @roseuid 4031BC2701B7
   */
  private void addPart() {
    //����������
	  //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·�� ��������汾
	    QmChooser qmChooser = new QmChooser("QMPart", "�����㲿��", this
                .getParentJFrame());
	    //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��

//    qmChooser.setChildQuery(false);
    qmChooser.setChildQuery(true);
    //���ո���������ִ������
    qmChooser.addListener(new QMQueryListener() {
      public void queryEvent(QMQueryEvent e) {
        qmChooser_queryEvent(e);
      }
    });
    qmChooser.setVisible(true);
  }

  /**
   * ����㲿���ĸ������ added by skybird 2005.3.4
   * @return
   */
  private void parentPart() {
    int index = qMMultiList.getSelectedRow();
    String thePartBsoid = null;
    String oldParentNum = null;
    setSelectedNum(index);
    if (index != -1) {
      thePartBsoid = qMMultiList.getCellAt(index, 0).toString();
      oldParentNum = qMMultiList.getCellAt(index, 3).toString();
    }
    else {
      JOptionPane.showMessageDialog(this.getParentJFrame(), "��ѡ�����",
                                    "��ʾ", JOptionPane.WARNING_MESSAGE);
      return;
    }
    ParentPartJDialog parentPartJDialog = new ParentPartJDialog(this
        .getParentJFrame());
    parentPartJDialog.setPartNum(thePartBsoid);
    parentPartJDialog.setTechnicsRouteList(this.getTechnicsRouteList());
    parentPartJDialog.setSize(650, 500);
    //this.isChangeRouteList = false;
    parentPartJDialog.setVisible(true);
    if (ParentPartJDialog.isSave) {
      QMPartIfc theParentPart = parentPartJDialog.getSelectedParentPart();
      if (theParentPart == null) {
        return;
      }
      if (this.allParts.containsKey(thePartBsoid
                                    + theParentPart.getPartNumber())) {
        return;
      }
      qMMultiList.addTextCell(theSelectedNum, 3, theParentPart
                              .getPartNumber());
      qMMultiList.addTextCell(theSelectedNum, 4, theParentPart
                              .getPartName());
      //CCBegin SS5
      
      boolean colorFlag=(Boolean)qMMultiList.getCellAt(theSelectedNum, 12).getValue();
      String flag="";
      if(colorFlag)
      {
    	  flag="1";
      }else
      {
    	  flag="0";
      }
      //CCEnd SS5
      String partMasterID = qMMultiList.getCellAt(theSelectedNum, 0)
          .toString();
      int count = 0;
      try {
        count = this.calCountInProduct(filterPart(partMasterID),
                                       theParentPart, this.theProductifc);
      }
      catch (QMRemoteException ex) {
        JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                      "�쳣��Ϣ", JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      qMMultiList.addTextCell(theSelectedNum, 5, new Integer(count)
                              .toString());
      //����Ҫ��������
      //��ͨ�������б�ı��㲿���������б����㲿���ĸ�����ŵ�ʱ�����
      // changeAddedParts(theSelectedNum, theParentPart);
      this.deleteParts.put(thePartBsoid + oldParentNum, this.allParts
                           .get(thePartBsoid + oldParentNum));
      this.addedParts.remove(thePartBsoid + oldParentNum);
      this.addedParts.put(thePartBsoid + theParentPart.getPartNumber(),
                          new Object[] {thePartBsoid, theParentPart,
                          new Integer(count)});


      this.allParts.remove(thePartBsoid + oldParentNum);
      this.allParts.put(thePartBsoid + theParentPart.getPartNumber(),
                        new Object[] {thePartBsoid, theParentPart});

      //��¼Ҫ���ĸ�����ŵ�ԭ�������Ľ��
      // changePartsToChange(theSelectedNum, theParentPart);
    }
  }
  //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·�� 
  private String getCodingDesc(String routeID) {
	    String code = null;
	    if (routeID != null) {
	      Class[] c = {
	          String.class};
	      Object[] objs = {
	          routeID};
	      try {
	        code = (String)this.useServiceMethod("TechnicsRouteService",
	                                             "getRouteCodeDesc", c, objs);
	      }
	      catch (QMRemoteException ex) {
	        JOptionPane.showMessageDialog(this, ex.getClientMessage(),
	                                      QMMessage.getLocalizedMessage(RESOURCE,
	            "information",
	            null), JOptionPane.INFORMATION_MESSAGE);
	      }
	    }
	    return code;

	  }
  //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��


  /**
   * ��ͨ������������ť�������㲿���������б����㲿���ĸ�����ŵ�ʱ�� �ı�addedParts������Ӧ������
   *
   * @param e
   */
  /*
   * public void changeAddedParts(int theSelectedNum, QMPartIfc theParentPart) {
   * String thePartBsoid = qMMultiList.getCellAt(theSelectedNum,
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
   * @param arg1 Vector
   * @param arg2 Vector
   */
  /*private void whenDelete(Vector arg1, Vector arg2) {
    int loop1 = arg1.size();
    int loop2 = arg2.size();
    for (int i = 0; i < loop1; i++) {
      Object[] tmp = (Object[]) arg1.elementAt(i);
      String partBsoid = (String) tmp[0];
      boolean flag1 = false;
      for (int j = 0; j < loop2; j++) {
        Object[] tmp1 = (Object[]) arg2.elementAt(j);
        String partBsoidToCompare = (String) tmp1[0];
        if (partBsoid.equals(partBsoidToCompare)) {
          flag1 = true;
        }
      }
      if (flag1) {
        arg1.remove(i);
      }
    }
  }*/

  /**
   * �Ƿ��ǲ�Ʒ���Ӽ� ������4�����ܲ��ԣ��Ż�
   * @param psVec Vector
   * @return Collection
   */
  private Collection isSubInProduct(Vector psVec) {
    //System.out.println(" zz д�� isSubInProduct"+theProductifc.getPartNumber());
    if (theProductifc == null) {
      return null;
    }
    Class[] classes = {
        Vector.class, QMPartIfc.class};
    Object[] objs = {
        psVec, theProductifc};
    try {
      return (Collection) CappRouteAction.useServiceMethod(
          "TechnicsRouteService",
          "isParentPart", classes, objs);
    }
    catch (QMRemoteException ex) {
      String title = QMMessage.getLocalizedMessage(RESOURCE, "notice",
          null);
      JOptionPane.showMessageDialog(this.getParentJFrame(), ex
                                    .getClientMessage(), title,
                                    JOptionPane.WARNING_MESSAGE);
      return null;
    }

  }

  /**
   * �Ƿ��ǲ�Ʒ���Ӽ�
   * @param partMaster
   * @return
   */
  /*private boolean isSubInProduct(QMPartMasterIfc partMaster) {
    //System.out.println(" 844���� �Ƿ��ǲ�Ʒ���Ӽ�ԭ���� zz");
    //String productID = this.getTechnicsRouteList().getProductMasterID();
    //QMPartIfc theProductifc = this.filterPart(productID);
    if (theProductifc == null) {
      return false;
    }
    QMPartIfc part = filterPart(partMaster.getBsoID());
    Class[] classes = {
        QMPartIfc.class, QMPartIfc.class};
    Object[] objs = {
        part, theProductifc};
    try {
      return ( (Boolean) CappRouteAction.useServiceMethod(
          "StandardPartService", "isParentPart", classes, objs))
          .booleanValue();
    }
    catch (QMRemoteException ex) {
      String title = QMMessage.getLocalizedMessage(RESOURCE, "notice",
          null);
      JOptionPane.showMessageDialog(this.getParentJFrame(), ex
                                    .getClientMessage(), title,
                                    JOptionPane.WARNING_MESSAGE);
      return false;
    }
  }*/

  //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
  public void qmChooser_queryEvent(QMQueryEvent e) {
	    if (verbose) {
	      System.out
	          .println(
	          "capproute.view.RouteListPartLinkJPanel:qmChooser_queryEvent(e) begin...");
	    }

	    if (e.getType().equals(QMQueryEvent.COMMAND)) {
	      if (e.getCommand().equals(QmQuery.OkCMD)) {
	        //��������������������������㲿��
	        QmChooser c = (QmChooser) e.getSource();
	        
	        BaseValueIfc[] bvi = c.getSelectedDetails();
	        Vector v = new Vector();
	        for (int i = 0; i < bvi.length; i++) {
//	          CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
	          QMPartIfc newPart = (QMPartIfc) bvi[i];
	          QMPartMasterIfc partmaster= (QMPartMasterIfc)newPart.getMaster();
	          //System.out.println("  partmaster="+partmaster+" newPart= "+newPart);
              Object []part={partmaster,newPart};
//            CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
	          v.add(part);
	        }
	        addPartsList(v);
	      }
	    }
	    if (verbose) {
	      System.out
	          .println("capproute.view.RouteListPartLinkJPanel:CappChooser_queryEvent(e) end...return is void");
	    }
	  }
  
  private void addPartsList(Vector partsVec) {
	    //System.out.println("addPartsList!!!!!!!!!");
	    for (int i = 0; i < partsVec.size(); i++) {
	    	
	    	Object [] part=(Object[])partsVec.elementAt(i);
//          CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
	    	QMPartMasterIfc subMaster=(QMPartMasterIfc) part[0];
	    	QMPartIfc partifc=(QMPartIfc) part[1];
	    	//CCBegin by leixiao 2011-1-12 ԭ�򣺽��������,��������׼״̬�ű���׼,��׼״̬���ձ�	    	
//	    	//�������׼״̬,��������״̬���ձϵĿ��Ա�·��
	    	System.out.println("------"+partifc.getLifeCycleState()+"--"+routeListInfo.getRouteListState());
	    	//CCBegin by liunan 2011-05-18 ���� ǰ׼ �� ��ȡ�� ֧�֡�
	    	//if(partifc.getLifeCycleState().toString().equals("PREPARING")||(!routeListInfo.getRouteListState().equals("�ձ�")&&partifc.getLifeCycleState().toString().equals("MANUFACTURING"))){
	    	if(partifc.getLifeCycleState().toString().equals("PREPARING")
	    	   ||partifc.getLifeCycleState().toString().equals("ADVANCEPREPARE")
	    	   ||partifc.getLifeCycleState().toString().equals("CANCELLED")
	    	   //CCBegin SS3
	    	   ||partifc.getLifeCycleState().toString().equals("BSXTrialYield")
	    	   //CCEnd SS3
	    	   //CCBegin SS12
	    	   //||partifc.getLifeCycleState().toString().equals("SHIZHI")
	    	   //CCEnd SS12
	    	   //CCBegin SS21
	    	   ||(routeListInfo.getRouteListState().equals("����")&&(partifc.getLifeCycleState().toString().equals("SHIZHI")))
	    	   //CCEnd SS21
	    	   //CCBegin SS15 SS19
	    	   ||(routeListInfo.getRouteListState().equals("�շ�")&&(partifc.getLifeCycleState().toString().equals("BSXDisused")||partifc.getLifeCycleState().toString().equals("DISAFFIRM")))
	    	   //CCEnd SS15  SS19
	    	   //CCBegin SS15 SS20
	    	   ||(routeListInfo.getRouteListState().equals("��׼")&&(partifc.getLifeCycleState().toString().equals("LINZHUN")))
	    	   //CCEnd SS15 SS20
	    	   //CCBegin SS11
	    	   ||partifc.getLifeCycleState().toString().equals("BSXTrialProduce")||partifc.getLifeCycleState().toString().equals("BSXYield")
	    	   //CCEnd SS11
	    	   //CCBegin by liunan 2012-05-21 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
	    	   ||(routeListInfo.getRouteListState().equals("�ձ�")&&manufacturingCheckBox.isSelected()&&partifc.getLifeCycleState().toString().equals("MANUFACTURING"))
	    	   //CCEnd by liunan 2012-05-21
	    	   ||(!routeListInfo.getRouteListState().equals("�ձ�")&&partifc.getLifeCycleState().toString().equals("MANUFACTURING"))){
	    	//CCEnd by liunan 2011-05-18
	    		//CCEnd by leixiao 2011-1-12 ԭ�򣺽��������
	    		//          CCBegin by leixiao 2009-3-26 ԭ�򣺽����������·�ߣ�Ϊ�ձ���ӣ�����׼���·��

	    	String count="";
	    	String routeid="";
	    	if(part.length>3){
	    		count=(String)part[2];	    	
	    	    routeid=(String)part[3];
	    	}
//          CCEnd by leixiao 2009-3-26 ԭ�򣺽����������·�ߣ���¼��ǰ���id
	    	//System.out.println("-----subMaster="+subMaster+" partid="+partifc);
	     // QMPartMasterIfc subMaster = (QMPartMasterIfc) partsVec.elementAt(i);
//          CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id	
	      String subID = subMaster.getBsoID();
	      String partid=partifc.getBsoID();
	      if (this.theProductifc == null) {
	        theProductifc = this.filterPart(routeListInfo
	                                        .getProductMasterID());
	      }
//        CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
	      String finalkey = addPartTohashmap(subMaster,partid,routeid,allParts);
	      System.out.println("finalkeyfinalkeyfinalkey===="+finalkey);
//        CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id     
	      if (finalkey == null) {
	        return;
	      }
	      if (addPartTohashmap(subMaster,partid,routeid, addedParts) == null) {
	        return;
	      }
	      String partNumber = subMaster.getPartNumber();
	      String partName = subMaster.getPartName();
	      String parentPartNum = theProductifc.getPartNumber();
	      String partRouteStatus = "��";
//        CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
	      String partversion=partifc.getVersionValue();
	      String partstate=partifc.getLifeCycleState().getDisplay();
	      
	      String[] listInfo = {
	          subID, partNumber, partName,
	          parentPartNum, partRouteStatus, finalkey, partid,partversion,partstate};
//        CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
	      int row = qMMultiList.getNumberOfRows();
	      qMMultiList.addTextCell(row, 0, listInfo[0]);
	      qMMultiList.addTextCell(row, 1, listInfo[1]);
	      qMMultiList.addTextCell(row, 2, listInfo[2]);
	      qMMultiList.addTextCell(row, 3, listInfo[3]);
	      qMMultiList.addTextCell(row, 4, listInfo[4]);
	      qMMultiList.addTextCell(row, 5, listInfo[5]); 	      
	      qMMultiList.addTextCell(row, 6, routeid);
	      qMMultiList.addTextCell(row, 7, count);
	      qMMultiList.addTextCell(row, 8, "");
//        CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id 
	      qMMultiList.addTextCell(row, 9, listInfo[6]);
	      qMMultiList.addTextCell(row, 10, listInfo[7]);
	      qMMultiList.addTextCell(row, 11, listInfo[8]); 
//        CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
	      //CCBegin SS5	      
	      qMMultiList.setCheckboxSelected(row, 12, false);
	      qMMultiList.setCheckboxEditable(true);
	      //CCEnd SS5
	    }//ifend��׼��������
	    }

	  }
  //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
  /**
   * �����㲿�������¼�����
   * @param e  ���������¼�
   */
  public void qmChooser_queryEventcp(QMQueryEvent e) {
    if (verbose) {
      System.out.println(
          "capproute.view.RouteListPartLinkJPanel:qmChooser_queryEvent(e) begin...");
    }
    if (e.getType().equals(QMQueryEvent.COMMAND)) {
      if (e.getCommand().equals(QmQuery.OkCMD)) {
        //��������������������������㲿��
        QmChooser c = (QmChooser) e.getSource();
        BaseValueIfc[] bvi = c.getSelectedDetails();
        if (bvi != null) {
         //add by guoxl 20080218
          Vector moreVec=new Vector();
          Vector lessVec=new Vector();
        //add by guoxl end

          Vector v = new Vector();
          Vector ps = new Vector();
          Vector nonMasters = new Vector();
          Vector vec = new Vector(bvi.length);
          //add by guoxl on 20080218
          QMPartMasterIfc partMasterIfc=null;
          //add by guoxl end
          for (int j = 0; j < bvi.length; j++) {
          //add by guoxl on 20080218
            partMasterIfc=(QMPartMasterIfc)bvi[j];
            moreVec.addElement(partMasterIfc.getPartNumber());
          //add by guoxl end

            vec.add(bvi[j]);
          }
          // zz start 20061110����·�ָ��ݲ��Ź����㲿�� ,���������������������ù淶���� ������е�������������ֱ�ӷ���
          if (vec != null &&
              routeListInfo.getRouteListLevel().equals(RouteListLevelType.
              SENCONDROUTE.getDisplay())) {
            Collection viatheDepart = null;

            Class[] cla = {
                String.class, Vector.class};
            Object[] obj = {
                routeListInfo.getRouteListDepartment(), vec};
            try {
              viatheDepart = (Collection) CappRouteAction.useServiceMethod(
                  "TechnicsRouteService", "getOptionPart", cla, obj);

            }
            catch (QMRemoteException ex) {
              JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                            QMMessage.getLocalizedMessage(
                  RESOURCE, "exception", null),
                                            JOptionPane.INFORMATION_MESSAGE);
            }

          if(viatheDepart==null||viatheDepart.size()==0){

            //modify by guoxl on 20080218(����·��û�ҷ��ϵ��㲿����������ʾ��Ϣ)
            for(int i=0;i<v.size();i++)
         {
           Object[] array=(Object[])v.elementAt(i);
           partMasterIfc=(QMPartMasterIfc)array[0];
           lessVec.addElement(partMasterIfc.getPartNumber());
         }

         if (moreVec.size() > lessVec.size()) {

           for (int i = 0; i < lessVec.size(); i++) {

             if (moreVec.contains(lessVec.elementAt(i))) {

               moreVec.remove(lessVec.elementAt(i));

             }

           }
           JOptionPane.showMessageDialog(this, "" + moreVec.toString() + "�㲿�����Ǹ�·�ߵ�λ��·�߼�!", "��ʾ��Ϣ",
                                         JOptionPane.INFORMATION_MESSAGE);
         }

          //modify by guoxl end
            return;

          }
          else
            vec = (Vector) viatheDepart;

          }

          // zz end 20061110����·�ָ��ݲ��Ź����㲿��
         Collection colection = filterPart(vec);
           //System.out.println(" ������filterPart������ collection====== " + colection.size());
          if (colection.size() == 0) {
            JOptionPane.showMessageDialog(this, "û�з��ϱ�׼���㲿��", "��ʾ��Ϣ",
                                          JOptionPane.INFORMATION_MESSAGE);
            return;

          }
          Vector vector = new Vector(colection);
          //System.out.println("vector========"+vector);
          for (int ii = 0, jj = vector.size(); ii < jj; ii++) {
            Object[] obj = (Object[]) vector.get(ii);
            if (obj[0] == null) { // û��master��part
              nonMasters.add( (QMPartMasterIfc) bvi[ii]);
            }
            else { //ps
              //System.out.println("obj[0] obj[0] obj[0] " + obj[0]);
              ps.add( (QMPartIfc) obj[0]);
            }
          }

          HashMap map = null;
          try {
            map = this.calCountInProduct(ps, this.theProductifc,
                                         this.theProductifc);
          }
          catch (QMRemoteException ex1) {
            JOptionPane.showMessageDialog(this, ex1
                                         .getClientMessage(), "�쳣��Ϣ",
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
          }
          Collection isCollection = this.isSubInProduct(ps);
          // System.out.println("isSubInProduct �õ��� collection  " + isCollection);
          Vector isornot = null;
          //zz  start
          if (isCollection == null) {
            for (int i = 0; i < ps.size(); i++) {
              QMPartIfc parent = null;
              String parentNum = null;
              Integer count = null;
              
              //��ѡ���ĳһ�㲿������Ϣ
              QMPartIfc pi = (QMPartIfc) ps.elementAt(i);
              QMPartMasterIfc newPart = (QMPartMasterIfc) pi.getMaster();

              if (!allParts.containsKey(newPart.getBsoID()
                                        + parentNum)) {

                //����Ӧ�ü��Դ���
                v.add(new Object[] {newPart, parent});
                allParts
                    .put(newPart.getBsoID() + parentNum,
                         new Object[] {newPart.getBsoID(),
                         parent});
           
                addedParts.put(newPart.getBsoID() + parentNum,
                               new Object[] {newPart.getBsoID(), parent,
                               count});
    
      
         
              }
            }
          }
          // zz end
          else {
            isornot = new Vector(isCollection);
            for (int i = 0; i < ps.size(); i++) {
              QMPartIfc parent = null;
              String parentNum = null;
              Integer count = null;
              //��ѡ���ĳһ�㲿������Ϣ
              QMPartIfc pi = (QMPartIfc) ps.elementAt(i);
              QMPartMasterIfc newPart = (QMPartMasterIfc) pi.getMaster();

              if ( ( (Boolean) isornot.elementAt(i)).equals(new Boolean(true))) {
                parent = theProductifc;
                parentNum = theProductifc.getPartNumber();
                count = (Integer) map.get(pi.getBsoID());

              }

              if ( ( (Boolean) isornot.elementAt(i)).equals(new Boolean(false))) {
                parent = null;

                count = null;

              }

        

              if (!allParts.containsKey(newPart.getBsoID()
                                        + parentNum)) {

                //����Ӧ�ü��Դ���
                v.add(new Object[] {newPart, parent});
                allParts
                    .put(newPart.getBsoID() + parentNum,
                         new Object[] {newPart.getBsoID(),
                         parent});
     
              addedParts.put(newPart.getBsoID() + parentNum,
                             new Object[] {newPart.getBsoID(), parent,
                             count});
  
              }
            }
          }

          this.addPartsToList(v, map, ps);


          for (int i = 0; i < nonMasters.size(); i++) {
            QMPartMasterInfo newPart = (QMPartMasterInfo) nonMasters.elementAt(
                i);
            Object[] obj2 = {
                ( (QMPartMasterInfo) newPart)
                .getPartNumber()};
            String s = QMMessage.getLocalizedMessage(RESOURCE,
                "55", obj2, null);
            String title = QMMessage.getLocalizedMessage(
                RESOURCE, "notice", null);
            JOptionPane.showMessageDialog(this
                                          .getParentJFrame(), s, title,
                                          JOptionPane.WARNING_MESSAGE);

          }
        }
      }
    }
    if (verbose) {
      System.out
          .println("capproute.view.RouteListPartLinkJPanel:CappChooser_queryEvent(e) end...return is void");
    }
  }

  //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·�� 
//CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
  private String addPartTohashmap(QMPartMasterIfc subMaster,String partid,String routeid,
          HashMap map) {
	//  System.out.println("--subMaster="+subMaster+" partid="+partid);
	  String iscomplete="n";
	 if( routeListInfo.getRouteListState().equals("�ձ�")){
		 iscomplete="y";
	 }
		 
String subID = subMaster.getBsoID();
if (this.theProductifc == null) {
theProductifc = this.filterPart(routeListInfo.getProductMasterID());
}
String key = subID + theProductifc.getPartNumber();
if (!map.containsKey(key)) {
map.put(key, new Object[] {subID,theProductifc,partid,routeid,iscomplete});
}
else {
int i = 0;
//CCBegin by leixiao 2009-11-12
int p =0;
//CCEnd by leixiao 2009-11-12
//CCBegin SS16
String samepart = "";
//CCEnd SS16
for (Iterator iter = map.keySet().iterator(); iter.hasNext(); ) {
String key1 = iter.next().toString();
if (key1.startsWith(key)) {
	String partid1=(String)((Object[])map.get(key1))[2];
//	CCBegin by leixiao 2009-11-12
	p++;
//	CCEnd by leixiao 2009-11-12
	//System.out.println("partid1="+partid1+" partid="+partid);
//	CCBegin by leixiao 2009-12-29 ��ʷ��û��partid
	if(partid1==null)
		continue;
//	CCEnd by leixiao 2009-12-29
	if(partid1.equals(partid))
	//CCBegin SS16
	{
    i++;
    try
    {
    	QMPartIfc cpart = (QMPartIfc) CappTreeHelper.refreshInfo(partid);
      if(samepart.equals(""))
      {
    	  samepart = cpart.getPartNumber();
      }
      else
      {
      	if(samepart.indexOf(cpart.getPartNumber())!=-1)
      	{
      		samepart = samepart +"," +cpart.getPartNumber();
      	}
      }
    }
    catch(QMRemoteException qe)
    {
    	qe.printStackTrace();
    }
  }
  //CCEnd SS16
}
}

//CCBegin by liunan 2012-05-21 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
//if (iscomplete.equals("y")&&i >= 1) {
if (iscomplete.equals("y")&&i >= 1&&!manufacturingCheckBox.isSelected()) {
//CCEnd by liunan 2012-05-21
	JOptionPane.showMessageDialog(null,
	              "�ձ��в��������ͬ���㲿����"+samepart,
	              "����",
	              JOptionPane.WARNING_MESSAGE);
	return null;
	}
else if (i >= 3) {
JOptionPane.showMessageDialog(null,
              "��ͬ�㲿����������������"+samepart,
              "����",
              JOptionPane.WARNING_MESSAGE);
return null;   
}
else {
for (int j = 0; j < p; j++) {
key = key + 1;
}
map.put(key, new Object[] {subID,theProductifc, partid,routeid,iscomplete});
}
}
return key;
}
//CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
  //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
  
  //CCBegin SS18
  private String addPartTohashmap111(QMPartMasterIfc subMaster, String partid, String routeid, HashMap map)
  {
  	String iscomplete="n";
  	if( routeListInfo.getRouteListState().equals("�ձ�"))
  	{
  		iscomplete="y";
  	}
  	String subID = subMaster.getBsoID();
  	if (this.theProductifc == null)
  	{
  		theProductifc = this.filterPart(routeListInfo.getProductMasterID());
  	}
  	String key = subID + theProductifc.getPartNumber();
  	if (!map.containsKey(key))
  	{
  		map.put(key, new Object[] {subID,theProductifc,partid,routeid,iscomplete});
  	}
  	else
  	{
  		int i=0;
  		int p =0;
  		for (Iterator iter = map.keySet().iterator(); iter.hasNext(); )
  		{
  			String key1 = iter.next().toString();
  			if (key1.startsWith(key))
  			{
  				String partid1=(String)((Object[])map.get(key1))[2];
  				p++;
  				if(partid1==null)
  				  continue;
  				if(partid1.equals(partid))
  				{
  					i++;
  				}
  			}
  		}
  		
  		if ((iscomplete.equals("y")&&i >= 1&&!manufacturingCheckBox.isSelected())||(i >= 3)) 
  		{
  			try
  			{
  				QMPartIfc cpart = (QMPartIfc) CappTreeHelper.refreshInfo(partid);
  				if(samepartstr.equals(""))
  				{
  					samepartstr = cpart.getPartNumber();
  				}
  				else
  				{
  					if(samepartstr.indexOf(cpart.getPartNumber())==-1)
  					{
  						samepartstr = samepartstr +"," +cpart.getPartNumber();
  					}
  				}
  			}
  			catch(QMRemoteException qe)
  			{
  				qe.printStackTrace();
  			}
  		}
  		
  		for (int j = 0; j < p; j++)
  		{
  			key = key + 1;
  		}
  		map.put(key, new Object[] {subID,theProductifc, partid,routeid,iscomplete});
  	}
  	return key;
  }
  //CCEnd SS18

  /**
   * �Ӳ�Ʒ�ṹ������㲿�������Ը��ݸ��㲿�������²�Ʒ�ṹ���һ���㲿���б���Ϊ��
   * ��·�߱����㲿�����б�ѡ�㲿���������ǰ�༭�Ĺ���·�߱���һ������·�߱���ϵͳ Ӧ�г���Ʒ�ṹ�е������㲿����
   * �����ǰ�༭�Ĺ���·�߱��Ƕ�������·�߱���ϵͳӦ����·�߱�ĵ�λ���ԣ��г���Ʒ �ṹ������һ��·���ж�Ӧ��λ���㲿������Ϊ��ѡ�㲿����
   *
   * @roseuid 4031BC410074
   */
  private void addConstructPart() {
    if (this.isChangeRouteList) {
    	
      selectPartDialog = new SelectPartJDialog(this.getParentJFrame());
      SelectPartJDialog.partTreePanel.getchangeConfig().clear();//CR2
      selectPartDialog.setRouteList(this.getTechnicsRouteList());
      this.isChangeRouteList = false;
    }
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
    if (parts != null && parts.size() > 0) {
      Object[] objs = parts.toArray();
      //  String as[] = new String[parts.size()];
      /* Vector ps = new Vector();
       for (int i = 0; i < parts.size(); i++) {
         Object[] partAndParent = (Object[]) objs[i];
         QMPartMasterIfc partMaster = (QMPartMasterIfc) partAndParent[0];
         ps.add(filterPart(partMaster.getBsoID()));
       }
       HashMap map = null;
       try {
         map = this.calCountInProduct(ps, this.theProductifc,
                                      this.theProductifc);
       }
       catch (QMRemoteException ex1) {
         JOptionPane.showMessageDialog(this, ex1
                                       .getClientMessage(), "�쳣��Ϣ",
                                       JOptionPane.INFORMATION_MESSAGE);
         return;
       }*/

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
          int co = ( (Integer) map.get( ( (QMPartIfc) ps.elementAt(i)).getBsoID())).
              intValue();
          count = new Integer(co).toString();
        }
        String partRouteStatus = "��";
        String[] listInfo = {
            partMaster.getBsoID(), partNumber,
            partName, parentPartNum, parentPartName, count,
            partRouteStatus};
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
  //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
  private void deletePart() {
	    int index = qMMultiList.getSelectedRow();

	    if (index < 0) {
      String title = QMMessage.getLocalizedMessage(RESOURCE,
          "information", null);
      JOptionPane.showMessageDialog(this
                                    .getParentJFrame(), "��ѡ���㲿��", title,
                                    JOptionPane.WARNING_MESSAGE);
      return;
    }
	    if (index != -1) {
	      String adoptStatus = qMMultiList.getCellAt(index, 4).toString();
	      if (!adoptStatus.equals("��")) {
	        int result = JOptionPane.showConfirmDialog(this
	            .getParentJFrame(), "ɾ���㲿����ɾ�����Ĺ���·�ߣ��Ƿ������", "��ʾ",
	            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
	        switch (result) {
	          case JOptionPane.NO_OPTION: {
	            return;
	          }
	        }
	      }
	      String bsoid = qMMultiList.getCellAt(index, 0).toString();
	      String parentNum = qMMultiList.getCellAt(index, 3).toString();
	      String finalkey = qMMultiList.getCellAt(index, 5).toString();
	      String routeID = qMMultiList.getCellAt(index, 6).toString();
//	      CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
	      String partID = qMMultiList.getCellAt(index, 9).toString();
//	      CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
	      QMPartIfc parent = null;
	      if (parentNum != null && !parentNum.trim().equals("")) {
	        parent = (QMPartIfc) ( (Object[])this.allParts.get(finalkey))[1];

	      }
	      allParts.remove(finalkey);
	      if (addedParts.containsKey(finalkey)) {
	        addedParts.remove(finalkey);








	      }
	      else {

	      }
//	      CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
	      deleteParts.put(finalkey,
	                        new Object[] {bsoid, parent, routeID, partID});
//	      CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id

	      //liuming 20070605 add
	      String linkID = qMMultiList.getCellAt(index, 8).toString();
	      
	      //String linkID = qMMultiList.getCellAt(index, 8).toString();
	      if(!linkID.equals(""))
	      {
	        if(updateLinksList.contains(linkID))
	        {
	          updateLinksList.remove(linkID);
	        }
	      }
	      qMMultiList.removeRow(index);

	    }
	  }
  
  //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��

  public void clearPartLinks() {
    this.addedParts = new HashMap();
    this.deleteParts = new HashMap();
  }

  /**
   * ������б���ӵ��㲿���������ύ����˱��档
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
   * @return
   */
  public Vector getPartsToChange() {
    return this.partsToChange;
  }

  /**
   * ������б�ɾ�����㲿���������ύ�����ɾ���� �����Ӧ�ж��㲿�������Ƿ��Ѵ��ڣ�ֻɾ���Ѿ����ڵĹ���
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
   * @return Vector
   */
 /* public Vector getPartIndex() {
    Vector vec = new Vector();
    int rows = qMMultiList.getNumberOfRows();
    for (int i = 0; i < rows; i++) {
      String[] ids = new String[2];
      ids[0] = qMMultiList.getCellText(i, 0);
      ids[1] = qMMultiList.getCellText(i, 3);
      vec.add(ids);
    }
    return vec;
  }*/
  //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��
  public Vector getPartIndex() {
	    Vector vec = new Vector();
	    int rows = qMMultiList.getNumberOfRows();
	    System.out.println("��ȡpartindex��"+rows);
	    for (int i = 0; i < rows; i++) {
	    	//CCBegin SS5
	    	//  String[] ids = new String[4];
	      //CCBegin SS13
	      //String[] ids = new String[5];
	      String[] ids = new String[6];
	      //CCEnd SS13
	      //CCEnd SS5
	      ids[0] = qMMultiList.getCellText(i, 0); //���masterID
	      ids[1] = qMMultiList.getCellText(i, 3); //�������  ʵ���ǲ�Ʒ���
	      ids[2] = qMMultiList.getCellText(i, 7); //����  liuming 20070523 add
//        CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
	      ids[3] = qMMultiList.getCellText(i, 9); //parid
//        CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
	      String partNumber = qMMultiList.getCellText(i, 1);
	      //CCBegin SS5
	      boolean colorFlag=(Boolean)qMMultiList.getSelectedObject(i, 12);
	      if(colorFlag==true)
	      {
	    	  ids[4]="1";
	      }else
	      {
	    	  ids[4]="0";
	      }
	      //CCEnd SS5
	      //CCBegin SS13
	      //CCBegin SS14
	      //ids[5] = qMMultiList.getCellText(i, 4); //·��״̬
	      String rstate = qMMultiList.getCellText(i, 4);
	      if(rstate.equals("��"))
	      {
	      	Class[] c = {String.class};
	      	Object[] objs = {ids[0]};
	      	try
	      	{
	      		ids[5] = (String)useServiceMethod("TechnicsRouteService", "getPartRouteState", c, objs);
	        }
	        catch (QMRemoteException ex)
	        {
	      	  JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                      QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
          }
        }
        else
        {
        	ids[5] = rstate;
        }
	      //CCEnd SS14
	      //System.out.println(i+" ·��״̬��"+ids[5]);
	      //CCEnd SS13
	    //  System.out.println("RouteListPartLink.getPartIndex::::::::::");
	    //  System.out.println(" RouteListPartLinkPanel PartNumber = "+partNumber + "    Count = "+ids[2]);
	      vec.add(ids);
	    }
	    return vec;
	  }

	  //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��


  /**
   * ���ý���Ϊ�鿴״̬
   */
  public void setViewModel() {
    addStructJButton.setVisible(false);
    addJButton.setVisible(false);
//  CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,����"����֪ͨ�����"��ť ,"��·�����"��ť,"����"��ť 
    adoptNoticeButton.setVisible(false);
    newButton.setVisible(false);
    //CCBegin SS2
    addMultPartsJButton1.setVisible(false);
    //CCEnd SS2
    //CCBegin SS10
    addAssemblyListPartsJButton.setVisible(false);
    //CCEnd SS10
    jsButton.setVisible(false);
    routelistButton.setVisible(false);
    publishButton.setVisible(false);
//  CCBEnd by leixiao 2008-7-31 ԭ�򣺽����������·��,����"����֪ͨ�����"��ť ,"��·�����"��ť,"����"��ť  
    structButton.setVisible(false);
    removeJButton.setVisible(false);
    upJButton.setVisible(false);
    downJButton.setVisible(false);
    parentPartJButton.setVisible(false);
    
    //CCBegin by liunan 2012-05-21 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
    manufacturingCheckBox.setVisible(false);
    //CCEnd by liunan 2012-05-21 
    
    //CCBegin SS1
    addLastRouteCheckBox.setVisible(false);
    //CCEnd SS1
    //CCBegin SS6
    saveAs.setVisible(false);
    //CCEnd SS6
    //CCBegin SS17
    setCountButton.setVisible(false);
    //CCEnd SS17
    //CCBegin SS18
    deleteYbSamePartButton.setVisible(false);
    //CCEnd SS18
  }

  /**
   * ���ý���Ϊ�༭״̬
   */
//CCBegin by leixiao 2009-12-04 setEditModel()->setEditModel(route)
  public void setEditModel(TechnicsRouteListIfc route) {
	routeListInfo=route;
    addStructJButton.setVisible(true);
    addJButton.setVisible(true);
//  CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,����"����֪ͨ�����"��ť ,"��·�����"��ť,"����"��ť 
    adoptNoticeButton.setVisible(true);
    newButton.setVisible(true);
    //CCBegin SS2
    addMultPartsJButton1.setVisible(true);
    //CCEnd SS2
    //CCBegin SS10
    addAssemblyListPartsJButton.setVisible(true);
    //CCEnd SS10
    jsButton.setVisible(true);
    publishButton.setVisible(true);
    structButton.setVisible(true);
   // System.out.println(routeListInfo);    
    if(routeListInfo.getRouteListState().equals("�ձ�")){
    	//System.out.println("Ϊ�ձ�");
        routelistButton.setVisible(true);
      //CCBegin by liunan 2012-05-21 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
      manufacturingCheckBox.setVisible(true);
      //CCEnd by liunan 2012-05-21 
        }
        //CCBegin by liunan 2011-09-21 �շ�֪ͨ�顣
        else if(routeListInfo.getRouteListState().equals("�շ�"))
        {
        	routelistButton.setVisible(true);
        }
        //CCEnd by liunan 2011-09-21
        else{
            routelistButton.setVisible(false);
        }
//  CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��,����"����֪ͨ�����"��ť ,"��·�����"��ť,"����"��ť   
    
    //CCBegin SS1
    addLastRouteCheckBox.setVisible(true);
    //CCEnd SS1
    //CCBegin SS6
    if(this.getTechnicsRouteList().getRouteListState().equals("ǰ׼")||this.getTechnicsRouteList().getRouteListState().equals("��׼")||
    		this.getTechnicsRouteList().getRouteListState().equals("����")
				|| this.getTechnicsRouteList().getRouteListState().equals("��׼")) {
    saveAs.setVisible(true);
    }
    //CCEnd SS6
    removeJButton.setVisible(true);
    parentPartJButton.setVisible(true);
    upJButton.setVisible(true);
    downJButton.setVisible(true);
    //CCBegin SS17
    setCountButton.setVisible(true);
    //CCEnd SS17
    //CCBegin SS18
    if(this.getTechnicsRouteList().getRouteListState().equals("�ձ�"))
    {
    	deleteYbSamePartButton.setVisible(true);
    }
    //CCEnd SS18
  }
//CCEnd by leixiao 2009-12-04 
  /**
   * ִ�дӲ�Ʒ�ṹ����Ӳ���
   * @param e ActionEvent
   */
  void addStructJButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    addConstructPart();
    setCursor(Cursor.getDefaultCursor());
  }

  /**
   * ִ����ͨ�������
   * @param e  ActionEvent
   */
  void addJButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    addPart();
    setCursor(Cursor.getDefaultCursor());
  }
//CCBegin by leixiao 2009-3-18 ԭ�򣺽����������·��,����"��׼֪ͨ�����"��ť 
  void addroutelistJButton_actionPerformed(ActionEvent e) {
	  //System.out.println("������׼֪ͨ��");
     // setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      SearchRouteListJDialog d = new SearchRouteListJDialog(this.getParentJFrame(), "������׼֪ͨ��", true);
      d.setVisible(true);
    //  setCursor(Cursor.getDefaultCursor());
	  }
//CCEnd by leixiao 2009-3-18 ԭ�򣺽����������·��,����"��׼֪ͨ�����"��ť  
  /**
   * ִ�в���֪ͨ�����
   *
   * @param e
   *            ActionEvent
   */
//CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,����֪ͨ�����  
  void adoptNoticeButton_actionPerformed(ActionEvent e) {
	    //CCBegin by liunan 2011-04-07 ������������������������ڣ�һ���ǡ�����֪ͨ��š���һ���ǡ����ñ��-��š�
	    //new SearchAdoptNoticeDialog(this.getParentJFrame(), "��������֪ͨ��", true);
	    
	    //CCBegin by liunan 2012-05-22 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
	    //new SearchAdoptNoticeDialog(this.getParentJFrame(), "��������֪ͨ��", true);
		//CCBegin SS4
	    if(manufacturingCheckBox.isSelected())
	    {
	         if(this.getTechnicsRouteList().getRouteListState().equals("ǰ׼")||this.getTechnicsRouteList().getRouteListState().equals("��׼")||
	            		this.getTechnicsRouteList().getRouteListState().equals("����")
	        				|| this.getTechnicsRouteList().getRouteListState().equals("��׼")) {
	        	 new SearchZXChangePartDialog(this.getParentJFrame(), "������֪ͨ�����", true,true,routeListInfo.getProjectId());
	         }else{
	    	     new SearchAdoptNoticeDialog(this.getParentJFrame(), "��������֪ͨ��", true, true);
	         }
	    
	    }
	    else
	    {
	    	
	    	   if(this.getTechnicsRouteList().getRouteListState().equals("ǰ׼")||this.getTechnicsRouteList().getRouteListState().equals("��׼")||
	            		this.getTechnicsRouteList().getRouteListState().equals("����")
	        				|| this.getTechnicsRouteList().getRouteListState().equals("��׼")) {
	    	new SearchZXChangePartDialog(this.getParentJFrame(), "������֪ͨ�����", true, false,routeListInfo.getProjectId());
	    	   }else{
	    		 new SearchAdoptNoticeDialog(this.getParentJFrame(), "��������֪ͨ��", true, false);
	    	   }
	    }
	    	//CCEnd SS4
	    //CCEnd by liunan 2012-05-22
	    
	    //CCEnd by liunan 2011-04-07
	  }
//CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��,����֪ͨ����� 
  
//CCBegin by leixiao 2008-7-31 ԭ�����Ӱ�����������  
  void publishButton_actionPerformed(ActionEvent e) {
	    //CCBegin by liunan 2011-04-07 ������������������������ڣ�һ���ǡ�����֪ͨ��š���һ���ǡ����ñ��-��š�
	    //new SearchPublishDialog(this.getParentJFrame(), "������������", true);
	    
	    //CCBegin by liunan 2012-05-22 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
	    //new SearchPublishDialog(this.getParentJFrame(), "����Ų��ñ�����", true);
		//CCBegin SS4
	    if(manufacturingCheckBox.isSelected())
	    {
	    	  if(this.getTechnicsRouteList().getRouteListState().equals("ǰ׼")||this.getTechnicsRouteList().getRouteListState().equals("��׼")||
	            		this.getTechnicsRouteList().getRouteListState().equals("����")
	        				|| this.getTechnicsRouteList().getRouteListState().equals("��׼")) {
	        	 new SearchZXAdoptPartDialog(this.getParentJFrame(), "������֪ͨ�����", true,true,routeListInfo.getProjectId());
	         }else{
	    	     new SearchPublishDialog(this.getParentJFrame(), "����Ų��ñ�����", true, true);
	         }
	      
	    }
	    else
	    {
	    	  if(this.getTechnicsRouteList().getRouteListState().equals("ǰ׼")||this.getTechnicsRouteList().getRouteListState().equals("��׼")||
	            		this.getTechnicsRouteList().getRouteListState().equals("����")
	        				|| this.getTechnicsRouteList().getRouteListState().equals("��׼")) {
	        	 new SearchZXAdoptPartDialog(this.getParentJFrame(), "������֪ͨ�����", true,false,routeListInfo.getProjectId());
	         }else{
	    	     new SearchPublishDialog(this.getParentJFrame(), "����Ų��ñ�����", false, true);
	         }
	      
	    }
	  //CCEnd SS4
	    //CCEnd by liunan 2012-05-22
	    
	    //System.out.println("1111111111111111");
	    //CCEnd by liunan 2011-04-07
	  }
//CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��,���������� 
  
//CCBegin by leixiao 2011-1-13 ԭ�����Ӱ��ܳɽṹ���  
  void structButton_actionPerformed(ActionEvent e) {
	  SearchStructPartJDialog struct=new SearchStructPartJDialog(this.getParentJFrame());  
	  struct.setVisible(true);
	  }
//CCEnd by leixiao 2011-1-13 ԭ�򣺽����������·��,���ܳɽṹ��� 
 
    
  //CCBegin SS2
  /**������ӹ���
   * @param e
   */
  void addMultPartsJButton1_actionPerformed(ActionEvent e) {
	    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    MultPartsSearchJDialog searchJDialog = new MultPartsSearchJDialog(this.
	        getParentJFrame());
	    searchJDialog.setVisible(true);
	    setCursor(Cursor.getDefaultCursor());

	  }
  //CCEnd SS2

  //CCBegin SS10
  /**�� ���պϼ���·�ߣ����뵥 �������״̬�㲿������
   * @param e
   */
  void addAssemblyListPartsJButton_actionPerformed(ActionEvent e)
  {
  	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  	AssemblyListPartsSearchJDialog searchJDialog = new AssemblyListPartsSearchJDialog(this.getParentJFrame());
	  searchJDialog.setVisible(true);
	  setCursor(Cursor.getDefaultCursor());
	}
  //CCEnd SS10
  
  /**
   * ����������������û��·�ߵ��Ӽ�
   * @param e ActionEvent
   */
//CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,��·�����  
  void newButton_actionPerformed(ActionEvent e) {
     SearchSubDialog d = new SearchSubDialog(this.getParentJFrame(), "�����������", true);
     d.setVisible(true);
  } 
//CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��,��·�����    
  
  /**
   * ����������������ڳ��Ͳ�Ʒ�е�ʹ������������ʾ�ڱ����������
   * @param e ActionEvent
   */
//CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,�������� 
  void jsButton_actionPerformed(ActionEvent e) {
     setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
     ProgressService.setProgressText("���ڴ�������...");
     ProgressService.showProgress();

      String productID = routeListInfo.getProductMasterID();
      int rowCounts = qMMultiList.getNumberOfRows();
      HashMap al = new HashMap();
      for (int i = 0; i < rowCounts; i++)
      {
          String partMasterID = qMMultiList.getCellText(i, 0); //���id
        //  System.out.println("-"+i+"--------partMasterID:"+partMasterID);
          al.put(String.valueOf(i),partMasterID);
      }

      //��productID��alΪ�������÷��񣬷���HashMap key=i, value=����
      Class[] c = { String.class,HashMap.class};
      Object[] objs = { productID,al};

      try {
        al = (HashMap)this.useServiceMethod("TechnicsRouteService",
                                            "getCounts", c, objs);
      // System.out.println("---------------a1:"+al);
        int size = al.size();
        //��������һ�������
        for(int i=0;i<size;i++)
        {
          qMMultiList.addTextCell(i, 7, al.get(String.valueOf(i)).toString());
        }

        //���¹���
        for(int k=0;k<rowCounts;k++)
        {
          String linkID = qMMultiList.getCellAt(k,8).toString();
          if(!linkID.equals("") && !updateLinksList.contains(linkID))
          {
            updateLinksList.add(linkID);
          }
        }


      }
      catch (QMRemoteException ex) {
        JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                      QMMessage.getLocalizedMessage(RESOURCE,
            "information",
            null), JOptionPane.INFORMATION_MESSAGE);

      }
      ProgressService.hideProgress();
      setCursor(Cursor.getDefaultCursor());


  }  
//CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��,�������� 

  /**
   * ִ�����������Ĺ���
   * @param e ActionEvent
   * �в���֮��
   */
  void parentPartJButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    parentPart();
    setCursor(Cursor.getDefaultCursor());
  }

  /**
   * ɾ����ѡ����㲿��
   * @param e ActionEvent
   */
  void removeJButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    deletePart();
    setCursor(Cursor.getDefaultCursor());
  }

//CCBegin by leixiao 2008-11-11 ԭ�򣺽����������·�� ,�����ֶ�����
//void qMMultiList_actionPerformed(ActionEvent e) {
//}
void qMMultiList_componentResized(ComponentEvent e) {
    qMMultiList.redrawColumns();
}

void qMMultiList_actionPerformed(ActionEvent e) {
	  //System.out.println("----11111111111111111");
	     Object obj = e.getSource();
	     int index = qMMultiList.getSelectedRow();
	     //CCBegin SS5
	   //  if(obj instanceof JTextField )
	     if((obj instanceof JTextField || obj instanceof JCheckBox))
	     //CCEnd SS5
	     {
	    	
	       String linkID = qMMultiList.getCellAt(index,8).toString();
	       //System.out.println("----"+linkID);
	       if(!linkID.equals("") && !updateLinksList.contains(linkID))
	       {
	         //System.out.println("Update PART = "+qMMultiList.getCellAt(index,1));
	         updateLinksList.add(linkID);
	       }
	     }
	  }
//CCEnd by leixiao 2008-11-11 ԭ�򣺽����������·��

  /** ���ñ�ѡ�е��� */
  public void setSelectedNum(int arg) {
    theSelectedNum = arg;
  }

  /** ��ȡ��ѡ�е��� */
  public int getSelectedNum() {
    return theSelectedNum;
  }

  /**
   * ִ�������Ʋ��� ʹ��MiltiList�ķ���ȡ��ѡ����
   * ����Ҫ�޸ĵ��� �ж�Ҫ�����Ƶ����Ƿ񵽴�����߽�
   * Added by Ginger 05/05/08
   */
  private void upRow() {
    int selectedRow = qMMultiList.getSelectedRow();
    int changedRow = selectedRow - 1;
    if (selectedRow < 0) {
      String title = QMMessage.getLocalizedMessage(RESOURCE,
          "information", null);
      JOptionPane.showMessageDialog(this
                                    .getParentJFrame(), "��ѡ���㲿��", title,
                                    JOptionPane.WARNING_MESSAGE);
      return;
    }
    if (selectedRow == 0) {
      upJButton.enableInputMethods(false);
    }
    else {
      qMMultiList.changeRow(selectedRow, changedRow);
    }
    if (selectedRow-- <= 0) {
      selectedRow = 0;
    }
    qMMultiList.selectRow(selectedRow);
  }

  private void downRow() {
    int selectedRow = qMMultiList.getSelectedRow();
    if (selectedRow < 0) {
      String title = QMMessage.getLocalizedMessage(RESOURCE,
          "information", null);
      JOptionPane.showMessageDialog(this
                                    .getParentJFrame(), "��ѡ���㲿��", title,
                                    JOptionPane.WARNING_MESSAGE);
      return;
    }
    int changedRow = selectedRow + 1;
    int totalRowNumber = qMMultiList.getNumberOfRows();
    if (selectedRow == (totalRowNumber - 1)) {
      downJButton.enableInputMethods(false);
    }
    else {
      qMMultiList.changeRow(selectedRow, changedRow);
    }
    if (selectedRow++ >= (totalRowNumber - 1)) {
      selectedRow = totalRowNumber - 1;
    }
    qMMultiList.selectRow(selectedRow);
  }

  /**
   * ִ�������¼�
   * @param e ActionEvent
   */
  private void upJButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    upRow();
    setCursor(Cursor.getDefaultCursor());
  }

  /**
   * ִ�������¼�
   * @param e ActionEvent
   */
  private void downJButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    downRow();
    setCursor(Cursor.getDefaultCursor());
  }
  
  //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·�� 
  public ArrayList getUpdateLinks()
  {
    return updateLinksList;
  }
  //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
  //CCBegin SS5
  
  //CCEnd SS6
  
  //CCBegin SS17
  void setCountButton_actionPerformed(ActionEvent e)
  {
  	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  	ProgressService.setProgressText("���ڴ�������...");
  	ProgressService.showProgress();
  	
  	int rowCounts = qMMultiList.getNumberOfRows();
  	System.out.println("rowCounts==="+rowCounts);
  	for (int i = 0; i < rowCounts; i++)
  	{
  		String count = qMMultiList.getCellText(i, 7);
  		System.out.println(i+"  count==="+count);
  		if(count==null||count.equals("")||count.equals("0"))
  		{
  			qMMultiList.addTextCell(i, 7, "1");
  		System.out.println("after count==="+qMMultiList.getCellText(i, 7));
        String linkID = qMMultiList.getCellAt(i,8).toString();
        if(!linkID.equals("") && !updateLinksList.contains(linkID))
        {
        	updateLinksList.add(linkID);
        }
  		}
  	}
    ProgressService.hideProgress();
    setCursor(Cursor.getDefaultCursor());
  }
  //CCEnd SS17

  //CCBegin SS18
  void deleteYbSamePartButton_actionPerformed(ActionEvent e)
  {
  	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  	ProgressService.setProgressText("���ڴ�������...");
  	ProgressService.showProgress();
  	
  	ArrayList numlist = new ArrayList();
  	ArrayList indexlist = new ArrayList();
  	int rowCounts = qMMultiList.getNumberOfRows();
  	for (int i = 0; i < rowCounts; i++)
  	{
  		String num = qMMultiList.getCellAt(i,1).toString();
  		System.out.println("num==="+num);
  		if(num!=null&&!num.equals(""))
  		{
  			if(numlist.contains(num))
  			{System.out.println("11");
  				indexlist.add(0,i);
  			}
  			else
  			{System.out.println("22");
  				numlist.add(num);
  			}
  		}
  	}
  	
  	for(Iterator iter= indexlist.iterator();iter.hasNext();)
  	{
  		int index = (Integer)iter.next();
  		
  		deletePart(index);
  	}
  	
    ProgressService.hideProgress();
    setCursor(Cursor.getDefaultCursor());
  }
  
  
  private void deletePart(int index)
  {
  	System.out.println("index==="+index);
  	if (index < 0)
  	{
      String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
      JOptionPane.showMessageDialog(this.getParentJFrame(), "��ѡ���㲿��", title, JOptionPane.WARNING_MESSAGE);
      return;
    }
    if (index != -1)
    {
	    String bsoid = qMMultiList.getCellAt(index, 0).toString();
	    String parentNum = qMMultiList.getCellAt(index, 3).toString();
	    String finalkey = qMMultiList.getCellAt(index, 5).toString();
	    String routeID = qMMultiList.getCellAt(index, 6).toString();
	    String partID = qMMultiList.getCellAt(index, 9).toString();
	    QMPartIfc parent = null;
	    if (parentNum != null && !parentNum.trim().equals(""))
	    {
	    	parent = (QMPartIfc) ( (Object[])this.allParts.get(finalkey))[1];
	    }
	    allParts.remove(finalkey);
	    if (addedParts.containsKey(finalkey))
	    {
	    	addedParts.remove(finalkey);
	    }
	    deleteParts.put(finalkey, new Object[] {bsoid, parent, routeID, partID});
	    String linkID = qMMultiList.getCellAt(index, 8).toString();
	    if(!linkID.equals(""))
	    {
	    	if(updateLinksList.contains(linkID))
	    	{
	    		updateLinksList.remove(linkID);
	    	}
	    }
	    qMMultiList.removeRow(index);
	  }
	}
  //CCEnd SS18
}
