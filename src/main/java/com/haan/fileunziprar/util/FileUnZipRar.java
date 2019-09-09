package com.haan.fileunziprar.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUnZipRar {

    /**
     * 解压zip和rar文件
     * @param file 需解压文件
     * @param outfiledirname 解压后文件保存文件夹名称
     * @Param charseName 字节编码
     * @return 执行结果
     */
    public static String unZipRarToFile(File file, String outfiledirname ,String charseName)throws Exception {
        //若目标保存文件夹不存在,则新建文件夹
        File outfiledir = new File(outfiledirname);
        if (!outfiledir.exists()) {
            outfiledir.mkdirs();
        }
        //解压文件,判断压缩类型
        if (file.getName().endsWith(".zip")) {
            //执行zip解压
            unZipToFile(file, outfiledir,charseName);
        } else if (file.getName().endsWith(".rar")) {
            //执行rar解压
            unRarToFile(file, outfiledir);
        }else {
            return "请上传zip或rar文件";
        }
        return "success";
    }

    private static String unZipToFile(File file ,File outfiledir,String charsetName) {
        //获取文件名称
        String filename = file.getName();

        FileOutputStream out = null;
        InputStream in = null;
        ZipFile zipFileData = null;
        ZipFile zipFile = null;
        try {
            //设置若设定有编码方式则使用，否则使用默认utf-8
            if (charsetName != null && charsetName != "") {
                zipFile = new ZipFile(file.getPath(), Charset.forName(charsetName));
            } else {
                zipFile = new ZipFile(file.getPath(), Charset.forName("utf8"));
            }
            //压缩包内容
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            //处理创建文件夹
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String filePath = "";
                //获取压缩文件的第一层目录名称
                String parentDir = entry.getName().substring(0, entry.getName().indexOf("/"));

                //若目标文件夹为空，则默认保存在压缩包所在文件夹，若存在，保存在目标文件夹
                if (outfiledir == null) {
                    //更改一层文件夹名称
                    filePath = file.getParentFile().getPath() + File.separator + entry.getName().replace(parentDir,filename.substring(0, filename.length() - 4));
                    //无需更改一层文件夹名称
                    //filePath = file.getParentFile().getPath() + File.separator + entry.getName();
                } else {
                    //更改一层文件夹名称
                    filePath = outfiledir.getPath() + File.separator + entry.getName().replace(parentDir, filename.substring(0, filename.length() - 4));
                    //无需更改一层文件夹名称
                    //filePath = outfiledir.getPath() + File.separator + entry.getName();
                }
                System.out.println(filePath);

                File f = new File(filePath);
                /**
                 * 创建文件夹路径
                 */
                File parentFile = f.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
            }
            /**
             * 创建文件内容,如果是文件夹进行下一循环，若是文件进行读写
             */
            if (charsetName != null && charsetName != "") {
                zipFileData = new ZipFile(file.getPath(), Charset.forName(charsetName));
            } else {
                zipFileData = new ZipFile(file.getPath(), Charset.forName("utf8"));
            }
            Enumeration<? extends ZipEntry> entriesData = zipFileData.entries();

            while (entriesData.hasMoreElements()) {
                ZipEntry entry = entriesData.nextElement();
                in = zipFile.getInputStream(entry);
                String filePath = "";
                //获取压缩文件的第一层目录名称
                String parentDir = entry.getName().substring(0, entry.getName().indexOf("/"));
                //若目标文件夹为空，则默认保存在压缩包所在文件夹，若存在，保存在目标文件夹
                if (outfiledir == null) {
                    //更改一层文件夹名称
                    filePath = file.getParentFile().getPath() + File.separator + entry.getName().replace(parentDir, filename.substring(0, filename.length() - 4));
                    //无需更改一层文件夹名称
                    //filePath = file.getParentFile().getPath() + File.separator + entry.getName();
                } else {
                    //更改一层文件夹名称
                    filePath = outfiledir.getPath() + File.separator + entry.getName().replace(parentDir, filename.substring(0, filename.length() - 4));
                    //无需更改一层文件夹名称
                    //filePath = outfiledir.getPath() + File.separator + entry.getName();
                }
                File f = new File(filePath);
                if (f.isDirectory()) {
                    continue;
                }
                out = new FileOutputStream(filePath);
                int len = -1;
                byte[] bytes = new byte[1024];
                while ((len = in.read(bytes)) != -1) {
                    out.write(bytes, 0, len);
                }
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        } finally {
            try {
                out.close();
                in.close();
                zipFile.close();
                zipFileData.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "success";
        }
    }
    private static String unRarToFile(File file ,File outfiledir) {

        return "展示不提供rar文件解压";
    }
}
