/** ���� DrawingPanelRB_en_US.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2011/12/13 �ֺ���  �޸�ԭ�򣺲μ�TD����5415
 */
// CR3  2014/07/31   xianglx CAD���ͼ�ͼ���������ļ���һ��dwg���ڲ鿴�ͱ༭��һ��jpg���ڴ�ӡԤ��


package com.faw_qm.cappclients.beans.drawingpanel;

import java.util.ListResourceBundle;


/**
 * <p>Title:��ͼbean��Դ�ļ� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:faw_qm </p>
 * @author: �ܴ�Ԫ
 * @version 1.0  2003/08/28
 */
public class DrawingPanelRB_en_US extends ListResourceBundle
{

    /**
     @roseuid 3F4DB40A00B3
     */
    public DrawingPanelRB_en_US()
    {

    }


    /**
      @return Object[][]
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents = new String[][]
                                       {
                                       {"1", "do you went to edit  Drawing��"}
                                       ,
                                       {"2", "Drawing"}
                                       ,
                                       {"3", "Drawing is not exist"}
                                       ,
                                       {"4", "do you went to view the Drawing"}
                                       ,
                                       {"5", "has technics drawing"}
                                       ,
                                       {"6", "no techinics drawing"}
                                       ,
                                       {"7",
                                       "select the tools for edit techinics drawing"}
                                       ,
                                       {"8",
                                       "please select you AutoCad exe file��acad.exe�� path"}
                                       ,
                                       {"9", "cad path is not exist"}
                                       ,
                                       {"10", "picture files (JPG,TIF)"}
                                       ,
                                       {"ok", "OK"}
                                       ,
                                       {"cancel", "cancel"}
                                       ,
                                       {"browse file", "browse files"}
                                       ,
                                       {"browse", "browse"}
                                       ,
                                       {"edit", "edit"}
                                       ,
                                       {"view", "view"}
                                       ,
                                       {"delete", "delete"}
                                       ,
                                       {"editTip", "edit Drawing"}
                                       ,
                                       {"viewTip", "view  Drawing"}
                                       ,
                                       {"deleteTip", "delete Drawing"}
                                       ,
                                       //2008.05.29 ��־���޸� �޸�ԭ�����ڱ��������ļ���ȡ��Ӧ��Ϊ������Դ�ļ���ȡ��
                                   	   //Begin CR1
//                                     {"11", "c:/_te_eo%fs$ie@o$%#.info"},
//                                     {"tempDxfPath", "c:/_te_eo%fs$ie@o$%#.dxf"},
//                                     {"tempViewDxfPath", "c:/_te_eo%fs$ie@o$%#view.dxf"},
//                                     {"tempViewPicPath", "c:/_te_eo%fs$ie@o$%#view"},
                                 	   {"11", "_te_eo%fs$ie@o$%#.info"},
//Begin  CR3	
//                                       {"tempDxfPath", "_te_eo%fs$ie@o$%#.dxf"},
																				//�����cad�ļ���ʲô��ʽ��ʾ���¸�ΪΪjpg�ļ���������Ҫ����һ��dwg�ļ���
																				//��ɵ����Ǳ����һ��dxf�ļ����������������ø�Ϊ��te_eo%fs$ie@o$%#.dxf���͡�Autocad��
                                       {"tempDxfPath", "_te_eo%fs$ie@o$%#.jpg"},
                                       {"cadFileDisplay", "jpg"},   
                                       {"tempDxfPathChange", "_te_eo%fs$ie@o$%#.dxf"},//ת���ɵ���ʷ����ʹ��
                                       {"tempDwgPath", "_te_eo%fs$ie@o$%#.dwg"},
//End  CR3	
                                       {"tempViewDxfPath", "_te_eo%fs$ie@o$%#view.dxf"},
                                       {"tempViewPicPath", "_te_eo%fs$ie@o$%#view"},
                                 	   //End CR1
                                       //2008.11.19 ��־���޸� �޸�ԭ���ϴ���ͼʱ����û���ϴ���ͼ�ļ���Ӧ��ʾ��ͼ�ļ������ڡ�
                                       {"drawDataNull","��ͼ�ļ�����򲻴��ڣ��������ϴ���ͼ�ļ�"},
                                       {"waring","����"},
                                       {"drawSeize","�鿴��ͼʧ�ܣ����������ɱ����ļ��ѱ���һ����������ʹ�ã����ȹر��Ѳ鿴���ļ�ͼ�ļ���"}




    };
}
