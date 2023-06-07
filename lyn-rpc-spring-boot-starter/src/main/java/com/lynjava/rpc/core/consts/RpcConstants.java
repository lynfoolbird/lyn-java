package com.lynjava.rpc.core.consts;

public interface RpcConstants {
    int ZK_CONNECT_BASE_SLEEP_TIME_MS = 1000;
    int ZK_CONNECT_MAX_RETRIES = 3;
    String ZK_BASE_PATH = "/sb_rpc";

    interface PROTOCOL {
        int HEADER_TOTAL_LEN = 42;
        short MAGIC = 0x00;
        byte VERSION = 0x1;
        int REQ_LEN = 32;
    }

}
