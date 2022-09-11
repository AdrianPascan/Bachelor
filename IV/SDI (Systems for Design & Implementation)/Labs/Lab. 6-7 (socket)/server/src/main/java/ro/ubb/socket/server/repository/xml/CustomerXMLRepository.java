package ro.ubb.socket.server.repository.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.ubb.socket.common.domain.Customer;
import ro.ubb.socket.common.domain.validators.Validator;
import ro.ubb.socket.common.domain.validators.ValidatorException;
import ro.ubb.socket.server.repository.InMemoryRepositoryLongId;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.IntStream;

public class CustomerXMLRepository extends InMemoryRepositoryLongId<Customer> {
    private String xmlPath;

    public CustomerXMLRepository(Validator<Customer> validator, String xmlPath) {
        super(validator);
        this.xmlPath = xmlPath;

        readFromXML();
    }

    @Override
    public Optional<Customer> save(Customer entity) throws ValidatorException {
        Optional<Customer> optional = super.save(entity);
        writeToXML();
        return optional;
    }

    @Override
    public Optional<Customer> delete(Long id) {
        Optional<Customer> optional = super.delete(id);
        writeToXML();
        return optional;
    }

    @Override
    public Optional<Customer> update(Customer entity) throws ValidatorException {
        Optional<Customer> optional =  super.update(entity);
        writeToXML();
        return optional;
    }

    private void readFromXML() {
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(xmlPath);

            Element root = document.getDocumentElement();

            NodeList customerNodes = root.getChildNodes();

            IntStream.range(0, customerNodes.getLength())
                    .mapToObj(customerNodes::item)
                    .filter(node -> node instanceof Element)
                    .map(node -> getCustomerFromElement((Element) node))
                    .forEach(super::save);

            nextId.set(1 + getMaxId());
        } catch (SAXException | IOException | ParserConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    private void writeToXML() {
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(xmlPath);

            Element root = document.getDocumentElement();

            IntStream.range(0, root.getChildNodes().getLength())
                    .forEach(index -> root.removeChild(root.getLastChild()));

            entities.values().stream()
                    .map(customer -> (Node) getElementFromCustomer(customer, document))
                    .forEach(root::appendChild);

            Transformer transformer = TransformerFactory
                    .newInstance()
                    .newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new DOMSource(document),
                    new StreamResult(new File(xmlPath)));
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException exception) {
            exception.printStackTrace();
        }
    }

    private Customer getCustomerFromElement(Element element){
        Customer customer = new Customer();

        Long id = Long.valueOf((element.getAttribute("id")));
        customer.setId(id);

        customer.setFirstName(element.getElementsByTagName("firstName").item(0).getTextContent());
        customer.setLastName(element.getElementsByTagName("lastName").item(0).getTextContent());
        customer.setAge(Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent()));

        return customer;
    }

    private Element getElementFromCustomer(Customer customer, Document document){
        Element element = document.createElement("customer");

        element.setAttribute("id", customer.getId().toString());

        Element firstNameElement = document.createElement("firstName");
        firstNameElement.setTextContent(customer.getFirstName());
        element.appendChild(firstNameElement);

        Element lastNameElement = document.createElement("lastName");
        lastNameElement.setTextContent(customer.getLastName());
        element.appendChild(lastNameElement);

        Element ageElement = document.createElement("age");
        ageElement.setTextContent(Integer.toString(customer.getAge()));
        element.appendChild(ageElement);

        return element;
    }
}
