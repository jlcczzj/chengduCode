package com.faw_qm.jfpublish.receive;

public abstract class AbstractStoreDelegate {

    protected PublishLoadHelper myHelper = null;

    public abstract ResultReport process();

    public void log(Object msg) {
        PublishPartsLog.log(msg);
    }

    public void errorLog(Object msg) {
        if (msg instanceof Throwable) {
            PublishPartsLog.log((Throwable) msg);
        } else {
            PublishPartsLog.log("*****ERROR: " + msg);
        }
    }

    public void userLog(Object obj) {
        myHelper.logger.userLog(obj);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
