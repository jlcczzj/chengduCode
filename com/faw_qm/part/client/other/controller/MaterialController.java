/** ���ɳ���MaterialController.java	1.1  2003/05/20
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: ���������嵥���ݿ����ࡣ</p>
 * <p>�������������嵥���水ť����¼�,��ִ����Ӧ����(��ʼ��������)��</p>
 * <p>���Ӷ��������嵥�������˫���¼���</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author  ����
 * @version 2.0
 * leix 443 ȡ���ж�isTailorRoute
 */
public class MaterialController extends EffAction
{
    /**���������嵥��������ģ��*/
    private MaterialBillModel model = null;


    /**���������嵥�������*/
    private MaterialBillJFrame view = null;


    /**�߳�״̬��������ʼ*/
    private static final int BEGIN = 0;


    /**�߳�״̬����������*/
    private static final int TAILOR = 1;


    /**�߳�״̬����������*/
    private static final int END = -1;


    /**�߳�״ֵ̬*/
    private int state = BEGIN;


    /**��ȫ�����Ƿ�ѡ����ַ�����ʾ*/
    private String all_String;


    /**���ּ����Ƿ�ѡ����ַ�����ʾ*/
    private String grade_String;


    /**��ͳ�Ʊ��Ƿ�ѡ����ַ�����ʾ*/
    private String statistics_String;
    private String statisticsselect;

    /**�����ࡱ�Ƿ�ѡ����ַ�����ʾ*/
    private String classify_String;


    /**��Դ�Ƿ�ɱ�ѡ����ַ�����ʾ*/
    private String source_String;


    /**�����Ƿ�ɱ�ѡ����ַ�����ʾ*/
    private String type_String;


    /**ͳ�Ʊ��Ƿ���õ��ַ�����ʾ*/
    private String statistics_Enable;


    /**�㲿����Դ*/
    private ProducedBy source;


    /**�㲿������*/
    private QMPartType type;


    /**��Դ�ļ�·��*/
    private static String RESOURCE =
            "com.faw_qm.part.client.other.util.OtherRB";


    /**���Ҫ�����ݿͻ���ҳ��Ĳ���*/
    private HashMap map = new HashMap();


    /**ѡ�е��㲿��ֵ����*/
    private QMPartIfc qmPartIfc;


    /**
     * ���캯����
     *
     * <p>��ʾ���������嵥���棬��������ǰ�̡߳�</p>
     *
     * <p>����һ�����������嵥�����������</p>
     *
     * <p>��ʼ�����������嵥�����ģ��<��/p>
     *
     * @param partIfc �㲿������ӿڡ�
     */
    public MaterialController(QMPartIfc partIfc)
    {
        PartDebug.trace(PartDebug.PART_CLIENT,
                        "MaterialController() begin ....");
        //���ղ���
        qmPartIfc = (QMPartIfc) partIfc;
        //��ʼ�������嵥����
        view = new MaterialBillJFrame(this, qmPartIfc);
        //��ʼ������ģ��
        model = new MaterialBillModel();
        model.addObserver(view);

        //���ý�����ʾ����Ļ����
        view.setSize(650, 500);
        PartScreenParameter.setLocationCenter(view);
         perform();
        //������ǰ�߳�
       // start();
        PartDebug.trace(PartDebug.PART_CLIENT,
                        "MaterialController() end ....return is void");
    }
//  CCBegin by leix 2009-11-30
    /**
     * ���캯����
     * <p>��ʾ���������嵥���棬��������ǰ�߳�</p>
     * <p>����һ�����������嵥���������</p>
     * <p>��ʼ�����������嵥�����ģ��</p>
     * @param partIfc �㲿������ӿ�
     * @param hashmap ���ݱ�
     */
    public MaterialController(QMPartIfc partIfc, boolean bool) {

      //���ղ���
      qmPartIfc = (QMPartIfc) partIfc;
      //��ʼ�������嵥����
      view = new MaterialBillJFrame(this, qmPartIfc, bool); //add by liun
      //��ʼ������ģ��
      model = new MaterialBillModel();
      model.addObserver(view);

      //���ý�����ʾ����Ļ����
      //CCBegin by liunan 2011-06-20 �����ѡ������������ۡ�
      //view.setSize(650, 500);
      view.setSize(680, 720);
      //CCEnd by liunan 2011-06-20
      PartScreenParameter.setLocationCenter(view);

      perform();
      //������ǰ�߳�
      //start();
    }
//  CCEnd by leix 2009-11-30

    /**
     * �߳����к�����
     * <p>���ö���ִ�з���perform()��</p>
     */
    public void run()
    {
      /*  perform(); //��ʾ����
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
                tailorBomList(); //����ѡ�������ת����Ӧ��ҳ�棻
            }
        }*/
    }


    /**
     * ���水ť����������������
     * @param e �����¼���
     */
    public void actionPerformed(ActionEvent e)
    {
        /**ѡ��ȫ�������ʽ*/
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

        /**ѡ��ּ������ʽ*/
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

        /**ѡ��ͳ�Ʊ������ʽ*/
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

        /**ѡ����������ʽ*/
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

        /**ִ��ȷ������*/
        if (e.getActionCommand().equals("OK"))
        {
            //����״̬��
            view.setStatusBar();
            //���C�ͻ��˴��ݲ������㲿����BsoID
            map.put("PartID", qmPartIfc.getBsoID());
            //���û��ѡ�С��ּ����͡�ͳ�Ʊ�������ʾѡ�񣻷���ִ�ж��������嵥
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
                    //��ǵ�ǰ�߳�״̬Ϊ������
                    state = TAILOR;
                     tailorBomList();
                }
                else
                {
                    PartDebug.trace(PartDebug.PART_CLIENT,
                                    "----------------" + view.getAttribute());
                    //���C�ͻ��˴��ݲ������㲿����������ԣ����ڽ�����ʾ��
                    map.put("attributeName", view.getAttribute());
                    PartDebug.trace(PartDebug.PART_CLIENT,
                                    "----------------" + view.getAttribute1());
                    //���C�ͻ��˴��ݲ������㲿����������ԣ����ڵ��÷���
                    map.put("attributeName1", view.getAttribute1());
                    //��ǵ�ǰ�߳�״̬Ϊ������
                    state = TAILOR;
                     tailorBomList();
                }
            }
        }
//      CCBegin by leix 2009-11-30
        if (e.getActionCommand().equals("EXPORT"))
        {
            //����״̬��
            view.setStatusBar();
            //���C�ͻ��˴��ݲ������㲿����BsoID
            map.put("PartID", qmPartIfc.getBsoID());
            //���û��ѡ�С��ּ����͡�ͳ�Ʊ�������ʾѡ�񣻷���ִ�ж��������嵥
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
                    //���C�ͻ��˴��ݲ������㲿����������ԣ����ڽ�����ʾ��
                    map.put("attributeName", view.getAttribute());
                    PartDebug.trace(PartDebug.PART_CLIENT,
                                    "----------------" + view.getAttribute1());
                    //���C�ͻ��˴��ݲ������㲿����������ԣ����ڵ��÷���
                    map.put("attributeName1", view.getAttribute1());
                }
                exportBOMTolocalText();
            }
        }
//      CCEnd by leix 2009-11-30
        /**ִ��ȡ������*/
        if (e.getActionCommand().equals("CANCEL"))
        {
            cancel();
        }
    }


    /**
     * ���������嵥��������ִ�з�����
     * <p>��ʾ���������嵥���档</p>
     */
    public void perform()
    {
        view.show();
    }


    /**
     * ȡ���Ѿ���ʼ���Ķ���,�رս��档
     * <p>���������б�,ȡ��������,������������״̬��ΪEND(-1)��</p>
     */

    public void cancel()
    {
        view.closeView();
        state = END;
    }


    /**
     * �������ģ�͡�
     * @return ���������ģ�͡�
     */
    public PartEffModel getModel()
    {
        return null;
    }


    /**
     * ��ÿ�������״̬��
     * @return ״̬(0:��ʼ,1:ִ��,-1:����)��
     */
    public int getEffState()
    {
        return state;
    }


    /**
     * ִ�ж��������嵥������
     */
    private void tailorBomList()
    {
        /**ѡ��ּ����*/
        if (grade_String.equals("True"))
        {
//          zz start �����嵥���Ӷ��ƹ���·�߹���
//            boolean isTailorRoute = RemoteProperty.getProperty(
//                    "tailorRoute", "false").equalsIgnoreCase("true");
//            if(isTailorRoute)
//                RichToThinUtil.toWebPage("Part-Other-classifylisting-001-Capp-0A.do",
//                        map);
////          zz end �����嵥���Ӷ��ƹ���·�߹���
//            else
//            //ת���ݿͻ�ҳ��
            RichToThinUtil.toWebPage("Part-Other-classifylisting-001-0A.do",
                                     map);
        }

        /**ѡ��ͳ�Ʊ����*/
        if (  statisticsselect.equals("True"))
        {
            //��ý����ϵ�¼����Ϣ����Դ�����ͣ�
            Vector c = (Vector) view.getOutput();
            PartDebug.trace(PartDebug.PART_CLIENT,
                            "************** vector : " + c.size());
            //�����õ���Ϣ���ϲ�Ϊ��
            if (c != null && c.size() != 0)
            {
                Iterator i = c.iterator();

                //��������еĵ�һ��Ԫ����һ����Դ����������������Դ��������һ��
                //���ַ�����������������û��ѡ����Դ��
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

                //��������еĵڶ���Ԫ����һ�����ͣ����������������ͣ�������һ��
                //���ַ�����������������û��ѡ�����ͣ�
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
//          zz start �����嵥���Ӷ��ƹ���·�߹���
//            boolean isTailorRoute = RemoteProperty.getProperty(
//                    "tailorRoute", "false").equalsIgnoreCase("true");
//            if(isTailorRoute)
//                RichToThinUtil.toWebPage("Part-Other-PartStatistics-001-Capp-0A.do", map);
////          zz end �����嵥���Ӷ��ƹ���·�߹���
//            else
//            //���ݹ�������Ĳ������ɏC�ͻ�����
            RichToThinUtil.toWebPage("Part-Other-PartStatistics-001-0A.do", map);
        }
        //2005/12/15
        //�ر������嵥����
        this.cancel();
    }


    /**
     * ����ҵ�����
     * <p>����EffAction��ͬ��������</p>
     * @param object ҵ�����
     */
    public void setObject(Object object)
    {
    }

//  CCBegin by leix 2009-11-30
    private void exportBOMTolocalText() {
        if (grade_String.equals("True")) {
          writeClassfyProcess();
        }

        /**ѡ��ͳ�Ʊ����*/
        if (statistics_String.equals("True")) {
          //��ý����ϵ�¼����Ϣ����Դ�����ͣ�
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

//  ��������ӣ���ͳ�Ʊ���ʽ���BOM�������ļ�
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
        chooser.setDialogTitle("����嵥��...");
        chooser.setFileFilter(filter);
        //ɾ��ϵͳ�Դ���AcceptAllFileFilter
        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
        //���򿪡�ģʽ�ļ�ѡ������ѡ������׼��ť����ȡ����ť
        view.setCursor(Cursor.getDefaultCursor());
        int state = chooser.showSaveDialog(null);
        if (state == chooser.CANCEL_OPTION) {
          this.cancel();
        }
        //���ѡ����ļ�
        selectedFile = chooser.getSelectedFile();
        //���ѡ������׼��ť,������ѡ���ļ���
        if (selectedFile != null && state == JFileChooser.APPROVE_OPTION) {
          //�ļ���ʽת��
          selectedFile = this.translateFile(selectedFile, filter);

          //�ж� 1 δ�����ļ���,�������ļ�����  2 ָ����·�������ڻ򲻿��� 3 �ļ��Ѵ���,������ָ���ļ���
          if (!filter.accept(selectedFile)) {
            JOptionPane.showMessageDialog(view,
                                          "·��������!",
                                          QMMessage.getLocalizedMessage(RESOURCE,
                "error", null),
                                          JOptionPane.ERROR_MESSAGE);
            return null;
          }
          if (selectedFile.exists()) {
            JOptionPane.showMessageDialog(view, "�ļ��Ѿ����ڣ�",
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
         * �����ı��ļ�������
         */
        public TXTFileFilter() {
        }

        /**
         * �ж�ָ�����ļ��Ƿ񱻱�����������
         * @param f �ļ�
         * @return ������ܣ��򷵻�true
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
         * ��ñ���������������Ϣ
         * @return Text Files(*.csv)
         */
        public String getDescription() {
          return "Text Files(*.csv)";
        }

        /**
         * ���ָ���ļ��ĺ�׺
         * @param f File
         * @return �ļ��ĺ�׺
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
