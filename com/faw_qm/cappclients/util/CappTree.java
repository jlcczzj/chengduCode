/** ���ɳ���CappTree.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: ���ղ��ֵ�����װ��</p>
 * <p>Description: ��Ҫ��CappTreeObject����
 *  �����ҵ������Ϣ��װ��CappTreeObject������</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author �ܴ�Ԫ
 * @version 1.0      2003/08/11
 */
public class CappTree extends JTree
{
    //��Դ����
    private static String __ENCODE__ = "GBK"; //һ��Ҫ��GBK
    private static String __SERVER_ENCODE__ = "GB2312"; //�������ϵ�ȱʡ����


    /**
     * ���ڵ�
     */
    private CappTreeNode root;


    /**
     * ����ģ��
     */
    private DefaultTreeModel myModel;


    /**
     *���캯��
     */
    public CappTree()
    {

    }


    /**
     * ���캯��
     * @param type
     * @type:String �������͡��й���������Դ���ȵȡ�������
     */
    public CappTree(String type)
    {
        super(new CappTreeNode(type));
        this.setRoot((CappTreeNode)this.myModel().getRoot());
        this.myModel.setAsksAllowsChildren(true);
        setCellRenderer(new CappTreeCellRenderer());

    }


    /**
     * �õ���ǰ����ģ��
     * @return DefaultTreeModel
     */
    public DefaultTreeModel myModel()
    {
        myModel = (DefaultTreeModel) getModel();
        return myModel;
    }


    /**
     * �õ����ڵ�
     * @return CappTreeNode
     */
    public CappTreeNode getRoot()
    {
        return this.root;
    }


    /**
     *  ���ø��ڵ�
     *  @param root;CappTreeNode
     */
    public void setRoot(CappTreeNode root)
    {
        this.root = root;
    }


    /**
     * ��ӽڵ�
     * @param father CappTreeNode  ���ڵ�
     * @param child  CappTreeNode  �ӽڵ�
     */
    public CappTreeNode addNode(CappTreeNode father, CappTreeNode child)
    {
        myModel.insertNodeInto(child, father, getLocation(father, child));
        return child;
    }


    /**
     * ��ӽڵ�
     * @param father CappTreeNode  ���ڵ�
     * @param child  CappTreeNode  �ӽڵ�
     */
    public CappTreeNode addNodeNotGetLocation(CappTreeNode father,
                                              CappTreeNode child)
    {
        father.add(child);
        // myModel..insertNodeInto(child, father, getLocation(father, child));
        return child;
    }


    /**
     * ��ӽڵ�
     * @param father CappTreeNode  ���ڵ�
     * @param child  CappTreeNode  �ӽڵ�
     */
    public CappTreeNode addNodeNotGetLocation(CappTreeNode father,
                                              CappTreeObject child)
    {

        myModel.insertNodeInto(new CappTreeNode(child), father, father.getChildCount());
        return father;
    }


    /**
     * �õ��ӽڵ�Ӧ�üӵ����ڵ��λ��
     * @param father  ���ڵ�
     * @param child  �ӽڵ�
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
     * ��ӽڵ�
     * @param father CappTreeNode, ���ڵ�
     * @param object  CappTreeObject  �ӽڵ��װ����
     */
    public CappTreeNode addNode(CappTreeNode father, CappTreeObject object)
    {
        return addNode(father, new CappTreeNode(object));

    }


    /**
     * ɾ��һ���ڵ�
     * @param node CappTreeNode Ҫɾ���Ľڵ�
     * @return CappTreeNode
     */
    public CappTreeNode removeNode(CappTreeNode node)
    {
        this.myModel.removeNodeFromParent(node);
        return node;
    }


    /**
     * ɾ������ָ������Ľڵ�
     * @param object : CappTreeObject ָ���Ķ���
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
     * ɾ�������ڵ�������нڵ�
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
     * ���Ұ���ָ������Ľڵ㼯��
     * @param object CappTreeObject
     * @return  Vector,�ڵ�ļ���
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
     * �ж������Ƿ������ǰ����
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
     * ���Ұ���ָ������Ľڵ�
     * @param object CappTreeObject
     * @return CappTreeNode  ���������Ľڵ�
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
     * ��ʼ��������ʾ��Ϣ
     * @param hashtable Hashtable ���������з�����Ϣ��hashtable
     */
    public void initTypeLabelNode(Hashtable hashtable)
    {

    }


    /**
     * �õ�ѡ�еĶ���
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
        �õ�ѡ�еĽڵ�
        @return CappTreeNode
        @roseuid 3F0630AA01B9
     */
    public CappTreeNode getSelectedNode()
    {
        return (CappTreeNode)this.getLastSelectedPathComponent();
    }


    /**
     * ����������еýڵ�
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
