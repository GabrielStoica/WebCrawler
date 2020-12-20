import java.io.File;
import java.util.ArrayList;

public class List {
    public String dots = "..\\";

    private String folder_name;
    private String file_extension;

    public ArrayList<String> extension_files = new ArrayList<String>();

    public List(String folder_name, String file_extension) {
        this.folder_name = folder_name.toLowerCase();
        this.file_extension = file_extension.toLowerCase();
    }

    public int extension(String path, String suffix) {

        File file = new File(path);
        int subTotal = 0;

        //count self
        if (path.endsWith(suffix.toLowerCase())) {
            subTotal += 1;
        }

        //count children
        if (file.isDirectory()) {

            for (File subFile : file.listFiles()) {
                String copy = subFile.toString().toLowerCase();
                boolean isFound = copy.endsWith(suffix.toLowerCase());
                if (isFound) {
                    extension_files.add(subFile.getAbsolutePath());
                    extension_files.add("\n");
                }
                subTotal += this.extension(subFile.getAbsolutePath(), suffix.toLowerCase());
            }
        }
        return subTotal;
    }

    public ArrayList<String> getList() {
        return extension_files;
    }

    public String getDots() {
        return dots;
    }

    public void print() {
        List fc = new List(this.folder_name, this.file_extension);

        int total = fc.extension(this.folder_name, this.file_extension);

        if (!fc.getList().isEmpty()) {
            System.out.printf("There are %d files ending with %s \n", total, this.file_extension);
            System.out.println(fc.getList());
            Logger.getInstance().sendDataToLogger(1,"Search by file extension " + this.file_extension);
        }
    }
}
