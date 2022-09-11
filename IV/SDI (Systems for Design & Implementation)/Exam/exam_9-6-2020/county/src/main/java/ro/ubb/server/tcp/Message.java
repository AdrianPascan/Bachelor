package ro.ubb.server.tcp;

import lombok.*;

import java.io.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {
    public static final Integer SERVER_PORT = 1029;
    public static final String HOST = "localhost";

    private Integer port;
    private String header;
    private String body;

    public void writeTo(OutputStream ouputStream) throws IOException {
        ouputStream.write((port + System.lineSeparator() + header + System.lineSeparator() + body + System.lineSeparator()).getBytes());
    }

    public void readFrom(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        port = Integer.parseInt(bufferedReader.readLine());
        header = bufferedReader.readLine();
        body = bufferedReader.readLine();
    }
}
