package org.aotu;

import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.SessionBy;
import org.nutz.mvc.impl.session.NopSessionProvider;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

@Localization(value = "msg/", defaultLocalizationKey = "zh-CN")
@Fail("jsp:jsp.500")
@Ok("json:full")
@IocBy(type = ComboIocProvider.class,
    args = {"*js","ioc/",
        "*anno","org.aotu",
        "*tx",
        "*async"})
@SessionBy(NopSessionProvider.class)
@Filters()
public class MainModule {


}


   


