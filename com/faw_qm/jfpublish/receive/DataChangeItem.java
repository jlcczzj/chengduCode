package com.faw_qm.jfpublish.receive;

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

public class DataChangeItem {
  public DataChangeItem() {
  }

  private String applecationID;

  private String holderId;

  private String streamDataID;

  private int status;

  private String mes;

  public static int SUCCESS = 1;

  public static int READY = -1;

  public static int FAILURE = 0;

  public static int SKIP = 2;

  public void setHolderId(String holderId) {
    this.holderId = holderId;
  }

  public String getHolderId() {
    return holderId;
  }

  public void setApplecationID(String applecationID) {
    this.applecationID = applecationID;
  }

  public String getApplecationID() {
    return applecationID;
  }

  public void setStreamDataID(String streamDataID) {
    this.streamDataID = streamDataID;
  }

  public String getStreamDataID() {
    return streamDataID;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void success() {
    setStatus(SUCCESS);
  }

  public void failure() {
    setStatus(FAILURE);
  }

  public void ready() {
    setStatus(READY);
  }

  public void skip() {
    setStatus(SKIP);
  }

  public boolean isSuccess() {
    return status == SUCCESS;
  }

  public boolean isReady() {
    return status == READY;
  }

  public boolean isFailure() {
    return status == FAILURE;
  }

  public boolean isSkip() {
    return status == SKIP;
  }

  public void setMessage(String mes) {
    this.mes = mes;
  }

  public String getMessage() {
    return mes;
  }

  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (! (obj instanceof DataChangeItem)) {
      return false;
    }
    DataChangeItem dataItem = (DataChangeItem) obj;
    return holderId.equals(dataItem.getHolderId())
        && applecationID.equals(dataItem.getApplecationID());
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();

    if (holderId != null) {
      sb.append(holderId);
    }
    sb.append(",");
    if (applecationID != null) {
      sb.append(applecationID);
    }
    sb.append(",");
    if (streamDataID != null) {
      sb.append(streamDataID);
    }
    sb.append(",");
    if (status == SUCCESS) {
      sb.append("Success");
    }
    else if (status == FAILURE) {
      sb.append("Failure");
    }
    else if (status == READY) {
      sb.append("Ready");
    }
    else if (status == SKIP) {
      sb.append("Skip");
    }
    else {
      sb.append("InvalidStatus");
    }
    sb.append(",");
    if (mes != null) {
      sb.append(mes);

    }
    return sb.toString();
  }

}
