/** 生成程序 QMPartIfc.java    1.0    2003/02/17
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 添加工艺路线使用的方法  guoxiaoliang 2013-02-04
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
 * 零部件值对象接口。
 * @author 吴先超
 * @version 1.0
 */

public interface QMPartIfc extends RevisionControlledIfc, BaselineableIfc,
        EffManagedVersionIfc, ViewManageableIfc, EffectivityManageableIfc,
        PartVersion,
        ContentHolderIfc, IBAHolderIfc, BuildTargetIfc, AffixIfc
{
    /**
     * 获得零部件名。
     * @return String
     */
    public String getPartName();


    /**
     * 设置零部件名。
     * @param name String
     */
    public void setPartName(String name);


    /**
     * 获得零部件号。
     * @return String
     */
    public String getPartNumber();


    /**
     * 设置零部件号。　
     * @param number String
     */
    public void setPartNumber(String number);


    /**
     * 获得默认单位。
     * @return Unit
     */
    public Unit getDefaultUnit();


    /**
     * 设置默认单位。
     * @param unit :Unit 枚举类型。
     */
    public void setDefaultUnit(Unit unit);


    /**
     * 获得该对象的所有有效性值的集合（即属性target为该EffManagedVersionIfc对象的所有
     * EffGroup对象）。该方法只是实现业务逻辑。
     * @return Vector
     */
    public Vector getEffVector();


    /**
     * 设置有效性集合的值。
     * @param vector Vector
     */
    public void setEffVector(Vector vector);


    /**
     * 获取零部件辅助类对象。
     * @return QMPartMap
     */
    public QMPartMap getQMPartMap();


    /**
     * 设置零部件辅助类对象。
     * @param map :QMPartMap
     */
    public void setQMPartMap(QMPartMap map);


    /**
     * 获得零部件来源。
     * @return ProducedBy
     */
    public ProducedBy getProducedBy();


    /**
     * 设置零部件来源。
     * @param producedBy :ProducedBy
     */
    public void setProducedBy(ProducedBy producedBy);


    /**
     * 获得零部件类型。
     * @return String
     */
    public QMPartType getPartType();


    /**
     * 设置零部件类型。
     * @param type QMPartType
     */
    public void setPartType(QMPartType type);


    /**
     * 设置项目组名称。
     * @param projectName String
     */
    public void setProjectName(String projectName);


    /**
     * 获取项目组名称。
     * @return ProjectName
     */
    public String getProjectName();


    /**
     * 获取零部件标准图标名称。
     * @return String
     */
    public String getStandardIconName();


    /**
     * 设置是否是颜色零件标识。
     * @param flag 颜色零件标识。
     */
    public void setColorFlag(boolean flag);


    /**
     * 获取是否是颜色零件标识。
     * @return 颜色零件标识。
     */
    public boolean getColorFlag();


    /**
     * 获得图标路径。
     *
     * @return String
     */
    public String getPartIconPath();


    /**
     * 获得图标。
     *
     * @return Image
     */
    public Image getPartIcon();


    /**
     * 获得图标相对URI,仅供瘦客户使用。
     *
     * @return String
     */
    public String getPartIconURI();
    /**
     * 获取标识
     * @return 返回字串形如：QMPart+partname+partnumber
     */
    public String getIBAID();
    /**
     * 获取选装代码
     * @return String 选装代码
     */
    public String getOptionCode();
   /**
    * 设置选装代码
    * @param code
    */
    public void setOptionCode(String code);
    
    public void setCompare(String s);

    public String getCompare();
    /**
     * 设置零部件类型。
     * @param type 类型枚举类
     * @see QMPartType
        　 */
    public void setPartType(String type);
    
    /**
     * 设置默认单位。
     * @param unit Unit 单位的枚举类
     * @see Unit
     */
    public void setDefaultUnit(String unit);
    
    /**
     * 设置零部件来源。
     * @param producedBy ProducedBy 来源枚举类
     * @see ProducedBy
     */
    public void setProducedBy(String producedBy);
    
    //CCBegin SS1
    
    /**
     * 获取是否是被cad检出标识
     * @param CHECKOUTBYCAD
     */
    public boolean getCheckoutByCAD();
    //CCEnd SS1

}
