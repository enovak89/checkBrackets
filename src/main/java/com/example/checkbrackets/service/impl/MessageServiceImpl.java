package com.example.checkbrackets.service.impl;

import com.example.checkbrackets.dto.MessageAnswer;
import com.example.checkbrackets.dto.MessageText;
import com.example.checkbrackets.exception.EmptyTextException;
import com.example.checkbrackets.mapper.MessageMapper;
import com.example.checkbrackets.model.Message;
import com.example.checkbrackets.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;


    public MessageServiceImpl(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public MessageAnswer checkBrackets(MessageText messageText) {
        String text = messageText.getText();
        if (text == null || text.isEmpty()) {
            throw new EmptyTextException();
        }
        Message message = messageMapper.mapToEntity(messageText);
        message.setIsCorrect(textChecker(text));
        return messageMapper.mapToDto(message);
    }

    public Boolean textChecker(String text) {
        Deque<Character> deque = new LinkedList<>();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '(') {
                if (deque.contains('(')) {
                    return false;
                } else {
                    deque.addFirst(text.charAt(i));
                }
            } else if (deque.isEmpty() && text.charAt(i) == ')') {
                return false;
            } else if (text.charAt(i) != ')' && !deque.isEmpty() && deque.getFirst() == '(' && text.charAt(i) != ' ') {
                deque.addLast(text.charAt(i));
            }
            if (text.charAt(i) == ')' && deque.size() > 1) {
                deque.clear();
            }
        }
        return deque.isEmpty();
    }
}
