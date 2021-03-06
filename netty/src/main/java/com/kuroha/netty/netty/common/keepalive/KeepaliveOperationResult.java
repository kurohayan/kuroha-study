package com.kuroha.netty.netty.common.keepalive;

import com.kuroha.netty.netty.common.OperationResult;
import lombok.Data;

/**
 * @author kuroha
 */
@Data
public class KeepaliveOperationResult extends OperationResult {

    private final Long time;

}
