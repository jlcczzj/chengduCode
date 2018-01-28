/** ����QMProductManager.java	1.0  2003/01/05
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/04/02 л��   ԭ��:�÷���Ϊ�Ҽ��������ڵ���и��²�����ʹ�û������ڵ���Ϣ
 *                       ����:��������
 *                       ��ע:���v3r11-չ���㲿�����ڵ������Ż�
 * CR2 ������ 20090601  ����ԭ�򣺶�ԭ�����и���ʱ�����ø�����������и��²�����Ȼ
 * 						  ��������ԭ�����⵼��ˢ��ʱ���ܶҸ�������ˢ�¡�
 * 						�����������������ȷ�ϣ����ڽ����ʸ���Ϊ�������Ե�ǰ�û��͸�
 * 						  �ĵ��㲿��ԭ�����и���ʱ�����Զ������丱����ӦTABҳ�����
 *   					  ����㲿������û�иü��ĸ���ʱ�����丱����ӵ��㲿�����ϡ�
 * 						��ע��TD����2085
 * CR3  2009/06/16  ��� TD:2339 �ڲ�Ʒ��Ϣ������������㲿�����㲿���������°汾��,��ʾ��Ϣ�д���
 *                       ԭ���ڼ���������㲿����ʹ�������°汾�����Ҳ������һ�Ρ�����ɹ�������ʾ��Ϣ
 *                       ����������㲿���������°汾�����á�����ʧ�ܡ�����ʾ��Ϣ
 *                       
 * CR4  20090616 ��ǿ �޸�ԭ��TD2256 �Ӳ�Ʒ��Ϣ���������빤�չ�̹�������
 *                             Ȼ���ڲ�Ʒ��Ϣ�����������㲿�����ִ�����ʾ��Ϣ��
 *                    �������������ˢ���¼�ʱ�¼�Դ��Vector�޸�ΪWorkableIfc��
 * CR5  20090618 ���� �޸�ԭ��TD2436 �ڲ�Ʒ��Ϣ�����У��㲿��A���������Խ��С�Ӧ�����С�����
 *                             ������������ӵ��㲿��B��,�㲿��B��״̬�����⡣
 *                    ������ÿ�ε���ˢ�¶�����µĲ�����
 * CR6  20090630 ��� �޸�ԭ��TD2432 ��proe�н��㲿������ټ��룬�ڲ�Ʒ��Ϣ�������У�ˢ�¸��㲿����������ʾ�㲿�����°汾
 *                    �޸ķ�����ÿ��ˢ�¶�����ȡһ�����´�汾������С�汾��  
 * CR7  2009/07/06 ��� �޸�ԭ��TD2523 �ڲ�Ʒ��Ϣ�������У��������ù淶�󣬵����ȷ�����������������ʾ��Ϣ
 *                      �޸ķ���: �ڸ��ݵ�ǰ���õ����ù淶ˢ������ʱ���������ϵ���ʾ���ݡ�
 * CR8  20090630 ��� �޸�ԭ��TD2432 ��proe�н��㲿������ټ��룬�ڲ�Ʒ��Ϣ�������У�ˢ�¸��㲿����������ʾ�㲿�����°汾.
 *                    ��ע��ͨ��cr6���޸ģ�ˢ���Ѿ��ܵõ����°汾������ͨ��ˢ�»�����㲿��������������ȫ��ͬ�����°汾������Ҫ������ɾ��һ����
 *                    �޸ķ������������������ȫ��ͬ�����°汾��������ɾ����ǰѡ�еģ������Ƶ���һ����ͬ�Ľڵ��ϡ�                                                                              
 * CR9 2009/12/25 ���� �޸�ԭ���޸��ڲ�Ʒ��Ϣ�������в鿴�㲿������Ϣʱ�Ľ��档
 *                     �޸ķ�����ʹ��û��sidebar��part_version_iterationsViewMain-001.screen����ʾ�� 
 * CCBegin by liunan 2012-04-25 �򲹶�v4r3_p044
 * CR12 2012/03/05 ��ԣ��޸�ԭ�򣺲�Ʒ����ģ��ͳһ�޸�����������
 *                       �޸ķ�����1.��ȡEnterpriseService�����е�hasRenameAccess��������У��
 *                                2.�㲿��ģ��master���������������������
 *                                3.�жϵ�ǰ�������㲿����ͼ�Ƿ�����㲿����һ��С�汾��A.1������ͼ��ͬ�������ͬ��������������������������������  
 * CCEnd by liunan 2012-04-25
 * SS1 2013-1-21  ��Ʒ��Ϣ����������������嵥�����������������erp���Ա����ܣ������ĸ�ʽ�ļ����ݰ������㲿��������š������������������ 
                  ÿ��������������λ����Դ������·�ߡ�װ��·�ߡ���T���ܡ��̶���ǰ�ںϼơ��ɱ���ǰ�ڣ���
                  ���к����������е�������Ҫ���㲿�������Ĺ��տ��л�ȡ
 * SS2 2013-1-21  ��Ʒ��Ϣ�������о��нṹ���ƹ��ܣ����Խ�ѡ���㲿���ṹ��ȫ���ƣ������սṹճ���������㲿����
 * SS3 �����û�����ʱ����㲿����ӦCAD�ĵ���״̬�����Ϊ���״̬����ʾ�û� ��� 2013-11-26
 * SS4 ���ӡ����ӹ�˾�����嵥�� liuyang 2013-12-18
 * SS5 ���"��������ͳ��"�͡������ܳɱ���ͳ�� ��liuyang 2014-6-11
 * SS6 ���˫���������չ���Ľṹ������������� pante 2015-01-07
 * SS7 ���Ӳ�Ʒ��Ϣ�������������λ�����ṹ��������� pante 2015-01-08
 * SS8 �㲿�������ʱ���ж��Ƿ�������õ��ͱ����������й������Զ����㲿�����µ����õ��Ĳ�������б� ������ 2015-02-04
 * SS9 ĳһ�߼��ܳɻ�����ظ������Ƿ����������ʾ��Ϣ����������ظ� ������ 2015-04-21
 * SS10 �ṹ����ʱ,ͬʱѡ����������и��ƹ��� ������ 2015-04-24
 * SS11 �㲿�������ʱ��ֻ�в��õ��ͱ��������������״̬�ǡ���ơ��͡����ء�ʱ���Ÿ��²��õ��ͱ����  ������ 2015-05-17
 * SS12 A004-2015-3158 ѡ��������������½���ʱ�޷����룬��Ϊ����㲿�����̴�����㲿������˼���ǰ��Ҫˢ�¶��� liunan 2015-7-7
 * SS13 A004-2016-3286 ����һ�����嵥 liunan 2016-1-20
 */
package com.faw_qm.part.client.main.controller;
import java.awt.AWTEventMulticaster;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

import com.faw_qm.acl.ejb.entity.QMPermission;
import com.faw_qm.auth.RequestHelper;
import com.faw_qm.baseline.model.BaselineIfc;
import com.faw_qm.cappclients.capp.view.TechnicsRegulationsMainJFrame;
import com.faw_qm.cappclients.capproute.view.CappRouteListManageJFrame;
import com.faw_qm.cappclients.capproute.view.SearchZXAdoptPartDialog;
import com.faw_qm.cappclients.summary.controller.SummaryMainController;
import com.faw_qm.clients.beans.explorer.Explorable;
import com.faw_qm.clients.beans.explorer.QMExplorer;
import com.faw_qm.clients.beans.explorer.QMExplorerEvent;
import com.faw_qm.clients.beans.explorer.QMExplorerListener;
import com.faw_qm.clients.beans.explorer.QMNode;
import com.faw_qm.clients.beans.explorer.QMTree;
import com.faw_qm.clients.beans.query.QMQueryEvent;
import com.faw_qm.clients.beans.query.QMQueryListener;
import com.faw_qm.clients.beans.query.QmChooser;
import com.faw_qm.clients.beans.query.QmQuery;
import com.faw_qm.clients.util.IntroduceDialog;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshListener;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.clients.vc.controller.CheckInOutTaskLogic;
import com.faw_qm.clients.vc.controller.CheckOutTask;
import com.faw_qm.clients.vc.controller.ReviseTask;
import com.faw_qm.clients.vc.controller.UndoCheckoutTask;
import com.faw_qm.clients.vc.util.AlreadyCheckedOutException;
import com.faw_qm.clients.vc.util.CheckInOutException;
import com.faw_qm.clients.vc.util.CheckedOutByOtherException;
import com.faw_qm.clients.vc.util.ObjectNoLongerExistsException;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.eff.model.EffConfigurationItemIfc;
import com.faw_qm.effectivity.model.QMConfigurationItemIfc;
import com.faw_qm.enterprise.model.RevisionControlledIfc;
import com.faw_qm.epm.epmdocument.model.EPMDocumentIfc;
import com.faw_qm.folder.model.FolderBasedIfc;
import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.folder.model.FolderedIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.lifecycle.client.view.LifeCycleStateDialog;
import com.faw_qm.lifecycle.client.view.SetLifeCycleStateDialog;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.client.baseline.controller.BaselineAddController;
import com.faw_qm.part.client.baseline.controller.BaselineCreateController;
import com.faw_qm.part.client.baseline.controller.BaselineRemoveController;
import com.faw_qm.part.client.baseline.controller.BaselineSearchController;
import com.faw_qm.part.client.design.controller.TaskDelegate;
import com.faw_qm.part.client.design.controller.TaskDelegateFactory;
import com.faw_qm.part.client.design.model.PartItem;
import com.faw_qm.part.client.design.model.ProgressService;
import com.faw_qm.part.client.design.model.QMPartInfoTaskDelegate;
import com.faw_qm.part.client.design.model.UsageItem;
import com.faw_qm.part.client.design.model.UsageMasterItem;
import com.faw_qm.part.client.design.util.TaskDelegateException;
import com.faw_qm.part.client.design.view.BaseAttrApplyAllJDialog;
import com.faw_qm.part.client.design.view.ExtendAttrApplyAllJDialog;
import com.faw_qm.part.client.design.view.MessageDialog;
import com.faw_qm.part.client.design.view.PartDesignRenameJDialog;
import com.faw_qm.part.client.design.view.PartTaskJPanel;
import com.faw_qm.part.client.effectivity.controller.EffCreateController;
import com.faw_qm.part.client.effectivity.controller.EffModifyController;
import com.faw_qm.part.client.effectivity.controller.EffRemoveController;
import com.faw_qm.part.client.effectivity.controller.EffSearchController;
import com.faw_qm.part.client.effectivity.view.EffectivityAddJFrame;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.client.main.util.PartShowMasterDialog;
import com.faw_qm.part.client.main.util.QMProductManagerRB;
import com.faw_qm.part.client.main.view.DWSSJDialog;
import com.faw_qm.part.client.main.view.MoveNumberDialog;
import com.faw_qm.part.client.main.view.PartPopupMenu;
import com.faw_qm.part.client.main.view.QMProductManagerJFrame;
import com.faw_qm.part.client.other.controller.BasicSearchController;
import com.faw_qm.part.client.other.controller.BatchCheckInTask;
import com.faw_qm.part.client.other.controller.DifferenceCompareController;
import com.faw_qm.part.client.other.controller.IBASearchController;
import com.faw_qm.part.client.other.controller.MaterialController;
import com.faw_qm.part.client.other.controller.MoveController;
import com.faw_qm.part.client.other.controller.SortController;
import com.faw_qm.part.client.other.controller.StructureConditionController;
import com.faw_qm.part.client.other.view.GPartBOM;
import com.faw_qm.part.client.other.view.LogicBomFrame;
import com.faw_qm.part.client.other.view.NewLogicBomFrame;
import com.faw_qm.part.client.other.view.NewSubCompBomJFrame;
import com.faw_qm.part.client.other.view.NewSubCompBomJFrame;
import com.faw_qm.part.client.other.view.SelectViewNameDialog;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.QMCt;
import com.faw_qm.util.QMThread;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.version.util.VersionControlHelper;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;
//CCEnd by liunan 2008-08-30
//import com.sun.tools.javac.main.JavacOption;
//import com.sun.tools.jdi.EventSetImpl.Itr;
//CCBegin SS13
import com.faw_qm.part.client.other.view.FirstLevelListFrame;
//CCEnd SS13

/**
 *
 * <p>Title: ��Ʒ�ṹ�������������ࡣ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p
 * <p>Company: һ������</p>
 * @author �����
 * @version 1.0
 * �޸�1��2007-10-25��������
 * ԭ�����Ҽ�����ʱ����ѡ�����Ǳ���ǰ�û�����������ǹ�������ʱ�����ø�������
 * ������������Ҽ�����ʱ����ѡ�����Ǳ���ǰ�û�����������ǹ�������ʱ����ֱ�ӷ���
 * �޸�2��2007-11-07����������
 * �޸�ԭ�򣺼���������ʱ����ֻ�и�����ʾ�ڲ�Ʒ���������򸱱�Ҳ����ִ�в�������ʧ
 * �����������ӷ����Լ����������ԭ���Ƿ���ʾ�ڲ�Ʒ�������ϣ�������ʾ��ɾ������������������
 * �޸�3��2007-11-14��������
 * �޸�ԭ�򣺼����������󣬲���������Ϊ����������Ӽ����Ӽ��״̬��Ϊ����״̬
 * ����������޸�ˢ�³���ʹ������ڼ�����������ˢ�²���������Ϊ����������Ӽ�
 * �޸�4��2008-02-25����ǿ
 * ����ԭ�򣺼�����Ӽ���ִ�м��в���ʱ���Ҳ����븸��֮��Ĺ�����ֵ����
 * ����취��������Ӽ���ִ�м��в���ʱ����ȡ�븸��֮��Ĺ�����ֵ����
 * �޸�5��2008-03-06����ǿ
 * ����ԭ�򣺼�����Ӽ���ִ�в����ƶ�����ʱ���Ҳ����븸��֮��Ĺ�����ֵ����
 * ����취��������Ӽ���ִ�в����ƶ�����ʱ����ȡ�븸��֮��Ĺ�����ֵ����
 */
public class QMProductManager extends Container implements ActionListener,
        QMExplorerListener, ItemSelectable
{
    /**���л�ID*/
    static final long serialVersionUID = 1L;

    /**�߳���*/
    protected ThreadGroup threadGroup = null;

    /**Item����*/
    protected ItemListener itemListener = null;

    /**���������*/
    protected Vector myListener = null;

    /**��ǰ���ù淶*/
    protected ConfigSpecItem configSpecItem = null;

    /**��ǰapplet*/
    private JApplet applet = null;

    /**�󶨵���Դ��Ϣ*/
    private static ResourceBundle resource = null;

    /**ָ����*/
    private Object cursorLock = new Object();  
   
    /**ָ��ȴ�*/
    private int waitingOnCursor = 0;

    /**�����㲿��ֵ����*/
    final static int SET_PART_ITEM = 0;

    /**��ͨ����*/
    final static int GENERAL_SEARCH = 1;

    /**�����㲿��*/
    final static int CREATE = 2;

    /**�����㲿��*/
    final static int UPDATE = 3;

    /**�鿴�㲿��*/
    final static int VIEW = 4;

    /**ɾ���㲿��*/
    final static int DELETE = 5;

    /**���*/
    final static int CHECKOUT = 6;

    /**����*/
    final static int CHECKIN = 7;

    /**�������*/
    final static int UNDOCHECKOUT = 8;

    /**�޶�*/
    final static int REVISE = 9;

    /**���õ�ǰ���ù淶*/
    final static int SYSCFIG = 10;

    /**ˢ��*/
    final static int REFRESH = 11;

    /**���*/
    final static int CLEAR = 12;

    /**�ּ������嵥*/
    final static int BUILD_GRADE_BOM = 13;

    /**ͳ�Ʊ�*/
    final static int BUILD_STATISTIC_BOM = 15;

    /**������ͼ�汾*/
    final static int NEWVIEWVERSION = 16;

    /**����*/
    final static int ABOUT = 17;

    /**����*/
    final static int HELP = 18;

    /**���������嵥*/
    final static int TAILOR_BOM = 19;

    /**������Ч��*/
    final static int CREATE_EFFCONFIGITEM = 20;

    /**������Ч��*/
    final static int UPDATE_EFFECTIVITY = 21;

    /**�����Ч��*/
    final static int ADD_EFFECTIVITY = 22;

    /**���ṹ�����Ч��*/
    final static int ADD_EFFECTIVITY_BY_STRUCTURE = 23;

    /**�����滻��*/
    final static int DEFINE_ALTERNATES = 24;

    /**����ṹ�滻��*/
    final static int DEFINE_SUBSTITUTES = 25;

    /**�������*/
    final static int CLEAR_ALL = 26;

    /**�ṹ�Ƚ�*/
    final static int COMPARE_STRUCTURE = 27;

    /**�鿴��Ч��*/
    final static int VIEW_EFFCONFIGITEM = 28;

    /**�Ƴ���Ч��*/
    final static int REMOVE_EFFECTIVITY = 29;

    /**ά����Ч�Է���*/
    final static int MAINTENANCE_EFFCONFIGITEM = 30;

    /**������׼��*/
    final static int CREATE_BASELINE = 31;

    /**ά����׼�߷���*/
    final static int MAINTENANCE_BASELINE = 32;

    /**��ӻ�׼��*/
    final static int ADD_BASELINE = 33;

    /**���ṹ��ӻ�׼��*/
    final static int POPULATE_BASELINE = 34;

    /**�鿴��׼��*/
    final static int VIEW_BASELINE = 35;

    /**�Ƴ���׼��*/
    final static int REMOVE_BASELINE = 36;

    /**����㲿�������ṹ*/
    final static int ADDPART = 37;

    /**�����ṹ��ˢ���޸ĵ��㲿��*/
    final static int REFRESHPART = 39;

    /**�����ṹ��ɾ���㲿��*/
    final static int DELETEPART = 40;

    /**�鿴�汾��ʷ*/
    final static int VIEW_VERSION_HISTORY = 41;

    /**�鿴������ʷ*/
    final static int VIEW_ITERATION_HISTORY = 42;

    /**��������������*/
    final static int BASE_ATTR_SEARCH = 43;

    /**��������������*/
    final static int IBA_SEARCH = 44;

    /**����ָ����������*/
    final static int RESET_LIFECYCLE = 45;

    /**������������״̬*/
    final static int SET_LIFECYCLE_STATE = 46;

    /**��ʾ����������ʷ*/
    final static int SHOW_LIFECYCLE_HISTORY = 47;

    /**���Ϊ*/
    final static int SAVEAS = 48;

    /**����*/
    //add by �ܴ��� 2003.10.21
    final static int SORT = 49;

    /**��������*/
    //add by л�� 2004.04.06
    final static int PUBLISHIBA = 50;

    /**���Ϊ��ʷ*/
    //add by skx 2004.5.12
    final static int SAVEAS_HISTORY = 51;

    /**���ԱȽ�*/
    final static int IBA_COMPARE = 52;

    /**���岿�������嵥*/
    //add by liun 2005.3.9
    final static int GP_BOM_LIST = 53;

    /**�鿴ע����ʷ*/
    final static int CATALOG_HISTORY = 54;

    //added by whj
    final static int COPY = 97;

    final static int PASTE = 98;

    final static int CUT = 99;

    final static int PUBLIC_SORT = 96;
    
    //CCBegin by liunan 2008-07-30
    //��������¼ӱ���ı�ʶ�����ݽ�Ű汾�޸ġ�
    final static int LOGIC=100;
    final static int BUILD_COMPARE_BOM = 60;
    final static int BUILD_FIRSTLEVELSON_BOM = 61;
    //CCEnd by liunan 2008-07-30
  //CCBegin by leix	 2010-12-20  �����߼��ܳ��������� 
    final static int NEWLOGIC=101;
  //CCEnd by leix	 2010-12-20  �����߼��ܳ��������� 
    
    /**�����ṹ��ˢ���޸ĵ��㲿��*/
    final static int REFRESHSOMEPART = 110;
    //CCBegin SS1
    final static int BUILD_ERP_BOM = 111;
    //CCEnd SS1
    //CCBegin SS4
    final static int SUBCOMPBOM = 120;
    //CCEnd SS4
    //CCBegin SS5
    final static int ZCBOM = 130;
    final static int DLZCBOM = 140;
    //CCEnd SS5
    
//    CCBegin SS7
    /**��λ����*/
    final static int DWSS = 141;
//    CCEnd SS7
    
    //CCBegin SS13
    final static int FIRSTLEVEL = 142;
    //CCEnd SS13
    
    /**������*/
    //modify by shf 2003/09/13
    private Object lock = new Object();

    protected Object lock2 = new Object();

    /**�ڲ���*/
    private MainRefreshListener listener;

    /**�����*/
    protected QMExplorer myExplorer = null;

    /**��Ʒ��Ϣ�����������*/
    private QMProductManagerJFrame frame;

    /**���ڱ����Դ��Ϣ·��*/
    protected static final String RESOURCE = "com.faw_qm.part.client.main.util.QMProductManagerRB";

    /**�쳣��Ϣ����:"����"*/
    static String exceptionTitle = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.MESSAGE, null);

    /**ѡ��������*/
    static String errorObject = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.WRANG_TYPE_OBJECT, null);

    /**û��ѡ�����*/
    static String noSelectObj = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.NOT_SELECT_OBJECT, null);

    /**û��ѡ����岿��*/
    //add by liun 2005.3.14
    static String noSelectGPart = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.NO_SELECT_GPART, null);

    /**����ɾ���滻���ͽṹ�滻��*/
    static String alterOrSub = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.ALTER_OR_SUB, null);

    /**�ּ���ͳ�Ʊ��������ԣ�������ʾ�ڽ����ϣ�*/
    static String attrName = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.ATTR_NAME, null);

    /**�ּ���ͳ�Ʊ��������ԣ����ڲ������ݣ�*/
    static String attrEnglishName = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.ATTR_ENG_NAME, null);
     //CCBegin SS1  
     /**�ּ���ͳ�Ʊ��������ԣ�������ʾ�ڽ����ϣ�*/
    static String erpAttrName = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.ERPATTR_NAME, null);

    /**�ּ���ͳ�Ʊ��������ԣ����ڲ������ݣ�*/
    static String erpAttrEnglishName = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.ERPATTR_ENG_NAME, null);
    //CCEnd SS1
//  CCBegin SS2 
    private QMPartMasterIfc allAchePartMasterIfc = null;
    private Vector vcachePartUsageLinkIfc = new Vector();
//  CCEnd SS2
//    //��Դ�㲿��������Ӧ�õ�Ŀ���㲿��//add by muyp 080926
//    static String attrApplyName = QMMessage.getLocalizedMessage(RESOURCE,
//            QMProductManagerRB.APPLY_ATTRIBUTE_TO_OTHER, null);//end
    //liyz add
    /**Ӧ������ѡ��tabҳ����*/
    static String selectObject = QMMessage.getLocalizedMessage(RESOURCE,
            QMProductManagerRB.WRANG_TYPE_SELECT_TAB, null);

    private PartPopupMenu partPop;

    /** Դ�㲿����Master��*/
    private QMTree myTree;

    /** Դ�㲿����Master��*/
    private QMPartMasterIfc sourcePartMaster;

    /** Դ�㲿���ĸ�����*/
    private QMPartIfc sourceParentPart = null;

    /** Ŀ���㲿����*/
    private QMPartIfc targetPart;

    /** ����Դ�㲿���ĸ����ڵ㣬����קԴ�㲿����ˢ���丸�ڵ�ʱʹ�á�*/
    private QMNode tempParentNode = null;

    /** �����ϴ���ק�Ľڵ㡣 */
    private QMNode oldnode = null;

    /** �ƶ��Ľڵ㣬����ק��Դ�㲿���ڵ� */
    private QMNode movenode = null;

    /** �����ϴ���ק�Ľڵ��Ӧ���㲿����*/
    private QMPartIfc oldpart = null;

    /** �����ϴ���ק�Ľڵ��Ӧ���㲿���Ļ�����Ϣ��*/
    private QMPartMasterIfc oldpartmaster= null;

    /** ���渴�ơ����С������ƶ��ķǶ�������ԭ�����㲿���е�ʹ�ù�ϵ��*/
    private PartUsageLinkIfc cachePartUsageLinkIfc = null;

    /** ���渴�ơ����С������ƶ����㲿���Ļ�����Ϣ��*/
    private QMPartMasterIfc cachePartMasterIfc = null;
    
    //CCBegin SS10
    /** ���渴�ơ����С������ƶ����㲿���Ļ�����Ϣ��*/
    private Vector cachePartMasterVec = new Vector();
    //CCEnd SS10
   

    private PartHelper helper = new PartHelper();

    boolean iscopy = true;

    private boolean isdraged = false;

    private Vector workablecopy=new Vector();
    
    ResourceBundle resourcebundle = ResourceBundle.getBundle(
            "com.faw_qm.part.client.main.util.QMProductManagerRB",
            RemoteProperty.getVersionLocale());

    /** �����ƶ���ʹ�������� */
    private float bmovenum = 1;
    public boolean fromcapp=false;
    private boolean capp=false;
    
    //add by muyp 20080623 begin
    /**Ӧ������*/
    final static int APPLY_TO_ALL_PARTS = 55;
  
    
    /**
     * �Ƿ�װ�˲�Ʒ����ģ��
     */
    String hasPcfg = com.faw_qm.framework.remote.RemoteProperty.getProperty(
            "com.faw_qm.hasPcfg", "true");
    
    //������ڵ��µ��㲿����Ϣ��
    //key���㲿������Ϣ��BsoId���㲿���Ĵ�汾�ţ�ֵ���㲿������Ϣֵ����
    private HashMap partMap=new HashMap();

    /**
     * ���캯��������һ��������
     * @param qe QMExplorer �����������
     */
    //�ܴ�Ԫ add for capp
    public QMProductManager(QMExplorer qe)
    {
        this.myExplorer = qe;
        this.threadGroup = QMCt.getContext().getThreadGroup();
        if(null == listener)
        {
            RefreshService.getRefreshService().addRefreshListener(
                    listener = new MainRefreshListener());
        }
        try
        {
            jbInit();
            getConfigSpecItem();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * ���캯���������һ��������Ҫ��Ϊcapp�ṩ������ֵΪtrue��
     * @param qe QMExplorer �����������
     */
    //������ add for capp 2007.06.29
    public QMProductManager(QMExplorer qe,boolean cp)
    {
    	this.fromcapp=cp;
        this.myExplorer = qe;
        this.threadGroup = QMCt.getContext().getThreadGroup();
        if(null == listener)
        {
            RefreshService.getRefreshService().addRefreshListener(
                    listener = new MainRefreshListener());
        }
        try
        {
            jbInit();
            getConfigSpecItem();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ���캯����
     */
    public QMProductManager()
    {
        this.threadGroup = QMCt.getContext().getThreadGroup();
        if(null == listener)
        {
            RefreshService.getRefreshService().addRefreshListener(
                    listener = new MainRefreshListener());
        }
        try
        {
            jbInit();
            getConfigSpecItem();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ��ȡ�������
     * @return QMExplorer �����������
     */
    //�ܴ��� add for sort 2003.10.23
    public QMExplorer getExplorer()
    {
        return myExplorer;
    }

    /**
     * ���ô���
     * @param frame QMProductManagerJFrame ��Ʒ��Ϣ����������档
     */
    public void setFrame(QMProductManagerJFrame frame)
    {
        this.frame = frame;
    }

    /**
     * by muyp
     * @throws Exception
     */
    private void setWorkableCopy(WorkableIfc workableIfc)
    {
    	
    	workablecopy.addElement(workableIfc);
    }
    
    /**
     * by muyp
     * @throws Exception
     */
    private Vector getWorkableCopy()
    {
    	return workablecopy;
    }
    
    /*
     * �����ʼ����
     */
    private void jbInit() throws Exception
    {
        this.setLayout(new BorderLayout());
        this.add(myExplorer, BorderLayout.CENTER);
        myExplorer.setDisplayUsesAsContents(true);
        myExplorer.addListener(this);
        myExplorer.setManager(this);
        myTree = myExplorer.getTree();
        if(myExplorer.getIsPart())
        {
//        	add whj 2008/08/08
            partPop = new PartPopupMenu(this);
           // partPop.setManager(this);
            partPop.setInvoker(myTree);
            TreeMouse treem = new TreeMouse();
            myTree.addMouseListener(treem);
            myTree.addMouseMotionListener(treem);
        }
        localize();
    }

    /*
     * ���ػ���Ϣ��
     */
    protected void localize() throws QMException
    {
        ResourceBundle rb = getPropertiesRB();
        java.lang.String[] tempString;
        java.lang.String[] tempString1;
        java.lang.String[] tempString2;
        java.lang.String temp;
        try
        {
            tempString = getValueSet(rb, "partexplorer.explorer.list.headings");
            myExplorer.setListCellFont(new java.awt.Font("Dialog", 0, 12));
            tempString = getValueSet(rb, "partexplorer.explorer.list.methods");
            myExplorer.setListMethods(tempString);
            tempString = getValueSet(rb,
                    "partexplorer.explorer.list.columnAlignments");
            myExplorer.setListColumnAlignments(tempString);
            tempString = getValueSet(rb,
                    "partexplorer.explorer.list.columnSizes");
            tempString = getValueSet(rb,
                    "partexplorer.explorer.list.columnWidths");
//            //liyz add ��properties�������Ƿ����������
//            //�����ã����𻷾�ע��begin
//            java.util.Properties prop=new Properties();
//            FileInputStream fis = 
//                 new FileInputStream("F:/PDMV4/product/productfactory/phosphor/cpdm/classes/properties/part.properties");
//            prop.load(fis);
//            String sort=prop.getProperty( "manager.compositor");
//            //end
            String sort = RemoteProperty.getProperty("manager.compositor");
            Boolean b=new Boolean(sort);
            String[] sort1=getValueSet(rb, "partexplorer.explorer.toolbar.iconSort");
            String[] sort2 = getValueSet(rb, "partexplorer.explorer.toolbar.textSort");
            String[] sort3 = getValueSet(rb,
                    "partexplorer.explorer.toolbar.singleSort");
            tempString = getValueSet(rb, "partexplorer.explorer.toolbar.icons");
            tempString1 = getValueSet(rb, "partexplorer.explorer.toolbar.text");
            tempString2 = getValueSet(rb,
                    "partexplorer.explorer.toolbar.single");
            if(b.booleanValue())
            {
                myExplorer.setTools(sort1,sort2,sort3);
            }
            else
            {
            	myExplorer.setTools(tempString, tempString1, tempString2);
            }
            //end
            temp = getValue(rb, "partexplorer.explorer.tree.statusBarText");
            myExplorer.setTreeStatusBarFont(new java.awt.Font("Dialog", 0, 12));
            myExplorer.setTreeStatusBarText(temp);
            temp = getValue(rb, "partexplorer.explorer.list.statusBarText");
            myExplorer.setListFont(new java.awt.Font("Dialog", 0, 12));
            myExplorer.setListHeadingFont(new java.awt.Font("Dialog", 0, 12));
            myExplorer.setListStatusBarFont(new java.awt.Font("Dialog", 0, 12));
            myExplorer.setListStatusBarText(temp);
            temp = getValue(rb, "partexplorer.explorer.tree.rootNodeText");
            myExplorer.setRootNodeText(temp);
            myExplorer.setTreeFont(new java.awt.Font("Dialog", 0, 12));
            //myExplorer.getPartTaskJPanel().addList((QMPartList)myExplorer.getList());
            //myExplorer.getPartTaskJPanel().addList((NewQMList)myExplorer.getList());//CR1
            //������λ��
            tempString = null;
            tempString1 = null;
            tempString2 = null;
            temp = null;
            rb = null;
        }
        catch (java.beans.PropertyVetoException e)
        {
            e.printStackTrace();
        }
        catch (java.util.MissingResourceException mse)
        {
            mse.printStackTrace();
        } catch (Exception e)
		{
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
    }

    /*
     * �����Դ��Ϣ��
     */
    protected ResourceBundle getPropertiesRB()
    {
        if(null == resource)
        {
            initResources();
        }
        return resource;
    }

    /**
     * ����Դ�ļ��л�ö�Ӧ�ؼ��ֵ���Ϣ��
     * @param rb ResourceBundle �󶨵���Դ�ļ���
     * @param key String �ؼ��֡�
     * @return String[] ��Դ�ļ��йؼ��ֶ�Ӧ����Ϣ�顣
     */
    protected String[] getValueSet(ResourceBundle rb, String key)
    {
        String[] values = null;
        try
        {
            String value = rb.getString(key);
            StringTokenizer st = new StringTokenizer(value, ",");
            int count = st.countTokens();
            values = new String[count];
            for (int i = 0; i < count; i++)
            {
                values[i] = st.nextToken();
            }
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return values;
    }

    /**
     *����Դ�ļ��л�ö�Ӧ�ؼ��ֵ���Ϣ��
     * @param rb ResourceBundle �󶨵���Դ�ļ���
     * @param key String �ؼ��֡�
     * @return String ��Դ�ļ��йؼ��ֶ�Ӧ����Ϣ��
     */
    protected String getValue(ResourceBundle rb, String key)
    {
        String values = null;
        try
        {
            values = rb.getString(key);
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
        }
        return values;
    }

    /**
     *��ʼ����Դ��
     */
    protected void initResources()
    {
        try
        {
            if(null == resource)
            {
                Locale locale = QMCt.getContext().getLocale();
                resource = ResourceBundle.getBundle(RESOURCE, locale);
            }
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
        }
    }

    /**
     *
     * <p>Title: ���ƽ�������в������̡߳�</p>
     * <p>Description: �ڲ��ࡣ</p>
     * <p>Copyright: Copyright (c) 2003</p>
     * <p>Company: һ������</p>
     * @author �����
     * @version 1.0
     */
    class WorkThread extends QMThread
    {
        /**�¼��ı��*/
        int action;

        /**��ǰ�㲿��*/
        QMPartIfc part = null;

        /**��ǰ�㲿��������Ϣ*/
        QMPartMasterIfc partMaster = null;

        /**
         * ���캯������������������
         * @param threadGroup ThreadGroup �߳��顣
         * @param action int �¼��ı�š�
         */
        public WorkThread(ThreadGroup threadGroup, int action)
        {
            super();
            this.action = action;
        }

        /**
         * ���캯������������������
         * @param threadGroup ThreadGroup �߳��顣
         * @param action int �¼��ı�š�
         * @param part QMPartIfc ��ǰ�㲿��ֵ����
         */
        public WorkThread(ThreadGroup threadGroup, int action, QMPartIfc part)
        {
            super();
            this.action = action;
            this.part = part;
        }

        /**
         * ���캯������������������
         * @param threadGroup ThreadGroup �߳��顣
         * @param action int �¼��ı�š�
         * @param partmaster QMPartMasterIfc ��ǰ�㲿��������Ϣ��
         */
        public WorkThread(ThreadGroup threadGroup, int action,
                QMPartMasterIfc partmaster)
        {
            super();
            this.action = action;
            this.partMaster = partmaster;
        }

        /**
         * ִ�С�
         */
        public void run()
        {
            try
            {
                synchronized (QMProductManager.this.cursorLock)
                {
                    waitingOnCursor++;
                }
                switch (action)
                {
                    case SET_PART_ITEM:
                        break;
                    case GENERAL_SEARCH:
                        general_search();
                        break;
                    case CREATE:
                        newObject();
                        break;
                    case SAVEAS:
                        saveAsPart();
                        break;
                    case UPDATE:
                        editSelectedObject();
                        break;
                    case VIEW:
                        viewSelectedObject();
                        break;
                    case DELETE:
                        deleteSelectedObject();
                        break;
                    case CHECKOUT:
                        checkoutSelectedObjects();
                        break;
                    case CHECKIN:
                        checkinSelectedObjects();
                        break;
                    case UNDOCHECKOUT:
                        undoCheckoutSelectedObjects();
                        break;
                    case REVISE:
                        reviseSelectedObject();
                        break;
                    case SYSCFIG:
                        setConfigSpecCommand(true);
                        break;
                    case REFRESH:
                        refreshSelectedObject();
                        break;
                    case CLEAR:
                        clear();
                        break;
                    case BUILD_GRADE_BOM:
                        buildSelectedGradeBOM();
                        break;  
//                  CCbegin SS1BUILD_ERP_BOM
                    case BUILD_ERP_BOM:  
                        buildSelectedErpBOM();
                        break; 
//                      CCend SS1
                    case BASE_ATTR_SEARCH:
                        baseAttrSearch();
                        break;
                    case IBA_SEARCH:
                        ibaSearch();
                        break;
                    case BUILD_STATISTIC_BOM:
                        buildSelectedStatisticBOM();
                        break;
                    case NEWVIEWVERSION:
                        assignViewSelectedObject();
                        break;                    //CCBegin by liunan 2008-07-30
                    //��������¼ӱ���ĵ��÷��������ݽ�Ű汾�޸ġ�
                    case BUILD_COMPARE_BOM:
                        buildSelectedCompareBOM();
                        break;
                    case BUILD_FIRSTLEVELSON_BOM:
                        buildSelectedFirstLevelSonBOM();
                        break;
                    case LOGIC:
                        bomLogic();
                        break;
                    //CCEnd by liunan 2008-07-30   
                      //CCBegin by leix	 2010-12-20  �����߼��ܳ��������� 
                    case NEWLOGIC:
                    	bomNewLogic();
                        break;
                      //CCEnd by leix	 2010-12-20  �����߼��ܳ��������� 
                    //CCBegin SS4
                    //CCBegin SS13
                    case FIRSTLEVEL:
                    	firstLevelList();
                        break;
                    //CCEnd SS13
                    case SUBCOMPBOM:
                    	subBom();
                    	break;
                   //CCEnd SS4
                   //CCBegin SS5
                    case ZCBOM:
                    	zcBom();
                    	break;
                    case DLZCBOM:
                    	dlzcBom();
                    	break;               
                   //CCEnd SS5
                    case ABOUT:
                        about();
                        break;
                    case TAILOR_BOM:
                        tailorBOM();
                        break;
                    //add by liun 2005.3.9
                    case GP_BOM_LIST:
                        gPBomList();
                        break;
                    case CREATE_EFFCONFIGITEM:
                        createEffConfigItem();
                        break;
                    case UPDATE_EFFECTIVITY:
                        updateSelectedEffectivity();
                        break;
                    case ADD_EFFECTIVITY:
                        addEffectivity();
                        break;
                    case ADD_EFFECTIVITY_BY_STRUCTURE:
                        populateEffectivity();
                        break;
                    case DEFINE_ALTERNATES:
                        defineAlternates();
                        break;
                    case DEFINE_SUBSTITUTES:
                        defineSubstitutes();
                        break;
                    case CLEAR_ALL:
                        clearAll();
                        break;
                    case COMPARE_STRUCTURE:
                        compareStructure();
                        break;
                    case IBA_COMPARE:
                        ibaCompare();
                        break;
                    case VIEW_EFFCONFIGITEM:
                        viewEffConfigItem();
                        break;
                    case REMOVE_EFFECTIVITY:
                        removeEffectivity();
                        break;
                    case MAINTENANCE_EFFCONFIGITEM:
                        maintenanceEffConfigItem();
                        break;
                    case CREATE_BASELINE:
                        createBaseline();
                        break;
                    case MAINTENANCE_BASELINE:
                        maintenanceBaseline();
                        break;
                    case ADD_BASELINE:
                        addBaseline();
                        break;
                    case POPULATE_BASELINE:
                        populateBaseline();
                        break;
                    case VIEW_BASELINE:
                        viewBaseline();
                        break;
                    case REMOVE_BASELINE:
                        removeBaseline();
                        break;
                    case ADDPART:
                        addPart(part);
                        break;
                    case REFRESHPART:
                        refreshPart(part);
                        break;
                    case DELETEPART:
                        deletePart(part);
                        break;
                    case VIEW_VERSION_HISTORY:
                        viewVersionHistory();
                        break;
                    case VIEW_ITERATION_HISTORY:
                        viewIterationHistory();
                        break;
                    //add by �ܴ��� 2003.10.21
                    case SORT:
                        sort();
                        break;
                    //add by л�� 2004.04.06
                    case PUBLISHIBA:
                        assignIBASelectedObject();
                        break;
                    case CATALOG_HISTORY:
                        viewCatalogHistory();
                        break;
                    //added by whj
                    case COPY:
                        copy();
                        break;
                    case PASTE:
                        paste();
                        break;
                    case CUT:
                        cut();
                        break;
                      case PUBLIC_SORT:
                        sort();
                        break;
                        //add by muyp 20080623 begin
                      case APPLY_TO_ALL_PARTS:
                    	  addAttrToOthersParts();
                    	  break;
                    	  //end
//                  	  CCBegin SS7
                      case DWSS:
                    	  DWSSPart();
                    	  break;
//                    	  CCEnd SS7
                }
            }
            catch (Throwable t)
            {
                t.printStackTrace();
            }
            finally
            {
                synchronized (cursorLock)
                {
                    if(0 == waitingOnCursor)
                    {
                        displayWaitIndicatorOff();
                    }
                }
                QMCt.setContextGroup(null);
            }
        } //end inner class

        /**
         * ��ȡ������
         * @return JFrame �����ڡ�
         */
        protected JFrame getParentFrame()
        {
            Component parent = getParent();
            if(null == parent)
            {
                return null;
            }
            while (!(parent instanceof JFrame))
            {
                parent = parent.getParent();
            }
            JFrame returnJFrame = (JFrame) parent;
            return returnJFrame;
        }
    }

    /**
     * ��õ�ǰ�������߳��顣
     * @return ThreadGroup �߳��顣
     */
    public ThreadGroup getThreadGroup()
    {
        ThreadGroup threadgroup = QMCt.getContextGroup();
        return threadgroup;
    }

    /**
     * �����Ƿ�ѡ���˸������������һ����㡣
     * @return boolean ���ѡ����Ǹ��ڵ㷵��true�����򷵻�false��
     */
    public boolean topLevelNodeSelected()
    {
        Explorable selectedDetail = myExplorer.getSelectedDetail();
        // ��������Ǵ��б���ѡ���,��һ�����Ǹ���㡣
        if(null != selectedDetail)
        {
            return false;
        }
        String rootNodeText = (myExplorer.getRootNode()).getLabel();
        String selectedNodeText = myExplorer.getSelectedNode().getLabel();
        if(selectedNodeText.equals(rootNodeText))
        {
            return true;
        }
        QMNode parentNode = (myExplorer.getSelectedNode()).getP();
        String parentNodeText = parentNode.getLabel();
        if(parentNodeText.equals(rootNodeText))
        {
            return true;
        }
        return false;
    }

    /**
     * ��õ�ǰѡ��Ķ���.��������б���ѡ���,�ͷ����б��ж���,�����ڷ��ش�����ѡ��Ķ���
     * ���û��ѡ����󷵻�null��
     * @return Object ��ǰѡ�еĶ���
     */
    public Object getSelectedObject()
    {
        Explorable busobj = null;
        QMNode node = null;
        busobj = myExplorer.getSelectedDetail();
        if(null == busobj)
        {
            node = myExplorer.getSelectedNode();
            if(null != node)
            {
                busobj = node.getObj();
            }
        }
        if(null == busobj)
        {
            return null;
        }
        else
        {
            Object returnObject = busobj.getObject();
            return returnObject;
        }
    }

    /**
     * ������б���ѡ�еĶ��󼯺�,����б���û�б�ѡ�еĶ���,�ͷ���ѡ������ṹ�ϵĶ���
     * @return Object[] ��ǰѡ�еĶ��󼯺ϡ�
     */
    public Object[] getSelectedObjects()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "getSelectedObjects()--- begin");
        Explorable[] busobjs = null;
        Object[] objs = null;
        Explorable busobj = null;
        QMNode[] node = null;
        busobjs = myExplorer.getSelectedDetails();
        if((null == busobjs) || (busobjs.length == 0))
        {
        	////////////muyp modify 080918
            node = myExplorer.getSelectedNodes();
            objs = new Object[node.length];
            if(null != node&&node.length!=0)
            {
            	for(int i=0;i<node.length;i++)
            	{
                    busobj = node[i].getObj();
                    if(null != busobj)
                    {
                        objs[i] = busobj.getObject();
                    }
            	}

            }//end
        }
        else
        {
            objs = new Object[busobjs.length];
            for (int i = 0, j = objs.length; i < j; i++)
            {
                objs[i] = busobjs[i].getObject();
            }
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "getSelectedObjects()--- end");
        return objs;
    }

    /**
     * �ڲ�Ʒ�������Ͻ��޸ĵ��㲿��ˢ�¡�
     * @param modifiedPart QMPartIfc ���޸ĵ��㲿����
     */
    public void refreshPart(QMPartIfc modifiedPart) {
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"------- ˢ�±��޸ĵ��㲿����refreshPart(part)");
		PartDebug.trace(PartDebug.PART_CLIENT, "------- �㲿�� ״̬Ϊ ��   "
				+ modifiedPart.getWorkableState());

		if (null == modifiedPart) {
			return;
		}

		QMNode currentNode = null;
		QMNode refreshNode = null;
		Explorable busobj = new PartItem(modifiedPart);
		((PartItem) busobj).setConfigSpecItem(getConfigSpecItem());
		QMNode[] matchingNodes = myExplorer.findNodes(busobj);
		for (int i = 0, j = matchingNodes.length; i < j; i++) {

			refreshNode = matchingNodes[i];
			currentNode = myExplorer.getSelectedNode();
			if ((null != currentNode) && (currentNode.equals(refreshNode))) {

				// ���ýڵ�ͼ�ꡣ
				myExplorer.refreshNode(refreshNode, busobj, true, true);
			} else {

				// ���ýڵ�ͼ�ꡣ
				myExplorer.refreshNode(refreshNode, busobj, false, true);
			}
			if (refreshNode.getLevel() > 1) {

				refreshCache(((QMNode) refreshNode.getParent()), busobj);
			}
		}

		// �޸�3��2007-11-14�������� begin��ˢ�±�ѡ������ڲ�Ʒ��Ϣ�������ϵĸ���
		if (!workablecopy.isEmpty()) 
		{
			for (Iterator ite = workablecopy.iterator(); ite.hasNext();) {
				WorkableIfc work1 = (WorkableIfc) ite.next();
				QMPartIfc part = (QMPartIfc) work1;

				if (!modifiedPart.getPartNumber().equals(part.getPartNumber()))
					continue;
				Explorable busobj1 = new PartItem(part);
				((PartItem) busobj1).setConfigSpecItem(getConfigSpecItem());
				QMNode[] copymatchingNodes = myExplorer.findNodes(busobj1);
				for (int i = 0, j = copymatchingNodes.length; i < j; i++) {

					refreshNode = copymatchingNodes[i];
					currentNode = myExplorer.getSelectedNode();
					if ((null != currentNode)
							&& (currentNode.equals(refreshNode))) {

						// ���ýڵ�ͼ�ꡣ
						myExplorer.refreshNode(refreshNode, busobj, true, true);
					} else {

						// ���ýڵ�ͼ�ꡣ
						myExplorer.refreshNode(refreshNode, busobj, true, true);
					}
					if (refreshNode.getLevel() > 1) {

						refreshCache(((QMNode) refreshNode.getP()), busobj,
								busobj1);
					}
				}
			}
		} // end
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"------- ˢ�±��޸ĵ��㲿��������refreshPart(part)------end()");
	}

    /**
     * ˢ�»��档
     * @param tempParentNode QMNode ���µ��㲿���ڵ㡣
     * @param newexp Explorable �ڵ�������㲿����
     */
    private void refreshCache(QMNode node, Explorable newexp)
    {
        if(null != newexp)
        {
            String oid = newexp.getUniqueIdentity();
            int length = node.totalDetails();
            for (int i = 0; i < length; i++)
            {
                Explorable oldexp = (Explorable) node.getDetail(i);
                if(oid.equals(oldexp.getUniqueIdentity()))
                {
                    if(oldexp instanceof UsageItem)
                    {
                        ((UsageItem) oldexp).setUsesPart((PartItem) newexp);
                    }
                    else
                    {
                        node.setDetail(i, newexp);
                    }
                }
            }
            oid = null;
        }
    }

    /**
     * ˢ�»��档
     * @param tempParentNode QMNode ���µ��㲿���ڵ㡣
     * @param newexp Explorable �ڵ�������㲿����
     * @param old Explorable 
     */
    //�޸�3��2007-11-14��������
    private void refreshCache(QMNode node, Explorable newexp, Explorable old)
    {
        if(null != newexp)
        {
            String oid = old.getUniqueIdentity();
            int length = node.totalDetails();
            for (int i = 0; i < length; i++)
            {
                Explorable oldexp = (Explorable) node.getDetail(i);
                if(oid.equals(oldexp.getUniqueIdentity()))
                {
                    if(oldexp instanceof UsageItem)
                    {
                        ((UsageItem) oldexp).setUsesPart((PartItem) newexp);
                    }
                    else
                    {
                        node.setDetail(i, newexp);
                    }
                }
            }
            oid = null;
        }
    }
    /**
     * �Ӳ�Ʒ������ҳ���Ͻ��㲿��ɾ����
     * @param deletedPart QMPartIfc ��Ҫɾ�����㲿��ֵ����
     */
    protected void deletePart(QMPartIfc deletedPart)
    {
        QMNode currentNode = null;
        try
        {
            Explorable busobj = new PartItem(deletedPart);
            
            QMNode[] matchingNodes = myExplorer.findNodes(busobj);
           
            for (int i = 0, j = matchingNodes.length; i < j; i++)
            {
            	
                if(CheckInOutTaskLogic.isWorkingCopy(deletedPart))
                {
                    boolean flag = matchingNodes[i].getParent().equals(
                            myExplorer.getRootNode());
                    if(flag)
                    {
                        myExplorer.removeNode(matchingNodes[i]);
                        //add by 0430  begin
                     myExplorer.getPartTaskJPanel().clear();
                     //add by 0430 end

                    }
                    else
                    {
                        //��ø��ӵ㡣
                        QMNode refreshNode = matchingNodes[i].getP();
                        //��ø��ӵ����
                        busobj = refreshNode.getObj();
                        //��õ�ǰѡ��Ľӵ㡣
                        currentNode = myExplorer.getSelectedNode();
                        boolean flag1 = false;
                        if(null != currentNode)
                        {
                            //update by cdc 2004.01.17
                            flag1 = currentNode.equals(refreshNode);
                        }
                        if(flag1)
                        {
                            //���ýڵ�ͼ�ꡣ
                            if(busobj instanceof UsageItem)
                            {
                                ((UsageItem) busobj).setIcon();
                            }
                            else
                            {
                                ((PartItem) busobj).setIcon();
                            }
                            myExplorer.refreshNode(refreshNode, busobj, true,
                                    true);


                        } //end if (flag1)
                        else
                        {
                            if(busobj instanceof UsageItem)
                            {
                                ((UsageItem) busobj).setIcon();
                            }
                            else
                            {
                                ((PartItem) busobj).setIcon();
                            }
                            
                            myExplorer.refreshNode(refreshNode, busobj, false,
                                    true);
                        } //end if (flag1) else
                        //update by cdc 2004.01.17
                    } //end if (flag) else
                } //end if (CheckInOutTaskLogic.isWorkingCopy(deletedPart))
                else
                {
                    myExplorer.removeNode(matchingNodes[i]);
                    //add by 0430  begin
                    myExplorer.getPartTaskJPanel().clear();
                    //add by 0430 end

                } //end if (CheckInOutTaskLogic.isWorkingCopy(deletedPart)) else
            }
        }
        catch (RuntimeException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * <p>Title: ����ˢ�·��������2003-08-27��</p>
     * <p>Description: �ڲ��ࡣ</p>
     * <p>Copyright: Copyright (c) 2003</p>
     * <p>Company: һ������</p>
     * @author someone
     * @version 1.0
     */
    class MainRefreshListener implements RefreshListener
    {
        //ʵ�ֽӿ��еĳ��󷽷�fefreshObject()��
        public void refreshObject(RefreshEvent refreshevent)
        {
            Object obj = refreshevent.getTarget();
            int i = refreshevent.getAction();
            
            //CR4 Begin 20090616 zhangq �޸�ԭ�򣺼����㲿����ʾ��Ϣ����  
            PropertyChangeEvent[]  propertyChanges=refreshevent.getPropertyChanges();
            if(propertyChanges!=null&&propertyChanges.length>0){
				Vector vec = new Vector(2);
				vec.add(propertyChanges[0].getNewValue());
				vec.add(propertyChanges[0].getOldValue());
				refreshPart(vec);
            }
            else if(obj instanceof QMPartIfc)
            {
                switch (i)
                {
                    case RefreshEvent.CREATE:
                    {
                        addQMExplorerPart((QMPartIfc) obj, true);
                        obj = null;
                        return;
                    }
                    case RefreshEvent.UPDATE:
                    {
                        refreshExplorerPart((QMPartIfc) obj);
                        obj = null;
                        return;
                    }
                    case RefreshEvent.DELETE:
                    {
                        deleteExplorerPart((QMPartIfc) obj);
                        obj = null;
                        return;
                    }
                }
            }
//            if(obj instanceof Vector)
//            {
//                switch (i)
//                {
////                    case RefreshEvent.CREATE:
////                    {
////                        addQMExplorerPart((QMPartIfc) obj, true);
////                        obj = null;
////                        return;
////                    }
//                    case RefreshEvent.UPDATE:
//                    {
//                        refreshExplorerPart((Vector) obj);
//                        obj = null;
//                        return;
//                    }
////                    case RefreshEvent.DELETE:
////                    {
////                        deleteExplorerPart((QMPartIfc) obj);
////                        obj = null;
////                        return;
////                    }
//                }
//            }
            //CR4 End 20090616 zhangq
            obj = null;
        }

        /**
         * ���캯����
         */
        MainRefreshListener()
        {
        }
    }

    /**
     * �����㲿�����ڹ���������ʾ��������ڸ��ڵ��¡�
     * �߳���������÷���addPart(QMPartIfc aPart)��
     * @param aPart QMPartIfc ��Ҫ��ӵ��㲿��ֵ����
     */
    public void addExplorerPart(QMPartIfc aPart)
    {
        addPart(aPart);
    }

    /**
     * �����㲿�����ڹ���������ʾ��������ڸ��ڵ��¡�
     * �߳���������÷���addPart(QMPartIfc aPart)��
     * @param aPart QMPartIfc ��Ҫ��ӵ��㲿��ֵ����
     */
    public void addQMExplorerPart(QMPartIfc aPart, boolean flag)
    {
        addQMPart(aPart, flag);
    }

    /**
     * �����㲿�����ڹ���������ʾ��������ڸ��ڵ��¡�
     * @param aPart QMPartIfc �������㲿��ֵ����
     */
    protected void addQMPart(QMPartIfc aPart, boolean flag)
    {
        //modify by shf 2003/09/13
        synchronized (lock)
        {
            PartDebug.trace(this, PartDebug.PART_CLIENT,
                    "QMProductManager.addPart()..begin ....");
            boolean okToAdd = true;
            //����PartItem����
            PartItem pi = new PartItem(aPart);
            //��õ�ǰ���ù淶��
            ConfigSpecItem specItem = getConfigSpecItem();
            if(null == specItem)
            {
                setConfigSpecCommand(false);
            }
            if(null != specItem)
            {
                //�ѵ�ǰ���ù淶����partItem�С�
                pi.setConfigSpecItem(specItem);
                // ��ø��ڵ�:�����
                QMNode rootNode = myExplorer.getRootNode();
                QMNode[] matchingNodes = myExplorer.findNodes(pi);
                for (int i = 0, j = matchingNodes.length; i < j; i++)
                {
                    //����ڵ��Ѿ��ڸ��ڵ��£���ʾ��Ϣ���㲿���Ѿ��ڹ���������ʾ������
                    if(rootNode.equals(((QMNode) matchingNodes[i].getParent())))
                    {
                        if(!flag)
                        {
                            Object[] params = {aPart.getPartNumber()
                                    + aPart.getPartName()};
                            String message = QMMessage.getLocalizedMessage(
                                    RESOURCE,
                                    QMProductManagerRB.PART_ALREADY_DISPLAYED,
                                    params);
                            showMessage(message, exceptionTitle);
                        }
                        okToAdd = false;
                        break;
                    }
                }
                if(okToAdd)
                {
                    myExplorer.addNode(rootNode, pi);
                }
            }
            PartDebug.trace(this, PartDebug.PART_CLIENT, "addPart()..end ....");
        }
    }

    /**
     * �����㲿�����ڹ���������ʾ��������ڸ��ڵ��¡�
     * @param aPart QMPartIfc �������㲿��ֵ����
     */
    protected void addPart(QMPartIfc aPart)
    {
        //modify by shf 2003/09/13
        synchronized (lock)
        {
            PartDebug.trace(this, PartDebug.PART_CLIENT,
                    "QMProductManager.addPart()..begin ....");
            boolean okToAdd = true;
            //����PartItem����
            PartItem pi = new PartItem(aPart);
            //��õ�ǰ���ù淶��
            ConfigSpecItem specItem = getConfigSpecItem();
            if(null == specItem)
            {
                setConfigSpecCommand(false);
            }
            if(null != specItem)
            {
                //�ѵ�ǰ���ù淶����partItem�С�
                pi.setConfigSpecItem(specItem);
                // ��ø��ڵ�:�����
                QMNode rootNode = myExplorer.getRootNode();
                QMNode[] matchingNodes = myExplorer.findNodes(pi);
                for (int i = 0, j = matchingNodes.length; i < j; i++)
                {
                    //����ڵ��Ѿ��ڸ��ڵ��£���ʾ��Ϣ���㲿���Ѿ��ڹ���������ʾ������
                    if(rootNode.equals(((QMNode) matchingNodes[i].getParent())))
                    {
                        Object[] params = {aPart.getPartNumber()
                                + aPart.getPartName()};
                        String message = QMMessage.getLocalizedMessage(
                                RESOURCE,
                                QMProductManagerRB.PART_ALREADY_DISPLAYED,
                                params);
                        showMessage(message, exceptionTitle);
                        okToAdd = false;
                        message = null;
                        break;
                    }
                }
                if(okToAdd)
                {
                    myExplorer.addNode(rootNode, pi);
                }
            }
            PartDebug.trace(this, PartDebug.PART_CLIENT, "addPart()..end ....");
        }
    }

    /**
     * ���ݵ�ǰ�����ù淶��part��
     * @param aPart QMPartMasterIfc
     */
    protected void addPartMaster(QMPartMasterIfc aPart)
    {
        if(getConfigSpecItem().equals(null))
        {
            setConfigSpecCommand(true);
        }
        if(null != getConfigSpecItem())
        {
            try
            {
                //���PART���ϡ�
                Vector vector = PartHelper.getAllVersions(aPart,
                        getConfigSpecItem().getConfigSpecInfo());
                if((null == vector) || (vector.size() == 0))
                {
                    Object[] params = {aPart.getPartNumber()};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            QMProductManagerRB.NO_QUALIFIED_VERSION, params);
                    showMessage(message, exceptionTitle);
                    message = null;
                    return;
                }
                int vecSize = vector.size();
                Object[] obj = null;
                ArrayList parts = new ArrayList();
                Object tempObject = null;
                for (int m = 0; m < vecSize; m++)
                {
                    tempObject = vector.elementAt(m);
                    if(tempObject instanceof Object[])
                    {
                        obj = (Object[]) tempObject;
                        //��obj�е�����Ԫ��(QMPartIfc����)����ѭ��:
                        for (int j = 0, jj = obj.length; j < jj; j++)
                        {
                            parts.add(obj[j]);
                        }
                    }
                    else if(tempObject instanceof Object)
                    {
                        parts.add(tempObject);
                    }
                }
                //������λ��
                tempObject = null;
                obj = null;
                if(0 == parts.size())
                {
                    Object[] param = {aPart.getPartNumber()};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            QMProductManagerRB.NO_QUALIFIED_VERSION, param);
                    showMessage(message, exceptionTitle);
                    message = null;
                }
                for (int i = 0, ii = parts.size(); i < ii; i++)
                {
                    addQMExplorerPart((QMPartIfc) parts.get(i), false);
                }
            }
            catch (QMRemoteException e)
            {
                showMessage(e.getMessage(), exceptionTitle);
                e.printStackTrace();
            }
        }
    }

    /**
     * �������ù淶��ť�Ŀɲ����ԡ�
     * @param flag boolean ���ù淶�����÷���true�����򷵻�false��
     */
    protected void setConfigMenu(boolean flag)
    {
        synchronized (lock)
        {
            frame.setConfigSpec.setEnabled(flag);
        }
    }

    /**
     * ������ӷ������ù淶���㲿����
     * @param partMasters QMPartMasterIfc[] ��ӵ��㲿����
     */
    //2003.09.16  synchronized����
    protected void addPartMasters(QMPartMasterIfc[] partMasters)
    {
    	 addPartMasters(partMasters,true);
    }
    
    /**
     * ������ӷ������ù淶���㲿����
     * @param partMasters QMPartMasterIfc[] ��ӵ��㲿����
     * @param isLatest boolean �Ƿ�ֻ��ʾ���°汾��
     */
    //2003.09.16  synchronized����
    protected void addPartMasters(QMPartMasterIfc[] partMasters,boolean isLatest)
    {
        synchronized (lock2)
        {
            //������ù淶Ϊ�ա�
            if(null == getConfigSpecItem())
            {
                setConfigSpecCommand(true);
            }
            //������ù淶��Ϊ�ա�
            else
            {
                displayWaitIndicatorOn();
                ProgressService.setProgressText(QMMessage.getLocalizedMessage(
                        RESOURCE, "working", null));
                ProgressService.showProgress();
                Hashtable table = null;
                Hashtable table2 = null;
                try
                {
                    //���PART���ϡ�
                    table = PartHelper.getAllVersionsNow(partMasters,
                            getConfigSpecItem().getConfigSpecInfo(),isLatest);
                    if(!isLatest){
                        table2=PartHelper.getAllVersionsNow(partMasters,
                                getConfigSpecItem().getConfigSpecInfo(),true);
                    }
                }
                catch (QMRemoteException e)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "warn", null);
                    JOptionPane.showMessageDialog(frame,
                            e.getLocalizedMessage(), title,
                            JOptionPane.WARNING_MESSAGE);
                    e.printStackTrace();
                    title = null;
                    return;
                }
                if(null != table)
                {
                    //���part���ϡ�
                    ArrayList part = (ArrayList) table.get("part");
                    //���master���ϡ�
                    ArrayList partMaster = (ArrayList) table.get("partmaster");
                    //�Ѿ���ʾ���㲿���ļ��ϣ�ֵ���㲿����BsoId����ֹ�ظ������ͬ���㲿����
                    List showPartList = new ArrayList();
                    //�����ϵ�PartMaster�ļ��ϣ�ֵ���㲿��������Ϣֵ����
                    List partMasterList = new ArrayList();
                    QMPartIfc partIfc = null;
                    //��ÿһ��partִ��addExplorerPart()��ӵ����ϡ�
                    //��isLatest==true������£�������е��㲿����
                    //��isLatest!=true������£�������ǰ��ʾ���㲿����
                    //�����ǰ��ʾ�İ汾�������ù淶������ʾ�ð汾�����°�����㲿����
                    //�����ǰ��ʾ�İ汾���������ù淶������ʾ���㲿���������ù淶�����°汾��
                    for (int i = 0, j = part.size(); i < j; i++)
                    {
                        partIfc = (QMPartIfc) part.get(i);
                        boolean isAddFlag = false;
                        if(isLatest)
                        {
                            isAddFlag = true;
                        }
                        else if(partMap != null
                                && partMap.get(partIfc.getMasterBsoID()
                                        + partIfc.getVersionID()) != null)
                        {
                            isAddFlag = true;
                            partMap.remove(partIfc.getMasterBsoID()
                                    + partIfc.getVersionID());
                        }
                        if(isAddFlag)
                        {
                            addQMExplorerPart(partIfc, false);
                            showPartList.add(partIfc.getBsoID());
                        }
                    }
   
                    //������ǰ��ʾ���㲿���������ǰ��ʾ�İ汾���������ù淶�������һ���������ù淶�����°汾��
                    if(!isLatest)
                    {
                        //key���㲿������Ϣ��BsoID��value�Ƿ������ù淶�����°汾���㲿����
                        Hashtable latestParts = new Hashtable();
                        if(table2 != null)
                        {
                            //���part���ϡ�
                            ArrayList part2 = (ArrayList) table2.get("part");
                            for (int index = 0; index < part2.size(); index++)
                            {
                                partIfc = (QMPartIfc) part2.get(index);
                                latestParts.put(partIfc.getMasterBsoID(), partIfc);
                            }
                        }
                        Iterator partMapKeyIter = partMap.keySet().iterator();
                        QMPartMasterIfc partMasterIfc =null;
                        while (partMapKeyIter.hasNext())
                        {
                            partMasterIfc = (QMPartMasterIfc) partMap
                                    .get((String) partMapKeyIter.next());
                            partIfc = (QMPartIfc) latestParts.get(partMasterIfc
                                    .getBsoID());
                            if(partIfc != null
                                    && !showPartList.contains(partIfc.getBsoID()))
                            {
                                addQMExplorerPart(partIfc, false);
                                showPartList.add(partIfc.getBsoID());
                            }
                            else
                            {
                                partMasterList.add(partMasterIfc);
                            }
                        }
                    }
                    else{
                        for (int i = 0; i < partMaster.size(); i++)
                        {
                            partMasterList.add(partMaster.get(i));
                        }
                    }
                    setConfigMenu(true);
                    //����в����ϵ�PartMaster��
                    if(0 != partMasterList.size())
                    {
                        PartShowMasterDialog dialog = new PartShowMasterDialog(
                                partMasterList, frame);
                        dialog.setSize(400, 300);
                        PartScreenParameter.centerWindow(dialog);
                    } //end if (0 != list.size())
                }
                ProgressService.hideProgress();
                displayWaitIndicatorOff();
            } //end if (null == getConfigSpecItem()) else
        }
    }

    /**
     * �������ù淶��
     * @param a_configSpecItem ConfigSpecItem ��ǰ���ù淶��
     */
    public void setConfigSpecItem(ConfigSpecItem a_configSpecItem)
    {
        this.configSpecItem = a_configSpecItem;
    }

    /**
     * ������ù淶,���Ϊ�վͳ��Ի���
     * @return ConfigSpecItem ��ǰ���ù淶��
     */
    public ConfigSpecItem getConfigSpecItem()
    {
        if(null == configSpecItem)
        {
            initialize();
        }
        return configSpecItem;
    }

    /**
     * ��ʼ����Ʒ�ṹ�����������û���ǰ�����ù淶
     * ����û���ǰ�����ù淶Ϊ��������Ĭ��ֵ����׼
     * ��ͼΪ�գ���������Ϊ�գ������������ϼС�
     */
    protected void initialize()
    {
        try
        {
            PartConfigSpecIfc configSpecIfc = (PartConfigSpecIfc) PartServiceRequest
                    .getCurrentConfigSpec();
            configSpecItem = new ConfigSpecItem(configSpecIfc);
        }
        catch (QMRemoteException qmRemoteException)
        {
            showMessage(qmRemoteException.getClientMessage(), exceptionTitle);
            qmRemoteException.printStackTrace();
        }
        catch (QMException qmException)
        {
            showMessage(qmException.getClientMessage(), exceptionTitle);
            qmException.printStackTrace();
        }
    }

    /**
     * ���õ�ǰ�����ù淶��
     * @param refresh boolean = true :�������õ�ɸѡ����ˢ�¹�����ҳ���ڵ��㲿����
     *                        =false :��ˢ�¹�����ҳ���ڵ��㲿����
     */
    protected void setConfigSpecCommand(boolean refresh)
    {
        ConfigSpecItem configSpecItem = null;
        try
        {
            //��ȡ�㲿�����ù淶��
            PartConfigSpecIfc partConfigSpecIfc = PartHelper.getConfigSpec();
            if(null != partConfigSpecIfc)
            {
                configSpecItem = new ConfigSpecItem(
                        (PartConfigSpecIfc) partConfigSpecIfc);
            }
            else
            {
                configSpecItem = new ConfigSpecItem();
            }
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
        }
        //��ʾ���༭���ù淶��ҳ�沢�����ݿ��д��ڵ�ɸѡ����������ҳ�档
        new StructureConditionController(configSpecItem, this);
    }

    /**
     * ��ø�����
     * @return JFrame �����ڡ�
     */
    protected JFrame getParentFrame()
    {
        Component parent = getParent();
        if(null == parent)
        {
            return null;
        }
        while (!(parent instanceof JFrame))
        {
            parent = parent.getParent();
        }
        JFrame returnJFrame = (JFrame) parent;
        return returnJFrame;
    }

    /**
     * ����"����"���
     */
    public void processNewCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE);
        work.start();
    }

    /**
     * ������
     */
    protected void newObject()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT, "newObject()..begin ....");
        TaskDelegate delegate = TaskDelegateFactory
                .instantiateTaskDelegate(QMPartInfo.class);
        if(null != delegate)
        {
            try
            {
                delegate.launchCreateTask();
            }
            catch (TaskDelegateException e)
            {
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.CREATE_TASKDELEGATE_FAILED, null);
                showMessage(message, exceptionTitle);
                message = null;
                e.printStackTrace();
            }
        }
        else
        {
            String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                    QMProductManagerRB.CREATE_TASKDELEGATE_FAILED, null);
            showMessage(message1, exceptionTitle);
            message1 = null;
        }
        displayWaitIndicatorOff();
        PartDebug.trace(this, PartDebug.PART_CLIENT, "newObject()..end ....");
    }

    /**
     * ����"����"���
     */
    public void processEditCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), UPDATE);
        work.start();
    }

    /**
     * ���¡�
     */
    protected void editSelectedObject()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "editSelectedObject()..begin ....");
        try
        {
            BaseValueIfc selectObject = (BaseValueIfc) getSelectedObject();
            if(null == selectObject)
            {
                showMessage(noSelectObj, exceptionTitle);
                return;
            }
            else
            {
                //2003/12/16
                if(selectObject instanceof QMPartMasterIfc)
                {
                    showMessage(errorObject, exceptionTitle);
                    return;
                }
                if(selectObject instanceof WorkableIfc)
                {
                    //if 11:����������û���������㲿��,��ʾ"�����Ѿ������˼��!"��
                    if(CheckInOutTaskLogic
                            .isCheckedOutByOther((WorkableIfc) selectObject))
                    {
                        //if 1
                        if(selectObject instanceof QMPartIfc)
                        {
                            QMPartIfc part = (QMPartIfc) selectObject;
                            String username = "";
                            UserIfc qmprincipal = CheckInOutTaskLogic
                                    .getCheckedOutBy(part);
                            if(qmprincipal != null)
                            {
                                username = qmprincipal.getUsersDesc();
                            }
                            if(username.trim().length()==0)
                            {
                            	/*�ж϶����Ƿ��ǹ���������*/
                                boolean flag1 = WorkInProgressHelper
                                        .isWorkingCopy((WorkableIfc)part);
                                if(flag1)
                                {
                                	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
                                }
                            }
                            Object[] objs = {username};
                            String message = QMMessage
                                    .getLocalizedMessage(
                                            RESOURCE,
                                            QMProductManagerRB.ALREADY_CHECKOUT_BY_OTHER,
                                            objs);
                            showMessage(message, exceptionTitle);
                            message = null;
                            return;
                        }
                    } //end if 12:�������ǰ�û������
                    else if(CheckInOutTaskLogic
                            .isCheckedOutByUser((WorkableIfc) selectObject))
                    {
                        //if 2:����Ǳ���ǰ�û���������ǹ�������,�ͻ�ù���������
                        if(!CheckInOutTaskLogic
                                .isWorkingCopy((WorkableIfc) selectObject))
                        {
                            selectObject = CheckInOutTaskLogic
                                    .getWorkingCopy((WorkableIfc) selectObject);
                        }
                    }
                    //if 3:����������޸�,��ʾ"���� * ��Ҫ��������޸�,����Ҫ�����"��
                    else if(!helper.isUpdateAllowed((FolderedIfc) selectObject))
                    {
                        QMPartIfc part = (QMPartIfc) selectObject;
                        Object[] objs = {part.getIdentity()};
                        String message = QMMessage.getLocalizedMessage(
                                RESOURCE,
                                QMProductManagerRB.CONFIRM_TO_CHECKOUT, objs);
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                QMProductManagerRB.CHECKOUT_TITLE, null);
                        //��ʾȷ�϶Ի��򲢻��ѡ��ķ���ֵ��
                        int i = JOptionPane.showConfirmDialog(frame, message,
                                title, JOptionPane.YES_NO_OPTION);
                        //if 4
                        if(JOptionPane.OK_OPTION == i)
                        {
                            //�������ϼ��е��㲿������������
                            //��ʾ"��ǰ�㲿���ڸ������ϼ��У����ܼ��!"��
                            if(!CheckInOutTaskLogic
                                    .isInVault((WorkableIfc) selectObject))
                            {
                                String message1 = QMMessage
                                        .getLocalizedMessage(
                                                RESOURCE,
                                                QMProductManagerRB.CANNOT_CHECKOUT_USERFOLDER,
                                                null);
                                showMessage(message1, exceptionTitle);
                                return;
                            }
                            selectObject = getCheckOutObject((WorkableIfc) selectObject);
                            //2003/12/15
                            if(null != selectObject)
                            {
                                if(!CheckInOutTaskLogic
                                        .isWorkingCopy((WorkableIfc) selectObject))
                                {
                                    selectObject = CheckInOutTaskLogic
                                            .getWorkingCopy((WorkableIfc) selectObject);
                                }
                                else
                                {
                                    return;
                                }
                            }
                        } //end if 4
                        else
                        {
                            return;
                        }
                    } //end if 3
                }
                //2003/12/15 �� if�ж�
                if(null != selectObject)
                {
                    TaskDelegate delegate = TaskDelegateFactory
                            .instantiateTaskDelegate(selectObject);
                    //2003/12/15
                    if(null != delegate)
                    {
                        delegate.setParentJFrame(getParentFrame());
                        delegate.setObject(selectObject);
                        try
                        {
                            delegate.launchUpdateTask();
                        }
                        catch (TaskDelegateException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Object[] objs = {selectObject.getBsoName()};
                        String message = QMMessage.getLocalizedMessage(
                                RESOURCE, QMProductManagerRB.UPDATE_FAILED,
                                objs);
                        showMessage(message, exceptionTitle);
                        message = null;
                    }
                }
            }
        }
        catch (QMRemoteException e)
        {
            showMessage(e.getMessage(), exceptionTitle);
            e.printStackTrace();
        }
        catch (QMException e)
        {
            showMessage(e.getMessage(), exceptionTitle);
            e.printStackTrace();
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "editSelectedObject()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * ����"���Ϊ"���
     */
    public void processSaveAsCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SAVEAS);
        work.start();
    }

    /**
     * ���Ϊ��
     */
    public void saveAsPart()
    {
        Object object = getSelectedObject();
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "----------- selected object is :" + object);
        if(null == object)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        QMPartIfc part = null;
        if(object instanceof QMPartIfc)
        {
            part = (QMPartIfc) object;
            PartDebug.trace(PartDebug.PART_CLIENT, "---- object is:   " + part);
            //add by skx 2004.5.11
            try
            {
                TaskDelegate taskdelegate = new QMPartInfoTaskDelegate();
                taskdelegate.setObject(part);
                taskdelegate.setParentJFrame(frame);
                taskdelegate.launchSaveAsTask();
            }
            catch (TaskDelegateException e)
            {
                e.printStackTrace();
            }
        }
    }
    
//    CCBegin SS7
    public void processDWSSCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), DWSS);
        work.start();
    }

    public void DWSSPart()
    {
    	new DWSSJDialog(this,frame);
    }
//    CCEnd SS7

    /**
     * ����"������"���
     */
    public void processRenameCommand()
    {
        changeIdentity();
    }

    /**
     * ��������
     */
    protected void changeIdentity()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "changeIdentity()..begin ....");
        Object object = getSelectedObject();
        PartDebug.trace(PartDebug.PART_CLIENT,
                "----------- selected object is :" + object);
        if(null == object)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        else
        {
            if(object instanceof QMPartIfc)
            {
                QMPartIfc part = (QMPartIfc) object;
                //check rename permission
                Class[] paraClass2 = {Object.class, String.class};
                Object[] objs2 = {(Object) part, QMPermission.MODIFY};
                //Collection result = null;
                try
                {
                    IBAUtility.invokeServiceMethod("AccessControlService",
                            "checkAccess", paraClass2, objs2);
                }
                catch (QMRemoteException ex)
                {
                    //flag=false;
                    String RESOURCES = "com.faw_qm.part.client.design.util.PartDesignViewRB";
                    String title = QMMessage.getLocalizedMessage(RESOURCES,
                            "exception", null);
                    JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                            title, JOptionPane.INFORMATION_MESSAGE);
                    ex.printStackTrace();
                    return;
                }
                //end check
                QMPartMasterIfc master = (QMPartMasterIfc) part.getMaster();
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.RENAME_TITLE, null);
                try
                {
                	//CCBegin by liunan 2012-04-25 �򲹶�v4r3_p044
                    //У����岿���Ƿ��ѱ������û������
                    /*part = (QMPartIfc) CheckInOutTaskLogic.refresh(part);



                    if(CheckInOutTaskLogic.isCheckedOutByOther(part))
                    {

                        String username = "";
                        UserIfc qmprincipal = CheckInOutTaskLogic
                                .getCheckedOutBy(part);
                        if(qmprincipal != null)
                        {
                            username = qmprincipal.getUsersDesc();
                        }
                        if(username.trim().length()==0)
                        {
                        	//�ж϶����Ƿ��ǹ���������
                            boolean flag = WorkInProgressHelper
                                    .isWorkingCopy((WorkableIfc) part);
                            if(flag)
                            {
                            	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
                            }
                        }
                        Object[] aobj = {username};
                        String message = QMMessage
                                .getLocalizedMessage(
                                        RESOURCE,
                                        QMProductManagerRB.ALREADY_CYHECKOUT_OTHER_NOTRENAME,
                                        aobj);
                        DialogFactory.showWarningDialog(this, message);
                    }
                    else
                    {
                        PartDesignRenameJDialog dialog = new PartDesignRenameJDialog(
                                getParentFrame(), title, true);
                        dialog.setIdentifiedObject((QMPartMasterIfc) master);
                        PartScreenParameter.centerWindow(dialog);
                        //������λ��
                        part = null;
                        master = null;
                        title = null;
                    }*/
                    //CR12 begin
                    Class[] paraClass = {MasteredIfc.class};
                    // ����ֵ�ļ��ϡ�
                    Object[] objs = {master};
                    // �жϵ�ǰ�û��Ƿ�����������Ȩ��
                    Boolean flag1 = (Boolean)IBAUtility.invokeServiceMethod("EnterpriseService", "hasRenameAccess", paraClass, objs);
                    if(flag1.booleanValue())
                    {
                        /* У����岿���Ƿ��ѱ������û������ */
                        part = (QMPartIfc)CheckInOutTaskLogic.refresh(part);
                        // ��ȡ��ǰ�������㲿������ͼ
                        String partViewName = part.getViewName();
                        // ���㲿���ĵ�һ���汾
                        QMPartIfc partfirst = new QMPartInfo();
                        // ͨ��һ���㲿������Ϣ�ҵ����㲿�����а汾ֵ����ļ��ϡ�
                        Collection collection = PartServiceRequest.findPart(master);
                        Iterator iter = collection.iterator();
                        // ��ȡ���㲿���ĵ�һ���汾
                        while(iter.hasNext())
                        {
                            IteratedIfc iterIfc = (IteratedIfc)iter.next();
                            if(iterIfc instanceof QMPartIfc)
                            {
                                partfirst = (QMPartIfc)iterIfc;
                            }
                        }
                        // ������������㲿����ͼ����㲿����һ���汾����ͼ��ͬ�����������������򲻿���
                        if(partViewName.equals(partfirst.getViewName()))
                        {
                            if(CheckInOutTaskLogic.isCheckedOutByOther(part))
                            {
                                String username = "";
                                UserIfc qmprincipal = CheckInOutTaskLogic.getCheckedOutBy(part);
                                if(qmprincipal != null)
                                {
                                    username = qmprincipal.getUsersDesc();
                                }
                                if(username.trim().length() == 0)
                                {
                                    /* �ж϶����Ƿ��ǹ��������� */
                                    boolean flag = WorkInProgressHelper.isWorkingCopy((WorkableIfc)part);
                                    if(flag)
                                    {
                                        username = CheckInOutTaskLogic.getCheckedOutBy(part, java.util.Locale.SIMPLIFIED_CHINESE);
                                    }
                                }
                                Object[] aobj = {username};
                                String message = QMMessage.getLocalizedMessage(RESOURCE, QMProductManagerRB.ALREADY_CYHECKOUT_OTHER_NOTRENAME, aobj);
                                DialogFactory.showWarningDialog(this, message);
                            }else
                            {
                                PartDesignRenameJDialog dialog = new PartDesignRenameJDialog(getParentFrame(), title, true);
                                dialog.setIdentifiedObject((QMPartMasterIfc)master);
                                PartScreenParameter.centerWindow(dialog);
                                // ������λ��
                                part = null;
                                master = null;
                                title = null;
                            }
                        }else
                        {
                            String message = "�����������㲿����ͼ����㲿����һ���汾����ͼ����ͬ���㲻�ܽ�����������";
                            showMessage(message, exceptionTitle);
                            message = null;
                        }

                    }else
                    {
                        String message = "����Ȩ�޽����㲿����������";
                        showMessage(message, exceptionTitle);
                        message = null;
                    }//CR12 End
                    //CCEnd by liunan 2012-04-25
                }
                catch (QMException e)
                {
                    e.printStackTrace();
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            QMProductManagerRB.RENAME_ERROR, null);
                    showMessage(message, exceptionTitle);
                    message = null;
                }
            }
        } //end if (null == object) else
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "changeIdentity()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * ����"ɾ��"���
     */
    public void processDeletePartCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), DELETE);
        work.start();
    }

    /**
     * ɾ����
     */
    public void deleteSelectedObject()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "deleteSelectedObject()..begin ....");
        Object obj = getSelectedObject();
        PartDebug.trace(PartDebug.PART_CLIENT,
                "----------- selected object is :" + obj);
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        //2003/12/16
        if(obj instanceof QMPartMasterIfc)
        {
            showMessage(errorObject, exceptionTitle);
            return;
        }
        QMPartIfc part = null;
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(errorObject, exceptionTitle);
            return;
        }
        try
        {
            part = (QMPartIfc) CheckInOutTaskLogic.refresh(part);
            if((null != part) && (isDeleteAllowed(part)))
            {
                Object[] param = {part.getPartName()};
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.DELETE_OBJECT, null);
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.CONFIRM_DELETE_OBJECT, param);
                //��ʾȷ�϶Ի��򲢻��ѡ��ķ���ֵ��
                int result = JOptionPane.showConfirmDialog(this, message,
                        title, JOptionPane.WARNING_MESSAGE);
                title = null;
                message = null;
                QMPartIfc original = null;
                switch (result)
                {
                    case JOptionPane.CLOSED_OPTION:
                        return;
                    case JOptionPane.CANCEL_OPTION:
                        return;
                    case JOptionPane.NO_OPTION:
                        return;
                    case JOptionPane.YES_OPTION:
                    {
                        //���ѡ�����Ϊ�������������ԭ����
                        if(CheckInOutTaskLogic
                                .isWorkingCopy((WorkableIfc) part))
                        {
                            original = (QMPartIfc) CheckInOutTaskLogic
                                    .getOriginalCopy((WorkableIfc) part);
                            //ˢ�²�ɾ��part��ͬʱˢ�¹��������档
                        }
                        part = PartHelper.refresh(part);
                        boolean flag = PartServiceRequest.isAlterOrSub(part);
                        if(flag)
                        {
                            showMessage(alterOrSub, exceptionTitle);
                            return;
                        }
                        PartHelper.deletePart(part);
                        RefreshService.getRefreshService().dispatchRefresh(
                                getParentFrame(), RefreshEvent.DELETE, part);
                        //���ԭ����Ϊ�գ�ˢ��ԭ���͹��������档
                        if(null != original)
                        {
                            PartDebug.trace(PartDebug.PART_CLIENT,
                                    "deleteSelectedObject()---original is:  "
                                            + original);
                            original = PartHelper.refresh(original);
                            RefreshService.getRefreshService().dispatchRefresh(
                                    getParentFrame(), RefreshEvent.UPDATE,
                                    original);
                        }
                    }
                }
            }
        }
        catch (QMRemoteException e)
        {
            showMessage(e.getMessage(), exceptionTitle);
            e.printStackTrace();
        }
        catch (QMException e)
        {
            e.printStackTrace();
        }
        displayWaitIndicatorOff();
    }

    /**
     * ����"���"���
     */
    public void processClearCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CLEAR);
        work.start();
    }

    /**
     * �����
     */
    public void clear()
    {
        ResourceBundle rb = getPropertiesRB();
        String temp = getValue(rb, "partexplorer.explorer.list.statusBarText");
        //2003/12/16
        Object obj = getSelectedObject();
        if(null != obj)
        {
            if(obj instanceof QMPartMasterIfc)
            {
                showMessage(errorObject, exceptionTitle);
                return;
            }
        }
        QMNode selected_node = myExplorer.getSelectedNode();
        //����1
        if((null != selected_node)
                && (selected_node != myExplorer.getRootNode()))
        //����1��ʼ
        {
            if(selected_node.getParent() == myExplorer.getRootNode())
            {
                myExplorer.checkChange(selected_node);
                myExplorer.removeNode(selected_node);
//                myExplorer.setClearAfterSign(true);
                //add by 0430 begin
                myExplorer.getPartTaskJPanel().clear();
                //add by 0430 end

            }
        } //����1����
        else
        {
            myExplorer.clear();
        } //����1���� else
        myExplorer.setListBar(temp);
        //������λ��
        temp = null;
        obj = null;
        selected_node = null;
        displayWaitIndicatorOff();
    }

    /**
     * ����"ˢ��"���
     */
    public void processRefreshCommand()
    {
        refreshSelectedObject();
    }

    /**
     * ˢ�¡�CR6 Begin
     */
    public void refreshSelectedObject()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT, "refreshSelectedObject()..begin ....");
        // ���ѡ��Ķ���(���ϵĻ����б��е�)��
        Object obj = getSelectedObject();

        // ���ѡ��Ķ���Ϊnull��
        if(null != obj)
        {
            // 2003/12/16
            if(obj instanceof QMPartMasterIfc)
            {
                showMessage(errorObject, exceptionTitle);
                return;
            }
            // ��ô�����ѡ��Ľڵ㡣
            // ���ѡ�����ΪQMPartIfc��
            if(obj instanceof QMPartIfc)
            {
                IteratedIfc BaseIfc = (IteratedIfc)obj;
                QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)BaseIfc.getMaster();
                PartConfigSpecIfc configSpecIfc = null;
                try
                {
                    configSpecIfc = (PartConfigSpecIfc)PartServiceRequest.getCurrentConfigSpec();
                }catch(QMException e1)
                {
                    e1.printStackTrace();
                }
                Class[] class2 = {QMPartMasterIfc.class, PartConfigSpecIfc.class};
                Object[] param2 = {partMasterIfc, configSpecIfc};
                try
                {

                    BaseIfc = (IteratedIfc)IBAUtility.invokeServiceMethod("StandardPartService", "getPartByConfigSpec", class2, param2);
                }catch(QMException e)
                {
                    showMessage(e.getClientMessage(), exceptionTitle);
                    this.clear();
                    e.printStackTrace();
                    return;
                }
                if(BaseIfc != null)
                {
                    BaseValueIfc baseIfc = (BaseValueIfc)BaseIfc;
                    Class[] class1 = {BaseValueIfc.class};
                    Object[] param = {(BaseValueIfc)baseIfc};
                    try
                    {
                        // ���ó־û�����ķ�����
                        baseIfc = (BaseValueIfc)IBAUtility.invokeServiceMethod("PersistService", "refreshInfo", class1, param);
                    }catch(QMException e)
                    {
                        showMessage(e.getClientMessage(), exceptionTitle);
                        this.clear();
                        e.printStackTrace();
                        return;
                    }
                    // ��part����һ��QMPartItem����
                    QMPartIfc part = (QMPartIfc)baseIfc;
                    // ת����PartItem��
                    Explorable newObj = new PartItem(part);
                    ((PartItem)newObj).setConfigSpecItem(getConfigSpecItem());
                    // ˢ��ѡ��Ľڵ㣬
                    // ���ýڵ�ͼ�ꡣ
                    ((PartItem)newObj).setIcon();
                    QMNode selectedNode = myExplorer.getSelectedNode();
                    myExplorer.getPartTaskJPanel().setReference(true);
                    if(myExplorer.checkChange(selectedNode))
                    {
                        QMPartIfc part1 = (QMPartIfc)myTree.getSelected().getObj().getObject();
                        // ת����PartItem��
                        Explorable newObj1 = new PartItem(part1);
                        ((PartItem)newObj1).setConfigSpecItem(getConfigSpecItem());
                        // ˢ��ѡ��Ľڵ㣬
                        // ���ýڵ�ͼ�ꡣ
                        ((PartItem)newObj1).setIcon();
                        QMNode selectedNode1 = myExplorer.getSelectedNode();
                        // myExplorer.refreshNode(selectedNode1, newObj1, true, false);
                        myExplorer.refreshNode(selectedNode1, newObj1, true, false);
                    }else
                    {
                        // myExplorer.refreshNode(selectedNode, newObj, true, false);
                        myExplorer.refreshNode(selectedNode, newObj, true, false);
                    }
                    // CR8 Begin  ����PartItem����
                    PartItem partItem = new PartItem(part);
                    // �õ������������Ϣ��ͬ�Ľڵ����������
                    QMNode[] matchingNodes = myExplorer.findNodes(partItem);
                    // ��ø��ڵ�:�����
                    QMNode rootNode = myExplorer.getRootNode();
                    // ������¼matchingNodes�����и��ڵ�Ϊ���ڵ����
                    int z = 0;
                    for(int i = 0, j = matchingNodes.length;i < j;i++)
                    {
                        if(rootNode.equals(((QMNode)matchingNodes[i].getParent())))
                        {
                            // matchingNodes������ÿ��һ�����ڵ�Ϊ���ڵ㣬z�ͼ�һ
                            z = ++z;
                        }
                    }
                    // ��������������Ӧ�ڵ㲢�Ҹ��ڵ�Ϊ���ڵ�Ľڵ��������һ��֤����������ͬ���㲿������
                    // ������ʾ��Ϣ��ɾ����ǰ����㲿��������������һ����ͬ���㲿���ϡ�
                   if(z > 1)
                    {
                        Object[] params = {part.getPartNumber() + part.getPartName()};
                        String message = QMMessage.getLocalizedMessage(RESOURCE, QMProductManagerRB.PART_ALREADY_DISPLAYED, params);
                        showMessage(message, exceptionTitle);
                        myExplorer.removeNode(selectedNode);
                        this.setSelectNode(partItem);
                    }
                    z = 0;
                    //Cr8 End
                    myExplorer.getPartTaskJPanel().setReference(false);
                }else
                {
                    QMNode selected_node = myExplorer.getSelectedNode();
                    myExplorer.checkChange(selected_node);
                    myExplorer.removeNode(selected_node);
                    myExplorer.getPartTaskJPanel().clear();
                    selected_node = null;
                }
                // ������λ��
                obj = null;
                // selectedNode = null;
            }
            PartDebug.trace(this, PartDebug.PART_CLIENT, "refreshSelectedObject()..end ....");
            displayWaitIndicatorOff();
        }

    }
//CR6 End
    /**
     * ����"����"���
     */
    public void processCheckInCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CHECKIN);
        work.start();
    }

//    /**
//     * ���롣
//     */
//    public void checkinSelectedObject()
//    {
//        PartDebug.trace(this, PartDebug.PART_CLIENT, "�������..��ʼ ....");
//        try
//        {
//            //���ѡ��Ķ���
//            Object selected_obj = getSelectedObject();
//            //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
//            if(null == selected_obj)
//            {
//                showMessage(noSelectObj, exceptionTitle);
//                return;
//            }
//            if(selected_obj instanceof QMPartMasterIfc)
//            {
//                showMessage(errorObject, exceptionTitle);
//                return;
//            }
//            if(selected_obj instanceof WorkableIfc)
//            {
//                try
//                {
//                    BaseValueIfc baseIfc = (BaseValueIfc) selected_obj;
//                    Class[] class1 = {BaseValueIfc.class};
//                    Object[] param = {(BaseValueIfc) baseIfc};
//                    //���ó־û�����ķ�����
//                    baseIfc = (BaseValueIfc) IBAUtility.invokeServiceMethod(
//                            "PersistService", "refreshInfo", class1, param);
//                    //���������������˼����
//                    if(CheckInOutTaskLogic
//                            .isCheckedOutByOther((WorkableIfc) baseIfc))
//                    {
//                        //if 1
//                        if(baseIfc instanceof QMPartIfc)
//                        {
//                            QMPartIfc part = (QMPartIfc) baseIfc;
//                            String username = "";
//                            UserIfc qmprincipal = CheckInOutTaskLogic
//                                    .getCheckedOutBy(part);
//                            if(qmprincipal != null)
//                            {
//                                username = qmprincipal.getUsersDesc();
//                            }
//                            else
//                            {
//                                username = part.getLocker();
//                            }
//                            if(username==null||username.trim().length()==0)
//                            {
//                            	/*�ж϶����Ƿ��ǹ���������*/
//                                boolean flag = WorkInProgressHelper
//                                        .isWorkingCopy((WorkableIfc) part);
//                                if(flag)
//                                {
//                                	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
//                                }
//                            }
//                            Object[] objs = {username};
//                            String message = QMMessage
//                                    .getLocalizedMessage(
//                                            RESOURCE,
//                                            QMProductManagerRB.ALREADY_CHECKOUT_BY_OTHER_NOTCHECKIN,
//                                            objs);
//                            showMessage(message, exceptionTitle);
//                            message = null;
//                            part = null;
//                            objs = null;
//                            return;
//                        }
//                    }
//                    else
//                    {
//                    	//2007-11-07,add by muyp begin,��ñ�ѡ�ж���ĸ���
//                    	WorkableIfc workableIfc=(WorkableIfc)selected_obj;
//                    	WorkableIfc workableIfc1=null;
//                    	if(CheckInOutTaskLogic.isWorkingCopy(workableIfc))
//                    	{
//                    		this.setWorkableCopy(workableIfc);
//                    		workableIfc1=CheckInOutTaskLogic.getOriginalCopy(workableIfc);
//                    	}
//                    	else 
//                    	{
//                    		workableIfc1=workableIfc;
//                    		this.setWorkableCopy(CheckInOutTaskLogic.getWorkingCopy(workableIfc));
//                    	}//end
//                    	
//                        myExplorer.setIsCheckin(false);
//                        boolean flag = myExplorer.checkChange(myExplorer
//                                .getSelectedNode());
//                        myExplorer.setTreeValueChanged(false);
//                        JFrame frame = getParentFrame();
//                        PartsCheckInTask checkin_task = new PartsCheckInTask(frame);
//                        //�޸�2��2007-11-07,add by muyp,begin���жϲ��������ԭ���Ƿ��Ѿ���ʾ���������,�����ñ�־
//                    	if(workableIfc1 instanceof QMPartIfc)
//                    	{
//                    		QMPartIfc part = (QMPartIfc) workableIfc1;
//                    		PartItem selected_obj1=new PartItem(part);
//                    		checkin_task.setIsInTree(isInTree(selected_obj1));
//                    	}//end
//                        //���ü���Ķ���
//                        if(flag)
//                        {
//                            checkin_task
//                                    .setCheckinItem((WorkableIfc) myExplorer
//                                            .getSelectedNode().getObj()
//                                            .getObject());
//                            flag = false;
//                        }
//                        else
//                            checkin_task.setCheckinItem((WorkableIfc) baseIfc);
//                        //���롣
//                        checkin_task.checkin();
//                        myExplorer.setIsCheckin(true);
//                    }
//                    //������λ��
//                    selected_obj = null;
//                    baseIfc = null;
//                }
//                //��ʾ:"��ǰ��δ��*�����"��
//                catch (NotCheckedOutException notcheckedoutexception)
//                {
//                    showMessage(notcheckedoutexception.getClientMessage(),
//                            exceptionTitle);
//                    notcheckedoutexception.printStackTrace();
//                }
//                catch (QMException qmexception)
//                {
//                    showMessage(qmexception.getClientMessage(), exceptionTitle);
//                    qmexception.printStackTrace();
//                }
//            }
//        }
//        finally
//        {
//            displayWaitIndicatorOff();
//        }
//        PartDebug.trace(this, PartDebug.PART_CLIENT,
//                "checkinSelectedObject()..���� ....");
//    }

    /**
     * ����"���"���
     */
    public void processCheckOutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CHECKOUT);
        work.start();
    }

//    /**
//     * �����
//     */
//    public void checkoutSelectedObject()
//    {
//        PartDebug.trace(this, PartDebug.PART_CLIENT,
//                "checkoutSelectedObject()..begin....");
//        Object obj = getSelectedObject();
//        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
//        if(null == obj)
//        {
//            showMessage(noSelectObj, exceptionTitle);
//            return;
//        }
//        if(obj instanceof QMPartMasterIfc)
//        {
//            showMessage(errorObject, exceptionTitle);
//            return;
//        }
//        //���ѡ��Ķ��������ϼй���
//        if(obj instanceof FolderedIfc)
//        {
//            FolderedIfc selected_object = (FolderedIfc) obj;
//            //���selected_object����Ϊnull,���ö�������
//            if(null != selected_object)
//            {
//                checkOutObject(selected_object);
//            }
//        }
//        obj = null;
//        displayWaitIndicatorOff();
//    }

    /**
     * ��������,�������ɹ�,����true,���򷵻�false
     * @param foldered_obj FolderedIfc
     * @return boolean
     */
    protected boolean checkOutObject(FolderedIfc foldered_obj,Vector messageVec)
    {
        boolean successful_checkout = false;
        String messageStr="";
        try
        {
            if(foldered_obj instanceof WorkableIfc)
            {
                try
                {
                    //�������ϼ��е��㲿������������
                    //��ʾ"��ǰ�㲿���ڸ������ϼ��У����ܼ��!"��
                    if(!CheckInOutTaskLogic
                            .isInVault((WorkableIfc) foldered_obj))
                    {
                        QMPartIfc part = (QMPartIfc) foldered_obj;
                        String username = "";
                        UserIfc qmprincipal = CheckInOutTaskLogic
                                .getCheckedOutBy(part);
                        if(qmprincipal != null)
                        {
                            username = qmprincipal.getUsersDesc();
                        }
                        if(username.trim().length()==0)
                        {
                        	/*�ж϶����Ƿ��ǹ���������*/
                            boolean flag = WorkInProgressHelper
                                    .isWorkingCopy((WorkableIfc) part);
                            if(flag)
                            {
                            	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
                            }
                        }
                        String message = "";
                        if(username != null && username.length() > 0)
                        {
                            //�㲿�����Լ����
                            if(CheckInOutTaskLogic
                                    .isCheckedOutByUser((WorkableIfc) foldered_obj))
                                message = QMMessage
                                        .getLocalizedMessage(
                                                RESOURCE,
                                                QMProductManagerRB.ALREADY_CHECKOUT_SELF,
                                                null);
                            else
                            {
                                //�㲿�������˼��
                                if(CheckInOutTaskLogic
                                        .isCheckedOutByOther((WorkableIfc) part))
                                {
                                    Object[] obj = {username};
                                    message = QMMessage
                                            .getLocalizedMessage(
                                                    RESOURCE,
                                                    QMProductManagerRB.ALREADY_CYHECKOUT_OTHER,
                                                    obj);
                                }
                                //�㲿����û�б����˼��Ҳû���Լ����˵���㲿�����´�����λ�ڸ������ϼ���
                                else
                                {
                                    Object[] obj = {username};
                                    message = QMMessage
                                            .getLocalizedMessage(
                                                    RESOURCE,
                                                    QMProductManagerRB.CANNOT_CHECKOUT_USERFOLDER,
                                                    obj);
                                }
                            }
                        }
                        else if(CheckInOutTaskLogic
                                .isCheckedOut((WorkableIfc) foldered_obj))
                        {
                            message = QMMessage.getLocalizedMessage(RESOURCE,
                                    QMProductManagerRB.ALREADY_CHECKOUT_SELF,
                                    null);
                        }
                        else
                        {
                            message = QMMessage
                                    .getLocalizedMessage(
                                            RESOURCE,
                                            QMProductManagerRB.CANNOT_CHECKOUT_USERFOLDER,
                                            null);
                        }
//                        showMessage(message, exceptionTitle);
                        messageStr=message;
                        return false;
                    }
                    myExplorer.setTreeValueChanged(true);
                    CheckOutTask checkout_task = new CheckOutTask(
                            getParentFrame(), CheckInOutTaskLogic
                                    .getCheckoutFolder());
                    checkout_task.setCheckoutItem((WorkableIfc) foldered_obj);
                    checkout_task.checkout();
                    successful_checkout = true;
                    messageStr="����ɹ�";
                }
                catch (AlreadyCheckedOutException acoe)
                {
                    successful_checkout = false;
                    String folder_name = "";
                    try
                    {
                        FolderIfc checkout_folder = CheckInOutTaskLogic
                                .getCheckoutFolder((WorkableIfc) foldered_obj);
                        //��ʾ:"�㲿��*�Ѿ��������*���ϼ���!"��
                        if(null != checkout_folder)
                        {
                            Class[] class1 = {FolderBasedIfc.class};
                            Object[] paraClass = {(FolderBasedIfc) checkout_folder};
                            folder_name = (String) IBAUtility
                                    .invokeServiceMethod("FolderService",
                                            "getPath", class1, paraClass);
                            Object[] param = {folder_name};
                            String message = QMMessage.getLocalizedMessage(
                                    RESOURCE,
                                    QMProductManagerRB.AIREADY_CHECKOUT, param);
//                            showMessage(message, exceptionTitle);
                            messageStr=message;
                        }
                    }
                    //��ʾ:"������������ϼ�ʱ��������."��
                    catch (QMException e)
                    {
                        Object[] param = {folder_name};
                        String message = QMMessage
                                .getLocalizedMessage(
                                        RESOURCE,
                                        QMProductManagerRB.RETRIEVE_CHECKOUT_FOLDER_FAILED,
                                        param);
//                        showMessage(message, exceptionTitle);
                        messageStr=message;
                        param = null;
                        message = null;
                        e.printStackTrace();
                    }
                    folder_name = null;
                    acoe.printStackTrace();
                }
                //��ʾ:"���㲿���Ѿ������˼��!"��
                catch (CheckedOutByOtherException coboe)
                {
                    try
                    {
                        successful_checkout = false;
                        String username1 = "";
                        UserIfc qmprincipal = CheckInOutTaskLogic
                                .getCheckedOutBy((QMPartIfc) foldered_obj);
                        if(qmprincipal != null)
                        {
                            username1 = qmprincipal.getUsersDesc();
                        }
                        if(username1.trim().length()==0)
                        {
                        	/*�ж϶����Ƿ��ǹ���������*/
                            boolean flag = WorkInProgressHelper
                                    .isWorkingCopy((WorkableIfc) (QMPartIfc) foldered_obj);
                            if(flag)
                            {
                            	username1=CheckInOutTaskLogic.getCheckedOutBy((QMPartIfc) foldered_obj,java.util.Locale.SIMPLIFIED_CHINESE);
                            }
                        }
                        Object[] objs = {username1};
                        String message = QMMessage.getLocalizedMessage(
                                RESOURCE,
                                QMProductManagerRB.ALREADY_CHECKOUT_BY_OTHER,
                                objs);
//                        showMessage(message, exceptionTitle);
                        messageStr=message;
                        message = null;
                        coboe.printStackTrace();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                catch (Exception e)
                {
                	messageStr=e.getMessage();
//                    showMessage(e.getMessage(), exceptionTitle);
                    successful_checkout = false;
                    e.printStackTrace();
                }
            } //end if (foldered_obj instanceof WorkableIfc)
        }
        finally
        {
        	if(!successful_checkout){
        		messageStr="���ʧ��:"+messageStr;
        	}
        	messageStr=foldered_obj.getIdentity()+messageStr;
            displayWaitIndicatorOff();
            messageVec.add(messageStr);
        }
        return successful_checkout;
    }

    /**
     * ����"�������"���
     */
    public void processUndoCheckOutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), UNDOCHECKOUT);
        work.start();
    }

//    /**
//     * ���������
//     */
//    public void undoCheckoutSelectedObject()
//    {
//        try
//        {
//            Object selected_obj = getSelectedObject();
//            //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
//            if(null == selected_obj)
//            {
//                showMessage(noSelectObj, exceptionTitle);
//                return;
//            }
//            else
//            {
//                //2003/12/16
//                if(selected_obj instanceof QMPartMasterIfc)
//                {
//                    showMessage(errorObject, exceptionTitle);
//                    return;
//                }
//                if(selected_obj instanceof WorkableIfc)
//                {
//                    /*�ж϶����Ƿ��ǹ���������*/
//                    boolean flag = WorkInProgressHelper
//                            .isWorkingCopy((WorkableIfc) selected_obj);
//                    
//                    Object obj = null;
//                    //2007-11-12,add by muyp
//                    WorkableIfc workableIfc=(WorkableIfc)selected_obj;
//                	WorkableIfc workableIfc1=null;
//                	
//                    /*�����ԭ�������ȡ�����Ӧ�Ĺ���������*/
//                    if(!flag)
//                    {
//                        Class[] classes = {WorkableIfc.class};
//                        Object[] aobj = {(WorkableIfc) selected_obj};
//                        obj = IBAUtility.invokeServiceMethod(
//                                "WorkInProgressService", "workingCopyOf",
//                                classes, aobj);
//                    }
//                    /*��������ǹ�����������ǰ�û��ɻ�ȡ����Ĺ�������������Ϊ���û��Ƕ���ļ���߻��ǹ���Ա��*/
//                    if(flag || obj != null)
//                    {
//                    	//2007-11-12,add by muyp,��ò�������ĸ���
//                    	if(flag && obj == null)
//                    	{
//                    		this.setWorkableCopy(workableIfc);
//                    		workableIfc1=CheckInOutTaskLogic.getOriginalCopy(workableIfc);
//                    	}
//                    	else if(!flag && obj != null)
//                    	{
//                    		workableIfc1=workableIfc;
//                    		this.setWorkableCopy((WorkableIfc)obj);
//                    	}//end
//                    	
//                        UndoCheckoutTask undo_checkout_task = new UndoCheckoutTask(
//                                getParentFrame(), (WorkableIfc) selected_obj);
//                        //�޸�2��2007-11-12,add by muyp,begin,�㲿���Ƿ��Ѿ���ʾ
//                    	if(workableIfc1 instanceof QMPartIfc)
//                    	{
//                    		QMPartIfc part = (QMPartIfc) workableIfc1;
//                    		PartItem selected_obj1=new PartItem(part);
//                    		undo_checkout_task.setIsInTree(isInTree(selected_obj1));
//                    	}//end
//                        undo_checkout_task.undoCheckout();
//                    }
//                    /*������Ϊ��ǰ�û��Ȳ��Ǹö���ļ����Ҳ���ǹ���Ա�������ö����ѱ�ĳ���û��������ʾ��Ϣ��*/
//                    else if(CheckInOutTaskLogic
//                            .isCheckedOutByOther((WorkableIfc) selected_obj))
//                    {
//                        String username = "";
//                        UserIfc qmprincipal = CheckInOutTaskLogic
//                                .getCheckedOutBy((WorkableIfc) selected_obj);
//                        if(qmprincipal != null)
//                        {
//                            username = qmprincipal.getUsersDesc();
//                        }
//                        if(username.trim().length()==0)
//                        {
//                        	/*�ж϶����Ƿ��ǹ���������*/
//                            boolean flag1 = WorkInProgressHelper
//                                    .isWorkingCopy((WorkableIfc)selected_obj);
//                            if(flag1)
//                            {
//                            	username=CheckInOutTaskLogic.getCheckedOutBy((QMPartIfc) selected_obj,java.util.Locale.SIMPLIFIED_CHINESE);
//                            }
//                        }
//                        Object[] aobj = {username};
//                        String message = QMMessage.getLocalizedMessage(
//                                RESOURCE,
//                                QMProductManagerRB.ALREADY_CYHECKOUT_OTHER,
//                                aobj);
//                        DialogFactory.showWarningDialog(this, message);
//                    }
//                    else
//                    {
//                        Object[] aobj = {((QMPartIfc) selected_obj)
//                                .getPartNumber()};
//                        String message = QMMessage.getLocalizedMessage(
//                                RESOURCE, QMProductManagerRB.NOT_CHECKED_OUT,
//                                aobj);
//                        DialogFactory.showWarningDialog(this, message);
//                    }
//                    myExplorer.setIsCheckin(true);
//                }
//            }
//            selected_obj = null;
//        }
//        catch (QMException ex)
//        {
//            ex.printStackTrace();
//        }
//        finally
//        {
//            displayWaitIndicatorOff();
//        }
//    }
    
    /**
     * ���������
     */
    public void undoCheckoutSelectedObject(BaseValueIfc selected_obj,
			Vector messageVec) {
    	String messageStr="";
		try {
			if (selected_obj instanceof QMPartMasterIfc) {
				showMessage(errorObject, exceptionTitle);
				messageVec.add(selected_obj.getIdentity()+"�������ʧ��"+errorObject);
				return;
			}
			if (selected_obj instanceof WorkableIfc) {
				/* �ж϶����Ƿ��ǹ��������� */
				boolean flag = WorkInProgressHelper
						.isWorkingCopy((WorkableIfc) selected_obj);

				Object obj = null;
				// 2007-11-12,add by muyp
				WorkableIfc workableIfc = (WorkableIfc) selected_obj;
				WorkableIfc workableIfc1 = null;

				/* �����ԭ�������ȡ�����Ӧ�Ĺ��������� */
				if (!flag) {
					Class[] classes = { WorkableIfc.class };
					Object[] aobj = { (WorkableIfc) selected_obj };
					obj = IBAUtility.invokeServiceMethod(
							"WorkInProgressService", "workingCopyOf", classes,
							aobj);
				}
				/* ��������ǹ�����������ǰ�û��ɻ�ȡ����Ĺ�������������Ϊ���û��Ƕ���ļ���߻��ǹ���Ա�� */
				if (flag || obj != null) {
					// 2007-11-12,add by muyp,��ò�������ĸ���
					if (flag && obj == null) {
						this.setWorkableCopy(workableIfc);
						workableIfc1 = CheckInOutTaskLogic
								.getOriginalCopy(workableIfc);
					} else if (!flag && obj != null) {
						workableIfc1 = workableIfc;
						this.setWorkableCopy((WorkableIfc) obj);
					}// end

					UndoCheckoutTask undo_checkout_task = new UndoCheckoutTask(
							getParentFrame(), (WorkableIfc) selected_obj);
					// �޸�2��2007-11-12,add by muyp,begin,�㲿���Ƿ��Ѿ���ʾ
					if (workableIfc1 instanceof QMPartIfc) {
						QMPartIfc part = (QMPartIfc) workableIfc1;
						PartItem selected_obj1 = new PartItem(part);
						undo_checkout_task.setIsInTree(isInTree(selected_obj1));
					}// end
					undo_checkout_task.undoCheckout();
					if(undo_checkout_task.getIsUndoCheckOutSuc())
					{
						messageStr=selected_obj.getIdentity()+"��������ɹ�";
					}
					else
					{
						messageStr=selected_obj.getIdentity()+"�������ʧ��";
					}

				}
				/* ������Ϊ��ǰ�û��Ȳ��Ǹö���ļ����Ҳ���ǹ���Ա�������ö����ѱ�ĳ���û��������ʾ��Ϣ�� */
				else if (CheckInOutTaskLogic
						.isCheckedOutByOther((WorkableIfc) selected_obj)) {
					String username = "";
					UserIfc qmprincipal = CheckInOutTaskLogic
							.getCheckedOutBy((WorkableIfc) selected_obj);
					if (qmprincipal != null) {
						username = qmprincipal.getUsersDesc();
					}
					if (username.trim().length() == 0) {
						/* �ж϶����Ƿ��ǹ��������� */
						boolean flag1 = WorkInProgressHelper
								.isWorkingCopy((WorkableIfc) selected_obj);
						if (flag1) {
							username = CheckInOutTaskLogic.getCheckedOutBy(
									(QMPartIfc) selected_obj,
									java.util.Locale.SIMPLIFIED_CHINESE);
						}
					}
					Object[] aobj = { username };
					String message = QMMessage.getLocalizedMessage(RESOURCE,
							QMProductManagerRB.ALREADY_CYHECKOUT_OTHER, aobj);
//					DialogFactory.showWarningDialog(this, message);
					messageStr=selected_obj.getIdentity()+"�������ʧ��:"+message;
				} else {
					Object[] aobj = { ((QMPartIfc) selected_obj)
							.getPartNumber() };
					String message = QMMessage.getLocalizedMessage(RESOURCE,
							QMProductManagerRB.NOT_CHECKED_OUT, aobj);
//					DialogFactory.showWarningDialog(this, message);
					messageStr=selected_obj.getIdentity()+"�������ʧ��:"+message;
				}
				// myExplorer.setIsCheckin(true);
			}
			selected_obj = null;
		} catch (QMException ex) {
			ex.printStackTrace();
			messageStr=selected_obj.getIdentity()+"�������ʧ��:"+ex.getMessage();
		} finally {
			displayWaitIndicatorOff();
			messageVec.add(messageStr);
		}
	}

    /**
     * ����"�ƶ�"���
     */
    public void processMoveCommand()
    {
        Object obj = getSelectedObject();
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        //�ж���ѡ��Ķ����Ƿ�ΪWorkableIfc����
        if(obj instanceof WorkableIfc)
        {
            //�ж���ѡ��Ķ����Ƿ��Ǽ��״̬��
            boolean flag = WorkInProgressHelper.isCheckedOut((WorkableIfc) obj);
            //���Ϊ���״̬����ʾ�쳣��Ϣ���ö����Ѿ�����������ܱ��ƶ�������
            if(flag)
            {
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.NOT_MOVE, null);
                showMessage(message, exceptionTitle);
                message = null;
                return;
            }
        }
        //�ж���ѡ��Ķ����Ƿ�ΪFolderedIfc����
        if(obj instanceof FolderedIfc)
        {
            FolderEntryIfc folderedIfc = (FolderEntryIfc) getSelectedObject();
            Class[] class1 = {FolderEntryIfc.class};
            Object[] param1 = {obj};
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
            boolean flag1 = flag.booleanValue();
            //���obj���ڸ����ļ����У����ڹ����ļ����С�
            if(!flag1)
            {
                new MoveController((RevisionControlledIfc) folderedIfc);
            }
            //���obj�ڸ����ļ����У���ʾ�쳣��Ϣ��
            //���ö����ڹ������ϼ��У����ܱ��Ƶ��������ϼ��С�����
            else
            {
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.NOT_IN_COMMON_FOLDER, null);
                showMessage(message, exceptionTitle);
                message = null;
                return;
            }
            folder = null;
        } //end if (obj instanceof FolderedIfc)
        obj = null;
        displayWaitIndicatorOff();
    }

    /**
     * ����"�޶�"���
     */
    public void processReviseCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), REVISE);
        work.start();
    }

    /**
     * �޶���
     */
    protected void reviseSelectedObject()
    {
        Object obj = getSelectedObject();
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        VersionedIfc version = null;
        if(obj instanceof VersionedIfc)
        {
            version = (VersionedIfc) obj;
            /*         Class[] paraClass2 = {Object.class,String.class};
             Object[] objs2 = {(Object) version,QMPermission.MODIFY};
             //Collection result = null;
             try
             {
             IBAUtility.invokeServiceMethod(
             "AccessControlService", "checkAccess",
             paraClass2, objs2);
             }
             catch (QMRemoteException ex)
             {
             //flag=false;
             String RESOURCES =
             "com.faw_qm.part.client.design.util.PartDesignViewRB";
             String title = QMMessage.getLocalizedMessage(RESOURCES,
             "exception", null);
             JOptionPane.showMessageDialog(this,
             ex.getClientMessage(),
             title,
             JOptionPane.
             INFORMATION_MESSAGE);

             return;
             }
             */
            /**Class[] class2 =
             {VersionedIfc.class};
             Object[] param2 =
             {version};

             Boolean flag = null;
             try
             {
             flag = (Boolean) IBAUtility.invokeServiceMethod(
             "VersionControlService",
             "isLatestVersion", class2, param2);
             }
             catch (QMRemoteException ex1)
             {
             flag = null;
             ex1.printStackTrace();
             }
             boolean flag1 = flag.booleanValue();
             if (!flag1)
             {
             String iden = "";
             if(obj instanceof QMPartIfc)
             {
             QMPartIfc partifc = (QMPartIfc)obj;
             iden = partifc.getIdentity();
             }
             Object aobj[] = {iden};*/
            /**�������°汾�������޶�*/
            /**String notNewView = QMMessage.getLocalizedMessage(
             RESOURCE,
             QMProductManagerRB.NOT_NEW_REVISE, aobj);
             showMessage(notNewView , exceptionTitle);
             return;
             }*/
            JFrame frame = getParentFrame();
            //�ж��û��Ը��㲿���Ƿ���"�޶�"Ȩ�ޣ���"�޶�"Ȩ�޲����޶��㲿��2007.03.19
            boolean flag = true;
            Class[] paraClass = {Object.class, String.class};
            Object[] para = {version, QMPermission.REVISE};
            try
            {
                 Boolean flag1 = (Boolean)IBAUtility.invokeServiceMethod(
                          "AccessControlService", "hasAccess", paraClass, para);
                 flag = flag1.booleanValue();
            }
            catch (QMException ex)
            {
                 ex.printStackTrace();
            }
            if(!flag)
            {
                  JOptionPane.showMessageDialog(frame, QMMessage.getLocalizedMessage(RESOURCE,
                       "canNotRevise", null),exceptionTitle,JOptionPane.WARNING_MESSAGE);
                  return;
            }
            ReviseTask revise_task = new ReviseTask(frame, version);
            revise_task.revise(false);
        }
        obj = null;
        displayWaitIndicatorOff();
    }

    /**
     * ����"�汾��ʷ"���
     */
    public void processVersionHistroyCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW_VERSION_HISTORY);
        work.start();
    }

    /**
     * �汾��ʷ��
     */
    protected void viewVersionHistory()
    {
        Object obj = getSelectedObject();
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof WorkableIfc)
        {
            WorkableIfc workable = (WorkableIfc) obj;
            String objID = workable.getBsoID();
            HashMap hashmap = new HashMap();
            hashmap.put("bsoID", objID);
            hashmap.put("mpage", "banben");
            //ת��"�汾��ʷ"ҳ�档
            RichToThinUtil.toWebPage("part_version_versionViewMain.screen",
                    hashmap);
        }
        obj = null;
        displayWaitIndicatorOff();
    }

    /**
     * ����"������ʷ"���
     */
    public void processIterationHistroyCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                VIEW_ITERATION_HISTORY);
        work.start();
    }

    /**
     * ������ʷ��
     */
    protected void viewIterationHistory()
    {
        Object obj = getSelectedObject();
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof WorkableIfc)
        {
            WorkableIfc workable = (WorkableIfc) obj;
            String objID = null;
            HashMap hashmap = new HashMap();
            if(workable instanceof IteratedIfc)
            {
                MasteredIfc master = ((IteratedIfc) workable).getMaster();
                objID = master.getBsoID();
                hashmap.put("BsoID", objID);
                hashmap.put("bsoID", ((IteratedIfc) workable).getBsoID());
                hashmap.put("mpage", "banxu");
            }
            //ת��"������ʷ"ҳ�档
            RichToThinUtil.toWebPage("part_version_iterationsViewMain.screen",
                    hashmap);
            //������λ��
            workable = null;
            objID = null;
            hashmap = null;
        }
        obj = null;
        displayWaitIndicatorOff();
    }

    /**
     * ����"������׼��"���
     */
    public void processCreateBaselineCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE_BASELINE);
        work.start();
    }

    /**
     * ������׼�ߡ�
     */
    protected void createBaseline()
    {
        new BaselineCreateController();
    }

    /**
     * ����"ά����׼��"���
     */
    public void processMaintenanceBaselineCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), MAINTENANCE_BASELINE);
        work.start();
    }

    /**
     * ά����׼�ߡ�
     */
    protected void maintenanceBaseline()
    {
        new BaselineSearchController(frame);
    }

    /**
     * ����"�鿴��׼��"���
     */
    public void processViewBaselineCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW_BASELINE);
        work.start();
    }

    /**
     * �鿴��׼�ߡ�
     */
    protected void viewBaseline()
    {
        new BaselineAddController(4, frame);
    }

    /**
     * ����"��ӻ�׼��"���
     */
    public void processAddBaselineCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ADD_BASELINE);
        work.start();
    }

    /**
     * ��ӻ�׼�ߡ�
     */
    protected void addBaseline()
    {
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        //��ʾ��ѡ��Ķ������ʹ��󡣡���
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        new BaselineAddController(0, part, frame);
        //������λ��
        part = null;
        obj = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT, "addBaseline()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * ����"����"���
     */
    //add by �ܴ��� 2003.10.21
    public void processSort()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SORT);
        work.start();
    }

    /**
     * ����
     */
    //add by �ܴ��� 2003.10.21
    protected void sort()
    {
        new SortController(this, frame);
    }

    /**
     * ����"���ṹ��ӻ�׼��"���
     */
    public void processPopulateBaselineCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), POPULATE_BASELINE);
        work.start();
    }

    /**
     * ���ṹ��ӻ�׼�ߡ�
     */
    protected void populateBaseline()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "populateBaseline()..begin ....");
        QMPartIfc part = null;
        PartConfigSpecIfc configSpecIfc = configSpecItem.getConfigSpecInfo();
        Object obj = getSelectedObject();
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        //��ʾ��ѡ��Ķ������ʹ��󡣡���
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        new BaselineAddController(1, part, configSpecIfc, frame);
        //������λ��
        part = null;
        configSpecIfc = null;
        obj = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "populateBaseline()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * ����"�Ƴ���׼��"���
     */
    public void processRemoveBaselineCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), REMOVE_BASELINE);
        work.start();
    }

    /**
     * �Ƴ���׼�ߡ�
     */
    protected void removeBaseline()
    {
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        //��ʾ��ѡ��Ķ������ʹ��󡣡���
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        //��part�������Ƴ���׼�ߡ�ҳ�档
        new BaselineRemoveController(part, frame);
        //������λ��
        part = null;
        obj = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "removeBaseline()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * ����"������Ч�Է���"���
     */
    public void processCreateConfigItemCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CREATE_EFFCONFIGITEM);
        work.start();
    }

    /**
     * ������Ч�Է�����
     */
    protected void createEffConfigItem()
    {
        new EffCreateController();
    }

    /**
     * ����"ά����Ч�Է���"���
     */
    public void processMaintenanceConfigItemCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                MAINTENANCE_EFFCONFIGITEM);
        work.start();
    }

    /**
     * ά����Ч�Է�����
     */
    protected void maintenanceEffConfigItem()
    {
        new EffSearchController("MAINTAIN", this, true);
    }

    /**
     * ����"�鿴��Ч�Է���"���
     */
    public void processViewConfigItemCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW_EFFCONFIGITEM);
        work.start();
    }

    /**
     * �鿴��Ч�Է�����
     */
    protected void viewEffConfigItem()
    {
        new EffSearchController("APP", this, false);
    }

    /**
     * ����"�����Ч��"���
     */
    public void processAddEffectivityCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ADD_EFFECTIVITY);
        work.start();
    }

    /**
     * �����Ч�ԡ�
     */
    protected void addEffectivity()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "addEffectivity()..begin ....");
        QMPartIfc part = null;
        //���ѡ��������
        Object obj = getSelectedObject();
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        //�������QMPartIfc������ʾ��Ϣ��������ѡ�����ʹ��󡣡���
        if(!(obj instanceof QMPartIfc))
        {
            showMessage(errorObject, exceptionTitle);
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
            QMPartIfc[] parts = new QMPartIfc[] {part};
            //ת�롰�����Ч�ԡ�ҳ�档
            EffectivityAddJFrame frame1 = new EffectivityAddJFrame(parts, true);
            PartScreenParameter.centerWindow(frame1);
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "addEffectivity()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * ����"���ṹ�����Ч��"���
     */
    public void processPopulateEffectivityCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(),
                ADD_EFFECTIVITY_BY_STRUCTURE);
        work.start();
    }

    /**
     * ���ṹ�����Ч�ԡ�
     */
    protected void populateEffectivity()
    {
        //���á�������Ч�Է��������档
        new EffSearchController("APP", this, true);
    }

    /**
     * ���ѡ�����Ч�Է�����
     * �˷���Ϊ��������Ч�Է���������������
     * @param a QMConfigurationItemIfc ѡ�����Ч�Է�����
     */
    //2003/12/19
    public void addConfiEff(QMConfigurationItemIfc a)
    {
        QMPartIfc[] parts = null;
        //�����Ч�Է�����solutionID��
        String solutionID = a.getSolutionID();
        // ���ѡ�����Ч�Է���û���㲿������ʾ��Ϣ��
        //������Ч�Է���û���㲿�����������ܽ��д������������
        if(null == solutionID)
        {
            String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                    QMProductManagerRB.NO_SOLUTION, null);
            showMessage(message1, exceptionTitle);
            message1 = null;
            return;
        }
        PartConfigSpecIfc partConfig = null;
        try
        {
            partConfig = (PartConfigSpecIfc) PartServiceRequest
                    .findPartConfigSpecIfc();
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        Class[] class1 = {String.class};
        Object[] param1 = {solutionID};
        if(!partConfig.getStandardActive())
        {
            String message0 = QMMessage.getLocalizedMessage(RESOURCE,
                    QMProductManagerRB.STANDCONFIG, null);
            showMessage(message0, exceptionTitle);
            return;
        }
        //���ó־û�����ķ�������㲿��������
        QMPartMasterIfc master = null;
        try
        {
            master = (QMPartMasterIfc) IBAUtility.invokeServiceMethod(
                    "PersistService", "refreshInfo", class1, param1);
        }
        catch (QMRemoteException ex2)
        {
            master = null;
            ex2.printStackTrace();
        }
        //����ڵ�ǰɸѡ������partMasterIfc�Ľṹ�������Ӽ���
        try
        {
            parts = (QMPartIfc[]) PartServiceRequest
                    .getAllSubPartsByConfigSpec(master, partConfig);
        }
        catch (QMException ex1)
        {
            ex1.printStackTrace();
        }
        //���partsΪ�ջ�û��Ԫ�أ���ʾ�쳣��Ϣ�����ýṹû�к��ʵİ汾���󡣡���
        if((null == parts) || (parts.length == 0))
        {
            String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                    QMProductManagerRB.NO_QUALIFIED_VERSION, null);
            showMessage(message1, exceptionTitle);
            message1 = null;
            return;
        }
        //���á����ṹ�����Ч�ԡ�ҳ�棬����parts�е��㲿����Ϣ���б���С�
        EffectivityAddJFrame frame1 = new EffectivityAddJFrame(parts, false);
        frame1.setConfigItem(a);
        frame1.setShow();
        PartScreenParameter.centerWindow(frame1);
        //������λ��
        parts = null;
        solutionID = null;
        partConfig = null;
        master = null;
    }

    /**
     * ����"�޸���Ч��ֵ"���
     */
    public void processUpdateEffValueCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), UPDATE_EFFECTIVITY);
        work.start();
    }

    /**
     * �޸���Ч��ֵ��
     */
    protected void updateSelectedEffectivity()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "updateSelectedEffectivity()..begin ....");
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "----------  selected object is:  " + obj);
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        //��ʾ��ѡ��Ķ������ʹ��󡣡���
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        //��part�������޸���Ч��ֵ��ҳ�档
        new EffModifyController(part);
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "updateSelectedEffectivity()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * ����"�Ƴ���Ч��"���
     */
    public void processRemoveEffectivityCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), REMOVE_EFFECTIVITY);
        work.start();
    }

    /**
     * �Ƴ���Ч�ԡ�
     */
    protected void removeEffectivity()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "removeEffectivity()..begin ....");
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        //��ʾ��ѡ��Ķ������ʹ��󡣡���
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        //���鵱ǰ�û���Ȩ�ޡ�
        //��part�������Ƴ���Ч�ԡ�ҳ�档
        new EffRemoveController(part);
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "removeEffectivity()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * ����"�滻��"���
     */
    public void processDefineAlternatesCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), DEFINE_ALTERNATES);
        work.start();
    }

    /**
     * �滻����
     */
    protected void defineAlternates()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "defineAlternates()..begin ....");
        //���ѡ��Ķ���
        Object obj = getSelectedObject();
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        QMPartMasterIfc partmaster = null;
        //���ѡ��Ķ���ΪQMPartIfc����ö�Ӧ��QMPartMasterIfc��
        if(obj instanceof QMPartIfc)
        {
            partmaster = (QMPartMasterIfc) (((QMPartIfc) obj).getMaster());
        }
        else if(obj instanceof QMPartMasterIfc)
        {
            partmaster = (QMPartMasterIfc) obj;
        }
        else
        //��ʾ�����������ʹ��󡣡���
        {
            showMessage(errorObject, exceptionTitle);
        }
        //��ö����master��bsoID��
        String bsoID = partmaster.getBsoID();
        HashMap hashmap = new HashMap();
        hashmap.put("bsoID", bsoID);
        //ת���༭�滻��ҳ�档
        RichToThinUtil.toWebPage("Part-AltAndSub-EditAlterPart-001-0A.do",
                hashmap);
        //������λ��
        obj = null;
        partmaster = null;
        bsoID = null;
        hashmap = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "defineAlternates()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * ����"�ṹ�滻��"���
     */
    public void processDefineSubstitutesCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), DEFINE_SUBSTITUTES);
        work.start();
    }

    /**
     * �ṹ�滻����
     */
    protected void defineSubstitutes()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "defineSubstitutes()..begin ....");
        Object selectedObj = getSelectedObject();
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == selectedObj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        // �����Ƿ�ѡ���˸������������һ����㣬����ǣ���ʾ�������ܶ���ṹ�滻��������
        if(topLevelNodeSelected())
        {
            String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                    QMProductManagerRB.CAN_NOT_DEFINE_SUBSTITUTES, null);
            showMessage(message1, exceptionTitle);
            message1 = null;
            return;
        }
        // �����ѡ�����masterֵ����
        QMPartMasterIfc selectedMaster = null;
        if(selectedObj instanceof QMPartIfc)
        {
            selectedMaster = (QMPartMasterIfc) (((QMPartIfc) selectedObj)
                    .getMaster());
        }
        else if(selectedObj instanceof QMPartMasterIfc)
        {
            //���selectedMasterΪnull����ʾ����ѡ��Ķ������㲿��������
            selectedMaster = (QMPartMasterIfc) selectedObj;
        }
        if(null == selectedMaster)
        {
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    QMProductManagerRB.OBJ_NOT_PART_OR_PARTMASTER, null);
            showMessage(message, exceptionTitle);
            message = null;
        }
        else
        {
            //���selectedMaster�����ұ��б���ѡ��ģ����丸���������������ѡ��չ��
            //�Ľ���ϵĲ�������Ϊֻ��ѡ�����Ͻ�㲢չ��ʱ�ұ��б��вŻ��ж�����ڣ���
            //���selectedMaster�����������ѡ��ģ������ϵĸ�������Ϊ�丸������
            BaseValueIfc detailObj = null;
            BaseValueIfc parentPart = null;
            //���selectedMaster�����ұ��б���ѡ��ģ�����б�����ѡ����
            if(null != (myExplorer.getSelectedDetail()))
            {
                detailObj = (BaseValueIfc) (myExplorer.getSelectedDetail()
                        .getObject());
                //����б���ѡ��Ķ��������ṹ��ѡ��Ķ�����ͬ��bsoID��ͬ����
                if(detailObj.getBsoID().equals(
                        ((BaseValueIfc) getSelectedObject()).getBsoID()))
                {
                    //����б���ѡ����������ṹ�еĸ��ڵ�͸�������
                    QMNode parentNode = myExplorer.getSelectedNode();
                    parentPart = (BaseValueIfc) (parentNode.getObj()
                            .getObject());
                }
            }
            // ��������������ṹ�л�õģ�ֱ�ӻ�����ĸ��ڵ�͸�������
            if(null == parentPart)
            {
                QMNode parentNode = (myExplorer.getSelectedNode()).getP();
                parentPart = (BaseValueIfc) (parentNode.getObj().getObject());
            }
            Class[] paraClass = {String.class, BaseValueIfc.class,
                    String.class, BaseValueIfc.class};
            Object[] objs = {"PartUsageLink", (BaseValueIfc) parentPart,
                    "usedBy", (BaseValueIfc) selectedMaster};
            Collection collection = null;
            try
            {
                collection = (Collection) IBAUtility.invokeServiceMethod(
                        "PersistService", "findLinkValueInfo", paraClass, objs);
            }
            catch (QMRemoteException ex)
            {
                collection = null;
                ex.printStackTrace();
            }
            PartUsageLinkIfc usageLink = null;
            Iterator iterator = collection.iterator();
            //���������û�ж�����ʾ����û���ҵ�ʹ�ù�ϵ������
            if(!iterator.hasNext())
            {
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.NO_USAGE_RELATIONSHIP_FOUND, null);
                showMessage(message, exceptionTitle);
            }
            else
            {
                //���ʹ�ù�ϵ����Ӧ�ĸ������Ӽ���bsoID��
                usageLink = (PartUsageLinkIfc) iterator.next();
                QMPartMasterIfc currentUsesMaster = (QMPartMasterIfc) usageLink
                        .getUses();
                QMPartIfc part = (QMPartIfc) usageLink.getUsedBy();
                String childID = currentUsesMaster.getBsoID();
                String parentID = part.getBsoID();
                String usageLinkID = usageLink.getBsoID();
                //��ʹ�ù�ϵ����Ӧ�ĸ������Ӽ���bsoID����hashmap�С�
                HashMap hashmap = new HashMap();
                hashmap.put("childID", childID);
                hashmap.put("parentID", parentID);
                hashmap.put("usageLinkID", usageLinkID);
                //ת���༭�����滻��ҳ�档
                RichToThinUtil.toWebPage(
                        "Part-AltAndSub-EditStruAlterPart-001-0A.do", hashmap);
                usageLink = null;
                currentUsesMaster = null;
                part = null;
                childID = null;
                parentID = null;
                usageLinkID = null;
                hashmap = null;
            } //end if (!iterator.hasNext()) else
            detailObj = null;
            parentPart = null;
        } //end if (null == selectedMaster) else
        //������λ��
        selectedObj = null;
        selectedMaster = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "defineSubstitutes()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * ����"������ͼ�汾"���
     */
    public void processPublishViewVersionCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), NEWVIEWVERSION);
        work.start();
    }

    /**
     * ������ͼ�汾��
     */
    protected void assignViewSelectedObject()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "assignViewSelectedObject()..begin ....");
        VersionedIfc version = null;
        Object selected_obj = getSelectedObject();
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == selected_obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        else
        {
            if (selected_obj instanceof VersionedIfc)
            {
                version = (VersionedIfc) selected_obj;
                JFrame frame = getParentFrame();
                //�ж��û��Ը��㲿���Ƿ���"�޶���ͼ�汾"Ȩ�ޣ���"�޶���ͼ�汾"Ȩ�޲��ܷ�����ͼ2007.01.12
                 boolean flag = true;
                 Class[] paraClass = {Object.class, String.class};
                 Object[] para = {version, QMPermission.REVISEVIEW};
                 try
                 {
                      Boolean flag1 = (Boolean)IBAUtility.invokeServiceMethod(
                               "AccessControlService", "hasAccess", paraClass, para);
                      flag = flag1.booleanValue();
                 }
                 catch (QMException ex)
                 {
                      ex.printStackTrace();

                 }
                 if(!flag)
                 {
                       JOptionPane.showMessageDialog(frame, QMMessage.getLocalizedMessage(RESOURCE,
                            "canNotReviseView", null),exceptionTitle,JOptionPane.WARNING_MESSAGE);
                       return;
                 }
                ReviseTask revise_task = new ReviseTask(frame, version);
                revise_task.revise(true);
            }
            else
            {
                //��ʾ"��ѡ�����ܷ�����ͼ�汾��"��
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.NOT_ASSIGN_VIEW_VERSION, null);
                showMessage(message, exceptionTitle);
            }
        }
        //������λ��
        version = null;
        selected_obj = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "assignViewSelectedObject()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * �����������ԡ����
     */
    //add by л��2004.04.06
    public void processPublishIBACommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), PUBLISHIBA);
        work.start();
    }

    /**
     * �������ԡ�
     */
    //add by л��2004.04.06
    protected void assignIBASelectedObject()
    {
        try
        {
            displayWaitIndicatorOn();
            BaseValueIfc selectObject = (BaseValueIfc) getSelectedObject();
            if(null == selectObject)
            {
                showMessage(noSelectObj, exceptionTitle);
                return;
            }
            else
            {
                if(selectObject instanceof QMPartMasterIfc)
                {
                    showMessage(errorObject, exceptionTitle);
                    return;
                }
                if(selectObject instanceof WorkableIfc)
                {
                    //if 11:����������û���������㲿��,��ʾ"�����Ѿ������˼��!"��
                    if(CheckInOutTaskLogic
                            .isCheckedOutByOther((WorkableIfc) selectObject))
                    {
                        //if 1
                        if(selectObject instanceof QMPartIfc)
                        {
                            QMPartIfc part = (QMPartIfc) selectObject;
                            String username = "";
                            UserIfc qmprincipal = CheckInOutTaskLogic
                                    .getCheckedOutBy(part);
                            if(qmprincipal != null)
                            {
                                username = qmprincipal.getUsersDesc();
                            }
                            if(username.trim().length()==0)
                            {
                            	/*�ж϶����Ƿ��ǹ���������*/
                                boolean flag1 = WorkInProgressHelper
                                        .isWorkingCopy((WorkableIfc)part);
                                if(flag1)
                                {
                                	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
                                }
                            }
                            Object[] objs = {username};
                            String message = QMMessage
                                    .getLocalizedMessage(
                                            RESOURCE,
                                            QMProductManagerRB.ALREADY_CHECKOUT_BY_OTHER,
                                            objs);
                            showMessage(message, exceptionTitle);
                            return;
                        }
                    } //end if 12:�������ǰ�û����
                    else if(CheckInOutTaskLogic
                            .isCheckedOutByUser((WorkableIfc) selectObject))
                    {
                        //if 2:����Ǳ���ǰ�û���������ǹ�������,�ͻ�ù�������
                        if(!CheckInOutTaskLogic
                                .isWorkingCopy((WorkableIfc) selectObject))
                        {
                            selectObject = CheckInOutTaskLogic
                                    .getWorkingCopy((WorkableIfc) selectObject);
                        }
                    }
                }
                int i=0;
                if(selectObject instanceof QMPartIfc)
                	i=PartServiceRequest.getIBACount((QMPartIfc)selectObject);
                if(i==0)
                {
                	
                     String message = QMMessage
                             .getLocalizedMessage(
                                     RESOURCE,
                                     QMProductManagerRB.NOIBA,
                                     null);
                     showMessage(message, exceptionTitle);
                     return;
                }
                if(!PartServiceRequest.publishIBA((QMPartIfc)selectObject))
                	
                {

                    String message = QMMessage
                            .getLocalizedMessage(
                                    RESOURCE,
                                    QMProductManagerRB.ALREADYPUBLISH,
                                    null);
                    showMessage(message, exceptionTitle);
                    return;
                }
                Object value=null;
                
                if(i>0)
                {
                	Object[] objs={Integer.toString(i)};
                	String message = QMMessage
                    .getLocalizedMessage(
                            RESOURCE,
                            QMProductManagerRB.IBACOUNT,
                            objs);
                    String title=QMMessage
                    .getLocalizedMessage(
                            RESOURCE,
                            QMProductManagerRB.PUBLISHSURE,
                            null);
                	JOptionPane op=new JOptionPane(message,JOptionPane.QUESTION_MESSAGE,JOptionPane.OK_CANCEL_OPTION);;
                	JDialog dialog=op.createDialog(frame, title);
                	dialog.setVisible(true);
                	value=op.getValue();
                	
                }
                int intVal=-2;
                if(value!=null)
                
                	if(value instanceof Integer)
                intVal=((Integer)value).intValue();
                if(intVal==JOptionPane.OK_OPTION)
                if(null != selectObject)
                {
                	
                    Class[] classes = {QMPartIfc.class};
                    Object[] objects = {selectObject};
                    Object obj = (QMPartMasterIfc) IBAUtility
                            .invokeServiceMethod("IBAValueService",
                                    "publishIBAToPartMaster", classes, objects);
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            QMProductManagerRB.SUCCESSFUL, null);
                    String message1 = QMMessage.getLocalizedMessage(RESOURCE,
                            QMProductManagerRB.UNSUCCESSFUL, null);
                    if((null != obj) && (obj instanceof QMPartMasterIfc))
                    {
                        DialogFactory.showFormattedInformDialog(this, message);
                        /**QMPartMasterIfc master = (QMPartMasterIfc) obj;
                         String masterBsoID = master.getBsoID();
                         HashMap hashmap = new HashMap();
                         hashmap.put("BsoID", masterBsoID);

                         //ת��ҳ��鿴�㲿������Ϣ�����ԡ�
                         RichToThinUtil.toWebPage(
                         "part_version_iterationsViewMain.screen",
                         hashmap);

                         master = null;
                         masterBsoID = null;
                         hashmap = null;*/
                    }
                    else
                    {
                        DialogFactory.showFormattedInformDialog(this, message1);
                    }
                    message1 = null;
                } //end if (null != selectObject)
            }
            selectObject = null;
        }
        catch (QMRemoteException e)
        {
            showMessage(e.getMessage(), exceptionTitle);
            e.printStackTrace();
        }
        catch (QMException e)
        {
            showMessage(e.getMessage(), exceptionTitle);
            e.printStackTrace();
        }
        finally
        {
            displayWaitIndicatorOff();
        }
    }

    /**
     * ����"���ù淶"���
     */
    public void processSetConfigSpecCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), SYSCFIG);
        work.start();
    }

    /**
     * ���ù淶��
     */
    protected void editConfigSpecItem()
    {
        new StructureConditionController(configSpecItem, this);
    }

    /**
     * ����"�鿴"���
     */
    public void processViewPartCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), VIEW);
        work.start();
    }

    /**
     * �鿴ע����ʷ��
     */
    public void viewCatalogHistory()
    {
        BaseValueIfc obj = (BaseValueIfc) getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        //�����ѡ��Ķ���ΪQMPartIfc��
        if(obj instanceof QMPartIfc)
        {
            String bsoID = obj.getBsoID();
            try
            {
                QMQuery query = new QMQuery("GenericVariantLink");
                QueryCondition cond = new QueryCondition("leftBsoID", "=",
                        bsoID);
                query.addCondition(cond);
                ServiceRequestInfo info = new ServiceRequestInfo();
                info.setServiceName("PersistService");
                info.setMethodName("findValueInfo");
                Class[] theClass = {QMQuery.class};
                info.setParaClasses(theClass);
                Object[] objs = {query};
                info.setParaValues(objs);
                RequestServer server = RequestServerFactory.getRequestServer();
                Collection links = (Collection) server.request(info);
                if(links == null || links.size() == 0)
                {
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            "noCatalog", null);
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "prompt", null);
                    showMessage(message, title);
                    return;
                }
                else
                {
                    Iterator iter = links.iterator();
                    String gpID = "";
                    while (iter.hasNext())
                    {
                        BinaryLinkIfc link = (BinaryLinkIfc) iter.next();
                        gpID = link.getRightBsoID();
                    }
                    HashMap hashmap = new HashMap();
                    hashmap.put("bsoID", gpID);
                    RichToThinUtil.toWebPage("GPart_Base_View.do", hashmap);
                }
            }
            catch (QMException e)
            {
                e.printStackTrace();
            }
            displayWaitIndicatorOff();
        }
    }

    /**
     * �鿴��
     */
    protected void viewSelectedObject()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "viewSelectedObject()..begin ....");
        BaseValueIfc obj = (BaseValueIfc) getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        //�����ѡ��Ķ���ΪQMPartIfc��
        if(obj instanceof QMPartIfc)
        {
            String bsoID = obj.getBsoID();
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
            displayWaitIndicatorOff();
        }
        else if(obj instanceof QMPartMasterIfc)
        {
            QMPartMasterIfc master = (QMPartMasterIfc) obj;
            String masterBsoID = master.getBsoID();
            ///////////////////////modify begin////////////////////////////
            //modify by muyp 20080507
            //�޸�ԭ�򣺶��㲿������Ϣ������в鿴��ҳ����ʾ����ȷ(TD1754)
          //CCBegin by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 TD2741    
            //Begin CR9
/*            ServiceRequestInfo info = new ServiceRequestInfo();
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
                    */
                    HashMap hashmap = new HashMap();
                    //modify by shf 2003/09/13
		            hashmap.put("BsoID", masterBsoID);;
//                    hashmap.put("bsoID", partID);
                    //ת��ҳ��鿴�㲿������Ϣ�����ԡ�
		            //modify by shf 2003/09/13
                    RichToThinUtil.toWebPage("part_version_iterationsViewMain-001.screen",
                    		hashmap);
                    master = null;
		            masterBsoID = null;
//                    partID = "";
                    hashmap = null;
        /*       }
			} 
   
    catch (QMRemoteException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}*/
                  //End CR9
                  //CCEnd by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 TD2741     
			//////////////////////modify end//////////////////////////
        }
    }

    /**
     * �Ӳ�Ʒ�ṹ���������ܽ����ȡ��ǰɸѡ������
     * effectibityActive  ��1��ɸѡ����Ϊ��Ч�ԣ���0����Ϊ��Ч�ԡ�
     * baselineActive     ��1��ɸѡ����Ϊ��׼�ߣ���0����Ϊ��׼�ߡ�
     * standardActive     ��1��ɸѡ����Ϊ��׼��  ��0����Ϊ��׼��
     * baselineID          ���ɸѡ����Ϊ��׼�ߣ���ʾ��׼�ߵ�bsoID��
     * configItemID        ���ɸѡ����Ϊ��Ч�ԣ���ʾ��Ч�Է�����bsoID��
     * viewObjectID        ���ɸѡ����Ϊ��Ч�Ի��׼����ʾ��ͼ��bsoID��
     * effectiveUnit       ���ɸѡ����Ϊ��Ч�ԣ���ʾ��Ч�Ե�ɸѡֵ��
     * effevtivityType     ���ɸѡ����Ϊ��Ч�ԣ���ʾ��Ч�����͡�
     * state               ���ɸѡ����Ϊ��׼����ʾ��������״̬��
     * workingIncluded     ���ɸѡ����Ϊ��׼����ʾ�Ƿ���������������Ƿ���Ա��������1����ʾ��������0����ʾ��������
     * @return HashMap ��������ʮ��������Ϣ�ļ��ϡ�
     */
    protected HashMap getInfoAboutConfigItem()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "getInfoAboutConfigItem()..begin ....");
        String effectivityActive = "0";
        String baselineActive = "0";
        String standardActive = "0";
        String baselineID = "";
        String configItemID = "";
        String viewObjectID = "";
        String effectiveUnit = "";
        String effectivityType = "";
        String state = "";
        String workingIncluded = "0";
        boolean flag4 = false;
        //���Ϊtrue��ɸѡ����Ϊ����Ч�ԡ���
        boolean flag1 = configSpecItem.getEffectivityActive();
        //���Ϊtrue��ɸѡ����Ϊ����׼�ߡ���
        boolean flag2 = configSpecItem.getBaselineActive();
        //���Ϊtrue��ɸѡ����Ϊ����׼����
        boolean flag3 = configSpecItem.getStandardActive();
        //���Ϊtrue��ɸѡ����Ϊ����׼��ʱ���ڼ���Ĺ�������ҲҪ��ʾ��
        if(flag3)
        {
            flag4 = configSpecItem.getCheckedOut();
        }
        //����Ϊ����Ч�ԡ�ɸѡ����ʱ��
        if(flag1)
        {
            effectivityActive = "1";
            //�����Ч�����͡�
            if(configSpecItem.getConfigSpecInfo().getEffectivity() == null)
            {
                effectivityType = "DATE";
            }
            else
            {
                //���������ɸѡ������ֵ��
                effectivityType = configSpecItem.getConfigSpecInfo()
                        .getEffectivity().getEffectivityType().toString();
            }
            effectiveUnit = configSpecItem.getConfigSpecInfo().getEffectivity()
                    .getEffectiveUnit();
            //���ɸѡ�����е���ͼ����
            ViewObjectIfc viewObject = null;
            if(null != configSpecItem)
            {
                viewObject = configSpecItem.getViewObjectIfc();
            }
            if(null == viewObject)
            {
                viewObjectID = "";
            }
            else if(PersistHelper.isPersistent(viewObject))
            {
                //�����Ч�Է�����
                viewObjectID = viewObject.getBsoID();
            }
            EffConfigurationItemIfc configItem = configSpecItem
                    .getEffConfigItemIfc();
            if(null != configItem)
            {
                //�����Ч�Է������ڣ������Ч�Է�����bsoID��
                if(PersistHelper.isPersistent(configItem))
                {
                    configItemID = configItem.getBsoID();
                }
            }
        }
        //����Ϊ����׼�ߡ�ɸѡ����ʱ��
        if(flag2)
        {
            baselineActive = "1";
            BaselineIfc baseline = configSpecItem.getBaselineIfc();
            //�����׼�ߴ��ڣ���ȡ��׼�ߵ�bsoID��
            if(PersistHelper.isPersistent(baseline))
            {
                baselineID = baseline.getBsoID();
            }
        } //end if(flag2)
        //����Ϊ����׼��ɸѡ����ʱ��
        if(flag3)
        {
            standardActive = "1";
            if(flag4)
            {
                workingIncluded = "1";
            }
            try
            {
                //���ɸѡ�����е���ͼ����
                ViewObjectIfc viewObject = configSpecItem.getViewObjectIfc();
                //�����ͼ���ڣ������ͼ��bsoID��
                if(null == viewObject)
                {
                    viewObjectID = "";
                }
                else if(PersistHelper.isPersistent(viewObject))
                {
                    //�����������״̬��
                    viewObjectID = viewObject.getBsoID();
                }
                LifeCycleState state2 = configSpecItem.getState();
                if(null == state2)
                {
                    state = "";
                }
                else
                {
                    state = state2.getDisplay();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                showMessage(e.getMessage(), exceptionTitle);
            }
        } //end if(flag3)
        //�����������Ӽ�¼��
        HashMap hashmap = new HashMap();
        hashmap.put("effectivityActive", effectivityActive);
        hashmap.put("baselineActive", baselineActive);
        hashmap.put("standardActive", standardActive);
        hashmap.put("baselineID", baselineID);
        hashmap.put("configItemID", configItemID);
        hashmap.put("viewObjectID", viewObjectID);
        hashmap.put("effectivityType", effectivityType);
        hashmap.put("effectiveUnit", effectiveUnit);
        hashmap.put("state", state);
        hashmap.put("workingIncluded", workingIncluded);
        //������λ��
        effectivityActive = null;
        baselineActive = null;
        standardActive = null;
        baselineID = null;
        configItemID = null;
        viewObjectID = null;
        effectivityType = null;
        effectiveUnit = null;
        state = null;
        workingIncluded = null;
        return hashmap;
    }

    /**
     * ����"�ṹ�Ƚ�"���
     */
    public void processCompareStructrueCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), COMPARE_STRUCTURE);
        work.start();
    }

    /**
     * �ṹ�Ƚϡ�
     */
    protected void compareStructure()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "compareStructure()..begin ....");
        QMPartIfc part = null;
        Object obj = getSelectedObject();

        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        //��ʾ��ѡ��Ķ������ʹ��󡣡���
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        QMPartMasterIfc partMaster = (QMPartMasterIfc) part.getMaster();
        new DifferenceCompareController(getConfigSpecItem(), part, partMaster);
        //������λ��
        part = null;
        obj = null;
        partMaster = null;
    }

    /**
     * �������ԱȽ����
     */
    public void processIbaCompareCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), IBA_COMPARE);
        work.start();
    }

    /**
     * �������ԱȽ����2005.4.20
     */
    protected void ibaCompare()
    {
        Object obj = getSelectedObject();
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(!(obj instanceof QMPartIfc))
        {
            showMessage(errorObject, exceptionTitle);
        }
        JFrame parentFrame = getParentFrame();
        String title = QMMessage.getLocalizedMessage(RESOURCE,
                QMProductManagerRB.GENENAL_SEARCH, null);
        QmChooser chooser = new QmChooser("QMPartMaster", title, parentFrame);
        chooser.setChildQuery(false);
        chooser.setRelColWidth(new int[]{1, 1});
        chooser.addListener(new QMQueryListener()
        {
            public void queryEvent(QMQueryEvent e)
            {
                if(e.getType().equals(QMQueryEvent.COMMAND))
                {
                    if(e.getCommand().equals(QmQuery.OkCMD))
                    {
                        frame.setConfigSpec.setEnabled(false);
                        QmChooser qmChooser = (QmChooser) e.getSource();
                        //���ѡ���������顣
                        BaseValueIfc[] objs = (BaseValueIfc[]) qmChooser
                                .getSelectedDetails();
                        QMPartMasterIfc[] partMasters = new QMPartMasterIfc[objs.length];
                        for (int i = 0, j = objs.length; i < j; i++)
                        {
                            QMPartMasterIfc part = (QMPartMasterIfc) objs[i];
                            partMasters[i] = part;
                        }
                        IBACompareThread thread = new IBACompareThread(
                                partMasters);
                        thread.start();
                    }
                }
            }
        });
        //�����������档
        chooser.setVisible(true);
        frame.setConfigSpec.setEnabled(true);
        obj = null;
        title = null;
    }

    /**
     * ���ԱȽ��̴߳���
     * <p>Title: </p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2005</p>
     * <p>Company: һ������</p>
     * @author л��
     * @version 1.0
     */
    class IBACompareThread extends QMThread
    {
        /**�㲿������Ϣ��ֵ��������*/
        private QMPartMasterIfc[] partMasters;

        /**
         * ���ԱȽϵ��̡߳�
         * @param partMaster QMPartMasterIfc[] �㲿������Ϣ��ֵ�������顣
         */
        public IBACompareThread(QMPartMasterIfc[] partMaster)
        {
            partMasters = partMaster;
        }

        /**
         * ִ�д������ԱȽϵķ�����
         */
        public void run()
        {
            processIbaCompare(partMasters);
        }
    }

    /**
     * �������ԱȽϡ�
     * @param masters QMPartMasterIfc[] ��Ҫ���бȽϵ��㲿����
     */
    private void processIbaCompare(QMPartMasterIfc[] masters)
    {
        Object obj = getSelectedObject();
        QMPartIfc sourcePart = (QMPartIfc) obj;
        if(null == getConfigSpecItem())
        {
            setConfigSpecCommand(true);
        }
        //������ù淶��Ϊ�ա�
        else
        {
            Hashtable table = null;
            try
            {
                //���PART���ϡ�
                table = PartHelper.getAllVersionsNow(masters,
                        getConfigSpecItem().getConfigSpecInfo());
            }
            catch (QMRemoteException e)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE, "warn",
                        null);
                JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(),
                        title, JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
                return;
            }
            if(null != table)
            {
                ArrayList part = (ArrayList) table.get("part");
                ArrayList partMaster = (ArrayList) table.get("partmaster");
                //���û�в����ϵ�PartMaster��
                if(0 == partMaster.size())
                {
                    String ids = sourcePart.getBsoID();
                    for (int n = 0; n < part.size(); n++)
                    {
                        ids = ids + ":" + ((QMPartIfc) part.get(n)).getBsoID();
                    }
                    HashMap hashmap = new HashMap();
                    hashmap.put("partIDs", ids);
                    RichToThinUtil.toWebPage("Part-Other-IBACompare.screen",
                            hashmap);
                    hashmap = null;
                    ids = null;
                }
                else if(0 == part.size())
                {
                    PartShowMasterDialog dialog = new PartShowMasterDialog(
                            partMaster, frame);
                    dialog.setSize(400, 300);
                    PartScreenParameter.centerWindow(dialog);
                }
                //����в����ϵ�PartMaster��
                else
                {
                    PartShowMasterDialog dialog = new PartShowMasterDialog(
                            partMaster, frame);
                    dialog.setSize(400, 300);
                    PartScreenParameter.centerWindow(dialog);
                    StringBuffer ids = new StringBuffer();
                    ids.append(sourcePart.getBsoID());
                    for (int n = 0, nn = part.size(); n < nn; n++)
                    {
                        ids.append(":" + ((QMPartIfc) part.get(n)).getBsoID());
                    }
                    HashMap hashmap = new HashMap();
                    hashmap.put("partIDs", ids);
                    RichToThinUtil.toWebPage("Part-Other-IBACompare.screen",
                            hashmap);
                    hashmap = null;
                    ids = null;
                }
            }
        } //end if (null == getConfigSpecItem()) else
    }

    /**
     * ����"��ͨ����"���
     */
    public void processGeneralSearchCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), GENERAL_SEARCH);
        work.start();
    }

    /**
     * <p>Title: ����㲿���̡߳�</p>
     * <p>Description: ���������㲿���߳��ࡣ</p>
     * <p>Copyright: Copyright (c) 2005</p>
     * <p>Company: </p>
     * @author not attributable
     * @version 1.0
     */
    class AddPartsThread extends QMThread
    {
        /**�㲿������Ϣ��ֵ��������*/
        private QMPartMasterIfc[] partMasters;

        /**
         * ����㲿�����̡߳�
         * @param partMaster QMPartMasterIfc[] �㲿������Ϣ��ֵ�������顣
         */
        public AddPartsThread(QMPartMasterIfc[] partMaster)
        {
            partMasters = partMaster;
        }

        /**
         * ��������㲿���ķ�����
         */
        public void run()
        {
            addPartMasters(partMasters);
        }
    }

    /**
     * ��ͨ������
     */
    //2003.09.10 lgz
    protected void general_search()
    {
        JFrame parentFrame = getParentFrame();
        String title = QMMessage.getLocalizedMessage(RESOURCE,
                QMProductManagerRB.GENENAL_SEARCH, null);
        QmChooser chooser = new QmChooser("QMPartMaster", title, parentFrame);
        chooser.setChildQuery(false);
        chooser.setRelColWidth(new int[]{1, 1});
        chooser.addListener(new QMQueryListener()
        {
            public void queryEvent(QMQueryEvent e)
            {
                if(e.getType().equals(QMQueryEvent.COMMAND))
                {
                    if(e.getCommand().equals(QmQuery.OkCMD))
                    {
                        frame.setConfigSpec.setEnabled(false);
                        QmChooser qmChooser = (QmChooser) e.getSource();
                        //���ѡ���������顣
                        BaseValueIfc[] objs = (BaseValueIfc[]) qmChooser
                                .getSelectedDetails();
                        QMPartMasterIfc[] partMasters = new QMPartMasterIfc[objs.length];
                        for (int i = 0, j = objs.length; i < j; i++)
                        {
                            QMPartMasterIfc part = (QMPartMasterIfc) objs[i];
                            partMasters[i] = part;
                        }
                        AddPartsThread thread = new AddPartsThread(partMasters);
                        thread.start();
                    }
                }
            }
        });
        //�����������档
        chooser.setVisible(true);
        title = null;
    }

    /**
     * ����"��������������"���
     */
    public void processBaseAttrSearchCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), BASE_ATTR_SEARCH);
        work.start();
    }

    /**
     * ����������������
     */
    protected void baseAttrSearch()
    {
        new BasicSearchController(getFrame());
    }

    /**
     * ����"��������������"���
     */
    public void processIBASearchCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), IBA_SEARCH);
        work.start();
    }

    /**
     * ����������������
     */
    protected void ibaSearch()
    {
        new IBASearchController(getFrame(), QMPartInfo.class.getName());
    }

    /**
     * ����"�ּ������嵥"���
     */
    public void processMaterialListCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), BUILD_GRADE_BOM);
        work.start();
    }

    /**
     * �ּ������嵥��
     */
    protected void buildSelectedGradeBOM()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "buildSelectedGradeBOM()..begin ....");
        HashMap hashmap = new HashMap();
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        String bsoID = part.getBsoID();
        hashmap.put("PartID", bsoID);
        //���C�ͻ��˴��ݲ������㲿����������ԣ����ڽ�����ʾ��
        hashmap.put("attributeName", attrName);
        //���C�ͻ��˴��ݲ������㲿����������ԣ����ڵ��÷���
        hashmap.put("attributeName1", attrEnglishName);
        RichToThinUtil.toWebPage("Part-Other-classifylisting-001-0A.do",
                hashmap);
        bsoID = null;
        hashmap = null;
        part = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "buildSelectedGradeBOM()..end ....");
        displayWaitIndicatorOff();
    }
 //CCbegin SS1
    
     /**
     * ����"erp�����嵥"���
     */
    public void processErpListCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), BUILD_ERP_BOM);
        work.start();
    }
    
    /**
     * �ּ������嵥��
     */
    protected void buildSelectedErpBOM()
    {
         PartDebug.trace(this, PartDebug.PART_CLIENT,
                "buildSelectedErpBOM()..begin ....");
        System.out.println("----buildSelectedErpBOM-----");
        HashMap hashmap = new HashMap();
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        String bsoID = part.getBsoID();
        hashmap.put("PartID", bsoID);
        System.out.println("erpAttrName="+erpAttrName);
        System.out.println("erpAttrEnglishName="+erpAttrEnglishName);
        //���C�ͻ��˴��ݲ������㲿����������ԣ����ڽ�����ʾ��
        hashmap.put("attributeName", erpAttrName);
        //���C�ͻ��˴��ݲ������㲿����������ԣ����ڵ��÷���
        hashmap.put("attributeName1", erpAttrEnglishName);
        RichToThinUtil.toWebPage("Part-ERP-classifylisting-001-0A.do",
                hashmap);
        bsoID = null;
        hashmap = null;
        part = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "buildSelectedErpBOM()..end ....");
        displayWaitIndicatorOff();
    }
    //CCend SS1
    
    /**
     * ����"ͳ�Ʊ������嵥"���
     */
    public void processBOMListCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), BUILD_STATISTIC_BOM);
        work.start();
    }

    /**
     * ͳ�Ʊ������嵥��
     */
    protected void buildSelectedStatisticBOM()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "buildSelectedStatisticBOM()..begin ....");
        HashMap hashmap = new HashMap();
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(errorObject, exceptionTitle);
        }
        String bsoID = part.getBsoID();
        hashmap.put("PartID", bsoID);
        //���C�ͻ��˴��ݲ������㲿����������ԣ����ڽ�����ʾ��
        hashmap.put("attributeName", attrName);
        //���C�ͻ��˴��ݲ������㲿����������ԣ����ڵ��÷���
        hashmap.put("attributeName1", attrEnglishName);
        hashmap.put("source", "");
        hashmap.put("type", "");
        RichToThinUtil
                .toWebPage("Part-Other-PartStatistics-001-0A.do", hashmap);
        bsoID = null;
        hashmap = null;
        part = null;
        obj = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "buildSelectedStatisticBOM()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * ����"���������嵥"���
     */
    public void processTailorBOMCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), TAILOR_BOM);
        work.start();
    }

    /**
     * ���������嵥��
     */
    protected void tailorBOM()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT, "tailorBOM()..begin ....");
        QMPartIfc part = null;
        Object obj = getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(exceptionTitle, exceptionTitle);
        }
        //CCBegin by liunan 2008-08-05
        //��Ϊ���ô�·�ߵĶ��ƽ��棬ԭ��������
        //new MaterialController(part);
        new MaterialController(part,false);
        //CCBegin by liunan 2008-08-05
        PartDebug.trace(this, PartDebug.PART_CLIENT, "tailorBOM()..end ....");
        displayWaitIndicatorOff();
    }

    //add by liun 2005.3.9
    /**
     * ����"���ƹ��岿�������嵥"���
     */
    public void processGPBomListCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), GP_BOM_LIST);
        work.start();
    }

    /**
     * ���ƹ��岿�������嵥��
     */
    protected void gPBomList()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT, "gPBomList()..begin ....");
        QMPartIfc gPart = null;
        Object obj = getSelectedObject();
        //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        if(((QMPartIfc) obj).getBsoName().equals("GenericPart"))
        {
            gPart = (QMPartIfc) obj;
        }
        //��ʾ��ѡ��Ķ������ʹ���,��ѡ��һ�����岿��������
        else
        {
            showMessage(noSelectGPart, exceptionTitle);
            return;
        }
        //��hashmap��part���������ƹ��岿�������嵥��ҳ�档
        GPartBOM newBOM = new GPartBOM(gPart.getBsoID());
        newBOM.setVisible(true);
        gPart = null;
        obj = null;
        PartDebug.trace(this, PartDebug.PART_CLIENT, "gPBomList()..end ....");
        displayWaitIndicatorOff();
    }

    /**
     * ����"��Ʒ�ṹ����"���
     */
    public void processProductManagerCommand()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT, "---- ��Ʒ�ṹ������ ----");
        RichToThinUtil.toWebPage("help/zh_cn/phosphorhelp_zh_CN.jsp",
                "?index=part/PIMOverView.html");
    }

    /**
     * ����"����"���
     */
    public void processAboutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), ABOUT);
        work.start();
    }

    /**
     * ���ڡ�
     */
    protected void about()
    {
    	String title=QMMessage.getLocalizedMessage(RESOURCE,
                "ProductionManager", null);
    	IntroduceDialog introduceFrame = new IntroduceDialog(this.getFrame(),title);
    	introduceFrame.setVisible();
    }

    //  added by whj
    public void processCopyCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), COPY);
        work.start();
    }

    /**
     *�����Ƶ����ճ����ѡ�е��㲿������
     */
    public void processPasteCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), PASTE);
        work.start();
    }

    /**
     * ����ǰ����ӵ�ǰ����ĸ������Ƴ�
     */
    public void processCutCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), CUT);
        work.start();
    }

    /**
     * ����ǰ����ӵ�ǰ����ĸ������Ƴ�
     */
    public void prodessDeleteStruCommand()
    {
        WorkThread work = new WorkThread(getThreadGroup(), PUBLIC_SORT);
        work.start();
    }

    //whj
    /**
     * ������С�
     */
    protected void clearAll()
    {
        myExplorer.clear();
    }

    /**
     * ��Ӽ�������
     * @param qmexplorerlistener QMExplorerListener ���������������
     */
    public synchronized void addListener(QMExplorerListener qmexplorerlistener)
    {
        myListener.addElement(qmexplorerlistener);
    }

    /**
     * �Ƴ���������
     * @param qmexplorerlistener QMExplorerListener ���������������
     */
    public synchronized void removeListener(
            QMExplorerListener qmexplorerlistener)
    {
        myListener.removeElement(qmexplorerlistener);
    }

    /**
     * ���ItemListener��������
     * @param l ItemListener Item������
     */
    public void addItemListener(ItemListener l)
    {
        itemListener = l;
    }

    /**
     * �Ƴ�ItemListener��������
     * @param l ItemListener Item������
     */
    public synchronized void removeItemListener(ItemListener l)
    {
        itemListener = AWTEventMulticaster.remove(itemListener, l);
    }

    /**
     * ����Item�¼���
     * @param e ItemEvent Item�¼���
     */
    protected void processItemEvent(ItemEvent e)
    {
        if(itemListener != null)
        {
            itemListener.itemStateChanged(e);
        }
    }

    /**
     * ������¼���
     * @param e QMExplorerEvent ����������¼���
     */
    public void explorerEvent(QMExplorerEvent e)
    {
        if(e.getType().equals(QMExplorerEvent.COMMAND))
        {
            processCommand(e);
        }
        else if(e.getType().equals(QMExplorerEvent.OPEN))
        {
            handleDoubleClickEvent(e);
        }
        else if(e.getType().equals(QMExplorerEvent.SELECT))
        {
            handleSelectEvent(e);
        }
        else if(e.getType().equals(QMExplorerEvent.EXPOSE))
        {
            handleSelectEvent(e);
        }
        else if(e.getType().equals(QMExplorerEvent.DESELECT))
        {
            handleDeselectEvent(e);
        }
    }

    /**
     * ����ѡȡ�¼���
     * @param e QMExplorerEvent  ����������¼���
     */
    protected void handleSelectEvent(QMExplorerEvent e)
    {
        ItemEvent itemEvent = new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED,
                null, ItemEvent.SELECTED);
        processItemEvent(itemEvent);
    }

    /**
     * ����ȡ��ѡȡ�¼���
     * @param e QMExplorerEvent  ����������¼���
     */
    protected void handleDeselectEvent(QMExplorerEvent e)
    {
        ItemEvent itemEvent = new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED,
                null, ItemEvent.DESELECTED);
        processItemEvent(itemEvent);
    }

    /**
     * �������˫���¼�����ʾ���������ҳ�档
     * @param e QMExplorerEvent  ����������¼���
     */
    protected void handleDoubleClickEvent(QMExplorerEvent e)
    {
        processViewPartCommand();
        displayWaitIndicatorOff();
    }

    /**
     * �����Ʒ�ṹ������������.���û�����������ϵİ�ťʱ�ᷢ��һ������,��������
     * �밴ťͼƬ������һ����
     * @param e QMExplorerEvent  ����������¼���
     */
    protected void processCommand(QMExplorerEvent e)
    {
        PartDebug
                .trace(this, PartDebug.PART_CLIENT, "processCommand()...begin");
        if(e.getCommand().equals("part_create"))
        {
            PartDebug.trace(this, PartDebug.PART_CLIENT,
                    "processComand.create begin ....");
            processNewCommand();
            PartDebug.trace(this, PartDebug.PART_CLIENT,
                    "processComand.create end ....");
        }
        else if(e.getCommand().equals("part_update"))
        {
            processEditCommand();
        }
        else if(e.getCommand().equals("part_delete"))
        {
            processDeletePartCommand();
        }
        else if(e.getCommand().equals("part_view"))
        {
            processViewPartCommand();
        }
        else if(e.getCommand().equals("part_checkIn"))
        {
            processCheckInCommand();
        }
        else if(e.getCommand().equals("part_checkOut"))
        {
            processCheckOutCommand();
        }
        else if(e.getCommand().equals("part_repeal"))
        {
            processUndoCheckOutCommand();
        }
        else if(e.getCommand().equals("public_refresh"))
        {
            processRefreshCommand();
        }
        else if(e.getCommand().equals("public_clear"))
        {
            processClearCommand();
        }
        else if(e.getCommand().equals("public_search"))
        {
            processGeneralSearchCommand();
        }
        else if(e.getCommand().equals("public_copy"))
        {
            processCopyCommand();
        }
        else if(e.getCommand().equals("public_paste"))
        {
            processPasteCommand();
        }
        else if(e.getCommand().equals("public_cut"))
        {
            processCutCommand();
        }
        else if(e.getCommand().equals("public_sort"))
        {
            prodessDeleteStruCommand();
        }
        //liyz add Ӧ�����е�ͼ�갴ť
        else if(e.getCommand().equals("part_applyAll"))
        {
        	processAddAttrCommand();
        }
        //end
        displayWaitIndicatorOff();
        PartDebug.trace(this, PartDebug.PART_CLIENT, "processCommand()...end");
    }

    /**
     * ��ȡJApplet��
     * @return JApplet ��ǰapplet��
     */
    public JApplet getApplet()
    {
        return applet;
    }

    /**
     * ����JApplet��
     * @param applet JApplet ��ǰapplet��
     */
    public void setApplet(JApplet applet)
    {
        this.applet = applet;
        QMCt.setContext(applet);
    }
//CCBegin SS2
     /**
     * ��ѡ������нṹ���ơ� 
     */
    public void Allcopy()
    {
    	if(myTree.getSelected()!=null)
    	{
//    		System.out.println("partIfc1.getBsoID()partIfc1.getBsoID()====="+partIfc1.getBsoID());
//    		if(myTree.getSelected()== myExplorer.getRootNode())
//    		{
    			iscopy = true;
    	        //ѡ��Ķ��󡣿�����������ڵ㣬Ҳ�������ұ��б��еĶ���
    	        Object selectObject = getSelectedObject();
    	        //�㲿��������ϢBsoID��
    	        String masterID = null;
    	        if(selectObject instanceof QMPartIfc)
    	        {
    	        	String masterID1 = null;
    	            if(selectObject instanceof QMPartIfc)
    	            {
    	            	allAchePartMasterIfc = (QMPartMasterIfc) ((QMPartIfc) selectObject).getMaster();
    	                masterID1 = allAchePartMasterIfc.getBsoID();
    	            }
    	            else if(selectObject instanceof QMPartMasterIfc)
    	            {
    	                masterID1 = ((QMPartMasterIfc) selectObject).getBsoID();
    	                allAchePartMasterIfc = (QMPartMasterIfc) selectObject;
    	            }
    	            //��������㲿����ֱ�ӻ�ȡ������ڵ㡣
    	            if(masterID1 == null || masterID1.equals(""))
    	            {
    	            	allAchePartMasterIfc = (QMPartMasterIfc) ((QMPartIfc) myTree.getSelected()
    	                        .getObj().getObject()).getMaster();
    	            }
    	        	QMPartIfc partIfc1=(QMPartIfc) selectObject;
    	        	System.out.println("partIfc1.getBsoID()partIfc1.getBsoID()====="+partIfc1.getBsoID());
    	        	Class[] classes = {
    	        			QMPartIfc.class, PartConfigSpecIfc.class};
    	                Object[] objs = {
    	                		partIfc1, null};
    	                try
                        {
    	                Collection col = (Collection) IBAUtility
                        .invokeServiceMethod(
    	                    "StandardPartService", "getUsesPartIfcs", classes,
    	                    objs);
    	                System.out.println("colcolcolcolcol"+col.size());
    	                Iterator iter = col.iterator();
    	                while (iter.hasNext()) {
    	                	Object[] subPartList;
    	                	
    	                	subPartList=(Object[])iter.next();
    	                	
//    	                	if (subPartList != null) {
//    	                        for (int i = 0; i < col.size(); i++) {
    	                        	PartUsageLinkIfc  cachePartUsageLinkIfc1  =  (PartUsageLinkIfc)subPartList[0];
    	                        	System.out.println("cachePartUsageLinkIfc1====="+cachePartUsageLinkIfc1.getBsoID());
    	                        	vcachePartUsageLinkIfc.add(cachePartUsageLinkIfc1);
//    	                          }
    	                        
    	                      }
    	                   
    	                
    	                
                        }
    	                catch (QMRemoteException e)
                        {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(getFrame(), e
                                    .getClientMessage(), resourcebundle
                                    .getString("worn"),
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
    	        	
    	        }
    		}
    		else
    		{
    			JOptionPane.showMessageDialog(getFrame(), "���˸��ڵ㲻�ܽṹ���ƣ�", resourcebundle
                       .getString("worn"), JOptionPane.ERROR_MESSAGE);
   		}
    	}
        
    /**
     * ճ���㲿����Ϊ����ճ��������ճ���Ͳ���ճ����
     */
    public void Allpaste()
    {
    	System.out.println("0000000001");
        if(vcachePartUsageLinkIfc.size() == 0)
        {
            JOptionPane.showMessageDialog(getFrame(), resourcebundle
                    .getString("notselectpart"), resourcebundle
                    .getString("worn"), JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        System.out.println("0000000002");
        //�õ�Ŀ���㲿��
        //�õ�Ŀ���㲿��
        Object obj=myTree.getSelected().getObj().getObject();
        System.out.println("obj="+obj);
        if(obj instanceof QMPartMasterIfc)
        {
        	 System.out.println("0000000003");
        	 JOptionPane.showMessageDialog(myExplorer.getManager(), "Ŀ���"+((QMPartMasterInfo)obj).getPartNumber()+"���������ù淶�����ܽ����ṹ��", "����",
                     JOptionPane.INFORMATION_MESSAGE);
        	 return;
        }
        else
        	if(obj instanceof QMPartIfc)
        {
        QMPartIfc part = (QMPartInfo) obj;
        System.out.println("0000000004");
        //Ŀ����Ƿ��ǿɸ���״̬
        boolean isall = helper.isAllowUpdate(part);
        if(isall)
        {
        	for (int i = 0; i < vcachePartUsageLinkIfc.size(); i++){
        		PartUsageLinkIfc all=(PartUsageLinkIfc)vcachePartUsageLinkIfc.get(i);
          boolean isparentpart = false;
          try {
            isparentpart = isParentPart(part, allAchePartMasterIfc);

          }
          catch (QMException e) {
            e.printStackTrace();
            return;
          }
          System.out.println("isparentpart"+isparentpart);
          if (isparentpart) {
            DisplayIdentity displayidentity = IdentityFactory
                .getDisplayIdentity(allAchePartMasterIfc);
            //��ö������� + ��ʶ
            String s = displayidentity.getLocalizedMessage(null);
            Object[] params = {
                s};
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                "nestingwaringtext", params);
            String title = resourcebundle.getString("nestingwaringtitle");
            JOptionPane.showMessageDialog(getParentFrame(), message, title,
                                          JOptionPane.WARNING_MESSAGE);
            displayWaitIndicatorOff();
            return;

          }
          else {
            PartUsageLinkIfc link = new PartUsageLinkInfo();
            link.setRightBsoID(part.getBsoID());
            link.setLeftBsoID(all.getLeftBsoID());
            System.out.println("1111111");
            if (iscopy) {
            	 System.out.println("all="+all);
              if (all != null) {
              	//�޸�4 20080225 zhnagq begin
                link.setQuantity(all.getQuantity());
                link.setDefaultUnit(all.getDefaultUnit());
                System.out.println("222222222222");
              }
              else {
                link.setQuantity(1);
                link.setDefaultUnit(Unit.getUnitDefault());
              }
              try {
                PartHelper.savePartUsageLink(link);
              }
              catch (Exception exc) {
                exc.printStackTrace();
              }
            }
            else {            
              link.setQuantity(bmovenum);
              if (all != null) {
                link.setDefaultUnit(all.getDefaultUnit());
              }
              else {
                link.setDefaultUnit(Unit.getUnitDefault());
              }
              try {
                PartHelper.savePartUsageLink(link);
              }
              catch (Exception exc) {
                exc.printStackTrace();
              }
              sourcePartMaster = null;
//              cachePartUsageLinkIfc = null;
            }
            Explorable newObj = new PartItem(part);
            ( (PartItem) newObj).setConfigSpecItem(getConfigSpecItem());
            //���ýڵ�ͼ�ꡣ
            ( (PartItem) newObj).setIcon();
            myExplorer.refreshNode(myTree.getSelected(), newObj, true, false);
          }
        }
        }
       
        //Ŀ���㲿�����ǿɸ���״̬��
        else
        {
            JOptionPane.showMessageDialog(getFrame(), resourcebundle
                    .getString("noupdate"), resourcebundle.getString("worn"),
                    JOptionPane.INFORMATION_MESSAGE);
        }
        vcachePartUsageLinkIfc.clear();
    }
    }
//CCEnd SS2
    /**
     * ��ü����Ķ���,�������ɹ�,���ؼ����Ķ���,������ʧ��,����null��
     * @param workable WorkableIfc Workableֵ����
     * @return WorkableIfc Workableֵ����
     */
    protected WorkableIfc getCheckOutObject(WorkableIfc workable)
    throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "getCheckOutObject()..begin ....");
        try
        {
            CheckOutTask checkout_task = new CheckOutTask(getParentFrame(),
                    CheckInOutTaskLogic.getCheckoutFolder());
            checkout_task.setCheckoutItem(workable);
            workable = checkout_task.checkout();
        }
        catch (AlreadyCheckedOutException acoe)
        {
            try
            {
                FolderIfc checkout_folder = CheckInOutTaskLogic
                        .getCheckoutFolder(workable);
                String folder_name = "";
                if(null != checkout_folder)
                {
                    Class[] class1 = {FolderBasedIfc.class};
                    Object[] paraClass = {(FolderBasedIfc) checkout_folder};
                    folder_name = (String) IBAUtility.invokeServiceMethod(
                            "FolderService", "getPath", class1, paraClass);
                    if(workable instanceof QMPartIfc)
                    {
                        Object[] param = {folder_name};
                        String message = QMMessage.getLocalizedMessage(
                                RESOURCE, QMProductManagerRB.AIREADY_CHECKOUT,
                                param);
                        showMessage(message, exceptionTitle);
                        message = null;
                    }
                }
            }
            catch (QMRemoteException e)
            {
                e.printStackTrace();
            }
            catch (CheckInOutException e)
            {
                e.printStackTrace();
            }
            catch (QMException e)
            {
                e.printStackTrace();
            }
            workable = null;
            acoe.printStackTrace();
        }
        //2003/12/15 ���쳣��Ϣ�� workable = null;
        catch (CheckedOutByOtherException coboe)
        {
            workable = null;
            coboe.printStackTrace();
            showMessage(coboe.getClientMessage(), exceptionTitle);
        }
        catch (ObjectNoLongerExistsException onle)
        {
            onle.printStackTrace();
            workable = null;
        }

        return workable;
    }

    /**
     * �ж϶����Ƿ�����ɾ����
     * @param part QMPartIfc �����жϵ��㲿��ֵ����
     * @return boolean ����true������ɾ������������
     */
    protected boolean isDeleteAllowed(QMPartIfc part)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "isDeleteAllowed()..begin ....");
        boolean allowed = false;
        try
        {
            if((CheckInOutTaskLogic.isCheckedOut(part))
                    && (!CheckInOutTaskLogic.isWorkingCopy(part)))
            {
                //Object[] params =
                //        {part.getPartName()};
                String username = "";
                UserIfc qmprincipal = CheckInOutTaskLogic.getCheckedOutBy(part);
                if(qmprincipal != null)
                {
                    username = qmprincipal.getUsersDesc();
                }
                if(username.trim().length()==0)
                {
                	/*�ж϶����Ƿ��ǹ���������*/
                    boolean flag1 = WorkInProgressHelper
                            .isWorkingCopy((WorkableIfc)part);
                    if(flag1)
                    {
                    	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
                    }
                }
                Object[] aobj = {username};
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        QMProductManagerRB.CANNOT_DELETE_CHECKED_OUT, aobj);
                showMessage(message, exceptionTitle);
                message = null;
            }
            else
            {
                allowed = true;
            }
        }
        catch (QMException e)
        {
            e.printStackTrace();
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "isDeleteAllowed()..end....");
        return allowed;
    }

    /**
     * �����Ʒ�ṹ������������.���û�����������ϵİ�ťʱ�ᷢ��һ������,��������
     * �밴ťͼƬ������һ����
     * @param actionevent ActionEvent �����Ļ�¼���
     */
    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getActionCommand().equals("create"))
        {
            processNewCommand();
        }
        else if(actionevent.getActionCommand().equals("update"))
        {
            processEditCommand();
        }
        else if(actionevent.getActionCommand().equals("delete"))
        {
            processDeletePartCommand();
        }
        else if(actionevent.getActionCommand().equals("viewprt"))
        {
            processViewPartCommand();
        }
        else if(actionevent.getActionCommand().equals("checkin"))
        {
            processCheckInCommand();
        }
        else if(actionevent.getActionCommand().equals("checkout"))
        {
            processCheckOutCommand();
        }
        else if(actionevent.getActionCommand().equals("undocheckout"))
        {
            processUndoCheckOutCommand();
        }
        else if(actionevent.getActionCommand().equals("refresh"))
        {
            processRefreshCommand();
        }
        else if(actionevent.getActionCommand().equals("clear"))
        {
            processClearCommand();
        }
        else if(actionevent.getActionCommand().equals("gsearch"))
        {
            processGeneralSearchCommand();
        }
        displayWaitIndicatorOff();
    }

    /**
     * ֵ�ı䡣
     * @param e TreeSelectionEvent ����ѡ���¼���
     */
    public void valueChanged(TreeSelectionEvent e)
    {
        itemListener.itemStateChanged(null);
    }

    /**
     * ���ݵ�ǰ���õ����ù淶ˢ������
     * �˷����� StructConditionController���á�
     */
    public void processNewConfigSpec()
    {
        //2003/09/22 lgz
        synchronized (lock)
        {
//            myExplorer.setIsCheckin(false);
//            if(myExplorer.getSelectedNode()!=null) {
//                myExplorer.checkChange(myExplorer
//                        .getSelectedNode());
//            }
            boolean isChanged = myExplorer.isChange();
            boolean isSaved = false;
            if(isChanged)
            {
                isSaved = myExplorer.saveChange();
            }
            if(!isSaved && isChanged)
            {
                refreshPart(myExplorer.getPartItem().getPart());
            }

            //��ø��ڵ㡣
            QMNode root = myExplorer.getRootNode();
            Vector children = new Vector();
            //����ӽڵ㣬������һ���ڵ�Ķ�����ӵ�Vector�С�
            for (QMNode n = root.getC(); null != n; n = n.getS())
            {
                Explorable e = n.getObj();
                children.addElement(e.getObject());
            }
            partMap=new HashMap();
            QMPartMasterIfc[] masters = new QMPartMasterIfc[children.size()];
            for (int i = 0, j = children.size(); i < j; i++)
            {
                //����� masterֱ�Ӽ��뵽���顣
                if(children.elementAt(i) instanceof QMPartMasterIfc)
                {
                    masters[i] = (QMPartMasterIfc) children.elementAt(i);
                }
                //�����part�����master�������顣
                else if((children.elementAt(i)) instanceof QMPartIfc)
                {
                    QMPartIfc part = (QMPartIfc) children.elementAt(i);
                    masters[i] = (QMPartMasterIfc) part.getMaster();
                    partMap.put(part.getMasterBsoID()+part.getVersionID(), 
                            part.getMaster());
                }
                else
                {
                    return;
                }
            }
            myExplorer.clear();
            addPartMasters(masters,false);
            myExplorer.getPartTaskJPanel().clear();//CR7
//            myExplorer.setIsCheckin(true);
        }
    }

    /**
     * ˢ�¹������е��㲿����
     * @param modifiedPart QMPartIfc ��ˢ�µ��㲿����
     */
    protected void refreshExplorerPart(QMPartIfc modifiedPart)
    {
        WorkThread work = new WorkThread(getThreadGroup(), REFRESHPART,
                modifiedPart);
        work.start();
        try
        {
            work.join();
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }
    
    //CR4 Begin 20090616 zhangq �޸�ԭ�򣺼����㲿����ʾ��Ϣ����
//    /**
//     * ˢ�¹������е��㲿����
//     * @param modifiedPart QMPartIfc ��ˢ�µ��㲿����
//     */
//    protected void refreshExplorerPart(Vector parts)
//    {
//    	refreshPart(parts);
////        WorkThread work = new WorkThread(getThreadGroup(), REFRESHSOMEPART,
////        		parts);
////        work.start();
////        try
////        {
////            work.join();
////        }
////        catch (InterruptedException ex)
////        {
////            ex.printStackTrace();
////        }
//    }
    //CR4 End 20090616 zhangq
    
    /**
     * �ڲ�Ʒ�������Ͻ��޸ĵ��㲿��ˢ�¡�
     * @param partVec Vector ���޸ĵ��㲿����
     */
    public void refreshPart(Vector partVec) {
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"------- ˢ�±��޸ĵ��㲿����refreshPart(part)");

		if (null == partVec||partVec.size()==0) {
			return;
		}

		QMNode currentNode = null;
		QMNode refreshNode = null;
		QMPartIfc modifiedPart=(QMPartIfc)partVec.get(0);
		QMPartIfc workCopyIfc=(QMPartIfc)partVec.get(1);
		Explorable busobj = new PartItem(modifiedPart);
		((PartItem) busobj).setConfigSpecItem(getConfigSpecItem());
		QMNode[] matchingNodes = myExplorer.findNodes(busobj);
		for (int i = 0, j = matchingNodes.length; i < j; i++) {

			refreshNode = matchingNodes[i];
			currentNode = myExplorer.getSelectedNode();
			if ((null != currentNode) && (currentNode.equals(refreshNode))) {

				// ���ýڵ�ͼ�ꡣ
				myExplorer.refreshNode(refreshNode, busobj, true, true);
			} else {

				// ���ýڵ�ͼ�ꡣ
				myExplorer.refreshNode(refreshNode, busobj, false, true);
			}
			if (refreshNode.getLevel() > 1) {

				refreshCache(((QMNode) refreshNode.getParent()), busobj);
			}
		}

				QMPartIfc part = (QMPartIfc) workCopyIfc;
				if (!modifiedPart.getPartNumber().equals(part.getPartNumber()))
					return;
				Explorable busobj1 = new PartItem(part);
				((PartItem) busobj1).setConfigSpecItem(getConfigSpecItem());
				QMNode[] copymatchingNodes = myExplorer.findNodes(busobj1);
				for (int i = 0, j = copymatchingNodes.length; i < j; i++) {

					refreshNode = copymatchingNodes[i];
					currentNode = myExplorer.getSelectedNode();
					if ((null != currentNode)
							&& (currentNode.equals(refreshNode))) {

						// ���ýڵ�ͼ�ꡣ
						myExplorer.refreshNode(refreshNode, busobj, true, true);
					} else {

						// ���ýڵ�ͼ�ꡣ
						myExplorer.refreshNode(refreshNode, busobj, true, true);
					}
					if (refreshNode.getLevel() > 1) {

						refreshCache(((QMNode) refreshNode.getP()), busobj,
								busobj1);
					}
				}
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"------- ˢ�±��޸ĵ��㲿��������refreshPart(part)------end()");
	}

    /**
     * �ӹ�������ɾ���㲿����
     * @param deletedPart QMPartIfc ��ɾ�����㲿����
     */
    protected void deleteExplorerPart(QMPartIfc deletedPart)
    {
        WorkThread work = new WorkThread(getThreadGroup(), DELETEPART,
                deletedPart);
        work.start();
        try
        {
            work.join();
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * ��ʾ������Ϣ��
     * @param message String ��ʾ�Ĵ�����Ϣ��
     * @param title String ��Ϣ��ı��⡣
     */
    public void showMessage(String message, String title)
    {
        JOptionPane.showMessageDialog(frame, message, title,
                JOptionPane.WARNING_MESSAGE);
        
    }

    /**
     * ��������ָ���������ڡ����
     */
    public void processResetLifeCycleCommand()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "processResetLifeCycleCommand()---begin");
        Object obj = getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        QMPartIfc part;
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(exceptionTitle, exceptionTitle);
            return;
        }
        LifeCycleStateDialog lifeCycleStateDialog = new LifeCycleStateDialog(
                frame, "", false);
        String str = part.getPartNumber() + "  " + part.getPartName();
        lifeCycleStateDialog.setName(str);
        lifeCycleStateDialog.setLifeCycleManaged(part);
        PartScreenParameter.centerWindow(lifeCycleStateDialog);
        str = null;
        obj = null;
        part = null;
    }

    /**
     * ����������������״̬�����
     */
    public void processSetLifeCycleStateCommand()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "processSetLifeCycleStateCommand()---begin");
        Object obj = getSelectedObject();
        PartDebug.trace(PartDebug.PART_CLIENT,
                "----------- selected object is :" + obj);
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        QMPartIfc part;
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(exceptionTitle, exceptionTitle);
            return;
        }
        try
        {
            SetLifeCycleStateDialog dialog = new SetLifeCycleStateDialog(frame,
                    part);
            String identity = part.getPartNumber() + "  " + part.getPartName();
            dialog.setName(identity);
            dialog.show();
            identity = null;
        }
        catch (QMException e)
        {
            //modify by shf 2003/09/13
            String message = e.getClientMessage();
            showMessage(message, exceptionTitle);
            message = null;
            e.printStackTrace();
        }
        obj = null;
        part = null;
    }

    /**
     * �����鿴����������ʷ��¼�����
     */
    public void processShowLifeCycleHistoryCommand()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "processShowLifeCycleHistoryCommand()---begin");
        Object obj = getSelectedObject();
        if(null == obj)
        {
            showMessage(noSelectObj, exceptionTitle);
            return;
        }
        QMPartIfc part;
        if(obj instanceof QMPartIfc)
        {
            part = (QMPartIfc) obj;
        }
        else
        {
            showMessage(exceptionTitle, exceptionTitle);
            return;
        }
        String partBsoID = part.getBsoID();
        HashMap hashmap = new HashMap();
        hashmap.put("bsoID", partBsoID);
        //2003/12/13
        hashmap.put("partName", part.getPartName());
        hashmap.put("partNumber", part.getPartNumber());
        hashmap.put("mpage", "shengminglishi");
        RichToThinUtil.toWebPage("Part_Look_LifeCycleHistory.screen", hashmap);
        partBsoID = null;
        hashmap = null;
        part = null;
        obj = null;
    }

    /**
     * ��ȡ����
     * @return QMProductManagerJFrame
     */
    public QMProductManagerJFrame getFrame()
    {
        return frame;
    }

    /**
     * ��ѡ������нṹ���ơ�
     */
    public void copy()
    {
    	//add whj 20070612
    	if(myTree.getSelected()!=null)
    	{
    		if(myTree.getSelected()== myExplorer.getRootNode())
    		{
    			JOptionPane.showMessageDialog(getFrame(), "���ڵ㲻�ܸ��ƣ�", resourcebundle
                        .getString("warn"), JOptionPane.ERROR_MESSAGE);
    		}
    		else
    		{
        iscopy = true;
        //ѡ��Ķ��󡣿�����������ڵ㣬Ҳ�������ұ��б��еĶ���
        //CCBegin SS10
        Object[] selectObject = getSelectedObjects();
//      Object selectObject = getSelectedObject();
        //CCEnd SS10
        //�㲿��������ϢBsoID��
        String masterID = null;
        //CCBegin SS10
        cachePartMasterVec.clear();
        if(null != selectObject && selectObject.length!=0)
        {
        	for(int i=0;i<selectObject.length;i++)
        	{
//        		 if(selectObject instanceof QMPartIfc)
        			 if(selectObject[i] instanceof QMPartIfc)
        //CCEnd SS10
        	        {
        	            cachePartMasterIfc = (QMPartMasterIfc) ((QMPartIfc) selectObject[i]).getMaster();
        	            //CCBegin SS10
        	            cachePartMasterVec.add(cachePartMasterIfc);
        	            //CCEnd SS10
        	            masterID = cachePartMasterIfc.getBsoID();
        	        }
        			//CCBegin SS10
//         	        else if(selectObject instanceof QMPartMasterIfc)
     	        	else if(selectObject[i] instanceof QMPartMasterIfc)
        		    //CCEnd SS10
        	        {
        	            masterID = ((QMPartMasterIfc) selectObject[i]).getBsoID();
        	            cachePartMasterIfc = (QMPartMasterIfc) selectObject[i];
        	        }
        			   //��������㲿����ֱ�ӻ�ȡ������ڵ㡣
        		        if(masterID == null || masterID.equals(""))
        		        {
        		            cachePartMasterIfc = (QMPartMasterIfc) ((QMPartIfc) myTree.getSelected()
        		                    .getObj().getObject()).getMaster();
        		        }
        		        Explorable explorable = myTree.getSelected().getObj();
        		        if(myTree.getSelected().getP() != myExplorer.getRootNode())
        		        {
        		            if(explorable instanceof UsageMasterItem)
        		            {
        		                cachePartUsageLinkIfc = ((UsageMasterItem) explorable).getPartUsageLink();
        		            }
        		            if(explorable instanceof UsageItem)
        		            {
        		                cachePartUsageLinkIfc = ((UsageItem) explorable).getPartUsageLink();
        		            }
        		        }
        		        else
        		        {
        		            cachePartUsageLinkIfc = null;
        		        }
        		        //CCBegin SS10
        	   }
    	}	 
        		        //CCEnd SS10
        	}

        }//end
    	else
    	{
    		JOptionPane.showMessageDialog(getFrame(), "û��ѡ��Ҫ���Ƶ����ڵ㣡", resourcebundle
                    .getString("warn"), JOptionPane.ERROR_MESSAGE);
    	}
    }

    /**
     * �����㲿����
     */
    public void cut()
    {
        //ѡ������ڵ���ڸ��ڵ㡣
    	if(myTree.getSelected()!=null)
    	{
    		if(myTree.getSelected()== myExplorer.getRootNode())
    		{
    			JOptionPane.showMessageDialog(getFrame(), "���ڵ㲻�ܼ��У�", resourcebundle
                        .getString("warn"), JOptionPane.ERROR_MESSAGE);
    		}
    		else

        if(myTree.getSelected().getP() != myExplorer.getRootNode())
        {
        	 QMPartInfo part=null;
        	Object obj=myTree.getSelected().getP().getObj()
            .getObject();
        	if(obj instanceof QMPartInfo)
        	{
        		part=(QMPartInfo)obj;
        	}
        	else
        		if((((QMNode)myTree.getSelected().getParent()).getP().getObj().getObject()) instanceof QMPartIfc)
    			{
    				part=(QMPartInfo)((QMNode)myTree.getSelected().getParent()).getP().getObj().getObject();
    			}
            if(helper.isAllowUpdate(part))
            {
                iscopy = true;
//              ѡ��Ķ��󡣿�����������ڵ㣬Ҳ�������ұ��б��еĶ���
                Object selectObject = getSelectedObject();
//              �㲿��������ϢBsoID��
                String masterID = null;
                //CCBegin SS10
	            cachePartMasterVec.clear();
	            //CCEnd SS10
                if(selectObject instanceof QMPartIfc)
                {
                    cachePartMasterIfc = (QMPartMasterIfc) ((QMPartIfc) selectObject)
                            .getMaster();
                    //CCBegin SS10
    	            cachePartMasterVec.add(cachePartMasterIfc);
    	            //CCEnd SS10
                    masterID = ((QMPartIfc) selectObject).getMasterBsoID();
                }
                else if(selectObject instanceof QMPartMasterIfc)
                {
                    cachePartMasterIfc = (QMPartMasterIfc) selectObject;
                    masterID = ((QMPartMasterIfc) selectObject).getBsoID();
                }
//              ��������㲿����ֱ�ӻ�ȡ������ڵ㡣
                if(masterID == null || masterID.equals(""))
                {
                    cachePartMasterIfc = (QMPartMasterIfc) ((QMPartIfc) myTree.getSelected()
                            .getObj().getObject()).getMaster();
                }
                Explorable explorable = myTree.getSelected().getObj();
                if(explorable instanceof UsageMasterItem)
                {
                    cachePartUsageLinkIfc = ((UsageMasterItem) explorable)
                            .getPartUsageLink();
                }
                if(explorable instanceof UsageItem)
                {
                    cachePartUsageLinkIfc = ((UsageItem) explorable).getPartUsageLink();
                }
                //�޸�4 20080225 zhangq begin
                if(explorable instanceof PartItem)
                {
                    Explorable parentExplorable = myTree.getSelected().getP()
                            .getObj();
                    if(parentExplorable instanceof UsageMasterItem)
                    {
                        cachePartUsageLinkIfc = ((UsageMasterItem) parentExplorable)
                                .getPartUsageLink();
                    }
                    String parentBsoID="";
                    String childMasterBsoID="";
                    if(parentExplorable instanceof UsageItem)
                    {
                        parentBsoID = ((UsageItem) parentExplorable)
                                .getUsesPart().getPart().getBsoID();
                        childMasterBsoID = ((PartItem) explorable)
                                .getPart().getMasterBsoID();
                    }
                    if(parentExplorable instanceof PartItem)
                    {
                        parentBsoID = ((PartItem) parentExplorable)
                                .getPart().getBsoID();
                        childMasterBsoID = ((PartItem) explorable)
                                .getPart().getMasterBsoID();

                    }
                    if(parentExplorable instanceof UsageItem ||parentExplorable instanceof PartItem) {
                        Class[] paraClass = {String.class, String.class,
                                String.class, String.class};
                        //����ֵ�ļ��ϡ�
                        Object[] objs = {"PartUsageLink", parentBsoID,
                                "usedBy", childMasterBsoID};
                        try
                        {
                            Collection col = (Collection) IBAUtility
                                    .invokeServiceMethod("PersistService",
                                            "findLinkValueInfo", paraClass,
                                            objs);
                            Iterator iter = col.iterator();
                            if(iter.hasNext())
                            {
                                cachePartUsageLinkIfc = (PartUsageLinkIfc) iter
                                        .next();
                            }
                        }
                        catch (QMRemoteException e)
                        {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(getFrame(), e
                                    .getClientMessage(), resourcebundle
                                    .getString("warn"),
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }
                //�޸�4 20080225 zhangq end
                try
                {
                    PartHelper.deletePartUsageLink(cachePartUsageLinkIfc);
                }
                catch (QMRemoteException e)
                {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(getFrame(), e.getClientMessage(), resourcebundle
                            .getString("warn"), JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //ˢ��ѡ��Ľڵ�ĸ��ڵ㣬
                Explorable newObj = new PartItem(part);
                ((PartItem) newObj).setConfigSpecItem(getConfigSpecItem());
                //���ýڵ�ͼ�ꡣ
                ((PartItem) newObj).setIcon();
                myExplorer.refreshNode(myTree.getSelected().getP(), newObj);
            }
            else
            {
                JOptionPane.showMessageDialog(getFrame(), resourcebundle
                        .getString("parentnoupdate"), resourcebundle
                        .getString("warn"), JOptionPane.INFORMATION_MESSAGE);
            }
        }
        //û�и��ڵ㣬ѡ����У��㲿��������������
        else

        {
            cachePartUsageLinkIfc = null;
            JOptionPane.showMessageDialog(getFrame(), "һ���ڵ㲻�ܼ��У�����ѡ���Ʋ�����", resourcebundle
                    .getString("warn"), JOptionPane.WARNING_MESSAGE);
            cachePartMasterIfc=null;
//            Object selectObject = myTree.getSelected().getObj().getObject();
//
//
//            if(selectObject instanceof QMPartIfc)
//            {
//                cachePartMasterIfc = (QMPartMasterIfc) ((QMPartIfc) selectObject)
//                        .getMaster();
//
//            }
//            clear();
        }
    }
    	else
    	{
    		JOptionPane.showMessageDialog(getFrame(), "û��ѡ��Ҫ���е����ڵ㣡", resourcebundle
                    .getString("warn"), JOptionPane.ERROR_MESSAGE);
    	}
    }


    /**
     * ��ǰ�㲿���Ӹ������Ƴ���
     * @throws QMException
     */
    public void moveClear() throws QMException
    {
        QMPartIfc parentPart = null;
        if(myTree.getSelected().getP() != myExplorer.getRootNode())
        {
            parentPart = (QMPartIfc) myTree.getSelected().getP().getObj()
                    .getObject();
            Explorable explorable = myTree.getSelected().getObj();
            PartUsageLinkIfc link = null;
            if(explorable instanceof UsageMasterItem)
            {
                link = ((UsageMasterItem) explorable).getPartUsageLink();
            }
            else if(explorable instanceof UsageItem)
            {
                link = ((UsageItem) explorable).getPartUsageLink();
            }
            if(link != null)
            {
                PartHelper.deletePartUsageLink(link);
            }
            Explorable newObj = new PartItem(parentPart);
            ((PartItem) newObj).setConfigSpecItem(getConfigSpecItem());
            //ˢ��ѡ��Ľڵ㣬
            //���ýڵ�ͼ�ꡣ
            ((PartItem) newObj).setIcon();
            myExplorer.refreshNode(myTree.getSelected().getP(), newObj);
        }
        else
        {
            JOptionPane.showMessageDialog(getFrame(), "�����������Ƴ�", resourcebundle
                    .getString("warn"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * ճ���㲿����Ϊ����ճ��������ճ���Ͳ���ճ����
     */
    public void paste()
    {
        //CCBegin SS10
//        if(cachePartMasterIfc == null)
    	if(cachePartMasterVec == null)
    		//CCEnd SS10
        {
            JOptionPane.showMessageDialog(getFrame(), resourcebundle
                    .getString("notselectpart"), resourcebundle
                    .getString("warn"), JOptionPane.INFORMATION_MESSAGE);
            return;
        } 
        //CCBegin SS10
    	else
    	{
    		for(int i=0;i<cachePartMasterVec.size();i++)
    		{
    			cachePartMasterIfc=(QMPartMasterIfc) cachePartMasterVec.elementAt(i);
    	//CCEnd SS10
    			 //�õ�Ŀ���㲿��
    	        //�õ�Ŀ���㲿��
    	        Object obj=myTree.getSelected().getObj().getObject();
    	        if(obj instanceof QMPartMasterIfc)
    	        {
    	        	 JOptionPane.showMessageDialog(myExplorer.getManager(), "Ŀ���"+((QMPartMasterInfo)obj).getPartNumber()+"���������ù淶�����ܽ����ṹ��", "����",
    	                     JOptionPane.INFORMATION_MESSAGE);
    	        	 return;
    	        }
    	        else
    	        	if(obj instanceof QMPartIfc)
    	        {
    	        QMPartIfc part = (QMPartInfo) obj;
    	        //Ŀ����Ƿ��ǿɸ���״̬
    	        boolean isall = helper.isAllowUpdate(part);
    	        if(isall)
    	        {
    	        	//CCBegin SS9
    	            boolean issamepart = false;
    	          	  try {
    					issamepart = isSamePart(part, cachePartMasterIfc);
    					if(issamepart)
    	            	{
    	            		 JOptionPane.showMessageDialog(getFrame(), 
    	                             "�㲿��"+ part.getPartNumber()+"�£��Ѵ���"+cachePartMasterIfc.getPartNumber()+"�㲿���������ظ����ƣ�", resourcebundle.getString("warn"),
    	                             JOptionPane.INFORMATION_MESSAGE);
    	            		  return;
    	            	}
    				} catch (QMException e1) {
    					e1.printStackTrace();
    				}
    			    //CCEnd SS9
    	          boolean isparentpart = false;
    	          try {
    	            isparentpart = isParentPart(part, cachePartMasterIfc);
    	          }
    	          catch (QMException e) {
    	            e.printStackTrace();
    	            return;
    	          }
    	          if (isparentpart) {
    	            DisplayIdentity displayidentity = IdentityFactory
    	                .getDisplayIdentity(cachePartMasterIfc);
    	            //��ö������� + ��ʶ
    	            String s = displayidentity.getLocalizedMessage(null);
    	            Object[] params = {
    	                s};
    	            String message = QMMessage.getLocalizedMessage(RESOURCE,
    	                "nestingwaringtext", params);
    	            String title = resourcebundle.getString("nestingwaringtitle");
    	            JOptionPane.showMessageDialog(getParentFrame(), message, title,
    	                                          JOptionPane.WARNING_MESSAGE);
    	            displayWaitIndicatorOff();
    	            return;
    	          }
    	          else {
    	            PartUsageLinkIfc link = new PartUsageLinkInfo();
    	            link.setRightBsoID(part.getBsoID());
    	            link.setLeftBsoID(cachePartMasterIfc.getBsoID());
    	            if (iscopy) {
    	              if (cachePartUsageLinkIfc != null) {
    	              	//�޸�4 20080225 zhnagq begin
    	                QMQuantity qmQuantity=new QMQuantity();
    	                qmQuantity.setDefaultUnit(cachePartUsageLinkIfc.getDefaultUnit());
    	                qmQuantity.setQuantity(cachePartUsageLinkIfc.getQuantity());
    	                link.setQMQuantity(qmQuantity);
    	                //�޸�4 20080225 zhangq end
    	                link.setQuantity(cachePartUsageLinkIfc.getQuantity());
    	                link.setDefaultUnit(cachePartUsageLinkIfc.getDefaultUnit());
    	              }
    	              else {
    	                link.setQuantity(1);
    	                link.setDefaultUnit(Unit.getUnitDefault());
    	              }
    	              try {
    	                PartHelper.savePartUsageLink(link);
    	              }
    	              catch (Exception exc) {
    	                exc.printStackTrace();
    	              }
    	            }
    	            else {
    	              link.setQuantity(bmovenum);
    	              if (cachePartUsageLinkIfc != null) {
    	                link.setDefaultUnit(cachePartUsageLinkIfc.getDefaultUnit());
    	              }
    	              else {
    	                link.setDefaultUnit(Unit.getUnitDefault());
    	              }
    	              try {
    	                PartHelper.savePartUsageLink(link);
    	              }
    	              catch (Exception exc) {
    	                exc.printStackTrace();
    	              }
    	              sourcePartMaster = null;
    	              cachePartUsageLinkIfc = null;
    	            }
    	            Explorable newObj = new PartItem(part);
    	            ( (PartItem) newObj).setConfigSpecItem(getConfigSpecItem());
    	            //���ýڵ�ͼ�ꡣ
    	            ( (PartItem) newObj).setIcon();
    	            myExplorer.refreshNode(myTree.getSelected(), newObj, true, false);
    	          }
    	        }
    	        //Ŀ���㲿�����ǿɸ���״̬��
    	        else
    	        {
    	            JOptionPane.showMessageDialog(getFrame(), resourcebundle
    	                    .getString("noupdate"), resourcebundle.getString("warn"),
    	                    JOptionPane.INFORMATION_MESSAGE);
    	        }
    	    }
    		}
    	      //CCBegin SS10
    	}
            //CCEnd SS10
    }

    /**
     * �����ƶ����������ѡ����������ڿɸ���״̬�����в����ƶ�������ƶ�������
     */
    public void bMove()
    {
        if(myTree.getSelected().getP() == myExplorer.getRootNode())
        {
            JOptionPane.showMessageDialog(getFrame(), resourcebundle
                    .getString("notbmove"), resourcebundle.getString("warn"),
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        else
        {
            //          2007.05.31 whj change for capp zz
            //          QMPartIfc ppart = (QMPartIfc) myTree.getSelected().getP().getObj()
            //                  .getObject();
            QMPartIfc ppart = null;
            if(myTree.getSelected().getP().getObj().getObject() instanceof QMPartIfc)
                ppart = (QMPartIfc) myTree.getSelected().getP().getObj()
                        .getObject();
            else
             ppart =(QMPartIfc)  ((QMNode)myTree.getSelected().getParent()).getP().getObj().getObject();
            boolean isalw = helper.isAllowUpdate(ppart);
            try
            {
                if(isalw)
                {
                    iscopy = false;
                    Object select = myTree.getSelected().getObj().getObject();
                    if(select instanceof QMPartIfc)
                    {
                        cachePartMasterIfc=(QMPartMasterIfc)((QMPartIfc) select).getMaster();
                    }
                    else if(select instanceof QMPartMasterIfc)
                    {
                      cachePartMasterIfc=(QMPartMasterIfc) select;
                    }
                    QMPartIfc parentpart = (QMPartIfc) myTree.getSelected()
                            .getP().getObj().getObject();
                    QMNode node = myTree.getSelected();
                    Explorable explorable = node.getObj();
                    PartUsageLinkIfc link = null;
                    if(explorable instanceof UsageMasterItem)
                    {
                        link = ((UsageMasterItem) explorable)
                                .getPartUsageLink();
                    }
                    if(explorable instanceof UsageItem)
                    {
                        link = ((UsageItem) explorable).getPartUsageLink();
                    }
                    //�޸�5 20080306 zhangq begin:�޸�ԭ�򣺼�����Ӽ���ִ�в����ƶ�����ʱ��
                    //�Ҳ����븸��֮��Ĺ�����ֵ����
                    if(explorable instanceof PartItem)
                    {
                        Explorable parentExplorable = node.getP()
                                .getObj();
                        if(parentExplorable instanceof UsageMasterItem)
                        {
                            link = ((UsageMasterItem) parentExplorable)
                                    .getPartUsageLink();
                        }
                        String parentBsoID="";
                        String childMasterBsoID="";
                        if(parentExplorable instanceof UsageItem)
                        {
                            parentBsoID = ((UsageItem) parentExplorable)
                                    .getUsesPart().getPart().getBsoID();
                            childMasterBsoID = ((PartItem) explorable)
                                    .getPart().getMasterBsoID();
                        }
                        if(parentExplorable instanceof PartItem)
                        {
                            parentBsoID = ((PartItem) parentExplorable)
                                    .getPart().getBsoID();
                            childMasterBsoID = ((PartItem) explorable)
                                    .getPart().getMasterBsoID();

                        }
                        if(parentExplorable instanceof UsageItem ||parentExplorable instanceof PartItem) {
                            Class[] paraClass = {String.class, String.class,
                                    String.class, String.class};
                            //����ֵ�ļ��ϡ�
                            Object[] objs = {"PartUsageLink", parentBsoID,
                                    "usedBy", childMasterBsoID};
                            try
                            {
                                Collection col = (Collection) IBAUtility
                                        .invokeServiceMethod("PersistService",
                                                "findLinkValueInfo", paraClass,
                                                objs);
                                Iterator iter = col.iterator();
                                if(iter.hasNext())
                                {
                                    link = (PartUsageLinkIfc) iter
                                            .next();
                                }
                            }
                            catch (QMRemoteException e)
                            {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(getFrame(), e
                                        .getClientMessage(), resourcebundle
                                        .getString("warn"),
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                    }
                    //�޸�5 20080306 zhangq end
                    bmovenum = link.getQuantity();
                    MoveNumberDialog movenumdia = new MoveNumberDialog(
                            getFrame(), "�����ƶ�", true, link, bmovenum);
                    bmovenum = movenumdia.getNumber();
                    if(bmovenum == 0)
                    {
                        return;
                    }
                    cachePartUsageLinkIfc = link;
                    if(bmovenum == link.getQuantity())
                    {
                        PartHelper.deletePartUsageLink(link);
                    }
                    else
                    {
                        link.setQuantity(link.getQuantity() - bmovenum);
                        PartHelper.savePartUsageLink(link);
                    }
                    Explorable newObj = new PartItem(parentpart);
                    ((PartItem) newObj).setConfigSpecItem(getConfigSpecItem());
                    //���ýڵ�ͼ�ꡣ
                    ((PartItem) newObj).setIcon();
                    myExplorer.refreshNode(myTree.getSelected().getP(), newObj);
                }
                else
                {
                    String s = resourcebundle.getString("parentnoupdate");
                    JOptionPane
                            .showMessageDialog(getFrame(), s, resourcebundle
                                    .getString("warn"),
                                    JOptionPane.INFORMATION_MESSAGE);
                }
            }
            catch (QMException qme)
            {
                qme.printStackTrace();
                showMessage(qme.getMessage(), exceptionTitle);
            }
        }
    }

    
    /**
     *�����������ϵ��������࣬��Ҫʵ���Ҽ��ĵ��ѡ���������ק��
     */
    class TreeMouse implements MouseListener, MouseMotionListener
    {
        public void mouseClicked(MouseEvent e)
        {
//        	CCBegin SS6
        	if(e.getClickCount() == 2){
        		QMNode root = myExplorer.getSelectedNode();
        		if (root == null)
        		{
        			QMNode topRoot = myExplorer.getRootNode();
        			if (topRoot.getC() == null)
        			{
        				String information = QMMessage.getLocalizedMessage(RESOURCE,
        						"noPart", null);
        				return;
        			}
        			root = myExplorer.getTree().getRoot();
        		}
        		ArrayList allChildren = getAllDirectChildren(root);
        		ArrayList sortedChildren = (ArrayList) allChildren.clone();
        		sortedChildren = finalSort(sortedChildren);
        		for (int i = 0; i < allChildren.size(); i++)
        		{
        			myExplorer.removeNode((QMNode) allChildren.get(i));
        		}
        		for (int i = 0; i < sortedChildren.size(); i++)
        		{
        			myExplorer.addNode(root, (QMNode) sortedChildren.get(i));
        		}
        		myExplorer.getTree().expandNode(root);
        		myExplorer.refresh();
        	}
//        	CCEnd SS6
        }

        /**
         * �շ���������ƶ�
         * @param mouseevent MouseEvent
         */
        public void mouseMoved(MouseEvent mouseevent)
        {
        }

        public void mousePressed(MouseEvent e)
        {

            if(e.isPopupTrigger())
            {
                TreePath treePath = myTree.getPathForLocation(e.getX(), e
                        .getY());
                myTree.setSelectionPath(treePath);
            }

            if(myTree.getSelected() != null
                    && myTree.getSelected() != myExplorer.getRootNode())
            {
                movenode = myTree.getSelected();
                Object obj = myTree.getSelected().getObj().getObject();
                if(obj instanceof QMPartIfc)
                {
                    sourcePartMaster = (QMPartMasterIfc) ((QMPartIfc) obj)
                            .getMaster();
                    capp=false;
                }
                else

                	if(obj instanceof QMPartMasterIfc)
                	{
                    sourcePartMaster = (QMPartMasterIfc) obj;
                	capp=false;
                }
                else
                {
                	capp=true;
                }
                if(myTree.getSelected().getP() != myExplorer.getRootNode())
                {
                    tempParentNode = myTree.getSelected().getP();
                    if((myTree.getSelected().getP()
                            .getObj().getObject()) instanceof QMPartIfc)
                    sourceParentPart = (QMPartIfc) myTree.getSelected().getP()
                            .getObj().getObject();
                }
                else
                {
                    sourceParentPart = null;
                }
            }
        }

        public void mouseDragged(MouseEvent e)
        {
        	if(fromcapp)
        		return;
            isdraged = true;
            setCursor(new Cursor(Cursor.MOVE_CURSOR));
        }

        public void mouseReleased(MouseEvent e)
        {
        	if(fromcapp)
        		return;
            if(myTree.getSelected() == myExplorer.getRootNode())
            {
                return;
            }
            //������ק��
            if(!isdraged)
            {
                displayWaitIndicatorOff();
                myExplorer.getList().deselectAll();
                if(e.getModifiers() == MouseEvent.META_MASK)
                {
                    TreePath treePath = myTree.getPathForLocation(e.getX(), e
                            .getY());
                    myTree.setSelectionPath(treePath);
                    if( myTree.getSelected() != null&&myTree.getSelected()!= myExplorer.getRootNode())
                    {
                    	partPop.initMenuItem();
                    partPop.show(myTree, e.getX(), e.getY());
                    }
                    else
                    	return;
                }
                oldnode = myTree.getSelected();
                if(oldnode == null)
                {
                    TreePath treePath0 = myTree.getPathForLocation(e.getX(), e
                            .getY());
                    myTree.setSelectionPath(treePath0);
                    oldnode = myTree.getSelected();
                }
                if(oldnode != null)
                {
                    Object select = oldnode.getObj().getObject();
                    if(select instanceof QMPartIfc)
                    {
                        oldpart = (QMPartIfc) select;
                    	capp=false;
                    }
                    else if(select instanceof QMPartMasterInfo)
                    {
                        oldpart = null;
                        oldpartmaster = (QMPartMasterInfo) select;
                    	capp=false;
                    }
                    else
                    {
                    	capp=true;
                    }
                }
            }
            //����ק��
            else
            	if(fromcapp)
            	{
            		return;

            	}
            	else
            {
                TreePath treePath1 = myTree.getPathForLocation(e.getX(), e
                        .getY());
                myTree.setSelectionPath(treePath1);
                //������������ѡ���˽ڵ㣬�������ڵ�����ק�����������ұ�ʹ�ýṹ�������ק��
                if(myTree.getSelected() != null)
                {
                    Object sel = myTree.getSelected().getObj().getObject();
                    if(sel instanceof QMPartIfc)
                    {
                        targetPart = (QMPartIfc) sel;
                    }
                    else if(sel instanceof QMPartMasterIfc)
                    {
                        targetPart = null;
                        JOptionPane.showMessageDialog(myExplorer.getManager(),
                                "��קĿ���"
                                        + ((QMPartMasterInfo) sel)
                                                .getPartNumber()
                                        + "���������ù淶�����ܽ����ṹ��", "����",
                                JOptionPane.INFORMATION_MESSAGE);
                        displayWaitIndicatorOff();
                        isdraged = false;
                        return;
                    }
                    boolean sameparent = false;
                    if(sourceParentPart != null)
                    {
                        sameparent = targetPart.getPartNumber().equals(
                                sourceParentPart.getPartNumber());
                    }
                    if(sameparent)
                    {
                        displayWaitIndicatorOff();
                        JOptionPane.showMessageDialog(myExplorer.getManager(),
                                "��קĿ�����ԭ���ĸ�����ͬ��", "����",
                                JOptionPane.INFORMATION_MESSAGE);
                        isdraged = false;
                        return;
                    }
                    oldnode = myTree.getSelected();
                    Object select = oldnode.getObj().getObject();
                    if(select instanceof QMPartIfc)
                    {
                        oldpart = (QMPartIfc) select;
                    }
                    else if(select instanceof QMPartMasterInfo)
                    {
                        oldpart = null;
                        oldpartmaster = (QMPartMasterInfo) select;
                    }
                    if((!sourcePartMaster.getPartNumber().equals(
                            targetPart.getPartNumber()))
                            && (!sameparent))
                    {
                        try
                        {
                            processDrage(true);
                        }
                        catch (QMException ex)
                        {
                            displayWaitIndicatorOff();
                            JOptionPane.showMessageDialog(getParentFrame(), ex
                                    .getClientMessage(), exceptionTitle,
                                    JOptionPane.INFORMATION_MESSAGE);
                            ex.printStackTrace();
                            isdraged = false;
                            return;
                        }
                        myExplorer.getList().deselectAll();
                    }
                }
                //���ұ�ʹ�ýṹ�������ק��
                else
                {
                    //����ϴ�ѡ��Ľڵ㲻Ϊ�ղ����Ƿ������ù淶���㲿������ʽ��ʼִ����ק������
                    if(oldnode != null && oldpart != null)
                    {
                        targetPart = oldpart;
                        if(getEnterList())
                        {
                            boolean sameparent = false;
                            if(sourceParentPart != null)
                            {
                                sameparent = targetPart.getPartNumber().equals(
                                        sourceParentPart.getPartNumber());
                            }
                            if(sameparent)
                            {
                                displayWaitIndicatorOff();
                                JOptionPane.showMessageDialog(myExplorer
                                        .getManager(), "��קĿ�����ԭ���ĸ�����ͬ��", "����",
                                        JOptionPane.INFORMATION_MESSAGE);
                                isdraged = false;
                                return;
                            }
                            if((!sourcePartMaster.getPartNumber().equals(
                                    targetPart.getPartNumber()))
                                    && (!sameparent))
                            {
                                try
                                {
                                    processDrage(false);
                                }
                                catch (QMException ex)
                                {
                                    displayWaitIndicatorOff();
                                    JOptionPane.showMessageDialog(
                                            getParentFrame(), ex
                                                    .getClientMessage(),
                                            exceptionTitle,
                                            JOptionPane.INFORMATION_MESSAGE);
                                    ex.printStackTrace();
                                    isdraged = false;
                                    return;
                                }
                                setEnterList(false);
                                myExplorer.getList().deselectAll();
                                myTree.setSelectedNode(oldnode);
                            }
                        }
                        myExplorer.getList().deselectAll();
                        myTree.setSelectedNode(oldnode);
                    }
                    else if(oldpart == null)
                    {
                        if(oldpartmaster != null)
                        {
                            displayWaitIndicatorOff();
                            JOptionPane.showMessageDialog(myExplorer
                                    .getManager(), "��קĿ���"
                                    + oldpartmaster.getPartNumber()
                                    + "���������ù淶�����ܽ����ṹ��", "����",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        //change whj 20070613
//                        else
//                        {
//                            displayWaitIndicatorOff();
//                            JOptionPane.showMessageDialog(myExplorer
//                                    .getManager(), "��קĿ������������ù淶�����ܽ����ṹ��",
//                                    "����", JOptionPane.INFORMATION_MESSAGE);
//                        }
                    }
                }
                isdraged = false;
                displayWaitIndicatorOff();
            }
            displayWaitIndicatorOff();
        }

        public void mouseEntered(MouseEvent e)
        {
        }

        public void mouseExited(MouseEvent e)
        {
        }
    }

    /**
     * �����ק�¼��Ĵ���
     * @throws QMException
     */
    public void processDrage(boolean isLeft) throws QMException
    {
        if(targetPart == null)
            return;
        displayWaitIndicatorOn();
        //Դ�㲿����Ŀ���㲿����ͬ��
        if(sourcePartMaster != null
                && sourcePartMaster.getPartNumber().equals(
                        targetPart.getPartNumber()))
        {
            displayWaitIndicatorOff();
            return;
        }
        //Դ�㲿���ĸ������ɸ��¡�
        if(sourceParentPart != null && !helper.isAllowUpdate(sourceParentPart))
        {
            displayWaitIndicatorOff();
            JOptionPane.showMessageDialog(this, "�������ǿɸ���״̬����������ק��", "����",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //Ŀ���㲿�����ɸ��¡�
        if(!helper.isAllowUpdate(targetPart))
        {
            displayWaitIndicatorOff();
            JOptionPane.showMessageDialog(this, "Ŀ������ǿɸ���״̬������ʧ�ܣ�", "����",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //���Դ�㲿����Ŀ���㲿����Ŀ���㲿���ĸ�������ɽṹǶ�׵ģ�������ק��
        if(isParentPart(targetPart, sourcePartMaster))
        {
            displayWaitIndicatorOff();
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    "nestingwaringtext", new Object[] {sourcePartMaster.getIdentity()});
            String title = resourcebundle.getString("nestingwaringtitle");
            JOptionPane.showMessageDialog(getParentFrame(), message, title,
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Explorable explorable = movenode.getObj();
        PartUsageLinkIfc oldUsageLink = null;
        if(explorable instanceof UsageMasterItem)
        {
            oldUsageLink = ((UsageMasterItem) explorable).getPartUsageLink();
        }
        if(explorable instanceof UsageItem)
        {
            oldUsageLink = ((UsageItem) explorable).getPartUsageLink();
        }
        float quantity = 1;
        Unit unit = Unit.getUnitDefault();
        if(oldUsageLink != null)
        {
            quantity = oldUsageLink.getQuantity();
            unit = oldUsageLink.getDefaultUnit();
        }
        //��������קǰ���¼���
        //������ק���¼���
        int i = -1;
        //������������ק���򵯳�ȷ�϶Ի���
        if(isLeft)
        {
            Object[] obj = new Object[3];
            obj[0] = sourcePartMaster.getPartNumber();
            if(sourceParentPart != null)
            {
                obj[1] = sourceParentPart.getPartNumber();
                obj[2] = targetPart.getPartNumber();
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        "confrimdrag", obj);
                i = DialogFactory.showYesNoDialog(getParentFrame(), message,
                        resourcebundle.getString("dragdialogtitle"));
            }
            else
            {
                obj[1] = targetPart.getPartNumber();
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        "onlyaddconfrimdrag", obj);
                i = DialogFactory.showYesNoDialog(getParentFrame(), message,
                        resourcebundle.getString("dragdialogtitle"));
            }
        }
        //���ȷ�϶Ի���ѡ���ǻ�ֱ����������ק��
        if(i == DialogFactory.YES_OPTION || !isLeft)
        {
            PartUsageLinkIfc newUsagelink = new PartUsageLinkInfo();
            newUsagelink.setRightBsoID(targetPart.getBsoID());
            newUsagelink.setLeftBsoID(sourcePartMaster.getBsoID());
            //�޸�4 20080225 zhnagq begin
            QMQuantity qmQuantity=new QMQuantity();
            qmQuantity.setDefaultUnit(unit);
            qmQuantity.setQuantity(quantity);
            newUsagelink.setQMQuantity(qmQuantity);
            //�޸�4 20080225 zhangq end
            newUsagelink.setQuantity(quantity);
            newUsagelink.setDefaultUnit(unit);
            if(oldUsageLink != null)
                PartHelper.deletePartUsageLink(oldUsageLink);
            PartHelper.savePartUsageLink(newUsagelink);
            if(sourceParentPart != null)
            {
                Explorable newExplorable = new PartItem(sourceParentPart);
                ((PartItem) newExplorable).setConfigSpecItem(getConfigSpecItem());
                //���ýڵ�ͼ�ꡣ
                ((PartItem) newExplorable).setIcon();
                //ˢ��Դ�㲿���ĸ�����
                myExplorer.refreshNode(tempParentNode, newExplorable);
            }
            Explorable newExplorable = new PartItem(targetPart);
            ((PartItem) newExplorable).setConfigSpecItem(getConfigSpecItem());
            //���ýڵ�ͼ�ꡣ
            ((PartItem) newExplorable).setIcon();
            //ˢ��ѡ��Ľڵ㡣
            myExplorer.refreshNode(oldnode, newExplorable, true, false);
            myTree.setSelectedNode(oldnode);
        }
        displayWaitIndicatorOff();
    }

    /**
     * �Ҽ��ĸ��¡�
     */
    public void updateSelectedObject()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "editSelectedObject()..begin ....");
        try
        {
            BaseValueIfc selectObject = (BaseValueIfc) getSelectedObject();
            if(null == selectObject)
            {
                showMessage(noSelectObj, exceptionTitle);
                return;
            }
            else
            {
                if(((QMPartIfc) selectObject).getBsoName().equals("GenericPart"))
                {
                    showMessage(QMMessage.getLocalizedMessage(RESOURCE,
                            QMProductManagerRB.CHOOSE_GENERIC_PART_WARNING, null),exceptionTitle);
                    return;
                }
                if(selectObject instanceof QMPartMasterIfc)
                {
                    showMessage(errorObject, exceptionTitle);
                    return;
                }
                if(selectObject instanceof WorkableIfc)
                {
                    //if 11:����������û���������㲿��,��ʾ"�����Ѿ������˼��!"��
                    if(CheckInOutTaskLogic
                            .isCheckedOutByOther((WorkableIfc) selectObject))
                    {
                        //if 1
                        if(selectObject instanceof QMPartIfc)
                        {
                            QMPartIfc part = (QMPartIfc) selectObject;
                            String username = "";
                            UserIfc qmprincipal = CheckInOutTaskLogic
                                    .getCheckedOutBy(part);
                            if(qmprincipal != null)
                            {
                                username = qmprincipal.getUsersDesc();
                            }
                            if(username.trim().length()==0)
                            {
                            	/*�ж϶����Ƿ��ǹ���������*/
                                boolean flag1 = WorkInProgressHelper
                                        .isWorkingCopy((WorkableIfc)part);
                                if(flag1)
                                {
                                	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
                                }
                            }
                            Object[] objs = {username};
                            String message = QMMessage
                                    .getLocalizedMessage(
                                            RESOURCE,
                                            QMProductManagerRB.ALREADY_CHECKOUT_BY_OTHER,
                                            objs);
                            showMessage(message, exceptionTitle);
                            message = null;
                            return;
                        }
                    } //end if 12:�������ǰ�û������
                    else if(CheckInOutTaskLogic
                            .isCheckedOutByUser((WorkableIfc) selectObject))
                    {
                    	//�޸�1 begin��2007-10-25��by ������
                        //if 2:����Ǳ���ǰ�û���������ǹ�������,��ֱ�ӷ���
                        if(!CheckInOutTaskLogic
                                .isWorkingCopy((WorkableIfc) selectObject))
                        {
                            selectObject = CheckInOutTaskLogic //Begin:CR2
        										.getWorkingCopy((WorkableIfc) selectObject);
//        					return;//End:CR2
                        }//�޸�1 end��2007-10-25��by ������
                    }
                    //if 3:����������޸�,��ʾ"���� * ��Ҫ��������޸�,����Ҫ�����"��
                    else if(!helper.isUpdateAllowed((FolderedIfc) selectObject))
                    {
                        QMPartIfc part = (QMPartIfc) selectObject;
                        Object[] objs = {part.getIdentity()};
                        String message = QMMessage.getLocalizedMessage(
                                RESOURCE,
                                QMProductManagerRB.CONFIRM_TO_CHECKOUT, objs);
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                QMProductManagerRB.CHECKOUT_TITLE, null);
                        //��ʾȷ�϶Ի��򲢻��ѡ��ķ���ֵ��
                        int i = JOptionPane.showConfirmDialog(frame, message,
                                title, JOptionPane.YES_NO_OPTION);
                        //if 4
                        if(JOptionPane.OK_OPTION == i)
                        {
                            //�������ϼ��е��㲿������������
                            //��ʾ"��ǰ�㲿���ڸ������ϼ��У����ܼ��!"��
                            if(!CheckInOutTaskLogic
                                    .isInVault((WorkableIfc) selectObject))
                            {
                                String message1 = QMMessage
                                        .getLocalizedMessage(
                                                RESOURCE,
                                                QMProductManagerRB.CANNOT_CHECKOUT_USERFOLDER,
                                                null);
                                showMessage(message1, exceptionTitle);
                                return;
                            }
                            selectObject = getCheckOutObject((WorkableIfc) selectObject);
                            if(null != selectObject)
                            {
                                if(!CheckInOutTaskLogic
                                        .isWorkingCopy((WorkableIfc) selectObject))
                                {
                                    selectObject = CheckInOutTaskLogic
                                            .getWorkingCopy((WorkableIfc) selectObject);
                                }
                                else
                                {
                                    return;
                                }
                            }
                        } //end if 4
                        else
                        {
                            return;
                        }
                    } //end if 3
                }
                //�� if�ж�
                if(null != selectObject)//Begin:CR2
                {
                	if(selectObject instanceof QMPartIfc)
                	{
                      PartItem partItem = new PartItem(
                    		  					(QMPartIfc) selectObject);
                      partItem.setConfigSpecItem(getConfigSpecItem());
                      //�����������㲿������ʱ����������ӵ��㲿�����ϡ�
                      if(!this.isInTree(partItem))
                      {
                    	  myExplorer.addNode(myExplorer.getRootNode(), (Explorable)partItem);
                      }
                      this.setSelectNode(partItem);
                	}
                }//End:CR2
            }
        }
        catch (QMRemoteException e)
        {
            showMessage(e.getMessage(), exceptionTitle);
            e.printStackTrace();
        }
        catch (QMException e)
        {
            showMessage(e.getMessage(), exceptionTitle);
            e.printStackTrace();
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "editSelectedObject()..end ....");
        displayWaitIndicatorOff();
    }
    
    //Begin:CR2
    /**
     * ȡ�ö��ض��ڵ�Ĳٿ�Ȩ
     */
    protected void setSelectNode(PartItem part)
    {
        QMNode qmnode =myExplorer.findNode((Explorable) part);
        if (qmnode != null)
        {
            try
            {
            	myExplorer.setSelectedNode(qmnode);
            }
            catch (Throwable _ex)
            {}
        }
        else
        {
            return;
        }
    }//End:CR2

    /**
     * �����Ƿ�����ק����
     * @param isd boolean
     */
    public void setDrage(boolean isd)
    {
        isdraged = isd;
    }

    /**
     * �ж��Ƿ�����ק����
     * @return boolean
     */
    public boolean getDrage()
    {
        return isdraged;
    }

    private boolean isenterlist = false;

    public void setEnterList(boolean ent)
    {
        isenterlist = ent;
    }

    public boolean getEnterList()
    {
        return isenterlist;
    }

    /**
     * �ж��㲿��partMasterIfc2�Ƿ����㲿��partIfc1�����Ȳ�������partIfc1����
     * @param partIfc1 :QMPartIfc ָ�����㲿����ֵ����
     * @param partMasterIfc2 :QMPartMasterIfc ��������㲿����ֵ����
     * flag==true:�㲿��partMasterIfc2���㲿��partIfc1�����Ȳ�������part1����
     * flag==false:�㲿��partMasterIfc2�����㲿��partIfc1�����Ȳ�����Ҳ����part1����
     * @return flag
     * @throws QMException
     */
    public boolean isParentPart(QMPartIfc partIfc1,
            QMPartMasterIfc partMasterIfc2) throws QMException
    {
        QMPartMasterIfc partMasterIfc1 = (QMPartMasterIfc) partIfc1.getMaster();
       // System.out.println("11");
        if(partMasterIfc1.getBsoID().equals(partMasterIfc2.getBsoID()))
        {
            return true;
        }
        Vector parentParts1 = PartServiceRequest.getAllParentParts(partIfc1);

        //���partIfc1û�и��׽ڵ㣬˵��partMasterIfc2��Զ��������partIfc1�ĸ��׽ڵ㣬���Է���
        //��Զ����false
        if(parentParts1 == null || parentParts1.size() == 0)
        {
            return false;
        }
        for (int i = 0; i < parentParts1.size(); i++)
        {
            String partMasterBsoID2 = partMasterIfc2.getBsoID();
            String parenPartMasterBsoID1 = ((QMPartMasterIfc) parentParts1.elementAt(i)).getBsoID();
            //���partMasterIfc2��BsoID��partIfc1��ĳ�����׽ڵ��BsoID��ȣ�����true;
            if(partMasterBsoID2.equals(parenPartMasterBsoID1))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * �������ȴ�״̬
     */
    public void displayWaitIndicatorOn()
    {
        setCursor(new Cursor(3));
    }

    /**
     * ��������һ��״̬
     */
    public void displayWaitIndicatorOff()
    {
        setCursor(Cursor.getDefaultCursor());
    }
    //add whj for capp 2007.06.25
    public void setFromCapp(boolean capp)

    {
    	fromcapp=capp;

    }
    public boolean getFromCapp()
    {
    	if(capp)
    	{
    	 Object obj = myTree.getSelected().getObj().getObject();
         if(obj instanceof QMPartIfc)
         {
             sourcePartMaster = (QMPartMasterIfc) ((QMPartIfc) obj)
                     .getMaster();
             capp=false;
         }
         else

         	if(obj instanceof QMPartMasterIfc)
         	{
             sourcePartMaster = (QMPartMasterIfc) obj;
         	capp=false;
         }
    	}
    	return (fromcapp&&capp);
    }
    
   //CCBeginby leixiao 2008-07-30  
    
         /**
   * ����"�Ƚ������嵥"�����yanqi-20060915��
   */
  public void processCompareListCommand() {
    WorkThread work = new WorkThread(getThreadGroup(), BUILD_COMPARE_BOM);
    work.start();
  }
    /**
   * �����ȽϹ�����ͼ��������ͼ�����ʹ������������嵥��������20060915
   */
  protected void buildSelectedCompareBOM() {

    HashMap hashmap = new HashMap();
    QMPartIfc part = null;
    Object obj = getSelectedObject();
    //���û��ѡ�������ʾ��Ϣ��û��ѡ���������
    if (obj == null) {
      showMessage(noSelectObj, exceptionTitle);
      return;
    }
    if (obj instanceof QMPartIfc) {
      part = (QMPartIfc) obj;
    }
    //��ʾ��ѡ��Ķ������ʹ��󡣡�
    else {
      showMessage(errorObject, exceptionTitle);
    }
    String bsoID = part.getBsoID();
    hashmap.put("PartID", bsoID);

    //���C�ͻ��˴��ݲ������㲿����������ԣ����ڽ�����ʾ��
    hashmap.put("attributeName", attrName);

    //���C�ͻ��˴��ݲ������㲿����������ԣ����ڵ��÷���
    hashmap.put("attributeName1", attrEnglishName);

    hashmap.put("source", "");
    hashmap.put("type", "");
    //�����������㲿��ͳ�Ʊ�ҳ��
    RichToThinUtil.toWebPage("Part-Other-PartCompare-001.screen", hashmap);

    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

  }

    /**
   * ����"һ���Ӽ��б�"�����yanqi-20061017��
   */
  public void processFirstLevelSonListCommand() {
    WorkThread work = new WorkThread(getThreadGroup(), BUILD_FIRSTLEVELSON_BOM);
    work.start();
  }

    /**
     * ����һ���Ӽ��б�������20061017
     */
    protected void buildSelectedFirstLevelSonBOM() {

      HashMap hashmap = new HashMap();
      QMPartIfc part = null;
      Object obj = getSelectedObject();
      //���û��ѡ�������ʾ��Ϣ��û��ѡ���������
      if (obj == null) {
        showMessage(noSelectObj, exceptionTitle);
        return;
      }
      if (obj instanceof QMPartIfc) {
        part = (QMPartIfc) obj;
      }
      //��ʾ��ѡ��Ķ������ʹ��󡣡�
      else {
        showMessage(errorObject, exceptionTitle);
      }
      String bsoID = part.getBsoID();
      hashmap.put("PartID", bsoID);

      //���C�ͻ��˴��ݲ������㲿����������ԣ����ڽ�����ʾ��
      //hashmap.put("attributeName", attrName);

      //���C�ͻ��˴��ݲ������㲿����������ԣ����ڵ��÷���
      //hashmap.put("attributeName1", attrEnglishName);

      //hashmap.put("source", "");
     // hashmap.put("type", "");
      //�����������㲿��ͳ�Ʊ�ҳ��
      RichToThinUtil.toWebPage("Part-Other-PartFirstLevelSonList-001.screen", hashmap);

      setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

    }

      /**
   * liuming 20070208 add
   * ����"�޺ϼ�װ���"���
   */
  public void processLogicBOMCommand() {
    WorkThread work = new WorkThread(getThreadGroup(), LOGIC);
    work.start();
  }

  /**
   * liuming 20070208 add
   * ����"�޺ϼ�װ���"���
   */
  protected void bomLogic()
  {
    QMPartIfc part = null;
    Object obj = getSelectedObject();
    //���û��ѡ�������ʾ��Ϣ��û��ѡ���������
    if (obj == null) {
      showMessage(noSelectObj, exceptionTitle);
      return;
    }
    if (obj instanceof QMPartIfc) {
      part = (QMPartIfc) obj;
    }
    //��ʾ��ѡ��Ķ������ʹ��󡣡�
    else {
      showMessage(exceptionTitle, exceptionTitle);
    }
    //��hashmap��part���������������嵥��ҳ��
    boolean bool = false; //add by liun
    LogicBomFrame a = new LogicBomFrame(part, bool);
    a.setVisible(true);
    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  }
     //CCEndby leixiao 2008-07-30  
  
  
//CCBegin by leix	 2010-12-20  �����߼��ܳ��������� 
  /**
   * leix
   * ����"�߼��ܳ���������"���
   */
  public void processNewLogicCommand() {
    WorkThread work = new WorkThread(getThreadGroup(), NEWLOGIC);
    work.start();
  }
   

  /**
   * leix
   * ����"�߼��ܳ���������"���
   */
  protected void bomNewLogic()
  {
    QMPartIfc part = null;
    Object obj = getSelectedObject();
    //���û��ѡ�������ʾ��Ϣ��û��ѡ���������
    if (obj == null) {
      showMessage(noSelectObj, exceptionTitle);
      return;
    }
    if (obj instanceof QMPartIfc) {
      part = (QMPartIfc) obj;
    }
    else {
      showMessage(exceptionTitle, exceptionTitle);
    }
    boolean bool = false; //add by liun
    NewLogicBomFrame a = new NewLogicBomFrame(part, bool);
    a.setVisible(true);
    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  }
//CCEnd by leix	 2010-12-20  �����߼��ܳ���������
  
  
  //CCBegin SS13
  /**
   * ����"����һ�����嵥"���
   */
  public void processFirstLevelCommand() {
    WorkThread work = new WorkThread(getThreadGroup(), FIRSTLEVEL);
    work.start();
  }
   

  /**
   * ����"����һ�����嵥"���
   */
  protected void firstLevelList()
  {
    QMPartIfc part = null;
    Object obj = getSelectedObject();
    //���û��ѡ�������ʾ��Ϣ��û��ѡ���������
    if (obj == null)
    {
      showMessage(noSelectObj, exceptionTitle);
      return;
    }
    if (obj instanceof QMPartIfc) {
      part = (QMPartIfc) obj;
    }
    else
    {
      showMessage(exceptionTitle, exceptionTitle);
    }
    FirstLevelListFrame a = new FirstLevelListFrame(part);
    a.setVisible(true);
    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  }
  //CCEnd SS13
  
  //CCBegin SS4
  /**
   *   �������ӹ�˾�����嵥������
   */
  public void processSubCompBom(){
	  WorkThread work = new WorkThread(getThreadGroup(),SUBCOMPBOM );
	  work.start();
  }
  /**
   *   �������ӹ�˾�����嵥������
   */
  protected void subBom()
 {
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"subBom()..begin ....");
		HashMap hashmap = new HashMap();
		QMPartIfc part = null;
		Object obj = getSelectedObject();
		if (null == obj) {
			showMessage(noSelectObj, exceptionTitle);
			return;
		}
		if (obj instanceof QMPartIfc) {
			part = (QMPartIfc) obj;
		} else {
			showMessage(errorObject, exceptionTitle);
		}
	    boolean bool = false;
	    NewSubCompBomJFrame a = new NewSubCompBomJFrame(part, bool);
		a.setVisible(true);
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
 //CCEnd SS4 
  //CCBegin SS5
  
  public void processZCBom()
  {
	  WorkThread work = new WorkThread(getThreadGroup(),ZCBOM );
	  work.start();

  }
  protected void zcBom()
  {
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"zcBom()..begin ....");
		HashMap hashmap = new HashMap();
		QMPartIfc part = null;
		Object obj = getSelectedObject();
		if (null == obj) {
			showMessage(noSelectObj, exceptionTitle);
			return;
		}
		if (obj instanceof QMPartIfc) {
			part = (QMPartIfc) obj;
		} else {
			showMessage(errorObject, exceptionTitle);
		}
		String bsoID = part.getBsoID();
		hashmap.put("PartID", bsoID);
		RichToThinUtil.toWebPage("Part-Other-WholePartStatistics-001.screen",
				hashmap);
		bsoID = null;
		hashmap = null;
		part = null;
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"buildSelectedGradeBOM()..end ....");
		displayWaitIndicatorOff();
	}
  public void processDLZCBom()
  {
	  WorkThread work = new WorkThread(getThreadGroup(),DLZCBOM );
	  work.start();

  }
  protected void dlzcBom()
  {
		PartDebug.trace(this, PartDebug.PART_CLIENT, "subBom()..begin ....");
		HashMap hashmap = new HashMap();
		QMPartIfc part = null;
		Object obj = getSelectedObject();
		if (null == obj) {
			showMessage(noSelectObj, exceptionTitle);
			return;
		}
		if (obj instanceof QMPartIfc) {
			part = (QMPartIfc) obj;
		} else {
			showMessage(errorObject, exceptionTitle);
		}
		boolean bool = false;
		SelectViewNameDialog a = new SelectViewNameDialog(frame,part);
		a.setVisible(true);
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

  //CCEnd SS5
    //�޸�2��2007-11-07,add by ������  begin
    /**
     * �����ж϶����Ƿ��Ѿ���ʾ,������root��1���ӽڵ�
     * @param partItem
     * @return flag
     */
    public boolean isInTree(PartItem partItem)
    {
    	boolean flag=false;
    	Explorable explorable=(Explorable)partItem;
    	QMNode[] node=myTree.findNodes(explorable);
    	for(int i=0;i<node.length &&!flag;i++)
    	{
    		String rootText=myTree.getRoot().getLabel();
    		String parentNodeText=node[i].getP().getLabel();
    		if(rootText.equals(parentNodeText))
    			flag=true;
    	}
    	return flag;
    }//end
    //add whj 2008/08/08
    /**
     * �ж��Ƿ���ڿ�ճ������
     * @return
     *@see boolean
     */
    public boolean isPaste()
    {
    	return cachePartMasterIfc!=null;
    }
    
    ////////////////////add by muyp begin///////////////////////
    /**
     * ����Ӧ�����С�����
     */
    public void processAddAttrCommand()
    {
    	WorkThread work = new WorkThread(getThreadGroup(), APPLY_TO_ALL_PARTS);
        work.start();
    }
    
    /**
     * Ӧ������
     *
     */
    public void addAttrToOthersParts()
    {
    	PartTaskJPanel task=myExplorer.getPartTaskJPanel();
    	int i = task.getSelectTabbed();
		int baseIndex=task.getBaseIndex();
		int ibaIndex=task.getIbaIndex();
    	//���ѡ��Ķ���
		Object selected_obj = this.getSelectedObject();
		//���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
		if(null == selected_obj)
		{
		    showMessage(noSelectObj, exceptionTitle);
		    return;
		}
		//������㲿������Ϣ����ʾ��Ϣ��ѡ��������
		if(selected_obj instanceof QMPartMasterIfc)
        {
            showMessage(errorObject, exceptionTitle);
            return;
        }
		if(selected_obj instanceof WorkableIfc)
		{
			BaseValueIfc baseIfc = (BaseValueIfc) selected_obj;
            Class[] class1 = {BaseValueIfc.class};
            Object[] param = {(BaseValueIfc) baseIfc};
            //���ó־û�����ķ�����
            try {
				baseIfc = (BaseValueIfc) IBAUtility.invokeServiceMethod(
				        "PersistService", "refreshInfo", class1, param);
				if(baseIfc instanceof QMPartIfc)
				{
					QMPartIfc part = (QMPartIfc)baseIfc;
					
                    Object[] objs = {part.getName()};
                    String title = QMMessage
                            .getLocalizedMessage(
                                    RESOURCE,
                                    QMProductManagerRB.APPLY_ATTRIBUTE_TO_OTHER,
                                    objs);
					if(i==baseIndex)
					{
						BaseAttrApplyAllJDialog dialog = new BaseAttrApplyAllJDialog(part,this.getParentFrame());					
						PartScreenParameter.centerWindow(dialog);
					}
					else if(i==ibaIndex)
					{
						ExtendAttrApplyAllJDialog dialog=new ExtendAttrApplyAllJDialog(this.getParentFrame(),title,
								task.getIBAAttributesJPanel());
						PartScreenParameter.centerWindow(dialog);

						//��ø��³ɹ����㲿�� //add by muyp 20081103 begin
						BaseValueIfc[] suc_obj = dialog.getApplySucObj();
						if(suc_obj!=null&&suc_obj.length>0)
						//ˢ���ڽڵ����ϵ��㲿��
						refreshApplySucPart(suc_obj);//end
					}
					else
					{
						showMessage(selectObject, exceptionTitle);
					}
				}
			} catch (QMRemoteException e) {
				e.printStackTrace();
			}

		}
		
    	
    }
    
//  add by muyp 081103 begin
	/**
	 * Ӧ������֮�󣬶��ڹ������������ϵ��㲿������ˢ��
	 * @param suc_obj BaseValueIfc[] Ӧ�����и��³ɹ����㲿��
	 * @see com.faw_qm.framework.service.BaseValueIfc
	 */
	private void refreshApplySucPart(BaseValueIfc[] suc_obj)
	{
		for(int i=0;i<suc_obj.length;i++)
		{
			if (suc_obj[i] instanceof WorkableIfc) {
				/* �ж϶����Ƿ��ǹ��������� */
				boolean flag = WorkInProgressHelper
						.isWorkingCopy((WorkableIfc) suc_obj[i]);
				if(flag)
				{
					if (suc_obj[i] instanceof QMPartIfc) {
						QMPartIfc partIfc = (QMPartIfc) suc_obj[i];
						PartItem selected_obj1 = new PartItem(partIfc);
						// ������������ϣ��ͶԸ�������ˢ��
						if(isInTree(selected_obj1))
						{
							// �������ΪQMPartIfc��
							if (suc_obj[i] instanceof QMPartIfc) {
								BaseValueIfc baseIfc = (BaseValueIfc) suc_obj[i];
								Class[] class1 = { BaseValueIfc.class };
								Object[] param = { (BaseValueIfc) baseIfc };
								try {
									// ���ó־û�����ķ�����
									baseIfc = (BaseValueIfc) IBAUtility.invokeServiceMethod(
											"PersistService", "refreshInfo", class1, param);
								} catch (QMException e) {
									showMessage(e.getClientMessage(), exceptionTitle);
									this.clear();
									e.printStackTrace();
									return;
								}
								// ��part����һ��QMPartItem����
								QMPartIfc part = (QMPartIfc) baseIfc;
								// ת����PartItem��
								Explorable newObj = new PartItem(part);
								((PartItem) newObj).setConfigSpecItem(getConfigSpecItem());
								// ˢ��ѡ��Ľڵ㣬
								// ���ýڵ�ͼ�ꡣ
								((PartItem) newObj).setIcon();
								//��ȡ���³ɹ��㲿�������ϵĽڵ�
						        Enumeration en = (((QMNode) (myTree.myModel().getRoot())).
				                          depthFirstEnumeration());
				                while (en.hasMoreElements())
				                {
				                    QMNode qmnode = (QMNode) en.nextElement();
				                    //����˽ڵ㲻�Ǹ��ڵ�
				                    if(!qmnode.isRoot())
				                    {
				                    	//����ڵ��㲿���ǵ�ǰ���㲿���������ˢ��
					                    if(qmnode.getObj().getObjectNumber().equals(partIfc.getName()))
					                    {
					                    	Explorable explorable=qmnode.getObj();       //CR5
											myExplorer.refreshNode(qmnode, explorable, false, true);
											break;
					                    }
				                    }
				                }
							}
						
						}
					}
				}
			}
		}

	}//end
    //end
	
	 /**
     * �������롣���ԶԶ���㲿�����м��롣
     */
    public void checkinSelectedObjects() {
		PartDebug.trace(this, PartDebug.PART_CLIENT, "�������..��ʼ ....");
		Vector checkin = new Vector();
		Vector isInTreeVec = new Vector();
		try {
			// ���ѡ��Ķ���
			Object[] selected_obj = getSelectedObjects();

			// ���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
			if (null == selected_obj) {
				showMessage(noSelectObj, exceptionTitle);
				return;
			}
			JFrame frame = getParentFrame();
			BatchCheckInTask checkin_task = new BatchCheckInTask(frame);
			WorkableIfc workingCopy=null;
			WorkableIfc originalCopy=null;
			try {
				for (int i = 0; i < selected_obj.length; i++) {
					if (selected_obj[i] instanceof QMPartMasterIfc) {
						isInTreeVec.add(new Boolean(false));
						checkin.add(selected_obj[i]);
						checkin.add(selected_obj[i]);
						checkin.add(selected_obj[i]);
					} else if (selected_obj[i] instanceof WorkableIfc) {
						WorkableIfc workableIfc = (WorkableIfc) selected_obj[i];
						boolean isChanged = myExplorer.isChange();
						boolean isSaved = false;
						if (isChanged) {
							isSaved = myExplorer.saveChange();
						}
						myExplorer.setTreeValueChanged(false);
						//CCBegin SS12
						//if (isSaved)
						//CCEnd SS12
						{
							BaseValueIfc baseIfc = (BaseValueIfc) workableIfc;
				            Class[] class1 = {BaseValueIfc.class};
				            Object[] param = {(BaseValueIfc) baseIfc};
				            workableIfc = (WorkableIfc) IBAUtility.invokeServiceMethod(
								        "PersistService", "refreshInfo", class1, param);
						} 
						if (workableIfc instanceof QMPartIfc) {
							//CCBegin SS3
							//���ÿ��part���Ҷ�ӦCAD�ĵ������CAD״̬�Ǽ����������ʾ���׳��쳣��ֹ����
							QMPartIfc partIfcTemp = (QMPartIfc) workableIfc;
							BaseValueIfc baseIfc = (BaseValueIfc) partIfcTemp;
				            Class[] class1 = {BaseValueIfc.class};
				            Object[] param = {(BaseValueIfc) baseIfc};
				            Vector EPMTemp = (Vector) IBAUtility.invokeServiceMethod(
								        "StandardPartService", "getEMPDocument", class1, param);
				            if(EPMTemp != null && EPMTemp.size()>0)
				            {
				            	for(int ii=0;ii<EPMTemp.size();ii++)
				            	{
				            		EPMDocumentIfc epmIfcTemp = (EPMDocumentIfc) EPMTemp.get(ii);
				            		if(CheckInOutTaskLogic.isCheckedOut(epmIfcTemp))
				            		{
				            			throw new QMException("�㲿��"+partIfcTemp.getPartNumber()+
				            					"��Ӧ��EPM�ĵ����ڼ��״̬������CAD�����н��������ٶ��㲿�����и��ģ�");
				            		}
				            	}
				            }
							//CCEnd SS3

							boolean isWorkingCopy=CheckInOutTaskLogic.isWorkingCopy(workableIfc);
							if(isWorkingCopy){
								workingCopy=workableIfc;
								originalCopy=CheckInOutTaskLogic.getOriginalCopy(workableIfc);
							}
							else{
								originalCopy=workableIfc;
								workingCopy=CheckInOutTaskLogic.getWorkingCopy(workableIfc);
							}
							PartItem selected_obj1 = new PartItem((QMPartIfc)originalCopy);
							isInTreeVec
									.add(new Boolean(isInTree(selected_obj1)));
						} else {
							isInTreeVec.add(new Boolean(false));
						}
						// ���ü���Ķ���
						checkin.add(workableIfc);
						checkin.add(originalCopy);
						checkin.add(workingCopy);
//						System.out.println("workableIfc="+workableIfc);
//						System.out.println("originalCopy="+originalCopy);
//						System.out.println("workingCopy="+workingCopy);
//				          CCBegin SS8	
//						  CCBegin SS11	
//						  Class[] class1 = {QMPartIfc.class,QMPartIfc.class};
//				            Object[] param = {originalCopy,workingCopy};
				            Class[] class1 = {QMPartIfc.class};
				            Object[] param = {workingCopy};
//					          CCEnd SS11
						 IBAUtility.invokeServiceMethod(
							        "BomNoticeService", "processAdoptNoticeByPart", class1, param);
//				          CCEnd SS8
					}
				}
				checkin_task.setIsInTree(isInTreeVec);
				checkin_task.setCheckinItem(checkin);
				checkin_task.checkIn();
			}
			// ��ʾ:"��ǰ��δ��*�����"��
			catch (QMException qmexception) {
				showMessage(qmexception.getClientMessage(), exceptionTitle);
				qmexception.printStackTrace();
			}

			selected_obj = null;
		} finally {
			displayWaitIndicatorOff();
		}
		PartDebug.trace(this, PartDebug.PART_CLIENT,
				"checkinSelectedObject()..���� ....");
	}
    
    /**
     * ������������ԶԶ���㲿�����м����
     */
    public void checkoutSelectedObjects()
    {
    	//CR3 begin
		Object[] obj = getSelectedObjects();

		// ���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
		if (null == obj) {
			showMessage(noSelectObj, exceptionTitle);
			return;
		}
		// �����Ҫ������㲿������������1����ѭ��������
		// modify by muyp 20080707
		Vector messageVec = new Vector();
		for (int i = 0; i < obj.length; i++) {
			try {
				if (obj[i] instanceof QMPartMasterIfc) {
					// showMessage(errorObject, exceptionTitle);
					messageVec.add(((QMPartMasterIfc) obj[i]).getIdentity()
							+ "���ʧ�ܣ�" + errorObject);

					continue;
				} 
				else {
					WorkableIfc workable = null;
					String objID = ((QMPartIfc) obj[i]).getBsoID();
					Class[] theClass = { String.class };
					Object[] theObjs = { objID };
					RequestHelper requestHelper = new RequestHelper();
					workable = (WorkableIfc) requestHelper.request(
							"PersistService", "refreshInfo", theClass, theObjs);
					WorkableIfc original = workable;
					boolean flag = VersionControlHelper
							.isLatestIteration(original); // �Ƿ������°�

					if (!flag) {
						messageVec.add(((QMPartIfc) obj[i]).getIdentity()
								+ "���ʧ�ܣ�����С�汾���ܽ��м��");
						continue;
					}

					// ���ѡ��Ķ��������ϼй���
					if (obj[i] instanceof FolderedIfc) {
						FolderedIfc selected_object = (FolderedIfc) obj[i];
						// ���selected_object����Ϊnull,���ö�������
						if (null != selected_object) {
							checkOutObject(selected_object, messageVec);
						}

					}
				}
			} catch (QMException e) {
				e.printStackTrace();
			}
		}

		obj = null;
		displayWaitIndicatorOff();
		MessageDialog mess = new MessageDialog(getParentFrame(), "�����Ϣ", true,
				messageVec);
		mess.setVisible(true);
		//CR3 end
	}
    
    /**
	 * ������������ԶԶ���㲿�����в�����
	 */
    public void undoCheckoutSelectedObjects() {
		Vector messageVec = new Vector();
		Object[] selected_obj = getSelectedObjects();
		// ���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
		if (null == selected_obj) {
			showMessage(noSelectObj, exceptionTitle);
			return;
		} else {
			for (int i = 0; i < selected_obj.length; i++) {
				undoCheckoutSelectedObject((BaseValueIfc) selected_obj[i],
						messageVec);
			}
		}
		selected_obj = null;
		displayWaitIndicatorOff();
		MessageDialog mess = new MessageDialog(getParentFrame(), "���������Ϣ",
				true, messageVec);
		mess.setVisible(true);
	}
    
    //liyz add ������ع������������
    
    /**
     * ������������������
     */
    public void processsetTechnicsRegulationCommand()
    {
    	TechnicsRegulationsMainJFrame tecJFrame =new TechnicsRegulationsMainJFrame(true);
    	tecJFrame.setVisible(true);
    }
    
    /**
     * ��������·�߱�����������
     */
    public void processsetTechnicsRouteCommand()
    {
    	CappRouteListManageJFrame routeJFrame = new CappRouteListManageJFrame(false);
    	routeJFrame.setVisible(true);    	
    }
    
    /**
     * �������ջ�������������
     */
    public void processsetTechnicsSummaryCommand()
    {
    	SummaryMainController summary = new SummaryMainController(false);
    	summary.getMainFrame().setVisible(true);
    }
    
	boolean ischeckin=true;
//    private WorkableIfc checkinSelectedObject()
//  {
//    	WorkableIfc work=null;
//      PartDebug.trace(this, PartDebug.PART_CLIENT, "�������..��ʼ ....");
//      
//          //���ѡ��Ķ���
//          Object selected_obj = getSelectedObject();
//          //���û��ѡ�������ʾ��Ϣ��û��ѡ��������󡱡�
//          if(null == selected_obj)
//          {
//              showMessage(noSelectObj, exceptionTitle);
//              ischeckin=false;
//          }
//          if(selected_obj instanceof QMPartMasterIfc)
//          {
//              showMessage(errorObject, exceptionTitle);
//              ischeckin=false;
//          }
//          if(selected_obj instanceof WorkableIfc)
//          {
//              try
//              {
//                  BaseValueIfc baseIfc = (BaseValueIfc) selected_obj;
//                  Class[] class1 = {BaseValueIfc.class};
//                  Object[] param = {(BaseValueIfc) baseIfc};
//                  //���ó־û�����ķ�����
//                  baseIfc = (BaseValueIfc) IBAUtility.invokeServiceMethod(
//                          "PersistService", "refreshInfo", class1, param);
//                  //���������������˼����
//                  if(CheckInOutTaskLogic
//                          .isCheckedOutByOther((WorkableIfc) baseIfc))
//                  {
//                      //if 1
//                      if(baseIfc instanceof QMPartIfc)
//                      {
//                          QMPartIfc part = (QMPartIfc) baseIfc;
//                          String username = "";
//                          UserIfc qmprincipal = CheckInOutTaskLogic
//                                  .getCheckedOutBy(part);
//                          if(qmprincipal != null)
//                          {
//                              username = qmprincipal.getUsersDesc();
//                          }
//                          else
//                          {
//                              username = part.getLocker();
//                          }
//                          if(username==null||username.trim().length()==0)
//                          {
//                          	/*�ж϶����Ƿ��ǹ���������*/
//                              boolean flag = WorkInProgressHelper
//                                      .isWorkingCopy((WorkableIfc) part);
//                              if(flag)
//                              {
//                              	username=CheckInOutTaskLogic.getCheckedOutBy(part,java.util.Locale.SIMPLIFIED_CHINESE);
//                              }
//                          }
//                          Object[] objs = {username};
//                          String message = QMMessage
//                                  .getLocalizedMessage(
//                                          RESOURCE,
//                                          QMProductManagerRB.ALREADY_CHECKOUT_BY_OTHER_NOTCHECKIN,
//                                          objs);
//                          showMessage(message, exceptionTitle);
//                          message = null;
//                          part = null;
//                          objs = null;
//                          ischeckin=false;
//                      }
//                  }
//                  else
//                  {
//                  	work=(WorkableIfc) baseIfc;
//                  }
//                  //������λ��
//                  selected_obj = null;
//                  baseIfc = null;
//              }
//              //��ʾ:"��ǰ��δ��*�����"��
//              catch (NotCheckedOutException notcheckedoutexception)
//              {
//                  showMessage(notcheckedoutexception.getClientMessage(),
//                          exceptionTitle);
//                  notcheckedoutexception.printStackTrace();
//                  ischeckin=false;
//              }
//              catch (QMException qmexception)
//              {
//                  showMessage(qmexception.getClientMessage(), exceptionTitle);
//                  qmexception.printStackTrace();
//                  ischeckin=false;
//              }
//          }
//  
//      PartDebug.trace(this, PartDebug.PART_CLIENT,
//              "checkinSelectedObject()..���� ....");
//      return work;
//  }
	
//	CCBegin SS6
	/**
     * ����һ���ڵ㣬���������ֱ�Ӻ��ӽڵ㡣
     * @param root ����Ľڵ㡣
     * @return ArrayList ��ArrayList�е�Ԫ��Ϊ�ҵ������нڵ㡣
     */
    public ArrayList getAllDirectChildren(QMNode root)
    {
        QMNode firstChild = root.getC();
        ArrayList allChildren = new ArrayList();
        for (QMNode node = firstChild; node != null; node = node.getS())
        {
            allChildren.add(node);
        }
        return allChildren;
    }
    
    public ArrayList finalSort(ArrayList list)
	{
		boolean isAscending = true;
		boolean isByNumber = true;
		boolean isByName = false;
		boolean isByCreateTime = false;
		boolean isByVersion = false;
		//������������Ƶı�ʾ����˵���������
		//t����true��f����false
		boolean tttt = isByNumber && isByName && isByCreateTime && isByVersion;
		boolean ffff = !isByNumber && !isByName && !isByCreateTime &&
		!isByVersion;
		boolean tttf = isByNumber && isByName && isByCreateTime && !isByVersion;
		boolean ttft = isByNumber && isByName && !isByCreateTime && isByVersion;
		boolean tftt = isByNumber && !isByName && isByCreateTime && isByVersion;
		boolean fttt = !isByNumber && isByName && isByCreateTime && isByVersion;
		boolean tfff = isByNumber && !isByName && !isByCreateTime &&
		!isByVersion;
		boolean ftff = !isByNumber && isByName && !isByCreateTime &&
		!isByVersion;
		boolean fftf = !isByNumber && !isByName && isByCreateTime &&
		!isByVersion;
		boolean ffft = !isByNumber && !isByName && !isByCreateTime &&
		isByVersion;
		boolean ttff = isByNumber && isByName && !isByCreateTime &&
		!isByVersion;
		boolean fftt = !isByNumber && !isByName && isByCreateTime &&
		isByVersion;
		boolean tftf = isByNumber && !isByName && isByCreateTime &&
		!isByVersion;
		boolean ftft = !isByNumber && isByName && !isByCreateTime &&
		isByVersion;
		boolean fttf = !isByNumber && isByName && isByCreateTime &&
		!isByVersion;
		boolean tfft = isByNumber && !isByName && !isByCreateTime &&
		isByVersion;
		ArrayList result = new ArrayList();
		if (tfff || tfft || tftf || tftt || ttff || ttft || tttf || tttt) //�Ȱ���������Ѿ�Ψһ
		{
			sortByNumber(list, isAscending);
			result = list;
		}
		return result;
	}
	public void sortByNumber(ArrayList allChildren, boolean isAscending)
	{
		if (allChildren == null)
		{
			return;
		}
		if (allChildren.size() < 2)
		{
			return;
		}
		QMPartComparator comp=new QMPartComparator(QMPartComparator.SORTBYNUMBER,isAscending);
		Collections.sort(allChildren,comp);
	}
	static class QMPartComparator implements java.util.Comparator {    //Begin CR1
		private static int SORTBYNUMBER= 1;
		private static int SORTBYNAME = 2;
		private static int SORTBYCREATIONDATE = 3;
		private static int SORTBYVERSION = 4;
		private int sortByKind = 1;
		private boolean isDesc = false;

		public QMPartComparator(int sortKind, boolean isDescFlag) {
			sortByKind = sortKind;
			isDesc = isDescFlag;
		}

		public int compare(Object obj1, Object obj2) {
			QMNode node1 = null;
			QMNode node2 = null;
			int number = 0;
			node1 = (QMNode) obj1;
			node2 = (QMNode) obj2;
			if (isDesc) {
				if (sortByKind == SORTBYNUMBER) {
					number = new String(node1.getObj().getObjectNumber())
					.compareTo(new String(node2.getObj()
							.getObjectNumber()));
				} else if (sortByKind == SORTBYNAME) {
					number = chineseCompareTo(node1.getObj().getObjectName(),node2.getObj().getObjectName());
				}
				else if (sortByKind == SORTBYCREATIONDATE) {
					number = node1.getObj().getObjectCreationDate()
							.compareTo(node2.getObj()
									.getObjectCreationDate());
				} else if(sortByKind == SORTBYVERSION) {
					number = new String(node1.getObj().getObjectVersion())
					.compareTo(new String(node2.getObj()
							.getObjectVersion()));
				}
				return number;
			}
			else {
				if (sortByKind == SORTBYNUMBER) {
					number = node1.getObj().getObjectNumber()
							.compareTo(node2.getObj()
									.getObjectNumber());
				} else if(sortByKind == SORTBYNAME) {
					number = chineseCompareTo(node1.getObj().getObjectName(),node2.getObj().getObjectName());
				}
				else if (sortByKind == SORTBYCREATIONDATE) {
					number = node1.getObj().getObjectCreationDate()
							.compareTo(node2.getObj()
									.getObjectCreationDate());
				} else if(sortByKind == SORTBYVERSION) {
					number = new String(node1.getObj().getObjectVersion())
					.compareTo(new String(node2.getObj()
							.getObjectVersion()));
				}
				return -number;
			}

		}

	}

	public static int chineseCompareTo(String s1, String s2)//CR!
	{
		int len1 = s1.length();
		int len2 = s2.length();
		int n = Math.min(len1, len2);
		for (int i = 0; i < n; i++)
		{
			int s1_code = getCharCode(s1.charAt(i) + "");
			int s2_code = getCharCode(s2.charAt(i) + "");
			if (s1_code != s2_code)
			{
				return s1_code - s2_code;
			}
		}
		return len1 - len2;
	}
	public static int getCharCode(String s)
	{
		if (s == null && s.equals(""))
		{
			return -1; //��������
		}
		byte[] b = s.getBytes();
		int value = 0;
		//��֤ȡ��һ���ַ������ֻ���Ӣ�ģ�
		for (int i = 0; i < b.length && i <= 2; i++)
		{
			value = value * 100 + b[i];
		}
		return value;
	}
//	CCEnd SS6
	
	
	
    //CCBegin SS9
    /**
     * �ж��㲿��partMasterIfc2�Ƿ����㲿��partIfc1���Ӽ���
     * @param partIfc1 :QMPartIfc ָ�����㲿����ֵ����
     * @param partMasterIfc2 :QMPartMasterIfc ��������㲿����ֵ����
     * flag==true:�㲿��partMasterIfc2���㲿��partIfc1���Ӽ���
     * flag==false:�㲿��partMasterIfc2�����㲿��partIfc1���Ӽ���
     * @return flag
     * @throws QMException
     */
    public boolean isSamePart(QMPartIfc partIfc1,
            QMPartMasterIfc partMasterIfc2) throws QMException
    {
        QMPartMasterIfc partMasterIfc1 = (QMPartMasterIfc) partIfc1.getMaster();
        Class[] paraClass = {QMPartIfc.class};
        //����ֵ�ļ��ϡ�
        Object[] objs = {partIfc1};
        Collection col = (Collection) IBAUtility
                    .invokeServiceMethod("StandardPartService",
                            "getSubParts", paraClass, objs);
            Vector parentParts1 = (Vector)col;
        if(parentParts1 == null || parentParts1.size() == 0)
        {
            return false;
        }
        for (int i = 0; i < parentParts1.size(); i++)
        {
            String partMasterBsoID2 = partMasterIfc2.getBsoID();
            String parenPartMasterBsoID1 = ((QMPartMasterIfc)((QMPartInfo) parentParts1.elementAt(i)).getMaster()).getBsoID();
            //���partMasterIfc2��BsoID��partIfc1��ĳ���ӽڵ��BsoID��ȣ�����true;
            if(partMasterBsoID2.equals(parenPartMasterBsoID1))
            {
                return true;
            }
        }
        return false;
    }
    //CCEnd SS9
}
