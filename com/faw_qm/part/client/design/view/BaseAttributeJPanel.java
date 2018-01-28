/** ���ɳ���DescribedByJPanel.java	1.1  2003/02/28
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/06/19 л�� ԭ�򣺻������Խ��桰״̬����û�ж��롣TD-2233
 *                     �����������������ں���״̬ʹ�õ���һ����壬����Ԫ�ر仯ʱ�޷�����ͳһ��Ӧ���ָ�Ϊ״̬Ϊ������壬����ͳһ��Ӧ����仯��
 * CR2 2009/07/03 ��� ԭ��TD2515 �����Ʒ��Ϣ������������ĸ��ڵ��Ʒ�ṹ,�ұ߻�������ҳ���ϼд���ʾ��\Root\Administrator"��
 *                          ���㲿���ϰѽ����ƻظ����㣬�������������������Ϣ��
 *                     �����������ڸ����ʱ����ջ�������������Ϣ 
  //CCBegin by leixiao 2009-12-16 �򲹶�v4r3_p001_20091215
 * CR3 2009/11/26 ����  ԭ�򣺲�Ʒ��Ϣ����������Tabҳ��Ϣһ��ȫ����ʼ����
 *                     ��������ע�ĸ�Tabҳ�ͳ�ʼ���ĸ�Tabҳ��
 * CR4 2009/11/30 ���� ԭ���޸�"״̬"ֵ��������������
 * 
 * CR5 2009/12/02 ���� ԭ��û�ж��㲿�������޸�Ҳ�ᵯ���Ի����Ƿ񱣴��*�㲿�����޸ġ���                  
  //CCEnd by leixiao 2009-12-16 �򲹶�v4r3_p001_20091215
 *CCBegin By leixiao��2009-12-08 �����Ƶ���ɫ����ʶ��parttaskjpanel�Ƶ�BaseAttributeJPanel
 */
package com.faw_qm.part.client.design.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.MissingResourceException;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.faw_qm.clients.beans.ViewChoice;
import com.faw_qm.clients.beans.folderPanel.FolderPanel;
import com.faw_qm.clients.beans.lifecycle.LifeCycleInfo;
import com.faw_qm.clients.beans.lifecycle.LifeCyclePanel;
import com.faw_qm.clients.prodmgmt.HelperPanel;
import com.faw_qm.clients.prodmgmt.PartAttributesPanel;
import com.faw_qm.clients.util.EnumeratedChoice;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.folder.model.SubFolderInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.QMPropertyException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.client.design.model.PartItem;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.Unit;
import com.faw_qm.viewmanage.model.ViewObjectInfo;


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

public class BaseAttributeJPanel extends HelperPanel
{
    /**���л�ID*/
    static final long serialVersionUID = 1L;   

    /**���ڱ����Դ��Ϣ·��*/
    protected static final String RESOURCE =
            "com.faw_qm.part.client.design.util.PartDesignViewRB";

    /**������壨�������ں���Ŀ�飩*/
    private LifeCycleInfo lifeCycleInfoPanel = new LifeCycleInfo();
    
    private JPanel lifeCycleStatePanel = new JPanel(); 
    
    private JLabel viewStateLabel = new JLabel();
    
    private JLabel viewStateValue = new JLabel();

    /**������壨��š����ơ���Դ�����ͣ�*/
    private PartAttributesPanel beanAttributesPanel;

    /**��ͼ��ǩ*/
    private JLabel viewJLabel = new JLabel();

    /**�����㲿��ʱ��������ʾ�㲿����ͼ*/
    private JLabel viewDisplayJLabel = new JLabel();

    /**��ͼѡ����*/
    private ViewChoice viewChoice = new ViewChoice();

    /**���ϼ����*/
    private FolderPanel folderPanel = new FolderPanel();
    
    /**������ʾ�汾�š���ͼ�͵�λ�����*/
    private JPanel changeLabelJPanel = new JPanel(new GridBagLayout());

    /**��λѡ���*/
    private EnumeratedChoice unitChoice = new EnumeratedChoice();

    /**��λ��ǩ*/
    private JLabel unitJLabel = new JLabel();

    /**�����㲿��ʱ��������ʾ�㲿����λ*/
    private JLabel unitDisplayJLabel = new JLabel();

    /**���ڻ��ϵͳ�ĵ�ǰ�汾*/
    private static String PART_CLIENT = RemoteProperty.getProperty(
            "part_client_customize_earmark", "A");

    /**�汾��ǩ*/
    private JLabel versionJLabel = new JLabel();

    /**�����㲿��ʱ��������ʾ�汾�ı�ǩ*/
    private JLabel displayVersionJLabel = new JLabel();
    
    //liyz add �����ߺʹ���ʱ�����Ϣ��� 
    //�û���Ϣ���
	private JPanel userJPanel = new JPanel(new GridBagLayout());
	//ʱ����Ϣ���
	private JPanel dateJPanel = new JPanel(new GridBagLayout());
	//�����߱�ǩ
	private JLabel createUserJLabel = new JLabel();
	//�����߱�ǩ
	private JLabel updateUserJLabel = new JLabel();
	//����ʱ���ǩ
	private JLabel createDateJLabel = new JLabel();
	//����ʱ���ǩ
	private JLabel updateDateJLabel = new JLabel();
	//��ʾ�����߱�ǩ
	private JLabel createUserValue = new JLabel();
	//��ʾ�����߱�ǩ
	private JLabel updateUserValue = new JLabel();
	//��ʾ����ʱ���ǩ
	private JLabel createDateValue = new JLabel();
	//��ʾ����ʱ���ǩ
	private JLabel updateDateValue = new JLabel();
	
	//liyz add �����Ϣ���
	private PartChangePanel partChangePanel = new PartChangePanel();
	private JScrollPane jScrollpane=new JScrollPane();
	
	//liyz add ����״̬���
	private JPanel workableStateJPanel = new JPanel(new GridBagLayout());
	//����״̬��ǩ
	private JLabel workableStateJLabel = new JLabel();
    
	//��ʾ����״̬��ǩ
	private JLabel workableStateValue = new JLabel();
    
    private boolean mainPart=true;  

    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    //CCBegin by leixiao 2009-12-16 �򲹶�v4r3_p001_20091215
    private boolean isShown;//��Tabҳ�Ƿ���ʾ�ı�־//CR3
    //CCEnd by leixiao 2009-12-16 �򲹶�v4r3_p001_20091215
    
    //CCBegin by liunan 2008-07-24
    JLabel colorFlag = new JLabel();
    JCheckBox colorFlagCheckBox = new JCheckBox();
    //CCEnd by liunan 2008-07-24

  
    /**
     * ���캯����
     */
    public BaseAttributeJPanel(boolean flag)
    {
        super();
        mainPart=flag;
        //CCBegin by leixiao 2009-12-16 �򲹶�v4r3_p001_20091215
        isShown = false;//CR3
        //CCEnd by leixiao 2009-12-16 �򲹶�v4r3_p001_20091215
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
     * ��ʼ����
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        this.setSize(620, 206);
        this.setLayout(gridBagLayout1);
        //��ʼ��PartAttributesPanel Bean
        beanAttributesPanel = new PartAttributesPanel();
        beanAttributesPanel
                .setObjectClassName("com.faw_qm.part.client.design.model.PartItem");
        beanAttributesPanel.setAttributes(new String[]{"Number", "Name",
                "Source", "Type"});
        beanAttributesPanel.setLabels(new String[]{
                getLabelsRB().getString("required")
                        + getLabelsRB().getString("numberLbl"),
                getLabelsRB().getString("required")
                        + getLabelsRB().getString("nameLbl"),
                getLabelsRB().getString("sourceLbl"),
                getLabelsRB().getString("typeLbl")});
        beanAttributesPanel.setEdits(new String[]{"true", "true", "true",
                "true"});
        viewJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        viewJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        viewJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        viewJLabel.setText("View:");
        viewJLabel.setBounds(new Rectangle(7, 30, 29, 18));
        
        //CCBegin by liunan 2008-07-24
        colorFlag.setHorizontalAlignment(SwingConstants.RIGHT);
        colorFlag.setHorizontalTextPosition(SwingConstants.RIGHT);
        colorFlag.setBounds(new Rectangle(7,30,29,18));
        colorFlag.setText("");
        //CCEnd by liunan 2008-07-24
        //CCBegin by liunan 2008-07-24
        colorFlagCheckBox.setFont(new java.awt.Font("SansSerif", 0, 12));
        colorFlagCheckBox.setText("��ɫ����ʶ");
        //CCEnd by liunan 2008-07-24
        //CCBegin by liunan 2008-07-24
        //����ȱʡֵ��Ĭ��Ϊδѡ�С�
        colorFlagCheckBox.setSelected(false);
        //CCEnd by liunan 2008-07-24
        
        viewStateLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        viewStateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        viewStateLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        viewStateLabel.setText("State:");
        viewStateLabel.setBounds(new Rectangle(7, 30, 29, 18));
        
        viewStateValue.setFont(new java.awt.Font("Dialog", 0, 12));
         //CCBegin by leixiao 2009-12-16 �򲹶�v4r3_p001_20091215
        viewStateValue.setHorizontalAlignment(SwingConstants.LEFT);//�޸�"״̬"ֵ�����������������ñ�ǩ������X��Ķ��뷽ʽ:RIGHT->LEFT//CR4
         //CCEnd by leixiao 2009-12-16 �򲹶�v4r3_p001_20091215
        viewStateValue.setHorizontalTextPosition(SwingConstants.RIGHT);
        viewStateValue.setText("");
        viewStateValue.setBounds(new Rectangle(7, 30, 29, 18));
        
        unitJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        unitJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        unitJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        unitJLabel.setText("Unit:");
        unitJLabel.setBounds(new Rectangle(7, 56, 29, 18));
        versionJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        versionJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        versionJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        versionJLabel.setText("Version:");
        versionJLabel.setBounds(new Rectangle(7, 4, 29, 18));
        
        lifeCycleStatePanel.setLayout(new GridBagLayout());  
        lifeCycleStatePanel.add(viewStateLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, 
                new Insets(0, 8, 0, 0), 0, 0));
        lifeCycleStatePanel.add(viewStateValue, new GridBagConstraints(1, 0, 1,
                1, 1.0, 0.0,GridBagConstraints.WEST, GridBagConstraints.NONE, 
                new Insets(0,8, 0, 0), 0, 0));
        lifeCycleStatePanel.add(new JLabel(), new GridBagConstraints(0, 1, 2,
                1, 1.0, 1.0,GridBagConstraints.WEST, GridBagConstraints.NONE, 
                new Insets(0,8, 0, 0), 0, 0));
      
        //liyz ��Ӵ����ߺʹ���ʱ���ǩ
        createUserJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        createUserJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        createUserJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        createUserJLabel.setText("CreateUser:");
        createUserJLabel.setBounds(new Rectangle(7, 4, 29, 18));
        updateUserJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        updateUserJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        updateUserJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        updateUserJLabel.setText("UpdateUser:");
        updateUserJLabel.setBounds(new Rectangle(7, 30, 29, 18));
        createDateJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        createDateJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        createDateJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        createDateJLabel.setText("CreateDate:");
        createDateJLabel.setBounds(new Rectangle(7, 4, 29, 18));
        updateDateJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        updateDateJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        updateDateJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        updateDateJLabel.setText("UpdateDate:");
        updateDateJLabel.setBounds(new Rectangle(7, 30, 29, 18));//end
        //liyz ��ӹ���״̬��ǩ
        workableStateJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        workableStateJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        workableStateJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        workableStateJLabel.setText("WorkableState:");
        workableStateJLabel.setBounds(new Rectangle(7, 4, 29, 18));//end
        lifeCycleInfoPanel.setLayout(new GridBagLayout());
        lifeCycleInfoPanel.resetPanelInterVal(3);
        lifeCycleInfoPanel.setBsoName("QMPart");
        viewChoice.setBlankChoiceAllowed(false);
        viewChoice.setFont(new java.awt.Font("SansSerif", 0, 12));
        viewChoice.setPreferredSize(new Dimension(52, 22));
    if(mainPart)
    {
        //CCBegin by liunan 2008-07-24
        colorFlagCheckBox.setEnabled(false);
        //CCEnd by liunan 2008-07-24
    changeLabelJPanel.add(viewJLabel, new GridBagConstraints(0, 0, 1,
            1, 0.0, 0.0, GridBagConstraints.NONE,
            GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));//5, 0
    changeLabelJPanel.add(viewChoice,
            new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0,
                            0), 0, 0));    
    changeLabelJPanel.add(unitJLabel, new GridBagConstraints(0, 1, 1,
            1, 0.0, 0.0, GridBagConstraints.NONE,
            GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));//5
    changeLabelJPanel.add(unitChoice,
            new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0,
                            0), 0, 0));//119
    changeLabelJPanel.add(versionJLabel, new GridBagConstraints(0, 2,
            1, 1, 0.0, 0.0, GridBagConstraints.NONE,
            GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));//0
    changeLabelJPanel.add(displayVersionJLabel,
            new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0,
                            0), 0, 0));//119    
    //CCBegin by liunan 2008-07-24
    changeLabelJPanel.add(colorFlag,
                          new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
        GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(0, 8, 0, 0), 0, 0));
    changeLabelJPanel.add(colorFlagCheckBox,
                          new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(0, 8, 0, 0), 0, 0));
    //CCEnd by liunan 2008-07-24
    userJPanel.add(createUserJLabel, new GridBagConstraints(0, 0, 1,
			1, 0.0, 0.0,GridBagConstraints.EAST, GridBagConstraints.NONE, 
			new Insets(0, 8, 0, 1), 0, 0));
    userJPanel.add(createUserValue, new GridBagConstraints(1, 0,1,
            1,1.0, 0.0,GridBagConstraints.WEST, GridBagConstraints.NONE, 
            new Insets(0, 8, 0, 0), 0, 0));
    userJPanel.add(updateUserJLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.EAST, GridBagConstraints.NONE, 
			new Insets(0, 8, 0, 0), 0, 0));
    userJPanel.add(updateUserValue, new GridBagConstraints(1, 1, 1,
			1, 1.0, 0.0,GridBagConstraints.WEST, GridBagConstraints.NONE, 
			new Insets(0,8, 0, 0), 0, 0));    
    
    dateJPanel.add(createDateJLabel, new GridBagConstraints(0, 0, 1,
            1, 0.0, 0.0, GridBagConstraints.EAST,
            GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));//5, 0
    dateJPanel.add(createDateValue,
            new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0,
                            0), 0, 0));    
    dateJPanel.add(updateDateJLabel, new GridBagConstraints(0, 1, 1,
            1, 0.0, 0.0, GridBagConstraints.EAST,
            GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));//5
    dateJPanel.add(updateDateValue,
            new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0,
                            0), 0, 0));//119

    workableStateJPanel.add(workableStateJLabel, new GridBagConstraints(0, 0, 1,
			1, 0.0, 0.0,GridBagConstraints.EAST, GridBagConstraints.NONE,
			new Insets(0, 8, 0, 0), 0, 0));
    workableStateJPanel.add(workableStateValue, new GridBagConstraints(1, 0,1,
            1,1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
            new Insets(0, 8, 0, 0), 0, 0));
    //Ϊ���沼������һ���
    workableStateJPanel.add(new JLabel(""), new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 120), 0, 0));
    add(beanAttributesPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 71, 0, 5), 0, 0));//155,5 2, 31, 0, 15
    add(changeLabelJPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 20, 0, 5), 0, 0));//2,10,38,15
    add(lifeCycleInfoPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 45, 0, 5), 0, 0));//-1,7,0,15  0, 7, -2, 15
    add(lifeCycleStatePanel, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 20, 0, 5), 0, 0));//-1,7,0,15  0, 7, -2, 15)
    
    add(userJPanel, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 55, 0, 5), 0, 0));
    add(dateJPanel, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));
    
    add(folderPanel, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 55, 0, 5), 0, 0));//0,15,3,15
    add(workableStateJPanel, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));//end
    
    jScrollpane.getViewport().add(partChangePanel);
    add(jScrollpane, new GridBagConstraints(0, 4, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 10, 0, 10), 0, 0));
    }
    else
    {
    	 //�˲��ֹ���������ı䣬���ȷ��ı䣬����ͬʱ��֤��enableUpdateFields()��
        //���ж���ͼ�͵�λ���ֹ����һ�¡�
        changeLabelJPanel.add(unitChoice, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(6, 8, 0, 0), 119, 0));
        changeLabelJPanel.add(unitJLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(6, 7, 0, 0), 5, 0));
        changeLabelJPanel.add(viewJLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4, 7, 0, 0), 5, 0));
        changeLabelJPanel.add(displayVersionJLabel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 0), 96, 18));
        changeLabelJPanel.add(versionJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 7, 0, 0), 5, 0));
        changeLabelJPanel.add(viewChoice, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 8, 0, 0), 0, 0));
        //CCBegin by liunan 2008-07-24
        changeLabelJPanel.add(colorFlag,
                new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.NONE,
                        new Insets(0, 8, 0, 0), 0, 0));
        changeLabelJPanel.add(colorFlagCheckBox,
                new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                        new Insets(0, 8, 0, 0), 0, 0));
        //CCEnd by liunan 2008-07-24
        
        add(beanAttributesPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 31, 0, 15), 155, 5));
        add(changeLabelJPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 20, 0, 15), 0, 0));
        add(lifeCycleInfoPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 7, 0, 15), 0, 0));
        add(lifeCycleStatePanel, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 20, 0, 5), 0, 0));
        add(folderPanel, new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 15, 3, 15), 0, 0));        
    }
 
    initialize();
    }

    /**
     * ��ʼ����
     */
    protected void initialize()
    {
        PART_CLIENT = "C"; //��ʱ
        localize();
      
        //���õ�λö��ֵ
        try
        {
            unitChoice.setEnumeratedTypeClassName("com.faw_qm.part.util.Unit");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //�����ǰϵͳ�Ǳ�׼��,����ͼ��Ч����Ŀ�����������ʧЧ��
        if(PART_CLIENT == "A")
        {
            viewChoice.setEnabled(false);
            lifeCycleInfoPanel.setEnabled(false);
        }
        //�����ǰϵͳ����ǿ�棬����ͼ��Ч����Ŀ�����������ʧЧ��
        if(PART_CLIENT == "B")
        {
            viewChoice.setEnabled(true);
            lifeCycleInfoPanel.setEnabled(false);
        }
        //�����ǰϵͳ����ҵ�棬��ͼ��Ч,��Ŀ�������������Ч��
        if(PART_CLIENT == "C")
        {
            viewChoice.setEnabled(true);
            lifeCycleInfoPanel.setEnabled(true);
        }
    }
    /**
     * ��Դ��Ϣ���ػ���
     */
    protected void localize()
    {
    	try
        {
            versionJLabel.setText(getLabelsRB().getString("revisionLbl"));
            viewJLabel.setText(getLabelsRB().getString("viewLbl"));
            unitJLabel.setText(getLabelsRB().getString("unitJLabel"));
            folderPanel.setFolderJLabelName(getLabelsRB().getString(
                    "locationLbl"));
            folderPanel.setBrowseJButtonName(getLabelsRB().getString(
                    "browseJButton"));
            folderPanel.setButtonMnemonic(70);
            folderPanel.setLabelModel(false);
            
            viewStateLabel.setText(getLabelsRB().getString("lifeCycleState"));
           
            //liyz add �����ߺʹ���ʱ���ǩ
            createUserJLabel.setText(getLabelsRB().getString("createUserJLabel"));
            updateUserJLabel.setText(getLabelsRB().getString("updateUserJLabel"));
            createDateJLabel.setText(getLabelsRB().getString("createDateJLabel"));
            updateDateJLabel.setText(getLabelsRB().getString("updateDateJLabel"));//end
            //liyz add ����״̬��ǩ
            workableStateJLabel.setText(getLabelsRB().getString("workableStateJLabel"));//end
            
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
        }
    }

    /**
     * ʹ�������ڴ����㲿��״̬��
     */
    protected void enableCreateFields()
    {
        PartDebug.debug("enableCreateFields() begin....", this,
                PartDebug.PART_CLIENT);
        //ʹ�汾��ǩ���ɼ�
        versionJLabel.setForeground(getBackground());
        //ʹfolderPanel���ڿɱ༭״̬
        folderPanel.setLabelModel(false);
        //ʹlifeCycleInfoPanel���ڴ���ģʽ
        if(PART_CLIENT.equals("C"))
        {
            lifeCycleInfoPanel.setMode(LifeCyclePanel.CREATE_MODE);
            lifeCycleStatePanel.setVisible(false);
            add(lifeCycleInfoPanel, new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 7, 0, 15), 0, 0));
        }
        try
        {
            beanAttributesPanel.setAttributes(new String[]{"Number", "Name",
                    "Source", "Type"});
            beanAttributesPanel.setLabels(new String[]{
                    getLabelsRB().getString("required")
                            + getLabelsRB().getString("numberLbl"),
                    getLabelsRB().getString("required")
                            + getLabelsRB().getString("nameLbl"),
                    getLabelsRB().getString("sourceLbl"),
                    getLabelsRB().getString("typeLbl")});
            beanAttributesPanel.setEdits(new String[]{"true", "true", "true",
                    "true"});
        }
        catch (QMPropertyException qmpropertyexception)
        {
            qmpropertyexception.printStackTrace();
        }
        beanAttributesPanel.initializeHelp();
       
        PartDebug.debug("enableCreateFields() end...return is void", this,
                PartDebug.PART_CLIENT);
    }
    /**
     * ʹ���洦�ڲ鿴״̬(�շ���)��
     */
    protected void enableViewFields()
    {
        if(mainPart)
        {
            PartDebug.debug("enableUpdateFields() begin....", this,
                    PartDebug.PART_CLIENT);
            //ʹfolderPanel���ڲ鿴ģʽ
            folderPanel.setLabelModel(true);
            folderPanel.getFolderPanelLabel();
            folderPanel.setEnabled(false);
            //ʹunitChoice��viewChoice�Ա�ǩ����(������������²���)
            unitChoice.setEnabled(false);
            viewChoice.setEnabled(false);
            changeLabelJPanel.remove(unitChoice);
            changeLabelJPanel.remove(viewChoice);
            changeLabelJPanel.setEnabled(false);
            viewDisplayJLabel.setSize(100, 22);
            viewDisplayJLabel.setEnabled(true);
            unitDisplayJLabel.setSize(100, 22);
            unitDisplayJLabel.setEnabled(true);
            displayVersionJLabel.setSize(100, 22);
            displayVersionJLabel.setEnabled(true);
            //CCBegin by liunan 2008-07-24
            colorFlagCheckBox.setEnabled(false);
            //CCEnd by liunan 2008-07-24
            
            viewStateValue.setSize(100, 22);
            viewStateValue.setEnabled(true);
            //liyz add ���������ֺʹ�������
            createUserValue.setSize(100, 22);
            createUserValue.setEnabled(true);
            updateUserValue.setSize(100, 22);
            updateUserValue.setEnabled(true);
            createDateValue.setSize(100, 22);
            createDateValue.setEnabled(true);
            updateDateValue.setSize(100, 22);
            updateDateValue.setEnabled(true);//end
            //liyz add ����״̬
            workableStateValue.setSize(100, 22);
            workableStateValue.setEnabled(true);//end
           
            changeLabelJPanel.add(viewJLabel, new GridBagConstraints(0, 0, 1,
                    1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));
            changeLabelJPanel.add(viewDisplayJLabel, new GridBagConstraints(1,
                    0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));
            changeLabelJPanel.add(unitJLabel, new GridBagConstraints(0, 1, 1,
                    1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));
            changeLabelJPanel.add(unitDisplayJLabel, new GridBagConstraints(1,
                    1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));
            changeLabelJPanel.add(versionJLabel, new GridBagConstraints(0, 2,
                    1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));
            changeLabelJPanel.add(displayVersionJLabel, new GridBagConstraints(
                    1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));
            //CCBegin by liunan 2008-07-24
            changeLabelJPanel.add(colorFlag,
                                  new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
            changeLabelJPanel.add(colorFlagCheckBox,
                                  new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 0, 0), 0, 0));
            //CCEnd by liunan 2008-07-24
//            attributeJPanel.add(changeLabelJPanel, new GridBagConstraints(0, 1,
//                    2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
//                    GridBagConstraints.BOTH, new Insets(0, 28, 0, 15), -1, 0));//2,10,38,15
            //liyz ���²���
            //CCBegin by leixiao 2009-12-25 new Insets(5, 20, 0, 15)->new Insets(5, 0, 0, 15)
            add(changeLabelJPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 0, 15), -1, 0));
          //CCEnd by leixiao 2009-12-25 
            //add(changeLabelJPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 20, 0, 15), -1, 0));
            add(lifeCycleStatePanel, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 20, 0, 15), -1, 0));
            add(dateJPanel, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));
            add(workableStateJPanel, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));//end
            if(PART_CLIENT.equals("C"))
            {
                //ʹlifeCycleInfoPanel���ڲ鿴ģʽ
                lifeCycleInfoPanel.setMode(3);
                lifeCycleStatePanel.setVisible(true);
            }
           
            try
            {
                beanAttributesPanel.setAttributes(new String[]{"Number",
                        "Name", "Source", "Type"});
                beanAttributesPanel.setLabels(new String[]{
                        getLabelsRB().getString("required")
                                + getLabelsRB().getString("numberLbl"),
                        getLabelsRB().getString("required")
                                + getLabelsRB().getString("nameLbl"),
                        getLabelsRB().getString("sourceLbl"),
                        getLabelsRB().getString("typeLbl")});
                beanAttributesPanel.setEdits(new String[]{"false", "false",
                        "false", "false"});
            }
            catch (QMPropertyException qmpropertyexception)
            {
                qmpropertyexception.printStackTrace();
            }
            beanAttributesPanel.initializeHelp();
            PartDebug.debug("enableUpdateFields() end...return is void", this,
                    PartDebug.PART_CLIENT);
        }
    }


    //CR3 Begin zhangq �޸�ԭ��TD2515
    /**
     * ʹ���洦�ڸ����㲿��״̬��
     */
    protected void enableUpdateFields()
    {
        PartDebug.debug("enableUpdateFields() begin....", this,
                PartDebug.PART_CLIENT);
        //ʹfolderPanel���ڲ鿴ģʽ
        folderPanel.setLabelModel(true);
        folderPanel.getFolderPanelLabel();
        //ʹunitChoice��viewChoice�Ա�ǩ����(������������²���)
        unitChoice.setEnabled(false);
        viewChoice.setEnabled(false);
        changeLabelJPanel.remove(unitChoice);
        changeLabelJPanel.remove(viewChoice);
        viewDisplayJLabel.setSize(100, 22);
        unitDisplayJLabel.setSize(100, 22);
        if(mainPart)
        {
            //���ڸ�����������²���
            changeLabelJPanel.add(viewDisplayJLabel,
                    new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                            GridBagConstraints.CENTER,
                            GridBagConstraints.HORIZONTAL, new Insets(2, 8, 3,
                                    0), 0, 0));//209
            changeLabelJPanel.add(viewJLabel, new GridBagConstraints(0, 0, 1,
                    1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));//(2, 8, 2, 0)5
            changeLabelJPanel.add(unitDisplayJLabel,
                    new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
                            GridBagConstraints.CENTER,
                            GridBagConstraints.HORIZONTAL, new Insets(2, 8, 3,
                                    0), 0, 0));//(2, 8, 2, 6)137
            changeLabelJPanel.add(unitJLabel, new GridBagConstraints(0, 1, 1,
                    1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));//2, 8, 2, 0 5
            changeLabelJPanel.add(versionJLabel, new GridBagConstraints(0, 2,
                    1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));//2, 8, 2, 0 5
            changeLabelJPanel.add(displayVersionJLabel, new GridBagConstraints(
                    1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(2, 8, 3, 0), 0, 0));//2, 8, 2, 6 137
                        //CCBegin by liunan 2008-07-24
            changeLabelJPanel.add(colorFlag,
                                  new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
            changeLabelJPanel.add(colorFlagCheckBox,
                                  new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 0, 0), 0, 0));
            //CCEnd by liunan 2008-07-24
            //CCBegin by leixiao 2009-12-28 �������
            //liyz ���²���
            add(changeLabelJPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 40, 0, 15), -1, 0));
           // add(changeLabelJPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 62, 0, 15), -1, 0));
          //CCEnd by leixiao 2009-12-28 
            add(lifeCycleStatePanel, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 62, 0, 15), -1, 0));
            add(dateJPanel, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 40, 0, 5), 0, 0));
            add(workableStateJPanel, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 40, 0, 5), 0, 0));
        }
        else
        {
            //add 0428 �ڴ����㲿������������ʾ�汾��ǩ
            //ʹ�汾��ǩ�ɼ�
            versionJLabel.setForeground(getForeground());
            //end
            //���ڸ�����������²���
            changeLabelJPanel.add(viewDisplayJLabel, new GridBagConstraints(1,
                    1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 6), 209,
                    0));
            changeLabelJPanel.add(unitDisplayJLabel, new GridBagConstraints(1,
                    2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 6), 137,
                    0));
            changeLabelJPanel.add(unitJLabel, new GridBagConstraints(0, 2, 1,
                    1, 0.0, 0.0, GridBagConstraints.WEST,
                    GridBagConstraints.NONE, new Insets(2, 21, 2, 0), 5, 0));
            changeLabelJPanel.add(viewJLabel, new GridBagConstraints(0, 1, 1,
                    1, 0.0, 0.0, GridBagConstraints.WEST,
                    GridBagConstraints.NONE, new Insets(2, 21, 2, 0), 5, 0));
            changeLabelJPanel.add(displayVersionJLabel, new GridBagConstraints(
                    1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 6), 259,
                    0));
            changeLabelJPanel.add(versionJLabel, new GridBagConstraints(0, 0,
                    1, 1, 0.0, 0.0, GridBagConstraints.WEST,
                    GridBagConstraints.NONE, new Insets(2, 21, 2, 0), 5, 0));
            //CCBegin by leixiao 2009-12-28 �������
//            add(changeLabelJPanel, new GridBagConstraints(1, 0,
//                    1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
//                    GridBagConstraints.HORIZONTAL, new Insets(0, 20, 30, 30),
//                    -1, 0));
            add(changeLabelJPanel, new GridBagConstraints(1, 0,
                    1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 20, 5, 30),
                    -1, 0));
            //CCEnd by leixiao 2009-12-28 �������
            add(lifeCycleStatePanel, new GridBagConstraints(1, 1,
                    1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 40, 30, 30),
                    -1, 0));
        }
        if(PART_CLIENT.equals("C"))
        {
            //ʹlifeCycleInfoPanel���ڲ鿴ģʽ
            lifeCycleInfoPanel.setMode(3);
            lifeCycleStatePanel.setVisible(true);
        }      
        try
        {
            beanAttributesPanel.setEdits(new String[]{"false", "false", "true",
                    "true"});
            //CCBegin by leixiao 2009-12-10 
//            colorFlagCheckBox.setEnabled(true);
            //CCEnd by liunan 2009-12-10
        }
        catch (QMPropertyException qmpropertyexception)
        {
            qmpropertyexception.printStackTrace();
        }
        beanAttributesPanel.initializeHelp();
        PartDebug.debug("enableUpdateFields() end...return is void", this,
                PartDebug.PART_CLIENT);
    }
     //CCBegin by leixiao 2009-12-16 �򲹶�v4r3_p001_20091215
      /**
     * ���ñ�š����ơ���Դ�����͡�
     *
     */
    public void setBeanAttributes(PartItem part)//��ʼ����ʱ��Ҫ�����㲿���������Դ�����ͣ������жϻ�����������Ƿ���ġ�//CR5
    {
        beanAttributesPanel.setObject(part);
    }
     //CCEnd by leixiao 2009-12-16 �򲹶�v4r3_p001_20091215  

    public void setPartItem(PartItem part,boolean isDispalyEmpty)
    {
        PartDebug.debug("setPartItem() begin....", this,
                PartDebug.PART_CLIENT);
        beanAttributesPanel.setObject(part);
        if(mainPart)
        {
            //liyz add ���������ֺʹ�������                
            createUserValue.setText(part.getCreatedByPersonName());                
            updateUserValue.setText(part.getModifiedByPersonName());
            if(part.getCreationDate()!=null)
            createDateValue.setText(part.getCreationDate().toString());
            else{
                if(isDispalyEmpty){
                    createDateValue.setText("");
                }
            }
            if(part.getLastUpdated()!=null)
            updateDateValue.setText(part.getLastUpdated().toString());//end
            else{
                if(isDispalyEmpty){
                    updateDateValue.setText("");
                }
            }
            //liyz add ����״̬
            try {
               workableStateValue.setText(part.getStatusText());
           } catch (QMException e) {
               e.printStackTrace();
           }//end
        }
        
        String version = part.getIterationID();
        displayVersionJLabel.setText(version);
        viewDisplayJLabel.setText(part.getViewName());
        unitChoice.setSelectedEnumeratedType(part.getDefaultUnit());
                    //CCBegin by liunan 2008-07-24
            if(part.getPart().getColorFlag())
            {
              colorFlagCheckBox.setSelected(true);
            }
            else
            {
                colorFlagCheckBox.setSelected(false);
            }
            //viewChoice.setSelectedView(part.getView());
            //CCEnd by liunan 2008-07-24
        if(isDispalyEmpty){
            unitDisplayJLabel.setText("");
        }
        else if(part.getPart().getPartNumber() != null)
        {
            unitDisplayJLabel.setText(part.getDefaultUnit().getDisplay());
        }
        try
        {
            if(isDispalyEmpty){
                folderPanel.setLabelText("");
            }
            else{
                folderPanel.setLabelText(part.getLocation());
            }
              
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane.showMessageDialog(getParentFrame(), ex
                    .getClientMessage(), title,
                    JOptionPane.INFORMATION_MESSAGE);
            ex.printStackTrace();
        }
        if(PART_CLIENT.equals("C"))
        {
            viewChoice.setEnabled(false);
            unitChoice.setEnabled(false);
            lifeCycleInfoPanel.setObject((LifeCycleManagedIfc) part
                    .getObject());
            viewStateValue.setText(part.getState());
            viewChoice.setEnabled(true);
            unitChoice.setEnabled(true);
            beanAttributesPanel.setEnabled(true);
            lifeCycleInfoPanel.setEnabled(true);
            partChangePanel.setPartItem(part);
           
        }            
      
        PartDebug.debug("setPartItem() end...return is void", this,
                PartDebug.PART_CLIENT);
    }
    
    
    /**
     * ���� PartItem ����һ���ؽ����񣬱��ڲ��̵߳��á�
     * @param part PartItem
     */
    public void setPartItem(PartItem part)
    {
        setPartItem(part,false);
    }
    //CR3 End zhangq
    /** CR2 Begin
     * ��������������ϵ�������ʾ��Ϣ���
     * 
     */  
    public void   clearBaseAttributeJPanel()
    {
    	createUserValue.setText(null);        
        updateUserValue.setText(null);
        createDateValue.setText(null);
        updateDateValue.setText(null);
        workableStateValue.setText(null);
        displayVersionJLabel.setText(null);
        viewDisplayJLabel.setText(null);
        unitChoice.setSelectedEnumeratedType(null);
        unitDisplayJLabel.setText(null);
        folderPanel.setLabelText("");
        viewStateValue.setText(null);
        lifeCycleInfoPanel.clear();
    }   
//  CR2 End   
    /**
     * �����Ƿ���ָ�����ϼС�
     * @return �����ָ�����ϼ�·�����򷵻����ϼС�
     * @throws QMRemoteException
     */
    protected SubFolderInfo checkFolderLocation() throws QMRemoteException
    {
        PartDebug.debug("checkFolderLocation() begin ....", this,
                PartDebug.PART_CLIENT);
        String location = "";
        SubFolderInfo folderInfo = null;
        //������ϼ�·��
        location = folderPanel.getFolderLocation();
        if(location != null && location.length() != 0)
        {
            //�������ϼз��񣬸��ݻ�õ����ϼ�·��������ϼ�
            Class[] paraClass = {String.class};
            Object[] objs = {location};
            folderInfo = (SubFolderInfo) IBAUtility.invokeServiceMethod(
                    "FolderService", "getFolder", paraClass, objs);
            //�޸�1 begin ��2007-10-29 By muyp
            //ȡ����Ч·�������β����ڵ����ϼе����
            folderPanel.setLabelText(folderInfo.getPath());
            //�޸�1 end
        }
        PartDebug.debug("FolderService.getFolder(String)...return "
                + folderInfo, this, PartDebug.PART_CLIENT);
        if(folderInfo != null)
        {
            //�������ϼз����ж�ָ�����ļ����Ƿ��Ǹ����ļ���
            Class[] paraClass2 = {FolderIfc.class};
            Object[] objs2 = {folderInfo};
            Boolean flag1 = null;
            try
            {
                flag1 = (Boolean) IBAUtility.invokeServiceMethod(
                        "FolderService", "isPersonalFolder", paraClass2, objs2);
            }
            catch (QMRemoteException ex)
            {
                String title = getLocalizedMessage(
                        PartDesignViewRB.LOCAL_NOT_VALID, null);
                JOptionPane.showMessageDialog(getParentFrame(), ex
                        .getClientMessage(), title,
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
            boolean flag = false;
            if(flag1 != null)
            {
                flag = flag1.booleanValue();
            }
            PartDebug.debug("FolderService.isPersonalFolder() end ... return "
                    + flag, this, PartDebug.PART_CLIENT);
            if(!flag)
            {
                //�׳��쳣��Ϣ����ָ�������ϼв��Ǹ����ļ���
                throw new QMRemoteException(
                        getLocalizedValue(PartDesignViewRB.LOCAL_NOT_CAB));
            }
        }
        if(folderInfo != null&&getPartItem()!=null)
        {
            getPartItem().setFolder(folderInfo);
        }
        PartDebug.debug("checkFolderLocation() end ... return " + folderInfo,
                this, PartDebug.PART_CLIENT);
        return folderInfo;
    }

    
    /**
     * �ڴ���������㲿��������������Ϊ�ʼ�Ľ��档
     */
    public void clear()
    {
       
        try
        {
            setPartItem(new PartItem());
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        unitDisplayJLabel.setText("");
        viewDisplayJLabel.setText("");
        lifeCycleInfoPanel.setMode(3);
        viewStateValue.setText("");
        folderPanel.setLabelText("");
    }
    
    
    /**
     * ���ñ�š����ơ���Դ�����͵�ֵ��
     * @param part PartItem ��Ҫ��ʾ���㲿����
     */
    public void setBeanAttribute(PartItem part)
    {
        beanAttributesPanel.setObject(part);
        try
        {
            beanAttributesPanel.setAttributes(new String[]{"Number", "Name",
                    "Source", "Type"});
            beanAttributesPanel.setLabels(new String[]{
                    getLabelsRB().getString("required")
                            + getLabelsRB().getString("numberLbl"),
                    getLabelsRB().getString("required")
                            + getLabelsRB().getString("nameLbl"),
                    getLabelsRB().getString("sourceLbl"),
                    getLabelsRB().getString("typeLbl")});
            beanAttributesPanel.setEdits(new String[]{"false", "false",
                    "false", "false"});
        }
        catch (QMPropertyException qmpropertyexception)
        {
            qmpropertyexception.printStackTrace();
        }
        beanAttributesPanel.initializeHelp();
    }
    
    public boolean ischange(PartItem partItem)
    {
        /**�㲿���Ƿ�ı�ı�־*/
        boolean isChange = false;
        String source = beanAttributesPanel.getSource();
        String type = beanAttributesPanel.getType();
        /**���ȼ����Դ�������Ƿ�ı�*/
        String sr = partItem.getSource().getDisplay();
        String st = partItem.getType().getDisplay();
        if(!sr.equals(source) || !st.equals(type))
        {
            isChange = true;
          
        }
        return isChange;
            
        }
    
    /**
     * �����̣߳����汻�޸ĵ��㲿����
     * �㡰���桱��ť������ʾ����Ի�����ѡ�񱣴棬����isDialog���������������
     * @param isDialog boolean
     */
    protected PartItem save( PartItem partItem,int model)
    throws QMException
    {    	
    	  beanAttributesPanel.setObjectAttributeValues();
          //CCBegin by leixiao 2009-12-10��ɫ����ʶ
    	 // System.out.println("��ɫ��ʶ����������"+colorFlagCheckBox.isSelected());
    	  partItem.getPart().setColorFlag(colorFlagCheckBox.isSelected());
    	  //CCEnd by liunan 2008-07-24
    	  if(model == 1)
          {
    		  
              ViewObjectInfo view = (ViewObjectInfo) viewChoice
                      .getSelectedView();
              if(view==null)
              {
              	return partItem;
//   	           setButtonWhenSave(true);
//   	            return;
              }
              partItem.setView(view);
              Unit unit = (Unit) unitChoice.getSelectedEnumeratedType();
              partItem.setDefaultUnit(unit);
              String folder = folderPanel.getFolderLocation();
              partItem.setLocation(folder);
              if(PART_CLIENT == "C")
              {
//            	  if(getPartItem()!=null)
//            	  {
//                  LifeCycleManagedIfc lifeCycleManagedIfc = (LifeCycleManagedIfc) getPartItem().getObject();
            	  //liyz modify �����㲿��ʱ���ܱ����������ں͹���������� 2009/05/25
            	  LifeCycleManagedIfc lifeCycleManagedIfc = (LifeCycleManagedIfc) partItem.getObject();
                  LifeCycleManagedIfc returnIfc = lifeCycleInfoPanel
                          .assign(lifeCycleManagedIfc);
                  partItem.setObject(returnIfc);
//            	  }
              }
          }
       return partItem;
    }
    public ViewObjectInfo getViewObject()
    {
    	return (ViewObjectInfo) viewChoice
        .getSelectedView();
    }
     //CCBegin by leixiao 2009-12-16 �򲹶�v4r3_p001_20091215
        /**
     * ����Tabҳ��ʾ��־
     * @param isShown
     */
    public void setIsShown(boolean isShown)//CR3
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
     //CCEnd by leixiao 2009-12-16 �򲹶�v4r3_p001_20091215
}
