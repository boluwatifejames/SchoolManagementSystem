package com.billsTech.PortalSystem01.repositories;

import com.billsTech.PortalSystem01.entity.GeneralRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneralRecordsRepo extends CrudRepository<GeneralRecord,Long> {
    List<GeneralRecord> findByMatricNumber(String matricNumber);
    List<GeneralRecord> findByMatricNumberAndLevelAndSemester(String matricNumber , String level , String semester);
}
