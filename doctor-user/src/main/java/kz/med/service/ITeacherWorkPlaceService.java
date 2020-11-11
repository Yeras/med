package kz.med.service;

import kz.med.model.PaginationObject;

import java.util.List;
import java.util.Map;

public interface ITeacherWorkPlaceService {

    PaginationObject allByUniversityId(Map<String, String> allRequestParams,
                                       Integer universityId);

    PaginationObject searchTeacher(Map<String, String> allRequestParams,
                                   Integer universityId,
                                   String searchTeacher);

}
