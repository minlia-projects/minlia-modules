package com.minlia.module.riskcontrol;

import com.minlia.cloud.constant.SymbolConstants;
import org.junit.Test;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/7/12 1:44 PM
 */
public class IpListTests {

    @Test
    public void list() {

        System.out.println(false && false);

        String ip = "127.255.255.255";
        String[] ips = ip.split(SymbolConstants.DOT_ZY);
        Long number = Long.valueOf(ips[0]) * (256 * 256 * 256) + Long.valueOf(ips[1]) * (256 * 256) + Long.valueOf(ips[2]) * (256) + Long.valueOf(ips[3]);
        System.out.println(number);

//        X=161 X（256*256*256）+132 X（256*256）+13 X（256）+1=2709785857

    }

}

//3232235520
//3232301055


//2130706433
//2147483647