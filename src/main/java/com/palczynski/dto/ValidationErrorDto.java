package com.palczynski.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Data
public class ValidationErrorDto {

    private List<Message> data;

    public void addMessage(String field, String message) {
        if(isNull(data)) {
            data = new ArrayList<>();
        }
        data.add(new ValidationErrorDto.Message(field, message));
    }

    @Data
    public class Message {

        private String field;
        private String message;

        public Message(String field, String message) {
            this.field = field;
            this.message = message;
        }

    }

}
