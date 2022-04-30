package com.timeshaft.after_end.service;

import com.timeshaft.after_end.entity.MessageStateType;

public interface MessageStateService {

    MessageStateType strToEnum(String state);

    String EnumToString(MessageStateType stateType);

}
