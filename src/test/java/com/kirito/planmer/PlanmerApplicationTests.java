package com.kirito.planmer;

import com.kirito.planmer.util.PLTokenUtil;
import com.kirito.planmer.util.ShareCodeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanmerApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(ShareCodeUtil.idToCode(50000));

    }

}
