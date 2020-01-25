package CVScreening.CVGenerator;

import CVScreening.DataModel.CV;

public abstract class CVReadWrite {

    String getFileName(CV cv){
        return "CV_" + cv.getInfo().getFirstName() + "_" + cv.getInfo().getLastName() + ".xml";
    }

}
