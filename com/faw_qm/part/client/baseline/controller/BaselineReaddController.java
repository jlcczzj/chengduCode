/**
 * ����BaselineReaddController.java 1.0 11/2/2003
 * ��Ȩ��һ��������˾����
 * ����������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/06/18 ���   ԭ��:TD2146 ����㲿������׼�ߣ�������ʾ�����ϢΪʧ��
 *                       ����:�޸Ĵ���jsp����������
 * CR2��2009/06/21 л�� TD2305�����ù淶��ΪJDialog��
 */
package com.faw_qm.part.client.baseline.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JFrame;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.part.client.baseline.view.PartBaselineReaddDialog;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceRequest;

/**
 * <p>Title:������ӻ�׼�߿����ࡣ </p>
 * <p>Description:��һ���㲿���Ĳ�ͬ�汾��ӵ���׼���С�</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:һ������</p>
 * @author ����־
 * @version 1.0
 */
public class BaselineReaddController implements ActionListener
{
    private QMPartIfc qmPart;

    private ManagedBaselineIfc baseline;

    private PartBaselineReaddDialog okCancelDialog;

    /**
     * ���캯��
     * @param qmPart ��ӵ��㲿����
     * @param stedBaseline ��ӵĻ�׼�ߡ�
     * @param frame �����ڡ�
     */
    public BaselineReaddController(QMPartIfc qmPart,
            ManagedBaselineIfc stedBaseline, JFrame frame)
    {
        this.qmPart = qmPart;
        this.baseline = stedBaseline;
        okCancelDialog = new PartBaselineReaddDialog(this, frame);
        PartScreenParameter.setLocationCenter(okCancelDialog);
        okCancelDialog.setVisible(true);
    }
    
    /**
     * ���캯��
     * @param qmPart ��ӵ��㲿����
     * @param stedBaseline ��ӵĻ�׼�ߡ�
     * @param dialog �����ڡ�
     */
    public BaselineReaddController(QMPartIfc qmPart,
            ManagedBaselineIfc stedBaseline, JDialog dialog)
    {
        this.qmPart = qmPart;
        this.baseline = stedBaseline;
        okCancelDialog = new PartBaselineReaddDialog(this, dialog);
        PartScreenParameter.setLocationCenter(okCancelDialog);
        okCancelDialog.setVisible(true);
    }

    /* ���� Javadoc��
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("OK"))
        {
            //������ӻ�׼��
            readdToBaseline();
            okCancelDialog.dispose();
        }
        if(e.getActionCommand().equals("CANCEL"))
        {
            okCancelDialog.dispose();
        }
    }

    /**
     * �������ӵ���׼���У���װ�����е�addToBaseline()��
     */
    protected void readdToBaseline()
    {
        PartDebug.debug("readdToBaseline() begin ....", this,
                PartDebug.PART_CLIENT);
        //���÷��񷽷���addToBaseline()
        try
        {
            baseline = (ManagedBaselineIfc) PartServiceRequest.addToBaseline(
                    qmPart, baseline);
            //�����ݿͻ������ʶ
            HashMap takeParams = new HashMap();
            takeParams.put("baselineBsoID", baseline.getBsoID());
            takeParams.put("baselineName", baseline.getBaselineName());
            takeParams.put("baselineDescription", baseline
                    .getBaselineDescription() == null ? "" : baseline
                    .getBaselineDescription());
            takeParams.put("qmpartBsoid", qmPart.getBsoID());
            takeParams.put("qmpartNumber", qmPart.getPartNumber());
            takeParams.put("qmpartName", qmPart.getPartName());
            takeParams.put("qmpartViewName", qmPart.getViewName() == null ? ""
                    : qmPart.getViewName());
            takeParams.put("qmpartVersionValue", qmPart.getVersionValue());
//          CR1 begin    
            takeParams.put("type", "add");//CR1 end
            RichToThinUtil.toWebPage(
                    "Part-BaseLine-AddBaseLineResult-001.screen", takeParams);
        }
        catch (QMException exception)
        {
            exception.printStackTrace();
        }
        PartDebug.debug("readdToBaseline()end....return is" + baseline, this,
                PartDebug.PART_CLIENT);
    }

    /**
     * @return ���� baseline��
     */
    public ManagedBaselineIfc getBaseline()
    {
        return baseline;
    }

    /**
     * @return ���� qmPart��
     */
    public QMPartIfc getQmPart()
    {
        return qmPart;
    }
}
