package com.gfashion.data;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName User
 * @Description TODO
 * @Author zhangyuanbo
 * @Date 2020/05/18  23:30
 * @Version 1.0
 */
@Slf4j
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String username;
    String password;
}
