/** 
 * 生成程序 GraphSelectionModel.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

/**
 * <p> Title:图元选择模式（被选择或未被选择）接口 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */
public interface GraphSelectionModel
{

    /**
     * 判断节点是否被选
     * @param graphnode - 传入GraphNode的对象
     * @return boolean
     * @roseuid 4028D38C037C
     */
    public abstract boolean isSelected(GraphNode graphnode);

    /**
     * 判断链接是否被选
     * @param graphlink - 传入GraphLink的对象
     * @return boolean
     * @roseuid 4028D38C037E
     */
    public abstract boolean isSelected(GraphLink graphlink);

    /**
     * 得到被选择的节点 的总数
     * @return int
     * @roseuid 4028D38C0380
     */
    public abstract int getNodesSelectedCount();

    /**
     * 得到被选择的链接的总数
     * @return int
     * @roseuid 4028D38C0381
     */
    public abstract int getLinksSelectedCount();

    /**
     * 清空选择的节点或链接
     * @roseuid 4028D38C0382
     */
    public abstract void clearSelection();

    /**
     * 添加节点
     * @param graphnode - 传入GraphNode的对象
     * @roseuid 4028D38C0383
     */
    public abstract void add(GraphNode graphnode);

    /**
     * 删除节点
     * @param graphnode - 传入GraphNode的对象
     * @roseuid 4028D38C0385
     */
    public abstract void remove(GraphNode graphnode);

    /**
     * 添加链接
     * @param graphlink - 传入GraphLink的对象
     * @roseuid 4028D38C0387
     */
    public abstract void add(GraphLink graphlink);

    /**
     * 删除链接
     * @param graphlink - 传入GraphLink的对象
     * @roseuid 4028D38C0389
     */
    public abstract void remove(GraphLink graphlink);

    /**
     * 得到所有被选择的节点
     * @return java.util.Enumeration
     * @roseuid 4028D38C038B
     */
    public abstract java.util.Enumeration allSelectedNodes();

    /**
     * 得到所有被选择的链接
     * @return java.util.Enumeration
     * @roseuid 4028D38C038C
     */
    public abstract java.util.Enumeration allSelectedLinks();

    /**
     * 添加图元选择模式监听器
     * @param graphselectionmodellistener - 传入GraphSelectionModelListener的对象
     * @roseuid 4028D38C038D
     */
    public abstract void addGraphSelectionModelListener(GraphSelectionModelListener graphselectionmodellistener);

    /**
     * 删除图元选择模式监听器
     * @param graphselectionmodellistener - 传入GraphSelectionModelListener的对象
     * @roseuid 4028D38C038F
     */
    public abstract void removeGraphSelectionModelListener(GraphSelectionModelListener graphselectionmodellistener);

    /**
     * 得到最后选择的节点
     * @return GraphNode
     * @roseuid 4028D38C0391
     */
    public abstract GraphNode getLastSelectedNode();

    /**
     * 得到最后选择的链接
     * @return GraphLink
     * @roseuid 4028D38C0392
     */
    public abstract GraphLink getLastSelectedLink();
}