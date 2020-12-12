package com.micheal.mute.cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/23 19:51
 * @Description 文件信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo implements Serializable {
    /**
     * 文件路径
     */
    private String path;
}
