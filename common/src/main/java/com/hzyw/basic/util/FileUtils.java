package com.hzyw.basic.util;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.hzyw.basic.config.Properties;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * @author haoyuan
 * date 2019.08.07
 */
@Slf4j
@Component
public class FileUtils {

    @SneakyThrows
    public static String checkDirectory(String directory)  {
        File screenshotDirectory = new File(directory);
        /*Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
        perms.add(PosixFilePermission.OWNER_READ);//设置所有者的读取权限
        perms.add(PosixFilePermission.OWNER_WRITE);//设置所有者的写权限
        perms.add(PosixFilePermission.OWNER_EXECUTE);//设置所有者的执行权限
        perms.add(PosixFilePermission.GROUP_READ);//设置组的读取权限
        perms.add(PosixFilePermission.GROUP_EXECUTE);//设置组的读取权限
        perms.add(PosixFilePermission.OTHERS_READ);//设置其他的读取权限
        perms.add(PosixFilePermission.OTHERS_EXECUTE);//设置其他的读取权限
        perms.add();
        Files.setPosixFilePermissions(Paths.get(screenshotDirectory.getParentFile().getAbsolutePath()),perms);*/
        if (!screenshotDirectory.exists()) {
            if (!screenshotDirectory.mkdirs()) {
                directory = System.getProperty("java.io.tmpdir");
                UserPrincipal owner = screenshotDirectory.toPath()
                        .getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName(Properties.PRINCIPAL_NAME);
                Files.setAttribute(screenshotDirectory.toPath(), "owner:owner", owner);
            }
        }

        return directory;
    }

    public static String getFileName(String methodName,String fileExtension) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random random = new Random(10000);
        if(StringUtils.isEmpty(fileExtension)){
            fileExtension=".png";
        }
        return methodName+"_" + sdf.format( new Date()) + "_" + random.nextInt(10000) + fileExtension;
    }


}

