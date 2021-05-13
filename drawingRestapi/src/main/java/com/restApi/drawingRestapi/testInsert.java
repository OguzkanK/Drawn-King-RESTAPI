package com.restApi.drawingRestapi;

import org.json.JSONArray;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import static java.lang.Class.forName;

@RestController
@CrossOrigin(origins = "http://localhost:5500")
public class testInsert { // This class is for testing on the localhost

    @RequestMapping(method = RequestMethod.POST, path = "/insert")

    public String insertMYSQL(@RequestBody String jsonInput){
        try{
            JSONArray dataArray = new JSONArray(jsonInput);
            String filepath = "D:/Apps/intellij Projects/drawingRestapi/images/"+ dataArray.getJSONObject(0).get("name") +".txt";
            File fileCheck = new File(filepath);
            String imgBase64 = (String) dataArray.getJSONObject(0).get("data");
            if(fileCheck.exists()) //Check if named image already exists
                return "duplicate";
            FileWriter file = new FileWriter(filepath); // Saves image locally as Base64
            file.write(imgBase64);
            file.flush();

            forName("com.mysql.jdbc.Driver"); // SQL connection
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/drawings", "root","");
            String query = "insert into `drawing-info` (id, name, score, link)" + " values (?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, (Integer) dataArray.getJSONObject(0).get("id"));
            preparedStmt.setString (2, (String) dataArray.getJSONObject(0).get("name"));
            preparedStmt.setInt   (3, (Integer) dataArray.getJSONObject(0).get("score"));
            preparedStmt.setString   (4, filepath);
            preparedStmt.execute();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "done";
    }
}

