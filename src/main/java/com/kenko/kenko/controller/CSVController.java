package com.kenko.kenko.controller;
import com.kenko.kenko.payload.response.MessageResponse;
import com.opencsv.CSVWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.util.Collections;

@RestController
public class CSVController {

    @PostMapping("/addDisease")
    public ResponseEntity addDisease(@RequestBody String[] disease) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("D:/project/df.csv", true), ',',
                    CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            String[] data = {"Cancer", "headache", "nausea", "vomiting", "cramps", "high_fever"};
            Iterable<String[]> lines = Collections.singleton(disease);
            writer.writeAll(lines);
            writer.close();
            return ResponseEntity.ok(new MessageResponse("Disease added successfully"));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error while adding disease with message :- "
                    + e.getMessage()));
        }
    }
}
