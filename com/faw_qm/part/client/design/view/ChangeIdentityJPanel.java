/** ���ɳ���ChangeIdentityJPanel.java	1.1  2003/02/28
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/12/21 ���� �޸�ԭ��TD2664:�㲿�������޶��󣬶�A�汾���㲿���������������������⡣
 *                              ������֮��û�ж����ϵ������汾���㲿������ˢ�¡�
 *                     �޸ķ����������ϵ������汾�㲿������ˢ�¡�
 * SS1 ���Ϊ�����������50��Ϊ200 xianglx 2014-12-17
 * SS2 ���Ϊ�ı�������30��Ϊ50 xianglx 2014-12-17
 */
package com.faw_qm.part.client.design.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.util.QMCt;
import com.faw_qm.util.TextValidCheck;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.version.model.VersionedIfc;
/**
 * <p>Title:����ҵ������Ψһ��ʶ����塣 </p>
 * <p>Description:���ڸ���ҵ���������ƺͱ�š� </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 * @see PartDesignRenameJDialog
 */

public class ChangeIdentityJPanel extends JPanel
{
    /**���л�ID*/
    static final long serialVersionUID = 1L;

    /**ҵ�����ı�����Ա�ǩ*/
    private JLabel numberJLabel = new JLabel();

    /**ҵ�������������Ա�ǩ*/
    private JLabel nameJLabel = new JLabel();

    /**ҵ����������Ե���ʾ�ı���*/
    private JTextField numberJTextField = new JTextField();

    /**ҵ������������Ե���ʾ�ı���*/
    private JTextField nameJTextField = new JTextField();

    /**�����������*/
    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    /**���ڱ����Դ��Ϣ·��*/
    protected static final String RESOURCE =
            "com.faw_qm.part.client.design.util.PartDesignViewRB";

    /**���ڱ����Դ��Ϣ*/
    protected static ResourceBundle resources = null;

    /**�ϼ�JApplet*/
    private JApplet parentApplet;

    /**ҵ������Ψһ��ʶ*/
    private QMPartMasterIfc identifiedObject;

    /**���ڱ�ǵ�ǰ����*/
    private String currentName;

    /**���ڱ�ǵ�ǰ���*/
    private String currentNumber;

    /**�߳���*/
    private ThreadGroup contextGroup;
MouseListener moulis=null;

    /**
     * ���캯����
     */
    public ChangeIdentityJPanel()
    {
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
         * ���캯����
         */
        public ChangeIdentityJPanel(MouseListener moulis)
        {
            try
            {
              this.moulis=moulis;
                jbInit();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    /**
     * ��ʼ����
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        //��õ�ǰ�߳���
        contextGroup = QMCt.getContext().getThreadGroup();
        //JLabel
        nameJLabel.setText("*Name:");
        if(moulis!=null)
        {
          nameJLabel.setName(QMMessage.getLocalizedMessage(
                    RESOURCE,
                    "renamename",null));
          nameJLabel.addMouseListener(moulis);
        }
        nameJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        nameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        nameJLabel.setBounds(new Rectangle(28, 120, 44, 18));
        numberJLabel.setText("*Number:");
         if(moulis!=null)
         {
           numberJLabel.setName(QMMessage.getLocalizedMessage(
                    RESOURCE,
                    "renamenum",null)
);
           numberJLabel.addMouseListener(moulis);
         }
        numberJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        numberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numberJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        numberJLabel.setBounds(new Rectangle(28, 78, 44, 18));
        this.setLayout(gridBagLayout1);
        this.setSize(400, 193);
        this.add(numberJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(60, 8, 0, 44), 272, 0));
        this.add(numberJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(60, 28, 0, 0), 12, 0));
        this.add(nameJLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(20, 28, 54, 0), 12, 0));
    if(moulis!=null)
          {
            nameJTextField.setName(QMMessage.getLocalizedMessage(
                     RESOURCE,
                     "renamename",null)
 );
            nameJTextField.addMouseListener(moulis);
          }

          if(moulis!=null)
                  {
                    numberJTextField.setName(QMMessage.getLocalizedMessage(
                             RESOURCE,
                             "renamenum",null)
         );
                    numberJTextField.addMouseListener(moulis);
                  }

        this.add(nameJTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(20, 8, 54, 44), 272, 0));
        localize();
    }


    /**
     * ��ʼ����Դ��Ϣ��
     */
    private void initResources()
    {
        try
        {
            resources = ResourceBundle.getBundle(RESOURCE,
                                                 getContext().getLocale());
            return;
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
            DialogFactory.showErrorDialog(this, PartDesignViewRB.MISS_RESOURCER);
        }
    }


    /**
     * ��Դ��Ϣ���ػ���
     */
    private void localize()
    {
        try
        {
            if (resources == null)
            {
                initResources();
            }
            nameJLabel.setText(QMMessage.getLocalizedMessage(
                    RESOURCE,
                    "nameJLabel",
                    null));
            numberJLabel.setText(QMMessage.getLocalizedMessage(
                    RESOURCE,
                    "numberJLabel",
                    null));
            return;
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
            DialogFactory.showErrorDialog(this, PartDesignViewRB.MISS_RESOURCER);
        }
    }


    /**
     * �����ϼ�JApplet��
     * @param applet  �ϼ�JApplet��
     */
    public void setParentJApplet(JApplet applet)
    {
        parentApplet = applet;
    }


    /**
     * ����ϼ�JApplet��
     * @return  �ϼ�JApplet��
     */
    public JApplet getParentJApplet()
    {
        return parentApplet;
    }

    /**
     * ����ҵ������Ψһ��ʶ��
     * @param identified  ҵ������Ψһ��ʶ��
     * @throws QMRemoteException
     * @see #initializeProperties
     */
    public void setIdentifiedObject(QMPartMasterIfc identified)
            throws QMRemoteException
    {
        PartDebug.debug("setIdentifiedObject(identified) begin ....", this,
                        PartDebug.PART_CLIENT);
        identifiedObject = identified;
        //��ʼ��ҵ���������ԣ����ƺͱ�ţ�
        initializeProperties();
        PartDebug.debug("setIdentifiedObject(identified) end ...return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * ���ҵ������Ψһ��ʶ��
     * @return ҵ������Ψһ��ʶ��
     */
    public QMPartMasterIfc getIdentifiedObject()
    {
        return identifiedObject;
    }

    public void replace()
    {
        identifiedObject.setPartName(currentName);
        identifiedObject.setPartNumber(currentNumber.toUpperCase());//liunan
    }


    /**
     * �ı�ҵ������Ψһ��ʶ��
     * @return ���ĺ��ҵ������Ψһ��ʶ��
     * @throws QMRemoteException
     * @see #setAttribute
     */
    public QMPartMasterIfc changeIdentity()
            throws QMRemoteException,QMException/////////2005.8.12
    {
        PartDebug.debug("changeIdentity() begin ....", this,
                        PartDebug.PART_CLIENT);
        boolean flag = false;
        //����㲿����ԭ���ƻ�ԭ��ŷ������޸ģ�������������㲿�����ƺͱ�ţ�
        //���������ݿ�
        if (nameJTextField.isShowing() && numberJTextField.isShowing()&&
            !(currentName.equals(nameJTextField.getText().trim()) &&
            currentNumber.equals(numberJTextField.getText().trim().toUpperCase())//liunan
            ))
        {
            if (currentName == null ||
                !currentName.equals(nameJTextField.getText().trim()))
            {
                flag = false;
            }
            if (currentNumber == null ||
                !currentNumber.equals(numberJTextField.getText().trim().toUpperCase()))//liunan
            {
                flag = true;
            }
            //������������㲿������
            identifiedObject.setPartName(nameJTextField.getText().trim());
            //������������㲿�����
            identifiedObject.setPartNumber(numberJTextField.getText().trim().toUpperCase());
//liunan
            PartDebug.debug(">>>>>>>>>>>>> new Number = " +
                            identifiedObject.getPartNumber(),
                            PartDebug.PART_CLIENT);
            PartDebug.debug(">>>>>>>>>>>>> new Name = " +
                            identifiedObject.getPartName(),
                            PartDebug.PART_CLIENT);
            PartDebug.debug(">>>>>>>>>>>>> new flag = " + flag,
                            PartDebug.PART_CLIENT);
            PartDebug.debug("ChangeIdentityJPanel: renamePartMaster() begin...", this,
                            PartDebug.PART_CLIENT);
            //���÷��񷽷����޸����ݿ����㲿���ı�ź�����
            try
            {
                identifiedObject = PartServiceRequest.renamePartMaster(
                        identifiedObject, flag);
            }
            catch (QMException ex)
            {
                throw new QMException(ex);/////////2005.8.12
            }
            PartDebug.debug("renamePartMaster() end...return " +
                            identifiedObject, this, PartDebug.PART_CLIENT);
            boolean ref=true;
            if (identifiedObject instanceof MasteredIfc)
            {
                PartDebug.debug(
                        "VersionControlService.allIterationsOf() begin...", this,
                        PartDebug.PART_CLIENT);
              PartConfigSpecIfc config = PartServiceRequest.findPartConfigSpecIfc();
              QMPartIfc part=PartServiceRequest.getPartByConfigSpec(identifiedObject, config);
              if(part!=null)
              {
            	  dispatchRefresh(part, RefreshEvent.UPDATE);
            	  ref=false;
            	  //CCBegin by leixiao 2009-12-24 �򲹶���v4r3_p003_20091223��TD2664
            	  Class[] classes = {VersionedIfc.class};//Begin CR1
                  Object[] objects = {part};
                  Collection col = (Collection)IBAUtility
                          .invokeServiceMethod("VersionControlService",
                                  "allVersionsOf", classes, objects);
                  if(col != null && col.size() != 0)
                  {
                      Iterator iterator = col.iterator();
                      while(iterator.hasNext())
                      {
                          QMPartIfc qmpart = (QMPartIfc)iterator.next();
                          dispatchRefresh(qmpart, RefreshEvent.UPDATE);
                      }
                  }//End CR1
                   //CCBegin by leixiao 2009-12-24 �򲹶���v4r3_p003_20091223
              }
//                //���÷���VersionControlService�ķ���,����master���õ���������
//                //���е�С�汾��������ͬ��֦����Ҳ����master��Ӧ������С�汾��
//                Class[] paraClass2 = {MasteredIfc.class};
//                Object[] objs2 = {(MasteredIfc) identifiedObject};
//                Collection result = null;
//                try
//                {
//                    result = (Collection) IBAUtility.invokeServiceMethod(
//                            "VersionControlService", "allIterationsOf",
//                            paraClass2, objs2);
//                }
//                catch (QMRemoteException ex)
//                {
//                    ex.printStackTrace();
//                    DialogFactory.showErrorDialog(this, ex.getClientMessage());
//                }
//                PartDebug.debug(
//                        "VersionControlService.allIterationsOf() end ...return:result", this,
//                        PartDebug.PART_CLIENT);
//                //������°汾
//                if (result != null)
//                {
//                    Object obj;
//                    Iterator i = result.iterator();
//                    if(i.hasNext())
//                    {
//                        obj = i.next();
//                        dispatchRefresh(obj, RefreshEvent.UPDATE);
//                    }
////                    for (Iterator i = result.iterator(); i.hasNext();
////                                      dispatchRefresh(obj, RefreshEvent.UPDATE))
////                    {
////                        obj = i.next();
////                    }
//                }
            }
            //����ˢ���¼���ˢ��ҵ������Ψһ��ʶ
            if(ref)
            dispatchRefresh(identifiedObject, RefreshEvent.UPDATE);
            Class[] aa = {String.class};
            Object[] objs2 = { identifiedObject.getPartNumber()};
            Collection result = null;
            try
            {
                result = (Collection) IBAUtility.invokeServiceMethod(
                        "EPMDocumentService", "renameCADFile",
                        aa, objs2);
            }
            catch (QMRemoteException ex)
            {
                ex.printStackTrace();
                DialogFactory.showErrorDialog(this, ex.getClientMessage());
            }
        }
        //�����ź����ƶ�û�����κθ��ģ����׳��쳣
        if (currentName.equals(nameJTextField.getText().trim()) &&
            currentNumber.equals(numberJTextField.getText().trim())
            )
        {
            //ͨ����ʶ������������ֵ�����Ӧ����ʾ��ʶ����
            DisplayIdentity displayidentity = IdentityFactory.
                                              getDisplayIdentity(
                    identifiedObject);
            String s = displayidentity.getLocalizedMessage(null);
            Object aobj[] = {s};
            throw new QMRemoteException(null,
                                        RESOURCE,
                                        PartDesignViewRB.NO_CHANGES_MADE,
                                        aobj);
        }
        else
        {
            PartDebug.debug("changeIdentity() end ...return " +
                            identifiedObject, this, PartDebug.PART_CLIENT);
            return identifiedObject;
        }

    }


    /**
     * ��ʼ��ҵ���������ԣ����ƺͱ�ţ���
     * @throws QMRemoteException
     */
    protected void initializeProperties()
            throws QMRemoteException
    {
        PartDebug.debug("initializeProperties() begin ....", this,
                        PartDebug.PART_CLIENT);
        if (identifiedObject != null)
        {
            //��õ�ǰ���������
            currentName = identifiedObject.getPartName();
            nameJTextField.setText(currentName);
            //��õ�ǰ����ı��
            currentNumber = identifiedObject.getPartNumber();
            numberJTextField.setText(currentNumber);
        }
        PartDebug.debug("initializeProperties() end ...return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * ���Context��
     * @return QMCt
     */
    protected QMCt getContext()
    {
        PartDebug.debug("getContext() begin...", this, PartDebug.PART_CLIENT);
        QMCt qmcontext = null;
        if (contextGroup != null)
        {
            qmcontext = QMCt.getContext(contextGroup);
        }
        else if (parentApplet != null)
        {
            qmcontext = QMCt.getContext(parentApplet);
        }
        if (qmcontext == null)
        {
            qmcontext = QMCt.getContext();
        }
        PartDebug.debug("getContext() end...return qmContext", this,
                        PartDebug.PART_CLIENT);
        return qmcontext;
    }


    /**
     * ��ñ�����Դ��Ϣ��
     * @param s ��Դ��Ϣ��
     * @param aobj ��Դ��Ϣ�еĲ�����
     * @return s1  ���ػ������Դ��Ϣ��
     */
    protected String getLocalizedMessage(String s, Object aobj[])
    {
        String s1 = "";
        if (resources == null)
        {
            initResources();
        }
        if (aobj != null)
        {
            s1 = MessageFormat.format(resources.getString(s), aobj);
        }
        else
        {
            s1 = resources.getString(s);
        }
        return s1;
    }


    /**
     * ���ݾ�����¼�������ʶ���¼�Ŀ�꣬����ˢ���¼���
     * @param obj target�¼�Ŀ�ꡣ
     * @param i  ������¼�������ʶ��
     */
    protected void dispatchRefresh(Object obj, int i)
    {
        PartDebug.debug("dispatchRefresh() begin ....", this,
                        PartDebug.PART_CLIENT);
        //ʵ����ˢ���¼�
        RefreshEvent refreshEvent = new RefreshEvent(getParent(), i, obj);
        //ָ��ˢ���¼�
        RefreshService.getRefreshService().dispatchRefresh(refreshEvent);

        PartDebug.debug("dispatchRefresh() end ...return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * ��������Ƿ񼤻�ķ�����
     * @param flag ����ֵ��
     */
    public void enablePanel(boolean flag)
    {
        if (isAncestorOf(nameJTextField))
        {
            nameJTextField.setEnabled(flag);
        }
        if (isAncestorOf(numberJTextField))
        {
            numberJTextField.setEnabled(flag);
        }
    }


    /**
     * ˢ�½��档
     */
    public void refresh()
    {
        invalidate();
        doLayout();
        repaint();
    }


    /**
     * ������������Ƿ�������Чֵ��
     * @return  �����������������Чֵ���򷵻�Ϊ�档
     */
    protected boolean checkRequiredFields()
    {
        PartDebug.debug("checkRequiredFields() begin....", this,
                        PartDebug.PART_CLIENT);
        boolean isOK = true;
        String message = "fell through ";
        String title = "";
        //lily lin 2004.8.16
      //  Validate validate = new Validate();
        //!--lily lin ���,�ж�"����","���"�Ƿ���ͨ���eg: %\/:*?"<>|


        try
        {
        	//2007.05.29 whj
        	 //�������Ƿ�Ϊ�գ����Ҳ�����ͨ���
        	TextValidCheck numbervalidate = new TextValidCheck(QMMessage.getLocalizedMessage(RESOURCE,
            		"numberLbl",
//CCBegin SS2
//                    null),1,30);
                    null),1,50);
//CCBEnd SS2
        	numbervalidate.check(numberJTextField.getText().trim(),true);
        	TextValidCheck namevalidate = new TextValidCheck(QMMessage.getLocalizedMessage(RESOURCE,
            		"nameLbl",
//CCBegin SS1
//                    null),1,50);
                    null),1,200);
//CCBEnd SS1
        	namevalidate.check(nameJTextField.getText().trim(),true);
        }
        catch(QMRemoteException re)
        {
        	message=re.getClientMessage();
        	 isOK = false;
        }
//        if (validate.validateString(numberJTextField.getText().trim()) == false)
//        {
//            message = QMMessage.getLocalizedMessage(RESOURCE,
//                    PartDesignViewRB.NUM_ENTERWRONG,
//                    null);
//            isOK = false;
//        }
//        //���������Ƿ�Ϊ��,���Ҳ�����ͨ���
//        else if (validate.validateString(nameJTextField.getText().trim()) == false)
//        {
//            message = QMMessage.getLocalizedMessage(RESOURCE,
//                    PartDesignViewRB.NAME_ENTERWRONG,
//                    null);
//            isOK = false;
//        }
        //-->
//        else if (numberJTextField.getText().trim().length() > 30)
//        {
//            message = QMMessage.getLocalizedMessage(RESOURCE, PartDesignViewRB.NUM_LENGTHWRONG, null);
//            isOK = false;
//        }
//        else if (nameJTextField.getText().trim().length() > 50)
//        {
//            message = QMMessage.getLocalizedMessage(RESOURCE, PartDesignViewRB.NAME_LENGTHWRONG, null);
//            isOK = false;
//        }
//        else
//        {
//            isOK = true;
//        }
        if (!isOK)
        {
            DialogFactory.showWarningDialog(this, message);
        }
        PartDebug.debug("checkRequiredFields() end....return is " + isOK, this,
                        PartDebug.PART_CLIENT);
        return isOK;
    }
}
