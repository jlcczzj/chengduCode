/** 程序QMProductManagerRB_zh_CN.java	1.0  2003/01/05
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/10/30 马辉 原因：删除工具栏中“更改”按钮
 *                     方案：删除工具栏中“更改”按钮
 *                     备注：删除工具栏中“更改”按钮
 * CR2 2010/03/16 马辉 修改原因：TD2897 成功检入，检出，撤销检出的提示对话框不符合规范  
 * CR3 2010/03/18 马辉 修改原因：TD2939 在产品信息管理器中，其零部件菜单下应该有更新功能，
 *                     修改方案：菜单中的"更新"只负责把没有检出的零部件检出，并把焦点移放到副本上  、
 * CR4 20110413 wangl 原因：TD4143。
 * CR5 2011/04/15 马辉 修改原因：TD4032信息管理器零部件菜单栏中部分移动和重命名快捷键重复
 *                     修改方案：部分移动快捷键为M，重命名快捷键为R  
 * SS1 2013-1-21  产品信息管理器中输出物料清单功能中增加输出带有erp属性报表功能
 * SS2 2013-1-21  产品信息管理器中具有结构复制功能，可以将选中零部件结构完全复制，并按照结构粘贴到其他零部件中
 * SS3 2014-6-6   文柳 解放工艺BOM:产品信息管理器增加右键“查看发布BOM” 
 * SS4 查看BOM版本与逻辑不合栏、没子组、不显示保存成功、应支持按单级/多级导出 xianglx 2014-9-28
 * SS5 增加定位搜索菜单 pante 2015-01-08
*/
package com.faw_qm.part.client.main.util;

import java.util.ListResourceBundle;


/**
 * <p>Description:本地化资源信息类。 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * @author 李玉芬
 * @version 1.0
 * CR1  2009/06/01 王亮   原因：详见缺陷TD=2161。
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
    //add by liun，添加状态栏提示信息2005-06-20。
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
    //liyz add 状态栏应用所有功能的提示信息
    public static final String MANAGE_APPLYALLPART="305";
    public static final String WRANG_TYPE_SELECT_TAB="306";//end
    
//  add by muyppeng 20080625 begin
    public static final String BROWSE_APPLYTOALLPARTS = "178";
    public static final String APPLY_ATTRIBUTE_TO_OTHER = "179";
    //end
//  liyz add工艺菜单项
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
            {"part.label", "零部件(P)"}
            ,
            {"part.createPart.label", "创建(N)..."}
            ,
            {"part.updatePart.label", "更新(U)..."}
            ,
            {"part.copyPart.label", "复制(C)"}
            ,//CCBegin SS2
            {"part.copyAllPart.label", "结构复制(J)"}
            ,//CCEnd SS2
            //CCBegin SS3
            {"VIEW_REALSE_BOM", "查看发布BOM(多级)"}
            ,//CCEnd SS3
            //CCBegin SS4
            {"VIEW_REALSE_BOM_DJ", "查看发布BOM(单级)"}
            ,//CCEnd SS4
          //CCBegin SS5
            {"DWSS", "定位搜索零件"} 
            ,//CCEnd SS5
            {"part.cutPart.label", "剪切(T)"}
            ,
            {"part.shareMovePart.label", "部分移动(M)..."}
            ,
            {"part.pastePart.label", "粘贴(P)"}
            ,
            {"part.pasteAllPart.label", "结构粘贴(K)"}
            ,
            {"part.saveAsPart.label", "另存为(G)..."}
            ,
            {"part.renamePart.label", "重命名(R)"}//CR5
            ,
            {"part.deletePart.label", "删除(D)"}
            ,
            {"part.clear.label", "在窗口中清除(A)"}
            ,
            {"part.refresh.label", "刷新(E)"}
            ,
            {"part.exit.label", "退出(X)"}
            ,
            {"version.label", "版本(V)"}
            ,
            {"version.checkIn.label", "检入(I)"}
            ,
            {"version.checkOut.label", "检出(O)"}
            ,
            {"version.undoCheckOut.label", "撤消检出(U)"}
            ,
            {"version.move.label", "更改资料夹(M)"}
            ,
            {"version.revise.label", "修订(R)"}
            ,
            {"version.versionHistroy.label", "版本历史(B)"}
            ,
            {"version.iterationHistroy.label", "版序历史(X)"}
            ,
            {"manage.label", "管理(M)"}
            ,
            {"manage.baseline.label", "基准线(B)"}
            ,
            {"manage.baseline.createBaseline.label", "创建基准线(C)"}
            ,
            {"manage.baseline.maintenanceBaseline.label", "维护基准线(E)"}
            ,
            {"manage.baseline.viewBaseline.label", "查看基准线(V)"}
            ,
            {"manage.baseline.addBaseline.label", "添加(A)"}
            ,
            {"manage.baseline.populateBaseline.label", "按结构添加(P)"}
            ,
            {"manage.baseline.removeBaseline.label", "移除(M)"}
            ,
            {"manage.effectivity.label", "有效性(E)"}
            ,
            {"manage.effectivity.createConfigItem.label", "创建方案(C)"}
            ,
            {"manage.effectivity.maintenanceConfigItem.label", "维护方案(E)"}
            ,
            {"manage.effectivity.viewConfigItem.label", "查看方案(V)"}
            ,
            {"manage.effectivity.addEffectivity.label", "添加(A)"}
            ,
            {"manage.effectivity.populateEffectivity.label", "按结构添加(P)"}
            ,
            {"manage.effectivity.updateEffValue.label", "修改有效性值(U)"}
            ,
            {"manage.effectivity.removeEffectivity.label", "移除(M)"}
            ,
            {"manage.defineAlternates.label", "替换件(A)"}
            ,
            {"manage.defineSubstitutes.label", "结构替换件(S)"}
            ,
            {"manage.publishViewVersion.label", "发布视图版本(P)"}
            ,
            {"manage.publishIBA.label", "发布特性(I)"}
            ,
            {"browse.label", "浏览(B)"}
            ,
            {"browse.setConfigSpec.label", "配置规范(P)"}
            ,
            {"browse.viewPart.label", "查看(V)"}
            ,
            {"browse.compareStructure.label", "结构比较(C)"}
            ,
            {"browse.ibaCompare.label", "属性比较(B)"}
            ,
            {"browse.saveAsHistory.label", "另存为历史(H)"}
            ,
            {"browse.catalogHistory.label", "注册历史(T)"}
            ,
            {"browse.generalSearch.label", "搜索零部件(S)"}
            ,
            {"browse.baseAttrSearch.label", "按基本属性搜索(J)"}
            ,
            {"browse.ibaSearch.label", "按事物特性搜索(I)"}
            ,
            {"browse.BOM.label", "物料清单(M)"}
            ,
            //CCBegin SS1
            {"browse.BOM.erpList.label", "ERP(E)"}
            ,
            //CCEnd SS1
            //CCBegin SS1
            {ERPATTR_NAME, " 零件号 零件名称 父件数量 生命周期状态 计量单位 来源 制造路线 装配路线 T单总 固定提前期合计 可变提前期"}
            ,
             {ERPATTR_ENG_NAME, " partNumber-0,partName-0,quantity-0,lifeCycleState-0,defaultUnit-0,producedBy-0,制造路线-1,装配路线-1,T单总-2,固定提前期合计-3,可变提前期-3,"}
            , //CCEnd SS1
            {"browse.BOM.materialList.label", "分级(F)"}
            ,
            {"browse.BOM.bomList.label", "统计表(T)"}
            ,
            {"browse.BOM.tailorBOM.label", "定制(D)"}
            ,
            {"browse.BOM.gPBomList.label", "广义部件定制(G)"}
            ,
            {"browse.publish.label", "发布"}
            ,
            {"lifeCycle.label", "生命周期(L)"}
            ,
            {"lifeCycle.commit.label", "提交"}
            ,
            {"lifeCycle.lifeCycleHistroy.label", "生命周期历史"}
            ,
            {"lifeCycle.viewProcessTrack.label", "查看过程记录"}
            ,
            {"lifeCycle.viewActor.label", "查看参与者"}
            ,
            {"lifeCycle.resetLifeCycle.label", "重新指定生命周期(R)"}
            ,
            {"lifeCycle.setLifeCycleState.label", "设置生命周期状态(S)"}
            ,
            {"lifeCycle.showLifeCycleHistory.label", "显示生命周期历史(H)"}
            ,
            {"help.label", "帮助(H)"}
            ,
            {"help.helpItem.label", "帮助"}
            ,
            {"help.productManager.label", "产品信息管理器(P)"}
            ,
            {"help.about.label", "关于(A)"}
            ,
            {"Title", "不符合要求的零件"}
            ,
            {"warn", "警告"}//CR1
            ,
            {"sort", "排序(O)"}
            ,
            {MAIN_PAGE_TITLE, "产品信息管理器"}
            ,
            {RENAME_TITLE, "重命名零部件"}
            ,
            {CHECKOUT_TITLE, "检出对象"}
            ,
            {OK, "确定(Y)"}
            ,
            {MESSAGE, "警告"}
            ,
            {GENENAL_SEARCH, "搜索零部件"}
            ,
            {BASE_ATTR_SEARCH, "按基本属性搜索"}
            ,
            {EXTEND_ATTR_SEARCH, "按扩展属性搜索"}
            ,
            {ABOUT_PRODMGR, "关于产品信息管理器"}
            ,
            {PRODMGR, "产品信息管理器"}
            ,
            {VERSION, "版本：1.0"}
            ,
            {VERSION_RIGHT, "版权所有：一汽启明信息技术公司"}
            ,
            {PRODUCT_MANAGER, "产品信息管理器在线帮助"}
            ,
			{"partexplorer.explorer.list.headings",
					"编号,名称,版本,视图,数量,单位,类型,来源,生命周期状态,使用关系ID"}
            ,
            {"partexplorer.explorer.tree.statusBarText", "产品结构"}
            ,
            {"partexplorer.explorer.list.statusBarText", "零部件"}
            ,
            {"partexplorer.explorer.tree.rootNodeText", "产品结构"}
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
            "创建,更新,Spacer,复制,剪切,粘贴,Spacer,删除,查看,Spacer,检入,检出,撤消检出,Spacer,刷新,在窗口中清除,Spacer,搜索零部件,Spacer,应用所有"}
            ,
            {"partexplorer.explorer.toolbar.single",
            "启动“创建零部件”窗口。,启动“更新零部件”窗口，其中您可以修改选定零部件的所选属性和关联。可以在左侧的树形结构中或右侧的列表中选择一个零部件进行更新。必须将零部件检出才能进行更新。,Spacer,复制所选零部件。,剪切所选零部件。,粘贴所选零部件。,Spacer,删除所选零部件，被其他零部件使用时无法删除, 启动零部件属性页，它显示选定零部件的属性和关联。,Spacer, 启动所选零部件的检入进程。,启动所选零部件的检出过程。,取消所选零部件的检出。,Spacer, 从数据库中检索所选零部件并用刷新部件替换当前显示的对象。如果所选零部件在树形视图中曾是展开的，现在它会折叠起来。,清除“产品信息管理器” 中的选中的首个零部件。,Spacer, 搜索零部件的基本记录，不包含零部件的版本信息。,Spacer,批量处理零部件。"}
            ,
            {ALREADY_CHECKOUT_BY_OTHER, "对象被用户*检出，不能修改!"}
            ,
            {ALREADY_CHECKOUT_BY_OTHER_NOTCHECKIN, "对象被用户*检出，不能检入!"}
            ,

            {CANNOT_CHECKOUT_USERFOLDER, "当前零部件在个人资料夹中，不能检出!"}
            ,
            {AIREADY_CHECKOUT, "零部件已经被检出到*资料夹中!"}
            ,
            {CANNOT_DELETE_CHECKED_OUT, "对象已经被用户*检出，不能删除。"}
            ,
            {
            ALREADY_CHECKOUT_SELF, "零部件已被检出!"
            }
            ,
            {
            CYHECKOUTBCAD, "零部件已由CAD检出!"
            }
            ,
            {
            ALREADY_CYHECKOUT_OTHER_NOTRENAME, "该零部件已被用户*检出,不能重命名"
            }
            ,

            {
            ALREADY_CYHECKOUT_OTHER,"该零部件已被用户*检出"
             }
            ,
            {CONFIRM_DELETE_OBJECT, "确定要删除对象 * 吗?"}
            ,
            {NOT_CHECKED_OUT, "该对象没有被检出!"}
            ,
            {RETRIEVE_CHECKOUT_FOLDER_FAILED, "在搜索检出资料夹 * 时发生错误!"}
            ,
            {CREATE_TASKDELEGATE_FAILED, "在创建代理对象实例时发生错误。"}
            ,
            {CONFIRM_TO_CHECKOUT, "对象 * 需要检出才能修改,现在要检出吗?"}
            ,
            {WRANG_TYPE_OBJECT, "选择的对象类型错误!"}
            ,
            {CAN_NOT_DEFINE_SUBSTITUTES, "顶级件不能定义结构替换件!"}
            ,
            {UPDATE_FAILED, "更新失败!"}
            ,
            {OBJ_NOT_PART_OR_PARTMASTER, "所选对象不是零部件或零部件的主信息!"}
            ,
            {NO_USAGE_RELATIONSHIP_FOUND, "没有发现使用关系!"}
            ,
            {NOT_ASSIGN_VIEW_VERSION, "所选对象不能发布视图版本!"}
            ,
            {RENAME_ERROR, "重命名时发生错误!"}
            ,
            {DELETE_OBJECT, "删除对象确认"}
            ,
            {NO_QUALIFIED_VERSION, "零部件没有合适的版本!"}
            ,
            {PART_ALREADY_DISPLAYED, "零部件*已经在管理器中显示!"}
            ,
            {USED_BY_OTHER, "零部件*已经被其他部件使用，不能删除!"}
            ,
            {NOT_IN_COMMON_FOLDER, "所选对象不在公共资料夹中，不能被移到其他资料夹中!"}
            ,
            {NOT_MOVE, "所选对象已经被检出，不能被移动"}
            ,
            {NOT_SELECT_OBJECT, "没有选择操作对象!"}
            ,
            {CANNOT_ENABLE_DEFINE_SUBSTITUTES, "不能定义结构替换件，当前用户没有访问权限。"}
            ,
            {CANNOT_ENABLE_DEFINE_ALTERNATES, "不能定义替换件，当前用户没有访问权限!"}
            ,
            {NO_SOLUTION, "该有效性方案没有零部件方案，不能进行此项操作!"}
            ,
            {NO_SUITABLE_VERSION, "该有效性方案没有符合当前配置规则的版本!"}
            ,
            {STANDCONFIG, "按结构添加有效性必须在标准配置规范下进行！"}
            ,
            {SUCCESSFUL, "操作成功！"}
            ,
            {UNSUCCESSFUL, "操作失败！"}
            ,
            {NO_SELECT_GPART, "选择的对象类型错误,请选择一个广义部件。"}
            ,
            {ICONIMAGE, "part"}
            ,
            ///////////////////////////状态栏提示信息///////////////////////////////
            {PART_MENU, "此菜单含有创建、另存为、更新、重命名、删除、清除、刷新、退出这几项功能。"}
            ,
            {PART_CREATE, "启动“创建零部件”窗口，创建一个新零部件。"}
            ,
            {PART_SAVEAS, "将选定零部件保存为新的零部件，可以用此方法快速创建一个新的零部件。"}
            ,
            {PART_UPDATE, "启动“更新零部件”窗口，其中您可以修改选定零部件的所选属性和关联。可以在左侧的树形结构中或右侧的列表中选择一个零部件进行更新。必须将零部件检出才能进行更新。"}
            ,
            {PART_COPY, "复制所选零部件。"}
            ,
            {PART_CUT, "剪切所选零部件。"}
            ,
            {PART_SHAREMOVE, "部分移动所选零部件。"}
            ,
            {PART_PASTE, "粘贴所选零部件。"}
            ,
            {PART_All_PASTE, "粘贴所选零部件的子件。"}
            ,
            {PART_RENAME, "用于选定零部件的重命名。名称和编号的改变将应用于该零部件的所有版本，而不仅仅是选定的版本。"}
            ,
            {PART_DELETE, "删除所选零部件的当前版本。"}
            ,
            {PART_CLEAR, "清除“产品信息管理器”中的所有零部件。"}
            ,
            {PART_REFRESH, "从数据库中检索所选零部件并用刷新零部件替换显示的对象。如果所选零部件在树形结构曾是展开的，现在它会折叠起来。"}
            ,
            {PART_EXIT, " 退出“产品信息管理器”窗口。"}
            ,
            {VERSION_MENU, "此菜单含有检入、检出、撤销检出、更改资料夹、修订、版本历史、版序历史这几项功能。"}
            ,
            {VERSION_CHECKIN, "启动所选零部件的检入进程。"}
            ,
            {VERSION_CHECKOUT, " 启动所选零部件的检出过程。"}
            ,
            {VERSION_UNDOCHECKOUT, " 取消所选零部件的检出。"}
            ,
            {VERSION_CHANGEFILE, "把选定零部件从当前资料夹移动到另一外资料夹中。"}
            ,
            {VERSION_REVISE, " 启动“修订”窗口，可用于创建零部件的新版本并指定其位置、生命周期和工作组。"}
            ,
            {VERSION_VERSIONS, " 显示零部件所有版本的信息。可以选择和查看特定版本，而且可以选择和比较不同的版本"}
            ,
            {VERSION_ITERATIONS, "显示零部件当前和以前版序的信息。版本的最新版序代表其当前状况。从该窗口，可以选择和查看特定的版序，而且可以选择和比较不同的版序。"}
            ,
            {MANAGE_MENU, "此菜单含有基准线、有效性、替换件、结构替换件、发布视图版本、发布特性这几项功能。"}
            ,
            {MANAGE_BASELINE, "用于基准线管理的二级菜单。"}
            ,
            {MANAGE_BASELINE_CREATE, " 启动“创建基准线”窗口，可以创建一个新的基准线。"}
            ,
            {MANAGE_BASELINE_UPDATE, " 通过搜索获得一个基准线，进行更新、删除等维护操作。"}
            ,
            {MANAGE_BASELINE_VIEW, " 通过搜索获得一个基准线，查看基准线的属性和基准线中的所有零部件。"}
            ,
            {MANAGE_BASELINE_ADD, "将选定的零部件添加到一个基准线中。"}
            ,
            {MANAGE_BASELINE_POPULATE, "将选定零部件及其子结构中的所有零部件的当前显示版本添加到基准线中。"}
            ,
            {MANAGE_BASELINE_CLEAR, "将选定零部件从基准线中删除。"}
            ,
            {MANAGE_EFFECTITVTY, " 用于有效性管理的二级菜单。"}
            ,
            {MANAGE_EFFECTITVTY_CREATE, " 启动“创建有效性方案”窗口，创建一个有效性方案。"}
            ,
            {MANAGE_EFFECTITVTY_UPDATE, "通过搜索获得一个有效性方案，进行更新、删除等维护操作。"}
            ,
            {MANAGE_EFFECTITVTY_VIEW, "通过搜索获得一个有效性方案，查看有效性方案的信息。"}
            ,
            {MANAGE_EFFECTITVTY_ADD, "为选定零部件添加有效性。"}
            ,
            {MANAGE_EFFECTITVTY_POPULATE, " 为有效性方案所属的产品方案中的所有零部件的当前版本添加统一的有效性。"}
            ,
            {MANAGE_EFFECTITVTY_CHANGEVALUE, "修零部件的有效性值区间。"}
            ,
            {MANAGE_EFFECTITVTY_CLEAR, " 将有效性值从选定的零部件中删除。"}
            ,
            {MANAGE_ALTERNATES, "定义选定零部件的替换件。"}
            ,
            {MANAGE_SUBSTITUTES, " 定义选定零部件在当前结构中的替换件。"}
            ,
            {MANAGE_PUBLISHVIEWVERSION, " 在新视图中创建选定零部件的新版本，要创建零部件的新视图版本，零部件必须创建于视图中。"}
            ,
            {MANAGE_PUBLISHIBA, "如果您已经安装了“重用管理器”部分，则此选项将显示在该菜单中。该菜单选项将所选零部件版本的事物特性复制到与其对应的零部件主信息对象。"}
            ,
            {BROWSE_MENU, "此菜单含有配置规范、查看、结构比较、搜索零部件、按基本属性搜索、物料清单、排序这几项功能。"}
            ,
            {BROWSE_CONFIGSPEC, "启动“配置规范”窗口。"}
            ,
            {BROWSE_VIEW, " 查看选定零部件的属性信息。 "}
            ,
            {BROWSE_COMPARESTRUCTURE, "比较选定产品在当前筛选条件下的产品结构与另一个产品或当前产品的另一个筛选条件下的结构差异。"}
            ,
            {BROWSE_COMPAREIBA, "比较选定产品在当前筛选条件下的产品结构与另一个产品或当前产品的另一个筛选条件下的IBA属性差异。"}
            ,
            {BROWSE_GENERALSEARCH, " 搜索零部件的基本记录，不包含零部件的版本信息。"}
            ,
            {BROWSE_BASEATTRSEARCH, " 根据零部件的属性进行搜索，返回零部件的特定版本。"}
            ,
            {BROWSE_IBASEARCH, " 根据零部件的事物特性进行搜索，返回零部件的特定版本。"}
            ,
            {BROWSE_BOM, "生成物料清单的二级菜单。"}
            ,
            {BROWSE_BOM_MATERIALLIST, " 生成选定零部件的分级物料清单。"}
            ,
             //CCBegin SS1
            {BROWSE_BOM_ERPLIST, " 生成选定零部件的ERP物料清单。"}
            ,
            //CCEnd SS1
            {BROWSE_BOM_BOMLIST, " 生成选定零部件的结构中所有零部件的统计表。"}
            ,
            {BROWSE_BOM_TAILORBOM, " 启动定制输出物料清单的窗口。"}
            ,
            {BROWSE_BOM_GPARTBOM, " 启动定制输出广义部件物料清单的窗口。"}
            ,
            {BROWSE_SORT, "根据不同的参数组合对产品结构树上的零部件进行排序。"}
            ,
            {LIFECYCLE_MENU, "此菜单含有重新指定生命周期、设置生命周期状态、显示生命周期历史这几项功能。"}
            ,
            {LIFECYCLE_RESET, "打开一个窗口使您可以指定一个不同的生命周期。"}
            ,
            {LIFECYCLE_SETSTATUS, " 打开一个窗口使您可以设置生命周期的对象独立性的状态。"}
            ,
            {LIFECYCLE_SHOWHISTORY, " 查看选定零部件的生命周期历史。"}
            ,
            {HELP_MENU, "此菜单含有产品信息管理器和关于这两项功能。"}
            ,
            {HELP_PRODUCTMANAGER, " 访问有关产品信息管理器的联机帮助和信息。"}
            ,
            {HELP_ABOUT, " 提供有关产品信息管理器的版本和版权信息。 "}
            ,
            //////////////////////////////////////////////////////////
            {NOT_NEW_VIEW, " 零部件*不是最新版本，不能发布视图。 "}
            ,
            {NOT_NEW_REVISE, " 零部件*不是最新版本，不能修订。 "}
            ,
            {ALTER_OR_SUB, " 该零部件是一个替换件或结构替换件，不能删除。 "}
            ,
            {ATTR_NAME, " 编号 名称 数量 版本 视图"}
            ,
            //CCBegin by liunan 2008-08-06
            {ATTR_ENG_NAME, " partNumber-0,partName-0,quantity-0,versionValue-0,viewName-0,"}
            //CCEnd by liunan 2008-08-06
            ,
             //CCBegin SS2
            {PART_ALL_COPY, "复制所选零部件的子件。"}
            //CCEnd SS2
            ,
            {"ProductionManager", "产品信息管理器"}
            ,
            {"version", "版        本：  * "}
            ,
            {"right1", "版权所有：  启明信息技术股份有限公司"}
            ,
            {"right2", "( C )  2001-* "}
            ,
            {"204", "( C )  2001-* "}
            ,
            {"about", "关于产品信息管理器"}
            ,
            {"net", "网        址："}
            ,
            {"working", "正在执行操作"}
            ,
            {"noCatalog", "此零部件不是由广义部件注册生成!"}
            ,
            {"prompt", "提示"}
            ,
            {"canNotRevise","您不能修订该零部件。要修订该零部件，需要对它有\"修订\"权限。"}
            ,
            {"canNotReviseView","您不能发布视图。需要对该零部件有\"修订视图版本\"权限才能发布视图。"}
             //added by whj for v3 new request
            ,
            {"update","更新"}
            ,
            {"copy", "复制"}
            ,
            {"paste","粘贴"}
            ,
            {"cut","剪切"}
            ,
            {"bmove","部分移动"}
            ,
            {"move","移动"}
            ,
            {"delete","删除"}
            ,
            {"view","查看"}
            ,
            {"checkin","检入"}
            ,
            {"checkin*","检入*"}//CR2
            ,
            {"checkout","检出"}
            ,
            {"checkout*","检出*"}//CR2
            ,
            {"undocheckout","撤销检出"}
            ,
            {"undocheckout*","撤销检出*"}//CR2
            ,
            {"movefromwindow","在窗口中清除"}
            ,
            {"refresh","刷新"}
            ,
            {"jiegou","使用结构"}
            ,
            {"jibenshuxing","基本属性"}
            ,
            {"shiwutexing","事物特性"}
            ,
            {"cankaowendang","参考文档"}
            ,
            {"miaoshuwendang","描述文档"}
            ,
            {"notselectpart","没有选择复制或剪切的零部件"}
            ,
            {"worn","警告！"}
            ,
            {"noupdate","目标对象不是可更新状态，不能执行操作"}
            ,
            {"parentnoupdate","所选对象的父件不是可更新状态，不能执行操作"}
            ,
            {"notbmove","所选对象不能进行部分移动"}
            ,
            {"moveNumber","移动数量"}
            ,
            {"queOk","确定"}
            ,
            {"queCanc","取消"}
            ,
            {"canbemovednumber","当前可移动数量"}
            ,
            {"canbeover","移动数量不能超过："}
            ,
            {"useUnit","使用单位"}
            ,
            {"allMove","全部移动"}
            ,
            {"needNumber","移动数量必须是整数"}
            ,
            {"cannotbeback","移动数量不能为空！"}
            ,
            {"canbezero","移动数量不能是零！"}
            ,
            {"canbenegative","移动数量不能是负数！"}
            ,
            {"confrimdrag", "是否将零部件 * 从零部件 * 中移除,并且添加到零部件 * 中?"}
            ,
            {"onlyaddconfrimdrag", "是否将零部件 * 添加到零部件 * 中?"}
            ,
            {"dragdialogtitle", "是否移动"}
            ,
            {"nestingwaringtext", "添加 * 将导致使用结构嵌套，您不能添加它。"}
            ,
            {"nestingwaringtitle", "使用结构嵌套"}
            
            ,
            {
            CHOOSE_GENERIC_PART_WARNING,"您选择的零部件是广义部件，不能在产品信息管理器里更新。"
            }
            ,
            {
            NOIBA,"您选择的零部件没有任何事物特性可以发布。"
            }
            ,
            {
            ALREADYPUBLISH,"您选择的零部件的事物特性已经发布过了。"
            }
            ,
            {
            IBACOUNT,"您选择的零部件的包含有*个事物特性，是否确认发布。"
            }
            ,
            {
            PUBLISHSURE,"确认发布"
            }
            //liyz add 状态栏应用所有功能的提示信息
            ,
            {
             MANAGE_APPLYALLPART,"提供给用户批量维护零部件基本属性与事物特性的一个功能。"
            }
            ,{"manage.applyAllPart.label", "应用所有(M)"}
            ,
            {
            	WRANG_TYPE_SELECT_TAB,"请选择基本属性或事物特性"
            }
            ,
            //add by muyppeng 20080625 begin
            {BROWSE_APPLYTOALLPARTS,"将所选择的零部件的属性应用到目标零部件。"}
            ,
            {APPLY_ATTRIBUTE_TO_OTHER,"将零部件*的事物特性应用到其他零部件"}
            ,
//          add by muyppeng 20080625 begin 
            {"browse.applyToAllParts.label","应用所有(A)"}
            //end
            ,//liyz add工艺菜单项
            {CAPP_MENU, "此菜单含有工艺浏览器、工艺路线表管理器、工艺汇总浏览器。"}
            ,
            {TECHNICS_REGULATION, "进入工艺浏览器"}
            ,
            {TECHNICS_SUMMARY, "进入工艺汇总浏览器"}
            ,
            {TECHNICS_ROUTE, "进入工艺路线表管理器"}
            ,
            {"capp.label", "工艺(C)"}
            ,
            {"capp.setTechnicsRegulation.label", "工艺浏览器(T)"}
            ,
            {"capp.setTechnicsRoute.label", "工艺路线表管理器(R)"}
            ,
            {"capp.setTechnicsSummary.label", "工艺汇总浏览器(S)"}
//          liyz add 工具栏中添加排序按钮
            ,
            {"partexplorer.explorer.toolbar.iconSort","part_create,part_update,Spacer,public_copy,public_cut,public_paste,Spacer,part_delete,part_view,Spacer,part_checkIn,part_checkOut,part_repeal,Spacer,public_refresh,public_clear,Spacer,public_search,Spacer,public_sort,Spacer,part_applyAll"}//CR1、CR3   
            ,
            {"partexplorer.explorer.toolbar.textSort","创建,更新,Spacer,复制,剪切,粘贴,Spacer,删除,查看,Spacer,检入,检出,撤消检出,Spacer,刷新,在窗口中清除,Spacer,搜索零部件,Spacer,排序,Spacer,应用所有"}//CR1、CR3   
            ,
            {"partexplorer.explorer.toolbar.singleSort","启动“创建零部件”窗口。,更新零部件,Spacer,复制所选零部件。,剪切所选零部件。,粘贴所选零部件。,Spacer,删除所选零部件，被其他零部件使用时无法删除, 启动零部件属性页，它显示选定零部件的属性和关联。,Spacer, 启动所选零部件的检入进程。,启动所选零部件的检出过程。,取消所选零部件的检出。,Spacer, 从数据库中检索所选零部件并用刷新部件替换当前显示的对象。如果所选零部件在树形视图中曾是展开的，现在它会折叠起来。,清除“产品信息管理器” 中的选中的首个零部件。,Spacer, 搜索零部件的基本记录，不包含零部件的版本信息。,Spacer,对所选件子件进行排序，如果没有选择件将对根节点下的零部件进行排序。,Spacer,批量处理零部件。"}//CR1 、CR3 
            ,
            {GPART_ALREADY_CHECKOUT,"该广义部件已被检出！"}//CR4
            
             //CCBegin by liunan 2008-07-30
            , 
            {"browse.BOM.compareList.label", "视图结构比较"}
            , 
            {"browse.BOM.firstLevelSonList.label", "一级子件列表"}
            //CCEnd by liunan 2008-07-30
    };
}
