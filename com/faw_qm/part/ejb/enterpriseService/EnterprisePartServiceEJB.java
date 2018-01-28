/** ���ɳ��� EnterprisePartServiceEJB.java    1.0    2003/02/28
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  * CR1 2010/02/08 ���   ԭ��:TD2847Ϊһ��������㲿��ִ���Ƴ���Ч�Բ���������
  * SS1 A004-2015-3130:�ṹ�Ƚϼ����ȽϺ��������ձ����������Խ�硣 liunan 2015-6-4
  * SS2 �ɶ��ṹ�������չ�� guoxiaoliang 
  */
package com.faw_qm.part.ejb.enterpriseService;

import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import com.faw_qm.acl.ejb.entity.QMPermission;
import com.faw_qm.acl.ejb.service.AccessControlService;
import com.faw_qm.clients.vc.controller.CheckInOutTaskLogic;
import com.faw_qm.config.ejb.service.ConfigService;
import com.faw_qm.container.batch.BatchContainer;
import com.faw_qm.container.batch.BatchContainerFactory;
import com.faw_qm.container.batch.TransactionContainer;
import com.faw_qm.container.batch.TransactionResult;
import com.faw_qm.eff.ejb.service.EffService;
import com.faw_qm.eff.model.EffConfigurationItemIfc;
import com.faw_qm.eff.model.EffIfc;
import com.faw_qm.eff.model.EffManagedVersionIfc;
import com.faw_qm.eff.util.EffGroup;
import com.faw_qm.eff.util.EffRange;
import com.faw_qm.effectivity.ejb.service.EffectivityService;
import com.faw_qm.effectivity.model.QMConfigurationItemIfc;
import com.faw_qm.effectivity.model.QMConfigurationItemInfo;
import com.faw_qm.effectivity.model.QMDatedEffectivityIfc;
import com.faw_qm.effectivity.model.QMDatedEffectivityInfo;
import com.faw_qm.effectivity.model.QMLotEffectivityIfc;
import com.faw_qm.effectivity.model.QMLotEffectivityInfo;
import com.faw_qm.effectivity.model.QMSerialNumEffectivityIfc;
import com.faw_qm.effectivity.model.QMSerialNumEffectivityInfo;
import com.faw_qm.effectivity.util.EffectivityType;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.PartAlternateLinkIfc;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.EffValidate;
import com.faw_qm.part.util.PartConfigSpecAssistant;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.wip.ejb.service.WorkInProgressService;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;

/**
 * �㲿����ҵ������EJBʵ���ࡣ
 * ����չ����Ļ����ϣ�������Ч�Թ�������ݣ�
 * �ṩ��Ʒ�ṹ�ȽϺͰ���Ч�����������嵥�ȹ��ܡ�
 * @author ���ȳ�
 * @version 1.0
 */
public class EnterprisePartServiceEJB extends BaseServiceImp
{
    /**���л�ID*/
    static final long serialVersionUID = 1L;

    public static final int DATE = 1 ;
    public static final int LOTNUM = 2 ;
    public static final int SERIALNUM = 3 ;
    private static String RESOURCE = "com.faw_qm.part.util.PartResource";

    /**
     * ���캯��
     */
    public EnterprisePartServiceEJB()
    {
        super();
    }
    /**
     * ����ָ����Դ������Ŀ�겿�������Ե�ɸѡ������������������ʹ�ù�ϵ�Ĳ�ͬ��
     * �����������˵ݹ鷽����difference(QMPartIfc sourcePartIfc, PartConfigSpecIfc sourceConfigSpecIfc,
     * QMPartIfc objectPartIfc, PartConfigSpecIfc objectConfigSpecIfc)��
     * @param sourcePartIfc :QMPartIfcԴ������ֵ����
     * @param objectPartIfc :QMPartIfcĿ�겿����ֵ����
     * @param sourceConfigSpecIfc :PartConfigSpecIfcԴ������ɸѡ������
     * @param objectConfigSpecIfc :PartConfigSpecIfcĿ�겿����ɸѡ������Ч�Է�����
     * @param map �����Ƚϵ�Դ�㲿����Ŀ���㲿���ļ���
     * @return vector:VectorԪ�ص����ݽṹString[]��<br>
     * String[0]����ǰ���첿������ڸ������Ĳ�Σ����������Ϊ0�㣬���������Ӳ���Ϊ1�㣬�Դ����ƣ�<br>
     * String[1]����ǰ���첿��������+"("+���+")"��<br>
     * String[2]����ǰ���첿����Դ�����еİ汾+����ͼ�������Դ������û�иò����Ļ�����ʾ""��
     *    ���Դ�������иò��첿������û�з������ù淶�İ汾�Ļ���
     *    ��ʾ"û�з������ù淶�İ汾"��<br>
     * String[3]����ǰ���첿��������һ���㲿����ʹ�õ�����+(��λ)�����Դ������û�иò����Ļ�����ʾ""
     *    ���Դ�������иò��첿������û�з������ù淶�İ汾�Ļ����ճ���ʾ����+����λ����<br>
     * String[4]����ǰ���첿����Ŀ�겿���еİ汾+����ͼ�������Ŀ�겿����û�иò����Ļ�����ʾ""��
     *    ���Ŀ�겿�����иò��첿������û�з������ù淶�İ汾�Ļ���
     *    ��ʾ"û�з������ù淶�İ汾"��<br>
     * String[5]����ǰ���첿��������һ���㲿����ʹ�õ�����+(��λ)�����Ŀ�겿����û�иò����Ļ�����ʾ""
     *    ���Ŀ�겿�����иò��첿������û�з������ù淶�İ汾�Ļ����ճ���ʾ����+����λ����<br>
     * String[6]����ʾ����ɫ�ı�ʶ::������첿����Դ������Ŀ�겿���ж����ڣ����������߰汾��ͬ�Ļ�����ʾΪ��ɫ(black)��
     *    ������첿����Դ�����д��ڣ�������Ŀ�겿���в����ڵĻ�����ʾΪ��ɫ(green)��
     *    ������첿����Ŀ�겿���д��ڣ�������Դ�����в����ڵĻ�����ʾΪ��ɫ(purple)��
     *    ������첿����Դ��������Ŀ�겿���еİ汾����������ͬ�Ļ�����ʾΪ��ɫ(gray)��
     *    ��Ϣ"û�з������ù淶�İ汾"����ɫΪ��ɫ(red),���и���Ϣ���е���ɫΪ��ɫ(black)��<br>
     * String[7]:�����㲿����BsoID�������Դ�㲿�����Եģ�����Դ�㲿��ʹ�õĸò����㲿����BsoID��<br>
     * String[8]:�����㲿����BsoID, �����Ŀ���㲿�����Եģ�����Ŀ���㲿��ʹ�õĵĲ����㲿����BsoID��<br>
     * @throws QMException
     */
    public Vector getBOMDifferences(QMPartIfc sourcePartIfc, PartConfigSpecIfc sourceConfigSpecIfc,
        QMPartIfc objectPartIfc, PartConfigSpecIfc objectConfigSpecIfc,HashMap map)
        throws QMException
    {
    	
        Vector vector = new Vector();
        Vector pathVector = new Vector();

          vector = difference(sourcePartIfc, sourceConfigSpecIfc, objectPartIfc,
                              objectConfigSpecIfc, pathVector, map);
        
        //���Դ��롣
        getBOMDifferencesBeginDebug(sourcePartIfc, objectPartIfc, vector);
        //������Ҫ��vector�е���ȫ��ͬ��Ԫ�ؽ��кϲ�:::
        Vector resultVector = new Vector();
        for(int i=0; i<vector.size(); i++)
        {
        	
            boolean flag = false;
            String[] obj1 = (String[])vector.elementAt(i);
            for(int j=0; j<resultVector.size(); j++)
            { 
            	
                String[] obj2 = (String[])resultVector.elementAt(j);
                //��Ҫ��obj1, obj2������Ԫ�ؽ��бȽϣ��������ͬ...���򣬼ӵ����������::
                boolean flag0 = obj1[0].equals(obj2[0]);
                boolean flag1 = obj1[1].equals(obj2[1]);
                boolean flag2 = obj1[2].equals(obj2[2]);
                boolean flag3 = obj1[3].equals(obj2[3]);
                boolean flag4 = obj1[4].equals(obj2[4]);
                boolean flag5 = obj1[5].equals(obj2[5]);
                boolean flag6 = obj1[6].equals(obj2[6]);
                boolean flag7 = obj1[7].equals(obj2[7]);
                boolean flag8 = obj1[8].equals(obj2[8]);
                boolean flag9 = (obj1[6].equals("gray") || obj1[6].equals("black"))
                              && obj2[6].equals("gray")|| obj2[6].equals("black");
                
                if(flag0 && flag1 && flag2 && flag3 && flag4 && flag5 && flag6
                   && flag7 && flag8 && flag9)
                {
                	
                    //�����ﻹ��Ҫ����жϣ���Ҫ�Ե�ǰ��Ԫ�ؽ��л����丸�׽ڵ㣬��������׽ڵ�
                    //��partNumber��ͬ�������ϻ��ݣ�ֱ���ҵ��Ľڵ��levelΪ1,������׽ڵ㶼
                    //��ͬ���ͺϲ������򣬲��ϲ���

                    //�������бȽϣ����level����1������ֱ��flag = true;
                    if(obj1[0].equals("1"))
                    {
                    
                        flag = true;
                        break;
                    }
                    else
                    {
                        //�������level = 1�Ľڵ�Ļ���
                        //obj1ȥvector.elementAt(i-1)���Լ�����һ���ڵ�,obj2ȥ��
                        //resultVector.elementAt(j-1)�е���һ��Ԫ�أ����
                        //��һ��Ԫ��Ҳ��ͬ������level > 1���ͼ�����ֱ�����ֲ���ͬ�ģ�����
                        //level =1 ��������ֲ���ͬ�ģ������Ժϲ����������level = 1 Ҳ��
                        //��ͬ�Ļ����Ϳ��Ժϲ��ˡ�
                    
                        int levelControl = (new Integer(obj1[0])).intValue();
                        int iii = i;
                        int jjj = j;
                        while(levelControl > 1)
                        {
                        	
                            String[] obj11 = (String[]) vector.elementAt(iii - 1);
                            String[] obj22 = (String[]) resultVector.elementAt(
                                jjj - 1);
                            String levelNow = obj11[0];
                         
                            if (obj11[1].equals(obj22[1]))
                            {
                            	
                                if (levelNow.equals("1"))
                                {
                                	
                                    flag = true;
                                    break;
                                }
                                else
                                {
                                	
                                    levelControl--;
                                    iii--;
                                    jjj--;
                                  
                                    //��Ҫ����ѭ����
                                }
                            }
                            else
                            {
                                //�����Ժϲ���
                                flag = false;
                                break;
                            }
                        }
                    }
                   
                }
            }
            if(flag == false)
                resultVector.addElement(obj1);
          
        }//end for(int i=0; i<vector.size(); i++)
        //���Դ��롣
       
        getBOMDifferencesEndDebug(resultVector);
        return resultVector;
    }

    private void getBOMDifferencesBeginDebug(QMPartIfc sourcePartIfc,
                                             QMPartIfc objectPartIfc,
                                             Vector vector)
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "getBOMDifferences() begin ....");
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");
        PartDebug.trace(PartDebug.PART_SERVICE, "���뷽��getBOMDifference��Ĳ����ֱ���:" +
                        sourcePartIfc.getBsoID() + "   " +
                        objectPartIfc.getBsoID());
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");

        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "getBOMDifferences() end....return is Vector");
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "getBOMDifference()�������м�ķ���ֵ��::");
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");
        for (int i = 0; i < vector.size(); i++)
        {
            Object obj = vector.elementAt(i);
            if (obj instanceof String[])
            {
                String[] obj1 = (String[]) obj;
                for (int j = 0; j < obj1.length; j++)
                {
                    PartDebug.trace(this, PartDebug.PART_SERVICE,
                                    "   " + obj1[j]);
                }
            }
        }
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");
    }

    private void getBOMDifferencesEndDebug(Vector resultVector)
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "getBOMDifferences() end....return is Vector");
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "getBOMDifference()���������ķ���ֵ��::");
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");
        for (int i = 0; i < resultVector.size(); i++)
        {
            Object obj = resultVector.elementAt(i);
            if (obj instanceof Object[])
            {
                Object[] obj1 = (Object[]) obj;
                for (int j = 0; j < obj1.length; j++)
                {
                    PartDebug.trace(PartDebug.PART_SERVICE,
                                    "obj instanceof Object[] obj==" + obj +
                                    "  obj1[" + j + "]==" + obj1[j]);
                }
            }
        }
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");
    }

    /**
     * ˽�еķ�������getBOMDifferences()���������ã�ʵ���˵ݹ�Ĺ��ܡ�
     * �÷�����ҵ���߼�Ϊ����ȡ��ǰԴ������Ŀ�겿���ķ��ϸ������ù淶�����㲿���ļ��ϣ���������������
     * ��QMPartIfc, Ҳ������QMPartMasterIfc�������������Ͻ��бȽϣ�
     *   ���һ�����첿��ֻ��Դ����������ֻ��Ŀ�겿���д��ڣ��ӵ�����������У����ټ����Ƚϣ�
     *   ���һ�����첿����Դ��������Ŀ�겿���ж����ڣ�����ʹ�õ��������߰汾����ͬ�Ļ��������Ƚϣ�
     * ���Ѹò��첿���Ĳ�������г�������ɫ����
     *   ���һ�����첿����Դ������Ŀ�겿���ж����ڣ�����ʹ�õ������Ͱ汾����ͬ�Ļ��������Ƚϣ�
     * ���Ѹò��첿���г�������ɫ����
     * ��Ҫ�ı䷵��ֵVector��Ԫ�ص����ݽṹString[]��
     * 0����ǰ���첿������ڸ������Ĳ�Σ����������Ϊ0�㣬���������Ӳ���Ϊ1�㣬�Դ����ƣ�
     * 1����ǰ���첿��������+"-"+��ţ������partName + partNumber֮ǰ�����ϺͲ�κ�
     *    ���Ӧ��"..."����Щ�������Ϊ(level-1)*3��
     *
     * 2����ǰ���첿����Դ�����еİ汾+����ͼ�������Դ������û�иò����Ļ�����ʾ""��
     *    ���Դ�������иò��첿������û�з������ù淶�İ汾�Ļ���
     *    ��ʾ"û�з������ù淶�İ汾"��
     * 3����ǰ���첿��������һ���㲿����ʹ�õ�����+(��λ)�����Դ������û�иò����Ļ�����ʾ""
     *    ���Դ�������иò��첿������û�з������ù淶�İ汾�Ļ����ճ���ʾ����+����λ����
     *
     * 4����ǰ���첿����Ŀ�겿���еİ汾+����ͼ�������Ŀ�겿����û�иò����Ļ�����ʾ""��
     *    ���Ŀ�겿�����иò��첿������û�з������ù淶�İ汾�Ļ���
     *    ��ʾ"û�з������ù淶�İ汾"��
     * 5����ǰ���첿��������һ���㲿����ʹ�õ�����+(��λ)�����Ŀ�겿����û�иò����Ļ�����ʾ""
     *    ���Ŀ�겿�����иò��첿������û�з������ù淶�İ汾�Ļ����ճ���ʾ����+����λ����
     *
     * 6����ʾ����ɫ�ı�ʶ::������첿����Դ������Ŀ�겿���ж����ڣ����������߰汾��ͬ�Ļ�����ʾΪ��ɫ(black)��
     *    ������첿����Դ�����д��ڣ�������Ŀ�겿���в����ڵĻ�����ʾΪ��ɫ(green)��
     *    ������첿����Ŀ�겿���д��ڣ�������Դ�����в����ڵĻ�����ʾΪ��ɫ(purple)��
     *    ������첿����Դ��������Ŀ�겿���еİ汾����������ͬ�Ļ�����ʾΪ��ɫ(gray)��
     *    ��Ϣ"û�з������ù淶�İ汾"����ɫΪ��ɫ(red),���и���Ϣ���е���ɫΪ��ɫ(black)��
     *
     * 7:�����㲿����BsoID�������Դ�㲿�����Եģ�����Դ�㲿��ʹ�õĸò����㲿����BsoID��
     *
     * 8:�����㲿����BsoID, �����Ŀ���㲿�����Եģ�����Ŀ���㲿��ʹ�õĵĲ����㲿����BsoID��
     *
     * @param sourcePartIfc :QMPartIfcԴ������ֵ����
     * @param objectPartIfc :QMPartIfcĿ�겿����ֵ����
     * @param sourceConfigSpecIfc :PartConfigSpecIfcԴ������ɸѡ������
     * @param objectConfigSpecIfc :PartConfigSpecIfcĿ�겿����ɸѡ������
     * @param pathVector ����㲿������ǰ���첿����·����Ϣ��pathVector�����ݽṹ�͸÷����ķ���ֵ��ͬ��
     * @return vector :Vector
     * @throws QMException
     */
    private Vector difference(QMPartIfc sourcePartIfc, PartConfigSpecIfc sourceConfigSpecIfc,
        QMPartIfc objectPartIfc, PartConfigSpecIfc objectConfigSpecIfc, Vector pathVector,HashMap map) throws QMException
    {
    	

        //���Դ��롣
        differenceBeginDebug(sourcePartIfc, objectPartIfc, pathVector);
        //�������յķ��ؽ����
        Vector resultVector = new Vector();
        //������StandardServiceEJB�еķ���:getUsesPartIfc;
        Collection he11=null;
        Collection he22=null;
        Collection temp11 = new Vector();
        Collection temp22 = new Vector();
        Vector aa=(Vector)map.get("dangqian");
        Vector bb=(Vector)map.get("mubiao");

        try
        {
            //�����ȡԴ�㲿����������һ���Ӳ���:

            he11 = (Vector)PartServiceRequest.getUsesPartIfcs(sourcePartIfc, sourceConfigSpecIfc);

            //���Ӳ������Ϻϲ�����:
           Collection temp111=hebing(he11,sourcePartIfc.getCompare());

            for (Iterator ite = temp111.iterator(); ite.hasNext(); ) {

              Object[] obj = (Object[]) ite.next();

              QMPartIfc part =null;
                QMPartMasterIfc master=null;
              if(obj[1] instanceof QMPartIfc)
                 part = (QMPartIfc) obj[1];
               else
                 if(obj[1] instanceof QMPartMasterIfc)
                   master=(QMPartMasterIfc)obj[1];


              for (Iterator iter = aa.iterator(); iter.hasNext(); )
              {

                QMPartIfc apart = (QMPartIfc) iter.next();
                if(part!=null)
                {
                  if (apart.getBsoID().equals(part.getBsoID())) {
                    part.setCompare(part.getCompare()+apart.getCompare());

                      obj[1] = part;

                    break;
                  }
                }
                else
                      obj[1] = master;


              }
               temp11.add(obj);
            }

            he22 = (Vector)PartServiceRequest.getUsesPartIfcs(objectPartIfc, objectConfigSpecIfc);

            Collection temp222=hebing(he22,objectPartIfc.getCompare());

            for (Iterator ites = temp222.iterator(); ites.hasNext(); )
            {

            Object[] obj = (Object[]) ites.next();

            QMPartIfc part = null;
            QMPartMasterIfc master=null;
            if(obj[1] instanceof QMPartIfc)
               part= (QMPartIfc) obj[1];
               else
                 if(obj[1] instanceof QMPartMasterIfc)
                   master=(QMPartMasterIfc)obj[1];

            for (Iterator iter = bb.iterator(); iter.hasNext(); )
            {

              QMPartIfc apart = (QMPartIfc) iter.next();
                if(part!=null)
                {
                  if (apart.getBsoID().equals(part.getBsoID())) {
                    part.setCompare(part.getCompare()+apart.getCompare());

                      obj[1] = part;

                    break;
                  }
                }
                else
                      obj[1] = master;


            }
             temp22.add(obj);
          }


        }
        catch(QMException ex)
        {
            ex.printStackTrace();

            throw ex;
        }
        Vector temp1 = new Vector();
        Vector temp2 = new Vector();
        boolean flag1 = false;
        boolean flag2 = false;
        if(temp11 != null && temp11.size() > 0)
        {

            Object[] array = temp11.toArray();
            for (int i = 0; i < array.length; i++)
            {
                Object object01 = array[i];
                if (object01 instanceof Object[])
                {
                    Object[] object02 = (Object[]) object01;
                    if (object02[1] instanceof QMPartIfc ||
                        object02[1] instanceof QMPartMasterIfc)
                        temp1.addElement(object01);
                }
            }
            if(temp1 != null && temp1.size() > 0)
            {
                flag1 = true;
                //���Դ��롣��Ҫ������е�temp1�е����е�Ԫ��::
                differenceDebug1(sourcePartIfc, temp1);
            }
            //end if(temp1 != null && temp1.size() > 0)

        }
        //end if(temp11 != null && temp11.size() > 0)
        if(temp22 != null && temp22.size() > 0)
        {

            Object[] array = temp22.toArray();
            for (int i = 0; i < array.length; i++)
            {
                Object object01 = array[i];
                if (object01 instanceof Object[])
                {
                    Object[] object02 = (Object[]) object01;
                    if (object02[1] instanceof QMPartIfc ||
                        object02[1] instanceof QMPartMasterIfc)
                        temp2.addElement(object01);
                }
            }
            if(temp2 != null && temp2.size() > 0)
            {
                flag2 = true;
                //���Դ��롣
                differenceDebug2(objectPartIfc, temp2);
            }
            //end if(temp2 != null && temp2.size() > 0)

        }
        //end if(temp22 != null && temp22.size() > 0)
        //����Ĵ��벻��Ҫ�ı䡣!!!!!!!!!!!!!!!!!!!!!!!

        //������Ҫ���ݷ���ֵtemp1, temp2��ֵ���������ȷ�����к��ֵĴ���:::
        //1�����temp1Ϊ�գ�����temp2Ҳ�ǿգ�ʲôҲ������ֱ�ӷ���
        //2�����temp1Ϊ�գ�����temp2���ǿգ���ֱ�ӷ���temp2�е����е�Ԫ�أ��������������κεĴ���
        //3�����temp1��Ϊ�գ�temp2Ϊ�գ���ֱ�ӷ���temp2�е�����Ԫ�أ��������������κεĴ���
        //4�����temp1��temp2����Ϊ�յ�����£��ٽ��и��ӵ�ҵ����
        //////////////////////////////////////////////////////////////////////////////////////////////
        //���ڴ���1:
        if(flag1 == false && flag2 == false)
        {
            //���Դ��롣
            differenceDebug3(resultVector);
            return resultVector;
        }
        //////////////////////////////////////////////////////////////////////////////////////////////
        //���ڴ���2�����temp1Ϊ�գ�����temp2���ǿգ���ֱ�ӷ���temp2�е����е�Ԫ�أ��������������κεĴ���
        //��Ҫ����pathVector��ȷ����resultVector����ӵ���������pathVector��size()Ϊ>0����pathVector�е�
        //����ֱ�Ӽӵ�resultVector�У����size()Ϊ0���Ͱ�temp2�е�����Ԫ�ؽ�����֯�����ŵ�resultVector�С�

        if((flag1 == false)&&(flag2 == true))
        {

            return process2(pathVector, resultVector, temp2);
        }
        //end if((flag1 == false)&&(flag2 == true))
        //////////////////////////////////////////////////////////////////////////////////////////////
        //���ڴ���3�����temp1��Ϊ�գ�temp2Ϊ�գ���ֱ�ӷ���temp2�е�����Ԫ�أ��������������κεĴ���
        if((flag1 == true)&&(flag2 == false))
        {

            return process3(pathVector, resultVector, temp1);
        }
        //end if((flag1 == true)&&(flag2 == false))
        //����Ĵ����޸���ϡ�!!!!!!!!!!!!!!!!!!!!
        //////////////////////////////////////////////////////////////////////////////////////////////
        //��ǰ����������������ʱ�򣬾���temp1 != null ����temp2 != null��ʱ��
        //���Զ���������Ĵ��룺������
       
        return process4(sourceConfigSpecIfc, objectConfigSpecIfc, pathVector, resultVector, temp1, temp2,map);
    }

    private Vector process2(Vector pathVector, Vector resultVector,
                            Vector temp2)
    {
        float quantity2 = 0.0f;
        String unit2 = "", version2 = "", viewName2 = "";
        String partNumber2 = "", partName2 = "", partBsoID2 = "";
        //level��temp2��Ԫ�����ڵĲ�κ�:
        int level = pathVector.size() + 1;
        //��Ҫ�Ȱ�pathVector�е����е�Ԫ�ض��ӵ�resultVector��ȥ:
        for(int i=0; i<pathVector.size(); i++)
        {
            String[] stringArray = (String[])pathVector.elementAt(i);
            resultVector.addElement(stringArray);
        }
        //end for(int i=0; i<pathVector.size(); i++)
        //��temp2�е�����Ԫ�ض��ӵ�resultVector��.
        for(int i=0; i<temp2.size(); i++)
        {
            if(temp2.elementAt(i) instanceof Object[])
            {
                Object[] tempObj = (Object[])temp2.elementAt(i);
                //��Ҫ����tempObj[1]���ж���QMPartIfc, ������QMPartMasterIfc
                if(tempObj[1] instanceof QMPartIfc)
                {
                    QMPartIfc partIfc = (QMPartIfc)tempObj[1];
                    partName2 = partIfc.getPartName();
                    partNumber2 = partIfc.getPartNumber();
                    version2 = partIfc.getVersionValue();
                    viewName2 = partIfc.getViewName();
                    partBsoID2 = partIfc.getBsoID();
                    quantity2 = ((PartUsageLinkIfc)tempObj[0]).getQuantity();
                    unit2 = (((PartUsageLinkIfc)tempObj[0]).getDefaultUnit()).getDisplay();
                    String[] tempArray12 = new String[18];
                    tempArray12[0] = (new Integer(level)).toString();
                    //��ÿ��Ա�����νṹ��
                    String dian = "";
                    int tempLevel = level;
                    while (tempLevel > 0)
                    {
                        dian = dian + "";
                        tempLevel--;
                    }
                    tempArray12[1] = dian + partNumber2 + "(" + partName2 + ")";
                    tempArray12[2] = "";
                    tempArray12[3] = "";
                    if(viewName2 != null && viewName2.length() > 0)
                        tempArray12[4] = version2 + "(" + viewName2 + ")";
                    else
                        tempArray12[4] = version2;
                    String tempQuantity = String.valueOf(quantity2);
                    if (tempQuantity.endsWith(".0"))
                        tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                    tempArray12[5] = tempQuantity + "(" + unit2 + ")";
                    //��ɫ
                    tempArray12[6] = "purple";
                    //�������һ��:BsoID
                    tempArray12[7] = "";
                    tempArray12[8] = partBsoID2;
                    tempArray12[9] = "false";
                    tempArray12[10] = Boolean.toString(this.isHasSubPart(partIfc));
                    tempArray12[11]=partNumber2;
                        tempArray12[12]=partName2;
                        tempArray12[13]=tempQuantity;
                        tempArray12[14]=version2;
                        tempArray12[15] = "ȡ��";
                        tempArray12[16] = "";
                        //CCBegin by leixiao 2010-2-24������������״̬����ʾ
                        tempArray12[17] = partIfc.getLifeCycleState().getDisplay();
                        //CCEnd by leixiao 2010-2-24������������״̬����ʾ
                        resultVector.addElement(tempArray12);
                }
                //���г���ֻ��QMPartMasterIfc��Ԫ�ء�
                else if (tempObj[1] instanceof QMPartMasterIfc)
                {
                    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc) tempObj[1];
                    partName2 = partMasterIfc.getPartName();
                    partNumber2 = partMasterIfc.getPartNumber();
                    version2 = ""; //"û���ҵ����ʵİ汾"
                    viewName2 = "";
                    partBsoID2 = partMasterIfc.getBsoID();
                    quantity2 = ((PartUsageLinkIfc) tempObj[0]).getQuantity();
                    unit2 = (((PartUsageLinkIfc) tempObj[0]).getDefaultUnit()).getDisplay();
                    String[] tempArray12 = new String[15];
                    tempArray12[0] = (new Integer(level)).toString();
                    tempArray12[1] = partNumber2 + "(" + partName2 + ")";
                    tempArray12[2] = "";
                    tempArray12[3] = "";
                    tempArray12[4] = "";
                    String tempQuantity = String.valueOf(quantity2);
                    if (tempQuantity.endsWith(".0"))
                        tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                    tempArray12[5] = tempQuantity + "(" + unit2 + ")";
                    //��ɫ
                    tempArray12[6] = "red";
                    //�������һ��:BsoID
                    tempArray12[7] = "";
                    tempArray12[8] = "";
                    tempArray12[9] = "false";
                    tempArray12[10] = "false";
                    tempArray12[11]=partNumber2;
                        tempArray12[12]=partName2;
                        tempArray12[13]=tempQuantity;
                        tempArray12[14]=version2;

                    resultVector.addElement(tempArray12);
                }
                //end if(tempObj[1] instanceof QMPartMasterIfc)
            }
            //end if(temp2.elementAt(i) instanceof Object[])
        }
        //end for(int i=0; i<temp2.size(); i++)
        //���Դ��롣
        differenceDebug4(resultVector);
        return resultVector;
    }

    private Vector process3(Vector pathVector, Vector resultVector,
                            Vector temp1)
    {
        float quantity1 = 0.0f;
        String unit1 = "", version1 = "", viewName1 = "";
        String partNumber1 = "", partName1 = "", partBsoID1 = "";
        //level��temp1��Ԫ�����ڵĲ�κ�:
        int level = pathVector.size() + 1;
        //��Ҫ�Ȱ�pathVector�е����е�Ԫ�ض��ӵ�resultVector��ȥ:
        for(int i=0; i<pathVector.size(); i++)
        {
            String[] stringArray = (String[])pathVector.elementAt(i);
            resultVector.addElement(stringArray);
        }
        //end for(int i=0; i<pathVector.size(); i++)
        //��temp1�е�����Ԫ�ض��ӵ�resultVector��.
        for(int i=0; i<temp1.size(); i++)
        {
            if(temp1.elementAt(i) instanceof Object[])
            {
                Object[] tempObj = (Object[])temp1.elementAt(i);
                //��Ҫ����tempObj[1]���ж���QMPartIfc, ������QMPartMasterIfc
                if(tempObj[1] instanceof QMPartIfc)
                {
                    QMPartIfc partIfc = (QMPartIfc)tempObj[1];
                    partName1 = partIfc.getPartName();
                    partNumber1 = partIfc.getPartNumber();
                    version1 = partIfc.getVersionValue();
                    viewName1 = partIfc.getViewName();
                    partBsoID1 = partIfc.getBsoID();
                    quantity1 = ((PartUsageLinkIfc)tempObj[0]).getQuantity();
                    unit1 = (((PartUsageLinkIfc)tempObj[0]).getDefaultUnit()).getDisplay();
                    String[] tempArray12 = new String[18];
                    tempArray12[0] = (new Integer(level)).toString();
                    //��ÿ��Ա�����νṹ��
                    String dian = "";
                    int tempLevel = level;
                    while (tempLevel > 0)
                    {
                        dian = dian + "";
                        tempLevel--;
                    }
                    tempArray12[1] = dian + partNumber1 + "(" + partName1 + ")";
                    if(viewName1 != null && viewName1.length() > 0)
                        tempArray12[2] = version1 + "(" + viewName1 + ")";
                    else
                        tempArray12[2] = version1;
                    String tempQuantity = String.valueOf(quantity1);
                    if (tempQuantity.endsWith(".0"))
                        tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                    tempArray12[3] = tempQuantity + "(" + unit1 + ")";
                    tempArray12[4] = "";
                    tempArray12[5] = "";
                    //��ɫ
                    tempArray12[6] = "green";
                    //�������һ�BsoID:
                    tempArray12[7] = partBsoID1;
                    tempArray12[8] = "";
                    tempArray12[9] = Boolean.toString(this.isHasSubPart(partIfc));
                    tempArray12[10] = "false";
                    tempArray12[11]=partNumber1;
                      tempArray12[12]=partName1;
                      tempArray12[13]=tempQuantity;
                      tempArray12[14]=version1;
                      tempArray12[15] = "����";
                      tempArray12[16] = "";
                      //CCBegin by leixiao 2010-2-24������������״̬����ʾ
                      tempArray12[17] = partIfc.getLifeCycleState().getDisplay();
                      //CCEnd by leixiao 2010-2-24������������״̬����ʾ
                    resultVector.addElement(tempArray12);


                }
                //���г���ֻ��QMPartMasterIfc��Ԫ�ء�
                else if (tempObj[1] instanceof QMPartMasterIfc)
                {
                    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc) tempObj[1];
                    partName1 = partMasterIfc.getPartName();
                    partNumber1 = partMasterIfc.getPartNumber();
                    version1 = "";
                    viewName1 = "";
                    partBsoID1 = partMasterIfc.getBsoID();
                    quantity1 = ((PartUsageLinkIfc) tempObj[0]).getQuantity();
                    unit1 = (((PartUsageLinkIfc) tempObj[0]).getDefaultUnit()).getDisplay();
                    String[] tempArray12 = new String[15];
                    tempArray12[0] = (new Integer(level)).toString();
                    //��ÿ��Ա�����νṹ��
                    String dian = "";
                    int tempLevel = level;
                    while (tempLevel > 0)
                    {
                        dian = dian + "";
                        tempLevel--;
                    }
                    tempArray12[1] = dian + partNumber1 + "(" + partName1 + ")";
                    tempArray12[2] = "";
                    String tempQuantity = String.valueOf(quantity1);
                    if (tempQuantity.endsWith(".0"))
                        tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                    tempArray12[3] = tempQuantity + "(" + unit1 + ")";
                    tempArray12[4] = "";
                    tempArray12[5] = "";
                    //��ɫ
                    tempArray12[6] = "red";
                    //�������һ�BsoID:
                    tempArray12[7] = "";
                    tempArray12[8] = "";
                    tempArray12[9] = "false";
                    tempArray12[10] = "false";
                    tempArray12[11]=partNumber1;
                      tempArray12[12]=partName1;
                      tempArray12[13]=tempQuantity;
                      tempArray12[14]=version1;

                    resultVector.addElement(tempArray12);
                }
                //end if(tempObj[1] instanceof QMPartMasterIfc)
            }
            //end if(temp1.elementAt(i) instanceof Object[])
        }
        //end for(int i=0; i<temp1.size(); i++)
        //���Դ��롣
        differenceDebug5(resultVector);
        return resultVector;
    }

    private Vector process4(PartConfigSpecIfc sourceConfigSpecIfc,
                            PartConfigSpecIfc objectConfigSpecIfc,
                            Vector pathVector, Vector resultVector,
                            Vector temp1, Vector temp2,HashMap map)
            throws QMException
    {
         
        float quantity1 = 0.0f, quantity2 = 0.0f;
        String unit1 = "", unit2 = "";
        String version1 = "", version2 = "", viewName1 = "", viewName2 = "";
        String partNumber1 = "", partNumber2 = "", partName1 = "", partName2 = "";
        String partBsoID1 = "", partBsoID2 = "";
         String compare1="";
         String compare2="";
        //CCBegin SS1
        String lstate = "";
        //CCEnd SS1
        boolean flag;
        int level = pathVector.size() + 1;
        //�ü�������������temp1����Ԫ�أ����Ǹ�Ԫ�ز���temp2�е���ЩԪ��:
        Vector temp1Rest = new Vector();
        for (int i=0; i<temp1.size(); i++)
        {

          QMPartIfc soupartIfc=null;
            QMPartIfc objpartIfc=null;
            //����������������ʶ�ӽڵ���QMPartIfc, ������QMPartMasterIfc,
            //��masterFlag1 = false��ʱ�򣬱�ʾԴ�����ĵ�ǰ�ӽڵ㲿����QMPartIfc,
            //���򣬱�ʾԴ�����ĵ�ǰ�ӽڵ㲿����QMPartMasterIfc.
            //��masterFlag2 ͬ��
            boolean masterFlag1 = false, masterFlag2 = false;
            flag = false;
            if(temp1.elementAt(i) instanceof Object[])
            {

                Object[] tempArray7 = (Object[])temp1.elementAt(i);
                if(tempArray7[1] instanceof QMPartIfc)
                {
                   soupartIfc = (QMPartIfc)tempArray7[1];
                    partNumber1 = soupartIfc.getPartNumber();
                    partName1 = soupartIfc.getPartName();
                    version1 = soupartIfc.getVersionValue();
                    viewName1 = soupartIfc.getViewName();
                    partBsoID1 = soupartIfc.getBsoID();
                    compare1=soupartIfc.getCompare();
                    //CCBegin SS1
                    lstate = soupartIfc.getLifeCycleState().getDisplay();
                    //CCEnd SS1

                }
                else
                {
                    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)tempArray7[1];
                    partNumber1 = partMasterIfc.getPartNumber();
                    partName1 = partMasterIfc.getPartName();
                    partBsoID1 = "";
                    //��Ҫһ����ʶflag = "red"�� masterFlag1 = true;
                    masterFlag1 = true;
                    //CCBegin SS1
                    lstate = "";
                    //CCEnd SS1
                }
                //end if and else (tempArray7[1] instanceof QMPartIfc)
                quantity1 = ((PartUsageLinkIfc)tempArray7[0]).getQuantity();
                unit1 = (((PartUsageLinkIfc)tempArray7[0]).getDefaultUnit()).getDisplay();
                //��Ŀ�겿���������Ӳ�������ѭ��:
                for (int j=0; j<temp2.size(); j++)
                {

                    masterFlag2 = false;
                    if(temp2.elementAt(j) instanceof Object[])
                    {
                        Object[] tempArray8 = (Object[])temp2.elementAt(j);
                        if(tempArray8[1] instanceof QMPartIfc)
                        {
                            objpartIfc = (QMPartIfc)tempArray8[1];
                            partNumber2 = objpartIfc.getPartNumber();
                            partName2 = objpartIfc.getPartName();
                            version2 = objpartIfc.getVersionValue();
                            viewName2 = objpartIfc.getViewName();
                            partBsoID2 = objpartIfc.getBsoID();
                            compare2=objpartIfc.getCompare();
                        }
                        else
                        {
                            QMPartMasterIfc partMasterIfc2 = (QMPartMasterIfc)tempArray8[1];
                            partNumber2 = partMasterIfc2.getPartNumber();
                            partName2 = partMasterIfc2.getPartName();
                            partBsoID2 = "";
                            //��Ҫһ����ʶflag = "red", masterFlag2 = true;
                            masterFlag2 = true;
                        }
                        //end if and else (tempArray8[1] instanceof QMPartIfc)
                        quantity2 = ((PartUsageLinkIfc)tempArray8[0]).getQuantity();
                        unit2 = (((PartUsageLinkIfc)tempArray8[0]).getDefaultUnit()).getDisplay();
                        //��������㲿����Ӧͬһ��QMPartMaster��
                        boolean flaga=(compare1.trim().length()!=0&&compare2.trim().length()!=0)&&(compare1.equals(compare2)&&!compare1.endsWith("a"));

                        if (partNumber1.equals(partNumber2) && partName1.equals(partName2))
                        {

                            flag = true;
                            temp2.setElementAt("null", j);
                            //��������ڵ�ʹ�õ���������ͬ�Ļ�������masterFlag1 masterFlag2�ж���false:
                            //�����е����⣺����������������㣬���ǰ汾����ͬ��
                            //Ҳ�ǲ��첿������Ҫֱ�ӷŵ���������У�������Ҫ�ټ����ݹ��ˣ�
                            //�������������ǣ������㲿��������ʹ�õ�������ͬ�����������㲿������
                            //���ϸ������ù淶�İ汾��

                            if ((quantity1 == quantity2 && masterFlag1 == false && masterFlag2 == false))
                            {
                                //��ʹ�汾��ͬ�Ļ���Ҳ��Ҫ�������бȽϣ�
                                //if(version1.equals(version2) || !version1.equals(version2))
                                {
                                    //��Ҫ�ѵ�ǰ��sourcePart, objectPart�ӵ�pathVector�У�
                                    String[] tempArray3 = new String[17];
                                    tempArray3[0] = (new Integer(level)).toString();
                                    //��ÿ��Ա�����νṹ��
                                    String dian = "";
                                    int tempLevel = level;
                                    while (tempLevel > 0)
                                    {
                                        dian = dian + "";
                                        tempLevel--;
                                    }
                                    tempArray3[1] = dian + partNumber1 + "(" + partName1 + ")";
                                    if(viewName1 != null && viewName1.length() > 0)
                                        tempArray3[2] = version1 + "(" + viewName1 + ")";
                                    else
                                        tempArray3[2] = version1;
                                    String tempQuantity = String.valueOf(quantity1);
                                    if (tempQuantity.endsWith(".0"))
                                        tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                                    tempArray3[3] = tempQuantity + "(" + unit1 + ")";
                                    if(viewName2 != null && viewName2.length() > 0)
                                        tempArray3[4] = version2 + "(" + viewName2 + ")";
                                    else
                                        tempArray3[4] = version2;
                                    String tempQuantity2 = String.valueOf(quantity2);
                                    if (tempQuantity2.endsWith(".0"))
                                        tempQuantity2 = tempQuantity2.substring(0, tempQuantity2.length() - 2);
                                    tempArray3[5] = tempQuantity2 + "(" + unit2 + ")";
                                    //��ɫ
                                    //��������㲿���İ汾��ͬ�Ļ�������"gray"������汾��ͬ�Ļ�������"black"
                                    if(version1.equals(version2))
                                        tempArray3[6] = "gray";
                                    else
                                        tempArray3[6] = "black";
                                    //����һ�partBsoID:
                                    tempArray3[7] = partBsoID1;
                                    tempArray3[8] = partBsoID2;

                                    tempArray3[9]="false";

                                    tempArray3[10] = "false";
                                    tempArray3[11] = partNumber1;
                                    tempArray3[12] = partName1;
                                    tempArray3[13] = tempQuantity2;
                                    tempArray3[14] = version2;
                                    tempArray3[15] = version1;
                                    tempArray3[16] = tempQuantity;
                                    //��װ�ڵ�·��:::
                                    pathVector.addElement(tempArray3);
                                    //��������Ҫ����tempArray3����ɫ���Խ�����Ͻ�����ӣ�
                                    if(!tempArray3[6].equals("gray"))
                                    {
                                        //��Ҫ��pathVector�е�����Ԫ�ؼӵ���������У�
                                        //2003.09.04�����ӣ����pathVector�е���ɫ����gray�Ļ����ͼӵ����Ľ�������У�
                                        ///////////////////////////////////////////////////////////////////////////////
                                        for (int ii = 0; ii < pathVector.size(); ii++)
                                        {
                                            String[] str = (String[])pathVector.elementAt(ii);
                                            resultVector.addElement(str);
                                        }
                                        ///////////////////////////////////////////////////////////////////////////////
                                    }

                                    Vector tempResult = difference((QMPartIfc)tempArray7[1], sourceConfigSpecIfc, (QMPartIfc)tempArray8[1], objectConfigSpecIfc, pathVector,map);
                                    int tempSize = pathVector.size() - 1;
                                    pathVector.removeElementAt(tempSize);
                                    //��Ҫ��tempResult�е�����Ԫ�ؼӵ�resultVector�У���󷵻ء�

                                    for (int k=0; k<tempResult.size(); k++)
                                    {
                                        String[] tempSs = (String[])tempResult.elementAt(k);
                                        resultVector.addElement(tempSs);
                                    }

                                }
                            }
                            else
                            {

                                //�����¼���������д���:
                                //masterFlag1 == false && masterFlag2 == true
                                //masterFlag1 == true && masterFlag2 == false
                                //masterFlag1 == false && masterFlag2 == false ����quantity1 != quantity2
                                if(masterFlag1 == false && masterFlag2 == true)
                                {

                                    //��Ҫ�Ȱ�pathVector�е����е�Ԫ�ض��ŵ�String[]��ȥ:
                                    for(int mm  = 0; mm<pathVector.size(); mm++)
                                    {
                                        String[] stringArray00 = (String[])pathVector.elementAt(mm);
                                        resultVector.addElement(stringArray00);
                                    }
                                    String[] tempArray3 = new String[15];
                                    tempArray3[0] = (new Integer(level)).toString();
                                    //��ÿ��Ա�����νṹ��
                                    String dian = "";
                                    int tempLevel = level;
                                    while (tempLevel > 0)
                                    {
                                        dian = dian + "";
                                        tempLevel--;
                                    }
                                    tempArray3[1] = dian + partNumber1 + "(" + partName1 + ")";
                                    if (viewName1 != null && viewName1.length() > 0)
                                        tempArray3[2] = version1 + "(" + viewName1 + ")";
                                    else
                                        tempArray3[2] = version1;
                                    String tempQuantity = String.valueOf(quantity1);
                                    if (tempQuantity.endsWith(".0"))
                                        tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                                    tempArray3[3] = tempQuantity + "(" + unit1 + ")";
                                    //"û���ҵ����ʰ汾"
                                    tempArray3[4] = "";
                                    String tempQuantity2 = String.valueOf(quantity2);
                                    if (tempQuantity2.endsWith(".0"))
                                        tempQuantity2 = tempQuantity2.substring(0, tempQuantity2.length() - 2);
                                    tempArray3[5] = tempQuantity2 + "(" + unit2 + ")";
                                    //��ɫ
                                    tempArray3[6] = "red";
                                    //�������һ�BsoID
                                    tempArray3[7] = partBsoID1;
                                    tempArray3[8] = partBsoID2;
                                     tempArray3[9]="false";
                                      tempArray3[10]="false";
                                      tempArray3[11] = partNumber1;
                                   tempArray3[12] = partName1;
                                   tempArray3[13] = tempQuantity2;
                                   tempArray3[14] = "";

                                    resultVector.addElement(tempArray3);
                                }
                                //end if(masterFlag1 == false && masterFlag2 == true)
                                if(masterFlag1 == true && masterFlag2 == false)
                                {

                                    //��Ҫ�Ȱ�pathVector�е����е�Ԫ�ض��ŵ�String[]��ȥ:
                                    for(int mm  = 0; mm<pathVector.size(); mm++)
                                    {
                                        String[] stringArray00 = (String[])pathVector.elementAt(mm);
                                        resultVector.addElement(stringArray00);
                                    }
                                    String[] tempArray3 = new String[15];
                                    tempArray3[0] = (new Integer(level)).toString();
                                    //��ÿ��Ա�����νṹ��
                                    String dian = "";
                                    int tempLevel = level;
                                    while (tempLevel > 0)
                                    {
                                        dian = dian + "";
                                        tempLevel--;
                                    }
                                    tempArray3[1] = dian + partNumber1 + "(" + partName1 + ")";
                                    //"û���ҵ����ʰ汾"
                                    tempArray3[2] = "";
                                    String tempQuantity = String.valueOf(quantity1);
                                    if (tempQuantity.endsWith(".0"))
                                        tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                                    tempArray3[3] = tempQuantity + "(" + unit1 + ")";
                                    if (viewName2 != null && viewName2.length() > 0)
                                        tempArray3[4] = version2 + "(" + viewName2 + ")";
                                    else
                                        tempArray3[4] = version2;
                                    String tempQuantity2 = String.valueOf(quantity2);
                                    if (tempQuantity2.endsWith(".0"))
                                        tempQuantity2 = tempQuantity2.substring(0, tempQuantity2.length() - 2);
                                    tempArray3[5] = tempQuantity2 + "(" + unit2 + ")";
                                    //��ɫ
                                    tempArray3[6] = "red";
                                    tempArray3[7] = partBsoID1;
                                    tempArray3[8] = partBsoID2;
                                    tempArray3[9]="false";
                                      tempArray3[10]="false";
                                      tempArray3[11] = partNumber1;
                                   tempArray3[12] = partName1;
                                   tempArray3[13] = tempQuantity2;
                                   tempArray3[14] = version2;

                                    resultVector.addElement(tempArray3);
                                }
                                //end if(masterFlag1 == true && masterFlag2 == false)
                                if(masterFlag1 == false && masterFlag2 == false && quantity1 != quantity2)
                                {

                                    //��Ҫ�Ȱ�pathVector�е����е�Ԫ�ض��ŵ�String[]��ȥ:
                                    for(int mm  = 0; mm<pathVector.size(); mm++)
                                    {
                                        String[] stringArray00 = (String[])pathVector.elementAt(mm);
                                        resultVector.addElement(stringArray00);
                                    }
                                    //CCBegin SS1
                                    //String[] tempArray3 = new String[17];
                                    String[] tempArray3 = new String[18];
                                    //CCEnd SS1
                                    tempArray3[0] = (new Integer(level)).toString();
                                    //��ÿ��Ա�����νṹ��
                                    String dian = "";
                                    int tempLevel = level;
                                    while (tempLevel > 0)
                                    {
                                        dian = dian + "";
                                        tempLevel--;
                                    }
                                    tempArray3[1] = dian + partNumber1 + "(" + partName1 + ")";
                                    if(viewName1 != null && viewName1.length() > 0)
                                        tempArray3[2] = version1 + "(" + viewName1 + ")";
                                    else
                                        tempArray3[2] = version1;
                                    String tempQuantity = String.valueOf(quantity1);
                                    if (tempQuantity.endsWith(".0"))
                                        tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                                    tempArray3[3] = tempQuantity + "(" + unit1 + ")";
                                    if (viewName2 != null && viewName2.length() > 0)
                                        tempArray3[4] = version2 + "(" + viewName2 + ")";
                                    else
                                        tempArray3[4] = version2;
                                    String tempQuantity2 = String.valueOf(quantity2);
                                    if (tempQuantity2.endsWith(".0"))
                                        tempQuantity2 = tempQuantity2.substring(0, tempQuantity2.length() - 2);
                                    tempArray3[5] = tempQuantity2 + "(" + unit2 + ")";
                                    //��ɫ
                                    tempArray3[6] = "black";
                                    tempArray3[7] = partBsoID1;
                                    tempArray3[8] = partBsoID2;

                                   tempArray3[9]="false";

                                     tempArray3[10]="false";
                                     tempArray3[11] = partNumber1;
                                  tempArray3[12] = partName1;
                                  tempArray3[13] = tempQuantity2;
                                  tempArray3[14] = version2;
                                  tempArray3[15] = version1;
                                  tempArray3[16] = tempQuantity;
                                  //CCBegin SS1
                                  tempArray3[17] = lstate;
                                  //CCEnd SS1
                                    resultVector.addElement(tempArray3);
                                }
                                //�����ȽϽṹ���㲿��������ͬ�Ĳ��������ù淶���Ӽ�
                                if(masterFlag1 == true && masterFlag2 == true )
                                {

                                  //��Ҫ�Ȱ�pathVector�е����е�Ԫ�ض��ŵ�String[]��ȥ:
                                  for(int mm  = 0; mm<pathVector.size(); mm++)
                                  {
                                      String[] stringArray00 = (String[])pathVector.elementAt(mm);
                                      resultVector.addElement(stringArray00);
                                  }
                                  String[] tempArray3 = new String[15];
                                  tempArray3[0] = (new Integer(level)).toString();
                                  //��ÿ��Ա�����νṹ��
                                  String dian = "";
                                  int tempLevel = level;
                                  while (tempLevel > 0)
                                  {
                                      dian = dian + "";
                                      tempLevel--;
                                  }
                                  tempArray3[1] = dian + partNumber1 + "(" + partName1 + ")";
                                  //"û���ҵ����ʰ汾"
                                  tempArray3[2] = "";
                                  String tempQuantity = String.valueOf(quantity1);
                                  if (tempQuantity.endsWith(".0"))
                                      tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                                  tempArray3[3] = tempQuantity + "(" + unit1 + ")";
                                      tempArray3[4] = "";
                                  String tempQuantity2 = String.valueOf(quantity2);
                                  if (tempQuantity2.endsWith(".0"))
                                      tempQuantity2 = tempQuantity2.substring(0, tempQuantity2.length() - 2);
                                  tempArray3[5] = tempQuantity2 + "(" + unit2 + ")";
                                  //��ɫ
                                  tempArray3[6] = "red";
                                  tempArray3[7] = partBsoID1;
                                  tempArray3[8] = partBsoID2;

                                   tempArray3[9]="false";

                                     tempArray3[10]="false";
                                     tempArray3[11] = partNumber1;
                                    tempArray3[12] = partName1;
                                    tempArray3[13] = tempQuantity2;
                                    tempArray3[14] = "";

                                  resultVector.addElement(tempArray3);


                                }
                                //end if(masterFlag1 == false && masterFlag2 == false && quantity1 != quantity2)
                            }
                            //end if and else (quantity1 == quantity2 && masterFlag1 == false && masterFlag2 == false)
                            //������������ͬ������Ĵ���
                            break;
                        }

                        //////whj add begin------------
                        else
                        if (flaga)
                        {

                          flag = true;
                          temp2.setElementAt("null", j);
                          //��������ڵ�ʹ�õ���������ͬ�Ļ�������masterFlag1 masterFlag2�ж���false:
                          //�����е����⣺����������������㣬���ǰ汾����ͬ��
                          //Ҳ�ǲ��첿������Ҫֱ�ӷŵ���������У�������Ҫ�ټ����ݹ��ˣ�
                          //�������������ǣ������㲿��������ʹ�õ�������ͬ�����������㲿������
                          //���ϸ������ù淶�İ汾��


                              //��Ҫ�ѵ�ǰ��sourcePart, objectPart�ӵ�pathVector�У�
                              String[] tempArray3 = new String[17];
                               // String[] tempArraya = new String[17];
                              tempArray3[0] = (new Integer(level)).toString();
                              //  tempArraya[0] = (new Integer(level)).toString();
                              //��ÿ��Ա�����νṹ��
                              String dian = "";
                              int tempLevel = level;
                              while (tempLevel > 0) {
                                dian = dian + "";
                                tempLevel--;
                              }
                              tempArray3[1] = dian + partNumber1+"("+partName1+")"+"*"+partNumber2+ "("+partName2 + ")";
                            //  tempArraya[1] = dian + partNumber2 + "(" + partName2 + ")";
                              if (viewName1 != null && viewName1.length() > 0)
                                tempArray3[2] = version1 + "(" + viewName1 + ")";
                              else
                                tempArray3[2] = version1;

                           //  tempArraya[2] = "";

                              String tempQuantity = String.valueOf(quantity1);

                              if (tempQuantity.endsWith(".0"))
                                tempQuantity = tempQuantity.substring(0,
                                    tempQuantity.length() - 2);


                              tempArray3[3] = tempQuantity + "(" + unit1 + ")";
                              // tempArraya[3] ="";
                              //if (viewName2 != null && viewName2.length() > 0)
                                tempArray3[4] = "";

                          //   if (viewName2!= null && viewName2.length() > 0)
                         // tempArraya[4] = version2 + "(" + viewName2 + ")";
                       // else
                       //  tempArraya[4] = version2;
                              String tempQuantity2 = String.valueOf(quantity2);
                              if (tempQuantity2.endsWith(".0"))
                                tempQuantity2 = tempQuantity2.substring(0,
                                    tempQuantity2.length() - 2);
                              tempArray3[5] ="";
                             //  tempArraya[5] = tempQuantity2 + "(" + unit2 + ")";
                              //��ɫ
                              //��������㲿���İ汾��ͬ�Ļ�������"gray"������汾��ͬ�Ļ�������"black"

                                tempArray3[6] = "gray";
                               //  tempArraya[6] = "gray";
                                //����һ�partBsoID:
                              tempArray3[7] = partBsoID1;
                              tempArray3[8] = "";
                            //  tempArraya[7] = "";
                            //  tempArraya[8] = partBsoID2;
                              tempArray3[9] = "false";
                           //    tempArraya[9] = "false";
                              tempArray3[10] = "false";
                          //     tempArraya[10] = "false";
                              tempArray3[11] = partNumber1+"*"+partNumber2;
                              tempArray3[12] = partName1+"*"+partName2;
                              tempArray3[13] = tempQuantity2;
                              tempArray3[14] = version2;
                              tempArray3[15] = "";
                              tempArray3[16] = tempQuantity2;
                             // tempArraya[11] = partNumber2;
                            // tempArraya[12] = partName2;
                            // tempArraya[13] = tempQuantity2;
                            // tempArraya[14] = version2;
                            // tempArraya[15] = "";
                           //  tempArraya[16] = tempQuantity2;

                            //  tempArraya[9] = "false";

                            //  tempArraya[10] = "false";


                              //��װ�ڵ�·��:::

                              pathVector.addElement(tempArray3);


                              Vector tempResult = difference( (QMPartIfc) tempArray7[1],
                                                             sourceConfigSpecIfc,
                                                             (QMPartIfc) tempArray8[1],
                                                             objectConfigSpecIfc,
                                                             pathVector, map);
                              int tempSize = pathVector.size() - 1;
                              pathVector.removeElementAt(tempSize);
                            //  pathVector.addElement(tempArraya);
                              //��Ҫ��tempResult�е�����Ԫ�ؼӵ�resultVector�У���󷵻ء�

                                resultVector.addElement(tempArray3);
                                // resultVector.addElement(tempArraya);
                              for (int k = 0; k < tempResult.size(); k++) {
                                String[] tempSs = (String[]) tempResult.elementAt(k);
                                resultVector.addElement(tempSs);
                              }

                            }



                        //////whj add end--------------



                        //end if ((partNumber1 == partNumber2)&&(partName1 == partName2))
                        //������ź����ƶ���ͬ������Ĵ���
                    }
                    //end if(temp2.elementAt(i) instanceof Object[])

                }
                //end for (int j=0; j<temp2.size(); j++)
                //��temp2�е�Ԫ�ر�����ɡ�
                //���flag == false,˵����temp2��û�к�temp1�е����Ԫ����ͬ��Ԫ��
                //�Ͱ�isHave1 = true, isHave2 = false, quantity1, quantity2 = 0
                //version1, version2 = null ===>resultVector�С�
                //˵��ֻ��tempArray1����������(partName1, partNumber1)Ԫ�ء�
                //�����Ԫ�ؼӵ�temp1Rest��ȥ
                if (flag == false)
                {
                	//liun 2005.12.26ȥ��if(masterFlag1 == false),Ϊ����ʾ��Դ�㲿���д���,
                	//����Ŀ���㲿���в����ڵ�partMaster.
                    //if(masterFlag1 == false)
                    temp1Rest.addElement(tempArray7);
                }
                //end if (flag == false)
            }
            //end if(temp1.elementAt(i) instanceof Object[])
        }
        //end for (int i=0; i<temp1.size(); i++)
        //�ڶ�temp1�е����е�Ԫ��ѭ��������Ϻ�temp1Rest�е���ЩԪ�ؾ���temp1���У�
        //����temp2��û�е��ˡ���Ҫ��temp1Rest�е����е�Ԫ�ؽ��д����ŵ�resultVector��ȥ��
        //��Ҫ�Ȱ�pathVector�е����е�Ԫ�ض��ŵ�String[]��ȥ:
        boolean pathVectorFlag = false;
        boolean temp2Flag = false;
        if(temp1Rest.size() > 0)
            pathVectorFlag = true;
        //���ʱ���Ѿ���temp2�е����е�ʣ�µ�Ԫ�ؽ����˱�ʶ���������"null"�Ļ����Ϳ���
        //���䱣�棬�������QMPartIfc, ������QMPartMasterIfc����temp2�е����е�Ԫ��
        //���д���

        for(int i=0; i<temp2.size(); i++)
        {
            Object obj = temp2.elementAt(i);
            if(obj != "null" && obj instanceof Object[])
            {
                Object[] obj1 = (Object[]) temp2.elementAt(i);
                if (obj1[1] instanceof QMPartIfc && obj1[1] instanceof QMPartMasterIfc)
                {
                    temp2Flag = true;
                    break;
                }
                //end if (obj1[1] instanceof QMPartIfc)
            }
            //end if(obj != "null" && obj instanceof Object[])
        }
        //end for(int i=0; i<temp2.size(); i++)
        //���pathVectorFlag����temp2Flag����һ��Ϊ��Ļ���˵����ǰ����������һ���в�ͬ��
        //�ط��������Ļ����Ͳ���Ҫ�ٽ��бȽ��ˣ��ѵ�ǰ��·��part�ӵ����Ľ��������ȥ��
        if(pathVectorFlag || temp2Flag)
        {
            //�ѵ�ǰ��·���ڵ㶼�ӵ����Ľ��������ȥ:
            for(int mm  = 0; mm<pathVector.size(); mm++)
            {
                String[] stringArray00 = (String[])pathVector.elementAt(mm);
                resultVector.addElement(stringArray00);
            }
        }
        //end if(pathVectorFlag || temp2Flag)
        //Ŀǰ�и����⣺temp1Rest�е�Ԫ�ص����Ͷ���������Щ��part, partMaster,
        //��Ϊtemp1Rest��Դ��tempArray7,��tempArray7�е����е�Ԫ�ض���ֱ����Դ��
        //temp1���飬�����Ļ�temp1Rest�оͿ�����QMPartIfc, QMPartMasterIfc�������
        //���֡�
        for(int i=0; i<temp1Rest.size(); i++)
        {

            pathVectorFlag = true;
            Object obj = temp1Rest.elementAt(i);
            boolean istihuan=false;
            if(obj instanceof Object[])
            {
                Object[] objArray = (Object[])obj;
                //����ǰ��ķ���(2003.08.21:13:34),��Ҫ��objArray[1]Ԫ�ص���������
                //���зֱ���
                if(objArray[1] instanceof QMPartIfc)
                {

                    QMPartIfc partIfc = (QMPartIfc) objArray[1];
                    Collection pur=isAltCon(partIfc);
                    Object[] tihuan=null;
                    Vector tem=temp2;
                    for (int m = 0; m < tem.size(); m++) {

                      if (tem.elementAt(m) != "null" &&
                          tem.elementAt(m) instanceof Object[]) {

                        Object[] obja = (Object[]) tem.elementAt(m);
                        if (obja[1] instanceof QMPartIfc) {
                          QMPartIfc partifc = (QMPartIfc) obja[1];
                          for (Iterator ite = pur.iterator(); ite.hasNext(); ) {
                            PartAlternateLinkIfc altLink = (PartAlternateLinkIfc) ite.
                                next();
                            if (partifc.getMasterBsoID().equals(altLink.getRightBsoID())) {
                              istihuan = true;
                              tihuan=obja;
                              partifc.setCompare("tihuan$");
                              obja[1] = partifc;

                              temp2.setElementAt(obja, m);
                              break;
                            }
                          }

                        }
                      }
                    }
                    partNumber1 = partIfc.getPartNumber();

                    partName1 = partIfc.getPartName();
                    viewName1 = partIfc.getViewName();
                    version1 = partIfc.getVersionValue();
                    partBsoID1 = partIfc.getBsoID();
                    quantity1 = ( (PartUsageLinkIfc) objArray[0]).getQuantity();
                    unit1 = ( ( (PartUsageLinkIfc) objArray[0]).getDefaultUnit()).getDisplay();
                    String[] tempArray4 = new String[18];
                    tempArray4[0] = (new Integer(level)).toString();
                    //��ÿ��Ա�����νṹ��
                    String dian = "";
                    int tempLevel = level;
                    while (tempLevel > 0)
                    {
                        dian = dian + "";
                        tempLevel--;
                    }
                    tempArray4[1] = dian + partNumber1 + "(" + partName1 + ")";
                    if (viewName1 != null && viewName1.length() > 0)
                        tempArray4[2] = version1 + "(" + viewName1 + ")";
                    else
                        tempArray4[2] = version1;
                    String tempQuantity = String.valueOf(quantity1);
                    if (tempQuantity.endsWith(".0"))
                        tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                    tempArray4[3] = tempQuantity + "(" + unit1 + ")";
                    tempArray4[4] = "";
                    tempArray4[5] = "";
                    tempArray4[6] = "green";
                    tempArray4[7] = partBsoID1;
                    tempArray4[8] = "";
                    if (partBsoID1.length() > 0) {
                      tempArray4[9] = Boolean.toString(isHasSubPart(partIfc));
                    }
                    else
                      tempArray4[9] = "false";

                      tempArray4[10] = "false";
                      tempArray4[11] = partNumber1;
                      tempArray4[12] = partName1;
                      tempArray4[13] = tempQuantity;
                      tempArray4[14] = version1;
                      if(istihuan)
                      {
                        QMPartIfc par=(QMPartIfc)tihuan[1];

                        tempArray4[15] = "�滻";
                        tempArray4[16] = "ԭ���ţ�"+par.getPartNumber()
                            +"�汾��"+par.getVersionValue()+"������"+((PartUsageLinkIfc)tihuan[0]).getQuantity();
                      }
                       else
                       {
                         tempArray4[15] = "����";
                         tempArray4[16] = "";
                       }
                      //CCBegin by leixiao 2010-2-24������������״̬����ʾ
                      tempArray4[17] = partIfc.getLifeCycleState().getDisplay();
                      //CCEnd by leixiao 2010-2-24������������״̬����ʾ

                    resultVector.addElement(tempArray4);
                }
                else if (objArray[1] instanceof QMPartMasterIfc)
                {

                    //˵����Դ�����и����������û�з��ϵ�ǰԴ�������ù淶�İ汾
                    //��Ŀ���㲿����û�иò������������
                    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc) objArray[1];
                    partNumber1 = partMasterIfc.getPartNumber();
                    partName1 = partMasterIfc.getPartName();
                    viewName1 = "";
                    version1 = "";
                    partBsoID1 = partMasterIfc.getBsoID();
                    quantity1 = ((PartUsageLinkIfc) objArray[0]).getQuantity();
                    unit1 = (((PartUsageLinkIfc) objArray[0]).getDefaultUnit()).getDisplay();
                    String[] tempArray4 = new String[15];
                    tempArray4[0] = (new Integer(level)).toString();
                    //��ÿ��Ա�����νṹ��
                    String dian = "";
                    int tempLevel = level;
                    while (tempLevel > 0)
                    {
                        dian = dian + "";
                        tempLevel--;
                    }
                    tempArray4[1] = dian + partNumber1 + "(" + partName1 + ")";
                    //û�з��ϵ�ǰ���ù淶�İ汾��
                    tempArray4[2] = "";
                    String tempQuantity = String.valueOf(quantity1);
                    if (tempQuantity.endsWith(".0"))
                        tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                    tempArray4[3] = tempQuantity + "(" + unit1 + ")";
                    tempArray4[4] = "";
                    tempArray4[5] = "";
                    tempArray4[6] = "red";
                    tempArray4[7] = "";
                    tempArray4[8] = "";
                     tempArray4[9] = "false";
                       tempArray4[10] = "false";
                       tempArray4[11] = partNumber1;
                                tempArray4[12] = partName1;
                                tempArray4[13] = tempQuantity;
                                tempArray4[14] = "";

                    resultVector.addElement(tempArray4);
                }
                //end if(objArray[1] instanceof QMPartMasterIfc)
            }
            //end if(obj instanceof Object[])
        }
        //end for(int i=0; i<temp1Rest.size(); i++)
        //�ڶ�temp1�е�����Ԫ��ѭ��������Ϻ�temp2�е�ʣ�µ���ЩԪ�ؾ���temp1
        //��û�е��ˣ���temp2�е�ʣ��Ԫ�ؽ���ѭ���������ŵ�resultVector�С�
        //isHave1 = false, isHave2 = true, quantity1 = 0, quantity2 , version1 = null
        //version2 ===>resultVector�С����ݶ��������ķ�����temp2��Ҳ������
        //QMPartIfc,��QMPartMasterIfc�������Ļ������Էֱ���д����ˣ�
        for (int i=0; i<temp2.size(); i++)
        {

            if(temp2.elementAt(i) != "null" && temp2.elementAt(i) instanceof Object[])
            {

                Object[] obj = (Object[])temp2.elementAt(i);
                String[] tempArray5 = new String[18];
                if(obj[1] instanceof QMPartIfc)
                {
                  String com=((QMPartIfc)obj[1]).getCompare();

                  boolean tihuan=com.equals("tihuan$");

                    partName2 = ((QMPartIfc)obj[1]).getPartName();

                    partNumber2 = ((QMPartIfc)obj[1]).getPartNumber();

                    version2 = ((QMPartIfc)obj[1]).getVersionValue();
                    viewName2 = ((QMPartIfc)obj[1]).getViewName();
                    partBsoID2 = ((QMPartIfc)obj[1]).getBsoID();
                    quantity2 = ((PartUsageLinkIfc)obj[0]).getQuantity();
                    unit2 = ((PartUsageLinkIfc)obj[0]).getDefaultUnit().getDisplay();
                  //CCBegin by leixiao 2010-2-24������������״̬����ʾ
                    String partstate = ((QMPartIfc)obj[1]).getLifeCycleState().getDisplay();
                  //CCEnd by leixiao 2010-2-24������������״̬����ʾ
                    tempArray5[0] = (new Integer(level)).toString();
                    //��ÿ��Ա�����νṹ��
                    String dian = "";
                    int tempLevel = level;
                    while (tempLevel > 0)
                    {
                        dian = dian + "";
                        tempLevel--;
                    }
                    tempArray5[1] = dian + partNumber2 + "(" + partName2 + ")";
                    tempArray5[2] = "";
                    tempArray5[3] = "";
                    if(viewName2 != null && viewName2.length() > 0)
                        tempArray5[4] = version2 + "(" + viewName2 + ")";
                    else
                        tempArray5[4] = version2;
                    String tempQuantity = String.valueOf(quantity2);
                    if (tempQuantity.endsWith(".0"))
                        tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                    tempArray5[5] = tempQuantity + "(" + unit2 + ")";

                    tempArray5[6] = "purple";
                    tempArray5[7] = "";
                    tempArray5[8] = partBsoID2;
                    tempArray5[9]="false";

                     tempArray5[10]=Boolean.toString(isHasSubPart(((QMPartIfc)obj[1])));
                   tempArray5[11] = partNumber2;
                   tempArray5[12] = partName2;
                   tempArray5[13] = tempQuantity;
                   tempArray5[14] = version2;
                    if(tihuan)
                    {
                       tempArray5[15] = "tihuan";
                       tempArray5[16] = tempQuantity;
                    }

                    else
                    {
                       tempArray5[15] = "ȡ��";
                       tempArray5[16] = "";
                    }
                    //CCBegin by leixiao 2010-2-24������������״̬����ʾ
                    tempArray5[17] = partstate;
                    //CCEnd by leixiao 2010-2-24������������״̬����ʾ

                    resultVector.addElement(tempArray5);
                }
                //end if(obj[1] instanceof QMPartIfc)
                else if (obj[1] instanceof QMPartMasterIfc)
                {

                    partName2 = ((QMPartMasterIfc) obj[1]).getPartName();
                    partNumber2 = ((QMPartMasterIfc) obj[1]).getPartNumber();
                    version2 = "";
                    viewName2 = "";
                    partBsoID2 = ((QMPartMasterIfc) obj[1]).getBsoID();
                    quantity2 = ((PartUsageLinkIfc) obj[0]).getQuantity();
                    unit2 = ((PartUsageLinkIfc) obj[0]).getDefaultUnit().getDisplay();
                    tempArray5[0] = (new Integer(level)).toString();
                    //��ÿ��Ա�����νṹ��
                    String dian = "";
                    int tempLevel = level;
                    while (tempLevel > 0)
                    {
                        dian = dian + "";
                        tempLevel--;
                    }
                    tempArray5[1] = dian + partNumber2 + "(" + partName2 + ")";
                    tempArray5[2] = "";
                    tempArray5[3] = "";
                    //û���ҵ����ʵİ汾��
                    tempArray5[4] = "";
                    //end if and else (viewName2 != null && viewName2.length() > 0)
                    String tempQuantity = String.valueOf(quantity2);
                    if (tempQuantity.endsWith(".0"))
                        tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                    tempArray5[5] = tempQuantity + "(" + unit2 + ")";
                    tempArray5[6] = "red";
                    tempArray5[7] = "";
                    tempArray5[8] = "";
                    tempArray5[9] = "false";
                    tempArray5[10] = "false";
                    tempArray5[11] = partNumber2;
                  tempArray5[12] = partName2;
                  tempArray5[13] = tempQuantity;
                  tempArray5[14] = "";

                    resultVector.addElement(tempArray5);
                }
                //end if(obj[1] instanceof QMPartMasterIfc)
            }
            //end if(temp2.elementAt(i) != "null" && temp2.elementAt(i) instanceof Object[])
        }
        //end for (int i=0; i<temp.size(); i++)
     
        differenceEndDebug(resultVector);
        return resultVector;
    }
   private Collection isAltCon(QMPartIfc part)
   throws QMException
   {

     PersistService pService = (PersistService) EJBServiceHelper.
                                      getPersistService();
            QMQuery query = new QMQuery("PartAlternateLink");
            query.addCondition(new QueryCondition("leftBsoID", "=",
                                                  part.getMasterBsoID()));
            Collection queryresult = pService.findValueInfo(query);

      return queryresult;

   }
    private void differenceBeginDebug(QMPartIfc sourcePartIfc,
                                      QMPartIfc objectPartIfc,
                                      Vector pathVector)
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "difference() begin ....");
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "���뷽��difference()��Ĳ����ֱ���: Դ������" +
                        sourcePartIfc.getBsoID()
                        + "   Ŀ�겿����  " + objectPartIfc.getBsoID());
        PartDebug.trace(PartDebug.PART_SERVICE, "���뷽�����pathVector������Ϊ:");
        for (int i = 0; i < pathVector.size(); i++)
        {
            String[] str = (String[]) pathVector.elementAt(i);
            for (int j = 0; j < str.length; j++)
            {
                PartDebug.trace(PartDebug.PART_SERVICE, "  " + str[j]);
            }
        }
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");
    }

    private void differenceDebug1(QMPartIfc sourcePartIfc, Vector temp1)
    {
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "��ѯ������Դ����sourcePart:" + sourcePartIfc.getBsoID() +
                        "�ķ������ù淶���ӽڵ��У�");
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------");
        for (int s = 0; s < temp1.size(); s++)
        {
            Object[] obj = (Object[]) temp1.elementAt(s);
            PartDebug.trace(PartDebug.PART_SERVICE,
                            "   " + ((BaseValueIfc) obj[0]).getBsoID() +
                            "   "
                            + ((BaseValueIfc) obj[1]).getBsoID());
        }
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------");
    }

    private void differenceDebug2(QMPartIfc objectPartIfc, Vector temp2)
    {
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "��ѯ������Ŀ�겿��objectPart:" +
                        objectPartIfc.getBsoID() + "�ķ������ù淶���ӽڵ��У�");
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------");
        for (int s = 0; s < temp2.size(); s++)
        {
            Object[] obj = (Object[]) temp2.elementAt(s);
            PartDebug.trace(PartDebug.PART_SERVICE,
                            "   " + ((BaseValueIfc) obj[0]).getBsoID() +
                            "   "
                            + ((BaseValueIfc) obj[1]).getBsoID());
        }
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------");
    }

    private void differenceDebug3(Vector resultVector)
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "difference() end....return is " +
                        "new resultVector()!!!(null)");

        PartDebug.trace(PartDebug.PART_SERVICE,
                "-----------------------------------------------------");
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "if((temp1.size() == 0)&&(temp2.size() == 0))");
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "reslutVector.size():::" + resultVector.size());
        PartDebug.trace(PartDebug.PART_SERVICE, "���߶�û�з������ù淶���ӽڵ��ˡ�");
        PartDebug.trace(PartDebug.PART_SERVICE,
                "-----------------------------------------------------");
    }

    private void differenceDebug4(Vector resultVector)
    {
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "difference() end....return is Vector");

        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "if((flag1 == false)&&(flag2 == true))");
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "reslutVector.size():::" + resultVector.size());
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "Դ�������Ӳ����գ� Ŀ�겿���Ӳ����ǿա�" +
                        "difference()�����ķ���ֵ��::");
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");
        for (int i = 0; i < resultVector.size(); i++)
        {
            Object obj = resultVector.elementAt(i);
            if (obj instanceof String[])
            {
                String[] obj1 = (String[]) obj;
                for (int j = 0; j < obj1.length; j++)
                {
                    PartDebug.trace(PartDebug.PART_SERVICE,
                                    "    " + obj1[j]);
                }
            }
        }
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");
    }

    private void differenceDebug5(Vector resultVector)
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "difference() end....return is Vector");

        PartDebug.trace(PartDebug.PART_SERVICE,
                "--------------------------------------------------------------------------");
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "if((flag1 == true)&&(flag2 == false))");
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "reslutVector.size():::" + resultVector.size());
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "Դ�������Ӳ����ǿգ�Ŀ�겿�����Ӳ���Ϊ�ա�" +
                        "difference()�����ķ���ֵ��::");
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");
        for (int i = 0; i < resultVector.size(); i++)
        {
            Object obj = resultVector.elementAt(i);
            if (obj instanceof String[])
            {
                String[] obj1 = (String[]) obj;
                for (int j = 0; j < obj1.length; j++)
                {
                    PartDebug.trace(PartDebug.PART_SERVICE,
                                    "   " + obj1[j]);
                }
            }
        }
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");
    }

    private void differenceEndDebug(Vector resultVector)
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "difference() end....return is Vector");
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");
        PartDebug.trace( PartDebug.PART_SERVICE,
                        "if((flag1 == true)&&(flag2 == true))");
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "reslutVector.size():::" + resultVector.size());
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "��������Ϊ�յ�ʱ��difference()�����ķ���ֵ��::");
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");
        for (int i = 0; i < resultVector.size(); i++)
        {
            Object obj = resultVector.elementAt(i);
            if (obj instanceof Object[])
            {
                Object[] obj1 = (Object[]) obj;
                for (int j = 0; j < obj1.length; j++)
                {
                    PartDebug.trace(PartDebug.PART_SERVICE, "   " + obj1[j]);
                }
            }
        }
        //end for (int i = 0; i < resultVector.size(); i++)
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");
    }

    /**
     * ������collection�е�Ԫ����������ΪPartUsageLinkIfc������rightBsoID(�㲿��������Ϣ��bsoID)Ϊ���ֱ����ڹ������в����ع�����
     * @param collection :Collection PartUsageLinkIfc���󼯺ϡ�
     * @return hashtable:Hashtable ��������Ϊ�㲿������ϢbsoID��ֵΪPartUsageLinkIfc
     * @see PartUsageLinkInfo
     */
    public Hashtable consolidateUsageLink(Collection collection)
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "consolidateUsageLink() begin ....");
        Iterator iterator = collection.iterator();
        Hashtable hashtable = new Hashtable();
        PartUsageLinkIfc usagelink = null;
        PartUsageLinkIfc usagelink1 = null;
        String masterID = null;
        while(iterator.hasNext())
        {
            usagelink=(PartUsageLinkIfc)(iterator.next());
            masterID = usagelink.getRightBsoID();
            usagelink1 = (PartUsageLinkIfc)hashtable.get(masterID);
            if(usagelink1 == null)
            {
                hashtable.put(masterID,usagelink);
            }
            else
            {
                usagelink1.setQuantity(usagelink.getQuantity()+usagelink1.getQuantity());
            }
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "consolidateUsageLink() end...."+
                "return is Hashtable");
        return hashtable;
    }

    /**
     * ���partIfc����������PartUsageLinkIfc����ļ��ϡ�
     * @param partIfc :QMPartIfc����
     * @return collection:Colllection ��partIfc����������PartUsageLinkIfc���󼯺ϡ�
     * @throws QMException
     * @see QMPartInfo
     * @see PartUsageLinkInfo
     */
    public Collection getConsolidatedBOM(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getConsolidatedBOM() begin ....");
        if(partIfc == null)
        {
            Object[] obj = {"QMPartIfc"};
            throw new PartException(RESOURCE, "CP00001", obj);
        }
        Hashtable hashtable = new Hashtable();
        PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        Collection collection = pService.expandValueInfo(partIfc,"usedBy","PartUsageLink",false);
        hashtable = consolidateUsageLink(collection);
        Enumeration enum2 = hashtable.elements();
        Vector vector1 = new Vector();
        while(enum2.hasMoreElements())
        {
            vector1.add(enum2.nextElement());
            //����ѭ���н�enum�е�Ԫ�ط���collection1��
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getConsolidatedBOM() end...."+
                "return is Collection");
        return vector1;
    }

    /**
     * �����½��͸��µ���Ч�Է�����
     * @param configItemIfc :EffConfigurationItemIfc ���������Ч��������ֵ����
     * @throws QMException
     * @return EffConfigurationItemIfc �ѱ������Ч��������ֵ����
     * @see EffConfigurationItemIfc
     */
    public EffConfigurationItemIfc saveConfigItemIfc(EffConfigurationItemIfc configItemIfc)
        throws QMException
    {
      PartDebug.trace(this, PartDebug.PART_SERVICE, "saveConfigItemIfc() begin ....");
      if(configItemIfc.getConfigItemName() == null || configItemIfc.getConfigItemName().length() < 1)
      {
        throw new PartException(RESOURCE, "E03016", null);
      }
      PersistService service = (PersistService)EJBServiceHelper.getPersistService();
      EffConfigurationItemIfc oldIfc = null;
      if(configItemIfc.getBsoID() != null)
        oldIfc = (EffConfigurationItemIfc)service.refreshInfo(configItemIfc.getBsoID());
        //���֮ǰ�־û����������Ч�Է���
      if(oldIfc != null)
      {
        //�����Ч�Է����Ѿ�ָ����Ʒ�������
        if(oldIfc.getSolutionID() != null && oldIfc.getSolutionID().length() > 0)
        {
          //�����ǰҪ���޸Ĳ�Ʒ�������
          if(!oldIfc.getSolutionID().equals(configItemIfc.getSolutionID()))
            throw new QMException(RESOURCE, "4", null);
        }
        else
            //֮ǰû��ָ������Ʒ�������
          oldIfc.setSolutionID(configItemIfc.getSolutionID());
        oldIfc.setDescription(configItemIfc.getDescription());
      }
      else
      {
        QMQuery query = new QMQuery("QMConfigurationItem");
        QueryCondition condition = new QueryCondition("configItemName", "=", configItemIfc.getConfigItemName());
        query.addCondition(condition);
        PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        Collection collection = pService.findValueInfo(query);
        if(collection.size() > 0)
          throw new PartException(RESOURCE, "17",new Object[]{configItemIfc.getConfigItemName()});
        //֮ǰû�б���������Ƶ���Ч�Է���
        oldIfc = configItemIfc;
      }
      PartDebug.trace(this, PartDebug.PART_SERVICE, "saveConfigItemIfc() end...." + "return is EffConfigurationItemIfc");
      return(EffConfigurationItemIfc)service.saveValueInfo(oldIfc);
    }

    /**
     * ɾ����Ч�Է�����
     * @param configItemIfc EffConfigurationItemIfc ��Ч�Է�����
     * @throws QMException
     */
    public void deleteConfigItemIfc(EffConfigurationItemIfc configItemIfc)
            throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "deleteConfigItemIfc() begin ....");
        PersistService service = (PersistService)EJBServiceHelper.getPersistService();
        service.deleteValueInfo(configItemIfc);
        PartDebug.trace(this, PartDebug.PART_SERVICE, "deleteConfigItemIfc end....return is void");
    }

    /**
     * ����һ��EffGroup����
     * @param effectivityType :��Ч������,������"QMDatedEffectivity", "QMLotEffectivity", "QMSerialNumEffectivity"��
     * @param value_range :��Ч��ֵ�ķ�Χ��
     * @param configItemIfc :QMConfigurationItemIfc ��Ч��������ֵ����
     * @param partIfc :EffManagedVersionIfc ����Ч�ԺͰ汾����Ķ���ֵ����,����Ϊ�㲿��ֵ����
     * @return EffGroup ��������Ĳ����������µ�EffGroup����
     * @exception QMException
     */
    public EffGroup createEffGroup(String effectivityType, String value_range,
        QMConfigurationItemIfc configItemIfc, EffManagedVersionIfc partIfc)
            throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "createEffGroup() begin ....");
        EffGroup effGroup = new EffGroup(configItemIfc, effectivityType, value_range);
        if(partIfc != null)
        {
            effGroup.setTarget(partIfc);
            PartDebug.trace(this, PartDebug.PART_SERVICE, "createEffGroup() end....return is EffGroup");
            return effGroup;
        }
        else
        {
            PartDebug.trace(this, PartDebug.PART_SERVICE, "createEffGroup() end....return is null");
            return null;
        }
    }

    /**
     * ����ڵ�ǰ���ù淶��partMasterIfc�Ľṹ�������Ӽ���
     * @param partMasterIfc �㲿������Ϣֵ����
     * @param configSpecIfc �㲿�����ù淶ֵ����
     * @return QMPartIfc[] �������ù淶�������Ӳ���ֵ����ļ��ϡ�
     * @exception QMException
     * @see QMPartMasterInfo
     * @see QMPartInfo
     */
    public QMPartIfc[] getAllSubPartsByConfigSpec(QMPartMasterIfc partMasterIfc,
        PartConfigSpecIfc configSpecIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllSubPartsByConfigSpec(QMPartMasterIfc,"+
                " PartConfigSpecIfc) begin ....");
        //��һ��������QMPartMasterIfc��ȡ�������ù淶�ģ����°汾��QMPartIfc,
        //�ڶ��������÷���getAllSubPartsByConfigSpec(QMPartIfc, PartConfigSpecIfc)
        //��������������Ľ���������ŵ�QMPartIfc[]��ȥ��
        ConfigService service = (ConfigService)EJBServiceHelper.getService("ConfigService");
        Collection collection = service.filteredIterationsOf(partMasterIfc,
            new PartConfigSpecAssistant(configSpecIfc));

        if((collection == null)||(collection.size()>1)||(collection.size() == 0))
        {
            throw new PartException(RESOURCE, "7", null);
        }
        Iterator iterator = collection.iterator();
        //��������ת����iterator.next()������Object[]
        Object obj = iterator.next();
        QMPartIfc partIfc = null;
        if(obj instanceof Object[])
        {
            Object[] obj1 = (Object[])obj;
            partIfc = (QMPartIfc)obj1[0];
        }
        else
        {
            partIfc = (QMPartIfc)obj;
        }
        Vector tempVector = new Vector();
        tempVector = getAllSubPartsByConfigSpec(partIfc, configSpecIfc);
        QMPartIfc[] partIfc2 = new QMPartIfc[tempVector.size()];
        for(int i=0; i<tempVector.size(); i++)
        {
            partIfc2[i] = (QMPartIfc)tempVector.elementAt(i);
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllSubPartsByConfigSpec(QMPartMasterIfc, "+
                "PartConfigSpecIfc) end....return is QMPartIfc[]");
        return partIfc2;
    }
    //���!!!!

    /**
     * ���ݵ�ǰ���ù淶��ȡQMPartIfc�µ������Ӳ�����
     * @param partIfc �㲿��ֵ����
     * @param configSpecIfc �㲿�����ù淶ֵ����
     * @return Vector ������ϣ������е�Ԫ����QMPartIfc��
     * @throws QMException
     * //CCBegin by leixiao 2011-1-12 ԭ��:���·��������  private->public ����׼���ṹ�����
     */
    public Vector getAllSubPartsByConfigSpec(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
            throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllSubPartsByConfigSpec(QMPartIfc,"+
                " PartConfigSpecIfc) begin ....");
        Vector resultVector = new Vector();
        resultVector.addElement(partIfc);
        Vector tempVector = new Vector();
        Collection collection = PartServiceRequest.getUsesPartIfcs(partIfc, configSpecIfc);
        if((collection == null)||(collection.size() == 0))
        {
            PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllSubPartsByConfigSpec(QMPartIfc, "+
                    "PartConfigSpecIfc) end....return is Vector");
            return resultVector;
        }
        else
        {
            Object[] tempArray = (Object[])collection.toArray();
            //��collection�е�Ԫ�ؽ���ѭ�������÷���getAllSubPartsByConfigSpec(),������ӵ�resultVector��
            for(int i=0; i<tempArray.length; i++)
            {
                if(tempArray[i] instanceof Object[])
                {
                    Object[] obj = (Object[])tempArray[i];
                    if (obj[1] instanceof QMPartIfc)
                    {
                        tempVector = getAllSubPartsByConfigSpec((QMPartIfc)obj[1], configSpecIfc);
                        for (int j=0; j<tempVector.size(); j++)
                            resultVector.addElement(tempVector.elementAt(j));
                    }
                    //end if(tempArray)
                }
                //end if(tempArray[i] instanceof Object[])
            }
            //end for(int i)
        }
        //end if(collection) and else
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllSubPartsByConfigSpec(QMPartIfc, "+
                "PartConfigSpecIfc) end....return is Vector");
        //��Ҫ�Խ�����Ͻ��кϲ����������ͬ��QMPartIfc�Ļ����ϲ���һ��:
        Vector result = new Vector();
        for(int i=0; i<resultVector.size(); i++)
        {
            boolean flag1 = false;
            QMPartIfc partIfc1 = (QMPartIfc)resultVector.elementAt(i);
            String bsoID1 = partIfc1.getBsoID();
            for(int j=0; j<result.size(); j++)
            {
                QMPartIfc partIfc2 = (QMPartIfc)result.elementAt(j);
                if(bsoID1.equals(partIfc2.getBsoID()))
                {
                    flag1 = true;
                    break;
                }
            }
            if(flag1 == false)
            {
                result.addElement(partIfc1);
            }
        }
        return result;
    }
    //���!!!!

    /**
     * ���part�����Ѿ����ڵ�EffGroup����ļ��ϡ�
     * @param partIfc QMPartIfc �㲿��ֵ����
     * @throws QMException
     * @return Vector Vector�е�ÿ��Ԫ�ض���EffGroup����
     * @see QMPartInfo
     */
    public Vector outputExistingEffGroups(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "outputExistingEffGroups() begin ....");
        EffService effService = (EffService)EJBServiceHelper.getService("EffService");
        partIfc = (QMPartIfc)effService.populateEffVector(partIfc);
        Vector vector = partIfc.getEffVector();
        PartDebug.trace(this, PartDebug.PART_SERVICE, "outputExistingEffGroups() end...."+
                "return is Vector ");
        return vector;
    }

    /**
     * ����ڵ�ǰ���ù淶��partMasterIfc�Ľṹ�������Ӽ�����Ч�Է�Χ���������EffGroup��
     * ���ϡ��÷���������ʾ���ṹ�����Ч�Խ����
     * @param partMasterIfc :�㲿������Ϣֵ����
     * @param configSpecIfc :PartConfigSpecIfc ���ù淶ֵ����
     * @param configItemID ��Ч��������bsoID��
     * @param value_range :String ��Ч��ֵ��Χ��
     * @return Vector EffGroup���󼯺ϡ�
     * @throws QMException
     * @see QMPartMasterInfo
     * @see PartConfigSpecInfo
     * @see QMConfigurationItemInfo
     * @see QMPartInfo
     */
    public Vector getEffGroups(QMPartMasterIfc partMasterIfc, PartConfigSpecIfc configSpecIfc,
        String configItemID, String value_range) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getEffGroups() begin ....");
        QMPartIfc[] partIfcs = getAllSubPartsByConfigSpec(partMasterIfc,configSpecIfc);
        PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
        QMConfigurationItemIfc configItemIfc = null;
        if(configItemID != null && configItemID.length() > 0)
        {
            configItemIfc = (QMConfigurationItemIfc)pService.refreshInfo(configItemID);
        }
        Vector vectorAll = new Vector();
        if(configItemIfc != null)
        {
            if(configItemIfc instanceof QMConfigurationItemIfc)
            {
                String effectivityType = configItemIfc.getEffectivityType().toString();
                QMPartIfc partIfc = new QMPartInfo();
                for(int i=0;i<partIfcs.length;i++)
                {
                    partIfc = partIfcs[i];
                    createEffGroup(effectivityType,value_range,configItemIfc,partIfc);
                    Vector vector = outputExistingEffGroups(partIfc);
                    for(int j=0;j<vector.size();j++)
                    {
                        vectorAll.addElement(vector.elementAt(j));
                    }
                }
            }
            //"������Ч���������ʵ��"
            else
            {
                throw new PartException(RESOURCE, "5", null);
            }
        }
        //��������Ч�ԣ�
        else
        {
            String effectivityType = "QMDatedEffectivity";
            QMPartIfc partIfc = new QMPartInfo();
            for(int i=0;i<partIfcs.length;i++)
            {
                partIfc = partIfcs[i];
                createEffGroup(effectivityType,value_range,configItemIfc,partIfc);
                Vector vector = outputExistingEffGroups(partIfc);
                if(vector != null && vector.size() > 0)
                {
                    for(int j=0;j<vector.size();j++)
                    {
                        vectorAll.addElement(vector.elementAt(j));
                    }
                }
            }
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getEffGroups() end....return is Vector ");
        return vectorAll;
    }

    private Vector getEffVector(WorkableIfc part , String itemID, boolean isacl) throws QMException
    {

         if (WorkInProgressHelper.isWorkingCopy(part))
         {
              WorkInProgressService wipservice = (WorkInProgressService)EJBServiceHelper.
                  getService("WorkInProgressService");
              part = wipservice.originalCopyOf(part);
         }
         QMPartIfc partIfc = (QMPartIfc)part;
         PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");

         QMQuery query = new QMQuery("Eff");
         QueryCondition condition1 = new QueryCondition("targetBranchID","=",partIfc.getBranchID());
         query.addCondition(condition1);
         query.addAND();
         QueryCondition condition2 = new QueryCondition("effContextID","=",itemID);
         query.addCondition(condition2);
         Collection collection = pService.findValueInfo(query);
         Iterator iter1 = collection.iterator();
         EffIfc[] tempEffArray2 = new EffIfc[collection.size()];
         Vector result = new Vector();
         int i = 0;
         while(iter1.hasNext())
         {
             tempEffArray2[i] = (EffIfc)iter1.next();
             i++;
         }
         if(collection.size() == 1)
         {
              EffGroup tempEffGroup = new EffGroup(tempEffArray2[0],isacl);
              result.addElement(tempEffGroup);
         }
         else
         {
              EffGroup tempEffGroup = new EffGroup(tempEffArray2,partIfc,isacl);
              result.addElement(tempEffGroup);
         }
         return result;
    }

   /**
     * �����㲿��ֵ�����ȡ���㲿����������Ч��ֵ��
     * @param partIfc : WorkableIfc �㲿��ֵ����
     * @param isacl : boolean �Ƿ���ʿ��ơ�
     * @return Vector EffGroup�ļ��ϡ�
     * @throws QMException
     * @see WorkableIfc
     */
    public Vector getEffVector(WorkableIfc partIfc , boolean isacl) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "getEffVector() begin ....");
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "----------------------------------------------");
        PartDebug.trace(PartDebug.PART_SERVICE, "����Ĳ���Ϊ��" + partIfc);
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "----------------------------------------------");
        if (WorkInProgressHelper.isWorkingCopy(partIfc))
        {
            WorkInProgressService wipservice = (WorkInProgressService)EJBServiceHelper.
                getService("WorkInProgressService");
            partIfc = wipservice.originalCopyOf(partIfc);
        }
        //��ѯEff�µ������б���࣬��ȡ�ͱ�partIfc������������Ч��ֵ����
        QMQuery query = new QMQuery("Eff");
        QueryCondition condition = new QueryCondition("targetBranchID","=",partIfc.getBranchID());
        query.addCondition(condition);
        PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        Collection collection = pService.findValueInfo(query);
        //��Ҫ��collection�е�������Ч��ֵ����ת�Ƶ�EffIfc[]�У�������EffGroup(EffIfc[])���캯��
        //��һ��:��ȡ��ЩEffIfc�����е�EffContextID,
        Iterator iterator = collection.iterator();
        //vector�����Ҫô��effContextID + effIfc.getBsoName(), Ҫô��"null" :
        Vector vector = new Vector();
        EffIfc effIfc = null;
        //���㲿��partIfc:QMPartIfc�������ģ����е���Ч��effIfc����ļ��ϣ�
        Vector tempEffArray1 = new Vector();
        //ԭ���ǰ�EffContextID��ΪΨһ�Ĺؼ��֣�������Ҫ��Ͼ������Ч�Ե�BsoName��������
        //�����������ȷ���Ƿ����Ч��ֵ���кϲ�:
        while(iterator.hasNext())
        {
            effIfc = (EffIfc)iterator.next();
            tempEffArray1.addElement(effIfc);
            //�ô���Ӧ�ü����жϣ����getEffContextID()Ϊ�յĻ�
            String str = effIfc.getEffContextID();
            if(str != null && str.length() > 0)
            {
                //��EffContextID�;������Ч�Ե�BsoName���������
                str = str + effIfc.getBsoName();
                if(!vector.contains(str))
                {
                    //vector �д�ŵ��Ǻ�effIfc��������Ч���������bsoID
                    vector.addElement(str);
                }
            }
            else
            {
                if(!vector.contains("null"))
                {
                    vector.addElement("null");
                }
            }
            //end if and else if(str != null && str.length() > 0)
        }
        //end while(iterator.hasNext())
        //result�������Ľ��,ΪEffGroup�ļ���
        Vector result = new Vector();
        //�ڶ���:����vector�е�ÿһ��EffContextIDֵ������tempEffArray1�е����е�Ԫ��
        //�����vector�е�ÿ��EffContextID����һ��EffGroup����, ���е�EffContextID
        //��ͬ��EffIfc����ͳһ�ķ���tempEffArray2�С�
        for(int i=0; i<vector.size(); i++)
        {
            String s1 = (String)vector.elementAt(i);
            Vector tempEffArray2 = new Vector();
            //�����Ǹ���EffContextID������Ч��ֵ���кϲ����µ�����Ҫ����Բ�ָ��EffContext
            //��bsoID�������Ļ�����Ҫ���EffContextID��EffectivityType������������
            //��Ч��ֵ���кϲ���
            for(int j=0; j<tempEffArray1.size(); j++)
            {
                EffIfc effIfc1 = (EffIfc)tempEffArray1.elementAt(j);
                String s2 = effIfc1.getEffContextID();
                //ע�������s2�п�����null�������Ļ����Ͳ���������ˣ�
                if(s2 != null && s2.length() > 0)
                {
                    s2 = s2 + effIfc1.getBsoName();
                    if(s1.equals(s2))
                    {
                        tempEffArray2.addElement(effIfc1);
                    }
                }
                else
                {
                    s2 = "null";
                    if(s1.equals(s2))
                    {
                        tempEffArray2.addElement(effIfc1);
                    }
                }
                //end if and else if(s2 != null && s2.length() > 0)
            }
            //end for(int j=0; j<tempEffArray1.size(); j++)
            //����tempEffArray2��������һ��Ԫ��EffIfc
            if(tempEffArray2.size() == 1)
            {
                EffGroup tempEffGroup = new EffGroup((EffIfc)tempEffArray2.elementAt(0),isacl);
                result.addElement(tempEffGroup);
            }
            else
            {
                EffIfc[] tempEffArray3 = new EffIfc[tempEffArray2.size()];
                for(int k=0; k<tempEffArray2.size(); k++)
                {
                    tempEffArray3[k] = (EffIfc)tempEffArray2.elementAt(k);
                }
                EffGroup tempEffGroup = new EffGroup(tempEffArray3,isacl);
                result.addElement(tempEffGroup);
            }
            //end if and else if(tempEffArray2.size() == 1)
        }
        //end for(int i=0; i<vector.size(); i++)
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getEffVector() end....return is Vector");
        return result;
    }

    /**
     * �����㲿��ֵ�����ȡ���㲿����������Ч��ֵ��
     * @param partIfc : WorkableIfc �㲿��ֵ����
     * @return Vector EffGroup�ļ��ϡ�
     * @throws QMException
     * @see WorkableIfc
     */
    public Vector getEffVector(WorkableIfc partIfc) throws QMException
    {
        return getEffVector(partIfc , true);
    }
    
    /**
     * �����㲿��ֵ�����ȡ���㲿����������Ч��ֵ��
     * @param partIfc��WorkableIfc �㲿��ֵ����
     * @param itemID����Ч��������bsoID��
     * @return Vector EffGroup�ļ��ϡ�
     * @throws QMException
     * @see WorkableIfc
     */
    public Vector getEffVector(WorkableIfc partIfc,String itemID) throws QMException
    {
        return getEffVector(partIfc , itemID,true);
    }

    /**
     * ��ȡ��������
     * @return "EnterprisePartService"
     */
    public String getServiceName()
    {
        return "EnterprisePartService";
    }


    /**
     * ɾ��part��Ӧ����Ч��ֵ��
     * @param partBsoID �����bsoID��
     * @param configItemName ��Ч�Է������ơ�
     * @param effectivityType ��Ч������:"DATE","LOT_NUM","SERIAL_NUM"��
     * @throws QMException
     */
    public void deleteAllEffs(String partBsoID, String configItemName, String effectivityType)throws QMException
  {
    effectivityType =this.invertChineseToEnglish(effectivityType);
    PersistService pService =(PersistService)EJBServiceHelper.getService("PersistService");
    QMPartIfc partIfc =(QMPartIfc)pService.refreshInfo(partBsoID);
    //��ѯEff�µ������б���࣬��ȡ�ͱ�partIfc������������Ч��ֵ����
    QMQuery query = new QMQuery("Eff");
    QueryCondition condition = new QueryCondition("targetBranchID","=",partIfc.getBranchID());
    query.addCondition(condition);
    Collection effVector = pService.findValueInfo(query);
    EffIfc effIfc1 ;
    //�����Ч�Է�������Ϊ��
    if(configItemName==null||configItemName.equals(""))
    {
      if(effectivityType.equals("DATE"))
      {
    	//CCBegin by leixiao 2010-5-4 �򲹶� v4r3_p013_20100415  
       // QMQuery datequery = new QMQuery("QMDatedEffectivity");
      //  QueryCondition datecondition1 = new QueryCondition("targetBranchID","=",partIfc.getBranchID());
      //CR1 begin
          String QMpartBranchID=null;
          boolean isWorkingCopy = CheckInOutTaskLogic.isWorkingCopy(partIfc);
          if(isWorkingCopy)
          {
              WorkableIfc originalCopy=CheckInOutTaskLogic.getOriginalCopy(partIfc);
              QMpartBranchID=originalCopy.getBranchID();
          }else
          {
              QMpartBranchID=partIfc.getBranchID();
          }
        QMQuery datequery = new QMQuery("QMDatedEffectivity");
        QueryCondition datecondition1 = new QueryCondition("targetBranchID","=",QMpartBranchID);//CR1 end
      //CCEnd by leixiao 2010-5-4 �򲹶� v4r3_p013_20100415  
        datequery.addCondition(datecondition1);
        datequery.addAND();
        QueryCondition datecondition2 = new QueryCondition("effContextID",QueryCondition.IS_NULL);
        datequery.addCondition(datecondition2);
        Collection dateeffVector = pService.findValueInfo(datequery);
        for(Iterator iter = dateeffVector.iterator() ; iter.hasNext() ; )
        {
          //����
          effIfc1 =(EffIfc)iter.next();
          //String bsoName =effIfc1.getBsoName();
          //if(bsoName.equals("QMDatedEffectivity"))
          this.editEff(effIfc1,"DELETE");
        }
      }
      else
      {
        //String s="��Ч�����Ͳ�����������";
        throw new PartException(RESOURCE,"13",null);
      }
    }
    //��Ч�Է������Ʋ�Ϊ��
    else
    {
      //Ҫɾ������Ч�Էŵ��ü�����
      //Vector deleteV=new Vector();
      QMQuery query2 =new QMQuery("QMConfigurationItem");
      QueryCondition condition2 =new QueryCondition("configItemName","=",configItemName);
      query2.addCondition(condition2);

      //��ö�Ӧ��Ч�����������Ƶ�������
      Collection itemVector = pService.findValueInfo(query2,false);
      if(itemVector.size()!=1)
      {
        //String s="������Ч������������Ʋ�����Ӧ��������";
        throw new PartException(RESOURCE,"14",null);
        //return;
      }
      AccessControlService access=(AccessControlService)EJBServiceHelper.getService("AccessControlService");
      itemVector = access.filterObjects(itemVector,QMPermission.READ);
      if(itemVector.size()!=1)
      {
        //String s="������Ч������������Ʋ�����Ӧ��������";
        Object[] objs = {configItemName};
        throw new PartException(RESOURCE,"15",objs);
        //return;
      }
      else
      {
        String itemID1="";
        String itemID2="";
        QMConfigurationItemIfc itemIfc=(QMConfigurationItemIfc)itemVector.iterator().next();//elementAt(0);
        //��������������id
        itemID1 =itemIfc.getBsoID();

        for(  Iterator iter = effVector.iterator();iter.hasNext() ;)
        {
          EffIfc effIfc2 =(EffIfc)iter.next();
          //�����Ч���������id
          itemID2 =effIfc2.getEffContextID();
          //�����Ч��bsoName
          String bsoName =effIfc2.getBsoName();
          if(itemID2!= null && itemID1.equals(itemID2))
          {
            //���Ϊ������Ч��
            if(bsoName.equals("QMDatedEffectivity")&&effectivityType.equals("DATE"))
            this.editEff(effIfc2,"DELETE");
            //���Ϊ���к���Ч��
            else if(bsoName.equals("QMSerialNumEffectivity")&&effectivityType.equals("SERIAL_NUM"))
            this.editEff(effIfc2,"DELETE");
            //���Ϊ���к���Ч��
            else if(bsoName.equals("QMLotEffectivity")&&effectivityType.equals("LOT_NUM"))

            this.editEff(effIfc2,"DELETE");
          }
        }
      }
    }
  }
  /**
   *����˽�з�������Ч������ת��ΪӢ�ġ�
   * @param chineseName String
   * @return String
   */
  private String invertChineseToEnglish(String chineseName)
  {
      EffectivityType types1[] = EffectivityType.getEffectivityTypeSet(Locale.CHINA);
      //�жϸ�chineseName�Ƿ���Ӣ�ĵģ�
      for(int i=0; i<types1.length; i++)
      {
        EffectivityType oneType = types1[i];
        String englishString = oneType.toString();
        if(englishString.equals(chineseName))
        {
          return chineseName;
        }
      }
      HashMap hashMap = new HashMap();
      for(int i=0;i<types1.length;i++)
      {
          hashMap.put(types1[i].getDisplay(), types1[i]);
      }
      Object result = hashMap.get(chineseName.trim());
      return result.toString();
   }


    /**
     * ����part��Ӧ����Ч��ֵ��
     * @param partIfc �㲿��ֵ����
     * @param configItemName ��Ч�Է������ơ�
     * @param oldEffectivityType ��Ч�����Ͳ���Ϊ�ձ�����:"DATE","LOT_NUM","SERIAL_NUM"֮һ��
     * @param effValue ��Ч��ֵ��
     * @throws QMException
     */
    public void createEff(QMPartIfc partIfc,String configItemName,String oldEffectivityType,String effValue)
      throws QMException
  {
      EffValidate effvalidate=new EffValidate();
      try
      {
         effValue = effvalidate.effSubString(effValue);
      }
      //��ʾ��Ϣ�����������Ч�Է�Χֵ��Ч��
      catch(PropertyVetoException pve)
      {
          pve.printStackTrace();
          throw new QMException(pve);
      }
      String effectivityType=invertChineseToEnglish(oldEffectivityType);
      Class dateClass=null;
      Class stringClass=null;
      try
      {
           dateClass = Class.forName("com.faw_qm.eff.util.DateEffRange");
           stringClass = Class.forName(
              "com.faw_qm.eff.util.StringEffRange");
      }
      catch(ClassNotFoundException e)
      {
         e.printStackTrace();
         return;
      }
      EffService effService =(EffService)EJBServiceHelper.getService("EffService");
      EffectivityService effectivityService =(EffectivityService)EJBServiceHelper.getService("EffectivityService");
      EffRange[] effRange =null;
      //�����Ч������Ϊ��
      if((effectivityType==null)||effectivityType.equals(""))
      {
          throw new PartException(RESOURCE,"13",null);
      }
      //�����Ч������������Ϊ�գ�effectivityType�������������ͣ�
      if(configItemName==null||configItemName.equals(""))
      {
        try
       {
         effvalidate.effValueValidate(1,effValue,null);
       }
       catch(PropertyVetoException pve)
       {
           pve.printStackTrace();
           throw new QMException(pve);
       }
        effRange=effService.rangeToEffRanges(dateClass,effValue);
        if(effRange.length>0)
        {
             for(int i=0;i<effRange.length;i++)
             {
                   QMDatedEffectivityIfc dateEffIfc = new
                       QMDatedEffectivityInfo();
                   dateEffIfc.setRange(effRange[i]);
                   effectivityService.setEffectivityTarget(dateEffIfc,
                       partIfc);
                   dateEffIfc.setEffContextID(null);
                   this.editEff(dateEffIfc, "CREATE");
              }
         }
       }
      //�����Ч�����������Ʋ�Ϊ��
       else
       {
           PersistService pService =(PersistService)EJBServiceHelper.getService("PersistService");
           QMQuery query2 =new QMQuery("QMConfigurationItem");
           QueryCondition condition2 =new QueryCondition("configItemName","=",configItemName);
           query2.addCondition(condition2);
           //��ö�Ӧ��Ч�����������Ƶ�������
           Collection itemVector = pService.findValueInfo(query2,false);
           if(itemVector.size()!=1)
           {
               //String s="������Ч������������Ʋ�����Ӧ��������";
               throw new PartException(RESOURCE,"14",null);
               //return;
           }
           AccessControlService access=(AccessControlService)EJBServiceHelper.getService("AccessControlService");
           itemVector = access.filterObjects(itemVector,QMPermission.READ);
           if(itemVector.size()!=1)
           {
                //String s="������Ч������������Ʋ�����Ӧ��������";
                Object[] objs = {configItemName};
                throw new PartException(RESOURCE,"15",objs);
                //return;
           }
           else
           {
              QMConfigurationItemIfc configIfc=(QMConfigurationItemIfc)itemVector.iterator().next();//elementAt(0);
              String configID =configIfc.getBsoID();
               if (effectivityType.equals("DATE"))
               {
                   try
                  {
                    effvalidate.effValueValidate(1,effValue,configIfc);
                  }
                  catch(PropertyVetoException pve)
                  {
                      pve.printStackTrace();
                      throw new QMException(pve);
                  }
                  effRange = effService.rangeToEffRanges(dateClass, effValue);
               }
               else if (effectivityType.equals("LOT_NUM"))
               {
                  try
                  {
                    effvalidate.effValueValidate(2,effValue,configIfc);
                  }
                  catch (PropertyVetoException pve)
                  {
                      pve.printStackTrace();
                      throw new QMException(pve);
                  }
                   effRange = effService.rangeToEffRanges(stringClass, effValue);
               }
               else if(effectivityType.equals("SERIAL_NUM"))
               {
                   try
                  {
                    effvalidate.effValueValidate(3,effValue,configIfc);
                  }
                  catch(PropertyVetoException pve)
                  {
                      pve.printStackTrace();
                    throw new QMException(pve);
                  }
                 effRange = effService.rangeToEffRanges(stringClass, effValue);
               }
               if (effRange.length > 0)
               {
                   for (int i = 0; i < effRange.length; i++) {
                       if (effectivityType.equals("DATE")) {
                           QMDatedEffectivityIfc dateEffIfc = new
                               QMDatedEffectivityInfo();
                           dateEffIfc.setRange(effRange[i]);
                           effectivityService.setEffectivityTarget(dateEffIfc,
                               partIfc);
                           dateEffIfc.setEffContextID(configID);
                           this.editEff(dateEffIfc, "CREATE");
                       }
                       else if (effectivityType.equals("LOT_NUM")) {
                           QMLotEffectivityIfc lotEffIfc = new
                               QMLotEffectivityInfo();
                           lotEffIfc.setRange(effRange[i]);
                           effectivityService.setEffectivityTarget(lotEffIfc,
                               partIfc);
                           lotEffIfc.setEffContextID(configID);
                           this.editEff(lotEffIfc, "CREATE");
                       }
                       else if (effectivityType.equals("SERIAL_NUM")) {
                           QMSerialNumEffectivityIfc serialEffIfc = new
                               QMSerialNumEffectivityInfo();
                           serialEffIfc.setRange(effRange[i]);
                           effectivityService.setEffectivityTarget(
                               serialEffIfc, partIfc);
                            serialEffIfc.setEffContextID(configID);
                           this.editEff(serialEffIfc, "CREATE");
                       }
                   }
               }
           }
       }
  }


    /**
     * ���� part��Ӧ����Ч��ֵ��
     * @param partIfc QMPartIfc �㲿����ֵ����
     * @param configItemName String ��Ч�Է������ơ�
     * @param oldEffectivityType String
     *   ��Ч�����Ͳ���Ϊ�ձ�����:"DATE","LOT_NUM","SERIAL_NUM"֮һ��
     * @param newEffValue String �µ���Ч��ֵ��Χ��
     * @throws QMException
     */
    public void updateEff(QMPartIfc partIfc,String configItemName,String oldEffectivityType,String newEffValue)
   throws QMException
  {
    EffValidate effvalidate=new EffValidate();
    try
    {
       newEffValue = effvalidate.effSubString(newEffValue);
    }
    //��ʾ��Ϣ�����������Ч�Է�Χֵ��Ч��
    catch(PropertyVetoException pve)
    {
        pve.printStackTrace();
      throw new QMException(pve);
    }
    if(configItemName==null||configItemName.equals(""))
    {
      EffValidate aValidate = new EffValidate ( ) ;
      try
      {
        aValidate.effValueValidate (1, newEffValue ,null) ;
      }
      catch(PropertyVetoException pve)
      {
          pve.printStackTrace();
        throw new QMException(pve);
      }
    }
    //��Ч�Է������Ʋ�Ϊ��
    else
    {
      oldEffectivityType =this.invertChineseToEnglish(oldEffectivityType);
      //Ҫɾ������Ч�Էŵ��ü�����
      //Vector deleteV=new Vector();
      QMQuery query2 =new QMQuery("QMConfigurationItem");
      QueryCondition condition2 =new QueryCondition("configItemName","=",configItemName);
      query2.addCondition(condition2);
      PersistService pService =(PersistService)EJBServiceHelper.getService("PersistService");
      //��ö�Ӧ��Ч�����������Ƶ�������
      Collection itemVector = pService.findValueInfo(query2,false);
      if(itemVector.size()!=1)
      {
        //String s="������Ч������������Ʋ�����Ӧ��������";
        throw new PartException(RESOURCE,"14",null);
        //return;
      }
      AccessControlService access=(AccessControlService)EJBServiceHelper.getService("AccessControlService");
      itemVector = access.filterObjects(itemVector,QMPermission.READ);
      if(itemVector.size()!=1)
      {
        //String s="������Ч������������Ʋ�����Ӧ��������";
        Object[] objs = {configItemName};
        throw new PartException(RESOURCE,"15",objs);
        //return;
      }
      else
      {
        QMConfigurationItemIfc itemIfc=(QMConfigurationItemIfc)itemVector.iterator().next();//elementAt(0);
        EffValidate aValidate = new EffValidate (  ) ;
        try
        {
          if(oldEffectivityType.equals("DATE"))
          aValidate.effValueValidate (1, newEffValue ,itemIfc) ;
          if(oldEffectivityType.equals("LOTNUM"))
          aValidate.effValueValidate (2, newEffValue ,itemIfc) ;
          if(oldEffectivityType.equals("SERIALNUM"))
          aValidate.effValueValidate (3, newEffValue ,itemIfc) ;
        }
        catch(PropertyVetoException pve)
        {
            pve.printStackTrace();
          throw new QMException(pve);
        }
      }
    }

    String partBsoID=partIfc.getBsoID();
    this.deleteAllEffs(partBsoID,configItemName,oldEffectivityType);
    this.createEff( partIfc,configItemName,oldEffectivityType,newEffValue);
  }


    /**
     * ����������������Ч�Խ��д��������¡�ɾ����
     *
     * @param obj ��Ч�Զ���
     * @param s ���������¡�ɾ����־��
     * @throws QMException
     */
    private void editEff(Object obj,String s)throws QMException
  {
    TransactionContainer transactioncontainer = BatchContainerFactory.instantiateTransactionContainer();
    BatchContainer batchcontainer = BatchContainerFactory.instantiateGeneralBatchContainer(transactioncontainer, "main");
    if(s.equals("CREATE"))
    {
      BaseValueIfc obj1 = (BaseValueIfc)obj;
      if(!PersistHelper.isPersistent(obj1))
      batchcontainer.add(obj1);
    }
    else if(s.equals("UPDATE"))
    {
      batchcontainer.replace(obj);
    }
    else if(s.equals("DELETE"))
    {
      Vector vector =new Vector();
      vector.addElement(obj);
      batchcontainer.populate(vector.elements());
      batchcontainer.remove(obj);
    }
    EffectivityService effectivityService =(EffectivityService)EJBServiceHelper.getService("EffectivityService");
    TransactionResult transactionresult =(TransactionResult)effectivityService.persistEffectivityBatch(transactioncontainer);
    if(transactionresult != null)
    {
       QMException exception = transactionresult.getException();
       if(exception != null)
       {
         throw new PartException(exception);
       }
    }
  }


  /**
   * �ڵ��÷���difference(),���нṹ�Ƚ�֮ǰ���Խṹ���е������ϲ���ͬ���������
   * @param result Collection//result �У���Ķ�������
   * @return Collection
   */
  private Collection hebing(Collection result,String parSt)
  {
    Vector hebing = new Vector();
    Iterator iterotor = result.iterator();
    PartUsageLinkIfc puli = new PartUsageLinkInfo();
    Object[] robj ;
    float rquantity;
    String rid;
    String leftBsoID;
    boolean flag;
    while (iterotor.hasNext())
    {
      robj = (Object[]) iterotor.next();
      puli = (PartUsageLinkIfc) robj[0];
      rquantity = puli.getQuantity();
      rid = puli.getRightBsoID();
      leftBsoID = puli.getLeftBsoID();
      //��ʶ�ڼ���hebing���Ƿ���ڵ�ǰ��ѭ��������㲿��,��ʼ�����,��Ϊ������:
      flag = false;
      //�����еĺϲ���ϵļ��Ͻ���ѭ��:
      PartDebug.trace(PartDebug.PART_SERVICE, "for֮ǰ��rid==" + rid + "   leftBsoID==" + leftBsoID);
      for (int j = 0; j < hebing.size(); j++)
      {
        Object[] obj = (Object[]) hebing.elementAt(j);
        PartUsageLinkIfc puli1 = (PartUsageLinkIfc) obj[0];
        float quantity = puli1.getQuantity();
        String id = puli1.getRightBsoID();
        String oldLeftBsoID = puli1.getLeftBsoID();
        //���ʹ�õ���ͬһ���㲿���Ļ�,�ϲ�����,
        if (leftBsoID.equals(oldLeftBsoID))
        {
          puli1.setQuantity(rquantity + quantity);
          //�ҵ�������㲿��:
          flag = true;
          obj[0] = puli1;
          QMPartIfc part=null;
          Object[] obj1=new Object[2];
          obj1[0]=puli1;
          if(obj[1] instanceof QMPartIfc)
          {
            part=(QMPartIfc)obj[1];
            part.setCompare("a#"+parSt+"a");
            obj1[1]=part;
          }
          else
            obj1[1]=obj[1];
          hebing.setElementAt(obj1, j);
          break;
        }
      }
      if (!flag)
      {
        Object[] obj=new Object[2];
        obj[0]=robj[0];
        QMPartIfc part=null;
        if(robj[1] instanceof QMPartIfc)
        {
          part=(QMPartIfc)robj[1];
           part.setCompare("a#"+parSt+"a");

          obj[1]=part;
        }
        else
        {
            obj[1]=robj[1];
        }
        hebing.addElement(robj);
      }
    }
    return hebing;
  }//end add
  private boolean isHasSubPart(QMPartIfc partIfc)

  {
    boolean flag=false;
    try
    {
      Collection coll = getUsesPartIfcs(partIfc);
      if (coll == null || coll.isEmpty()) {
       flag= false;
      }
      else
        flag= true;

    }catch(QMException e)
    {
      e.printStackTrace();
    }
     return flag;
  }
  private Collection getUsesPartIfcs(QMPartIfc partIfc)
        throws QMException
    {
     Collection links = null;
      PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
       if(partIfc.getBsoName().equals("GenericPart"))
         links = pservice.navigateValueInfo(partIfc, "usedBy", "GenericPartUsageLink", false);
       else if(partIfc.getBsoName().equals("QMPart"))
         links = pservice.navigateValueInfo(partIfc, "usedBy", "PartUsageLink", false);
         return links;

    }
  
  //CCBegin SS2
  
  /**
   * ��ӡ���Ľṹ���ܣ����ؼ���û���ظ��ļ�
   * ����ڵ�ǰɸѡ������partMasterIfc�Ľṹ�������Ӽ���
   * @param partMasterIfc �㲿������Ϣֵ����
   * @param configSpecIfc ���ù淶��
   * @return Vector �����Ӳ���ֵ����ļ��ϡ�
   * @exception QMException
   */
  public Vector getAllSubPartsByConfigSpecForQQ(QMPartMasterIfc partMasterIfc,
      PartConfigSpecIfc configSpecIfc) throws QMException
  {
      PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllSubPartsByConfigSpec(QMPartMasterIfc,"+
              " PartConfigSpecIfc) begin ....");
      //��һ��������QMPartMasterIfc��ȡ�������ù淶�ģ����°汾��QMPartIfc,
      //�ڶ��������÷���getAllSubPartsByConfigSpec(QMPartIfc, PartConfigSpecIfc)
      //��������������Ľ���������ŵ�QMPartIfc[]��ȥ��
      ConfigService service = (ConfigService)EJBServiceHelper.getService("ConfigService");
      Collection collection = service.filteredIterationsOf(partMasterIfc,
          new PartConfigSpecAssistant(configSpecIfc));

      if((collection == null)||(collection.size()>1)||(collection.size() == 0))
      {
          throw new PartException(RESOURCE, "7", null);
      }
      Iterator iterator = collection.iterator();
      //��������ת����iterator.next()������Object[]
      Object obj = iterator.next();
      QMPartIfc partIfc = null;
      if(obj instanceof Object[])
      {
          Object[] obj1 = (Object[])obj;
          partIfc = (QMPartIfc)obj1[0];
      }
      else
      {
          partIfc = (QMPartIfc)obj;
      }
      Vector tempVector = new Vector();

      tempVector = getAllSubPartsByConfigSpec(partIfc, configSpecIfc);
      //QMPartIfc[] partIfc2 = new QMPartIfc[tempVector.size()];
      Vector returnvector = new Vector();
      for(int i=0; i<tempVector.size(); i++)
      {
          if(!returnvector.contains(tempVector.elementAt(i)))
          returnvector.add((QMPartIfc)tempVector.elementAt(i));
      }
      PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllSubPartsByConfigSpec(QMPartMasterIfc, "+
              "PartConfigSpecIfc) end....return is QMPartIfc[]");
      return returnvector;
  }
  
  //CCEnd SS2

}
