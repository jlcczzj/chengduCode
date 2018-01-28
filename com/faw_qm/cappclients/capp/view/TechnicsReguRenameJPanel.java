/** ���ɳ���TechnicsReguRenameJPanel.java	1.1  2003/08/08
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.faw_qm.capp.model.QMTechnicsMasterIfc;
import com.faw_qm.capp.model.QMTechnicsMasterInfo;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.version.model.MasteredIfc;


/**
 * <p>Title: ���ڸ��Ĺ��տ������ƺͱ�ŵ����</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ���� Ѧ��
 * @version 1.0
 */

public class TechnicsReguRenameJPanel extends JPanel
{
    private JLabel numJLabel = new JLabel();
    private CappTextField numJTextField;
    private JLabel nameJLabel = new JLabel();
    private CappTextField nameJTextField;
    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**��Դ�ļ�·��*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.capp.util.CappLMRB";


    /**���տ�����Ϣ����*/
    private QMTechnicsMasterInfo technicsObject;


    /**���ڱ�ǵ�ǰ����*/
    private String currentName;


    /**���ڱ�ǵ�ǰ���*/
    private String currentNumber;


    /**���������*/
    private RequestServer server = RequestServerFactory.getRequestServer();


    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    /**������*/
    private Component parent;

    public TechnicsReguRenameJPanel(Component parent)
    {
        this.parent = parent;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * �����ʼ��
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        numJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        numJLabel.setText("*���ձ��");
        setLayout(gridBagLayout1);
        setSize(400, 100);
        nameJLabel.setMaximumSize(new Dimension(53, 22));
        nameJLabel.setMinimumSize(new Dimension(53, 22));
        nameJLabel.setPreferredSize(new Dimension(53, 22));
        nameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        nameJLabel.setText("*��������");
        add(nameJLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.NORTHEAST,
                                               GridBagConstraints.NONE,
                                               new Insets(20, 34, 62, 0), 0, 0));
        add(numJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.EAST,
                                              GridBagConstraints.NONE,
                                              new Insets(57, 34, 0, 0), 0, 0));
        String technicsNameNamedisp = QMMessage.getLocalizedMessage(RESOURCE,
                "technicsName", null);
        nameJTextField = new CappTextField(parent, technicsNameNamedisp, 40, false);
        add(nameJTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(20, 8, 62, 40), 0, 0));
        String technicsNumdisp = QMMessage.getLocalizedMessage(RESOURCE,
                "technicsNumber", null);
//      CCBegin by leixiao 2009-6-18 ԭ�򣺽��ϵͳ���� ,��ų���20->50
        numJTextField = new CappTextField(parent, technicsNumdisp, 50, false);
//      CCEnd by leixiao 2009-6-18 ԭ�򣺽��ϵͳ���� ,��ų���20->40
        add(numJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                                  , GridBagConstraints.WEST,
                                                  GridBagConstraints.HORIZONTAL,
                                                  new Insets(57, 8, 0, 40), 0,
                                                  0));
        localize();
    }


    /**
     * ������Ϣ���ػ�
     */
    private void localize()
    {
        numJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "technicsNumberJLabel", null));
        nameJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "technicsNameJLabel", null));
    }


    /**
     * ���ù��տ�ҵ�����
     * @param masterInfo ���տ�����Ϣ
     */
    public void setTechnics(QMTechnicsMasterInfo masterInfo)
    {
        technicsObject = masterInfo;
        initializeProperties();
    }


    /**
     * ��ù��տ�ҵ�����
     * @return  ���տ�����Ϣ
     */
    public QMTechnicsMasterInfo getTechnics()
    {
        return technicsObject;
    }


    /**
     * ��ʼ��ҵ���������ԣ����ƺͱ�ţ�
     * @throws QMRemoteException
     */
    private void initializeProperties()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsReguRenameJPanel.initializeProperties() begin...");
        }
        if (technicsObject != null)
        {
            //��õ�ǰ���������
            currentName = technicsObject.getTechnicsName();
            nameJTextField.setText(currentName);
            //��õ�ǰ����ı��
            currentNumber = technicsObject.getTechnicsNumber();
            numJTextField.setText(currentNumber);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsReguRenameJPanel.initializeProperties() end...return is void");
        }
    }


    /**
     * �ı�ҵ������Ψһ��ʶ
     * @return ���ĺ��ҵ������Ψһ��ʶ
     * @throws QMRemoteException
     * @see #setAttribute
     */
    public QMTechnicsMasterInfo changeIdentity()
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsReguRenameJPanel.changeIdentity() begin...");
        }
        boolean flag = false;
        //������յ�ԭ���ƻ�ԭ��ŷ������޸ģ�����������Ĺ������ƺͱ�ţ�
        //���������ݿ�
        if (nameJTextField.isShowing() && numJTextField.isShowing())
        {
            if (numJTextField.getText() == null || numJTextField.getText().trim() == null)
            {
                throw new QMRemoteException(RESOURCE,
                                            CappLMRB.NO_TECHNICS_NUMBER_ENTERED, null);
            }
            if (nameJTextField.getText() == null ||
                nameJTextField.getText().trim() == null)
            {
                throw new QMRemoteException(RESOURCE,
                                            CappLMRB.NO_TECHNICS_NAME_ENTERED, null);
            }

            //�����ź����ƶ�û�����κθ��ģ����׳��쳣
            if (currentName.equals(nameJTextField.getText().trim()) &&
                currentNumber.equals(numJTextField.getText().trim())
                )
            {

                //ͨ����ʶ������������ֵ�����Ӧ����ʾ��ʶ����
                DisplayIdentity displayidentity = IdentityFactory.
                                                  getDisplayIdentity(
                        technicsObject);
                String s = displayidentity.getLocalizedMessage(null);
                Object aobj[] =
                        {s};
                throw new QMRemoteException(RESOURCE, CappLMRB.NO_CHANGE_MADE,
                                            aobj);
            }
            if (currentName == null ||
                !currentName.equals(nameJTextField.getText().trim()))
            {
                flag = false;
            }
            if (currentNumber == null ||
                !currentNumber.equals(numJTextField.getText().trim()))
            {
                flag = true;
            }

            String oldname = technicsObject.getTechnicsName();
            String oldnum = technicsObject.getTechnicsNumber();
            //����������Ĺ�������
            technicsObject.setTechnicsName(nameJTextField.getText().trim());
            //������������㲿�����
            technicsObject.setTechnicsNumber(numJTextField.getText().trim());

            //���÷��񷽷����޸����ݿ��й��յı�ź�����
            ServiceRequestInfo info1 = new ServiceRequestInfo();
            info1.setServiceName("StandardCappService");
            info1.setMethodName("renameTechnicsMaster");
            Class[] paraClass1 =
                    {QMTechnicsMasterIfc.class, boolean.class};
            info1.setParaClasses(paraClass1);
            Object[] objs1 =
                    {technicsObject, new Boolean(flag)};
            info1.setParaValues(objs1);
            try
            {
                technicsObject = (QMTechnicsMasterInfo) server.request(info1);
            }
            catch (QMRemoteException ex)
            {
                technicsObject.setTechnicsName(oldname);
                technicsObject.setTechnicsNumber(oldnum);
                throw ex;
            }

            if (technicsObject instanceof MasteredIfc)
            {

                //���÷���VersionControlService�ķ���,����master���õ���������
                //���е�С�汾��������ͬ��֦����Ҳ����master��Ӧ������С�汾��
                ServiceRequestInfo info2 = new ServiceRequestInfo();
                info2.setServiceName("VersionControlService");
                info2.setMethodName("allIterationsOf");
                Class[] paraClass2 =
                        {MasteredIfc.class};
                info2.setParaClasses(paraClass2);
                Object[] objs2 =
                        {(MasteredIfc) technicsObject};
                info2.setParaValues(objs2);
                Collection result = null;
                try
                {
                    result = (Collection) server.request(info2);
                }
                catch (QMRemoteException ex)
                {
                    technicsObject.setTechnicsName(oldname);
                    technicsObject.setTechnicsNumber(oldnum);
                    throw ex;
                }

                //�����汾����ˢ��
                if (result != null)
                {
                    Object obj;
                    for (Iterator i = result.iterator(); i.hasNext();
                                      dispatchRefresh(obj, RefreshEvent.UPDATE))
                    {
                        obj = i.next();
                    }
                }
            }

            //����ˢ���¼���ˢ��ҵ������Ψһ��ʶ
            dispatchRefresh(technicsObject, RefreshEvent.UPDATE);
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsReguRenameJPanel.changeIdentity() end...return: " +
                    technicsObject);
        }
        return technicsObject;
    }


    /**
     * ���ݾ�����¼�������ʶ���¼�Ŀ�꣬����ˢ���¼�
     * @param obj target�¼�Ŀ��
     * @param i  ������¼�������ʶ
     */
    private void dispatchRefresh(Object obj, int i)
    {
        //ʵ����ˢ���¼�
        RefreshEvent refreshEvent = new RefreshEvent(getParent(), i, obj);
        //ָ��ˢ���¼�
        RefreshService.getRefreshService().dispatchRefresh(refreshEvent);

    }


    /**
     * ˢ�½���
     */
    public void refresh()
    {
        invalidate();
        doLayout();
        repaint();
    }


    /**
     * ������������Ƿ�������Чֵ
     * @return  �����������������Чֵ���򷵻�Ϊ��
     */
    protected boolean checkRequiredFields()
            throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsReguRenameJPanel.checkRequiredFields() begin...");
        }
        boolean isOK = false;

        isOK = numJTextField.check();
        if (isOK)
        {
            isOK = nameJTextField.check();

        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsReguRenameJPanel.checkRequiredFields() end...return: " +
                               isOK);
        }
        return isOK;

    }

}
