package com.example.io_filesystem_example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.io_filesystem_example.databinding.ActivityMain2Binding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    private String text = "Not content";
    private String fileName = "test.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2);
        binding.setExternalText(text);

        // files from SD
        File sdcard = Environment.getExternalStorageDirectory();

        // Files from
        File[] externalStorageVolumes =
                ContextCompat.getExternalFilesDirs(getApplicationContext(), null);
        File primaryExternalStorage = externalStorageVolumes[0];

        binding.setListStrages(Arrays.stream(Objects.requireNonNull(sdcard.listFiles()))
                .map(File::getName).limit(5).collect(Collectors.joining("\n"))
        );

        binding.readExternal.setOnClickListener((v) -> {
            File file = new File(Paths.get(sdcard.getPath(), "Download").toFile(), fileName);

            if (file.exists()) {
                Toast.makeText(this, "File found", Toast.LENGTH_SHORT).show();
                StringBuilder stringBuilder = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    do {
                        line = br.readLine();
                        Log.d("TAG", line);
                        stringBuilder.append(line).append('\n');
                    } while (line != null);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    String contents = stringBuilder.toString();
                    binding.setExternalText(contents);
                }
            }

        });


    }


    // Checks if a volume containing external storage is available
    // for read and write.
    private boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    // Checks if a volume containing external storage is available to at least read.
    private boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }

}