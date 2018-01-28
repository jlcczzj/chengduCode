/**
 * 程序BaselineReaddController.java 1.0 11/2/2003
 * 版权归一汽启明公司所有
 * 本程序属于一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/06/18 马辉   原因:TD2146 添加零部件到基准线，界面显示结果信息为失败
 *                       方案:修改传入jsp的数据类型
 * CR2：2009/06/21 谢斌 TD2305将配置规范改为JDialog。
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
 * <p>Title:重新添加基准线控制类。 </p>
 * <p>Description:把一个零部件的不同版本添加到基准线中。</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:一汽启明</p>
 * @author 刘贵志
 * @version 1.0
 */
public class BaselineReaddController implements ActionListener
{
    private QMPartIfc qmPart;

    private ManagedBaselineIfc baseline;

    private PartBaselineReaddDialog okCancelDialog;

    /**
     * 构造函数
     * @param qmPart 添加的零部件。
     * @param stedBaseline 添加的基准线。
     * @param frame 父窗口。
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
     * 构造函数
     * @param qmPart 添加的零部件。
     * @param stedBaseline 添加的基准线。
     * @param dialog 父窗口。
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

    /* （非 Javadoc）
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("OK"))
        {
            //重新添加基准线
            readdToBaseline();
            okCancelDialog.dispose();
        }
        if(e.getActionCommand().equals("CANCEL"))
        {
            okCancelDialog.dispose();
        }
    }

    /**
     * 把零件添加到基准线中，封装服务中的addToBaseline()。
     */
    protected void readdToBaseline()
    {
        PartDebug.debug("readdToBaseline() begin ....", this,
                PartDebug.PART_CLIENT);
        //调用服务方法：addToBaseline()
        try
        {
            baseline = (ManagedBaselineIfc) PartServiceRequest.addToBaseline(
                    qmPart, baseline);
            //定义瘦客户界面标识
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
     * @return 返回 baseline。
     */
    public ManagedBaselineIfc getBaseline()
    {
        return baseline;
    }

    /**
     * @return 返回 qmPart。
     */
    public QMPartIfc getQmPart()
    {
        return qmPart;
    }
}
