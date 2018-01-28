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
 * <p>Title: 物料清单</p>
 * <p>Description: 零部件报表中转服务。</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 启明</p>
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
//CCBegin by leixiao 2010-3-1 解放图纸及文档附件导出
  public HashMap ExportFile(Vector list,String path, String type) throws QMException;
  
  public String isInGroup(String groupname)  throws  QMException;
  //CCEnd by leixiao 2010-3-1 解放图纸及文档附件导出
//CCBegin by leix	 2010-12-20  增加逻辑总成数量报表
  public Vector setMaterialList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,
          String[] routeNames, PartConfigSpecIfc configSpecIfc,boolean islogic) throws QMException;
//CCEnd by leix	 2010-12-20  增加逻辑总成数量报表
  public void handleckwd(String issave) throws QMException;
  
  public Collection getUsesPartIfcs(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc) throws QMException;
}
