package com.example.dictionary.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.dictionary.base.model.PageInfo;
import com.example.dictionary.base.service.AbstractBaseService;
import com.example.dictionary.mapper.UserMapper;
import com.example.dictionary.model.dataobject.UserDo;
import com.example.dictionary.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl extends AbstractBaseService<UserMapper, UserDo> implements UserService {

    private UserMapper userMapper;

    @Override
    public PageInfo<UserDo> qryUserList(int pageIndex, int pageSize) {
        Page<UserDo> page = new Page<>();
        page.setRecords(userMapper.qryUserList(page));
        return formatPageInfo(page);
    }

    @Override
    public List<UserDo> selectUserList() {
        return userMapper.selectUserList();
    }

    private byte[] createSalt(){
        byte[] salt = new byte[16];
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.nextBytes(salt);
            return salt;
        } catch (Exception e) {
            return null;
        }
    }

    private String getBytes(String salt, String password){
        try {
            MessageDigest msgDigest = MessageDigest.getInstance("MD5");
            if (StringUtils.isNotBlank(salt)){
                msgDigest.update(salt.getBytes());
            }
            byte[] digest = msgDigest.digest(password.getBytes());
            return bytesToHex(digest);
        } catch (Exception e) {
            return null;
        }
    }

    private String bytesToHex(byte[] bytes){
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];
            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }
}
