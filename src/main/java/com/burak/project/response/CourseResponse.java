package com.burak.project.response;

import com.burak.project.model.Course;
import lombok.Data;

import java.util.List;

@Data
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private Long instructorId;
    private String instructorName;
    private Integer duration;
    private Integer enrollmentCount;
    private List<WeekResponse> weekResponses;

    public CourseResponse(Course course){
        id = course.getId();
        title = course.getTitle();
        description = course.getDescription();
        instructorId = course.getInstructor().getId();
        instructorName = course.getInstructor().getUsername();
        duration = course.getDuration();
        enrollmentCount = course.getEnrollments().size();
        weekResponses = course.getWeeks().stream().map(WeekResponse::new).toList();
    }
}
