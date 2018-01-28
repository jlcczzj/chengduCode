package com.faw_qm.jfpublish.receive;

/**
 * <p>Title: Blob列表文件过滤器</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: FAW_QM</p>
 * @author ShangHaiFeng
 * @version 1.0
 */

import java.io.File;
import java.io.FileFilter;

public class BlobListFileFilter
    implements FileFilter {

  private String extension = "";

  public BlobListFileFilter() {
  }

  public BlobListFileFilter(String extension) {
    this.extension = extension;
  }

  public boolean accept(File f) {
    if (f.isDirectory()) {
      return false;
    }
    String fileName = f.getName();
    String ext = FileNameParser.getFileExtension(fileName);
    if (ext != null) {
      if (ext.equalsIgnoreCase(extension.toLowerCase())) {
        return true;
      }
      else {
        return false;
      }
    }
    else {
      return false;
    }
  }

}
