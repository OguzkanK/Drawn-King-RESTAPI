package com.restApi.drawingRestapi;

import java.io.File;
import java.sql.*;

import java.io.FileWriter;
import java.util.Scanner;

import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.*;

import static java.lang.Class.forName;

@RestController
@CrossOrigin(origins = "http://localhost:5500")
public class dbTest { // This class is for testing on the localhost

    @RequestMapping(method = RequestMethod.GET, path = "/test")
    public String pullData(@RequestParam(required = false) String additionalQuery){
        if(additionalQuery == null)
            additionalQuery = "";
        String filepath, data;
        JSONObject drawingDetails;
        JSONObject drawingEntry;
        JSONArray entryList = new JSONArray();

        try{
            forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/drawings", "root","");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `drawing-info`" + additionalQuery);
            while(rs.next()) {
                data = "";
                drawingDetails = new JSONObject();
                drawingEntry = new JSONObject();
                filepath = rs.getString(4);
                File imgFile = new File(filepath);
                if(imgFile.exists()) {
                    Scanner dataReader = new Scanner(imgFile);
                    while(dataReader.hasNextLine()){
                        data += dataReader.nextLine();
                    }
                    dataReader.close();
                    drawingDetails.put("id", rs.getInt(1));
                    drawingDetails.put("name", rs.getString(2));
                    drawingDetails.put("score", rs.getInt(3));
                    drawingDetails.put("data", data);
                    drawingEntry.put("drawing", drawingDetails);
                    entryList.put(drawingEntry);
                }
            }
            con.close();

            FileWriter file = new FileWriter("D:/Apps/intellij Projects/drawingRestapi/images/output.txt");
            file.write(entryList.toString());
            file.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
        return entryList.toString();
    }
}
