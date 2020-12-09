import java.io.File;
import java.util.ArrayList;

public class Search {
    public String dots = "..\\";

    private String folder_name;
    private String keyword;

    public ArrayList<String> keyword_files = new ArrayList<String>();

    public Search(String folder_name, String keyword) {
        this.folder_name = folder_name;
        this.keyword = keyword;
    }

    public boolean verify(String path, String word){

        if(path.contains(word.toLowerCase()))
            return true;
        return false;
    }

    public int keyword(String path, String word){
        File file = new File(path);
        int subTotal = 0;


        //count self
        if(verify(path.toLowerCase(), word.toLowerCase())){
            subTotal += 1;
        }

        //count children
        if(file.isDirectory()) {

            for (File subFile : file.listFiles()) {
                String copy = subFile.toString().toLowerCase();
                boolean isFound = verify(copy, word);
                if (isFound) {
                    keyword_files.add(subFile.getAbsolutePath());
                    keyword_files.add("\n");
                }
                subTotal += this.keyword(subFile.getAbsolutePath(), word.toLowerCase());
            }
        }
        return subTotal;

    }

    public ArrayList<String> getList(){
        return keyword_files;
    }

    public String getDots() {
        return dots;
    }

    public void print(){
        Search fc = new Search(this.folder_name, this.keyword);

        int total = fc.keyword(this.folder_name,this.keyword);

        if(!fc.getList().isEmpty()) {
            System.out.printf("There are %d files that contain the keyword %s \n", total, this.keyword);
            System.out.println(fc.getList());
        }
    }
}
