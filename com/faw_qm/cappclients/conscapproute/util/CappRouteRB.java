/**
 * 生成程序 CappRouteRB.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2011/12/22 零件列表里增加添加零件快捷方式
 * CR2 2012/04/09 吕航原因参见TD5996
 * SS1 新增通过解放艺准通知书查找零部件 liunan 2013-10-24
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.util.ListResourceBundle;

/**
 * Title:工艺路线模块的资源类 Description: Copyright: Copyright (c) 2004 Company: 一汽启明
 * @author 刘明
 * @version 1.0
 */
public class CappRouteRB extends ListResourceBundle
{
    // 常量定义
    public static final String MISSING_RESOURCER = "1";

    public static final String NO_CHANGE_MADE = "2";

    public static final String NO_NUMBER_ENTERED = "3";

    public static final String NO_NAME_ENTERED = "4";

    public static final String REQUIRED_FIELDS_MISSING = "5";

    public static final String NO_LOCATION_ENTERED = "6";

    public static final String LOCATION_NOT_VALID = "7";

    public static final String LOCATION_NOT_PERSONAL_CABINET = "8";

    public static final String SAVING = "9";

    public static final String ADD_RECYCLE = "10";

    public static final String NO_PRODUCT_ENTERED = "11";

    public static final String IS_SAVE_CREATE_ROUTELIST = "12";

    public static final String IS_SAVE_UPDATE_ROUTELIST = "13";

    public static final String PATH_NOT_EXIST = "14";

    public static final String FILE_ALREADY_EXIST = "15";

    public static final String IS_SAVE_CREATE_ROUTE = "16";

    public static final String IS_SAVE_UPDATE_ROUTE = "17";

    public static final String WRONG_TYPE_OBJECT = "20";

    public static final String CONFIRM_DELETE_OBJECT = "21";

    public static final String SYSTEM_ERROR = "22";

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

    public static final String NOT_CHECKOUT_OWNER_DISPLAY = "41";

    public static final String NOT_PERMITED_CHECK_OUT = "42";

    public static final String NOU_CHECK_OUT_BY_YOU = "43";

    public static final String VERIFY_UNDO_CHECKOUT_NOT_OWNER = "44";

    public static final String CAN_NOT_EDIT_ROUTE = "45";

    public static final String NOT_HAVE_PART = "46";

    public static final String CANNOT_MODIFY_ORIGINAL_OBJECT = "47";

    public static final String NO_DEPARTMENT_ENTER = "48";

    public static final String CAN_NOT_COPY_ROUTE = "49";

    public static final String PART_NOT_HAVE_ROUTE = "50";

    public static final String IS_EXIT_SYSTEM = "51";

    /**
     * 构造资源类
     * @roseuid 4031A67602F2
     */
    public CappRouteRB()
    {}

    /**
     * 获得资源内容
     * @return Object[][] 对象2维数组
     * @roseuid 4031A6760325
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][] = {

            // JButtons
            {"OkJButton", "确定"},
            {"CancelJButton", "取消"},
            {"SaveJButton", "保存"},
            {"QuitJButton", "退出"},
            {"addJButton", "添加"},
            {"deleteJButton", "删除"},
            {"searchTreeJButton", "搜索"},
            {"upJButton", "上移"},
            {"downJButton", "下移"},
            {"browseJButton", "浏览"},

            // JLabel
            {"checking in", "正在检入"},
            {"describe", "注释"},
            {"reviseTypeJLabel", "修订路线表"},
            {"descriJLabel", "说明"},

            // Titles
            {"exception", "异常信息"},
            {"default_status", "欢迎进入工艺路线管理器. . ."},
            {"information", "提示"},
            {"error", "错误"},
            {"warning", "警告"},
            {"notice", "通知"},
            {"afreshAssignLifeCycle", "重新指定生命周期"},
            {"afreshAssignProject", "重新指定工作组"},
            {"findPartTitle", "搜索零部件"},
            {"reviseTitle", "修订"},
            {"checkInTitle", "检入工艺路线表"},
            {"renameRouteListTitle", "重命名工艺路线表"},
            {"toolbar.icons",
                    "routeList_create,routeList_update,routeList_delete,Spacer,routeList_checkIn,routeList_checkOut,routeList_repeal,Spacer,routeList_view,public_search,Spacer,route_edit,Spacer,public_help"},
            {"toolbar.text", "创建,更新,删除,Spacer,检入,检出,撤消检出,Spacer,查看,搜索,Spacer,编辑路线,Spacer,帮助"}, {"toolbar.discribe", "创建,更新,删除,Spacer,检入,检出,撤消检出,Spacer,查看,搜索,Spacer,编辑路线,Spacer,帮助"},
            //begin 20120105 xucy add
            {"routetoolbar.icons", "Spacer,Spacer,Spacer,public_copy,public_paste,route_delete,route_view,public_refresh,public_clear"},
            {"routetoolbar.text", "Spacer,Spacer,Spacer,复制路线(Ctrl+C),粘贴路线(Ctrl+V),删除路线(Ctrl+M),复制自,保存为典型路线,应用典型路线"},
            {"routetoolbar.descripe", "Spacer,Spacer,Spacer,复制路线,粘贴路线,删除路线,复制自,保存为典型路线,应用典型路线"},
            //end 20120105 xucy add
            {"1", "缺乏必须的资源。"}, {"2", "*并没有发生任何更改。"},
            {"3", "艺准编号不能为空。"}, {"4", "艺准名称不能为空。"}, {"5", "缺少必需的字段"}, {"6", "资料夹位置不能为空。"}, {"7", "资料夹路径无效"}, {"8", "所指定的资料夹不是您的个人资料夹"}, {"9", "正在保存..."}, {"10", "零部件*已存在，不可重复添加。"},
            {"11", "“用于产品”项不能为空。"}, {"12", "是否保存新建路线表？"}, {"13", "是否保存更新路线表？"}, {"14", "指定的路径不存在或不可用。请确认您是否录入了正确的文件扩展名。"}, {"15", "文件已存在,请重新指定文件名。"}, {"16", "是否保存新建路线？"}, {"17", "是否保存更新路线？"},

            {"20", "选择的对象类型错误。"}, {"21", "删除操作将无法恢复，是否继续？"}, {"22", "系统配置错误，请与系统管理员联系！"},

            {"23", "必须指定资料夹位置，才能执行检入。请选择资料夹。"}, {"24", "检入*失败，因为主要内容的上载或存储过程中出现错误。"}, {"25", "*正处于非首次检入状态。请先检出它，再执行检入操作。"}, {"26", "您没有选择操作对象，无法执行当前操作。"}, {"27", "*已经被用户*检出。"}, {"28", "*已经被您检出。"},
            {"29", "在初始化本地化资源时出错."}, {"30", "您确实要撤消检出*，并丢失所有变更吗？"}, {"31", "*已经被检出到资料夹*中！"}, {"32", "在搜索检出资料夹 * 时发生错误."}, {"33", "*已经被他人检出!"}, {"34", "检出*失败！"}, {"35", "您未获得修订*的许可，因为它尚未被检入。"},
            {"36", "*已经被*检出，您确实要检入它吗？"}, {"37", "尚未指定存放当前对象的副本的资料夹。必须给定检出资料夹，才能检出。"}, {"38", "无法找到包含*的工作副本的资料夹，因为*没有被检出。"}, {"39", "不能删除*,因为它已经被检出!"}, {"40", "*需要检出到个人资料夹中才能修改,现在要检出吗？"},
            {"41", "您尚未检出*，它当前正被*检出。"}, {"42", "您未获得检出*的许可，因为它尚未被检入。"}, {"43", "您尚未检出*。"}, {"44", "*已被*检出。您确实要撤销检出并丢弃所有变更吗？"},
            {"45", "请选择欲编辑路线的零部件。"},
            {"46", "当前没有可供选择的零部件。"},
            // { "47", "*是工作副本的原本，不允许修改！" }, { "48", "单位不能为空。" },
            {"47", "*被用户*检出，不允许修改！"}, {"48", "单位不能为空。"}, {"49", "零部件*的工艺路线已存在,不能创建新的工艺路线。"}, {"50", "零部件*的工艺路线不存在，不能执行当前操作。"}, {"51", "是否退出工艺路线管理器？"}, {"52", "编号不能大于30个字符,请重新输入"},
            {"53", "名称不能大于200个字符,请重新输入"}, {"54", "说明不能大于2000个字符,请重新输入"}, {"0", "测试"}, {"55", "零部件*没有符合配置规范的版本，不可添加。"}, {"Help/Summary/QMSummary", "help/zh_cn/routlist/index.html"},
            {"Desc/Summary/QMSummary", ""},
            //CR1 begin
            //begin 20120105 xucy add
            //CCBegin SS1
            //{"addparttoolbar.icons1", "routeList_create,routeList_update,changeNotice,part_openIcon"}, {"addparttoolbar.text1", "批量添加(Ctrl+P),从产品结构中添加(Ctrl+B),按变更采用通知书添加(Ctrl+N),添加(Ctrl+J)"},
            //{"addparttoolbar.discribe1", "批量添加,从产品结构中添加,按变更采用通知书添加,添加"},
            {"addparttoolbar.icons1", "routeList_create,routeList_update,changeNotice,part_openIcon,partM_aptProjectManager"}, {"addparttoolbar.text1", "批量添加(Ctrl+P),从产品结构中添加(Ctrl+B),按变更采用通知书添加(Ctrl+T),添加(Ctrl+J),按解放艺准通知书添加(Ctrl+N)"},
            {"addparttoolbar.discribe1", "批量添加,从产品结构中添加,按变更采用通知书添加,添加,按解放艺准(毕)通知书添加"},
            //CCEnd SS1
            
            {"addparttoolbar.icons2", "routeList_update"}, {"addparttoolbar.text2", "从产品结构中添加"},
            {"addparttoolbar.discribe2", "从产品结构中添加"},
            //CR2 begin
            {"parttoolbar.icons", "Spacer,Spacer,Spacer,part_view,route_editGraph,route_parentPart,routeCode_delete,part_deletestr,public_moveUp,public_moveDown"},
            {"parttoolbar.text", "Spacer,Spacer,Spacer,查看零件(Alt+V),路线图(Ctrl+R),装配位置(Ctrl+L),删除装配位置,删除零件(Ctrl+E),上移(Ctrl+↑),下移(Ctrl+↓)"}, {"parttoolbar.descripe", "Spacer,Spacer,Spacer,查看零件,路线图,装配位置,删除装配位置,删除零件,上移,下移"},
          //CR2 end
            //end 20120105 xucy add
            //CR2 begin
            {"checkin*", "检入*"}, {"checkout*", "检出*"}, {"undocheckout*", "撤销检出*"},
            //CR2 end
            //20120111 xucy add
          
            {"56", "工艺路线表*尚未检入，不能发布路线。"}, {"57", "工艺路线表*发布成功。"}, {"58", "快捷键*没有定义对应的路线信息,不能应用。"}, {"59", "零件*无路线，不能复制路线"},{"60", "路线单位*不存在"}};

}
