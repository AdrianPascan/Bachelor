package com.verval.business;

import com.verval.business.business.VerificationAndValidationGradeCalculator;
import com.verval.business.exception.InvalidPointException;
import com.verval.business.model.GradeType;
import com.verval.business.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;

public class VerificationAndValidationGradebookTest {

    private VerificationAndValidationGradeCalculator gradeCalculator;

    @BeforeEach
    void setup() {
        gradeCalculator = new VerificationAndValidationGradeCalculator();
    }

    @Test
    void validate_granted_point_wrong_value_test(){
        try {
            gradeCalculator.addPoint(new Point(7, 10, GradeType.Granted));
            fail();
        } catch (InvalidPointException exception) {
            System.out.println(exception.getMessage());
        }
        assertTrue(true);
    }

    @Test
    void validate_point_wrong_value_upper_test(){
        try {
            gradeCalculator.addPoint(new Point(11, 10, GradeType.Seminar));
            fail();
        } catch (InvalidPointException exception) {
            System.out.println(exception.getMessage());
        }
        assertTrue(true);
    }

    @Test
    void validate_point_wrong_value_lower_test(){
        try {
            gradeCalculator.addPoint(new Point(-1, 10, GradeType.Laboratory));
            fail();
        } catch (InvalidPointException exception) {
            System.out.println(exception.getMessage());
        }
        assertTrue(true);
    }

    @Test
    void validate_point_exam_type_test(){
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Exam));
        try {
            gradeCalculator.addPoint(new Point(7, 10, GradeType.Exam));
            fail();
        } catch (InvalidPointException exception) {
            System.out.println(exception.getMessage());
        }
        assertTrue(true);
    }

    @Test
    void validate_point_granted_type_test(){
        try {
            gradeCalculator.addPoint(new Point(10, 10, GradeType.Granted));
            fail();
        } catch (InvalidPointException exception) {
            System.out.println(exception.getMessage());
        }
        assertTrue(true);
    }

    @Test
    void validate_passing_laboratory_failed(){
        gradeCalculator.addPoint(new Point(7, 10, GradeType.Laboratory));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Seminar));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Bonuses));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Bonuses));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Bonuses));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Exam));

        try {
            assertTrue(gradeCalculator.isPassing());
            fail();
        } catch (AssertionFailedError error) {
            System.out.println(error.getExpected());
            assertTrue(true);
        }
    }

    @Test
    void validate_passing_laboratory_passed(){
        gradeCalculator.addPoint(new Point(6, 10, GradeType.Laboratory));
        gradeCalculator.addPoint(new Point(5, 10, GradeType.Laboratory));
        gradeCalculator.addPoint(new Point(6, 10, GradeType.Seminar));
        gradeCalculator.addPoint(new Point(5, 10, GradeType.Seminar));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Bonuses));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Bonuses));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Bonuses));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Exam));

        assertTrue(gradeCalculator.isPassing());

    }

    @Test
    void validate_passing_seminary_failed(){
        gradeCalculator.addPoint(new Point(7, 10, GradeType.Seminar));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Laboratory));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Bonuses));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Bonuses));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Bonuses));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Exam));

        try {
            assertTrue(gradeCalculator.isPassing());
            fail();
        } catch (AssertionFailedError error) {
            System.out.println(error.getExpected());
            assertTrue(true);
        }
    }

    @Test
    void validate_passing_seminary_passed(){
        gradeCalculator.addPoint(new Point(6, 10, GradeType.Seminar));
        gradeCalculator.addPoint(new Point(5, 10, GradeType.Seminar));
        gradeCalculator.addPoint(new Point(6, 10, GradeType.Laboratory));
        gradeCalculator.addPoint(new Point(5, 10, GradeType.Laboratory));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Bonuses));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Bonuses));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Bonuses));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Exam));

        assertTrue(gradeCalculator.isPassing());
    }

    @Test
    void validate_passing_grade_failed(){
        gradeCalculator.addPoint(new Point(6, 10, GradeType.Seminar));
        gradeCalculator.addPoint(new Point(5, 10, GradeType.Seminar));
        gradeCalculator.addPoint(new Point(6, 10, GradeType.Laboratory));
        gradeCalculator.addPoint(new Point(5, 10, GradeType.Laboratory));
        gradeCalculator.addPoint(new Point(10, 10, GradeType.Exam));

        try {
            assertTrue(gradeCalculator.isPassing());
            fail();
        } catch (AssertionFailedError error) {
            System.out.println(error.getExpected());
            assertTrue(true);
        }
    }

}
