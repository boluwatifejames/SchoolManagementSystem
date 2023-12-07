package com.billsTech.PortalSystem01.repositories;

import com.billsTech.PortalSystem01.entity.Complaints;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ComplaintRepo extends CrudRepository<Complaints,Long> {
    @Query("SELECT c.firstName , c.lastName , c.complaintDescription ,c.date FROM Complaints c WHERE  c.matricNumber = :matricNumber")
    List<Object[]> findByMatricNumber(String matricNumber);
}
