var ioc = {
	conf : {
		type : "org.nutz.ioc.impl.PropertiesProxy",
		fields : {
			paths : [ "coustom/db.properties" ]
		}
	},
	dataSource : {
		type : "com.alibaba.druid.pool.DruidDataSource",
		events : {
			create : "init",
			depose : 'close'
		},
		 fields : {
             url : {java:"$conf.get('db.url')"},
             username : {java:"$conf.get('db.username')"},
             password : {java:"$conf.get('db.password')"},
             driverClassName : {java:"$conf.get('db.driverClassName')"},
             testWhileIdle : true,
             validationQuery : {java:"$conf.get('db.validationQuery')"},
             maxActive : {java:"$conf.get('db.maxActive')"},
             //druid监控
             filters : "mergeStat",
             connectionProperties : "druid.stat.slowSqlMillis=2000"
         }
	},
	dao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [ {
			refer : "dataSource"
		} ]
	},
	tmpFilePool : {
	    type : 'org.nutz.filepool.NutFilePool',
	    // 临时文件最大个数为 1000 个
	    args : [ "~/BSDImage", 1000 ]   
	},
	uploadFileContext : {
	    type : 'org.nutz.mvc.upload.UploadingContext',
	    singleton : false,
	    args : [ { refer : 'tmpFilePool' } ],
	    fields : {
	        // 是否忽略空文件, 默认为 false
	        ignoreNull : true,
	        // 单个文件最大尺寸(大约的值，单位为字节，即 1048576 为 1M)
	        maxFileSize : 10485760,
	        // 正则表达式匹配可以支持的文件名
	        nameFilter : '^(.+[.])(jpg)$' 
	    } 
	},
	myUpload : {
	    type : 'org.nutz.mvc.upload.UploadAdaptor',
	    singleton : false,
	    args : [ { refer : 'uploadFileContext' } ] 
	}
};


