package Java;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class weatherforcastapp {
    private static JFrame frame;
    private static JTextField locationField;
    private static JTextArea weatherdisplay;
    private static JButton fetchButton;
    private static String apikey ="0c870abf3dd6911a718557262f7c916f";//replace your own generated key from api
    private static String fetchweatherdata(String city){
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apikey);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
             BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             String response = "";
             String line ;
             while((line = reader.readLine()) != null){
                 response += line;
             }
             reader.close();
             JSONObject jsonObject = (JSONObject) JSONValue.parse(response.toString());
             JSONObject mainOBJ = (JSONObject) jsonObject.get("main");

             double temperature = (Double) mainOBJ.get("temp");
             long humidity = (Long) mainOBJ.get("humidity");

             //convert into celsius
            double temperatureCelsius = temperature - 273.15;

            //Retrieve weather description
            JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
            JSONObject weatherOBJ = (JSONObject) weatherArray.get(0);
            String description = (String) weatherOBJ.get("description");
            return "desccription: "+description+"\ntemperature:"+temperatureCelsius+"Celsius\nHumidity:"+humidity+"%";

        }catch (Exception e){
            return "Failed to fetch weather data. Please try again and check your City.";
        }

    }
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Weather Forecast Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new FlowLayout());
        JTextField locationfield = new JTextField(15);
        JButton fetchbutton = new JButton("Fetch Weather");
        JTextArea weatherDisplay = new JTextArea(10,30);
        weatherDisplay.setEditable(false);
        frame.add(new JLabel("Enter Location"));
        frame.add(locationfield);
        frame.add(fetchbutton);
        frame.add(weatherDisplay);
        fetchbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String location = locationfield.getText();
                String weatherinfo = fetchweatherdata(location);
                weatherDisplay.setText(weatherinfo);
            }
        });

        frame.setVisible(true);
    }

}
