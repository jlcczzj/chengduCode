/** ���� DrawingPanelRB.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2011/12/13 �ֺ��� �޸�ԭ�򣺲μ�TD����5415
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
public class DrawingPanelRB extends ListResourceBundle
{

    /**
     @roseuid 3F4DB3FE0278
     */
    public DrawingPanelRB()
    {

    }


    /**
     @return Object[][]
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents = new String[][]{
	{ "1", "��Ҫ������༭��ͼ��" },
	{ "2", "��ͼ" },
	{ "3", "��ͼ������" },
	{ "4", "��Ҫ�鿴��ͼ��" },
	{ "5", "�й��ռ�ͼ" },
	{ "6", "�޹��ռ�ͼ" },
	{ "7", "ѡ��༭���ռ�ͼ����" },
	{ "8", "��ѡ�����AutCad�Ŀ�ִ���ļ���acad.exe����λ��" },
	{ "9", "cad ·��������" },
	{ "10", "ͼƬ�ļ�(JPG,TIF)" },
	{ "ok", "ȷ��(Y)" },
	{ "cancel", "ȡ��(C)" },
	{ "browse file", "ѡ���ļ�" },
	{ "browse", "���(B)" },
	{ "edit", "�༭" },
	{ "view", "�鿴" },
	{ "delete", "ɾ��" },
	{ "editTip", "�༭��ͼ" },
	{ "viewTip", "�鿴��ͼ" },
	{ "deleteTip", "ɾ����ͼ" },
	//{"path","C:/_te_eo%fs$ie@o$%#.info"}
    //2008.05.29 ��־���޸� �޸�ԭ�����ڱ��������ļ���ȡ��Ӧ��Ϊ������Դ�ļ���ȡ��
	//Begin CR1
//    {"11", "c:/_te_eo%fs$ie@o$%#.info"},
//    {"tempDxfPath", "c:/_te_eo%fs$ie@o$%#.dxf"},
//    {"tempViewDxfPath", "c:/_te_eo%fs$ie@o$%#view.dxf"},
//    {"tempViewPicPath", "c:/_te_eo%fs$ie@o$%#view"},
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
    {"warning","����"},
    {"drawSeize","�鿴��ͼʧ�ܣ����������ɱ����ļ��ѱ���һ����������ʹ�ã����ȹر��Ѳ鿴���ļ�ͼ�ļ���"}

    

    };
}
