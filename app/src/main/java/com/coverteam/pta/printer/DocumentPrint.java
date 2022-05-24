package com.coverteam.pta.printer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.print.PDFPrint;

import com.tejpratapsingh.pdfcreator.utils.FileManager;
import com.tejpratapsingh.pdfcreator.utils.PDFUtil;

import java.io.File;

public class DocumentPrint {


    private  String template (){
        String template = "";

        template ="<p style=\"text-align: left;\"><span style=\"font-size: 11px;\">Bandar Lampung, &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2020,</span><br><span style=\"font-size: 11px;\">Kepada:</span><br><span style=\"font-size: 11px;\">Yth. Ketua PTA Bandar Lampung</span><br><span style=\"font-size: 11px;\">Di tempat</span></p>\n" +
                "<p style=\"text-align: center;\"><strong><span style=\"font-size: 11px;\">FOMULIR PERMINTAAN DAN PEMEBERIAN CUTI</span></strong><br><span style=\"font-size: 11px;\">Nomor :</span></p>\n" +
                "<table style=\"width: 100%; border-collapse: collapse; border: 1px solid rgb(0, 0, 0);\">\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <td colspan=\"4\" style=\"width: 100%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">I. DATA PEGAWAI</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 24.9833%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Nama</span></td>\n" +
                "            <td style=\"width: 24.9666%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td style=\"width: 24.9666%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">NIP</span></td>\n" +
                "            <td style=\"width: 25.0167%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 24.9833%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Jabatan</span></td>\n" +
                "            <td style=\"width: 24.9666%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td style=\"width: 24.9666%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Masa Kerja</span></td>\n" +
                "            <td style=\"width: 25.0167%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 24.9833%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Unit Kerja</span></td>\n" +
                "            <td colspan=\"3\" style=\"width: 74.8999%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"4\" style=\"width: 99.8665%; border: 0px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">II. JENIS CUTI YANG DIAMBIL **</span></td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "<table style=\"width: 100%; border-collapse: collapse; border: 1px solid rgb(0, 0, 0);\">\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">1. Cuti Tahuan</span></td>\n" +
                "            <td style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">2. Cuti Besar</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">3. Cuti Sakit</span></td>\n" +
                "            <td style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">4. Cuti Melahirkan</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td rowspan=\"3\" style=\"width: 49.8498%; vertical-align: top; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">5. Cuti Alasan Penting</span></td>\n" +
                "            <td style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">6. Cuti di Luar Tanggungan Negara</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "<table style=\"width: 100%; border-collapse: collapse; border: 1px solid rgb(0, 0, 0);\">\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <td colspan=\"5\" style=\"width: 99.6997%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">III. ALASAN CUTI</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"5\" style=\"width: 99.8498%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br><br></span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"5\" style=\"width: 99.6997%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">IV. LAMANYA CUTI</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 15.991%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Selama</span></td>\n" +
                "            <td colspan=\"2\" style=\"width: 33.7087%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">&nbsp;( Hari, Bulan, Tahun)</span></td>\n" +
                "            <td style=\"width: 16.2163%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Mulai Tanggal</span></td>\n" +
                "            <td style=\"width: 33.9339%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"5\" style=\"width: 99.6997%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">V. CATATAN CUTI</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"3\" style=\"width: 49.9248%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">1. CUTI TAHUNAN</span></td>\n" +
                "            <td colspan=\"2\" style=\"width: 49.9248%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">2. CUTI BESAR</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 15.991%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Tahun</span></td>\n" +
                "            <td style=\"width: 17.3423%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Sisa</span></td>\n" +
                "            <td style=\"width: 16.3382%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Keterangan</span></td>\n" +
                "            <td colspan=\"2\" style=\"width: 49.9248%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">3. CUTI SAKIT</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 15.991%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td style=\"width: 17.3423%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td style=\"width: 16.3382%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td colspan=\"2\" style=\"width: 49.9248%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">4. CUTI MELAHIRKAN</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 15.991%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td style=\"width: 17.3423%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td style=\"width: 16.3382%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td colspan=\"2\" style=\"width: 49.9248%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">5. CUTI ALASAN PENTING</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 15.991%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td style=\"width: 17.3423%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td style=\"width: 16.3382%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td colspan=\"2\" style=\"width: 49.9248%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">6. CUTI DI LUAR TANGGUNGAN NEGARA</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"5\" style=\"width: 99.6997%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">VI. ALAMAT SELAMA MENJALANGKAN CUTI</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"3\" rowspan=\"2\" style=\"width: 49.6997%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td style=\"width: 16.2163%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">TLP</span></td>\n" +
                "            <td style=\"width: 33.9339%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"2\" style=\"width: 50.2256%; vertical-align: top; border: 1px solid rgb(0, 0, 0);\">\n" +
                "                <div style=\"text-align: right;\"><span style=\"font-size: 11px;\">&nbsp;Hormat Saya, &lt;nama&gt;</span></div><span style=\"font-size: 11px;\"><br><br></span>\n" +
                "                <div data-empty=\"true\" style=\"text-align: right;\"><span style=\"font-size: 11px;\">( &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; )</span></div>\n" +
                "                <div data-empty=\"true\" style=\"text-align: right;\"><span style=\"font-size: 11px;\">NIP: &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span></div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "<table style=\"width: 100%; border-collapse: collapse; border: 1px solid rgb(0, 0, 0);\">\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <td colspan=\"4\" style=\"width: 100%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">VII. PERTIMBANGAN ATASAN LANGSUNG</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 25%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">DI SETUJUI</span></td>\n" +
                "            <td style=\"width: 25%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">PERUBAHAN**</span></td>\n" +
                "            <td style=\"width: 25%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">DI TANGGUHKAN</span></td>\n" +
                "            <td style=\"width: 25%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">TIDAK DI SETUJUI</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"2\" style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td colspan=\"2\" style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\">\n" +
                "                <div data-empty=\"true\" style=\"text-align: right;\"><span style=\"font-size: 11px;\"><br></span></div><span style=\"font-size: 11px;\"><br><br></span>\n" +
                "                <div data-empty=\"true\" style=\"text-align: right;\"><span style=\"font-size: 11px;\">(&lt;nama&gt;)</span></div>\n" +
                "                <div data-empty=\"true\" style=\"text-align: right;\"><span style=\"font-size: 11px;\">NIP:&nbsp;</span></div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"4\" style=\"width: 100%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">VII. PERTIMBANGAN PEJABAT LANGSUNG</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 25%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">DI SETUJUI</span></td>\n" +
                "            <td style=\"width: 25%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">PERUBAHAN**</span></td>\n" +
                "            <td style=\"width: 25%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">DI TANGGUHKAN</span></td>\n" +
                "            <td style=\"width: 25%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">TIDAK DI SETUJUI</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"2\" style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td colspan=\"2\" style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\">\n" +
                "                <div data-empty=\"true\" style=\"text-align: right;\"><span style=\"font-size: 11px;\"><br></span></div><span style=\"font-size: 11px;\"><br><br></span>\n" +
                "                <div data-empty=\"true\" style=\"text-align: right;\"><span style=\"font-size: 11px;\">(&lt;nama&gt;)</span></div>\n" +
                "                <div data-empty=\"true\" style=\"text-align: right;\"><span style=\"font-size: 11px;\">NIP:&nbsp;</span></div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "<p><br></p>";

        return  template;
    }

    public   void print(Context context){
        FileManager.getInstance().cleanTempFolder(context);
        // Create Temp File to save Pdf To
        final File savedPDFFile = FileManager.getInstance().createTempFile(context, "pdf", false);
        // Generate Pdf From Html

        PDFUtil.generatePDFFromHTML(context, savedPDFFile,   template(), new PDFPrint.OnPDFPrintListener() {
            @Override
            public void onSuccess(File file) {
                // Open Pdf Viewer
                Uri pdfUri = Uri.fromFile(savedPDFFile);
                System.out.println(pdfUri);

                Intent intentPdfViewer = new Intent(context, PdfViewerExampleActivity.class);
                intentPdfViewer.putExtra(PdfViewerExampleActivity.PDF_FILE_URI, pdfUri);

                context.startActivity(intentPdfViewer);
            }

            @Override
            public void onError(Exception exception) {
                exception.printStackTrace();
            }
        });
    }



}
