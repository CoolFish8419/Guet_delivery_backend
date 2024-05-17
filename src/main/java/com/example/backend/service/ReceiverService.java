package com.example.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.dto.*;
import com.example.backend.entity.Receiver;

public interface ReceiverService extends IService<Receiver> {
    ReceiverResponse All(ReceiverDTO receiverDTO);
    ReceiverResponse Ma_All();
    ReceiverResponse Search(ReceiverDTO receiverDTO);
    ReceiverResponse Delete(ReceiverDTO receiverDTO);
    ReceiverResponse Update(ReceiverDTO receiverDTO);
    ReceiverResponse Add(ReceiverDTO receiverDTO);
    ReceiverResponse Details(ReceiverDTO receiverDTO);

}
