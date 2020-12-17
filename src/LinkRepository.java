import java.util.ArrayList;

public class LinkRepository {
    private static ArrayList<String> UniqueURLs=null;

    public static void AddLink(String URl)
    {
        if(UniqueURLs==null)
        {
            UniqueURLs=new ArrayList<String>();
        }
        UniqueURLs.add(URl);
    }

    public static boolean Verify(String URL)
    {
        if(UniqueURLs==null)
            return false;
        for(String line: UniqueURLs)
        {
            if(line.compareToIgnoreCase(URL) == 0)
                return true;
        }
        return false;
    }

}
