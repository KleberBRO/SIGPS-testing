package com.ufrpe.sigps.repository;

import com.ufrpe.sigps.model.Inventor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventorRepository extends JpaRepository<Inventor, Long> {
    // Você pode adicionar métodos específicos aqui se precisar, por exemplo:
    // List<Inventor> findByDepartment(String department);
}