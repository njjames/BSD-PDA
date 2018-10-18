package org.aotu.login.module;

import org.aotu.Jsons;
import org.aotu.login.entity.loginEntity;
import org.aotu.user.entity.userEntity;
import org.apache.commons.codec.binary.Base64;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Title :loginModule Package :org.aotu.login.module
 *
 * @author ZhangYaLong
 * @version V1.0
 * @Description: <登陆>
 * @date 2017年4月12日下午1:15:42
 */

@IocBean
@At("/login")
public class loginModule {
    @Inject
    Dao dao;

    @Inject
    Jsons jsons;

    /**
     * 登录
     * @param req
     * @param session
     * @param sid
     * @return
     */
    @At("/")
    @Ok("raw:json")
    public String login(HttpServletRequest req, HttpSession session, String sid) {
        String userName = req.getParameter("name");
        String psd = req.getParameter("psd");
        // boolean result=false;//登录成功与否的结果。默认为false
        userEntity user = new userEntity();
        if (userName != "") {
            user = dao.fetch(
                    userEntity.class,
                    Cnd.where("caozuoyuan_xm", "=", userName).
                            and("caozuoyuan_password", "=", psd));
            if (user != null) {
                // ///////////////////新增业务////////////////////////////////////////////
                Sql sql1 = Sqls
                        .queryRecord("select sys_ydbgnum,sys_flag_huowei,isnull(flag_pad_overtime,'') as flag_pad_overtime  from sm_system_info");
                dao.execute(sql1);
                List<Record> res1 = sql1.getList(Record.class);
                String sys_ydbgnum = res1.get(0).getString("sys_ydbgnum");
                String sys_flag_huowei = res1.get(0).getString("sys_flag_huowei");
                String flag_pad_overtime = res1.get(0).getString("flag_pad_overtime");
                if (flag_pad_overtime.indexOf("已过试用期") > -1) {
                    return jsons.json(1, 1, 0, flag_pad_overtime);
                }
                String json = "";
                if (sys_ydbgnum == null) {
                    Sql sql99 = Sqls
                            .queryRecord("select sum(RecCount) as shu from ( select count(*) as RecCount from jinhuo_pz_zj union select count(*) from xiaosh_pz_sj ) a ");
                    dao.execute(sql99);
                    List<Record> res99 = sql99.getList(Record.class);
                    int shu = res99.get(0).getInt("shu");
                    if (shu > 200) {
                        return jsons.json(1, 1, 0, "登录失败，到期,请在服务器上插加密狗");
                    }
                } else {
                    System.out.println("===================" + sys_ydbgnum);
                    int _rr = jiemi(sys_ydbgnum);
                    if (_rr < 0) {
                        return jsons.json(1, 1, 0, "登录失败，加密算法值错误");
                    }

                    Sql sql0 = Sqls.queryRecord("select * from sm_HardDisk_No_pingban where HardDisk_No_app='" + sid + "'");
                    dao.execute(sql0);
                    List<Record> res0 = sql0.getList(Record.class);
                    System.out.println("=res0.size()=" + res0.size()
                            + "=====_rr=" + _rr + "==================");
                    if (res0.size() < 1)
                        return jsons.json(1, 1, 0, "登录失败，设备未注册");
                    if (res0.size() > _rr)  //这里应该是大于，如果是小于永远都登不进去了
                        return jsons.json(1, 1, 0, "登录失败，设备注册数量超出");

                }
                ///////////////////////////////////////////////////////////////////////
                String[] qx = sys_flag_huowei.split(",");
                String czy_qx = user.getCaozuoyuan_Gndm();
                String caozuoyuanqux = user.getCaozuoyuan_Gndm();
                for (String xxxx : qx) {
                    if (caozuoyuanqux.indexOf(xxxx) > 0) { // danw_dz和Caozuoyuan_Gndm相差就就是sys_flag_huowei，不知道为什么使用danw_dz这个字段
                        caozuoyuanqux = caozuoyuanqux.replace(xxxx + ",", "");
                    }
                }
                System.out.println("===" + user.getCaozuoyuan_Gndm());
                // /////////////////////////////////////////////////////////////////////
                loginEntity gs = dao.fetch(loginEntity.class, Cnd.where("GongSiNo", "=", user.getGongSiNo()));
                user.setDanw_dz(caozuoyuanqux);
                user.setDanw_dh(gs.getDanw_dh());
                session.setAttribute("user", user);// 将该user保存到session中。 }
                json = Json.toJson(user, JsonFormat.full());
                System.out.println(json);
                return jsons.json(1, 1, 1, json);
            }
        }
        return jsons.json(1, 1, 0, "账号或密码错误，请重新输入！");
    }

    /**
     * @param strBaseCipher
     * @return
     */
    private static int jiemi(String strBaseCipher) {
        String Key = "lxp770426@tom.com";
        int count = 6;
        try {
            String strCipher = strBaseCipher.substring(count, strBaseCipher.length());
            byte[] dataCipher = Base64.decodeBase64(strCipher.getBytes());
            String strPlain = new String(dataCipher, "UTF8");
            if (strPlain.indexOf(Key) > 0) {
                strPlain = strPlain.replace(Key, "");
                int _r = 0;
                try {
                    _r = Integer.parseInt(strPlain);
                } catch (Exception e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
                return _r;
            } else {
                return 0;
            }
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * @return
     * @throws Exception
     */
    @At("/gongsi")
    @Ok("raw:json")
    public String gong() throws Exception {
        //动态连接
		/*SimpleDataSource dataSource = new SimpleDataSource();
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setJdbcUrl("jdbc:sqlserver://localhost:1433; DatabaseName=qpqxcss001");
		dataSource.setUsername("sa");
		dataSource.setPassword("sa");*/

        // 创建一个NutDao实例,在真实项目中, NutDao通常由ioc托管, 使用注入的方式获得.
        //Dao dao = new NutDao(dataSource);
        List<loginEntity> gong = dao.query(loginEntity.class, null);
        String js = Json.toJson(gong, JsonFormat.compact());
        if (gong.size() != 0) {
            return jsons.json(1, gong.size(), 1, js);
        }
        return jsons.json(1, gong.size(), 0, js);

    }
}
