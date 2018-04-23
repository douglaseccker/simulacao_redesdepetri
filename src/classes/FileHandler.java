package classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileHandler {
    private String contentFile;

    public FileHandler() {
        this.contentFile = "";
    }

    public String read(String path) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        String linha = "";
        String content = "";

        while (true) {
            if (linha != null) {
                content += linha + "\n";
            } else {
                break;
            }

            linha = buffRead.readLine();
        }

        buffRead.close();

        return content;
    }

    public void addLine(String line) {
        this.contentFile += line + "\n";
    }

    public void addLine(int line) {
        this.contentFile += line + "\n";
    }

    public void save(String path) throws IOException {
        if(path.indexOf(".txt") == -1) {
            path += ".txt";
        }

        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));

            writer.write(this.contentFile);
        } catch (IOException ex) {
            throw ex;
        } finally {
            writer.close();
            System.out.println("Arquivo salvo com sucesso!");
        }
    }
}
