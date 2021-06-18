package com.bogdanrotaru.cvscreeningapp.repositories;

import com.bogdanrotaru.cvscreeningapp.model.CV;
import com.bogdanrotaru.cvscreeningapp.model.dto.CvDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public interface CVRepository extends CrudRepository<CvDao, String> {

    List<CvDao> findAll();
}
