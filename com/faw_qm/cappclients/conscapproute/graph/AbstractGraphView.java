/** 
 * ���ɳ��� AbstractGraphView.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * �����ͼԪ��ͼ���÷���ʵ����ͼԪģʽ��ѡ��ģʽ����µļ�����������ɾ��ͼԪ�ڵ� �����ӣ��ı�ͼԪ�ڵ㼰���ӵ����ԣ�ʵ�����ĵ������ק��˫���ȹ��� <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
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
     * ������
     * @roseuid 402971940275
     */
    public AbstractGraphView()
    {
        this(createDefaultGraphModel(), createDefaultGraphSelectionModel());
    }

    /**
     * ������
     * @param graphmodel - ����ͼԪģʽ�Ķ���
     * @roseuid 402971940273
     */
    public AbstractGraphView(GraphModel graphmodel)
    {
        this(graphmodel, createDefaultGraphSelectionModel());
    }

    /**
     * ������
     * @param graphselectionmodel GraphSelectionModel����
     * @roseuid 402971940271
     */
    public AbstractGraphView(GraphSelectionModel graphselectionmodel)
    {
        this(createDefaultGraphModel(), graphselectionmodel);
    }

    /**
     * ������
     * @param graphmodel - ����ͼԪģʽ�͵Ķ���
     * @param graphselectionmodel - ����ͼԪѡ��ģʽ�͵Ķ���
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
     * ��ý������
     * @return AbstractGraphView
     * @roseuid 402971940276
     */
    public static AbstractGraphView getFocusedComponent()
    {
        return focusedComponent;
    }

    /**
     * ���ý������
     * @param abstractgraphview ����ͼԪ��ͼ�Ͷ���
     * @roseuid 402971940277
     */
    public static void setFocusedComponent(AbstractGraphView abstractgraphview)
    {
        focusedComponent = abstractgraphview;
    }

    /**
     * �õ�ͼԪģʽ�Ͷ����ģʽ
     * @return GraphModel
     * @roseuid 402971940279
     */
    public GraphModel getModel()
    {
        return model;
    }

    /**
     * ����ͼԪģʽ�� ���ģʽ�Ѵ��ڣ���ɾ�������������ԭ�е�ģʽ��������µļ����������»���ģʽ��
     * @param graphmodel - ͼԪģʽ�Ͷ���
     * @throws QMException 
     * @throws QMPropertyVetoException
     * @roseuid 40297194027A
     */
    public void setModel(GraphModel graphmodel) throws QMException
    {
        modelValidate(graphmodel);//ͼԪģʽ�ͣ�
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
     * �ж�ͼԪģʽ�Ƿ���ڡ� ����������򴴽��µĶ��󣬲��׳��쳣��
     * @param graphmodel ͼԪģʽ�͵Ķ���
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
     * ���ѡ��ģʽ����
     * @return GraphSelectionModel
     * @roseuid 40297194027E
     */
    public GraphSelectionModel getSelectionModel()
    {
        return selectionModel;
    }

    /**
     * ����ѡ��ģʽ�� ���ѡ��ģʽ���ڣ����Ƴ�ͼԪѡ��ģʽ�ļ�������������µļ����������»���ģʽ��
     * @param graphselectionmodel ͼԪѡ��ģʽ�Ķ���
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
     * ѡ��ģʽ����֤�� ���ͼԪѡ��ģʽ�����ڣ����׳��쳣
     * @param graphselectionmodel ͼԪѡ��ģʽ�͵Ķ���
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
     * ����ȱʡ��ͼԪģ��
     * @return GraphModel
     * @roseuid 402971940283
     */
    public static GraphModel createDefaultGraphModel()
    {
        return new DefaultGraphModel();
    }

    /**
     * ����ȱʡ��ͼԪѡ��ģ��
     * @return ȱʡ��ͼԪѡ��ģ��ʵ��
     * @roseuid 402971940284
     */
    public static GraphSelectionModel createDefaultGraphSelectionModel()
    {
        return new DefaultGraphSelectionModel();
    }

    /**
     * ʵ��FocusListener�������еķ�������ý���
     * @param focusevent ���ݽ����¼�����
     * @roseuid 402971940285
     */
    public void focusGained(FocusEvent focusevent)
    {
        focusedComponent = this;
    }

    /**
     * ʵ��FocusListener�������еķ��������ڷ�������
     * @param focusevent ���ݽ����¼�����
     * @roseuid 402971940287
     */
    public void focusLost(FocusEvent focusevent)
    {

    }

    /**
     * ʵ��GraphSelectionModelListener�������еķ����� ѡ��ͼԪ�ڵ㡣
     * @param graphselectionmodelevent ����ͼԪѡ��ģʽ�¼��Ķ���
     * @roseuid 402971940289
     */
    public void graphNodeSelected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        repaint();
    }

    /**
     * ʵ��GraphSelectionModelListener�������еķ����� �ڵ�û�б�ѡ�С�
     * @param graphselectionmodelevent ͼԪѡ��ģʽ�¼��Ķ���
     * @roseuid 40297194028B
     */
    public void graphNodeUnselected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        repaint();
    }

    /**
     * ʵ��GraphSelectionModelListener�������еķ����� ���ӱ�ѡ�С�
     * @param graphselectionmodelevent ͼԪѡ��ģʽ�¼��Ķ���
     * @roseuid 40297194028D
     */
    public void graphLinkSelected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        repaint();
    }

    /**
     * ʵ��GraphSelectionModelListener�������еķ����� ����û�б�ѡ�С�
     * @param graphselectionmodelevent ͼԪѡ��ģʽ�¼��Ķ���
     * @roseuid 40297194028F
     */
    public void graphLinkUnselected(GraphSelectionModelEvent graphselectionmodelevent)
    {
        repaint();
    }

    /**
     * ʵ��GraphSelectionModelListener�������еķ����� �����ѡ�Ľڵ�����ӡ�
     * @param graphselectionmodelevent ͼԪѡ��ģʽ�¼��Ķ���
     * @roseuid 402971940291
     */
    public void graphSelectionCleared(GraphSelectionModelEvent graphselectionmodelevent)
    {
        repaint();
    }

    /**
     * ʵ��GraphSelectionModelListener�������еķ����� ����ͼԪģʽ�¼���õ��Ľڵ���ӵ�����ϡ�
     * @param graphmodelevent ����ͼԪģʽ�¼��Ķ���
     * @roseuid 402971940293
     */
    public void graphNodeInserted(GraphModelEvent graphmodelevent)
    {
        repaint();
    }

    /**
     * ʵ��GraphSelectionModelListener�������еķ����� ���¡������¼��������ڽڵ��ϣ�����Ӧ�Ľڵ㡣
     * @param propertychangeevent �������Ըı��¼�����
     * @roseuid 402971940295
     */
    public void graphNodeChanged(PropertyChangeEvent propertychangeevent)
    {
        repaint();
    }

    /**
     * ʵ��GraphSelectionModelListener�������еķ����� ɾ��ͼԪģʽ�¼��еõ��Ľڵ㣬��ģʽ����Ϊָ��ģʽ��������档
     * @param graphmodelevent ����ͼԪģʽ�¼�����
     * @roseuid 402971940297
     */
    public void graphNodeRemoved(GraphModelEvent graphmodelevent)
    {
        repaint();
    }

    /**
     * ʵ��GraphSelectionModelListener�������еķ����� �ѵ�ǰ�̼߳��뵽�߳��飬��ͼԪģʽ�¼��õ���������ӵ�����ϡ�
     * @param graphmodelevent ����ͼԪģʽ�¼�����
     * @roseuid 402971940299
     */
    public void graphLinkInserted(GraphModelEvent graphmodelevent)
    {
        repaint();
    }

    /**
     * ʵ��GraphSelectionModelListener�������еķ����� ���Ըı��¼�����������description�������ɱ�ǩ������description�������ӡ�
     * @param propertychangeevent �������Ըı��¼�����
     * @roseuid 40297194029B
     */
    public void graphLinkChanged(PropertyChangeEvent propertychangeevent)
    {
        repaint();
    }

    /**
     * ʵ��GraphSelectionModelListener�������еķ����� ɾ�����Ӳ���ģʽ����Ϊָ��ģʽ��
     * @param graphmodelevent ����ͼԪģʽ�¼�����
     * @roseuid 40297194029D
     */
    public void graphLinkRemoved(GraphModelEvent graphmodelevent)
    {
        repaint();
    }

    /**
     * ʵ��GraphSelectionModelListener�������еķ����� �ı�ͼԪ�ṹ��
     * @param graphmodelevent ����ͼԪģʽ�¼�����
     * @roseuid 40297194029F
     */
    public void graphStructureChanged(GraphModelEvent graphmodelevent)
    {
        repaint();
    }

    /**
     * ʵ��MouseListener�������еķ�������ʾ������ʱ�������¼�
     * @param mouseevent ��������¼�����
     * @roseuid 4029719402A1
     */
    public void mouseClicked(MouseEvent mouseevent)
    {

    }

    /**
     * ʵ��MouseListener�������еķ�������ʾ�������ʱ�������¼�
     * @param mouseevent ��������¼�����
     * @roseuid 4029719402A3
     */
    public void mousePressed(MouseEvent mouseevent)
    {

    }

    /**
     * ʵ��MouseListener�������еķ�������ʾ�ͷ����ʱ�������¼�
     * @param mouseevent ��������¼�����
     * @roseuid 4029719402A5
     */
    public void mouseReleased(MouseEvent mouseevent)
    {

    }

    /**
     * ʵ��MouseListener�������еķ������շ���
     * @param mouseevent ��������¼�����
     * @roseuid 4029719402A7
     */
    public void mouseEntered(MouseEvent mouseevent)
    {

    }

    /**
     * ʵ��MouseListener�������еķ������շ���
     * @param mouseevent ��������¼�����
     * @roseuid 4029719402A9
     */
    public void mouseExited(MouseEvent mouseevent)
    {

    }

    /**
     * ʵ��MouseMotionListener�������еķ�������ʾ��ק���ʱ�������¼�
     * @param mouseevent ��������¼�����
     * @roseuid 4029719402AB
     */
    public void mouseDragged(MouseEvent mouseevent)
    {

    }

    /**
     * ʵ��MouseMotionListener�������еķ�������ʾ�ƶ����ʱ�������¼�
     * @param mouseevent ��������¼�����
     * @roseuid 4029719402AD
     */
    public void mouseMoved(MouseEvent mouseevent)
    {

    }

    /**
     * �ж϶����Ƿ���ڡ�
     * @param point - Point�ͣ�ָ�������λ��
     * @return boolean
     * @roseuid 4029719402AF
     */
    public abstract boolean isObjectExisting(Point point);
}