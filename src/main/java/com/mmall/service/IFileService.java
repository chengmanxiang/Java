package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by johncheng on 2017/9/14.
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
