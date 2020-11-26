import java.io.*;

/**
 * Clasa care implementeaza crearea fisiereului sitemap.txt
 * Folderele din cadrul site-ului dat ca parametru de initializare,
 * sunt marcate cu "/" dupa terminarea denumirii acestora.
 * Fisierele din cadrul site-ului sunt spatiate corespunzator,
 * pentru a putea deduce path-ul acestora si au caracterul "-"
 * in dreptul numelui
 * Rezultatul executiei acestei Clase este stocat in fisierul sitemap.txt
 *
 * @author Stoica Gabriel
 */
public class GenerateSitemap {

    private String _rootDirectory;
    private String _siteName;
    private String _sitemap = "";

    /**
     * Constructorul clasei
     * Initializeaza directorul parinte in care vom
     * cauta folder-ul avand numele site-ului a caruit sitemap
     * se doreste a se afisa in fisierul sitemap.txt
     *
     * @param _rootDirectory Directorul parinte, de forma: "D:\Path"
     * @param _siteName Numele folderului a carui sitemap se doreste a se obtine, de forma "wiki.mta.ro"
     */
    public GenerateSitemap(String _rootDirectory, String _siteName) {
        this._rootDirectory = _rootDirectory;
        this._siteName = _siteName;
    }

    /**
     * Functia care scrie sitemap-ul in fisierul "sitemap.txt"
     *
     * @param sitemapString String-ul care va contine sitemap-ul
     * @throws IOException Exceptie returnata atunci cand nu se poate deschide fisierul "sitemap.txt"
     */
    private void createSitemapFile(String sitemapString) throws IOException {
        PrintWriter outputFile = new PrintWriter("sitemap.txt");

        outputFile.print(sitemapString);
        outputFile.close();
    }

    /**
     * Functia principala a Clasei
     * Realizeaza parcurgerea recursiva de tip Depth-First-Search
     * si verifica tipul obiectului din directorul parinte
     * Daca este fisier, concateneaza caracterul "-" in dreptul numelui sau
     * Daca este folder, concateneaza caracterul "/" la finalul numelui sau
     * Indenteaza fisierele/folderele in functie de adancimea, pentru a crea
     * structura sitemap-ului
     *
     * @param filesInDirectory Obiect de tip File[] care contine toate fisierel/folderele din directorul parinte
     * @param pathDepth Index responsabil cu parcurgerea folderelor/fisierelor
     * @param depth Index responsabil cu stocarea adancimii din structura arborescenta
     */
    private void recursivePathChecker(File[] filesInDirectory, int pathDepth, int depth) {

        if (pathDepth == filesInDirectory.length) {
            return;
        }

        if (!_sitemap.equals(""))
            this._sitemap += "\n";

        for (int i = 0; i < depth; i++) {
            this._sitemap += "\t";
        }

        if (filesInDirectory[pathDepth].isDirectory()) {
            this._sitemap += filesInDirectory[pathDepth].getName() + "/";

            recursivePathChecker(filesInDirectory[pathDepth].listFiles(), 0, depth + 1);
        } else if (filesInDirectory[pathDepth].isFile()) {
            this._sitemap += "- " + filesInDirectory[pathDepth].getName();

        }

        recursivePathChecker(filesInDirectory, ++pathDepth, depth);
    }

    /**
     * Functie care se va apela atunci cand se doreste
     * crearea Sitemap-ului
     * Valideaza tipul si existenta folderului parinte si
     * transmite lista continutului acestuia catre functia recursiva
     * dupa care realizeaza afiseara in fisier
     *
     * @throws IOException Exceptie retunata atunci cand folderul nu exista
     */
    public void generateSitemap() throws IOException {
        String mainPath = (this._rootDirectory + "/" + this._siteName);
        File mainPathDirectory = new File(mainPath);

        if (mainPathDirectory.exists() && mainPathDirectory.isDirectory()) {
            File[] filesInDirectory = mainPathDirectory.listFiles();
            recursivePathChecker(filesInDirectory, 0, 0);

            //System.out.println(this._sitemap);
            createSitemapFile(this._sitemap);

        } else {
            throw new FileNotFoundException("Folderul specificat nu exista!");
        }

    }

}
