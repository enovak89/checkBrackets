package com.example.checkbrackets.mapper.impl;

import com.example.checkbrackets.dto.EmptyTextExceptionDTO;
import com.example.checkbrackets.dto.MessageAnswer;
import com.example.checkbrackets.dto.MessageText;
import com.example.checkbrackets.mapper.MessageMapper;
import com.example.checkbrackets.model.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageMapperImpl implements MessageMapper {

    public Message mapToEntity(MessageText text) {
        Message message = new Message();
        message.setText(text.getText());
        return message;
    }

    public MessageAnswer mapToDto(Message message) {
        MessageAnswer messageAnswer = new MessageAnswer();
        messageAnswer.setIsCorrect(message.getIsCorrect());
        return messageAnswer;
    }

    public EmptyTextExceptionDTO mapExceptionToDTO() {
        EmptyTextExceptionDTO emptyTextExceptionDTO = new EmptyTextExceptionDTO();
        emptyTextExceptionDTO.setExceptionMessage("Text must be not empty");
        return emptyTextExceptionDTO;
    }
}
