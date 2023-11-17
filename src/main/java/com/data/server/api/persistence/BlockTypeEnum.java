package com.data.server.api.persistence;

import java.util.Arrays;
import java.util.Optional;

public enum BlockTypeEnum {
    BLOCKTYPEA("blocktypea"),
    BLOCKTYPEB("blocktypeb");

    private final String type;

    BlockTypeEnum(String type) {
        this.type = type;
    }


    public static Optional<BlockTypeEnum> getBlockByType(String blockType) {
        return Arrays.stream(BlockTypeEnum.values())
                .filter(blockTypeEnum -> blockTypeEnum.type.equalsIgnoreCase(blockType))
                .findFirst();
    }

}
