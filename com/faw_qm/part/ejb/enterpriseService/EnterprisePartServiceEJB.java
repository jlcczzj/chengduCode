/** 生成程序 EnterprisePartServiceEJB.java    1.0    2003/02/28
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
  * CR1 2010/02/08 马辉   原因:TD2847为一个检出的零部件执行移除有效性操作的问题
  * SS1 A004-2015-3130:结构比较继续比较后生成最终报表出错，数组越界。 liunan 2015-6-4
  * SS2 成都结构搜索工艺规程 guoxiaoliang 
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
 * 零部件企业级服务EJB实现类。
 * 在扩展服务的基础上，增加有效性管理等内容，
 * 提供产品结构比较和按有效性生成物料清单等功能。
 * @author 吴先超
 * @version 1.0
 */
public class EnterprisePartServiceEJB extends BaseServiceImp
{
    /**序列化ID*/
    static final long serialVersionUID = 1L;

    public static final int DATE = 1 ;
    public static final int LOTNUM = 2 ;
    public static final int SERIALNUM = 3 ;
    private static String RESOURCE = "com.faw_qm.part.util.PartResource";

    /**
     * 构造函数
     */
    public EnterprisePartServiceEJB()
    {
        super();
    }
    /**
     * 根据指定的源部件和目标部件及各自的筛选条件，返回两个部件使用关系的不同。
     * 本方法调用了递归方法：difference(QMPartIfc sourcePartIfc, PartConfigSpecIfc sourceConfigSpecIfc,
     * QMPartIfc objectPartIfc, PartConfigSpecIfc objectConfigSpecIfc)。
     * @param sourcePartIfc :QMPartIfc源部件的值对象。
     * @param objectPartIfc :QMPartIfc目标部件的值对象。
     * @param sourceConfigSpecIfc :PartConfigSpecIfc源部件的筛选条件。
     * @param objectConfigSpecIfc :PartConfigSpecIfc目标部件的筛选条件有效性方案。
     * @param map 继续比较的源零部件和目标零部件的集合
     * @return vector:Vector元素的数据结构String[]：<br>
     * String[0]：当前差异部件相对于根部件的层次，这里，根部件为0层，根部件的子部件为1层，以此类推；<br>
     * String[1]：当前差异部件的名称+"("+编号+")"；<br>
     * String[2]：当前差异部件在源部件中的版本+（视图），如果源部件中没有该部件的话，显示""，
     *    如果源部件中有该差异部件但是没有符合配置规范的版本的话，
     *    显示"没有符合配置规范的版本"；<br>
     * String[3]：当前差异部件被其上一级零部件所使用的数量+(单位)，如果源部件中没有该部件的话，显示""
     *    如果源部件中有该差异部件但是没有符合配置规范的版本的话，照常显示数量+（单位）；<br>
     * String[4]：当前差异部件在目标部件中的版本+（视图），如果目标部件中没有该部件的话，显示""，
     *    如果目标部件中有该差异部件但是没有符合配置规范的版本的话，
     *    显示"没有符合配置规范的版本"；<br>
     * String[5]：当前差异部件被其上一级零部件所使用的数量+(单位)，如果目标部件中没有该部件的话，显示""
     *    如果目标部件中有该差异部件但是没有符合配置规范的版本的话，照常显示数量+（单位）；<br>
     * String[6]：显示的颜色的标识::如果差异部件在源部件和目标部件中都存在，但数量或者版本不同的话，显示为黑色(black)；
     *    如果差异部件在源部件中存在，但是在目标部件中不存在的话，显示为绿色(green)；
     *    如果差异部件在目标部件中存在，但是在源部件中不存在的话，显示为紫色(purple)；
     *    如果差异部件在源部件和在目标部件中的版本和数量都相同的话，显示为灰色(gray)；
     *    信息"没有符合配置规范的版本"的颜色为红色(red),含有该信息的行的颜色为黑色(black)。<br>
     * String[7]:差异零部件的BsoID，是针对源零部件而言的，即是源零部件使用的该差异零部件的BsoID；<br>
     * String[8]:差异零部件的BsoID, 是针对目标零部件而言的，即是目标零部件使用的的差异零部件的BsoID；<br>
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
        
        //测试代码。
        getBOMDifferencesBeginDebug(sourcePartIfc, objectPartIfc, vector);
        //还是需要对vector中的完全相同的元素进行合并:::
        Vector resultVector = new Vector();
        for(int i=0; i<vector.size(); i++)
        {
        	
            boolean flag = false;
            String[] obj1 = (String[])vector.elementAt(i);
            for(int j=0; j<resultVector.size(); j++)
            { 
            	
                String[] obj2 = (String[])resultVector.elementAt(j);
                //需要对obj1, obj2的所有元素进行比较，如果都相同...否则，加到结果集合中::
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
                	
                    //在这里还需要添加判断：需要对当前的元素进行回溯其父亲节点，如果，父亲节点
                    //的partNumber相同，再向上回溯，直到找到的节点的level为1,如果父亲节点都
                    //相同，就合并，否则，不合并：

                    //这样进行比较：如果level都是1话，就直接flag = true;
                    if(obj1[0].equals("1"))
                    {
                    
                        flag = true;
                        break;
                    }
                    else
                    {
                        //如果不是level = 1的节点的话：
                        //obj1去vector.elementAt(i-1)找自己的上一个节点,obj2去找
                        //resultVector.elementAt(j-1)中的上一个元素：如果
                        //上一个元素也相同，而且level > 1，就继续，直到发现不相同的，或者
                        //level =1 ，如果发现不相同的，不可以合并，如果到了level = 1 也是
                        //相同的话，就可以合并了。
                    
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
                                  
                                    //需要继续循环：
                                }
                            }
                            else
                            {
                                //不可以合并：
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
        //测试代码。
       
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
        PartDebug.trace(PartDebug.PART_SERVICE, "进入方法getBOMDifference后的参数分别是:" +
                        sourcePartIfc.getBsoID() + "   " +
                        objectPartIfc.getBsoID());
        PartDebug.trace(PartDebug.PART_SERVICE,
                "---------------------------------------------------------------------------");

        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "getBOMDifferences() end....return is Vector");
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "getBOMDifference()方法的中间的返回值是::");
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
                        "getBOMDifference()方法的最后的返回值是::");
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
     * 私有的方法，被getBOMDifferences()方法所调用，实现了递归的功能。
     * 该方法的业务逻辑为：获取当前源部件和目标部件的符合各自配置规范的子零部件的集合，这个集合里面可能
     * 有QMPartIfc, 也可能有QMPartMasterIfc；对这两个集合进行比较，
     *   如果一个差异部件只在源部件，或者只在目标部件中存在，加到最后结果集合中，不再继续比较；
     *   如果一个差异部件在源部件和在目标部件中都存在，但是使用的数量或者版本不相同的话，继续比较，
     * 并把该差异部件的差异情况列出来（黑色）；
     *   如果一个差异部件在源部件和目标部件中都存在，而且使用的数量和版本都相同的话，继续比较，
     * 并把该差异部件列出来（灰色）。
     * 需要改变返回值Vector中元素的数据结构String[]：
     * 0：当前差异部件相对于根部件的层次，这里，根部件为0层，根部件的子部件为1层，以此类推；
     * 1：当前差异部件的名称+"-"+编号，最好在partName + partNumber之前，加上和层次号
     *    相对应的"..."，这些点的数量为(level-1)*3；
     *
     * 2：当前差异部件在源部件中的版本+（视图），如果源部件中没有该部件的话，显示""，
     *    如果源部件中有该差异部件但是没有符合配置规范的版本的话，
     *    显示"没有符合配置规范的版本"；
     * 3：当前差异部件被其上一级零部件所使用的数量+(单位)，如果源部件中没有该部件的话，显示""
     *    如果源部件中有该差异部件但是没有符合配置规范的版本的话，照常显示数量+（单位）；
     *
     * 4：当前差异部件在目标部件中的版本+（视图），如果目标部件中没有该部件的话，显示""，
     *    如果目标部件中有该差异部件但是没有符合配置规范的版本的话，
     *    显示"没有符合配置规范的版本"；
     * 5：当前差异部件被其上一级零部件所使用的数量+(单位)，如果目标部件中没有该部件的话，显示""
     *    如果目标部件中有该差异部件但是没有符合配置规范的版本的话，照常显示数量+（单位）；
     *
     * 6：显示的颜色的标识::如果差异部件在源部件和目标部件中都存在，但数量或者版本不同的话，显示为黑色(black)；
     *    如果差异部件在源部件中存在，但是在目标部件中不存在的话，显示为绿色(green)；
     *    如果差异部件在目标部件中存在，但是在源部件中不存在的话，显示为紫色(purple)；
     *    如果差异部件在源部件和在目标部件中的版本和数量都相同的话，显示为灰色(gray)；
     *    信息"没有符合配置规范的版本"的颜色为红色(red),含有该信息的行的颜色为黑色(black)。
     *
     * 7:差异零部件的BsoID，是针对源零部件而言的，即是源零部件使用的该差异零部件的BsoID；
     *
     * 8:差异零部件的BsoID, 是针对目标零部件而言的，即是目标零部件使用的的差异零部件的BsoID；
     *
     * @param sourcePartIfc :QMPartIfc源部件的值对象。
     * @param objectPartIfc :QMPartIfc目标部件的值对象。
     * @param sourceConfigSpecIfc :PartConfigSpecIfc源部件的筛选条件。
     * @param objectConfigSpecIfc :PartConfigSpecIfc目标部件的筛选条件。
     * @param pathVector 根结点部件到当前差异部件的路径信息，pathVector的数据结构和该方法的返回值相同。
     * @return vector :Vector
     * @throws QMException
     */
    private Vector difference(QMPartIfc sourcePartIfc, PartConfigSpecIfc sourceConfigSpecIfc,
        QMPartIfc objectPartIfc, PartConfigSpecIfc objectConfigSpecIfc, Vector pathVector,HashMap map) throws QMException
    {
    	

        //测试代码。
        differenceBeginDebug(sourcePartIfc, objectPartIfc, pathVector);
        //保存最终的返回结果。
        Vector resultVector = new Vector();
        //调用了StandardServiceEJB中的方法:getUsesPartIfc;
        Collection he11=null;
        Collection he22=null;
        Collection temp11 = new Vector();
        Collection temp22 = new Vector();
        Vector aa=(Vector)map.get("dangqian");
        Vector bb=(Vector)map.get("mubiao");

        try
        {
            //这里获取源零部件的所有下一级子部件:

            he11 = (Vector)PartServiceRequest.getUsesPartIfcs(sourcePartIfc, sourceConfigSpecIfc);

            //对子部件集合合并处理:
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
                //测试代码。需要输出所有的temp1中的所有的元素::
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
                //测试代码。
                differenceDebug2(objectPartIfc, temp2);
            }
            //end if(temp2 != null && temp2.size() > 0)

        }
        //end if(temp22 != null && temp22.size() > 0)
        //上面的代码不需要改变。!!!!!!!!!!!!!!!!!!!!!!!

        //下面需要根据返回值temp1, temp2的值的情况，来确定进行何种的处理:::
        //1：如果temp1为空，而且temp2也是空，什么也不做，直接返回
        //2：如果temp1为空，但是temp2不是空，就直接返回temp2中的所有的元素，不进行其他的任何的处理
        //3：如果temp1不为空，temp2为空，就直接返回temp2中的所有元素，不进行其他的任何的处理
        //4：如果temp1和temp2都不为空的情况下，再进行复杂的业务处理。
        //////////////////////////////////////////////////////////////////////////////////////////////
        //现在处理1:
        if(flag1 == false && flag2 == false)
        {
            //测试代码。
            differenceDebug3(resultVector);
            return resultVector;
        }
        //////////////////////////////////////////////////////////////////////////////////////////////
        //现在处理2：如果temp1为空，但是temp2不是空，就直接返回temp2中的所有的元素，不进行其他的任何的处理。
        //需要根据pathVector来确定对resultVector的添加的情况，如果pathVector的size()为>0，把pathVector中的
        //内容直接加到resultVector中，如果size()为0，就把temp2中的所有元素进行组织，并放到resultVector中。

        if((flag1 == false)&&(flag2 == true))
        {

            return process2(pathVector, resultVector, temp2);
        }
        //end if((flag1 == false)&&(flag2 == true))
        //////////////////////////////////////////////////////////////////////////////////////////////
        //现在处理3：如果temp1不为空，temp2为空，就直接返回temp2中的所有元素，不进行其他的任何的处理
        if((flag1 == true)&&(flag2 == false))
        {

            return process3(pathVector, resultVector, temp1);
        }
        //end if((flag1 == true)&&(flag2 == false))
        //上面的代码修改完毕。!!!!!!!!!!!!!!!!!!!!
        //////////////////////////////////////////////////////////////////////////////////////////////
        //当前三种情况都不满足的时候，就是temp1 != null 而且temp2 != null的时候，
        //就自动进入下面的代码：：：：
       
        return process4(sourceConfigSpecIfc, objectConfigSpecIfc, pathVector, resultVector, temp1, temp2,map);
    }

    private Vector process2(Vector pathVector, Vector resultVector,
                            Vector temp2)
    {
        float quantity2 = 0.0f;
        String unit2 = "", version2 = "", viewName2 = "";
        String partNumber2 = "", partName2 = "", partBsoID2 = "";
        //level是temp2中元素所在的层次号:
        int level = pathVector.size() + 1;
        //需要先把pathVector中的所有的元素都加到resultVector中去:
        for(int i=0; i<pathVector.size(); i++)
        {
            String[] stringArray = (String[])pathVector.elementAt(i);
            resultVector.addElement(stringArray);
        }
        //end for(int i=0; i<pathVector.size(); i++)
        //把temp2中的所有元素都加到resultVector中.
        for(int i=0; i<temp2.size(); i++)
        {
            if(temp2.elementAt(i) instanceof Object[])
            {
                Object[] tempObj = (Object[])temp2.elementAt(i);
                //需要根据tempObj[1]来判断是QMPartIfc, 或者是QMPartMasterIfc
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
                    //最好可以标明层次结构：
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
                    //紫色
                    tempArray12[6] = "purple";
                    //增加最后一项:BsoID
                    tempArray12[7] = "";
                    tempArray12[8] = partBsoID2;
                    tempArray12[9] = "false";
                    tempArray12[10] = Boolean.toString(this.isHasSubPart(partIfc));
                    tempArray12[11]=partNumber2;
                        tempArray12[12]=partName2;
                        tempArray12[13]=tempQuantity;
                        tempArray12[14]=version2;
                        tempArray12[15] = "取消";
                        tempArray12[16] = "";
                        //CCBegin by leixiao 2010-2-24增加生命周期状态的显示
                        tempArray12[17] = partIfc.getLifeCycleState().getDisplay();
                        //CCEnd by leixiao 2010-2-24增加生命周期状态的显示
                        resultVector.addElement(tempArray12);
                }
                //不列出来只有QMPartMasterIfc的元素。
                else if (tempObj[1] instanceof QMPartMasterIfc)
                {
                    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc) tempObj[1];
                    partName2 = partMasterIfc.getPartName();
                    partNumber2 = partMasterIfc.getPartNumber();
                    version2 = ""; //"没有找到合适的版本"
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
                    //红色
                    tempArray12[6] = "red";
                    //增加最后一项:BsoID
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
        //测试代码。
        differenceDebug4(resultVector);
        return resultVector;
    }

    private Vector process3(Vector pathVector, Vector resultVector,
                            Vector temp1)
    {
        float quantity1 = 0.0f;
        String unit1 = "", version1 = "", viewName1 = "";
        String partNumber1 = "", partName1 = "", partBsoID1 = "";
        //level是temp1中元素所在的层次号:
        int level = pathVector.size() + 1;
        //需要先把pathVector中的所有的元素都加到resultVector中去:
        for(int i=0; i<pathVector.size(); i++)
        {
            String[] stringArray = (String[])pathVector.elementAt(i);
            resultVector.addElement(stringArray);
        }
        //end for(int i=0; i<pathVector.size(); i++)
        //把temp1中的所有元素都加到resultVector中.
        for(int i=0; i<temp1.size(); i++)
        {
            if(temp1.elementAt(i) instanceof Object[])
            {
                Object[] tempObj = (Object[])temp1.elementAt(i);
                //需要根据tempObj[1]来判断是QMPartIfc, 或者是QMPartMasterIfc
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
                    //最好可以标明层次结构：
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
                    //绿色
                    tempArray12[6] = "green";
                    //增加最后一项：BsoID:
                    tempArray12[7] = partBsoID1;
                    tempArray12[8] = "";
                    tempArray12[9] = Boolean.toString(this.isHasSubPart(partIfc));
                    tempArray12[10] = "false";
                    tempArray12[11]=partNumber1;
                      tempArray12[12]=partName1;
                      tempArray12[13]=tempQuantity;
                      tempArray12[14]=version1;
                      tempArray12[15] = "新增";
                      tempArray12[16] = "";
                      //CCBegin by leixiao 2010-2-24增加生命周期状态的显示
                      tempArray12[17] = partIfc.getLifeCycleState().getDisplay();
                      //CCEnd by leixiao 2010-2-24增加生命周期状态的显示
                    resultVector.addElement(tempArray12);


                }
                //不列出来只有QMPartMasterIfc的元素。
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
                    //最好可以标明层次结构：
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
                    //红色
                    tempArray12[6] = "red";
                    //增加最后一项：BsoID:
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
        //测试代码。
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
        //该集合用来保存在temp1中有元素，但是该元素不在temp2中的那些元素:
        Vector temp1Rest = new Vector();
        for (int i=0; i<temp1.size(); i++)
        {

          QMPartIfc soupartIfc=null;
            QMPartIfc objpartIfc=null;
            //这两个参数用来标识子节点是QMPartIfc, 或者是QMPartMasterIfc,
            //当masterFlag1 = false的时候，表示源部件的当前子节点部件是QMPartIfc,
            //否则，表示源部件的当前子节点部件是QMPartMasterIfc.
            //对masterFlag2 同理。
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
                    //需要一个标识flag = "red"， masterFlag1 = true;
                    masterFlag1 = true;
                    //CCBegin SS1
                    lstate = "";
                    //CCEnd SS1
                }
                //end if and else (tempArray7[1] instanceof QMPartIfc)
                quantity1 = ((PartUsageLinkIfc)tempArray7[0]).getQuantity();
                unit1 = (((PartUsageLinkIfc)tempArray7[0]).getDefaultUnit()).getDisplay();
                //对目标部件的所有子部件进行循环:
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
                            //需要一个标识flag = "red", masterFlag2 = true;
                            masterFlag2 = true;
                        }
                        //end if and else (tempArray8[1] instanceof QMPartIfc)
                        quantity2 = ((PartUsageLinkIfc)tempArray8[0]).getQuantity();
                        unit2 = (((PartUsageLinkIfc)tempArray8[0]).getDefaultUnit()).getDisplay();
                        //如果两个零部件对应同一个QMPartMaster：
                        boolean flaga=(compare1.trim().length()!=0&&compare2.trim().length()!=0)&&(compare1.equals(compare2)&&!compare1.endsWith("a"));

                        if (partNumber1.equals(partNumber2) && partName1.equals(partName2))
                        {

                            flag = true;
                            temp2.setElementAt("null", j);
                            //如果被父节点使用的数量是相同的话，而且masterFlag1 masterFlag2中都是false:
                            //这里有点问题：如果三个条件都满足，但是版本不相同。
                            //也是差异部件：需要直接放到结果集合中，而不需要再继续递归了！
                            //下面的这种情况是：两个零部件被父类使用的数量相同，而且两个零部件都有
                            //符合各自配置规范的版本：

                            if ((quantity1 == quantity2 && masterFlag1 == false && masterFlag2 == false))
                            {
                                //即使版本不同的话，也需要继续进行比较：
                                //if(version1.equals(version2) || !version1.equals(version2))
                                {
                                    //需要把当前的sourcePart, objectPart加到pathVector中，
                                    String[] tempArray3 = new String[17];
                                    tempArray3[0] = (new Integer(level)).toString();
                                    //最好可以标明层次结构：
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
                                    //灰色
                                    //如果两个零部件的版本相同的话，就是"gray"，如果版本不同的话，就是"black"
                                    if(version1.equals(version2))
                                        tempArray3[6] = "gray";
                                    else
                                        tempArray3[6] = "black";
                                    //增加一项：partBsoID:
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
                                    //组装节点路径:::
                                    pathVector.addElement(tempArray3);
                                    //在这里需要根据tempArray3的颜色来对结果集合进行添加：
                                    if(!tempArray3[6].equals("gray"))
                                    {
                                        //需要把pathVector中的所有元素加到结果集合中：
                                        //2003.09.04新增加：如果pathVector中的颜色不是gray的话，就加到最后的结果集合中：
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
                                    //需要把tempResult中的所有元素加到resultVector中，最后返回。

                                    for (int k=0; k<tempResult.size(); k++)
                                    {
                                        String[] tempSs = (String[])tempResult.elementAt(k);
                                        resultVector.addElement(tempSs);
                                    }

                                }
                            }
                            else
                            {

                                //对以下几种情况进行处理:
                                //masterFlag1 == false && masterFlag2 == true
                                //masterFlag1 == true && masterFlag2 == false
                                //masterFlag1 == false && masterFlag2 == false 而且quantity1 != quantity2
                                if(masterFlag1 == false && masterFlag2 == true)
                                {

                                    //需要先把pathVector中的所有的元素都放到String[]中去:
                                    for(int mm  = 0; mm<pathVector.size(); mm++)
                                    {
                                        String[] stringArray00 = (String[])pathVector.elementAt(mm);
                                        resultVector.addElement(stringArray00);
                                    }
                                    String[] tempArray3 = new String[15];
                                    tempArray3[0] = (new Integer(level)).toString();
                                    //最好可以标明层次结构：
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
                                    //"没有找到合适版本"
                                    tempArray3[4] = "";
                                    String tempQuantity2 = String.valueOf(quantity2);
                                    if (tempQuantity2.endsWith(".0"))
                                        tempQuantity2 = tempQuantity2.substring(0, tempQuantity2.length() - 2);
                                    tempArray3[5] = tempQuantity2 + "(" + unit2 + ")";
                                    //红色
                                    tempArray3[6] = "red";
                                    //增加最后一项：BsoID
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

                                    //需要先把pathVector中的所有的元素都放到String[]中去:
                                    for(int mm  = 0; mm<pathVector.size(); mm++)
                                    {
                                        String[] stringArray00 = (String[])pathVector.elementAt(mm);
                                        resultVector.addElement(stringArray00);
                                    }
                                    String[] tempArray3 = new String[15];
                                    tempArray3[0] = (new Integer(level)).toString();
                                    //最好可以标明层次结构：
                                    String dian = "";
                                    int tempLevel = level;
                                    while (tempLevel > 0)
                                    {
                                        dian = dian + "";
                                        tempLevel--;
                                    }
                                    tempArray3[1] = dian + partNumber1 + "(" + partName1 + ")";
                                    //"没有找到合适版本"
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
                                    //红色
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

                                    //需要先把pathVector中的所有的元素都放到String[]中去:
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
                                    //最好可以标明层次结构：
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
                                    //红色
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
                                //两个比较结构的零部件中有相同的不符合配置规范的子件
                                if(masterFlag1 == true && masterFlag2 == true )
                                {

                                  //需要先把pathVector中的所有的元素都放到String[]中去:
                                  for(int mm  = 0; mm<pathVector.size(); mm++)
                                  {
                                      String[] stringArray00 = (String[])pathVector.elementAt(mm);
                                      resultVector.addElement(stringArray00);
                                  }
                                  String[] tempArray3 = new String[15];
                                  tempArray3[0] = (new Integer(level)).toString();
                                  //最好可以标明层次结构：
                                  String dian = "";
                                  int tempLevel = level;
                                  while (tempLevel > 0)
                                  {
                                      dian = dian + "";
                                      tempLevel--;
                                  }
                                  tempArray3[1] = dian + partNumber1 + "(" + partName1 + ")";
                                  //"没有找到合适版本"
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
                                  //红色
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
                            //结束对数量相同的情况的处理
                            break;
                        }

                        //////whj add begin------------
                        else
                        if (flaga)
                        {

                          flag = true;
                          temp2.setElementAt("null", j);
                          //如果被父节点使用的数量是相同的话，而且masterFlag1 masterFlag2中都是false:
                          //这里有点问题：如果三个条件都满足，但是版本不相同。
                          //也是差异部件：需要直接放到结果集合中，而不需要再继续递归了！
                          //下面的这种情况是：两个零部件被父类使用的数量相同，而且两个零部件都有
                          //符合各自配置规范的版本：


                              //需要把当前的sourcePart, objectPart加到pathVector中，
                              String[] tempArray3 = new String[17];
                               // String[] tempArraya = new String[17];
                              tempArray3[0] = (new Integer(level)).toString();
                              //  tempArraya[0] = (new Integer(level)).toString();
                              //最好可以标明层次结构：
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
                              //灰色
                              //如果两个零部件的版本相同的话，就是"gray"，如果版本不同的话，就是"black"

                                tempArray3[6] = "gray";
                               //  tempArraya[6] = "gray";
                                //增加一项：partBsoID:
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


                              //组装节点路径:::

                              pathVector.addElement(tempArray3);


                              Vector tempResult = difference( (QMPartIfc) tempArray7[1],
                                                             sourceConfigSpecIfc,
                                                             (QMPartIfc) tempArray8[1],
                                                             objectConfigSpecIfc,
                                                             pathVector, map);
                              int tempSize = pathVector.size() - 1;
                              pathVector.removeElementAt(tempSize);
                            //  pathVector.addElement(tempArraya);
                              //需要把tempResult中的所有元素加到resultVector中，最后返回。

                                resultVector.addElement(tempArray3);
                                // resultVector.addElement(tempArraya);
                              for (int k = 0; k < tempResult.size(); k++) {
                                String[] tempSs = (String[]) tempResult.elementAt(k);
                                resultVector.addElement(tempSs);
                              }

                            }



                        //////whj add end--------------



                        //end if ((partNumber1 == partNumber2)&&(partName1 == partName2))
                        //结束编号和名称都相同的情况的处理。
                    }
                    //end if(temp2.elementAt(i) instanceof Object[])

                }
                //end for (int j=0; j<temp2.size(); j++)
                //对temp2中的元素遍历完成。
                //如果flag == false,说明在temp2中没有和temp1中的这个元素相同的元素
                //就把isHave1 = true, isHave2 = false, quantity1, quantity2 = 0
                //version1, version2 = null ===>resultVector中。
                //说明只有tempArray1中有这样的(partName1, partNumber1)元素。
                //把这个元素加到temp1Rest中去
                if (flag == false)
                {
                	//liun 2005.12.26去掉if(masterFlag1 == false),为了显示在源零部件中存在,
                	//但在目标零部件中不存在的partMaster.
                    //if(masterFlag1 == false)
                    temp1Rest.addElement(tempArray7);
                }
                //end if (flag == false)
            }
            //end if(temp1.elementAt(i) instanceof Object[])
        }
        //end for (int i=0; i<temp1.size(); i++)
        //在对temp1中的所有的元素循环处理完毕后，temp1Rest中的那些元素就是temp1中有，
        //但是temp2中没有的了。需要对temp1Rest中的所有的元素进行处理，放到resultVector中去。
        //需要先把pathVector中的所有的元素都放到String[]中去:
        boolean pathVectorFlag = false;
        boolean temp2Flag = false;
        if(temp1Rest.size() > 0)
            pathVectorFlag = true;
        //这个时候已经对temp2中的所有的剩下的元素进行了标识，如果不是"null"的话，就可以
        //对其保存，最后按照是QMPartIfc, 或者是QMPartMasterIfc来对temp2中的所有的元素
        //进行处理：

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
        //如果pathVectorFlag或者temp2Flag中有一个为真的话，说明当前的两个部件一定有不同的
        //地方，这样的话，就不需要再进行比较了：把当前的路径part加到最后的结果集合中去。
        if(pathVectorFlag || temp2Flag)
        {
            //把当前的路径节点都加到最后的结果集合中去:
            for(int mm  = 0; mm<pathVector.size(); mm++)
            {
                String[] stringArray00 = (String[])pathVector.elementAt(mm);
                resultVector.addElement(stringArray00);
            }
        }
        //end if(pathVectorFlag || temp2Flag)
        //目前有个问题：temp1Rest中的元素的类型都可能有哪些？part, partMaster,
        //因为temp1Rest来源于tempArray7,而tempArray7中的所有的元素都是直接来源于
        //temp1数组，这样的话temp1Rest中就可以有QMPartIfc, QMPartMasterIfc两种情况
        //出现。
        for(int i=0; i<temp1Rest.size(); i++)
        {

            pathVectorFlag = true;
            Object obj = temp1Rest.elementAt(i);
            boolean istihuan=false;
            if(obj instanceof Object[])
            {
                Object[] objArray = (Object[])obj;
                //按照前面的分析(2003.08.21:13:34),需要对objArray[1]元素的两种类型
                //进行分别处理：
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
                    //最好可以标明层次结构：
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

                        tempArray4[15] = "替换";
                        tempArray4[16] = "原件号："+par.getPartNumber()
                            +"版本："+par.getVersionValue()+"数量："+((PartUsageLinkIfc)tihuan[0]).getQuantity();
                      }
                       else
                       {
                         tempArray4[15] = "新增";
                         tempArray4[16] = "";
                       }
                      //CCBegin by leixiao 2010-2-24增加生命周期状态的显示
                      tempArray4[17] = partIfc.getLifeCycleState().getDisplay();
                      //CCEnd by leixiao 2010-2-24增加生命周期状态的显示

                    resultVector.addElement(tempArray4);
                }
                else if (objArray[1] instanceof QMPartMasterIfc)
                {

                    //说明是源部件有该零件，而且没有符合当前源部件配置规范的版本
                    //而目标零部件中没有该部件：的情况：
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
                    //最好可以标明层次结构：
                    String dian = "";
                    int tempLevel = level;
                    while (tempLevel > 0)
                    {
                        dian = dian + "";
                        tempLevel--;
                    }
                    tempArray4[1] = dian + partNumber1 + "(" + partName1 + ")";
                    //没有符合当前配置规范的版本：
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
        //在对temp1中的所有元素循环处理完毕后，temp2中的剩下的那些元素就是temp1
        //中没有的了，对temp2中的剩余元素进行循环处理，都放到resultVector中。
        //isHave1 = false, isHave2 = true, quantity1 = 0, quantity2 , version1 = null
        //version2 ===>resultVector中。根据对上面代码的分析，temp2中也可以有
        //QMPartIfc,和QMPartMasterIfc：这样的话，可以分别进行处理了：
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
                  //CCBegin by leixiao 2010-2-24增加生命周期状态的显示
                    String partstate = ((QMPartIfc)obj[1]).getLifeCycleState().getDisplay();
                  //CCEnd by leixiao 2010-2-24增加生命周期状态的显示
                    tempArray5[0] = (new Integer(level)).toString();
                    //最好可以标明层次结构：
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
                       tempArray5[15] = "取消";
                       tempArray5[16] = "";
                    }
                    //CCBegin by leixiao 2010-2-24增加生命周期状态的显示
                    tempArray5[17] = partstate;
                    //CCEnd by leixiao 2010-2-24增加生命周期状态的显示

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
                    //最好可以标明层次结构：
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
                    //没有找到合适的版本：
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
                        "进入方法difference()后的参数分别是: 源部件：" +
                        sourcePartIfc.getBsoID()
                        + "   目标部件：  " + objectPartIfc.getBsoID());
        PartDebug.trace(PartDebug.PART_SERVICE, "进入方法后的pathVector的内容为:");
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
                        "查询出来的源部件sourcePart:" + sourcePartIfc.getBsoID() +
                        "的符合配置规范的子节点有：");
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
                        "查询出来的目标部件objectPart:" +
                        objectPartIfc.getBsoID() + "的符合配置规范的子节点有：");
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
        PartDebug.trace(PartDebug.PART_SERVICE, "两者都没有符合配置规范的子节点了。");
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
                        "源部件的子部件空， 目标部件子部件非空。" +
                        "difference()方法的返回值是::");
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
                        "源部件的子部件非空，目标部件的子部件为空。" +
                        "difference()方法的返回值是::");
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
                        "两个都不为空的时候。difference()方法的返回值是::");
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
     * 将参数collection中的元素上溯造型为PartUsageLinkIfc对象以rightBsoID(零部件的主信息的bsoID)为键字保存在哈西表中并返回哈西表。
     * @param collection :Collection PartUsageLinkIfc对象集合。
     * @return hashtable:Hashtable 哈西表。键为零部件主信息bsoID，值为PartUsageLinkIfc
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
     * 获得partIfc关联的所有PartUsageLinkIfc对象的集合。
     * @param partIfc :QMPartIfc对象。
     * @return collection:Colllection 与partIfc关联的所有PartUsageLinkIfc对象集合。
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
            //：在循环中将enum中的元素放入collection1中
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getConsolidatedBOM() end...."+
                "return is Collection");
        return vector1;
    }

    /**
     * 保存新建和更新的有效性方案。
     * @param configItemIfc :EffConfigurationItemIfc 待保存的有效性配置项值对象。
     * @throws QMException
     * @return EffConfigurationItemIfc 已保存的有效性配置项值对象。
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
        //如果之前持久化保存过该有效性方案
      if(oldIfc != null)
      {
        //如果有效性方案已经指定产品解决方案
        if(oldIfc.getSolutionID() != null && oldIfc.getSolutionID().length() > 0)
        {
          //如果当前要求修改产品解决方案
          if(!oldIfc.getSolutionID().equals(configItemIfc.getSolutionID()))
            throw new QMException(RESOURCE, "4", null);
        }
        else
            //之前没有指定过产品解决方案
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
        //之前没有保存过该名称的有效性方案
        oldIfc = configItemIfc;
      }
      PartDebug.trace(this, PartDebug.PART_SERVICE, "saveConfigItemIfc() end...." + "return is EffConfigurationItemIfc");
      return(EffConfigurationItemIfc)service.saveValueInfo(oldIfc);
    }

    /**
     * 删除有效性方案。
     * @param configItemIfc EffConfigurationItemIfc 有效性方案。
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
     * 创建一个EffGroup对象。
     * @param effectivityType :有效性类型,可以是"QMDatedEffectivity", "QMLotEffectivity", "QMSerialNumEffectivity"。
     * @param value_range :有效性值的范围。
     * @param configItemIfc :QMConfigurationItemIfc 有效性上下文值对象。
     * @param partIfc :EffManagedVersionIfc 受有效性和版本管理的对象值对象,这里为零部件值对象。
     * @return EffGroup 根据输入的参数创建的新的EffGroup对象。
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
     * 获得在当前配置规范下partMasterIfc的结构下所有子件。
     * @param partMasterIfc 零部件主信息值对象。
     * @param configSpecIfc 零部件配置规范值对象。
     * @return QMPartIfc[] 符合配置规范的所有子部件值对象的集合。
     * @exception QMException
     * @see QMPartMasterInfo
     * @see QMPartInfo
     */
    public QMPartIfc[] getAllSubPartsByConfigSpec(QMPartMasterIfc partMasterIfc,
        PartConfigSpecIfc configSpecIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllSubPartsByConfigSpec(QMPartMasterIfc,"+
                " PartConfigSpecIfc) begin ....");
        //第一步：根据QMPartMasterIfc获取符合配置规范的，最新版本的QMPartIfc,
        //第二步：调用方法getAllSubPartsByConfigSpec(QMPartIfc, PartConfigSpecIfc)
        //第三步：对上面的结果，处理，放到QMPartIfc[]中去。
        ConfigService service = (ConfigService)EJBServiceHelper.getService("ConfigService");
        Collection collection = service.filteredIterationsOf(partMasterIfc,
            new PartConfigSpecAssistant(configSpecIfc));

        if((collection == null)||(collection.size()>1)||(collection.size() == 0))
        {
            throw new PartException(RESOURCE, "7", null);
        }
        Iterator iterator = collection.iterator();
        //不能这样转化：iterator.next()里面是Object[]
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
    //完成!!!!

    /**
     * 根据当前配置规范获取QMPartIfc下的所有子部件。
     * @param partIfc 零部件值对象。
     * @param configSpecIfc 零部件配置规范值对象。
     * @return Vector 结果集合，集合中的元素是QMPartIfc。
     * @throws QMException
     * //CCBegin by leixiao 2011-1-12 原因:解放路线新需求  private->public 给艺准按结构添加用
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
            //对collection中的元素进行循环，调用方法getAllSubPartsByConfigSpec(),结果，加到resultVector中
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
        //需要对结果集合进行合并，如果有相同的QMPartIfc的话，合并到一起:
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
    //完成!!!!

    /**
     * 获得part对象已经存在的EffGroup对象的集合。
     * @param partIfc QMPartIfc 零部件值对象
     * @throws QMException
     * @return Vector Vector中的每个元素都是EffGroup对象。
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
     * 获得在当前配置规范下partMasterIfc的结构下所有子件的有效性范围数据类对象EffGroup的
     * 集合。该方法用于显示按结构添加有效性结果。
     * @param partMasterIfc :零部件主信息值对象。
     * @param configSpecIfc :PartConfigSpecIfc 配置规范值对象。
     * @param configItemID 有效性配置项bsoID。
     * @param value_range :String 有效性值范围。
     * @return Vector EffGroup对象集合。
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
            //"不是有效性配置项的实例"
            else
            {
                throw new PartException(RESOURCE, "5", null);
            }
        }
        //是日期有效性：
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
     * 根据零部件值对象获取该零部件的所有有效性值。
     * @param partIfc : WorkableIfc 零部件值对象。
     * @param isacl : boolean 是否访问控制。
     * @return Vector EffGroup的集合。
     * @throws QMException
     * @see WorkableIfc
     */
    public Vector getEffVector(WorkableIfc partIfc , boolean isacl) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "getEffVector() begin ....");
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "----------------------------------------------");
        PartDebug.trace(PartDebug.PART_SERVICE, "输入的参数为：" + partIfc);
        PartDebug.trace(PartDebug.PART_SERVICE,
                        "----------------------------------------------");
        if (WorkInProgressHelper.isWorkingCopy(partIfc))
        {
            WorkInProgressService wipservice = (WorkInProgressService)EJBServiceHelper.
                getService("WorkInProgressService");
            partIfc = wipservice.originalCopyOf(partIfc);
        }
        //查询Eff下的所有有表的类，获取和本partIfc关联的所有有效性值对象
        QMQuery query = new QMQuery("Eff");
        QueryCondition condition = new QueryCondition("targetBranchID","=",partIfc.getBranchID());
        query.addCondition(condition);
        PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        Collection collection = pService.findValueInfo(query);
        //需要把collection中的所有有效性值对象转移到EffIfc[]中，并调用EffGroup(EffIfc[])构造函数
        //第一步:获取这些EffIfc的所有的EffContextID,
        Iterator iterator = collection.iterator();
        //vector保存的要么是effContextID + effIfc.getBsoName(), 要么是"null" :
        Vector vector = new Vector();
        EffIfc effIfc = null;
        //该零部件partIfc:QMPartIfc所关联的，所有的有效性effIfc对象的集合：
        Vector tempEffArray1 = new Vector();
        //原来是把EffContextID作为唯一的关键字，现在需要结合具体的有效性的BsoName两个变量
        //结合起来，来确定是否对有效性值进行合并:
        while(iterator.hasNext())
        {
            effIfc = (EffIfc)iterator.next();
            tempEffArray1.addElement(effIfc);
            //该处就应该加上判断，如果getEffContextID()为空的话
            String str = effIfc.getEffContextID();
            if(str != null && str.length() > 0)
            {
                //把EffContextID和具体的有效性的BsoName结合起来。
                str = str + effIfc.getBsoName();
                if(!vector.contains(str))
                {
                    //vector 中存放的是和effIfc关联的有效性配置项的bsoID
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
        //result保存最后的结果,为EffGroup的集合
        Vector result = new Vector();
        //第二步:根据vector中的每一个EffContextID值，过滤tempEffArray1中的所有的元素
        //并针对vector中的每个EffContextID生成一个EffGroup对象, 所有的EffContextID
        //相同的EffIfc对象统一的放在tempEffArray2中。
        for(int i=0; i<vector.size(); i++)
        {
            String s1 = (String)vector.elementAt(i);
            Vector tempEffArray2 = new Vector();
            //现在是根据EffContextID来对有效性值进行合并，新的需求要求可以不指定EffContext
            //的bsoID，这样的话，需要结合EffContextID和EffectivityType两个参数来对
            //有效性值进行合并。
            for(int j=0; j<tempEffArray1.size(); j++)
            {
                EffIfc effIfc1 = (EffIfc)tempEffArray1.elementAt(j);
                String s2 = effIfc1.getEffContextID();
                //注意这里的s2有可能是null，这样的话，就不可以相加了：
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
            //现在tempEffArray2中至少有一个元素EffIfc
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
     * 根据零部件值对象获取该零部件的所有有效性值。
     * @param partIfc : WorkableIfc 零部件值对象。
     * @return Vector EffGroup的集合。
     * @throws QMException
     * @see WorkableIfc
     */
    public Vector getEffVector(WorkableIfc partIfc) throws QMException
    {
        return getEffVector(partIfc , true);
    }
    
    /**
     * 根据零部件值对象获取该零部件的所有有效性值。
     * @param partIfc：WorkableIfc 零部件值对象。
     * @param itemID：有效性配置项bsoID。
     * @return Vector EffGroup的集合。
     * @throws QMException
     * @see WorkableIfc
     */
    public Vector getEffVector(WorkableIfc partIfc,String itemID) throws QMException
    {
        return getEffVector(partIfc , itemID,true);
    }

    /**
     * 获取服务名称
     * @return "EnterprisePartService"
     */
    public String getServiceName()
    {
        return "EnterprisePartService";
    }


    /**
     * 删除part对应的有效性值。
     * @param partBsoID 零件的bsoID。
     * @param configItemName 有效性方案名称。
     * @param effectivityType 有效性类型:"DATE","LOT_NUM","SERIAL_NUM"。
     * @throws QMException
     */
    public void deleteAllEffs(String partBsoID, String configItemName, String effectivityType)throws QMException
  {
    effectivityType =this.invertChineseToEnglish(effectivityType);
    PersistService pService =(PersistService)EJBServiceHelper.getService("PersistService");
    QMPartIfc partIfc =(QMPartIfc)pService.refreshInfo(partBsoID);
    //查询Eff下的所有有表的类，获取和本partIfc关联的所有有效性值对象
    QMQuery query = new QMQuery("Eff");
    QueryCondition condition = new QueryCondition("targetBranchID","=",partIfc.getBranchID());
    query.addCondition(condition);
    Collection effVector = pService.findValueInfo(query);
    EffIfc effIfc1 ;
    //如果有效性方案名称为空
    if(configItemName==null||configItemName.equals(""))
    {
      if(effectivityType.equals("DATE"))
      {
    	//CCBegin by leixiao 2010-5-4 打补丁 v4r3_p013_20100415  
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
      //CCEnd by leixiao 2010-5-4 打补丁 v4r3_p013_20100415  
        datequery.addCondition(datecondition1);
        datequery.addAND();
        QueryCondition datecondition2 = new QueryCondition("effContextID",QueryCondition.IS_NULL);
        datequery.addCondition(datecondition2);
        Collection dateeffVector = pService.findValueInfo(datequery);
        for(Iterator iter = dateeffVector.iterator() ; iter.hasNext() ; )
        {
          //过滤
          effIfc1 =(EffIfc)iter.next();
          //String bsoName =effIfc1.getBsoName();
          //if(bsoName.equals("QMDatedEffectivity"))
          this.editEff(effIfc1,"DELETE");
        }
      }
      else
      {
        //String s="有效性类型不是日期类型";
        throw new PartException(RESOURCE,"13",null);
      }
    }
    //有效性方案名称不为空
    else
    {
      //要删除的有效性放到该集合中
      //Vector deleteV=new Vector();
      QMQuery query2 =new QMQuery("QMConfigurationItem");
      QueryCondition condition2 =new QueryCondition("configItemName","=",configItemName);
      query2.addCondition(condition2);

      //获得对应有效性配置项名称的配置项
      Collection itemVector = pService.findValueInfo(query2,false);
      if(itemVector.size()!=1)
      {
        //String s="根据有效性配置项的名称不到相应的配置项";
        throw new PartException(RESOURCE,"14",null);
        //return;
      }
      AccessControlService access=(AccessControlService)EJBServiceHelper.getService("AccessControlService");
      itemVector = access.filterObjects(itemVector,QMPermission.READ);
      if(itemVector.size()!=1)
      {
        //String s="根据有效性配置项的名称不到相应的配置项";
        Object[] objs = {configItemName};
        throw new PartException(RESOURCE,"15",objs);
        //return;
      }
      else
      {
        String itemID1="";
        String itemID2="";
        QMConfigurationItemIfc itemIfc=(QMConfigurationItemIfc)itemVector.iterator().next();//elementAt(0);
        //传进来的配置项id
        itemID1 =itemIfc.getBsoID();

        for(  Iterator iter = effVector.iterator();iter.hasNext() ;)
        {
          EffIfc effIfc2 =(EffIfc)iter.next();
          //获得有效性配置项的id
          itemID2 =effIfc2.getEffContextID();
          //获得有效性bsoName
          String bsoName =effIfc2.getBsoName();
          if(itemID2!= null && itemID1.equals(itemID2))
          {
            //如果为日期有效性
            if(bsoName.equals("QMDatedEffectivity")&&effectivityType.equals("DATE"))
            this.editEff(effIfc2,"DELETE");
            //如果为序列号有效性
            else if(bsoName.equals("QMSerialNumEffectivity")&&effectivityType.equals("SERIAL_NUM"))
            this.editEff(effIfc2,"DELETE");
            //如果为序列号有效性
            else if(bsoName.equals("QMLotEffectivity")&&effectivityType.equals("LOT_NUM"))

            this.editEff(effIfc2,"DELETE");
          }
        }
      }
    }
  }
  /**
   *定义私有方法将有效性类型转化为英文。
   * @param chineseName String
   * @return String
   */
  private String invertChineseToEnglish(String chineseName)
  {
      EffectivityType types1[] = EffectivityType.getEffectivityTypeSet(Locale.CHINA);
      //判断该chineseName是否是英文的：
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
     * 创建part对应的有效性值。
     * @param partIfc 零部件值对象。
     * @param configItemName 有效性方案名称。
     * @param oldEffectivityType 有效性类型不能为空必须是:"DATE","LOT_NUM","SERIAL_NUM"之一。
     * @param effValue 有效性值。
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
      //提示信息：“输入的有效性范围值无效”
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
      //如果有效性类型为空
      if((effectivityType==null)||effectivityType.equals(""))
      {
          throw new PartException(RESOURCE,"13",null);
      }
      //如果有效性配置项名称为空（effectivityType必须是日期类型）
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
      //如果有效性配置项名称不为空
       else
       {
           PersistService pService =(PersistService)EJBServiceHelper.getService("PersistService");
           QMQuery query2 =new QMQuery("QMConfigurationItem");
           QueryCondition condition2 =new QueryCondition("configItemName","=",configItemName);
           query2.addCondition(condition2);
           //获得对应有效性配置项名称的配置项
           Collection itemVector = pService.findValueInfo(query2,false);
           if(itemVector.size()!=1)
           {
               //String s="根据有效性配置项的名称不到相应的配置项";
               throw new PartException(RESOURCE,"14",null);
               //return;
           }
           AccessControlService access=(AccessControlService)EJBServiceHelper.getService("AccessControlService");
           itemVector = access.filterObjects(itemVector,QMPermission.READ);
           if(itemVector.size()!=1)
           {
                //String s="根据有效性配置项的名称不到相应的配置项";
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
     * 更新 part对应的有效性值。
     * @param partIfc QMPartIfc 零部件的值对象。
     * @param configItemName String 有效性方案名称。
     * @param oldEffectivityType String
     *   有效性类型不能为空必须是:"DATE","LOT_NUM","SERIAL_NUM"之一。
     * @param newEffValue String 新的有效性值范围。
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
    //提示信息：“输入的有效性范围值无效”
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
    //有效性方案名称不为空
    else
    {
      oldEffectivityType =this.invertChineseToEnglish(oldEffectivityType);
      //要删除的有效性放到该集合中
      //Vector deleteV=new Vector();
      QMQuery query2 =new QMQuery("QMConfigurationItem");
      QueryCondition condition2 =new QueryCondition("configItemName","=",configItemName);
      query2.addCondition(condition2);
      PersistService pService =(PersistService)EJBServiceHelper.getService("PersistService");
      //获得对应有效性配置项名称的配置项
      Collection itemVector = pService.findValueInfo(query2,false);
      if(itemVector.size()!=1)
      {
        //String s="根据有效性配置项的名称不到相应的配置项";
        throw new PartException(RESOURCE,"14",null);
        //return;
      }
      AccessControlService access=(AccessControlService)EJBServiceHelper.getService("AccessControlService");
      itemVector = access.filterObjects(itemVector,QMPermission.READ);
      if(itemVector.size()!=1)
      {
        //String s="根据有效性配置项的名称不到相应的配置项";
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
     * 利用事物容器对有效性进行创建、更新、删除。
     *
     * @param obj 有效性对象。
     * @param s 创建、更新、删除标志。
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
   * 在调用方法difference(),进行结构比较之前，对结构进行调整，合并相同零件的数量
   * @param result Collection//result 中，存的都是数组
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
      //标识在集合hebing中是否存在当前被循环的这个零部件,初始情况下,认为不存在:
      flag = false;
      //对已有的合并完毕的集合进行循环:
      PartDebug.trace(PartDebug.PART_SERVICE, "for之前的rid==" + rid + "   leftBsoID==" + leftBsoID);
      for (int j = 0; j < hebing.size(); j++)
      {
        Object[] obj = (Object[]) hebing.elementAt(j);
        PartUsageLinkIfc puli1 = (PartUsageLinkIfc) obj[0];
        float quantity = puli1.getQuantity();
        String id = puli1.getRightBsoID();
        String oldLeftBsoID = puli1.getLeftBsoID();
        //如果使用的是同一个零部件的话,合并数量,
        if (leftBsoID.equals(oldLeftBsoID))
        {
          puli1.setQuantity(rquantity + quantity);
          //找到了这个零部件:
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
   * 打印中心结构汇总，返回集合没有重复的件
   * 获得在当前筛选条件下partMasterIfc的结构下所有子件。
   * @param partMasterIfc 零部件主信息值对象。
   * @param configSpecIfc 配置规范。
   * @return Vector 所有子部件值对象的集合。
   * @exception QMException
   */
  public Vector getAllSubPartsByConfigSpecForQQ(QMPartMasterIfc partMasterIfc,
      PartConfigSpecIfc configSpecIfc) throws QMException
  {
      PartDebug.trace(this, PartDebug.PART_SERVICE, "getAllSubPartsByConfigSpec(QMPartMasterIfc,"+
              " PartConfigSpecIfc) begin ....");
      //第一步：根据QMPartMasterIfc获取符合配置规范的，最新版本的QMPartIfc,
      //第二步：调用方法getAllSubPartsByConfigSpec(QMPartIfc, PartConfigSpecIfc)
      //第三步：对上面的结果，处理，放到QMPartIfc[]中去。
      ConfigService service = (ConfigService)EJBServiceHelper.getService("ConfigService");
      Collection collection = service.filteredIterationsOf(partMasterIfc,
          new PartConfigSpecAssistant(configSpecIfc));

      if((collection == null)||(collection.size()>1)||(collection.size() == 0))
      {
          throw new PartException(RESOURCE, "7", null);
      }
      Iterator iterator = collection.iterator();
      //不能这样转化：iterator.next()里面是Object[]
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
