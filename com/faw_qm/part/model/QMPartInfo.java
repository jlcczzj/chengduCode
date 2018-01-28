/** ���ɳ��� QMPartInfo.java    1.0    2003/02/17
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 * SS1 ��ӹ���·��ʹ�õķ���  guoxiaoliang 2013-02-04
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
 * �㲿��ֵ����ʵ���ࡣ
 * @author ���ȳ�
 * @version 1.0
 */

public class QMPartInfo extends RevisionControlledInfo implements QMPartIfc
{
    private QMPartMap theQMPartMap;
    private ViewManageMap theViewManageMap;
    private Vector effVector;
    private AffixMap theAffixMap;
    protected FormatContentHolderMap holderMap = null;

    public static final String StandardIcon = "StandardIcon"; //��׼ͼ��
    public static final String CheckOutIcon = "CheckOutIcon"; //����ԭ��ͼ��
    public static final String WorkingIcon = "WorkingIcon"; //��������ͼ��
    public static HashMap iconCache = new HashMap();
    static final long serialVersionUID = -1L;
    private String  compare="";

    /**
     * ���캯���������µ��㲿��ֵ����
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
            //�����˸���RevisionControlledInfo�еķ���
            initialize();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new QMException(e);
        }
    }


    /**
     * ���캯������������������
     * @param s :String �㲿���š�
     * @param s1 :String �㲿������
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
     * ��ȡ�㲿�����������
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
     * �����㲿�����������
     * @param map QMPartMap
     */
    public void setQMPartMap(QMPartMap map)
    {
        theQMPartMap = map;
    }


    /**
     * ����㲿������
     * @return String �㲿������
     */
    public String getPartName()
    {
        return ((QMPartMasterInfo) getMaster()).getPartName();
    }


    /**
     * �����㲿������
     * @param name String �㲿������
     */
    public void setPartName(String name)
    {
        ((QMPartMasterInfo) getMaster()).setPartName(name);
    }


    /**
     * ����㲿���š�
     * @return String �㲿�����
     */
    public String getPartNumber()
    {
        return ((QMPartMasterInfo) getMaster()).getPartNumber();
    }


    /**
     * �����㲿���š�
     * @param number String �㲿�����
     */
    public void setPartNumber(String number)
    {
        ((QMPartMasterInfo) getMaster()).setPartNumber(number);
    }


    /**
     * ���Ĭ�ϵ�λ��
     * @return Unit ��λ��ö����
     * @see Unit
     */
    public Unit getDefaultUnit()
    {
        return ((QMPartMasterInfo) getMaster()).getDefaultUnit();
    }


    /**
     * ����Ĭ�ϵ�λ��
     * @param unit Unit ��λ��ö����
     * @see Unit
     */
    public void setDefaultUnit(Unit unit)
    {
        ((QMPartMasterInfo) getMaster()).setDefaultUnit(unit);
    }


    /**
     * ��øö����������Ч��ֵ�ļ��ϣ�������targetΪ��EffManagedVersionIfc���������
     * EffGroup���󣩡��÷���ֻ��ʵ��ҵ���߼���
     * @return Vector EffGroup���󼯺�
     */
    public Vector getEffVector()
    {
        return effVector;
    }


    /**
     * ������Ч��ֵ���ϡ�
     * @param vector VectorEffGroup���󼯺�
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
     * ����㲿����Դ��
     * @return ProducedBy ��Դö����
     * @see ProducedBy
     */
    public ProducedBy getProducedBy()
    {
        return getQMPartMap().getProducedBy();
    }


    /**
     * �����㲿����Դ��
     * @param producedBy ProducedBy ��Դö����
     * @see ProducedBy
     */
    public void setProducedBy(ProducedBy producedBy)
    {
        getQMPartMap().setProducedBy(producedBy);
    }


    /**
     * ����㲿�����͡�
     * @return QMPartType ����ö����
     * @see QMPartType
     */
    public QMPartType getPartType()
    {
        return getQMPartMap().getPartType();
    }


    /**
     * �����㲿�����͡�
     * @param type ����ö����
     * @see QMPartType
        �� */
    public void setPartType(QMPartType type)
    {
        getQMPartMap().setPartType(type);
    }


    /**
     * ��ȡҵ���������
     * @return "QMPart"
     */
    public String getBsoName()
    {
        return "QMPart";
    }


    /**
     * ��ȡ��ͼ�����������
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
     * ������ͼ�����������
     * @param map ViewManageMap
     */
    public void setViewManageMap(ViewManageMap map)
    {
        theViewManageMap = map;
    }


    /**
     * ��ȡ��ͼ���ơ�
     * @return String viewName ��ͼ����
     */
    public String getViewName()
    {
        return getViewManageMap().getViewName();
    }


    /**
     * ������ͼ���ơ�
     * @param viewName String ��ͼ����
     */
    public void setViewName(String viewName)
    {
        getViewManageMap().setViewName(viewName);
    }


    /**
     * ���ù��������ơ�
     * @param projectName String ����������
     */
    public void setProjectName(String projectName)
    {

        //���ʱ����е������ˣ�
        //���ʱ��Ӧ�������ñ��㲿����projectId���ԣ�
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "setProjectName(String) begin ....");
        //����projectName , �ҵ�ֵ����->bsoID, ����bsoID.(setProjectID����)
        if (projectName == null)
        {
            PartDebug.trace(this, PartDebug.PART_SERVICE,
                            "setProjectName(String) end....return is null ");
            return;
        }
        Collection collection = null;
        //����Ƿ���˵��õĻ���
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
        //����ǿͻ��˵��õĻ���
        else
        {
            try
            {
                QMQuery query = new QMQuery("Project");
                QueryCondition condition = new QueryCondition("name", "=",
                        projectName);
                query.addCondition(condition);
                //������Ҫ���տͻ��˵�˼·���е��ã�
                //�������͵ļ���
                Class[] paraClass =
                                    {QMQuery.class};
                //����ֵ�ļ���
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
            //"û�в��ҵ�(���߲��ҵ��˶��)��Ӧ����Ŀ�飬��ȷ��������Ŀ�����Ƿ���ȷ"
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
     * ���ù��������ơ�
     * @param projectName String ����������
     */
    public void setProjectDisplayName(String projectName)
    {
        theQMPartMap.setProjectName(projectName);
    }


    /**
     * ��ȡ���������ơ�
     * @return ProjectName ����������
     */
    public String getProjectName()
    {
        return theQMPartMap.getProjectName();
    }


    /**
     * ��ȡ��ʽ���������������������
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
     * ���ø�ʽ���������������������
     * @param map0 FormatContentHolderMap
     */
    public void setFormatContentHolderMap(FormatContentHolderMap map0)
    {
        holderMap = map0;
    }


    /**
     * �������ݸ�ʽID��
     * @param format0 String
     */
    public void setDataFormatID(String format0)
    {
        getFormatContentHolderMap().setDataFormatID(format0);
    }


    /**
     * ��ȡ���ݸ�ʽID��
     * @return String
     */
    public String getDataFormatID()
    {
        return getFormatContentHolderMap().getDataFormatID();
    }


    /**
     * ��ȡ���ݸ�ʽ����
     * @return String
     */
    public String getDataFormatName()
    {
        return getFormatContentHolderMap().getDataFormatName();
    }


    /**
     * �������ݸ�ʽ����
     * @param name0 String
     */
    public void setDataFormatName(String name0)
    {
        getFormatContentHolderMap().setDataFormatName(name0);
    }


    /**
     * ��ȡ�㲿����׼ͼ�����ơ�
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
     * �����Ƿ�����ɫ�����ʶ��
     * @param flag ��ɫ�����ʶ��
     */
    public void setColorFlag(boolean flag)
    {
        this.getQMPartMap().setColorFlag(flag);
    }


    /**
     * ��ȡ�Ƿ�����ɫ�����ʶ��
     * @return ��ɫ�����ʶ��
     */
    public boolean getColorFlag()
    {
        return getQMPartMap().getColorFlag();
    }


    /**
     * ���ͼ��·����
     *
     * @return String ·����
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
    * ��ȡ�㲿��ͼ���URI
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
     * ���ͼ�ꡣ
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
     * ���ݾ���������·�����ͼ�꣬���Ϊ��ǰ���������
     * ����õ�ͼ��������/images/�¡�·��Ϊ"/images/..."����ʽ�� ע��·��������׺��
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
	 * ��ȡ����������������
	 * @return AttributeContainer ����������������
	 *@see AttributeContainer
	 */
    public AttributeContainer getAttributeContainer()
    {
        return getQMPartMap().getAttributeContainer();
    }
    /**
     * ��������������������
     * @param attributecontainer ����������������
     *@see AttributeContainer
     */
    public void setAttributeContainer(AttributeContainer attributeContainer)
    {
        getQMPartMap().setAttributeContainer(attributeContainer);
    }
    /**
     * ��ȡһ���ַ�����ʶ��
     * @return String ���磺"QMPart" + PartName + PartNumber;
   
     */
    public String getIBAID()
    {
        String partIden = "QMPart" + this.getPartName() + this.getPartNumber();
        return partIden;
    }
    /**
     * ��ȡѡװ����
     * @return String ѡװ����
     */
    public String getOptionCode()
    {
        return getQMPartMap().getOptionCode();
    }
    /**
     * ����ѡװ����
     * @param code String
     */
    public void setOptionCode(String code)
    {
        getQMPartMap().setOptionCode(code);
    }


    /**
     * ��ȡΨһ��ʶ��
     * @return String Ψһ��ʶ��
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
     * �������������ĸ�����(Map��)����
     * @param map AffixMap
     */
    public void setAffixMap(AffixMap map)
    {
        theAffixMap = map;
    }


    /**
     * ��ȡ���������ĸ�����(Map��)����
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
     * ��ȡ����������ID��
     * @return String
     */
    public String getAttrContID()
    {
        return getAffixMap().getAttrContID();
    }


    /**
     * ��������������ID
     * @param contID String
     */
    public void setAttrContID(String contID)
    {
        getAffixMap().setAttrContID(contID);
    }
    /**
     * ��������չ����
     * @deprecated
     */
    public void setCompare(String s)
    {
        compare=s;
    }
    /**
     * ��������չ����
     * @deprecated
     */
    public String getCompare()
    {
        return compare;
    }
    /**
     * �����㲿�����͡�
     * @param type ����ö����
     * @see QMPartType
        �� */
    public void setPartType(String type)
    {
        getQMPartMap().setPartType(type);
    }
    /**
     * ����Ĭ�ϵ�λ��
     * @param unit Unit ��λ��ö����
     * @see Unit
     */
    public void setDefaultUnit(String unit)
    {
        ((QMPartMasterInfo) getMaster()).setDefaultUnit(unit);
    }
    /**
     * �����㲿����Դ��
     * @param producedBy ProducedBy ��Դö����
     * @see ProducedBy
     */
    public void setProducedBy(String producedBy)
    {
        getQMPartMap().setProducedBy(producedBy);
    }
  //CCBegin SS1
//  �Ƿ��Ǳ�cad�����ʶ
    private boolean checkoutByCAD;
    /**
     * ��ȡ�Ƿ��Ǳ�cad�����ʶ
     * @param CHECKOUTBYCAD
     */
    public boolean getCheckoutByCAD()
    {
        return checkoutByCAD;
    }
    //CCEnd SS1
}
