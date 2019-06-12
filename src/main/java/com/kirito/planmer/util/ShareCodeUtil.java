package com.kirito.planmer.util;


/**
 *
 * @author zen.wang zenyes@gmail.com
 */
public class ShareCodeUtil {

    /** 自定义进制(0,1没有加入,容易与o,l混淆) */
    private static final char[] r=new char[]{'F', 'L', 'G', 'W', '5', 'X', 'C', '3', '9', 'Z', 'M', '6', '7', 'Y', 'R', 'T', '2', 'H', 'S', '8', 'D', 'V', 'E', 'J', '4', 'K', 'Q', 'P', 'U', 'A', 'N', 'B'};

    /** 进制长度 */
    private static final int binLen=r.length;


    //    private static final long startNumber = 1048576L;
    private static final int startNumber = 0;


    /**
     *
     * @param id ID
     * @return 随机码
     */
    public static String idToCode(int id,int costomStartNumber) {
        if(costomStartNumber<0){
            costomStartNumber = startNumber;
        }
        id += costomStartNumber;
        char[] buf=new char[32];
        int charPos=32;

        while((id / binLen) > 0) {
            int ind=(int)(id % binLen);
            // System.out.println(num + "-->" + ind);
            buf[--charPos]=r[ind];
            id /= binLen;
        }
        buf[--charPos]=r[(int)(id % binLen)];
        // System.out.println(num + "-->" + num % binLen);
        String str=new String(buf, charPos, (32 - charPos));
        return str.toUpperCase();
    }
    public static String idToCode(int idL){
        return idToCode(idL,-1);
    }
    public static String idToCode(String id){
        int idL = Integer.parseInt(id);
        return idToCode(idL,-1);
    }
    public static String idToCode(String id,int costomStartNumber){
        int idL = Integer.parseInt(id);
        return idToCode(id,costomStartNumber);
    }


    public static int codeToId(String code) {
        code = code.toUpperCase();
        char chs[]=code.toCharArray();
        int res=0;
        for(int i=0; i < chs.length; i++) {
            int ind=0;
            for(int j=0; j < binLen; j++) {
                if(chs[i] == r[j]) {
                    ind=j;
                    break;
                }
            }
            if(i > 0) {
                res=res * binLen + ind;
            } else {
                res=ind;
            }
//             logger.debug(ind + "-->" + res);

        }
        res -= startNumber;
        return res;
    }

}