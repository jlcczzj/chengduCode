package com.faw_qm.cappclients.drag;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Vector;


/**
 * 自定义剪切板数据类型，封装Vector，内部元素为可序列化对象
 * 
 * */
public class ObjectSelection implements Transferable,  ClipboardOwner
{
	public static final DataFlavor objectFlavor = new DataFlavor(Object.class, "object");//class为自定义的java类 字串随便  
	private static final DataFlavor[] flavors = { objectFlavor };  
	private Object data;  
	
	
	
	public ObjectSelection(Object data)
	{  
	    this.data = data;  
	}  
	
	
	
	      
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException 
	{
		
	    if (flavor.equals(flavors[0])) 
	    {
	        return data;  
	    } 
	    else 
	    {  
	        throw new UnsupportedFlavorException(flavor);  
	    }  
	}  
	  
	
	
	
	public DataFlavor[] getTransferDataFlavors() 
	{  
	    return (DataFlavor[]) flavors.clone();  
	}  
	  
	
	
	public boolean isDataFlavorSupported(DataFlavor flavor) 
	{  
		 if (flavor.equals(flavors[0])) 
		 {  
             return true;  
         }  
		 return false;
	}  
	  

	
	public void lostOwnership(Clipboard clipboard, Transferable contents) 
	{  
	  
	} 
}