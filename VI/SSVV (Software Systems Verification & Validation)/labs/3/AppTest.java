package ssvv.org.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ssvv.org.example.Exceptions.ValidatorException;
import ssvv.org.example.Repository.XMLFileRepository.StudentXMLRepo;
import ssvv.org.example.Repository.XMLFileRepository.TemaLabXMLRepo;
import ssvv.org.example.Service.XMLFileService.StudentXMLService;
import ssvv.org.example.Service.XMLFileService.TemaLabXMLService;
import ssvv.org.example.Validator.StudentValidator;
import ssvv.org.example.Validator.TemaLabValidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.UUID;

import static org.junit.Assert.*;


/**
 * Unit test for simple App.
 */
public class AppTest {
    StudentValidator studentValidator = new StudentValidator();
    StudentXMLRepo studentXMLRepositoty = new StudentXMLRepo(studentValidator, "src/main/StudentiXML.xml");
    StudentXMLService studentXMLService = new StudentXMLService(studentXMLRepositoty);

    TemaLabValidator assignmentValidator = new TemaLabValidator();
    TemaLabXMLRepo temaLabXMLRepo = new TemaLabXMLRepo(assignmentValidator, "src/main/TemaLaboratorXML.xml");
    TemaLabXMLService temaLabXMLService = new TemaLabXMLService(temaLabXMLRepo);

    @Test
    public void testAddAssignment1() {
        String id = "";
        String description = "des";
        int deadlineWeek = 0;
        int weekToPressent = 0;

        String[] params = {id, description, String.valueOf(deadlineWeek), String.valueOf(weekToPressent)};

        try {
            temaLabXMLService.add(params);
            fail();
        } catch (ValidatorException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            assertTrue(e.getClass().getName().equalsIgnoreCase(NumberFormatException.class.getName()));
            assertEquals("For input string: \"\"", e.getMessage());
        }
    }

    @Test
    public void testAddAssignment2() {
        int id = 0;
        String description = "des";
        int deadlineWeek = 1;
        int weekToPressent = 2;

        String[] params = {String.valueOf(id), description, String.valueOf(deadlineWeek), String.valueOf(weekToPressent)};

        try {
            temaLabXMLService.add(params);
        } catch (ValidatorException e) {
            e.printStackTrace();
        }

        File file = new File("src/main/TemaLaboratorXML.xml");

        if (file.length() != 0) {
            // Delete the content
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(file.getPath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (pw != null) {
                pw.close();
            }

            // Pass the test
            assertTrue(true);
        } else {
            fail();
        }
    }

    @Test
    public void testAddAssignment3() {
        int id = 0;
        String description = "";
        int deadlineWeek = 0;
        int weekToPressent = 0;

        String[] params = {String.valueOf(id), description, String.valueOf(deadlineWeek), String.valueOf(weekToPressent)};

        try {
            temaLabXMLService.add(params);
            fail();
        } catch (ValidatorException e) {
            e.printStackTrace();

            assertTrue(e.getClass().getName().equalsIgnoreCase(ValidatorException.class.getName()));
            assertEquals("Descriere tema invalida\n", e.getMessage());
        }
    }

    @Test
    public void testAddAssignment4() {
        int id = 0;
        String description = "des";
        int deadlineWeek = 0;
        int weekToPressent = 0;

        String[] params = {String.valueOf(id), description, String.valueOf(deadlineWeek), String.valueOf(weekToPressent)};

        try {
            temaLabXMLService.add(params);
            fail();
        } catch (ValidatorException e) {
            e.printStackTrace();

            assertTrue(e.getClass().getName().equalsIgnoreCase(ValidatorException.class.getName()));
            assertEquals("Sapatamana predarii invalida\n", e.getMessage());
        }
    }

    @Test
    public void testAddAssignment5() {
        int id = 0;
        String description = "";
        int deadlineWeek = 1;
        int weekToPressent = 0;

        String[] params = {String.valueOf(id), description, String.valueOf(deadlineWeek), String.valueOf(weekToPressent)};

        try {
            temaLabXMLService.add(params);
            fail();
        } catch (ValidatorException e) {
            e.printStackTrace();

            assertTrue(e.getClass().getName().equalsIgnoreCase(ValidatorException.class.getName()));
            assertEquals("Descriere tema invalida\n", e.getMessage());
        }
    }

    @Test
    public void testAddStudentGroupValid() {
        String id = UUID.randomUUID().toString();
        String name = "Ionel";
        String professor = "Ionel`s professor";
        String email = "Ionel@email";

        String group = "5";

        String[] params = {id, name, group, email, professor};

        try {
            studentXMLService.add(params);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void testAddStudentGroupInvalid() {
        String id = UUID.randomUUID().toString();
        String name = "Ionel";
        String professor = "Ionel`s professor";
        String email = "Ionel@email";

        String group = "-5";

        String[] params = {id, name, group, email, professor};

        try {
            studentXMLService.add(params);
            fail();
        } catch (ValidatorException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void testAddStudentNameValid() {
        String id = UUID.randomUUID().toString();
        String professor = "Ionel`s professor";
        String email = "Ionel@email";
        String group = "5";

        String name = "Ionel";

        String[] params = {id, name, group, email, professor};

        try {
            studentXMLService.add(params);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void testAddStudentNameInvalid() {
        String id = UUID.randomUUID().toString();
        String professor = "Ionel`s professor";
        String email = "Ionel@email";
        String group = "5";

        String name = "";

        String[] params = {id, name, group, email, professor};

        try {
            studentXMLService.add(params);
            fail();
        } catch (ValidatorException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void testAddStudentProfessorValid() {
        String id = UUID.randomUUID().toString();
        String email = "Ionel@email";
        String group = "5";
        String name = "Ionel";

        String professor = "Ionel`s professor";

        String[] params = {id, name, group, email, professor};

        try {
            studentXMLService.add(params);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void testAddStudentProfessorInvalid() {
        String id = UUID.randomUUID().toString();
        String email = "Ionel@email";
        String group = "5";
        String name = "Ionel";

        String professor = "";

        String[] params = {id, name, group, email, professor};

        try {
            studentXMLService.add(params);
            fail();
        } catch (ValidatorException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void testAddStudentIdValid() {
        String email = "Ionel@email";
        String group = "5";
        String name = "Ionel";
        String professor = "Ionel`s professor";

        String id = UUID.randomUUID().toString();

        String[] params = {id, name, group, email, professor};

        try {
            studentXMLService.add(params);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void testAddStudentIdInvalid() {
        String email = "Ionel@email";
        String group = "5";
        String name = "Ionel";
        String professor = "Ionel`s professor";

        String id = "";

        String[] params = {id, name, group, email, professor};

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

        String[] params = {id, name, group, email, professor};

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

        String[] params = {id, name, group, email, professor};

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

        String[] params = {id, name, group, email, professor};

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

        String[] params = {id, name, group, email, professor};

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

        String[] params = {id, name, group, email, professor};

        try {
            studentXMLService.add(params);
        } catch (ValidatorException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }
}
