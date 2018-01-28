/** 生成程序 QMPartInfo.java    1.0    2003/02/17
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 * SS1 添加工艺路线使用的方法  guoxiaoliang 2013-02-04
 * 
 */

package com.faw_qm.part.model;


import java.awt.Canvas;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.faw_qm.affixattr.model.AffixMap;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.content.model.FormatContentHolderMap;
import com.faw_qm.eff.util.EffGroup;
import com.faw_qm.enterprise.model.RevisionControlledInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.iba.value.util.AttributeContainer;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;
import com.faw_qm.part.util.Unit;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.project.model.ProjectIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.util.JNDIUtil;
import com.faw_qm.viewmanage.model.ViewManageMap;
import com.faw_qm.wip.util.WorkInProgressState;


/**
 * 零部件值对象实现类。
 * @author 吴先超
 * @version 1.0
 */

public class QMPartInfo extends RevisionControlledInfo implements QMPartIfc
{
    private QMPartMap theQMPartMap;
    private ViewManageMap theViewManageMap;
    private Vector effVector;
    private AffixMap theAffixMap;
    protected FormatContentHolderMap holderMap = null;

    public static final String StandardIcon = "StandardIcon"; //标准图标
    public static final String CheckOutIcon = "CheckOutIcon"; //工作原本图标
    public static final String WorkingIcon = "WorkingIcon"; //工作副本图标
    public static HashMap iconCache = new HashMap();
    static final long serialVersionUID = -1L;
    private String  compare="";

    /**
     * 构造函数，创建新的零部件值对象。
     * @throws QMException
     */
    public QMPartInfo()
            throws QMException
    {
        super();
        theQMPartMap = new QMPartMap();
        theViewManageMap = new ViewManageMap();
        theAffixMap = new AffixMap();
      
        try
        {
            //调用了父类RevisionControlledInfo中的方法
            initialize();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new QMException(e);
        }
    }


    /**
     * 构造函数。含有两个参数。
     * @param s :String 零部件号。
     * @param s1 :String 零部件名。
     * @throws QMException
     */

    public QMPartInfo(String s, String s1)
            throws QMException
    {
        this();
        initialize(s, s1);
        this.setEventSet(null);
    }

    protected void initialize(String s, String s1)
    {
        setPartNumber(s);
        setPartName(s1);
        setName(s1);
    }


    /**
     * 获取零部件辅助类对象。
     * @return QMPartMap
     */
    public QMPartMap getQMPartMap()
    {
        if (theQMPartMap == null)
        {
            theQMPartMap = new QMPartMap();
        }
        return theQMPartMap;
    }


    /**
     * 设置零部件辅助类对象。
     * @param map QMPartMap
     */
    public void setQMPartMap(QMPartMap map)
    {
        theQMPartMap = map;
    }


    /**
     * 获得零部件名。
     * @return String 零部件名称
     */
    public String getPartName()
    {
        return ((QMPartMasterInfo) getMaster()).getPartName();
    }


    /**
     * 设置零部件名。
     * @param name String 零部件名称
     */
    public void setPartName(String name)
    {
        ((QMPartMasterInfo) getMaster()).setPartName(name);
    }


    /**
     * 获得零部件号。
     * @return String 零部件编号
     */
    public String getPartNumber()
    {
        return ((QMPartMasterInfo) getMaster()).getPartNumber();
    }


    /**
     * 设置零部件号。
     * @param number String 零部件编号
     */
    public void setPartNumber(String number)
    {
        ((QMPartMasterInfo) getMaster()).setPartNumber(number);
    }


    /**
     * 获得默认单位。
     * @return Unit 单位的枚举类
     * @see Unit
     */
    public Unit getDefaultUnit()
    {
        return ((QMPartMasterInfo) getMaster()).getDefaultUnit();
    }


    /**
     * 设置默认单位。
     * @param unit Unit 单位的枚举类
     * @see Unit
     */
    public void setDefaultUnit(Unit unit)
    {
        ((QMPartMasterInfo) getMaster()).setDefaultUnit(unit);
    }


    /**
     * 获得该对象的所有有效性值的集合（即属性target为该EffManagedVersionIfc对象的所有
     * EffGroup对象）。该方法只是实现业务逻辑。
     * @return Vector EffGroup对象集合
     */
    public Vector getEffVector()
    {
        return effVector;
    }


    /**
     * 设置有效性值集合。
     * @param vector VectorEffGroup对象集合
     */
    public void setEffVector(Vector vector)
    {
        effVector = vector;
        for (int i = 0; effVector != null && i < effVector.size(); i++)
        {
            try
            {
                ((EffGroup) effVector.elementAt(i)).setTarget(this);
            }
            catch (QMException ex)
            {
                ex.printStackTrace();
            }
        }
    }


    /**
     * 获得零部件来源。
     * @return ProducedBy 来源枚举类
     * @see ProducedBy
     */
    public ProducedBy getProducedBy()
    {
        return getQMPartMap().getProducedBy();
    }


    /**
     * 设置零部件来源。
     * @param producedBy ProducedBy 来源枚举类
     * @see ProducedBy
     */
    public void setProducedBy(ProducedBy producedBy)
    {
        getQMPartMap().setProducedBy(producedBy);
    }


    /**
     * 获得零部件类型。
     * @return QMPartType 类型枚举类
     * @see QMPartType
     */
    public QMPartType getPartType()
    {
        return getQMPartMap().getPartType();
    }


    /**
     * 设置零部件类型。
     * @param type 类型枚举类
     * @see QMPartType
        　 */
    public void setPartType(QMPartType type)
    {
        getQMPartMap().setPartType(type);
    }


    /**
     * 获取业务对象名。
     * @return "QMPart"
     */
    public String getBsoName()
    {
        return "QMPart";
    }


    /**
     * 获取视图管理辅助类对象。
     * @return ViewManageMap
     */
    public ViewManageMap getViewManageMap()
    {
        if (theViewManageMap == null)
        {
            theViewManageMap = new ViewManageMap();
        }
        return theViewManageMap;
    }


    /**
     * 设置视图管理辅助类对象。
     * @param map ViewManageMap
     */
    public void setViewManageMap(ViewManageMap map)
    {
        theViewManageMap = map;
    }


    /**
     * 获取视图名称。
     * @return String viewName 视图名称
     */
    public String getViewName()
    {
        return getViewManageMap().getViewName();
    }


    /**
     * 设置视图名称。
     * @param viewName String 视图名称
     */
    public void setViewName(String viewName)
    {
        getViewManageMap().setViewName(viewName);
    }


    /**
     * 设置工作组名称。
     * @param projectName String 工作组名称
     */
    public void setProjectName(String projectName)
    {

        //这个时候就有点问题了：
        //这个时候应该是设置本零部件的projectId属性：
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "setProjectName(String) begin ....");
        //根据projectName , 找到值对象->bsoID, 设置bsoID.(setProjectID方法)
        if (projectName == null)
        {
            PartDebug.trace(this, PartDebug.PART_SERVICE,
                            "setProjectName(String) end....return is null ");
            return;
        }
        Collection collection = null;
        //如果是服务端调用的话：
        if (IBAUtility.server == null)
        {
            try
            {
                QMQuery query = new QMQuery("Project");
                QueryCondition condition = new QueryCondition("name", "=",
                        projectName);
                query.addCondition(condition);
                PersistService pService = (PersistService) EJBServiceHelper.
                                          getPersistService();
                collection = pService.findValueInfo(query);
            }
            catch (QMException ex)
            {
                ex.printStackTrace();
            }
        }
        //如果是客户端调用的话：
        else
        {
            try
            {
                QMQuery query = new QMQuery("Project");
                QueryCondition condition = new QueryCondition("name", "=",
                        projectName);
                query.addCondition(condition);
                //这里需要按照客户端的思路进行调用：
                //参数类型的集合
                Class[] paraClass =
                                    {QMQuery.class};
                //参数值的集合
                Object[] objs =
                                {query};
                collection = (Collection) IBAUtility.invokeServiceMethod(
                        "PersistService", "findValueInfo", paraClass, objs);
            }
            catch (QMException ex)
            {
                ex.printStackTrace();
            }
        }
        if ((collection == null) || (collection.size() == 0) ||
            (collection.size() > 1))
        {
            //"没有查找到(或者查找到了多个)相应的项目组，请确定输入项目组名是否正确"
            //throw new PartException(RESOURCE, "8", null);
        }
        else
        {
            Iterator iterator = collection.iterator();
            ProjectIfc projectIfc = (ProjectIfc) iterator.next();
            setProjectId(projectIfc.getBsoID());
            theQMPartMap.setProjectName(projectName);
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "setProjectName(String) end....return is void ");
    }


    /**
     * 设置工作组名称。
     * @param projectName String 工作组名称
     */
    public void setProjectDisplayName(String projectName)
    {
        theQMPartMap.setProjectName(projectName);
    }


    /**
     * 获取工作组名称。
     * @return ProjectName 工作组名称
     */
    public String getProjectName()
    {
        return theQMPartMap.getProjectName();
    }


    /**
     * 获取格式化的内容容器辅助类对象。
     * @return FormatContentHolderMap
     */
    public FormatContentHolderMap getFormatContentHolderMap()
    {
        if (holderMap == null)
        {
            holderMap = new FormatContentHolderMap();
        }
        return holderMap;
    }


    /**
     * 设置格式化的内容容器辅助类对象。
     * @param map0 FormatContentHolderMap
     */
    public void setFormatContentHolderMap(FormatContentHolderMap map0)
    {
        holderMap = map0;
    }


    /**
     * 设置数据格式ID。
     * @param format0 String
     */
    public void setDataFormatID(String format0)
    {
        getFormatContentHolderMap().setDataFormatID(format0);
    }


    /**
     * 获取数据格式ID。
     * @return String
     */
    public String getDataFormatID()
    {
        return getFormatContentHolderMap().getDataFormatID();
    }


    /**
     * 获取数据格式名。
     * @return String
     */
    public String getDataFormatName()
    {
        return getFormatContentHolderMap().getDataFormatName();
    }


    /**
     * 设置数据格式名。
     * @param name0 String
     */
    public void setDataFormatName(String name0)
    {
        getFormatContentHolderMap().setDataFormatName(name0);
    }


    /**
     * 获取零部件标准图标名称。
     * @return String
     */
    public String getStandardIconName()
    {
        String icon = this.getIconName("StandardIcon");
        if (icon != null && icon.length() > 0)
        {
            String sub = icon.substring(0, 1);
            if (sub.equals("/"))
            {
                String icon1 = "";
                for (int i = 1; i < icon.length(); i++)
                {
                    icon1 = icon1 + icon.substring(i, i + 1);
                }
                return icon1;
            }
            else
            {
                return icon;
            }
        }
        else
        {
            return "";
        }
    }


    /**
     * 设置是否是颜色零件标识。
     * @param flag 颜色零件标识。
     */
    public void setColorFlag(boolean flag)
    {
        this.getQMPartMap().setColorFlag(flag);
    }


    /**
     * 获取是否是颜色零件标识。
     * @return 颜色零件标识。
     */
    public boolean getColorFlag()
    {
        return getQMPartMap().getColorFlag();
    }


    /**
     * 获得图标路径。
     *
     * @return String 路径名
     */
    public String getPartIconPath()
    {
        String bsoName = getBsoName();
        String iconName = StandardIcon;
        if (getWorkableState().equals(WorkInProgressState.CHECKED_OUT.toString()))
        {
            iconName = CheckOutIcon;
        }
        else if (getWorkableState().equals(WorkInProgressState.WORKING.toString()))
        {
            iconName = WorkingIcon;
        }
        String iconPath = null;
        iconPath = (String)JNDIUtil.getFeatureValue(bsoName, iconName);
        return iconPath;
    }
   /**
    * 获取零部件图标的URI
    * @return String  URI
    */
    public String getPartIconURI()
    {
        String partURI = getPartIconPath();
        if (partURI != null)
        {
            if (partURI.startsWith("/") || partURI.startsWith("\\"))
            {
                partURI = partURI.substring(1);
            }
        }
        return partURI;
    }


    /**
     * 获得图标。
     *
     * @return Image
     */
    public Image getPartIcon()
    {
        Image image = null;
        String iconPath = getPartIconPath();
        String iconKey = getBsoName() + iconPath;
        if (iconCache.containsKey(iconKey))
        {
            image = (Image) iconCache.get(iconKey);
        }
        else
        {
            image = getImage(iconPath);
            iconCache.put(iconKey, image);
        }
        return image;
    }

    protected Image getImage(String iconPath)
    {
        Image image = null;
        if (iconPath != null)
        {
            URL url = QMPartInfo.class.getResource(iconPath);
            if (url != null)
            {
                image = loadImage(url);
            }
        }
        return image;
    }


    /**
     * 根据具体的组件和路径获得图标，组件为当前运行组件。
     * 欲获得的图标必须放在/images/下。路径为"/images/..."的形式。 注意路径包含后缀。
     *
     * @param uri URL
     * @return java.awt.Image
     */
    protected static synchronized Image loadImage(URL uri)
    {
        Image image = null;
        try
        {
            image = Toolkit.getDefaultToolkit().getImage(uri);
            if (image != null)
            {
                MediaTracker mediatracker = new MediaTracker(new Canvas());
                mediatracker.addImage(image, 0);
                try
                {
                    mediatracker.waitForAll();
                    if (mediatracker.isErrorAny())
                    {
                        image = null;
                    }
                }
                catch (InterruptedException interruptedexception)
                {
                    image = null;
                    interruptedexception.printStackTrace();
                }
            }
        }
        catch (Exception exception1)
        {
            image = null;
            exception1.printStackTrace();
        }
        return image;
    }
    /**
	 * 获取事物特性属性容器
	 * @return AttributeContainer 事物特性属性容器
	 *@see AttributeContainer
	 */
    public AttributeContainer getAttributeContainer()
    {
        return getQMPartMap().getAttributeContainer();
    }
    /**
     * 设置事物特性属性容器
     * @param attributecontainer 事物特性属性容器
     *@see AttributeContainer
     */
    public void setAttributeContainer(AttributeContainer attributeContainer)
    {
        getQMPartMap().setAttributeContainer(attributeContainer);
    }
    /**
     * 获取一个字符串标识。
     * @return String 形如："QMPart" + PartName + PartNumber;
   
     */
    public String getIBAID()
    {
        String partIden = "QMPart" + this.getPartName() + this.getPartNumber();
        return partIden;
    }
    /**
     * 获取选装代码
     * @return String 选装代码
     */
    public String getOptionCode()
    {
        return getQMPartMap().getOptionCode();
    }
    /**
     * 设置选装代码
     * @param code String
     */
    public void setOptionCode(String code)
    {
        getQMPartMap().setOptionCode(code);
    }


    /**
     * 获取唯一标识。
     * @return String 唯一标识。
     */
    public String getIdentity()
    {

        String identity = getPartNumber() + "(" + getPartName() + ")" +
                          getVersionValue();
        if (getViewName() != null && getViewName().length() > 0)
        {
            identity = identity + "(" + this.getViewName() + ")";
        }
        return identity;
    }


    /**
     * 设置属性容器的辅助类(Map类)对象。
     * @param map AffixMap
     */
    public void setAffixMap(AffixMap map)
    {
        theAffixMap = map;
    }


    /**
     * 获取属性容器的辅助类(Map类)对象。
     * @return AffixMap
     */
    public AffixMap getAffixMap()
    {
        if (theAffixMap == null)
        {
            theAffixMap = new AffixMap();
        }
        return theAffixMap;
    }


    /**
     * 获取属性容器的ID。
     * @return String
     */
    public String getAttrContID()
    {
        return getAffixMap().getAttrContID();
    }


    /**
     * 设置属性容器的ID
     * @param contID String
     */
    public void setAttrContID(String contID)
    {
        getAffixMap().setAttrContID(contID);
    }
    /**
     * 保留的扩展属性
     * @deprecated
     */
    public void setCompare(String s)
    {
        compare=s;
    }
    /**
     * 保留的扩展属性
     * @deprecated
     */
    public String getCompare()
    {
        return compare;
    }
    /**
     * 设置零部件类型。
     * @param type 类型枚举类
     * @see QMPartType
        　 */
    public void setPartType(String type)
    {
        getQMPartMap().setPartType(type);
    }
    /**
     * 设置默认单位。
     * @param unit Unit 单位的枚举类
     * @see Unit
     */
    public void setDefaultUnit(String unit)
    {
        ((QMPartMasterInfo) getMaster()).setDefaultUnit(unit);
    }
    /**
     * 设置零部件来源。
     * @param producedBy ProducedBy 来源枚举类
     * @see ProducedBy
     */
    public void setProducedBy(String producedBy)
    {
        getQMPartMap().setProducedBy(producedBy);
    }
  //CCBegin SS1
//  是否是被cad检出标识
    private boolean checkoutByCAD;
    /**
     * 获取是否是被cad检出标识
     * @param CHECKOUTBYCAD
     */
    public boolean getCheckoutByCAD()
    {
        return checkoutByCAD;
    }
    //CCEnd SS1
}
