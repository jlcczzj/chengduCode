/**
 * 生成程序ERPResource.java	1.0              2006-11-16
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.util;

import java.util.ListResourceBundle;

/**
 * <p>Title: 资源文件。</p>
 * <p>Description: 中文信息。</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 谢斌
 * @version 1.0
 */
public class ERPResource extends ListResourceBundle
{
    /**
     * 构造函数。
     */
    public ERPResource()
    {
        super();
    }

    /* （非 Javadoc）
     * @see java.util.ListResourceBundle#getContents()
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][] = {
            {"Util.0", "发布失败！"},
            {"Util.1", "实例化名为*的类时出错！"},
            {"Util.2", "访问名为*的类时出错！"},
            {"Util.3", "名为*的类在系统中不存在！"},
            {"Util.4", "创建XML文件失败！"},
            {"Util.5", "读取名为*的文件失败！"},
            {"Util.6", "名为*的类不存在！"},
            {"Util.7", "刷新属性容器时出错！"},
            {"Util.8", "计量单位格式出错！"},
            {"Util.9", "出现不兼容单位！"},
            {"Util.10", "发布器为空，发布取消！"},
            {"Util.11", "发布源为空，发布取消！"},
            {"Util.12", "获取事物特性时出错！"},
            {"Util.13", "获取子零部件时出错！"},
            {"Util.14", "获取父零部件时出错！"},
            {"Util.15", "查找编号为*的FilterPart时出错！"},
            {"Util.16", "保存对象*时出错！"},
            {"Util.17", "获取名为*的零部件结构时出错！"},
            {"Util.18", "获取视图的时候出错！"},
            {"Util.19", "构造查询条件时出错！"},
            {"Util.20", "获取编号为*的零部件的基本信息时出错！"},
            {"Util.21", "获取编号为*的零部件的小版本对象时出错！"},
            {"Util.22", "通过采用通知书或更改采用通知书获取关联的零部件失败！"},
            {"Util.23", "获取编号为*的零部件的前一版本出错！"},
            {"Util.24", "开始创建XML文档。"},
            {"Util.25", "成功创建名称为*的XML文档，文件大小为 * 字节，保存在应用服务器*的路径下。"},
            {"Util.26", "开始组织发布数据，数据源为：**。"},
            {"Util.27", "完成组织发布数据。"},
            {"Util.28", "开始保存FilterPart数据。"},
            {"Util.29", "完成保存FilterPart数据。"},
            {"Util.30", "完成上传文件。"},
            {"Util.31", "上传文件失败！"},
            {"Util.32", "服务器连接不可用，取消上传文件操作！"},
            {"Util.33", "上传文件为空，取消上传文件操作！"},
            {"Util.34", "共发布*条名称为*的数据。"},
            {"Util.35", "开始上传文件。"},
            {"Util.36", "*开始上传文件。"},
            {"Util.37", "目标FTP服务器为*。"},
            {"Util.38", "FTP服务器端口号为*。"},
            {"Util.39", "FTP用户名为*。"},
            {"Util.40", "FTP密码为*。"},
            {"Util.41", "上传文件名称为*。"},
            {"Util.42", "更新发布标识出错！"},
            {"Util.43", "*完成上传文件。"},
            {"Util.44", "共保存*条FilterPart数据。"},
            {"Util.45", "上传文件的本地路径为*。"},
            {"Util.46", "采用通知书已经发布过,不允许修改!"},
            {"Util.47", "开始附加变更通知单.html！"},
            {"Util.48", "完成附加变更通知单.html！"},
            {"Util.49", "附加变更通知单.html失败！"}, 
            {"Util.50", "没有需要发布的零部件数据！"}, 
            {"Util.51", "查找编号为*的零部件对应的物料时出错！"},
            {"Util.52", "上传文件大小为 * 字节。"},
            {"Util.53", "该文件*已经存在，创建失败！"},
            {"Util.61", "关联*条零部件信息。"}, 
            {"Util.62", "需要发布*条零部件信息。"}, 
            {"Util.63", "查找编号为*，版本为*的零部件最新版序时出错！"}, 
            {"Util.64", "无法得到物料号为*的物料拆分的零部件的信息!"}, 
            {"Util.65", "查找编号为*，版本为*的零部件时出错！"}, 
            {"Util.66", "刷新物料号为*的物料时出错！"}, 
            {"Util.67", "获取BsoID为*的零部件主信息时出错！"}, 
            {"Util.68", "处理物料的发布数据时出错！"}, 
            {"Util.69", "构造更改采用通知书*关联的零部件的查询条件时出错！"}, 
            {"Util.70", "查询更改采用通知书*关联的零部件时出错！"}, 
            {"Util.71", "查找编号为*，父物料号为*的物料结构时出错！"}, 
            {"Util.72", "获取编号为*的零部件上一个发布版本时出错！"}, 
            {"Util.81", "发布的工艺的编号为*，名称为*，版本为*，种类为*。"}, 
            {"Util.82", "获取工艺相关的信息时出错！"}, 
            {"Util.83", "有回头件"}, 
            {"Util.84", "没有回头件"},
            {"Util.85", "定员的数据定义格式有误！"}, 
            {"Util.86", "没有需要发布的零部件数据，发布取消！"}, 
            {"IntePack.01", "集成包*的来源不能为空！"},
            {"IntePack.02", "没有找到集成包默认管理域！"},
            {"IntePack.03", "用户*不是该集成包的创建者，无法发布集成包！"},
            {"IntePack.04", "集成包服务:对该集成包无发布权限，不能发布集成包 * ，请检查权限！"},
            {"IntePack.attrValidate", "*长度不符合要求！"}, 
            {"IntePack.intePackName", "集成包名称"}};
}
