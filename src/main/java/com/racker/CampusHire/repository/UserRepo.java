package com.racker.CampusHire.repository;

import com.racker.CampusHire.entity.Department;
import com.racker.CampusHire.entity.Role;
import com.racker.CampusHire.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUid(String uid);

    boolean existsByEmpId(String empId);

    List<User> findAllByDepartment(Department department);
    List<User> findAllByRole(Role role);
}
