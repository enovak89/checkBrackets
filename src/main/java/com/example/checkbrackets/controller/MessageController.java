package com.example.checkbrackets.controller;

import com.example.checkbrackets.dto.MessageAnswer;
import com.example.checkbrackets.dto.MessageText;
import com.example.checkbrackets.exception.EmptyTextException;
import com.example.checkbrackets.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyTextException.class)
    public String handleException(Exception e) {
        return HttpStatus.BAD_REQUEST.toString() + " " + e.getMessage();
    }

    @PostMapping("/checkBrackets")
    public ResponseEntity<MessageAnswer> checkBrackets(@RequestBody MessageText messageText) {
//        if (messageText.getText() == null || messageText.getText().isEmpty()) {
//            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
//        }
        return ResponseEntity.ok(messageService.checkBrackets(messageText));
    }

}
