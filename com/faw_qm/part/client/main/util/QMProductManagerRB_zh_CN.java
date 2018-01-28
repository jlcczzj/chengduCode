/** ����QMProductManagerRB_zh_CN.java	1.0  2003/01/05
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/10/30 ��� ԭ��ɾ���������С����ġ���ť
 *                     ������ɾ���������С����ġ���ť
 *                     ��ע��ɾ���������С����ġ���ť
 * CR2 2010/03/16 ��� �޸�ԭ��TD2897 �ɹ����룬����������������ʾ�Ի��򲻷��Ϲ淶  
 * CR3 2010/03/18 ��� �޸�ԭ��TD2939 �ڲ�Ʒ��Ϣ�������У����㲿���˵���Ӧ���и��¹��ܣ�
 *                     �޸ķ������˵��е�"����"ֻ�����û�м�����㲿����������ѽ����Ʒŵ�������  ��
 * CR4 20110413 wangl ԭ��TD4143��
 * CR5 2011/04/15 ��� �޸�ԭ��TD4032��Ϣ�������㲿���˵����в����ƶ�����������ݼ��ظ�
 *                     �޸ķ����������ƶ���ݼ�ΪM����������ݼ�ΪR  
 * SS1 2013-1-21  ��Ʒ��Ϣ����������������嵥�����������������erp���Ա�����
 * SS2 2013-1-21  ��Ʒ��Ϣ�������о��нṹ���ƹ��ܣ����Խ�ѡ���㲿���ṹ��ȫ���ƣ������սṹճ���������㲿����
 * SS3 2014-6-6   ���� ��Ź���BOM:��Ʒ��Ϣ�����������Ҽ����鿴����BOM�� 
 * SS4 �鿴BOM�汾���߼���������û���顢����ʾ����ɹ���Ӧ֧�ְ�����/�༶���� xianglx 2014-9-28
 * SS5 ���Ӷ�λ�����˵� pante 2015-01-08
*/
package com.faw_qm.part.client.main.util;

import java.util.ListResourceBundle;


/**
 * <p>Description:���ػ���Դ��Ϣ�ࡣ </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * @author �����
 * @version 1.0
 * CR1  2009/06/01 ����   ԭ�����ȱ��TD=2161��
 */
public class QMProductManagerRB_zh_CN extends ListResourceBundle
{
    public static final String ALREADY_CHECKOUT_BY_OTHER = "1";
    public static final String AIREADY_CHECKOUT = "2";
    public static final String CANNOT_DELETE_CHECKED_OUT = "3";
    public static final String CONFIRM_DELETE_OBJECT = "4";
    public static final String NOT_CHECKED_OUT = "5";
    public static final String RETRIEVE_CHECKOUT_FOLDER_FAILED = "6";
    public static final String CREATE_TASKDELEGATE_FAILED = "7";
    public static final String CONFIRM_TO_CHECKOUT = "8";
    public static final String WRANG_TYPE_OBJECT = "9";
    public static final String CAN_NOT_DEFINE_SUBSTITUTES = "10";
    public static final String UPDATE_FAILED = "11";
    public static final String OBJ_NOT_PART_OR_PARTMASTER = "12";
    public static final String NO_USAGE_RELATIONSHIP_FOUND = "13";
    public static final String NOT_ASSIGN_VIEW_VERSION = "14";
    public static final String RENAME_ERROR = "15";
    public static final String DELETE_OBJECT = "16";
    public static final String NO_QUALIFIED_VERSION = "17";
    public static final String PART_ALREADY_DISPLAYED = "18";
    public static final String USED_BY_OTHER = "19";
    public static final String NOT_IN_COMMON_FOLDER = "20";
    public static final String NOT_MOVE = "21";
    public static final String NOT_SELECT_OBJECT = "22";
    public static final String CANNOT_ENABLE_DEFINE_SUBSTITUTES = "23";
    public static final String CANNOT_ENABLE_DEFINE_ALTERNATES = "24";
    public static final String ABOUT_PRODMGR = "25";
    public static final String PRODMGR = "26";
    public static final String VERSION = "27";
    public static final String VERSION_RIGHT = "28";
    public static final String NO_SOLUTION = "29";
    public static final String NO_SUITABLE_VERSION = "30";

    public static final String MAIN_PAGE_TITLE = "100";
    public static final String RENAME_TITLE = "101";
    public static final String CHECKOUT_TITLE = "102";
    public static final String OK = "103";
    public static final String MESSAGE = "104";
    public static final String GENENAL_SEARCH = "105";
    public static final String BASE_ATTR_SEARCH = "106";
    public static final String EXTEND_ATTR_SEARCH = "107";
    public static final String PRODUCT_MANAGER = "108";
    public static final String SUCCESSFUL = "110";
    public static final String UNSUCCESSFUL = "111";
    public static final String ICONIMAGE = "112";
    public static final String NO_SELECT_GPART = "113";
    public static final String STANDCONFIG = "109";

    //////////////////////////////////begin//////////////////////////////////
    //add by liun�����״̬����ʾ��Ϣ2005-06-20��
    public static final String PART_MENU = "114";
    public static final String PART_CREATE = "115";
    public static final String PART_SAVEAS = "116";
    public static final String PART_UPDATE = "117";
    public static final String PART_COPY = "190";
    public static final String PART_CUT = "191";
    public static final String PART_SHAREMOVE = "192";
    public static final String PART_PASTE = "193";
    public static final String PART_RENAME = "118";
    public static final String PART_DELETE = "119";
    public static final String PART_CLEAR = "120";
    public static final String PART_REFRESH = "121";
    public static final String PART_EXIT = "122";
    public static final String VERSION_MENU = "123";
    public static final String VERSION_CHECKIN = "124";
    public static final String VERSION_CHECKOUT = "125";
    public static final String VERSION_UNDOCHECKOUT = "126";
    public static final String VERSION_CHANGEFILE = "127";
    public static final String VERSION_REVISE = "128";
    public static final String VERSION_VERSIONS = "129";
    public static final String VERSION_ITERATIONS = "130";
    public static final String MANAGE_MENU = "131";
    public static final String MANAGE_BASELINE = "132";
    public static final String MANAGE_BASELINE_CREATE = "133";
    public static final String MANAGE_BASELINE_UPDATE = "134";
    public static final String MANAGE_BASELINE_VIEW = "135";
    public static final String MANAGE_BASELINE_ADD = "136";
    public static final String MANAGE_BASELINE_POPULATE = "137";
    public static final String MANAGE_BASELINE_CLEAR = "138";
    public static final String MANAGE_EFFECTITVTY = "139";
    public static final String MANAGE_EFFECTITVTY_CREATE = "140";
    public static final String MANAGE_EFFECTITVTY_UPDATE = "141";
    public static final String MANAGE_EFFECTITVTY_VIEW = "142";
    public static final String MANAGE_EFFECTITVTY_ADD = "143";
    public static final String MANAGE_EFFECTITVTY_POPULATE = "144";
    public static final String MANAGE_EFFECTITVTY_CHANGEVALUE = "145";
    public static final String MANAGE_EFFECTITVTY_CLEAR = "146";
    public static final String MANAGE_ALTERNATES = "147";
    public static final String MANAGE_SUBSTITUTES = "148";
    public static final String MANAGE_PUBLISHVIEWVERSION = "149";
    public static final String MANAGE_PUBLISHIBA = "150";
    public static final String BROWSE_MENU = "151";
    public static final String BROWSE_CONFIGSPEC = "152";
    public static final String BROWSE_VIEW = "153";
    public static final String BROWSE_COMPARESTRUCTURE = "154";
    public static final String BROWSE_COMPAREIBA = "155";
    public static final String BROWSE_GENERALSEARCH = "156";
    public static final String BROWSE_BASEATTRSEARCH = "157";
    public static final String BROWSE_BOM = "158";
    public static final String BROWSE_BOM_MATERIALLIST = "159";
    public static final String BROWSE_BOM_BOMLIST = "160";
    public static final String BROWSE_BOM_TAILORBOM = "161";
    public static final String BROWSE_BOM_GPARTBOM = "162";
    public static final String BROWSE_SORT = "163";
    public static final String LIFECYCLE_MENU = "164";
    public static final String LIFECYCLE_RESET = "165";
    public static final String LIFECYCLE_SETSTATUS = "166";
    public static final String LIFECYCLE_SHOWHISTORY = "167";
    public static final String HELP_MENU = "168";
    public static final String HELP_PRODUCTMANAGER = "169";
    public static final String HELP_ABOUT = "170";

    public static final String ALTER_OR_SUB = "171";
    public static final String NOT_NEW_VIEW = "172";
    public static final String BROWSE_IBASEARCH = "173";
    public static final String CANNOT_CHECKOUT_USERFOLDER = "174";
    public static final String NOT_NEW_REVISE = "175";
    public static final String ATTR_NAME = "176";
    public static final String ATTR_ENG_NAME = "177";
    public static final String ALREADY_CYHECKOUT_OTHER_NOTRENAME="180";
    public static final String ALREADY_CHECKOUT_BY_OTHER_NOTCHECKIN="251";
    ///////////////////////////////////end///////////////////////////////////
    public static final String ALREADY_CHECKOUT_SELF="201";
    public static final String ALREADY_CYHECKOUT_OTHER="203";

    public static final String YEAR="204";
    public static final String CHOOSE_GENERIC_PART_WARNING="205";
    public static final String NOIBA="301";
    public static final String ALREADYPUBLISH="302";
    public static final String IBACOUNT="303";
    public static final String PUBLISHSURE="304";
    //liyz add ״̬��Ӧ�����й��ܵ���ʾ��Ϣ
    public static final String MANAGE_APPLYALLPART="305";
    public static final String WRANG_TYPE_SELECT_TAB="306";//end
    
//  add by muyppeng 20080625 begin
    public static final String BROWSE_APPLYTOALLPARTS = "178";
    public static final String APPLY_ATTRIBUTE_TO_OTHER = "179";
    //end
//  liyz add���ղ˵���
    public static final String CAPP_MENU ="185";
    public static final String TECHNICS_REGULATION ="186";
    public static final String TECHNICS_SUMMARY = "187";
    public static final String TECHNICS_ROUTE ="188";
    public static final String CYHECKOUTBCAD="189";
    public static final String GPART_ALREADY_CHECKOUT="312";//CR4
    
    
    
    //CCBegin SS2
    public static final String ERPATTR_NAME = "311";
    public static final String ERPATTR_ENG_NAME = "310";
    public static final String BROWSE_BOM_ERPLIST = "307";
    public static final String PART_ALL_COPY = "308";
    public static final String PART_All_PASTE = "309";
    //CCEnd SS2
    //CCBegin SS3
    public static final String VIEW_REALSE_BOM = "313";
    //CCEnd SS3
    //CCBegin SS4
    public static final String VIEW_REALSE_BOM_DJ = "314";
    //CCEnd SS4
  //CCBegin SS5
    public static final String DWSS = "315";
    //CCEnd SS5
    
    public Object getContents()[][]
    {
        return contents;
    }

    static final Object[][] contents =
            {
            {"part.label", "�㲿��(P)"}
            ,
            {"part.createPart.label", "����(N)..."}
            ,
            {"part.updatePart.label", "����(U)..."}
            ,
            {"part.copyPart.label", "����(C)"}
            ,//CCBegin SS2
            {"part.copyAllPart.label", "�ṹ����(J)"}
            ,//CCEnd SS2
            //CCBegin SS3
            {"VIEW_REALSE_BOM", "�鿴����BOM(�༶)"}
            ,//CCEnd SS3
            //CCBegin SS4
            {"VIEW_REALSE_BOM_DJ", "�鿴����BOM(����)"}
            ,//CCEnd SS4
          //CCBegin SS5
            {"DWSS", "��λ�������"} 
            ,//CCEnd SS5
            {"part.cutPart.label", "����(T)"}
            ,
            {"part.shareMovePart.label", "�����ƶ�(M)..."}
            ,
            {"part.pastePart.label", "ճ��(P)"}
            ,
            {"part.pasteAllPart.label", "�ṹճ��(K)"}
            ,
            {"part.saveAsPart.label", "���Ϊ(G)..."}
            ,
            {"part.renamePart.label", "������(R)"}//CR5
            ,
            {"part.deletePart.label", "ɾ��(D)"}
            ,
            {"part.clear.label", "�ڴ��������(A)"}
            ,
            {"part.refresh.label", "ˢ��(E)"}
            ,
            {"part.exit.label", "�˳�(X)"}
            ,
            {"version.label", "�汾(V)"}
            ,
            {"version.checkIn.label", "����(I)"}
            ,
            {"version.checkOut.label", "���(O)"}
            ,
            {"version.undoCheckOut.label", "�������(U)"}
            ,
            {"version.move.label", "�������ϼ�(M)"}
            ,
            {"version.revise.label", "�޶�(R)"}
            ,
            {"version.versionHistroy.label", "�汾��ʷ(B)"}
            ,
            {"version.iterationHistroy.label", "������ʷ(X)"}
            ,
            {"manage.label", "����(M)"}
            ,
            {"manage.baseline.label", "��׼��(B)"}
            ,
            {"manage.baseline.createBaseline.label", "������׼��(C)"}
            ,
            {"manage.baseline.maintenanceBaseline.label", "ά����׼��(E)"}
            ,
            {"manage.baseline.viewBaseline.label", "�鿴��׼��(V)"}
            ,
            {"manage.baseline.addBaseline.label", "���(A)"}
            ,
            {"manage.baseline.populateBaseline.label", "���ṹ���(P)"}
            ,
            {"manage.baseline.removeBaseline.label", "�Ƴ�(M)"}
            ,
            {"manage.effectivity.label", "��Ч��(E)"}
            ,
            {"manage.effectivity.createConfigItem.label", "��������(C)"}
            ,
            {"manage.effectivity.maintenanceConfigItem.label", "ά������(E)"}
            ,
            {"manage.effectivity.viewConfigItem.label", "�鿴����(V)"}
            ,
            {"manage.effectivity.addEffectivity.label", "���(A)"}
            ,
            {"manage.effectivity.populateEffectivity.label", "���ṹ���(P)"}
            ,
            {"manage.effectivity.updateEffValue.label", "�޸���Ч��ֵ(U)"}
            ,
            {"manage.effectivity.removeEffectivity.label", "�Ƴ�(M)"}
            ,
            {"manage.defineAlternates.label", "�滻��(A)"}
            ,
            {"manage.defineSubstitutes.label", "�ṹ�滻��(S)"}
            ,
            {"manage.publishViewVersion.label", "������ͼ�汾(P)"}
            ,
            {"manage.publishIBA.label", "��������(I)"}
            ,
            {"browse.label", "���(B)"}
            ,
            {"browse.setConfigSpec.label", "���ù淶(P)"}
            ,
            {"browse.viewPart.label", "�鿴(V)"}
            ,
            {"browse.compareStructure.label", "�ṹ�Ƚ�(C)"}
            ,
            {"browse.ibaCompare.label", "���ԱȽ�(B)"}
            ,
            {"browse.saveAsHistory.label", "���Ϊ��ʷ(H)"}
            ,
            {"browse.catalogHistory.label", "ע����ʷ(T)"}
            ,
            {"browse.generalSearch.label", "�����㲿��(S)"}
            ,
            {"browse.baseAttrSearch.label", "��������������(J)"}
            ,
            {"browse.ibaSearch.label", "��������������(I)"}
            ,
            {"browse.BOM.label", "�����嵥(M)"}
            ,
            //CCBegin SS1
            {"browse.BOM.erpList.label", "ERP(E)"}
            ,
            //CCEnd SS1
            //CCBegin SS1
            {ERPATTR_NAME, " ����� ������� �������� ��������״̬ ������λ ��Դ ����·�� װ��·�� T���� �̶���ǰ�ںϼ� �ɱ���ǰ��"}
            ,
             {ERPATTR_ENG_NAME, " partNumber-0,partName-0,quantity-0,lifeCycleState-0,defaultUnit-0,producedBy-0,����·��-1,װ��·��-1,T����-2,�̶���ǰ�ںϼ�-3,�ɱ���ǰ��-3,"}
            , //CCEnd SS1
            {"browse.BOM.materialList.label", "�ּ�(F)"}
            ,
            {"browse.BOM.bomList.label", "ͳ�Ʊ�(T)"}
            ,
            {"browse.BOM.tailorBOM.label", "����(D)"}
            ,
            {"browse.BOM.gPBomList.label", "���岿������(G)"}
            ,
            {"browse.publish.label", "����"}
            ,
            {"lifeCycle.label", "��������(L)"}
            ,
            {"lifeCycle.commit.label", "�ύ"}
            ,
            {"lifeCycle.lifeCycleHistroy.label", "����������ʷ"}
            ,
            {"lifeCycle.viewProcessTrack.label", "�鿴���̼�¼"}
            ,
            {"lifeCycle.viewActor.label", "�鿴������"}
            ,
            {"lifeCycle.resetLifeCycle.label", "����ָ����������(R)"}
            ,
            {"lifeCycle.setLifeCycleState.label", "������������״̬(S)"}
            ,
            {"lifeCycle.showLifeCycleHistory.label", "��ʾ����������ʷ(H)"}
            ,
            {"help.label", "����(H)"}
            ,
            {"help.helpItem.label", "����"}
            ,
            {"help.productManager.label", "��Ʒ��Ϣ������(P)"}
            ,
            {"help.about.label", "����(A)"}
            ,
            {"Title", "������Ҫ������"}
            ,
            {"warn", "����"}//CR1
            ,
            {"sort", "����(O)"}
            ,
            {MAIN_PAGE_TITLE, "��Ʒ��Ϣ������"}
            ,
            {RENAME_TITLE, "�������㲿��"}
            ,
            {CHECKOUT_TITLE, "�������"}
            ,
            {OK, "ȷ��(Y)"}
            ,
            {MESSAGE, "����"}
            ,
            {GENENAL_SEARCH, "�����㲿��"}
            ,
            {BASE_ATTR_SEARCH, "��������������"}
            ,
            {EXTEND_ATTR_SEARCH, "����չ��������"}
            ,
            {ABOUT_PRODMGR, "���ڲ�Ʒ��Ϣ������"}
            ,
            {PRODMGR, "��Ʒ��Ϣ������"}
            ,
            {VERSION, "�汾��1.0"}
            ,
            {VERSION_RIGHT, "��Ȩ���У�һ��������Ϣ������˾"}
            ,
            {PRODUCT_MANAGER, "��Ʒ��Ϣ���������߰���"}
            ,
			{"partexplorer.explorer.list.headings",
					"���,����,�汾,��ͼ,����,��λ,����,��Դ,��������״̬,ʹ�ù�ϵID"}
            ,
            {"partexplorer.explorer.tree.statusBarText", "��Ʒ�ṹ"}
            ,
            {"partexplorer.explorer.list.statusBarText", "�㲿��"}
            ,
            {"partexplorer.explorer.tree.rootNodeText", "��Ʒ�ṹ"}
            ,
			{
					"partexplorer.explorer.list.methods",
					"getNumber,getName,getIterationID,getViewName,getQuantityString, getUnitName,getType,getProducedBy,getState,getPartUsageLink"}
            ,
			{"partexplorer.explorer.list.columnAlignments",
					"Left,Left,Center,Center,Center,Center,Left,Left,Left,Left"}
            ,
			{"partexplorer.explorer.list.columnSizes",
					"40,40,40,40,40,40,40,40,40,0"}
            ,
			{"partexplorer.explorer.list.columnWidths", "4,4,4,4,4,4,6,6,10,0"}
            ,
            {"partexplorer.explorer.toolbar.icons",  "part_create,part_update,Spacer,public_copy,public_cut,public_paste,Spacer,part_delete,part_view,Spacer,part_checkIn,part_checkOut,part_repeal,Spacer,public_refresh,public_clear,Spacer,public_search,Spacer,part_applyAll"},

            {"partexplorer.explorer.toolbar.text",
            "����,����,Spacer,����,����,ճ��,Spacer,ɾ��,�鿴,Spacer,����,���,�������,Spacer,ˢ��,�ڴ��������,Spacer,�����㲿��,Spacer,Ӧ������"}
            ,
            {"partexplorer.explorer.toolbar.single",
            "�����������㲿�������ڡ�,�����������㲿�������ڣ������������޸�ѡ���㲿������ѡ���Ժ͹������������������νṹ�л��Ҳ���б���ѡ��һ���㲿�����и��¡����뽫�㲿��������ܽ��и��¡�,Spacer,������ѡ�㲿����,������ѡ�㲿����,ճ����ѡ�㲿����,Spacer,ɾ����ѡ�㲿�����������㲿��ʹ��ʱ�޷�ɾ��, �����㲿������ҳ������ʾѡ���㲿�������Ժ͹�����,Spacer, ������ѡ�㲿���ļ�����̡�,������ѡ�㲿���ļ�����̡�,ȡ����ѡ�㲿���ļ����,Spacer, �����ݿ��м�����ѡ�㲿������ˢ�²����滻��ǰ��ʾ�Ķ��������ѡ�㲿����������ͼ������չ���ģ����������۵�������,�������Ʒ��Ϣ�������� �е�ѡ�е��׸��㲿����,Spacer, �����㲿���Ļ�����¼���������㲿���İ汾��Ϣ��,Spacer,���������㲿����"}
            ,
            {ALREADY_CHECKOUT_BY_OTHER, "�����û�*����������޸�!"}
            ,
            {ALREADY_CHECKOUT_BY_OTHER_NOTCHECKIN, "�����û�*��������ܼ���!"}
            ,

            {CANNOT_CHECKOUT_USERFOLDER, "��ǰ�㲿���ڸ������ϼ��У����ܼ��!"}
            ,
            {AIREADY_CHECKOUT, "�㲿���Ѿ��������*���ϼ���!"}
            ,
            {CANNOT_DELETE_CHECKED_OUT, "�����Ѿ����û�*���������ɾ����"}
            ,
            {
            ALREADY_CHECKOUT_SELF, "�㲿���ѱ����!"
            }
            ,
            {
            CYHECKOUTBCAD, "�㲿������CAD���!"
            }
            ,
            {
            ALREADY_CYHECKOUT_OTHER_NOTRENAME, "���㲿���ѱ��û�*���,����������"
            }
            ,

            {
            ALREADY_CYHECKOUT_OTHER,"���㲿���ѱ��û�*���"
             }
            ,
            {CONFIRM_DELETE_OBJECT, "ȷ��Ҫɾ������ * ��?"}
            ,
            {NOT_CHECKED_OUT, "�ö���û�б����!"}
            ,
            {RETRIEVE_CHECKOUT_FOLDER_FAILED, "������������ϼ� * ʱ��������!"}
            ,
            {CREATE_TASKDELEGATE_FAILED, "�ڴ����������ʵ��ʱ��������"}
            ,
            {CONFIRM_TO_CHECKOUT, "���� * ��Ҫ��������޸�,����Ҫ�����?"}
            ,
            {WRANG_TYPE_OBJECT, "ѡ��Ķ������ʹ���!"}
            ,
            {CAN_NOT_DEFINE_SUBSTITUTES, "���������ܶ���ṹ�滻��!"}
            ,
            {UPDATE_FAILED, "����ʧ��!"}
            ,
            {OBJ_NOT_PART_OR_PARTMASTER, "��ѡ�������㲿�����㲿��������Ϣ!"}
            ,
            {NO_USAGE_RELATIONSHIP_FOUND, "û�з���ʹ�ù�ϵ!"}
            ,
            {NOT_ASSIGN_VIEW_VERSION, "��ѡ�����ܷ�����ͼ�汾!"}
            ,
            {RENAME_ERROR, "������ʱ��������!"}
            ,
            {DELETE_OBJECT, "ɾ������ȷ��"}
            ,
            {NO_QUALIFIED_VERSION, "�㲿��û�к��ʵİ汾!"}
            ,
            {PART_ALREADY_DISPLAYED, "�㲿��*�Ѿ��ڹ���������ʾ!"}
            ,
            {USED_BY_OTHER, "�㲿��*�Ѿ�����������ʹ�ã�����ɾ��!"}
            ,
            {NOT_IN_COMMON_FOLDER, "��ѡ�����ڹ������ϼ��У����ܱ��Ƶ��������ϼ���!"}
            ,
            {NOT_MOVE, "��ѡ�����Ѿ�����������ܱ��ƶ�"}
            ,
            {NOT_SELECT_OBJECT, "û��ѡ���������!"}
            ,
            {CANNOT_ENABLE_DEFINE_SUBSTITUTES, "���ܶ���ṹ�滻������ǰ�û�û�з���Ȩ�ޡ�"}
            ,
            {CANNOT_ENABLE_DEFINE_ALTERNATES, "���ܶ����滻������ǰ�û�û�з���Ȩ��!"}
            ,
            {NO_SOLUTION, "����Ч�Է���û���㲿�����������ܽ��д������!"}
            ,
            {NO_SUITABLE_VERSION, "����Ч�Է���û�з��ϵ�ǰ���ù���İ汾!"}
            ,
            {STANDCONFIG, "���ṹ�����Ч�Ա����ڱ�׼���ù淶�½��У�"}
            ,
            {SUCCESSFUL, "�����ɹ���"}
            ,
            {UNSUCCESSFUL, "����ʧ�ܣ�"}
            ,
            {NO_SELECT_GPART, "ѡ��Ķ������ʹ���,��ѡ��һ�����岿����"}
            ,
            {ICONIMAGE, "part"}
            ,
            ///////////////////////////״̬����ʾ��Ϣ///////////////////////////////
            {PART_MENU, "�˲˵����д��������Ϊ�����¡���������ɾ���������ˢ�¡��˳��⼸��ܡ�"}
            ,
            {PART_CREATE, "�����������㲿�������ڣ�����һ�����㲿����"}
            ,
            {PART_SAVEAS, "��ѡ���㲿������Ϊ�µ��㲿���������ô˷������ٴ���һ���µ��㲿����"}
            ,
            {PART_UPDATE, "�����������㲿�������ڣ������������޸�ѡ���㲿������ѡ���Ժ͹������������������νṹ�л��Ҳ���б���ѡ��һ���㲿�����и��¡����뽫�㲿��������ܽ��и��¡�"}
            ,
            {PART_COPY, "������ѡ�㲿����"}
            ,
            {PART_CUT, "������ѡ�㲿����"}
            ,
            {PART_SHAREMOVE, "�����ƶ���ѡ�㲿����"}
            ,
            {PART_PASTE, "ճ����ѡ�㲿����"}
            ,
            {PART_All_PASTE, "ճ����ѡ�㲿�����Ӽ���"}
            ,
            {PART_RENAME, "����ѡ���㲿���������������ƺͱ�ŵĸı佫Ӧ���ڸ��㲿�������а汾������������ѡ���İ汾��"}
            ,
            {PART_DELETE, "ɾ����ѡ�㲿���ĵ�ǰ�汾��"}
            ,
            {PART_CLEAR, "�������Ʒ��Ϣ���������е������㲿����"}
            ,
            {PART_REFRESH, "�����ݿ��м�����ѡ�㲿������ˢ���㲿���滻��ʾ�Ķ��������ѡ�㲿�������νṹ����չ���ģ����������۵�������"}
            ,
            {PART_EXIT, " �˳�����Ʒ��Ϣ�����������ڡ�"}
            ,
            {VERSION_MENU, "�˲˵����м��롢���������������������ϼС��޶����汾��ʷ��������ʷ�⼸��ܡ�"}
            ,
            {VERSION_CHECKIN, "������ѡ�㲿���ļ�����̡�"}
            ,
            {VERSION_CHECKOUT, " ������ѡ�㲿���ļ�����̡�"}
            ,
            {VERSION_UNDOCHECKOUT, " ȡ����ѡ�㲿���ļ����"}
            ,
            {VERSION_CHANGEFILE, "��ѡ���㲿���ӵ�ǰ���ϼ��ƶ�����һ�����ϼ��С�"}
            ,
            {VERSION_REVISE, " �������޶������ڣ������ڴ����㲿�����°汾��ָ����λ�á��������ں͹����顣"}
            ,
            {VERSION_VERSIONS, " ��ʾ�㲿�����а汾����Ϣ������ѡ��Ͳ鿴�ض��汾�����ҿ���ѡ��ͱȽϲ�ͬ�İ汾"}
            ,
            {VERSION_ITERATIONS, "��ʾ�㲿����ǰ����ǰ�������Ϣ���汾�����°�������䵱ǰ״�����Ӹô��ڣ�����ѡ��Ͳ鿴�ض��İ��򣬶��ҿ���ѡ��ͱȽϲ�ͬ�İ���"}
            ,
            {MANAGE_MENU, "�˲˵����л�׼�ߡ���Ч�ԡ��滻�����ṹ�滻����������ͼ�汾�����������⼸��ܡ�"}
            ,
            {MANAGE_BASELINE, "���ڻ�׼�߹���Ķ����˵���"}
            ,
            {MANAGE_BASELINE_CREATE, " ������������׼�ߡ����ڣ����Դ���һ���µĻ�׼�ߡ�"}
            ,
            {MANAGE_BASELINE_UPDATE, " ͨ���������һ����׼�ߣ����и��¡�ɾ����ά��������"}
            ,
            {MANAGE_BASELINE_VIEW, " ͨ���������һ����׼�ߣ��鿴��׼�ߵ����Ժͻ�׼���е������㲿����"}
            ,
            {MANAGE_BASELINE_ADD, "��ѡ�����㲿����ӵ�һ����׼���С�"}
            ,
            {MANAGE_BASELINE_POPULATE, "��ѡ���㲿�������ӽṹ�е������㲿���ĵ�ǰ��ʾ�汾��ӵ���׼���С�"}
            ,
            {MANAGE_BASELINE_CLEAR, "��ѡ���㲿���ӻ�׼����ɾ����"}
            ,
            {MANAGE_EFFECTITVTY, " ������Ч�Թ���Ķ����˵���"}
            ,
            {MANAGE_EFFECTITVTY_CREATE, " ������������Ч�Է��������ڣ�����һ����Ч�Է�����"}
            ,
            {MANAGE_EFFECTITVTY_UPDATE, "ͨ���������һ����Ч�Է��������и��¡�ɾ����ά��������"}
            ,
            {MANAGE_EFFECTITVTY_VIEW, "ͨ���������һ����Ч�Է������鿴��Ч�Է�������Ϣ��"}
            ,
            {MANAGE_EFFECTITVTY_ADD, "Ϊѡ���㲿�������Ч�ԡ�"}
            ,
            {MANAGE_EFFECTITVTY_POPULATE, " Ϊ��Ч�Է��������Ĳ�Ʒ�����е������㲿���ĵ�ǰ�汾���ͳһ����Ч�ԡ�"}
            ,
            {MANAGE_EFFECTITVTY_CHANGEVALUE, "���㲿������Ч��ֵ���䡣"}
            ,
            {MANAGE_EFFECTITVTY_CLEAR, " ����Ч��ֵ��ѡ�����㲿����ɾ����"}
            ,
            {MANAGE_ALTERNATES, "����ѡ���㲿�����滻����"}
            ,
            {MANAGE_SUBSTITUTES, " ����ѡ���㲿���ڵ�ǰ�ṹ�е��滻����"}
            ,
            {MANAGE_PUBLISHVIEWVERSION, " ������ͼ�д���ѡ���㲿�����°汾��Ҫ�����㲿��������ͼ�汾���㲿�����봴������ͼ�С�"}
            ,
            {MANAGE_PUBLISHIBA, "������Ѿ���װ�ˡ����ù����������֣����ѡ���ʾ�ڸò˵��С��ò˵�ѡ���ѡ�㲿���汾���������Ը��Ƶ������Ӧ���㲿������Ϣ����"}
            ,
            {BROWSE_MENU, "�˲˵��������ù淶���鿴���ṹ�Ƚϡ������㲿�������������������������嵥�������⼸��ܡ�"}
            ,
            {BROWSE_CONFIGSPEC, "���������ù淶�����ڡ�"}
            ,
            {BROWSE_VIEW, " �鿴ѡ���㲿����������Ϣ�� "}
            ,
            {BROWSE_COMPARESTRUCTURE, "�Ƚ�ѡ����Ʒ�ڵ�ǰɸѡ�����µĲ�Ʒ�ṹ����һ����Ʒ��ǰ��Ʒ����һ��ɸѡ�����µĽṹ���졣"}
            ,
            {BROWSE_COMPAREIBA, "�Ƚ�ѡ����Ʒ�ڵ�ǰɸѡ�����µĲ�Ʒ�ṹ����һ����Ʒ��ǰ��Ʒ����һ��ɸѡ�����µ�IBA���Բ��졣"}
            ,
            {BROWSE_GENERALSEARCH, " �����㲿���Ļ�����¼���������㲿���İ汾��Ϣ��"}
            ,
            {BROWSE_BASEATTRSEARCH, " �����㲿�������Խ��������������㲿�����ض��汾��"}
            ,
            {BROWSE_IBASEARCH, " �����㲿�����������Խ��������������㲿�����ض��汾��"}
            ,
            {BROWSE_BOM, "���������嵥�Ķ����˵���"}
            ,
            {BROWSE_BOM_MATERIALLIST, " ����ѡ���㲿���ķּ������嵥��"}
            ,
             //CCBegin SS1
            {BROWSE_BOM_ERPLIST, " ����ѡ���㲿����ERP�����嵥��"}
            ,
            //CCEnd SS1
            {BROWSE_BOM_BOMLIST, " ����ѡ���㲿���Ľṹ�������㲿����ͳ�Ʊ�"}
            ,
            {BROWSE_BOM_TAILORBOM, " ����������������嵥�Ĵ��ڡ�"}
            ,
            {BROWSE_BOM_GPARTBOM, " ��������������岿�������嵥�Ĵ��ڡ�"}
            ,
            {BROWSE_SORT, "���ݲ�ͬ�Ĳ�����϶Բ�Ʒ�ṹ���ϵ��㲿����������"}
            ,
            {LIFECYCLE_MENU, "�˲˵���������ָ���������ڡ�������������״̬����ʾ����������ʷ�⼸��ܡ�"}
            ,
            {LIFECYCLE_RESET, "��һ������ʹ������ָ��һ����ͬ���������ڡ�"}
            ,
            {LIFECYCLE_SETSTATUS, " ��һ������ʹ�����������������ڵĶ�������Ե�״̬��"}
            ,
            {LIFECYCLE_SHOWHISTORY, " �鿴ѡ���㲿��������������ʷ��"}
            ,
            {HELP_MENU, "�˲˵����в�Ʒ��Ϣ�������͹���������ܡ�"}
            ,
            {HELP_PRODUCTMANAGER, " �����йز�Ʒ��Ϣ��������������������Ϣ��"}
            ,
            {HELP_ABOUT, " �ṩ�йز�Ʒ��Ϣ�������İ汾�Ͱ�Ȩ��Ϣ�� "}
            ,
            //////////////////////////////////////////////////////////
            {NOT_NEW_VIEW, " �㲿��*�������°汾�����ܷ�����ͼ�� "}
            ,
            {NOT_NEW_REVISE, " �㲿��*�������°汾�������޶��� "}
            ,
            {ALTER_OR_SUB, " ���㲿����һ���滻����ṹ�滻��������ɾ���� "}
            ,
            {ATTR_NAME, " ��� ���� ���� �汾 ��ͼ"}
            ,
            //CCBegin by liunan 2008-08-06
            {ATTR_ENG_NAME, " partNumber-0,partName-0,quantity-0,versionValue-0,viewName-0,"}
            //CCEnd by liunan 2008-08-06
            ,
             //CCBegin SS2
            {PART_ALL_COPY, "������ѡ�㲿�����Ӽ���"}
            //CCEnd SS2
            ,
            {"ProductionManager", "��Ʒ��Ϣ������"}
            ,
            {"version", "��        ����  * "}
            ,
            {"right1", "��Ȩ���У�  ������Ϣ�����ɷ����޹�˾"}
            ,
            {"right2", "( C )  2001-* "}
            ,
            {"204", "( C )  2001-* "}
            ,
            {"about", "���ڲ�Ʒ��Ϣ������"}
            ,
            {"net", "��        ַ��"}
            ,
            {"working", "����ִ�в���"}
            ,
            {"noCatalog", "���㲿�������ɹ��岿��ע������!"}
            ,
            {"prompt", "��ʾ"}
            ,
            {"canNotRevise","�������޶����㲿����Ҫ�޶����㲿������Ҫ������\"�޶�\"Ȩ�ޡ�"}
            ,
            {"canNotReviseView","�����ܷ�����ͼ����Ҫ�Ը��㲿����\"�޶���ͼ�汾\"Ȩ�޲��ܷ�����ͼ��"}
             //added by whj for v3 new request
            ,
            {"update","����"}
            ,
            {"copy", "����"}
            ,
            {"paste","ճ��"}
            ,
            {"cut","����"}
            ,
            {"bmove","�����ƶ�"}
            ,
            {"move","�ƶ�"}
            ,
            {"delete","ɾ��"}
            ,
            {"view","�鿴"}
            ,
            {"checkin","����"}
            ,
            {"checkin*","����*"}//CR2
            ,
            {"checkout","���"}
            ,
            {"checkout*","���*"}//CR2
            ,
            {"undocheckout","�������"}
            ,
            {"undocheckout*","�������*"}//CR2
            ,
            {"movefromwindow","�ڴ��������"}
            ,
            {"refresh","ˢ��"}
            ,
            {"jiegou","ʹ�ýṹ"}
            ,
            {"jibenshuxing","��������"}
            ,
            {"shiwutexing","��������"}
            ,
            {"cankaowendang","�ο��ĵ�"}
            ,
            {"miaoshuwendang","�����ĵ�"}
            ,
            {"notselectpart","û��ѡ���ƻ���е��㲿��"}
            ,
            {"worn","���棡"}
            ,
            {"noupdate","Ŀ������ǿɸ���״̬������ִ�в���"}
            ,
            {"parentnoupdate","��ѡ����ĸ������ǿɸ���״̬������ִ�в���"}
            ,
            {"notbmove","��ѡ�����ܽ��в����ƶ�"}
            ,
            {"moveNumber","�ƶ�����"}
            ,
            {"queOk","ȷ��"}
            ,
            {"queCanc","ȡ��"}
            ,
            {"canbemovednumber","��ǰ���ƶ�����"}
            ,
            {"canbeover","�ƶ��������ܳ�����"}
            ,
            {"useUnit","ʹ�õ�λ"}
            ,
            {"allMove","ȫ���ƶ�"}
            ,
            {"needNumber","�ƶ���������������"}
            ,
            {"cannotbeback","�ƶ���������Ϊ�գ�"}
            ,
            {"canbezero","�ƶ������������㣡"}
            ,
            {"canbenegative","�ƶ����������Ǹ�����"}
            ,
            {"confrimdrag", "�Ƿ��㲿�� * ���㲿�� * ���Ƴ�,������ӵ��㲿�� * ��?"}
            ,
            {"onlyaddconfrimdrag", "�Ƿ��㲿�� * ��ӵ��㲿�� * ��?"}
            ,
            {"dragdialogtitle", "�Ƿ��ƶ�"}
            ,
            {"nestingwaringtext", "��� * ������ʹ�ýṹǶ�ף��������������"}
            ,
            {"nestingwaringtitle", "ʹ�ýṹǶ��"}
            
            ,
            {
            CHOOSE_GENERIC_PART_WARNING,"��ѡ����㲿���ǹ��岿���������ڲ�Ʒ��Ϣ����������¡�"
            }
            ,
            {
            NOIBA,"��ѡ����㲿��û���κ��������Կ��Է�����"
            }
            ,
            {
            ALREADYPUBLISH,"��ѡ����㲿�������������Ѿ��������ˡ�"
            }
            ,
            {
            IBACOUNT,"��ѡ����㲿���İ�����*���������ԣ��Ƿ�ȷ�Ϸ�����"
            }
            ,
            {
            PUBLISHSURE,"ȷ�Ϸ���"
            }
            //liyz add ״̬��Ӧ�����й��ܵ���ʾ��Ϣ
            ,
            {
             MANAGE_APPLYALLPART,"�ṩ���û�����ά���㲿�������������������Ե�һ�����ܡ�"
            }
            ,{"manage.applyAllPart.label", "Ӧ������(M)"}
            ,
            {
            	WRANG_TYPE_SELECT_TAB,"��ѡ��������Ի���������"
            }
            ,
            //add by muyppeng 20080625 begin
            {BROWSE_APPLYTOALLPARTS,"����ѡ����㲿��������Ӧ�õ�Ŀ���㲿����"}
            ,
            {APPLY_ATTRIBUTE_TO_OTHER,"���㲿��*����������Ӧ�õ������㲿��"}
            ,
//          add by muyppeng 20080625 begin 
            {"browse.applyToAllParts.label","Ӧ������(A)"}
            //end
            ,//liyz add���ղ˵���
            {CAPP_MENU, "�˲˵����й��������������·�߱�����������ջ����������"}
            ,
            {TECHNICS_REGULATION, "���빤�������"}
            ,
            {TECHNICS_SUMMARY, "���빤�ջ��������"}
            ,
            {TECHNICS_ROUTE, "���빤��·�߱������"}
            ,
            {"capp.label", "����(C)"}
            ,
            {"capp.setTechnicsRegulation.label", "���������(T)"}
            ,
            {"capp.setTechnicsRoute.label", "����·�߱������(R)"}
            ,
            {"capp.setTechnicsSummary.label", "���ջ��������(S)"}
//          liyz add ���������������ť
            ,
            {"partexplorer.explorer.toolbar.iconSort","part_create,part_update,Spacer,public_copy,public_cut,public_paste,Spacer,part_delete,part_view,Spacer,part_checkIn,part_checkOut,part_repeal,Spacer,public_refresh,public_clear,Spacer,public_search,Spacer,public_sort,Spacer,part_applyAll"}//CR1��CR3   
            ,
            {"partexplorer.explorer.toolbar.textSort","����,����,Spacer,����,����,ճ��,Spacer,ɾ��,�鿴,Spacer,����,���,�������,Spacer,ˢ��,�ڴ��������,Spacer,�����㲿��,Spacer,����,Spacer,Ӧ������"}//CR1��CR3   
            ,
            {"partexplorer.explorer.toolbar.singleSort","�����������㲿�������ڡ�,�����㲿��,Spacer,������ѡ�㲿����,������ѡ�㲿����,ճ����ѡ�㲿����,Spacer,ɾ����ѡ�㲿�����������㲿��ʹ��ʱ�޷�ɾ��, �����㲿������ҳ������ʾѡ���㲿�������Ժ͹�����,Spacer, ������ѡ�㲿���ļ�����̡�,������ѡ�㲿���ļ�����̡�,ȡ����ѡ�㲿���ļ����,Spacer, �����ݿ��м�����ѡ�㲿������ˢ�²����滻��ǰ��ʾ�Ķ��������ѡ�㲿����������ͼ������չ���ģ����������۵�������,�������Ʒ��Ϣ�������� �е�ѡ�е��׸��㲿����,Spacer, �����㲿���Ļ�����¼���������㲿���İ汾��Ϣ��,Spacer,����ѡ���Ӽ������������û��ѡ������Ը��ڵ��µ��㲿����������,Spacer,���������㲿����"}//CR1 ��CR3 
            ,
            {GPART_ALREADY_CHECKOUT,"�ù��岿���ѱ������"}//CR4
            
             //CCBegin by liunan 2008-07-30
            , 
            {"browse.BOM.compareList.label", "��ͼ�ṹ�Ƚ�"}
            , 
            {"browse.BOM.firstLevelSonList.label", "һ���Ӽ��б�"}
            //CCEnd by liunan 2008-07-30
    };
}
