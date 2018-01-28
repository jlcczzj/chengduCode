/** 生成程序CappTree.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */


package com.faw_qm.cappclients.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.faw_qm.clients.util.FolderCompare;
import com.faw_qm.jview.chrset.DataDisplay;
import com.faw_qm.wip.model.WorkableIfc;


/**
 * <p>Title: 工艺部分的树封装类</p>
 * <p>Description: 主要挂CappTreeObject对象
 *  具体的业务类信息封装在CappTreeObject对象里</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 管春元
 * @version 1.0      2003/08/11
 */
public class CappTree extends JTree
{
    //资源变量
    private static String __ENCODE__ = "GBK"; //一定要是GBK
    private static String __SERVER_ENCODE__ = "GB2312"; //服务器上的缺省编码


    /**
     * 根节点
     */
    private CappTreeNode root;


    /**
     * 树的模型
     */
    private DefaultTreeModel myModel;


    /**
     *构造函数
     */
    public CappTree()
    {

    }


    /**
     * 构造函数
     * @param type
     * @type:String 树的类型。有工艺树，资源树等等。。。。
     */
    public CappTree(String type)
    {
        super(new CappTreeNode(type));
        this.setRoot((CappTreeNode)this.myModel().getRoot());
        this.myModel.setAsksAllowsChildren(true);
        setCellRenderer(new CappTreeCellRenderer());

    }


    /**
     * 得到当前的树模型
     * @return DefaultTreeModel
     */
    public DefaultTreeModel myModel()
    {
        myModel = (DefaultTreeModel) getModel();
        return myModel;
    }


    /**
     * 得到跟节点
     * @return CappTreeNode
     */
    public CappTreeNode getRoot()
    {
        return this.root;
    }


    /**
     *  设置跟节点
     *  @param root;CappTreeNode
     */
    public void setRoot(CappTreeNode root)
    {
        this.root = root;
    }


    /**
     * 添加节点
     * @param father CappTreeNode  父节点
     * @param child  CappTreeNode  子节点
     */
    public CappTreeNode addNode(CappTreeNode father, CappTreeNode child)
    {
        myModel.insertNodeInto(child, father, getLocation(father, child));
        return child;
    }


    /**
     * 添加节点
     * @param father CappTreeNode  父节点
     * @param child  CappTreeNode  子节点
     */
    public CappTreeNode addNodeNotGetLocation(CappTreeNode father,
                                              CappTreeNode child)
    {
        father.add(child);
        // myModel..insertNodeInto(child, father, getLocation(father, child));
        return child;
    }


    /**
     * 添加节点
     * @param father CappTreeNode  父节点
     * @param child  CappTreeNode  子节点
     */
    public CappTreeNode addNodeNotGetLocation(CappTreeNode father,
                                              CappTreeObject child)
    {

        myModel.insertNodeInto(new CappTreeNode(child), father, father.getChildCount());
        return father;
    }


    /**
     * 得到子节点应该加到父节点的位置
     * @param father  父节点
     * @param child  子节点
     * @return
     */
    private int getLocation(CappTreeNode father, CappTreeNode child)
    {
        CappTreeNode node;
        if (!(child.getObject() instanceof BusinessTreeObject))
        {
            return father.getChildCount();
        }
        for (int i = 0; i < father.getChildCount(); i++)
        {
            node = (CappTreeNode) father.getChildAt(i);
            int j = compareTowNode(child, node);
            if (j == -1)
            {
                return i;
            }
            else
            if (j == 0)
            {
                if (child.getObject().getObject() instanceof WorkableIfc)
                {
                    if (((WorkableIfc) child.getObject().getObject()).
                        getWorkableState().equals("c/o"))
                    {
                        return i;
                    }
                }
            }
        }
        return father.getChildCount();
    }

    private int compareTowNode(CappTreeNode node, CappTreeNode node1)
    {
        if (node.getObject() instanceof BusinessTreeObject)
        {
            Object[] obj1 = ((BusinessTreeObject) node.getObject()).
                            getOrderNote();
            Object[] obj2 = ((BusinessTreeObject) node1.getObject()).
                            getOrderNote();
            String type = (String) obj1[0];
            Object value1 = obj1[1];
            Object value2 = obj2[1];
            int i = 0;
            if (type.equals("java.lang.String"))
            {
                i = FolderCompare.compare((String) value1, (String) value1,
                                          (String) value2, (String) value2);
            }
            else
            if (type.equals("int"))
            {
                i = ((Integer) value1).intValue() - ((Integer) value2).intValue();
            }
            else
            if (type.equals("java.util.Vector"))
            {
                String s1 = DataDisplay.translate((Vector) value1);
                String s2 = DataDisplay.translate((Vector) value2);
                i = FolderCompare.compare(s1, s1, s2, s2);
            }
            else
            if (type.equals("com.faw_qm.codemanage.model.CodingIfc"))
            {
                i = FolderCompare.compare(value1.toString(), value1.toString(),
                                          value2.toString(), value2.toString());
            }
            else
            if (type.equals("com.faw_qm.framework.service.BaseValueInfo"))
            {
                i = FolderCompare.compare(value1.toString(), value1.toString(),
                                          value2.toString(), value2.toString());
            }

            if (i > 0)
            {
                return 1;
            }
            else if (i < 0)
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }

        return 0;
    }


    /**
     * 添加节点
     * @param father CappTreeNode, 父节点
     * @param object  CappTreeObject  子节点封装对象
     */
    public CappTreeNode addNode(CappTreeNode father, CappTreeObject object)
    {
        return addNode(father, new CappTreeNode(object));

    }


    /**
     * 删除一个节点
     * @param node CappTreeNode 要删除的节点
     * @return CappTreeNode
     */
    public CappTreeNode removeNode(CappTreeNode node)
    {
        this.myModel.removeNodeFromParent(node);
        return node;
    }


    /**
     * 删除包含指定对象的节点
     * @param object : CappTreeObject 指定的对象
     */
    public void removeNodes(CappTreeObject object)
    {
        Vector vec = findNodes(object);
        for (int i = 0; i < vec.size(); i++)
        {
            this.removeNode((CappTreeNode) vec.elementAt(i));
        }
    }


    /**
     * 删除初根节点外的所有节点
     */
    public void removeAllExceptRoot()
    {
        Enumeration en = getRoot().depthFirstEnumeration();
        Vector vec = new Vector();
        while (en.hasMoreElements())
        {
            CappTreeNode qmnode = (CappTreeNode) en.nextElement();
            if ((qmnode.getObject()) != null &&
                qmnode.getObject().getType() == CappTreeObject.BUSINESSOBJECT)
            {
                vec.add(qmnode);
            }
        }
        for (int i = 0; i < vec.size(); i++)
        {
            myModel.removeNodeFromParent((CappTreeNode) vec.elementAt(i));
        }
    }


    /**
     * 查找包含指定对象的节点集合
     * @param object CappTreeObject
     * @return  Vector,节点的集合
     */
    public Vector findNodes(CappTreeObject object)
    {
        Vector vector = new Vector();
        String s1 = object.getUniqueIdentity();
        String s2;
        Enumeration en = (((CappTreeNode) (myModel().getRoot())).
                          depthFirstEnumeration());
        while (en.hasMoreElements())
        {
            CappTreeNode qmnode = (CappTreeNode) en.nextElement();
            CappTreeObject obj;
            if ((obj = qmnode.getObject()) != null)
            {
                s2 = obj.getUniqueIdentity();
                if (s1.equals(s2))
                {
                    vector.add(qmnode);
                }
            }
        }
        return vector;
    }


    /**
     * 判断树上是否包含当前对象
     * @param obj
     * @return
     */
    public boolean isInTree(CappTreeObject obj)
    {
        String s1 = obj.getUniqueIdentity();
        String s2;
        Enumeration en = (((CappTreeNode) (myModel().getRoot())).
                          depthFirstEnumeration());
        while (en.hasMoreElements())
        {
            CappTreeNode qmnode = (CappTreeNode) en.nextElement();
            CappTreeObject obj1;
            if ((obj1 = qmnode.getObject()) != null)
            {
                s2 = obj1.getUniqueIdentity();
                if (s1.equals(s2))
                {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 查找包含指定对象的节点
     * @param object CappTreeObject
     * @return CappTreeNode  满足条件的节点
     */
    public CappTreeNode findNode(CappTreeObject object)
    {
        Vector vec = findNodes(object);
        if (vec.size() > 0)
        {
            return (CappTreeNode) vec.elementAt(0);
        }
        else
        {
            return null;
        }
    }


    /**
     * 初始化分类显示信息
     * @param hashtable Hashtable ：包含所有分类信息的hashtable
     */
    public void initTypeLabelNode(Hashtable hashtable)
    {

    }


    /**
     * 得到选中的对象
     * @return CappTreeObject
     */
    public CappTreeObject getSelectedObject()
    {
        CappTreeNode node = getSelectedNode();
        if (node == null)
        {
            return null;
        }
        return this.getSelectedNode().getObject();
    }


    /**
        得到选中的节点
        @return CappTreeNode
        @roseuid 3F0630AA01B9
     */
    public CappTreeNode getSelectedNode()
    {
        return (CappTreeNode)this.getLastSelectedPathComponent();
    }


    /**
     * 获得树上所有得节点
     * @return QMNode[]
     */
    public CappTreeNode[] getSelectedNodes()
    {
        CappTreeNode[] nodes;
        TreePath[] path = getSelectionPaths();
        nodes = new CappTreeNode[path.length];
        if (path != null && path.length > 0)
        {
            for (int i = 0, j = path.length; i < j; i++)
            {
                Object[] obj = path[i].getPath();
                nodes[i] = (CappTreeNode) obj[obj.length - 1];
            }
        }
        return nodes;
    }

}
