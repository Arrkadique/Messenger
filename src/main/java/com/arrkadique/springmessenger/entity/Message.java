package com.arrkadique.springmessenger.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "message")
@Data
@Slf4j
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private Long dialogId;

    private String username;

    private String messageText;

    private String avatar;

    public Message(Long dialogId, String username, String messageText, String avatar) {
        this.dialogId = dialogId;
        this.username = username;
        this.messageText = messageText;
        this.avatar = avatar;
    }

    public Message(String admin, String noOneMessages) {
        this.username = admin;
        this.messageText = noOneMessages;
    }
    public Message(Long dialogId,String admin, String noOneMessages) {
        this.username = admin;
        this.messageText = noOneMessages;
        this.dialogId = dialogId;
    }

    public Message() {

    }
}
