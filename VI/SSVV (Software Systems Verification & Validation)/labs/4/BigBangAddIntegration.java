package ssvv.org.example;

import org.junit.Test;
import ssvv.org.example.Exceptions.ValidatorException;
import ssvv.org.example.Repository.XMLFileRepository.NotaXMLRepo;
import ssvv.org.example.Repository.XMLFileRepository.StudentXMLRepo;
import ssvv.org.example.Repository.XMLFileRepository.TemaLabXMLRepo;
import ssvv.org.example.Service.XMLFileService.NotaXMLService;
import ssvv.org.example.Service.XMLFileService.StudentXMLService;
import ssvv.org.example.Service.XMLFileService.TemaLabXMLService;
import ssvv.org.example.Validator.NotaValidator;
import ssvv.org.example.Validator.StudentValidator;
import ssvv.org.example.Validator.TemaLabValidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.*;

public class BigBangAddIntegration {
    StudentValidator studentValidator = new StudentValidator();
    StudentXMLRepo studentXMLRepositoty = new StudentXMLRepo(studentValidator, "src/main/StudentiXML.xml");
    StudentXMLService studentXMLService = new StudentXMLService(studentXMLRepositoty);

    TemaLabValidator assignmentValidator = new TemaLabValidator();
    TemaLabXMLRepo temaLabXMLRepo = new TemaLabXMLRepo(assignmentValidator, "src/main/TemaLaboratorXML.xml");
    TemaLabXMLService temaLabXMLService = new TemaLabXMLService(temaLabXMLRepo);

    NotaValidator vn=new NotaValidator();
    NotaXMLRepo ntrepo=new NotaXMLRepo(vn, "src/main/NotaXML.xml");
    NotaXMLService gradeService=new NotaXMLService(ntrepo);

    int studentId = 1;
    int asssignmentId = 2;
    int gradeId = 3;

    @Test
    public void testAdd() {
        testAddStudent();
        testAddAssignment();
        testAddGrade();
    }

    @Test
    public void testAddStudentAssigmentGrade() {
        int id = studentId;
        String professor = "Ionel`s professor";
        String email = "Ionel@email";
        String group = "5";
        String name = "Ionel";

        int idAssignment = asssignmentId;
        String description = "des";
        int deadlineWeek = 1;
        int weekToPressent = 2;

        int value = 8;
        LocalDateTime time = LocalDateTime.now();

        String[] paramsGrade = {String.valueOf(gradeId), String.valueOf(studentId),
                String.valueOf(asssignmentId), String.valueOf(value), time.toString()};

        String[] paramsAssig = {String.valueOf(idAssignment), description, String.valueOf(deadlineWeek), String.valueOf(weekToPressent)};

        String[] paramsStud = {String.valueOf(id), name, group, email, professor};


        try {
            studentXMLService.add(paramsStud);
            temaLabXMLService.add(paramsAssig);
            gradeService.add(paramsGrade);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void testAddStudentAssigment() {
        int id = studentId;
        String professor = "Ionel`s professor";
        String email = "Ionel@email";
        String group = "5";
        String name = "Ionel";

        int idAssignment = asssignmentId;
        String description = "des";
        int deadlineWeek = 1;
        int weekToPressent = 2;

        String[] paramsAssig = {String.valueOf(idAssignment), description, String.valueOf(deadlineWeek), String.valueOf(weekToPressent)};

        String[] paramsStud = {String.valueOf(id), name, group, email, professor};

        try {
            studentXMLService.add(paramsStud);
            temaLabXMLService.add(paramsAssig);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void testAddGrade() {
        int id = gradeId;
        int value = 8;
        LocalDateTime time = LocalDateTime.now();

        String[] params = {String.valueOf(id), String.valueOf(studentId),
                String.valueOf(asssignmentId), String.valueOf(value), time.toString()};

        try {
            gradeService.add(params);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void testAddAssignment() {
        int id = asssignmentId;
        String description = "des";
        int deadlineWeek = 1;
        int weekToPressent = 2;

        String[] params = {String.valueOf(id), description, String.valueOf(deadlineWeek), String.valueOf(weekToPressent)};

        try {
            temaLabXMLService.add(params);
            assertTrue(true);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testAddStudent() {
        int id = studentId;
        String professor = "Ionel`s professor";
        String email = "Ionel@email";
        String group = "5";

        String name = "Ionel";

        String[] params = {String.valueOf(id), name, group, email, professor};

        try {
            studentXMLService.add(params);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }
}
