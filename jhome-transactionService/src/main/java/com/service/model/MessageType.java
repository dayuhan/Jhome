package com.service.model;

public enum MessageType {
    //0：待确认（需要巡检） 1：提交事务（需要巡检）  2：删除事务  3： 已完成  4： 回滚事务（需要巡检）
    WAITINGCONFIRM(0), COMMIT_TRANSACTION(1), DEL_TRANSACTION(2), COMPLETED(3), BACK_TRANSACTION(4);
    private Integer type;

    MessageType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
