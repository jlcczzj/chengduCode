/** 
 * 生成程序 GraphSelectionModelListener.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.util.EventListener;

/**
 * <p> Title:图元选择模式（被选择或未被选择）监听器 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */
public interface GraphSelectionModelListener extends EventListener
{
    public static final int NODE_SELECTED = 0;

    public static final int NODE_UNSELECTED = 1;

    public static final int LINK_SELECTED = 2;

    public static final int LINK_UNSELECTED = 3;

    public static final int SELECTION_CLEARED = 4;

    /**
     * 接点被选择
     * @param graphselectionmodelevent - 传入GraphSelectionModelEvent的对象
     * @roseuid 4028D3D8016A
     */
    public abstract void graphNodeSelected(GraphSelectionModelEvent graphselectionmodelevent);

    /**
     * 接点没有被选择
     * @param graphselectionmodelevent - 传入GraphSelectionModelEvent的对象
     * @roseuid 4028D3D8016C
     */
    public abstract void graphNodeUnselected(GraphSelectionModelEvent graphselectionmodelevent);

    /**
     * 链接被选择
     * @param graphselectionmodelevent - 传入GraphSelectionModelEvent的对象
     * @roseuid 4028D3D8016E
     */
    public abstract void graphLinkSelected(GraphSelectionModelEvent graphselectionmodelevent);

    /**
     * 链接没有被选择
     * @param graphselectionmodelevent - 传入GraphSelectionModelEvent的对象
     * @roseuid 4028D3D80170
     */
    public abstract void graphLinkUnselected(GraphSelectionModelEvent graphselectionmodelevent);

    /**
     * 清空选择的节点或链接
     * @param graphselectionmodelevent - 传入GraphSelectionModelEvent的对象
     * @roseuid 4028D3D80172
     */
    public abstract void graphSelectionCleared(GraphSelectionModelEvent graphselectionmodelevent);
}