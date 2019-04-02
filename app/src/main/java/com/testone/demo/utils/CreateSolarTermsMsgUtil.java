package com.testone.demo.utils;

import com.testone.demo.bean.SolarTermsBean;

public class CreateSolarTermsMsgUtil {

    public void createSolarTermsMsg() {
        SolarTermsBean lichunBean = new SolarTermsBean();
        lichunBean.setSolarTermsNumber(1);
        lichunBean.setSolarTerms("立春");
        lichunBean.setSolarTermsDes("东风解冻：冻结于冬，遇春风而解散，不曰春而曰东者，《吕氏春秋》曰：“东方属木。”木，火母也，火气温，故解冻。");
        lichunBean.save();

        SolarTermsBean yunshuiBean = new SolarTermsBean();
        yunshuiBean.setSolarTermsNumber(2);
        yunshuiBean.setSolarTerms("雨水");
        yunshuiBean.setSolarTermsDes("雨水，是二十四节气中第二个节气，指太阳到达黄经330°时，在公历每年2月18日-20日之间，表示雨水的增多。");
        yunshuiBean.save();

        SolarTermsBean jingzheBean = new SolarTermsBean();
        jingzheBean.setSolarTermsNumber(3);
        jingzheBean.setSolarTerms("惊蛰");
        jingzheBean.setSolarTermsDes("动物昆虫自入冬以来即藏伏土中，不饮不食，称为“蛰”；到了这时天气转暖，大地春雷，而“惊蛰”即上天以打雷方式惊醒蛰居动物的冬眠。这时中国大部分地区进入春耕季节。");
        jingzheBean.save();

        SolarTermsBean chunfenBean = new SolarTermsBean();
        chunfenBean.setSolarTermsNumber(4);
        chunfenBean.setSolarTerms("春分");
        chunfenBean.setSolarTermsDes("春分时，全球昼夜除极点附近以外几乎等长。春分之后，北半球各地昼渐长夜渐短，南半球各地夜渐长昼渐短。");
        chunfenBean.save();

        SolarTermsBean qingmingBean = new SolarTermsBean();
        qingmingBean.setSolarTermsNumber(5);
        qingmingBean.setSolarTerms("清明");
        qingmingBean.setSolarTermsDes("《岁时百问》中说：“万物生长此时，皆清洁而明净，故谓之清明。”《历书》：“春分后十五日，斗指丁，为清明，时万物皆洁齐而清明，盖时当气清景明，万物皆显，因此得名。");
        qingmingBean.save();

        SolarTermsBean guyuBean = new SolarTermsBean();
        guyuBean.setSolarTermsNumber(6);
        guyuBean.setSolarTerms("谷雨");
        guyuBean.setSolarTermsDes("谷雨是二十四节气中的第六个节气，是春季的最后一个节气，也是唯一将物候、时令与稼穑农事紧密对应的一个节气。“清明断雪，谷雨断霜”，谷雨节气的到来意味着寒潮天气已结束，极利于农作物中谷类作物的生长。有意思的是，此时中国江南地区秧苗初插、作物新种，最需要雨水的滋润，恰好此时的雨水也较多，每年的第一场大雨一般就出现于此时，对水稻栽插和玉米、棉花的苗期生长有利。");
        guyuBean.save();

        SolarTermsBean lixiaBean = new SolarTermsBean();
        lixiaBean.setSolarTermsNumber(7);
        lixiaBean.setSolarTerms("立夏");
        lixiaBean.setSolarTermsDes("《月令七十二候集解》：“立夏，四月节。立字解见春。夏，假也。物至此时皆假大也。 ”在天文学上，立夏表示即将告别春天，是夏天的开始。人们习惯上都把立夏当作是温度明显升高，炎暑将临，雷雨增多，农作物进入旺季生长的一个重要节气。");
        lixiaBean.save();

        SolarTermsBean xiaomanBean = new SolarTermsBean();
        xiaomanBean.setSolarTermsNumber(8);
        xiaomanBean.setSolarTerms("小满");
        xiaomanBean.setSolarTermsDes("四月中，小满者，物至于此小得盈满。");
        xiaomanBean.save();

        SolarTermsBean mangzhongBean = new SolarTermsBean();
        mangzhongBean.setSolarTermsNumber(9);
        mangzhongBean.setSolarTerms("芒种");
        mangzhongBean.setSolarTermsDes("芒种字面的意思是“有芒的麦子快收，有芒的稻子可种”。《月令七十二侯集解》：“五月节，谓有芒之种谷可稼种矣。”");
        mangzhongBean.save();

        SolarTermsBean xiazhiBean = new SolarTermsBean();
        xiazhiBean.setSolarTermsNumber(10);
        xiazhiBean.setSolarTerms("夏至");
        xiazhiBean.setSolarTermsDes("《月令七十二候集解》：“五月中……夏，假也，至，极也，万物于此皆假大而至极也。”");
        xiazhiBean.save();

        SolarTermsBean xiaoshuBean = new SolarTermsBean();
        xiaoshuBean.setSolarTermsNumber(11);
        xiaoshuBean.setSolarTerms("小暑");
        xiaoshuBean.setSolarTermsDes("“小暑之日，温风至，后五日蟋蟀居壁，后五日鹰乃学习。”");
        xiaoshuBean.save();

        SolarTermsBean dashuBean = new SolarTermsBean();
        dashuBean.setSolarTermsNumber(12);
        dashuBean.setSolarTerms("大暑");
        dashuBean.setSolarTermsDes("《月令七十二候集解》中说：“大暑，六月中。暑，热也，就热之中分为大小，月初为小，月中为大，今则热气犹大也。”其气候特征是：“斗指丙为大暑，斯时天气甚烈于小暑，故名曰大暑。”");
        dashuBean.save();

        SolarTermsBean liqiuBean = new SolarTermsBean();
        liqiuBean.setSolarTermsNumber(13);
        liqiuBean.setSolarTerms("立秋");
        liqiuBean.setSolarTermsDes("立秋，七月节。 立字解见春。秋，揫也，物于此而揫敛也。");
        liqiuBean.save();

        SolarTermsBean chushuBean = new SolarTermsBean();
        chushuBean.setSolarTermsNumber(14);
        chushuBean.setSolarTerms("处暑");
        chushuBean.setSolarTermsDes("处暑节气意味着即将进入气象意义的秋天，处暑后中国黄河以北地区气温逐渐下降。");
        chushuBean.save();

        SolarTermsBean bailuBean = new SolarTermsBean();
        bailuBean.setSolarTermsNumber(15);
        bailuBean.setSolarTerms("白露");
        bailuBean.setSolarTermsDes("《月令七十二候集解》中说：“八月节……阴气渐重，露凝而白也。” 天气渐转凉，会在清晨时分发现地面和叶子上有许多露珠，这是因夜晚水汽凝结在上面，故名白露。古人以四时配五行，秋属金，金色白，故以白形容秋露。进入“白露”，晚上会感到一丝丝的凉意。");
        bailuBean.save();

        SolarTermsBean qiufenBean = new SolarTermsBean();
        qiufenBean.setSolarTermsNumber(16);
        qiufenBean.setSolarTerms("秋分");
        qiufenBean.setSolarTermsDes("《月令七十二候集解》：“八月中，解见秋分”、“分者平也，此当九十日之半，故谓之分。”分就是半，这是秋季九十天的中分点。");
        qiufenBean.save();

        SolarTermsBean hanluBean = new SolarTermsBean();
        hanluBean.setSolarTermsNumber(17);
        hanluBean.setSolarTerms("寒露");
        hanluBean.setSolarTermsDes("《月令七十二候集解》中说：“九月节，露气寒冷，将凝结也。”此时气温较“白露”时更低，露水更多，原先地面上洁白晶莹的露水即将凝结成霜，寒意愈盛，故名。寒露也代表深秋的到来，气候由凉爽逐渐转入寒冷下雪");
        hanluBean.save();

        SolarTermsBean shuangjiangBean = new SolarTermsBean();
        shuangjiangBean.setSolarTermsNumber(18);
        shuangjiangBean.setSolarTerms("霜降");
        shuangjiangBean.setSolarTermsDes("霜降节气含有天气渐冷、初霜出现的意思，是秋季的最后一个节气，也意味着冬天即将开始。");
        shuangjiangBean.save();

        SolarTermsBean lidongBean = new SolarTermsBean();
        lidongBean.setSolarTermsNumber(19);
        lidongBean.setSolarTerms("立冬");
        lidongBean.setSolarTermsDes("立，建始也，表示冬季自此开始。冬是终了的意思，有农作物收割后要收藏起来的含意，中国又把立冬作为冬季的开始。");
        lidongBean.save();

        SolarTermsBean xiaoxueBean = new SolarTermsBean();
        xiaoxueBean.setSolarTermsNumber(20);
        xiaoxueBean.setSolarTerms("小雪");
        xiaoxueBean.setSolarTermsDes("《月令七十二候集解》曰：“十月中，雨下而为寒气所薄，故凝而为雪。小者未盛之辞。”");
        xiaoxueBean.save();

        SolarTermsBean daxueBean = new SolarTermsBean();
        daxueBean.setSolarTermsNumber(21);
        daxueBean.setSolarTerms("大雪");
        daxueBean.setSolarTermsDes("《月令七十二候集解》说：“大雪，十一月节。大者，盛也。至此而雪盛矣。”大雪的意思是天气更冷，降雪的可能性比小雪时更大了，并不指降雪量一定很大。");
        daxueBean.save();

        SolarTermsBean dongzhiBean = new SolarTermsBean();
        dongzhiBean.setSolarTermsNumber(22);
        dongzhiBean.setSolarTerms("冬至");
        dongzhiBean.setSolarTermsDes("《月令七十二候集解》：“十一月中，终藏之气，至此而极也。”");
        dongzhiBean.save();

        SolarTermsBean xiaohanBean = new SolarTermsBean();
        xiaohanBean.setSolarTermsNumber(23);
        xiaohanBean.setSolarTerms("小寒");
        xiaohanBean.setSolarTermsDes("《礼记．月令》：“季冬之月，日在婺女，昏娄中，旦氐中。其日壬癸。”小雪处季冬。《月令七十二候集解》：“十二月节，月初寒尚小，故云，月半则大矣。”");
        xiaohanBean.save();

        SolarTermsBean dahanBean = new SolarTermsBean();
        dahanBean.setSolarTermsNumber(24);
        dahanBean.setSolarTerms("大寒");
        dahanBean.setSolarTermsDes("《逸周书•周月》：“冬三月中气：小雪、冬至、大寒。”汉董仲舒《春秋繁露•阴阳出入上下》：“小雪而物咸成，大寒而物毕藏。”\n" +
                "《月令七十二候集解》：“十二月中，解见前。”《授时通考•天时》引《三礼义宗》：“大寒为中者，上形于小寒，故谓之大……寒气之逆极，故谓大寒。”");
        dahanBean.save();
    }
}
