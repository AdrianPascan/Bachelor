package ro.ubb.repository.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.ubb.domain.Book;
import ro.ubb.domain.Customer;
import ro.ubb.domain.validators.Validator;
import ro.ubb.domain.validators.ValidatorException;
import ro.ubb.repository.InMemoryRepository;
import ro.ubb.repository.InMemoryRepositoryLongId;

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

public class BookXMLRepository extends InMemoryRepositoryLongId<Book> {
    private String xmlPath;

    public BookXMLRepository(Validator<Book> validator, String xmlPath) {
        super(validator);
        this.xmlPath = xmlPath;

        readFromXML();
    }

    @Override
    public Optional<Book> save(Book entity) throws ValidatorException {
        Optional<Book> optional = super.save(entity);
        writeToXML();
        return optional;
    }

    @Override
    public Optional<Book> delete(Long id) {
        Optional<Book> optional = super.delete(id);
        writeToXML();
        return optional;
    }

    @Override
    public Optional<Book> update(Book entity) throws ValidatorException {
        Optional<Book> optional =  super.update(entity);
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
            NodeList bookNodes = root.getChildNodes();

            IntStream.range(0, bookNodes.getLength())
                    .mapToObj(bookNodes::item)
                    .filter(node -> node instanceof Element)
                    .map(node -> getBookFromElement((Element) node))
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
                    .map(book -> (Node) getElementFromBook(book, document))
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

    private Book getBookFromElement(Element element){
        Book book = new Book();

        Long id = Long.valueOf((element.getAttribute("id")));
        book.setId(id);

        book.setIsbn(element.getElementsByTagName("isbn").item(0).getTextContent());
        book.setTitle(element.getElementsByTagName("title").item(0).getTextContent());
        book.setAuthor(element.getElementsByTagName("author").item(0).getTextContent());
        book.setPublishingHouse(element.getElementsByTagName("publishingHouse").item(0).getTextContent());
        book.setYear(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));
        book.setPrice(Float.parseFloat(element.getElementsByTagName("price").item(0).getTextContent()));

        return book;
    }

    private Element getElementFromBook(Book book, Document document){
        Element element = document.createElement("book");

        element.setAttribute("id", book.getId().toString());

        Element isbnElement = document.createElement("isbn");
        isbnElement.setTextContent(book.getIsbn());
        element.appendChild(isbnElement);

        Element titleElement = document.createElement("title");
        titleElement.setTextContent(book.getTitle());
        element.appendChild(titleElement);

        Element authorElement = document.createElement("author");
        authorElement.setTextContent(book.getAuthor());
        element.appendChild(authorElement);

        Element publishingHouseElement = document.createElement("publishingHouse");
        publishingHouseElement.setTextContent(book.getPublishingHouse());
        element.appendChild(publishingHouseElement);

        Element yearElement = document.createElement("year");
        yearElement.setTextContent(Integer.toString(book.getYear()));
        element.appendChild(yearElement);

        Element priceElement = document.createElement("price");
        priceElement.setTextContent(Float.toString(book.getPrice()));
        element.appendChild(priceElement);

        return element;
    }
}
