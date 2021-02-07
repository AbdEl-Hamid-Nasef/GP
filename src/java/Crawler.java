import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Crawler {
    public String linkstxtpath = "D:\\Abdonasef Work 2\\FCI-CU\\Java projects\\News Summerizer\\links\\Full Links.txt";
    
    //////////////////////////////////////////////////////////////////////////////////////////////////
    public  ArrayList<String> getlinks (String link) throws Exception{
         ArrayList<String> fullLinks = new ArrayList<>();
         ArrayList<String> cleaned = new ArrayList<>();
         try {
            Document doc =  Jsoup.connect(link).get();
            org.jsoup.select.Elements links = doc.select("a");
            links.forEach((e) -> {
                fullLinks.add(e.attr("abs:href"));
             });  
            
            cleaned = cleanLinks(fullLinks);
            saveLinkOnText(cleaned);
            
	}catch (IOException ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
	}
         
        
         return cleaned;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    public void saveLinkOnText(ArrayList<String> links) throws Exception{
        ArrayList<String> oldLinks = loadLinkFromText(linkstxtpath);
        try (
            OutputStream os = new FileOutputStream(linkstxtpath, true)) {
            OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osw);
            String line = "";
            
            for (int i = 0; i < links.size(); i++){
                if(!oldLinks.contains(links.get(i))){
                    
                    bw.write(links.get(i) + "\n");
                    
                }
            }
            bw.flush();
            bw.close();
            osw.close();
            os.flush();
            os.close();
        }

        
        
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<String> loadLinkFromText(String txtpath) throws Exception{
        ArrayList<String> links = new ArrayList<>();
        try (
            InputStream is = new FileInputStream(txtpath)) {
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            
            while((line = br.readLine()) != null){
                links.add(line);
            }
            is.close();
            br.close();
            isr.close();
        }
    return links;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<String> cleanLinks(ArrayList<String> links){
        ArrayList<String> cleanedLinks = new ArrayList<>();
        int counter = 0;
        for(int i = 0; i < links.size(); i++){
                counter = 0;
                if((links.get(i).contains("skynewsarabia.com")) && (!links.get(i).contains("@")) && 
                        (!links.get(i).contains("program")) && (!links.get(i).contains("video")) && (!links.get(i).contains("topic"))
                        && (!links.get(i).contains("image_gallery")) && (!links.get(i).contains("follow-us"))
                        && (!links.get(i).contains("staticpage")) && (!links.get(i).contains("infographic"))
                        && (!links.get(i).contains("live-story")) && (!links.get(i).contains("protection"))
                        && (!links.get(i).contains("email")) && (!links.get(i).contains("blog"))
                        ){
                    for(int j = 0; j < links.get(i).length(); j++){
                        if((links.get(i).charAt(j) == '/') && (!links.get(i).equals("https://www.skynewsarabia.com/sport/tennis")) 
                                &&(!links.get(i).equals("https://www.skynewsarabia.com/sport/football")) 
                                &&(!links.get(i).equals("https://www.skynewsarabia.com/sport/varieties")) 
                                &&(!links.get(i).equals("https://www.skynewsarabia.com/sport/basketball")) 
                                &&(!links.get(i).equals("https://www.skynewsarabia.com/sport/middle-east")) 
                                &&(!links.get(i).equals("https://www.skynewsarabia.com/sport/business")) 
                                && (!links.get(i).equals("https://www.skynewsarabia.com/sport/motorsport"))
                                && (!links.get(i).equals("https://www.skynewsarabia.com/sport/world"))
                                && (!links.get(i).equals("https://www.skynewsarabia.com/sport/technology"))
                                
                                ){
                            counter++;
                        }
                    }
                    if(counter > 3){
                        cleanedLinks.add(links.get(i));
                    }
                }
                
            }
        return cleanedLinks;
    }
}
