package com.luv2code.springmvc.repository;

import com.luv2code.springmvc.models.CollegeStudent;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends CrudRepository<CollegeStudent, Integer> {

  @Query(value = "SELECT * FROM student s WHERE s.email_address=:email", nativeQuery = true)
  public Optional<CollegeStudent> findByEmailAddress(@Param("email") String email);

}
