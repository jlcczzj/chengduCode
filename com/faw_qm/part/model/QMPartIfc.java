/** ���ɳ��� QMPartIfc.java    1.0    2003/02/17
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ��ӹ���·��ʹ�õķ���  guoxiaoliang 2013-02-04
 */

package com.faw_qm.part.model;

import java.awt.Image;
import java.util.Vector;

import com.faw_qm.affixattr.model.AffixIfc;
import com.faw_qm.baseline.model.BaselineableIfc;
import com.faw_qm.build.model.BuildTargetIfc;
import com.faw_qm.content.model.ContentHolderIfc;
import com.faw_qm.eff.model.EffManagedVersionIfc;
import com.faw_qm.effectivity.model.EffectivityManageableIfc;
import com.faw_qm.enterprise.model.RevisionControlledIfc;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.part.ejb.entity.PartVersion;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;
import com.faw_qm.part.util.Unit;
import com.faw_qm.viewmanage.model.ViewManageableIfc;


/**
 * �㲿��ֵ����ӿڡ�
 * @author ���ȳ�
 * @version 1.0
 */

public interface QMPartIfc extends RevisionControlledIfc, BaselineableIfc,
        EffManagedVersionIfc, ViewManageableIfc, EffectivityManageableIfc,
        PartVersion,
        ContentHolderIfc, IBAHolderIfc, BuildTargetIfc, AffixIfc
{
    /**
     * ����㲿������
     * @return String
     */
    public String getPartName();


    /**
     * �����㲿������
     * @param name String
     */
    public void setPartName(String name);


    /**
     * ����㲿���š�
     * @return String
     */
    public String getPartNumber();


    /**
     * �����㲿���š���
     * @param number String
     */
    public void setPartNumber(String number);


    /**
     * ���Ĭ�ϵ�λ��
     * @return Unit
     */
    public Unit getDefaultUnit();


    /**
     * ����Ĭ�ϵ�λ��
     * @param unit :Unit ö�����͡�
     */
    public void setDefaultUnit(Unit unit);


    /**
     * ��øö����������Ч��ֵ�ļ��ϣ�������targetΪ��EffManagedVersionIfc���������
     * EffGroup���󣩡��÷���ֻ��ʵ��ҵ���߼���
     * @return Vector
     */
    public Vector getEffVector();


    /**
     * ������Ч�Լ��ϵ�ֵ��
     * @param vector Vector
     */
    public void setEffVector(Vector vector);


    /**
     * ��ȡ�㲿�����������
     * @return QMPartMap
     */
    public QMPartMap getQMPartMap();


    /**
     * �����㲿�����������
     * @param map :QMPartMap
     */
    public void setQMPartMap(QMPartMap map);


    /**
     * ����㲿����Դ��
     * @return ProducedBy
     */
    public ProducedBy getProducedBy();


    /**
     * �����㲿����Դ��
     * @param producedBy :ProducedBy
     */
    public void setProducedBy(ProducedBy producedBy);


    /**
     * ����㲿�����͡�
     * @return String
     */
    public QMPartType getPartType();


    /**
     * �����㲿�����͡�
     * @param type QMPartType
     */
    public void setPartType(QMPartType type);


    /**
     * ������Ŀ�����ơ�
     * @param projectName String
     */
    public void setProjectName(String projectName);


    /**
     * ��ȡ��Ŀ�����ơ�
     * @return ProjectName
     */
    public String getProjectName();


    /**
     * ��ȡ�㲿����׼ͼ�����ơ�
     * @return String
     */
    public String getStandardIconName();


    /**
     * �����Ƿ�����ɫ�����ʶ��
     * @param flag ��ɫ�����ʶ��
     */
    public void setColorFlag(boolean flag);


    /**
     * ��ȡ�Ƿ�����ɫ�����ʶ��
     * @return ��ɫ�����ʶ��
     */
    public boolean getColorFlag();


    /**
     * ���ͼ��·����
     *
     * @return String
     */
    public String getPartIconPath();


    /**
     * ���ͼ�ꡣ
     *
     * @return Image
     */
    public Image getPartIcon();


    /**
     * ���ͼ�����URI,�����ݿͻ�ʹ�á�
     *
     * @return String
     */
    public String getPartIconURI();
    /**
     * ��ȡ��ʶ
     * @return �����ִ����磺QMPart+partname+partnumber
     */
    public String getIBAID();
    /**
     * ��ȡѡװ����
     * @return String ѡװ����
     */
    public String getOptionCode();
   /**
    * ����ѡװ����
    * @param code
    */
    public void setOptionCode(String code);
    
    public void setCompare(String s);

    public String getCompare();
    /**
     * �����㲿�����͡�
     * @param type ����ö����
     * @see QMPartType
        �� */
    public void setPartType(String type);
    
    /**
     * ����Ĭ�ϵ�λ��
     * @param unit Unit ��λ��ö����
     * @see Unit
     */
    public void setDefaultUnit(String unit);
    
    /**
     * �����㲿����Դ��
     * @param producedBy ProducedBy ��Դö����
     * @see ProducedBy
     */
    public void setProducedBy(String producedBy);
    
    //CCBegin SS1
    
    /**
     * ��ȡ�Ƿ��Ǳ�cad�����ʶ
     * @param CHECKOUTBYCAD
     */
    public boolean getCheckoutByCAD();
    //CCEnd SS1

}
