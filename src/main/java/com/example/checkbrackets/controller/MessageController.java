package com.example.checkbrackets.controller;

import com.example.checkbrackets.dto.EmptyTextExceptionDTO;
import com.example.checkbrackets.dto.MessageAnswer;
import com.example.checkbrackets.dto.MessageText;
import com.example.checkbrackets.exception.EmptyTextException;
import com.example.checkbrackets.mapper.MessageMapper;
import com.example.checkbrackets.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Проверить расстановку скобок",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "*/*",
                                    schema = @Schema(implementation = MessageAnswer.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
            }
    )
    @PostMapping("/checkBrackets")
    public ResponseEntity<MessageAnswer> checkBrackets(@RequestBody MessageText messageText) {
        return ResponseEntity.ok(messageService.checkBrackets(messageText));
    }
}
