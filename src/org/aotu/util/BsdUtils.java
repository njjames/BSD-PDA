package org.aotu.util;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;

import java.util.List;

public class BsdUtils {

    /**
     * 把权限的字符串转化为可以在in中使用的带单引号的字符串
     * 例如，公司权限为"01,02,", 转化之后就是 "'01','02'"
     * @param gnStr
     * @return
     */
    public static String gn2quote(String gnStr) {
        if (gnStr == null || gnStr.equals("")) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        String[] split = gnStr.split(",");
        for (int i = 0; i < split.length; i++) {
            sb.append("'").append(split[i]).append("'").append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


    public static void updateWorkPzGz(Dao dao, String work_no) {
        double xche_rgf = 0;
        double xche_clf = 0;
        Sql sql1_11 = Sqls
                .queryRecord("select sum(wxxm_je) xche_rgf from work_mx_gz where work_no='" + work_no + "' and wxxm_zt in ('正常','保险')");
        dao.execute(sql1_11);
        List<Record> resRgf = sql1_11.getList(Record.class);
//        if (resRgf != null && resRgf.size() > 0) {
        // 只需判断下面的条件既可，因为查询结果resRgf肯定不为null，并且size肯定是1
        if (resRgf.get(0).getString("xche_rgf") != null ) {
            xche_rgf = Double.parseDouble(resRgf.get(0).getString("xche_rgf"));
        }
        Sql sql1_1 = Sqls
                .create("update work_pz_gz set xche_rgf = " + xche_rgf + ",xche_rgbh = " + xche_rgf + ",xche_rgsl=0,xche_rgse=0 where work_no='" + work_no + "'");
        dao.execute(sql1_1);

        Sql sql1_22 = Sqls
                .queryRecord("select sum(peij_je) xche_clf from work_ll_gz where work_no='" + work_no + "' and peij_zt in ('正常','保险')");
        dao.execute(sql1_22);
        List<Record> resClf = sql1_22.getList(Record.class);
        if (resClf.get(0).getString("xche_clf") != null) {
            xche_clf = Double.parseDouble(resClf.get(0).getString("xche_clf"));
        }
        Sql sql1_2 = Sqls
                .create("update work_pz_gz set xche_clf = " + xche_clf + ",xche_clbh = " + xche_clf + ",xche_clsl=0,xche_clse=0 where work_no='" + work_no + "'");
        dao.execute(sql1_2);
        Sql sql1_3 = Sqls
                .create("update work_pz_gz set xche_yhje = isnull(xche_wxxm_yhje,0)+isnull(xche_peij_yhje,0) where work_no = '" + work_no + "'");
        dao.execute(sql1_3);
        Sql sql1_4 = Sqls
                .create("update work_pz_gz set xche_hjje= isnull(xche_rgf,0) + isnull(xche_clf,0) where work_no = '" + work_no + "'");
        dao.execute(sql1_4);
    }
}
