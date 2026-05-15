package com.example.nammavastra.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.properties.TextAlignment
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

object PdfExportUtility {

    fun exportEstimatePdf(
        context: Context,
        fabricType: String,
        materialCost: String,
        laborCost: String,
        transportCost: String,
        profitMargin: Int,
        gstAmount: Double,
        finalPrice: Double
    ) {
        val fileName = "NammaVastraEstimate_${System.currentTimeMillis()}.pdf"
        
        try {
            val outputStream: OutputStream? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                }
                val uri: Uri? = context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                uri?.let { context.contentResolver.openOutputStream(it) }
            } else {
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file = File(downloadsDir, fileName)
                FileOutputStream(file)
            }

            if (outputStream != null) {
                val writer = PdfWriter(outputStream)
                val pdf = PdfDocument(writer)
                val document = Document(pdf)

                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                val currentDate = sdf.format(Date())

                // Branding
                document.add(Paragraph("NAMMA-VASTRA").setBold().setFontSize(24f).setTextAlignment(TextAlignment.CENTER))
                document.add(Paragraph("Empowering Handloom Weavers Digitally").setItalic().setFontSize(10f).setTextAlignment(TextAlignment.CENTER))
                document.add(Paragraph("\n"))
                
                document.add(Paragraph("PRICE ESTIMATE REPORT").setBold().setFontSize(18f))
                document.add(Paragraph("Generated on: $currentDate"))
                document.add(Paragraph("------------------------------------------------------------------"))
                
                document.add(Paragraph("Fabric Type: $fabricType"))
                document.add(Paragraph("Material Cost: ₹$materialCost"))
                document.add(Paragraph("Labor Cost: ₹$laborCost"))
                document.add(Paragraph("Transport Cost: ₹$transportCost"))
                document.add(Paragraph("Profit Margin: $profitMargin%"))
                
                if (gstAmount > 0) {
                    document.add(Paragraph("GST (5%): ₹%.2f".format(gstAmount)))
                }
                
                document.add(Paragraph("------------------------------------------------------------------"))
                document.add(Paragraph("SUGGESTED RETAIL PRICE: ₹%.2f".format(finalPrice)).setBold().setFontSize(16f))
                
                document.add(Paragraph("\n\n"))
                document.add(Paragraph("Thank you for choosing Namma-Vastra.").setFontSize(10f).setTextAlignment(TextAlignment.CENTER))

                document.close()
                Toast.makeText(context, "PDF Saved Successfully in Downloads", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "PDF Export Failed: Could not open output stream", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "PDF Export Failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
}
