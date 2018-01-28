package com.jf.ejb.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.PartConfigSpecIfc;
import java.util.HashMap;

/**
 * <p>Title: �����嵥</p>
 * <p>Description: �㲿��������ת����</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: ����</p>
 * @author liunan 2009-02-11
 * @version 1.0
 */

public interface JFService extends BaseService
{
  public Vector setBOMList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,String[] routeNames,
                             String source, String type, PartConfigSpecIfc configSpecIfc) throws QMException;

  public Vector setMaterialList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,
                               String[] routeNames, PartConfigSpecIfc configSpecIfc) throws QMException;

  public Vector setMaterialList2(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,
                                String[] routeNames, PartConfigSpecIfc configSpecIfc) throws QMException;

  public String getExportBOMClassfiyString(HashMap map) throws QMException;

  public String getExportBOMClassfiyString2(HashMap map) throws QMException;
  
  public ArrayList gatherExportData(String routeListID, String IsExpandByProduct)  throws QMException;
//CCBegin by leixiao 2010-3-1 ���ͼֽ���ĵ���������
  public HashMap ExportFile(Vector list,String path, String type) throws QMException;
  
  public String isInGroup(String groupname)  throws  QMException;
  //CCEnd by leixiao 2010-3-1 ���ͼֽ���ĵ���������
//CCBegin by leix	 2010-12-20  �����߼��ܳ���������
  public Vector setMaterialList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,
          String[] routeNames, PartConfigSpecIfc configSpecIfc,boolean islogic) throws QMException;
//CCEnd by leix	 2010-12-20  �����߼��ܳ���������
  public void handleckwd(String issave) throws QMException;
  
  public Collection getUsesPartIfcs(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc) throws QMException;
}
