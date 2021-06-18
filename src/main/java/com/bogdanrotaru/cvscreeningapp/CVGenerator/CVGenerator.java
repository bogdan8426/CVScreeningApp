package com.bogdanrotaru.cvscreeningapp.CVGenerator;


import com.bogdanrotaru.cvscreeningapp.exceptions.CVFilesReadException;
import com.bogdanrotaru.cvscreeningapp.exceptions.CVFilesWriteException;
import com.bogdanrotaru.cvscreeningapp.exceptions.CVGeneratorException;
import com.bogdanrotaru.cvscreeningapp.model.CV;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class CVGenerator {

    private final static Logger log;

    static {
        log = Logger.getLogger(CVGenerator.class.getName());
        try {
            LogManager.getLogManager().readConfiguration(
                    new FileInputStream("logger.properties"));
        } catch (IOException e) {
            log.log(Level.WARNING, "Could not load the logger.properties file, using default logger configuration.");
        }
    }

    public CVGenerator() {
    }

    public List<CV> generateRandomFiles() throws CVGeneratorException {
        log.info("Generating random CVs based on .txt files in root folder...");

        List<CV> cvs = new RandomCVs().generate();

//        log.info("Writing .xml files to the CVs directory...");
//        writeXMLs(cvs);
        return cvs;
    }

    public ObservableList<CV> loadCVs() throws CVFilesReadException {
        try {
            return new CVReader().loadCVs();
        } catch (FileNotFoundException e) {
            throw new CVFilesReadException("Problem reading xml files: ", e);
        }
    }

    private void writeXMLs(List<CV> cvs) throws CVFilesWriteException {
        if (cvs.isEmpty()) {
            throw new CVFilesWriteException("ERROR: Please load the CVs first!");
        }
        File cvFolder = new File("C:\\Users\\Bogdan\\Desktop\\Facultate\\Java advanced\\CV Screening Application\\src\\CVScreening\\CVs");
        File[] files = cvFolder.listFiles();
        if (files == null) {
            log.info("CVs folder is empty, writing xml cvs...");
        } else {
            log.warning("CVs folder not empty, overwriting files...");
            for (File file : files)
                if (!file.isDirectory()) {
                    file.delete();
                }
        }
        new CVWriter(cvs).saveCVs();
    }


}
