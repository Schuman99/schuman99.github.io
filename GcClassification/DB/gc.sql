create DATABASE gcsql;


--------顶层数据表(可回收、有害、易腐、其他)-----------------------------
drop table if EXISTS gc_top_category ;
create table gc_top_category(
	id INT PRIMARY key auto_increment,
	gc_id INT UNIQUE key,
	gc_name VARCHAR(10) not null,
	link_count int
);

insert into gc_top_category(id,gc_id,gc_name) VALUES(1,0,'可回收垃圾');
insert into gc_top_category(id,gc_id,gc_name) VALUES(2,1,'有害垃圾');
insert into gc_top_category(id,gc_id,gc_name) VALUES(3,2,'易腐垃圾');
insert into gc_top_category(id,gc_id,gc_name) VALUES(4,3,'其他垃圾');


select * from gcsql.gc_top_category;


-----------------二级目录------------------------------
drop table IF EXISTS gc_second_category ;
create table gc_second_category(
	id INT PRIMARY key auto_increment ,
	gc_id varchar(10)	UNIQUE key,
	gc_name VARCHAR(10) not null,
	gc_parent_id INT,
	CONSTRAINT gc_id_fk FOREIGN key(gc_parent_id) REFERENCES gc_top_category(gc_id)
);

--------------有害-------------------
insert into gc_second_category(gc_id,gc_name,gc_parent_id) VALUES('00','电池类',0);
insert into gc_second_category(gc_id,gc_name,gc_parent_id) VALUES('01','灯管类',0);
insert into gc_second_category(gc_id,gc_name,gc_parent_id) VALUES('02','家用化学品类',0);
insert into gc_second_category(gc_id,gc_name,gc_parent_id) VALUES('03','其他类',0);
---------------可回收---------------------''
insert into gc_second_category(gc_id,gc_name,gc_parent_id) VALUES('10','废金属类',1);
insert into gc_second_category(gc_id,gc_name,gc_parent_id) VALUES('11','废玻璃类',1);
insert into gc_second_category(gc_id,gc_name,gc_parent_id) VALUES('12','废塑料类',1);
insert into gc_second_category(gc_id,gc_name,gc_parent_id) VALUES('13','废纸类',1);
insert into gc_second_category(gc_id,gc_name,gc_parent_id) VALUES('14','废织物类',1);
insert into gc_second_category(gc_id,gc_name,gc_parent_id) VALUES('15','废旧电器电子产品',1);

---------------易腐---------------------
insert into gc_second_category(gc_id,gc_name,gc_parent_id) VALUES('20','其他类',2);

---------------其他垃圾-----------------
insert into gc_second_category(gc_id,gc_name,gc_parent_id) VALUES('30','其他类',3);

select * from gc_second_category;


-------------------详细目录---------------------------
drop table IF EXISTS gc_detail;
create table gc_detail(
	id INT PRIMARY key auto_increment,
	gc_id VARCHAR(10) UNIQUE key,
	gc_name VARCHAR(15) not null,
	link_count int,
	gc_parent_id VARCHAR(10),
	CONSTRAINT gc_id_detail_fk FOREIGN key(gc_parent_id) REFERENCES gc_second_category(gc_id)
);
-------------有害>>电池类00--------------------------------------
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0000','废铅酸蓄电池','00');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0001','废铅酸蓄电池','00');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0002','氧化汞电池','00');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0003','纽扣电池','00');
update gc_detail set gc_name = '镍镉电池' where gc_id = '0001';
-------------有害>>灯管类01--------------------------------------
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0100','荧光灯','01');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0101','节能灯','01');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0102','卤素灯','01');
-------------有害>>家用化学品类02--------------------------------
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0200','过期药物及其包装','02');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0201','药丸片剂','02');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0202','体温表','02');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0203','温度计(含汞)','02');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0204','血压计(含汞)','02');
-------------有害>>其他类03--------------------------------------
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0300','染发剂','03');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0301','过期化妆品','03');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0302','指甲油','03');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0303','洗甲水','03');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0304','漂白剂','03');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0305','杀菌剂','03');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0306','废胶片','03');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0307','相纸·X光片','03');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0308','感光胶片','03');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0309','硒鼓墨盒及其包装物','03');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0310','废油漆桶','03');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0311','溶剂及其包装物','03');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0312','废杀虫剂·老鼠药','03');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('0313','消毒剂及其包装','03');

-------------可回收物>>废金属类 10---------------------------------------------------------
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1000','易拉罐','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1001','哑铃','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1002','煤气罐','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1003','螺丝','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1004','铁钉','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1005','钥匙','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1006','滑板车','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1007','废锁头','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1008','金属托盘','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1009','铝箔','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1010','铁皮','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1011','刀具','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1012','剪刀','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1013','厨房用锅','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1014','金属容器','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1015','自行车[车铃/车篮]','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1016','金属瓶盖','10');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1017','金属衣架','10');

-------------可回收物>>废玻璃类 11---------------------------------------------------------
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1100','搪瓷制品','11');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1101','放大镜','11');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1102','窗玻璃','11');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1103','酒瓶','11');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1104','牛奶瓶','11');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1105','平板玻璃','11');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1106','玻璃杯','11');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1107','玻璃盒','11');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1108','玻璃瓶','11');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1109','玻璃工艺品','11');
-------------可回收物>>废塑料类 12---------------------------------------------------------
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1200','乳液罐','12');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1201','塑料盘','12');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1202','塑料衣架','12');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1203','泡沫','12');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1204','塑料盆','12');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1205','食品保鲜盒','12');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1206','洗发水瓶','12');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1207','饮料瓶','12');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1208','塑料桶','12');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1209','塑料玩具','12');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1210','PE塑料','12');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1211','收纳盒','12');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1212','施工安全帽','12');
-------------可回收物>>废纸类 13---------------------------------------------------------
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1300','日历','13');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1301','报纸','13');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1302','旧书','13');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1303','牛奶盒','13');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1304','宣传单','13');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1305','纸袋','13');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1306','杂志','13');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1307','作业本','13');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1308','硬板纸箱','13');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1309','纸质快递包装盒','13');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1310','纸盒','13');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1311','信封','13');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1312','纸塑铝复合包装','13');
-------------可回收物>>废织物类 14---------------------------------------------------------
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1400','背包','14');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1401','书包','14');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1402','衣物','14');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1403','地毯','14');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1404','棉被','14');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1405','桌布','14');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1406','丝绸制品','14');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1407','窗帘','14');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1408','皮鞋皮带','14');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1409','篮球','14');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1410','足球','14');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1411','皮球','14');
-------------可回收物>>废旧电器电子产品类 15---------------------------------------------------------
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1500','插座','15');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1501','手机','15');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1502','电线','15');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1503','电吹风','15');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1504','遥控器','15');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1505','电动剃须刀','15');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1506','打印机','15');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1507','数码产品','15');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1508','充电宝','15');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1509','电子表','15');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1510','电路板[主板/内存条]','15');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1511','硬盘','15');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1512','网线','15');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1513','磁盘','15');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('1514','U盘','15');
-------------易腐>>其他类 20---------------------------------------------------------
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2000','边角肉块','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2001','鸡鸭鱼内脏','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2002','蛋壳','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2003','菜帮菜叶','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2004','菌菇','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2005','鱼鳞','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2006','虾壳','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2007','螃蟹壳','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2008','鱿鱼','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2009','鸡鸭鱼骨头','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2010','玉米棒','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2011','剩菜剩饭','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2012','蛋糕','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2013','干果仁','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2014','饼干','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2015','大米','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2016','加工类食品','20');

insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2017','过期奶粉','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2018','过期面粉','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2019','过期食用油','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2020','过期白糖','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2021','过期红糖','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2022','过期糕团','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2023','果皮','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2024','水果茎枝','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2025','西瓜籽','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2026','瓜子壳','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2027','瓜果皮壳','20');

insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2028','盆栽落叶','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2029','多肉植物','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2030','花瓣','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2031','八角','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2032','花椒','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2033','桂皮','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2034','茶叶渣','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2035','玉米渣','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2036','甘蔗渣','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2037','果汁渣','20');

insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2038','中药药渣','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2039','酱料','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2040','火锅底料','20');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('2041','宠物饲料','20');


-------------其他>>其他类 30---------------------------------------------------------

insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3000','破碗碎碟','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3001','破花盆','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3002','破花瓶','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3003','碎玻璃瓶','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3004','陶瓷制品','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3005','面膜纸','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3006','卫生棉','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3007','卫生巾','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3008','已污染的塑料袋','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3009','已污染纸酒精棉及包装','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3010','普通干电池','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3011','受污染的纸尿裤','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3012','棉棒','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3013','一次性餐盒','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3014','快递外包装','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3015','牙刷','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3016','牙线','30');

insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3017','猫砂','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3018','狗尿垫','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3019','吸管','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3020','纸巾','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3021','创口贴','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3022','橡皮泥','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3023','透明胶带','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3024','胶带','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3025','笔','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3026','眼镜','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3027','眼镜盒','30');

insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3028','羽毛球','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3029','气球','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3030','浴帽','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3031','浴球','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3032','手机壳','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3033','手机膜','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3034','手机卡','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3035','鼠标垫','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3036','编织袋','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3037','砧板','30');

insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3038','塑料扫把','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3039','簸箕','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3040','绷带','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3041','受污染的墙纸','30');

insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3042','尼龙制品','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3043','干燥剂','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3044','橡皮筋','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3045','帐篷','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3046','伞','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3047','防尘罩','30');

insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3048','雨衣','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3049','遮光布','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3050','厨房用的钢丝球','30');
insert into gc_detail(gc_id,gc_name,gc_parent_id) VALUES('3051','花篮','30');







select * from gc_detail;

commit;





