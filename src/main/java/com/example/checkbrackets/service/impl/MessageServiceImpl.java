package com.example.checkbrackets.service.impl;

import com.example.checkbrackets.dto.MessageAnswer;
import com.example.checkbrackets.dto.MessageText;
import com.example.checkbrackets.mapper.MessageMapper;
import com.example.checkbrackets.model.Message;
import com.example.checkbrackets.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;


    public MessageServiceImpl(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public MessageAnswer checkBrackets(MessageText messageText) {
        Message message = messageMapper.mapToEntity(messageText);
        message.setIsCorrect(true);
        MessageAnswer messageAnswer = messageMapper.mapToDto(message);
        return messageAnswer;
    }
}
