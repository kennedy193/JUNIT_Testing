package model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Entity
public class StudentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id")
    private UUID registration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private UUID course;

    @Column(nullable = false)
    private int marksInCourse;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRegistration() {
        return registration;
    }

    public void setRegistration(UUID registrationNumber) {
        this.registration = registrationNumber;
    }

    public UUID getCourse() {
        return course;
    }

    public void setCourse(UUID courseId) {
        this.course = courseId;
    }

    public int getMarksInCourse() {
        return marksInCourse;
    }

    public void setMarksInCourse(int marksInCourse) {
        this.marksInCourse = marksInCourse;
    }
}
