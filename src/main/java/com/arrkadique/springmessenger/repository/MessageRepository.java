package com.arrkadique.springmessenger.repository;

import com.arrkadique.springmessenger.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByDialogId(Long Id);
}
