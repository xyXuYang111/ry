package com.ruoyi.web.controller.file;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.redis.redisRepository.RedisRepository;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.framework.web.domain.server.SysFile;
import com.ruoyi.system.code.QRCodeUtil;
import com.ruoyi.system.domain.SysFileInfo;
import com.ruoyi.system.domain.SysFileInfo;
import com.ruoyi.system.domain.SysFileType;
import com.ruoyi.system.domain.SysFileInfo;
import com.ruoyi.system.service.SysFileInfoService;
import com.ruoyi.system.service.SysFileTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/23 00:36
 * @Description:
 */
@Controller
@RequestMapping("/system/file")
@Slf4j
public class SysFileInfoController extends BaseController {

    private String prefix = "file";

    @Autowired
    private SysFileInfoService sysFileInfoService;

    @Autowired
    private SysFileTypeService sysFileTypeService;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final String redis_code = "FILE_REDIS";

    @RequiresPermissions("system:file:list")
    @GetMapping()
    public String operlog() {
        log.debug("登录主界面");
        return prefix + "/file";
    }

    @RequiresPermissions("system:file:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysFileInfo file)
    {
        List<SysFileInfo> list = sysFileInfoService.selectFileList(file);
        return getDataTable(list);
    }

    /**
     * 选择部门树
     */
    @GetMapping("/selectFileTree/{fileId}")
    public String selectDeptTree(@PathVariable("fileId") String fileId, ModelMap mmap)
    {
        SysFileInfo file = new SysFileInfo();
        file.setFileTypeId(fileId);
        mmap.put("file", sysFileInfoService.selectFileInfo(file));
        return prefix + "/tree";
    }

    /**
     * 新增部门
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        SysFileType fileInfo = sysFileTypeService.selectFileTypeById(1L);
        mmap.put("file", fileInfo);
        return prefix + "/add";
    }

    /**
     * 新增保存部门
     */
    @Log(title = "文件管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:file:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysFileInfo sysFile) throws Exception{
        sysFile.setCreateBy(ShiroUtils.getLoginName());
        sysFileInfoService.insertFile(sysFile);
        return toAjax(1);
    }

    @Log(title = "文件管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:file:add")
    @PostMapping("/uploadFile")
    @ResponseBody
    public AjaxResult uploadFile(SysFileInfo sysFile) throws Exception{
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setFileId(sysFile.getFileId());
        SysFileInfo sysFileInfo1 = sysFileInfoService.selectFileInfo(sysFileInfo);
        MultipartFile file = sysFile.getFile();
        if (file != null) {
            String path = "F:\\file\\" + file.getOriginalFilename();
            File newFile = new File(path);
            //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
            file.transferTo(newFile);
            sysFileInfo1.setFileUrl(path);
        }
        sysFileInfoService.updateFile(sysFileInfo1);
        return AjaxResult.success(sysFileInfo1.getFileUrl());
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{fileId}")
    public String edit(@PathVariable("fileId") String fileId, ModelMap mmap)
    {
        SysFileInfo file = new SysFileInfo();
        file.setFileId(fileId);
        SysFileInfo sysFile = sysFileInfoService.selectFileInfo(file);
        mmap.put("file", sysFile);
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @Log(title = "文件管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:file:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysFileInfo file)
    {
        file.setUpdateBy(ShiroUtils.getUserIdStr());
        sysFileInfoService.updateFile(file);
        return toAjax(1);
    }

    /**
     * 保存
     */
    @Log(title = "文件管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:file:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@Validated String ids)
    {
        SysFileInfo sysFile = new SysFileInfo();
        sysFile.setFileId(ids);
        sysFileInfoService.deleteFile(sysFile);
        return toAjax(1);
    }

    @Log(title = "文件管理", businessType = BusinessType.OTHER)
    @GetMapping(value="/downFile")
    public void downFile(HttpServletResponse response, @Validated String ids){

        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setFileId(ids);
        SysFileInfo sysFileInfo1 = sysFileInfoService.selectFileInfo(sysFileInfo);
        //本地生成二维码
        String fileUrl= sysFileInfo1.getFileUrl();
        if(fileUrl != null && fileUrl.trim().length() > 0){
            File file = new File(fileUrl);
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition",
                    "attachment;fileName=" + file.getName());// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @RequiresPermissions("system:file:syncRedis")
    @Log(title = "文件管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncRedis")
    @ResponseBody
    public AjaxResult syncRedis(@Validated String ids) {
        log.debug("账号同步");
        List<SysFileInfo> sysFileInfoList = sysFileInfoService.selectFileList(new SysFileInfo());
        //所有记录进行存储
        if(sysFileInfoList.size() > 0){
            RedisRepository.delete(redis_code);
            for(SysFileInfo sysFileInfo : sysFileInfoList){
                String str = JSON.toJSONString(sysFileInfo);
                RedisRepository.leftPush(redis_code, str);
            }
        }
        return toAjax(1);
    }

    @RequiresPermissions("system:file:syncMongo")
    @Log(title = "文件管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncMongo")
    @ResponseBody
    public AjaxResult syncMongo(@Validated String ids) {
        log.debug("账号同步");
        if(ids.trim().length() == 0){
            List<SysFileInfo> sysFileInfoList = sysFileInfoService.selectFileList(new SysFileInfo());
            //所有记录进行存储
            if(sysFileInfoList.size() > 0){
                for(SysFileInfo sysFileInfo : sysFileInfoList){
                    mongoTemplate.remove(sysFileInfo);
                    mongoTemplate.insert(sysFileInfo);
                }
            }
        }else{
            String[] fileIdArray = ids.split(",");
            for(String fileId : fileIdArray){
                SysFileInfo sysFileInfoInfo = new SysFileInfo();
                sysFileInfoInfo.setFileId(fileId);

                SysFileInfo sysFileInfo = sysFileInfoService.selectFileInfo(sysFileInfoInfo);
                if(sysFileInfo != null){
                    mongoTemplate.remove(sysFileInfo);
                    mongoTemplate.insert(sysFileInfo);
                }
            }
        }
        return toAjax(1);
    }

    @Log(title = "文件管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:file:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysFileInfo operLog)
    {
        List<SysFileInfo> list = sysFileInfoService.selectFileList(operLog);
        ExcelUtil<SysFileInfo> util = new ExcelUtil<SysFileInfo>(SysFileInfo.class);
        return util.exportExcel(list, "文件管理");
    }
}
