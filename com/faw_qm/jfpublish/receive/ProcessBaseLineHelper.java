package com.faw_qm.jfpublish.receive;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import java.util.Collection;
import java.util.Iterator;

import com.faw_qm.baseline.ejb.service.BaselineService;
import com.faw_qm.baseline.model.BaselineIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.util.EJBServiceHelper;

public class ProcessBaseLineHelper {

  public ProcessBaseLineHelper() {
  }

  /**
   *
   * ͨ������ɾ�����з�����part
   *
   * @param baseline
   *            BaselineIfc
   * @throws QMException
   */
  public static void deletePartByBaseLine(BaselineIfc baseline) throws
      QMException {

    QMPartInfo part = null;

    // �ҵ�ָ�������е����ж���
    StandardPartService partser = (StandardPartService) EJBServiceHelper
        .getService("StandardPartService");
    BaselineService blser = (BaselineService) EJBServiceHelper
        .getService("BaselineService");
    Collection baseItems = blser.getBaselineItems(baseline);

    // �ӻ�����ɾ������
    blser.removeFromBaseline(baseItems, baseline);
    // ������ɾ��ԭ�����еĶ���
    for (Iterator ite = baseItems.iterator(); ite.hasNext(); ) {
      part = (QMPartInfo) ite.next();
      partser.deletePart(part);
    }
  }
}
