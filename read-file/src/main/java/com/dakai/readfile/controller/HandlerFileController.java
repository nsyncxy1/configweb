package com.dakai.readfile.controller;

import cn.hutool.core.io.IoUtil;
import com.dakai.readfile.domain.AjaxResult;
import com.dakai.readfile.domain.FileVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/file")
public class HandlerFileController {

    @Value("${dakai.path}")
    private String path;

    @PostMapping("/read")
    public AjaxResult readFile(@RequestParam("file") MultipartFile file) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().forEach(sb::append);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(inputStream);
        }
        return AjaxResult.success("success", sb);
    }

    @PostMapping("/write")
    public AjaxResult write(@RequestParam("content") String content, @RequestParam("path") String path) {

        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            writer.write(content);
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(writer);
        }
        return AjaxResult.success();
    }

    @GetMapping("/getDirectory")
    public AjaxResult directory() {

        File f = new File(path);//获取路径  F:\测试目录
        if (!f.exists()) {
            return AjaxResult.error(path + " not exists");
        }

        List<FileVo> list = new ArrayList<>();

        File fa[] = f.listFiles();//用数组接收  F:\笔记总结\C#, F:\笔记总结\if语句.txt
        for (int i = 0; i < fa.length; i++) {//循环遍历
            File fs = fa[i];//获取数组中的第i个
            list.add(searchDir(fs));
        }

        return AjaxResult.success(list);
    }

    private FileVo searchDir(File fs) {
        List<FileVo> list = new ArrayList<>();
        String name = fs.getName();
        FileVo fileVo = new FileVo();
        fileVo.setName(name);
        fileVo.setFullName(fs.getPath());
        if (fs.isDirectory()) {
            fileVo.setType(true);
            File fa[] = fs.listFiles();
            for (int i = 0; i < fa.length; i++) {//循环遍历
                File fs1 = fa[i];//获取数组中的第i个
                FileVo fileVo1 = searchDir(fs1);
                list.add(fileVo1);
            }
            fileVo.setChild(list);
            System.out.println(fs.getName() + " [目录]");//如果是目录就输出
        } else {
            fileVo.setType(false);
            System.out.println(fs.getName());//否则直接输出
        }
        return fileVo;
    }
}
