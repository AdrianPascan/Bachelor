package ssvv.org.example;

import org.junit.Test;
import ssvv.org.example.Domain.Student;
import ssvv.org.example.Exceptions.ValidatorException;
import ssvv.org.example.Repository.MemoryRepository.StudentRepo;
import ssvv.org.example.Repository.TxtFileRepository.StudentFileRepo;
import ssvv.org.example.Repository.XMLFileRepository.NotaXMLRepo;
import ssvv.org.example.Repository.XMLFileRepository.StudentXMLRepo;
import ssvv.org.example.Repository.XMLFileRepository.TemaLabXMLRepo;
import ssvv.org.example.Service.TxtFileService.StudentService;
import ssvv.org.example.Service.XMLFileService.NotaXMLService;
import ssvv.org.example.Service.XMLFileService.StudentXMLService;
import ssvv.org.example.Service.XMLFileService.TemaLabXMLService;
import ssvv.org.example.Validator.NotaValidator;
import ssvv.org.example.Validator.StudentValidator;
import ssvv.org.example.Validator.TemaLabValidator;

import java.util.UUID;

import static org.junit.Assert.*;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    StudentValidator studentValidator = new StudentValidator();
    StudentXMLRepo studentXMLRepositoty = new StudentXMLRepo(studentValidator, "src/main/StudentiXML.xml");
    StudentXMLService studentXMLService = new StudentXMLService(studentXMLRepositoty);

    @Test
    public void testAddStudentGroupValid(){
        String id = UUID.randomUUID().toString();
        String name = "Ionel";
        String professor = "Ionel`s professor";
        String email = "Ionel@email";

        String group = "5";

        String[] params={id, name, group, email, professor};

        try {
            studentXMLService.add(params);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void testAddStudentGroupInvalid(){
        String id = UUID.randomUUID().toString();
        String name = "Ionel";
        String professor = "Ionel`s professor";
        String email = "Ionel@email";

        String group = "-5";

        String[] params={id, name, group, email, professor};

        try {
            studentXMLService.add(params);
            fail();
        } catch (ValidatorException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void testAddStudentNameValid(){
        String id = UUID.randomUUID().toString();
        String professor = "Ionel`s professor";
        String email = "Ionel@email";
        String group = "5";

        String name = "Ionel";

        String[] params={id, name, group, email, professor};

        try {
            studentXMLService.add(params);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void testAddStudentNameInvalid(){
        String id = UUID.randomUUID().toString();
        String professor = "Ionel`s professor";
        String email = "Ionel@email";
        String group = "5";

        String name = "";

        String[] params={id, name, group, email, professor};

        try {
            studentXMLService.add(params);
            fail();
        } catch (ValidatorException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void testAddStudentProfessorValid(){
        String id = UUID.randomUUID().toString();
        String email = "Ionel@email";
        String group = "5";
        String name = "Ionel";

        String professor = "Ionel`s professor";

        String[] params={id, name, group, email, professor};

        try {
            studentXMLService.add(params);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void testAddStudentProfessorInvalid(){
        String id = UUID.randomUUID().toString();
        String email = "Ionel@email";
        String group = "5";
        String name = "Ionel";

        String professor = "";

        String[] params={id, name, group, email, professor};

        try {
            studentXMLService.add(params);
            fail();
        } catch (ValidatorException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void testAddStudentIdValid(){
        String email = "Ionel@email";
        String group = "5";
        String name = "Ionel";
        String professor = "Ionel`s professor";

        String id = UUID.randomUUID().toString();

        String[] params={id, name, group, email, professor};

        try {
            studentXMLService.add(params);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void testAddStudentIdInvalid(){
        String email = "Ionel@email";
        String group = "5";
        String name = "Ionel";
        String professor = "Ionel`s professor";

        String id = "";

        String[] params={id, name, group, email, professor};

        try {
            studentXMLService.add(params);
            fail();
        } catch (ValidatorException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void testAddStudentBVAtest1() {
        String id = UUID.randomUUID().toString();
        String email = "Ionel@email";
        String name = "Ionel";
        String professor = "Ionel`s professor";

        String group = "-1";

        String[] params={id, name, group, email, professor};

        try {
            studentXMLService.add(params);
            fail();
        } catch (ValidatorException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void testAddStudentBVAtest2() {
        String id = UUID.randomUUID().toString();
        String email = "Ionel@email";
        String name = "Ionel";
        String professor = "Ionel`s professor";

        String group = "0";

        String[] params={id, name, group, email, professor};

        try {
            studentXMLService.add(params);
            fail();
        } catch (ValidatorException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void testAddStudentBVAtest3() {
        String id = UUID.randomUUID().toString();
        String email = "Ionel@email";
        String name = "Ionel";
        String professor = "Ionel`s professor";

        String group = "1";

        String[] params={id, name, group, email, professor};

        try {
            studentXMLService.add(params);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void testAddStudentBVAtest4() {
        String id = UUID.randomUUID().toString();
        String email = "Ionel@email";
        String name = "Ionel";
        String professor = "Ionel`s professor";

        String group = String.valueOf(Integer.MAX_VALUE - 1);

        String[] params={id, name, group, email, professor};

        try {
            studentXMLService.add(params);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void testAddStudentBVAtest5() {
        String id = UUID.randomUUID().toString();
        String email = "Ionel@email";
        String name = "Ionel";
        String professor = "Ionel`s professor";

        String group = String.valueOf(Integer.MAX_VALUE);

        String[] params={id, name, group, email, professor};

        try {
            studentXMLService.add(params);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }
}
