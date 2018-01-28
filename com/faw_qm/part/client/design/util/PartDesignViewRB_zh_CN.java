/** ���ɳ���PartDesignViewRB_zh_CN.java	1.1  2003/02/28
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/07/06 ��� ԭ��:TD2543�ڲ�Ʒ��Ϣ�������в˵����պͽ����Ҳ������µĻ�ԭ��ť��ݼ������ظ�
 *                     ����:�Ҳ������µĻ�ԭ��ť��ݼ�����ΪAIT+Z
 */
package com.faw_qm.part.client.design.util;

import java.util.ListResourceBundle;


/**
 * <p>Title: ��Դ��Ϣ�⣨�������ģ�</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */
public class PartDesignViewRB_zh_CN extends ListResourceBundle
{
    public static final String SAVE_FAILURE = "0";
    public static final String NO_QUA_VERSION = "1";
    public static final String NO_NAME_ENTERED = "2";
    public static final String NO_NUMBER_ENTER = "3";
    public static final String NO_LOCAL_ENTER = "4";
    public static final String LOCAL_NOT_CAB = "5";
    public static final String LOCAL_NOT_VALID = "6";
    public static final String REQUIR_FIELD_MS = "7";
    public static final String SAVING = "8";
    public static final String SAVE_PART_FAIL = "9";
    public static final String QMPART_SOURCE = "10";
    public static final String QMPART_TYPE = "11";
    public static final String QMDM_BSONAME = "12";
    public static final String QMPM_BSONAME = "13";
    public static final String CONFIRM_DELCOPY = "14";
    public static final String INVALID_DB_DELET = "15";
    public static final String PART_CF_NESTING = "16";
    public static final String USE_CF_NESTING = "17";
    public static final String ADD_DOC_REPEAT = "18";
    public static final String ADD_DOC_FAILURE = "19";
    public static final String INVALID_MODE = "20";
    public static final String ADD_PART_REPEAT = "21";
    public static final String ADD_PART_FAIL = "22";
    public static final String DELETE_USE_FAIL = "23";
    public static final String UPDATE_USE_FAIL = "24";
    public static final String CONF_DELETE_OBJ = "25";
    public static final String NOT_CONF_ACTION = "26";
    public static final String CONF_DE_OBTITLE = "27";
    public static final String OBJ_NOT_QMPART = "28";
    public static final String OBJ_NOT_QMPM = "29";
    public static final String NO_IDE_ATTR = "30";
    public static final String NO_CHANGES_MADE = "31";
    public static final String RETRIEVE_CAB_ER = "32";
    public static final String NULL_ATTR_VALUE = "33";
    public static final String RENAME_FOLD_ER = "34";
    public static final String INIT_UP_FAIL = "35";
    public static final String INIT_CR_FAIL = "36";
    public static final String INIT_VIEW_FAIL = "37";
    public static final String INIT_URL_FAIL = "38";
    public static final String NO_SEARCH_URL = "39";
    public static final String CONF_DEL_FOLD = "40";
    public static final String CONF_DEL_VERS = "41";
    public static final String CONF_DEL_SC = "42";
    public static final String DELETE_FAILED = "43";
    public static final String INIT_HELP_FAIL = "44";
    public static final String UP_WORK_COPY = "45";
    public static final String LC_SUBMIT_FAIL = "46";
    public static final String LC_SUBMIT_SUC = "47";
    public static final String CHANGEFOLD_FAIL = "48";
    public static final String LC_HISTORY_FAIL = "49";
    public static final String AUGMENT_LC_FAIL = "50";
    public static final String REASSIGNLC_FAIL = "51";
    public static final String REA_PRO_FAIL = "52";
    public static final String EX_TO_OBJ_FAIL = "53";
    public static final String CRE_NOT_AVAIL = "54";
    public static final String UP_NOT_AVAIL = "55";
    public static final String VIEW_NOT_AVAIL = "56";
    public static final String UP_NOT_AUTHORIZ = "57";
    public static final String PROMPT_VIEW_OBJ = "58";
    public static final String OBJ_NOT_EXIST = "59";
    public static final String CONF_ER_OPER = "60";
    public static final String NAME_ENTERWRONG = "61";
    public static final String NUM_ENTERWRONG = "62";
    public static final String NUM_LENGTHWRONG = "63";
    public static final String NAME_LENGTHWRONG = "64";
//  add by muyp 081024
    public static final String PART_ALREADY_DISPLAYED = "65";
    public static final String PART_EQUAL_SELECTED = "66";
    public static String MESSAGE = "68";
	public static String NO_ATTR = "67";
//	add by muyp 081029
	public static String GET_ATTRIBUTECONTAINER_FAILED = "69";
	public static String REFRESH_ATTRIBUTECONTAINER_FAILED = "70";
	public static String IBACONSTRAINTEXCEPTION = "71";
	public static String STORE_VALUEINFO_FAILED = "72";
	public static String UPDATE_VALUEINFO_FAILED = "73";//end1029

    //needed
    public static final String MISS_RESOURCER = "181";
    public static final String SETM_NOT_FOUND = "182";
    public static final String SETM_FAIL = "183";
    public static final String GETM_NOT_FOUND = "184";
    public static final String GETM_FAIL = "185";
    public static final String EXATTR_PL_EMPTY = "186";
    public static final String ASSOPL_INIT_FAIL = "187";

    public static final String REFERENCE = "120";
    public static final String REFERENCE_CUR = "117";
    public static final String USAGE = "118";
    public static final String NOT_USAGE = "122";
    public static final String SPEC_IS_NULL = "A01";
    
    public Object getContents()[][]
    {
        return contents;
    }

    public PartDesignViewRB_zh_CN()
    {
        super();
    }

    static final Object[][] contents =
    {
        {"sourceLbl", "��Դ"}
        ,
        {"nameLbl", "����"}
        ,
        {"numberLbl", "���"}
        ,
        {"nameJLabel", "* ����"}
        ,
        {"numberJLabel", "* ���"}
        ,
        {"statusLbl", "״̬��"}
        ,
        {"typeLbl", "����"}
        ,
        {"locationLbl", "* ���ϼ�"}
        ,
        {"revisionLbl", "�汾"}
        ,
        {"iterationLbl", "Iteration:"}
        ,
        {"projectLbl", "������"}
        ,
        {"lifecycleLbl", "��������"}
        ,
        {"viewLbl", "��ͼ"}
        ,
        {"checkedOutLbl", "�㲿���������ҵĸ����ļ�����"}
        ,
        {"quantityJLabel", "����"}
        ,
        //CCBegin by liunan 2008-07-24
        {"unitJLabel", "ʹ�÷�ʽ"}
        //CCEnd by liunan 2008-07-24
        ,
        {"createUserJLabel", "������"}
        ,
        {"updateUserJLabel", "������"}
        ,
        {"createDateJLabel", "����ʱ��"}
        ,
        {"updateDateJLabel", "����ʱ��"}
        ,
        {"workableStateJLabel", "����״̬"}
        ,
        {"partAttrSetJButton","��ʾ����"}
        //end
        ,
        {"addUsageJButton", "���(A)"}
        ,
        {"removeUsageJButton", "��ȥ(R)"}
        ,
        {"addRefJButton", "���(A)"}
        ,
        {"removeRefJButton", "��ȥ(R)"}
        ,
        {"viewUsageJButton", "�鿴(V)"}
        ,
        {"versionJButton", "�汾��ʷ(H)"}
        ,
//      modify by muyp20080626 begin
        {"showattrsettedJButton","��ʾ����"}
        ,//end
        {"searchJButton", "����(S)"}
        ,
        {"browseJButton", "���. . ."}
        ,
        {"cancelJButton", "ȡ��(C)"}
        ,
        {"revertJButton", "��ԭ(Z)"}//CR1
        ,
        {"okJButton", "ȷ��(Y)"}
        ,
        {"saveJButton", "����(S)"}
        ,
        {"editAttributesJButton", "�༭����(I). . ."}
        ,
        {"numberHeading", "���"}
        ,
        {"nameHeading", "����"}
        ,
        {"quantityHeading", "����"}
        ,
        {"unitHeading", "��λ"}
        ,
        {"sourceHeading", "��Դ"}
        ,
        {"typeHeading", "����"}
        ,
        {"revisionHeading", "�汾"}
        ,
        {"statusHeading", "��������״̬��"}
        ,
        {"viewHeading", "��ͼ"}            
        ,
        {"Identity", "ID"}
        ,
        {"versionHeading", "�汾"}
        ,
        {"typeHeading", "����"}
        ,
        {"idHeading", "ID"}
        ,
        {"identityHeading", "Identity"}
        ,
        {"ellipses", ". . ."}
        ,
        {"required", "*"}
        ,
        {"informationTitle", "֪ͨ"}
        ,
        {"errorTitle", "����"}
        ,
        {"exception", "�쳣��Ϣ"}
        ,
        {"createPartTitle", "�����㲿��"}
        ,
        {"updatePartTitle", "�����㲿��"}
        ,
        {"viewPartTitle", "�鿴�㲿��"}
        ,
        {"findPartTitle", "�����㲿��"}
        ,
        {"findDocTitle", "�����ĵ�"}
        ,
        {"renamePartTitle", "�㲿��������"}
        ,
        {"saveAsPartTitle", "�㲿�����Ϊ"}
        ,
        {"extendAttri", "��չ����"}
        ,
        {"usage", "ʹ�ýṹ"}
        ,
        {"struct", "�ṹ"}
        ,
        {"describedBy", "������ϵ"}
        ,
        {"reference", "�ο���ϵ"}
        ,
        {"describedDoc", "�����ĵ�"}
        ,
        {"referenceDoc", "�ο��ĵ�"}
        ,
        //liyz add �������
        {"cappRegulation","���չ��"}
        ,
        {"cappSummary","���ջ���"}
        ,
        {"cappRoute","����·��"}//end
        ,
        {"attribute", "��������"}
        ,
        {"editAttributes", "��������"}
        ,
        {"0", "{0}����ʧ�ܡ�"}
        ,
        {"1", "δ��{0}�ҵ����ϵ�ǰɸѡ�����İ汾��"}
        ,
        {"2", "�㲿�������Ʋ�Ϊ�ա�"}
        ,
        {"3", "�㲿���ı�Ų�Ϊ�ա�"}
        ,
        {"4", "�㲿�������ϼ�λ�ò���Ϊ�ա�"}
        ,
        {"5", "��ָ�������ϼв������ĸ������ϼ�"}
        ,
        {"6", "���ϼ�·����Ч"}
        ,
        {"7", "ȱ�ٱ�����ֶ�"}
        ,
        {"8", "���ڱ���. . ."}
        ,
        {"9", "{0}����ʧ��"}
        ,
        {"10", "com.faw_qm.part.util.ProducedBy"}
        ,
        {"11", "com.faw_qm.part.util.QMPartType"}
        ,
        {"12", "DocMaster"}
        ,
        {"13", "QMPartMaster"}
        ,
        {"14", "*��*�Ĺ���������ɾ��*�����ܼ����������ʧ��*���������и���." + "ɾ��*��"}
        ,
        {"15", "{0}�ѱ��������ɾ������ɾ����֮ǰ��Ӧ�������"}
        ,
        {"16", "���{0}������ʹ�ýṹǶ�ף��������������"}
        ,
        {"17", "ʹ�ýṹǶ��"}
        ,
        {"18", "�Ѵ���{0}��"}
        ,
        {"19", "��ӹ����ĵ�ʧ��"}
        ,
        {"20", "��Чģʽ"}
        ,
        {"21", "�Ѵ���{0}��"}
        ,
        {"22", "���ʹ�ù���ʧ��"}
        ,
        {"23", "ɾ��ʹ�ù���ʧ��"}
        ,
        {"24", "����ʹ�ù���ʧ��"}
        ,
        {"25", "ɾ�����������ָܻ����Ƿ������"}
        ,
        {"26", "����ִ�е�ǰ��������Ϊû�з���������ʾȷ�϶Ի�����ϼ��Ӵ���"}
        ,
        {"27", "ȷ���Ƿ�ɾ��"}
        ,
        {"28", "Ϊ��������ǰ��������������ҵ����������QMPart����"}
        ,
        {"29", "Ϊ��������ǰ��������������ҵ����������QMPartMaster����"}
        ,
        {"30", "û�з���{0}�пɸ��ĵ����ԡ�"}
        ,
        {"31", "*��û�з����κθ��ġ�"}
        ,
        {"32", "�һ����ϼ�ʱ�������´���{0}"}
        ,
        {"33", "��Ϊ{0}�ṩһ��ֵ��"}
        ,
        {"34", "��ͼ�����������ϼ�ʱ�������´���{0}"}
        ,
        {"35", "��ͼΪ{0}��ʼ�����´���ʱ�������´���{1}"}
        ,
        {"36", "��ͼΪ{0}��ʼ����������ʱ�������´���{1}"}
        ,
        {"37", "��ͼΪ{0}��ʼ���鿴����ʱ�������´���{1}"}
        ,
        {"38", "��ʼ��������ҵURL����ʱ�������´���: {0} "}
        ,
        {"39", "���������Կ���û�з�������{0}��" + "�����Զ���������ҵʱ����URL�Ǳ���� ��"}
        ,
        {"40", "����ɾ�����ϼ�{0}������ͬʱɾ�������������ϼС��Ƿ�ɾ�������ϼ�?"}
        ,
        {"41", "��*������汾�е�����С�汾����ɾ�����Ƿ�ɾ����"}
        ,
        {"43", "ִ��ɾ��{0}����ʱ�������´���: {1}"}
        ,
        {"44", "ִ�л�����߰�����URL����ʱ�������´���{0}"}
        ,
        {"45", "��ǰ���Ѿ������{0}����������{1}���ϼ��и����乤��������"}
        ,
        {"46", "ִ���ύ{0}����ʱ�������´���{1}��"}
        ,
        {"47", "����{0}�ύ�ɹ���"}
        ,
        {"48", "�������ϼ�{0}ʱ���ִ���"}
        ,
        {"49", "��ʾ{0}������������ʷʱ�������´���{1}��"}
        ,
        {"50", "ִ������{0}��������������ʱ�������´���{1}��"}
        ,
        {"57", "����Ȩ����{0}��"}
        ,
        {"58", "��Ҫ�鿴{0}��?"}
        ,
        {"59", "Ϊ{0}ִ�и�������ʱ���ִ���" + "����Ҫ���µĶ��������ݿ����ѳ��ڲ����ڡ�"}
        ,
        {"60", "��ȷ�����Ƿ��Ѿ���������ȷ�Ĳ�������ϵͳ����Ա��ϵ��"}
        ,
        {"61", "����Ϊ�ջ��ߺ���ͨ���eg:%\\/:*?\"<>|"}
        ,
        {"62", "���Ϊ�ջ��ߺ���ͨ���eg:%\\/:*?\"<>|"}
        ,
        {"63", "��ų��ȹ���,���ܴ���30�ַ�!"}
        ,
        {"64", "���Ƴ��ȹ���,���ܴ���50�ַ�!"}
        ,
        {"181", "ȱ���������Դ"}
        ,
        {"182", "û���ҵ�����{0}��ֵ�ķ�����"}
        ,
        {"183", "����{0}��{1}����ֵʱ��������{2}��"}
        ,
        {"184", "û���ҵ����{0}��ֵ�ķ�����"}
        ,
        {"185", "���{0}��{1}����ֵʱ��������{2}��"}
        ,
        {"186", "��������Ϊ�գ��޷�������չ���ԡ�"}
        ,
        {"187", "��ʼ�������������ʧ�ܡ�"}
        ,
        {"workingCopy", "��������"}
        ,
        {"checkedOutBy", "�� * ���"}
        ,
        {"checkedIn", "����"}
        ,
        {"117", "* ���ڲο� *"}
        ,
        {"118", "* ʹ�� * �� *"}
        ,
        {"120", " * �ο� *"}
        ,
        {"122", " * ����ʹ�� * "}
        ,
        {"126", "���ܻ������������"}
        ,
        {"127", "���ܻ������Ϣ"}
        ,
        {"80", "û��ָ��ҵ�������չ���Ա���ʧ��!"}
        ,
        {"81", "��������Ϊ�գ��޷��������!"}
        ,
        {"82", "����"}
        ,
        {"83", "��ȷ��:"}
        ,
        {"84", "������Ϊ��"}
        ,
        {"85", " �ַ�����"}
        ,
        {"86", " Ϊtrue����false"}
        ,
        {"87", "����ֵӦΪ"}
        ,
        {"88", "����"}
        ,
        {"89", "�Բ���,�Ҳ������ʵİ汾!"}
        ,
        {"A01", "ConfigSpecItem(���ù淶)Ϊ��!"}
        ,
        {"part", " �㲿�� "}
        ,
        {"127", " ����Ϊ�� "}
        ,
        {"130", " ����*����QMPart "}
        ,
        {"162", "����"}
        ,
        {"163", "�û�* �� �㲿��* û�ж�Ȩ��."}
        ,
//      add by muyp 081029
        {"69","���Դ�㲿������������ʧ�ܡ�"}
        ,
        {"70","ˢ����������ʧ�ܡ�"}
        ,
        {"71","��������Լ��ʧ�ܣ�����Ӧ�����С�"}
        ,
        {"72","������߸����㲿��*��������������ʱʧ�ܡ�"}
        ,
        {"73","�����㲿��*��������������ʱʧ�ܡ�"}
        ,//end
        {"information", "��ʾ��Ϣ"}
        ,
        {"lost", "ȱ�ٱ�Ҫ�ֶ�"}
        ,
        {"initError", "�����ʼ������"}
        ,
        {"original", "Դ�㲿��"}
        ,
        {"target", "���㲿��"}
        ,
        {"notExist", "������."}
        ,
        {"errorWhenCopyLink", "����ʹ�ù�ϵʱ��������."}
        ,
        {"errorWhenCopyReferenceLink", "���Ʋο���ϵʱ��������."}
        ,
        {"errorWhenCopyDesLink", "����������ϵʱ��������."}
        ,
        {"errorWhenCopyAttr", "������չ����ʱ��������."}
        ,
        {"nameOrNum", "���ƺͱ�ŵĳ���Ӧ������200���ַ�����."}
        ,
        {"renamenum","�������µı��"}
        ,
        {"renamename","�������µ�����"}
        ,
        {"renameornumber","�������µı�Ż�����"}
        ,
        {"confirmsave","�Ƿ񱣴����� * �������޸ģ�"}
        //liyz add applyAll
        ,{  " "," "   }
	     ,
	     {	"viewName","��ͼ" }
	     ,
		 {	"defaultUnit","��λ" }
	     ,
		 {  "location","���ϼ�" }
	     ,
         {	"producedBy","��Դ" }
	     ,
 		 {	"partType","����" }
	     ,
 		 { "lifeCycleState","��������" }
	     ,
		 { "applyAll","Ӧ������"	 }
	     ,
		 { "ok","ȷ��" }
	     ,
		 { "cancel","ȡ��" }
	     ,
		 { "choiceAttr","ѡ�����������Ŀ" }
	     ,
	     {"IBAItem","ѡ������������Ŀ"}
	     ,
		 { "add","���" }
	     ,
		 { "remove","���" }
	     ,
	     { "idHeading", "ID" }
	     ,
	     { "numberHeading", "���" }
	     ,
	     { "nameHeading", "����" }
	     ,
         {"findPartTitle", "�����㲿��"}
	     ,
	     {"QMPM_BSONAME","QMPartMaster"}
	     ,
	     {"exception", "�쳣��Ϣ"}
	     ,
	     {"68","����"}
	     ,
	     {"67" ,"���㲿��û���������ԣ���"}
	     ,
	     {"BaseAttrApplyAll","��*�Ļ�������Ӧ����������ѡ�㲿��"}
	     ,
	     {"65","�㲿��*�Ѿ����б�����ʾ!"}
	     ,
	     {"66","�㲿��*��Դ�㲿����ͬ��������ӣ���"}
	     //liyz add AttrSet
	     ,
	     {"ok", "ȷ��"}
         ,
         {"cancel", "ȡ��"}            
         ,
         {"mayOutput", "��ѡ������"}
         ,
         {"deleteAll", "<<ȫ��ɾ��"}
         ,
         {"addAttribute", "���>"}
         ,
         {"addAll", "ȫ�����>>"}
         ,
         {"deleteAttri", "<ɾ��"}
         ,
         {"upMove", "����"}
         ,
         {"downMove", "����"}
         ,
         {"outPut", "��������"}               
         ,
         {"AttrSet","Tabҳ��ʾ���ԡ�������"}
         //liyz add changePanel
         ,
         {
			"issueTopic","���ⱨ��" 
		 },    		 
		 {
			 "source","��Դ"
		 },
         {
 			"category","���" 
 		 },
 		 {
 			"priority","���ȼ�"
 		 },
 		 {
 			 "lifeCycleState","״̬"
 		 },
 		{
 			"finishDate","���ʱ��"
 		 },
         {
 			"requestTopic","�������" 
 		 },
		 {
			 "needDate","Ҫ������"
		 },
         {
  			"category","���" 
  		 },
  		 {
  			"priority","���ȼ�"
  		 },
  		 {
  			 "lifeCycleState","״̬"
  		 },
		 {
 			"finishDate","���ʱ��"
 		 },
         {
  			"noticeTopic","���֪ͨ��" 
  		 },
 		 {
 			"lifeCycleState","״̬"
 		 },
          {
   			"description","˵��" 
   		 },
   		 {
   			"finishDate","���ʱ��"
   		 },
   		 {
  			"noticeTopic","���֪ͨ��" 
  		 },
 		 {
 			"lifeCycleState","״̬"
 		 },
          {
   			"description","˵��" 
   		 },
   		 {
   			"finishDate","���ʱ��"
   		 },
         {
  			"issue","���ⱨ����" 
  		 },
         {
   			"request","���������" 
   		 },
         {
   			"noticeBefore","�˰汾��Ҫ�����" 
   		 },
         {
    		"noticeAfter","�˰汾������" 
    	 }
	     ,
	     {
	    	 "look","�鿴"
	     }
	     ,
	     {
	    	 "move","�Ƴ�"
	     }
	     ,
	     {
	    	 "version","�汾��ʷ"
	     }
	     ,
	     {
	    	 "enterroute","���빤��·�߱������"
	     }
	     ,
	     {
	    	 "entersummary","���빤�ջ��������"
	     }
	     ,
	     {
	    	 "enterregulateionH","���빤�������(H)"
	     }
	     ,
	     {
	    	 "enterregulateion","���빤�������"
	     }
	     ,
	     {
	    	 "showset","��ʾ����"
	     }
	     ,
	     {
	    	 "nofirstcheckin","��ǰ�����ǵ�һ�μ�����㲿����\n�����㲿���ǵ�һ�μ��룬���β�����"
	     }
	     ,
	     {
	    	 "firstcheckin","��ǰ�����һ�μ�����㲿����\n�����㲿�����ǵ�һ�μ��룬���β�����"
	     }
	     ,
	     {
	    	 "notcheckout","�㲿��δ�������"
	     }
	     ,
	     {
	    	 "nocheckoutone","�㲿��*δ�������"
	     }
	     ,
	     {
	    	 "cannotbecheckout","�����㲿�������ϼ�������"
	     }
	     ,
	     {
	    	 "checkoutByOther","�㲿�������˼��"
	     }
	     ,
	     {"29", "��������ѡ�㲿���ļ���ᶪʧ���б������ȷʵҪ������"}
    };
}
