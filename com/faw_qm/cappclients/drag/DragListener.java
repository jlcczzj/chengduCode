package com.faw_qm.cappclients.drag;

import java.awt.Component;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;



public abstract class DragListener implements DragGestureListener
{
	private DragSource ds;
	
	public DragListener(Component component)
	{
		if(component != null)
		{
			ds = DragSource.getDefaultDragSource();
			DragSource ds = new DragSource();
			DragGestureRecognizer dgr = ds.createDefaultDragGestureRecognizer(component, DnDConstants.ACTION_COPY, this);
		}
	}

	public void dragGestureRecognized(DragGestureEvent dge) 
	{
		Object data = getTransferableData();
		System.out.println("TransferableData--------drag-----------" + data);
		if(data != null)
		{
			ObjectSelection os = new ObjectSelection(data);
			if(ds != null)
			{
				ds.startDrag(dge, DragSource.DefaultCopyDrop, os, new DragSourceAdapter(){});
			}
		}
	}
	
	
	/**
	 * …Ë÷√ ˝æ›
	 * */
	public abstract Object getTransferableData();
}