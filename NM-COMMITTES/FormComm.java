package com.example.layoutpractise3;

import static androidx.core.content.FileProvider.getUriForFile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import com.example.layoutpractise3.BuildConfig;

import java.io.IOException;

import java.io.File;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class FormComm extends AppCompatActivity {
    private static final String NAME_REGEX = "^[a-zA-Z\\s]+$";
    private static final String COURSE_REGEX = "^[a-zA-Z\\s]+$";
    private static final String DIVISION_REGEX = "^[a-zA-Z\\s]{1}$";
    private static final String ROLL_NO_REGEX = "^[0-9]{2}$";
    private static final String CONTACT_NO_REGEX = "^[0-9]{10}$";
    private static final String GMAIL_REGEX ="^[a-zA-Z0-9._%+-]+@gmail.com+$"; //"^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    protected void shareExcelFile(File file)  {

        Uri fileUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Value = bundle.getInt("value");
        }
        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_SEND);
//        intent.setData(Uri.parse("mailto:"));
//        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "nirmalvivek24@gmail.com" });
        switch(Value) {
            case 11:
                intent.setAction(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "nirmalvivek24@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "FORM SUBMISSION TO JOIN COMPUTER SOCIETY COMMITTEE");
                break;
            case 12:
                intent.setAction(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "nirmalvivek24@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "FORM SUBMISSION TO JOIN TECHFEST");
                break;
            case 21:
                intent.setAction(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "nirmalvivek24@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "FORM SUBMISSION TO JOIN CULTURAL SOCIETY");
                break;
            case 31:
                intent.setAction(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "nirmalvivek24@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "FORM SUBMISSION TO JOIN CULTURAL SOCIETY");
                break;
            case 41:
                intent.setAction(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "nirmalvivek24@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "FORM SUBMISSION TO JOIN GYMKHANA");
                break;
            case 51:
                intent.setAction(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "nirmalvivek24@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "FORM SUBMISSION TO JOIN ARITHMOS");
                break;
            case 61:
                intent.setAction(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "nirmalvivek24@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "FORM SUBMISSION TO JOIN DLS");
                break;
        }
//


        //intent.putExtra(Intent.EXTRA_SUBJECT, "FORM SUBMISSION TO JOIN COMMITTEE");

        intent.putExtra(Intent.EXTRA_TEXT, "Please see attached Excel file for the form data.");
        intent.setType("application/vnd.ms-excel");
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    private File createExcelFile(Workbook workbook) throws IOException {
        // Save the workbook to the Downloads folder
        String fileName = "Form Data.xlsx";
        String dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        File file = new File(dirPath, fileName);

        // Check if the file already exists
        if (file.exists()) {
            file.delete();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);
        fileOutputStream.close();

        return file;
    }

    int Value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_comm);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Value = bundle.getInt("value");
        }

        final TextView title=findViewById(R.id.heading);
        Button btn=findViewById(R.id.button);

        switch(Value)
        {
            case 11:  title.setText("COMPUTER SOCIETY MEMBERSHIP FORM");

                break;
            case 12: title.setText("TECHFEST MEMBERSHIP FORM");
                break;
            case 21:  title.setText("CULTURAL SOCIETY MEMBERSHIP FORM");
                break;
            case 31: title.setText("CULTURAL SOCIETY MEMBERSHIP FORM");
                break;
            case 41:  title.setText("GYMKHANA MEMBERSHIP FORM");
                break;
            case 51:  title.setText("ARITHMOS MEMBERSHIP FORM");
                break;
            case 61:  title.setText("DLS MEMBERSHIP FORM");
                break;
        }

        //  final String To= "nirmalvivek24@gmail.com";
        btn.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("IntentReset")
            @Override
            public void onClick(View v) {
                final EditText name=findViewById(R.id.edit1);
                final EditText division=findViewById(R.id.edit2);
                final EditText rollNo=findViewById(R.id.edit3);
                final EditText course=findViewById(R.id.edit4);
                final EditText gmail=findViewById(R.id.edit5);
                final EditText contactNo=findViewById(R.id.edit8);
                final EditText skills=findViewById(R.id.edit6);
                final EditText reason=findViewById(R.id.edit7);

                // Create a new Excel workbook and sheet
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Form Data");

                String[] headers = { "Full Name", "Class Division", "Roll No", "Course", "Gmail", "Skills", "Reason to Join", "Contact Number" };

                // Create a new row for the headers and write them to the sheet
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }


                // Get the form data and write it to the sheet
//                name.getText().toString().trim();
//                division.getText().toString().trim();
//                rollNo.getText().toString().trim();
//                course.getText().toString().trim();
//
//                gmail.getText().toString().trim();
//                skills.getText().toString().trim();
//                reason.getText().toString().trim();
//                contactNo.getText().toString().trim();

                Row dataRow = sheet.createRow(1);
                dataRow.createCell(0).setCellValue(String.valueOf(name.getText().toString().trim()));
                dataRow.createCell(1).setCellValue(String.valueOf(division.getText().toString().trim()));
                dataRow.createCell(2).setCellValue(String.valueOf(rollNo.getText().toString().trim()));
                dataRow.createCell(3).setCellValue(String.valueOf(course.getText().toString().trim()));
                dataRow.createCell(4).setCellValue(String.valueOf(gmail.getText().toString().trim()));
                dataRow.createCell(5).setCellValue(String.valueOf(skills.getText().toString().trim()));
                dataRow.createCell(6).setCellValue(String.valueOf(reason.getText().toString().trim()));
                dataRow.createCell(7).setCellValue(String.valueOf(contactNo.getText().toString().trim()));

                if (!validateInput(name, NAME_REGEX)) {
                    name.setError("Invalid name. Please enter only letters.");
                    return;
                }

                if (!validateInput(course, COURSE_REGEX)) {
                    course.setError("Invalid course . Please enter only letters.");
                    return;
                }

                if (!validateInput(division, DIVISION_REGEX)) {
                    division.setError("Invalid DIVISION . Please enter only ONE letter.");
                    return;
                }

//                if (!validateInput(age, AGE_REGEX)) {
//                    ageEditText.setError("Invalid age. Please enter a 2-digit number.");
//                    return;
//                }

                if (!validateInput(rollNo, ROLL_NO_REGEX)) {
                    rollNo.setError("Invalid roll number. Please enter a 2-digit number.");
                    return;
                }

                if (!validateInput(contactNo, CONTACT_NO_REGEX)) {
                    contactNo.setError("Invalid contact number. Please enter only 10 numbers.");
                    return;
                }

                if (!validateInput(gmail, GMAIL_REGEX)) {
                    gmail.setError("Invalid Gmail. Please enter a valid Gmail.");
                    return;
                }

                try {
                    File file = createExcelFile(workbook);
                    shareExcelFile(file);

                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                finish();
            }
        });
    }
    private boolean validateInput(EditText input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input.getText());
        return matcher.matches();
    }
}
