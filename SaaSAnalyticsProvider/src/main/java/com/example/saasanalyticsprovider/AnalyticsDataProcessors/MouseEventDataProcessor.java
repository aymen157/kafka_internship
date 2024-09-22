package com.example.saasanalyticsprovider.AnalyticsDataProcessors;

import com.example.saasanalyticsprovider.Entities.MouseEventData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

@Component
public class MouseEventDataProcessor implements IDataProcessor<MouseEventData, Object> {

    @Value(value = "${spring.kafka.mouse-analytics-dump-folder}")
    String MouseDataDumpingFolder;
    @Autowired
    ObjectMapper objectMapper;
    @Value("${spring.kafka.mouse-analytics-dump-singlefile}")
    boolean singleFile = true;

    @Override
    public Object Process(MouseEventData data) {
        FileWriter writer = null; // Declare the writer outside the try block
        RandomAccessFile file = null;
        try {
            String serialized = objectMapper.writeValueAsString(data);
            //String jarPath = new File(MouseEventDataProcessor.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();

            if(!singleFile)
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd-HH-mm-SSS");
                String formattedDate = sdf.format(data.getDate());
                var filePath = Paths.get(MouseDataDumpingFolder, formattedDate + ".json"); // Add .txt or any file extension

                writer = new FileWriter(filePath.toAbsolutePath().toString());
                writer.write(serialized);
                System.out.println("Written to " + filePath.toAbsolutePath());
            }
            else
            {
                var filePath = Paths.get(MouseDataDumpingFolder, "user_mouse_data.json");
                var filePath_str = filePath.normalize().toAbsolutePath().toString();
                File f = new File(filePath_str);
                boolean exists = !f.createNewFile();
                file = new RandomAccessFile(filePath_str, "rw");
                if(exists)
                {
                    System.out.println("Appending to file: " + filePath_str);

                    long length = file.length();
                    // Move to the end of the file
                    file.seek(length);

                    // Read backwards until we find ']'
                    for (long i = length - 1; i >= 0; i--) {
                        file.seek(i);
                        int byteRead = file.read(); // Read one byte

                        // Check if it's the character we want
                        if (byteRead == ']') {
                            file.seek(i); // Move back to the position of ']'
                            // Add your string here
                            file.writeBytes("," + serialized + ']'); // Write your string
                            break; // Exit loop after writing
                        }
                    }
                }
                else
                {
                    System.out.println("Writing new file: " + filePath_str);
                    file.writeBytes("[" + serialized + "]");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("MouseEventDataProcessor failed: " + e.getMessage());
        } finally {
            // Ensure the writer is closed in the finally block
            if(file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    System.err.println("Failed to close the file: " + e.getMessage());
                }
            }
            if (writer != null) {
                try {
                    writer.close(); // Close the writer to flush the data
                } catch (IOException e) {
                    System.err.println("Failed to close the writer: " + e.getMessage());
                }
            }
        }
        return null;
    }

}
