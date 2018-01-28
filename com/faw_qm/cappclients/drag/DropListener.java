package com.faw_qm.cappclients.drag;

import java.awt.Component;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.util.TooManyListenersException;

public abstract class DropListener extends DropTargetAdapter
{
	public DropListener(Component component)
	{
		if(component != null)
		{
    		try 
    		{
    			DropTarget target = new DropTarget();
        		target.setComponent(component);
        		target.setActive(true);
				target.addDropTargetListener(this);
			} 
    		catch (TooManyListenersException e) 
    		{
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void drop(DropTargetDropEvent dtde)
	{
		try
		{
			dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			Transferable transferable = dtde.getTransferable();
			if(transferable.isDataFlavorSupported(getDataFlavor()))
			{
				Object data = transferable.getTransferData(getDataFlavor());
				display(data, dtde.getLocation());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置返回数据类型
	 * */
	public abstract DataFlavor getDataFlavor();
	
	
	/**
	 * 设置数据
	 * data为拖拽数据，P为拖拽位置(用于确定位置，例如JTable中根据rowAtPoint(Point point) 确定行索引等)
	 * */
	public abstract void display(Object data, Point p);
}