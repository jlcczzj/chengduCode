/** 生成程序MaterialController.java	1.1  2003/05/20
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.part.client.other.controller;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.effectivity.controller.EffAction;
import com.faw_qm.part.client.effectivity.model.PartEffModel;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.client.other.model.MaterialBillModel;
import com.faw_qm.part.client.other.view.MaterialBillJFrame;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import java.io.FileWriter;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.clients.widgets.IBAUtility;
import java.awt.Cursor;
import java.io.File;
import java.io.IOException;


/**
 * <p>Title: 定制物料清单数据控制类。</p>
 * <p>监听定制物料清单界面按钮点击事件,并执行相应动作(初始化动作类)。</p>
 * <p>监视定制物料清单界面鼠标双击事件。</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author  刘明
 * @version 2.0
 * leix 443 取消判断isTailorRoute
 */
public class MaterialController extends EffAction
{
    /**定制物料清单界面数据模型*/
    private MaterialBillModel model = null;


    /**定制物料清单界面对象*/
    private MaterialBillJFrame view = null;


    /**线程状态常量：开始*/
    private static final int BEGIN = 0;


    /**线程状态常量：定制*/
    private static final int TAILOR = 1;


    /**线程状态常量：结束*/
    private static final int END = -1;


    /**线程状态值*/
    private int state = BEGIN;


    /**“全部”是否被选择的字符串表示*/
    private String all_String;


    /**“分级”是否被选择的字符串表示*/
    private String grade_String;


    /**“统计表”是否被选择的字符串表示*/
    private String statistics_String;
    private String statisticsselect;

    /**“分类”是否被选择的字符串表示*/
    private String classify_String;


    /**来源是否可被选择的字符串表示*/
    private String source_String;


    /**类型是否可被选择的字符串表示*/
    private String type_String;


    /**统计表是否可用的字符串表示*/
    private String statistics_Enable;


    /**零部件来源*/
    private ProducedBy source;


    /**零部件类型*/
    private QMPartType type;


    /**资源文件路径*/
    private static String RESOURCE =
            "com.faw_qm.part.client.other.util.OtherRB";


    /**存放要传到瘦客户端页面的参数*/
    private HashMap map = new HashMap();


    /**选中的零部件值对象*/
    private QMPartIfc qmPartIfc;


    /**
     * 构造函数。
     *
     * <p>显示定制物料清单界面，并启动当前线程。</p>
     *
     * <p>构造一个定制物料清单界面控制器。</p>
     *
     * <p>初始化定制物料清单界面和模型<。/p>
     *
     * @param partIfc 零部件对象接口。
     */
    public MaterialController(QMPartIfc partIfc)
    {
        PartDebug.trace(PartDebug.PART_CLIENT,
                        "MaterialController() begin ....");
        //接收参数
        qmPartIfc = (QMPartIfc) partIfc;
        //初始化物料清单界面
        view = new MaterialBillJFrame(this, qmPartIfc);
        //初始化数据模型
        model = new MaterialBillModel();
        model.addObserver(view);

        //设置界面显示在屏幕中央
        view.setSize(650, 500);
        PartScreenParameter.setLocationCenter(view);
         perform();
        //启动当前线程
       // start();
        PartDebug.trace(PartDebug.PART_CLIENT,
                        "MaterialController() end ....return is void");
    }
//  CCBegin by leix 2009-11-30
    /**
     * 构造函数。
     * <p>显示定制物料清单界面，并启动当前线程</p>
     * <p>构造一个定制物料清单界面控制器</p>
     * <p>初始化定制物料清单界面和模型</p>
     * @param partIfc 零部件对象接口
     * @param hashmap 数据表
     */
    public MaterialController(QMPartIfc partIfc, boolean bool) {

      //接收参数
      qmPartIfc = (QMPartIfc) partIfc;
      //初始化物料清单界面
      view = new MaterialBillJFrame(this, qmPartIfc, bool); //add by liun
      //初始化数据模型
      model = new MaterialBillModel();
      model.addObserver(view);

      //设置界面显示在屏幕中央
      //CCBegin by liunan 2011-06-20 扩大可选属性面积，美观。
      //view.setSize(650, 500);
      view.setSize(680, 720);
      //CCEnd by liunan 2011-06-20
      PartScreenParameter.setLocationCenter(view);

      perform();
      //启动当前线程
      //start();
    }
//  CCEnd by leix 2009-11-30

    /**
     * 线程运行函数。
     * <p>调用动作执行方法perform()。</p>
     */
    public void run()
    {
      /*  perform(); //显示界面
        while (state != END)
        {
            try
            {
                sleep(100);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if (state == TAILOR)
            {
                tailorBomList(); //根据选择的条件转到相应的页面；
            }
        }*/
    }


    /**
     * 界面按钮动作监听处理函数。
     * @param e 动作事件。
     */
    public void actionPerformed(ActionEvent e)
    {
        /**选择全部输出方式*/
        if (e.getActionCommand().equals("ALL"))
        {
            Vector allVector = new Vector();
            all_String = "True";
            grade_String = "False";
            statistics_String = "False";
            classify_String = "False";
            source_String = "False";
            type_String = "False";
            statistics_Enable = "True";
            statisticsselect="False";
            allVector.add(all_String);
            allVector.add(grade_String);
            allVector.add(statistics_String);
            allVector.add(classify_String);
            allVector.add(source_String);
            allVector.add(type_String);
            allVector.add(statistics_Enable);

            model.refresh(allVector);
        }

        /**选择分级输出方式*/
        if (e.getActionCommand().equals("GRADE"))
        {
            Vector gradeVector = new Vector();
            all_String = "True";
            grade_String = "True";
            statistics_String = "False";
            classify_String = "False";
            source_String = "False";
            type_String = "False";
            statistics_Enable = "True";
            statisticsselect="False";
            gradeVector.add(all_String);
            gradeVector.add(grade_String);
            gradeVector.add(statistics_String);
            gradeVector.add(classify_String);
            gradeVector.add(source_String);
            gradeVector.add(type_String);
            gradeVector.add(statistics_Enable);

            model.refresh(gradeVector);
        }

        /**选择统计表输出方式*/
        if (e.getActionCommand().equals("STATISTICS"))
        {
            Vector statisticsVector = new Vector();
            all_String = "True";
            grade_String = "False";
            statistics_String = "True";
            classify_String = "False";
            source_String = "False";
            type_String = "False";
            statistics_Enable = "True";
            statisticsselect="True";
            statisticsVector.add(all_String);
            statisticsVector.add(grade_String);
            statisticsVector.add(statistics_String);
            statisticsVector.add(classify_String);
            statisticsVector.add(source_String);
            statisticsVector.add(type_String);
            statisticsVector.add(statistics_Enable);

            model.refresh(statisticsVector);
        }

        /**选择分类输出方式*/
        if (e.getActionCommand().equals("CLASSIFY"))
        {
            Vector classifyVector = new Vector();
            all_String = "False";
            grade_String = "False";
            statistics_String = "False";
            classify_String = "True";
            source_String = "True";
            type_String = "True";
            statistics_Enable = "True";
            statisticsselect="True";
            classifyVector.add(all_String);
            classifyVector.add(grade_String);
            classifyVector.add(statistics_String);
            classifyVector.add(classify_String);
            classifyVector.add(source_String);
            classifyVector.add(type_String);
            classifyVector.add(statistics_Enable);

            model.refresh(classifyVector);
        }

        /**执行确定动作*/
        if (e.getActionCommand().equals("OK"))
        {
            //设置状态栏
            view.setStatusBar();
            //给廋客户端传递参数：零部件的BsoID
            map.put("PartID", qmPartIfc.getBsoID());
            //如果没有选中“分级”和“统计表”，则提示选择；否则执行定制物料清单
            if ((grade_String != "True") && (  statisticsselect != "True"))
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        "Please select output mode", null);
                JOptionPane.showMessageDialog(view, message, title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                if (view.getAttribute().equals(""))
                {
                    //标记当前线程状态为：定制
                    state = TAILOR;
                     tailorBomList();
                }
                else
                {
                    PartDebug.trace(PartDebug.PART_CLIENT,
                                    "----------------" + view.getAttribute());
                    //给廋客户端传递参数：零部件的输出属性（用于界面显示）
                    map.put("attributeName", view.getAttribute());
                    PartDebug.trace(PartDebug.PART_CLIENT,
                                    "----------------" + view.getAttribute1());
                    //给廋客户端传递参数：零部件的输出属性（用于调用服务）
                    map.put("attributeName1", view.getAttribute1());
                    //标记当前线程状态为：定制
                    state = TAILOR;
                     tailorBomList();
                }
            }
        }
//      CCBegin by leix 2009-11-30
        if (e.getActionCommand().equals("EXPORT"))
        {
            //设置状态栏
            view.setStatusBar();
            //给廋客户端传递参数：零部件的BsoID
            map.put("PartID", qmPartIfc.getBsoID());
            //如果没有选中“分级”和“统计表”，则提示选择；否则执行定制物料清单
            if ((grade_String != "True") && (  statisticsselect != "True"))
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        "Please select output mode", null);
                JOptionPane.showMessageDialog(view, message, title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                if (!view.getAttribute().equals(""))
                {
                    PartDebug.trace(PartDebug.PART_CLIENT,
                                    "----------------" + view.getAttribute());
                    //给廋客户端传递参数：零部件的输出属性（用于界面显示）
                    map.put("attributeName", view.getAttribute());
                    PartDebug.trace(PartDebug.PART_CLIENT,
                                    "----------------" + view.getAttribute1());
                    //给廋客户端传递参数：零部件的输出属性（用于调用服务）
                    map.put("attributeName1", view.getAttribute1());
                }
                exportBOMTolocalText();
            }
        }
//      CCEnd by leix 2009-11-30
        /**执行取消动作*/
        if (e.getActionCommand().equals("CANCEL"))
        {
            cancel();
        }
    }


    /**
     * 定制物料清单控制器的执行方法。
     * <p>显示定制物料清单界面。</p>
     */
    public void perform()
    {
        view.show();
    }


    /**
     * 取消已经初始化的动作,关闭界面。
     * <p>遍历动作列表,取消各动作,并将主控制器状态置为END(-1)。</p>
     */

    public void cancel()
    {
        view.closeView();
        state = END;
    }


    /**
     * 获得数据模型。
     * @return 界面的数据模型。
     */
    public PartEffModel getModel()
    {
        return null;
    }


    /**
     * 获得控制器的状态。
     * @return 状态(0:开始,1:执行,-1:结束)。
     */
    public int getEffState()
    {
        return state;
    }


    /**
     * 执行定制物料清单操作。
     */
    private void tailorBomList()
    {
        /**选择分级输出*/
        if (grade_String.equals("True"))
        {
//          zz start 物料清单增加定制工艺路线功能
//            boolean isTailorRoute = RemoteProperty.getProperty(
//                    "tailorRoute", "false").equalsIgnoreCase("true");
//            if(isTailorRoute)
//                RichToThinUtil.toWebPage("Part-Other-classifylisting-001-Capp-0A.do",
//                        map);
////          zz end 物料清单增加定制工艺路线功能
//            else
//            //转到瘦客户页面
            RichToThinUtil.toWebPage("Part-Other-classifylisting-001-0A.do",
                                     map);
        }

        /**选择统计表输出*/
        if (  statisticsselect.equals("True"))
        {
            //获得界面上的录入信息（来源和类型）
            Vector c = (Vector) view.getOutput();
            PartDebug.trace(PartDebug.PART_CLIENT,
                            "************** vector : " + c.size());
            //如果获得的信息集合不为空
            if (c != null && c.size() != 0)
            {
                Iterator i = c.iterator();

                //如果集合中的第一个元素是一种来源，则向哈西表传入该来源；否则传入一个
                //空字符串（即表明界面上没有选择来源）
                Object produce = i.next();
                PartDebug.trace(PartDebug.PART_CLIENT,
                                ":::::::::::::::: produce = " + produce);
                if (produce instanceof ProducedBy)
                {
                    source = (ProducedBy) produce;
                    map.put("source", source.toString());
                }
                else
                {
                    map.put("source", "");
                }

                //如果集合中的第二个元素是一种类型，则向哈西表传入该类型；否则传入一个
                //空字符串（即表明界面上没有选择类型）
                Object partType = i.next();
                PartDebug.trace(PartDebug.PART_CLIENT,
                                ":::::::::::::::: partType = " + partType);
                if (partType instanceof QMPartType)
                {
                    type = (QMPartType) partType;
                    map.put("type", type.toString());
                }
                else
                {
                    map.put("type", "");
                }
                PartDebug.trace(PartDebug.PART_CLIENT,
                                ":::::::::::::::::::::: type = " + type);
            }
            else
            {
                map.put("source", "");
                map.put("type", "");
            }
//          zz start 物料清单增加定制工艺路线功能
//            boolean isTailorRoute = RemoteProperty.getProperty(
//                    "tailorRoute", "false").equalsIgnoreCase("true");
//            if(isTailorRoute)
//                RichToThinUtil.toWebPage("Part-Other-PartStatistics-001-Capp-0A.do", map);
////          zz end 物料清单增加定制工艺路线功能
//            else
//            //根据哈西表传入的参数生成廋客户界面
            RichToThinUtil.toWebPage("Part-Other-PartStatistics-001-0A.do", map);
        }
        //2005/12/15
        //关闭物料清单界面
        this.cancel();
    }


    /**
     * 设置业务对象。
     * <p>重载EffAction的同名方法。</p>
     * @param object 业务对象。
     */
    public void setObject(Object object)
    {
    }

//  CCBegin by leix 2009-11-30
    private void exportBOMTolocalText() {
        if (grade_String.equals("True")) {
          writeClassfyProcess();
        }

        /**选择统计表输出*/
        if (statistics_String.equals("True")) {
          //获得界面上的录入信息（来源和类型）
          Vector c = (Vector) view.getOutput();
          if (c != null && c.size() != 0) {
            Iterator i = c.iterator();
            Object produce = i.next();
            if (produce instanceof ProducedBy) {
              source = (ProducedBy) produce;
              map.put("source", source.toString());
            }
            else {
              map.put("source", "");
            }
            Object partType = i.next();
            if (partType instanceof QMPartType) {
              type = (QMPartType) partType;
              map.put("type", type.toString());
            }
            else {
              map.put("type", "");
            }
          }
          else {
            map.put("source", "");
            map.put("type", "");
          }
          writeStatisticsProcess();
        }
      }
      private void writeClassfyProcess() {
        String path = getSelectedPath();
        if (path == null) {
          return;
        }
        try {
          view.setCursor(Cursor.WAIT_CURSOR);
          Class[] c = {
              HashMap.class};
          Object[] objs = {
              map};
          String returnStr = (String) IBAUtility.invokeServiceMethod(
              "StandardPartService", "getExportBOMClassfiyString", c, objs);
          FileWriter fw = new FileWriter(path, false);
          fw.write(returnStr);
          fw.close();
          view.setCursor(Cursor.getDefaultCursor());
        }
        catch (QMRemoteException ex) {
          view.setCursor(Cursor.getDefaultCursor());
          ex.printStackTrace();
          JOptionPane.showMessageDialog(view,
                                        ex.getClientMessage(),
                                        QMMessage.getLocalizedMessage(RESOURCE,
              "information", null),
                                        JOptionPane.INFORMATION_MESSAGE);
        }
        catch (IOException ex1) {
          view.setCursor(Cursor.getDefaultCursor());
          ex1.printStackTrace();
        }
      }

//  屈晓光添加，以统计表形式输出BOM到本地文件
      private void writeStatisticsProcess() {
        String path = getSelectedPath();
        if (path == null) {
          return;
        }
        try {
          view.setCursor(Cursor.WAIT_CURSOR);
          Class[] c = {
              HashMap.class};
          Object[] objs = {
              map};
          String returnStr = (String) IBAUtility.invokeServiceMethod(
              "StandardPartService", "getExportBOMStatisticsString", c, objs);
          FileWriter fw = new FileWriter(path, false);
          fw.write(returnStr);
          fw.close();

        }
        catch (QMRemoteException ex) {
          ex.printStackTrace();
          JOptionPane.showMessageDialog(view,
                                        ex.getClientMessage(),
                                        QMMessage.getLocalizedMessage(RESOURCE,
              "information", null),
                                        JOptionPane.INFORMATION_MESSAGE);
          view.setCursor(Cursor.getDefaultCursor());
        }
        catch (IOException ex1) {
          view.setCursor(Cursor.getDefaultCursor());
          ex1.printStackTrace();
        }

      }

      private String getSelectedPath() {
        FileFilter filter;
        File selectedFile = null;
        view.setCursor(Cursor.WAIT_CURSOR);
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setMultiSelectionEnabled(false);
        filter = new TXTFileFilter();
        if (selectedFile != null) {
          chooser.setSelectedFile(selectedFile);
        }
        chooser.setDialogTitle("输出清单至...");
        chooser.setFileFilter(filter);
        //删除系统自带的AcceptAllFileFilter
        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
        //“打开”模式文件选择器中选择了批准按钮还是取消按钮
        view.setCursor(Cursor.getDefaultCursor());
        int state = chooser.showSaveDialog(null);
        if (state == chooser.CANCEL_OPTION) {
          this.cancel();
        }
        //获得选择的文件
        selectedFile = chooser.getSelectedFile();
        //如果选择了批准按钮,则获得所选择文件名
        if (selectedFile != null && state == JFileChooser.APPROVE_OPTION) {
          //文件格式转换
          selectedFile = this.translateFile(selectedFile, filter);

          //判断 1 未输入文件名,请输入文件名称  2 指定的路径不存在或不可用 3 文件已存在,请重新指定文件名
          if (!filter.accept(selectedFile)) {
            JOptionPane.showMessageDialog(view,
                                          "路径不存在!",
                                          QMMessage.getLocalizedMessage(RESOURCE,
                "error", null),
                                          JOptionPane.ERROR_MESSAGE);
            return null;
          }
          if (selectedFile.exists()) {
            JOptionPane.showMessageDialog(view, "文件已经存在！",
                                          QMMessage.getLocalizedMessage(RESOURCE,
                "warning", null),
                                          JOptionPane.WARNING_MESSAGE);
            return null;
          }
        }

        return selectedFile.getPath();
      }

      private File translateFile(File file, FileFilter filter) {
        String path = file.getPath();
        if (!path.endsWith(".csv")) {
          path = path + ".csv";
        }
        return new File(path);
      }

      public class TXTFileFilter
          extends FileFilter {

        /**
         * 构造文本文件过滤器
         */
        public TXTFileFilter() {
        }

        /**
         * 判断指定的文件是否被本过滤器接受
         * @param f 文件
         * @return 如果接受，则返回true
         */
        public boolean accept(File f) {
          boolean accept = f.isDirectory();
          if (!accept) {
            String suffix = getSuffix(f);
            if (suffix != null) {
              accept = suffix.equals("csv");
            }
          }
          return accept;
        }

        /**
         * 获得本过滤器的描述信息
         * @return Text Files(*.csv)
         */
        public String getDescription() {
          return "Text Files(*.csv)";
        }

        /**
         * 获得指定文件的后缀
         * @param f File
         * @return 文件的后缀
         */
        private String getSuffix(File f) {
          String s = f.getPath(), suffix = null;
          int i = s.lastIndexOf('.');
          if (i > 0 && i < s.length() - 1) {
            suffix = s.substring(i + 1).toLowerCase();
          }
          return suffix;
        }
      }
//    CCEnd by leix 2009-11-30
}
