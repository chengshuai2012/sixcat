package com.link.cloud;

/**
 * Created by ymh on 2016/7/1 0001.
 */
public class Events {


    /**
     * 关闭
     */
    public static class CloseEvent {
        public static final String FINISH_ALL = "finishAll";
        public static final String FINISH_BUY_VIP = "finishBuyVip";
        public static final String FINISH_VIDEO_PREVIEW = "finishVideoPreveiw";

        public String eventType;
        public final String[] ignoreClass;

        public CloseEvent(String eventType, String... ignoreClass) {
            this.eventType = eventType;
            this.ignoreClass = ignoreClass;
        }
    }

    public static class NextView {
    }

    public static class CardInfoNextView {
    }

    public static class BackView {
    }

      public static class BackAndClearnView {
    }




    public static class SuccessView {
    }

    public static class finish {
    }


}
