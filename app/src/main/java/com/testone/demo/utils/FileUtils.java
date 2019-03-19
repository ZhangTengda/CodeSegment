package com.testone.demo.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.testone.demo.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class FileUtils {

    public static final String DEFAULT_ATTACHMENT_MIME_TYPE = "application/octet-stream";
    public static final String K9_SETTINGS_MIME_TYPE = "application/x-k9settings";
    public static final String UTF8 = "UTF-8";
    public static final String ENTER = "\n";
    public static final String TYPE_GIF = "47494638396126026f01";
    private static final String[] UNITS = new String[]{
            "MB", "GB", "TB", "PB"
    };

    public static File getSDCardDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    public static void copyFileOrDir(File sourceFile, File targetFile) {
        if (targetFile.exists() && targetFile.isDirectory()) {
            try {
                copyDirectiory(sourceFile.getPath(), targetFile.getPath());
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        } else {
            try {
                copyFile(sourceFile, targetFile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }
    }

    // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    // 复制文件夹
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

    public static void makeDir(String dir) {
        File absDir = new File(dir);
        if (!absDir.exists()) {
            absDir.mkdirs();
        }
    }

//	public static String readFile(String file, String encoding) {
//		FileInputStream fin = null;
//		try {
//			fin = new FileInputStream(file);
//			int length = fin.available();
//			byte[] buffer = new byte[length];
//			fin.read(buffer);
//			return EncodingUtils.getString(buffer, encoding);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		} finally {
//			close(fin);
//		}
//	}

//    public static void saveDataToFile(File fileToWrite, String data) {
//        saveDataToFile(fileToWrite, data, false);
//    }

//	public static synchronized void saveDataToFile(File fileToWrite, String data, boolean appand) {
//		FileOutputStream fOut = null;
//		OutputStreamWriter myOutWriter = null;
//		data = EncodingUtils.getString(data.getBytes(), UTF8);
//		try {
//			if (!fileToWrite.exists()) {
//				fileToWrite.createNewFile();
//			}
//
//			fOut = new FileOutputStream(fileToWrite, appand);
//			myOutWriter = new OutputStreamWriter(fOut);
//			myOutWriter.append(data);
//			myOutWriter.flush();
//		} catch (Exception e) {
//			MXLog.e(MXLog.APP_WARN, e);
//		} finally {
//			close(fOut);
//			close(myOutWriter);
//		}
//	}


    public static void deleteFile(String filepath) throws IOException {
        File f = new File(filepath);// 定义文件路径
        if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
            if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
                f.delete();
            } else {// 若有则把文件放进数组，并判断是否有下级目录
                File delFile[] = f.listFiles();
                int i = f.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        deleteFile(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
                    }
                    delFile[j].delete();// 删除文件
                }
            }
        }
    }


    public static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 将字节格式化，最小单位为 MB
     *
     * @param size 字节长度 B
     * @return
     */
    public static String byteConvert(long size) {
        double viewFileSize = ((double) size);
        if (size < 1) {
            return "0.00MB";
        }
        if (size > 0 && size < (0.01 * 1024 * 1024)) {
            return "0.01MB";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        viewFileSize /= 1024 * 1024;//MB
        int unitIdx = 0;
        while (viewFileSize > 1024) {
            unitIdx++;
            viewFileSize /= 1024;
        }
        return df.format(viewFileSize) + UNITS[unitIdx];
    }

    public static String bytesToHuman(long size) {
        long Kb = 1 * 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size < Kb)
            return floatForm(size) + " byte";
        if (size >= Kb && size < Mb)
            return floatForm((double) size / Kb) + " KB";
        if (size >= Mb && size < Gb)
            return floatForm((double) size / Mb) + " MB";
        if (size >= Gb && size < Tb)
            return floatForm((double) size / Gb) + " GB";
        if (size >= Tb && size < Pb)
            return floatForm((double) size / Tb) + " TB";
        if (size >= Pb && size < Eb)
            return floatForm((double) size / Pb) + " PB";
        if (size >= Eb)
            return floatForm((double) size / Eb) + " EB";

        return "???";
    }

    public static String bytesToHuman2(long size) {
        long Kb = 1 * 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;
        DecimalFormat df = new DecimalFormat("0.0");
        if (size < Kb)
            return floatForm(size, "0.0") + " b";
        if (size >= Kb && size < Mb)
            return floatForm((double) size / Kb, "0.0") + " K";
        if (size >= Mb && size < Gb)
            return floatForm((double) size / Mb, "0.0") + " M";
        if (size >= Gb && size < Tb)
            return floatForm((double) size / Gb, "0.0") + " G";
        if (size >= Tb && size < Pb)
            return floatForm((double) size / Tb, "0.0") + " T";
        if (size >= Pb && size < Eb)
            return floatForm((double) size / Pb, "0.0") + " P";
        if (size >= Eb)
            return floatForm((double) size / Eb, "0.0") + " E";

        return "???";
    }

    public static String floatForm(double d) {
        return floatForm(d, "#.##");
    }

    public static String floatForm(double d, String formatStr) {
        return new DecimalFormat(formatStr).format(d);
    }


    public static void deleteFileOrDirectory(File fileOrDirectory) {
        if (!fileOrDirectory.exists()) {
            return;
        }
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteFileOrDirectory(child);
            }
        }
        fileOrDirectory.delete();
    }

    public static void deleteSubFileOrDirectory(File fileOrDirectory) {
        deleteSubFileOrDirectory(fileOrDirectory, true);

    }

    public static void deleteSubFileOrDirectory(File fileOrDirectory, boolean includeRootFolder) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles()) {
                deleteFileOrDirectory(child);
            }
        if (includeRootFolder) {
            fileOrDirectory.delete();
        }
    }

    public static boolean isVideo(String contentType) {
        if (TextUtils.isEmpty(contentType)) {
            return false;
        }

        if ("video/quicktime".equals(contentType) || "application/vnd.rn-realmedia".equals(contentType) || "video/x-msvideo".equals(contentType)
                || "video/x-dv".equals(contentType) || "application/x-dvi".equals(contentType) || "video/mp4".equals(contentType)
                || "video/mpeg".equals(contentType) || "application/mp4".equals(contentType) || "video/vnd.objectvideo".equals(contentType) || "video/*".equals(contentType)) {
            return true;
        }
        return false;

    }

    public static boolean isAudio(String contentType) {
        if (TextUtils.isEmpty(contentType)) {
            return false;
        }

        if (contentType.startsWith("audio/")) {
            return true;
        }
        return false;

    }

    public static boolean isPicture(String contentType) {
        if (TextUtils.isEmpty(contentType)) {
            return false;
        }
        if (contentType.startsWith("image/")) {
            return true;
        }

        return false;

    }

    public static String getCatalogFromContentType(String contentType) {
        String catalog = "unknown";

        if (contentType != null && !"".equals(contentType)) {
            if (contentType.startsWith("image/")) {
                catalog = "image";
            } else if ("application/msword".equals(contentType) || "application/vnd.ms-excel".equals(contentType)
                    || "application/vnd.ms-powerpoint".equals(contentType)
                    || "application/vnd.openxmlformats-officedocument.presentationml.presentation".equals(contentType)
                    || "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType)
                    || "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType)
                    || "application/pdf".equals(contentType)) {
                catalog = "doc";
            } else if ("application/vnd.ms-excel".equals(contentType)
                    || "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType)) {
                catalog = "excel";
            } else if ("application/vnd.ms-powerpoint".equals(contentType)
                    || "application/vnd.openxmlformats-officedocument.presentationml.presentation".equals(contentType)) {
                catalog = "ppt";
            } else if ("application/pdf".equals(contentType)) {
                catalog = "pdf";
            } else if ("video/quicktime".equals(contentType) || "application/vnd.rn-realmedia".equals(contentType)
                    || "video/x-msvideo".equals(contentType) || "video/x-dv".equals(contentType) || "application/x-dvi".equals(contentType)
                    || "video/mp4".equals(contentType) || "video/mpeg".equals(contentType) || "application/mp4".equals(contentType)
                    || "video/vnd.objectvideo".equals(contentType)) {
                catalog = "video";
            } else if (contentType.startsWith("audio/")) {
                catalog = "audio";
            } else if ("application/zip".equals(contentType)) {
                catalog = "zip";
            } else if ("text/plain".equals(contentType)) {
                catalog = "txt";
            } else if ("audio/amr".equals(contentType)) {
                catalog = "audio";
            }
        }
        return catalog;
    }

//	public static String getDefaultThumbnailFromContentType(String contentType) {
//		String thumbnail = "/images/files/file_90x90.png?vc=" + MXKit.getInstance().getAppVersionName();
//
//		if (contentType != null && !"".equals(contentType)) {
//			if (contentType.startsWith("image/")) {
//				thumbnail = "/images/files/image_90x90.png?vc=" + MXKit.getInstance().getAppVersionName();
//			} else if ("application/msword".equals(contentType)
//					|| "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType)) {
//				thumbnail = "/images/files/word_90x90.png?vc=" + MXKit.getInstance().getAppVersionName();
//			} else if ("application/vnd.ms-excel".equals(contentType)
//					|| "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType)) {
//				thumbnail = "/images/files/excel_90x90.png?vc=" + MXKit.getInstance().getAppVersionName();
//			} else if ("application/vnd.ms-powerpoint".equals(contentType)
//					|| "application/vnd.openxmlformats-officedocument.presentationml.presentation".equals(contentType)) {
//				thumbnail = "/images/files/ppt_90x90.png?vc=" + MXKit.getInstance().getAppVersionName();
//			} else if ("application/pdf".equals(contentType)) {
//				thumbnail = "/images/files/pdf_90x90.png?vc=" + MXKit.getInstance().getAppVersionName();
//			} else if ("video/quicktime".equals(contentType) || "application/vnd.rn-realmedia".equals(contentType)
//					|| "video/x-msvideo".equals(contentType) || "video/x-dv".equals(contentType) || "application/x-dvi".equals(contentType)
//					|| "video/mp4".equals(contentType) || "video/mpeg".equals(contentType) || "application/mp4".equals(contentType)
//					|| "video/vnd.objectvideo".equals(contentType)) {
//				thumbnail = "/images/files/video_90x90.png?vc=" + MXKit.getInstance().getAppVersionName();
//			} else if (contentType.startsWith("audio/")) {
//				thumbnail = "/images/files/video_90x90.png?vc=" + MXKit.getInstance().getAppVersionName();
//			} else if ("application/zip".equals(contentType)) {
//				thumbnail = "/images/files/zip_90x90.png?vc=" + MXKit.getInstance().getAppVersionName();
//			} else if ("text/plain".equals(contentType)) {
//				thumbnail = "/images/files/txt_90x90.png?vc=" + MXKit.getInstance().getAppVersionName();
//			}
//		}
//		return thumbnail;
//	}

    public static boolean isTxt(String filePath, String contentType) {
        if (!TextUtils.isEmpty(filePath) && (filePath.endsWith(".txt"))) {
            return true;
        }

        if ("text/plain".equals(contentType)) {
            return true;
        }
        return false;
    }


    public static boolean isMsWord(String filePath, String contentType) {
        if (!TextUtils.isEmpty(filePath)
                && (filePath.endsWith(".doc") || filePath.endsWith(".docx") || filePath.endsWith(".txt"))) {
            return true;
        }

        if ("application/msword".equals(contentType)
                || "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType)) {
            return true;
        }
        return false;
    }

    public static boolean isMsExcel(String filePath, String contentType) {
        if (!TextUtils.isEmpty(filePath) && (filePath.endsWith(".xls") || filePath.endsWith(".xlsx"))) {
            return true;
        }
        if ("application/vnd.ms-excel".equals(contentType)
                || "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType)) {
            return true;
        }
        return false;
    }

    public static boolean isMsPPT(String filePath, String contentType) {
        if (!TextUtils.isEmpty(filePath) && (filePath.endsWith(".ppt") || filePath.endsWith(".pptx"))) {
            return true;
        }
        if ("application/vnd.ms-powerpoint".equals(contentType) || "application/vnd.openxmlformats-officedocument.presentationml.presentation".equals(contentType)) {
            return true;
        }
        return false;
    }

    public static boolean isPDF(String filePath, String contentType) {
        if (!TextUtils.isEmpty(filePath) && (filePath.endsWith(".pdf"))) {
            return true;
        }
        if ("application/pdf".equals(contentType)) {
            return true;
        }
        return false;
    }

//    public static int getFileIconByServerType(String serverType) {
//        int defaultIcon = R.drawable.mx_file_file_90x90;
//
//        if (serverType != null && !"".equals(serverType)) {
//            if ("image".equals(serverType)) {
//                return R.drawable.mx_file_image_90x90;
//            } else if ("word".equals(serverType)) {
//                return R.drawable.mx_file_word_90x90;
//            } else if ("excel".equals(serverType)) {
//                return R.drawable.mx_file_excel_90x90;
//            } else if ("ppt".equals(serverType)) {
//                return R.drawable.mx_file_ppt_90x90;
//            } else if ("pdf".equals(serverType)) {
//                return R.drawable.mx_file_pdf_90x90;
//            } else if ("video".equals(serverType)) {
//                return R.drawable.mx_file_video_90x90;
//            } else if ("audio".equals(serverType)) {
//                return R.drawable.mx_file_audio_90x90;
//            } else if ("zip".equals(serverType)) {
//                return R.drawable.mx_file_zip_90x90;
//            } else if ("txt".equals(serverType)) {
//                return R.drawable.mx_file_text_90x90;
//            } else if ("folder".equals(serverType)) {
//                return R.drawable.mx_net_disk_folder;
//            } else if ("doc".equals(serverType)) {
//                return R.drawable.mx_file_doc_90x90;
//            } else if ("file".equals(serverType)) {
//                return R.drawable.mx_file_file_90x90;
//            } else if ("group".equals(serverType)) {
//                return R.drawable.mx_net_disk_partition;
//            }
//        }
//        return defaultIcon;
//    }


    public static int getFileIconByContentType(String contentType) {
        int defaultIcon = R.drawable.mx_file_file_90x90;

        if (contentType != null && !"".equals(contentType)) {
            if (contentType.startsWith("image/")) {
                return R.drawable.mx_file_image_90x90;
            } else if ("application/msword".equals(contentType)
                    || "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType)) {
                return R.drawable.mx_file_word_90x90;
            } else if ("application/vnd.ms-excel".equals(contentType)
                    || "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType)) {
                return R.drawable.mx_file_excel_90x90;
            } else if ("application/vnd.ms-powerpoint".equals(contentType)
                    || "application/vnd.openxmlformats-officedocument.presentationml.presentation".equals(contentType)) {
                return R.drawable.mx_file_ppt_90x90;
            } else if ("application/pdf".equals(contentType)) {
                return R.drawable.mx_file_pdf_90x90;
            } else if ("video/quicktime".equals(contentType) || "application/vnd.rn-realmedia".equals(contentType)
                    || "video/x-msvideo".equals(contentType) || "video/x-dv".equals(contentType) || "application/x-dvi".equals(contentType)
                    || "video/mp4".equals(contentType) || "video/mpeg".equals(contentType) || "application/mp4".equals(contentType)
                    || "video/vnd.objectvideo".equals(contentType) || "video/*".equals(contentType)) {
                return R.drawable.mx_file_video_90x90;
            } else if (contentType.startsWith("audio/")) {
                return R.drawable.mx_file_audio_90x90;
            } else if ("application/zip".equals(contentType)) {
                return R.drawable.mx_file_zip_90x90;
            } else if ("text/plain".equals(contentType)) {
                return R.drawable.mx_file_text_90x90;
            }
        }
        return defaultIcon;
    }

//	public static void installAppPackage(String zipFilePath, String appDownloadTempPath, String app_id, int version_code) throws MXException {
//		String appsRootPath = AppcenterUtils.getWebAppInstallRoot();
//		String unzipAppPath = appDownloadTempPath + File.separator + app_id;
//		String newUpgradePackageName = version_code + ".zip";
//		String upgradeAppRootPath = appsRootPath + app_id + File.separator +  version_code;
//
//		FileUtils.unzipFile(zipFilePath, unzipAppPath);
//		File newUpgradePackage = new File(zipFilePath);
//
//		FileUtils.deleteSubFileOrDirectory(new File(appsRootPath + app_id ));//clean app目录
//
//		FileUtils.copyFileOrDir(newUpgradePackage, new File(unzipAppPath
//				+ File.separator + newUpgradePackageName));// 将升级包考至应用目录中
//		FileUtils.deleteFileOrDirectory(newUpgradePackage);
//
//		File unzipedApp = new File(unzipAppPath);
//		FileUtils.makeDir(upgradeAppRootPath);
//		FileUtils.copyFileOrDir(unzipedApp, new File(upgradeAppRootPath));
//		FileUtils.deleteFileOrDirectory(unzipedApp);
//		File tmpAppFile = new File(appDownloadTempPath );
//		File tmpFile = tmpAppFile.getParentFile();
//		if (tmpFile.isDirectory()) {
//			File[] fileNames = tmpFile.listFiles();
//			if (fileNames != null && fileNames.length == 0) {
//				FileUtils.deleteFileOrDirectory(tmpFile);// 删除临时文件夹
//			} else {
//				FileUtils.deleteFileOrDirectory(new File(appDownloadTempPath));// 删除临时文件夹
//			}
//		}
//	}

//	public static void unzipFile(String _zipFile, String _location) throws MXException {
//		File f = new File(_location);
//
//		if (!f.isDirectory()) {
//			f.mkdirs();
//		}
//		try {
//			FileInputStream fin = new FileInputStream(_zipFile);
//			ZipInputStream zin = new ZipInputStream(fin);
//			ZipEntry ze = null;
//			byte[] buffer = new byte[1024];
//			String filename;
//			while ((ze = zin.getNextEntry()) != null) {
//				MXLog.v("Decompress", "Unzipping " + ze.getName());
//				filename = ze.getName();
//				if (ze.isDirectory()) {
//                    File fmd = new File(_location, filename);
//                    fmd.mkdirs();
//                    continue;
//                } else {
//                    // Make this part of the code more efficient .code-revise
//                    File fmd = new File(_location, filename);
//                    MXLog.d("Unzipping", fmd.getParentFile().getPath());
//                    String parent = fmd.getParentFile().getPath();
//
//                    File fmd_1 = new File(parent);
//                    fmd_1.mkdirs();
//                    // end of .code-revise
//                }
//				FileOutputStream fout = new FileOutputStream(_location + File.separator + filename);
//				int count;
//                // cteni zipu a zapis
//                while ((count = zin.read(buffer)) != -1) {
//                    fout.write(buffer, 0, count);
//                }
//
//                fout.close();
//                zin.closeEntry();
//			}
//			zin.close();
//		} catch (Exception e) {
//			MXLog.e("Decompress", "unzip", e);
//			throw new MXException("Decompress file fail");
//		}
//	}

    public static void zipFile(String _zipFile, String _location) {

    }

    public static void zip(String src, String dest) throws IOException {
        //提供了一个数据项压缩成一个ZIP归档输出流
        ZipOutputStream out = null;
        try {

            File outFile = new File(dest);//源文件或者目录
            File fileOrDirectory = new File(src);//压缩文件路径
            out = new ZipOutputStream(new FileOutputStream(outFile));
            //如果此文件是一个文件，否则为false。
            if (fileOrDirectory.isFile()) {
                zipFileOrDirectory(out, fileOrDirectory, "");
            } else {
                //返回一个文件或空阵列。
                File[] entries = fileOrDirectory.listFiles();
                for (int i = 0; i < entries.length; i++) {
                    // 递归压缩，更新curPaths
                    zipFileOrDirectory(out, entries[i], "");
                }
            }
        } catch (IOException ex) {
        } finally {
            //关闭输出流
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                }
            }
        }
    }


    private static void zipFileOrDirectory(ZipOutputStream out,
                                           File fileOrDirectory, String curPath) throws IOException {
        //从文件中读取字节的输入流
        FileInputStream in = null;
        try {
            //如果此文件是一个目录，否则返回false。
            if (!fileOrDirectory.isDirectory()) {
                // 压缩文件
                byte[] buffer = new byte[4096];
                int bytes_read;
                in = new FileInputStream(fileOrDirectory);
                //实例代表一个条目内的ZIP归档
                ZipEntry entry = new ZipEntry(curPath
                        + fileOrDirectory.getName());
                //条目的信息写入底层流
                out.putNextEntry(entry);
                while ((bytes_read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytes_read);
                }
                out.closeEntry();
            } else {
                // 压缩目录
                File[] entries = fileOrDirectory.listFiles();
                for (int i = 0; i < entries.length; i++) {
                    // 递归压缩，更新curPaths
                    zipFileOrDirectory(out, entries[i], curPath
                            + fileOrDirectory.getName() + "/");
                }
            }
        } catch (IOException ex) {
            // throw ex;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    public static String getFileNameByUrl(String url) {
        if (!TextUtils.isEmpty(url) && url.contains("/")) {
            int index = url.lastIndexOf("/");
            String suffix = url.substring(index + 1, url.length());
            return suffix;
        }
        return null;

    }
	public static String guessContentTypeFromName(String fileName) {
		String contentType = null;
		try{
			contentType = URLConnection.guessContentTypeFromName(fileName);

		}catch(Exception e){
		}

		if(TextUtils.isEmpty(contentType)){
			try{
				contentType = getMimeTypeByExtension(fileName);
			}catch(Exception e){
			}

		}
		return contentType;

	}

    public static String getMimeTypeByExtension(String filename) {
        String returnedType = null;
        String extension = null;
        if (filename != null && filename.lastIndexOf('.') != -1) {
            extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase(Locale.US);
        }

        if (extension != null) {
            for (String[] contentTypeMapEntry : MIME_TYPE_BY_EXTENSION_MAP) {
                if (contentTypeMapEntry[0].equals(extension)) {
                    return contentTypeMapEntry[1];
                }
            }
        }

        if (extension != null) {
            returnedType = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }

        // If the MIME type set by the user's mailer is application/octet-stream, try to figure
        // out whether there's a sane file type extension.
        if (returnedType != null && !DEFAULT_ATTACHMENT_MIME_TYPE.equalsIgnoreCase(returnedType)) {
            return returnedType;
        } else

            return DEFAULT_ATTACHMENT_MIME_TYPE;
    }

    public static final String[][] MIME_TYPE_BY_EXTENSION_MAP = new String[][] {
            //* Do not delete the next two lines
            { "", DEFAULT_ATTACHMENT_MIME_TYPE },
            { "k9s", K9_SETTINGS_MIME_TYPE},
            //* Do not delete the previous two lines
            { "123", "application/vnd.lotus-1-2-3"},
            { "323", "text/h323"},
            { "3dml", "text/vnd.in3d.3dml"},
            { "3g2", "video/3gpp2"},
            { "3gp", "video/3gpp"},
            { "aab", "application/x-authorware-bin"},
            { "aac", "audio/x-aac"},
            { "aam", "application/x-authorware-map"},
            { "a", "application/octet-stream"},
            { "aas", "application/x-authorware-seg"},
            { "abw", "application/x-abiword"},
            { "acc", "application/vnd.americandynamics.acc"},
            { "ace", "application/x-ace-compressed"},
            { "acu", "application/vnd.acucobol"},
            { "acutc", "application/vnd.acucorp"},
            { "acx", "application/internet-property-stream"},
            { "adp", "audio/adpcm"},
            { "aep", "application/vnd.audiograph"},
            { "afm", "application/x-font-type1"},
            { "afp", "application/vnd.ibm.modcap"},
            { "ai", "application/postscript"},
            { "aif", "audio/x-aiff"},
            { "aifc", "audio/x-aiff"},
            { "aiff", "audio/x-aiff"},
            { "air", "application/vnd.adobe.air-application-installer-package+zip"},
            { "ami", "application/vnd.amiga.ami"},
            { "apk", "application/vnd.android.package-archive"},
            { "application", "application/x-ms-application"},
            { "apr", "application/vnd.lotus-approach"},
            { "asc", "application/pgp-signature"},
            { "asf", "video/x-ms-asf"},
            { "asm", "text/x-asm"},
            { "aso", "application/vnd.accpac.simply.aso"},
            { "asr", "video/x-ms-asf"},
            { "asx", "video/x-ms-asf"},
            { "atc", "application/vnd.acucorp"},
            { "atom", "application/atom+xml"},
            { "atomcat", "application/atomcat+xml"},
            { "atomsvc", "application/atomsvc+xml"},
            { "atx", "application/vnd.antix.game-component"},
            { "au", "audio/basic"},
            { "avi", "video/x-msvideo"},
            { "aw", "application/applixware"},
            { "axs", "application/olescript"},
            { "azf", "application/vnd.airzip.filesecure.azf"},
            { "azs", "application/vnd.airzip.filesecure.azs"},
            { "azw", "application/vnd.amazon.ebook"},
            { "bas", "text/plain"},
            { "bat", "application/x-msdownload"},
            { "bcpio", "application/x-bcpio"},
            { "bdf", "application/x-font-bdf"},
            { "bdm", "application/vnd.syncml.dm+wbxml"},
            { "bh2", "application/vnd.fujitsu.oasysprs"},
            { "bin", "application/octet-stream"},
            { "bmi", "application/vnd.bmi"},
            { "bmp", "image/bmp"},
            { "book", "application/vnd.framemaker"},
            { "box", "application/vnd.previewsystems.box"},
            { "boz", "application/x-bzip2"},
            { "bpk", "application/octet-stream"},
            { "btif", "image/prs.btif"},
            { "bz2", "application/x-bzip2"},
            { "bz", "application/x-bzip"},
            { "c4d", "application/vnd.clonk.c4group"},
            { "c4f", "application/vnd.clonk.c4group"},
            { "c4g", "application/vnd.clonk.c4group"},
            { "c4p", "application/vnd.clonk.c4group"},
            { "c4u", "application/vnd.clonk.c4group"},
            { "cab", "application/vnd.ms-cab-compressed"},
            { "car", "application/vnd.curl.car"},
            { "cat", "application/vnd.ms-pki.seccat"},
            { "cct", "application/x-director"},
            { "cc", "text/x-c"},
            { "ccxml", "application/ccxml+xml"},
            { "cdbcmsg", "application/vnd.contact.cmsg"},
            { "cdf", "application/x-cdf"},
            { "cdkey", "application/vnd.mediastation.cdkey"},
            { "cdx", "chemical/x-cdx"},
            { "cdxml", "application/vnd.chemdraw+xml"},
            { "cdy", "application/vnd.cinderella"},
            { "cer", "application/x-x509-ca-cert"},
            { "cgm", "image/cgm"},
            { "chat", "application/x-chat"},
            { "chm", "application/vnd.ms-htmlhelp"},
            { "chrt", "application/vnd.kde.kchart"},
            { "cif", "chemical/x-cif"},
            { "cii", "application/vnd.anser-web-certificate-issue-initiation"},
            { "cla", "application/vnd.claymore"},
            { "class", "application/java-vm"},
            { "clkk", "application/vnd.crick.clicker.keyboard"},
            { "clkp", "application/vnd.crick.clicker.palette"},
            { "clkt", "application/vnd.crick.clicker.template"},
            { "clkw", "application/vnd.crick.clicker.wordbank"},
            { "clkx", "application/vnd.crick.clicker"},
            { "clp", "application/x-msclip"},
            { "cmc", "application/vnd.cosmocaller"},
            { "cmdf", "chemical/x-cmdf"},
            { "cml", "chemical/x-cml"},
            { "cmp", "application/vnd.yellowriver-custom-menu"},
            { "cmx", "image/x-cmx"},
            { "cod", "application/vnd.rim.cod"},
            { "com", "application/x-msdownload"},
            { "conf", "text/plain"},
            { "cpio", "application/x-cpio"},
            { "cpp", "text/x-c"},
            { "cpt", "application/mac-compactpro"},
            { "crd", "application/x-mscardfile"},
            { "crl", "application/pkix-crl"},
            { "crt", "application/x-x509-ca-cert"},
            { "csh", "application/x-csh"},
            { "csml", "chemical/x-csml"},
            { "csp", "application/vnd.commonspace"},
            { "css", "text/css"},
            { "cst", "application/x-director"},
            { "csv", "text/csv"},
            { "c", "text/plain"},
            { "cu", "application/cu-seeme"},
            { "curl", "text/vnd.curl"},
            { "cww", "application/prs.cww"},
            { "cxt", "application/x-director"},
            { "cxx", "text/x-c"},
            { "daf", "application/vnd.mobius.daf"},
            { "dataless", "application/vnd.fdsn.seed"},
            { "davmount", "application/davmount+xml"},
            { "dcr", "application/x-director"},
            { "dcurl", "text/vnd.curl.dcurl"},
            { "dd2", "application/vnd.oma.dd2+xml"},
            { "ddd", "application/vnd.fujixerox.ddd"},
            { "deb", "application/x-debian-package"},
            { "def", "text/plain"},
            { "deploy", "application/octet-stream"},
            { "der", "application/x-x509-ca-cert"},
            { "dfac", "application/vnd.dreamfactory"},
            { "dic", "text/x-c"},
            { "diff", "text/plain"},
            { "dir", "application/x-director"},
            { "dis", "application/vnd.mobius.dis"},
            { "dist", "application/octet-stream"},
            { "distz", "application/octet-stream"},
            { "djv", "image/vnd.djvu"},
            { "djvu", "image/vnd.djvu"},
            { "dll", "application/x-msdownload"},
            { "dmg", "application/octet-stream"},
            { "dms", "application/octet-stream"},
            { "dna", "application/vnd.dna"},
            { "doc", "application/msword"},
            { "docm", "application/vnd.ms-word.document.macroenabled.12"},
            { "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            { "dot", "application/msword"},
            { "dotm", "application/vnd.ms-word.template.macroenabled.12"},
            { "dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template"},
            { "dp", "application/vnd.osgi.dp"},
            { "dpg", "application/vnd.dpgraph"},
            { "dsc", "text/prs.lines.tag"},
            { "dtb", "application/x-dtbook+xml"},
            { "dtd", "application/xml-dtd"},
            { "dts", "audio/vnd.dts"},
            { "dtshd", "audio/vnd.dts.hd"},
            { "dump", "application/octet-stream"},
            { "dvi", "application/x-dvi"},
            { "dwf", "model/vnd.dwf"},
            { "dwg", "image/vnd.dwg"},
            { "dxf", "image/vnd.dxf"},
            { "dxp", "application/vnd.spotfire.dxp"},
            { "dxr", "application/x-director"},
            { "ecelp4800", "audio/vnd.nuera.ecelp4800"},
            { "ecelp7470", "audio/vnd.nuera.ecelp7470"},
            { "ecelp9600", "audio/vnd.nuera.ecelp9600"},
            { "ecma", "application/ecmascript"},
            { "edm", "application/vnd.novadigm.edm"},
            { "edx", "application/vnd.novadigm.edx"},
            { "efif", "application/vnd.picsel"},
            { "ei6", "application/vnd.pg.osasli"},
            { "elc", "application/octet-stream"},
            { "eml", "message/rfc822"},
            { "emma", "application/emma+xml"},
            { "eol", "audio/vnd.digital-winds"},
            { "eot", "application/vnd.ms-fontobject"},
            { "eps", "application/postscript"},
            { "epub", "application/epub+zip"},
            { "es3", "application/vnd.eszigno3+xml"},
            { "esf", "application/vnd.epson.esf"},
            { "et3", "application/vnd.eszigno3+xml"},
            { "etx", "text/x-setext"},
            { "evy", "application/envoy"},
            { "exe", "application/octet-stream"},
            { "ext", "application/vnd.novadigm.ext"},
            { "ez2", "application/vnd.ezpix-album"},
            { "ez3", "application/vnd.ezpix-package"},
            { "ez", "application/andrew-inset"},
            { "f4v", "video/x-f4v"},
            { "f77", "text/x-fortran"},
            { "f90", "text/x-fortran"},
            { "fbs", "image/vnd.fastbidsheet"},
            { "fdf", "application/vnd.fdf"},
            { "fe_launch", "application/vnd.denovo.fcselayout-link"},
            { "fg5", "application/vnd.fujitsu.oasysgp"},
            { "fgd", "application/x-director"},
            { "fh4", "image/x-freehand"},
            { "fh5", "image/x-freehand"},
            { "fh7", "image/x-freehand"},
            { "fhc", "image/x-freehand"},
            { "fh", "image/x-freehand"},
            { "fif", "application/fractals"},
            { "fig", "application/x-xfig"},
            { "fli", "video/x-fli"},
            { "flo", "application/vnd.micrografx.flo"},
            { "flr", "x-world/x-vrml"},
            { "flv", "video/x-flv"},
            { "flw", "application/vnd.kde.kivio"},
            { "flx", "text/vnd.fmi.flexstor"},
            { "fly", "text/vnd.fly"},
            { "fm", "application/vnd.framemaker"},
            { "fnc", "application/vnd.frogans.fnc"},
            { "for", "text/x-fortran"},
            { "fpx", "image/vnd.fpx"},
            { "frame", "application/vnd.framemaker"},
            { "fsc", "application/vnd.fsc.weblaunch"},
            { "fst", "image/vnd.fst"},
            { "ftc", "application/vnd.fluxtime.clip"},
            { "f", "text/x-fortran"},
            { "fti", "application/vnd.anser-web-funds-transfer-initiation"},
            { "fvt", "video/vnd.fvt"},
            { "fzs", "application/vnd.fuzzysheet"},
            { "g3", "image/g3fax"},
            { "gac", "application/vnd.groove-account"},
            { "gdl", "model/vnd.gdl"},
            { "geo", "application/vnd.dynageo"},
            { "gex", "application/vnd.geometry-explorer"},
            { "ggb", "application/vnd.geogebra.file"},
            { "ggt", "application/vnd.geogebra.tool"},
            { "ghf", "application/vnd.groove-help"},
            { "gif", "image/gif"},
            { "gim", "application/vnd.groove-identity-message"},
            { "gmx", "application/vnd.gmx"},
            { "gnumeric", "application/x-gnumeric"},
            { "gph", "application/vnd.flographit"},
            { "gqf", "application/vnd.grafeq"},
            { "gqs", "application/vnd.grafeq"},
            { "gram", "application/srgs"},
            { "gre", "application/vnd.geometry-explorer"},
            { "grv", "application/vnd.groove-injector"},
            { "grxml", "application/srgs+xml"},
            { "gsf", "application/x-font-ghostscript"},
            { "gtar", "application/x-gtar"},
            { "gtm", "application/vnd.groove-tool-message"},
            { "gtw", "model/vnd.gtw"},
            { "gv", "text/vnd.graphviz"},
            { "gz", "application/x-gzip"},
            { "h261", "video/h261"},
            { "h263", "video/h263"},
            { "h264", "video/h264"},
            { "hbci", "application/vnd.hbci"},
            { "hdf", "application/x-hdf"},
            { "hh", "text/x-c"},
            { "hlp", "application/winhlp"},
            { "hpgl", "application/vnd.hp-hpgl"},
            { "hpid", "application/vnd.hp-hpid"},
            { "hps", "application/vnd.hp-hps"},
            { "hqx", "application/mac-binhex40"},
            { "hta", "application/hta"},
            { "htc", "text/x-component"},
            { "h", "text/plain"},
            { "htke", "application/vnd.kenameaapp"},
            { "html", "text/html"},
            { "htm", "text/html"},
            { "htt", "text/webviewhtml"},
            { "hvd", "application/vnd.yamaha.hv-dic"},
            { "hvp", "application/vnd.yamaha.hv-voice"},
            { "hvs", "application/vnd.yamaha.hv-script"},
            { "icc", "application/vnd.iccprofile"},
            { "ice", "x-conference/x-cooltalk"},
            { "icm", "application/vnd.iccprofile"},
            { "ico", "image/x-icon"},
            { "ics", "text/calendar"},
            { "ief", "image/ief"},
            { "ifb", "text/calendar"},
            { "ifm", "application/vnd.shana.informed.formdata"},
            { "iges", "model/iges"},
            { "igl", "application/vnd.igloader"},
            { "igs", "model/iges"},
            { "igx", "application/vnd.micrografx.igx"},
            { "iif", "application/vnd.shana.informed.interchange"},
            { "iii", "application/x-iphone"},
            { "imp", "application/vnd.accpac.simply.imp"},
            { "ims", "application/vnd.ms-ims"},
            { "ins", "application/x-internet-signup"},
            { "in", "text/plain"},
            { "ipk", "application/vnd.shana.informed.package"},
            { "irm", "application/vnd.ibm.rights-management"},
            { "irp", "application/vnd.irepository.package+xml"},
            { "iso", "application/octet-stream"},
            { "isp", "application/x-internet-signup"},
            { "itp", "application/vnd.shana.informed.formtemplate"},
            { "ivp", "application/vnd.immervision-ivp"},
            { "ivu", "application/vnd.immervision-ivu"},
            { "jad", "text/vnd.sun.j2me.app-descriptor"},
            { "jam", "application/vnd.jam"},
            { "jar", "application/java-archive"},
            { "java", "text/x-java-source"},
            { "jfif", "image/pipeg"},
            { "jisp", "application/vnd.jisp"},
            { "jlt", "application/vnd.hp-jlyt"},
            { "jnlp", "application/x-java-jnlp-file"},
            { "joda", "application/vnd.joost.joda-archive"},
            { "jpeg", "image/jpeg"},
            { "jpe", "image/jpeg"},
            { "jpg", "image/jpeg"},
            { "jpgm", "video/jpm"},
            { "jpgv", "video/jpeg"},
            { "jpm", "video/jpm"},
            { "js", "application/x-javascript"},
            { "json", "application/json"},
            { "kar", "audio/midi"},
            { "karbon", "application/vnd.kde.karbon"},
            { "kfo", "application/vnd.kde.kformula"},
            { "kia", "application/vnd.kidspiration"},
            { "kil", "application/x-killustrator"},
            { "kml", "application/vnd.google-earth.kml+xml"},
            { "kmz", "application/vnd.google-earth.kmz"},
            { "kne", "application/vnd.kinar"},
            { "knp", "application/vnd.kinar"},
            { "kon", "application/vnd.kde.kontour"},
            { "kpr", "application/vnd.kde.kpresenter"},
            { "kpt", "application/vnd.kde.kpresenter"},
            { "ksh", "text/plain"},
            { "ksp", "application/vnd.kde.kspread"},
            { "ktr", "application/vnd.kahootz"},
            { "ktz", "application/vnd.kahootz"},
            { "kwd", "application/vnd.kde.kword"},
            { "kwt", "application/vnd.kde.kword"},
            { "latex", "application/x-latex"},
            { "lbd", "application/vnd.llamagraphics.life-balance.desktop"},
            { "lbe", "application/vnd.llamagraphics.life-balance.exchange+xml"},
            { "les", "application/vnd.hhe.lesson-player"},
            { "lha", "application/octet-stream"},
            { "link66", "application/vnd.route66.link66+xml"},
            { "list3820", "application/vnd.ibm.modcap"},
            { "listafp", "application/vnd.ibm.modcap"},
            { "list", "text/plain"},
            { "log", "text/plain"},
            { "lostxml", "application/lost+xml"},
            { "lrf", "application/octet-stream"},
            { "lrm", "application/vnd.ms-lrm"},
            { "lsf", "video/x-la-asf"},
            { "lsx", "video/x-la-asf"},
            { "ltf", "application/vnd.frogans.ltf"},
            { "lvp", "audio/vnd.lucent.voice"},
            { "lwp", "application/vnd.lotus-wordpro"},
            { "lzh", "application/octet-stream"},
            { "m13", "application/x-msmediaview"},
            { "m14", "application/x-msmediaview"},
            { "m1v", "video/mpeg"},
            { "m2a", "audio/mpeg"},
            { "m2v", "video/mpeg"},
            { "m3a", "audio/mpeg"},
            { "m3u", "audio/x-mpegurl"},
            { "m4u", "video/vnd.mpegurl"},
            { "m4v", "video/x-m4v"},
            { "ma", "application/mathematica"},
            { "mag", "application/vnd.ecowin.chart"},
            { "maker", "application/vnd.framemaker"},
            { "man", "text/troff"},
            { "mathml", "application/mathml+xml"},
            { "mb", "application/mathematica"},
            { "mbk", "application/vnd.mobius.mbk"},
            { "mbox", "application/mbox"},
            { "mc1", "application/vnd.medcalcdata"},
            { "mcd", "application/vnd.mcd"},
            { "mcurl", "text/vnd.curl.mcurl"},
            { "mdb", "application/x-msaccess"},
            { "mdi", "image/vnd.ms-modi"},
            { "mesh", "model/mesh"},
            { "me", "text/troff"},
            { "mfm", "application/vnd.mfmp"},
            { "mgz", "application/vnd.proteus.magazine"},
            { "mht", "message/rfc822"},
            { "mhtml", "message/rfc822"},
            { "mid", "audio/midi"},
            { "midi", "audio/midi"},
            { "mif", "application/vnd.mif"},
            { "mime", "message/rfc822"},
            { "mj2", "video/mj2"},
            { "mjp2", "video/mj2"},
            { "mlp", "application/vnd.dolby.mlp"},
            { "mmd", "application/vnd.chipnuts.karaoke-mmd"},
            { "mmf", "application/vnd.smaf"},
            { "mmr", "image/vnd.fujixerox.edmics-mmr"},
            { "mny", "application/x-msmoney"},
            { "mobi", "application/x-mobipocket-ebook"},
            { "movie", "video/x-sgi-movie"},
            { "mov", "video/quicktime"},
            { "mp2a", "audio/mpeg"},
            { "mp2", "video/mpeg"},
            { "mp3", "audio/mpeg"},
            { "mp4a", "audio/mp4"},
            { "mp4s", "application/mp4"},
            { "mp4", "video/mp4"},
            { "mp4v", "video/mp4"},
            { "mpa", "video/mpeg"},
            { "mpc", "application/vnd.mophun.certificate"},
            { "mpeg", "video/mpeg"},
            { "mpe", "video/mpeg"},
            { "mpg4", "video/mp4"},
            { "mpga", "audio/mpeg"},
            { "mpg", "video/mpeg"},
            { "mpkg", "application/vnd.apple.installer+xml"},
            { "mpm", "application/vnd.blueice.multipass"},
            { "mpn", "application/vnd.mophun.application"},
            { "mpp", "application/vnd.ms-project"},
            { "mpt", "application/vnd.ms-project"},
            { "mpv2", "video/mpeg"},
            { "mpy", "application/vnd.ibm.minipay"},
            { "mqy", "application/vnd.mobius.mqy"},
            { "mrc", "application/marc"},
            { "mscml", "application/mediaservercontrol+xml"},
            { "mseed", "application/vnd.fdsn.mseed"},
            { "mseq", "application/vnd.mseq"},
            { "msf", "application/vnd.epson.msf"},
            { "msh", "model/mesh"},
            { "msi", "application/x-msdownload"},
            { "ms", "text/troff"},
            { "msty", "application/vnd.muvee.style"},
            { "mts", "model/vnd.mts"},
            { "mus", "application/vnd.musician"},
            { "musicxml", "application/vnd.recordare.musicxml+xml"},
            { "mvb", "application/x-msmediaview"},
            { "mxf", "application/mxf"},
            { "mxl", "application/vnd.recordare.musicxml"},
            { "mxml", "application/xv+xml"},
            { "mxs", "application/vnd.triscape.mxs"},
            { "mxu", "video/vnd.mpegurl"},
            { "nb", "application/mathematica"},
            { "nc", "application/x-netcdf"},
            { "ncx", "application/x-dtbncx+xml"},
            { "n-gage", "application/vnd.nokia.n-gage.symbian.install"},
            { "ngdat", "application/vnd.nokia.n-gage.data"},
            { "nlu", "application/vnd.neurolanguage.nlu"},
            { "nml", "application/vnd.enliven"},
            { "nnd", "application/vnd.noblenet-directory"},
            { "nns", "application/vnd.noblenet-sealer"},
            { "nnw", "application/vnd.noblenet-web"},
            { "npx", "image/vnd.net-fpx"},
            { "nsf", "application/vnd.lotus-notes"},
            { "nws", "message/rfc822"},
            { "oa2", "application/vnd.fujitsu.oasys2"},
            { "oa3", "application/vnd.fujitsu.oasys3"},
            { "o", "application/octet-stream"},
            { "oas", "application/vnd.fujitsu.oasys"},
            { "obd", "application/x-msbinder"},
            { "obj", "application/octet-stream"},
            { "oda", "application/oda"},
            { "odb", "application/vnd.oasis.opendocument.database"},
            { "odc", "application/vnd.oasis.opendocument.chart"},
            { "odf", "application/vnd.oasis.opendocument.formula"},
            { "odft", "application/vnd.oasis.opendocument.formula-template"},
            { "odg", "application/vnd.oasis.opendocument.graphics"},
            { "odi", "application/vnd.oasis.opendocument.image"},
            { "odp", "application/vnd.oasis.opendocument.presentation"},
            { "ods", "application/vnd.oasis.opendocument.spreadsheet"},
            { "odt", "application/vnd.oasis.opendocument.text"},
            { "oga", "audio/ogg"},
            { "ogg", "audio/ogg"},
            { "ogv", "video/ogg"},
            { "ogx", "application/ogg"},
            { "onepkg", "application/onenote"},
            { "onetmp", "application/onenote"},
            { "onetoc2", "application/onenote"},
            { "onetoc", "application/onenote"},
            { "opf", "application/oebps-package+xml"},
            { "oprc", "application/vnd.palm"},
            { "org", "application/vnd.lotus-organizer"},
            { "osf", "application/vnd.yamaha.openscoreformat"},
            { "osfpvg", "application/vnd.yamaha.openscoreformat.osfpvg+xml"},
            { "otc", "application/vnd.oasis.opendocument.chart-template"},
            { "otf", "application/x-font-otf"},
            { "otg", "application/vnd.oasis.opendocument.graphics-template"},
            { "oth", "application/vnd.oasis.opendocument.text-web"},
            { "oti", "application/vnd.oasis.opendocument.image-template"},
            { "otm", "application/vnd.oasis.opendocument.text-master"},
            { "otp", "application/vnd.oasis.opendocument.presentation-template"},
            { "ots", "application/vnd.oasis.opendocument.spreadsheet-template"},
            { "ott", "application/vnd.oasis.opendocument.text-template"},
            { "oxt", "application/vnd.openofficeorg.extension"},
            { "p10", "application/pkcs10"},
            { "p12", "application/x-pkcs12"},
            { "p7b", "application/x-pkcs7-certificates"},
            { "p7c", "application/x-pkcs7-mime"},
            { "p7m", "application/x-pkcs7-mime"},
            { "p7r", "application/x-pkcs7-certreqresp"},
            { "p7s", "application/x-pkcs7-signature"},
            { "pas", "text/x-pascal"},
            { "pbd", "application/vnd.powerbuilder6"},
            { "pbm", "image/x-portable-bitmap"},
            { "pcf", "application/x-font-pcf"},
            { "pcl", "application/vnd.hp-pcl"},
            { "pclxl", "application/vnd.hp-pclxl"},
            { "pct", "image/x-pict"},
            { "pcurl", "application/vnd.curl.pcurl"},
            { "pcx", "image/x-pcx"},
            { "pdb", "application/vnd.palm"},
            { "pdf", "application/pdf"},
            { "pfa", "application/x-font-type1"},
            { "pfb", "application/x-font-type1"},
            { "pfm", "application/x-font-type1"},
            { "pfr", "application/font-tdpfr"},
            { "pfx", "application/x-pkcs12"},
            { "pgm", "image/x-portable-graymap"},
            { "pgn", "application/x-chess-pgn"},
            { "pgp", "application/pgp-encrypted"},
            { "pic", "image/x-pict"},
            { "pkg", "application/octet-stream"},
            { "pki", "application/pkixcmp"},
            { "pkipath", "application/pkix-pkipath"},
            { "pko", "application/ynd.ms-pkipko"},
            { "plb", "application/vnd.3gpp.pic-bw-large"},
            { "plc", "application/vnd.mobius.plc"},
            { "plf", "application/vnd.pocketlearn"},
            { "pls", "application/pls+xml"},
            { "pl", "text/plain"},
            { "pma", "application/x-perfmon"},
            { "pmc", "application/x-perfmon"},
            { "pml", "application/x-perfmon"},
            { "pmr", "application/x-perfmon"},
            { "pmw", "application/x-perfmon"},
            { "png", "image/png"},
            { "pnm", "image/x-portable-anymap"},
            { "portpkg", "application/vnd.macports.portpkg"},
            { "pot,", "application/vnd.ms-powerpoint"},
            { "pot", "application/vnd.ms-powerpoint"},
            { "potm", "application/vnd.ms-powerpoint.template.macroenabled.12"},
            { "potx", "application/vnd.openxmlformats-officedocument.presentationml.template"},
            { "ppa", "application/vnd.ms-powerpoint"},
            { "ppam", "application/vnd.ms-powerpoint.addin.macroenabled.12"},
            { "ppd", "application/vnd.cups-ppd"},
            { "ppm", "image/x-portable-pixmap"},
            { "pps", "application/vnd.ms-powerpoint"},
            { "ppsm", "application/vnd.ms-powerpoint.slideshow.macroenabled.12"},
            { "ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow"},
            { "ppt", "application/vnd.ms-powerpoint"},
            { "pptm", "application/vnd.ms-powerpoint.presentation.macroenabled.12"},
            { "pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            { "pqa", "application/vnd.palm"},
            { "prc", "application/x-mobipocket-ebook"},
            { "pre", "application/vnd.lotus-freelance"},
            { "prf", "application/pics-rules"},
            { "ps", "application/postscript"},
            { "psb", "application/vnd.3gpp.pic-bw-small"},
            { "psd", "image/vnd.adobe.photoshop"},
            { "psf", "application/x-font-linux-psf"},
            { "p", "text/x-pascal"},
            { "ptid", "application/vnd.pvi.ptid1"},
            { "pub", "application/x-mspublisher"},
            { "pvb", "application/vnd.3gpp.pic-bw-var"},
            { "pwn", "application/vnd.3m.post-it-notes"},
            { "pwz", "application/vnd.ms-powerpoint"},
            { "pya", "audio/vnd.ms-playready.media.pya"},
            { "pyc", "application/x-python-code"},
            { "pyo", "application/x-python-code"},
            { "py", "text/x-python"},
            { "pyv", "video/vnd.ms-playready.media.pyv"},
            { "qam", "application/vnd.epson.quickanime"},
            { "qbo", "application/vnd.intu.qbo"},
            { "qfx", "application/vnd.intu.qfx"},
            { "qps", "application/vnd.publishare-delta-tree"},
            { "qt", "video/quicktime"},
            { "qwd", "application/vnd.quark.quarkxpress"},
            { "qwt", "application/vnd.quark.quarkxpress"},
            { "qxb", "application/vnd.quark.quarkxpress"},
            { "qxd", "application/vnd.quark.quarkxpress"},
            { "qxl", "application/vnd.quark.quarkxpress"},
            { "qxt", "application/vnd.quark.quarkxpress"},
            { "ra", "audio/x-pn-realaudio"},
            { "ram", "audio/x-pn-realaudio"},
            { "rar", "application/x-rar-compressed"},
            { "ras", "image/x-cmu-raster"},
            { "rcprofile", "application/vnd.ipunplugged.rcprofile"},
            { "rdf", "application/rdf+xml"},
            { "rdz", "application/vnd.data-vision.rdz"},
            { "rep", "application/vnd.businessobjects"},
            { "res", "application/x-dtbresource+xml"},
            { "rgb", "image/x-rgb"},
            { "rif", "application/reginfo+xml"},
            { "rl", "application/resource-lists+xml"},
            { "rlc", "image/vnd.fujixerox.edmics-rlc"},
            { "rld", "application/resource-lists-diff+xml"},
            { "rm", "application/vnd.rn-realmedia"},
            { "rmi", "audio/midi"},
            { "rmp", "audio/x-pn-realaudio-plugin"},
            { "rms", "application/vnd.jcp.javame.midlet-rms"},
            { "rnc", "application/relax-ng-compact-syntax"},
            { "roff", "text/troff"},
            { "rpm", "application/x-rpm"},
            { "rpss", "application/vnd.nokia.radio-presets"},
            { "rpst", "application/vnd.nokia.radio-preset"},
            { "rq", "application/sparql-query"},
            { "rs", "application/rls-services+xml"},
            { "rsd", "application/rsd+xml"},
            { "rss", "application/rss+xml"},
            { "rtf", "application/rtf"},
            { "rtx", "text/richtext"},
            { "saf", "application/vnd.yamaha.smaf-audio"},
            { "sbml", "application/sbml+xml"},
            { "sc", "application/vnd.ibm.secure-container"},
            { "scd", "application/x-msschedule"},
            { "scm", "application/vnd.lotus-screencam"},
            { "scq", "application/scvp-cv-request"},
            { "scs", "application/scvp-cv-response"},
            { "sct", "text/scriptlet"},
            { "scurl", "text/vnd.curl.scurl"},
            { "sda", "application/vnd.stardivision.draw"},
            { "sdc", "application/vnd.stardivision.calc"},
            { "sdd", "application/vnd.stardivision.impress"},
            { "sdkd", "application/vnd.solent.sdkm+xml"},
            { "sdkm", "application/vnd.solent.sdkm+xml"},
            { "sdp", "application/sdp"},
            { "sdw", "application/vnd.stardivision.writer"},
            { "see", "application/vnd.seemail"},
            { "seed", "application/vnd.fdsn.seed"},
            { "sema", "application/vnd.sema"},
            { "semd", "application/vnd.semd"},
            { "semf", "application/vnd.semf"},
            { "ser", "application/java-serialized-object"},
            { "setpay", "application/set-payment-initiation"},
            { "setreg", "application/set-registration-initiation"},
            { "sfd-hdstx", "application/vnd.hydrostatix.sof-data"},
            { "sfs", "application/vnd.spotfire.sfs"},
            { "sgl", "application/vnd.stardivision.writer-global"},
            { "sgml", "text/sgml"},
            { "sgm", "text/sgml"},
            { "sh", "application/x-sh"},
            { "shar", "application/x-shar"},
            { "shf", "application/shf+xml"},
            { "sic", "application/vnd.wap.sic"},
            { "sig", "application/pgp-signature"},
            { "silo", "model/mesh"},
            { "sis", "application/vnd.symbian.install"},
            { "sisx", "application/vnd.symbian.install"},
            { "sit", "application/x-stuffit"},
            { "si", "text/vnd.wap.si"},
            { "sitx", "application/x-stuffitx"},
            { "skd", "application/vnd.koan"},
            { "skm", "application/vnd.koan"},
            { "skp", "application/vnd.koan"},
            { "skt", "application/vnd.koan"},
            { "slc", "application/vnd.wap.slc"},
            { "sldm", "application/vnd.ms-powerpoint.slide.macroenabled.12"},
            { "sldx", "application/vnd.openxmlformats-officedocument.presentationml.slide"},
            { "slt", "application/vnd.epson.salt"},
            { "sl", "text/vnd.wap.sl"},
            { "smf", "application/vnd.stardivision.math"},
            { "smi", "application/smil+xml"},
            { "smil", "application/smil+xml"},
            { "snd", "audio/basic"},
            { "snf", "application/x-font-snf"},
            { "so", "application/octet-stream"},
            { "spc", "application/x-pkcs7-certificates"},
            { "spf", "application/vnd.yamaha.smaf-phrase"},
            { "spl", "application/x-futuresplash"},
            { "spot", "text/vnd.in3d.spot"},
            { "spp", "application/scvp-vp-response"},
            { "spq", "application/scvp-vp-request"},
            { "spx", "audio/ogg"},
            { "src", "application/x-wais-source"},
            { "srx", "application/sparql-results+xml"},
            { "sse", "application/vnd.kodak-descriptor"},
            { "ssf", "application/vnd.epson.ssf"},
            { "ssml", "application/ssml+xml"},
            { "sst", "application/vnd.ms-pkicertstore"},
            { "stc", "application/vnd.sun.xml.calc.template"},
            { "std", "application/vnd.sun.xml.draw.template"},
            { "s", "text/x-asm"},
            { "stf", "application/vnd.wt.stf"},
            { "sti", "application/vnd.sun.xml.impress.template"},
            { "stk", "application/hyperstudio"},
            { "stl", "application/vnd.ms-pki.stl"},
            { "stm", "text/html"},
            { "str", "application/vnd.pg.format"},
            { "stw", "application/vnd.sun.xml.writer.template"},
            { "sus", "application/vnd.sus-calendar"},
            { "susp", "application/vnd.sus-calendar"},
            { "sv4cpio", "application/x-sv4cpio"},
            { "sv4crc", "application/x-sv4crc"},
            { "svd", "application/vnd.svd"},
            { "svg", "image/svg+xml"},
            { "svgz", "image/svg+xml"},
            { "swa", "application/x-director"},
            { "swf", "application/x-shockwave-flash"},
            { "swi", "application/vnd.arastra.swi"},
            { "sxc", "application/vnd.sun.xml.calc"},
            { "sxd", "application/vnd.sun.xml.draw"},
            { "sxg", "application/vnd.sun.xml.writer.global"},
            { "sxi", "application/vnd.sun.xml.impress"},
            { "sxm", "application/vnd.sun.xml.math"},
            { "sxw", "application/vnd.sun.xml.writer"},
            { "tao", "application/vnd.tao.intent-module-archive"},
            { "t", "application/x-troff"},
            { "tar", "application/x-tar"},
            { "tcap", "application/vnd.3gpp2.tcap"},
            { "tcl", "application/x-tcl"},
            { "teacher", "application/vnd.smart.teacher"},
            { "tex", "application/x-tex"},
            { "texi", "application/x-texinfo"},
            { "texinfo", "application/x-texinfo"},
            { "text", "text/plain"},
            { "tfm", "application/x-tex-tfm"},
            { "tgz", "application/x-gzip"},
            { "tiff", "image/tiff"},
            { "tif", "image/tiff"},
            { "tmo", "application/vnd.tmobile-livetv"},
            { "torrent", "application/x-bittorrent"},
            { "tpl", "application/vnd.groove-tool-template"},
            { "tpt", "application/vnd.trid.tpt"},
            { "tra", "application/vnd.trueapp"},
            { "trm", "application/x-msterminal"},
            { "tr", "text/troff"},
            { "tsv", "text/tab-separated-values"},
            { "ttc", "application/x-font-ttf"},
            { "ttf", "application/x-font-ttf"},
            { "twd", "application/vnd.simtech-mindmapper"},
            { "twds", "application/vnd.simtech-mindmapper"},
            { "txd", "application/vnd.genomatix.tuxedo"},
            { "txf", "application/vnd.mobius.txf"},
            { "txt", "text/plain"},
            { "u32", "application/x-authorware-bin"},
            { "udeb", "application/x-debian-package"},
            { "ufd", "application/vnd.ufdl"},
            { "ufdl", "application/vnd.ufdl"},
            { "uls", "text/iuls"},
            { "umj", "application/vnd.umajin"},
            { "unityweb", "application/vnd.unity"},
            { "uoml", "application/vnd.uoml+xml"},
            { "uris", "text/uri-list"},
            { "uri", "text/uri-list"},
            { "urls", "text/uri-list"},
            { "ustar", "application/x-ustar"},
            { "utz", "application/vnd.uiq.theme"},
            { "uu", "text/x-uuencode"},
            { "vcd", "application/x-cdlink"},
            { "vcf", "text/x-vcard"},
            { "vcg", "application/vnd.groove-vcard"},
            { "vcs", "text/x-vcalendar"},
            { "vcx", "application/vnd.vcx"},
            { "vis", "application/vnd.visionary"},
            { "viv", "video/vnd.vivo"},
            { "vor", "application/vnd.stardivision.writer"},
            { "vox", "application/x-authorware-bin"},
            { "vrml", "x-world/x-vrml"},
            { "vsd", "application/vnd.visio"},
            { "vsf", "application/vnd.vsf"},
            { "vss", "application/vnd.visio"},
            { "vst", "application/vnd.visio"},
            { "vsw", "application/vnd.visio"},
            { "vtu", "model/vnd.vtu"},
            { "vxml", "application/voicexml+xml"},
            { "w3d", "application/x-director"},
            { "wad", "application/x-doom"},
            { "wav", "audio/x-wav"},
            { "wax", "audio/x-ms-wax"},
            { "wbmp", "image/vnd.wap.wbmp"},
            { "wbs", "application/vnd.criticaltools.wbs+xml"},
            { "wbxml", "application/vnd.wap.wbxml"},
            { "wcm", "application/vnd.ms-works"},
            { "wdb", "application/vnd.ms-works"},
            { "wiz", "application/msword"},
            { "wks", "application/vnd.ms-works"},
            { "wma", "audio/x-ms-wma"},
            { "wmd", "application/x-ms-wmd"},
            { "wmf", "application/x-msmetafile"},
            { "wmlc", "application/vnd.wap.wmlc"},
            { "wmlsc", "application/vnd.wap.wmlscriptc"},
            { "wmls", "text/vnd.wap.wmlscript"},
            { "wml", "text/vnd.wap.wml"},
            { "wm", "video/x-ms-wm"},
            { "wmv", "video/x-ms-wmv"},
            { "wmx", "video/x-ms-wmx"},
            { "wmz", "application/x-ms-wmz"},
            { "wpd", "application/vnd.wordperfect"},
            { "wpl", "application/vnd.ms-wpl"},
            { "wps", "application/vnd.ms-works"},
            { "wqd", "application/vnd.wqd"},
            { "wri", "application/x-mswrite"},
            { "wrl", "x-world/x-vrml"},
            { "wrz", "x-world/x-vrml"},
            { "wsdl", "application/wsdl+xml"},
            { "wspolicy", "application/wspolicy+xml"},
            { "wtb", "application/vnd.webturbo"},
            { "wvx", "video/x-ms-wvx"},
            { "x32", "application/x-authorware-bin"},
            { "x3d", "application/vnd.hzn-3d-crossword"},
            { "xaf", "x-world/x-vrml"},
            { "xap", "application/x-silverlight-app"},
            { "xar", "application/vnd.xara"},
            { "xbap", "application/x-ms-xbap"},
            { "xbd", "application/vnd.fujixerox.docuworks.binder"},
            { "xbm", "image/x-xbitmap"},
            { "xdm", "application/vnd.syncml.dm+xml"},
            { "xdp", "application/vnd.adobe.xdp+xml"},
            { "xdw", "application/vnd.fujixerox.docuworks"},
            { "xenc", "application/xenc+xml"},
            { "xer", "application/patch-ops-error+xml"},
            { "xfdf", "application/vnd.adobe.xfdf"},
            { "xfdl", "application/vnd.xfdl"},
            { "xht", "application/xhtml+xml"},
            { "xhtml", "application/xhtml+xml"},
            { "xhvml", "application/xv+xml"},
            { "xif", "image/vnd.xiff"},
//    { "xla", "application/vnd.ms-excel"},
            { "xlam", "application/vnd.ms-excel.addin.macroenabled.12"},
//    { "xlb", "application/vnd.ms-excel"},
/*    { "xlc", "application/vnd.ms-excel"},
    { "xlm", "application/vnd.ms-excel"},*/
            { "xls", "application/vnd.ms-excel"},
            { "xlsb", "application/vnd.ms-excel.sheet.binary.macroenabled.12"},
            { "xlsm", "application/vnd.ms-excel.sheet.macroenabled.12"},
            { "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            { "xlt", "application/vnd.ms-excel"},
            { "xltm", "application/vnd.ms-excel.template.macroenabled.12"},
            { "xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template"},
            { "xlw", "application/vnd.ms-excel"},
            { "xml", "application/xml"},
            { "xo", "application/vnd.olpc-sugar"},
            { "xof", "x-world/x-vrml"},
            { "xop", "application/xop+xml"},
            { "xpdl", "application/xml"},
            { "xpi", "application/x-xpinstall"},
            { "xpm", "image/x-xpixmap"},
            { "xpr", "application/vnd.is-xpr"},
            { "xps", "application/vnd.ms-xpsdocument"},
            { "xpw", "application/vnd.intercon.formnet"},
            { "xpx", "application/vnd.intercon.formnet"},
            { "xsl", "application/xml"},
            { "xslt", "application/xslt+xml"},
            { "xsm", "application/vnd.syncml+xml"},
            { "xspf", "application/xspf+xml"},
            { "xul", "application/vnd.mozilla.xul+xml"},
            { "xvm", "application/xv+xml"},
            { "xvml", "application/xv+xml"},
            { "xwd", "image/x-xwindowdump"},
            { "xyz", "chemical/x-xyz"},
            { "z", "application/x-compress"},
            { "zaz", "application/vnd.zzazz.deck+xml"},
            { "zip", "application/zip"},
            { "zir", "application/vnd.zul"},
            { "zirz", "application/vnd.zul"},
            { "zmm", "application/vnd.handheld-entertainment+xml"},
            { "amr", "audio/AMR"}
    };

    /**
     * 获取文件夹的大小
     *
     * @param fileFloder
     * @return
     */
    public static double getFloderSize(File fileFloder) {
        double floderSize = 0;
        File[] listFiles = fileFloder.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isDirectory()) {
                    floderSize += getFloderSize(listFiles[i]);
                } else {
                    floderSize += listFiles[i].length();
                }
            }
        }
        return floderSize;
    }

    /**
     * 只删除文件夹下的文件不删除文件夹
     *
     * @param fileFloder
     */
    public static void deleteFloderFile(File fileFloder) {
        File[] listFiles = fileFloder.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].isDirectory()) {
                deleteFloderFile(listFiles[i]);
            } else {
                listFiles[i].delete();
            }
        }
    }

//    /**
//     * 获取缓存的音频文件
//     *
//     * @param context
//     * @param audioCachePath
//     * @return
//     */
//    public static List<File> getAudioFiles(Context context, String audioCachePath) {
//        List<File> files = new ArrayList<File>();
//        DBStoreHelper helper = DBStoreHelper.getInstance(context);
//        List<ConversationMessage> messageList = helper.queryMessageListByMessageType(MXCacheManager.getInstance().getCurrentUser().getCurrentIdentity().getId(), ConversationMessage.MESSAGE_TYPE_VOICE);
//        for (ConversationMessage message : messageList) {
//            File file = new File(audioCachePath, message.getFile_id() + ".amr");
//            if (file.exists() && file.length() > 0) {
//                files.add(file);
//            }
//        }
//        return files;
//    }

//    /**
//     * 获取所有缓存音频文件的大小
//     *
//     * @param context
//     * @return
//     */
//    public static double getAudioFileSize(Context context) {
//        List<File> audioFiles = getAudioFiles(context, ConversationCacheUtil.getConversationVoiceTemp());
//        Double fileSize = 0.0;
//        for (File file : audioFiles) {
//            fileSize += file.length();
//        }
//        return fileSize;
//    }

//    /**
//     * 清理聊天中所有的缓存音频文件
//     */
//    public static void deleteAudioFiles(Context context) {
//        List<File> audioFiles = getAudioFiles(context, ConversationCacheUtil.getConversationVoiceTemp());
//        for (File file : audioFiles) {
//            file.delete();
//        }
//    }

    public static void writeToLocalFile(File file, InputStream in) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = in.read(buffer)) != -1) {
                fos.write(buffer);
            }
            in.close();
            fos.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

    }

    public static String loadFileContent(String path) {
        if (path.startsWith("file://")) {
            path = path.replace("file://", "");
        }
        StringBuilder builder;
        try {
            File file = new File(path);
            if (!file.exists()) {
                return "";
            }
            InputStream in = new FileInputStream(file);

            builder = new StringBuilder(in.available() + 10);

            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(in));
            char[] data = new char[2048];
            int len = -1;
            while ((len = localBufferedReader.read(data)) > 0) {
                builder.append(data, 0, len);
            }
            localBufferedReader.close();
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            return builder.toString();

        } catch (IOException e) {
        }

        return "";
    }

//    public static String getVideoFilePath(Context context, String videoPath) {
//        String filePath = null;
//        Uri uri = Uri.fromFile(new File(videoPath));
//        if (uri == null) {
//            return null;
//        }
//        final boolean isKitKat = Build.VERSION.SDK_INT >= 19;
//        Bitmap thumbailBitmap = null;
//        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
//            // ExternalStorageProvider
//            if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                if ("primary".equalsIgnoreCase(type)) {
//                    filePath = MXKit.getInstance().getKitConfiguration().getSdCardFolder() + "/" + split[1];
//                }
//            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
//
//                final String id = DocumentsContract.getDocumentId(uri);
//                final Uri contentUri = ContentUris
//                        .withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
//
//                filePath = WBSysUtils.getDataColumn(context, contentUri, null, null);
//            } else if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//
//                Uri contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//
//                final String selection = "_id=?";
//                final String[] selectionArgs = new String[]{split[1]};
//
//                filePath = WBSysUtils.getDataColumn(context, contentUri, selection, selectionArgs);
//            }
//        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            filePath = WBSysUtils.getDataColumn(context, uri, null, null);
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            filePath = uri.getPath();
//        }
//
//        if (filePath != null && !"".equals(filePath)) {
//            if (WBSysUtils.checkAttachementSize(context, filePath) != 0) {
//                int uploadFileMaxSize = WBSysUtils.uploadFileMaxSize();
//                WBSysUtils.toast(context, String.format(context.getResources().getString(R.string.mx_attachement_oversize), uploadFileMaxSize), Toast.LENGTH_SHORT, true);
//                return null;
//            }
//            thumbailBitmap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MINI_KIND);
//        }
//        if (thumbailBitmap == null) {
//            WBSysUtils.toast(context, context.getResources().getString(R.string.mx_toast_video_read_fail_can_not_send),
//                    Toast.LENGTH_SHORT);
//            return null;
//        }
//
//        return filePath;
//    }

    /**
     * 根据Uri获取图片文件的绝对路径
     */
    public static String getRealFilePath(Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static byte[] fileToBytes(File file) {
        if (file == null) {
            return null;
        }
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream(fis.available());
            byte[] b = new byte[fis.available()];
            int len = -1;
            while ((len = fis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            return bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static File bytesToFile(ArrayList<byte[]> pcmData, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            for (byte[] data : pcmData) {
                bos.write(data, 0, data.length);
            }
            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            bos.flush();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 读取asset目录下文件。
     *
     * @return 二进制文件数据
     */
    public static byte[] readAssetsFile(Context context, String filename) {
        try {
            InputStream ins = context.getAssets().open(filename);
            byte[] data = new byte[ins.available()];

            ins.read(data);
            ins.close();

            return data;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static String isFloderExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        return filePath;
    }

    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return file.exists();
    }

    public static <P> boolean exists(P file) {
        if (file == null) {
            return false;
        }
        final Class<?> clazz = file.getClass();
        if (clazz.isAssignableFrom(String.class)) {
            return new File((String) file).exists();
        } else if (clazz.isAssignableFrom(File.class)) {
//            Log.d("FileUtils", "((File) file).length():" + ((File) file).length());
            return ((File) file).exists();
        }
        return false;
    }

    public static boolean isGif(String path) {
        if (path == null) {
            throw new NullPointerException("path == null");
        }
        try {
            final FileInputStream fis = new FileInputStream(path);
            byte[] fileHeader = new byte[5];
            fis.read(fileHeader);
            fis.close();
            final String fileCode = bytesToHexString(fileHeader);
            if (TYPE_GIF.startsWith(fileCode)) {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    /**
     * 得到上传文件的文件头
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null) {
            throw new NullPointerException("byte[] src == null");
        }
        for (final byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
