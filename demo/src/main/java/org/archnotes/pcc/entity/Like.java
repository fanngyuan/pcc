package org.archnotes.pcc.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by fanngyuan on 12/22/16.
 */
public class Like {

    @JSONField(name = "target_id")
    private int targetId;

    @JSONField(name = "user_id")
    private int userId;

    private int type;

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
