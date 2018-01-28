/**
 * 程序OtherRB.java 1.0 11/2/2003
 * 版权归一汽启明公司所有
 * 本程序属于一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.part.client.other.util;

import java.util.ListResourceBundle;


/**
 * <p>Title: 资源文件。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 刘明 刘贵志
 * @version 1.0
 */

public class OtherRB extends ListResourceBundle
{

    public OtherRB()
    {
    }

    public Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][] =
            {
            {"doc", "文档"}
            ,
            {"MaterialTitle", "定制物料清单"}
            ,
            {"searchPart", "搜索零部件"}
            ,
            {"Please select output mode", "请选择输出方式!"}
            ,
            {"At least one attribute", "至少在左边的属性中选中一个属性!"}
            ,
            {"standar", "标准"}
            ,
            {"baseLine", "基准线"}
            ,
            {"effectivity", "有效性"}
            ,
            {"value", "*值"}
            ,
            {"search", "搜索(F). . ."}
            ,
            {"commonSearch", "普通搜索"}
            ,
            {"searches", "搜索(S). . ."}
            ,
            {"Not including person folder's part", "不含个人资料夹中的零部件"}
            ,
            {"Don't save filter condition", "不保存筛选条件"}
            ,
            {"original", "源资料夹"}
            ,
            {"goal", "目标资料夹"}
            ,
            {"Move operation object", "更改资料夹"}
            ,
            {"name_check", "非"}
            ,
            {"number_check", "非"}
            ,
            {"unit_check", "合并结果"}
            ,
            {"browse", "浏览"}
            ,
            {"name", "名称"}
            ,
            {"name1", "*名称"}
            ,
            {"number", "编号"}
            ,
            {"view", "视图"}
            ,
            {"state", "状态"}
            ,
            {"exit", "退出"}
            ,
            {"stop", "停止"}
            ,
            {"StructureTitle", "配置规范"}
            ,
            {"DeferenceTitle", "结构差异比较"}
            ,
            {"type", "类型"}
            ,
            {"standard", "标准"}
            ,
            {"date", "日期"}
            ,
            {"lot_num", "批号"}
            ,
            {"serial_num", "序列号"}
            ,
            {"error", "错误"}
            ,
            {"warning", "警告"}
            ,
            {"1", "根据输入的字符串没有找到相应的基准线!"}
            ,
            {"2", "基准线名称不唯一，请重新选择!"}
            ,
            {"3", "根据输入的字符串没有找到相应的有效性方案!"}
            ,
            {"4", "数据类型不匹配，必须为时间类型(yyyy/M/d)!"}
            ,
            {"5", "源零部件"}
            ,
            {"6", "目标零部件"}
            ,
            {"7", "请选择不同的目标零部件或者不同的配置规范!"}
            ,
            {"8", "请选择基准线!"}
            ,
            {"9", "请选择有效性!"}
            ,
            {"10", "请选择有效性类型!"}
            ,
            {"11", "请输入有效性值!"}
            ,
            {"12", "有效性名称与类型不一致!"}
            ,
            {"13", "请选择日期类型!"}
            ,
            {"14", "源资料夹"}
            ,
            {"15", "更改资料夹"}
            ,
            {"16", "序列号和批号型的有效性值 * 必须为数字!"}
            ,
            {"17", "有效性值的长度过长,不能大于100字符!"}
            ,
            {"out", "输出"}
            ,
            {"ok", "确定(Y)"}
            ,
            {"cancel", "取消(C)"}
            ,
            {"all", "全部"}
            ,
            {"grade", "分级"}
            ,
            {"statistics", "统计表"}
            ,
            {"classify", "分类"}
            ,
            {"type", "类型"}
            ,
            {"mayOutput", "可输出属性"}
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
            {"outPut", "输出属性"}
            ,
            {"source", "来源"}
            ,
            {"viewName", "视图"}
            ,
            {"projectTeamName", "工作组"}
            ,
            {"version", "版本"}
            ,
            {"partType", "类型"}
            ,
            {"lifeCycleState", "生命周期状态"}
            ,
            //CCBegin by liunan 2008-07-30
            {"unit", "使用方式"}
            ,
            //CCEnd by liunan 2008-07-30
            {"partName", "名称"}
            ,
            {"partNumber", "编号"}
            ,
            {"quantity", "数量"}
            ,
            {"attributeName", "  属性名"}
            ,
            {"attributeValue", "  属性值"}
            ,
            {"extendSearchTitle", "按扩展属性搜索"}
            ,
            {"basicSearchTitle", "按基本属性搜索"}
            ,
            {"searchJButton", "查找"}
            ,
            {"stopJButton", "停止"}
            ,
            {"uniteJCheckBox", "合并结果"}
            ,
            {"okJButton", "确定(Y)"}
            ,
            {"cancelJButton", "取消(C)"}
            ,
            {"clearJButton", "清除"}
            ,
            {"viewJButton", "查看(V)"}
            ,
            {"extendAttrJPanel", "搜索条件"}
            ,
            {"processing", "正在处理属性"}
            ,
            {"searching", "正在搜索数据库"}
            ,
            {"23", "找到"}
            ,
            {"24", "找到"}
            ,
            {"25", "**个*"}
            ,
            {"26", "**个新加的*"}
            ,
            {"part", "零部件"}
            ,
            {"information", "提示"}
            ,
            {"numberSequence", "按编号排序"}
            ,
            {"nameSequence", "按名称排序"}
            ,
            {"createTime", "按时间排序"}
            ,
            {"versionSequence", "按版本排序"}
            ,
            {"deAscending", "降序"}
            ,
            {"ascending", "升序"}
            ,
            {"sortFrameTitle", "排序"}
            ,
            {"sortOK", "排序(O)"}
            ,
            {"sortCancel", "退出(Q)"}
            ,
            {"noSelectedNodes", "由于您没有选择任何节点，\n我们将对根结点下的零部件排序"}
            ,
            {"noPart", "请先加入零部件"}
            ,
            {"noConfigSpec", "当前用户没有读取配置规范的权限！"}
            ,
            {"norightversion", "根据目标零件配置规范无法获取合适版本！"}
            ,
            {"schema", "C:QMPart; G:搜索条件;A:partNumber;A:partName;A:versionID;A:viewName;A:producedByStr;A:partTypeStr;A:lifeCycleState;A:projectId;A:location;"}
            ,
            {"schema1", "C:QMPart; G:按基本属性;A:partNumber;A:partName;A:versionID;A:viewName;A:producedByStr;A:partTypeStr;A:lifeCycleState;A:projectId;A:location;"}
            ,
            {"state1", "状态："}
            ,
            {"attributeJLabel", "<对象编号 名称 版本>"}
            ,
            {"pathJLabel", "<资料夹路径>"}
            ,
            {"objectType", "对象类型"}
            ,
            {"selectlocation", "选择目标资料夹"}
            ,
            {"routeList", "工艺路线"}
            ,
            {"alternates","替换件"}
            ,
            {"substitutes","结构替换件"}//zz
            ,
            //CCBegin by liunan 2008-07-30
            {"manufactureRoute","制造路线"}
            ,
            {"assemblyRoute","装配路线"}
            ,
            {"remark","备注"}
            ,
            {"routeListNumber","艺准编号"}
            ,
            {"vendor", "供应商"}
            ,
            {"part", "零部件"}
            ,
            {"technicsRoute", "工艺路线"}
            ,
            {"includeTechnicsRoute", "包含工艺路线"}
            //CCEnd by liunan 2008-07-30
    };
}
