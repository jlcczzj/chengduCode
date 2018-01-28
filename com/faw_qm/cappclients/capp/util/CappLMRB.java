/** 生成程序CappLMRB.java	1.1  2003/08/08
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 *
 * CR1 2009/04/28  刘学宇   原因：工步无重新生成工步号的功能
 *                          方案：添加了用于重新生成工步号的资源数据。
 *                          备注：变更记录标识为CRSS-007
 * CR2 2009/04/29  刘志城   原因：工艺规程编辑界面添加移除按钮
 *                          方案：工艺规程从工艺树中擦去不显示但不删除。
 *                          备注：变更记录标识为"CRSS-012"   
 * CR3 2009/04/05 刘志城    原因：优化撤销检出逻辑，减少查询数据库次数。
 *                         方案：去掉判断是否被别的关联，优化撤销检出代码逻辑。
 *                         备注：性能测试用例名称：“工艺撤销检出”。                          
 */
package com.faw_qm.cappclients.capp.util;



/**
 * <p>Title:工艺规程模块资源文件 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明 薛静
 *问题(1)2008.02.20 刘志城修改 修改原因:高级搜索按创建日期搜时输入非规定日期格式仍继续搜索
 *                     应改为:停止搜索,弹出提示信息,请用户重新输入正确日期格式时添加属性.
 * @version 1.0
 */

public class CappLMRB extends java.util.ListResourceBundle
{
    public static final String INVALID_MODE = "1";
    public static final String NO_CHANGE_MADE = "2";
    public static final String NO_TECHNICS_NUMBER_ENTERED = "3";
    public static final String NO_TECHNICS_NAME_ENTERED = "4";
    public static final String REQUIRED_FIELDS_MISSING = "5";
    public static final String NO_LOCATION_ENTERED = "6";
    public static final String LOCATION_NOT_VALID = "7";
    public static final String LOCATION_NOT_PERSONAL_CABINET = "8";
    public static final String SAVING = "9";
    public static final String IS_SAVE_QMTECHNICS = "10";
    public static final String IS_SAVE_QMTECHNICS_UPDATE = "11";
    public static final String LOCATION_IS_PERSONAL_CABINET = "12";
    public static final String NO_STEP_NUMBER_ENTERED = "13";
    public static final String NO_STEP_NAME_ENTERED = "14";
    public static final String IS_SAVE_QMPROCEDURE = "15";
    public static final String IS_SAVE_QMPROCEDURE_UPDATE = "16";
    public static final String NO_PACE_NUMBER_ENTERED = "17";
    public static final String IS_SAVE_QMPROCEDURE_PACE = "18";
    public static final String IS_SAVE_QMPROCEDURE_PACE_UPDATE = "19";
    public static final String WRONG_TYPE_OBJECT = "20";
    public static final String CONFIRM_DELETE_OBJECT = "21";
    public static final String COPY_CONTENT_ERROR = "22";
    public static final String CONFIRM_APPOINT_FOLDER = "23";
    public static final String CHECK_IN_FAILURE = "24";
    public static final String NOT_FIRST_CHECK_IN = "25";
    public static final String NOT_SELECT_OBJECT = "26";
    public static final String ALREADY_BY_OTHERS_CHECK_OUT = "27";
    public static final String ALREADY_BY_OWN_CHECK_OUT = "28";
    public static final String INIT_RESOURCE_ERROR = "29";
    public static final String VERIFY_UNDO_CHECKOUT = "30";
    public static final String ALREADY_CHECKOUT = "31";
    public static final String RETRIEVE_CHECKOUT_FOLDER_FAILED = "32";
    public static final String ALREADY_CHECKOUT_BY_OTHER = "33";
    public static final String CHECK_OUT_FAILURE = "34";
    public static final String NOT_PERMITED_REVISE = "35";
    public static final String VERIFY_CHECKIN_NOT_OWNER = "36";
    public static final String NULL_CHECK_OUT_FOLDER = "37";
    public static final String NOT_FOUND_FOLDER = "38";
    public static final String CANNOT_DELETE_CHECKED_OUT = "39";
    public static final String PLEASE_CONFIRM_CHECK_OUT = "40";
    public static final String EQUIPMENT_OR_TOOL_NOT_ONLYONE = "41";
    public static final String CAN_NOT_CREATE_PACE = "42";
    public static final String COMMIT_QMTECHNICS_SUCCESS = "43";
    public static final String COMFIRM_UNITE_TECHNICS = "44";
    public static final String NOT_SAME_TECHNICE_TYPE = "45";
    public static final String SAVE_STEP_FAILURE = "46";
    public static final String SYSTEM_ERROR = "47";
    public static final String NOT_CHECKOUT_OWNER_DISPLAY = "48";
    public static final String VERIFY_UNDO_CHECKOUT_NOT_OWNER = "49";
    public static final String NOT_FOUND_EXTEND_CONTENT = "50";
    public static final String MISSING_RESOURCER = "51";
    public static final String PLEASE_CHECK_IN_FIRST = "52";
    public static final String CONFIRM_CREATE_STEP = "53";
    public static final String CONFIRM_CREATE_PACE = "54";
    public static final String CAN_NOT_COMMITED = "55";
    public static final String HOURS_TYPE_INVALID = "56";
    public static final String IS_CREATE_TOOL = "57";
    public static final String MUST_IS_INTEGER = "58";
    public static final String STEP_NUMBER_INVALID = "59";
    public static final String ADD_TECHNICS_REPEAT = "60";
    public static final String NO_CHECK_IN_QMPROCEDURE = "61";
    public static final String NO_TECHNICS_CONTENT_ENTERED = "62";
    public static final String CAN_NOT_CREATE_QMPROCEDURE = "63";
    public static final String NOT_CHECKOUT_PASTOBJECT = "64";
    public static final String CAN_NOT_CHECKOUT = "65";
    public static final String FIRST_CHECKOUT_TECHNICS = "66";
    public static final String RELATIONTECHNICS_CANTBE_THISTECHNICS = "67";
    public static final String NOU_CHECK_OUT_BY_YOU = "68";
    public static final String TECHNICS_TYPE_NOT_SAME = "69";
    public static final String CANNOT_CHANGE_LOCATION_CHECK_OUT_OBJECT = "70";
    public static final String CANNOT_CHANGE_LOCATION_INVAULT_OBJECT = "71";
    public static final String IS_CREATE_EQUIPMENT = "72";
    public static final String IS_CREATE_MATERIAL = "73";
    public static final String CANNOT_MODIFY_ORIGINAL_OBJECT = "74";
    public static final String CANNOT_COLLECT_CHECKED_OUT_OBJECT = "75";
    public static final String CANNOT_COPY_ORIGINAL_OBJECT = "76";
    public static final String NO_PERMISSION = "77";
    public static final String NOT_SELECT_SPECIAL_CHARACTER = "78";
    public static final String NOT_TECHNICSTYPE_ENTERED = "79";
    public static final String NO_STYPETYPE_ENTERED = "80";
    public static final String NO_PROCESSTYPE_ENTERED = "81";
    public static final String NO_STEPCLASSIFI_ENTERED = "82";
    public static final String NO_WORKSHOP_ENTERED = "83";
    public static final String NO_DRAWINGEXPORT_ENTERED = "84";
    public static final String TECHNICSTYPE = "85";
    public static final String STEPTYPE = "86";
    public static final String STEPCLASSIFI = "87";
    public static final String PROCESSTYPE = "88";
    public static final String WORKSHOP = "89";
    public static final String DRAWINGEXPORT = "90";
    public static final String STEPNUMBER_TOO_LONG = "91";
    public static final String PACENUMBER_TOO_LONG = "92";
    public static final String TECHNICSNUMBER_TOO_LONG = "93";
    public static final String TECHNICSNAME_TOO_LONG = "94";
    public static final String STEPNAME_TOO_LONG = "95";
    public static final String STEP_NAME_INVALID = "96";
    public static final String TECHNICS_NUMBER_INVALID = "97";
    public static final String TECHNICS_NAME_INVALID = "98";
    public static final String REMARK_INVALID = "99";
    public static final String REMARK_TOOLONG = "100";
    public static final String MATERIAL_NUMBER_INVALID = "101";
    public static final String MATERIAL_NUMBER_TOOLONG = "102";
    public static final String USECOUNT_INVALID = "103";
    public static final String USECOUNT_TOOLONG = "104";
    public static final String MATERIAL_USECOUNT_TOOLONG = "105";
    public static final String CANNOT_DELETE_FOR_RELATIONTECHNICS = "110";
    public static final String THIS_PART_USE_NO_PUSHMTECHNICS = "111";
    //Begin CR3
    public static final String RELATIONTECHNICS_CANTBE_CHECK_OUT_OBJECT = "121";
    //End CR3
    public CappLMRB()
    {
    }

    public Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][] =
            {
            //JButtons
            {"browsePdfJButton", "浏览PDF文件(P)"}
            ,
            {"createPdfJButton", "生成PDF文件(O)"}
            ,
            {"browseDxfJButton", "浏览DXF文件(B)"}
            ,
            {"createDxfJButton", "确定(y)"}
            ,
            {"OkJButton", "确定"}
            ,
            {"CancelJButton", "取消"}
            ,
            {"SaveJButton", "保存"}
            ,
            {"QuitJButton", "退出"}
            ,
            {"ParaJButton", "附加信息"}
            ,
            {"addJButton", "添加"}
            ,
            {"editJButton", "编辑(E). . ."}
            ,
            {"deleteJButton", "删除"}
            ,
            {"searchTreeJButton", "搜索"}
            ,
            {"upJButton", "上移"}
            ,
            {"downJButton", "下移"}
            ,
            {"browseJButton", "浏览"}
            ,
            {"storageJButton", "入库"}
            ,
            //JLabels
            {"checking in", "正在检入"}
            ,
            {"describe", "注释"}
            ,
            {"aqlJLabel", "合格质量水平数值（AQL）"}
            ,
            {"inspectNumberJLabel", "抽检批量（n）"}
            ,
            {"inspectTimeJLabel", "抽检频次"}
            ,
            {"eligibleNumberJLabel", "合格判定数（c）"}
            ,
            {"procedureNumberJLabel", "加工工序号"}
            ,
            {"reviseTechnics", "修订工艺"}
            ,
            {"explain", "说明"}
            ,
            {"technicsNumberJLabel", "*工艺编号"}
            ,
            //wangh 20070131
            {"stepNumberJLabel2", "*编号"}
            ,

            {"technicsNameJLabel", "*工艺名称"}
            ,
            {"technicsTypeJLabel", "*工艺种类"}
            ,
            {"remarkJLabel", "备注"}
            ,
            {"paceNumberJLabel", "*工步号"}
            ,
            {"drawFormatJLabel", "*简图输出"}
            ,
            {"technicsContentJLabel", "工艺内容"}
            ,
            {"mtechContentJLabel", "*工艺内容"}
            ,
            {"drawingJLabel", "工艺简图"}
            ,
            {"leftJLabel", "工艺树及资源分类树"}
            ,
            {"rightJLabel", "工艺内容"}
            ,
            {"stepNumberJLabel", "*工序号"}
            ,
            {"relationTechnicsJLabel", "关联工艺"}
            ,
            {"stepNameJLabel", "*工序名称"}
            ,
            {"stepTypeJLabel", "*工序种类"}
            ,
            {"stepClassifiJLabel", "*工序类别"}
            ,
            {"processTypeJLabel", "*加工类型"}
            ,
            {"workshopJLabel", "*部门"}
            ,
            {"drawingExportJLabel", "*简图输出"}
            ,
            {"drawingJLabel", "工艺简图"}
            ,
            {"specharTypeJLabel", "特殊符号类型"}
            ,
            {"currentSpecharJLabel", "当前符号"}
            ,
            //Titles
            {"exception", "异常信息"}
            ,
            {"information", "提示"}
            ,
            {"error", "错误"}
            ,
            {"renameTechnicsTitle", "重命名工艺"}
            ,
            {"checkInTitle", "检入"}
            ,
            {"notice", "通知"}
            ,
            {"afreshAssignLifeCycle", "重新指定生命周期"}
            ,
          //2008.03.09 徐德政改，将“项目组”改为“工作组”
            {"afreshAssignProject", "重新指定工作组"}
            //end mario
            ,
            {"technicsParam", "工艺参数"}
            ,
            {"technicsInformationTitle", "工艺信息维护"}
            ,
            {"maintainToolEquipmentTitle", "维护工装与设备关联"}
            ,
            {"reviseTitle", "修订工艺版本"}
            ,
            {"describeDocTitle", "文档"}
            ,
            {"partTitle", "零部件"}
            ,
            {"materialTitle", "材料"}
            ,
            {"equipmentTitle", "设备"}
            ,
            {"toolTitle", "工装"}
            ,
            {"docTitle", "文档"}
            ,
            {"technicsMasterTitle", "工艺主信息"}
            ,
            {"extendAttriTitle", "扩展属性"}
            ,
            {"aboutTechnicsTitle", "关于工艺规程管理器"}
            ,
            {"selectTemplateTitle", "模板选择"}
            ,
            {"technicsReguMainTitle", "工艺规程管理器"}
            ,
            {"technicsTreeTitle", "工艺树"}
            ,
            {"termTreeTitle", "工艺术语"}
            ,
            {"drawingTreeTitle", "简图"}
            ,
            {"selectObjectTitle", "选择对象"}
            ,
            {"editSpecharTitle", "编辑特殊符号"}
            ,
            {"productStructure", "产品结构"}
            ,
            //Menus
            {"jMenuFile", "文件"}
            ,
            {"jMenuFileExit", "退出"}
            ,
            {"jMenuHelp", "帮助"}
            ,
            {"jMenuHelpAbout", "关于"}
            ,
            {"jMenuFileCollect", "合并"}
            ,
            {"jMenuFileCollect1", "合并(O)"}
            ,
            {"jMenuFileImport", "导入"}
            ,
            {"jMenuFileExport", "导出"}
            ,
            {"jMenuExportAll", "导出全部工艺(P)"}
            ,
            {"jMenuFileSearch", "搜索"}
            ,
            {"jMenuFileRefresh", "刷新"}
            ,
            {"jMenuFileCreate", "新建"}
            ,
            {"jMenuCreateTechnics", "工艺主信息"}
            ,
            {"jMenuCreateStep", "工序"}
            ,
            {"jMenuCreatePace", "工步..."}
            ,
            {"jMenuSelect", "选中"}
            ,
            {"jMenuSelectView", "查看"}
            ,
            {"jMenuSelectUpdate", "更新"}
            ,
            {"jMenuSelectFormStepNum", "重新生成工序号"}
            //CR1 begin
            ,
            {"jMenuSelectFormPaceNum", "重新生成工步号"}
            //CR1 end
            ,
            {"jMenuSelectBrowse", "打印预览"}
            ,
            {"jMenuSelectChangeLocation", "更改资料夹"}
            ,
            {"jMenuSelectRename", "重命名"}
            ,
            {"jMenuSelectSaveAs", "另存为"}
            ,
            {"jMenuSelectUseTech", "应用典型工艺"}
            ,
            {"jMenuSelectUseStep", "应用典型工序"}
            ,
            {"jMenuSelectMadeTech", "生成典型工艺"}
            ,
            {"jMenuSelectMadeStep", "生成典型工序"}
            ,
            {"jMenuSelectCopy", "复制"}
            ,
            {"jMenuSelectPaste", "粘贴"}
            ,
            {"jMenuVersion", "版本"}
            ,
            {"jMenuVersionCheckIn", "检入"}
            ,
            {"jMenuVersionCheckOut", "检出"}
            ,
            {"jMenuVersionCancel", "撤销检出"}
            ,
            {"jMenuVersionRevise", "修订"}
            ,
            {"jMenuVersionVersion", "查看版本历史"}
            ,
            {"jMenuVersionIteration", "查看版序历史"}
            ,
            {"jMenuLifeCycle", "生命周期"}
            ,
            {"jMenuSetLifeCycleState", "重新指定生命周期状态"}
            ,
            {"jMenuLifeCycleSelect", "重新指定生命周期"}
            ,
            {"jMenuLifeCycleView", "查看生命周期历史"}
            ,
          //2008.03.09 徐德政改，将“项目组”改为“工作组”
            {"jMenuLifeCycleGroup", "重新指定工作组"}
            //end mario
            ,
            {"jMenuIntellect", "智能工艺"}
            ,
            {"jMenuIntellectPart", "零件族工艺派生"}
            ,
            {"jMenuIntellectRoot", "加工路线工艺派生"}
            ,
            {"jMenuSearch", "搜索"}
            ,
            {"jMenuSearchEquip", "按设备搜索工艺"}
            ,
            {"jMenuSearchTool", "按工装搜索工艺"}
            ,
            {"jMenuSearchMaterial", "按材料搜索工艺"}
            ,
            {"jMenuSearchTechnics", "搜索工艺规程"}
            ,
            {"jMenuHelpManage", "工艺规程管理"}
            ,
            {"jMenuSelectDelete", "删除"}
            ,
            //Begin CR2
            {"jMenuSelectMoveOut", "移除(K)"}
            //End CR2
            ,
            {"jMenuAddObject", "添加选中对象"}
            ,
            {"jMenuConfigCrit", "配置规范"}
            ,
            {"jMenuHelpItem", "帮助"}  
            ,
            //other
            {"SECOND", "秒"}
            ,
            {"MINUTE", "分"}
            ,
            {"HOUR", "小时"}
            ,
            {"partNumber", "零部件编号"}
            ,
            {"partName", "零部件名称"}
            ,
            {"partVersion", "版本"}
            ,
            {"mainPart", "主要零件"}
            ,
            {"mainTechnics", "主要工艺"}
            ,
            {"docNumber", "文挡编号"}
            ,
            {"docName", "文挡名称"}
            ,
            {"docVersion", "版本"}
            ,
            {"materialNumber", "材料编号"}
            ,
            {"materialName", "材料名称"}
            ,
            {"roughtType", "毛坯种类"}
            ,
            {"mRation", "材料消耗定额（Kg）"}
            ,
            {"mmMark", "主要"}
            ,
            {"usageCount", "使用数量"}
            ,
            {"unit", "计量单位"}
            ,
            {"extendContent", "扩展内容"}
            ,
            {"controlPlan", "控制计划"}
            ,
            {"productCharactor", "产品特性"}
            ,
            {"productCriterion", "产品规范"}
            ,
            {"processCharactor", "过程特性"}
            ,
            {"processCriterion", "过程规范"}
            ,
            {"charactorType", "特性分类"}
            ,
            {"evaluate", "评价测量"}
            ,
            {"samplingCapacity", "取样容量"}
            ,
            {"samplingFrequency", "取样频率"}
            ,
            {"controlMethod", "控制方法"}
            ,
            {"reactionPlan", "反应计划"}
            ,
            {"equipmentNumber", "设备编号"}
            ,
            {"equipmentName", "设备名称"}
            ,
            {"equipmentType", "设备型号"}
            ,
            {"planeNumber", "平面图号"}
            ,
            {"necessary", "必要"}
            ,
            {"toolNumber", "工装编号"}
            ,
            {"toolName", "工装名称"}
            ,
            {"selectedEquipment", "选中设备"}
            ,
            {"selectedTool", "选中工装"}
            ,
            {"mainID", "主要标识"}
            ,
            {"select", "选中"}
            ,
            {"templateType", "模板类型"}
            ,
            {"technicsNumber", "工艺编号"}
            ,
            {"technicsName", "工艺名称"}
            ,
            {"version", "版本"}
            ,
            {"technicsMaster", "工艺主信息"}
            ,
            {"procedureStep", "工序"}
            ,
            {"procedurePace", "工步"}
            ,
            {"procedureNum1", "工序编号"}
            ,
            {"procedureNum", "工序号"}
            ,
            {"processFlowNum", "过程流程编号"}
            ,
            {"controlPlanNum", "控制计划编号"}
            ,
            {"femaNum", "过程FMEA编号"}
            ,
            {"taskInstructNum", "作业指导编号"}
            ,
            {"procedureName", "工序名称"}
            ,
            {"paceNum", "工步号"}
            ,
            //Begin CR2
            {"toolbar.icons", "public_createObj,public_updateObj,public_deleteObj,public_viewObj,Spacer,public_checkInObj,public_checkOutObj,public_repealObj,Spacer,public_copy,public_paste,Spacer,public_search,public_clear"}
            ,
            {"toolbar.text",
            "创建,更新,删除,查看,Spacer,检入,检出,撤消检出,Spacer,复制,粘贴,Spacer,搜索,移除"}
            //End CR2
            ,
            {"techAction", "添加工艺规程"}
            ,
            {"eqAction", "添加设备关联"}
            ,
            {"toAction", "添加工装关联"}
            ,
            {"maAction", "添加材料关联"}
            ,
            {"termAction", "选中工艺术语"}
            ,
            {"drawAction", "添加工艺简图"}
            ,
            {"partAction", "添加零部件"}
            ,
            //Messages
            {"1", "无效模式"}
            ,
            {"2", "*并没有发生任何更改。"}
            ,
            {"3", "工艺编号不能为空。"}
            ,
            {"4", "工艺名称不能为空。"}
            ,
            {"5", "缺少必需的字段"}
            ,
            {"6", "资料夹位置不能为空。"}
            ,
            {"7", "资料夹路径无效"}
            ,
            {"8", "所指定的资料夹不是您的个人资料夹"}
            ,
            {"9", "正在保存..."}
            ,
            {"10", "是否保存新建工艺主信息？"}
            ,
            {"11", "是否保存更新工艺主信息数据？"}
            ,
            {"12", "当前工艺规程在您的个人文件夹中，不能提交。"}
            ,
            {"13", "工序编号不能为空。"}
            ,
            {"14", "工序名称不能为空。"}
            ,
            {"15", "是否保存新建工序？"}
            ,
            {"16", "是否保存更新工序数据？"}
            ,
            {"17", "工步号不能为空。"}
            ,
            {"18", "是否保存新建工步？"}
            ,
            {"19", "是否保存更新工步数据？"}
            ,
            {"20", "选择的对象类型错误。"}
            ,
            {"21", "删除操作将无法恢复，是否继续？"}
            ,
            {"22", "复制的类型或内容不匹配，不能复制"}
            ,
            {"23", "必须指定工艺的资料夹位置，才能执行检入。请您选择资料夹。"}
            ,
            {"24", "检入*失败，因为主要内容的上载或存储过程中出现错误。"}
            ,
            {"25", "*正处于非首次检入状态。请先检出它，再执行检入操作。"}
            ,
            {"26", "您没有选择操作对象，无法执行当前操作。"}
            ,
            {"27", "*已经被用户*检出。"}
            ,
            {"28", "*已经被您检出。"}
            ,
            {"29", "在初始化本地化资源时,出错:"}
            ,
            {"30", "您确实要撤消检出*，并丢失所有变更吗？"}
            ,
            {"31", "*已经被检出到资料夹*中！"}
            ,
            {"32", "在搜索检出资料夹 * 时发生错误."}
            ,
            {"33", "*已经被他人检出!"}
            ,
            {"34", "检出*失败！"}
            ,
            {"35", "您未获得修订*的许可，因为它尚未被检入。"}
            ,
            {"36", "*已经被*检出，您确实要检入它吗？"}
            ,
            {"37", "尚未指定存放当前对象的副本的资料夹。必须给定检出资料夹，才能检出。"}
            ,
            {"38", "无法找到包含*的工作副本的资料夹，因为*没有被检出。"}
            ,
            {"39", "不能删除*,因为它已经被检出!"}
            ,
            {"40", "*需要检出到个人资料夹中才能修改,现在要检出吗？"}
            ,
            {"41", "设备列表和工装列表必须都添加有数据，且您所选择的设备和工装中至少有一项是一条数据，才能入库。"}
            ,
            {"42", "当前工序已经选择了关联工艺，不能为该工序创建工步。"}
            ,
            {"43", "当前工艺规程已经成功提交到工作流程中！"}
            ,
            {"44", "是否合并工艺规程？"}
            ,
            {"45", "所选工艺*与新建工艺的工艺种类不匹配！"}
            ,
            {"46", "保存工序（步）失败"}
            ,
            {"47", "系统配置错误，请与系统管理员联系！"}
            ,
            {"48", "您尚未检出*，它当前正被*检出。"}
            ,
            {"49", "*当前已被*检出。您确实要撤销检出并丢弃对*所做的所有更改吗？"}
            ,
            {"50", "当前工序种类没有可维护的扩展内容。"}
            ,
            {"51", "缺乏必须的资源。"}
            ,
            {"52", "*正处于检出状态，您必须首先将它检入，才能进行当前操作。"}
            ,
            {"53", "您必须首先在工艺树上选择一个工艺卡对象，才能新建工序。"}
            ,
            {"54", "您必须首先在工艺树上选择一个工序（步）对象，才能新建工步。"}
            ,
            {"55", "*没有被任何零部件使用，不能提交。"}
            ,
            {"56", "工时数值为实数，请正确输入。"}
            ,
            {"57", "您所指定的工装不存在，是否申请新建工装？"}
            ,
            {"58", "工序（步）号必须为整型数据，不得含有文字、字母或符号。"}
            ,
            {"59", "工序（步）号无效"}
            ,
            {"60", "已经存在*！"}
            ,
            {"61", "*的工艺内容（工序或工步）尚未检入！"}
            ,
            {"62", "工艺内容不能为空。"}
            ,
            {"63", "您尚未检出*，不能新建其工序或工步。"}
            ,
            {"64", "您尚未检出要粘贴的对象！"}
            ,
            {"65", "当前对象不能检出！"}
            ,
            {"66", "首次检入只能检入工艺主信息"}
            ,
            {"67", "关联工艺不能选择本工序所属的工艺"}
            ,
            {"68", "您尚未检出*。"}
            ,
            {"69", "*与*的工艺种类不匹配！"}
            ,
            {"70", "*处于检出状态，不能更改资料夹！"}
            ,
            {"71", "*不在公共资料夹中，不能更改资料夹！"}
            ,
            {"72", "您所指定的设备不存在，是否申请新建设备？"}
            ,
            {"73", "您所指定的材料不存在，是否申请新建材料？"}
            ,
            {"74", "*是工作副本的原本，不允许修改！"}
            ,
            {"75", "*已经被检出，不能合并！"}
            ,
            {"76", "*是工作副本的原本，不能复制！"}
            ,
            {"77", "不具有更新权限"}
            ,
            {"78", "您没有选择有效的特殊符号。"}
            ,
            {"79", "工艺种类不能为空。"}
            ,
            {"80", "工序种类不能为空。"}
            ,
            {"81", "加工类型不能为空。"}
            ,
            {"82", "工序类别不能为空。"}
            ,
            {"83", "部门不能为空。"}
            ,
            {"84", "简图输出方式不能为空。"}
            ,
            {"85", "工艺种类"}
            ,
            {"86", "工序种类"}
            ,
            {"87", "工序类别"}
            ,
            {"88", "加工类型"}
            ,
            {"89", "部门"}
            ,
            {"90", "图页形式"}
            ,
            {"91", "您输入的工序号过长,请确保工序号小于99999"}
            ,
            {"92", "您输入的工步号过长，请确保工步号小于99999"}
            ,
            {"93", "您输入的工艺编号过长，请确保长度小于20位"}
            ,
            {"94", "您输入的工艺名称过长，请确保长度小于40位"}
            ,
            {"95", "您输入的工序名称过长，请确保长度小于40位"}
            ,
            {"96", "工序名称无效"}
            ,
            {"97", "工艺编号无效"}
            ,
            {"98", "工艺名称无效"}
            ,
            {"99", "备注信息无效"}
            ,
            {"100", "您输入的备注信息过长，请确保长度小于40位"}
            ,
            {"101", "材料消耗定额无效"}
            ,
            {"102", "请确保材料消耗定额在0~99999.99999之间"}
            ,
            {"103", "使用数量无效"}
            ,
            {"104", "请确保输入的使用数量在1~99之间"}
            ,
            {"105", "请确保输入的使用数量在0~99999.9999之间"}
            ,
            {"106", "工时无效"}
            ,
            {"107", "您输入的工时无效，请确保工时在0~9999999.999之间"}
            ,
            {"108", "保存后是否连续建工序"}
            ,
            {"109", "保存后是否连续建工步"}
            ,
            {"110", "不能删除此工艺,此工艺已被工艺卡*关联"}
            ,
            {"111", "*还未使用冲压下料工艺做为主要工艺,不能使用冲压工艺"}
            ,
            //代码分类,用来在代码管理中查找工序种类
            {"112", "工艺内容"}
            ,
            {"113", "代码管理中\"*\"工序种类未建或不正确,请与系统管理员联系"}
            ,
            {"114", "*属性值应为*类型"}
            ,
            {"115", "*下不能新建工步"}
            ,
            {"116", "请检查属性文件中配置的工艺使用材料关联控制类是否正确"}
            ,
            {"117", "请检查属性文件中配置的零件使用工艺关联控制类是否正确"}
            ,
            {
             "118","工序被检出"
            }
            ,
            {
             "119","删除该工艺主信息,将会使其下的工序和工步也全部删除且不能恢复,是否继续"
            }
            //Begin CR3
            ,
            {
             "121","关联工艺不能选择处于检出状态的工艺"
            }
            //End CR3
               ,

            //为 TechnicsStepJPanel界面实例化工序种类面板所用
            {"technicsTypeForTechnicsStepJPanel", "工艺种类信息"}
            ,
            {"workingCopyTitle", "* 的工作副本"}
            ,
            {"techCheckedOutBy", "由 * 检出"}
            ,
            {"inPesrsonFolder", "在个人文件柜"}
            ,
            {"techCheckedIn", "已检入"}
            ,
            {"ifQuitMessage", "这样将退出系统,是否继续?"}
            ,
            {"flowdrawing", "制件流程图"}
            ,

            {"0", "测试"}
            ,
            {"Help/Capp/QMTechnics", "help/zh_cn/capp/index.html"}
            ,
            {"Desc/Capp/QMTechnics", ""}
            ,
            //状态栏中的内容
            {"default_status", "要获得帮助，请点击“帮助”菜单或点击F1 键。"}
            ,
            {"jMenuFileExit_status", "退出本系统。"}
            ,
            {"jMenuHelpAbout_status", "关于本系统。"}
            ,
            {"jMenuFileCollect_status", "将几个不同工艺规程合并为一个。"}
            ,
            {"jMenuFileExport_status", "导出一个或几个工艺规程的信息。"}
            ,
            {"jMenuFileRefresh_status", "刷新工艺树上选中的节点。"}
            ,
            {"jMenuFileCreate_status", "创建一个工艺主信息或工序或工步。"}
            ,
            {"jMenuCreateTechnics_status", "创建一个工艺主信息。"}
            ,
            {"jMenuCreateStep_status", "创建一个工序。"}
            ,
            {"jMenuCreatePace_status", "创建一个工步。"}
            ,
            {"jMenuSelectMadeTech_status", "将选中的工艺生成一个典型工艺。"}
            ,
            {"jMenuSelectMadeStep_status", "将选中的工序生成一个典型工序。"}
            ,
            {"jMenuSelectCopy_status", "复制工艺或工序或工步到剪贴板中。"}
            ,
            {"jMenuSelectPaste_status", "将剪贴版中的对象粘贴到工艺树选择的节点下。"}
            ,
            {"jMenuVersionCheckIn_status", "将工艺检入到一个公共资料夹中。"}
            ,
            {"jMenuVersionCheckOut_status", "检出工艺到个人资料夹。"}
            ,
            {"jMenuVersionCancel_status", "将检出的工艺撤销回检出前的状态。"}
            ,
            {"jMenuVersionRevise_status", "修订工艺,将大版本升级。"}
            ,
            {"jMenuVersionVersion_status", "查看工艺树选中节点的版本历史。"}
            ,
            {"jMenuVersionIteration_status", "查看工艺树选中节点的版序版本。"}
            ,
            {"jMenuSetLifeCycleState_status", "重新指定工艺的生命周期状态。"}
            ,
            {"jMenuLifeCycleSelect_status", "重新指定工艺的生命周期。"}
            ,
            {"jMenuLifeCycleView_status", "查看工艺的生命周期历史。"}
            ,
          //2008.03.09 徐德政改，将“项目组”改为“工作组”
            {"jMenuLifeCycleGroup_status", "重新指定工艺的工作组。"}
            //end mario
            ,
            {"jMenuSearchEquip_status", "按使用的设备信息搜索工艺。"}
            ,
            {"jMenuSearchTool_status", "按使用的工装信息搜索工艺。"}
            ,
            {"jMenuSearchMaterial_status", "按使用的材料信息搜索工艺。"}
            ,
            {"jMenuSearchTechnics_status", "搜索工艺。"}
            ,
            {"jMenuAddObject_status", "将当前选中对象加入工艺内容面板的列表中。"}
            ,
            {"jMenuSelectDelete_status", "删除工艺树上选中的对象。"}
            ,
            //Begin CR2
            {"jMenuMoveOutTechnics_status", "移除工艺。"}
            //End CR2
            ,
            {"jMenuSelectView_status", "查看工艺树上选中的对象。"}
            ,
            {"jMenuSelectUpdate_status", "更新工艺树上选中的对象。"}
            ,
            {"jMenuSelectFormStepNum_status", "将选中工艺的工序按顺序重新生成工序号。"}
            //CR1 begin
            ,
            {"jMenuSelectFormPaceNum_status", "将选中工艺的工步按顺序重新生成工步号。"}
            //CR1 end
            
            ,
            {"jMenuSelectBrowse_status", "打印预览工艺或工序信息。"}
            ,
            {"jMenuSelectChangeLocation_status", "重新指定工艺的存储资料夹。"}
            ,
            {"jMenuSelectRename_status", "为工艺主信息重新指定工艺编号和工艺名称。"}
            ,
            {"jMenuSelectSaveAs_status", "将工艺存为另一个工艺,并可修改工艺主信息的某些信息。"}
            ,
            {"jMenuSelectUseTech_status", "将已存在的典型工艺复制到当前选择的工艺下。"}
            ,
            {"jMenuSelectUseStep_status", "将已存在的典型工序复制到当前选择的工艺下。"}
            ,
            {"jMenuConfigCrit_status", "设置搜索零件的配置规范。"}
            ,
            {"jMenuHelpItem_status", "帮助。"}
            ,
            {
            "exportSuccessful", "导出成功！"
    }
            ,
            {
            "drawingFormatConfigError", "capp.property中配置的*简图输出方式不正确,请改正。"
    }
            ,
            //问题(1)2008.02.20 刘志城修改 修改原因:高级搜索按创建日期搜时输入非规定日期格式仍继续搜索
            //应改为:停止搜索,弹出提示信息,请用户重新输入正确日期格式时添加属性.
            {
            "dateCreatedSearch","输入时间错误,请按格式输入。"
            }
            ,
            {"processFlow","特性控制"}
            ,
            {"processFMEA","过程FMEA"}
            ,
            {"controlPlan","控制计划"}

    };


}
