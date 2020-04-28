package com.kuroha.netty.netty.common;

import lombok.Data;

/**
 * @author kuroha
 */
@Data
public class MessageHeader {

    private int version = 1;

    private long streamId;

    private int opCode;

}
