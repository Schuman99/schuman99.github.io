package com.general.common.controller;

import com.general.common.enums.AttachTypeEnum;
import com.general.core.msg.AjaxResult;
import com.general.core.msg.MessageCode;
import com.general.core.util.*;
import com.general.logic.base.dto.BaseAttachDTO;
import com.general.logic.base.service.BaseAttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author ysj
 * @create 2018-05-15
 **/
@RestController
@RequestMapping(value = "/api/file")
public class FileController {

    @Autowired
    private FilePathConfig filePathConfig;
    @Autowired
    private BaseAttachService baseAttachService;

    /**
     * 上传文件
     */
    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult uploadAvatar(MultipartFile file, HttpServletRequest request) {
        String userId = (String) request.getParameter("userId");
        String folder = (String) request.getParameter("folder");
        AjaxResult result = null;
        String fileName = file.getOriginalFilename();
        String newFileName = generateFileName(fileName);
        // 获取配置文件中上传路径，如果为空，则从request中获取路径
        String uploadPath = filePathConfig.getUploadPath() != null ? filePathConfig.getUploadPath() : request.getServletContext().getRealPath("");
        String filePath = "/files/file/" + userId + "/" + "avatar" + "/";
        isExists(uploadPath , filePath + newFileName);
        File newFile = new File(uploadPath + filePath + newFileName);
        try {
            FileUtils.inputstreamtofile(file.getInputStream(),newFile);
            BaseAttachDTO baseAttach = new BaseAttachDTO();
            baseAttach.setAttachName(newFile.getName());
            baseAttach.setAttachPath(filePath);
            baseAttach.setAttachType(AttachTypeEnum.TOU_XIANG.toString());
            baseAttach = baseAttachService.saveByDTO(baseAttach);
            result = AjaxResult.createSuccessResult(baseAttach);
        } catch (IOException e) {
            e.printStackTrace();
            result = AjaxResult.createErrorResult(MessageCode.UPDATE_FAILED);
        }
        return result;
    }

    /**
     * 上传文件
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file, HttpServletRequest request) {
        String userId = (String) request.getParameter("userId");
        String folder = (String) request.getParameter("folder");
        AjaxResult result = null;
        String fileName = file.getOriginalFilename();
        String newFileName = generateFileName(fileName);
        // 获取配置文件中上传路径，如果为空，则从request中获取路径
        String uploadPath = filePathConfig.getUploadPath() != null ? filePathConfig.getUploadPath() : request.getServletContext().getRealPath("");
        String filePath = "/files/file/" + userId + "/" + folder + "/";
        isExists(uploadPath , filePath + newFileName);
        File newFile = new File(uploadPath + filePath + newFileName);
        try {
            FileUtils.inputstreamtofile(file.getInputStream(),newFile);
            BaseAttachDTO baseAttach = new BaseAttachDTO();
            baseAttach.setAttachName(newFile.getName());
            baseAttach.setAttachPath(filePath);
            baseAttach.setAttachType(AttachTypeEnum.FU_JIAN.toString());
            baseAttach = baseAttachService.saveByDTO(baseAttach);
            result = AjaxResult.createSuccessResult(baseAttach);
        } catch (IOException e) {
            e.printStackTrace();
            result = AjaxResult.createErrorResult(MessageCode.UPDATE_FAILED);
        }
        return result;
    }

    /**
     * 下载文件
     */
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    public void downloadFile(String fileName, String filePath, HttpServletRequest request, HttpServletResponse response) {
//		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("gbk"),"iso-8859-1"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
//		response.setContentType("multipart/form-data;charset=UTF-8");
        response.setContentType("application/octet-stream;charset=UTF-8");
//		response.setCharacterEncoding("UTF-8");
        try {
//			String path = request.getServletContext().getRealPath(filePath);
            String path = filePathConfig.getUploadPath() != null ? filePathConfig.getUploadPath() : request.getServletContext().getRealPath("");
            path += filePath;
            InputStream inputStream = new FileInputStream(new File(path));
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }

            // 这里主要关闭。
            os.close();

            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除附件
     */
    @RequestMapping(value = "/deleteFile")
    public AjaxResult deleteFile(@RequestBody BaseAttachDTO baseAttachDTO, HttpServletRequest request) {
        AjaxResult result = null;
        int res = baseAttachService.delete(baseAttachDTO);
        if (res > 0) {
            result = AjaxResult.createSuccessResultWithCode(MessageCode.OPERATE_SUCCESS, "删除附件成功！");
        } else {
            result = AjaxResult.createErrorResult(MessageCode.OPERATE_FAILED, "删除附件失败！");
        }
        return result;
    }

    /**
     * 保存 base64附件
     */
    @RequestMapping(value = "/saveFile")
    public AjaxResult saveFile(@RequestBody BaseAttachDTO baseAttachDTO, HttpServletRequest request) {
        AjaxResult result = null;
        baseAttachDTO = baseAttachService.saveByDTO(baseAttachDTO);
        if (baseAttachDTO != null) {
            result =AjaxResult.createSuccessResultWithCode(MessageCode.SAVE_SUCCESS,baseAttachDTO);
        } else {
            result = AjaxResult.createErrorResult(MessageCode.SAVE_SUCCESS, "保存附件失败！");
        }
        return result;
    }

    public String generateFileName(String fileName) {
        String suffix = "";
        int index = fileName.indexOf(".");
        if (index > 0) {
            suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        return String.valueOf(System.currentTimeMillis()) + suffix;
    }

    /**
     * 判断文件是否存在
     */
    private void isExists (String uploadPath, String path) {
        File file = new File(uploadPath + path);
        if (path.indexOf(".") == -1) {
            if (!file.exists()){
                file.mkdirs();
            }
        } else {
            path = path.substring(0,path.lastIndexOf("/"));
            isExists(uploadPath,path);
        }
//        if (file.isDirectory() || path.indexOf(".") == -1){
//            if(!file.exists()){
//                file.mkdirs();
//                return;
//            }
//        } else {
//            path = path.substring(0,path.lastIndexOf("/"));
//            isExists(uploadPath,path);
//        }
    }

    @PostMapping("/saveBase64")
    @ResponseBody
    public AjaxResult getParams(@RequestBody BaseAttachDTO baseAttachDTO) {
        BaseAttachDTO dto = base64ToImg(baseAttachDTO);
        dto = baseAttachService.saveByDTO(dto);
//        System.out.println("转化完成");
        return AjaxResult.createSuccessResult(dto);
    }

    private  BaseAttachDTO base64ToImg(BaseAttachDTO dto) {
        String userId = UserUtils.getLoginAccountDTO().getId();
        String folder = "photo";
        String suffix = "." + dto.getAttachPath().split(";")[0].split("/")[1];
        String fileName =  "test1" + suffix;
        String newFileName = generateFileName(fileName);
        dto.setAttachName(newFileName);
        // 获取配置文件中上传路径
        String uploadPath =  ApplicationContextUtils.getBean(FilePathConfig.class).getUploadPath();//filePathConfig.getUploadPath() != null ? filePathConfig.getUploadPath() : request.getServletContext().getRealPath("");
        String filePath = "/files/file/" + userId + "/" + folder + "/";
        isExists(uploadPath, filePath + newFileName);
        String imgPath = uploadPath + filePath + newFileName;
//        data:image/jpeg;base64,/9j/4RwlRXhpZgAASUkqAAgAAAA

        String base64 =dto.getAttachPath().split(",")[1];
        Boolean flag = ImageUtils.Base64ToImage(base64,imgPath);
        if (flag) {
            dto.setAttachPath(filePath);
        }
        return dto;
    }

    @GetMapping(value = "/getVideos")
    public void getVideos(String videoPath ,HttpServletRequest request, HttpServletResponse response)
    {
//        /files/file/dahua/video/1542699906776warn.mp4
        System.out.println("查看路径" + videoPath);
        try {
            String uploadPath = filePathConfig.getUploadPath() != null ? filePathConfig.getUploadPath() : request.getServletContext().getRealPath("");
            String filePath =  videoPath; // "/files/file/dahua/" + "video" + "/" + "aa.ogg" ;
            File mp4 = new File(uploadPath + filePath);
            System.out.println(mp4.exists());
            InputStream inputStream =  new FileInputStream(mp4);
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            // 这里主要关闭。
            os.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
