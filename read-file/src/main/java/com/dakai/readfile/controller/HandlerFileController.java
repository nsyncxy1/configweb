package com.dakai.readfile.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.dakai.readfile.domain.AjaxResult;
import com.dakai.readfile.domain.CommandVo;
import com.dakai.readfile.domain.FileContentVo;
import com.dakai.readfile.domain.FileVo;
import com.dakai.readfile.utils.EncodingDetect;
import com.dakai.readfile.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/file")
@ConfigurationProperties(prefix = "dakai")
public class HandlerFileController {

    private String uploadPath;

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    private String[] paths;
    public void setPaths(String[] paths) {
        this.paths = paths;
    }


    //按行读取
    @PostMapping("/readline")
    public AjaxResult readLine(@RequestParam("path") String path) {
        StringBuilder sb = new StringBuilder();
        Stream<String> lines = null;

        String encode = EncodingDetect.getJavaEncode(path);

        if (StrUtil.isBlank(encode)) {
            return AjaxResult.error();
        }

        try {
//            inputStream = file.getInputStream();
//            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().forEach(sb::append);
            lines = Files.lines(Paths.get(path), Charset.forName(encode));
            lines.forEach(sb::append);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(lines);
        }
//        System.out.println("---->"+sb);
        return AjaxResult.success("success", sb.toString());
    }

    //读取整个文件
    @PostMapping("/read")
    public AjaxResult readFile(@RequestParam("path") String path) {

        String encode = EncodingDetect.getJavaEncode(path);

        File file = new File(path);
        Long filelength = file.length();
        if (filelength>1024*1024*3) {
            return AjaxResult.error("The file is too large. > 3M .");
        }

        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String s = new String(filecontent, encode);
            return AjaxResult.success("Read success.", s);
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            return AjaxResult.error("The OS does not support " + encode);
        }

    }

    @PostMapping("/write")
    public AjaxResult write(@RequestBody FileContentVo fileContentVo) {

        FileWriter writer = null;
        try {
            writer = new FileWriter(fileContentVo.getPath());
            writer.write(fileContentVo.getContent());
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(writer);
        }
        return AjaxResult.success("Write success.");
    }

    @GetMapping("/getDirectory")
    public AjaxResult directory() {


//        List<Map<String,List<FileVo>>> lists = new ArrayList<>();
        List<FileVo> list = new ArrayList<>();
        for (String path : paths) {

        File f = new File(path);//获取路径  F:\测试目录
        if (!f.exists()) {
            continue;
        }

            list.add(searchDir(f));



        /*File fa[] = f.listFiles();//用数组接收  F:\笔记总结\C#, F:\笔记总结\if语句.txt
        for (int i = 0; i < fa.length; i++) {//循环遍历
            File fs = fa[i];//获取数组中的第i个
            list.add(searchDir(fs));
        }
            Map<String,List<FileVo>> map = new HashMap<>();
            map.put(path, list);
            lists.add(map);*/
        }
        return AjaxResult.success(list);
    }




    @PostMapping("/command")
    public AjaxResult doCommand(@RequestBody CommandVo commandVo) {

   /*     String osName = System.getProperties().getProperty("os.name");
        System.out.println("Your OS is :"+osName);
        if (!("Linux".equals(osName))) {
            return AjaxResult.error("Os must be Linux.");
        }*/

        String command = commandVo.getCommand();
        boolean abc = StrUtil.containsAny(command, "rm");
        if (abc) {
            return AjaxResult.error("Contains illegal characters.");
        }


        StringBuilder sb = new StringBuilder();
//        sb.append("Run the command:").append(command).append("\n");
        BufferedReader br = null;
        try {
            //      logger.info("启用失败或删除wifi后删除连接信息:"+cmd);

//            List<String> commandList = new ArrayList<>();
//            commandList.add("/bin/sh");
//            commandList.add("-c");
//            commandList.add(command);
            Process p =  Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",command});
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            //   StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
                System.out.println("info--->"+line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorString = e.toString();
            System.out.println("err--->"+errorString);
        }finally {
            IoUtil.close(br);
        }

        return AjaxResult.success(sb.toString());
    }

    @PostMapping("/upload")
    public AjaxResult upload(MultipartFile file) {

        System.out.println(uploadPath);
        try {
            FileUploadUtils.upload(uploadPath, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return AjaxResult.success();
    }


    private FileVo searchDir(File fs) {
        List<FileVo> list = new ArrayList<>();
        String name = fs.getName();
        FileVo fileVo = new FileVo();
        fileVo.setName(name);
        fileVo.setPath(fs.getPath());
        if (fs.isDirectory()) {
            fileVo.setType(true);
            File fa[] = fs.listFiles();
            for (int i = 0; i < fa.length; i++) {//循环遍历
                File fs1 = fa[i];//获取数组中的第i个
                FileVo fileVo1 = searchDir(fs1);
                list.add(fileVo1);
            }
            fileVo.setChild(list);
//            System.out.println(fs.getName() + " [目录]");//如果是目录就输出
        } else {
            fileVo.setType(false);
//            System.out.println(fs.getName());//否则直接输出
        }
        return fileVo;
    }




}
