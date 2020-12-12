import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Clasa ce contine metoda statica care limiteaza descarcarea unei resurse
 * din Internet in functie de dimensiunea acesteia
 * @author VertUnix
 */
public class SizeLimiter {

    /**
     * Constructorul clasi
     */
    public SizeLimiter() {
    }

    /**
     * Functia verifica daca resursa este in limita de dimensiune acceptata
     * @param ByteSizeLimit limita in octeti pt a descarca resursa
     * @param url adresa URL a resursei a carei dimensiune se verifica
     * @param verbose boolean ce stabileste daca functia va afisa sau nu detalii
     *                despre dimensiunea descarcarilor
     * @return boolean ce semnifica daca resursa este in limita de dimensiune acceptata
     */
    public static boolean isWithinSizeLimit(long ByteSizeLimit, String url, boolean verbose) {

        HttpURLConnection content;
        try {

            content = (HttpURLConnection) new URL(url).openConnection();
            //
            long contentLength = content.getContentLengthLong();

            if(verbose) {

                String printedContentLength;
                if (contentLength == -1) //daca site-ul nu ofera informatii despre content length in header-ul HTTP
                    printedContentLength = "n/a ";
                else
                    printedContentLength = String.format("%.2f", (double) contentLength / (1024 * 1024));

                System.out.println(printedContentLength + "MB " + url);
            }

            //codul log-ului (INFO/ WARN/ ERR)
            int logMessageCode;
            //mesajul de scris in jurnal
            String logMessage;

            if (contentLength == -1) {

                logMessageCode = 2;
                logMessage = "This site doesn't specify its content length. Downloading anyway...";
                Logger.getInstance().sendDataToLogger(logMessageCode, logMessage);
                return true;
            } else if(contentLength <= ByteSizeLimit) {

                return true;
            } else {

                logMessageCode = 2;
                logMessage = "Web page/ resource larger than the specified size. " + contentLength + "/ " +
                        + ByteSizeLimit + "Bytes limit. " + "Ignoring download.";

                Logger.getInstance().sendDataToLogger(logMessageCode, logMessage);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }




}
