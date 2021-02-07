/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nasef Abd El-Hamid
 */
@WebServlet(urlPatterns = {"/operationsServlet"})
public class operationsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            
            //varriables
            Crawler craw = new Crawler();
            Scraber scrabe = new Scraber();
            ArrayList<String> links = new ArrayList <>();
            ArrayList<String> oldLinks = new ArrayList <>();
            //end
            
            //getting old links from DB( currently Files)
            oldLinks = craw.loadLinkFromText("D:\\Abdonasef Work 2\\FCI-CU\\Java projects\\News Summerizer\\links\\Full Links.txt");
            //end
            
            //Crawling
            links = craw.getlinks("https://www.skynewsarabia.com");
            //end
            
            // Scarping
            for(int i = 0; i < links.size(); i++){
                if(!oldLinks.contains(links.get(i))){
                    scrabe.doScrabeSkyNews(links.get(i));
                }
            }
            //end
            
            //text summerize
            SummaryTool summary = new SummaryTool();
            ArrayList<String> filesnames= new ArrayList<>();
            ArrayList<String> summerizedfilesnames= new ArrayList<>();
            File file = null;
            
            filesnames = summary.getFileNames("D:\\Abdonasef Work 2\\FCI-CU\\Java projects\\News Summerizer\\scrabedNews");
            summerizedfilesnames = summary.getFileNames("D:\\Abdonasef Work 2\\FCI-CU\\Java projects\\News Summerizer\\Summery");
            
            
            for(int i = 0; i < filesnames.size(); i++){
                file = new File(("D:\\Abdonasef Work 2\\FCI-CU\\Java projects\\News Summerizer\\scrabedNews\\" + filesnames.get(i)));
                
                if((file.length() != 0) && (!summerizedfilesnames.contains(filesnames.get(i)))){
                    summary.init(filesnames.get(i));
                    summary.extractSentenceFromContext();
                    summary.groupSentencesIntoParagraphs();
                    summary.createIntersectionMatrix();
                    summary.createDictionary();
                    summary.createSummary();
                    summary.printSummary();
                    summary.finish();
                }else if(file.length() == 0){
                    out.print(filesnames.get(i) + "<br>");
                }
            }
            //end
            
            
            
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(operationsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(operationsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
