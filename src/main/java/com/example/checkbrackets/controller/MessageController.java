package com.example.checkbrackets.controller;

import com.example.checkbrackets.dto.EmptyTextExceptionDTO;
import com.example.checkbrackets.dto.MessageAnswer;
import com.example.checkbrackets.dto.MessageText;
import com.example.checkbrackets.exception.EmptyTextException;
import com.example.checkbrackets.mapper.MessageMapper;
import com.example.checkbrackets.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    public MessageController(MessageService messageService, MessageMapper messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyTextException.class)
    public EmptyTextExceptionDTO handleException(Exception e) {
        return messageMapper.mapExceptionToDTO();
    }

    @PostMapping("/checkBrackets")
    public ResponseEntity<MessageAnswer> checkBrackets(@RequestBody MessageText messageText) {
        return ResponseEntity.ok(messageService.checkBrackets(messageText));
    }

}
