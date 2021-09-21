package com.lynjava.ddd.test.dto;

/**
 * Created by liyanan on 2018/6/16.
 */
public class LiveResponseCode extends BaseResponseCode {

    public LiveResponseCode(int code, String message) {
        super(code, message);
    }

    public static final BaseResponseCode LIVE_ROOM_HAS_DELETED = new LiveResponseCode(1001, "直播间已删除");
    public static final BaseResponseCode LIVE_ROOM_START = new LiveResponseCode(1002, "直播间开始时间是%s");
}
