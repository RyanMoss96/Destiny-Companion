
package destinycompanionjava;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;



/**
 * Destiny Companion
 * @author Ryan Moss
 * 
 */
public class DestinyCompanionJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       
        
        try{
          String memberID = getMemberId();
          System.out.println(memberID);
          getMemberDetails(memberID);
        }catch(IOException ioe){
            // Handle Exception
        }

        
    }
    
    private static String getMemberId() throws IOException {
        String sURL = "https://www.bungie.net/platform/destiny/2/Stats/GetMembershipIdByDisplayName/mossypne/";
        String memberID = httpConnect(sURL);
        return convertMemberID(memberID);
    }
    
    private static String convertMemberID(String jsonString) {
        JSONObject jsonObj = new JSONObject(jsonString);
        
        String memberID = jsonObj.get("Response").toString();
        return memberID;
    }
    
    private static void getMemberDetails(String memberID) throws IOException {
        String sURL = "https://www.bungie.net/platform/destiny/2/Account/";
        sURL = sURL + memberID + "/";
        String memberDetails = httpConnect(sURL);
        System.out.println(memberDetails);
        System.out.println(getCharacterDetails(memberDetails));
        
        
    }
    
    private static String getCharacterDetails(String jsonString) throws IOException
    {
        JSONObject jsonObj = new JSONObject(jsonString);
        FileWriter file = new FileWriter("/Users/ryanmoss/NetBeansProjects/destinyCompanionJava/file.txt");
        
        JSONObject response = (JSONObject) jsonObj.getJSONObject("Response");
        JSONObject data = (JSONObject) response.getJSONObject("data");
        JSONArray characters = (JSONArray) data.get("characters");
        
        System.out.println(characters.get(0));
        
        try {
           file.write(jsonObj.toString());
 
        } catch (IOException e) {
            e.printStackTrace();
 
        } finally {
            file.flush();
            file.close();
        }
        JSONObject charObj = jsonObj.getJSONObject("Response");
        
        String character = "c";
        return character;
    }
    
    private static String httpConnect(String sURL)throws IOException{
        String apiKey = "";
        
        URL url = new URL(sURL);
        
        HttpURLConnection con;
        con = (HttpURLConnection) url.openConnection();
        con.addRequestProperty("x-api-key", apiKey);
       
      
        con.connect();
        
        
        switch (con.getResponseCode()) {
            case 200:
            case 201:
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                
                return sb.toString();
             
        }
        return null;
    }
}
