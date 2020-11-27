package com.example.pdfexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Jpeg;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfBody;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "PdfCreatorActivity";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private File pdfFile;
    ImageView imgdownload;
    Bitmap bmp;
//    ConnectionClass connectionClass;
//    ArrayList<GiftitemPOJO> MyList1;
    //    GiftitemPOJO giftitemPOJO;
    Context context;
//    GiftitemPOJO name;
//    GiftitemPOJO price;
//    GiftitemPOJO url;
//    GiftitemPOJO type;
//    GiftitemPOJO date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgdownload = findViewById(R.id.downloadpdf);
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.images);
        context = this;
//        giftitemPOJO = new GiftitemPOJO();
//        connectionClass = new ConnectionClass();
//        DoLOgin doLOgin=new DoLOgin();
//        doLOgin.execute();
        imgdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createPdfWrapper();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void createPdfWrapper() throws FileNotFoundException, DocumentException {
        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdf();
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private void createPdf() throws FileNotFoundException, DocumentException {
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        String pdfname = "GiftItem.pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{3, 3, 3, 3, 3});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell("Name");
        table.addCell("Price");
        table.addCell("Type");
        table.addCell("URL");
        table.addCell("Date");
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
//        for (int i = 0; i < MyList1.size(); i++) {
//            name = MyList1.get(i);
//            type = MyList1.get(i);
//            date = MyList1.get(i);
//            url = MyList1.get(i);
//            price = MyList1.get(i);
            String namen = "vaishali";
            String pricen = "67";
            String daten = "restdkydtdkyfyf,gvyhyjgy,gu,yj,fytdtrs";
            String typen = "try";
            String urln = "abc";
            table.addCell(String.valueOf(namen));
            table.addCell(String.valueOf(pricen));
            table.addCell(String.valueOf(typen));
            table.addCell(String.valueOf(urln));
            table.addCell(String.valueOf(daten.substring(0, 10)));
//        }
//        System.out.println("Done");
        PdfWriter.getInstance(document, output);
        document.open();
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.UNDERLINE, BaseColor.BLUE);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.BLUE);
        document.add(new Paragraph("Pdf Data \n\n", f));
        document.add(new Paragraph("Pdf File Through Itext", g));
        //document.add(new Jpeg(R.drawable.images));
        try {
//            InputStream ims = getAssets().open("images.png");
//            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.scalePercent(30);
            image.setAlignment(Element.ALIGN_LEFT);
            document.add(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.add(table);
//        for (int i = 0; i < MyList1.size(); i++) {
//            document.add(new Paragraph(String.valueOf(MyList1.get(i))));
//        }
        document.close();
//        Log.e("safiya", MyList1.toString());
        previewPdf();
    }
    private void previewPdf() {
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            //Uri uri = Uri.fromFile(pdfFile);
            Uri uri = FileProvider.getUriForFile(context, "com.example.pdfexample.fileprovider", pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }
    }
//    class DoLOgin extends AsyncTask {
//        @Override
//        protected Object doInBackground(Object[] objects) {
//            try {
//                Connection con = connectionClass.CONN();
//                if (con == null) {
//                } else {
//                    String query = "select * from youtable";
//                    Statement statement = con.createStatement();
//                    ResultSet rs = statement.executeQuery(query);
//                    MyList1 = new ArrayList<GiftitemPOJO>();
//                    while (rs.next()) {
//                        giftitemPOJO.setItem_name(rs.getString("item_name"));
//                        giftitemPOJO.setItem_price(rs.getString("item_price"));
//                        giftitemPOJO.setItem_URL(rs.getString("Item_URL"));
//                        giftitemPOJO.setItem_type_code(rs.getString("Item_type_code"));
//                        giftitemPOJO.setCreatedAt(rs.getString("CreatedAt"));
//                        MyList1.add(giftitemPOJO);
//                        giftitemPOJO = new GiftitemPOJO();
//                    }
//                }
//            } catch (Exception e) {
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Object o) {
//            super.onPostExecute(o);
//        }
//
//    }
}