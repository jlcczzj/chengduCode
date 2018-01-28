/** 程序 DrawingPanelRB.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2011/12/13 贾浩鑫 修改原因：参见TD问题5415
 */
// CR3  2014/07/31   xianglx CAD类型简图保存两个文件：一个dwg用于查看和编辑，一个jpg用于打印预览

package com.faw_qm.cappclients.beans.drawingpanel;

import java.util.ListResourceBundle;


/**
 * <p>Title:简图bean资源文件 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:faw_qm </p>
 * @author: 管春元
 * @version 1.0  2003/08/28
 */
public class DrawingPanelRB extends ListResourceBundle
{

    /**
     @roseuid 3F4DB3FE0278
     */
    public DrawingPanelRB()
    {

    }


    /**
     @return Object[][]
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents = new String[][]{
	{ "1", "您要创建或编辑简图吗？" },
	{ "2", "简图" },
	{ "3", "简图不存在" },
	{ "4", "您要查看简图吗？" },
	{ "5", "有工艺简图" },
	{ "6", "无工艺简图" },
	{ "7", "选择编辑工艺简图工具" },
	{ "8", "请选择你的AutCad的可执行文件（acad.exe）的位置" },
	{ "9", "cad 路径不存在" },
	{ "10", "图片文件(JPG,TIF)" },
	{ "ok", "确定(Y)" },
	{ "cancel", "取消(C)" },
	{ "browse file", "选择文件" },
	{ "browse", "浏览(B)" },
	{ "edit", "编辑" },
	{ "view", "查看" },
	{ "delete", "删除" },
	{ "editTip", "编辑简图" },
	{ "viewTip", "查看简图" },
	{ "deleteTip", "删除简图" },
	//{"path","C:/_te_eo%fs$ie@o$%#.info"}
    //2008.05.29 刘志城修改 修改原因：用于本地生成文件读取。应改为：读资源文件读取。
	//Begin CR1
//    {"11", "c:/_te_eo%fs$ie@o$%#.info"},
//    {"tempDxfPath", "c:/_te_eo%fs$ie@o$%#.dxf"},
//    {"tempViewDxfPath", "c:/_te_eo%fs$ie@o$%#view.dxf"},
//    {"tempViewPicPath", "c:/_te_eo%fs$ie@o$%#view"},
	{"11", "_te_eo%fs$ie@o$%#.info"},
//Begin  CR3	
//                                       {"tempDxfPath", "_te_eo%fs$ie@o$%#.dxf"},
																				//保存的cad文件以什么形式显示，新改为为jpg文件，这样还要保存一个dwg文件；
																				//如旧的这是保存成一个dxf文件，把下面两个设置改为“te_eo%fs$ie@o$%#.dxf”和“Autocad”
                                       {"tempDxfPath", "_te_eo%fs$ie@o$%#.jpg"},
                                       {"cadFileDisplay", "jpg"},   
                                       {"tempDxfPathChange", "_te_eo%fs$ie@o$%#.dxf"},//转换旧的历史数据使用
                                       {"tempDwgPath", "_te_eo%fs$ie@o$%#.dwg"},
//End  CR3	
    {"tempViewDxfPath", "_te_eo%fs$ie@o$%#view.dxf"},
    {"tempViewPicPath", "_te_eo%fs$ie@o$%#view"},
	//End CR1
    //2008.11.19 刘志城修改 修改原因：上传简图时，若没有上传简图文件，应提示简图文件不存在。
    {"drawDataNull","简图文件错误或不存在，请重新上传简图文件"},
    {"warning","警告"},
    {"drawSeize","查看简图失败！可能是生成本地文件已被另一个程序正在使用，请先关闭已查看过的简图文件。"}

    

    };
}
