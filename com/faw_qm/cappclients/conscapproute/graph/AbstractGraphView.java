/** 
 * 生成程序 AbstractGraphView.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.awt.Color;

import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.util.Locale;

import javax.swing.JComponent;

import com.faw_qm.cappclients.conscapproute.util.GraphRB;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;

/**
 * 抽象的图元视图，该方法实现了图元模式和选择模式添加新的监听器，插入删除图元节点 和链接，改变图元节点及链接的属性，实现鼠标的点击、拖拽，双击等功能 <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */
public abstract class AbstractGraphView extends JComponent implements FocusListener, MouseMotionListener, MouseListener, Serializable, GraphModelListener, GraphSelectionModelListener
{
    public static final String FOCUSED_COMPONENT = "focusedComponent";

    public static final String MODEL = "model";

    public static final String SELECTION_MODEL = "selectionModel";

    private static final String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.GraphRB";

    private static boolean VERBOSE;

    public GraphModel model;

    public GraphSelectionModel selectionModel;

    private static AbstractGraphView focusedComponent;

    /**
     * 构造器
     * @roseuid 402971940275
     */
    public AbstractGraphView()
    {
        this(createDefaultGraphModel(), createDefaultGraphSelectionModel());
    }

    /**
     * 构造器
     * @param graphmodel - 传递图元模式的对象
     * @roseuid 402971940273
     */
    public AbstractGraphView(GraphModel graphmodel)
    {
        this(graphmodel, createDefaultGraphSelectionModel());
    }

    /**
     * 构造器
     * @param graphselectionmodel GraphSelectionModel对象
     * @roseuid 402971940271
     */
    public AbstractGraphView(GraphSelectionModel graphselectionmodel)
    {
        this(createDefaultGraphModel(), graphselectionmodel);
    }

    /**
     * 构造器
     * @param graphmodel - 传递图元模式型的对象
     * @param graphselectionmodel - 传递图元选择模式型的对象
     * @roseuid 40297194026E
     */
    public AbstractGraphView(GraphModel graphmodel, GraphSelectionModel graphselectionmodel)
    {
        setBackground(Color.white);
        try
        {
            setSelectionModel(graphselectionmodel);
        }catch(Exception qmpropertyvetoexception)
        {
            try
            {
                if(VERBOSE)
                    System.out.println("Impossible to use the selection model provided, creating a default one.");
                setSelectionModel(createDefaultGraphSelectionModel());
            }catch(Exception qmpropertyvetoexception2)
            {
                qmpropertyvetoexception2.printStackTrace();
            }
        }

        try
        {
            setModel(graphmodel);

            if(VERBOSE)
                System.out.println("Impossible to use the model provided, creating a default one.");

            setModel(createDefaultGraphModel());

        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showWarningDialog(this, message);
            ex.printStackTrace();
        }
        addMouseListener(this);
        addMouseMotionListener(this);
        addFocusListener(this);
    }

    /**
     * 获得焦点组件
     * @return AbstractGraphView
     * @roseuid 402971940276
     */
    public static AbstractGraphView getFocusedComponent()
    {
        return focusedComponent;
    }

    /**
     * 设置焦点组件
     * @param abstractgraphview 抽象图元视图型对象
     * @roseuid 402971940277
     */
    public static void setFocusedComponent(AbstractGraphView abstractgraphview)
    {
        focusedComponent = abstractgraphview;
    }

    /**
     * 得到图元模式型对象的模式
     * @return GraphModel
     * @roseuid 402971940279
     */
    public GraphModel getModel()
    {
        return model;
    }

    /**
     * 设置图元模式。 如果模式已存在，则删除监听器，清空原有的模式，并添加新的监听器，重新绘制模式。
     * @param graphmodel - 图元模式型对象
     * @throws QMException 
     * @throws QMPropertyVetoException
     * @roseuid 40297194027A
     */
    public void setModel(GraphModel graphmodel) throws QMException
    {
        modelValidate(graphmodel);//图元模式型，
        if(graphmodel != null)
        {
            graphmodel.removeGraphModelListener(this);
            selectionModel.clearSelection();
        }
        model = graphmodel;
        model.addGraphModelListener(this);
        repaint();
    }

    /**
     * 判断图元模式是否存在。 如果不存在则创建新的对象，并抛出异常。
     * @param graphmodel 图元模式型的对象
     * @throws QMException 
     * @throws QMPropertyVetoException
     * @roseuid 40297194027C
     */
    private void modelValidate(GraphModel graphmodel) throws QMException //xucy 20111214
    {
        if(graphmodel == null)
        {
            Locale locale = RemoteProperty.getVersionLocale();
            String s = QMMessage.getLocalizedMessage(RESOURCE, GraphRB.PROPERTY_VALUE_IS_EMPTY, null, locale);
            throw new QMException(s);
        }else
        {
            return;
        }
    }

    /**
     * 获得选择模式对象
     * @return GraphSelectionModel
     * @roseuid 40297194027E
     */
    public GraphSelectionModel getSelectionModel()
    {
        return selectionModel;
    }

    /**
     * 设置选择模式。 如果选择模式存在，则移除图元选择模式的监听器，并添加新的监听器，重新绘制模式。
     * @param graphselectionmodel 图元选择模式的对象
     * @throws QMException 
     * @throws QMPropertyVetoException
     * @roseuid 40297194027F
     */
    public void setSelectionModel(GraphSelectionModel graphselectionmodel) throws QMException
    {
        selectionModelValidate(graphselectionmodel);
        if(selectionModel != null)
            selectionModel.removeGraphSelectionModelListener(this);
        selectionModel = graphselectionmodel;
        selectionModel.addGraphSelectionModelListener(this);
        repaint();
    }

    /**
     * 选择模式的验证。 如果图元选择模式不存在，则抛出异常
     * @param graphselectionmodel 图元选择模式型的对象
     * @throws QMException 
     * @throws QMPropertyVetoException
     * @roseuid 402971940281
     */
    private void selectionModelValidate(GraphSelectionModel graphselectionmodel) throws QMException//xucy 20111214 
    {
        if(graphselectionmodel == null)
        {
            Locale locale = RemoteProperty.getVersionLocale();
            String s = QMMessage.getLocalizedMessage(RESOURCE, GraphRB.PROPERTY_VALUE_IS_EMPTY, null, locale);
            throw new QMException(s);
        }else
        {
            return;
        }
    }

    /**
     * 创建缺省的图元模型
     * @return GraphModel
     * @roseuid 402971940283
     */
    public static GraphModel createDefaultGraphModel()
    {
        return new DefaultGraphModel();
    }

    /**
     * 创建缺省的图元选择模型
     * @return 缺省的图元选择模型实例
     * @roseuid 402971940284
     */
    public static GraphSelectionModel createDefaultGraphSelectionModel()
    {
        return new DefaultGraphSelectionModel();
    }

    /**
     * 实现FocusListener监听器中的方法，获得焦点
     * @param focusevent 传递焦点事件对象
     * @roseuid 402971940285
     */
    public void focusGained(FocusEvent focusevent)
    {
        focusedComponent = this;
    }

    /**
     * 实现FocusListener监听器中的方法，用于放弃焦点
     * @param focusevent 传递焦点事件对象
     * @roseuid 402971940287
     */
    public void focusLost(FocusEvent focusevent)
    {

    }

    /**
     * 实现GraphSelectionModelListener监听器中的方法。 选中图元节点。
     * @param graphselectionmodelevent 传递图元选择模式事件的对象
     * @roseuid 402971940289
     */
    public void graphNodeSelected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        repaint();
    }

    /**
     * 实现GraphSelectionModelListener监听器中的方法。 节点没有被选中。
     * @param graphselectionmodelevent 图元选择模式事件的对象
     * @roseuid 40297194028B
     */
    public void graphNodeUnselected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        repaint();
    }

    /**
     * 实现GraphSelectionModelListener监听器中的方法。 链接被选中。
     * @param graphselectionmodelevent 图元选择模式事件的对象
     * @roseuid 40297194028D
     */
    public void graphLinkSelected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        repaint();
    }

    /**
     * 实现GraphSelectionModelListener监听器中的方法。 链接没有被选中。
     * @param graphselectionmodelevent 图元选择模式事件的对象
     * @roseuid 40297194028F
     */
    public void graphLinkUnselected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        repaint();
    }

    /**
     * 实现GraphSelectionModelListener监听器中的方法。 清空所选的节点和链接。
     * @param graphselectionmodelevent 图元选择模式事件的对象
     * @roseuid 402971940291
     */
    public void graphSelectionCleared(GraphSelectionModelEvent graphselectionmodelevent)
    {
        repaint();
    }

    /**
     * 实现GraphSelectionModelListener监听器中的方法。 把在图元模式事件里得到的节点添加到面板上。
     * @param graphmodelevent 传递图元模式事件的对象
     * @roseuid 402971940293
     */
    public void graphNodeInserted(GraphModelEvent graphmodelevent)
    {
        repaint();
    }

    /**
     * 实现GraphSelectionModelListener监听器中的方法。 更新、设置事件属性名在节点哈希表里对应的节点。
     * @param propertychangeevent 传递属性改变事件对象
     * @roseuid 402971940295
     */
    public void graphNodeChanged(PropertyChangeEvent propertychangeevent)
    {
        repaint();
    }

    /**
     * 实现GraphSelectionModelListener监听器中的方法。 删除图元模式事件中得到的节点，把模式设置为指针模式并重新描绘。
     * @param graphmodelevent 传递图元模式事件对象
     * @roseuid 402971940297
     */
    public void graphNodeRemoved(GraphModelEvent graphmodelevent)
    {
        repaint();
    }

    /**
     * 实现GraphSelectionModelListener监听器中的方法。 把当前线程加入到线程组，把图元模式事件得到的连接添加到面板上。
     * @param graphmodelevent 传递图元模式事件对象
     * @roseuid 402971940299
     */
    public void graphLinkInserted(GraphModelEvent graphmodelevent)
    {
        repaint();
    }

    /**
     * 实现GraphSelectionModelListener监听器中的方法。 属性改变事件的属性名是description把它作成标签，不是description则画线链接。
     * @param propertychangeevent 传递属性改变事件对象
     * @roseuid 40297194029B
     */
    public void graphLinkChanged(PropertyChangeEvent propertychangeevent)
    {
        repaint();
    }

    /**
     * 实现GraphSelectionModelListener监听器中的方法。 删除连接并把模式设置为指针模式。
     * @param graphmodelevent 传递图元模式事件对象
     * @roseuid 40297194029D
     */
    public void graphLinkRemoved(GraphModelEvent graphmodelevent)
    {
        repaint();
    }

    /**
     * 实现GraphSelectionModelListener监听器中的方法。 改变图元结构。
     * @param graphmodelevent 传递图元模式事件对象
     * @roseuid 40297194029F
     */
    public void graphStructureChanged(GraphModelEvent graphmodelevent)
    {
        repaint();
    }

    /**
     * 实现MouseListener监听器中的方法，表示点击鼠标时激发的事件
     * @param mouseevent 传递鼠标事件对象
     * @roseuid 4029719402A1
     */
    public void mouseClicked(MouseEvent mouseevent)
    {

    }

    /**
     * 实现MouseListener监听器中的方法，表示按下鼠标时激发的事件
     * @param mouseevent 传递鼠标事件对象
     * @roseuid 4029719402A3
     */
    public void mousePressed(MouseEvent mouseevent)
    {

    }

    /**
     * 实现MouseListener监听器中的方法，表示释放鼠标时激发的事件
     * @param mouseevent 传递鼠标事件对象
     * @roseuid 4029719402A5
     */
    public void mouseReleased(MouseEvent mouseevent)
    {

    }

    /**
     * 实现MouseListener监听器中的方法，空方法
     * @param mouseevent 传递鼠标事件对象
     * @roseuid 4029719402A7
     */
    public void mouseEntered(MouseEvent mouseevent)
    {

    }

    /**
     * 实现MouseListener监听器中的方法，空方法
     * @param mouseevent 传递鼠标事件对象
     * @roseuid 4029719402A9
     */
    public void mouseExited(MouseEvent mouseevent)
    {

    }

    /**
     * 实现MouseMotionListener监听器中的方法。表示拖拽鼠标时激发的事件
     * @param mouseevent 传递鼠标事件对象
     * @roseuid 4029719402AB
     */
    public void mouseDragged(MouseEvent mouseevent)
    {

    }

    /**
     * 实现MouseMotionListener监听器中的方法。表示移动鼠标时激发的事件
     * @param mouseevent 传递鼠标事件对象
     * @roseuid 4029719402AD
     */
    public void mouseMoved(MouseEvent mouseevent)
    {

    }

    /**
     * 判断对象是否存在。
     * @param point - Point型，指定对象的位置
     * @return boolean
     * @roseuid 4029719402AF
     */
    public abstract boolean isObjectExisting(Point point);
}