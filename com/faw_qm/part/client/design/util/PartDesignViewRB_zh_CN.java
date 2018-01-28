/** 生成程序PartDesignViewRB_zh_CN.java	1.1  2003/02/28
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/07/06 马辉 原因:TD2543在产品信息管理器中菜单工艺和界面右侧面板底下的还原按钮快捷键设置重复
 *                     方案:右侧面板底下的还原按钮快捷键设置为AIT+Z
 */
package com.faw_qm.part.client.design.util;

import java.util.ListResourceBundle;


/**
 * <p>Title: 资源信息库（简体中文）</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
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
        {"sourceLbl", "来源"}
        ,
        {"nameLbl", "名称"}
        ,
        {"numberLbl", "编号"}
        ,
        {"nameJLabel", "* 名称"}
        ,
        {"numberJLabel", "* 编号"}
        ,
        {"statusLbl", "状态："}
        ,
        {"typeLbl", "类型"}
        ,
        {"locationLbl", "* 资料夹"}
        ,
        {"revisionLbl", "版本"}
        ,
        {"iterationLbl", "Iteration:"}
        ,
        {"projectLbl", "工作组"}
        ,
        {"lifecycleLbl", "生命周期"}
        ,
        {"viewLbl", "视图"}
        ,
        {"checkedOutLbl", "零部件包含在我的个人文件夹中"}
        ,
        {"quantityJLabel", "数量"}
        ,
        //CCBegin by liunan 2008-07-24
        {"unitJLabel", "使用方式"}
        //CCEnd by liunan 2008-07-24
        ,
        {"createUserJLabel", "创建者"}
        ,
        {"updateUserJLabel", "更新者"}
        ,
        {"createDateJLabel", "创建时间"}
        ,
        {"updateDateJLabel", "更新时间"}
        ,
        {"workableStateJLabel", "工作状态"}
        ,
        {"partAttrSetJButton","显示设置"}
        //end
        ,
        {"addUsageJButton", "添加(A)"}
        ,
        {"removeUsageJButton", "移去(R)"}
        ,
        {"addRefJButton", "添加(A)"}
        ,
        {"removeRefJButton", "移去(R)"}
        ,
        {"viewUsageJButton", "查看(V)"}
        ,
        {"versionJButton", "版本历史(H)"}
        ,
//      modify by muyp20080626 begin
        {"showattrsettedJButton","显示设置"}
        ,//end
        {"searchJButton", "搜索(S)"}
        ,
        {"browseJButton", "浏览. . ."}
        ,
        {"cancelJButton", "取消(C)"}
        ,
        {"revertJButton", "还原(Z)"}//CR1
        ,
        {"okJButton", "确定(Y)"}
        ,
        {"saveJButton", "保存(S)"}
        ,
        {"editAttributesJButton", "编辑特性(I). . ."}
        ,
        {"numberHeading", "编号"}
        ,
        {"nameHeading", "名称"}
        ,
        {"quantityHeading", "数量"}
        ,
        {"unitHeading", "单位"}
        ,
        {"sourceHeading", "来源"}
        ,
        {"typeHeading", "类型"}
        ,
        {"revisionHeading", "版本"}
        ,
        {"statusHeading", "生命周期状态："}
        ,
        {"viewHeading", "视图"}            
        ,
        {"Identity", "ID"}
        ,
        {"versionHeading", "版本"}
        ,
        {"typeHeading", "类型"}
        ,
        {"idHeading", "ID"}
        ,
        {"identityHeading", "Identity"}
        ,
        {"ellipses", ". . ."}
        ,
        {"required", "*"}
        ,
        {"informationTitle", "通知"}
        ,
        {"errorTitle", "警告"}
        ,
        {"exception", "异常信息"}
        ,
        {"createPartTitle", "创建零部件"}
        ,
        {"updatePartTitle", "更新零部件"}
        ,
        {"viewPartTitle", "查看零部件"}
        ,
        {"findPartTitle", "搜索零部件"}
        ,
        {"findDocTitle", "搜索文档"}
        ,
        {"renamePartTitle", "零部件重命名"}
        ,
        {"saveAsPartTitle", "零部件另存为"}
        ,
        {"extendAttri", "扩展属性"}
        ,
        {"usage", "使用结构"}
        ,
        {"struct", "结构"}
        ,
        {"describedBy", "描述关系"}
        ,
        {"reference", "参考关系"}
        ,
        {"describedDoc", "描述文档"}
        ,
        {"referenceDoc", "参考文档"}
        ,
        //liyz add 工艺相关
        {"cappRegulation","工艺规程"}
        ,
        {"cappSummary","工艺汇总"}
        ,
        {"cappRoute","工艺路线"}//end
        ,
        {"attribute", "基本属性"}
        ,
        {"editAttributes", "事物特性"}
        ,
        {"0", "{0}保存失败。"}
        ,
        {"1", "未给{0}找到符合当前筛选条件的版本。"}
        ,
        {"2", "零部件的名称不为空。"}
        ,
        {"3", "零部件的编号不为空。"}
        ,
        {"4", "零部件的资料夹位置不能为空。"}
        ,
        {"5", "所指定的资料夹不是您的个人资料夹"}
        ,
        {"6", "资料夹路径无效"}
        ,
        {"7", "缺少必需的字段"}
        ,
        {"8", "正在保存. . ."}
        ,
        {"9", "{0}保存失败"}
        ,
        {"10", "com.faw_qm.part.util.ProducedBy"}
        ,
        {"11", "com.faw_qm.part.util.QMPartType"}
        ,
        {"12", "DocMaster"}
        ,
        {"13", "QMPartMaster"}
        ,
        {"14", "*是*的工作副本。删除*将不能检出，并将丢失对*所做的所有更改." + "删除*吗？"}
        ,
        {"15", "{0}已被检出不能删除。在删除它之前不应检出它。"}
        ,
        {"16", "添加{0}将导致使用结构嵌套，您不能添加它。"}
        ,
        {"17", "使用结构嵌套"}
        ,
        {"18", "已存在{0}。"}
        ,
        {"19", "添加关联文档失败"}
        ,
        {"20", "无效模式"}
        ,
        {"21", "已存在{0}。"}
        ,
        {"22", "添加使用关联失败"}
        ,
        {"23", "删除使用关联失败"}
        ,
        {"24", "更新使用关联失败"}
        ,
        {"25", "删除操作将不能恢复，是否继续？"}
        ,
        {"26", "不能执行当前操作，因为没有发现用以显示确认对话框的上级视窗。"}
        ,
        {"27", "确认是否删除"}
        ,
        {"28", "为了启动当前操作任务，所给的业务对象必须是QMPart对象。"}
        ,
        {"29", "为了启动当前操作任务，所给的业务对象必须是QMPartMaster对象。"}
        ,
        {"30", "没有发现{0}有可更改的属性。"}
        ,
        {"31", "*并没有发生任何更改。"}
        ,
        {"32", "找回资料夹时出现以下错误：{0}"}
        ,
        {"33", "请为{0}提供一个值。"}
        ,
        {"34", "企图重命名该资料夹时导致以下错误：{0}"}
        ,
        {"35", "试图为{0}初始化更新代理时发生以下错误：{1}"}
        ,
        {"36", "试图为{0}初始化创建代理时发生以下错误：{1}"}
        ,
        {"37", "试图为{0}初始化查看代理时发生以下错误：{1}"}
        ,
        {"38", "初始化搜索企业URL进程时发生以下错误: {0} "}
        ,
        {"39", "在您的属性库中没有发现属性{0}，" + "该属性对于搜索企业时创建URL是必需的 。"}
        ,
        {"40", "即将删除资料夹{0}，并将同时删掉其所有子资料夹。是否删除该资料夹?"}
        ,
        {"41", "在*的这个版本中的所有小版本将被删除。是否删除？"}
        ,
        {"43", "执行删除{0}操作时发生以下错误: {1}"}
        ,
        {"44", "执行获得在线帮助的URL操作时发生以下错误：{0}"}
        ,
        {"45", "当前您已经检出了{0}，请在您的{1}资料夹中更新其工作副本。"}
        ,
        {"46", "执行提交{0}操作时发生以下错误：{1}。"}
        ,
        {"47", "对象{0}提交成功！"}
        ,
        {"48", "更改资料夹{0}时出现错误。"}
        ,
        {"49", "显示{0}的生命周期历史时出现以下错误：{1}。"}
        ,
        {"50", "执行增加{0}的生命周期任务时出现以下错误：{1}。"}
        ,
        {"57", "您无权更新{0}。"}
        ,
        {"58", "您要查看{0}吗?"}
        ,
        {"59", "为{0}执行更新任务时出现错误。" + "您正要更新的对象在数据库中已长期不存在。"}
        ,
        {"60", "请确认您是否已经进行了正确的操作或与系统管理员联系！"}
        ,
        {"61", "名称为空或者含有通配符eg:%\\/:*?\"<>|"}
        ,
        {"62", "编号为空或者含有通配符eg:%\\/:*?\"<>|"}
        ,
        {"63", "编号长度过长,不能大于30字符!"}
        ,
        {"64", "名称长度过长,不能大于50字符!"}
        ,
        {"181", "缺乏必须的资源"}
        ,
        {"182", "没有找到设置{0}的值的方法。"}
        ,
        {"183", "设置{0}的{1}属性值时发生错误：{2}。"}
        ,
        {"184", "没有找到获得{0}的值的方法。"}
        ,
        {"185", "获得{0}的{1}属性值时发生错误：{2}。"}
        ,
        {"186", "属性容器为空，无法创建扩展属性。"}
        ,
        {"187", "初始化描述关联面板失败。"}
        ,
        {"workingCopy", "工作副本"}
        ,
        {"checkedOutBy", "被 * 检出"}
        ,
        {"checkedIn", "检入"}
        ,
        {"117", "* 现在参考 *"}
        ,
        {"118", "* 使用 * 的 *"}
        ,
        {"120", " * 参考 *"}
        ,
        {"122", " * 不再使用 * "}
        ,
        {"126", "不能获得属性描述器"}
        ,
        {"127", "不能获得类信息"}
        ,
        {"80", "没有指定业务对象，扩展属性保存失败!"}
        ,
        {"81", "属性容器为空，无法创建面板!"}
        ,
        {"82", "属性"}
        ,
        {"83", "请确认:"}
        ,
        {"84", "不允许为空"}
        ,
        {"85", " 字符长度"}
        ,
        {"86", " 为true或者false"}
        ,
        {"87", "属性值应为"}
        ,
        {"88", "类型"}
        ,
        {"89", "对不起,找不到合适的版本!"}
        ,
        {"A01", "ConfigSpecItem(配置规范)为空!"}
        ,
        {"part", " 零部件 "}
        ,
        {"127", " 对象为空 "}
        ,
        {"130", " 对象*不是QMPart "}
        ,
        {"162", "进度"}
        ,
        {"163", "用户* 对 零部件* 没有读权限."}
        ,
//      add by muyp 081029
        {"69","获得源零部件的属性容器失败。"}
        ,
        {"70","刷新属性容器失败。"}
        ,
        {"71","事物特性约束失败，不能应用所有。"}
        ,
        {"72","保存或者更新零部件*的事物特性容器时失败。"}
        ,
        {"73","更新零部件*的事物特性容器时失败。"}
        ,//end
        {"information", "提示信息"}
        ,
        {"lost", "缺少必要字段"}
        ,
        {"initError", "界面初始化错误"}
        ,
        {"original", "源零部件"}
        ,
        {"target", "新零部件"}
        ,
        {"notExist", "不存在."}
        ,
        {"errorWhenCopyLink", "复制使用关系时发生错误."}
        ,
        {"errorWhenCopyReferenceLink", "复制参考关系时发生错误."}
        ,
        {"errorWhenCopyDesLink", "复制描述关系时发生错误."}
        ,
        {"errorWhenCopyAttr", "复制扩展属性时发生错误."}
        ,
        {"nameOrNum", "名称和编号的长度应控制在200个字符以内."}
        ,
        {"renamenum","请输入新的编号"}
        ,
        {"renamename","请输入新的名称"}
        ,
        {"renameornumber","请输入新的编号或名称"}
        ,
        {"confirmsave","是否保存您对 * 所做的修改？"}
        //liyz add applyAll
        ,{  " "," "   }
	     ,
	     {	"viewName","视图" }
	     ,
		 {	"defaultUnit","单位" }
	     ,
		 {  "location","资料夹" }
	     ,
         {	"producedBy","来源" }
	     ,
 		 {	"partType","类型" }
	     ,
 		 { "lifeCycleState","生命周期" }
	     ,
		 { "applyAll","应用所有"	 }
	     ,
		 { "ok","确定" }
	     ,
		 { "cancel","取消" }
	     ,
		 { "choiceAttr","选择基本属性项目" }
	     ,
	     {"IBAItem","选择事物特性项目"}
	     ,
		 { "add","添加" }
	     ,
		 { "remove","清除" }
	     ,
	     { "idHeading", "ID" }
	     ,
	     { "numberHeading", "编号" }
	     ,
	     { "nameHeading", "名称" }
	     ,
         {"findPartTitle", "搜索零部件"}
	     ,
	     {"QMPM_BSONAME","QMPartMaster"}
	     ,
	     {"exception", "异常信息"}
	     ,
	     {"68","警告"}
	     ,
	     {"67" ,"该零部件没有事物特性！！"}
	     ,
	     {"BaseAttrApplyAll","将*的基本属性应用于下列所选零部件"}
	     ,
	     {"65","零部件*已经在列表中显示!"}
	     ,
	     {"66","零部件*与源零部件相同，不可添加！！"}
	     //liyz add AttrSet
	     ,
	     {"ok", "确定"}
         ,
         {"cancel", "取消"}            
         ,
         {"mayOutput", "可选择属性"}
         ,
         {"deleteAll", "<<全部删除"}
         ,
         {"addAttribute", "添加>"}
         ,
         {"addAll", "全部添加>>"}
         ,
         {"deleteAttri", "<删除"}
         ,
         {"upMove", "上移"}
         ,
         {"downMove", "下移"}
         ,
         {"outPut", "定制属性"}               
         ,
         {"AttrSet","Tab页显示属性——设置"}
         //liyz add changePanel
         ,
         {
			"issueTopic","问题报告" 
		 },    		 
		 {
			 "source","来源"
		 },
         {
 			"category","类别" 
 		 },
 		 {
 			"priority","优先级"
 		 },
 		 {
 			 "lifeCycleState","状态"
 		 },
 		{
 			"finishDate","解决时间"
 		 },
         {
 			"requestTopic","变更请求" 
 		 },
		 {
			 "needDate","要求日期"
		 },
         {
  			"category","类别" 
  		 },
  		 {
  			"priority","优先级"
  		 },
  		 {
  			 "lifeCycleState","状态"
  		 },
		 {
 			"finishDate","完成时间"
 		 },
         {
  			"noticeTopic","变更通知书" 
  		 },
 		 {
 			"lifeCycleState","状态"
 		 },
          {
   			"description","说明" 
   		 },
   		 {
   			"finishDate","完成时间"
   		 },
   		 {
  			"noticeTopic","变更通知书" 
  		 },
 		 {
 			"lifeCycleState","状态"
 		 },
          {
   			"description","说明" 
   		 },
   		 {
   			"finishDate","完成时间"
   		 },
         {
  			"issue","问题报告于" 
  		 },
         {
   			"request","变更请求于" 
   		 },
         {
   			"noticeBefore","此版本将要变更于" 
   		 },
         {
    		"noticeAfter","此版本产生于" 
    	 }
	     ,
	     {
	    	 "look","查看"
	     }
	     ,
	     {
	    	 "move","移除"
	     }
	     ,
	     {
	    	 "version","版本历史"
	     }
	     ,
	     {
	    	 "enterroute","进入工艺路线表管理器"
	     }
	     ,
	     {
	    	 "entersummary","进入工艺汇总浏览器"
	     }
	     ,
	     {
	    	 "enterregulateionH","进入工艺浏览器(H)"
	     }
	     ,
	     {
	    	 "enterregulateion","进入工艺浏览器"
	     }
	     ,
	     {
	    	 "showset","显示设置"
	     }
	     ,
	     {
	    	 "nofirstcheckin","当前处理不是第一次检入的零部件，\n下列零部件是第一次检入，本次不处理"
	     }
	     ,
	     {
	    	 "firstcheckin","当前处理第一次检入的零部件，\n下列零部件不是第一次检入，本次不处理"
	     }
	     ,
	     {
	    	 "notcheckout","零部件未被您检出"
	     }
	     ,
	     {
	    	 "nocheckoutone","零部件*未被您检出"
	     }
	     ,
	     {
	    	 "cannotbecheckout","下列零部件不符合检入条件"
	     }
	     ,
	     {
	    	 "checkoutByOther","零部件被他人检出"
	     }
	     ,
	     {"29", "撤消对所选零部件的检出会丢失所有变更，您确实要撤消吗？"}
    };
}
