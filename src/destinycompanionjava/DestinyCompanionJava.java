/*
*Destiny Companion by Ryan Moss 27/09/15
*Created to gain experience in HTTP requests and JSON
*Read the Read Me for details on work still to do.
*/
package destinycompanionjava;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;



/**
 *
 * @author ryanmoss
 */
public class DestinyCompanionJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       
        
        try{
          String memberID = getMemberId();
          getMemberDetails(memberID);
        }catch(IOException ioe){
            // Handle Exception
        }

        
    }
    
    public static String getMemberId() throws IOException {
        String sURL = "https://www.bungie.net/platform/destiny/2/Stats/GetMembershipIdByDisplayName/mossypne/";
        String apiKey = "";
        
        URL u = new URL(sURL);
        HttpURLConnection c;
        
        c = (HttpURLConnection) u.openConnection();
        
        
        c.addRequestProperty("x-api-key", apiKey);
       
      
        c.connect();
        
        
        switch (c.getResponseCode()) {
            case 200:
            case 201:
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                
                return convertMemberID(sb.toString());
                
                
                
                
        }
        return null;
        
    }
    
    private static String convertMemberID(String jsonString) {
        JSONObject jsonObj = new JSONObject(jsonString);
        System.out.println(jsonObj.get("Response"));
        
        String memberID = jsonObj.get("Response").toString();
        return memberID;
    }
    
    private static void getMemberDetails(String memberID) throws IOException {
        String sURL = "https://www.bungie.net/platform/destiny/2/Account/";
        String apiKey = "";
        
        sURL = sURL + memberID + "/";
        
        System.out.println(sURL);
        
        URL u = new URL(sURL);
        HttpURLConnection c;
        
        c = (HttpURLConnection) u.openConnection();
        
        
        c.addRequestProperty("x-api-key", apiKey);
       
      
        c.connect();
        
        
        switch (c.getResponseCode()) {
            case 200:
            case 201:
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                
                System.out.println(sb.toString());
                
                
                
                
        }
        
    }
}
