package com.example.checkbrackets.service;

import com.example.checkbrackets.dto.MessageAnswer;
import com.example.checkbrackets.dto.MessageText;


public interface MessageService {
    MessageAnswer checkBrackets(MessageText messageText);
}
