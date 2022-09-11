package ro.ubb.socket.server.repository.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.ubb.socket.common.domain.Transaction;
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

public class TransactionXMLRepository extends InMemoryRepositoryLongId<Transaction> {
    private String xmlPath;

    public TransactionXMLRepository(Validator<Transaction> validator, String xmlPath) {
        super(validator);
        this.xmlPath = xmlPath;

        readFromXML();
    }

    @Override
    public Optional<Transaction> save(Transaction entity) throws ValidatorException {
        Optional<Transaction> optional = super.save(entity);
        writeToXML();
        return optional;
    }

    @Override
    public Optional<Transaction> delete(Long id) {
        Optional<Transaction> optional = super.delete(id);
        writeToXML();
        return optional;
    }

    @Override
    public Optional<Transaction> update(Transaction entity) throws ValidatorException {
        Optional<Transaction> optional =  super.update(entity);
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

            NodeList transactionNodes = root.getChildNodes();

            IntStream.range(0, transactionNodes.getLength())
                    .mapToObj(transactionNodes::item)
                    .filter(node -> node instanceof Element)
                    .map(node -> getTransactionFromElement((Element) node))
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
                    .map(transaction -> (Node) getElementFromTransaction(transaction, document))
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

    private Transaction getTransactionFromElement(Element element){
        Transaction transaction = new Transaction();

        Long id = Long.valueOf((element.getAttribute("id")));
        transaction.setId(id);

        transaction.setCustomerId(Long.valueOf(element.getElementsByTagName("customerId").item(0).getTextContent()));
        transaction.setBookId(Long.valueOf(element.getElementsByTagName("bookId").item(0).getTextContent()));

        return transaction;
    }

    private Element getElementFromTransaction(Transaction transaction, Document document){
        Element element = document.createElement("transaction");

        element.setAttribute("id", transaction.getId().toString());

        Element customerIdElement = document.createElement("customerId");
        customerIdElement.setTextContent(transaction.getCustomerId().toString());
        element.appendChild(customerIdElement);

        Element bookIdElement = document.createElement("bookId");
        bookIdElement.setTextContent(transaction.getBookId().toString());
        element.appendChild(bookIdElement);

        return element;
    }
}