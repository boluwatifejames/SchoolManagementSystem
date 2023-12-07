package com.billsTech.PortalSystem01.repositories;

import com.billsTech.PortalSystem01.entity.TeacherRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadRepo extends CrudRepository<TeacherRecord,Long> {
}
