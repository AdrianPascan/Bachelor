package com.verval.business.business;

import com.verval.business.exception.InvalidPointException;
import com.verval.business.model.GradeType;
import com.verval.business.model.Point;

import java.util.ArrayList;

public class VerificationAndValidationGradeCalculator {
    private  ArrayList<Point> points;
    private String studentId;

    public VerificationAndValidationGradeCalculator() {
        points = new ArrayList<Point>();
        points.add(new Point(10,10, GradeType.Granted));
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }


    public void addPoint(Point point) {
        validatePoint(point);
        points.add(point);
    }

    public double getGrade() {
        return points.stream().mapToDouble(Point::getPoint).sum() / 10;
    }

    // Task B_1
    // Validate the value of the point, it needs to be in the interval of [0,10]
    // If the point is invalid throw InvalidPointException
    private void validatePoint(Point point) {
        // Add code here to validate the point

        // validate interval
        if (point.getType() == GradeType.Granted) {
            if (point.getPoint() != 10) {
                throw new InvalidPointException("Granted point should have the value 10.");
            }
        } else {
            if (!((point.getPoint() >= 0) && (point.getPoint() <= 10)))
                throw new InvalidPointException("Point should have the value between 0 and 10.");
        }

        // validate type of point, the exam & granted can be added only once
        if (point.getType() == GradeType.Exam) {
            if (existsGradeType(GradeType.Exam)) {
                throw new InvalidPointException("Exam point is already added.");
            }
        }

        if (point.getType() == GradeType.Granted) {
            if (existsGradeType(GradeType.Granted)) {
                throw new InvalidPointException("Granted point is already added.");
            }
        }
    }

    private double getPointsSumOfType(GradeType type) {
        return points.stream().filter(point->point.getType() == type)
                .mapToDouble(Point::getPoint).sum();
    }

    // Task B_2
    // Conditions for a passing grade are listed below
    // The sum of the laboratory grades needs to be greater than 10
    // The sum of the seminar grades needs to be greater than 10
    // There can only one exam grade be added
    // The grade calculated with the getGrade function needs to be greater or equal to 5
	public boolean isPassing() {
        // Add code here to check if the grade is passing or not
        // Return true or false
        if (getPointsSumOfType(GradeType.Laboratory) <= 10)
            return false;
        if (getPointsSumOfType(GradeType.Seminar) <= 10)
            return false;
        if (countOfGradeType(GradeType.Exam) != 1)
            return false;
        return !(getGrade() < 5);
    }

    // calculate the number of points which have a specific type
    private int countOfGradeType(GradeType type) {
        return (int) points.stream().filter(point -> point.getType() == type).count();
    }

    // checks if there is a point with that specific type
    private boolean existsGradeType(GradeType type) {
        int count = (int) points.stream().filter(point -> point.getType() == type).count();

        return count != 0;
    }
}
