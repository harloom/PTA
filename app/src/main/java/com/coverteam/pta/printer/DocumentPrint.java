package com.coverteam.pta.printer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.print.PDFPrint;

import com.coverteam.pta.data.models.AlasanCuti;
import com.coverteam.pta.data.models.DocumentCuti;
import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.tools.CustomMask;
import com.tejpratapsingh.pdfcreator.utils.FileManager;
import com.tejpratapsingh.pdfcreator.utils.PDFUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DocumentPrint {
    private DocumentCuti documentCuti;
    private  Users dataPengaju ;
    private  Users dataPejabat;
    private  Users dataAtasan;


    public DocumentPrint(DocumentCuti documentCuti) {
        this.documentCuti = documentCuti;
    }

    public void setDataPengaju(Users dataPengaju) {
        this.dataPengaju = dataPengaju;
    }

    public void setDataPejabat(Users dataPejabat) {
        this.dataPejabat = dataPejabat;
    }

    public void setDataAtasan(Users dataAtasan) {
        this.dataAtasan = dataAtasan;
    }

    private String getNamaPengaju() {

        return  this.dataPengaju.getNama();
    }

    private  String getMasaKerja(){
        return  this.dataPengaju.getMasaKerja() + "Bulan";
    }

    private String getDate(){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        String dateString = dateFormatter.format(new Date());

        if(this.documentCuti.getCreatedAt() !=null){
            dateString = dateFormatter.format(this.documentCuti.getCreatedAt().toDate());
        }
        return dateString;
    }

    private  String getNumber (){
        String numberPrint = "";
        Date dateNow = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM / yyyy", Locale.getDefault());
        String dateString = dateFormatter.format(dateNow);
        numberPrint = " W/8A/ "+ dateNow.getTime() + " / KP-052 / " + dateString;
        return  numberPrint;
    }

    private  String htmlCutiTahunanStrike (){
        String html = "CUTI TAHUNAN";

        if(this.documentCuti.getTypeAlasan() !=null && !this.documentCuti.getTypeAlasan().equals(AlasanCuti.CUTI_TAHUNAN)){
            html ="<del>" +html+" </del>";
        }

        return  html;
    };

    private  String htmlCutiBesarStrike (){
        String html = "CUTI BESAR";

        if(this.documentCuti.getTypeAlasan() !=null &&
                !this.documentCuti.getTypeAlasan().equals(AlasanCuti.CUTI_BESAR)){
            html ="<del>" +html+" </del>";
        }

        return  html;
    };

    private  String htmlCutiSakitStrike (){
        String html = "CUTI SAKIT";

        if(this.documentCuti.getTypeAlasan() !=null &&
                !this.documentCuti.getTypeAlasan().equals(AlasanCuti.CUTI_SAKIT)){
            html ="<del>" +html+" </del>";
        }

        return  html;
    };




    private  String htmlCutiMelahirkanStrike (){
        String html = "CUTI MELAHIRKAN";

        if(this.documentCuti.getTypeAlasan() !=null &&
                !this.documentCuti.getTypeAlasan().equals(AlasanCuti.CUTI_MELAHIRKAN)){
            html ="<del>" +html+" </del>";
        }

        return  html;
    };

    private  String htmlCutiAlasanPentingStrike (){
        String html = "CUTI ALASAN PENTING";

        if(this.documentCuti.getTypeAlasan() !=null &&
                !this.documentCuti.getTypeAlasan().equals(AlasanCuti.CUTI_ALASAN_PENTING)){
            html ="<del>" +html+" </del>";
        }

        return  html;
    };

    private  String htmlCutiDiLuarTanggunganNegaraStrike (){
        String html = "CUTI di LUAR TANGGUNGAN NEGARA";

        if(this.documentCuti.getTypeAlasan() !=null &&
                !this.documentCuti.getTypeAlasan().equals(AlasanCuti.CUTI_DILUAR_TANGUNGAN_NEGARA)){
            html ="<del>" +html+" </del>";
        }

        return  html;
    };

    private String  getTGlAwalDanAkhir(){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        String dateString = "";

        String dateFirst = dateFormatter.format(this.documentCuti.getListTgl().get(0));

        String dateLast = dateFormatter.format(this.documentCuti.getListTgl().get(this.documentCuti.getListTgl().size() - 1));

        dateString = dateFirst + " s/d " + dateLast;
        return  dateString;
    }

    private  String getYearNow(){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy", Locale.getDefault());
        String dateString = dateFormatter.format(new Date());

        return  dateString;
    }

    private  String getNameAtasan(){
        String a = "Tidak ada atasan";

        if(this.dataAtasan != null && this.dataAtasan.getNama() !=null){
            a = this.dataAtasan.getNama();
        }

        return  a;
    }


    private  String getNipAtasan(){
        String a = "";

        if(this.dataAtasan != null && this.dataAtasan.getNip() !=null){
            a = CustomMask.formatNIP(this.dataAtasan.getNip());
        }

        return  a;
    }

    private  String getNamePejabat(){
        String a = "Tidak ada pejabat";

        if(this.dataPejabat != null && this.dataPejabat.getNama() !=null){
            a = this.dataPejabat.getNama();
        }

        return  a;
    }


    private  String getNipPejabat(){
        String a = "";

        if(this.dataPejabat != null && this.dataPejabat.getNip() !=null){
            a = CustomMask.formatNIP(this.dataPejabat.getNip());
        }

        return  a;
    }


    private  String template (){
        String template = "";

        template ="<p style=\"text-align: left;\"><span style=\"font-size: 11px;\">Bandar Lampung, &nbsp; &nbsp; &nbsp; &nbsp; "+ getDate() +" &nbsp;,</span><br><span style=\"font-size: 11px;\">Kepada:</span><br><span style=\"font-size: 11px;\">Yth. Ketua PTA Bandar Lampung</span><br><span style=\"font-size: 11px;\">Di tempat</span></p>\n" +
                "<p style=\"text-align: center;\"><strong><span style=\"font-size: 11px;\">FOMULIR PERMINTAAN DAN PEMEBERIAN CUTI</span></strong><br><span style=\"font-size: 11px;\">Nomor : "  +getNumber()+ " </span></p>\n" +
                "<table style=\"width: 100%; border-collapse: collapse; border: 1px solid rgb(0, 0, 0);\">\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <td colspan=\"4\" style=\"width: 100%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">I. DATA PEGAWAI</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 24.9833%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Nama</span></td>\n" +
                "            <td style=\"width: 24.9666%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">  "+getNamaPengaju()+";</span></td>\n" +
                "            <td style=\"width: 24.9666%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">NIP</span></td>\n" +
                "            <td style=\"width: 25.0167%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">  "+ this.dataPengaju.getNip()+"</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 24.9833%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Jabatan</span></td>\n" +
                "            <td style=\"width: 24.9666%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">  "+ this.dataPengaju.getJabatan()+"</span></td>\n" +
                "            <td style=\"width: 24.9666%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Masa Kerja</span></td>\n" +
                "            <td style=\"width: 25.0167%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">  "+ getMasaKerja() +"</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 24.9833%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Unit Kerja</span></td>\n" +
                "            <td colspan=\"3\" style=\"width: 74.8999%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">  "+ this.dataPengaju.getRole()+"</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"4\" style=\"width: 99.8665%; border: 0px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">II. JENIS CUTI YANG DIAMBIL **</span></td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "<table style=\"width: 100%; border-collapse: collapse; border: 1px solid rgb(0, 0, 0);\">\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">1. "+ htmlCutiTahunanStrike()+"</span></td>\n" +
                "            <td style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">2. "+ htmlCutiBesarStrike()+"</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">3. "+ htmlCutiSakitStrike()+"</span></td>\n" +
                "            <td style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">4. "+ htmlCutiMelahirkanStrike()+"</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td rowspan=\"3\" style=\"width: 49.8498%; vertical-align: top; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">5. "+ htmlCutiAlasanPentingStrike()+"</span></td>\n" +
                "            <td style=\"width: 50%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">6. "+ htmlCutiDiLuarTanggunganNegaraStrike()+"</span></td>\n" +
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
                "            <td colspan=\"5\" style=\"width: 99.8498%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">  "+ this.documentCuti.getAlasan()+"<br><br></span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"5\" style=\"width: 99.6997%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">IV. LAMANYA CUTI</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 15.991%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Selama</span></td>\n" +
                "            <td colspan=\"2\" style=\"width: 33.7087%; border: 1px solid rgb(0, 0, 0);\">  "+ this.documentCuti.getListTgl().size()  +"Hari  </td>\n" +
                "            <td style=\"width: 16.2163%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Mulai Tanggal</span></td>\n" +
                "            <td style=\"width: 33.9339%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">&nbsp;" +getTGlAwalDanAkhir() +" </span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"5\" style=\"width: 99.6997%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">V. CATATAN CUTI</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"3\" style=\"width: 49.9248%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">1. "+ htmlCutiTahunanStrike() +"</span></td>\n" +
                "            <td colspan=\"2\" style=\"width: 49.9248%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">2. "+ htmlCutiBesarStrike()+ "</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 15.991%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Tahun</span></td>\n" +
                "            <td style=\"width: 17.3423%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Sisa</span></td>\n" +
                "            <td style=\"width: 16.3382%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">Keterangan</span></td>\n" +
                "            <td colspan=\"2\" style=\"width: 49.9248%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">3. "+ htmlCutiSakitStrike()+"</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 15.991%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">"+ getYearNow() +"</span></td>\n" +
                "            <td style=\"width: 17.3423%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">"+ this.dataPengaju.getJumlahMaximalCutiPertahun() +"</span></td>\n" +
                "            <td style=\"width: 16.3382%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"> Hari </span></td>\n" +
                "            <td colspan=\"2\" style=\"width: 49.9248%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">4. " + htmlCutiMelahirkanStrike()+ "</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 15.991%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td style=\"width: 17.3423%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td style=\"width: 16.3382%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td colspan=\"2\" style=\"width: 49.9248%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">5. "+ htmlCutiAlasanPentingStrike()+"</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 15.991%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td style=\"width: 17.3423%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td style=\"width: 16.3382%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "            <td colspan=\"2\" style=\"width: 49.9248%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">6. "+htmlCutiDiLuarTanggunganNegaraStrike()+"</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"5\" style=\"width: 99.6997%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">VI. ALAMAT SELAMA MENJALANGKAN CUTI</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"3\" rowspan=\"2\" style=\"width: 49.6997%; border: 1px solid rgb(0, 0, 0); vertical-align: top;\"><span style=\"font-size: 11px;\">  "+ this.documentCuti.getAlamat()+"</span></td>\n" +
                "            <td style=\"width: 16.2163%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\">TLP</span></td>\n" +
                "            <td style=\"width: 33.9339%; border: 1px solid rgb(0, 0, 0);\"><span style=\"font-size: 11px;\"><br></span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"2\" style=\"width: 50.2256%; vertical-align: top; border: 1px solid rgb(0, 0, 0); text-align: right;\">\n" +
                "                <div style=\"text-align: right;\"><span style=\"font-size: 11px;\">&nbsp;Hormat Saya, "+getNamaPengaju()+"&nbsp; </span></div><span style=\"font-size: 11px;\"><br><br></span>\n" +
                "                <div data-empty=\"true\" style=\"text-align: right;\"><span style=\"font-size: 11px;\">( &nbsp; "+getNamaPengaju()+" &nbsp; ) &nbsp; </span></div>\n" +
                "                <div data-empty=\"true\" style=\"text-align: right;\"><span style=\"font-size: 11px;\">NIP: &nbsp; &nbsp; &nbsp;"+ this.dataPengaju.getNip()+" &nbsp;</span></div>\n" +
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
                "                <div data-empty=\"true\" style=\"text-align: right;\"><span style=\"font-size: 11px;\"><br></span></div><br><span style=\"font-size: 11px;\"><br></span>\n" +
                "                <div data-empty=\"true\" style=\"text-align: right;\"><span style=\"font-size: 11px;\">( "+getNameAtasan() +" )&nbsp; </span></div>\n" +
                "                <div data-empty=\"true\" style=\"text-align: right;\"><span style=\"font-size: 11px;\">NIP: &nbsp; &nbsp; &nbsp; "+ getNipAtasan()+" &nbsp; </span><span style=\"font-size: 11px;\">&nbsp;</span></div>\n" +
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
                "                <div data-empty=\"true\" style=\"text-align: right;\"><span style=\"font-size: 11px;\">( "+getNamePejabat()+ ")&nbsp;  </span></div>\n" +
                "                <div data-empty=\"true\" style=\"text-align: right;\"><span style=\"font-size: 11px;\">NIP: &nbsp; "+getNipPejabat()+"&nbsp;</span></div>\n" +
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
