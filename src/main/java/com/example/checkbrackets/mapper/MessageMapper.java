package com.example.checkbrackets.mapper;

import com.example.checkbrackets.dto.EmptyTextExceptionDTO;
import com.example.checkbrackets.dto.MessageAnswer;
import com.example.checkbrackets.dto.MessageText;
import com.example.checkbrackets.model.Message;

public interface MessageMapper {
    Message mapToEntity(MessageText text);

    MessageAnswer mapToDto(Message message);

    EmptyTextExceptionDTO mapExceptionToDTO();
}
