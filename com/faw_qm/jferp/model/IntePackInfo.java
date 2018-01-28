/**
 * ���ɳ���CompositiveInfo.java	1.0              2007-9-24
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.model;

import java.sql.Timestamp;
import com.faw_qm.enterprise.model.ItemInfo;
import com.faw_qm.jferp.util.IntePackSourceType;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.util.QMMessage;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class IntePackInfo extends ItemInfo implements IntePackIfc
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final String RESOURCE = "com.faw_qm.jferp.util.ERPResource";

    String name;

    IntePackSourceType sourceType;

    String source;

    int state;

    String compositiveDomain;

    String publisher;

    Timestamp publishTime;

    String domain;

    String creator;
    



    /**
     * ��ȡΨһ��ʶ��
     * @return String Ψһ��ʶ��
     */
    public String getIdentity()
    {
        return this.getBsoID();
    }

    /**
     * ��ȡҵ���������
     * @return "IntePack"
     */
    public String getBsoName()
    {
        return "JFIntePack";
    }

    /**
     * ���ü��ɰ����ơ�
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * ���ü��ɰ���Դ���͡�
     * @param name IntePackSourceType
     */
    public void setSourceType(IntePackSourceType compositiveType)
    {
        this.sourceType = compositiveType;
    }

    /**
     * ���ü��ɰ���Դ��
     * @param name String
     */
    public void setSource(String source)
    {
        this.source = source;
    }

    /**
     * ���ü��ɰ�״̬��
     * @param name int
     */
    public void setState(int state)
    {
        this.state = state;
    }

    /**
     * ���ü��ɰ������ߡ�
     * @param name String
     */
    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    /**
     * ���ü��ɰ������ߡ�
     * @param name String
     */
    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    /**
     * ���ü��ɰ�����ʱ�䡣
     * @param name String
     */
    public void setPublishTime(Timestamp promulgationTime)
    {
        this.publishTime = promulgationTime;
    }

    /**
     * ��ü��ɰ����ơ�
     * @return String
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * ��ü��ɰ���Դ���͡�
     * @return String
     */
    public IntePackSourceType getSourceType()
    {
        return this.sourceType;
    }

    /**
     * ��ü��ɰ���Դ��
     * @return String
     */
    public String getSource()
    {
        return this.source;
    }

    /**
     * ��ü��ɰ�״̬��
     * @return String
     */
    public int getState()
    {
        return this.state;
    }

    /**
     * ��ü��ɰ������ߡ�
     * @return String
     */
    public String getPublisher()
    {
        return this.publisher;
    }

    /**
     * ��ü��ɰ������ߡ�
     * @return String
     */
    public Timestamp getPublishTime()
    {
        return this.publishTime;
    }

    /**
     * ��ü��ɰ������ߡ�
     * @return String
     */
    public String getCreator()
    {
        return this.creator;
    }

    /**
     * ��ü��ɰ�״̬����ʾ���ơ�
     * @return String
     */
    public String getStateDisplayName()
    {
        String display = "";
        if(this.getState() == 0)
        {
            display = "�Ѵ���";
        }
        else if(this.getState() == 9)
        {
            display = "�ѷ���";
        }
        return display;
    }

    /**
     * ��IntePackEJB��CreateByValueInfo��UpdateByValueInfo�����ص�����֤���Ե���Ч�ԡ�
     * @exception QMException
     */
    public void attrValidate() throws QMException
    {
        intePackNameValidate(getName());
    }

    /**
     * ����intePackName����֤������֤�����intePackName����Ч�ԡ�
     *
     * @param intePackName ���ɰ����ơ�
     * @throws QMException
     */
    public static void intePackNameValidate(String intePackName)
            throws QMException
    {
        if(intePackName != null && intePackName.length() > 200)
        {
            //"���ɰ�����"
            String str = QMMessage.getLocalizedMessage(RESOURCE,
                    "IJFntePack.intePackName", null);
            Object[] obj = {str};
            //"*���Ȳ�����Ҫ��"
            throw new QMException(RESOURCE, "IntePack.attrValidate", obj);
        }
    }
    
    
}
