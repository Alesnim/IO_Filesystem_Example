package com.example.io_filesystem_example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.io_filesystem_example.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        fileName = "example";
        binding.setFileText("Not content");
        fileListGet();
        binding.writeIternal.setOnClickListener((v) -> {
            String content = "Hello from file";
            File file = new File(this.getFilesDir(), fileName);
            try (FileOutputStream fos = this.openFileOutput(fileName, MODE_PRIVATE)) {
                fos.write(content.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (file.exists()) {
                Toast.makeText(this, "File create", Toast.LENGTH_SHORT).show();
                fileListGet();

            }
        });


        binding.readIternal.setOnClickListener((v) -> {

            try (FileInputStream fis = this.openFileInput(fileName)) {
                InputStreamReader inputStreamReader =
                        new InputStreamReader(fis, StandardCharsets.UTF_8);
                StringBuilder stringBuilder = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                    String line = reader.readLine();
                    while (line != null) {
                        stringBuilder.append(line).append('\n');
                        line = reader.readLine();
                    }
                } catch (IOException e) {
                    // Error occurred when opening raw file for reading.
                } finally {
                    String contents = stringBuilder.toString();
                    binding.setFileText(contents);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        binding.next.setOnClickListener((v) ->
        {
            startActivity(new Intent(this, MainActivity2.class));
        });
    }

    private void fileListGet() {
        String[] files = this.fileList();
        binding.setFileList(String.join("/n", files));
    }


}