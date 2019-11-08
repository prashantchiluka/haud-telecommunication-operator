package com.haud.telecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haud.telecom.entity.SimCard;

@Repository
public interface SimCardRepository extends JpaRepository<SimCard,Long>{

}
