package com.bogdanrotaru.cvscreeningapp.model.dto;

import com.bogdanrotaru.cvscreeningapp.model.Education;
import com.bogdanrotaru.cvscreeningapp.model.Experience;
import com.bogdanrotaru.cvscreeningapp.model.PersonalInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
public class CvDao {

    private String id;
    private PersonalInfo info;
    private List<Education> education;
    private List<Experience> experience;


}
