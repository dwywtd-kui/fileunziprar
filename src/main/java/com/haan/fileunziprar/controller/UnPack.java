package com.haan.fileunziprar.controller;

import com.haan.fileunziprar.util.FileUnZipRar;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class UnPack {

    @RequestMapping("/unpack")
    public String unpack(String file,String savepath) throws Exception {
        File ziprarfile=new File(file);
        String result = FileUnZipRar.unZipRarToFile(ziprarfile, savepath, "GBK");
        return result;
    }
}
