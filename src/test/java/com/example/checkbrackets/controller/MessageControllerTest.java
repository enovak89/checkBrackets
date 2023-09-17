package com.example.checkbrackets.controller;

import com.example.checkbrackets.dto.EmptyTextExceptionDTO;
import com.example.checkbrackets.dto.MessageAnswer;
import com.example.checkbrackets.dto.MessageText;
import com.example.checkbrackets.exception.EmptyTextException;
import com.example.checkbrackets.mapper.MessageMapper;
import com.example.checkbrackets.service.MessageService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
class MessageControllerTest {

    @MockBean
    private MessageService messageService;
    @MockBean
    private MessageMapper messageMapper;

    private final MessageController messageController;
    private final MockMvc mockMvc;

    @Autowired
    public MessageControllerTest(MessageController messageController, MockMvc mockMvc) {
        this.messageController = messageController;
        this.mockMvc = mockMvc;
    }

    @Test
    void handleExceptionCorrect() throws Exception {
        EmptyTextExceptionDTO emptyTextExceptionDTO = new EmptyTextExceptionDTO();
        emptyTextExceptionDTO.setExceptionMessage("Text must be not empty");
        JSONObject messageEmptyTextJSON = new JSONObject();

        when(messageController.checkBrackets(any(MessageText.class))).thenThrow(EmptyTextException.class);
        when(messageMapper.mapExceptionToDTO()).thenReturn(emptyTextExceptionDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/checkBrackets")
                        .content(messageEmptyTextJSON.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exceptionMessage").value(emptyTextExceptionDTO.getExceptionMessage()));
    }

    @Test
    void checkBracketsCorrect() throws Exception {
        MessageAnswer messageAnswerCorrect = new MessageAnswer();
        messageAnswerCorrect.setIsCorrect(true);
        JSONObject messageTextCorrectJSON = new JSONObject();
        messageTextCorrectJSON.put("text", "(ABC)");

        when(messageService.checkBrackets(any(MessageText.class))).thenReturn(messageAnswerCorrect);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/checkBrackets")
                        .content(messageTextCorrectJSON.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value("true"));
    }

    @Test
    void checkBracketsIncorrect() throws Exception {
        MessageAnswer messageAnswerIncorrect = new MessageAnswer();
        messageAnswerIncorrect.setIsCorrect(false);
        JSONObject messageTextCorrectJSON = new JSONObject();
        messageTextCorrectJSON.put("text", "(ABC");

        when(messageService.checkBrackets(any(MessageText.class))).thenReturn(messageAnswerIncorrect);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/checkBrackets")
                        .content(messageTextCorrectJSON.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value("false"));
    }
}