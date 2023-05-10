package com.arrkadique.springmessenger.repository;

import com.arrkadique.springmessenger.entity.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DialogRepository extends JpaRepository<Dialog, Long> {
    Dialog findByUserId1AndUserId2(Long user_id1, Long user_id2);
}
