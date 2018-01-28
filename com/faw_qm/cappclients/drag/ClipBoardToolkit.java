package com.faw_qm.cappclients.drag;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;


/**
 * ���а壬�û����ݵĸ��ơ�ճ������ק��
 * ע��һ��jvm�ڿ���
 * */
public class ClipBoardToolkit
{
	/** 
     * �Ӽ��а������֡� 
     */  
    public static Object getClipboardData()
    {  
    	Object paste = null;  
    	try
    	{
    		Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();  
            // ��ȡ���а��е�����  
            Transferable clipTf = sysClip.getContents(null); 
            if (clipTf != null) 
            {  
                // ��������Ƿ����ı�����  
                if (clipTf.isDataFlavorSupported(ObjectSelection.objectFlavor)) 
                {  
                	paste = clipTf.getTransferData(ObjectSelection.objectFlavor); 
                }
                else if(clipTf.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
                {
                	System.out.println("File--------");
                	paste = clipTf.getTransferData(DataFlavor.javaFileListFlavor); 
                }
                else if(clipTf.isDataFlavorSupported(DataFlavor.imageFlavor))
                {
                	System.out.println("Image--------");
                	paste = clipTf.getTransferData(DataFlavor.imageFlavor); 
                }
            } 
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	if(paste != null)
    	{
    		System.out.println("1--------" + paste.getClass().getName());
    	}
        return paste;  
    }  
    
    
    /** 
     * ���ַ������Ƶ����а塣 
     */  
    public static void setClipboardData(Object copy) 
    {  
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();  
        ObjectSelection vs = new ObjectSelection(copy);  
        clip.setContents(vs, null);  
    }
}