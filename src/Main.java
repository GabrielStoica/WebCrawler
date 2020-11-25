import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Main {
    public static void main(String[] args){

        System.out.println("Test citire cod sursa HTML al unui site");

        Document htmlDocument = null;
        try {
            htmlDocument = Jsoup.connect("https://mta.ro").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(htmlDocument);
    }
}
