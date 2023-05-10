package com.arrkadique.springmessenger.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "dialog")
@Data
@Slf4j
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId1;

    private Long userId2;
}
