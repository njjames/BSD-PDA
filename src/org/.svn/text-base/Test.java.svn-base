package org;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;



public class Test {

	public static void main(String[] args) {

	    String mytext;
		try {
			mytext = java.net.URLEncoder.encode("沪A12345",   "GB2312");
			System.out.println(mytext);
		       String   mytext2   =   java.net.URLDecoder.decode(mytext,   "utf-8");     
	           
//		       这两条语句在同一个页面中的话,得到的结果是:     
//		       mytext:   %E4%B8%AD%E5%9B%BD       
//		       mytex2:   中国    
		        
		      String   zhongguo=new      String("中国".getBytes("iso8859_1"));     
		       zhongguo=java.net.URLDecoder.decode(zhongguo,"utf-8");    
		       
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}     
	}

	/**
	 * @param strBaseCipher
	 * @param Key
	 * @return
	 */
	static int jiemi(String strBaseCipher) {
		String Key = "lxp770426@tom.com";
		int count = 6;
		try {
			String strCipher = strBaseCipher.substring(count, strBaseCipher.length() );
			byte [] dataCipher = Base64.decodeBase64(strCipher.getBytes());
			String strPlain = new String(dataCipher,"UTF8");
			if (strPlain.indexOf(Key)>0)
            {
                strPlain = strPlain.replace(Key, "");
                int _r = 0;
                try {
					_r = Integer.parseInt(strPlain);
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
                return _r;
            }
            else
            {
                return 0;
            }
		} catch (Exception e) {

		}
		return 0;
	}
	
}
