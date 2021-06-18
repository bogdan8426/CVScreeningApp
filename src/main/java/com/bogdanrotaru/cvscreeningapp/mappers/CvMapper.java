package com.bogdanrotaru.cvscreeningapp.mappers;

import com.bogdanrotaru.cvscreeningapp.model.CV;
import com.bogdanrotaru.cvscreeningapp.model.dto.CvDao;

import java.util.UUID;

public class CvMapper {


    public static CV map(CvDao cvDao) {
        return new CV(cvDao.getId(), cvDao.getInfo(), cvDao.getEducation(), cvDao.getExperience());
    }

    public static CvDao map(CV cv) {
        return new CvDao(cv.getId() == null ? UUID.randomUUID().toString() : cv.getId().toString(),
                cv.getInfo(), cv.getEducation(), cv.getExperience());
    }
}
