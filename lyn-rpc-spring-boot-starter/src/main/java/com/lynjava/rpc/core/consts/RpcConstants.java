package com.lynjava.rpc.core.consts;

public interface RpcConstants {
    String CONFIG_PREFIX = "lyn.rpc";

    interface ZK {
        int CONNECT_BASE_SLEEP_TIME_MS = 1000;
        int CONNECT_MAX_RETRIES = 3;
        String REGISTRY_BASE_PATH = "/lyn_rpc";
    }

    interface PROTOCOL {
        int HEADER_TOTAL_LEN = 42;
        short MAGIC = 0x00;
        byte VERSION = 0x1;
        int REQ_LEN = 32;
    }

}
