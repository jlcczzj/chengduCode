/** ���ɳ���DescribedByJPanel.java	1.1  2003/02/28
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/11/26 ����  ԭ�򣺲�Ʒ��Ϣ����������Tabҳ��Ϣһ��ȫ����ʼ����
 *                     ��������ע�ĸ�Tabҳ�ͳ�ʼ���ĸ�Tabҳ��
 */
package com.faw_qm.part.client.design.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyVetoException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.faw_qm.clients.beans.AssociationsPanel;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.design.model.DescribedByAssociationsModel;
import com.faw_qm.part.client.design.model.PartItem;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.client.main.controller.QMProductManager;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.util.QMCt;


/**
 * <p>Title: ����������塣</p>
 * <p>Description:�㲿�����������Ŀ��ӻ����档�ڱ������㲿����������������</p>
 * <p>���������¡�ɾ���Ȳ�����</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 * @see DescribedByAssociationsModel,AssociationsPanel
 */

public class DescribedByJPanel extends JPanel
{
    /**���л�ID*/
    static final long serialVersionUID = 1L;

    /**�༭������ϵ��壨JavaBean��*/
    private AssociationsPanel describedByAssociationsPanel;

    /**�༭������ϵ����ģ��*/
    private DescribedByAssociationsModel model;

    /**���ڱ�ǽ���ģʽ*/
    public String mode;

    /**���ڱ����Դ��Ϣ·��*/
    protected static final String RESOURCE =
            "com.faw_qm.part.client.design.util.PartDesignViewRB";

    /**�߳���*/
    private ThreadGroup contextThreadGroup;

    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private boolean isReference=false;
    //��ͷ
	private String[] deslistheading = null;

	//��ñ�ͷ�ķ���
	private String[] headingMethod1;
	private String[] headingMethodEPM;//EPM�ĵ�������ֵ��ȡ�ķ�����
    /**�Ƿ�����Ϣ���������õı�־������Ϊtrue*/
    private boolean isparttask = false;
    private boolean isShown;//��Tabҳ����ʾ��־//CR1
    
    /**
     * ���캯����
     */
    public DescribedByJPanel()
    {
        super();
        try
        {
        	setHeadingMethods();
        	setDesListHeading();
        	setHeadingMethodsEPM();
        	
            jbInit();
            isShown = false;//CR1
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    //add by muyp 081118 begin
    /**
     * ���캯����һ��������flag�����ж��Ƿ����ʾ���ù��ܡ�
     * @param flag boolean true�����ʾ���ù��ܡ�
     */
    public DescribedByJPanel(boolean flag)
    {
        super();
        try
        {
        	isparttask = flag;
        	setHeadingMethods();
        	setDesListHeading();
        	setHeadingMethodsEPM();
        	
            jbInit();
            isShown = false;//CR1
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }//end


    /**
     * ��ʼ����
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        this.setSize(620, 206);
        this.setLayout(gridBagLayout1);

        //��ʼ��������ϵ���
        try
        {
        	if(this.isparttask)
        	{
        		describedByAssociationsPanel = new AssociationsPanel(true,isparttask);
        	}
        	else
        		describedByAssociationsPanel = new AssociationsPanel(true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    PartDesignViewRB.ASSOPL_INIT_FAIL, null);
            //�쳣����ʼ��������ϵ���ʧ��
            JOptionPane.showMessageDialog(this,
                                          message,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }
        //��ʼ��������ϵ���ģ��
        model = new DescribedByAssociationsModel();
        //��ʼ����ģʽΪ���鿴��
        mode = "View";
        //��õ�ǰ�߳���
        contextThreadGroup = QMCt.getContext().getThreadGroup();
        //���û����Add��ťʱ���������������ÿ�ѡ�����б�,ȷ��Ҫѡ��Ķ�������ͣ�����ΪDoc��
        describedByAssociationsPanel.setChooserOptions("2");
        //����ҵ�����ڹ����еĽ�ɫ
        describedByAssociationsPanel.setRole("describes");
        //���ö����б���б���
        try
        {
//            String as[] = new String[3]; //������ע��
//            as[0] = "translateNumber";
//            as[1] = "translateName";
//            as[2] = "translateVersion";
            describedByAssociationsPanel.setLabels(deslistheading);
        }
        catch (PropertyVetoException _ex)
        {
            _ex.printStackTrace();
        }
        //���ù�������
        try
        {
            describedByAssociationsPanel.setLinkClassName(
                    "com.faw_qm.part.model.PartDescribeLinkInfo");
        }
        catch (QMRemoteException qre)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
            JOptionPane.showMessageDialog(this,
                                          qre.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        describedByAssociationsPanel.setHeadingMethods(headingMethod1);
//      ����EPM�ĵ���ͷ����ֵ��ȡ������
        describedByAssociationsPanel.setHeadingMethodsEPM(headingMethodEPM);
        //����ҵ������
        try
        {
            describedByAssociationsPanel.setObjectClassName(
                    "com.faw_qm.part.model.QMPartInfo");
        }
        catch (ClassNotFoundException _ex)
        {
            _ex.printStackTrace();
        }

//        String as1[] = new String[3]; //������ ע��
//        as1[0] = "docNum";
//        as1[1] = "docName";
//        as1[2] = "versionID";
        //���ý���ʾ�ڶ����б��й�����ҵ���������Լ�
        describedByAssociationsPanel.setOtherSideAttributes(headingMethod1);
        //���ù���ҵ���������
        try
        {
            describedByAssociationsPanel.setOtherSideClassName(
                    "com.faw_qm.doc.model.DocInfo");
        }
        catch (ClassNotFoundException _ex)
        {
            _ex.printStackTrace();
        }
        //��������
        describedByAssociationsPanel.setFont(PartScreenParameter.getFont());
        //���ý���Ϊ�༭ģʽ
        describedByAssociationsPanel.setMode("Edit");
        ((PartMultiList)(describedByAssociationsPanel.getMultiList())).setUpdate(true);
        ((PartMultiList)(describedByAssociationsPanel.getMultiList())).setParentPanel(describedByAssociationsPanel);
        
      
        //���沼��
        add(describedByAssociationsPanel,
            new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                   , GridBagConstraints.CENTER,
                                   GridBagConstraints.BOTH,
                                   new Insets(0, 0, 0, 0), 0, 0));
        //���óߴ�
        describedByAssociationsPanel.setBounds(15, 15, 590, 240);
        //�����Ƿ�ֻ������Ĺ���
        describedByAssociationsPanel.setSaveUpdatesOnly(true);
        //��AssociationsPanel�·���AttributeForm����ɾ��
        describedByAssociationsPanel.removeAttributeForm();
        //����ֻ����ĵ���ÿ����汾�����°汾
        describedByAssociationsPanel.setLastIteration(true);
        //�����б����ݲ�������
        describedByAssociationsPanel.getMultiList().setAllowSorting(false);

        //��Դ��Ϣ���ػ�
//        localize();//������ ע��

    }


    /**
     * ��Դ��Ϣ���ػ���
     */
    protected void localize()
    {
        try
        {
            String as[] = new String[3];
            as[0] = QMMessage.getLocalizedMessage(RESOURCE, "numberHeading", null,
                                                  getContext().getLocale());
            as[1] = QMMessage.getLocalizedMessage(RESOURCE, "nameHeading", null,
                                                  getContext().getLocale());
            as[2] = QMMessage.getLocalizedMessage(RESOURCE, "versionHeading", null,
                                                  getContext().getLocale());
            describedByAssociationsPanel.setLabels(as);
            return;
        }
        catch (PropertyVetoException _ex)
        {
            _ex.printStackTrace();
            return;
        }
    }


    /**
     * ����������������ģʽ��
     * @param s ��ʾģʽ��
     */
    public void setMode(String s)
    {
        PartDebug.debug("setMode(s) begin...", this, PartDebug.PART_CLIENT);
        if (describedByAssociationsPanel != null)
        {
            //���ý���ģʽ
            describedByAssociationsPanel.setMode(s);
            PartMultiList multiList = (PartMultiList)describedByAssociationsPanel.getMultiList();
            if(s.equals("Edit"))
            {
                multiList.setUpdate(true);
                multiList.setParentPanel(describedByAssociationsPanel);
            }
            else
            {
                multiList.setUpdate(false);
                multiList.setParentPanel(describedByAssociationsPanel);
            }

        }
        PartDebug.debug("setMode(s) end...return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * ����PartItem����
     * @param  partItem  PartItem����
     */
    public void setPartItem(PartItem partItem)
    {
        PartDebug.debug("setPartItem(partItem) begin...", this,
                        PartDebug.PART_CLIENT);
        //����ģ�͵�ҵ�����
        model.setPart(partItem.getPart());

        try
        {
            //����ģ��
        	describedByAssociationsPanel.setReference(getReference());
            describedByAssociationsPanel.setModel(model);
            //���ý�Ҫ���鿴��༭��ҵ�����
            describedByAssociationsPanel.setObject(model.getPart());
            PartDebug.debug("setPartItem(partItem) end...return is void", this,
                            PartDebug.PART_CLIENT);
            return;
        }
        catch (PropertyVetoException _ex)
        {
            _ex.printStackTrace();
            return;
        }

    }


    /**
     * ��������������
     * @param partItem PartItem����
     * @return   ����ɹ��򷵻�True,���򷵻�false��
     * @throws QMRemoteException
     */
    public boolean save(PartItem partItem)
            throws QMRemoteException
    {
        PartDebug.debug("save(partItem) begin ....", this,
                        PartDebug.PART_CLIENT);
        boolean flag = false;
        if (describedByAssociationsPanel != null)
        {
            //���Ҫ����Ĺ����㲿��
            QMPartIfc part = partItem.getPart();
            //�������
            QMPartIfc partIfc =
                    (QMPartIfc) describedByAssociationsPanel.save(part);
            //�ѱ���Ĺ�������ģ��
            model.setPart(partIfc);

            flag = true;
        }
        PartDebug.debug("save(partItem) end ...return " + flag, this,
                        PartDebug.PART_CLIENT);
        return flag;
    }


    /**
     * ���ý�����ʾģʽ���༭��鿴����
     * @param flag ���flag = true ,��mode = Edit;����mode = View��
     */
    public void setUpdateMode(boolean flag)
    {
        mode = flag ? "Edit" : "View";
        setMode(mode);
    }


    /**
     * ���Context��
     * @return  qmcontext  QMCt
     */
    public QMCt getContext()
    {
        QMCt qmcontext = null;
        if (contextThreadGroup != null)
        {
            qmcontext = QMCt.getContext(contextThreadGroup);
        }
        if (qmcontext == null)
        {
            qmcontext = QMCt.getContext();
        }
        return qmcontext;
    }
    
    /**
     * �����������Ƿ�ı��־��ֵ��
     * @return  boolean
     */
    public boolean isChanged(){
    	return describedByAssociationsPanel.isDirty();
    }
    
    /**
     * ������������Ƿ�ı��־��ֵ��
     * @param flag boolean
     */
    public void setChanged(boolean flag){
    	describedByAssociationsPanel.setDirty(flag);
    }
    /**
     * set for capp ���ε���������2007.06.29 ������
     * @param manage
     */
    public void setManager(QMProductManager manage)
    {
    	
    	 if( manage.fromcapp)
    	        ((PartMultiList)(describedByAssociationsPanel.getMultiList())).setFromCapp(true);
    }
    
    public void setReference(boolean flag)
    {
    	isReference=flag;
    }
    public boolean getReference()
    {
    	return isReference;
    }
    
    /////add by muyp 20080922
    /**
     * �����б��ͷ
     */
    private void setDesListHeading()
    {
    	String s = "description";
    	try {
    		deslistheading = (String[])PartServiceRequest.getListHead(s);
		} catch (QMException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
    }
    
    /**
     * ��ʼ��HeadingMethods
     * @return void:void��
     */
    private void setHeadingMethods()
            throws PropertyVetoException
    {
    	try
    	{
    		headingMethod1 = PartServiceRequest.getListMethod("description");
    	}
    	catch(QMException e)
    	{
    		e.printStackTrace();
    	}
    }
    /**
     * ���get�б�ͷ�ķ������ļ���
     * @return
     */
    public String[] getHeadingMethods()
    {
    	return headingMethod1;
    }
    
    /**
     * �ø����������ʼ��HeadingMethodsEPM��EPM�ĵ���
     * @param as[]:�����ĵ����б���
     * @return void:void��
     */
    private void setHeadingMethodsEPM()
            throws PropertyVetoException
    {
    	headingMethodEPM = new String[headingMethod1.length];
         for(int i=0;i<headingMethod1.length;i++)
         {
        	 if(headingMethod1[i].equals("getDocNum"))
        	 {
        		 headingMethodEPM[i]="getDocNumber";
        	 }
        	 else if(headingMethod1[i].equals("getDocName")||headingMethod1[i].equals("getVersionID"))
        	 {
        		 headingMethodEPM[i]=headingMethod1[i];
        	 }
         }
            
    }
    
    /**
     * �����ʾ���ð�ť
     */
    public JButton getShowAttrSettedJButton()
    {
    	return describedByAssociationsPanel.getShowAttrSettedJButton();
    }
    
    public PartMultiList getDescDocList()
    {
    	return describedByAssociationsPanel.getMultiList();
    }
    
    public AssociationsPanel getDescPanel()
    {
    	return describedByAssociationsPanel;
    }
    //end
    /**
     * ����Tabҳ��ʾ��־
     */
    public void setIsShown(boolean isShown)//CR1
    {
        this.isShown = isShown;
    }
    /**
     * ��ȡTabҳ��ʾ��־
     * @return
     */
    public boolean getIsShown()
    {
        return isShown;
    }
}
