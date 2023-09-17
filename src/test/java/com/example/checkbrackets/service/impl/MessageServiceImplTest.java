package com.example.checkbrackets.service.impl;

import com.example.checkbrackets.dto.MessageAnswer;
import com.example.checkbrackets.dto.MessageText;
import com.example.checkbrackets.exception.EmptyTextException;
import com.example.checkbrackets.mapper.MessageMapper;
import com.example.checkbrackets.model.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MessageServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MessageServiceImplTest {
    @MockBean
    private MessageMapper messageMapper;

    private final MessageServiceImpl messageServiceImpl;

    @Autowired
    public MessageServiceImplTest(MessageServiceImpl messageServiceImpl) {
        this.messageServiceImpl = messageServiceImpl;
    }

    private final String CORRECT_TEXT = "(Abc)def(ghi). (     jkl     )!";
    private final String INCORRECT_EMPTY_TEXT = "";
    private final String INCORRECT_EMPTY_BRACKETS_TEXT = "(abc)def()!";
    private final String INCORRECT_ONE_BEGIN_BRACKET_TEXT = "(abc)def(!";
    private final String INCORRECT_ONE_END_BRACKET_TEXT = "(abc)def)!";


    @Test
    void checkCorrectBrackets() {
        MessageText correctText = new MessageText();
        correctText.setText(CORRECT_TEXT);
        Message message = new Message();

        MessageAnswer expectedResult = new MessageAnswer();
        expectedResult.setIsCorrect(true);

        when(messageMapper.mapToEntity(any(MessageText.class))).thenReturn(message);
        when(messageMapper.mapToDto(any(Message.class))).thenReturn(expectedResult);

        assertEquals(expectedResult, messageServiceImpl.checkBrackets(correctText));
        verify(messageMapper, times(1)).mapToEntity(correctText);
        verify(messageMapper, times(1)).mapToDto(message);
    }

    @Test
    void checkIncorrectBrackets() {
        MessageText correctText = new MessageText();
        correctText.setText(INCORRECT_EMPTY_BRACKETS_TEXT);
        Message message = new Message();
        MessageAnswer expectedResult = new MessageAnswer();
        expectedResult.setIsCorrect(false);

        when(messageMapper.mapToEntity(any(MessageText.class))).thenReturn(message);
        when(messageMapper.mapToDto(any(Message.class))).thenReturn(expectedResult);

        assertEquals(expectedResult, messageServiceImpl.checkBrackets(correctText));
        verify(messageMapper, times(1)).mapToEntity(correctText);
        verify(messageMapper, times(1)).mapToDto(message);
    }

    @Test
    void checkNullTextBracketsWithException() {
        MessageText correctText = new MessageText();

        assertThrows(EmptyTextException.class, () -> messageServiceImpl.checkBrackets(correctText));
    }

    @Test
    void checkEmptyTextBracketsWithException() {
        MessageText correctText = new MessageText();
        correctText.setText(INCORRECT_EMPTY_TEXT);
        Message message = new Message();

        assertThrows(EmptyTextException.class, () -> messageServiceImpl.checkBrackets(correctText));
    }

    @Test
    void textCorrectChecker() {
        assertTrue(messageServiceImpl.textChecker(CORRECT_TEXT));
    }

    @Test
    void textIncorrectEmptyBracketsChecker() {
        assertFalse(messageServiceImpl.textChecker(INCORRECT_EMPTY_BRACKETS_TEXT));
    }

    @Test
    void textIncorrectOneBeginBracketChecker() {
        assertFalse(messageServiceImpl.textChecker(INCORRECT_ONE_BEGIN_BRACKET_TEXT));
    }

    @Test
    void textIncorrectOneEndBracketChecker() {
        assertFalse(messageServiceImpl.textChecker(INCORRECT_ONE_END_BRACKET_TEXT));
    }
}