package com.faw_qm.jfpublish.receive;

import com.faw_qm.pcfg.logic.LogicBase;
import com.faw_qm.pcfg.logic.XMLToLogicBase;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class LogicBaseConversion {

  public LogicBaseConversion() {
  }

  public static LogicBase xmlToLogicBase(String xml) {
  	//System.out.println("liunan in xmlToLogicBase xml ===== "+xml);
    return XMLToLogicBase.loadLogicBase(xml);
  }
}
