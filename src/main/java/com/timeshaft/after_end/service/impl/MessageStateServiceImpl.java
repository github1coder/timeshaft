package com.timeshaft.after_end.service.impl;

import com.timeshaft.after_end.entity.MessageStateType;
import com.timeshaft.after_end.service.MessageStateService;
import org.springframework.stereotype.Service;

/**
 * feishu
 * 实现消息状态字符串与枚举类的转换
 *
 * @author : dxt
 * @since : 2022-04-28 23:25
 **/
@Service("MessageStateService")
public class MessageStateServiceImpl implements MessageStateService {

    @Override
    public MessageStateType strToEnum(String state) {
        switch (state) {
            case "read": return MessageStateType.READ;
            case "notRead": return MessageStateType.UNREAD;
            default: return null;
        }
    }


    @Override
    public String EnumToString(MessageStateType stateType) {
        switch (stateType) {
            case READ: return "read";
            case UNREAD: return "notRead";
            default: return null;
        }
    }
}
