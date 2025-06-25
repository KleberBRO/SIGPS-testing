package com.ufrpe.sigps.repository;

import com.ufrpe.sigps.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
}