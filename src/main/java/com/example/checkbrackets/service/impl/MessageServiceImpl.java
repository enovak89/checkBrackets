package com.example.checkbrackets.service.impl;

import com.example.checkbrackets.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    public Boolean checkBrackets() {
        return true;
    }
}
